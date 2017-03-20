
package rex.exceptions;


import javax.swing.JOptionPane;
import rex.utils.I18n; 
/**
 *   Copyright (C) 2006 CINCOM SYSTEMS, INC.
 *   All Rights Reserved
 *   Copyright (C) 2006 Igor Mekterovic
 *   All Rights Reserved
 */

public class RexXMLADiscoverException extends Exception {
	private String msg;
	public RexXMLADiscoverException() {
		super();
    }
	public RexXMLADiscoverException(String msg) {
        super(msg);
        this.msg=msg;
    }
	public String getError()
	{
		return msg;
	}
	public void showMessageErrorInDialog()
	{
        /**
         * Copyright (C) 2006 CINCOM SYSTEMS, INC.
         * All Rights Reserved
         * Copyright (C) 2006 Igor Mekterovic
         * All Rights Reserved
         */ 
  
         /*implementing I18n */
         JOptionPane.showMessageDialog(null,msg,
                I18n.getString("msgTitle.error"),
                JOptionPane.WARNING_MESSAGE);
           /* end of modification for I18n */
        
	}	
}

