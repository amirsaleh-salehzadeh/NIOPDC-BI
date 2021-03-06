package rex.graphics.dimensiontree.elements;

import javax.swing.ImageIcon;
import rex.metadata.ServerMetadata;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import rex.utils.*;
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


/*
 <row>
  <CATALOG_NAME>Test</CATALOG_NAME>
  <CUBE_NAME>Ispit</CUBE_NAME>
  <DIMENSION_UNIQUE_NAME>[AkIzvodjacPismeni]</DIMENSION_UNIQUE_NAME>
  <HIERARCHY_UNIQUE_NAME>[AkIzvodjacPismeni]</HIERARCHY_UNIQUE_NAME>
  <HIERARCHY_CAPTION>AkIzvodjacPismeni</HIERARCHY_CAPTION>
  <DIMENSION_TYPE>3</DIMENSION_TYPE>
  <HIERARCHY_CARDINALITY>5</HIERARCHY_CARDINALITY>
  <DEFAULT_MEMBER>[AkIzvodjacPismeni].[Svi akademski stupnjevi]</DEFAULT_MEMBER>
  <ALL_MEMBER>[AkIzvodjacPismeni].[Svi akademski stupnjevi]</ALL_MEMBER>
  <STRUCTURE>0</STRUCTURE>
  <IS_VIRTUAL>false</IS_VIRTUAL>
  <IS_READWRITE>false</IS_READWRITE>
  <DIMENSION_UNIQUE_SETTINGS>0</DIMENSION_UNIQUE_SETTINGS>
  <DIMENSION_IS_VISIBLE>true</DIMENSION_IS_VISIBLE>
  <HIERARCHY_ORDINAL>1</HIERARCHY_ORDINAL>
  <DIMENSION_IS_SHARED>true</DIMENSION_IS_SHARED>
  </row>

 */
public class HierarchyElement implements DimensionTreeElement{
   private String catalogName;
   private String cubeName;
   private String dimensionUniqueName;
   private String hierarchyName;
   private String hierarchyUniqueName;
   private String hierarchyGuid;
   private String hierarchyCaption;
   private short dimensionType;
   private int hierarchyCardinality;
   private String defaultMember;
   private String allMember;
   private String description;
   private short structure;
   private boolean isVirtual;
   private boolean isReadwrite;
   private int dimensionUniqueSettings;
   private String dimensionMasterUniqueName;
   private boolean dimensionIsVisible;
   private int hierarchyOrdinal;
   private boolean dimensionIsShared;

   private boolean nodeEnabled;

   private XMLADiscoverRestrictions restrictions;
   private XMLADiscoverProperties   properties;

   private ServerMetadata parent;

   static int HIERARCHY_CARDINALITY_NOT_INITIALIZED = -1;

   static ImageIcon icon;

