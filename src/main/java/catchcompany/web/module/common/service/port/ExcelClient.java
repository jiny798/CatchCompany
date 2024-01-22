package catchcompany.web.module.common.service.port;

import java.util.List;
import java.util.function.Function;

public interface ExcelClient {
	public <R> List<R> getRowList(String filePath, Function<List<String>, R> function);

	public <R> List<R> getRowList(String filePath, int startRowNum, int endRowNum, Function<List<String>, R> function);
}
