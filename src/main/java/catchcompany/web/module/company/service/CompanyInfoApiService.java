package catchcompany.web.module.company.service;

import java.io.ByteArrayInputStream;
import java.util.stream.IntStream;
import java.util.zip.ZipInputStream;

import javax.xml.parsers.DocumentBuilderFactory;

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
import catchcompany.web.module.company.repository.CompanyRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class CompanyInfoApiService {

	private final CompanyRepository companyRepository;
	private final CompanyInvestInfoService companyInvestInfoService;
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

}
