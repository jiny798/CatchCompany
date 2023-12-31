package catchcompany.web.module.corporation.controller.dto;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class InvestInfoResponse {

	private String status;
	private String message;
	private List<InvestCompanyResponse> list;

}
