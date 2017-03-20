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

package com.symbol.xmlaprocessor.response.resultset;

import com.symbol.xmlaprocessor.response.*;

/**
* Measure represents the FACT DATA returned from the Query
*/
public class ResultSetMeasure
	implements Measure
{

	private Object value = null;

	/**
	* Default constructor
	*/
	protected ResultSetMeasure() {}
	
	/**
	* Create a new Measure with the specified value
	*
	* @param _value The value of the measure
	*/
	protected ResultSetMeasure(Object _value) {
		setValue(_value);
	}

	/**
	* Returns the value of the Measure
	*
	* @return The value of the measure
	*/
	public Object getValue() {
		return value;
	}
	
	/**
	* Returns a string containing the value of the Measure
	*
	* @return String
	*/
	public String toString() {
		if (value != null) return value.toString();
		return "";
	}
	
	/**
	* Sets the value of the Measure
	*
	* @param _obj The value of the measure
	*/
	protected void setValue(Object _obj) {
		value = _obj;
	}

}
