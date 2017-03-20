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

import java.util.*;

/**
* Each member represents a specific Member within a Dimesion.
*/
public interface Member {

	/**
	* Returns the child member specified by the passed in name
	*
	* @param _name The name of the child member to find
	* @return The Member that was found (if any)
	*/
	public Member getChildMemberByName(String _name);
	
	/**
	* Returns all the child members
	* 
	* @return All of the child members
	*/
	public List getChildMembers();
	
	/**
	* Returns the dimension that this member is a member of
	* 
	* @return The Dimension of this member
	*/
	public Dimension getDimension();
	
	/**
	* The passed in member is the other member that is required to
	* create the relationship to retrieve the specific Measure.
	*
	* @param _member The Member to use to derive the relationship to get
	* the Measure.
	*/
	public Measure getMeasure(Member _member);
	
	/**
	* Returns the name of this Member
	*
	* @return The name of this Member
	*/
	public String getName();
	
	/**
	* Returns the parent member specified by the passed in name
	*
	* @param _name The name of the parent member to find
	* @return The Member that was found (if any)
	*/
	public Member getParentMemberByName(String _name);
	
	/**
	* Returns all the parent members
	* 
	* @return All of the parent members
	*/
	public List getParentMembers();
	
}
