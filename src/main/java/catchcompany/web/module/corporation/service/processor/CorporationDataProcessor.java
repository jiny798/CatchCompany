package catchcompany.web.module.corporation.service.processor;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.IntStream;

import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import catchcompany.web.module.corporation.domain.Corporation;
import catchcompany.web.module.corporation.service.port.CorporationDataRestClient;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class CorporationDataProcessor {

	private final CorporationDataRestClient restCorporationDataClient;

	public List<Corporation> getCompanyList() {
		Document document = restCorporationDataClient.execute();

		List<Corporation> corporationList = new CopyOnWriteArrayList<>();
		document.getDocumentElement().normalize();
		Element root = document.getDocumentElement();
		NodeList nodeList = root.getElementsByTagName("list");
		IntStream stream = IntStream.rangeClosed(0, nodeList.getLength());
		stream.parallel().forEach(i -> {
			Node nNode = nodeList.item(i);
			Element eElement = (Element)nNode;
			if (eElement != null) {
				corporationList.add(Corporation.createCompany(
					getTagValue("corp_code", eElement),
					getTagValue("corp_name", eElement),
					getTagValue("stock_code", eElement)));
			}
		});

		return corporationList;
	}

	private String getTagValue(String tag, Element eElement) {
		NodeList nlList = eElement.getElementsByTagName(tag).item(0).getChildNodes();
		Node nValue = (Node)nlList.item(0);
		if (nValue == null)
			return null;
		return nValue.getNodeValue();
	}
}
