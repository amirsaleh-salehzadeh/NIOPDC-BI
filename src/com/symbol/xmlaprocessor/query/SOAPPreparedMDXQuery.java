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

import java.io.PrintWriter;
import java.io.StringWriter;
import javax.xml.soap.*;
import com.symbol.xmlaprocessor.request.*;
import com.symbol.xmlaprocessor.response.*;

/**
* SOAPPreparedMDXQuery is an implementation of PreparedMDXQuery that is meant for use with 
* SOAP and Apache's Axis Soap Provider.
* <P>
* In many places it makes Axis specific calls.
* <P>
* This class should never be created on it's own.  Developers should use
* the QueryProvider.getPreparedMDXQuery() method to retrieve instances of this object
*
*/
public class SOAPPreparedMDXQuery 
	extends SOAPMDXQuery
	implements PreparedMDXQuery
{

	private Object[] values = null;

	/**
	* Protected constructor
	*
	* @param _metaData The SOAPMetaData for use by this object
	*/
	protected SOAPPreparedMDXQuery(SOAPMetaData _metaData) 
	{
		super(_metaData);
		
		// determines the size of the vector based on the number of ?'s
		int i = 0;
		int curPos = _metaData.getQuery().indexOf("?");
		while (curPos != -1) {
			curPos = _metaData.getQuery().indexOf("?", curPos+1);
			i++; 
		}
		values = new Object[i];
	}
	
	/**
	* Clears all the parameters that we set in the Query
	* <P>
	* (I.E. the values that replaces the ?'s)
	*/
	public void clearParameters() {
		for (int i = 0; i<values.length; i++) {
			values[i] = null;
		}
	}
	
	/**
	* Returns the Query after the ?'s have been replaced by their proper
	* values.
	* <P>
	* This may be called at anytime to see how the replacements are coming along.
	* <P>
	* This method is not defined in the MDXQuery interface
	* 
	* @return The query after being parsed with the current values from setObject
	*/
	public String getParsedQuery() 
		throws NullPointerException
	{
		return buildQuery( getMetaData().getQuery() );
	}
	
	/**
	* Returns the SOAPEnvelope that is created by this query.
	* <P>
	* This method is not defined in the MDXQuery interface
	*
	* @return The SOAPEnvelope for this query
	* @throws SOAPException Thrown if an arror occurs while building the Envelope
	*/
	public SOAPEnvelope getSOAPEnvelope() 
		throws SOAPException
	{
		return getSOAPEnvelope(getParsedQuery());
	}
	
	/**
	* Sets an object to the specific ?
	* <P>
	* Position is not based on the ? location in the Query string itself,
	* but instead is based on the number of ?'s in the query
	* <P>
	* Just as with java.sql.PreparedStatement, the initial position of the
	* first ? in your query is at position 1, not 0.
	*
	* @param _position The position of the ? to replace
	* @param _obj The object to put in the position
	*/
	public void setObject(int _position, Object _obj) {
		values[_position-1] = _obj;
	}
	
	/**
	* Builds the query based on the values set from calls to setObject()
	* and from the Query string.
	*
	* @param _query The Query to use
	* @return The new query with the ?'s replaces with their proper values
	*/
	protected String buildQuery(String _query) {
		String tmpQuery = _query;
		int i = 0;
		int curPos = tmpQuery.indexOf("?");
		while (curPos != -1) {
			tmpQuery = tmpQuery.substring(0, curPos)+
				values[i].toString()+
				tmpQuery.substring( curPos+1 );
			curPos = tmpQuery.indexOf("?", curPos+(values[i].toString().length()));
			i++;
		}
		return tmpQuery;
	}

	/**
	* Implementation of toString() to provider more formatted information
	*
	* @return Information about the query
	*/
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("Target  : ").append( ((SOAPMetaData)getMetaData()).getTarget() )
			.append("\n")
			.append("Query   : ").append(getMetaData().getQuery())
			.append("\n");
		for( int i=0; i<values.length;i++) {
			sb.append( "Object  : Position ").append(i+1)
				.append(", Value ").append(values[i].toString())
				.append("\n");
		}
		
		sb.append("Parsed Query: ").append(getParsedQuery()).append("\n");
		
		try {
			sb.append("Envelope: ").append(getSOAPEnvelope().toString());
		}
		catch (SOAPException e) {
			StringWriter st = new StringWriter();
			e.printStackTrace(new PrintWriter(st));
			sb.append("Envelope: ").append("Error creating envelope. ")
				.append(st.toString());
		}
		return sb.toString();
	}
}
