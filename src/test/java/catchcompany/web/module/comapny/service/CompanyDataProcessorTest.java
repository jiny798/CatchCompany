package catchcompany.web.module.comapny.service;

import static org.assertj.core.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import catchcompany.web.module.company.domain.Company;
import catchcompany.web.module.company.service.CompanyDataProcessor;
import catchcompany.web.module.company.service.port.CompanyDataRestClient;
import catchcompany.web.module.mock.MockCompanyDataRestClient;

public class CompanyDataProcessorTest {

	@Test
	public void 회사정보_XML_을_파싱하여_List_로_가져올_수_있다() throws InterruptedException {
		// given
		CompanyDataRestClient client = new MockCompanyDataRestClient(
			"<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n"
				+ "<result>\n"
				+ "    <list>\n"
				+ "        <corp_code>00888888</corp_code>\n"
				+ "        <corp_name>샘숭전자</corp_name>\n"
				+ "        <stock_code>1111</stock_code>\n"
				+ "        <modify_date>20170630</modify_date>\n"
				+ "    </list>\n"
				+ "    <list>\n"
				+ "        <corp_code>00999999</corp_code>\n"
				+ "        <corp_name>엔지전자</corp_name>\n"
				+ "        <stock_code>2222</stock_code>\n"
				+ "        <modify_date>20170630</modify_date>\n"
				+ "    </list>\n"
				+ "</result>");
		CompanyDataProcessor companyDataProcessor = new CompanyDataProcessor(client);

		// when
		List<Company> companies = companyDataProcessor.getCompanyList();
		Company company1 = companies.get(0);
		Company company2 = companies.get(1);

		// then
		if(company1.getName().equals("샘숭전자")) {
			assertThat(company1.getName()).isEqualTo("샘숭전자");
			assertThat(company1.getCorporationCode()).isEqualTo("00888888");
			assertThat(company1.getStockCode()).isEqualTo("1111");
			assertThat(company2.getName()).isEqualTo("엔지전자");
			assertThat(company2.getCorporationCode()).isEqualTo("00999999");
			assertThat(company2.getStockCode()).isEqualTo("2222");
		}else{
			assertThat(company2.getName()).isEqualTo("샘숭전자");
			assertThat(company2.getCorporationCode()).isEqualTo("00888888");
			assertThat(company2.getStockCode()).isEqualTo("1111");
			assertThat(company1.getName()).isEqualTo("엔지전자");
			assertThat(company1.getCorporationCode()).isEqualTo("00999999");
			assertThat(company1.getStockCode()).isEqualTo("2222");
		}

	}

	@Test
	public void List_는_멀티스레드에서_안전하지_않다() {
		List<Integer> list = new ArrayList<>();
		IntStream stream = IntStream.rangeClosed(0, 100);
		stream.parallel().forEach(i -> {
			list.add(i);
		});

		System.out.println(list.size());
	}
}
