package io.elastic.jdee1;

import com.jdedwards.system.xml.XMLRequest;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.text.MessageFormat;
import java.util.Map;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonValue;
import javax.swing.table.DefaultTableModel;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class Utils {

  private static final Logger logger = LoggerFactory.getLogger(Utils.class);

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

  String[] columnNames = new String[]{"Parameter", "Input Value", "Output Value"};
  Object[][] Parameters = new Object[][]{{"", "", ""}};
  DefaultTableModel BSFNParmsModel = new DefaultTableModel(Parameters, columnNames);

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

  public JsonObject getTemplate_actionPerformed(final JsonObject config) {
    JsonObjectBuilder properties = Json.createObjectBuilder();
    JsonObjectBuilder field = Json.createObjectBuilder();
    executed = false;
    String response = "";
    final String function = "AddressBookMasterMBF"; //getRequiredNonEmptyString(config, CFG_FUNCTION, "Function is required");
    logger.info("lastFunction: {}, function: {}", lastFunction, function);
    if (function.compareTo("") != 0) {
      if (function.compareTo(lastFunction) == 0) {
        executed = true;
      } else {
        lastFunction = "";
        clearBSFNModel();
        errors = "";
        Node node = null;
        logger.info("config: {}", config.toString());
        try {
          node = createTemplateRequestXMLDocument(config);
          logger.info("node1: {}", convertXMLDocumentToString(node));
        } catch (ParserConfigurationException var12) {
          errors = "Failed to create XML document for get template.\n" + var12.toString();
          logger.info("Error: {}", errors);
          properties.add("error", errors);
          return properties.build();
        }
        logger.info("node2: {}", convertXMLDocumentToString(node));
        try {
          response = executeXMLRequest(config, node);
        } catch (Exception var11) {
          errors = "Failed to Execute XML request for get template.\n" + var11.toString();
          logger.info("Error: {}", errors);
          properties.add("error", errors);
          return properties.build();
        }
        logger.info("response: {}", response);
        try {
          XMLDoc = convertStringToXMLDocument(response);
        } catch (Exception var10) {
          errors = "Failed to convert response to XML document.\n" + var10.toString();
          logger.info("Error: {}", errors);
          properties.add("error", errors);
          return properties.build();
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

            String name = x.getNodeValue();
            String type = "string";
            field.add("title", name)
                .add("type", type);
            properties.add(name, field);

            BSFNParmsModel.addRow(row);
          }

          executed = true;
          lastFunction = function;
        }
      }
    }
    return properties.build();
  }

  private String executeXMLRequest(final JsonObject config, Node node)
      throws UnsupportedEncodingException, IOException {
    String request = convertXMLDocumentToString(node);
    final String server = getRequiredNonEmptyString(config, CFG_SERVER, "Server is required");
    final String port = getRequiredNonEmptyString(config, CFG_PORT, "Port is required");
    XMLRequest xml = new XMLRequest(server, Integer.parseInt(port), request);
    //String response = createTemplateResponseXMLDocument(config);
    String response = xml.execute();
    return response;
  }

  private String createTemplateResponseXMLDocument(final JsonObject config) {
    Node node = null;
    final String user = getRequiredNonEmptyString(config, CFG_USER, "User is required");
    final String password = getRequiredNonEmptyString(config, CFG_PASSWORD, "Password is required");
    final String environment = getRequiredNonEmptyString(config, CFG_ENV,
        "Environment is required");
    final String function = getRequiredNonEmptyString(config, CFG_FUNCTION, "Function is required");
    return MessageFormat.format("<?xml version=\"1.0\" encoding=\"UTF-8\" ?><jdeResponse comment=\"\" environment=\"{0}\" pwd=\"{1}\" role=\"*ALL\" session=\"3916.1534421300.6\" sessionidle=\"\" type=\"callmethod\" user=\"{2}\">\n"
        + "\t<callMethod app=\"\" name=\"{3}\">\n"
        + "\t\t<returnCode code=\"0\"/>\n"
        + "\t\t<params>\n"
        + "\t\t\t<param name=\"cActionCode\">I</param>\n"
        + "\t\t\t<param name=\"mnAddressBookNumber\">202930</param>\n"
        + "\t\t\t<param name=\"szLongAddressNumber\">0011I00000UH3pUQAT</param>\n"
        + "\t\t\t<param name=\"szTaxId\">SERVICEMAX</param>\n"
        + "\t\t\t<param name=\"szSearchType\">C</param>\n"
        + "\t\t\t<param name=\"szAlphaName\">Compugen</param>\n"
        + "\t\t\t<param name=\"szSecondaryAlphaName\">                                        </param>\n"
        + "\t\t\t<param name=\"szMailingName\">Compugen</param>\n"
        + "\t\t\t<param name=\"szSecondaryMailingName\">                                        </param>\n"
        + "\t\t\t<param name=\"szDescriptionCompressed\">COMPUGEN</param>\n"
        + "\t\t\t<param name=\"szBusinessUnit\">         M30</param>\n"
        + "\t\t\t<param name=\"szAddressLine1\">PO Box 2121 Compugen</param>\n"
        + "\t\t\t<param name=\"szAddressLine2\">2018-06-13T14:33:30.000Z</param>\n"
        + "\t\t\t<param name=\"szAddressLine3\">www.test.com</param>\n"
        + "\t\t\t<param name=\"szAddressLine4\">Banking</param>\n"
        + "\t\t\t<param name=\"szPostalCode\">10008</param>\n"
        + "\t\t\t<param name=\"szCity\">New York</param>\n"
        + "\t\t\t<param name=\"szCounty\">                         </param>\n"
        + "\t\t\t<param name=\"szState\">NE</param>\n"
        + "\t\t\t<param name=\"szCountry\">   </param>\n"
        + "\t\t\t<param name=\"szPrefix1\">(212)</param>\n"
        + "\t\t\t<param name=\"szPhoneNumber1\">247-1672</param>\n"
        + "\t\t\t<param name=\"szPhoneNumberType1\">    </param>\n"
        + "\t\t\t<param name=\"cPayablesYNM\">N</param>\n"
        + "\t\t\t<param name=\"cReceivablesYN\">N</param>\n"
        + "\t\t\t<param name=\"cEmployeeYN\">N</param>\n"
        + "\t\t\t<param name=\"cUserCode\">N</param>\n"
        + "\t\t\t<param name=\"cARAPNettingY\">N</param>\n"
        + "\t\t\t<param name=\"cSubledgerInactiveCode\"> </param>\n"
        + "\t\t\t<param name=\"cPersonCorporationCode\"> </param>\n"
        + "\t\t\t<param name=\"szCertificate\">                    </param>\n"
        + "\t\t\t<param name=\"szAddlIndTaxID\">                    </param>\n"
        + "\t\t\t<param name=\"szCreditMessage\">  </param>\n"
        + "\t\t\t<param name=\"szLanguage\">  </param>\n"
        + "\t\t\t<param name=\"szIndustryClassification\">7000</param>\n"
        + "\t\t\t<param name=\"cEMail\"> </param>\n"
        + "\t\t\t<param name=\"mn1stAddressNumber\">202930</param>\n"
        + "\t\t\t<param name=\"mn2ndAddressNumber\">202930</param>\n"
        + "\t\t\t<param name=\"mn3rdAddressNumber\">202930</param>\n"
        + "\t\t\t<param name=\"mn4thAddressNumber\">202930</param>\n"
        + "\t\t\t<param name=\"mn5thAddressNumber\">202930</param>\n"
        + "\t\t\t<param name=\"mnFactorSpecialPayee\">202930</param>\n"
        + "\t\t\t<param name=\"cAddressType3YN\">N</param>\n"
        + "\t\t\t<param name=\"cAddressType4YN\">N</param>\n"
        + "\t\t\t<param name=\"cAddressType5YN\">N</param>\n"
        + "\t\t\t<param name=\"szCategoryCode01\">   </param>\n"
        + "\t\t\t<param name=\"szAccountRepresentative\">   </param>\n"
        + "\t\t\t<param name=\"szCategoryCode03\">   </param>\n"
        + "\t\t\t<param name=\"szGeographicRegion\">   </param>\n"
        + "\t\t\t<param name=\"szCategoryCode05\">   </param>\n"
        + "\t\t\t<param name=\"szCategoryCode06\">   </param>\n"
        + "\t\t\t<param name=\"sz1099Reporting\">   </param>\n"
        + "\t\t\t<param name=\"szCategoryCode08\">   </param>\n"
        + "\t\t\t<param name=\"szCategoryCode09\">   </param>\n"
        + "\t\t\t<param name=\"szCategoryCode10\">   </param>\n"
        + "\t\t\t<param name=\"szSalesRegion\">   </param>\n"
        + "\t\t\t<param name=\"szCategoryCode12\">   </param>\n"
        + "\t\t\t<param name=\"szLineOfBusiness\">   </param>\n"
        + "\t\t\t<param name=\"szSalesVolume\">   </param>\n"
        + "\t\t\t<param name=\"szCategoryCode15\">   </param>\n"
        + "\t\t\t<param name=\"szCategoryCode16\">   </param>\n"
        + "\t\t\t<param name=\"szCategoryCode17\">   </param>\n"
        + "\t\t\t<param name=\"szCategoryCode18\">   </param>\n"
        + "\t\t\t<param name=\"szCategoryCode19\">   </param>\n"
        + "\t\t\t<param name=\"szCategoryCode20\">   </param>\n"
        + "\t\t\t<param name=\"szCategoryCode21\">   </param>\n"
        + "\t\t\t<param name=\"szCategoryCode22\">   </param>\n"
        + "\t\t\t<param name=\"szCategoryCode23\">   </param>\n"
        + "\t\t\t<param name=\"szCategoryCode24\">   </param>\n"
        + "\t\t\t<param name=\"szCategoryCode25\">   </param>\n"
        + "\t\t\t<param name=\"szCategoryCode26\">   </param>\n"
        + "\t\t\t<param name=\"szCategoryCode27\">   </param>\n"
        + "\t\t\t<param name=\"szCategoryCode28\">   </param>\n"
        + "\t\t\t<param name=\"szCategoryCode29\">   </param>\n"
        + "\t\t\t<param name=\"szCategoryCode30\">   </param>\n"
        + "\t\t\t<param name=\"szGlBankAccount\">        </param>\n"
        + "\t\t\t<param name=\"cClearedY\"> </param>\n"
        + "\t\t\t<param name=\"szRemark\">                              </param>\n"
        + "\t\t\t<param name=\"szUserReservedCode\">  </param>\n"
        + "\t\t\t<param name=\"mnUserReservedAmount\">.00</param>\n"
        + "\t\t\t<param name=\"szUserReservedReference\">0011I00000UH3pU</param>\n"
        + "\t\t\t<param name=\"szProgramId\">          </param>\n"
        + "\t\t\t<param name=\"szRemark1\">                                        </param>\n"
        + "\t\t\t<param name=\"cEdiSuccessfullyProcess\">0</param>\n"
        + "\t\t\t<param name=\"szShortcutClientType\">  </param>\n"
        + "\t\t\t<param name=\"szTicker\">          </param>\n"
        + "\t\t\t<param name=\"szStockExchange\">          </param>\n"
        + "\t\t\t<param name=\"szDUNSNumber\">             </param>\n"
        + "\t\t\t<param name=\"szClassificationCode01\">   </param>\n"
        + "\t\t\t<param name=\"szClassificationCode02\">   </param>\n"
        + "\t\t\t<param name=\"szClassificationCode03\">   </param>\n"
        + "\t\t\t<param name=\"szClassificationCode04\">   </param>\n"
        + "\t\t\t<param name=\"szClassificationCode05\">   </param>\n"
        + "\t\t\t<param name=\"szYearStarted\">               </param>\n"
        + "\t\t\t<param name=\"szEmployeeGroupApprovals\">     </param>\n"
        + "\t\t\t<param name=\"cIndicatorFlg\"> </param>\n"
        + "\t\t\t<param name=\"szRevenueRange\">C</param>\n"
        + "\t\t</params>\n"
        + "\t</callMethod>\n"
        + "</jdeResponse>", environment, password, user, function);
  }

  private Node createTemplateRequestXMLDocument(final JsonObject config)
      throws ParserConfigurationException {
    Node node = null;
    final String user = getRequiredNonEmptyString(config, CFG_USER, "User is required");
    final String password = getRequiredNonEmptyString(config, CFG_PASSWORD, "Password is required");
    final String environment = getRequiredNonEmptyString(config, CFG_ENV,
        "Environment is required");
    final String function = "AddressBookMasterMBF";//getRequiredNonEmptyString(config, CFG_FUNCTION, "Function is required");
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

  public JsonObject jbExecute_actionPerformed(JsonObject config, JsonObject body)
      throws ParserConfigurationException, IOException, SAXException {
    errors = "";

    String ret;
    JsonObjectBuilder properties = Json.createObjectBuilder();
    JsonObjectBuilder field = Json.createObjectBuilder();

    getTemplate_actionPerformed(config);
/*
    for(int i = 0; i < BSFNParmsModel.getRowCount(); ++i) {
      ret = (String)BSFNParmsModel.getValueAt(i, 0);
      String value = (String)BSFNParmsModel.getValueAt(i, 1);
      if (value.compareTo("") != 0) {
        setParameterValue(value, i);
      }
    }
*/

    StringBuilder keys = new StringBuilder();
    StringBuilder values = new StringBuilder();
    StringBuilder setString = new StringBuilder();
    int indexParam = 0;

    for (Map.Entry<String, JsonValue> entry : body.entrySet()) {
      if (entry.getValue().toString().compareTo("") != 0) {
        setParameterValue(entry.getKey().toString(), entry.getValue().toString());
      }
    }

    logger.info("jbExecute Config: {}", config.toString());
    setCredentialsInXMLDocument(config);
    String response = null;
    logger.info("jbExecute Request: {}", documentToString(XMLDoc));
    try {
      response = executeXMLRequest(config, XMLDoc);
    } catch (IOException var13) {
      errors = "Failed to Execute XMLRequest for function call.\n" + var13.toString();
      properties.add("error", errors);
      return properties.build();
    }
    logger.info("jbExecute Response: {}", documentToString(XMLResponseDoc));
    try {
      XMLResponseDoc = convertStringToXMLDocument(response);
    } catch (Exception var12) {
      errors = "Failed to Execute XMLRequest for function call.\n" + var12.toString();
      var12.printStackTrace();
      properties.add("error", errors);
      return properties.build();
    }

    ret = getReturnCodeFromXMLDocument(XMLResponseDoc);
    returnCode = ret;

    session = getSessionIDFromXMLDocument(XMLResponseDoc);

    NodeList parms = XMLResponseDoc.getElementsByTagName("param");
    Node node = null;
    for(int i = 0; i < parms.getLength(); ++i) {
      node = parms.item(i);
      NamedNodeMap attributes = node.getAttributes();
      Node x = attributes.getNamedItem("name");
      String parmname = x.getNodeValue();
      Node textNode = null;
      if ((textNode = node.getFirstChild()) != null) {
        int index = getParameterIndexByName(parmname);
        if (index != -1) {
          BSFNParmsModel.setValueAt(textNode.getNodeValue(), index, 2);

          String name = x.getNodeValue();
          String type = "string";
          field.add("title", name)
              .add("type", type);
          properties.add(name, field);

        }
      }
    }

    XMLResponseDoc.getElementsByTagName("jdeResponse");

    displayBSFNErrors(XMLResponseDoc);

    return properties.build();
  }

  public void setParameterValue(String key, String value) {
    NodeList parms = XMLDoc.getElementsByTagName("param");
    logger.info("XMLDoc: {}", convertXMLDocumentToString(XMLDoc));
    for (int i = 0, len = parms.getLength(); i < len; i++) {
      Element elm = (Element)parms.item(i);
      logger.info("elm.getAttribute(\"name\"): {}", elm.getAttribute("name"));
      if (elm.getAttribute("name").contains(key)) {
        logger.info("contains: true");
        Node node = parms.item(i);
        Node textNode = null;
        if ((textNode = node.getFirstChild()) == null) {
          textNode = XMLDoc.createTextNode(value);
          node.appendChild((Node)textNode);
        }

        if (textNode != null) {
          ((Node)textNode).setNodeValue(value);
        }
      }
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

  public static String documentToString(Document doc) {
    try {
      StringWriter sw = new StringWriter();
      TransformerFactory tf = TransformerFactory.newInstance();
      Transformer transformer = tf.newTransformer();
      transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
      transformer.setOutputProperty(OutputKeys.METHOD, "xml");
      transformer.setOutputProperty(OutputKeys.INDENT, "yes");
      transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");

      transformer.transform(new DOMSource(doc), new StreamResult(sw));
      return sw.toString();
    } catch (Exception ex) {
      throw new RuntimeException("Error converting to String", ex);
    }
  }

  public void addAttribute(Document doc, String name, String value) {
    Element root = doc.getDocumentElement();
    Element person = (Element)root.getFirstChild();
    person.setAttribute(name,value);
  }

}