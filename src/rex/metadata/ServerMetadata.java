package rex.metadata;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.String;



import rex.metadata.resultelements.Cell;
import rex.utils.*;
import org.w3c.dom.NodeList;

//import rex.exceptions.RexXMLAException;
import rex.graphics.datasourcetree.elements.*;
import rex.graphics.dimensiontree.elements.DimensionTreeElement;
import rex.graphics.dimensiontree.elements.DimensionElement;
import rex.graphics.dimensiontree.elements.HierarchyElement;
import rex.graphics.dimensiontree.elements.LevelElement;
import org.w3c.dom.Document;

import rex.graphics.dimensiontree.elements.MeasureElement;

import rex.graphics.filtertree.elements.FilterTreeLevelElement;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.parsers.SAXParser;

import rex.xmla.*;

import org.w3c.dom.NamedNodeMap;
import javax.swing.JOptionPane;
import java.awt.Component;


/**
 * <p>Title: WHEX</p>
 * <p>Description: Holds all the metadata for specific server(url)</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author igor
 * @version 1.0
 */

public class ServerMetadata {
   private java.net.URL endpoint;
   private Component panel;
   XMLAPort port;

   public ServerMetadata(String webServiceURL, Component panel) {
      try{
         this.panel = panel;
         endpoint = new java.net.URL(webServiceURL);
      }catch(Exception e){
         S.out("Cannot instantiate ServerMetaData!");
         S.out("Error:" + e);
      }
   }

   public ServerMetadata(String webServiceURL) {
       this(webServiceURL, null);
   }
   public boolean isHTTPS(){
//      S.out(endpoint.getProtocol());
      if (endpoint != null && endpoint.getProtocol().equals("https"))
         return true;
      else
         return false;
   }
   private void init(){
      if (port == null){
         try{
            port = new RexXMLAPort(endpoint);
         }catch(Exception e){
            S.out("Error while initializing service & call...");
            S.out("Error:" + e);
         }
      }
//      S.out("port = " + port + "   hash=" + port.hashCode());
   }

   private boolean errorOccured(Document resultDOM){
      int i, j;
      NodeList errList = resultDOM.getElementsByTagName("Error");
      if (errList != null && errList.getLength() > 0) {

         StringBuffer sb = new StringBuffer("");
         // Mondrian:
         // <Error ErrorCode="rex.mondrian.olap.MondrianException" Description="Mondrian Error:Syntax error at line 11, column 16, token &#39;fsdfsd&#39;" Source="Mondrian" Help=""/>
         // AS:
         // <Error
         //     ErrorCode="2147483653"
         //     Description="An unexpected error has occurred"
         //     Source="XML for Analysis Provider"
         //     HelpFile=""
         //     />

         for (i = 0; i < errList.getLength(); i++) {
            sb.append("\nError occured!");
            NamedNodeMap attrs = errList.item(i).getAttributes();
            for (j = 0; j < attrs.getLength(); j++) {
               sb.append("\n  " + attrs.item(j).getNodeName() + " = " + attrs.item(j).getNodeValue());
            }
         }
        /**
          * Copyright (C) 2006 CINCOM SYSTEMS, INC.
          * All Rights Reserved
          * Copyright (C) 2006 Igor Mekterovic
          * All Rights Reserved
          */ 
        /* implementing localization */
         JOptionPane.showMessageDialog(  panel
                              , I18n.getString("msgText.discoverCall") + sb.toString()
                              ,I18n.getString("msgTitle.discoverCall")
                              , JOptionPane.ERROR_MESSAGE);
           /* end of modification for I18n */

         return true;
      }
      return false;
   }
   

   public boolean isValidUrl() {
       return(!(endpoint == null));
   }
   
