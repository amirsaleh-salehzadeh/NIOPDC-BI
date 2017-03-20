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

import java.net.URL;
import javax.xml.soap.SOAPEnvelope;
import com.symbol.xmlaprocessor.request.*;
import com.symbol.xmlaprocessor.response.ResponseProcessor;

/**
* Implementation of MetaData that has SOAP Specific information
*/
public class SOAPMetaData 
	implements MetaData
{

	private String query = null;
	private String nameSpace = null;
	private String target = null;
	private PropertyList propList = null;
	private ResponseProcessor responseProcessor = null;
	private SOAPEnvelope queryEnvelope = null;
	private SOAPEnvelope responseEnvelope = null;

	/**
	* Protected Constructor
	*/
	protected SOAPMetaData() {};
	
	/**
	* Returns the Query
	*
	* @return The Query
	*/
	public String getQuery() { return query; }
	
	/**
	* Returns the Query Envelope that was used for the query
	*
	* @return The Query Envelope that was used
	*/
	public SOAPEnvelope getQueryEnvelope() { return queryEnvelope; }
	
	/**
	* Returns the namespace of the query
	*
	* @return The namespace of the query
	*/
	public String getNameSpace() { return nameSpace; }
	
	/**
	* Returns the ResponseProcessor object to use when processing the
	* response from the XML/A service
	*
	* @return The ResponseProcessor object
	*/
	public ResponseProcessor getResponseProcessor() { return responseProcessor; }
	
	/**
	* Returns the property list of the query
	*
	* @return The property list of the query
	*/
	public PropertyList getPropertyList() { return propList; }
	
	/**
	* Returns the Target URI of the query
	*
	* @return The Target URI of the quert
	*/
	public String getTarget() { return target;}
	
	protected void setQuery(String _query) {
		query = _query;
	}
	
	protected void setQueryEnvelope(SOAPEnvelope _env) {
		queryEnvelope = _env;
	}
	
	protected void setNameSpace(String _nameSpace) {
		nameSpace = _nameSpace;
	}
	
	protected void setResponseProcessor(ResponseProcessor _pr) {
		responseProcessor = _pr;
	}
	
	protected void setPropertyList(PropertyList _propList) {
		propList = _propList;
	}
	
	protected void setTarget(String _target) {
		target = _target;
	}
}
