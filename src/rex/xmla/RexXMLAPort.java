package rex.xmla;

import java.net.HttpURLConnection;

import rex.exceptions.*;
import rex.graphics.datasourcetree.elements.CatalogElement;
import rex.graphics.datasourcetree.elements.CubeElement;
import rex.graphics.datasourcetree.elements.DataSourceTreeElement;
import rex.metadata.ServerMetadata;
import rex.metadata.resultelements.Cell;
import rex.utils.DOM;
import rex.utils.S;
import java.io.IOException;
import org.w3c.dom.Document;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.HashMap;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.ByteArrayOutputStream;
import java.io.BufferedOutputStream;
import java.io.EOFException;
import java.io.BufferedInputStream;
import java.io.OutputStream;
import javax.xml.parsers.ParserConfigurationException;
import java.util.GregorianCalendar;
import java.io.PrintWriter;
import rex.utils.XMLDocumentWriter;

import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;
import rex.graphics.AuthenticationDialog;
import java.net.PasswordAuthentication;
import java.net.Authenticator;

import org.w3c.dom.Node;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: </p>
 *
 * <p>Company: </p>
 *
 * @author Igor Mekterovic
 * @version 0.3
 */
public class RexXMLAPort implements XMLAPort{
   URL endpoint;
   boolean connOpen;
   HttpURLConnection urlConnection;
//   private XMLAProperties properties;
//   private XMLARestrictions restrictions;
   private String requestType;
   DocumentBuilder builder;
   private boolean verbose;
   private String empty="    "; 
   
   public RexXMLAPort(URL _endpoint) throws ParserConfigurationException{
   
	
      endpoint = _endpoint;
      connOpen = false;
//      S.out("showsoap=" + System.getProperty("showsoap"));
      verbose = System.getProperty("showsoap") == null ? false: true;
      
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

      // INstantiation issueds are caught outside constructor, because other XMLAPort implementations
      // might have other issues (like opening an URL) and all these exceptions are caught in ServerMetadata object
      // or whomeever uses this class

      //try {
         builder = factory.newDocumentBuilder();
//      }
//      catch (ParserConfigurationException pce) {
//         pce.printStackTrace();
//         return;
//      }
   }

   private String getRequestType(){
      return requestType == null ? "DISCOVER_DATASOURCES" : requestType;
   }
   private void setRequestType(String _requestType){
      requestType = _requestType;
   }

   private void setDiscoverMimeHeaders() {
      urlConnection.setRequestProperty("SOAPAction", "\"urn:schemas-microsoft-com:xml-analysis:Discover\"");
      urlConnection.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
      urlConnection.setRequestProperty("Accept", "application/soap+xml, application/dime, multipart/related, text/*");
   }

   private void setExecuteMimeHeaders() {
      urlConnection.setRequestProperty("SOAPAction", "\"urn:schemas-microsoft-com:xml-analysis:Execute\"");
      urlConnection.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
      urlConnection.setRequestProperty("Accept", "application/soap+xml, application/dime, multipart/related, text/*");
   }

    /*
  	 *  "throws RexXMLAException" statement added by prakash  
  	 * Throws RexXMLAException if any problem occured while creating connection with OLAP Server 
  	 */
   private boolean init() throws RexXMLAExecuteException{

      try {
		   Authenticator.setDefault (new Authenticator (){
           protected PasswordAuthentication getPasswordAuthentication() {
           AuthenticationDialog ad = new AuthenticationDialog(null);
           return new PasswordAuthentication (ad.getUsername()
                                               , ad.getPasswordCA());
           }
         });
         urlConnection = (HttpURLConnection) endpoint.openConnection();
         urlConnection.setRequestMethod("POST");
         urlConnection.setDoInput(true);
         urlConnection.setDoOutput(true);         
         connOpen = true;
      } catch (IOException e) {
      	 //e.printStackTrace();//Commented by Prakash
      	 
      	/**
      	 *   Copyright (C) 2006 CINCOM SYSTEMS, INC.
      	 *   All Rights Reserved
      	 *   Copyright (C) 2006 Igor Mekterovic
      	 *   All Rights Reserved
      	 */
      	 
         /*
       	 * Line added by prakash 
       	 * Throw RexXMLAException if not able to make connection with OLAP Server. 
       	 */
         throw new RexXMLAExecuteException(urlConnection.toString()+" is not available or not able to respond");
         /*
          * End of the line
          * By Prakash
          */
         //return false;
      }

      return true;
   }




