import com.ibm.broker.config.appdev.InputTerminal;
import com.ibm.broker.config.appdev.Node;
import com.ibm.broker.config.appdev.NodeProperty;
import com.ibm.broker.config.appdev.OutputTerminal;

/*** 
 * <p>  <I>XMLValidatorNodeUDN</I> instance</p>
 * <p></p>
 */
public class XMLValidatorNodeUDN extends Node {

	private static final long serialVersionUID = 1L;

	// Node constants
	protected final static String NODE_TYPE_NAME = "XMLValidatorNode";
	protected final static String NODE_GRAPHIC_16 = "platform:/plugin/com.ibm.mq.supportpacs.XMLValidator/icons/full/obj16/XMLValidator.gif";
	protected final static String NODE_GRAPHIC_32 = "platform:/plugin/com.ibm.mq.supportpacs.XMLValidator/icons/full/obj30/XMLValidator.gif";

	protected final static String PROPERTY_SCHEMALOCATION = "schemaLocation";
	protected final static String PROPERTY_NONAMESPACESCHEMALOCATION = "noNameSpaceSchemaLocation";
	protected final static String PROPERTY_IGNOREWARNINGS = "ignoreWarnings";
	protected final static String PROPERTY_FULLSCHEMACHECK = "fullSchemaCheck";
	protected final static String PROPERTY_ENABLELOGGING = "enableLogging";
	protected final static String PROPERTY_LOGFILENAME = "logFileName";

	protected NodeProperty[] getNodeProperties() {
		return new NodeProperty[] {
			new NodeProperty(XMLValidatorNodeUDN.PROPERTY_SCHEMALOCATION,		NodeProperty.Usage.OPTIONAL,	false,	NodeProperty.Type.STRING, null,"","",	"XMLValidator",	"com.ibm.mq.supportpacs.XMLValidator"),
			new NodeProperty(XMLValidatorNodeUDN.PROPERTY_NONAMESPACESCHEMALOCATION,		NodeProperty.Usage.OPTIONAL,	false,	NodeProperty.Type.STRING, null,"","",	"XMLValidator",	"com.ibm.mq.supportpacs.XMLValidator"),
			new NodeProperty(XMLValidatorNodeUDN.PROPERTY_IGNOREWARNINGS,		NodeProperty.Usage.OPTIONAL,	false,	NodeProperty.Type.BOOLEAN, "false","","",	"XMLValidator",	"com.ibm.mq.supportpacs.XMLValidator"),
			new NodeProperty(XMLValidatorNodeUDN.PROPERTY_FULLSCHEMACHECK,		NodeProperty.Usage.OPTIONAL,	false,	NodeProperty.Type.BOOLEAN, "false","","",	"XMLValidator",	"com.ibm.mq.supportpacs.XMLValidator"),
			new NodeProperty(XMLValidatorNodeUDN.PROPERTY_ENABLELOGGING,		NodeProperty.Usage.OPTIONAL,	false,	NodeProperty.Type.BOOLEAN, "false","","",	"XMLValidator",	"com.ibm.mq.supportpacs.XMLValidator"),
			new NodeProperty(XMLValidatorNodeUDN.PROPERTY_LOGFILENAME,		NodeProperty.Usage.OPTIONAL,	false,	NodeProperty.Type.STRING, null,"","",	"XMLValidator",	"com.ibm.mq.supportpacs.XMLValidator")
		};
	}

	public XMLValidatorNodeUDN() {
	}

	public final InputTerminal INPUT_TERMINAL_IN = new InputTerminal(this,"InTerminal.In");
	@Override
	public InputTerminal[] getInputTerminals() {
		return new InputTerminal[] {
			INPUT_TERMINAL_IN
	};
	}

	public final OutputTerminal OUTPUT_TERMINAL_INVALID = new OutputTerminal(this,"OutTerminal.Invalid");
	public final OutputTerminal OUTPUT_TERMINAL_OUT = new OutputTerminal(this,"OutTerminal.Out");
	@Override
	public OutputTerminal[] getOutputTerminals() {
		return new OutputTerminal[] {
			OUTPUT_TERMINAL_INVALID,
			OUTPUT_TERMINAL_OUT
		};
	}

	@Override
	public String getTypeName() {
		return NODE_TYPE_NAME;
	}

	protected String getGraphic16() {
		return NODE_GRAPHIC_16;
	}

	protected String getGraphic32() {
		return NODE_GRAPHIC_32;
	}

	/**
	 * Set the <I>XMLValidatorNodeUDN</I> "<I>xsi:schemaLocation</I>" property
	 * 
	 * @param value String ; the value to set the property "<I>xsi:schemaLocation</I>"
	 */
	public XMLValidatorNodeUDN setSchemaLocation(String value) {
		setProperty(XMLValidatorNodeUDN.PROPERTY_SCHEMALOCATION, value);
		return this;
	}

