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
import org.apache.xerces.parsers.DOMParser;
import org.w3c.dom.*;
import org.xml.sax.*;
import com.symbol.xmlaprocessor.response.*;

/**
* This class parses XML returned from an Execute query to XMLA
*
* @author Sloan Seaman (sloan@sgi.net)
*/
public class ScannedResponseProcessor 
	implements ErrorHandler, ResponseProcessor
{
	private static final Logger log = Logger.getLogger(ScannedResponseProcessor.class);
	private int rowCount = 0;
	private int columnCount = 0;
	private int colPos = 0;
	private int rowPos = 0;
	private int cellOrd = 0;
	private Element cellEl = null;
	private int measureCellOrd = 0;
	private NodeList valueCells = null;
	private NodeList childrenNL = null;
	private ScannedMeasure measure = new ScannedMeasure(null);
	private Exception error = null;
	
	// change to protected at a later time
	public ScannedResponseProcessor() {	}
	
	/**
	* This method processes the InputStream that is passed in and
	* builds a data structure representing the MultiDimentional data.
	* <P>
	* This is accomplished by first building the dimensions from the 
	* AxisInfo tag's.
	* <P>
	* Then it fills the dimension with Members by going through the
	* Axis tags child Tuples Tags.
	* <P>
	* At the same time it stored in the Member object the type of member that it
	* is (Row or Column) and the position that the Member holds on the
	* Cell grid.  This is used later to determine the Cell Ordinal
	* to lookup the final Measure
	* <P>
	*
	* @param _stream The InputStream to process to build the data structure
	* @return Cube containing results
	*/
	public Object process(java.io.InputStream _stream)
		throws Exception
	{
		DOMParser parser = new DOMParser();
		parser.setErrorHandler(this);
		parser.parse(new InputSource( _stream ));
		Document doc = parser.getDocument();

		Object obj = processXML(doc.getDocumentElement());
		if (error != null) throw error;
		
		return obj;
	}

	public Object processXML(Element _element) {
		log.debug("process() invoked");
		ScannedCube cube = new ScannedCube(null, new ArrayList(), new HashMap());

		rowCount = 0;
		columnCount = 0;
		int curCol = 0;
		String dimensionName = null;
		
		if (_element != null) {
			Element returnEl = (Element)_element.getElementsByTagName(XMLResponseConstants.RETURN).item(0);
			Element rootEl = (Element)returnEl.getElementsByTagName(XMLResponseConstants.ROOT).item(0);
			
			log.debug("Building dimensions");
			// Build the dimensions
			Element olapInfoEl = (Element)rootEl.getElementsByTagName(XMLResponseConstants.OLAP_INFO).item(0);
			Element axesInfoEl = (Element)olapInfoEl.getElementsByTagName(XMLResponseConstants.AXES_INFO).item(0);
			NodeList axisInfoNL = axesInfoEl.getElementsByTagName(XMLResponseConstants.AXIS_INFO);
			NodeList hierInfoNL = null;
			
			for (int i=0; i<axisInfoNL.getLength(); i++) {
				hierInfoNL = ((Element)axisInfoNL.item(i)).getElementsByTagName(XMLResponseConstants.HIERARCHY_INFO);
				for (int j=0; j<hierInfoNL.getLength(); j++) {
					cube.addDimension( new ScannedDimension(
						hierInfoNL.item(j).getAttributes().getNamedItem(XMLResponseConstants.NAME).getNodeValue(),
						new ArrayList(), new HashMap()) );
				}
			}

			Element  axisEl  = null, tuplesEl = null,    cellEl = null, memberEl = null;
			NodeList tupleNL = null, memberNL = null, captionNL = null;
			String axisName = null, memberName = null;
			ScannedDimension dimension = null;
			ScannedMember member = null;
			ScannedMember parentMember = null;
			
			// Build the members
			log.debug("Building Members");
			Element axesEl = (Element)rootEl.getElementsByTagName(XMLResponseConstants.AXES).item(0);
			NodeList axisNL = axesEl.getElementsByTagName(XMLResponseConstants.AXIS);
			for (int i=0; i< axisNL.getLength(); i++) {
				axisEl = (Element)axisNL.item(i);
				axisName = axisEl.getAttributes().getNamedItem(XMLResponseConstants.NAME).getNodeValue();
				
				tuplesEl = (Element)axisEl.getElementsByTagName(XMLResponseConstants.TUPLES).item(0);
				tupleNL = tuplesEl.getElementsByTagName(XMLResponseConstants.TUPLE);
				for (int j=0; j<tupleNL.getLength(); j++) {
					member = null;
					parentMember = null;
					
					memberNL = ((Element)tupleNL.item(j)).getElementsByTagName(XMLResponseConstants.MEMBER);
					for (int k=0; k<memberNL.getLength(); k++) {
						memberEl = (Element)memberNL.item(k);
						dimensionName = memberEl.getAttributes().getNamedItem(XMLResponseConstants.MEMBER_HIERARCHY).getNodeValue();
						
						memberName= getTextValue(memberEl.getElementsByTagName(XMLResponseConstants.MEMBER_CAPTION).item(0));
						dimension = (ScannedDimension)cube.getDimensionByName(dimensionName);
						//log.debug("Dimension:"+dimensionName+" Member:"+memberName);
						member = (ScannedMember)dimension.getMemberByName(memberName);
						if (member == null) {
							member = new ScannedMember(memberName, new ArrayList(), new ArrayList(), 
								new HashMap(), new HashMap());
							dimension.addMember(member);
							member.setDimension(dimension);
						}
						if (parentMember != null) {
							// If I could get rid of this next line and have it work 100%
							// It would be perfect.....
							member = new ScannedMember(memberName, new ArrayList(), new ArrayList(), 
								new HashMap(), new HashMap());
							member.addParentMember(parentMember);
							parentMember.addChildMember(member);
						}
						parentMember = member;
					}

					member.setScannedResponseProcessor(this);

					/*
					 This sets the type of member and the position it holds
					 in the cell grid layout
					*/
					if (axisName.equals(XMLResponseConstants.ROWS_AXIS_NAME)) {
						member.setType(0);
						member.setPosition(rowCount++);
					}
					else if (axisName.equals(XMLResponseConstants.COLUMNS_AXIS_NAME)) {
						member.setType(1);
						member.setPosition(columnCount++);
					}
				}
			}
			// I'm at .4 secs right here... kinda slow....
			
			log.debug("Setting celldata for later use");
			valueCells = rootEl.getElementsByTagName(XMLResponseConstants.CELL_DATA).item(0).getChildNodes();
			
		}
		log.debug("process() complete");
		return cube;
	}
	
	/**
	* This method works by retrieving the position stored in the
	* _member and _relationship Members and based on their type does
	* the equation: <BR>
	* <DL>
	*	colPos + (rowPos * totalColumns)
	* </DL>
	* <BR>
	* To determine the Cell ordinal of the measure that we want to
	* retrieve
	* <P>
	* We then lookup through the Cell tags until we hit one with
	* the proper ordinal.
	*
	* @param _member The Member object that actually called this method
	* @param _relationship The Member object that was passed in via
	* the getMeasure() method
	* @return The Measure that was found (if any)
	*/
	protected Measure findMeasure(ScannedMember _member, 
		ScannedMember _relationship) 
	{
		measure.setValue(null);
		
		// lets figure out the Cell ordinal now
		colPos = _member.getPosition();
		if (_member.getType() == 0) colPos = _relationship.getPosition();
		
		rowPos = _relationship.getPosition();
		if (_relationship.getType() == 1) rowPos = _member.getPosition();

		measureCellOrd = colPos + (rowPos * columnCount);
		
		// Lets get the data now...
		cellOrd = 0;
		cellEl = null;
		for (int i=0;i<valueCells.getLength();i++) {
			while (valueCells.item(i++).getNodeType() != Node.ELEMENT_NODE) { }
			cellEl = (Element)valueCells.item(i-1);
			
			cellOrd = Integer.parseInt(cellEl.getAttributes().getNamedItem(XMLResponseConstants.CELL_ORDINAL).getNodeValue());
			if (measureCellOrd == cellOrd) {
				measure.setValue(
					getTextValue(cellEl.getElementsByTagName(XMLResponseConstants.CELL_VALUE).item(0))
				);
				break;

			}
		}
		return measure;
	}
	
	private String getTextValue(org.w3c.dom.Node _node) {
		childrenNL = _node.getChildNodes();
		for(int i=0; i<childrenNL.getLength(); i++) {
			if ( childrenNL.item(i) instanceof Text ) return childrenNL.item(i).getNodeValue();
		}
		return null;
	}
	
	public void error(SAXParseException spe) {
		error = spe;
		log.error("Error parsing response", spe);
	}
	
	public void fatalError(SAXParseException spe) {
		error = spe;
		log.fatal("Fatal error parsing response", spe);
	}

	public void warning(SAXParseException spe) {
		log.warn("Warning while parsing response", spe);
	}
	
}
