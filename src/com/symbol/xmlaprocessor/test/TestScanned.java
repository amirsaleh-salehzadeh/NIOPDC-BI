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
import com.symbol.xmlaprocessor.response.scan.*;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

/**
* Simple Test with one dimension on either axis
*/
public class TestScanned {
	
	private static final Logger log = Logger.getLogger(TestScanned.class);

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
			
			sqp.setResponseProcessor(new ScannedResponseProcessor());
			
			MDXQuery query = sqp.createMDXQuery(args[4]);

			log.debug("Query Info:\n"+query.toString());
			
			Cube cube = (Cube)query.executeQuery();
			
			//log.debug(((ScannedCube)cube).dumpDimensions());
			
			Dimension x = cube.getDimensionByName("CUSTOMERS");
			List members = x.getMembers();
			for (int i=0;i<members.size();i++) {
				log.debug(((Member)members.get(i)).getName());
			}
			
/*			Member netDlrs = cube.getDimensionByName("Measures").getMemberByName("Net Dlrs");
			Member week = netDlrs.getChildMemberByName("Week 19");
			
			Dimension store = cube.getDimensionByName("STORE");
			Member storeId = store.getMemberByName("ALL STORE");
			Member loyalCustomer = storeId.getChildMemberByName("Loyal Customer");
			
			Measure m = week.getMeasure(loyalCustomer);
			log.debug("Measure:"+m.getValue());
			
			Measure measure = loyalCustomer.getMeasure(week);
			log.debug("Measure:"+measure.getValue());
*/
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}


}
