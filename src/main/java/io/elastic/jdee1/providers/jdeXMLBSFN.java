//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

import com.jdedwards.system.xml.XMLRequest;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import javax.swing.ActionMap;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
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

public class jdeXMLBSFN extends JFrame {
  private static final long serialVersionUID = 1L;
  private String[] columnNames = new String[]{"Parameter", "Input Value", "Output Value"};
  private Object[][] Parameters = new Object[][]{{"", "", ""}};
  private DefaultTableModel BSFNParmsModel;
  private Document XMLDoc;
  private Document XMLResponseDoc;
  private Document XMLServer;
  private JTextField jtfFunction;
  private JTextArea jtaRequestDocument;
  private JTextArea jtaResponseDocument;
  private JTextArea jtaErrors;
  private JButton jbGetTemplate;
  private JTextField jtfServer;
  private JTextField jtfPort;
  private JLabel jLabel1;
  private JLabel jLabel2;
  private JTextField jtfUser;
  private JTextField jtfEnvironment;
  private JLabel jLabel3;
  private JLabel jLabel4;
  private JLabel jLabel5;
  private JTable jtblParameters;
  private JButton jbExecute;
  private JTextField jtfSessionID;
  private JTextField jtfReturnCode;
  private JLabel jLabel6;
  private JTabbedPane jtpXML;
  private JPasswordField jtfPassword;
  private JButton jbClearSession;
  private JButton jbSaveServer;
  private JScrollPane XMLReqPane;
  private JScrollPane XMLResPane;
  private JScrollPane ErrorPane;
  private JScrollPane GridPane;
  private JPanel jServerPanel;
  private JPanel jUserPanel;
  private JPanel jFunctionPanel;
  private JLabel jLabel7;
  private String LastFunction;
  Cursor hourglassCursor;
  Cursor defaultCursor;

  public jdeXMLBSFN() {
    this.BSFNParmsModel = new DefaultTableModel(this.Parameters, this.columnNames);
    this.XMLDoc = null;
    this.XMLResponseDoc = null;
    this.XMLServer = null;
    this.jtfFunction = new JTextField();
    this.jtaRequestDocument = new JTextArea();
    this.jtaResponseDocument = new JTextArea();
    this.jtaErrors = new JTextArea();
    this.jbGetTemplate = new JButton();
    this.jtfServer = new JTextField();
    this.jtfPort = new JTextField();
    this.jLabel1 = new JLabel();
    this.jLabel2 = new JLabel();
    this.jtfUser = new JTextField();
    this.jtfEnvironment = new JTextField();
    this.jLabel3 = new JLabel();
    this.jLabel4 = new JLabel();
    this.jLabel5 = new JLabel();
    this.jtblParameters = new JTable(this.BSFNParmsModel);
    this.jbExecute = new JButton();
    this.jtfSessionID = new JTextField();
    this.jtfReturnCode = new JTextField();
    this.jLabel6 = new JLabel();
    this.jtpXML = new JTabbedPane();
    this.jtfPassword = new JPasswordField();
    this.jbClearSession = new JButton();
    this.jbSaveServer = new JButton();
    this.XMLReqPane = new JScrollPane(this.jtaRequestDocument);
    this.XMLResPane = new JScrollPane(this.jtaResponseDocument);
    this.ErrorPane = new JScrollPane(this.jtaErrors);
    this.GridPane = new JScrollPane(this.jtblParameters);
    this.jServerPanel = new JPanel();
    this.jUserPanel = new JPanel();
    this.jFunctionPanel = new JPanel();
    this.jLabel7 = new JLabel();
    this.hourglassCursor = new Cursor(3);
    this.defaultCursor = new Cursor(0);

    try {
      this.jbInit();
    } catch (Exception var2) {
      var2.printStackTrace();
    }

  }

