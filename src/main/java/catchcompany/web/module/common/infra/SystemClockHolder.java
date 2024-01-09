package catchcompany.web.module.common.infra;

import java.time.Clock;

import org.springframework.stereotype.Component;

import catchcompany.web.module.common.service.port.ClockHolder;

@Component
public class SystemClockHolder implements ClockHolder {

	@Override
	public long millis() {
		return Clock.systemUTC().millis();
	}
}
