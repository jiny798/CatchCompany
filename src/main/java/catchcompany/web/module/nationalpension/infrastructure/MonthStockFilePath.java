package catchcompany.web.module.nationalpension.infrastructure;

import org.springframework.stereotype.Component;

import catchcompany.web.module.common.service.port.FilePath;

@Component
public class MonthStockFilePath implements FilePath {
	private String path = "C:\\Users\\file\\3q.xlsx";
	@Override
	public String getPath() {
		return path;
	}
}
