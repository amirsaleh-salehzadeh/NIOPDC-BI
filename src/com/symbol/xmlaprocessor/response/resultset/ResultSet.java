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
import org.apache.log4j.Logger;
import org.w3c.dom.*;

/**
* The ResultSet object represents the Results of a query that used
* the ResultSetResponseProcessor handler
* 
*/
public class ResultSet {

	private static final Logger log = Logger.getLogger(ResultSet.class);
	
	// This has so many class vars so I don't have to create them each time
	// i.e. speed reasons
	private ResultSetMember[] columns = null;
	private NodeList rows = null;
	private NodeList valueCells = null;
	private NodeList memberNL = null;
	private NodeList childrenNL = null;
	
	private Element valueCell = null;
	private Element memberEl = null;
	
	private int rowCount = 0;
	private int rowsIdx = 0;
	private int rowsLength = 0;
	private int valueCellsIdx = 0;
	private int valueCellsLength = 0;
	private int valueCellRow = -1;
	private int valueCellOrd = 0;
	private int curCellOrd = 0;
	
	private String memberName = null;
	
	private Member currentRowMember = null;
	private Map rowValues = new HashMap();
	private Object value = null;
	private ResultSetMember member = new ResultSetMember("",
		new ArrayList(), new ArrayList(), new HashMap(), new HashMap());
	private ResultSetMember parentMember = null;
	private ResultSetMeasure measure = null;
	private ResultSetMeasure[] columnObjectBank = null;
	
	
	/**
	* Constructs a new ResultSet with the passed in Columns (List of Members),
	* rows (iterator though SOAPElement) and values (iterator though SOAPElement)
	*
	* @param _columns List of Member objects that represent the columns
	* @param _rows NodeList that represent the rows
	* @param _values NodeList that represent the values
	*/
	public ResultSet(ResultSetMember[] _columns, NodeList _rows, NodeList _values) {
		columns = _columns;
		columnObjectBank = new ResultSetMeasure[columns.length];
		rows = _rows;
		rowsLength = rows.getLength() -1;
		valueCells = _values;
		valueCellsLength = valueCells.getLength() -1;
		
		// prepopulates the columnObjectBank with Measures...
		for (int i=0;i<columns.length;i++) {
			columnObjectBank[i] = new ResultSetMeasure(null);
		}
	}
	

	/**
	* Returns the value of the column specified by _member for the current row
	*
	* @param The Measure object for the column
	*/
	public Object get(Member _member) {
		return rowValues.get(_member);
	}
	
	/**
	* Returns the Row member (the lowest member in the ancestry) for the
	* current row
	*
	* @return The row member
	*/
	public Member getRowMember() {
		return currentRowMember;
	}
	
	/**
	* Is there another row?
	*
	* @return True if there is, false otherwise
	*/
	public boolean next() {
		if (rowsIdx < rowsLength) {
			while (rows.item(rowsIdx++).getNodeType() != Node.ELEMENT_NODE) { }
			
			setRowTuple((Element)rows.item(rowsIdx-1));
			//rows.remove();
			
			setRowValues();
			
			rowCount++;
			return true;
		}
		else {
			close();
		}
		return false;
	}
	
	/**
	* Call this when done with the ResultSet
	* <P>
	* It is basically just good garbage collection...
	*/
	private void close() {
		for (int i=0;i<columns.length;i++) {
			columns[i] = null; 
		}
		columns = null;
		for (int i=0;i<columnObjectBank.length;i++) {
			columnObjectBank[i] = null; 
		}
		rows = null;
		valueCells = null;
		memberNL = null;
		childrenNL = null;
		
		valueCell = null;
		memberEl = null;

		memberName = null;
		
		currentRowMember = null;
		rowValues.clear();
		rowValues = null;
		value = null;
		member = null;
		parentMember = null;
		measure = null;
		
		for (int i=0;i < columnObjectBank.length;i++) {
			columnObjectBank[i] = null;
		}
		columnObjectBank = null;
	}

	/**
	* Sets the row values
	*/
	private void setRowValues() {
		rowValues.clear();

		// clean
		for (int i=0;i<columns.length;i++) {
			columnObjectBank[i].setValue(null);
			columns[i].setMeasure(null);
		}
		
		if (valueCellsIdx < valueCellsLength) {
			
			if (valueCellRow < rowCount) {
				while (valueCells.item(valueCellsIdx++).getNodeType() != Node.ELEMENT_NODE) { }
				valueCell = (Element)valueCells.item(valueCellsIdx-1);
				//valueCells.remove();
				valueCellOrd = Integer.parseInt(valueCell.getAttributes().getNamedItem(XMLResponseConstants.CELL_ORDINAL).getNodeValue() );
				valueCellRow =  valueCellOrd / columns.length;
			}
			if (valueCellRow == rowCount) {
				curCellOrd = 0;
				value = null;
				for (int i=0;i<columns.length;i++) {
					curCellOrd = i + (rowCount * columns.length);
					measure = (ResultSetMeasure)columns[i].getMeasure(null);

					if (curCellOrd == valueCellOrd) {
						value = getTextValue(valueCell.getElementsByTagName(XMLResponseConstants.CELL_VALUE).item(0));
						
						// Instead of creating a new object, use a banked object
						// This is for memory usage reasons
						columnObjectBank[i].setValue(value);
						columns[i].setMeasure(columnObjectBank[i]);
						
						rowValues.put(columns[i], value);
						
						if (valueCellsIdx < valueCellsLength) {
							while (valueCells.item(valueCellsIdx++).getNodeType() != Node.ELEMENT_NODE) { }
							valueCell = (Element)valueCells.item(valueCellsIdx-1);
							//valueCells.remove();
							valueCellOrd = Integer.parseInt(valueCell.getAttributes().getNamedItem(XMLResponseConstants.CELL_ORDINAL).getNodeValue() );
							valueCellRow =  valueCellOrd / columns.length;
						}
					}
					else {
						if (measure != null) measure.setValue(null);
					}
				}
			}
		}
	}
	
	/**
	* Set the row members
	*/
	private void setRowTuple(Element _rowTuple) {
		parentMember = null;
		
		memberNL = _rowTuple.getElementsByTagName(XMLResponseConstants.MEMBER);
		for (int i=0; i<memberNL.getLength();i++) {
			memberEl = (Element)memberNL.item(i);
			// use the same member object over and over again (for speed)
			member.clear();
			memberName = null;
			
			memberName = getTextValue(memberEl.getElementsByTagName(XMLResponseConstants.MEMBER_CAPTION).item(0));
							
			member.setName( memberName );
			
			if (parentMember != null) {
				member.addParentMember(parentMember);
				parentMember.addChildMember(member);
			}
			parentMember = member;
		}
		
		// This sets getRowMember to the lowest member in the ancestry
		currentRowMember = member;
	}
	
	private String getTextValue(Node _node) {
		childrenNL = _node.getChildNodes();
		for(int i=0; i<childrenNL.getLength(); i++) {
			if ( childrenNL.item(i) instanceof Text ) return childrenNL.item(i).getNodeValue();
		}
		return null;
	}
}
