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
import org.apache.log4j.Logger;
import com.symbol.xmlaprocessor.response.*;


/**
* Each member represents a specific Member within a Dimesion.
* <P>
* In the case of a Member of a Dimension having Children that are then in
* another Dimension, the Child Member is a seperate object from the Member
* that is places in the child Dimension.  In english: <BR>
* Say you have a Dimension (Measures) with the following members:<BR>
* <LI>Nbr Units</LI>
* <LI>Net Dlrs</LI>
* <LI>Pref Discounts</LI>
* <P>
* and there is another Dimesion (Time) with the following members:<BR>
* <LI>2001</LI>
* <LI>2002</LI>
* <LI>2003</LI>
* <P>
* and the Members of the Time dimension can be thought of as children of the
* Measures dimension.
* <P>
* The objects in the childMembers() of the Members from the Measures dimension
* will NOT be the same as the objects in the Time dimension.
* <P>
* Technically this is incorrect, but it is done this way because it was the 
* only way I could get it to work.  Go figure.
* <P>
* <B>Accessed</B>
* <P>
* When the getMeasure(Member) method is called, the passed in member should 
* be null since the actual Member relationship is based on the row position
* within the ResultSet.
*/
public class ResultSetMember 
	implements Member
{

	private static final Logger log = Logger.getLogger(ResultSetMember.class);

	private List childMembers = null;
	private Dimension dimension = null;
	private String name = null;
	private List parentMembers = null;
	private Map childLookupTable = null;
	private Map parentLookupTable = null;
	private ResultSetMeasure measure = null;
	private int type = 1;
	private int position = 0;

	/**
	* Constructs a new Member object
	* <P>
	* Most of what is passed in is just storage objects.
	*
	* @param _name The name of the Member
	* @param _childMembers The List to use for storing child members
	* @param _parentMembers The List to use for storing parent members
	* @param _childLookupTable The lookup table for use when finding a child
	* @param _parentLookupTable The lookup table for use when finding a parent
	*/
	protected ResultSetMember(String _name, List _childMembers, List _parentMembers,
		Map _childLookupTable, 
		Map _parentLookupTable) 
	{
		childLookupTable = _childLookupTable;
		parentLookupTable = _parentLookupTable;
		setChildMembers(_childMembers);
		setParentMembers(_parentMembers);
		setName(_name);
	}

	/**
	* Returns the child member specified by the passed in name
	*
	* @param _name The name of the child member to find
	* @return The Member that was found (if any)
	*/
	public Member getChildMemberByName(String _name) {
		int pos = getLookupPosition(childLookupTable, _name);
		if (pos != -1) {
			return (ResultSetMember)childMembers.get(pos);
		}
		return null;
	}
	
	/**
	* Returns all the child members
	* 
	* @return All of the child members
	*/
	public List getChildMembers() {
		return childMembers;
	}
	
	/**
	* Returns the dimension that this member is a member of
	* 
	* @return The Dimension of this member
	*/
	public Dimension getDimension() {
		return dimension;
	}
	
	/**
	* The passed in member in the case of the ResultSet processor
	* should be null since I will derive the relationship based on the
	* row in the result set
	* <P>
	*
	* @param _member Send a null
	* @return The Measure for the current row in the ResultSet
	*/
	public Measure getMeasure(Member _member) {
		return measure;
	}
	
	/**
	* Returns the name of this Member
	*
	* @return The name of this Member
	*/
	public String getName() {
		return name;
	}
	
	/**
	* Returns the parent member specified by the passed in name
	*
	* @param _name The name of the parent member to find
	* @return The Member that was found (if any)
	*/
	public Member getParentMemberByName(String _name) {
		int pos = getLookupPosition(parentLookupTable, _name);
		if (pos != -1) {
			return (Member)parentMembers.get(pos);
		}
		return null;
	}
	
	/**
	* Returns all the parent members
	* 
	* @return All of the parent members
	*/
	public List getParentMembers(){
		return parentMembers;
	}
	
	/**
	* Will display the name of the member
	* 
	* @return A String with the name of the member in it
	*/
	public String toString() {
		return name;
	}
	
	/**
	* Clears all the internal data structures
	*
	*/
	protected void clear() {
		childMembers.clear();
		parentMembers.clear();
		childLookupTable.clear();
		parentLookupTable.clear();
	}

	/**
	* Adds a Child Member to the current list of parent
	* 
	* @param _member The Member to add
	*/
	protected void addChildMember(ResultSetMember _member) {
		childLookupTable.put(_member.getName().toUpperCase(), new Integer(childMembers.size()));
		childMembers.add(_member);
	}
	
	/**
	* Finds the position of the string in the lookup map
	*
	* @param _str The string to get the position of
	*/
	protected int getLookupPosition(Map _lookupTable, String _str) {
		if (_lookupTable.containsKey(_str.toUpperCase())) {
			return ((Integer)_lookupTable.get(_str.toUpperCase())).intValue();
		}
		return -1;
	}	
	
	/**
	* Sets the Child Members to the passed in list
	* <P>
	* Also populates the lookup table
	*
	* @param _list The List to use
	*/
	protected void setChildMembers(List _list) {
		childMembers = _list;
		for (int i=0;i<_list.size();i++) {
			childLookupTable.put( ((Member)_list.get(i)).getName().toUpperCase(), new Integer(i));
		}
	}
	
	/**
	* Sets the Dimension for this Member
	* 
	* @param _dimenstion The Dimenstion of this member
	*/
	protected void setDimension(Dimension _dimension) {
		dimension = _dimension;
	}
	
	/**
	* Sets the name of the Member
	*
	* @param _str The name of the Member
	*/
	protected void setName(String _str) {
		name = _str;
	}
	
	/**
	* Sets the Measure that will be returned from the getMeasure()
	* method
	*
	* @param _measure The Measure to return
	*/
	protected void setMeasure(ResultSetMeasure _measure) {
		measure = _measure;
	}
	
	/**
	* Adds a Parent Member to the current list of parent
	* 
	* @param _member The Member to add
	*/
	protected void addParentMember(ResultSetMember _member) {
		parentLookupTable.put(_member.getName().toUpperCase(), new Integer(parentMembers.size()));
		parentMembers.add(_member);
	}
	
	/**
	* Sets the Parent Members to the passed in list
	* <P>
	* Also populates the lookup table
	*
	* @param _list The List to use
	*/
	protected void setParentMembers(List _list) {
		parentMembers = _list;
		for (int i=0;i<_list.size();i++) {
			parentLookupTable.put( ((Member)_list.get(i)).getName().toUpperCase(), new Integer(i));
		}
	}
}
