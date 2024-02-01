package catchcompany.web.module.common.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PageInfo {
	private int number;
	private int pageLimit;
	private int startPage;
	private int endPage;
	private boolean first;
	private boolean last;
}
