package catchcompany.web.module.company.service;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import java.util.zip.ZipInputStream;

import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import catchcompany.web.module.company.domain.Company;
import catchcompany.web.module.company.domain.CompanyInvestInfo;
import catchcompany.web.module.company.repository.CompanyInvestInfoRepository;
import catchcompany.web.module.company.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class CompanyInfoClient {

	private final CompanyRepository companyRepository;

	private final CompanyInvestInfoRepository companyInvestInfoRepository;
	@Value("${dart.api-key}")
	private String apiKey;

	public void processCompanyInfoToDatabase() {
		RestTemplate restTemplate = new RestTemplate();
		UriComponents uriComponents = UriComponentsBuilder
			.fromHttpUrl("https://opendart.fss.or.kr/api")
			.pathSegment("corpCode.xml")
			.queryParam("crtfc_key", apiKey)
			.build();

		Document document = restTemplate.execute(uriComponents.toUriString(), HttpMethod.GET, null, response -> {
			ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(response.getBody().readAllBytes());
			ZipInputStream zipInputStream = new ZipInputStream(byteArrayInputStream);
			Document tmpDocument = null;
			try {
				zipInputStream.getNextEntry();
				tmpDocument = DocumentBuilderFactory.newDefaultInstance().newDocumentBuilder().parse(zipInputStream);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return tmpDocument;
		});

		processXmlToDatabase(document);
	}

	private void processXmlToDatabase(Document document) {
		document.getDocumentElement().normalize();
		Element root = document.getDocumentElement();
		NodeList nList = root.getElementsByTagName("list");
		IntStream stream = IntStream.rangeClosed(0, nList.getLength());
		stream.parallel().forEach(i -> {
			Node nNode = nList.item(i);
			Element eElement = (Element)nNode;
			if (eElement != null) {
				Company company = companyRepository.save(Company.createCompany(
					getTagValue("corp_code", eElement),
					getTagValue("corp_name", eElement),
					getTagValue("stock_code", eElement)));
			}
		});
	}

	private String getTagValue(String tag, Element eElement) {
		NodeList nlList = eElement.getElementsByTagName(tag).item(0).getChildNodes();
		Node nValue = (Node)nlList.item(0);
		if (nValue == null)
			return null;
		return nValue.getNodeValue();
	}

	public void processCompanyInvestInfoToDatabase() {
		try {
			FileInputStream file = new FileInputStream("C:\\Users\\file\\BusinessReport.xls");
			HSSFWorkbook workbook = new HSSFWorkbook(file);
			HSSFSheet sheet = workbook.getSheetAt(0); //첫번째 시트탭
			int rows = sheet.getPhysicalNumberOfRows(); // 행의 길이를 모두 불러온다
			IntStream stream = IntStream.range(0, rows);
			stream.parallel().forEach(rowIndex -> {
				List<String> recordInfoList = new ArrayList<>();
				HSSFRow row = sheet.getRow(rowIndex); //행 읽기
				if (row != null) {
					int cells = row.getPhysicalNumberOfCells();
					for (int colIndex = 0; colIndex <= cells; colIndex++) {
						//셀값을 읽는다
						HSSFCell cell = row.getCell(colIndex);
						String value = "";
						//셀이 빈값일경우를 위한 널체크
						if (cell == null) {
							continue;
						} else {
							//타입별로 내용 읽기
							switch (cell.getCellType()) {
								case FORMULA:
									value = cell.getCellFormula();
									break;
								case NUMERIC:
									value = cell.getNumericCellValue() + "";
									break;
								case STRING:
									value = cell.getStringCellValue() + "";
									break;
								case BLANK:
									value = cell.getBooleanCellValue() + "";
									break;
								case ERROR:
									value = cell.getErrorCellValue() + "";
									break;
							}
						}

						recordInfoList.add(value);
					} //컬럼 읽기 종료
				}
				if (rowIndex > 2 && recordInfoList.size() >= 16 && !recordInfoList.get(4).trim().equals("합계")
					&& !recordInfoList.get(4).trim().equals("-")) {
					String investorName = recordInfoList.get(0).trim(); // 투자하는 회사명
					String investorCode = recordInfoList.get(1).trim(); // 투자하는 회사 stock 코드
					String corporationClass = null;
					String investCompany = recordInfoList.get(4).trim(); // 투자받는 회사명
					String investDate = recordInfoList.get(5).trim(); // 투자 날짜
					String investTarget = recordInfoList.get(6).trim(); // 투자목적
					String change1 = recordInfoList.get(11).trim(); // 증감수량
					String change2 = recordInfoList.get(12).trim(); // 증감 취득,처분 금액
					String change3 = recordInfoList.get(13).trim(); // 증감 평가손액
					String current1 = recordInfoList.get(14).trim(); // 기말잔액 수량
					String current2 = recordInfoList.get(15).trim(); // 기말잔액 지분율
					String current3 = recordInfoList.get(16).trim(); // 기말잔액 장부가액
					List<Company> companies = companyRepository.findByName(investorName.trim());
					Company company = null;
					if (companies != null) {
						company = companies.get(0);
					}
					if (investTarget.indexOf("투자") >= 0) {
						investTarget = "투자";
					}
					String corpName = investCompany.replaceAll("(주)", "");
					corpName = investCompany.replaceAll("㈜", "");
					List<Company> corpList = companyRepository.findByName(corpName);
					corporationClass = "-";
					if (!corpList.isEmpty()) {
						for (Company c : corpList) {
							if (!c.getStockCode().isBlank()) {
								corporationClass = "상장";
								break;
							}
						}
					}

					CompanyInvestInfo companyInvestInfo = CompanyInvestInfo.builder()
						.company(company)
						.investorName(investorName)
						.corporationCode(investorCode)
						.name(investCompany)
						.corporationClass(corporationClass)
						.investTarget(investTarget)
						.initialInvestmentDate(investDate)
						.currentStockCount(current1)
						.currentStockShareRatio(current2)
						.currentStockEvaluationValue(current3)
						.recentStockAmountOfChange(change1)
						.recentAcquisitionAmount(change2)
						.recentEvaluationGainsAndLosses(change3)
						.build();
					companyInvestInfoRepository.save(companyInvestInfo);
				}
			});

		} catch (IOException ex) {
			ex.printStackTrace();
		}

	}

}
