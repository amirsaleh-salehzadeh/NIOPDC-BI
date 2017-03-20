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

import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPException;
import org.apache.axis.message.MessageElement;

public abstract class AbstractRequestSchema 
	extends MessageElement
{

	protected AbstractRequestSchema(String _namespace, String _localName, String _value) 
		throws SOAPException
	{
		super(_namespace, _localName);
		if (_value != null) addTextNode(_value);
	}
	
	protected AbstractRequestSchema(String _namespace, String _localName, Request[] _children) 
		throws SOAPException
	{
		super(_namespace, _localName);
		if (_children != null) {
			for (int i=0;i<_children.length;i++) {
				if (_children[i] != null) addChildElement(_children[i]);
			}
		}
	}

	public SOAPElement toSOAPElement() {
		return (SOAPElement)this;
	}

}
