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

package com.symbol.xmlaprocessor.response;

import java.io.InputStream;

/**
* All classes that implement this interface are in charge
* of processing the returned XML from a Query to XML/A
*
* @author Sloan Seaman (sloan@sgi.net)
*/
public interface ResponseProcessor
{
	/**
	* Processes the information from the service and returns a
	* result.
	*
	* @param The InputStream from the service
	* @return The results of the processing
	*/
	public Object process(InputStream _Stream)
		throws Exception;

}
