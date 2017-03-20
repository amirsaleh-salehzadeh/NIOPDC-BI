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
    Foundation, Inc., 9 Temple Place, Suite 330, Boston, MA  02111-1307  USA
	
	XMLA Processor was funded by Symbol Corporation.
	Please support the Symbol Corporation whenever possible.
	.
	Author: Sloan Seaman
	Email : sloan@sgi.net
*/

package com.symbol.xmlaprocessor.test;

import java.util.*;
import org.apache.axis.Message;
import org.apache.axis.MessageContext;
import org.apache.axis.client.Call;
import org.apache.axis.client.Service;
import org.apache.axis.message.*;
import com.symbol.xmlaprocessor.query.*;
import com.symbol.xmlaprocessor.request.*;
import com.symbol.xmlaprocessor.response.*;
import com.symbol.xmlaprocessor.response.resultset.*;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

/**
* Simple Test with one dimension on either axis
*/
public class TestResultSet {
	
	private static final Logger log = Logger.getLogger(TestResultSet.class);

	public static void main(String [] args) {
		try {
			DOMConfigurator.configure(args[0]);

			Class.forName("com.symbol.xmlaprocessor.query.SOAPQueryProvider");
			QueryProviderManager.setDefaultQueryProviderName(
				"com.symbol.xmlaprocessor.query.SOAPQueryProvider");
			
			QueryProvider sqp = QueryProviderManager.getQueryProvider();
			java.util.Properties props = new java.util.Properties(); // you could use a file here as well
			props.setProperty(SOAPQueryProvider.TARGET_URI, args[1]);
			props.setProperty(SOAPQueryProvider.NAMESPACE, "ns1");
			sqp.setConfiguration(props);
			
			((SOAPQueryProvider)sqp).addProperty(new DataSourceInfo("MSOLAP", args[2]));
			((SOAPQueryProvider)sqp).addProperty(new Catalog(args[3]));
			((SOAPQueryProvider)sqp).addProperty(Format.TABULAR);
			((SOAPQueryProvider)sqp).addProperty(Content.DATA);
			
			// This is not a neccessary step, but it will ensure no one can
			// modify the configuration
			// QueryProviderManager.setFrozen(TestQueryProvider.class.hashCode()+"", true);
			
			sqp.setResponseProcessor(new ResultSetResponseProcessor());
			
			MDXQuery query = sqp.createMDXQuery(args[4]);

			log.debug("Query Info:\n"+query.toString());
			
			Cube cube = (Cube)query.executeQuery();
			
			ResultSet rs = ((ResultSetCube)cube).getResultSet();

			List dimensions = cube.getDimensions();
			List members = null;
			List childMembers = null;
			Measure measure = null;
			
			while (rs.next()) {
				for (int h=0;h<dimensions.size();h++) {
					members = ((Dimension)dimensions.get(h)).getMembers();
					if (members.size() > 0) {
						for (int i=0;i<members.size();i++) {
							childMembers = ((Member)members.get(i)).getChildMembers();
							for (int j=0;j<childMembers.size();j++) {
								measure = ((Member)childMembers.get(j)).getMeasure(null);
								if (measure != null) {
									log.debug("Member:"+rs.getRowMember().toString()+" Object:"+measure.toString());
								}
							}
						}
					}
					else {
						log.debug("Member:"+rs.getRowMember().toString());
						break;
					}
				}
			}
			
/*			Member netDlrs = cube.getDimensionByName("Measures").getMemberByName("Net Dlrs");
			Member week = netDlrs.getChildMemberByName("Week 18");
			Member member = null;
			while (rs.next()) {
				Measure m = week.getMeasure(null);
				if (m != null) {
					member = rs.getRowMember();
					log.debug("Member:"+member.toString()+" Object:"+m.toString());
				}
			}
			
			This is another way
			while (rs.next()) {
				Object o = rs.get(week);
				if (o != null) {
					member = rs.getRowMember();
					log.debug("Member:"+member.toString()+" Object:"+o.toString());
				}
			}
*/

		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

}
