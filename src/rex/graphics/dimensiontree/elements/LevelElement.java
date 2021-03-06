package rex.graphics.dimensiontree.elements;

import javax.swing.ImageIcon;
import rex.metadata.ServerMetadata;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import rex.utils.*;
import rex.metadata.QueryElement;
import rex.xmla.XMLADiscoverRestrictions;
import rex.xmla.XMLADiscoverProperties;
import rex.xmla.RexXMLADiscoverRestrictions;
import rex.xmla.XMLAObjectsFactory;


/**
 * <p>Title: WHEX</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author igor
 * @version 1.0
 */

public class LevelElement implements 
        DimensionTreeElement, 
        QueryElement,
        LanguageChangedListener{

   private String catalogName;
   private String schemaName;
   private String cubeName;
   private String dimensionUniqueName;
   private String hierarchyUniqueName;
   private String levelName;
   private String levelUniqueName;
   private String levelGuid;
   private String levelCaption;
   private int levelNumber;
   private int levelCardinality;
   private int levelType;
   private String description;
   private int customRollupSettings;
   private int levelUniqueSettings;
   private boolean levelIsVisible;
   private String levelOrderingProperty;
   private int levelDbtype;
   private String levelMasterUniqueName;
   private String levelNameSqlColumnName;
   private String levelKeySqlColumnName;
   private String levelUniqueNameSqlColumnName;
   private boolean nodeEnabled;


   private XMLADiscoverRestrictions restrictions;
   private XMLADiscoverProperties   properties;

   private ServerMetadata parent;

   static int LEVEL_CARDINALITY_NOT_INITIALIZED = -1;
   static ImageIcon[] icon;
   static {
       icon = new ImageIcon[]{
            S.getAppIcon("level_1.gif")
          , S.getAppIcon("level_2.gif")
          , S.getAppIcon("level_3.gif")
          , S.getAppIcon("level_4.gif")
          , S.getAppIcon("level_5.gif")
          , S.getAppIcon("level_6.gif")
          , S.getAppIcon("level_7.gif")
          , S.getAppIcon("level_8.gif")
          , S.getAppIcon("level_9.gif")
          , S.getAppIcon("level_10.gif")
       };
    
   }
   public LevelElement(  ServerMetadata svm
                           , Node rowNode
                           , XMLADiscoverRestrictions _restrictions
                           , XMLADiscoverProperties   _properties) {
       /*adding this class to the list of classes that implement I18n */
    /**
      * Copyright (C) 2006 CINCOM SYSTEMS, INC.
      * All Rights Reserved
      * Copyright (C) 2006 Igor Mekterovic
      * All Rights Reserved
      */ 
      I18n.addOnLanguageChangedListener(this);
  /* end of modification for I18n */

                           
      restrictions = XMLAObjectsFactory.newXMLADiscoverRestrictions();

      restrictions.setCatalog(_restrictions.getCatalog());
      restrictions.setCubeName(_restrictions.getCubeName());


      properties = _properties;


      levelCardinality = LEVEL_CARDINALITY_NOT_INITIALIZED;

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
            }else if (nl.item(i).getNodeName().equals("DIMENSION_UNIQUE_NAME")) {
               dimensionUniqueName = DOM.getTextFromDOMElement(nl.item(i));
            }else if (nl.item(i).getNodeName().equals("HIERARCHY_UNIQUE_NAME")) {
               hierarchyUniqueName = DOM.getTextFromDOMElement(nl.item(i));
            }else if (nl.item(i).getNodeName().equals("LEVEL_NAME")) {
               levelName = DOM.getTextFromDOMElement(nl.item(i));
            }else if (nl.item(i).getNodeName().equals("LEVEL_UNIQUE_NAME")) {
               levelUniqueName = DOM.getTextFromDOMElement(nl.item(i));
            }else if (nl.item(i).getNodeName().equals("LEVEL_NUMBER")) {
               levelNumber = Integer.parseInt(DOM.getTextFromDOMElement(nl.item(i)));
            }else if (nl.item(i).getNodeName().equals("LEVEL_CAPTION")) {
               levelCaption = DOM.getTextFromDOMElement(nl.item(i));
            }else if (nl.item(i).getNodeName().equals("LEVEL_CARDINALITY")) {
               levelCardinality = Integer.parseInt(DOM.getTextFromDOMElement(nl.item(i)));
            }

         }
      }
      if (!(levelUniqueName.startsWith("[Measures]"))){
         restrictions.setLevelUniqueName(levelUniqueName);
      }
