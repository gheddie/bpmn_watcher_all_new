package de.gravitex.bpmn.server.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.camunda.bpm.engine.RepositoryService;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class ProviderUtil
{
	private static Document readXml(InputStream is) throws SAXException, IOException, ParserConfigurationException
	{
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

		dbf.setValidating(false);
		dbf.setIgnoringComments(false);
		dbf.setIgnoringElementContentWhitespace(true);
		dbf.setNamespaceAware(true);
		// dbf.setCoalescing(true);
		// dbf.setExpandEntityReferences(true);

		DocumentBuilder db = null;
		db = dbf.newDocumentBuilder();

		// db.setErrorHandler( new MyErrorHandler());

		return db.parse(is);
	}

	public static List<HashMap<String, String>> readPocessElements(String processDefinitionId, String processId, RepositoryService repositoryService)
	{
		try
		{
			Document document = readXml(repositoryService.getProcessModel(processDefinitionId));
			document.getDocumentElement().normalize();
			NodeList processNodeList = document.getElementsByTagName("bpmn2:process");
			System.out.println("read "+processNodeList.getLength()+" process nodes...");
			Element processNode = null;
			List<HashMap<String, String>> result = new ArrayList<HashMap<String,String>>();
			for (int processIndex = 0; processIndex < processNodeList.getLength(); processIndex++)
			{
				result.add(readPocessElementsRecursive(((Element) processNodeList.item(processIndex)), processDefinitionId, repositoryService, new HashMap<String, String>()));
			}
			return result;
		} catch (SAXException | IOException | ParserConfigurationException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public static HashMap<String, String> readPocessElementsRecursive(Node processNode, String processDefinitionId, RepositoryService repositoryService, HashMap<String, String> typesByElementId) throws SAXException, IOException, ParserConfigurationException
	{
		// search 'bpmn2:process' node for elemts...
		NodeList elementNodes = processNode.getChildNodes();
		Node elementNode = null;
		Element element = null;
		for (int elementIndex = 0; elementIndex < elementNodes.getLength(); elementIndex++)
		{
			elementNode = elementNodes.item(elementIndex);
			if (elementNode instanceof Element)
			{
				element = (Element) elementNode;
				if (element.getAttribute("id") != null)
				{
					System.out.println(element.getAttribute("id") + " :: " + elementNode.getNodeName());
					typesByElementId.put(element.getAttribute("id"), elementNode.getNodeName());
				}
			}
			//recursion
			readPocessElementsRecursive(elementNode, processDefinitionId, repositoryService, typesByElementId);
		}
		return typesByElementId;
	}
}