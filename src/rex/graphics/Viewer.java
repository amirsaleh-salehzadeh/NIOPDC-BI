package rex.graphics;


import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JFrame;
import java.awt.*;
import java.awt.event.*;
import javax.swing.JPanel;
import rex.metadata.ServerMetadata;

import javax.swing.ToolTipManager;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.ImageIcon;

import rex.utils.S;
import rex.graphics.dimensiontree.*;
import rex.metadata.*;
import javax.swing.tree.TreePath;
import javax.swing.JPopupMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.dnd.DnDConstants;
import rex.utils.UIPropertyManager;
import javax.swing.BoxLayout;
import java.util.LinkedList;
import rex.utils.AppColors;
import rex.metadata.resultelements.Tuple;
import javax.swing.JTabbedPane;
import java.util.Iterator;
import java.util.ListIterator;
import rex.metadata.resultelements.Member;
import rex.graphics.filtertree.elements.FilterTreeRootElement;
import javax.swing.JComponent;
import rex.graphics.charts.Chart;
import rex.graphics.charts.ChartPickerDialog;
import java.util.ArrayList;
import javax.swing.SwingUtilities;
import rex.graphics.dimensiontree.elements.DimensionElement;
import rex.graphics.filtertree.*;
import rex.graphics.dimensiontree.DimensionTree;
import rex.graphics.dimensiontree.DimensionTreeModel;
import rex.graphics.filtertree.FilterTreeModel;
import rex.graphics.filtertree.FilterTree;
import org.w3c.dom.Document;
import rex.xmla.XMLADiscoverRestrictions;
import rex.xmla.XMLADiscoverProperties;
import rex.xmla.XMLAExecuteProperties;
import rex.xmla.XMLAObjectsFactory;
import rex.graphics.dimensiontree.dnd.TreeDragSource;
import rex.utils.I18n;

/**
 * <p>Title: WHEX</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author igor
 * @version 1.0
 */

public class Viewer extends JPanel implements IViewer{
   private Toolbar toolbar;
   private DimensionTree dimTree;
   private JSplitPane splitPane;
   private JPanel rightPane;
   private JPanel leftPane;
   private LinkedList filterTrees;
   private LinkedList chartTabs;
   private TreeDragSource ds;
   private Query q;
   private LinkedList chapters;
   private StatusBar statusBar;
   private XMLADiscoverRestrictions restrictions;
   private XMLADiscoverProperties properties;
   private XMLAExecuteProperties execProperties;
   private ServerMetadata smd;
   private Document d;
   private ExecuteResult er;
   private static int MAX_CHAPTER_COUNT = 10;
   private boolean errorOccured;
   private String errorMessage;
   private RexTabbedPane parentTabbedPane;
   private MDXViewer mdxViewer;
   private CubeExplorer2 cubeExplorer;
   private static ImageIcon tableIcon, chartIcon;
   static {
      tableIcon = S.getAppIcon("table.gif");
      chartIcon = S.getAppIcon("chart.gif");
   }


   public Viewer(    XMLADiscoverRestrictions _restrictions
                  ,  XMLADiscoverProperties   _properties
                  ,  ServerMetadata _smd
                  ,  JTabbedPane    _parentTabbedPane){
      this(_restrictions, _properties, _smd);
      parentTabbedPane = (RexTabbedPane)_parentTabbedPane;
   }


   public Viewer(    XMLADiscoverRestrictions  _restrictions
                  ,  XMLADiscoverProperties    _properties
                  ,  ServerMetadata _smd){
      restrictions = _restrictions;
      properties   = _properties;
      smd = _smd;
      execProperties = XMLAObjectsFactory.newXMLAExecuteProperties();


      execProperties.setCatalog(properties.getCatalog());
      execProperties.setDataSourceInfo(properties.getDataSourceInfo());


      q = new Query(_restrictions.getCubeName(), this);

      dimTree = new DimensionTree(_restrictions, _properties, _smd, q);


      ds = new TreeDragSource(dimTree.getTree(), DnDConstants.ACTION_COPY_OR_MOVE);
      rightPane = new JPanel(){
//         Image image = imageIcon.getImage();
//         Image grayImage = GrayFilter.createDisabledImage(image);
         {
            setOpaque(false);
         } // instance initializer

         public void paintComponent(Graphics g) {
            S.paintBackground(g, this);
            super.paintComponent(g);
         }
      };

      rightPane.setLayout(new BoxLayout(rightPane, BoxLayout.Y_AXIS));
      rightPane.setBackground(Color.BLACK);

      chapters = new LinkedList();
      Page c = new Page(dimTree.getTree(), q);
      c.setCurrent(true);
      chapters.add(c);
      rightPane.add(c);

      leftPane = new JPanel();
      leftPane.setLayout(new BorderLayout());
      leftPane.add(dimTree, BorderLayout.CENTER);

//      splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT
//                                 , leftPane
//                                 , new JScrollPane(rightPane));
      splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT
                                 , leftPane
                                 , rightPane);

