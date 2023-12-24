package catchcompany.web.module.company.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import catchcompany.web.module.company.domain.Company;
import catchcompany.web.module.company.service.port.CompanyDataRestClient;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class CompanyDataProcessor {

	private final CompanyDataRestClient restCompanyDataClient;

	public List<Company> getCompanyList() {
		Document document = restCompanyDataClient.execute();

		List<Company> companyList = new CopyOnWriteArrayList<>();
		document.getDocumentElement().normalize();
		Element root = document.getDocumentElement();
		NodeList nodeList = root.getElementsByTagName("list");
		IntStream stream = IntStream.rangeClosed(0, nodeList.getLength());
		stream.parallel().forEach(i -> {
			Node nNode = nodeList.item(i);
			Element eElement = (Element)nNode;
			if (eElement != null) {
				companyList.add(Company.createCompany(
					getTagValue("corp_code", eElement),
					getTagValue("corp_name", eElement),
					getTagValue("stock_code", eElement)));
			}
		});

		return companyList;
	}

	private String getTagValue(String tag, Element eElement) {
		NodeList nlList = eElement.getElementsByTagName(tag).item(0).getChildNodes();
		Node nValue = (Node)nlList.item(0);
		if (nValue == null)
			return null;
		return nValue.getNodeValue();
	}
}
