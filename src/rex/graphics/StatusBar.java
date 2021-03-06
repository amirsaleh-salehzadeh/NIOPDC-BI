package rex.graphics;

import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import rex.utils.AppColors;
import java.util.Calendar;
import java.util.TimeZone;
import javax.swing.Box;

import rex.utils.*;
import javax.swing.Timer;
import java.text.NumberFormat;
import javax.swing.Action;
import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;
import java.awt.Graphics;
import javax.swing.SwingUtilities;
import java.awt.Rectangle;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import java.awt.Dimension;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


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
 
public class StatusBar extends JPanel 
        implements LanguageChangedListener{
   private JLabel
        status
      , progress
      , dataCellsNo
      , clientTime
      , serverTime
//      , timeDate
      , memUsage;
//   Calendar calendar;
   javax.swing.Timer timer;
   private boolean stopProgressing;
   private long clientStart, clientStop, serverStart, serverStop;
   private static long MB = 1024*1024;
   private static ImageIcon[][] progressIcon;
   public static int JOB_TYPE_NORMAL
                   , JOB_TYPE_CRITICAL;

   static {
     JOB_TYPE_NORMAL   = 0;
     JOB_TYPE_CRITICAL = 1;
     progressIcon = new ImageIcon[2][12];
     // filenames are composed of pbar + first index + second index
     progressIcon[0][0] = S.getAppIcon("pbar01.gif");
     progressIcon[0][1] = S.getAppIcon("pbar02.gif");
     progressIcon[0][2] = S.getAppIcon("pbar03.gif");
     progressIcon[0][3] = S.getAppIcon("pbar04.gif");
     progressIcon[0][4] = S.getAppIcon("pbar05.gif");
     progressIcon[0][5] = S.getAppIcon("pbar06.gif");
     progressIcon[0][6] = S.getAppIcon("pbar07.gif");
     progressIcon[0][7] = S.getAppIcon("pbar08.gif");
     progressIcon[0][8] = S.getAppIcon("pbar09.gif");
     progressIcon[0][9] = S.getAppIcon("pbar010.gif");
     progressIcon[0][10] = S.getAppIcon("pbar011.gif");
     progressIcon[0][11] = S.getAppIcon("pbar012.gif");

     progressIcon[1][0] = S.getAppIcon("pbar11.gif");
     progressIcon[1][1] = S.getAppIcon("pbar12.gif");
     progressIcon[1][2] = S.getAppIcon("pbar13.gif");
     progressIcon[1][3] = S.getAppIcon("pbar14.gif");
     progressIcon[1][4] = S.getAppIcon("pbar15.gif");
     progressIcon[1][5] = S.getAppIcon("pbar16.gif");
     progressIcon[1][6] = S.getAppIcon("pbar17.gif");
     progressIcon[1][7] = S.getAppIcon("pbar18.gif");
     progressIcon[1][8] = S.getAppIcon("pbar19.gif");
     progressIcon[1][9] = S.getAppIcon("pbar110.gif");
     progressIcon[1][10] = S.getAppIcon("pbar111.gif");
     progressIcon[1][11] = S.getAppIcon("pbar112.gif");

   }




   public StatusBar() {
      this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
      this.setBorder(AppColors.STATUS_BAR_BORDER);
      this.setOpaque(true);
      this.setBackground(AppColors.STATUS_BAR_BACKGROUND);
      status= new JLabel(" ");
      
      status.setPreferredSize(new Dimension(Short.MAX_VALUE , 20));
      status.setBorder(AppColors.STATUS_BAR_STATUS_MESSAGE_BORDER);

      progress = new JLabel(" "); //progressIcon[0]
      progress.setBorder(AppColors.STATUS_BAR_PROGRESSBAR_BORDER);
      progress.setMinimumSize(new Dimension(95, 20));
      progress.setPreferredSize(new Dimension(95, 20));
//      S.out("progressIcon[0].getIconWidth() = " + progressIcon[0].getIconWidth()
//             + "progressIcon[0].getIconHeight()=" + progressIcon[0].getIconHeight());
      dataCellsNo = new JLabel(" ");
      dataCellsNo.setBorder(AppColors.STATUS_BAR_DATA_CELLS_NO_BORDER);

      clientTime = new JLabel(" ");
      clientTime.setBorder(AppColors.STATUS_BAR_TIME_BORDER);

      serverTime = new JLabel(" ");
      serverTime.setBorder(AppColors.STATUS_BAR_TIME_BORDER );

      memUsage = new JLabel(" ");
      memUsage.setBorder(AppColors.STATUS_BAR_MEM_USAGE_BORDER);
      refreshMemUsage();

      this.add(status);
      this.add(Box.createHorizontalGlue());
      this.add(progress);
      this.add(dataCellsNo);
      this.add(clientTime);
      this.add(serverTime);
      this.add(memUsage);
      JLabel runGC = new JLabel(S.getAppIcon("garbage_can.gif"));
      runGC.setBorder(AppColors.STATUS_BAR_RUN_GC_BORDER);
      runGC.setMaximumSize(new Dimension(30, 18));
      runGC.addMouseListener(new MouseAdapter(){
         public void mousePressed(MouseEvent e){
         /**
          * Copyright (C) 2006 CINCOM SYSTEMS, INC.
          * All Rights Reserved
          * Copyright (C) 2006 Igor Mekterovic
          * All Rights Reserved
          */ 
             /* implementing localization */
             setStatus(I18n.getString("label.gC"));
               /* end of modification for I18n */
            Runtime.getRuntime().gc();
            refreshMemUsage();
            /**
              * Copyright (C) 2006 CINCOM SYSTEMS, INC.
              * All Rights Reserved
              * Copyright (C) 2006 Igor Mekterovic
              * All Rights Reserved
              */ 
            /* implementing localization */
            setStatus(I18n.getString("statusBar.ready"));
              /* end of modification for I18n */
         }
      });
      this.add(runGC);
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
   
      //
//      this.add(timeDate);
//      updateTimeDate();
//      calendar = Calendar.getInstance();
   }

//   private void updateTimeDate(){
//      timeDate.setText("" + Calendar.getInstance(TimeZone.getDefault()).getTime());
//   }
   public void startClientClock(){
      clientStart = Calendar.getInstance().getTimeInMillis();
//      S.out("clientStart=" + clientStart);
      if(timer == null) {
           stopProgressing = false;
//           timer = new javax.swing.Timer(18, createTickAction());
//           timer.start();
//           S.out("started ticking");
       }

   }
   public void stopClientClock(){
      clientStop = Calendar.getInstance().getTimeInMillis() - clientStart;
//      S.out("clientStop=" + clientStop);
     /**
      * Copyright (C) 2006 CINCOM SYSTEMS, INC.
      * All Rights Reserved
      * Copyright (C) 2006 Igor Mekterovic
      * All Rights Reserved
      */ 
      /* implementing localization */
      calculateTimeDiff(I18n.getString("label.clientTime"), clientTime, clientStop);
      stopProgressing = true;
   }
   public void startServerClock(){
      serverStart = Calendar.getInstance().getTimeInMillis();
//      S.out("serverStart=" + serverStart);
   }
   public void stopServerClock(){
      serverStop = Calendar.getInstance().getTimeInMillis() - serverStart;
//      S.out("serverStop=" + serverStop);
     /**
      * Copyright (C) 2006 CINCOM SYSTEMS, INC.
      * All Rights Reserved
      * Copyright (C) 2006 Igor Mekterovic
      * All Rights Reserved
      */ 
      /* implementing localization */      
      calculateTimeDiff(I18n.getString("label.serverTime") , serverTime, serverStop);
 
   }
   private void calculateTimeDiff(String prefix, JLabel lab, long diff){
      NumberFormat nf = NumberFormat.getInstance();
      nf.setMinimumIntegerDigits(2);
      lab.setText(""
                   + prefix
                   + nf.format(diff/(1000*60))
                   + ":"
                   + nf.format(((diff/1000)%60))
                   + ":"
                   + nf.format((diff%1000)));
   }
   public void setStatus(String _status){
      status.setText(_status);
      S.out("Setting status:\t" + _status);
//      status.repaint();
      paintComponentImmediately(status);
//      S.out("Runtime.getRuntime().freeMemory()=" + Runtime.getRuntime().freeMemory()/(1024*1024)
//                + "\nRuntime.getRuntime().totalMemory()=" + Runtime.getRuntime().totalMemory()/(1024*1024)
//                + "\nRuntime.getRuntime().maxMemory()=" + Runtime.getRuntime().maxMemory()/(1024*1024));
      refreshMemUsage();
//      this.repaint();
//      this.revalidate();
   }
   private void refreshMemUsage(){
      memUsage.setText(Runtime.getRuntime().totalMemory()/MB
                 + "/"
                 + Runtime.getRuntime().maxMemory()/MB
                 + "MB");
   }
   public void setProgressText(String p){
      progress.setText(p);
      progress.repaint();
      this.getRootPane().repaint();
   }
   public String getProgressText(){
      return progress.getText();

   }

   public void setDataCellsNumber(int rowNum, int colNum){
      //dataCellsNo.setText("Data cells:" + rowNum + "x" + colNum + "=" + rowNum*colNum + " cells.");
     /**
      * Copyright (C) 2006 CINCOM SYSTEMS, INC.
      * All Rights Reserved
      * Copyright (C) 2006 Igor Mekterovic
      * All Rights Reserved
      */ 
      /* implementing localization */
      dataCellsNo.setText(I18n.getString("label.dataCells") + rowNum + 
              "x" + colNum + "=" + rowNum*colNum + 
             I18n.getString("label.cells") + " ");
   }

   private void paintComponentImmediately(JComponent c){
   //   c.setVisible(true);
      Rectangle progressRect = c.getBounds();
//      c.x = 0;
//      c.y = 0;
      progressRect.x=0;
      progressRect.y=0;
//      S.out("progressRect = " + progressRect);
      c.paintImmediately(progressRect);
   }

   public void progressJob(Runnable job, int jobType){
      Thread jobThread = new Thread(job);
      progress.setIcon(progressIcon[jobType][0]);
      jobThread.start();
      int loop = 0;
      while (jobThread.isAlive()) {
         try {
            // first progress fast, then slow down:
            // (last step is ~ minute)
            jobThread.join((int)Math.exp((loop>5)? loop: 0) + 100);
         }
         catch (InterruptedException e) {
            throw new RuntimeException(e);
         }
         loop = (loop + 1)%12;
         progress.setIcon(progressIcon[jobType][loop]);
         paintComponentImmediately(progress);
      }
// should I leave this out?
      for(; loop<12; loop++){
         progress.setIcon(progressIcon[jobType][loop]);
         paintComponentImmediately(progress);
      }
      progress.setIcon(null);
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
        status.setText(" ");
        progress.setText(" ");
        dataCellsNo.setText(" ");
        clientTime.setText(" ");
        serverTime.setText(" ");
        memUsage.setText(" ");
          status.setPreferredSize(new Dimension(Short.MAX_VALUE , 20));
          
      progress.setPreferredSize(new Dimension(125, 20));
      dataCellsNo.setPreferredSize(new Dimension(135, 20));
      clientTime.setPreferredSize(new Dimension(135, 20));
              serverTime.setPreferredSize(new Dimension(135, 20));
              
              memUsage.setPreferredSize(new Dimension(125, 20));
    }
   
   /* end of modification for I18n */


}