	/**
	 * Get the <I>XMLValidatorNodeUDN</I> "<I>xsi:schemaLocation</I>" property
	 * 
	 * @return String; the value of the property "<I>xsi:schemaLocation</I>"
	 */
	public String getSchemaLocation() {
		return (String)getPropertyValue(XMLValidatorNodeUDN.PROPERTY_SCHEMALOCATION);
	}

	/**
	 * Set the <I>XMLValidatorNodeUDN</I> "<I>xsi:noNamespaceSchemaLocation</I>" property
	 * 
	 * @param value String ; the value to set the property "<I>xsi:noNamespaceSchemaLocation</I>"
	 */
	public XMLValidatorNodeUDN setNoNameSpaceSchemaLocation(String value) {
		setProperty(XMLValidatorNodeUDN.PROPERTY_NONAMESPACESCHEMALOCATION, value);
		return this;
	}

	/**
	 * Get the <I>XMLValidatorNodeUDN</I> "<I>xsi:noNamespaceSchemaLocation</I>" property
	 * 
	 * @return String; the value of the property "<I>xsi:noNamespaceSchemaLocation</I>"
	 */
	public String getNoNameSpaceSchemaLocation() {
		return (String)getPropertyValue(XMLValidatorNodeUDN.PROPERTY_NONAMESPACESCHEMALOCATION);
	}

	/**
	 * Set the <I>XMLValidatorNodeUDN</I> "<I>Ignore Warnings</I>" property
	 * 
	 * @param value boolean ; the value to set the property "<I>Ignore Warnings</I>"
	 */
	public XMLValidatorNodeUDN setIgnoreWarnings(boolean value) {
		setProperty(XMLValidatorNodeUDN.PROPERTY_IGNOREWARNINGS, String.valueOf(value));
		return this;
	}

	/**
	 * Get the <I>XMLValidatorNodeUDN</I> "<I>Ignore Warnings</I>" property
	 * 
	 * @return boolean; the value of the property "<I>Ignore Warnings</I>"
	 */
	public boolean getIgnoreWarnings(){
	if (getPropertyValue(XMLValidatorNodeUDN.PROPERTY_IGNOREWARNINGS).equals("true")){
		return true;
	} else {
		return false;
		}
	}

	/**
	 * Set the <I>XMLValidatorNodeUDN</I> "<I>Full Schema Check</I>" property
	 * 
	 * @param value boolean ; the value to set the property "<I>Full Schema Check</I>"
	 */
	public XMLValidatorNodeUDN setFullSchemaCheck(boolean value) {
		setProperty(XMLValidatorNodeUDN.PROPERTY_FULLSCHEMACHECK, String.valueOf(value));
		return this;
	}

	/**
	 * Get the <I>XMLValidatorNodeUDN</I> "<I>Full Schema Check</I>" property
	 * 
	 * @return boolean; the value of the property "<I>Full Schema Check</I>"
	 */
	public boolean getFullSchemaCheck(){
	if (getPropertyValue(XMLValidatorNodeUDN.PROPERTY_FULLSCHEMACHECK).equals("true")){
		return true;
	} else {
		return false;
		}
	}

	/**
	 * Set the <I>XMLValidatorNodeUDN</I> "<I>Enable Logging</I>" property
	 * 
	 * @param value boolean ; the value to set the property "<I>Enable Logging</I>"
	 */
	public XMLValidatorNodeUDN setEnableLogging(boolean value) {
		setProperty(XMLValidatorNodeUDN.PROPERTY_ENABLELOGGING, String.valueOf(value));
		return this;
	}

	/**
	 * Get the <I>XMLValidatorNodeUDN</I> "<I>Enable Logging</I>" property
	 * 
	 * @return boolean; the value of the property "<I>Enable Logging</I>"
	 */
	public boolean getEnableLogging(){
	if (getPropertyValue(XMLValidatorNodeUDN.PROPERTY_ENABLELOGGING).equals("true")){
		return true;
	} else {
		return false;
		}
	}

	/**
	 * Set the <I>XMLValidatorNodeUDN</I> "<I>Log Filename</I>" property
	 * 
	 * @param value String ; the value to set the property "<I>Log Filename</I>"
	 */
	public XMLValidatorNodeUDN setLogFileName(String value) {
		setProperty(XMLValidatorNodeUDN.PROPERTY_LOGFILENAME, value);
		return this;
	}

	/**
	 * Get the <I>XMLValidatorNodeUDN</I> "<I>Log Filename</I>" property
	 * 
	 * @return String; the value of the property "<I>Log Filename</I>"
	 */
	public String getLogFileName() {
		return (String)getPropertyValue(XMLValidatorNodeUDN.PROPERTY_LOGFILENAME);
	}

	public String getNodeName() {
		String retVal = super.getNodeName();
		if ((retVal==null) || retVal.equals(""))
			retVal = "XML Validator";
		return retVal;
	};
}
