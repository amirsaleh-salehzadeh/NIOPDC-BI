package rex.graphics.dimensiontree.elements;

import javax.swing.ImageIcon;
import rex.metadata.ServerMetadata;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import rex.utils.*;
import rex.metadata.QueryElement;
import rex.graphics.dimensiontree.elements.DimensionTreeElement;
import rex.metadata.UniqueNameElement;
import rex.xmla.XMLADiscoverRestrictions;
import rex.xmla.XMLADiscoverProperties;

/**
 * <p>Title: Warehouse Explorer</p>
 *
 * <p>Description: XMLA Client</p>
 *
 * <p>Copyright: Copyright (c) 2005</p>
 *
 * <p>Company: </p>
 *
 * @author Igor
 * @version 0.3
 */



/*
 <row>
  <CATALOG_NAME>Test</CATALOG_NAME>
  <CUBE_NAME>Ispit</CUBE_NAME>
  <DIMENSION_UNIQUE_NAME>[RbrIzlaskaNaIspit]</DIMENSION_UNIQUE_NAME>
  <HIERARCHY_UNIQUE_NAME>[RbrIzlaskaNaIspit]</HIERARCHY_UNIQUE_NAME>
  <LEVEL_UNIQUE_NAME>[RbrIzlaskaNaIspit].[NazivRbrIzlaskaNaIspit]</LEVEL_UNIQUE_NAME>
  <LEVEL_NUMBER>1</LEVEL_NUMBER>
  <MEMBER_ORDINAL>61</MEMBER_ORDINAL>
  <MEMBER_NAME>61. izlazak</MEMBER_NAME>
  <MEMBER_UNIQUE_NAME>[RbrIzlaskaNaIspit].[All RbrIzlaskaNaIspit].[61. izlazak]</MEMBER_UNIQUE_NAME>
  <MEMBER_TYPE>1</MEMBER_TYPE>
  <MEMBER_CAPTION>61. izlazak</MEMBER_CAPTION>
  <CHILDREN_CARDINALITY>0</CHILDREN_CARDINALITY>
  <PARENT_LEVEL>0</PARENT_LEVEL>
  <PARENT_UNIQUE_NAME>[RbrIzlaskaNaIspit].[All RbrIzlaskaNaIspit]</PARENT_UNIQUE_NAME>
  <PARENT_COUNT>1</PARENT_COUNT>
  <MEMBER_KEY>61</MEMBER_KEY>
  <IS_PLACEHOLDERMEMBER>false</IS_PLACEHOLDERMEMBER>
  <IS_DATAMEMBER>false</IS_DATAMEMBER>
  </row>

 */



public class MemberElement implements DimensionTreeElement, UniqueNameElement, QueryElement{

   private String catalogName;
   private String cubeName;
   private String dimensionUniqueName;
   private String hierarchyUniqueName;
   private String levelUniqueName;
   private int levelNumber;
   private String memberName;
   private String memberUniqueName;
   private String memberCaption;
   private int memberType;
   private int memberOrdinal;
   private int childrenCardinality;
   private boolean nodeEnabled;


   private XMLADiscoverRestrictions restrictions;
   private XMLADiscoverProperties   properties;

   private ServerMetadata parent;

   static int MEMBER_CARDINALITY_NOT_INITIALIZED = -1;
   static ImageIcon memberIcon;

   static {
       memberIcon = S.getAppIcon("insert_picture.gif");
   }
   public MemberElement(     ServerMetadata svm
                           , Node rowNode
                           , XMLADiscoverRestrictions _restrictions
                           , XMLADiscoverProperties   _properties) {

      parent = svm;
      nodeEnabled = true;
      NodeList nl = rowNode.getChildNodes();

      // some providers don't supply this information and it is important for rendering a tree
      // for mdx editor, to dertermine whether a node is a leaf or not
      childrenCardinality = MEMBER_CARDINALITY_NOT_INITIALIZED;

      for(int i=0; i < nl.getLength(); i++){
         // TO BE DONE: OTHER VARS...

         if (nl.item(i).getNodeType() == 1){
            if (nl.item(i).getNodeName().equals("CATALOG_NAME")) {
               catalogName = DOM.getTextFromDOMElement(nl.item(i));
            }else if (nl.item(i).getNodeName().equals("CUBE_NAME")) {
               cubeName = DOM.getTextFromDOMElement(nl.item(i));
            }else if (nl.item(i).getNodeName().equals("DIMENSION_UNIQUE_NAME")) {
               dimensionUniqueName = DOM.getTextFromDOMElement(nl.item(i));
            }else if (nl.item(i).getNodeName().equals("HIERARCHY_UNIQUE_NAME")) {
               hierarchyUniqueName = DOM.getTextFromDOMElement(nl.item(i));
            }else if (nl.item(i).getNodeName().equals("LEVEL_UNIQUE_NAME")) {
               levelUniqueName = DOM.getTextFromDOMElement(nl.item(i));
            }else if (nl.item(i).getNodeName().equals("LEVEL_NUMBER")) {
               levelNumber = Integer.parseInt(DOM.getTextFromDOMElement(nl.item(i)));
            }else if (nl.item(i).getNodeName().equals("MEMBER_NAME")) {
               memberName = DOM.getTextFromDOMElement(nl.item(i));
            }else if (nl.item(i).getNodeName().equals("MEMBER_UNIQUE_NAME")) {
               memberUniqueName = DOM.getTextFromDOMElement(nl.item(i));
            }else if (nl.item(i).getNodeName().equals("MEMBER_CAPTION")) {
               memberCaption = DOM.getTextFromDOMElement(nl.item(i));
            }else if (nl.item(i).getNodeName().equals("MEMBER_TYPE")) {
               memberType = Integer.parseInt(DOM.getTextFromDOMElement(nl.item(i)));
            }else if (nl.item(i).getNodeName().equals("MEMBER_ORDINAL")){
               memberOrdinal = Integer.parseInt(DOM.getTextFromDOMElement(nl.item(i)));
            }else if (nl.item(i).getNodeName().equals("CHILDREN_CARDINALITY")){
               childrenCardinality = Integer.parseInt(DOM.getTextFromDOMElement(nl.item(i)));
            }

         }
      }


   }
   public MemberElement(
                             ServerMetadata svm
                           , XMLADiscoverRestrictions _restrictions
                           , XMLADiscoverProperties   _properties
                           , String _catalogName
                           , String _cubeName
                           , String _dimensionUniqueName
                           , String _hierarchyUniqueName
                           , String _levelUniqueName
                           , int    _levelNumber
                           , String _memberName
                           , String _memberUniqueName
                           , String _memberCaption
                           , int    _memberType
                           , int    _memberOrdinal
                           , int    _childrenCardinality) {

      parent = svm;
      restrictions = _restrictions;
      properties = _properties;
      nodeEnabled = true;
      catalogName         = _catalogName;
      cubeName = _cubeName;
      dimensionUniqueName = _dimensionUniqueName;
      hierarchyUniqueName = _hierarchyUniqueName;
      levelUniqueName = _levelUniqueName;
      levelNumber = _levelNumber;
      memberName = _memberName;
      memberUniqueName = _memberUniqueName;
      memberCaption = _memberCaption;
      memberType = _memberType;
      memberOrdinal = _memberOrdinal;
      childrenCardinality = _childrenCardinality;
   }

