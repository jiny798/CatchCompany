package catchcompany.web.module.uri.application;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class UriManager {
	@Value("${dart.api-key}")
	private String apiKey;

	public String getCorpCodeUri() {
		return UriComponentsBuilder
			.fromHttpUrl("https://opendart.fss.or.kr/api")
			.pathSegment("corpCode.xml")
			.queryParam("crtfc_key", apiKey)
			.build()
			.toUriString();
	}

}
