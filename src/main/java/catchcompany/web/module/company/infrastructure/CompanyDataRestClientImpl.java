package catchcompany.web.module.company.infrastructure;

import java.io.ByteArrayInputStream;
import java.util.zip.ZipInputStream;

import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.Document;

import catchcompany.web.module.company.service.port.CompanyDataRestClient;
import catchcompany.web.module.uri.application.UriManager;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CompanyDataRestClientImpl implements CompanyDataRestClient {

	private final RestTemplate restTemplate;
	private final UriManager uriManager;


	@Override
	public Document execute() {
		return restTemplate.execute(uriManager.getCorpCodeUri(), HttpMethod.GET, null, response -> {
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
	}
}
