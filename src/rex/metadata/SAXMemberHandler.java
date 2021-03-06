package rex.metadata;

import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import rex.utils.S;
import org.w3c.dom.NodeList;
import java.util.LinkedList;
import rex.graphics.dimensiontree.elements.DimensionTreeElement;
import rex.graphics.filtertree.elements.FilterTreeMemberElement;
import java.util.HashMap;

import rex.graphics.dimensiontree.elements.MemberElement;
import rex.xmla.XMLADiscoverRestrictions;
import rex.xmla.XMLADiscoverProperties;

/**
 * <p>Title: WHEX</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author igor
 * @version 1.0
 */

public class SAXMemberHandler extends DefaultHandler{
   private int rowCount;

   LinkedList members;
   StringBuffer current;
   private String catalogName;
   private String cubeName;
   private String dimensionUniqueName;
   private String hierarchyUniqueName;
   private String levelUniqueName;
   private String levelNumber;
   private String memberName;
   private String memberUniqueName;
   private String memberCaption;
   private String memberType;
   private String memberOrdinal;
   private HashMap elements;

   private boolean spawnFilterTreeMemberElement;

   private XMLADiscoverRestrictions restrictions;
   private XMLADiscoverProperties   properties;

   private ServerMetadata svm;


/*
    <row>
    <CATALOG_NAME>Test</CATALOG_NAME>
    <CUBE_NAME>Ispit</CUBE_NAME>
    <DIMENSION_UNIQUE_NAME>[OcjenaPismeni]</DIMENSION_UNIQUE_NAME>
    <HIERARCHY_UNIQUE_NAME>[OcjenaPismeni]</HIERARCHY_UNIQUE_NAME>
    <LEVEL_UNIQUE_NAME>[OcjenaPismeni].[Ocjena Naziv]</LEVEL_UNIQUE_NAME>
    <LEVEL_NUMBER>2</LEVEL_NUMBER>
    <MEMBER_ORDINAL>11</MEMBER_ORDINAL>
    <MEMBER_NAME>Izvrstan (5)</MEMBER_NAME>
    <MEMBER_UNIQUE_NAME>[OcjenaPismeni].[Sve ocjene pismenog ispita].[Pozitivna ocjena].[Izvrstan (5)]</MEMBER_UNIQUE_NAME>
    <MEMBER_TYPE>1</MEMBER_TYPE>
    <MEMBER_CAPTION>Izvrstan (5)</MEMBER_CAPTION>
    <CHILDREN_CARDINALITY>0</CHILDREN_CARDINALITY>
    <PARENT_LEVEL>1</PARENT_LEVEL>
    <PARENT_UNIQUE_NAME>[OcjenaPismeni].[Sve ocjene pismenog ispita].[Pozitivna ocjena]</PARENT_UNIQUE_NAME>
    <PARENT_COUNT>1</PARENT_COUNT>
    <MEMBER_KEY>5</MEMBER_KEY>
    <IS_PLACEHOLDERMEMBER>false</IS_PLACEHOLDERMEMBER>
    <IS_DATAMEMBER>false</IS_DATAMEMBER>
    </row>
*/

public SAXMemberHandler(  ServerMetadata _svm
                        , XMLADiscoverRestrictions _restrictions
                        , XMLADiscoverProperties   _properties
                        , boolean _spawnFilterTreeMemberElement) {
      super();
      rowCount = 0;
      current = new StringBuffer("");
      elements = new HashMap();
      members = new LinkedList();
      // these guys are only forwarded to FilterTreeMemberElement
      svm = _svm;
      restrictions = _restrictions;
      properties   = _properties;
      spawnFilterTreeMemberElement = _spawnFilterTreeMemberElement;
   }

   public void startElement(String namespaceURI,
                            String sName, // simple name
                            String qName, // qualified name
                            Attributes attrs) throws SAXException{
      current = new StringBuffer("");
   }

   public void endElement(String namespaceURI,
                          String sName, // simple name
                          String qName  // qualified name
                         ) throws SAXException{
      if (qName.equals("row")) {
         rowCount++;
         MemberElement member;
         if (spawnFilterTreeMemberElement){
//            S.out("creating filter tree member");
            member = new FilterTreeMemberElement(
                     svm,
                     restrictions,
                     properties,
                     (String) elements.get("CATALOG_NAME"),
                     (String) elements.get("CUBE_NAME"),
                     (String) elements.get("DIMENSION_UNIQUE_NAME"),
                     (String) elements.get("HIERARCHY_UNIQUE_NAME"),
                     (String) elements.get("LEVEL_UNIQUE_NAME"),
                     Integer.parseInt((String) elements.get("LEVEL_NUMBER")),
                     (String) elements.get("MEMBER_NAME"),
                     (String) elements.get("MEMBER_UNIQUE_NAME"),
                     (String) elements.get("MEMBER_CAPTION"),
                     Integer.parseInt((String) elements.get("MEMBER_TYPE")),
                     Integer.parseInt((String) elements.get("MEMBER_ORDINAL")),
                     Integer.parseInt((String) elements.get("CHILDREN_CARDINALITY"))
                     );
         }else{
//            S.out("creating dim. tree member");
            member = new MemberElement(
                     svm,
                     restrictions,
                     properties,
                     (String) elements.get("CATALOG_NAME"),
                     (String) elements.get("CUBE_NAME"),
                     (String) elements.get("DIMENSION_UNIQUE_NAME"),
                     (String) elements.get("HIERARCHY_UNIQUE_NAME"),
                     (String) elements.get("LEVEL_UNIQUE_NAME"),
                     Integer.parseInt((String) elements.get("LEVEL_NUMBER")),
                     (String) elements.get("MEMBER_NAME"),
                     (String) elements.get("MEMBER_UNIQUE_NAME"),
                     (String) elements.get("MEMBER_CAPTION"),
                     Integer.parseInt((String) elements.get("MEMBER_TYPE")),
                     Integer.parseInt((String) elements.get("MEMBER_ORDINAL")),
                     Integer.parseInt((String) elements.get(
                     "CHILDREN_CARDINALITY"))
                                              );

         }
         members.addLast(member);
      }else{
         String el = (String)elements.get(qName);
//         S.out(qName + ".hashCode()=" + qName.hashCode());
         if (!qName.startsWith("xsd")){
//            S.out("adding!!!  ----> current = " + current);
            elements.put(qName, current.toString());
//            el = current.toString();
//            elements.put(qName, el);
         }else{
//            S.out("skipping xsd");
         }
      }

   }

   public int getRowCount(){
      return rowCount;
   }

   public void characters(char buf[], int offset, int len)   throws SAXException
   {
       String s = new String(buf, offset, len);
       current.append(s);
   }

   public DimensionTreeElement[] getMembers(){
      if (members.size()>0){
         return (DimensionTreeElement[])members.toArray(new DimensionTreeElement[0]);
      }else{
         return null;
      }
   }

}
