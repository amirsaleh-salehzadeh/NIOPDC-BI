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
 <xsd:complexType name="row">
- <xsd:sequence>
  <xsd:element name="CATALOG_NAME" type="xsd:string" sql:field="CATALOG_NAME" minOccurs="0" />
  <xsd:element name="SCHEMA_NAME" type="xsd:string" sql:field="SCHEMA_NAME" minOccurs="0" />
  <xsd:element name="CUBE_NAME" type="xsd:string" sql:field="CUBE_NAME" minOccurs="0" />
  <xsd:element name="DIMENSION_NAME" type="xsd:string" sql:field="DIMENSION_NAME" minOccurs="0" />
  <xsd:element name="DIMENSION_UNIQUE_NAME" type="xsd:string" sql:field="DIMENSION_UNIQUE_NAME" minOccurs="0" />
  <xsd:element name="DIMENSION_GUID" type="uuid" sql:field="DIMENSION_GUID" minOccurs="0" />
  <xsd:element name="DIMENSION_CAPTION" type="xsd:string" sql:field="DIMENSION_CAPTION" minOccurs="0" />
  <xsd:element name="DIMENSION_ORDINAL" type="xsd:unsignedInt" sql:field="DIMENSION_ORDINAL" />
  <xsd:element name="DIMENSION_TYPE" type="xsd:short" sql:field="DIMENSION_TYPE" />
  <xsd:element name="DIMENSION_CARDINALITY" type="xsd:unsignedInt" sql:field="DIMENSION_CARDINALITY" />
  <xsd:element name="DEFAULT_HIERARCHY" type="xsd:string" sql:field="DEFAULT_HIERARCHY" minOccurs="0" />
  <xsd:element name="DESCRIPTION" type="xsd:string" sql:field="DESCRIPTION" minOccurs="0" />
  <xsd:element name="IS_VIRTUAL" type="xsd:boolean" sql:field="IS_VIRTUAL" />
  <xsd:element name="IS_READWRITE" type="xsd:boolean" sql:field="IS_READWRITE" />
  <xsd:element name="DIMENSION_UNIQUE_SETTINGS" type="xsd:int" sql:field="DIMENSION_UNIQUE_SETTINGS" />
  <xsd:element name="DIMENSION_MASTER_UNIQUE_NAME" type="xsd:string" sql:field="DIMENSION_MASTER_UNIQUE_NAME" minOccurs="0" />
  <xsd:element name="DIMENSION_IS_VISIBLE" type="xsd:boolean" sql:field="DIMENSION_IS_VISIBLE" />
  </xsd:sequence>
  </xsd:complexType>

*/

public class DimensionElement implements DimensionTreeElement {

   private String catalogName;
   private String schemaName;
   private String cubeName;
   private String dimensionName;
   private String dimensionUniqueName;
   private String dimensionGuid;
   private String dimensionCaption;
   private int dimensionOrdinal;
   private short dimensionType;
   private int dimensionCardinality;
   private String defaultHierarchy;
   private String description;
   private boolean isVirtual;
   private boolean isReadwrite;
   private int dimensionUniqueSettings;
   private String dimensionMasterUniqueName;
   private boolean dimensionIsVisible;

   private boolean nodeEnabled;

   private XMLADiscoverRestrictions restrictions;
   private XMLADiscoverProperties   properties;

   private ServerMetadata parent;

