package rex.graphics;

import javax.swing.JPanel;
import javax.swing.ImageIcon;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import rex.utils.AppColors;
import java.awt.Graphics;
import rex.utils.S;
import javax.swing.BoxLayout;
import java.awt.Component;
import javax.swing.Box;
import java.awt.Dimension;
import javax.swing.JOptionPane;
import rex.utils.*;//sbalda
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

public class Toolbar extends JPanel 
        implements LanguageChangedListener{ 
   private static ImageIcon showRowTotalsIcon;
   private static ImageIcon showColumnTotalsIcon;
   private static ImageIcon hideRowTotalsIcon;
   private static ImageIcon hideColumnTotalsIcon;
   private static ImageIcon showMDXIcon;
   private static ImageIcon hideMDXIcon;
   private static ImageIcon newQueryIcon;
   private static ImageIcon refreshQueryIcon;
   private static ImageIcon addChartIcon;
   private static ImageIcon closeViewerIcon;
   private static ImageIcon exportToHTMLIcon;
   static{
      showRowTotalsIcon      = S.getAppIcon("toolbox_show_row_totals.gif");
      hideRowTotalsIcon      = S.getAppIcon("toolbox_hide_row_totals.gif");
      showColumnTotalsIcon   = S.getAppIcon("toolbox_show_column_totals.gif");
      hideColumnTotalsIcon   = S.getAppIcon("toolbox_hide_column_totals.gif");
      showMDXIcon            = S.getAppIcon("toolbox_show_mdx.gif");
      hideMDXIcon            = S.getAppIcon("toolbox_hide_mdx.gif");
      newQueryIcon           = S.getAppIcon("toolbox_new_query.gif");
      refreshQueryIcon       = S.getAppIcon("toolbox_refresh_query.gif");
      addChartIcon           = S.getAppIcon("toolbox_add_chart.gif");
      closeViewerIcon        = S.getAppIcon("toolbox_close_viewer.gif");
      exportToHTMLIcon       = S.getAppIcon("toolbox_export_to_html.gif");
   }
   private JLabel showRowTotals
                , showColumnTotals
                , showMDX
                , newQuery
                , refreshQuery
                , addChart
                , closeViewer
                , exportToHTML;

   private boolean showRowTotalsOn, showColumnTotalsOn, showMDXOn;
   private Viewer viewer;

   public Toolbar(Viewer _viewer) {
      viewer = _viewer;
      showRowTotalsOn = false;
      showColumnTotalsOn = false;
      showMDXOn    = false;
//      this.add(newQueryIcon);
//      this.add(newQueryIcon);

      showColumnTotals = new JLabel(showColumnTotalsIcon);
      //showColumnTotals.setToolTipText("Shows/hides COLUMN totals for query result");
      /**
      * Copyright (C) 2006 CINCOM SYSTEMS, INC.
      * All Rights Reserved
      * Copyright (C) 2006 Igor Mekterovic
      * All Rights Reserved
      */
      /* implementing localization */
      showColumnTotals.setToolTipText(I18n.getString("toolTip.showColumnTotals"));
      /* end of modification for I18n */
      showColumnTotals.addMouseListener(new MouseAdapter() {
         public void mouseClicked(MouseEvent e) {
            showColumnTotalsOn = !showColumnTotalsOn;
            if (showColumnTotalsOn) {
               showColumnTotals.setIcon(hideColumnTotalsIcon);
            }
            else {
               showColumnTotals.setIcon(showColumnTotalsIcon);
            }
            viewer.setShowColumnTotalsOn(showColumnTotalsOn);
         }
      });
      showColumnTotals.setBorder(AppColors.TOOLBAR_LABEL_BORDER);



      showRowTotals = new JLabel(showRowTotalsIcon);
    /**
      * Copyright (C) 2006 CINCOM SYSTEMS, INC.
      * All Rights Reserved
      * Copyright (C) 2006 Igor Mekterovic
      * All Rights Reserved
      */
      /* implementing localization */
      showRowTotals.setToolTipText(I18n.getString("toolTip.showRowTotals"));
      /* end of modification for I18n */
      showRowTotals.addMouseListener(new MouseAdapter(){
         public void mouseClicked(MouseEvent e){
            showRowTotalsOn = !showRowTotalsOn;
            if (showRowTotalsOn){
               showRowTotals.setIcon(hideRowTotalsIcon);
            }else{
               showRowTotals.setIcon(showRowTotalsIcon);
            }
            viewer.setShowRowTotalsOn(showRowTotalsOn);
         }
      });
      showRowTotals.setBorder(AppColors.TOOLBAR_LABEL_BORDER);

      showMDX = new JLabel(showMDXIcon);
    /**
      * Copyright (C) 2006 CINCOM SYSTEMS, INC.
      * All Rights Reserved
      * Copyright (C) 2006 Igor Mekterovic
      * All Rights Reserved
      */
      /* implementing localization */
      showMDX.setToolTipText(I18n.getString("toolTip.showMDX"));
      /* end of modification for I18n */
      showMDX.addMouseListener(new MouseAdapter(){
         public void mouseClicked(MouseEvent e){
            showMDXOn = !showMDXOn;
            S.out("showMDXOn is now = " + showMDXOn);
            if (showMDXOn){
               viewer.makeMDXViewer();
               showMDX.setIcon(hideMDXIcon);
            }else{
               viewer.removeMDXViewer();
               showMDX.setIcon(showMDXIcon);
            }
         }
      });
      showMDX.setBorder(AppColors.TOOLBAR_LABEL_BORDER);

      newQuery = new JLabel(newQueryIcon);
    /**
      * Copyright (C) 2006 CINCOM SYSTEMS, INC.
      * All Rights Reserved
      * Copyright (C) 2006 Igor Mekterovic
      * All Rights Reserved
      */
      /* implementing localization */
      newQuery.setToolTipText(I18n.getString("toolTip.dropCurrent"));
      /* end of modification for I18n */
      newQuery.addMouseListener(new MouseAdapter(){
         public void mouseClicked(MouseEvent e){
            viewer.newQuery();
         }
      });
      newQuery.setBorder(AppColors.TOOLBAR_LABEL_BORDER);

      refreshQuery = new JLabel(refreshQueryIcon);
    /**
      * Copyright (C) 2006 CINCOM SYSTEMS, INC.
      * All Rights Reserved
      * Copyright (C) 2006 Igor Mekterovic
      * All Rights Reserved
      */
      /* implementing localization */
      refreshQuery.setToolTipText(I18n.getString("toolTip.refreshQuery"));
     /* end of modification for I18n */
      refreshQuery.addMouseListener(new MouseAdapter(){
         public void mouseClicked(MouseEvent e){
            viewer.refreshQuery();
         }
      });
      refreshQuery.setBorder(AppColors.TOOLBAR_LABEL_BORDER);

      addChart = new JLabel(addChartIcon);
    /**
      * Copyright (C) 2006 CINCOM SYSTEMS, INC.
      * All Rights Reserved
      * Copyright (C) 2006 Igor Mekterovic
      * All Rights Reserved
      */
      /* implementing localization */
      addChart.setToolTipText(I18n.getString("toolTip.addChart"));
      /* end of modification for I18n */
      addChart.addMouseListener(new MouseAdapter(){
         public void mouseClicked(MouseEvent e){
            viewer.addChart();
         }
      });
      addChart.setBorder(AppColors.TOOLBAR_LABEL_BORDER);

      closeViewer = new JLabel(closeViewerIcon);
    /**
      * Copyright (C) 2006 CINCOM SYSTEMS, INC.
      * All Rights Reserved
      * Copyright (C) 2006 Igor Mekterovic
      * All Rights Reserved
      */
      /* implementing localization */
      closeViewer.setToolTipText(I18n.getString("toolTip.closeThisTab"));
      /* end of modification for I18n */
      closeViewer.addMouseListener(new MouseAdapter(){
         public void mouseClicked(MouseEvent e){
            viewer.closeViewer();
         }
      });
      closeViewer.setBorder(AppColors.TOOLBAR_LABEL_BORDER);


      exportToHTML = new JLabel(exportToHTMLIcon);
    /**
      * Copyright (C) 2006 CINCOM SYSTEMS, INC.
      * All Rights Reserved
      * Copyright (C) 2006 Igor Mekterovic
      * All Rights Reserved
      */
      /* implementing localization */
      exportToHTML.setToolTipText(I18n.getString("toolTip.exportToHtml"));
      exportToHTML.addMouseListener(new MouseAdapter(){
         public void mouseClicked(MouseEvent e){
               JOptionPane.showMessageDialog(null
                                        , I18n.getString("msgText.notDone")
                                        ,  I18n.getString("msgTitle.exportToHTML")
                                        , JOptionPane.ERROR_MESSAGE);

         }
      });
      /* end of modification for I18n */
      exportToHTML.setBorder(AppColors.TOOLBAR_LABEL_BORDER);


//      this.setLayout(new FlowLayout(FlowLayout.LEFT));
      this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
      this.add(Box.createRigidArea(new Dimension(2, 0)));
      this.add(newQuery);
      this.add(Box.createRigidArea(new Dimension(2, 0)));
      this.add(exportToHTML);
      this.add(Box.createRigidArea(new Dimension(2, 0)));
      this.add(refreshQuery);
      this.add(Box.createRigidArea(new Dimension(2, 0)));
      this.add(showMDX);
      this.add(Box.createRigidArea(new Dimension(2, 0)));
      this.add(showColumnTotals);
      this.add(Box.createRigidArea(new Dimension(2, 0)));
      this.add(showRowTotals);
      this.add(Box.createRigidArea(new Dimension(2, 0)));
      this.add(addChart);
      this.add(Box.createRigidArea(new Dimension(2, 0)));
      this.add(closeViewer);
      this.setBorder(AppColors.TOOLBAR_BORDER);
      this.setOpaque(false);
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
   }

   public boolean isShowRowTotalsOn(){
      return showRowTotalsOn;
   }
   public boolean isShowColumnTotalsOn(){
      return showColumnTotalsOn;
   }

   public boolean isShowMDXOn(){
      return showMDXOn;
   }
   public void paintComponent(Graphics g) {
      S.paintBackgroundHorizontal(g, this);
      super.paintComponent(g);
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
      showColumnTotals.setToolTipText(I18n.getString("toolTip.showColumnTotals"));
      showRowTotals.setToolTipText(I18n.getString("toolTip.showRowTotals"));
      showMDX.setToolTipText(I18n.getString("toolTip.showMDX"));
      newQuery.setToolTipText(I18n.getString("toolTip.dropCurrent"));
      refreshQuery.setToolTipText(I18n.getString("toolTip.refreshQuery"));
      exportToHTML.setToolTipText(I18n.getString("toolTip.exportToHtml"));
      addChart.setToolTipText(I18n.getString("toolTip.addChart"));
      closeViewer.setToolTipText(I18n.getString("toolTip.closeThisTab"));
    }
   
  /* end of modification for I18n */

}