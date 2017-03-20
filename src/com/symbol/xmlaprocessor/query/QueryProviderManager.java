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

package com.symbol.xmlaprocessor.query;

import java.util.Hashtable;
import java.util.Map;

/**
* QueryProviderManager managers all query providers within a JVM.
* <P>
* Developers wishing to access a Query provider should do
* so via:<BR>
* <CODE>QueryProviderManager.getQueryProvider()</CODE>
* <BR>or<BR>
* <CODE>QueryProviderManager.getQueryProvider(uri of specificProvider)</CODE>
* <P>
* If you do not specify a specific QueryProvider to return, the 
* one set by <CODE>setDefaultQueryProviderName</CODE> will be used.
* <P>
* All other methods within the QueryProviderManager should only be called during 
* initial setup.  If the other methods are called at a later time, a resulting 
* Exception exception may occur.
*/
public class QueryProviderManager {

	private static Map map = new Hashtable();

	private static String defaultName = null;
	
	private static boolean frozen = false;
	
	private static String frozenKey = null;
	
	/**
	* Removes a registered QueryProvider
	*
	* @param _uri The URI of the QueryProvider to remove
	* @throws Exception Thrown if an attempt to deregister occurs
	*	while the configuration is locked
	*/
	public static void deregisterQueryProvider(String _uri) 
		throws Exception
	{
		if (frozen) throw new Exception("Configuration is frozen");
		map.remove(_uri);
	}

	/**
	* Get a QueryProvider
	* <P>
	* Uses the default QueryProvider
	*
	* @return A QueryProvider
	* @throws Exception Thrown if a QueryProvider cannot be retrieved
	*/
	public static QueryProvider getQueryProvider() 
		throws Exception
	{
		return getQueryProvider(defaultName);
	}
	
	/**
	* Get a QueryProvider
	*
	* @param The uri (com.symbol.whatever) of the factory to load
	* @return A QueryProvider
	* @throws Exception Thrown if a QueryProvider cannot be retrieved
	*/
	public static QueryProvider getQueryProvider(String _uri) 
		throws Exception
	{
		if (!map.containsKey(_uri)) { 
			throw new Exception("QueryProvider "+_uri+" not found");
		}
		return (QueryProvider)map.get(_uri);
	}
	
	/**
	* Registers QueryProvider
	*
	* @param _uri The URI of the QueryProvider
	* @param _pmf The QueryProvider to store (Related to the _uri)
	* @throws Exception Thrown if an attempt to register occurs
	*	while the configuration is locked or if the QueryProvider
	*	could not be registered
	*/
	public static void registerQueryProvider(String _uri, 
		Class _provider)
		throws Exception
	{
		if (frozen) throw new Exception("Configuration is frozen");
		try {
			map.put(_uri, _provider.newInstance());
		}
		catch (Exception e) {
			throw new Exception("Could not register provider", e);
		}
	}
	
	/**
	* Sets the URI of the default QueryProvider
	* <P>
	* Used if a developer calls JDOQueryProviderManageger.getQueryProvider()
	*
	* @param _uri The URI of the default QueryProvider
	* @throws Exception Thrown if an attempt to register occurs
	*	while the configuration is locked
	*/
	public static void setDefaultQueryProviderName(String _str) 
		throws Exception
	{
		if (frozen) throw new Exception("Configuration is frozen");
		defaultName = _str;
	}

	/**
	* Freezes the configuration so that no more QueryProviders
	* may be added or removed.
	* <P>
	* A key should be passed in to set the state of the freeze.
	* <BR>
	* This key will be used at later times to see if the class has the right
	* to change the frozen state.
	* <P>
	* Optimally, the Initialization class will load everything and then
	* freeze the configuration with a key that only it knows. 
	*
	* @param _key A Security key of sometype to use (may only be set once!)
	* @param _state The state that should be set
	* @throws Exception Thrown if access is denied to the state change attempt
	*/
	public static void setFrozen(String _key, boolean _state) 
		throws Exception
	{
		if (frozenKey == null) {
			frozenKey = _key;
			frozen = _state;
		}
		if (frozenKey.equals(_key)) {
			frozen = _state;
		}
		else { 
			throw new Exception("Object does not have Access Rights to change Frozen State");
		}
	}
	
}
