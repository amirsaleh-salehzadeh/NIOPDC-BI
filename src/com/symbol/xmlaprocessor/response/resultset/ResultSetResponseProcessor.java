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
import org.w3c.dom.*;
import org.apache.xerces.parsers.DOMParser;
import org.xml.sax.*;
import com.symbol.xmlaprocessor.response.*;

/**
* This class parses XML returned from an Execute query to XMLA
*
* @author Sloan Seaman (sloan@sgi.net)
*/
public class ResultSetResponseProcessor 
	implements ErrorHandler, ResponseProcessor
{
	private static final Logger log = Logger.getLogger(ResultSetResponseProcessor.class);
	
	private NodeList rowTuples = null;
	private NodeList valueCells = null;
	private ResultSetDimension dimension = null;
	private ResultSetMember member = null;
	private ResultSetMember parentMember = null;
	private List finalColumnMembers = new ArrayList();
	private String memberName = null;
	private String nodeName = null;
	private String dimensionName = null;
	private int nodeType;
	private Exception error;
	
	public ResultSetResponseProcessor() {}
	
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
	
	private Object processXML(Element _element) {
		log.debug("Processing XML");
		NodeList valueCells = null;
		NodeList rowTuples = null;
		List finalColumnMembers = new ArrayList();
		ResultSetCube cube = new ResultSetCube(null, new ArrayList(), new HashMap());
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
					cube.addDimension( new ResultSetDimension(
						hierInfoNL.item(j).getAttributes().getNamedItem(XMLResponseConstants.NAME).getNodeValue(),
						new ArrayList(), new HashMap()) );
				}
			}

			Element  axisEl  = null, tuplesEl = null,    cellEl = null, memberEl = null;
			NodeList tupleNL = null, memberNL = null, captionNL = null;
			String axisName = null, memberName = null;
			ResultSetDimension dimension = null;
			ResultSetMember member = null;
			ResultSetMember parentMember = null;
			
			// Build the members
			log.debug("Building Members");
			Element axesEl = (Element)rootEl.getElementsByTagName(XMLResponseConstants.AXES).item(0);
			NodeList axisNL = axesEl.getElementsByTagName(XMLResponseConstants.AXIS);
			for (int i=0; i< axisNL.getLength(); i++) {
				axisEl = (Element)axisNL.item(i);
				axisName = axisEl.getAttributes().getNamedItem(XMLResponseConstants.NAME).getNodeValue();
				if (axisName.equals(XMLResponseConstants.COLUMNS_AXIS_NAME)) {
					tuplesEl = (Element)axisEl.getElementsByTagName(XMLResponseConstants.TUPLES).item(0);
					tupleNL = tuplesEl.getElementsByTagName(XMLResponseConstants.TUPLE);
					for (int j=0; j<tupleNL.getLength(); j++) {
						member = null;
						parentMember = null;
						
						memberNL = ((Element)tupleNL.item(j)).getElementsByTagName(XMLResponseConstants.MEMBER);
						for (int k=0; k<memberNL.getLength(); k++) {
							memberEl = (Element)memberNL.item(k);
							dimensionName = memberEl.getAttributes().getNamedItem(XMLResponseConstants.MEMBER_HIERARCHY).getNodeValue();
							
							captionNL = memberEl.getElementsByTagName(XMLResponseConstants.MEMBER_CAPTION).item(0).getChildNodes();
							for (int l=0; l<captionNL.getLength(); l++) {
								if ( captionNL.item(i) instanceof Text ) {
									memberName = captionNL.item(i).getNodeValue();
									break;
								}
							}

							dimension = (ResultSetDimension)cube.getDimensionByName(dimensionName);
							//log.debug("Dimension:"+dimensionName+" Member:"+memberName);
							member = (ResultSetMember)dimension.getMemberByName(memberName);
							if (member == null) {
								member = new ResultSetMember(memberName, new ArrayList(), new ArrayList(), 
									new HashMap(), new HashMap());
								dimension.addMember(member);
								member.setDimension(dimension);
							}
							if (parentMember != null) {
								// If I could get rid of this next line and have it work 100%
								// It would be perfect.....
								member = new ResultSetMember(memberName, new ArrayList(), new ArrayList(), 
									new HashMap(), new HashMap());
								member.addParentMember(parentMember);
								parentMember.addChildMember(member);
							}
							parentMember = member;
						}
						finalColumnMembers.add(member);
					}
				}
				else if (axisName.equals(XMLResponseConstants.ROWS_AXIS_NAME)) {
					// the row members I will build as needed
					rowTuples = axisEl.getElementsByTagName(XMLResponseConstants.TUPLES).item(0).getChildNodes();
				}
				
			}
			
			log.debug("Setting celldata for later use");
			valueCells = rootEl.getElementsByTagName(XMLResponseConstants.CELL_DATA).item(0).getChildNodes();
			// Unlike the cached version, I stop here
			
		}
		log.debug("process() complete");
		
		cube.setResultSet( new ResultSet(
			(ResultSetMember[])finalColumnMembers.toArray(new ResultSetMember[]{}), 
			rowTuples, valueCells) );
		return cube;
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
