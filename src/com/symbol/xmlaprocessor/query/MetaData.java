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

/**
* The MetaData class contains all the relevant information for a Query.
* <P>
* This this architecture has been defined to be very flexible, the MetaData
* only contains the Query itself.
* <P>
* If you are using the SOAPQueryProvider, the metadata that it returns may
* be cast to SOAPMetaData to retrieve futher information
*/
public interface MetaData 
{

	/**
	* Returns the Query that will be used
	*
	* @return The query to use
	*/
	public String getQuery();

}
