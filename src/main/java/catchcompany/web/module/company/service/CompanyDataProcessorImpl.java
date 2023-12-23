package catchcompany.web.module.company.service;

import java.io.ByteArrayInputStream;
import java.util.zip.ZipInputStream;

import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class CompanyDataProcessorImpl implements CompanyDataProcessor {

	private final RestTemplate restTemplate;

	public NodeList getNodeList(String uri) {
		Document document = restTemplate.execute(uri, HttpMethod.GET, null, response -> {
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
		document.getDocumentElement().normalize();
		Element root = document.getDocumentElement();
		NodeList nList = root.getElementsByTagName("list");
		return nList;
	}
}
