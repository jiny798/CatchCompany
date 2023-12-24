package catchcompany.web.module.common.service.port;

import org.springframework.http.HttpMethod;
import org.springframework.lang.Nullable;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;

public interface RestTemplateClient<T> {
	public <T> T execute(String uri, HttpMethod httpMethod, @Nullable RequestCallback requestCallback,
		@Nullable ResponseExtractor<T> responseExtractor);
}