      splitPane.setContinuousLayout(false);
      splitPane.setOneTouchExpandable(true);
      splitPane.setDividerSize(8);
      splitPane.setDividerLocation((int)dimTree.getMinimumSize().getWidth());

      this.setLayout(new BorderLayout());
      toolbar = new Toolbar(this);
      this.add(toolbar, BorderLayout.NORTH);

      this.add(splitPane, BorderLayout.CENTER);

      statusBar = new StatusBar();
      this.add(statusBar, BorderLayout.SOUTH);

   }
//   private void setExecuteResult(ClsXMLAProxExecuteResult _r){
//      r = _r;
//   }

   public void refreshQuery(){

      if (q.canExecute()){
         statusBar.startServerClock();
          /**
          * Copyright (C) 2006 CINCOM SYSTEMS, INC.
          * All Rights Reserved
          * Copyright (C) 2006 Igor Mekterovic
          * All Rights Reserved
          */          /* implementing localization */
         statusBar.setStatus(I18n.getString("statusBar.execQuery"));
          /* end of modification for I18n */

         errorOccured = false;
         statusBar.progressJob(new Runnable(){
            public void run(){
               try{
                  d = smd.execute(q.getQuery(), execProperties);
               }catch(Exception e){
                  errorOccured = true;
                  errorMessage = e.getMessage();
                  //e.printStackTrace();//Commented by Prakash
//                  statusBar.setStatus("An error occured while executing a query :(");
                  return;
               }
            }
         }, StatusBar.JOB_TYPE_CRITICAL);
         statusBar.stopServerClock();
         if (!errorOccured){
              /**
              * Copyright (C) 2006 CINCOM SYSTEMS, INC.
              * All Rights Reserved
              * Copyright (C) 2006 Igor Mekterovic
              * All Rights Reserved
              */ /* implementing localization */
            statusBar.setStatus(I18n.getString("statusBar.resultDisplay"));
              /* end of modification for I18n */

            statusBar.startClientClock();

            statusBar.progressJob(new Runnable() {
               public void run() {
                  try{
                     er = new ExecuteResult(d, q);
                  }catch(ExecuteResultParseException e){
                /**
                  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
                  * All Rights Reserved
                  * Copyright (C) 2006 Igor Mekterovic
                  * All Rights Reserved
                  */  /* implementing localization */
                      statusBar.setStatus(I18n.getString("statusBar.errorOccured"));
                  /* end of modification for I18n */
     
                     errorOccured = true;
                     errorMessage = e.getMessage();
                     //e.printStackTrace();//Commented by Prakash
                     return;
                  }
                  if (!er.isValid()) {
                     if (er.getAxis("Axis1").getTupleCount() == 0) {
                    /**
                      * Copyright (C) 2006 CINCOM SYSTEMS, INC.
                      * All Rights Reserved
                      * Copyright (C) 2006 Igor Mekterovic
                      * All Rights Reserved
                      */  /* implementing localization */
                         statusBar.setStatus(I18n.getString("statusBar.errAxis1"));
                           /* end of modification for I18n */

                        q.clearRowAxis();
                     }
                     else if (er.getAxis("Axis0").getTupleCount() == 0) {
                    /**
                      * Copyright (C) 2006 CINCOM SYSTEMS, INC.
                      * All Rights Reserved
                      * Copyright (C) 2006 Igor Mekterovic
                      * All Rights Reserved
                      */  /* implementing localization */
                         statusBar.setStatus(I18n.getString("statusBar.errAxis0"));
                           /* end of modification for I18n */

                        q.clearColumnAxis();
                     }
                     //((Chapter)chapters.getFirst()).refreshDisplay();
                     return;
                  }
                  q.updateQueryWithResults(er);

                  rightPane.removeAll();
                  JComponent rightPaneTableContent;
                  int chapterCount;
                  if (er.getAxis("Axis2") != null) {
                    // *****************        THERE ARE PAGES !!!  *************************/
                     chapterCount = er.getAxis("Axis2").getTupleCount();
                     chapters.clear();
                     // here we make different cube slicers and make new cube explorers
                     // one for each chapter
                     final JTabbedPane jtp = new JTabbedPane(JTabbedPane.TOP);

                     for (int cc = 0; cc < chapterCount && cc < MAX_CHAPTER_COUNT; cc++) {
                        CubeSlicer cs = new CubeSlicer( (short) 1
                           , (short) 0
                           , new short[] {2}
                           , new Tuple[] {er.getAxis("Axis2").getTupleAt(cc)}
                           );
                        cubeExplorer = new CubeExplorer2(er, cs, toolbar.isShowColumnTotalsOn(), toolbar.isShowRowTotalsOn());
                        cubeExplorer.setMaximumSize(new Dimension(Short.MAX_VALUE, cubeExplorer.getPrefferedHeight()));

                        Page c = new Page(dimTree.getTree(), q, cubeExplorer);
                        c.setMaximumSize(new Dimension(Short.MAX_VALUE, Page.getAdditionalHeight() + cubeExplorer.getPrefferedHeight()));
                        chapters.add(c);
                        // this is heavy - now I have to check if there are any charts to display
                        // chart tabs ARE INSIDE page tabs!
                        if (chartTabs != null && chartTabs.size() > 0) {
                           JTabbedPane chartTabbedPane = new JTabbedPane(JTabbedPane.RIGHT);
                           chartTabbedPane.addTab(null, tableIcon, c);
                           Iterator it = chartTabs.iterator();
                           Integer type;
                           while (it.hasNext()) {
                              type = (Integer) it.next();
                              // I'm creating a NEW chart that is the same type as the one in the chartTabs
                              // In the case when I have pages (e.g. 3 pages) and charts (e.g. 2 charts)
                              // I will create 3*2 charts.
                              Chart pageChart  = new Chart(er, cs, type.intValue());
                              chartTabbedPane.addTab(null, pageChart.getIcon(), pageChart);
                           }

                           jtp.addTab(er.getAxis("Axis2").getTupleAt(cc).getShortCaption()
                                      , null
                                      , chartTabbedPane
                                      , er.getAxis("Axis2").getTupleAt(cc).getToolTip()
                                      );

                        }else{
                           JPanel holder = new JPanel(){
                              {
                                 setOpaque(false);
                              } // instance initializer

                              public void paintComponent(Graphics g) {
                                 S.paintBackground(g, this);
                                 super.paintComponent(g);
                              }
                           };

                           holder.setLayout(new BoxLayout(holder, BoxLayout.Y_AXIS));
                           holder.add(c);


                           jtp.addTab(er.getAxis("Axis2").getTupleAt(cc).
                                      getShortCaption()
                                      , null
                                      , holder
                                      , er.getAxis("Axis2").getTupleAt(cc).getToolTip()
                                      );
                        }

                     }
                     jtp.addMouseListener(new PageLabelPopUpListener(jtp));
                     rightPane.add(jtp);


                  }else{
                     // *****************        NO PAGES  *************************/
                     chapterCount = 1;
                     chapters.clear();

                     CubeSlicer cs = new CubeSlicer( (short) 1, (short) 0);
                     cubeExplorer = new CubeExplorer2(er, cs, toolbar.isShowColumnTotalsOn(), toolbar.isShowRowTotalsOn());
                     cubeExplorer.setMaximumSize(new Dimension(Short.MAX_VALUE, cubeExplorer.getPrefferedHeight()));

                     Page c = new Page(dimTree.getTree(), q, cubeExplorer);
                     c.setMaximumSize(new Dimension(Short.MAX_VALUE, Page.getAdditionalHeight() + cubeExplorer.getPrefferedHeight()));
                     c.setCurrent(true);
                     chapters.add(c);

                     if (chartTabs != null && chartTabs.size()>0){
                        JTabbedPane chartTabbedPane = new JTabbedPane(JTabbedPane.RIGHT);
                        chartTabbedPane.addTab(null, tableIcon, c);
                        Iterator it = chartTabs.iterator();
                        Integer type;
                        Chart chart;
                        while (it.hasNext()) {
                           type = (Integer) it.next();
                           chart = new Chart(er, cs, type.intValue());
                           chartTabbedPane.addTab(null, chart.getIcon(), chart);
                        }
                        rightPane.add(chartTabbedPane);
                     }
                     else {
                        rightPane.add(c);
                     }
                  }


                  rightPane.repaint();
                  rightPane.invalidate();
                  rightPane.revalidate();
                  ListIterator it = chapters.listIterator();
                  Page currentChapter;
                  while (it.hasNext()) {
                     if ( ( (Page) it.next()).isCurrent()) {
                        ( (Page) it.previous()).getCubeExplorer().setPrefferedDividerLocation();
                        break;
                     }
                  }
                  if (toolbar.isShowMDXOn()){
                     if (mdxViewer != null){
                        mdxViewer.addQuery(q.getLastQueryGenerated());
                     }else{
                        S.out("assert: trying to call getLastQueryGenerated() on a NULL object(mdxViewer)");
                     }
                  }
                  //              ce.setPrefferedDividerLocation();
               }
            }, StatusBar.JOB_TYPE_NORMAL);


         }

         if (!errorOccured){
            statusBar.setDataCellsNumber(er.getAxis("Axis1").getTupleCount(),
                                         er.getAxis("Axis0").getTupleCount());
            statusBar.stopClientClock();
         /**
          * Copyright (C) 2006 CINCOM SYSTEMS, INC.
          * All Rights Reserved
          * Copyright (C) 2006 Igor Mekterovic
          * All Rights Reserved
          */     /* implementing localization */        
            statusBar.setStatus(I18n.getString("statusBar.ready"));
  /* end of modification for I18n */

         }else{
              /**
              * Copyright (C) 2006 CINCOM SYSTEMS, INC.
              * All Rights Reserved
              * Copyright (C) 2006 Igor Mekterovic
              * All Rights Reserved
              */ /* implementing localization */
            JOptionPane.showMessageDialog(  null
                                          , I18n.getString("msgText.errQueryExec") + errorMessage
                                          , I18n.getString("msgTitle.execQuery")
                                          , JOptionPane.ERROR_MESSAGE);
             
            statusBar.setStatus(I18n.getString("statusBar.errorExec"));
              /* end of modification for I18n */

            
         }


      }else{
         // refresh EmptyResultTable
         ((Page)chapters.getFirst()).refreshDisplay();
      }

   }

   public void enableTreeElements( QueryElement elementToEnable){
       //System.out.println(elementToEnable.getUniqueName()+" from Viewer");
      ((DimensionTreeModel) dimTree.getTree().getModel()).enableTreeElements( elementToEnable);
   }
