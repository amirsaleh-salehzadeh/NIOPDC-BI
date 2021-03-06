package rex.graphics.mdxeditor;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.JTextField;

import rex.utils.*;
/**
 * Displays a dialog:
 * <br>- to enter cell properties for calculated member
 * @author Igor Mekterovic
 * @version 0.3
 */
 /**
  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
  * All Rights Reserved
  * Copyright (C) 2006 Igor Mekterovic
  * All Rights Reserved
  */ 
 /*  Added LanguageChangedListener to implement I18n  */
 
public class CellPropertiesDialog extends JDialog
implements LanguageChangedListener{ 
   private JComboBox cbFormats;
   private JTextField foreColor;
   private JTextField backColor;
   private JTextField solveOrder;

   private JLabel lblSolve;
   private JLabel lblBack;
   private JLabel lblFore;
   private JLabel lblFormat;
   private JButton jb;


   public CellPropertiesDialog(JDialog owner) {
 /**
  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
  * All Rights Reserved
  * Copyright (C) 2006 Igor Mekterovic
  * All Rights Reserved
  */ 
/* implementing localization */
 
       super((JFrame)null, I18n.getString("frame.cellProperties"), true);
      //super((JFrame)null, "Please enter calulated member formating", true);
//      S.out("starting dialog...");
     
        lblSolve=new JLabel(I18n.getString("label.solveOrder"));
        lblBack=new JLabel(I18n.getString("label.backColor"));
        lblFore=new JLabel(I18n.getString("label.foreColor"));
        lblFormat=new JLabel(I18n.getString("label.formatString"));
  /* end of modification for I18n */


      JPanel jp = new JPanel(new GridLayout (4, 2));

      jp.add (lblSolve);
      solveOrder = new JTextField();
      jp.add (solveOrder);


      jp.add (lblBack);
      backColor = new JTextField();
      jp.add (backColor);

      jp.add (lblFore);
      foreColor = new JTextField();
      jp.add (foreColor);


      String formats[] = new String[]{
           ""
         , "#,#"
         , "#,#.00"
         , "#,#.00"
         , "Standard"
         , "Currency"
         , "Short Date"
         , "Short Time"
         , "Percent"
      };

      cbFormats = new JComboBox( formats );
      cbFormats.setMaximumSize(cbFormats.getPreferredSize());
      cbFormats.setEditable( true );

      cbFormats.setRequestFocusEnabled( false );
      cbFormats.setBackground(Color.white);
//      cbFormats.addActionListener( new cbTabSizeListener());


      jp.add (lblFormat); 
      jp.add (cbFormats);



      getContentPane().setLayout (new BorderLayout());
      getContentPane().add (jp, BorderLayout.CENTER);
       /**
  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
  * All Rights Reserved
  * Copyright (C) 2006 Igor Mekterovic
  * All Rights Reserved
  */ 
      jb = new JButton(I18n.getString("btn.ok"));
        /* end of modification for I18n */

      jb.addActionListener (new ActionListener() {
         public void actionPerformed (ActionEvent e) {
            dispose();
         }
      });
      getContentPane().add (jb, BorderLayout.SOUTH);
      Dimension sd = Toolkit.getDefaultToolkit().getScreenSize();
      setLocation(sd.width / 2 - 250 / 2, sd.height / 2 - 120 / 2);
      setResizable(false);
      setSize(350, 160);
//      pack();
      setVisible(true);
   }


   /**
    * Returns calculated member or named set name that user typed in.
    * @return String
    */
   public String getFormat(){
      StringBuffer format = new StringBuffer("");

      if (!solveOrder.getText().trim().equals("")){
         format.append("SOLVE_ORDER='" + solveOrder.getText().trim() + "'");
      }

      if (!backColor.getText().trim().equals("")){
          if (format.length()>0) format.append(", ");
         format.append("BACK_COLOR='" + backColor.getText().trim() + "'");
      }
      if (!foreColor.getText().trim().equals("")){
         if (format.length()>0) format.append(", ");
         format.append("FORE_COLOR='" + foreColor.getText().trim() + "'");
      }
      if (!cbFormats.getSelectedItem().toString().trim().equals("")){
         if (format.length()>0) format.append(", ");
         format.append("FORMAT_STRING='" + cbFormats.getSelectedItem() + "'");
      }

      return format.toString();
   }



   /**
    * Main function used for testing the class.
    * @param args String[]
    */
   public static void main(String[] args){
      CellPropertiesDialog testDialog = new CellPropertiesDialog(null);
      System.out.println(testDialog.getFormat());
      testDialog.dispose();
   }
 /**
  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
  * All Rights Reserved
  * Copyright (C) 2006 Igor Mekterovic
  * All Rights Reserved
  */
 /**
  * Helper method that is executed when the language is changed
  */

    public void languageChanged(LanguageChangedEvent evt) {
        this.applyI18n();
    }
  /**
  *  Helper method to implement locatization when language is changed
  */  
    public void applyI18n(){
        lblSolve.setText(I18n.getString("label.solveOrder"));
        lblBack.setText(I18n.getString("label.backColor"));
        lblFore.setText(I18n.getString("label.foreColor"));
        lblFormat=new JLabel(I18n.getString("label.formatString"));
        jb.setText(I18n.getString("btn.ok"));
    }
  /* end of modification for I18n */

}
