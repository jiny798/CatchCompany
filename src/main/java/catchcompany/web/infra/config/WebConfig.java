package catchcompany.web.infra.config;

import java.time.Duration;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class WebConfig {
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
		return restTemplateBuilder
			.requestFactory(() ->
				new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory())
			)
			.setConnectTimeout(Duration.ofMillis(10000)) // connection-timeout
			.setReadTimeout(Duration.ofMillis(10000)) // read-timeout
			.build();
	}
}
