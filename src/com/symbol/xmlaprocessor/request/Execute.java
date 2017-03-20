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

package com.symbol.xmlaprocessor.request;

import javax.xml.soap.Name;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPException;
import org.apache.axis.message.MessageElement;
import org.apache.axis.message.PrefixedQName; 
import org.apache.axis.message.SOAPBodyElement;
import com.symbol.xmlaprocessor.Constants;

public class Execute
	extends SOAPBodyElement
	implements RequestSchema
{

	public static final String SOAP_ACTION = "urn:schemas-microsoft-com:xml-analysis:Execute";

	private Command command = null;
	private Properties properties = null;

	public Execute(String _prefix) {
		this(_prefix, null, null);
	}
	
	public Execute(String _prefix, Command _command, Properties _properties) {
		super( new PrefixedQName(Constants.XMLA_NAMESPACE, "Execute", _prefix));
		
		command = _command;
		properties = _properties;
	}
	
	public void setCommand(Command _command) { 
		command = _command;
	}
	
	public void setProperties(Properties _properties) {
		properties = _properties;
	}
	
	public SOAPElement toSOAPElement() 
		throws SOAPException
	{
		if (command != null) addChildElement(command.toSOAPElement());
		if (properties != null) addChildElement(properties.toSOAPElement());
		return (SOAPElement) this;
	}
}
