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

package com.symbol.xmlaprocessor.response.scan;

import java.util.*;
import com.symbol.xmlaprocessor.response.*;


/**
* The Dimension class represents the Dimensions that may exist within a 
* Cube
*/
public class ScannedDimension
	implements Dimension
{

	private List members = null;
	private String name = null;
	private Map lookupTable = null;

	/**
	* Constructs a new Dimension
	*
	* @param _name The name of the Dimension
	* @param _members The List to use for storing Members
	* @param _lookupTable The LookupTable to use
	*/
	protected ScannedDimension(String _name, List _members, Map _lookupTable) {
		lookupTable = _lookupTable;
		setMembers(_members);
		setName(_name);
	}

	/**
	* Returns the name of the Dimension
	*
	* @return The name of the Dimension
	*/
	public String getName() {
		return name;
	}
	
	/**
	* Returns the Members of this Dimension that is specified by the name
	*
	* @param _name The name of the Member
	* @return The Member (if any)
	*/
	public Member getMemberByName(String _name) {
		int pos = getLookupPosition(_name);
		if (pos != -1) return (Member)members.get(pos);
		return null;
	}
	
	/**
	* Returns a List of all the Members of this Dimension
	*
	* @return The List of Members
	*/
	public List getMembers() {
		return members;
	}
	
	/**
	* Returns the name of the Dimension
	* 
	* @return String
	*/
	public String toString() {
		return name;
	}
	
	/**
	* Adds a Member to the list of members in this Dimension
	*
	* @param _member The Member to add
	*/
	protected void addMember(Member _member){
		lookupTable.put(_member.getName().toUpperCase(), new Integer(members.size()));
		members.add(_member);
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
	* Sets the List of Members to the passed in list
	*
	* @param _list The List of members
	*/
	protected void setMembers(List _list) {
		members = _list;
		for (int i=0;i<_list.size();i++) {
			lookupTable.put( ((Member)_list.get(i)).getName().toUpperCase(), new Integer(i));
		}
	}
	
	/**
	* Sets the name of the Dimension
	*
	* @param _str The name of the Dimension
	*/
	protected void setName(String _str) {
		name = _str;
	}
	
}
