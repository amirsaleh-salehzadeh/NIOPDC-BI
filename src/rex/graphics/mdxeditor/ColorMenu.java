package rex.graphics.mdxeditor;

/**
 *   Copyright (C) 2006 CINCOM SYSTEMS, INC.
 *   All Rights Reserved
 *   Copyright (C) 2006 Igor Mekterovic
 *   All Rights Reserved
 */

import java.awt.*;
import java.awt.event.*;
import java.io.Serializable;
import javax.swing.*;
import javax.swing.border.*;
/*
 * This class helps to set background and foreground color of TextArea in MdxEditor.
 * @author pyadav
 *
 */
class ColorMenu extends JMenu
{
  protected ColorPane colorPane;
  Color current=Color.WHITE;
  public ColorMenu(String name) {
   super(name); 
   colorPane = new ColorPane();
   colorPane.setBorder(new CompoundBorder(new LineBorder(Color.black),new LineBorder(Color.white)));
   SelectListener selectListener = new SelectListener();
   colorPane.addMouseListener(selectListener);
   add(colorPane);
  }
/*
 * 
 */
  public void setColor(Color c) {
    if (c == null)
    {
      return;
    }
  }
/*
 * Returns selected color.
 */
  public Color getColor() {
      return current;
  }
/*
 * Notify MdxEditor about the changes.
 */
  public void doSelection() {
    fireActionPerformed(new ActionEvent(this,
      ActionEvent.ACTION_PERFORMED, getActionCommand()));
  }
/*
 * Listener class generates event when ColorPane panel gets clicked. 
 * @author pyadav
 *
 */
  class SelectListener extends MouseAdapter implements Serializable {
      public void mousePressed(MouseEvent e) {
	    current = colorPane.getColorForLocation(e.getX(), e.getY());
	    MenuSelectionManager.defaultManager().clearSelectedPath();
	    doSelection();
	}
  }
/*
 * Creates 216 Color Combinations.
 * @author pyadav
 *
 */
  class ColorPane extends JPanel 
  {

      protected Color[] colors;
      protected Dimension paneSize;
      protected Dimension numPane;
      protected Dimension gap;

      public ColorPane() 
      {
          initValues();
          initColors();
          setToolTipText(""); 
          setOpaque(true);
          setBackground(Color.white);
      }
/*
 * Initialize variables. 
 */
      protected void initValues() 
      {
          paneSize = new Dimension(10,10);  	// Color Cell Size in ColorPane
          numPane = new Dimension( 24, 9 );		// 24 Columns and 9 Rows in ColorPane
          gap = new Dimension(1, 1);			// Border size of paneSize.
      }
/*
 *  (non-Javadoc)
 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
 */
      public void paintComponent(Graphics g) 
      {
           g.setColor(getBackground());
           g.fillRect(0,0,getWidth(), getHeight());
           for (int row = 0; row < numPane.height; row++) 
           {
               for (int column = 0; column < numPane.width; column++) 
               {
                   g.setColor( getColorForCell(column, row) ); 
                   int x;
                   if (!this.getComponentOrientation().isLeftToRight()) 
                   {
                       x = (numPane.width - column - 1) * (paneSize.width + gap.width);
                   } 
                   else 
                   {
                       x = column * (paneSize.width + gap.width);
                   }
                   int y = row * (paneSize.height + gap.height);
                   g.fillRect( x, y, paneSize.width, paneSize.height);
                   g.setColor(Color.black);
                   g.drawLine( x+paneSize.width-1, y, x+paneSize.width-1, y+paneSize.height-1);
                   g.drawLine( x, y+paneSize.height-1, x+paneSize.width-1, y+paneSize.height-1);
               }
           }
      }
/*
 *  (non-Javadoc)
 * @see java.awt.Component#getPreferredSize()
 */
      public Dimension getPreferredSize() 
      {
          int w = numPane.width * (paneSize.width + gap.width) -1;
          int h = numPane.height * (paneSize.height + gap.height) -1;
          return new Dimension( w, h );
      }
/*
 * Generates 216 Color combination
 */
      protected void initColors() 
      {          
          colors = new Color[216];
          int counter = 0;
          int[] values = new int[] { 0, 32, 64, 128, 192, 255 };
          for (int r=0; r<values.length; r++) 
          {
              for (int g=0; g<values.length; g++) 
              {
                  for (int b=0; b<values.length; b++) 
                  {
                      /**
                       * Breaking PMD violation rule named AvoidInstantiatingObjectsInLoops but can't do it outside.
                       * by Prakash. 09-05-2007. 
                       */
                      colors[counter] = new Color(values[r], values[g], values[b]);
                      counter++;
                  }
              }
          }
      }

      public String getToolTipText(MouseEvent e) 
      {
          Color color = getColorForLocation(e.getX(), e.getY());
          return color.getRed()+", "+ color.getGreen() + ", " + color.getBlue();
      }

      public Color getColorForLocation( int x, int y ) 
      {
          int column;
          if (!this.getComponentOrientation().isLeftToRight()) 
          {
              column = numPane.width - x / (paneSize.width + gap.width) - 1;
          } 
          else 
          {
              column = x / (paneSize.width + gap.width);
          }
          int row = y / (paneSize.height + gap.height);
          return getColorForCell(column, row);
      }
      
      private Color getColorForCell( int column, int row) 
      {
          return colors[ (row * numPane.width) + column ]; 
      }
  }
}
