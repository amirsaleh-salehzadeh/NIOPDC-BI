package rex.utils;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;

/**
 * <p>Title: WHEX</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author igor
 * @version 1.0
 */

public class DOM {
   // An array of names for DOM node-types
   // (Array indexes = nodeType() values.)
   public static final String[] typeName = {
      "none",
      "Element",
      "Attr",
      "Text",
      "CDATA",
      "EntityRef",
      "Entity",
      "ProcInstr",
      "Comment",
      "Document",
      "DocType",
      "DocFragment",
      "Notation",
   };
   public static final short ELEMENT_TYPE = Node.ELEMENT_NODE;
   public static final short ATTR_TYPE = Node.ATTRIBUTE_NODE;
   public static final short TEXT_TYPE = Node.TEXT_NODE;
   public static final short CDATA_TYPE = Node.CDATA_SECTION_NODE;
   public static final short ENTITYREF_TYPE = Node.ENTITY_REFERENCE_NODE;
   public static final short ENTITY_TYPE = Node.ENTITY_NODE;
   public static final short PROCINSTR_TYPE = Node.PROCESSING_INSTRUCTION_NODE;
   public static final short COMMENT_TYPE = Node.COMMENT_NODE;
   public static final short DOCUMENT_TYPE = Node.DOCUMENT_NODE;
   public static final short DOCTYPE_TYPE = Node.DOCUMENT_TYPE_NODE;
   public static final short DOCFRAG_TYPE = Node.DOCUMENT_FRAGMENT_NODE;
   public static final short NOTATION_TYPE = Node.NOTATION_NODE;

   public DOM() {
   }
   public static String getTextFromDOMElement(org.w3c.dom.Node node){
      String retVal = "";
      if (node.getNodeType() != ELEMENT_TYPE) return "";
      org.w3c.dom.NodeList nodeList = node.getChildNodes();
      int i;
      // Do not traverse recursevly beacuse I need only text from the
      // 1. level below given node. I'm not intereseted in any sublemets's text.
      for (i=0; i<nodeList.getLength(); i++) {
         if (nodeList.item(i).getNodeType() == 3){  // 3 is a Text element
            retVal += nodeList.item(i).getNodeValue();
         }
      }
      return retVal;
   }
   public static final void dumpNodeAttributes(NamedNodeMap attrs){
      int i;
      if (attrs != null){
         for (i = 0; i < attrs.getLength(); i++) {
            S.out("attr(" + i + ") :"
                + " name=" + attrs.item(i).getNodeName()
                + " type=" + attrs.item(i).getNodeType()
                + " value=" + attrs.item(i).getNodeValue());
         }
      }else{
         S.out("node has no attributes (IS NULL!)");
      }
   }

   public static final void dumpChildNodes(Node n){
      NodeList nl = n.getChildNodes();

      for(int i=0; i < nl.getLength(); i++){
         S.out("node(" + i + ")"
                + " (" + typeName[nl.item(i).getNodeType()] + ") "
                + " = " + nl.item(i).getNodeName()
                + " (" + getTextFromDOMElement(nl.item(i)) + ")");
      }

   }

   public static Node getFirstChildNodeWithName(Node n, String nodeName){
      NodeList nl = n.getChildNodes();
      for(int i=0; i < nl.getLength(); i++){
         if (nl.item(i).getNodeName().equals(nodeName)){
            return nl.item(i);
         }
      }
      return null;
   }

   public static int getCountOfChildNodesOfType(Node n, short type){
      NodeList allChildren = n.getChildNodes();
      int count = 0;
      for(int i=0; i < allChildren.getLength(); i++){
         if (allChildren.item(i).getNodeType() == type)
            count++;
      }
      return count;

   }


}