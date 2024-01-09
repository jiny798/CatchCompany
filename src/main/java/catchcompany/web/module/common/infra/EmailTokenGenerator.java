package catchcompany.web.module.common.infra;

import java.util.UUID;

import org.springframework.stereotype.Component;

import catchcompany.web.module.common.service.port.TokenGenerator;

@Component
public class EmailTokenGenerator implements TokenGenerator {
	@Override
	public String random() {
		return UUID.randomUUID().toString();
	}
}