   private boolean flattenDimensions;
   public static short
        DIMENSION_TYPE_TIME    = (short)1
      , DIMENSION_TYPE_MEASURE = (short)2
      , DIMENSION_TYPE_REGULAR = (short)3;
   static int DIMENSION_CARDINALITY_NOT_INITIALIZED = -1;
   static ImageIcon iconRegular, iconTime, iconMeasure;
   static {
       iconRegular = S.getAppIcon("dimension.gif");
       iconTime = S.getAppIcon("time_dimension.gif");
       iconMeasure = S.getAppIcon("measure_dimension.gif");
   }
   public DimensionElement(  ServerMetadata svm
                           , Node rowNode
                           , XMLADiscoverRestrictions _restrictions
                           , XMLADiscoverProperties   _properties) {
      parent = svm;

      nodeEnabled = true;

      restrictions = _restrictions;
      properties = _properties;

      // some providers don't supply this information and it is important for rendering a tree
      // for mdx editor, to dertermine whether a node is a leaf or not
      dimensionCardinality = DIMENSION_CARDINALITY_NOT_INITIALIZED;

      NodeList nl = rowNode.getChildNodes();

      for(int i=0; i < nl.getLength(); i++){
         if (nl.item(i).getNodeType() == 1){
            if (nl.item(i).getNodeName().equals("CATALOG_NAME")) {
               catalogName = DOM.getTextFromDOMElement(nl.item(i));
            }else if (nl.item(i).getNodeName().equals("SCHEMA_NAME")) {
               schemaName = DOM.getTextFromDOMElement(nl.item(i));
            }else if (nl.item(i).getNodeName().equals("CUBE_NAME")) {
               cubeName = DOM.getTextFromDOMElement(nl.item(i));
            }else if (nl.item(i).getNodeName().equals("DIMENSION_NAME")) {
               dimensionName = DOM.getTextFromDOMElement(nl.item(i));
            }else if (nl.item(i).getNodeName().equals("DIMENSION_UNIQUE_NAME")) {
               dimensionUniqueName = DOM.getTextFromDOMElement(nl.item(i));
            }else if (nl.item(i).getNodeName().equals("DIMENSION_GUID")) {
               dimensionGuid = DOM.getTextFromDOMElement(nl.item(i));
            }else if (nl.item(i).getNodeName().equals("DIMENSION_CAPTION")) {
               dimensionCaption = DOM.getTextFromDOMElement(nl.item(i));
            }else if (nl.item(i).getNodeName().equals("DIMENSION_ORDINAL")) {
               dimensionOrdinal = Integer.parseInt(DOM.getTextFromDOMElement(nl.item(i)));
            }else if (nl.item(i).getNodeName().equals("DIMENSION_TYPE")) {
               dimensionType = Short.parseShort( DOM.getTextFromDOMElement(nl.item(i)));
            }else if (nl.item(i).getNodeName().equals("DIMENSION_CARDINALITY")) {
               dimensionCardinality = Integer.parseInt(DOM.getTextFromDOMElement(nl.item(i)));
            }else if (nl.item(i).getNodeName().equals("DEFAULT_HIERARCHY")) {
               defaultHierarchy = DOM.getTextFromDOMElement(nl.item(i));
            }else if (nl.item(i).getNodeName().equals("DESCRIPTION")) {
               description = DOM.getTextFromDOMElement(nl.item(i));
            }else if (nl.item(i).getNodeName().equals("IS_VIRTUAL")) {
               isVirtual = (Boolean.valueOf(DOM.getTextFromDOMElement(nl.item(i)))).booleanValue();
            }else if (nl.item(i).getNodeName().equals("IS_READWRITE")) {
               isReadwrite = (Boolean.valueOf(DOM.getTextFromDOMElement(nl.item(i)))).booleanValue();
            }else if (nl.item(i).getNodeName().equals("DIMENSION_UNIQUE_SETTINGS")) {
               dimensionUniqueSettings = Integer.parseInt(DOM.getTextFromDOMElement(nl.item(i)));
            }else if (nl.item(i).getNodeName().equals("DIMENSION_MASTER_UNIQUE_NAME")) {
               dimensionMasterUniqueName = DOM.getTextFromDOMElement(nl.item(i));
            }else if (nl.item(i).getNodeName().equals("DIMENSION_IS_VISIBLE")) {
               dimensionIsVisible = (Boolean.valueOf(DOM.getTextFromDOMElement(nl.item(i)))).booleanValue();
            }

         }
      }
   }

// <DEBUG>
// this ugly constructor is for debugging purposes only:
   public DimensionElement(  ServerMetadata svm
                           , XMLADiscoverRestrictions _restrictions
                           , XMLADiscoverProperties   _properties
                           , String _catalogName
                           , String _cubeName
                           , String _dimensionName
                           , String _dimensionUniqueName
                           , String _dimensionCaption
                          ) {
      parent = svm;

      nodeEnabled = true;

      restrictions = _restrictions;
      properties = _properties;
      catalogName = _catalogName;
      cubeName = _cubeName;
      dimensionName = _dimensionName;
      dimensionUniqueName = _dimensionUniqueName;
      dimensionCaption = _dimensionCaption;
   }
// </DEBUG>



