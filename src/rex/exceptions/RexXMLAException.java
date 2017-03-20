
package rex.exceptions;

import javax.swing.JOptionPane;

public class RexXMLAException extends Exception {
	private String msg;
	public RexXMLAException() {
		super();
    }
	public RexXMLAException(String msg) {
        super(msg);
        this.msg=msg;
    }
	public String getError()
	{
		return msg;
	}
	public void showMessageErrorInDialog()
	{
		JOptionPane.showMessageDialog(null,msg,"Error",JOptionPane.WARNING_MESSAGE);
	}	
}
