package catchcompany.web.module.mock;

import java.io.IOException;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import catchcompany.web.module.corporation.service.port.CorporationDataRestClient;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MockCorporationDataRestClient implements CorporationDataRestClient {

	private final String responseBody;

	@Override
	public Document execute() {
		DocumentBuilder db = null;
		Document document = null;
		InputSource is = new InputSource();
		is.setCharacterStream(new StringReader(responseBody));
		try {
			db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			document = db.parse(is);
		} catch (ParserConfigurationException | SAXException | IOException ex) {
			ex.printStackTrace();
		}

		return document;
	}
}
