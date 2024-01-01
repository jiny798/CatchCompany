package catchcompany.web.module.corporation.infrastructure;

import java.io.ByteArrayInputStream;
import java.util.zip.ZipInputStream;

import javax.xml.parsers.DocumentBuilderFactory;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.w3c.dom.Document;

import catchcompany.web.module.corporation.service.port.CorporationDataRestClient;
import catchcompany.web.module.uri.application.UriManager;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CorporationDataRestClientImpl implements CorporationDataRestClient {

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
