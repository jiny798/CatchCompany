package catchcompany.web.module.mock;

import catchcompany.web.module.common.service.port.TokenGenerator;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MockTokenGenerator implements TokenGenerator {
	private final String uuid;

	@Override
	public String random() {
		return uuid;
	}
}