   public DimensionTreeElement[] getChildren(boolean noMatterWhat){
      // other restrictons are already set, i.e. cube and catalog name
      restrictions.setDimensionUniqueName(dimensionUniqueName);
      restrictions.setHierarchyUniqueName(null);
//      S.out("callint parent.getCubeList with catalogName = " + catalogName);
      if (flattenDimensions){
//         S.out("DimensionElement.getLevelList, restrictions = " + restrictions.getRestrictionList());
         return parent.getLevelList(restrictions, properties);
      }else{
//         S.out("DimensionElement.getHierarchyList, restrictions = " + restrictions.getRestrictionList());
         return parent.getHierarchyList(restrictions, properties);
      }

   }

   public void setFlattenDimensions(boolean _flattenDimensions){
      flattenDimensions =_flattenDimensions;
   }
   public boolean getFlattenDimensions(){
      return flattenDimensions;
   }


   public String toString(){
      if (dimensionCaption == null){
          /**
          * Copyright (C) 2006 CINCOM SYSTEMS, INC.
          * All Rights Reserved
          * Copyright (C) 2006 Igor Mekterovic
          * All Rights Reserved
          */ 
          /* implementing localization */
         return I18n.getString("toolTip.notInitialzed"); 
           /* end of modification for I18n */

      }
      else if (flattenDimensions)
         return dimensionCaption;
      else
         return dimensionCaption;

   }

   public ImageIcon getIcon(){
      if (isMeasureDimension())
         return iconMeasure;
      else if (isTimeDimension())
         return iconTime;
      else
         return iconRegular;
   }

   public static ImageIcon getDimensionIcon() {
      return iconRegular;
   }


   public String getToolTip(){
      if (dimensionName == null){
          /**
          * Copyright (C) 2006 CINCOM SYSTEMS, INC.
          * All Rights Reserved
          * Copyright (C) 2006 Igor Mekterovic
          * All Rights Reserved
          */ 
          /* implementing localization */
          return I18n.getString("toolTip.notInitialzed"); 
      }
      else{

          return "<html>" +I18n.getString("toolTip.dimensionName") + dimensionName
            + "<br>"+ I18n.getString("toolTip.caption") + dimensionCaption
            + "<br>"+ I18n.getString("toolTip.dimensionUniqueName") + dimensionUniqueName
            + "<html>";
            /* end of modification for I18n */

      }

   }
   public String[] getPopUpActionList(){
      return null;
   }

   public String getUniqueName(){
      return dimensionUniqueName;
   }
   public String getDimensionUniqueName(){
      return dimensionUniqueName;
   }
   public String getCaption(){
      return dimensionCaption;
   }
//   public String getQueryMembersExpression(){
//      return dimensionUniqueName + ".Members";
//   }
   public boolean isEnabled(){
      return nodeEnabled;
   }
   public void setEnabled(boolean newValue){
      nodeEnabled = newValue;
   }

   public boolean isMeasureDimension(){
      return (dimensionType == DimensionElement.DIMENSION_TYPE_MEASURE);
   }

   public boolean isTimeDimension(){
      return (dimensionType == DimensionElement.DIMENSION_TYPE_TIME);
   }

   public int getChildrenCount(){
      if (dimensionCardinality == DIMENSION_CARDINALITY_NOT_INITIALIZED){
         // WARNING: this could be expensive...
         DimensionTreeElement[] list = this.getChildren(true);
         dimensionCardinality = list == null ? 0 : list.length;
      }
      return dimensionCardinality;
   }



// these 3 methods are needed when filter tree is created based upon a dimension
   public ServerMetadata getServerMetadata(){
      return parent;
   }
   public XMLADiscoverRestrictions getRestrictions(){
      return restrictions;
   }
   public XMLADiscoverProperties   getProperties(){
      return properties;
   }
/////////////////////////////////////////////////


}
