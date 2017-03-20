/*
    XMLA Processor is free software; you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation; either version 2 of the License, or
    (at your option) any later version.

    XMLA Processor is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with XMLA Processor; if not, write to the Free Software
    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
	
	XMLA Processor was funded by Symbol Corporation.
	Please support the Symbol Corporation whenever possible.
	
	Author: Sloan Seaman
	Email : sloan@sgi.net
*/

package com.symbol.xmlaprocessor.query;

import java.util.Map;
import java.util.HashMap;
import java.util.Properties;
import javax.xml.soap.SOAPException;
import com.symbol.xmlaprocessor.request.*;
import com.symbol.xmlaprocessor.response.ResponseProcessor;

/**
* SOAPQueryProvider is the default provider to use when wishing to 
* execute MDX queryies against an XML/A SOAP service.
*
* SOAPQueryProvider should be instantiated by using the Class.forName() method
* like so: <BR>
* <CODE><PRE>
*	Class.forName("com.symbol.xmlaprocessor.query.SOAPQueryProvider");
* </PRE></CODE>
* <P>
* The class will then register itself with the QueryProviderManager for use
* within the application.
* <P>
* Before the class may be used it will need to be configured only one time.
* To do this, first retrieve an instance of the object from the QueryProviderManger
* like so:<BR>
* <CODE><PRE>
*	QueryProviderManager.getQueryProvider("com.symbol.xmlaprocessor.query.SOAPQueryProvider");
* </PRE></CODE>
* <BR>
* Note: If you do not wish to always have to specify the name of the QueryProvider
* to retrieve, you can set the DefaultQueryProviderName like so:<BR>
* <CODE><PRE>
*	QueryProviderManager.SetDefaultQueryProviderName("com.symbol.xmlaprocessor.query.SOAPQueryProvider");
* </PRE></CODE>
* <BR>
* From then on you will only need to use <CODE>getQueryProvider()</CODE> 
* in your code to get an instance of the same QueryProvider
* <P>
* You now need to configure the SOAPQueryProvider so that it knows how to 
* go about its job.
* <P>
* To set the configuration use the <CODE>setConfiguration(PropertyList _props)</CODE>
* method.
* <P>
* This currently isn't very scalable and only addresses a few of the propList
* that can be set.<BR>
* If you wish to use a propList not supported buy <CODE>setConfiguration()</CODE>
* then you should create your own com.symbol.xmlaprocessor.request.PropertyList object
* and set it via the <CODE>setPropertyList()</CODE> method.
* <P>
* The following PropertyList may be set via setConfiguration():<BR>
* <DL>com.symbol.xmlaprocessor.query.CATALOG
* 	<DD>The name of the Catalog to access</DD>
* </DL>
* <DL>com.symbol.xmlaprocessor.query.DATASOURCE_PROVIDER
* 	<DD>The DataSourceInfo Provider to use</DD>
* </DL>
* <DL>com.symbol.xmlaprocessor.query.DATASOURCE_SOURCE
* 	<DD>The DataSourceInfo Data Source to use</DD>
* </DL>
* <DL>com.symbol.xmlaprocessor.query.FORMAT
* 	<DD>The FORMAT to use</DD>
* </DL>
* <DL>com.symbol.xmlaprocessor.query.NAMESPACE
* 	<DD>The NAMESPACE to use</DD>
* </DL>
* <DL>com.symbol.xmlaprocessor.query.TARGET_URI
* 	<DD>The Target URI to use</DD>
* </DL>
* <P>
* Example:<BR>
* <CODE><PRE>
*	QueryProvider sqp = QueryProviderManager.getQueryProvider();
*	PropertyList props = new PropertyList(); // you could use a file here as well
*	props.setProperty(SOAPQueryProvider.TARGET_URI, "http://localhost:80/xmla/msxisapi.dll");
*	props.setProperty(SOAPQueryProvider.DATASOURCE_PROVIDER, "MSOLAP");
*	props.setProperty(SOAPQueryProvider.DATASOURCE_SOURCE, "OLAP-SERVER");
*	props.setProperty(SOAPQueryProvider.CATALOG, "pss");
*	props.setProperty(SOAPQueryProvider.FORMAT, "TABULAR");
*	props.setProperty(SOAPQueryProvider.NAMESPACE, "ns1");
*	sqp.setConfiguration(props);
* </CODE></PRE>
* <BR>
* Would create the following envelope:<BR>
* <CODE><PRE>
*	&lt;soapenv:Envelope xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/" 
*	xmlns:xsd="http://www.w3.org/2001/XMLSchema" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
*		&lt;soapenv:Body&gt;
*			&lt;ns1:Execute xmlns:ns1="urn:schemas-microsoft-com:xml-analysis"&gt;
*				&lt;ns1:Command&gt;
*					&lt;ns1:Statement&gt;stmt would be here&lt;/ns1:Statement&gt;
*				&lt;/ns1:Command&gt;
*				&lt;ns1:PropertyList&gt;
*					&lt;ns1:PropertyList&gt;
*						&lt;ns1:DataSourceInfo&gt;Provider=MSOLAP;Data Source=OLAP-SERVER&lt;/ns1:DataSourceInfo&gt;
*						&lt;ns1:Catalog&gt;pss&lt;/ns1:Catalog&gt;
*						&lt;ns1:Format&gt;TABULAR&lt;/ns1:Format&gt;
*					&lt;/ns1:PropertyList&gt;
*				&lt;/ns1:PropertyList&gt;
*			&lt;/ns1:Execute&gt;
*		&lt;/soapenv:Body&gt;
*	&lt;/soapenv:Envelope&gt;
* </CODE></PRE>
* <P>
* You would then use either the <CODE>getMDXQuery()</CODE> or
* <CODE>getPreparedMDXQuery()</CODE> methods to do the actual query
* <P>
* Furthermore you can specify the ProcessResponse object to use (via
* <CODE>setProcessResponse()</CODE> to set your own way of processing
* the response from the XML/A service
*
* @see com.symbol.xmlaprocessor.test.TestQueryProvider
*/
public class SOAPQueryProvider
	implements QueryProvider
{
	public static final String NAMESPACE = "com.symbol.xmlaprocessor.query.NAMESPACE";
	public static final String TARGET_URI = "com.symbol.xmlaprocessor.query.TARGET_URI";
	public static final String CATALOG = "com.symbol.xmlaprocessor.query.CATALOG";
	public static final String DATASOURCE_PROVIDER = "com.symbol.xmlaprocessor.query.DATASOURCE_PROVIDER";
	public static final String DATASOURCE_SOURCE = "com.symbol.xmlaprocessor.query.DATASOURCE_SOURCE";
	public static final String FORMAT = "com.symbol.xmlaprocessor.query.FORMAT";
	public static final String CONTENT = "com.symbol.xmlaprocessor.query.CONTENT";
	
	private String nameSpace = null;
	private String target = null;
	private PropertyList propList = null;
	private ResponseProcessor responseProcessor = null;

	/**
	* Static initializer that registers the class with the QueryProviderManager.
	* <P>
	* The one issue with this that I haven't figured out how to get around
	* is that you will only be able to put this class in the QueryProviderManager
	* once since it uses its Class Name to register itself.
	* <P>
	* This means that if you did another Class.forName() at a later date,
	* the new class would overwrite the existing one (unless the QueryProviderManager
	* has been frozen)
	*/
	static {
		try {
			QueryProviderManager.registerQueryProvider(
				SOAPQueryProvider.class.getName(), SOAPQueryProvider.class);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	};
	
	/**
	* Public constructor.
	* <P>
	* Required so that the QueryProviderManager can make a new instance of the object
	*/
	public SOAPQueryProvider() { }

	/**
	* Sets the configuration.
	* <P>
	* If any required propList are not found, a NullPointerException
	* will be thrown (on purpose)
	*
	* @param _props The PropertyList object to use
	* @throws NullPointerException Thrown if a require property is not present
	*/
	public void setConfiguration(Properties _props) 
		throws NullPointerException
	{
		try {
			propList = new PropertyList();

			if (_props.getProperty(TARGET_URI) == null)
				throw new NullPointerException("Required property "+TARGET_URI+" not found");
			setTarget(_props.getProperty(TARGET_URI));
		
			if (_props.getProperty(NAMESPACE) == null)
				throw new NullPointerException("Required property "+NAMESPACE+" not found");
			setNameSpace(_props.getProperty(NAMESPACE));
			
			if (_props.getProperty(CATALOG) != null)
				addProperty(new Catalog(_props.getProperty(CATALOG)));
			
			if (_props.getProperty(FORMAT) != null)
				addProperty(new Format(_props.getProperty(FORMAT)));

			if (_props.getProperty(CONTENT) != null)
				addProperty(new Content(_props.getProperty(CONTENT)));
			
			if ( (_props.getProperty(DATASOURCE_PROVIDER) != null) &&
				(_props.getProperty(DATASOURCE_SOURCE) != null) ) 
			{
				addProperty(new DataSourceInfo(
					_props.getProperty(DATASOURCE_PROVIDER),
					_props.getProperty(DATASOURCE_SOURCE) ));
			}

		}
		catch (SOAPException e) {
			throw new NullPointerException("Error setting property. "+e.getMessage());
		}
	}
	
	/**
	* Allows the manual addition of propList to the alreadying existing
	* propList
	*
	* @param _property The Property to add
	*/
	public void addProperty(Property _prop) 
		throws SOAPException
	{
		if (propList == null) propList = new PropertyList();
		propList.addChildElement(_prop);
	}

	/**
	* Allows the manual setting of the name space
	*
	* @param _nameSpace The name space to use
	*/
	public void setNameSpace(String _nameSpace) {
		nameSpace = _nameSpace;
	}
	
	/**
	* Allows the manual setting of the ResponseProcessor object.
	* <P>
	* If one is not set, the default one will be used.
	* <P>
	* Change this only if you want to really have control over how
	* the response from the query is processed
	* 
	* @param _pr The ResponseProcessor object to use when processing
	* the response from the query
	*/
	public void setResponseProcessor(ResponseProcessor _pr) {
		responseProcessor = _pr;
	}
	
	/**
	* Allows the manual setting of the Propeties Object
	* <P>
	* This is one way to get around any limitations of the
	* PropertyList tag being set using the Catalog, DataSourceInfo, 
	* and Format parameters in setConfiguration()
	*
	* @param _props The PropertyList object to use
	*/
	public void setPropertyList(PropertyList _propList) {
		propList = _propList;
	}
	
	/**
	* Allows the manual setting of the target uri
	*
	* @param _target The target uri to use
	*/
	public void setTarget(String _target) {
		target = _target;
	}
	
    /**
	* Returns a new MDXQuery object with the initialized to the specific MDXQuery
	*
	* @return The MDXQuery object
	*/
	public MDXQuery createMDXQuery(String _query) {
		SOAPMetaData metaData = new SOAPMetaData();
		metaData.setNameSpace(nameSpace);
		metaData.setResponseProcessor(responseProcessor);
		metaData.setPropertyList(propList);
		metaData.setQuery(_query);
		metaData.setTarget(target);
		return (MDXQuery)new SOAPMDXQuery(metaData);
	}

	/**
	* Returns a new PreparedMDXQUery object with the initialized to the specific 
	* PreparedMDXQuery
	*
	* @return The PreparedMDXQuery object
	*/
	public PreparedMDXQuery createPreparedMDXQuery(String _query) {
		SOAPMetaData metaData = new SOAPMetaData();
		metaData.setNameSpace(nameSpace);
		metaData.setResponseProcessor(responseProcessor);
		metaData.setPropertyList(propList);
		metaData.setQuery(_query);
		metaData.setTarget(target);
		return (PreparedMDXQuery)new SOAPPreparedMDXQuery(metaData);
	}


}