//   public JTree getDimensionTree(){
//      return dimTree.getTree();
//   }
   public FilterTreeModel addMemberToFilter(Member filterMember){
      if (filterTrees == null){
         filterTrees = new LinkedList();
      }
      LinkedList eml = new LinkedList();
      eml.add(filterMember);
      FilterTree filterTree = new FilterTree(  smd
                                             , q
                                             , ((DimensionTreeModel)dimTree.getTree().getModel()).getDimensionTreeElement(filterMember.getDimensionUniqueName())
                                             , eml
                                             , this);
      filterTrees.add(filterTree);
      refreshLeftPane();
      return (FilterTreeModel)filterTree.getTree().getModel();
   }
   public void dropFilterTree(FilterTree filterToDrop){
      ListIterator it = filterTrees.listIterator();
      while(it.hasNext()){
         if((FilterTree)it.next() == filterToDrop){
            it.previous();
            it.remove();
            refreshLeftPane();
            return;
         }
      }
      S.out("assert: Viewer:dropFilterTree:Couldn't find filter to drop!");
   }
   public void refreshFilterDisplay(){
      refreshLeftPane();
   }
   private void refreshLeftPane(){
      if (filterTrees != null && filterTrees.size()>0){
         leftPane.removeAll();
         // the whole display depends on the FIRST filterTree - the first one is the boss !
         if (!((FilterTree)filterTrees.getFirst()).isDisplayedInTab()){
            JPanel filtersPane = new JPanel();
            filtersPane.setLayout(new BoxLayout(filtersPane, BoxLayout.Y_AXIS));
            Iterator it = filterTrees.iterator();
            while (it.hasNext()) {
               filtersPane.add( (JPanel) it.next());
            }
            JSplitPane dimFilterSplitPane = new JSplitPane(JSplitPane.
               HORIZONTAL_SPLIT
               , dimTree
               , new JScrollPane(filtersPane));
            dimFilterSplitPane.setContinuousLayout(false);
            dimFilterSplitPane.setOneTouchExpandable(true);
            dimFilterSplitPane.setDividerSize(8);
            dimFilterSplitPane.setDividerLocation( (int) dimTree.getMinimumSize().getWidth());
            leftPane.add(dimFilterSplitPane);
            splitPane.setDividerLocation(  (int) dimTree.getMinimumSize().getWidth()
                                         + (int) filtersPane.getMinimumSize().getWidth());
         }else{
            JTabbedPane dimFilterTabbedPane = new JTabbedPane(JTabbedPane.LEFT);
            dimFilterTabbedPane.addTab(null
                                   , DimensionElement.getDimensionIcon()
                                   , dimTree
                                   , "Dimension tree used for browsing and generating queries."
                                   );
            
            Iterator it = filterTrees.iterator();
            int i=1;
            FilterTree ft;
            while (it.hasNext()) {
               ft = (FilterTree)it.next();
               dimFilterTabbedPane.addTab(   null // "Filter" + i
                                           , FilterTreeRootElement.getFilterIcon()
                                           , ft
                                           , ft.getCaption());
               i++;
            }
            leftPane.add(dimFilterTabbedPane);
         }
      }else{
         leftPane.removeAll();
         leftPane.add(dimTree, BorderLayout.CENTER);
      }
      this.invalidate();
      this.revalidate();
      this.repaint();
   }

   void makeMDXViewer(){
      if (mdxViewer == null){
         mdxViewer = new MDXViewer();
         parentTabbedPane.addMDXViewer(mdxViewer, restrictions.getCubeName());
      }
   }
   void closeViewer(){
        /**
  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
  * All Rights Reserved
  * Copyright (C) 2006 Igor Mekterovic
  * All Rights Reserved
  */           /* implementing localization */
      if (JOptionPane.YES_OPTION == JOptionPane.showConfirmDialog(
                    null
                  , I18n.getString("msgText.closeTab")
                  , I18n.getString("msgTitle.closeTab")
                  , JOptionPane.YES_NO_OPTION)){

            /* end of modification for I18n */

         removeMDXViewer();
         parentTabbedPane.closeViewer(this);
      }

   }
   void removeMDXViewer(){
      if (mdxViewer != null){
         parentTabbedPane.removeMDXViewer(mdxViewer);
         mdxViewer = null;
      }
   }
   void setShowColumnTotalsOn(boolean _showColumnTotalsOn){
      if (cubeExplorer != null){
         cubeExplorer.setShowColumnTotalsOn(_showColumnTotalsOn);
      }
   }

   void setShowRowTotalsOn(boolean _showRowTotalsOn){
      if (cubeExplorer != null){
         cubeExplorer.setShowRowTotalsOn(_showRowTotalsOn);
      }
   }
   void newQuery(){
      if (filterTrees != null){
         filterTrees = null;
         refreshFilterDisplay();
      }
      chartTabs = null;
      chapters = new LinkedList();
      q = new Query(restrictions.getCubeName(), this);
      ((DimensionTreeModel)dimTree.getTree().getModel()).enableAllTreeElements();
      dimTree.setQuery(q);
      Page c = new Page(dimTree.getTree(), q);
      c.setCurrent(true);
      chapters.add(c);
      rightPane.removeAll();
      rightPane.add(c);
      this.invalidate();
      this.revalidate();
      this.repaint();
   }
   public void addChart(){
      // chartTabs hold Integer values that represent charts being displayed
      int[] selectedCharts = null;
      if (chartTabs == null){
         chartTabs = new LinkedList();
      }else{
         selectedCharts = new int[chartTabs.size()];
         Iterator it = chartTabs.iterator();
         int i=0;
         while(it.hasNext()){
            selectedCharts[i] = ((Integer)it.next()).intValue();
            i++;
         }
         chartTabs.clear();
      }
      ChartPickerDialog chartPicker = new ChartPickerDialog((JFrame)this.getRootPane().getParent()
                                                           , selectedCharts);
      chartPicker.pack();

      chartPicker.setVisible(true);
      Integer[] chartsToSpawn = chartPicker.getSelectedChartTypes();
      for(int i=0; i<chartsToSpawn.length; i++){
         chartTabs.addLast(new Integer(chartsToSpawn[i].intValue())); //new Chart(er, new CubeSlicer( (short) 1, (short) 0), chartsToSpawn[i].intValue())
      }
      refreshQuery();
   }




   class PageLabelPopUpListener extends MouseAdapter implements ActionListener{
      JTabbedPane jtp;
      JPopupMenu popup;
      JMenuItem menuItem;
      int idx;
      PageLabelPopUpListener(JTabbedPane _jtp){
         jtp = _jtp;
         popup = new JPopupMenu();

         menuItem = new JMenuItem( (String) TupleMemberLabelPopUpActions.popUpCaptions.get(TupleMemberLabelPopUpActions.KEEP_THIS_MEMBER_ONLY));
         menuItem.addActionListener(this);
         popup.add(menuItem);

         menuItem = new JMenuItem( (String) TupleMemberLabelPopUpActions.popUpCaptions.get(TupleMemberLabelPopUpActions.REMOVE_MEMBER_FROM_QUERY));
         menuItem.addActionListener(this);
         popup.add(menuItem);

         menuItem = new JMenuItem( (String) TupleMemberLabelPopUpActions.popUpCaptions.get(TupleMemberLabelPopUpActions.REMOVE_DIMENSION_FROM_QUERY));
         menuItem.addActionListener(this);
         popup.add(menuItem);

         menuItem = new JMenuItem( (String) TupleMemberLabelPopUpActions.popUpCaptions.get(TupleMemberLabelPopUpActions.SEND_MEMBER_TO_FILTER));
         menuItem.addActionListener(this);
         popup.add(menuItem);


      }
      public void mouseClicked(MouseEvent e) {
//         S.out("mouse clicked");
         if (SwingUtilities.isRightMouseButton(e)) {
            S.out("idx=" + jtp.indexAtLocation(e.getX(), e.getY())); //
            idx = jtp.indexAtLocation(e.getX(), e.getY());
            popup.show(e.getComponent()
                       , e.getX()
                       , e.getY());
         }
         if (e.isPopupTrigger()) {
            S.out("e.getX()=" + e.getX()
                  + "e.getY()=" + e.getY()
                  + "point = " + e.getPoint()
                  + " E = " + e);
         }
      }

      public void actionPerformed(ActionEvent e){
         int i = 0;
         // To do:
         if (e.getActionCommand().equals((String)TupleMemberLabelPopUpActions.popUpCaptions.get(TupleMemberLabelPopUpActions.REMOVE_DIMENSION_FROM_QUERY))){
            q.removePageDimensionFromQuery(er.getAxis("Axis2").getTupleAt(idx).getMemberAt(0));
         }else if (e.getActionCommand().equals((String)TupleMemberLabelPopUpActions.popUpCaptions.get(TupleMemberLabelPopUpActions.REMOVE_MEMBER_FROM_QUERY))){
            q.removePageMemberFromQuery(er.getAxis("Axis2").getTupleAt(idx).getMemberAt(0));
         }else if (e.getActionCommand().equals((String)TupleMemberLabelPopUpActions.popUpCaptions.get(TupleMemberLabelPopUpActions.KEEP_THIS_MEMBER_ONLY))){
            q.keepThisMemberOnlyOnPages(er.getAxis("Axis2").getTupleAt(idx).getMemberAt(0));
         }else if (e.getActionCommand().equals((String)TupleMemberLabelPopUpActions.popUpCaptions.get(TupleMemberLabelPopUpActions.SEND_MEMBER_TO_FILTER))){
            q.addPageMemberToFilter(er.getAxis("Axis2").getTupleAt(idx).getMemberAt(0));
         }

      }



   }


   public static void main(String[] args) {
     ServerMetadata svm = new ServerMetadata("http://localhost:8080/xmla/msxisapi.dll");

     XMLADiscoverRestrictions restrictions = XMLAObjectsFactory.newXMLADiscoverRestrictions();
     XMLADiscoverProperties   properties   = XMLAObjectsFactory.newXMLADiscoverProperties();


     restrictions.setCatalog("Foodmart 2000");
     restrictions.setCubeName("Sales");


     properties.setDataSourceInfo("Local Analysis Server");
     properties.setCatalog("Sales");
     properties.setFormat("Tabular");
     properties.setContent("SchemaData");



     // only cube element could have started an explore action:
     // (for the time being, if I find a meaning in exploring catalogs and datasources, I'll move those getCubeName annd getCatalogName to interface)




     UIPropertyManager  pm = new UIPropertyManager();
     pm.setSystemUI();

     Viewer v = new Viewer(restrictions, properties , svm);


     JFrame frame2 = new JFrame("Test viewer");

     frame2.getContentPane().add(v, BorderLayout.CENTER );

     frame2.addWindowListener(new WindowAdapter() {
        public void windowClosing(WindowEvent e) {
           System.exit(0);
        }
     });
     frame2.pack();
     frame2.setExtendedState(frame2.MAXIMIZED_BOTH);
     frame2.setVisible(true);

  }


}
