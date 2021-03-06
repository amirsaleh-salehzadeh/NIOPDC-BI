package rex.graphics.charts;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.GridLayout;
import javax.swing.JCheckBox;
import java.awt.FlowLayout;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.BorderFactory;
import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import rex.utils.*;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.util.ArrayList;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JDialog;
import java.awt.Dimension;

/**
 * <p>Title: WHEX</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author igor
 * @version 1.0
 */
 /**
  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
  * All Rights Reserved
  * Copyright (C) 2006 Igor Mekterovic
  * All Rights Reserved
  */ 
 /*  Added LanguageChangedListener to implement I18n  */

public class ChartPickerDialog extends JDialog 
        implements LanguageChangedListener {
    
   boolean[] chartSelected;
   private static final int OPTIONS_COUNT = 6;
   ArrayList retVal;
   JButton jb; 
 /**
  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
  * All Rights Reserved
  * Copyright (C) 2006 Igor Mekterovic
  * All Rights Reserved
  */ /* implementing localization */
   public ChartPickerDialog(JFrame owner, int[] selChartType) {
       super(owner, I18n.getString("frame.chartPicker"), true);
         /* end of modification for I18n */

      this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
      retVal = new ArrayList();


      JPanel upperPanel = new JPanel();
      upperPanel.setLayout(new GridLayout(OPTIONS_COUNT, 2));

      final Object[][] options = new Object[OPTIONS_COUNT][3];

      options[0][0] = "Vertical bar";
      options[0][1] = Chart.getIconForType(Chart.BAR_VERTICAL);
      options[0][2] = new Integer(Chart.BAR_VERTICAL);

      options[1][0] = "Horizontal bar";
      options[1][1] = Chart.getIconForType(Chart.BAR_HORIZONTAL);
      options[1][2] = new Integer(Chart.BAR_HORIZONTAL);

      options[2][0] = "3D vertical bar";
      options[2][1] = Chart.getIconForType(Chart.BAR_3D_VERTICAL);
      options[2][2] = new Integer(Chart.BAR_3D_VERTICAL);

      options[3][0] = "3D horizontal bar";
      options[3][1] = Chart.getIconForType(Chart.BAR_3D_HORIZONTAL);
      options[3][2] = new Integer(Chart.BAR_3D_HORIZONTAL);

      options[4][0] = "3D pie chart";
      options[4][1] = Chart.getIconForType(Chart.PIE_3D);
      options[4][2] = new Integer(Chart.PIE_3D);

      options[5][0] = "Combined";
      options[5][1] = Chart.getIconForType(Chart.COMBINED);
      options[5][2] = new Integer(Chart.COMBINED);

      chartSelected = new boolean[OPTIONS_COUNT];
      for(int i=0; i < OPTIONS_COUNT; i++){
         chartSelected[i] = false;
         for(int type=0; selChartType != null && type < selChartType.length; type++){
            if (((Integer)options[i][2]).intValue() == selChartType[type]){
               chartSelected[i] = true;
               break;
            }
         }
      }




      JCheckBox chkbx;
      for(int i=0; i<OPTIONS_COUNT; i++){
         chkbx = new JCheckBox((String)options[i][0], chartSelected[i]);
         chkbx.addItemListener(new ChartPickerListener(i));
         upperPanel.add(new JLabel((ImageIcon)options[i][1]));
         upperPanel.add(chkbx);
      }

      upperPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
      this.getContentPane().add(upperPanel, BorderLayout.CENTER);
     /**
      * Copyright (C) 2006 CINCOM SYSTEMS, INC.
      * All Rights Reserved
      * Copyright (C) 2006 Igor Mekterovic
      * All Rights Reserved
      *//* implementing localization */
      jb = new JButton(I18n.getString("btn.selectedCharts")); 
        /* end of modification for I18n */

      jb.addActionListener(new ActionListener(){
         public void actionPerformed(ActionEvent e){
            for(int i=0; i < OPTIONS_COUNT; i++){
               if (chartSelected[i]) {
                  retVal.add(options[i][2]);
               }
            }
            ChartPickerDialog.this.setVisible(false);
            // ChartPickerFrame.this;
         }
      });
      this.getContentPane().add(jb, BorderLayout.SOUTH);
      Dimension myScreenSize = getToolkit().getDefaultToolkit().getScreenSize();
//      S.out("screensize = " + myScreenSize
//            + " w=" + this.getWidth()
//            + " h=" + this.getHeight());
      this.setLocation( (myScreenSize.width/2 - 200) //-  this.getWidth()/2
                      , (myScreenSize.height/2) -200 ); // - this.getHeight()/2)
     /**
      * Copyright (C) 2006 CINCOM SYSTEMS, INC.
      * All Rights Reserved
      * Copyright (C) 2006 Igor Mekterovic
      * All Rights Reserved
      *//* implementing localization */
          /*adding this class to the list of classes that implement I18n */

      I18n.addOnLanguageChangedListener(this);
      applyI18n();
        /* end of modification for I18n */
               
   }

   public Integer[] getSelectedChartTypes(){
      return (Integer[])retVal.toArray(new Integer[0]);
   }


   class ChartPickerListener implements ItemListener{
      int myIndex;
      ChartPickerListener(int index){
         myIndex = index;
      }
      public void itemStateChanged(ItemEvent e) {
         if (e.getStateChange() == ItemEvent.SELECTED) {
            chartSelected[myIndex] = true;
         }else{
            chartSelected[myIndex] = false;
         }

      }
   }


   public static void main(String[] args) {

      JFrame frame = new JFrame("testing");
      frame.addWindowListener(new WindowAdapter() {
         public void windowClosing(WindowEvent e) {
            System.exit(0);
         }
      });
      JDialog dialog = new ChartPickerDialog((JFrame)frame.getRootPane().getParent(), new int[]{1,3});

      frame.pack();
      frame.setVisible(true);

      dialog.pack();
      dialog.setVisible(true);

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
      jb.setText(I18n.getString("btn.selectedCharts"));
    }
  /* end of modification for I18n */

}

