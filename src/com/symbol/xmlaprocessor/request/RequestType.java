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

package com.symbol.xmlaprocessor.request;

import javax.xml.soap.SOAPException;

public class RequestType
	extends Request
{

	public static final String LOCAL_NAME = "RequestType";
	
	public static RequestType DISCOVER_DATASOURCES = null;
	public static RequestType DISCOVER_PROPERTIES = null;
	public static RequestType DISCOVER_SCHEMA_ROWSETS = null;
	public static RequestType DISCOVER_ENUMERATORS = null;
	public static RequestType DISCOVER_KEYWORDS = null;
	public static RequestType DISCOVER_LITERALS = null;

	static {
		try {
			DISCOVER_DATASOURCES = new RequestType("DISCOVER_DATASOURCES");
			DISCOVER_PROPERTIES = new RequestType("DISCOVER_PROPERTIES");
			DISCOVER_SCHEMA_ROWSETS = new RequestType("DISCOVER_SCHEMA_ROWSETS");
			DISCOVER_ENUMERATORS = new RequestType("DISCOVER_ENUMERATORS");
			DISCOVER_KEYWORDS = new RequestType("DISCOVER_KEYWORDS");
			DISCOVER_LITERALS = new RequestType("DISCOVER_LITERALS");
		}
		catch (Exception e) { e.printStackTrace(); }
	};
	
	public RequestType() 
		throws SOAPException
	{
		this(null);
	}
	
	public RequestType(String _requestType) 
		throws SOAPException
	{
		super(LOCAL_NAME, _requestType);
	}
}
