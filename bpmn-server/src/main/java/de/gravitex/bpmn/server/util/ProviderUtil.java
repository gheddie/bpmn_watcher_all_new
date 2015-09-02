package de.gravitex.bpmn.server.util;

import java.io.IOException;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class ProviderUtil
{
	  public static Document readXml(InputStream is) throws SAXException, IOException,
      ParserConfigurationException {
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
}