   public DataSourceTreeElement[] discoverDataSources(){
      Document result;
      DataSourceTreeElement retVal[] = null;
      if (endpoint == null) return retVal;
      init();
      XMLARestrictions r = XMLAObjectsFactory.newXMLADiscoverRestrictions();
      XMLAProperties   p = XMLAObjectsFactory.newXMLADiscoverProperties();

      try{
         result = port.discoverDataSources(r, p);
         if (errorOccured(result))
            return null;
         NodeList nl  = result.getElementsByTagName("row");
         
         int i;
         if (nl != null){
            if (nl.getLength() > 0) {
               retVal = new DataSourceElement[nl.getLength()];
               for (i = 0; i < nl.getLength(); i++) {
                  retVal[i] = new DataSourceElement(this, nl.item(i));
               }
            }
         }
      }catch(Exception e){
         S.out("Error(discoverDataSources):" + e);
         S.reportError(panel, e);
         //e.printStackTrace();//commented by Prakash
         return null;
      }
      return retVal;
   }


   public  DataSourceTreeElement[] getCatalogList(  XMLADiscoverRestrictions restrictions
                                                 ,  XMLADiscoverProperties   properties){
      Document result;
      DataSourceTreeElement[] retVal = null;
      if (endpoint == null) return retVal;
      init();

      try{

         result = port.getCatalogList(restrictions, properties);
         if (errorOccured(result))
            return null;
         NodeList nl  = result.getElementsByTagName("row");

         int i;
         if (nl != null){
            if (nl.getLength() > 0) {
               retVal = new  CatalogElement[nl.getLength()];
               for (i = 0; i < nl.getLength(); i++) {
                  //S.out("node name= " + nl.item(i).getNodeName() + " node value =" + getTextFromDOMElement(nl.item(i)));
                  retVal[i] = new CatalogElement(this, nl.item(i), properties.getDataSourceInfo());
               }
            }
         }
      }catch(Exception e){
         S.out("Error(getCatalogList):" + e);
         return null;
      }
      return retVal;
   }

   public DataSourceTreeElement[] getCubeList(  XMLADiscoverRestrictions restrictions
                                             ,  XMLADiscoverProperties   properties){
      Document result;
      DataSourceTreeElement[] retVal = null;
      if (endpoint == null) return retVal;
      init();

      try{
         result = port.getCubeList(restrictions, properties);

         if (errorOccured(result))
            return null;

         NodeList nl  = result.getElementsByTagName("row");

         if (nl != null){
            int i;
            if (nl.getLength() > 0) {
               // S.out("cube list length = " + nl.getLength());
               retVal = new DataSourceTreeElement[nl.getLength()];
               for (i = 0; i < nl.getLength(); i++) {
                  //S.out("node name= " + nl.item(i).getNodeName() + " node value =" + rex.utils.DOM.getTextFromDOMElement(nl.item(i)));
                  retVal[i] = new CubeElement(this, nl.item(i), properties.getDataSourceInfo());
               }
            }
         }
      }catch(Exception e){
         S.out("Error(getCubeList):" + e);
         return null;
      }
      return retVal;
   }