   public DimensionTreeElement[] getChildren(boolean noMatterWhat){
//      S.out("FilterTreeMemberElement returns 0 children");
      if (childrenCardinality > 0){
//         S.out("-------------------------------------------\n"
//               + "Getting children for " + memberCaption + "(" + memberUniqueName+ ") + children count=" + childrenCardinality
//               + "\nwith Restrictions:\n" + restrictions.getRestrictionList());

//       All these objects SHARE the same restrictions/properties, so I must set it exeactly before
//       query execution beacause it gets overwritten !!
         restrictions.setMemberUniqueName(memberUniqueName);
         restrictions.setTreeOp(XMLADiscoverRestrictions.MD_TREEOP_CHILDREN);
         // comment next line if you don't want "deep" tree (only 2 members levels)
         restrictions.setLevelUniqueName(null);
         return parent.getDimensionTreeMembersList(restrictions, properties);
      }else{
         return null;
      }
   }


   public String toString(){
      if (memberCaption == null){
/**
  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
  * All Rights Reserved
  * Copyright (C) 2006 Igor Mekterovic
  * All Rights Reserved
  */ 
           return I18n.getString("toolTip.notInitialized");
             /* end of modification for I18n */

      }
      else
         return memberCaption;

   }

   public ImageIcon getIcon(){
      return null;
//      return memberIcon;
   }

   public String getToolTip(){
      if (memberCaption == null){
/**
  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
  * All Rights Reserved
  * Copyright (C) 2006 Igor Mekterovic
  * All Rights Reserved
  */ 
           return I18n.getString("toolTip.notInitialized");
      }else{
         return "<html>"
            + I18n.getString("toolTip.membername:")                + memberName
                + "<BR>"+ I18n.getString("toolTip.memberUniqueName") + memberUniqueName
                + "<BR>"+ I18n.getString("toolTip.memberCaption")     + memberCaption
                + "<BR>"+ I18n.getString("toolTip.memberOrdinal")     + memberOrdinal
                + "<BR>"+ I18n.getString("toolTip.childrenCount") + childrenCardinality
                + "</html>";
           /* end of modification for I18n */

      }
   }




   public String[] getPopUpActionList(){
      return null;
   }
   public String getUniqueName(){
      return memberUniqueName;
   }
   public String getCaption(){
      return memberCaption;
   }
   public String getQueryMembersExpression(){
      return memberUniqueName;
   }

   public boolean isEnabled(){
      return nodeEnabled;
   }
   public void setEnabled(boolean newValue){
      nodeEnabled = newValue;
   }
   public String getHierarchyUniqueName(){
//       <MEMBER_UNIQUE_NAME>[Measures].[Prolaznost]</MEASURE_UNIQUE_NAME>
      return memberUniqueName.substring(0, memberUniqueName.lastIndexOf(".") - 1);
//       return measureUniqueName;
   }
   public boolean isMeasure(){
      return false;
   }

   public int getChildrenCount(){
      if (childrenCardinality == MEMBER_CARDINALITY_NOT_INITIALIZED){
         // WARNING: this could be expensive...
         DimensionTreeElement[] list = this.getChildren(true);
         childrenCardinality = list == null ? 0 : list.length;
      }
      return childrenCardinality;
   }


   public ServerMetadata getServerMetadata(){
      return parent;
   }
   public XMLADiscoverRestrictions getRestrictions(){
      return restrictions;
   }
   public XMLADiscoverProperties   getProperties(){
      return properties;
   }
   public String getDimensionUniqueName(){
      return dimensionUniqueName;
   }
}