   /** Encode a string appropriately for XML.
    *
    * Lifted from ApacheSOAP 2.2 (org.apache.soap.Utils)
    *
    * @param orig the String to encode
    * @return a String in which XML special chars are repalced by entities
    */
   public static String xmlEncodeString(String orig)
   {
       if (orig == null)
       {
           return "";
       }

       char[] chars = orig.toCharArray();

       // if the string doesn't have any of the magic characters, leave
       // it alone.
       boolean needsEncoding = false;

       search:
       for(int i = 0; i < chars.length; i++) {
           switch(chars[i]) {
           case '&': case '"': case '\'': case '<': case '>':
               needsEncoding = true;
               break search;
           }
       }

       if (!needsEncoding) return orig;

       StringBuffer strBuf = new StringBuffer();
       for (int i = 0; i < chars.length; i++)
       {
           switch (chars[i])
           {
           case '&'  : strBuf.append("&amp;");
                       break;
           case '\"' : strBuf.append("&quot;");
                       break;
           case '\'' : strBuf.append("&apos;");
                       break;
           case '<'  : strBuf.append("&lt;");
                       break;
           case '\r' : strBuf.append("&#xd;");
                       break;
           case '>'  : strBuf.append("&gt;");
                       break;
           default   :
               if (((int)chars[i]) > 127) {
                       strBuf.append("&#");
                       strBuf.append((int)chars[i]);
                       strBuf.append(";");
               } else {
                       strBuf.append(chars[i]);
               }
           }
       }

       return strBuf.toString();
   }
   
   /**
    *   Copyright (C) 2006 CINCOM SYSTEMS, INC.
    *   All Rights Reserved
    *   Copyright (C) 2006 Igor Mekterovic
    *   All Rights Reserved
    */
   
/*
 * Function added by Prakash.
 * This function will accept Document as a parameter and throw RexXMLAExecuteException or RexXMLADiscoverException if found any errors.   
 */
   private boolean errorOccured(Document resultDOM,String typeOfError,XMLARestrictions restrictions, XMLAProperties properties)throws RexXMLAExecuteException,RexXMLADiscoverException
   {
       final NodeList faultList = resultDOM.getElementsByTagName("SOAP-ENV:Fault");
       if (faultList != null && faultList.getLength() > 0) 
       {
           StringBuffer sBuffer = new StringBuffer("");
           NodeList faultStringList = resultDOM.getElementsByTagName("faultstring");
           if (faultStringList != null && faultStringList.getLength() > 0) 
           {
               sBuffer.append("\n  faultstring = " + DOM.getTextFromDOMElement(faultStringList.item(0)));
           }
           faultStringList = resultDOM.getElementsByTagName("desc");
           if (faultStringList != null && faultStringList.getLength() > 0) 
           {
               sBuffer.append("\n  desc = " + DOM.getTextFromDOMElement(faultStringList.item(0)));
           }

           /*
  			* Initialize and throw RexXMLAException with cause of the error   
  			*/
           if(typeOfError.equals("Execute"))
           {  			
  			throw new RexXMLAExecuteException(sBuffer.toString());
           }
           else
           {
               throw new RexXMLADiscoverException(sBuffer.toString());
           }
       }
       
   		int errListCounter, errAttributeListCounter;
   		final NodeList errList = resultDOM.getElementsByTagName("Error");
   		final NodeList rowList = resultDOM.getElementsByTagName("row");
   		if (errList != null && errList.getLength() > 0) 
   		{
   			StringBuffer sBuffer = new StringBuffer("");
   			for (errListCounter = 0; errListCounter < errList.getLength(); errListCounter++) 
   			{
   				//sBuffer.append("\nError occured!");
   				NamedNodeMap attrs = errList.item(errListCounter).getAttributes();
   				for (errAttributeListCounter = 0; errAttributeListCounter < attrs.getLength(); errAttributeListCounter++) 
   				{
   					sBuffer.append("\n  " + attrs.item(errAttributeListCounter).getNodeName() + " = " + attrs.item(errAttributeListCounter).getNodeValue());
   				}
   			}

   			/*
   			 * Initialize and throw RexXMLAException with cause of the error   
   			 */
   			if(typeOfError.equals("Execute"))
   			{   				
   			 throw new RexXMLAExecuteException(sBuffer.toString());
   			}
   			else
   			{   			
   			 throw new RexXMLADiscoverException(sBuffer.toString());
   			}
   		}
   		
		/*
		 * By Prakash
		 * Check if there is no results of "row" element in soap result.   
		 */
   		if (typeOfError.equals("Discover") && rowList.getLength() == 0) 
   		{  
   			return errorOccuredInDiscover(restrictions,properties);
   		}
   		return false;
   }
   