   public DimensionTreeElement[] getDimensionList( XMLADiscoverRestrictions restrictions
                                                ,  XMLADiscoverProperties   properties){
      Document result;
      DimensionTreeElement[] retVal = null;
      if (endpoint == null) return retVal;
      init();

      try{
         result = port.getDimensionList(restrictions, properties);

         if (errorOccured(result))
            return null;
         
         NodeList nl = result.getElementsByTagName("row");

         if (nl != null){
            int i;
            if (nl.getLength() > 0) {
               // S.out("cube list length = " + nl.getLength());
               retVal = new DimensionTreeElement[nl.getLength()];
               for (i = 0; i < nl.getLength(); i++) {
                  //S.out("node name= " + nl.item(i).getNodeName() + " node value =" + rex.utils.DOM.getTextFromDOMElement(nl.item(i)));
                  retVal[i] = new DimensionElement(this, nl.item(i), restrictions, properties);
               }
            }
         }
      }catch(Exception e){
         S.out("Error(getDimensionList):" + e + " (" + e.getMessage() + ")");
         return null;
      }
      return retVal;
   }
   public DimensionTreeElement[] getHierarchyList( XMLADiscoverRestrictions restrictions
                                                ,  XMLADiscoverProperties   properties){
      Document result;
      DimensionTreeElement[] retVal = null;
      if (endpoint == null) return retVal;
      init();

      try{
         result = port.getHierarchyList(restrictions, properties);

         if (errorOccured(result))
            return null;

         NodeList nl = result.getElementsByTagName("row");

         if (nl != null){
            int i;
            if (nl.getLength() > 0) {

               retVal = new DimensionTreeElement[nl.getLength()];
               for (i = 0; i < nl.getLength(); i++) {
                  //S.out("node name= " + nl.item(i).getNodeName() + " node value =" + rex.utils.DOM.getTextFromDOMElement(nl.item(i)));
                  retVal[i] = new HierarchyElement(this, nl.item(i), restrictions, properties);
               }
            }
         }
      }catch(Exception e){
         S.out("Error(getHierarchyList):" + e);
         return null;
      }
      return retVal;
   }

   public DimensionTreeElement[] getLevelList(  XMLADiscoverRestrictions restrictions
                                             ,  XMLADiscoverProperties   properties){
      Document result;
      DimensionTreeElement[] retVal = null;
      if (endpoint == null) return retVal;
      init();

      try{
         result = port.getLevelList(restrictions, properties);

         if (errorOccured(result))
            return null;

         NodeList nl = result.getElementsByTagName("row");

         if (nl != null){
            int i;
            if (nl.getLength() > 0) {

               retVal = new DimensionTreeElement[nl.getLength()];
               for (i = 0; i < nl.getLength(); i++) {
                  retVal[i] = new LevelElement(this, nl.item(i), restrictions, properties);
               }
            }
         }
      }catch(Exception e){
         S.out("Error(getLevelList):" + e);
         return null;
      }
      return retVal;
   }
   public DimensionTreeElement[] getFilterLevelList( XMLADiscoverRestrictions restrictions
                                                  ,  XMLADiscoverProperties   properties){
      Document result;
      DimensionTreeElement[] retVal = null;
      if (endpoint == null) return retVal;
      init();

      try{
         result = port.getLevelList(restrictions, properties);

         if (errorOccured(result))
            return null;

         NodeList nl = result.getElementsByTagName("row");

         if (nl != null){
            int i;
            if (nl.getLength() > 0) {
               retVal = new DimensionTreeElement[nl.getLength()];
               for (i = 0; i < nl.getLength(); i++) {
                  retVal[i] = new FilterTreeLevelElement(this, nl.item(i), restrictions, properties);
               }
            }
         }
//         S.out("got levels back... level count=" + retVal.length);
      }catch(Exception e){
         S.out("Error(getFilterLevelList):" + e);
         e.printStackTrace();
         return null;
      }
      return retVal;
   }

   public DimensionTreeElement[] getMeasuresList(  XMLADiscoverRestrictions restrictions
                                                ,  XMLADiscoverProperties   properties){
      Document result;
      DimensionTreeElement[] retVal = null;
      if (endpoint == null) return retVal;
      init();

      try{

         result = port.getMeasureList(restrictions, properties);

         if (errorOccured(result))
            return null;

         NodeList nl = result.getElementsByTagName("row");

         if (nl != null){
            int i;
            if (nl.getLength() > 0) {
               retVal = new DimensionTreeElement[nl.getLength()];
               for (i = 0; i < nl.getLength(); i++) {
                  retVal[i] = new MeasureElement(this, nl.item(i), restrictions, properties);
               }
            }
         }
      }catch(Exception e){
         S.out("Error(getMeasuresList):" + e);
         return null;
      }
      return retVal;
   }


   public DimensionTreeElement[] getDimensionTreeMembersList( XMLADiscoverRestrictions restrictions
                                                           ,  XMLADiscoverProperties   properties){
      return getMembersList(restrictions, properties, false);
   }

