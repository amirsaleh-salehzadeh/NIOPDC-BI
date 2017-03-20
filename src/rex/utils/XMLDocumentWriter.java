package rex.utils;

/**
 *   Copyright (C) 2006 CINCOM SYSTEMS, INC.
 *   All Rights Reserved
 *   Copyright (C) 2006 Igor Mekterovic
 *   All Rights Reserved
 */


import org.w3c.dom.CDATASection;
import org.w3c.dom.Comment;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentType;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.ProcessingInstruction;
import org.w3c.dom.Text;

import org.apache.log4j.*;


public class XMLDocumentWriter {
  static Logger logger;

  /** Initialize logger */
  public XMLDocumentWriter() {
    logger=Logger.getLogger(XMLDocumentWriter.class);
  }

  public void write(Node node) {
       write(node, "");
  }
  
  public void write(Node node, String align) {
      // log output depends on the type of the node
      short nodeType=node.getNodeType();
      if (nodeType==Node.DOCUMENT_NODE) 
      {
          logger.info(align + "<?xml version='1.0'?>");
          Node child = ((Document) node).getFirstChild(); // first node
          while (child != null)// till not null 
          { 
              write(child, align); // log Output node
              child = child.getNextSibling(); // next node
          }
      } 
      else if(nodeType==Node.DOCUMENT_TYPE_NODE)// <!DOCTYPE> tag 
      {          
      logger.info("<!DOCTYPE " + ((DocumentType) node).getName() + ">");
      }
      else if(nodeType==Node.ELEMENT_NODE)// Most nodes are Elements 
      { 
          Element element = (Element) node;        
          logger.info(align + "<" + element.getTagName()); // Begin start tag        		
          NamedNodeMap attrs = element.getAttributes(); // Get attributes
          for (int i = 0; i < attrs.getLength(); i++) 
          {
              logger.info(" " + attrs.item(i).getNodeName() + "='" + // Log attributes name
              transform(attrs.item(i).getNodeValue()) + "'"); // Log attributes value
          }        
          logger.info(">");

          String newalign = align + "    ";
          Node child = element.getFirstChild(); 
          while (child != null) 
          {
              write(child, newalign); // Log child
              child = child.getNextSibling();
          }
          logger.info(align + "</" + element.getTagName() + ">");// Log tag   		
      }
      else if(nodeType==Node.TEXT_NODE) 
      { 
          String text = ((Text)node).getData().trim(); 
          if ((text != null) && text.length() > 0)
          {
              logger.info(align + transform(text));
          }
      }
      else if(nodeType==Node.PROCESSING_INSTRUCTION_NODE) 
      { 
          logger.info(align + "<?" + ((ProcessingInstruction) node).getTarget() + " " 
                + ((ProcessingInstruction) node).getData() + "?>");
      }
      else if(nodeType==Node.ENTITY_REFERENCE_NODE) 
      { 
          logger.info(align + "&" + node.getNodeName() + ";");
      }
      else if(nodeType==Node.CDATA_SECTION_NODE) 
      {      
          logger.info(align + "<" + "![CDATA[" + ((CDATASection) node).getData() + "]]" + ">");
      }
      else if(nodeType==Node.COMMENT_NODE) 
      {       
          logger.info(align + "<!--" + ((Comment) node).getData() + "-->");
      }
      else 
      { 
      logger.error("Ignoring node: " + node.getClass().getName());
      }
    }
  
  String transform(String s) {
    StringBuffer sb = new StringBuffer();
    char[] c = s.toCharArray();
    for (int i = 0; i < c.length; i++)
    {
      switch (c[i]) {
      default:
        sb.append(c[i]);
        break;
      case '<'  : sb.append("&lt;");
      break;
      case '>'  : sb.append("&gt;");
      break;
      case '&'  : sb.append("&amp;");
      break;
      case '\"' : sb.append("&quot;");
      break;
      case '\'' : sb.append("&apos;");
      break;
      }
    }
    return sb.toString();
  }  
}
