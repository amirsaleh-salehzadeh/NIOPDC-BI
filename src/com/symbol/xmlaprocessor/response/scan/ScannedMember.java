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
* Let me next discuss how the Measures are stored and accessed (as this is
* a bit tricky).
* <P>
* <B>Stored</B>
* <P>
* When a Measure is added to a Member it is also passed in with an Ancestry that
* represents the Member that must be used to get the Measure.  Think of this
* as a location.  You cannot get to the location with just the Longitude.  You need
* the Latitude as well.
* <P>
* In our case, the Member object itself defined the Longitude, while the Ancesrty
* that is passed in with the Measure defineds the Latitude that is required to 
* retrieve the Measure.
* <P>
* When the Measure is added, a Storage Tree is built to store it.  Each node in
* the tree represents a Member in the Ancestry that is used to retrieve the 
* Measure.
* <P>
* Don't worry, Member does not make a tree for each Measure.  There is one
* tree and new Nodes are made when required.
* <P>
* I.E. there will only be one Net Dlrs node, but it may have many children 
* (2002, 2003, etc).
* <P>
* <B>Accessed</B>
* <P>
* Whenever you request a child of a Member object, the returned Member,
* behind the scenes, has it's Ancestor(parent) recorded so that the object
* knows who its current parent is.  This is to avoid confusion in multi-parent
* situations.
* <P>
* When the getMeasure(Member) method is called, the passed in member is used
* to create the relationship to get the Measure.
* <P>
* The passed in Members Ancestry is accessed and then used to traverse the
* storage tree within the Member itself.
* <P>
* Example:<BR>
* <DL>
*	I have a Measure that has a Member Ancestry consisting of Net Dlrs->2002
*	and the Member object itself represents Customer 3000.
* 	<P>
*	The Member object, when adding the Measure to itself, will construct a
*	tree with a Node <I>Net Dlrs</I> that has a child node <I>2002</I>.
* 	<P>
*	When you want to retrieve the Net Dollars for Customer 3000 in the year
*	2002, you call the getMeasure(Member) method, passing in the Member 2002
*	which you got from doing a getChildMemberByName("2002") on the Net Dlrs
*	Member object.
* </DL>
* <P>
* Complex enough?  I think so...
* 
*/
public class ScannedMember 
	implements Member
{

	private static final Logger log = Logger.getLogger(ScannedMember.class);

	private List childMembers = null;
	private Dimension dimension = null;
	private String name = null;
	private ScannedResponseProcessor scannedResponseProcessor = null;
	private List parentMembers = null;
	private Map childLookupTable = null;
	private Map parentLookupTable = null;
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
	protected ScannedMember(String _name, List _childMembers, List _parentMembers,
		Map _childLookupTable, Map _parentLookupTable) 
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
			return (ScannedMember)childMembers.get(pos);
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
	* The passed in member is the other member that is required to
	* create the relationship to retrieve the specific Measure.
	* <P>
	* This method works like so:<BR>
	* The passed in Member object is checked to see if it has
	* any Ancestry (if it is the child of something).
	* <P>
	* If it isn't, I look in the storage tree at the root for a node that is
	* equal to the name of the member.
	* <P>
	* If the Member does have ancestry, then I traverse the storage
	* tree following the path defined by the Ancestry.<BR>
	* The Node finding method stops at the parent of the actual node that I want
	* so I use the name of the Member object that was passed in to
	* get the final node in the storage tree that has the value I want.
	* <P>
	* I then return that value.
	*
	* @param _member The Member to use to derive the relationship to get
	* the Measure.
	*/
	public Measure getMeasure(Member _member) {
		return scannedResponseProcessor.findMeasure(this, (ScannedMember)_member);
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
	* Adds a Child Member to the current list of parent
	* 
	* @param _member The Member to add
	*/
	protected void addChildMember(ScannedMember _member) {
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
	* Returns the position of this Member
	* <P>
	* This is used to determine the Cell ordinal
	*/
	protected int getPosition() {
		return position;
	}
	
	/**
	* Returns the current type for this Member
	* <P>
	* 0 = ROW, 1 = COLUMN
	*
	* @return The current type
	*/
	protected int getType() {
		return type;
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
	* Adds a Parent Member to the current list of parent
	* 
	* @param _member The Member to add
	*/
	protected void addParentMember(ScannedMember _member) {
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
	
	/**
	* Sets the position of this member
	* <P>
	* This is used to deterine the Cell Ordinal when
	* looking the measure up
	*
	* @param _int The position of this member
	*/
	protected void setPosition(int _pos) {
		position = _pos;
	}
	
	/**
	* Sets the ScannedResponseProcessor.
	* <P>
	* This objects findMeasure() method will be called
	* when a call to getMeasure() is done.
	*
	* @param _anc The ScannedResponseProcessor to use
	*/
	protected void setScannedResponseProcessor(
		ScannedResponseProcessor _srp) 
	{
		scannedResponseProcessor = _srp;
	}
	
	/**
	* Sets the type of Member that this object is (Row or Column)
	* <P>
	* 0 = ROW, 1 = COLUMN
	*
	* @param _int The type
	*/
	protected void setType(int _int) {
		type = _int;
	}
}
