package io.elastic.jdee1;

import com.jdedwards.system.xml.XMLRequest;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import javax.json.JsonObject;
import javax.swing.table.DefaultTableModel;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Utils {

  private static final Logger logger = LoggerFactory.getLogger(Utils.class);

  public DefaultTableModel BSFNParmsModel;
  private Document XMLDoc;
  private Document XMLResponseDoc;
  private Document XMLServer;

  private static final String CFG_USER = "user";
  private static final String CFG_PASSWORD = "password";
  private static final String CFG_ENV = "environment";
  private static final String CFG_FUNCTION = "function";
  private static final String CFG_SERVER = "server";
  private static final String CFG_PORT = "port";

  public String session = "";
  public Boolean executed = false;
  public String lastFunction = "";
  public String returnCode = "";
  public String errors = "";

  private String getSessionIDFromXMLDocument(Document doc) {
    String ret = null;

    try {
      NodeList returncodelist = doc.getElementsByTagName("jdeResponse");
      Node returncode = returncodelist.item(0);
      NamedNodeMap attributes = returncode.getAttributes();
      Node att = attributes.getNamedItem("session");
      ret = att.getNodeValue();
    } catch (Exception var7) {
      ret = "";
    }

    return ret;
  }

  public String getTemplate_actionPerformed(final JsonObject config) {
    executed = false;
    String response = "";
    final String function = getRequiredNonEmptyString(config, CFG_FUNCTION, "Function is required");
    if (function.compareTo("") != 0) {
      if (function.compareTo(lastFunction) == 0) {
        executed = true;
      } else {
        lastFunction = "";
        clearBSFNModel();
        errors = "";
        Node node = null;

        try {
          node = createTemplateRequestXMLDocument(config);
        } catch (ParserConfigurationException var12) {
          errors = "Failed to create XML document for get template.\n" + var12.toString();
          return errors;
        }

        try {
          response = executeXMLRequest(config, node);
        } catch (Exception var11) {
          errors = "Failed to Execute XML request for get template.\n" + var11.toString();
          return errors;
        }

        try {
          XMLDoc = convertStringToXMLDocument(response);
        } catch (Exception var10) {
          errors = "Failed to convert response to XML document.\n" + var10.toString();
          return errors;
        }

        String ret = getReturnCodeFromXMLDocument(XMLDoc);
        if (ret.compareTo("99") == 0) {
          errors = "Failed to retrieve template\n";
          showTemplateErrorMessage(XMLDoc);
        } else {
          NodeList parms = XMLDoc.getElementsByTagName("param");

          for (int i = 0; i < parms.getLength(); ++i) {
            NamedNodeMap attributes = parms.item(i).getAttributes();
            Node x = attributes.getNamedItem("name");
            String[] row = new String[]{x.getNodeValue(), "", ""};
            BSFNParmsModel.addRow(row);
          }

          executed = true;
          lastFunction = function;
        }
      }
    }
    return response;
  }

  private String executeXMLRequest(final JsonObject config, Node node)
      throws UnsupportedEncodingException, IOException {
    String request = convertXMLDocumentToString(node);
    final String server = getRequiredNonEmptyString(config, CFG_SERVER, "Server is required");
    final String port = getRequiredNonEmptyString(config, CFG_PORT, "Port is required");
    logger.info("Request: ", request);
    XMLRequest xml = new XMLRequest(server, Integer.parseInt(port), request);
    String response = xml.execute();
    logger.info("Response: ", response);
    return response;
  }

  private Node createTemplateRequestXMLDocument(final JsonObject config)
      throws ParserConfigurationException {
    Node node = null;
    final String user = getRequiredNonEmptyString(config, CFG_USER, "User is required");
    final String password = getRequiredNonEmptyString(config, CFG_PASSWORD, "Password is required");
    final String environment = getRequiredNonEmptyString(config, CFG_ENV,
        "Environment is required");
    final String function = getRequiredNonEmptyString(config, CFG_FUNCTION, "Function is required");
    DocumentBuilder Builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
    Document Doc = Builder.newDocument();
    Element element = Doc.createElement("jdeRequest");
    element.setAttribute("type", "callmethod");
    element.setAttribute("user", user);
    element.setAttribute("pwd", password);
    element.setAttribute("environment", environment);
    element.setAttribute("session", session);
    Doc.appendChild(element);
    Element element2 = Doc.createElement("callMethodTemplate");
    element2.setAttribute("app", "");
    element2.setAttribute("name", function);
    element.appendChild(element2);
    return Doc;
  }

  private String getReturnCodeFromXMLDocument(Document doc) {
    String ret = null;

    try {
      NodeList returncodelist = doc.getElementsByTagName("returnCode");
      Node returncode = returncodelist.item(0);
      NamedNodeMap attributes = returncode.getAttributes();
      Node att = attributes.getNamedItem("code");
      ret = att.getNodeValue();
    } catch (Exception var7) {
      ret = "";
    }

    return ret;
  }

  private Document convertStringToXMLDocument(String XML)
      throws ParserConfigurationException, SAXException, IOException {
    Document doc = null;
    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
    DocumentBuilder db = dbf.newDocumentBuilder();
    InputSource is = new InputSource();
    is.setCharacterStream(new StringReader(XML));
    doc = db.parse(is);
    return doc;
  }

  private static String getRequiredNonEmptyString(final JsonObject config, final String key,
      final String message) {
    final String value = config.getString(key);
    if (value == null || value.equals("")) {
      throw new RuntimeException(message);
    }
    return value;
  }

  private String convertXMLDocumentToString(Node node) {
    try {
      Source source = new DOMSource(node);
      StringWriter stringWriter = new StringWriter();
      Result result = new StreamResult(stringWriter);
      TransformerFactory factory = TransformerFactory.newInstance();
      Transformer transformer = factory.newTransformer();
      transformer.transform(source, result);
      return stringWriter.getBuffer().toString();
    } catch (Exception var7) {
      return var7.toString();
    }
  }

  private void clearBSFNModel() {
    int j = BSFNParmsModel.getRowCount();

    for (int i = 0; i < j; ++i) {
      BSFNParmsModel.removeRow(0);
    }
  }

  private void showTemplateErrorMessage(Document doc) {
    String ret = null;

    try {
      NodeList returncodelist = doc.getElementsByTagName("returnCode");
      Node returncode = returncodelist.item(0);
      ret = returncode.getFirstChild().getNodeValue();
    } catch (Exception var5) {
      ret = "";
    }

    errors = ret;
  }

  public void jbExecute_actionPerformed(JsonObject config) {
    errors = "";

    String ret;
    for(int i = 0; i < BSFNParmsModel.getRowCount(); ++i) {
      ret = (String)BSFNParmsModel.getValueAt(i, 0);
      String value = (String)BSFNParmsModel.getValueAt(i, 1);
      if (value.compareTo("") != 0) {
        setParameterValue(value, i);
      }
    }

    setCredentialsInXMLDocument(config);
    String response = null;

    try {
      response = executeXMLRequest(config, XMLDoc);
    } catch (IOException var13) {
      errors = "Failed to Execute XMLRequest for function call.\n" + var13.toString();
      return;
    }

    try {
      XMLResponseDoc = convertStringToXMLDocument(response);
    } catch (Exception var12) {
      errors = "Failed to Execute XMLRequest for function call.\n" + var12.toString();
      var12.printStackTrace();
      return;
    }

    ret = getReturnCodeFromXMLDocument(XMLResponseDoc);
    returnCode = ret;

    session = getSessionIDFromXMLDocument(XMLResponseDoc);

    NodeList parms = XMLResponseDoc.getElementsByTagName("param");

    for(int i = 0; i < parms.getLength(); ++i) {
      Node node = parms.item(i);
      NamedNodeMap attributes = node.getAttributes();
      Node x = attributes.getNamedItem("name");
      String parmname = x.getNodeValue();
      Node textNode = null;
      if ((textNode = node.getFirstChild()) != null) {
        int index = getParameterIndexByName(parmname);
        if (index != -1) {
          BSFNParmsModel.setValueAt(textNode.getNodeValue(), index, 2);
        }
      }
    }

    displayBSFNErrors(XMLResponseDoc);
  }

  public void setParameterValue(String value, int index) {
    NodeList parms = XMLDoc.getElementsByTagName("param");
    Node node = parms.item(index);
    Node textNode = null;
    if ((textNode = node.getFirstChild()) == null) {
      textNode = XMLDoc.createTextNode(value);
      node.appendChild((Node)textNode);
    }

    if (textNode != null) {
      ((Node)textNode).setNodeValue(value);
    }

  }

  private void setCredentialsInXMLDocument(JsonObject config) {
    NodeList requestlist = XMLDoc.getElementsByTagName("jdeRequest");
    Node request = requestlist.item(0);
    final String user = getRequiredNonEmptyString(config, CFG_USER, "User is required");
    final String password = getRequiredNonEmptyString(config, CFG_PASSWORD, "Password is required");
    final String environment = getRequiredNonEmptyString(config, CFG_ENV,
        "Environment is required");

    try {
      NamedNodeMap attributes = request.getAttributes();
      Node att = attributes.getNamedItem("user");
      att.setNodeValue(user);
      att = attributes.getNamedItem("pwd");
      att.setNodeValue(password);
      att = attributes.getNamedItem("environment");
      att.setNodeValue(environment);
      att = attributes.getNamedItem("session");
      att.setNodeValue(session);
    } catch (Exception var6) {
      var6.printStackTrace();
    }

  }

  private int getParameterIndexByName(String parmname) {
    for(int i = 0; i < this.BSFNParmsModel.getRowCount(); ++i) {
      String parm = null;
      parm = (String)this.BSFNParmsModel.getValueAt(i, 0);
      if (parm.compareTo(parmname) == 0) {
        return i;
      }
    }

    return -1;
  }

  private void displayBSFNErrors(Document doc) {
    String codestring = "";
    String message = "";

    try {
      NodeList errorlist = doc.getElementsByTagName("error");

      for(int i = 0; i < errorlist.getLength(); ++i) {
        Node error = errorlist.item(i);
        NamedNodeMap attributes = error.getAttributes();
        Node code = attributes.getNamedItem("code");
        if (code != null) {
          codestring = code.getNodeValue();
        }

        message = error.getFirstChild().getNodeValue();
        errors += codestring + " - " + message + "\n";
      }
    } catch (Exception var9) {
      errors += "Failed to get error";
    }

  }

}