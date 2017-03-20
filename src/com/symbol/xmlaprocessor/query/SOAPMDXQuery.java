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
	.
	Author: Sloan Seaman
	Email : sloan@sgi.net
*/

package com.symbol.xmlaprocessor.query;

import java.io.*;
import java.net.*;
import java.util.Map;
import java.util.HashMap;
import javax.xml.rpc.Service;
import javax.xml.soap.SOAPException;
import org.apache.axis.message.SOAPEnvelope;
import org.apache.axis.message.SOAPBodyElement;
import org.xml.sax.SAXException;
import org.apache.log4j.Logger;
import com.symbol.xmlaprocessor.request.*;
import com.symbol.xmlaprocessor.response.*;

/**
* SOAPMDXQuery is an implementation of MDXQuery that is meant for use with 
* SOAP and Apache's Axis Soap Provider.
* <P>
* In many places it makes Axis specific calls.
* <P>
* This class should never be created on it's own.  Developers should use
* the QueryProvider.getMDXQuery() method to retrieve instances of this object
*
*/
public class SOAPMDXQuery 
	implements MDXQuery
{

	private static final Logger log = Logger.getLogger(SOAPMDXQuery.class);
	private SOAPMetaData metaData = null;

	/**
	* Protected constructor
	*
	* @param _metaData The SOAPMetaData for use by this object
	*/
	protected SOAPMDXQuery(SOAPMetaData _metaData) {
		metaData = _metaData;
	}
	
	/**
	* Returns the metaData for this query
	*
	* @return The metaData for this query
	*/
	public MetaData getMetaData() {
		return metaData;
	}
	
	/**
	* Returns the SOAPEnvelope that is created by this query.
	* <P>
	* This method is not defined in the MDXQuery interface
	*
	* @return The SOAPEnvelope for this query
	* @throws SOAPException Thrown if an arror occurs while building the Envelope
	*/
	public javax.xml.soap.SOAPEnvelope getSOAPEnvelope() 
		throws SOAPException
	{
		return getSOAPEnvelope(metaData.getQuery());
	}
	
	/**
	* Returns the SOAPEnvelope that is created by the specified query.
	* <P>
	* This method is not defined in the MDXQuery interface
	*
	* @param _query The Query to use in the Envelope
	* @return The SOAPEnvelope for this query
	* @throws SOAPException Thrown if an arror occurs while building the Envelope
	*/
	public SOAPEnvelope getSOAPEnvelope(String _query) 
		throws SOAPException
	{
		Execute execute = new Execute(metaData.getNameSpace());
		execute.setCommand(
			new Command(
				new Statement(_query)
			)
		);
		if (metaData.getPropertyList() != null) {
			execute.setProperties(new Properties(metaData.getPropertyList()));
		}
		else {
			log.warn("No properties set. This means that Catalog, DataSource, and Format are not set");
		}
	
		SOAPEnvelope envelope = new SOAPEnvelope();
		envelope.addBodyElement((SOAPBodyElement)execute.toSOAPElement());
		return envelope;
	}
	
	/**
	* Executes the Query and returns the ResultSet
	* 
	* @return The Result set from the query
	* @throws Exception Throws an exeception if an error occurs
	*/
	public Object executeQuery() 
		throws Exception
	{
		return executeQuery(getSOAPEnvelope());
	}

	public Object executeQuery(javax.xml.soap.SOAPEnvelope _envelope) 
		throws SOAPException
	{
		log.debug("executeQuery() invoked");
		
		HttpURLConnection conn = null;
		OutputStreamWriter wr = null;
		InputStream is = null;
		try {
			log.debug("Setting query envelope");
			metaData.setQueryEnvelope(_envelope);
		
			log.debug("Getting Connection objects");
			URL url = new URL(metaData.getTarget());
		
			conn = (HttpURLConnection)url.openConnection();
			conn.setRequestMethod("POST");
			conn.setRequestProperty("SOAPAction", "\""+Execute.SOAP_ACTION+"\"");
			conn.setDoOutput(true);
			
			wr = new OutputStreamWriter(conn.getOutputStream());
			wr.write(_envelope.toString());

			log.debug("Excuting call to SOAP service");
			wr.flush();
			wr.close();

			is = conn.getInputStream(); 
			Object obj = metaData.getResponseProcessor().process(is);
			is.close();
			
			conn.disconnect();
			log.debug("Call complete");

			log.debug("executeQuery() complete");
			return obj;
		}
		catch (ProtocolException pe) {
			log.error("Invalid Protocol", pe);
			throw new SOAPException("Invalid Protocol", pe);
		}
		catch (MalformedURLException mue) {
			log.error("Target URI not valid", mue);
			throw new SOAPException("Target URI not valid", mue);
		}
		catch (IOException ioe) {
			log.error("Error with connection. "+ioe.getMessage());
			if (conn != null) {
				BufferedReader br = null;
				try {
					if (conn.getResponseCode() == conn.HTTP_INTERNAL_ERROR) {
						br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
						StringBuffer sb = new StringBuffer();
						String data = null;
						while ( (data = br.readLine()) != null) {
							sb.append(data);
						}
						br.close();
						log.error("Content of error: "+sb.toString());
						throw new SOAPException("Error with connection. "+sb.toString(), ioe);
					}
				}
				catch (IOException e) {
					log.debug("Error getting error stream", e);
				}
				finally {
					try {
						if (br != null) br.close();
					}
					catch (Exception e) {
						throw new SOAPException("Could not close error stream", e);
					}
				}
			}
			throw new SOAPException("Error with connection. ", ioe);
		}
		catch (Exception e) {
			log.error("Unknown Error", e);
			throw new SOAPException(e);
		}
		finally {
			try {
				if (is != null) is.close();
				if (conn != null) conn.disconnect();
				if (wr != null) wr.close();
			}
			catch (Exception e) {
				throw new SOAPException("Could not close connection");
			}
		}
	}
	
	/**
	* Implementation of toString() to provider more formatted information
	*
	* @return Information about the query
	*/
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("Target  : ").append(metaData.getTarget())
			.append("\n")
			.append("Query   : ").append(metaData.getQuery())
			.append("\n");
		try {
			sb.append("Envelope: ").append(getSOAPEnvelope().toString());
		}
		catch (SOAPException e) {
			StringWriter st = new StringWriter();
			e.printStackTrace(new PrintWriter(st));
			sb.append("Envelope: ").append("Error creating envelepe. ")
				.append(st.toString());
		}
		return sb.toString();
	}
}
