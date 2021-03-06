package rex.graphics.dimensiontree.elements;

import javax.swing.ImageIcon;
import rex.metadata.ServerMetadata;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import rex.utils.*;
import rex.metadata.QueryElement;
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
  <MEASURE_NAME>Prolaznost</MEASURE_NAME>
  <MEASURE_UNIQUE_NAME>[Measures].[Prolaznost]</MEASURE_UNIQUE_NAME>
  <MEASURE_CAPTION>Prolaznost</MEASURE_CAPTION>
  <MEASURE_AGGREGATOR>127</MEASURE_AGGREGATOR>
  <DATA_TYPE>12</DATA_TYPE>
  <NUMERIC_PRECISION>65535</NUMERIC_PRECISION>
  <NUMERIC_SCALE>-1</NUMERIC_SCALE>
  <EXPRESSION>[Measures].[Broj položenih ispita]/[Measures].[Broj ispita]</EXPRESSION>
  <MEASURE_IS_VISIBLE>true</MEASURE_IS_VISIBLE>
  <MEASURE_NAME_SQL_COLUMN_NAME>Measures:Prolaznost</MEASURE_NAME_SQL_COLUMN_NAME>
  </row>
 */
 /**
  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
  * All Rights Reserved
  * Copyright (C) 2006 Igor Mekterovic
  * All Rights Reserved
  */ 
 /*  Added LanguageChangedListener to implement I18n  */
 
public class MeasureElement implements 
        DimensionTreeElement, 
        QueryElement,
        LanguageChangedListener{

   private String catalogName;
   private String cubeName;
   private String measureName;
   private String measureUniqueName;
   private String measureCaption;
   private String expression;
   private boolean nodeEnabled;


   private XMLADiscoverRestrictions restrictions;
   private XMLADiscoverProperties   properties;

   private ServerMetadata parent;

   static ImageIcon icon;

   static {
       icon = S.getAppIcon("measure.gif");
   }
   public MeasureElement(  ServerMetadata svm
                           , Node rowNode
                           , XMLADiscoverRestrictions _restrictions
                           , XMLADiscoverProperties   _properties) {
    /**
      * Copyright (C) 2006 CINCOM SYSTEMS, INC.
      * All Rights Reserved
      * Copyright (C) 2006 Igor Mekterovic
      * All Rights Reserved
      */ 
     /*adding this class to the list of classes that implement I18n */

       I18n.addOnLanguageChangedListener(this);
      /* end of modification for I18n */
               
      parent = svm;
      nodeEnabled = true;
      NodeList nl = rowNode.getChildNodes();

      for(int i=0; i < nl.getLength(); i++){
         // TO BE DONE: OTHER VARS...

         if (nl.item(i).getNodeType() == 1){
            if (nl.item(i).getNodeName().equals("CATALOG_NAME")) {
               catalogName = DOM.getTextFromDOMElement(nl.item(i));
            }else if (nl.item(i).getNodeName().equals("CUBE_NAME")) {
               cubeName = DOM.getTextFromDOMElement(nl.item(i));
            }else if (nl.item(i).getNodeName().equals("MEASURE_NAME")) {
               measureName = DOM.getTextFromDOMElement(nl.item(i));
            }else if (nl.item(i).getNodeName().equals("MEASURE_UNIQUE_NAME")) {
               measureUniqueName = DOM.getTextFromDOMElement(nl.item(i));
            }else if (nl.item(i).getNodeName().equals("MEASURE_CAPTION")) {
               measureCaption = DOM.getTextFromDOMElement(nl.item(i));
            }else if (nl.item(i).getNodeName().equals("EXPRESSION")) {
               expression = DOM.getTextFromDOMElement(nl.item(i));
            }
         }
      }


   }

   public DimensionTreeElement[] getChildren(boolean noMatterWhat){
      return null;
   }


   public String toString(){
      if (measureCaption == null){
      /**
      * Copyright (C) 2006 CINCOM SYSTEMS, INC.
      * All Rights Reserved
      * Copyright (C) 2006 Igor Mekterovic
      * All Rights Reserved
      */ 
           return I18n.getString("toolTip.notInitialized");
      /* end of modification for I18n */

   }else
         return measureCaption;

   }

   public ImageIcon getIcon(){
      return icon;
   }

   public String getToolTip(){
      if (measureCaption == null){
      /**
      * Copyright (C) 2006 CINCOM SYSTEMS, INC.
      * All Rights Reserved
      * Copyright (C) 2006 Igor Mekterovic
      * All Rights Reserved
      */ 
           return I18n.getString("toolTip.notInitialized");
      }
      else{
            return "<html>"
                + I18n.getString("toolTip.measureName") + measureName
                + "<BR>"+ I18n.getString("toolTip.measureUniqueName") + measureUniqueName
                + "<BR>"+ I18n.getString("toolTip.measureCaption") + measureCaption
                + "<BR>"+ I18n.getString("toolTip.measureExpression") + expression
                + "</html>";
              /* end of modification for I18n */

      }
   }




   public String[] getPopUpActionList(){
      if (isEnabled()){
         return new String[] {
              PopUpActions.SEND_TO_MEASURES
//            , PopUpActions.SEND_TO_ROWS
         };
      }else{
         return null;
      }
   }
   public String getUniqueName(){
      return measureUniqueName;
   }
   public String getCaption(){
      return measureCaption;
   }
   public String getQueryMembersExpression(){
      return measureUniqueName;
   }

   public boolean isEnabled(){
      return nodeEnabled;
   }
   public void setEnabled(boolean newValue){
      nodeEnabled = newValue;
   }
   public String getHierarchyUniqueName(){
//       <MEASURE_UNIQUE_NAME>[Measures].[Prolaznost]</MEASURE_UNIQUE_NAME>
      return measureUniqueName.substring(0, measureUniqueName.lastIndexOf(".") - 1);
//       return measureUniqueName;
   }
   public String getDimensionUniqueName(){
      // this is called from mdxbuildertree
//      S.out("assert:Someone is calling  getDimensionUniqueName() on a MEASURE element!?");
      return "[Measures]";
   }
   public boolean isMeasure(){
      return true;
   }
   public int getChildrenCount(){
      return 0;
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

     /**
      * Copyright (C) 2006 CINCOM SYSTEMS, INC.
      * All Rights Reserved
      * Copyright (C) 2006 Igor Mekterovic
      * All Rights Reserved
      */ 
/**
  * Helper method that is executed when the language is changed
  */

    public void languageChanged(LanguageChangedEvent evt) {
        PopUpActions.popUpCaptions.put(PopUpActions.SEND_TO_MEASURES,I18n.getString("menu.sendToMeasures"));       
    }
  /* end of modification for I18n */

}
