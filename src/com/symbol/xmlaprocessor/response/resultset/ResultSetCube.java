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

import java.util.*;
import com.symbol.xmlaprocessor.response.*;

/**
* A Cube is the highest level object returned from an MDX query
*
* Everything you could every want to know is retrieved though the cube
*/
public class ResultSetCube
	implements Cube
{

	private List dimensions = null;
	private String name = null;
	private Map lookupTable = null;
	private ResultSet resultSet = null;

	/**
	* Creates a new cube
	*
	* @param _name The name of the cube
	* @param _dimensions The List to use for dimensions
	* @param _lookupTable The LookupTable to use for finding dimensions
	*/
	protected ResultSetCube(String _name, List _dimensions, Map _lookupTable) {
		lookupTable = _lookupTable;
		setDimensions(_dimensions);
		setName(_name);
	}
	
	/**
	* Returns the dimension specified by the specific name
	*
	* @param _name The name of the dimension to retrieve
	* @return The Dimension (if any)
	*/
	public Dimension getDimensionByName(String _name) {
		int pos = getLookupPosition(_name);
		if (pos != -1) return (Dimension)dimensions.get(pos);
		return null;
	}
	
	/**
	* Returns a list of all the Dimensions in this cube
	*
	* @return A list of Dimensions
	*/
	public List getDimensions() {
		return dimensions;
	}
	
	/**
	* Returns the name of the cube
	*
	* @return The name of the cube
	*/
	public String getName() {
		return name;
	}
	
	/**
	* Returns the Result Set to use to go through the results
	*
	* @return ResultSet
	*/
	public ResultSet getResultSet() {
		return resultSet;
	}
	
	/**
	* Returns the name of the Cube
	*
	* @return String
	*/
	public String toString() {
		return name;
	}
	
	/**
	* Adds a dimension to the dimension list
	*
	* @param _dimension The dimension to add
	*/
	protected void addDimension(Dimension _dimension) {
		dimensions.add(_dimension);
		lookupTable.put(_dimension.getName().toUpperCase(), new Integer(dimensions.size()-1));
	}
	
	/**
	* Finds the position of the string in the lookup map
	*
	* @param _str The string to get the position of
	*/
	protected int getLookupPosition(String _str) {
		if (lookupTable.containsKey(_str.toUpperCase())) {
			return ((Integer)lookupTable.get(_str.toUpperCase())).intValue();
		}
		return -1;
	}		
	
	/**
	* Sets the dimensions list to the provided list
	*
	* @param _list The new dimension list
	*/
	protected void setDimensions(List _list) {
		dimensions = _list;
		for (int i=0;i<_list.size();i++) {
			lookupTable.put( ((Dimension)_list.get(i)).getName().toUpperCase() , new Integer(i));
		}
	}
	
	/**
	* Sets the name of the cube
	*
	* @param _str The name of the cube
	*/
	protected void setName(String _str) {
		name = _str;
	}
	
	/**
	* Sets the ResultSet to return
	*
	* @param _rs The resultset
	*/
	protected void setResultSet(ResultSet _rs) {
		resultSet = _rs;
	}
	
	/**
	* Dumps the entire contents of the Cube to a String...
	* <P>
	* This could take a while
	*/
	public String dumpDimensions() {
		StringBuffer sb = new StringBuffer("Dimensions Dump:\n");
		for (Iterator dims = dimensions.iterator(); dims.hasNext();) {
			Dimension dim = (Dimension)dims.next();
			sb.append("Dimension:"+dim.getName()).append("\n");
			for (Iterator mems = dim.getMembers().iterator(); mems.hasNext();) {
				dumpMembers(sb, (Member)mems.next(), "\t");
			}
			sb.append("\n");
		}
		return sb.toString();
	}
	
	private void dumpMembers(StringBuffer _sb, Member _mem, String _tabs) {
		_sb.append("Mem:"+_tabs.toString()+_mem.getName()).append("\n");
		for (Iterator mems = _mem.getChildMembers().iterator(); mems.hasNext();) {
			dumpMembers(_sb, (Member)mems.next(), _tabs+"\t");
		}
	}
}
