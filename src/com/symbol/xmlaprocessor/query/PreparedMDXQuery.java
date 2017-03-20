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
* The PreparedMDXQuery interface is much like the java.sql.PreparedStatement
* object.
* <P>
* The Query may be set using the <code>createPreparedMDXQuert</code> and may
* contain N number of ?'s that my be set at a later time (before the call
* to executeQuery()).
* <P>
* Example: <BR>
* <CODE><PRE>
*	PreparedMDXQuery query = sqp.createPreparedMDXQuery("SELECT  [MEASURES].MEMBERS ON COLUMNS, "+
*		"descendants([CUSTOMERS].[All CUSTOMERS].[SNS CUSTOMER] ,[cust id]) ON ROWS "+
*		"FROM EM2 "+
*		"WHERE ([TIME].[All TIME].[?].[Week ?]) ");
*
*	query.setObject(2, "28");
*	query.setObject(1, "2001");
* </PRE></CODE>
*
*/
public interface PreparedMDXQuery 
	extends MDXQuery
{

	/**
	* Clears all the parameters that we set in the Query
	* <P>
	* (I.E. the values that replaces the ?'s)
	*/
	public void clearParameters();
	
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
	public void setObject(int _position, Object _obj);
}