   /*
    * End of the function
    * Added by Prakash
    */
   
   /**
    *   Copyright (C) 2006 CINCOM SYSTEMS, INC.
    *   All Rights Reserved
    *   Copyright (C) 2006 Igor Mekterovic
    *   All Rights Reserved
    */
   
   /*
    * Function added by Prakash.
    * This function will accept Document as a parameter and throw RexXMLADiscoverException if found any errors.   
    */
   public boolean errorOccuredInDiscover(XMLARestrictions restrictions,XMLAProperties properties)throws RexXMLADiscoverException
   {
   			final String propXML=properties.getPropertyListXML(empty);   			
   			final String restXML=restrictions.getRestrictionListXML(empty);
   			final String dataSourceInfo=propXML.substring((propXML.indexOf("<DataSourceInfo>"))+"<DataSourceInfo>".length(),propXML.indexOf("</DataSourceInfo>"));
   			final String catalog=propXML.substring((propXML.indexOf("<Catalog>"))+"<Catalog>".length(),propXML.indexOf("</Catalog>"));
   			final String cube=restXML.substring((restXML.indexOf("<CUBE_NAME>"))+"<CUBE_NAME>".length(),restXML.indexOf("</CUBE_NAME>"));
    	 	DataSourceTreeElement[] retVal = null;
   			String catalogs[]=null;
   			RexXMLADiscoverProperties tempDiscProp=new RexXMLADiscoverProperties();
   			RexXMLADiscoverRestrictions tempDiscRest=new RexXMLADiscoverRestrictions();
   			try
			{
   				tempDiscProp.setDataSourceInfo(dataSourceInfo);
   				String [] result = getCatalogListAsString(tempDiscRest,tempDiscProp);   				
   				int index,checkCatalog=0;
				for (index = 0; index < result.length; index++) 
   				{   							
   					if(catalog.equals(result[index]))
   					{
   						checkCatalog++;
   					}
   				}
   				if(checkCatalog==0)
   				{
   					throw new RexXMLADiscoverException(catalog+" Catalog not found in URL "+urlConnection.getURL().toString());
   				}
   				else
   				{
   					tempDiscProp.setCatalog(catalog);
   					result = getCubeListAsString(tempDiscRest,tempDiscProp);   					
   					int checkCube=0;
   					for (index = 0; index < result.length; index++) 
   					{  						
   						S.out(result[index]);
   						if(cube.equals(result[index]))
   						{
   							checkCube++;
   						}
   					}
   					if(checkCube==0)
   					{
   						throw new RexXMLADiscoverException(cube+" cube not found in "+catalog+" catalog.");
   					}
   				}
			}
   			catch(Exception e)
			{
   				throw new RexXMLADiscoverException(e.toString());   				
			}   		
   		return false;
   }
   
   /*
    * End of the function
    * Added by Prakash
    */
   
