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

import com.symbol.xmlaprocessor.response.ResponseProcessor;
import java.util.Properties;

/**
* Generic interface to retrieve a Query provider
*/
public interface QueryProvider {

    /**
	* Returns a new MDXQuery object with the initialized to the specific MDXQuery
	*
	* @return The MDXQuery object
	*/
	public MDXQuery createMDXQuery(String _query);

	/**
	* Returns a new PreparedMDXQUery object with the initialized to the specific 
	* PreparedMDXQuery
	*
	* @return The PreparedMDXQuery object
	*/
	public PreparedMDXQuery createPreparedMDXQuery(String _query);

	/**
	* Configures the Connection for usage
	*
	* @param _props The Properties to use
	* @throws NullPointerException Thrown if a require property is not present
	*/
	public void setConfiguration(Properties _props)
		throws NullPointerException;
		
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
	public void setResponseProcessor(ResponseProcessor _pr);
}

