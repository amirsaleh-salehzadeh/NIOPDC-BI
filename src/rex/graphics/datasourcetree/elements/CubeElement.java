package rex.graphics.datasourcetree.elements;


import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import rex.utils.*;
import rex.metadata.ServerMetadata;
import javax.swing.ImageIcon;

/**
 * <p>Title: WHEX</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author igor
 * @version 1.0
 */

/*
  <CATALOG_NAME>Test</CATALOG_NAME>
  <CUBE_NAME>Ispit</CUBE_NAME>
  <CUBE_TYPE>CUBE</CUBE_TYPE>
  <LAST_SCHEMA_UPDATE>2003-09-17T11:23:21</LAST_SCHEMA_UPDATE>
  <LAST_DATA_UPDATE>2003-09-17T11:23:21</LAST_DATA_UPDATE>
  <IS_DRILLTHROUGH_ENABLED>false</IS_DRILLTHROUGH_ENABLED>
  <IS_LINKABLE>true</IS_LINKABLE>
  <IS_WRITE_ENABLED>false</IS_WRITE_ENABLED>
  <IS_SQL_ENABLED>true</IS_SQL_ENABLED>

*/
public class CubeElement  implements DataSourceTreeElement{
   private String dataSourceInfo;
   private String catalogName;
   private String cubeName;
   private String cubeType;
   private String lastSchemaUpdate;
   private String lastDataUpdate;
   private String isDrillthroughEnabled;
   private String isLinkable;
   private String isWriteEnabled;
   private String isSQLEnabled;
   private ServerMetadata parent;

   static ImageIcon icon;
   static {
      icon = S.getAppIcon("cube.gif");
   }

/*
   public CubeElement(ServerMetadata svm) {
      parent = svm;
   }
*/
   public CubeElement(ServerMetadata svm, Node rowNode, String myDataSourceInfo) {
      parent = svm;
      dataSourceInfo = myDataSourceInfo;
      NodeList nl = rowNode.getChildNodes();

      for(int i=0; i < nl.getLength(); i++){
         //S.out("nl.getNodeType = " + nl.item(i).getNodeType() + " node name = " +  nl.item(i).getNodeName() + " text = " + DOM.getTextFromDOMElement(nl.item(i)));
         if (nl.item(i).getNodeType() == 1){
            if (nl.item(i).getNodeName().equals("CATALOG_NAME")) {
               catalogName = DOM.getTextFromDOMElement(nl.item(i));
            }else if (nl.item(i).getNodeName().equals("CUBE_NAME")) {
               cubeName = DOM.getTextFromDOMElement(nl.item(i));
            }else if (nl.item(i).getNodeName().equals("CUBE_TYPE")) {
               cubeType = DOM.getTextFromDOMElement(nl.item(i));
            }else if (nl.item(i).getNodeName().equals("LAST_SCHEMA_UPDATE")) {
               lastSchemaUpdate = DOM.getTextFromDOMElement(nl.item(i));
            }else if (nl.item(i).getNodeName().equals("LAST_DATA_UPDATE")) {
               lastDataUpdate = DOM.getTextFromDOMElement(nl.item(i));
            }else if (nl.item(i).getNodeName().equals("IS_DRILLTHROUGH_ENABLED")) {
               isDrillthroughEnabled = DOM.getTextFromDOMElement(nl.item(i));
            }else if (nl.item(i).getNodeName().equals("IS_LINKABLE")) {
               isLinkable = DOM.getTextFromDOMElement(nl.item(i));
            }else if (nl.item(i).getNodeName().equals("IS_WRITE_ENABLED")) {
               isWriteEnabled = DOM.getTextFromDOMElement(nl.item(i));
            }else if (nl.item(i).getNodeName().equals("IS_SQL_ENABLED")) {
               isSQLEnabled = DOM.getTextFromDOMElement(nl.item(i));
            }
         }
      }
   }
   public DataSourceTreeElement[] getChildren(){
      return null;
   }

 /**
  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
  * All Rights Reserved
  * Copyright (C) 2006 Igor Mekterovic
  * All Rights Reserved
  */ 
   /* implementing localization */
   public String toString(){
      if (cubeName == null){
         return I18n.getString("toolTip.notInitialized");
           /* end of modification for I18n */

      }
      else{
         return "" + cubeName;
      }
   }

   public ImageIcon getIcon(){
      return icon;
   }

   public String getToolTip(){
      if (catalogName == null){
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
           return "" + cubeName
            +   I18n.getString("toolTip.type") + cubeType
            + I18n.getString("toolTip.lastSchemaUpdate")+ lastSchemaUpdate
            + I18n.getString("toolTip.lastDataUpdate") + lastDataUpdate;
             /* end of modification for I18n */

      }
   }
   
   public String[] getPopUpActionList(){
    /**
      * Copyright (C) 2006 CINCOM SYSTEMS, INC.
      * All Rights Reserved
      * Copyright (C) 2006 Igor Mekterovic
      * All Rights Reserved
      */ 
       /* implementing localization */
       return new String[] {I18n.getString("menu.explore1"), I18n.getString("menu.explore2"), I18n.getString("menu.explore3")}; //sbalda
         /* end of modification for I18n */

   }
   public ServerMetadata getServerMetaData(){
      return parent;
   }

   public String getDataSourceInfo(){
      return dataSourceInfo;
   }
   public String getCubeName(){
      return cubeName;
   }
   public String getCatalogName(){
      return catalogName;
   }
}
