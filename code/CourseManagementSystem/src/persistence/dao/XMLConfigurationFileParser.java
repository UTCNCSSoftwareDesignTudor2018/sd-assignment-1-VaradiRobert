package persistence.dao;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XMLConfigurationFileParser {
	private static final String DEFAULT_CONFIGURATION_FILE_NAME = "./src/persistence/dao/configuration/mappings.config.xml";
	private String configurationFileName;
	private File fileToParse;
	public XMLConfigurationFileParser() {
		this.configurationFileName = DEFAULT_CONFIGURATION_FILE_NAME;
		this.fileToParse = new File(configurationFileName);
	}
	public XMLConfigurationFileParser(String fileName) {
		this.configurationFileName = fileName;
		this.fileToParse = new File(configurationFileName).getAbsoluteFile();
	}
	public DAOConfiguration parse(String entityName) {
		Map<String, Column> mapping = new HashMap<String, Column>();
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = null;
		try {
			dBuilder =  dbFactory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Document doc = null;
		try {
			doc = dBuilder.parse(fileToParse);
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		NodeList nList = doc.getElementsByTagName("mapping");
		int lengthNList = nList.getLength();
		for(int i = 0; i < lengthNList; i++) {
			Node nNode = nList.item(i);
			if(nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element)nNode;
				if(eElement.getAttribute("model").equals(entityName)) {
					String tableName = eElement.getAttribute("table");
					Node primaryKeyNode = eElement.getElementsByTagName("primary-key").item(0);
					Element primaryKeyElement = (Element)primaryKeyNode;
					mapping.put(primaryKeyElement.getAttribute("name"), new Column(primaryKeyElement.getAttribute("column"), true));
					NodeList fieldList = eElement.getElementsByTagName("field");
					int fieldListLength = fieldList.getLength();
					for(int j = 0; j < fieldListLength; j++) {
						Node n = fieldList.item(j);
						Element fieldElement = (Element)n;
						mapping.put(fieldElement.getAttribute("name"), new Column(fieldElement.getAttribute("column"), false));
					}
					return new DAOConfiguration(tableName, mapping);
				}
			}
		}
		return null;
	}
}