   /* 
    * By Prakash
    * Modified to throw RexXMLAExecuteException and RexXMLADiscoverException.
    */
   public Document execute(String mdx, XMLAExecuteProperties properties) throws RexXMLAExecuteException,RexXMLADiscoverException,IOException{


      if (!init()) 
      {
      	return null;
      }

      StringBuffer request = new StringBuffer("");
      request.append( "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
           +  "\n<SOAP-ENV:Envelope"
           +  "\n  xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\""
           +  "\n  SOAP-ENV:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\">"
           +  "\n  <SOAP-ENV:Body>"
           +  "\n    <Execute  xmlns=\"urn:schemas-microsoft-com:xml-analysis\""
           +  "\n              SOAP-ENV:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\">"
            );
      
         request.append("\n   <Command>"
                      + "\n      <Statement>"
                      + "\n         " + xmlEncodeString(mdx)
                      + "\n      </Statement>"
                      + "\n   </Command>");

         request.append(properties.getXML("    "));

         request.append("\n    </Execute>");
         request.append("\n  </SOAP-ENV:Body>");
         request.append("\n</SOAP-ENV:Envelope>");
         setExecuteMimeHeaders();
         try {
            byte[] b = request.toString().getBytes("UTF8");
            if (verbose){
               S.out("\nSENDING FOLLOWING SOAP MESSAGE (" + GregorianCalendar.getInstance().getTime() + "):\n" + request);              
            }
            writeToStream(b, urlConnection.getOutputStream());
            Document d = builder.parse(urlConnection.getInputStream());
   
            /**
             *   Copyright (C) 2006 CINCOM SYSTEMS, INC.
             *   All Rights Reserved
             *   Copyright (C) 2006 Igor Mekterovic
             *   All Rights Reserved
             */
        
            /**
             * Lines Inserted by Prakash 
             * Redirecting verbose messages to logs 
             */
        	if (verbose){                
                XMLDocumentWriter dw = new XMLDocumentWriter();
                S.out("\nRECEIEVED SOAP MESSAGE (" + GregorianCalendar.getInstance().getTime() + "):\n" );
                dw.write(d.getChildNodes().item(0));
        	}
            
            /*
             * Line added by prakash
             * This function accept Document result and throw RexXMLAException if found any error.
             */
            if (errorOccured(d,"Execute",null,null))
            {
            	return null;
            }
            /*
             * End of the Line 
             * By Prakash
             */
            return d;

         }catch(org.xml.sax.SAXException e){
         	S.out("from SAXParser");
            //e.printStackTrace();//Commented by Prakash
            
         	/**
         	 * Line inserted by Prakash.
         	 * Log SAXException error.
         	 */
         	S.reportError(e);
         }         
         /*
          * catch block added by prakash 
          * catch FIleNotFoundException and Throw RexXMLAException if not able to make connection with OLAP Server. 
          */
         catch(java.io.FileNotFoundException exc)
		 {            
             throw new RexXMLAExecuteException(urlConnection.getURL().toString()+" is not available or not able to respond");
		 }
         /*
          * End of the catch block
          * By Prakash
          */
         urlConnection.disconnect();
         return null;

   }

