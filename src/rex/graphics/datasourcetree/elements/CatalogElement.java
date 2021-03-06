package rex.graphics.datasourcetree.elements;


import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import rex.utils.*;
import rex.metadata.ServerMetadata;
import javax.swing.ImageIcon;
import rex.xmla.XMLADiscoverRestrictions;
import rex.xmla.XMLADiscoverProperties;
import rex.xmla.XMLAObjectsFactory;

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
  <CATALOG_NAME>FoodMart 2000</CATALOG_NAME>
  <DESCRIPTION />
  <ROLES />
  <DATE_MODIFIED>2003-04-09T00:01:10</DATE_MODIFIED>
  </row>
*/

public class CatalogElement implements DataSourceTreeElement{
   private String dataSourceInfo;
   private String catalogName;
   private String description;
   private String roles;
   private String dateModified;
   private ServerMetadata parent;

   static ImageIcon icon;
   static {
      icon = S.getAppIcon("catalog.gif");
   }
/*
   public CatalogElement(ServerMetadata svm, String myDataSourceInfo) {
      dataSourceInfo = myDataSourceInfo;
      parent = svm;
   }
*/
   public CatalogElement(ServerMetadata svm, Node rowNode, String myDataSourceInfo) {
      parent = svm;
      dataSourceInfo = myDataSourceInfo;

      NodeList nl = rowNode.getChildNodes();

      for(int i=0; i < nl.getLength(); i++){
         if (nl.item(i).getNodeType() == 1){
            if (nl.item(i).getNodeName().equals("CATALOG_NAME")) {
               catalogName = DOM.getTextFromDOMElement(nl.item(i));
            }else if (nl.item(i).getNodeName().equals("DESCRIPTION")) {
               description = DOM.getTextFromDOMElement(nl.item(i));
            }else if (nl.item(i).getNodeName().equals("ROLES")) {
               roles = DOM.getTextFromDOMElement(nl.item(i));
            }else if (nl.item(i).getNodeName().equals("DATE_MODIFIED")) {
               dateModified = DOM.getTextFromDOMElement(nl.item(i));
            }
         }
      }
   }
   public DataSourceTreeElement[] getChildren(){
      XMLADiscoverRestrictions restrictions = XMLAObjectsFactory.newXMLADiscoverRestrictions();
      XMLADiscoverProperties   properties   = XMLAObjectsFactory.newXMLADiscoverProperties();


      properties.setDataSourceInfo(dataSourceInfo);

      properties.setCatalog(catalogName);
      properties.setFormat("Tabular");
      properties.setContent("SchemaData");


      //S.out("callint parent.getCubeList with catalogName = " + catalogName);
      return parent.getCubeList(restrictions, properties);
   }

   public String toString(){
      if (catalogName == null){
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
      else{
         return "" + catalogName;
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
          return "" + catalogName
            + I18n.getString("toolTip.dateModified") + dateModified
            + I18n.getString("toolTip.desc") + description;
      }
  /* end of modification for I18n */

   }
   public String[] getPopUpActionList(){
      return null; //new String[] {"Refresh"};
   }
   public ServerMetadata getServerMetaData(){
      return parent;
   }

   public String getDataSourceInfo(){
      return dataSourceInfo;
   }

}