   static {
       icon = S.getAppIcon("hierarchy.gif");
   }
   public HierarchyElement(  ServerMetadata svm
                           , Node rowNode
                           , XMLADiscoverRestrictions _restrictions
                           , XMLADiscoverProperties   _properties) {
      parent = svm;

      nodeEnabled = true;

      restrictions = _restrictions;
      properties  = _properties;

      // some providers don't supply this information and it is important for rendering a tree
      // for mdx editor, to dertermine whether a node is a leaf or not
      hierarchyCardinality = HIERARCHY_CARDINALITY_NOT_INITIALIZED;

      NodeList nl = rowNode.getChildNodes();

      for(int i=0; i < nl.getLength(); i++){
         if (nl.item(i).getNodeType() == 1){
            if (nl.item(i).getNodeName().equals("CATALOG_NAME")) {
               catalogName = DOM.getTextFromDOMElement(nl.item(i));
            }else if (nl.item(i).getNodeName().equals("CUBE_NAME")) {
               cubeName = DOM.getTextFromDOMElement(nl.item(i));
            }else if (nl.item(i).getNodeName().equals("DIMENSION_UNIQUE_NAME")) {
               dimensionUniqueName = DOM.getTextFromDOMElement(nl.item(i));
            }else if (nl.item(i).getNodeName().equals("HIERARCHY_NAME")) {
               hierarchyName = DOM.getTextFromDOMElement(nl.item(i));
            }else if (nl.item(i).getNodeName().equals("HIERARCHY_UNIQUE_NAME")) {
               hierarchyUniqueName = DOM.getTextFromDOMElement(nl.item(i));
            }else if (nl.item(i).getNodeName().equals("HIERARCHY_GUID")) {
               hierarchyGuid = DOM.getTextFromDOMElement(nl.item(i));
            }else if (nl.item(i).getNodeName().equals("HIERARCHY_CAPTION")) {
               hierarchyCaption = DOM.getTextFromDOMElement(nl.item(i));
            }else if (nl.item(i).getNodeName().equals("DIMENSION_TYPE")) {
               dimensionType = Short.parseShort(DOM.getTextFromDOMElement(nl.item(i)));
            }else if (nl.item(i).getNodeName().equals("HIERARCHY_CARDINALITY")) {
               hierarchyCardinality = Integer.parseInt(DOM.getTextFromDOMElement(nl.item(i)));
            }else if (nl.item(i).getNodeName().equals("DEFAULT_MEMBER")) {
               defaultMember = DOM.getTextFromDOMElement(nl.item(i));
            }else if (nl.item(i).getNodeName().equals("ALL_MEMBER")) {
               allMember = DOM.getTextFromDOMElement(nl.item(i));
            }else if (nl.item(i).getNodeName().equals("DESCRIPTION")) {
               description = DOM.getTextFromDOMElement(nl.item(i));
            }else if (nl.item(i).getNodeName().equals("STRUCTURE")) {
               structure = Short.parseShort(DOM.getTextFromDOMElement(nl.item(i)));
            }else if (nl.item(i).getNodeName().equals("IS_VIRTUAL")) {
               isVirtual = Boolean.valueOf(DOM.getTextFromDOMElement(nl.item(i))).booleanValue();
            }else if (nl.item(i).getNodeName().equals("IS_READWRITE")) {
               isReadwrite = (Boolean.valueOf(DOM.getTextFromDOMElement(nl.item(i)))).booleanValue();
            }else if (nl.item(i).getNodeName().equals("DIMENSION_UNIQUE_SETTINGS")) {
               dimensionUniqueSettings = Integer.parseInt(DOM.getTextFromDOMElement(nl.item(i)));
            }else if (nl.item(i).getNodeName().equals("DIMENSION_MASTER_UNIQUE_NAME")) {
               dimensionMasterUniqueName = DOM.getTextFromDOMElement(nl.item(i));
            }else if (nl.item(i).getNodeName().equals("DIMENSION_IS_VISIBLE")) {
               dimensionIsVisible = (Boolean.valueOf(DOM.getTextFromDOMElement(nl.item(i)))).booleanValue();
            }else if (nl.item(i).getNodeName().equals("HIERARCHY_ORDINAL")) {
               hierarchyOrdinal = Integer.parseInt(DOM.getTextFromDOMElement(nl.item(i)));
            }else if (nl.item(i).getNodeName().equals("DIMENSION_IS_SHARED")) {
               dimensionIsShared = (Boolean.valueOf(DOM.getTextFromDOMElement(nl.item(i)))).booleanValue();
            }
         }

      }

      //S.out("Constructor :: hierarchyUniqueName = " + hierarchyUniqueName);


   }

   public DimensionTreeElement[] getChildren(boolean noMatterWhat){
      // other restrictons are already set, i.e. cube and catalog name
      restrictions.setDimensionUniqueName(dimensionUniqueName);
      restrictions.setHierarchyUniqueName(hierarchyUniqueName);
      //S.out("HierarchyElement.getLevelList, restrictions = " + restrictions.getRestrictionList());
      return parent.getLevelList(restrictions, properties);

   }


   public String toString(){
      if (hierarchyName == null){
     /**
      * Copyright (C) 2006 CINCOM SYSTEMS, INC.
      * All Rights Reserved
      * Copyright (C) 2006 Igor Mekterovic
      * All Rights Reserved
      */ 
      /* implementing localization */
          return I18n.getString("toolTip.notInitialized");
           /* end of modification for I18n */
 
      }
      else
         return hierarchyName;

   }

   public ImageIcon getIcon(){
      return icon;
   }

   public String getToolTip(){
     if (hierarchyName == null){
              /**
      * Copyright (C) 2006 CINCOM SYSTEMS, INC.
      * All Rights Reserved
      * Copyright (C) 2006 Igor Mekterovic
      * All Rights Reserved
      */ 
      /* implementing localization */
          return I18n.getString("toolTip.notInitialized"); 
      }
      else{
         return "" + hierarchyName
            + I18n.getString("toolTip.caption") + hierarchyCaption
            + I18n.getString("toolTip.dimensionUniqueName") + dimensionUniqueName;
           /* end of modification for I18n */

      }

   }


   public String[] getPopUpActionList(){
      return null;
//      return new String[] {"Explore!"};
   }

   public String getHierarchyUniqueName(){
      return hierarchyUniqueName;
   }

   public String getDimensionUniqueName(){
      return dimensionUniqueName;
   }
   public String getUniqueName(){
      return hierarchyUniqueName;
   }

   public String getCaption(){
      return hierarchyCaption;
   }
   public String getQueryMembersExpression(){
      return hierarchyUniqueName + ".Members";
   }
   public boolean isEnabled(){
      return nodeEnabled;
   }
   public void setEnabled(boolean newValue){
      nodeEnabled = newValue;
   }


   public int getChildrenCount(){
      if (hierarchyCardinality == HIERARCHY_CARDINALITY_NOT_INITIALIZED){
         // WARNING: this could be expensive...
         DimensionTreeElement[] list = this.getChildren(true);
         hierarchyCardinality = list == null ? 0 : list.length;
      }
      return hierarchyCardinality;
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


}
