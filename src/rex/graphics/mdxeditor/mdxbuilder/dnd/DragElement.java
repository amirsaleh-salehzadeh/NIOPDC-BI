/*
 * Created on Nov 29, 2006
 *
 * Author: pyadav
 * 
 */
package rex.graphics.mdxeditor.mdxbuilder.dnd;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Area;
/**
 * @author pyadav
 *
 */
public class DragElement extends JPanel {
	
    private Point location = new Point(0, 0);
    String dragLabel;
    private int width;
    private int height;
    private float alpha = 0.7f;
    private boolean visible=true;
    private Rectangle visibleRect = null;
	/**
	 * @param text
	 */
	public DragElement() {
		setOpaque(false);
	}
	public void showString(boolean v)
	{
		visible=v;
	}
	public void setImage(String text,int h,int w) {
		dragLabel=text;
		width=w;
		height=h;
	}
	
    public void setPoint(Point location) {
        this.location = location;
    }
    public Point getPoint(){
    	return location;
    }

    protected void paintComponent(Graphics g) {
        if (dragLabel == null || !isVisible()) {
            return;
        }

        Graphics2D g2 = (Graphics2D) g.create();
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                            RenderingHints.VALUE_ANTIALIAS_ON);

        
        if (visibleRect != null) {
            g2.setClip(visibleRect);
        }
        
        if (visibleRect != null) {
            Area clip = new Area(visibleRect);
            g2.setClip(clip);
        }
        
        if(visible)
        {
            g2.drawString(dragLabel, (int)location.getX(), (int)location.getY());
        }
        else
        {
            g2.drawString("", (int)location.getX(), (int)location.getY());
        }
    }
}
