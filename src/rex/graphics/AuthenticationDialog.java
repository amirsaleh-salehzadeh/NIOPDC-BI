package rex.graphics;

import javax.swing.JDialog;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import rex.utils.LanguageChangedListener;
import rex.utils.S;
import javax.swing.JFrame;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Font;
import rex.utils.*;

/**
 * Copyright (C) 2006 CINCOM SYSTEMS, INC.
 * All Rights Reserved
 * Copyright (C) 2006 Igor Mekterovic
 * All Rights Reserved
 */ 
 /*  Added LanguageChangedListener to implement I18n  */
 
public class AuthenticationDialog 
        extends JDialog 
        implements LanguageChangedListener{
    
   JTextField username;
   JPasswordField password;
   /* declared variabled to implement I18n */
   JLabel lblUserName;
   JLabel lblPassword;
   JButton jb;
   /* end of modification for I18n */
   
   public AuthenticationDialog(JDialog owner) {
       super((JFrame)null, I18n.getString("frame.authentication"), true);

//      S.out("starting dialog...");
      JPanel jp = new JPanel(new GridLayout (2, 2));
    /**
     * Copyright (C) 2006 CINCOM SYSTEMS, INC.
     * All Rights Reserved
     * Copyright (C) 2006 Igor Mekterovic
     * All Rights Reserved
     */
      lblUserName=new JLabel(I18n.getString("label.userName"));
      lblPassword=new JLabel(I18n.getString("label.password"));
      jb= new JButton(I18n.getString("btn.ok"));
      jp.add (lblUserName);
       /* end of modification for I18n */
      username = new JTextField();
      username.setFont(new Font("Monospaced", Font.BOLD, 16)); // username.getFont().deriveFont(16.f)
      //username.setBackground (Color.lightGray);
      jp.add (username);
   
      jp.add (lblPassword);
      password = new JPasswordField();
      //password.setBackground (Color.lightGray);
      password.setFont(new Font("Monospaced", Font.BOLD, 16));
      jp.add (password);
      getContentPane().setLayout (new BorderLayout());
      getContentPane().add (jp, BorderLayout.CENTER);

      
      jb.addActionListener (new ActionListener() {
         public void actionPerformed (ActionEvent e) {
            S.out("disposing...");
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
      /*adding this class to the list of classes to implement I18n */
      I18n.addOnLanguageChangedListener(this);
      applyI18n();
     /* end of modification for I18n */
      setSize(250, 120);
//      pack();
      setVisible(true);
   }

   public String getPassword(){
      return new String(password.getPassword());
   }
   public char[] getPasswordCA(){
      return password.getPassword();
   }

   public String getUsername(){
      return username.getText();
   }

   public static void main(String[] args){
     new AuthenticationDialog(null);
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
      lblUserName.setText(I18n.getString("label.userName"));
      lblPassword.setText(I18n.getString("label.password"));
      jb.setText(I18n.getString("btn.ok"));
    }
   /* end of modification for I18n */
}