//      S.out("levelUniqueName:" + levelUniqueName
//            + " dimensionUniqueName:"+ dimensionUniqueName
//            + " hierarchyUniqueName:" + hierarchyUniqueName);

   }

   public DimensionTreeElement[] getChildren(boolean noMatterWhat){
      DimensionTreeElement[] list = null;
      if (levelUniqueName.startsWith("[Measures]")){
         list = parent.getMeasuresList(restrictions, properties);
      }else if (noMatterWhat){
         list = parent.getDimensionTreeMembersList(restrictions, properties);
      }
      if (list != null)
         levelCardinality = list.length;
      return list;
   }


   public String toString(){
      if (levelName == null){
          /**
  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
  * All Rights Reserved
  * Copyright (C) 2006 Igor Mekterovic
  * All Rights Reserved
  */ /* implementing localization */
          return I18n.getString("toolTip.notInitialized");
            /* end of modification for I18n */

      }
      else
         return levelName;

   }

   public ImageIcon getIcon(){
      return icon[levelNumber];
   }

   public String getToolTip(){
     if (levelName == null){
    /**
      * Copyright (C) 2006 CINCOM SYSTEMS, INC.
      * All Rights Reserved
      * Copyright (C) 2006 Igor Mekterovic
      * All Rights Reserved
      */ 
       /* implementing localization */
          return I18n.getString("toolTip.notInitialized");
      } else{
         return "<html>"+ I18n.getString("toolTip.levelUniqueName") + levelUniqueName
                   +  "<br>"+ I18n.getString("toolTip.dimensionUniqueName")  + dimensionUniqueName
                   + "<br>"+ I18n.getString("toolTip.hierarchyUniqueName")    + hierarchyUniqueName
                   + "<br>"+ I18n.getString("toolTip.childrenCount") + getChildrenCount()
                   + "</html>";
   /* end of modification for I18n */

      }

   }




   public String[] getPopUpActionList(){
      if (isEnabled()){
         return new String[] {
              PopUpActions.SEND_TO_COLUMNS
            , PopUpActions.SEND_TO_ROWS
            , PopUpActions.SEND_TO_PAGES
         };
      }else{
         return null;
      }
   }
   public String getUniqueName(){
      return levelUniqueName;
   }
   public String getCaption(){
      return levelCaption;
   }
   public String getQueryMembersExpression(){
      return levelUniqueName + ".Members";
   }

   public boolean isEnabled(){
      return nodeEnabled;
   }
   public void setEnabled(boolean newValue){
      nodeEnabled = newValue;
   }
   public String getHierarchyUniqueName(){
      return hierarchyUniqueName;
   }
   public String getDimensionUniqueName(){
      return dimensionUniqueName;
   }
   public boolean isMeasure(){
      return false;
   }
   public int getChildrenCount(){
      if (levelCardinality == LEVEL_CARDINALITY_NOT_INITIALIZED){
         // WARNING: this could be expensive...
         // Damn, this IS expensive, especially for Mondrian, so I'd rather comment it:
//         DimensionTreeElement[] list = this.getChildren(true);
//         levelCardinality = list == null ? 0 : list.length;
         // ...And LIE about it rather than waiting for 30 seconds:
         return 2;
        // but I'm not going to set levelCardinality, maybe someone will call getChildren and then I'll update it
      }
      return levelCardinality;
   }

// these methods are needed when FilterTreeLevelElement extends
// this and then sets it's own limitations
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
        PopUpActions.popUpCaptions.put(PopUpActions.FLATTEN_DIMENSIONS,I18n.getString("menu.flattenDims"));
        PopUpActions.popUpCaptions.put(PopUpActions.GROUP_HIERARCHIES_BY_DIMENSION,I18n.getString("menu.groupDim"));
        PopUpActions.popUpCaptions.put(PopUpActions.SEND_TO_ROWS,I18n.getString("menu.sendToRows"));
        PopUpActions.popUpCaptions.put(PopUpActions.SEND_TO_COLUMNS,I18n.getString("menu.sendToCols"));
        PopUpActions.popUpCaptions.put(PopUpActions.SEND_TO_MEASURES,I18n.getString("menu.sendToMeasures"));
        PopUpActions.popUpCaptions.put(PopUpActions.SEND_TO_PAGES,I18n.getString("menu.sendToPages"));
       
    }
  /* end of modification for I18n */



}
