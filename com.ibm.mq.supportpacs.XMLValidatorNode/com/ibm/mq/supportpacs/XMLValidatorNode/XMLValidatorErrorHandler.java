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
 *    A Java class used to keep track of errors reported
 *    by the XML Parser		
 *  
 * 
 */

package com.ibm.mq.supportpacs.XMLValidatorNode;
import java.util.Vector;

import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class XMLValidatorErrorHandler implements ErrorHandler {
	
	private Vector errors = new Vector();
	private boolean ignoreWarnings = false;
	
	/**
	 * Constructor for XMLValidatorErrorHandler.
	 */
	public XMLValidatorErrorHandler() {
		super();
	}

	/**
	 * @see org.xml.sax.ErrorHandler#error(SAXParseException)
	 * 
	 * Called by the parser when an error is found
	 */
	public void error(SAXParseException arg0) throws SAXException {
		errors.add( new String("Error: " + getErrorText(arg0)) );
		//System.out.println("Error occurred: " + arg0.getMessage());
	}

	/**
	 * @see org.xml.sax.ErrorHandler#fatalError(SAXParseException)
	 * 
	 * Called by the parser when a fatal error is found
	 */
	public void fatalError(SAXParseException arg0) throws SAXException {
		errors.add( new String("Fatal Error: " + getErrorText(arg0)) );
		//System.out.println("Error occurred: " + arg0.getMessage());
	}

	/**
	 * @see org.xml.sax.ErrorHandler#warning(SAXParseException)
	 * 
	 * Called by the parser when a  warning is found. The reporting of these
	 * warnings can be surpressed if the method setIgnoreWarnings(boolean) is called
	 * with a true parameter.
	 */
	public void warning(SAXParseException arg0) throws SAXException {
		if (!ignoreWarnings) {
			errors.add( new String("Warning: " + getErrorText(arg0)) );
			//System.out.println("Error occurred: " + arg0.getMessage());
		}
	}

	/*
	 * A private method that returns a formatted version of the SAX Exception
	 */
	private String getErrorText(SAXParseException saxe) {
		return "[" + saxe.getLineNumber() + ":" + saxe.getColumnNumber() + "] " + saxe.getMessage();
	}

	/**
	 * Returns a vector of the errors encountered. All objects within the vector are string
	 * objects containing details of the exceptions encountered.
	 * @return Vector
	 */
	public Vector getErrors() {
		return errors;
	}

	/**
	 * Returns the ignoreWarnings flag. 
	 * @return boolean
	 */
	public boolean isIgnoreWarnings() {
		return ignoreWarnings;
	}

	/**
	 * Sets the ignoreWarnings flag, which determines whether warnings will be reported
	 * in the Vector returned by getErrors()
	 * 
	 * @param ignoreWarnings The ignoreWarnings to set
	 */
	public void setIgnoreWarnings(boolean ignoreWarnings) {
		this.ignoreWarnings = ignoreWarnings;
	}

}