   /* 
    * By Prakash
    * Modified to throw RexXMLAExecuteException and RexXMLADiscoverException.
    */
   private Document discover(XMLARestrictions restrictions, XMLAProperties properties) throws RexXMLAExecuteException,RexXMLADiscoverException,IOException {

      if (!init())
      {
      	return null;
      }

      StringBuffer request = new StringBuffer("");
      request.append( "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
           +  "\n<SOAP-ENV:Envelope"
           +  "\n  xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\""
           +  "\n  SOAP-ENV:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\">"
           +  "\n  <SOAP-ENV:Body>"
           +  "\n    <Discover xmlns=\"urn:schemas-microsoft-com:xml-analysis\""
           +  "\n              SOAP-ENV:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\">"
            );
         request.append("\n    <RequestType>" + getRequestType() +  "</RequestType>");
         request.append(restrictions.getXML("    "));
         request.append(properties.getXML("    "));
         request.append("\n    </Discover>");
         request.append("\n  </SOAP-ENV:Body>");
         request.append("\n</SOAP-ENV:Envelope>");

         setDiscoverMimeHeaders();
         try {
            byte[] b = request.toString().getBytes("UTF8");
            if (verbose){
               S.out("\nSENDING SOAP MESSAGE (" + GregorianCalendar.getInstance().getTime() + "):\n" + request);
            }
            writeToStream(b, urlConnection.getOutputStream());
            Document d = builder.parse(urlConnection.getInputStream());
            
            /**
             *   Copyright (C) 2006 CINCOM SYSTEMS, INC.
             *   All Rights Reserved
             *   Copyright (C) 2006 Igor Mekterovic
             *   All Rights Reserved
             */
                
        /**
         * Lines Inserted by Prakash 
         * Redirecting verbose messages to logs 
         */
            if (verbose){                
            	XMLDocumentWriter dw = new XMLDocumentWriter();
            	S.out("\nRECEIEVED SOAP MESSAGE (" + GregorianCalendar.getInstance().getTime() + "):\n" );
            	dw.write(d.getChildNodes().item(0));
            }
            
            /*
             * Line added by prakash
             * THis function accept Document result and throw RexXMLAException if found any error.
             */
            if (errorOccured(d,"Discover",restrictions,properties))
            {
            	return null;
            }
            /*
             * End of the Line 
             * By Prakash
             */
            
            return d;

         }  catch(org.xml.sax.SAXException e){
            //e.printStackTrace();//Commented by Prakash
         	/**
         	 * Line inserted by Prakash.
         	 * Log SAXException error.
         	 */
         	S.reportError(e);
         }
         /*
          * catch block added by prakash 
          * catch FIleNotFoundException and Throw RexXMLAException if not able to make connection with OLAP Server. 
          */
         catch(java.io.FileNotFoundException exc)
		 {            
             throw new RexXMLAExecuteException(urlConnection.getURL().toString()+" is not available or not able to respond");
		 }
         /*
          * End of the catch block
          * By Prakash
          */

         urlConnection.disconnect();
         return null;

   }

   /* 
    * By Prakash
    * Modified to throw RexXMLAExecuteException and RexXMLADiscoverException.
    */
   private InputStream discoverAsStream(XMLARestrictions restrictions, XMLAProperties properties) throws RexXMLAExecuteException,RexXMLADiscoverException,IOException{

      if (!init()) 
      {
      	return null;
      }

      StringBuffer request = new StringBuffer("");
      request.append( "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
           +  "\n<SOAP-ENV:Envelope"
           +  "\n  xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\""
           +  "\n  SOAP-ENV:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\">"
           +  "\n  <SOAP-ENV:Body>"
           +  "\n    <Discover xmlns=\"urn:schemas-microsoft-com:xml-analysis\""
           +  "\n              SOAP-ENV:encodingStyle=\"http://schemas.xmlsoap.org/soap/encoding/\">"
            );
         request.append("\n    <RequestType>" + getRequestType() +  "</RequestType>");
         request.append(restrictions.getXML("    "));
         request.append(properties.getXML("    "));
         request.append("\n    </Discover>");
         request.append("\n  </SOAP-ENV:Body>");
         request.append("\n</SOAP-ENV:Envelope>");

         setDiscoverMimeHeaders();

//         System.out.println("RexXMLAPort.discoverAsStream()#####################################################");
//         System.out.println(request.toString());
//         System.out.println("RexXMLAPort.discoverAsStream()#####################################################");

         byte[] b = request.toString().getBytes("UTF8");
         if (verbose){
            S.out("SENDING SOAP MESSAGE (" + GregorianCalendar.getInstance().getTime() + "):\n" + request);
         }
         writeToStream(b, urlConnection.getOutputStream());
         return urlConnection.getInputStream();
   }

