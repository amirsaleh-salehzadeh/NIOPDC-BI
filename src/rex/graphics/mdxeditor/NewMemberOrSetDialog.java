package rex.graphics.mdxeditor;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
/**
 * Commented to avoid pmd violation named DuplicateImports.
 * by Prakash. 10-05-2007.
 */
//import rex.utils.LanguageChangedListener;
import rex.utils.*;
/**
 * Displays a dialog:
 * <br>- to choose whether to make a calculated member or a named set.
 * <br>- enter a name
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

public class NewMemberOrSetDialog extends JDialog
                                  implements ActionListener,
                                  LanguageChangedListener  { 
   JTextField caption;
   private String selected;
   JButton jb;
   JLabel lblName;
   public static String   MEMBERS_SELECTED = "MEMBER_SELECTED"
                        , SET_SELECTED     = "SET_SELECTED";

   public NewMemberOrSetDialog(JDialog owner) {
      super((JFrame)null, "Member or set?", true);
//      S.out("starting dialog...");
      JPanel jp = new JPanel(new GridLayout (2, 2));

      JRadioButton memberButton = new JRadioButton("Member");
      memberButton.setActionCommand(NewMemberOrSetDialog.MEMBERS_SELECTED);
      memberButton.addActionListener(this);
      memberButton.setSelected(true);

      JRadioButton setButton = new JRadioButton("Set");
      setButton.setActionCommand(NewMemberOrSetDialog.SET_SELECTED);
      setButton.addActionListener(this);



      ButtonGroup group = new ButtonGroup();
      group.add(memberButton);
      group.add(setButton);
      selected = NewMemberOrSetDialog.MEMBERS_SELECTED;


      caption = new JTextField();
      caption.setBackground (Color.white);

      jp.add(memberButton);
      jp.add(setButton);
       
        /**
          * Copyright (C) 2006 CINCOM SYSTEMS, INC.
          * All Rights Reserved
          * Copyright (C) 2006 Igor Mekterovic
          * All Rights Reserved
          */ 
        /* implementing localization */
      lblName=new JLabel(I18n.getString("label.name"));
      /* end of modification for I18n */

      jp.add (lblName);
      jp.add (caption);




      getContentPane().setLayout (new BorderLayout());
      getContentPane().add (jp, BorderLayout.CENTER);
       
    /**
      * Copyright (C) 2006 CINCOM SYSTEMS, INC.
      * All Rights Reserved
      * Copyright (C) 2006 Igor Mekterovic
      * All Rights Reserved
      */ 
    /* implementing localization */
      jb = new JButton (I18n.getString("btn.ok"));
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
       
    /**
      * Copyright (C) 2006 CINCOM SYSTEMS, INC.
      * All Rights Reserved
      * Copyright (C) 2006 Igor Mekterovic
      * All Rights Reserved
      */ 
    /*adding this class to the list of classes that implement I18n */

      I18n.addOnLanguageChangedListener(this);
      applyI18n();
      /* end of modification for I18n */

      setSize(250, 120);
//      pack();
      setVisible(true);
   }


   /**
    * Returns calculated member or named set name that user typed in.
    * @return String
    */
   public String getName(){
      return caption.getText().trim();
   }

   /**
    * Returns a constant (see public members) MEMBERS_SELECTED or SET_SELECTED depending on whether user chose one or another.
    * @return String
    */
   public String selected(){
      return selected;
   }

   /** Listens to the radio buttons. */
   public void actionPerformed(ActionEvent e) {
      selected = e.getActionCommand();
   }

   /**
    * Main function used for testing the class.
    * @param args String[]
    */
   public static void main(String[] args){
      NewMemberOrSetDialog testDialog = new NewMemberOrSetDialog(null);
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
       lblName.setText(I18n.getString("label.name"));
        jb.setText(I18n.getString("btn.ok"));
    }
   
    /* end of modification for I18n */

}
