package catchcompany.web.module.mock;

import catchcompany.web.module.common.service.port.ClockHolder;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MockClockHolder implements ClockHolder {
	private final long millis;

	@Override
	public long millis() {
		return millis;
	}
}
