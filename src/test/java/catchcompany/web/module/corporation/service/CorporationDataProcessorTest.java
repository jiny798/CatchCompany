package catchcompany.web.module.corporation.service;

import static org.assertj.core.api.Assertions.*;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;

import catchcompany.web.module.corporation.domain.Corporation;
import catchcompany.web.module.corporation.service.processor.CorporationDataProcessor;
import catchcompany.web.module.corporation.service.port.CorporationDataRestClient;
import catchcompany.web.module.mock.MockCorporationDataRestClient;

public class CorporationDataProcessorTest {

	@Test
	public void 회사정보_XML_을_파싱하여_List_로_가져올_수_있다() throws InterruptedException {
		// given
		CorporationDataRestClient client = new MockCorporationDataRestClient(
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
		CorporationDataProcessor corporationDataProcessor = new CorporationDataProcessor(client);

		// when
		List<Corporation> companies = corporationDataProcessor.getCompanyList();
		Corporation corporation1 = companies.get(0);
		Corporation corporation2 = companies.get(1);

		// then
		if(corporation1.getName().equals("샘숭전자")) {
			assertThat(corporation1.getName()).isEqualTo("샘숭전자");
			assertThat(corporation1.getCorporationCode()).isEqualTo("00888888");
			assertThat(corporation1.getStockCode()).isEqualTo("1111");
			assertThat(corporation2.getName()).isEqualTo("엔지전자");
			assertThat(corporation2.getCorporationCode()).isEqualTo("00999999");
			assertThat(corporation2.getStockCode()).isEqualTo("2222");
		}else{
			assertThat(corporation2.getName()).isEqualTo("샘숭전자");
			assertThat(corporation2.getCorporationCode()).isEqualTo("00888888");
			assertThat(corporation2.getStockCode()).isEqualTo("1111");
			assertThat(corporation1.getName()).isEqualTo("엔지전자");
			assertThat(corporation1.getCorporationCode()).isEqualTo("00999999");
			assertThat(corporation1.getStockCode()).isEqualTo("2222");
		}

	}

	@Test
	public void List_는_멀티스레드에서_안전하지_않다() {
		List<Integer> list = new CopyOnWriteArrayList<>();
		IntStream stream = IntStream.rangeClosed(0, 499);
		stream.parallel().forEach(i -> {
			list.add(i);
		});

		assertThat(list.size()).isEqualTo(500);
	}
}
