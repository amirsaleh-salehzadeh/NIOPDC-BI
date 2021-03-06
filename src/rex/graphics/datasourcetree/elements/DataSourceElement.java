package rex.graphics.datasourcetree.elements;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import rex.utils.*;
import rex.metadata.ServerMetadata;
import javax.swing.ImageIcon;
import rex.xmla.*;
/**
 * <p>Title: WHEX</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author igor
 * @version 1.0
 */

/*
 <xsd:element name="DataSourceName" type="xsd:string" sql:field="DataSourceName" minOccurs="0" />
  <xsd:element name="DataSourceDescription" type="xsd:string" sql:field="DataSourceDescription" minOccurs="0" />
  <xsd:element name="URL" type="xsd:string" sql:field="URL" minOccurs="0" />
  <xsd:element name="DataSourceInfo" type="xsd:string" sql:field="DataSourceInfo" minOccurs="0" />
  <xsd:element name="ProviderName" type="xsd:string" sql:field="ProviderName" minOccurs="0" />
- <xsd:element name="ProviderType" sql:field="ProviderType" minOccurs="0">
- <xsd:complexType>
- <xsd:sequence maxOccurs="unbounded" minOccurs="0">
  <xsd:any processContents="lax" maxOccurs="unbounded" />
  </xsd:sequence>
  </xsd:complexType>
  </xsd:element>
  <xsd:element name="AuthenticationMode" type="xsd:string" sql:field="AuthenticationMode" minOccurs="0" />

 */


public class DataSourceElement  implements DataSourceTreeElement{
   private String dataSourceName;
   private String dataSourceDescription;
   private String URL;
   private String providerName;
   private String providerType;
   private String authenticationMode;
   private ServerMetadata parent;

   static ImageIcon icon
                  , secureicon;
   static {
      icon = S.getAppIcon("server.gif");
      secureicon = S.getAppIcon("secureserver.gif");
   }
/*
   public DataSourceElement(ServerMetadata svm) {
      parent = svm;
   }
*/
   public DataSourceElement(ServerMetadata svm, Node rowNode) {
      parent = svm;

      NodeList nl = rowNode.getChildNodes();

      for(int i=0; i < nl.getLength(); i++){
         if (nl.item(i).getNodeType() == 1){
            //S.out(nl.item(i).getNodeName());
            if (nl.item(i).getNodeName().equals("DataSourceName")) {
               dataSourceName = DOM.getTextFromDOMElement(nl.item(i));
            }else if (nl.item(i).getNodeName().equals("DataSourceDescription")) {
               dataSourceDescription = DOM.getTextFromDOMElement(nl.item(i));
            }else if (nl.item(i).getNodeName().equals("URL")) {
               URL = DOM.getTextFromDOMElement(nl.item(i));
            }else if (nl.item(i).getNodeName().equals("ProviderName")) {
               providerName = DOM.getTextFromDOMElement(nl.item(i));
            }else if (nl.item(i).getNodeName().equals("ProviderType")) {
               providerType = DOM.getTextFromDOMElement(nl.item(i));
            }else if (nl.item(i).getNodeName().equals("AuthenticationMode")) {
               authenticationMode = DOM.getTextFromDOMElement(nl.item(i));
            }
         }
      }
   }

   public DataSourceTreeElement[] getChildren(){
      XMLADiscoverRestrictions restrictions = XMLAObjectsFactory.newXMLADiscoverRestrictions();
      XMLADiscoverProperties   properties   = XMLAObjectsFactory.newXMLADiscoverProperties();


      properties.setDataSourceInfo(dataSourceName);
/*
      pl.setCatalog("Foodmart 2000");
      pl.setFormat("Tabular");
      pl.setContent("SchemaData");
*/

      return parent.getCatalogList(restrictions, properties);
   }

   public String toString(){
      if (dataSourceName == null){
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
         return "" + dataSourceName;
   }

   public String getDataSourceName(){
      return dataSourceName;
   }

   public ImageIcon getIcon(){
      if (parent.isHTTPS() )
         return secureicon;
      else
         return icon;

   }

   public String getToolTip(){
      if (dataSourceName == null){
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
         return "<html>" + dataSourceName
                   + "<br>"+ I18n.getString("toolTip.url")+ URL
                   + "<br>"+ I18n.getString("toolTip.desc") + dataSourceDescription
                   + "<br>"+ I18n.getString("toolTip.providerName") + providerName
                   + "<br>"+ I18n.getString("toolTip.providerType")+ providerType
                   + "<br>"+ I18n.getString("toolTip.authMode") + authenticationMode
                   + "</html>";
          /* end of modification for I18n */

      }
   }
   public String[] getPopUpActionList(){
      return null; //new String[] {"Refresh"};
   }

   public ServerMetadata getServerMetaData(){
      return parent;
   }

   public String getDataSourceInfo(){
      return dataSourceName;
   }


}