   /* 
    * By Prakash
    * Modified to throw RexXMLAExecuteException and RexXMLADiscoverException.
    */
   public Document discoverDataSources(XMLARestrictions restrictions, XMLAProperties properties)throws RexXMLAExecuteException,RexXMLADiscoverException,IOException {
      setRequestType("DISCOVER_DATASOURCES");
      return discover(restrictions, properties);
   }

   /**
    *   Copyright (C) 2006 CINCOM SYSTEMS, INC.
    *   All Rights Reserved
    *   Copyright (C) 2006 Igor Mekterovic
    *   All Rights Reserved
    */
   
   /*
    * Function added by Prakash.
    * This function returns an Array of Data Sources.   
    */
   public String[] discoverDataSourcesAsString(XMLARestrictions restrictions, XMLAProperties properties)throws RexXMLAExecuteException,RexXMLADiscoverException,IOException {
   	String dataSourceName[]=null;
    setRequestType("DISCOVER_DATASOURCES");
    final Document result=discover(restrictions, properties);
    NodeList nlist  = result.getElementsByTagName("row");
	int nlistCounter;
	if ((nlist != null) && (nlist.getLength() > 0)) 
	{
		dataSourceName= new String[nlist.getLength()];	      	
		for (nlistCounter = 0; nlistCounter < nlist.getLength(); nlistCounter++) 
	    {	               	
	    	NodeList nodelist=nlist.item(nlistCounter).getChildNodes();
	    	for(int j=0; j < nodelist.getLength(); j++)
	      	{
	    		if ((nodelist.item(j).getNodeType() == 1) && (nodelist.item(j).getNodeName().equals("DataSourceName"))) 
	      		{
	      			dataSourceName[nlistCounter] = DOM.getTextFromDOMElement(nodelist.item(j));
	      		}	      				
	      	}
	    }    
	}
	return dataSourceName;
 }
// End of the Function
   
   /* 
    * By Prakash
    * Modified to throw RexXMLAExecuteException and RexXMLADiscoverException.
    */
   public Document getCatalogList(XMLARestrictions restrictions, XMLAProperties properties) throws RexXMLAExecuteException,RexXMLADiscoverException,IOException{
      setRequestType("DBSCHEMA_CATALOGS");
      return discover(restrictions, properties);
   }
   
   /**
    *   Copyright (C) 2006 CINCOM SYSTEMS, INC.
    *   All Rights Reserved
    *   Copyright (C) 2006 Igor Mekterovic
    *   All Rights Reserved
    */
   
   /*
    * Function added by Prakash.
    * This function returns an Array of Catalogs.   
    */
   public String [] getCatalogListAsString(XMLARestrictions restrictions, XMLAProperties properties) throws RexXMLAExecuteException,RexXMLADiscoverException,IOException{
   	String catalogName []=null;
    setRequestType("DBSCHEMA_CATALOGS");
    final Document result=discover(restrictions, properties);
    final NodeList nlist  = result.getElementsByTagName("row");
	int nlistCounter;
	if ((nlist != null) && (nlist.getLength() > 0)) 
	{
		catalogName= new String[nlist.getLength()];				      				
		for (nlistCounter = 0; nlistCounter < nlist.getLength(); nlistCounter++) 
		{	               	
			NodeList nodelist=nlist.item(nlistCounter).getChildNodes();
			for(int j=0; j < nodelist.getLength(); j++)
			{
				if ((nodelist.item(j).getNodeType() == 1) && (nodelist.item(j).getNodeName().equals("CATALOG_NAME"))) 
				{
					catalogName[nlistCounter] = DOM.getTextFromDOMElement(nodelist.item(j));
				}
	      	}
		}
	}
	return catalogName;
 }

   /* 
    * By Prakash
    * Modified to throw RexXMLAExecuteException and RexXMLADiscoverException.
    */
   public Document getCubeList(XMLARestrictions restrictions, XMLAProperties properties) throws RexXMLAExecuteException,RexXMLADiscoverException,IOException{
      setRequestType("MDSCHEMA_CUBES");
      return discover(restrictions, properties);
   }

