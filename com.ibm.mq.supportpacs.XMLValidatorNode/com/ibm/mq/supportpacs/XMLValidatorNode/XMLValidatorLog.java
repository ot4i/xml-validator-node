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
 *    A Java class used to keep provide logging
 *    functionality to other classes in the plugin node.
 *  
 * 
 */

package com.ibm.mq.supportpacs.XMLValidatorNode;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.Date;

public class XMLValidatorLog {
	
	private String logFileName = "";
	private static String className = "XMLValidatorLog";
	private String nodeVersion = "1.0";

	//Flag to determine whether logging should occur
	private boolean enableLogging = true;
	//The default PrintStream, reassigned if a logFileName is set.
	private PrintStream logPrintStream = System.out;
	private int indentSize = 0;
	//A constant to determine the starting position of log text.
	private int classNamePadSize = 27;
	
	
	public XMLValidatorLog(String logFn) {
		setLogFileName(logFn);
	}
	
	/*
	 * Log a string, using nodeName as the origin identifier, and text as the
	 * string to be logged.
	 */
	public void log(String nodeName,String text) {
		if (enableLogging) {
			Date d = new Date();
			String indent = new String();
			for (int x=0;x<indentSize;x++){
				indent = indent + "   ";
			}
			//Pad out the 
			while (nodeName.length() < classNamePadSize){
				nodeName = nodeName + " ";
			}
			String logText = d + " " + nodeName + " " + nodeVersion + " " + indent + text;
			logPrintStream.println(logText);
		}
	}
	/*
	 * Used at the start of method entry to put a marker, and start indenting subsequent
	 * logs more.
	 */
	public void logEntry(String nodeName,String text) {
		log(nodeName," >> " + text);
		indentSize++;
	}
	/*
	 * Used at the end of method entry to put a marker, and start indenting subsequent
	 * logs less.
	 */
	public void logExit(String nodeName,String text) {
		indentSize--;
		log(nodeName," << " + text);
		
	}
	
	/**
	 * @return Returns the enableLogging flag.
	 */
	public boolean isEnableLogging() {
		return enableLogging;
	}
	/**
	 * @param enableLogging The enableLogging to set.
	 * 
	 * Sets the enableLogging flag, if true, subsequent log calls
	 * will be output.
	 */
	public void setEnableLogging(boolean enableLogging) {
		log(className,"Setting log enabled to " + enableLogging);
		this.enableLogging = enableLogging;
	}
	/**
	 * @return Returns the logFileName.
	 */
	public String getLogFileName() {
		return logFileName;
	}
	/**
	 * @param logFileName The logFileName to set.
	 * Which will open a new file, or if a blank string ("") is given, output will
	 * be rerouted to System.out
	 */
	public void setLogFileName(String logFileName) {
		if (!logFileName.equals("")) {
			try {
				log(className,"Attempting to change output to \"" + logFileName + "\"");
				logPrintStream = new PrintStream(new FileOutputStream(logFileName,true));
				this.logFileName = logFileName;
			} catch (FileNotFoundException fnfe) {
				log(className, "Could not change log output to \"" + logFileName + "\"");
				fnfe.printStackTrace(logPrintStream);
			}
		} else {
			logPrintStream = System.out;
			this.logFileName = logFileName;
		}
		
	}
}
