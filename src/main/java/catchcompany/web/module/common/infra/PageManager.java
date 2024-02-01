package catchcompany.web.module.common.infra;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import catchcompany.web.module.common.dto.PageInfo;

@Component
public class PageManager {

	public PageInfo generatePageInfo(Page page, int pageNum, int limit) {
		int pageLimit = limit;
		int startPage = (((pageNum - 1) / pageLimit) * pageLimit) + 1;
		int endPage = Math.min((startPage + pageLimit - 1), page.getTotalPages());
		if (endPage == 0)
			endPage = 1;

		return new PageInfo(pageNum, pageLimit, startPage, endPage, page.isFirst(), page.isLast());
	}
}
