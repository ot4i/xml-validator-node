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
 *    A Java class that is called by the broker when a message
 *    enters the node. The evaluate method is called, which in turn
 *    passes the content of the message to a helper class that validates
 *    the message. If there are no problems with the XML content, the message
 *    is propagated to the output node, otherwise, the errors are placed in the
 *    Environment tree, and passed to the Invalid node.  
 * 
 */

package com.ibm.mq.supportpacs.XMLValidatorNode;
import com.ibm.broker.plugin.MbElement;
import com.ibm.broker.plugin.MbException;
import com.ibm.broker.plugin.MbInputTerminal;
import com.ibm.broker.plugin.MbMessageAssembly;
import com.ibm.broker.plugin.MbNode;
import com.ibm.broker.plugin.MbNodeInterface;
import com.ibm.broker.plugin.MbOutputTerminal;
import java.util.Vector;

public class XMLValidatorNode extends MbNode implements MbNodeInterface {
	
	private static final String nodeName = "XMLValidatorNode";
	private static final String outTerminal = "Out";
	private static final String inTerminal = "In";
	private static final String invalidTerminal = "Invalid";
	
	//Properties made visible to the broker, via get/set methods.
	private String SchemaLocation = "";
	private String NoNameSpaceSchemaLocation = "";
	private boolean ignoreWarnings = false;
	private boolean fullSchemaCheck = false;
	private String logFileName = "";
	
	private XMLValidatorLog l;
	
	/**
	 * Constructor for XMLValidator.
	 * @throws MbException
	 */
	public XMLValidatorNode() throws MbException {
		super();
		l = new XMLValidatorLog(logFileName);
		l.logEntry(nodeName,"Constructor");
		l.log(nodeName,"Creating terminals");
		createInputTerminal(inTerminal);
		createOutputTerminal(outTerminal);
		createOutputTerminal(invalidTerminal);
		l.logExit(nodeName,"Constructor");
	}

	/**
	 * @see com.ibm.broker.plugin.MbNodeInterface#evaluate(MbMessageAssembly, MbInputTerminal)
	 * 
	 * Method driven by the broker to process the message.
	 * Gets the bitstream of a message, and passes
	 * 
	 */
	public void evaluate(MbMessageAssembly arg0, MbInputTerminal arg1)
		throws MbException {
			l.logEntry(nodeName,"evaluate");
			String terminal = "";
			int errorSz = 0;
			//Obtain the bitstream of the message body, i.e. the last element of the message 
			byte[] bitstream = arg0.getMessage().getRootElement().getLastChild().toBitstream(null,null,null,0,0,0);
			//Convert the byte array into a string.
			String bitstreamText = new String(bitstream);
			l.log(nodeName,"Message body: " + bitstreamText);
			//Create a parser interface class, and set the ignoreWarnings and fullSchemaCheck flags.
			XMLValidatorParserInterface pi = new XMLValidatorParserInterface(l);
			pi.setIgnoreWarnings(ignoreWarnings);
			pi.setFullSchemaCheck(fullSchemaCheck);
			/*
			 * Check the node property, LocalEnvironment, and Environment to see if the NoNameSpaceSchemaLocation
			 * value should be set on the parser.
			 */
			if (!NoNameSpaceSchemaLocation.equals("")) {
				l.log(nodeName,"Setting NoNameSpaceSchemaLocation from message flow settings to: " + NoNameSpaceSchemaLocation);
				pi.setNoNSSchemaLocn(NoNameSpaceSchemaLocation);	
			} else {
				//The tree location of the NoNameSpaceSchemaLocation value
				String noNSSLPath = "/XMLValidator/NoNamespaceSchemaLocation";
				//Check the LocalEnvironment
				MbElement noNSSLLocEnv = arg0.getLocalEnvironment().getRootElement().getFirstElementByPath(noNSSLPath);
				if (noNSSLLocEnv != null) {
					l.log(nodeName,"Found LocalEnvironment variable for noNamespaceSchemaLocation:" + (String) noNSSLLocEnv.getValue());
					pi.setNoNSSchemaLocn((String) noNSSLLocEnv.getValue());
				} else {
					//LocalEnvironment was not set, check the global environment
					MbElement noNSSLEnv = arg0.getGlobalEnvironment().getRootElement().getFirstElementByPath(noNSSLPath);
					if (noNSSLEnv != null) {
						l.log(nodeName,"Found Environment variable for noNamespaceSchemaLocation:" + (String) noNSSLEnv.getValue());
						pi.setNoNSSchemaLocn((String) noNSSLEnv.getValue());
					} 
				}
			}
			/*
			 * Check the node property, LocalEnvironment, and Environment to see if the SchemaLocation
			 * value should be set on the parser.
			 */

			if (!SchemaLocation.equals("")) {
				l.log(nodeName,"Setting SchemaLocation from message flow settings to: " + SchemaLocation);
				pi.setSchemaLocn(SchemaLocation);	
			} else {
//				The tree location of the NoNameSpaceSchemaLocation value
				String SLPath = "/XMLValidator/SchemaLocation";
//				Check the LocalEnvironment
				MbElement SLLocEnv = arg0.getLocalEnvironment().getRootElement().getFirstElementByPath(SLPath);
				if (SLLocEnv != null) {
					l.log(nodeName,"Found LocalEnvironment variable for SchemaLocation:" + (String) SLLocEnv.getValue());
					pi.setSchemaLocn((String) SLLocEnv.getValue());
				} else {
					//LocalEnvironment was not set, check the global environment
					MbElement SLEnv = arg0.getGlobalEnvironment().getRootElement().getFirstElementByPath(SLPath);
					if (SLEnv != null) {
						l.log(nodeName,"Found Environment variable for SchemaLocation:" + (String) SLEnv.getValue());
						pi.setSchemaLocn((String) SLEnv.getValue());
					} 
				}
			}
			l.log(nodeName,"Validating...");
			//Do the validation
			Vector errors = pi.validateXML(bitstreamText);
			errorSz = errors.size(); 
			if (errorSz > 0) {
				//arg0.getGlobalEnvironment().getRootElement().createElementAsFirstChild(MbElement.TYPE_NAME,"noOfErrors",
				//Integer.toString(errorSz));
				//Create an XMLValidationErrors element to place the Errors in.
				MbElement errorLocn = arg0.getGlobalEnvironment().getRootElement().createElementAsFirstChild(MbElement.TYPE_NAME);
				errorLocn.setName("XMLValidationErrors");
				//Loop around for each of the errors, and place in the Environment Tree
				for (int x=0;x<errorSz;x++) {
					errorLocn.createElementAsLastChild(MbElement.TYPE_NAME,"Error",(String) errors.get(x));
				}
				//Route the message to the invalid terminal
				terminal = invalidTerminal;
			} else {
				//No errors detected, so send to the out terminal
				terminal = outTerminal;
			}
			l.log(nodeName,"Propagating message to " + terminal + "...");
			MbOutputTerminal outTerm = getOutputTerminal(terminal);
			outTerm.propagate(arg0);			
			l.logExit(nodeName,"evaluate");
			
	}
	/**
	 * Returns the nodeName, as required by the broker
	 * @return String
	 */
	public static String getNodeName() {
		return nodeName;	
	}
	/**
	 * Returns the noNSSchemaLocation.
	 * @return String
	 */
	public String getNoNameSpaceSchemaLocation() {
		return NoNameSpaceSchemaLocation;
	}

