/*
 * Created on May 12, 2006
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package rex.graphics.mdxeditor.jsp;

/**
 * @author pyadav
 *
 * This class will store Environment Variables with values.
 * 
 */
import java.io.*;
import java.util.*;

/**
 *   Copyright (C) 2006 CINCOM SYSTEMS, INC.
 *   All Rights Reserved
 *   Copyright (C) 2006 Igor Mekterovic
 *   All Rights Reserved
 */

public class ReadEnv 
{
	public static Properties getEnvVars() throws Exception
	{
                Properties envVars = new Properties();
                envVars.putAll(System.getenv());
                
		return envVars;
	}
}