   /**
    *   Copyright (C) 2006 CINCOM SYSTEMS, INC.
    *   All Rights Reserved
    *   Copyright (C) 2006 Igor Mekterovic
    *   All Rights Reserved
    */
   
   public String [] getCubeListAsString(XMLARestrictions restrictions, XMLAProperties properties) throws RexXMLAExecuteException,RexXMLADiscoverException,IOException{
   	String cubeName[]=null;
    setRequestType("MDSCHEMA_CUBES");
    Document result=discover(restrictions, properties);
    final NodeList nlist  = result.getElementsByTagName("row");
	int nlistCounter;
	if ((nlist != null) && (nlist.getLength() > 0)) 
	{
		cubeName= new String[nlist.getLength()];	
		for (nlistCounter = 0; nlistCounter < nlist.getLength(); nlistCounter++) 
		{	               	
			final NodeList nodelist=nlist.item(nlistCounter).getChildNodes();
			for(int j=0; j < nodelist.getLength(); j++)
			{
				if ((nodelist.item(j).getNodeType() == 1) && (nodelist.item(j).getNodeName().equals("CUBE_NAME"))) 
				{
					cubeName[nlistCounter] = DOM.getTextFromDOMElement(nodelist.item(j));
				}
			}
		}                       	
	}			
	return cubeName;
   }
   
   /* 
    * By Prakash
    * Modified to throw RexXMLAExecuteException and RexXMLADiscoverException.
    */
   public Document getDimensionList(XMLARestrictions restrictions, XMLAProperties properties) throws RexXMLAExecuteException,RexXMLADiscoverException,IOException{
      setRequestType("MDSCHEMA_DIMENSIONS");
      return discover(restrictions, properties);
   }

   /* 
    * By Prakash
    * Modified to throw RexXMLAExecuteException and RexXMLADiscoverException.
    */
   public Document getHierarchyList(XMLARestrictions restrictions, XMLAProperties properties) throws RexXMLAExecuteException,RexXMLADiscoverException,IOException{
      setRequestType("MDSCHEMA_HIERARCHIES");
      return discover(restrictions, properties);
   }

   /* 
    * By Prakash
    * Modified to throw RexXMLAExecuteException and RexXMLADiscoverException.
    */
   public Document getLevelList(XMLARestrictions restrictions, XMLAProperties properties) throws RexXMLAExecuteException,RexXMLADiscoverException,IOException{
      setRequestType("MDSCHEMA_LEVELS");
      return discover(restrictions, properties);
   }

   /* 
    * By Prakash
    * Modified to throw RexXMLAExecuteException and RexXMLADiscoverException.
    */
   public Document getMeasureList(XMLARestrictions restrictions, XMLAProperties properties) throws RexXMLAExecuteException,RexXMLADiscoverException,IOException{
      setRequestType("MDSCHEMA_MEASURES");
      return discover(restrictions, properties);
   }

   /* 
    * By Prakash
    * Modified to throw RexXMLAExecuteException and RexXMLADiscoverException.
    */
   public Document getMemberList(XMLARestrictions restrictions, XMLAProperties properties) throws RexXMLAExecuteException,RexXMLADiscoverException,IOException{
      setRequestType("MDSCHEMA_MEMBERS");
      return discover(restrictions, properties);
   }

   /* 
    * By Prakash
    * Modified to throw RexXMLAExecuteException and RexXMLADiscoverException.
    */
   public InputStream getMemberListAsStream(XMLARestrictions restrictions, XMLAProperties properties) throws RexXMLAExecuteException,RexXMLADiscoverException,IOException{
      setRequestType("MDSCHEMA_MEMBERS");
      return discoverAsStream(restrictions, properties);
   }


