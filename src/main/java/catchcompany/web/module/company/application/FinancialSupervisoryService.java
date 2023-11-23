package catchcompany.web.module.company.application;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.IntStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

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
import org.xml.sax.SAXException;

import catchcompany.web.module.company.domain.Company;
import catchcompany.web.module.company.domain.CompanyRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class FinancialSupervisoryService {

	private final CompanyRepository companyRepository;
	private final InvestInfoService investInfoService;
	@Value("${dart.api-key}")
	private String apiKey;
	private static final String DIRECTORY = "C:\\Users\\catch\\";
	private static final String FILE_NAME = "CORPCODE.xml";

	public void processCompanyInfoToDatabase() {
		// RestTemplate restTemplate = new RestTemplate();
		// UriComponents uriComponents = UriComponentsBuilder
		// 	.fromHttpUrl("https://opendart.fss.or.kr/api")
		// 	.pathSegment("corpCode.xml")
		// 	.queryParam("crtfc_key", apiKey)
		// 	.build();
		//
		// Document document = restTemplate.execute(uriComponents.toUriString(), HttpMethod.GET, null, response -> {
		// 	ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(response.getBody().readAllBytes());
		// 	ZipInputStream zipInputStream = new ZipInputStream(byteArrayInputStream);
		// 	Document tmpDocument = null;
		// 	try {
		// 		zipInputStream.getNextEntry();
		// 		tmpDocument = DocumentBuilderFactory.newDefaultInstance().newDocumentBuilder().parse(zipInputStream);
		// 	} catch (Exception e) {
		// 		e.printStackTrace();
		// 	}
		// 	return tmpDocument;
		// });

		investInfoService.processCompanyInvestInfoToDatabase(null);
		// processXmlToDatabase(document);
	}

	public void processXmlToDatabase(Document document) {
		document.getDocumentElement().normalize();
		Element root = document.getDocumentElement();
		NodeList nList = root.getElementsByTagName("list");
		IntStream stream = IntStream.rangeClosed(0,nList.getLength() );
		stream.parallel().forEach(i -> {
			Node nNode = nList.item(i);
			Element eElement = (Element)nNode;
			Company company = companyRepository.save(Company.createCompany(
				getTagValue("corp_code", eElement),
				getTagValue("corp_name", eElement)));
			investInfoService.processCompanyInvestInfoToDatabase(company);
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
