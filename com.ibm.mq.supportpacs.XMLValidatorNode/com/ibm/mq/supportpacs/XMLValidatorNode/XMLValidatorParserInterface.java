/*
 * Copyright (c) IBM Corporation 2004
 * 
 * PLEASE NOTE - This program is supplied "AS IS" with no
 *               warranty or liability. It is not part of
 *               any product. Please ensure that you read
 *               and understand it before you run it. Make
 *               sure that by running it you will not
 *               overwrite or delete any important data.
 * 
 * Part of WebSphere Business Integration Message Broker SupportPac IA9A
 * 
 * Description:
 *    A Java class that provides an interface to an XML Parser
 *    for use by the XMLValidatorNode class. Certain XML Parser
 * 	  properties are set to enable validation of the XML when parsed
 *    and the parser can also be forced to use a particular schema
 *    file. 
 * 
 */
package com.ibm.mq.supportpacs.XMLValidatorNode;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Vector;

import org.apache.xerces.parsers.DOMParser;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;

public class XMLValidatorParserInterface {

	private InputSource inputSrc = null;
	private static String className = "XMLValidatorParserInterface";
	private XMLValidatorErrorHandler errHandler =
		new XMLValidatorErrorHandler();
	private DOMParser parser = new DOMParser();
	private boolean fullSchemaCheck = false;
	private XMLValidatorLog l;
	
	/**
	 * Constructor for ParserInterface.
	 */
	public XMLValidatorParserInterface(XMLValidatorLog l) {
		super();
		this.l = l;
	}
	/*
	 * Method to validate the XML document, passed to the method in the form
	 * of a String object. The string is parsed and validated according to the
	 * properties set using the other methods available on this class.
	 * 
	 * A vector containing string objects describing any errors encountered is
	 * returned. 
	 */
	public Vector validateXML(String doc) {
		l.logEntry(className,"validateXML");
		setXMLData(doc);
		parseXMLData();
		l.logExit(className,"validateXML");
		return errHandler.getErrors();
	}
	/*
	 * Enables the setting of the schemaLocation property on the Xerces parser
	 * This allows the overriding of the schemaLocation attribute in XML documents,
	 * to force the usage of a particular schema file, and may be a list of namespace - schema files.
	 * 
	 */
	public void setSchemaLocn(String locn) {
		setProperty("http://apache.org/xml/properties/schema/external-schemaLocation",locn);
	}
	/*
	 * Enables the setting of the noNamespaceSchemaLocation property on the Xerces parser
	 * This allows the overriding of the noNamespaceSchemaLocation attribute in XML documentsm
	 * to force the usage of a particular schema file for documents without a namespace.
	 */
	public void setNoNSSchemaLocn(String locn) {
		setProperty("http://apache.org/xml/properties/schema/external-noNamespaceSchemaLocation",locn);
	}

	/*
	 * Parses the document set via the setXMLData function
	 */
	private void parseXMLData() {
		l.logEntry(className,"parseXMLData");
		parser.setErrorHandler(errHandler);
		setFeature("http://xml.org/sax/features/validation", true);
		setFeature("http://apache.org/xml/features/validation/schema",true);
		if (fullSchemaCheck) {
			setFeature("http://apache.org/xml/features/validation/schema-full-checking",true);
		}
		l.logExit(className,"parseXMLData");
		try {
			parser.parse(inputSrc);
		} catch (IOException io) {
			io.printStackTrace();
			//throw new Exception("IO Exception");
		} catch (SAXException saxe) {
			l.log(className,"SAX Parser exception caught whilst parsing data");
			saxe.printStackTrace();
			//throw new Exception("SAX Parser Exception");

		}

	}
	
	/*
	 * Allows an arbritary feature to be set on the parser.
	 */
	private void setFeature(String feature, boolean setting) {
		try {
			l.log(className,"Setting parser feature " + feature + " to \"" + setting + "\"");
			parser.setFeature(feature, setting);
		} catch (SAXNotRecognizedException e) {
			l.log(className,"Unrecognized feature: ");
			l.log(className,feature);
		} catch (SAXNotSupportedException e) {
			l.log(className,"Unrecognized feature: ");
			l.log(className,feature);
		}
	}
	
	/*
	 * Allows an arbritary property to be set on the parser.
	 */
	private void setProperty(String feature, String setting) {
		l.logEntry(className,"setProperty");
		try {
			l.log(className,"Setting parser property " + feature + " to \"" + setting + "\"");
			parser.setProperty(feature, setting);
		} catch (SAXNotRecognizedException e) {
			l.log(className,"Unrecognized feature: ");
			l.log(className,feature);
		} catch (SAXNotSupportedException e) {
			l.log(className,"Unrecognized feature: ");
			l.log(className,feature);
		}
		l.logExit(className,"setProperty");
	}

	/*
	 * Sets the data to be parsed.
	 */
	private void setXMLData(String document) {
		l.logEntry(className,"setXMLData");
		byte[] ba = document.getBytes();
		ByteArrayInputStream bais = new ByteArrayInputStream(ba);
		inputSrc = new InputSource(bais);
		l.logExit(className,"setXMLData");

	}

	/*
	 * Sets the XMLValidatorErrorHandler to ignore warnings.
	 */
	public void setIgnoreWarnings(boolean iw) {
		errHandler.setIgnoreWarnings(iw);	
	}
	
	/**
	 * Sets the fullSchemaCheck.
	 * @param fullSchemaCheck The fullSchemaCheck to set
	 */
	public void setFullSchemaCheck(boolean fullSchemaCheck) {
		this.fullSchemaCheck = fullSchemaCheck;
	}

}