   private byte[] readFromStream(InputStream urlInputStream) throws IOException {
      // ovo je kod skoro uzet iz PipeThreada - objasnjenja su tamo
      BufferedInputStream in = null;
      ByteArrayOutputStream bout = null;
      try {
         in = new BufferedInputStream(urlInputStream);
         bout = new ByteArrayOutputStream();
         int counter = 0;
         byte b = 0;
         int len = 0;
         while (len != -1) {
            len = in.available();
            if (len == 0) {
               b = (byte) in.read();
               if (b != -1) {
                  bout.write(b);
                  counter = 0;
               }
               else {
                  //bout.write(b);
                  if (counter > 100) {
                     throw new EOFException();
                  }
                  counter++;
               }
            }
            else {
               len = in.available();
               byte[] bp = new byte[len];
               in.read(bp);
               bout.write(bp);
               counter = 0;
            }
         }
      }
      catch (EOFException e) {
         // to je u redu, gotovi smo
     	/**
     	 * Line inserted by Prakash.
     	 * Log EOFException error.
     	 */
     	S.reportError(e);
      }
      finally {
         try {
            if (in != null) {
               in.close();
            }
            if (urlInputStream != null) {
               urlInputStream.close();
            }
         }
         catch (IOException e) {
         	/**
         	 * Line inserted by Prakash.
         	 * Log IOException error.
         	 */
         	S.reportError(e);
         }
      }
      return bout.toByteArray();
    }

   private void writeToStream(byte[] data, OutputStream urlOutputStream) throws IOException{
      BufferedOutputStream os = null;
      try {
         os = new BufferedOutputStream(urlOutputStream);
         os.write(data);
         os.flush();
      }
      finally {
         try {
            if (os != null) {
               os.close();
            }
            if (urlOutputStream != null) {
               urlOutputStream.close();
            }
         }
         catch (IOException e) {
      
         	/**
         	 *   Copyright (C) 2006 CINCOM SYSTEMS, INC.
         	 *   All Rights Reserved
         	 *   Copyright (C) 2006 Igor Mekterovic
         	 *   All Rights Reserved
         	 */
         	
         	/**
         	 * Line inserted by Prakash.
         	 * Log IOException error.
         	 */
         	S.reportError(e);
         }

      }
   }
   public static void main(String[] args) {


         XMLADiscoverRestrictions rest = XMLAObjectsFactory.newXMLADiscoverRestrictions();
         XMLADiscoverProperties   prop = XMLAObjectsFactory.newXMLADiscoverProperties();

   	RexXMLAPort svm=null;
   	XMLAExecuteProperties execPropFoodMart;
    execPropFoodMart= XMLAObjectsFactory.newXMLAExecuteProperties();
    execPropFoodMart.setDataSourceInfo("FoodMartSource");
    execPropFoodMart.setCatalog("FoodMart");
    
    ((RexXMLADiscoverProperties)prop).setDataSourceInfo("FoodMartSource");
    ((RexXMLADiscoverProperties)prop).setCatalog("FoodMart");
    ((RexXMLADiscoverRestrictions)rest).setCubeName("Sale");
    ((RexXMLADiscoverProperties)prop).setFormat("Tabular");
    ((RexXMLADiscoverProperties)prop).setContent("SchemaData");
	  
    String mdxQuerySales="SELECT NON EMPTY {[Measures].[Unit Sales]} ON COLUMNS, NON EMPTY {[Time].[1997]} ON ROWS FROM [Sales]";
   	try {
   	svm = new RexXMLAPort(new URL("http://localhost:8080/mondrian/xmla"));
    }
    catch(ParserConfigurationException pce)
	{
    	S.reportError(pce);
	}
    catch(MalformedURLException mfe)
	{
    	S.reportError(mfe);
	}
    try {
    svm.execute(mdxQuerySales,execPropFoodMart);
    //System.out.println((new Cell((((svm.execute(mdxQuerySales,execPropFoodMart)).getElementsByTagName("CellData")).item(0).getChildNodes()).item(1))).getCellValue());
   // svm.getDimensionList(r,p);
    }
    catch(RexXMLAExecuteException io)
	{
    	S.reportError(io);
	}
    catch(RexXMLADiscoverException io)
	{
    	S.reportError(io);      	
	}
    catch(IOException io)
	{
    	S.reportError(io);      	
	}
   }
}