   public DimensionTreeElement[] getFilterTreeMembersList( XMLADiscoverRestrictions restrictions
                                                        ,  XMLADiscoverProperties   properties){
      return getMembersList(restrictions, properties, true);
   }

   private DimensionTreeElement[] getMembersList(  XMLADiscoverRestrictions restrictions
                                                ,  XMLADiscoverProperties   properties
                                                ,  boolean spawnFilterTreeMemberElement){
      DimensionTreeElement[] retVal = null;
      if (endpoint == null) return retVal;
      init();

      try{

//         S.out("end discover...");

         // Use the default (non-validating) parser
         SAXParserFactory factory = SAXParserFactory.newInstance();
         SAXMemberHandler handler = new SAXMemberHandler(this, restrictions, properties, spawnFilterTreeMemberElement);
         try {
             // Parse the input
             SAXParser saxParser = factory.newSAXParser();
//             S.out("starting parse...");
             saxParser.parse(port.getMemberListAsStream(restrictions, properties) , handler);
//             for(int g=0; g<retVal.length;g++){
//                S.out(">>>>>>>>>>>>>>" + retVal[g]);
//             }
//             S.out("end parse...getting members");
             retVal = handler.getMembers();
//             S.out("got members: " + handler.getRowCount());
//             return retVal;
//             return (DimensionTreeElement[]) handler.getMembers();
//             S.out("handler.getRowCount() = " + handler.getRowCount() + " handler.getRowCount2()=" + handler.getRowCount2());
         } catch (Throwable t) {
             t.printStackTrace();
         }

      }catch(Exception e){
         S.out("Error(getMembersList2):" + e);
         return null;
      }
      return retVal;
   }


   public Document execute(String mdx, XMLAExecuteProperties properties)throws java.io.IOException{
   	 
    if (endpoint == null) return null;
   	init();
   	try{      	
   		return port.execute( mdx, properties);
      }

      catch(java.io.IOException e){
        S.out("Error executing MDX:" + mdx + "\ne.getMessage() = " + e.getMessage() + "\nStackTrace:" );
        e.printStackTrace();
        throw e;
     }
      
      /**
       *   Copyright (C) 2006 CINCOM SYSTEMS, INC.
       *   All Rights Reserved
       *   Copyright (C) 2006 Igor Mekterovic
       *   All Rights Reserved
       */
      
      /*
       * Catch blocks added by prakash
       * 28th June 06 
       * for catching RexXMLAException and RexXMLADiscoverException.
       * Both Exceptions are throwing IOException by passing error Information. 
       */
      catch(rex.exceptions.RexXMLADiscoverException e){
      	throw new IOException(e.getError());
      }
      catch(rex.exceptions.RexXMLAExecuteException e){
      	throw new IOException(e.getError());
      }
      /*
       * End of catch block addition by prakash
       */
   }


   public static void main(String[] args) {

      ServerMetadata svm = new ServerMetadata("http://localhost:8080/mondrian/xmla");

      XMLAExecuteProperties execPropFoodMart;
      execPropFoodMart= XMLAObjectsFactory.newXMLAExecuteProperties();
      execPropFoodMart.setDataSourceInfo("FoodMartSource");
	  execPropFoodMart.setCatalog("FoodMart");
      String mdxQuerySales="SELECT NON EMPT {[Measures].[Unit Sales]} ON COLUMNS, NON EMPTY {[Time].[1997]} ON ROWS FROM [Sales]";
      try {
      //svm.execute(mdxQuerySales,execPropFoodMart);
      System.out.println((new Cell((((svm.execute(mdxQuerySales,execPropFoodMart)).getElementsByTagName("CellData")).item(0).getChildNodes()).item(1))).getCellValue());
      }
      catch(java.io.IOException io)
	  {
      	System.out.println(io.toString());      	
	  }
  }
}