	/**
	 * Returns the schemaLocation.
	 * @return String
	 */
	public String getSchemaLocation() {
		return SchemaLocation;
	}

	/**
	 * Sets the noNSSchemaLocation.
	 * @param noNSSchemaLocation The noNSSchemaLocation to set
	 */
	public void setNoNameSpaceSchemaLocation(String noNSSchemaLocation) {
		l.logEntry(nodeName,"setNoNameSpaceSchemaLocation");
		l.log(nodeName,"Setting NoNameSpaceSchemaLocation to " + noNSSchemaLocation);
		NoNameSpaceSchemaLocation = noNSSchemaLocation;
		l.logExit(nodeName,"setNoNameSpaceSchemaLocation");
	}

	/**
	 * Sets the schemaLocation.
	 * @param schemaLocation The schemaLocation to set
	 */
	public void setSchemaLocation(String schemaLocation) {
		l.logEntry(nodeName,"setSchemaLocation");
		l.log(nodeName,"Setting SchemaLocation to " + schemaLocation);
		SchemaLocation = schemaLocation;
		l.logExit(nodeName,"setSchemaLocation");
	}

	/**
	 * Returns the ignoreWarnings.
	 * @return String
	 */
	public String getIgnoreWarnings() {
		Boolean iw = new Boolean(ignoreWarnings);
		return  iw.toString();
	}

	/**
	 * Sets the ignoreWarnings.
	 * @param ignoreWarnings The ignoreWarnings to set
	 */
	public void setIgnoreWarnings(String ignoreWarnings) {
		l.logEntry(nodeName,"setIgnoreWarnings");
		l.log(nodeName,"Setting ignoreWarnings to " + Boolean.valueOf(ignoreWarnings) + " from string \"" + ignoreWarnings + "\"");
		this.ignoreWarnings = Boolean.valueOf(ignoreWarnings).booleanValue();
		l.logExit(nodeName,"setIgnoreWarnings");
	}
	/**
	 * Returns the fullSchemaCheck.
	 * @return String
	 */
	public String getFullSchemaCheck() {
		Boolean fsc = new Boolean(fullSchemaCheck);
		return fsc.toString();
	}

	/**
	 * Sets the fullSchemaCheck.
	 * @param fullSchemaCheck The fullSchemaCheck to set
	 */
	public void setFullSchemaCheck(String fullSchemaCheck) {
		this.fullSchemaCheck = Boolean.valueOf(fullSchemaCheck).booleanValue();
	}

	/**
	 * @return Returns the enableLogging.
	 */
	public String getEnableLogging() {
		Boolean el = new Boolean(l.isEnableLogging());
		return el.toString();
	}
	/**
	 * @param enableLogging The enableLogging to set.
	 */
	public void setEnableLogging(String enableLogging) {
		l.setEnableLogging(Boolean.valueOf(enableLogging).booleanValue());
	}
	/**
	 * @return Returns the logFileName.
	 */
	public String getLogFileName() {
		return l.getLogFileName();
	}
	/**
	 * @param logFileName The logFileName to set.
	 */
	public void setLogFileName(String logFileName) {
		l.setLogFileName(logFileName);
	}
}