  private void jbInit() throws Exception {
    this.getContentPane().setLayout((LayoutManager)null);
    this.setSize(new Dimension(617, 565));
    this.setTitle("JDEXML BSFN");
    this.jtfFunction.setBounds(new Rectangle(20, 20, 250, 20));
    this.XMLReqPane.setBounds(new Rectangle(5, 340, 510, 120));
    this.XMLResPane.setBounds(new Rectangle(5, 340, 510, 120));
    this.jtpXML.add(this.GridPane, "Parameters");
    this.jtpXML.add(this.ErrorPane, "Errors");
    this.jtpXML.add(this.XMLReqPane, "XML Request");
    this.jtpXML.add(this.XMLResPane, "XML Response");
    this.jtpXML.setBounds(new Rectangle(0, 195, 610, 335));
    this.jtaRequestDocument.setBorder(BorderFactory.createBevelBorder(1));
    this.jtaResponseDocument.setBorder(BorderFactory.createBevelBorder(1));
    this.jtaRequestDocument.setEditable(false);
    this.jtaResponseDocument.setEditable(false);
    this.jbGetTemplate.setText("Get Template");
    this.jbGetTemplate.setBounds(new Rectangle(410, 20, 30, 20));
    this.jbGetTemplate.setActionMap(new ActionMap());
    this.jbGetTemplate.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jdeXMLBSFN.this.jbGetTemplate_actionPerformed(e);
      }
    });
    this.jbSaveServer.setText("Save");
    this.jbSaveServer.setBounds(new Rectangle(435, 20, 90, 20));
    this.jbSaveServer.setActionMap(new ActionMap());
    this.jbSaveServer.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jdeXMLBSFN.this.jbSaveServer_actionPerformed(e);
      }
    });
    this.jServerPanel.setBounds(new Rectangle(0, 0, 610, 50));
    this.jServerPanel.setBorder(BorderFactory.createTitledBorder("Server"));
    this.jServerPanel.setLayout((LayoutManager)null);
    this.jLabel1.setText("Name");
    this.jLabel1.setBounds(new Rectangle(30, 20, 60, 20));
    this.jLabel2.setText("Port");
    this.jServerPanel.add(this.jLabel1);
    this.jServerPanel.add(this.jtfServer);
    this.jServerPanel.add(this.jLabel2);
    this.jServerPanel.add(this.jtfPort);
    this.jServerPanel.add(this.jbSaveServer);
    this.jLabel2.setToolTipText("null");
    this.jtfServer.setBounds(new Rectangle(85, 20, 85, 20));
    this.jtfPort.setBounds(new Rectangle(295, 20, 60, 20));
    this.jLabel2.setBounds(new Rectangle(225, 20, 40, 20));
    this.jUserPanel.setBounds(new Rectangle(0, 50, 610, 80));
    this.jUserPanel.setBorder(BorderFactory.createTitledBorder("Credentials"));
    this.jUserPanel.setLayout((LayoutManager)null);
    this.jtfUser.setBounds(new Rectangle(85, 25, 85, 20));
    this.jtfEnvironment.setBounds(new Rectangle(520, 25, 70, 20));
    this.jLabel3.setText("Password");
    this.jLabel3.setBounds(new Rectangle(225, 25, 70, 20));
    this.jLabel4.setText("User");
    this.jLabel4.setBounds(new Rectangle(30, 25, 50, 20));
    this.jLabel5.setText("Environment");
    this.jLabel5.setBounds(new Rectangle(435, 25, 75, 20));
    this.jUserPanel.add(this.jLabel4, (Object)null);
    this.jUserPanel.add(this.jtfUser);
    this.jUserPanel.add(this.jLabel3, (Object)null);
    this.jUserPanel.add(this.jtfPassword, (Object)null);
    this.jUserPanel.add(this.jLabel5, (Object)null);
    this.jtfPassword.setBounds(new Rectangle(295, 25, 80, 20));
    this.jUserPanel.add(this.jtfEnvironment);
    this.jUserPanel.add(this.jtfSessionID, (Object)null);
    this.jUserPanel.add(this.jLabel6, (Object)null);
    this.jUserPanel.add(this.jbClearSession, (Object)null);
    this.jLabel6.setText("Session");
    this.jLabel6.setBounds(new Rectangle(30, 50, 60, 20));
    this.jtfSessionID.setBounds(new Rectangle(85, 50, 185, 20));
    this.jtfSessionID.setEditable(false);
    this.jbClearSession.setText("Clear Session");
    this.jbClearSession.setBounds(new Rectangle(295, 50, 120, 20));
    this.jbClearSession.setActionMap(new ActionMap());
    this.jbClearSession.setVisible(true);
    this.jbClearSession.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jdeXMLBSFN.this.jbClearSession_actionPerformed(e);
      }
    });
    this.jFunctionPanel.setBounds(new Rectangle(0, 135, 610, 50));
    this.jFunctionPanel.setLayout((LayoutManager)null);
    this.jFunctionPanel.setBorder(BorderFactory.createTitledBorder("Business Function"));
    this.jLabel7.setText("Return Code");
    this.jLabel7.setBounds(new Rectangle(475, 25, 85, 15));
    this.jtfReturnCode.setBounds(new Rectangle(560, 20, 30, 20));
    this.jtfReturnCode.setEditable(false);
    this.jbExecute.setText("Execute");
    this.jbExecute.setBounds(new Rectangle(280, 20, 80, 20));
    this.jbExecute.setEnabled(false);
    this.jbExecute.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        jdeXMLBSFN.this.jbExecute_actionPerformed(e);
      }
    });
    this.jFunctionPanel.add(this.jLabel7, (Object)null);
    this.jFunctionPanel.add(this.jtfReturnCode, (Object)null);
    this.jFunctionPanel.add(this.jbExecute, (Object)null);
    this.jFunctionPanel.add(this.jbGetTemplate, (Object)null);
    this.jFunctionPanel.add(this.jtfFunction, (Object)null);
    this.jtfFunction.addFocusListener(new FocusAdapter() {
      public void focusLost(FocusEvent e) {
        jdeXMLBSFN.this.jtfFunction_focusLost(e);
      }
    });
    this.getContentPane().add(this.jtpXML, (Object)null);
    this.getContentPane().add(this.jServerPanel, (Object)null);
    this.getContentPane().add(this.jUserPanel, (Object)null);
    this.getContentPane().add(this.jFunctionPanel, (Object)null);
    this.setLocationRelativeTo((Component)null);
    this.setDefaultCloseOperation(3);
    this.setVisible(true);
    this.setResizable(false);
    this.LastFunction = "";
    this.jbGetTemplate.setVisible(false);
    this.LoadServerXMLFile();
  }

  private void jbClearSession_actionPerformed(ActionEvent e) {
    this.jtfSessionID.setText("");
    this.jtfEnvironment.setEditable(true);
  }

  private void jbSaveServer_actionPerformed(ActionEvent e) {
    try {
      DocumentBuilder Builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
      this.XMLServer = Builder.newDocument();
      Element element = this.XMLServer.createElement("Server");
      Element server = this.XMLServer.createElement("Name");
      server.setTextContent(this.jtfServer.getText());
      element.appendChild(server);
      Element port = this.XMLServer.createElement("Port");
      port.setTextContent(this.jtfPort.getText());
      element.appendChild(port);
      this.XMLServer.appendChild(element);
    } catch (ParserConfigurationException var7) {
      var7.printStackTrace();
      return;
    }

    try {
      PrintWriter output = new PrintWriter("jdeXMLBSFN.xml");
      output.print(this.ConvertXMLDocumentToString(this.XMLServer));
      output.close();
    } catch (FileNotFoundException var6) {
      var6.printStackTrace();
    }

  }

  private void LoadServerXMLFile() {
    File file = new File("jdeXMLBSFN.xml");
    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

    try {
      DocumentBuilder db = dbf.newDocumentBuilder();
      this.XMLServer = db.parse(file);
    } catch (Exception var14) {
      return;
    }

    NodeList nodeLst = this.XMLServer.getElementsByTagName("Server");

    for(int s = 0; s < nodeLst.getLength(); ++s) {
      Node fstNode = nodeLst.item(s);
      if (fstNode.getNodeType() == 1) {
        Element fstElmnt = (Element)fstNode;
        NodeList fstNmElmntLst = fstElmnt.getElementsByTagName("Name");
        Element fstNmElmnt = (Element)fstNmElmntLst.item(0);
        NodeList fstNm = fstNmElmnt.getChildNodes();
        this.jtfServer.setText(fstNm.item(0).getNodeValue());
        NodeList lstNmElmntLst = fstElmnt.getElementsByTagName("Port");
        Element lstNmElmnt = (Element)lstNmElmntLst.item(0);
        NodeList lstNm = lstNmElmnt.getChildNodes();
        this.jtfPort.setText(lstNm.item(0).getNodeValue());
      }
    }

  }

  private void jbGetTemplate_actionPerformed(ActionEvent e) {
    this.jbExecute.setEnabled(false);
    if (this.jtfFunction.getText().compareTo("") != 0) {
      if (this.jtfFunction.getText().compareTo(this.LastFunction) == 0) {
        this.jbExecute.setEnabled(true);
      } else {
        this.LastFunction = "";
        this.getGlassPane().addMouseListener(new MouseAdapter() {
        });
        this.getGlassPane().setCursor(this.hourglassCursor);
        this.getGlassPane().setVisible(true);
        this.ClearBSFNModel();
        this.jtaErrors.setText("");
        Node node = null;

        try {
          node = this.CreateTemplateRequestXMLDocument();
        } catch (ParserConfigurationException var12) {
          this.jtaErrors.setText("Failed to create XML document for get template.\n" + var12.toString());
          this.jtpXML.setSelectedIndex(1);
          this.getGlassPane().setVisible(false);
          return;
        }

        String response;
        try {
          response = this.ExecuteXMLRequest(node);
        } catch (Exception var11) {
          this.jtaErrors.setText("Failed to Execute XML request for get template.\n" + var11.toString());
          this.jtpXML.setSelectedIndex(1);
          this.getGlassPane().setVisible(false);
          return;
        }

        try {
          this.XMLDoc = this.ConvertStringToXMLDocument(response);
        } catch (Exception var10) {
          this.jtaErrors.setText("Failed to convert response to XML document.\n" + var10.toString());
          this.jtpXML.setSelectedIndex(1);
          this.getGlassPane().setVisible(false);
          return;
        }

        this.jtfReturnCode.setForeground(Color.BLACK);
        String ret = this.getReturnCodeFromXMLDocument(this.XMLDoc);
        this.jtfReturnCode.setText(ret);
        if (ret.compareTo("99") == 0) {
          this.jtfReturnCode.setForeground(Color.RED);
          this.jtpXML.setSelectedIndex(1);
          this.jtaErrors.setText("Failed to retrieve template\n");
          this.ShowTemplateErrorMessage(this.XMLDoc);
          this.getGlassPane().setVisible(false);
        } else {
          NodeList parms = this.XMLDoc.getElementsByTagName("param");

          for(int i = 0; i < parms.getLength(); ++i) {
            NamedNodeMap attributes = parms.item(i).getAttributes();
            Node x = attributes.getNamedItem("name");
            String[] row = new String[]{x.getNodeValue(), "", ""};
            this.BSFNParmsModel.addRow(row);
          }

          this.jtpXML.setSelectedIndex(0);
          this.jbExecute.setEnabled(true);
          this.LastFunction = this.jtfFunction.getText();
          this.getGlassPane().setVisible(false);
        }
      }
    }
  }

  private String ExecuteXMLRequest(Node node) throws UnsupportedEncodingException, IOException {
    String request = this.ConvertXMLDocumentToString(node);
    this.jtaRequestDocument.setText(request);
    XMLRequest xml = new XMLRequest(this.jtfServer.getText(), Integer.parseInt(this.jtfPort.getText()), request);
    String response = xml.execute();
    this.jtaResponseDocument.setText(response);
    return response;
  }

  private void ClearBSFNModel() {
    int j = this.BSFNParmsModel.getRowCount();

    for(int i = 0; i < j; ++i) {
      this.BSFNParmsModel.removeRow(0);
    }

  }

  private void ShowTemplateErrorMessage(Document doc) {
    String ret = null;

    try {
      NodeList returncodelist = doc.getElementsByTagName("returnCode");
      Node returncode = returncodelist.item(0);
      ret = returncode.getFirstChild().getNodeValue();
    } catch (Exception var5) {
      ret = "";
    }

    this.jtaErrors.append(ret);
  }

  private void DisplayBSFNErrors(Document doc) {
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
        this.jtaErrors.append(codestring + " - " + message + "\n");
      }
    } catch (Exception var9) {
      this.jtaErrors.append("Failed to get error");
    }

  }

  private Node CreateTemplateRequestXMLDocument() throws ParserConfigurationException {
    Node node = null;
    DocumentBuilder Builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
    Document Doc = Builder.newDocument();
    Element element = Doc.createElement("jdeRequest");
    element.setAttribute("type", "callmethod");
    element.setAttribute("user", this.jtfUser.getText());
    element.setAttribute("pwd", String.valueOf(this.jtfPassword.getPassword()));
    element.setAttribute("environment", this.jtfEnvironment.getText());
    element.setAttribute("session", this.jtfSessionID.getText());
    Doc.appendChild(element);
    Element element2 = Doc.createElement("callMethodTemplate");
    element2.setAttribute("app", "");
    element2.setAttribute("name", this.jtfFunction.getText());
    element.appendChild(element2);
    return Doc;
  }

  private String ConvertXMLDocumentToString(Node node) {
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

  private Document ConvertStringToXMLDocument(String XML) throws ParserConfigurationException, SAXException, IOException {
    Document doc = null;
    DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
    DocumentBuilder db = dbf.newDocumentBuilder();
    InputSource is = new InputSource();
    is.setCharacterStream(new StringReader(XML));
    doc = db.parse(is);
    return doc;
  }

  private void jbExecute_actionPerformed(ActionEvent e) {
    this.jtaErrors.setText("");
    this.setCursor(this.hourglassCursor);

    String ret;
    for(int i = 0; i < this.BSFNParmsModel.getRowCount(); ++i) {
      ret = (String)this.BSFNParmsModel.getValueAt(i, 0);
      String value = (String)this.BSFNParmsModel.getValueAt(i, 1);
      if (value.compareTo("") != 0) {
        this.setParameterValue(ret, value, i);
      }
    }

    this.setCredentialsInXMLDocument();
    String response = null;

    try {
      response = this.ExecuteXMLRequest(this.XMLDoc);
    } catch (IOException var13) {
      this.setCursor(this.defaultCursor);
      this.jtaErrors.setText("Failed to Execute XMLRequest for function call.\n" + var13.toString());
      this.jtpXML.setSelectedIndex(1);
      return;
    }

    try {
      this.XMLResponseDoc = this.ConvertStringToXMLDocument(response);
    } catch (Exception var12) {
      this.setCursor(this.defaultCursor);
      this.jtaErrors.setText("Failed to Execute XMLRequest for function call.\n" + var12.toString());
      this.jtpXML.setSelectedIndex(1);
      var12.printStackTrace();
      return;
    }

    ret = this.getReturnCodeFromXMLDocument(this.XMLResponseDoc);
    this.jtfReturnCode.setText(ret);
    this.jtfReturnCode.setForeground(Color.BLACK);
    if (ret.compareTo("1") == 0) {
      this.jtfReturnCode.setForeground(Color.YELLOW);
    }

    if (ret.compareTo("2") == 0) {
      this.jtfReturnCode.setForeground(Color.RED);
    }

    this.jtfSessionID.setText(this.getSessionIDFromXMLDocument(this.XMLResponseDoc));
    if (this.jtfSessionID.getText().compareToIgnoreCase("") > 0) {
      this.jtfEnvironment.setEditable(false);
    } else {
      this.jtfEnvironment.setEditable(true);
    }

    NodeList parms = this.XMLResponseDoc.getElementsByTagName("param");

    for(int i = 0; i < parms.getLength(); ++i) {
      Node node = parms.item(i);
      NamedNodeMap attributes = node.getAttributes();
      Node x = attributes.getNamedItem("name");
      String parmname = x.getNodeValue();
      Node textNode = null;
      if ((textNode = node.getFirstChild()) != null) {
        int index = this.GetParameterIndexByName(parmname);
        if (index != -1) {
          this.BSFNParmsModel.setValueAt(textNode.getNodeValue(), index, 2);
        }
      }
    }

    this.DisplayBSFNErrors(this.XMLResponseDoc);
    this.setCursor(this.defaultCursor);
  }

  private void setParameterValue(String parm, String value, int index) {
    NodeList parms = this.XMLDoc.getElementsByTagName("param");
    Node node = parms.item(index);
    Node textNode = null;
    if ((textNode = node.getFirstChild()) == null) {
      textNode = this.XMLDoc.createTextNode(value);
      node.appendChild((Node)textNode);
    }

    if (textNode != null) {
      ((Node)textNode).setNodeValue(value);
    }

  }

  private void setCredentialsInXMLDocument() {
    NodeList requestlist = this.XMLDoc.getElementsByTagName("jdeRequest");
    Node request = requestlist.item(0);

    try {
      NamedNodeMap attributes = request.getAttributes();
      Node att = attributes.getNamedItem("user");
      att.setNodeValue(this.jtfUser.getText());
      att = attributes.getNamedItem("pwd");
      String pwd = String.valueOf(this.jtfPassword.getPassword());
      att.setNodeValue(pwd);
      att = attributes.getNamedItem("environment");
      att.setNodeValue(this.jtfEnvironment.getText());
      att = attributes.getNamedItem("session");
      att.setNodeValue(this.jtfSessionID.getText());
    } catch (Exception var6) {
      var6.printStackTrace();
    }

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

  private int GetParameterIndexByName(String parmname) {
    for(int i = 0; i < this.BSFNParmsModel.getRowCount(); ++i) {
      String parm = null;
      parm = (String)this.BSFNParmsModel.getValueAt(i, 0);
      if (parm.compareTo(parmname) == 0) {
        return i;
      }
    }

    return -1;
  }

  public static void main(String[] args) {
    new jdeXMLBSFN();
  }

  private void jtfFunction_focusLost(FocusEvent e) {
    this.jbGetTemplate.doClick();
  }
}
