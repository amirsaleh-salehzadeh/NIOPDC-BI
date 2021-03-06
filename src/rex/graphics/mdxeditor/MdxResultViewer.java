package rex.graphics.mdxeditor;

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import rex.graphics.*;
import rex.graphics.charts.*;
import rex.graphics.dimensiontree.*;
import rex.graphics.dimensiontree.dnd.*;
import rex.graphics.filtertree.*;
import rex.graphics.filtertree.elements.*;
import rex.metadata.*;
import rex.metadata.resultelements.*;
import rex.utils.*;
import rex.xmla.*;
import org.w3c.dom.Document;
import rex.xmla.XMLAObjectsFactory;
import rex.graphics.mdxeditor.mdxbuilder.MdxBuilderTree;//By Prakash
import java.lang.OutOfMemoryError;

/**
 * Displays a result of an MDX query.
 *
 * @see rex.graphics.CubeExplorer
 * @see rex.graphics.mdxeditor.MdxResultPage
 * @author Igor Mekterovic
 * @version 0.3
 */
public class MdxResultViewer extends JPanel implements IViewer{
   private JSplitPane splitPane;
   private JPanel rightPane;
   private JPanel leftPane;
   private LinkedList filterTrees;
   private LinkedList chartTabs;
   private TreeDragSource ds;
   private Query q;
   private LinkedList pages;
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
   private CubeExplorer2 cubeExplorer;
   private String mdxQuery;
   private static ImageIcon tableIcon, chartIcon;
   private boolean  showRowTotalsOn, showColumnTotalsOn;
   private MdxEditor parent;


   static {
      tableIcon = S.getAppIcon("table.gif");
      chartIcon = S.getAppIcon("chart.gif");
   }

   /**
    * Full constructor.
    *
    * @param _restrictions ClsXMLAProxDiscoverRestrictions
    * @param _properties ClsXMLAProxDiscoverProperties
    * @param _smd ServerMetadata
    * @param _mdxQuery String
    * @param _statusBar StatusBar
    * @param _showRowTotalsOn boolean
    * @param _showColumnTotalsOn boolean
    * @param _parent MdxEditor
    */
   public MdxResultViewer(
                     XMLADiscoverRestrictions _restrictions
                  ,  XMLADiscoverProperties   _properties
                  ,  ServerMetadata _smd
                  ,  String _mdxQuery
                  ,  StatusBar _statusBar
                  ,  boolean _showRowTotalsOn
                  ,  boolean _showColumnTotalsOn
                  ,  MdxEditor _parent){
      this(_restrictions, _properties, _smd);
      mdxQuery           = _mdxQuery;
      statusBar          = _statusBar;
      showRowTotalsOn    = _showRowTotalsOn;
      showColumnTotalsOn = _showColumnTotalsOn;
      parent             = _parent;
      refreshQuery();

   }

  /*
    * By Prakash on 15th January.
    * For generating Order Node.   
    */
   /**
    * Full constructor.
    *
    * @param _restrictions ClsXMLAProxDiscoverRestrictions
    * @param _properties ClsXMLAProxDiscoverProperties
    * @param _smd ServerMetadata
    * @param _mdxQuery String
    * @param _statusBar StatusBar
    * @param _showRowTotalsOn boolean
    * @param _showColumnTotalsOn boolean
    * @param _parent MdxEditor
    * @param _MDXBuilderTree MDXBuilderTree
    * @param _DimensionTree DimensionTree
    */
   public MdxResultViewer(
                     XMLADiscoverRestrictions _restrictions
                  ,  XMLADiscoverProperties   _properties
                  ,  ServerMetadata _smd
                  ,  String _mdxQuery
                  ,  StatusBar _statusBar
                  ,  boolean _showRowTotalsOn
                  ,  boolean _showColumnTotalsOn
                  ,  MdxEditor _parent
				  ,	 MdxBuilderTree _MdxBuilderTree
				  ,	 DimensionTree _DimensionTree){
      this(_restrictions, _properties, _smd, _MdxBuilderTree, _DimensionTree);
      mdxQuery           = _mdxQuery;
      statusBar          = _statusBar;
      showRowTotalsOn    = _showRowTotalsOn;
      showColumnTotalsOn = _showColumnTotalsOn;
      parent             = _parent;
      refreshQuery();

   }

   /**
    * Constructor used for testing.
    *
    * @param _restrictions ClsXMLAProxDiscoverRestrictions
    * @param _properties ClsXMLAProxDiscoverProperties
    * @param _smd ServerMetadata
    */
   public MdxResultViewer( XMLADiscoverRestrictions _restrictions
                           ,  XMLADiscoverProperties   _properties
                           ,  ServerMetadata _smd){
      restrictions = _restrictions;
      properties   = _properties;
      smd = _smd;
      execProperties = XMLAObjectsFactory.newXMLAExecuteProperties();

      execProperties.setCatalog(properties.getCatalog());
      execProperties.setDataSourceInfo(properties.getDataSourceInfo());


      q = new Query(_restrictions.getCubeName(), this);

      rightPane = new JPanel(){

         {
            setOpaque(false);
         } // instance initializer

         public void paintComponent(Graphics g) {
            S.paintBackground(g, this);
            super.paintComponent(g);
         }
      };

      rightPane.setLayout(new BoxLayout(rightPane, BoxLayout.Y_AXIS));




      pages = new LinkedList();
      MdxResultPage c = new MdxResultPage();
      c.setCurrent(true);
      pages.add(c);
      rightPane.add(c);


      this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));

      this.add(rightPane);

   }

   /*
    * By Prakash on 15th January.
    * For generating Order Node.   
    */
   /**
    * Constructor used for testing.
    *
    * @param _restrictions ClsXMLAProxDiscoverRestrictions
    * @param _properties ClsXMLAProxDiscoverProperties
    * @param _smd ServerMetadata
    * @param _MdxBuilderTree _MdxBuilderTree
    * @param _DimensionTree _DimensionTree
    */
   public MdxResultViewer( XMLADiscoverRestrictions _restrictions
                           ,  XMLADiscoverProperties   _properties
                           ,  ServerMetadata _smd
						   ,  MdxBuilderTree _MdxBuilderTree
						   ,  DimensionTree _DimensionTree){
      restrictions = _restrictions;
      properties   = _properties;
      smd = _smd;
      execProperties = XMLAObjectsFactory.newXMLAExecuteProperties();

      execProperties.setCatalog(properties.getCatalog());
      execProperties.setDataSourceInfo(properties.getDataSourceInfo());


      q = new Query(_restrictions.getCubeName(), this, _MdxBuilderTree, _DimensionTree);

      rightPane = new JPanel(){

         {
            setOpaque(false);
         } // instance initializer

         public void paintComponent(Graphics g) {
            S.paintBackground(g, this);
            super.paintComponent(g);
         }
      };

      rightPane.setLayout(new BoxLayout(rightPane, BoxLayout.Y_AXIS));

      pages = new LinkedList();
      MdxResultPage c = new MdxResultPage();
      c.setCurrent(true);
      pages.add(c);
      rightPane.add(c);


      this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
      
      this.add(rightPane);

   }

   /**
    * Refreshes a MDX query:
    * Query is executed, while refreshing progress bar in another thread. Afterwards, depending on the result of the query,
    * CubeExplorer objects and MdxResultPage objests are created and displayed.
    * <br> If there are members on PAGES axis, result is sliced along that axis and each slice (MdxResultPage) is displayed in separate tab.
    */
   public void refreshQuery(){
//      mdxQuery = _mdxquery;       
      if (q.canExecute()) {
          q.updateMBTree(parent.getToolbar().getAppendGeneratedMDX());
         mdxQuery = q.getQuery();
         parent.appendGeneratedMDX("\n\n-- *** Generated(" + 
                 GregorianCalendar.getInstance().getTime() + "):  *** \n" + 
                 mdxQuery);         
      }
      // else use mdxQuery that was passed on in the contructor, i.e. query that user typed in
      if (true){
         statusBar.startServerClock();
          
            /**
              * Copyright (C) 2006 CINCOM SYSTEMS, INC.
              * All Rights Reserved
              * Copyright (C) 2006 Igor Mekterovic
              * All Rights Reserved
              */ 
            /* implementing localization */
         statusBar.setStatus(I18n.getString("statusBar.execQuery"));
           /* end of modification for I18n */

         errorOccured = false;
         statusBar.progressJob(new Runnable(){
            public void run(){
               try{                   
                  d = smd.execute(mdxQuery, execProperties);                  
               }catch(Exception e){
                  errorOccured = true;
                  errorMessage = e.getMessage();
                  //e.printStackTrace();//Commented by Prakash
                  return;
               }
               /**
                * Copyright (C) 2006 CINCOM SYSTEMS, INC.
                * All Rights Reserved
                * Copyright (C) 2006 Igor Mekterovic
                * All Rights Reserved
                */
               /*
                * By Prakash.
                * Halt thread if OutOfMemoryError.
                */
               catch(OutOfMemoryError mem)
               {                   
                   errorOccured = true;
                   errorMessage = mem.getMessage();
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
              */ 
            /* implementing localization */
             statusBar.setStatus(I18n.getString("statusBar.resultDisplay"));
               /* end of modification for I18n */             
            statusBar.startClientClock();

            statusBar.progressJob(new Runnable() {
               public void run() {
                  try{                      
                     er = new ExecuteResult(d, q);                      
                  }catch(ExecuteResultParseException e){
                     errorOccured = true;
                     errorMessage = e.getMessage();
                     //e.printStackTrace();//Commented by Prakash
                     return;
                  }
                  /**
                   * Copyright (C) 2006 CINCOM SYSTEMS, INC.
                   * All Rights Reserved
                   * Copyright (C) 2006 Igor Mekterovic
                   * All Rights Reserved
                   */
                  /*
                   * By Prakash.
                   * Halt thread if OutOfMemoryError.
                   */
                  catch(OutOfMemoryError mem)
                  {
                      errorOccured = true;
                      errorMessage = mem.getMessage();
                      return;
                  }
                  if (!er.isValid()) {                                            
                      /**
                       * Copyright (C) 2006 CINCOM SYSTEMS, INC.
                       * All Rights Reserved
                       * Copyright (C) 2006 Igor Mekterovic
                       * All Rights Reserved
                       */ 
                     if ((er.getAxis("Axis1") != null ? er.getAxis("Axis1").getTupleCount() : 0) == 0) {                         
                        /**
                          * Copyright (C) 2006 CINCOM SYSTEMS, INC.
                          * All Rights Reserved
                          * Copyright (C) 2006 Igor Mekterovic
                          * All Rights Reserved
                          */ 
                        /* implementing localization */
                         statusBar.setStatus(I18n.getString("statusBar.errAxis1"));
                           /* end of modification for I18n */

                         errorOccured = true;                         
                         errorMessage = I18n.getString("statusBar.errAxis1");
                     }
                     /**
                      * Copyright (C) 2006 CINCOM SYSTEMS, INC.
                      * All Rights Reserved
                      * Copyright (C) 2006 Igor Mekterovic
                      * All Rights Reserved
                      */ 
                     else if ((er.getAxis("Axis0") != null ? er.getAxis("Axis0").getTupleCount() : 0) == 0) {                         
                        /* implementing localization */
                          statusBar.setStatus(I18n.getString("statusBar.errAxis0"));
                            /* end of modification for I18n */
                        errorOccured = true;
                        errorMessage = I18n.getString("statusBar.errAxis0");
                     }
                     //((Chapter)pages.getFirst()).refreshDisplay();
                  }

                  rightPane.removeAll();
                  JComponent rightPaneTableContent;
                  int chapterCount;
                  if (er.getAxis("Axis2") != null) {
                    // *****************        THERE ARE PAGES !!!  *************************/
                     chapterCount = er.getAxis("Axis2").getTupleCount();
                     pages.clear();
                     // here we make different cube slicers and make new cube explorers
                     // one for each chapter
                     final JTabbedPane jtp = new JTabbedPane(JTabbedPane.TOP);
                     for (int cc = 0; cc < chapterCount && cc < MAX_CHAPTER_COUNT; cc++) {
                        CubeSlicer cs = new CubeSlicer( (short) 1
                           , (short) 0
                           , new short[] {2}
                           , new Tuple[] {er.getAxis("Axis2").getTupleAt(cc)}
                           );
                        cubeExplorer = new CubeExplorer2(er, cs, showColumnTotalsOn, showRowTotalsOn);
                        cubeExplorer.setMaximumSize(new Dimension(Short.MAX_VALUE, cubeExplorer.getHeight()));

                        MdxResultPage c = new MdxResultPage(cubeExplorer);
                        c.setMaximumSize(new Dimension(  Short.MAX_VALUE
                                                       , cubeExplorer.getPrefferedHeight() + MdxResultPage.getAdditionalHeight()));
                        pages.add(c);
                        // this is heavy - now I have to check if there are any charts to display
                        // chart tabs ARE INSIDE page tabs!
                        if (chartTabs != null && chartTabs.size() > 0) {
                           // THERE ARE NO CHARTS SO FAR IN THE MDX EDITOR
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

                           jtp.addTab( er.getAxis("Axis2").getTupleAt(cc).getShortCaption()
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
                     pages.clear();

                     CubeSlicer cs = new CubeSlicer( (short) 1, (short) 0);
                     cubeExplorer = new CubeExplorer2(er, cs, showColumnTotalsOn, showRowTotalsOn);
                     cubeExplorer.setMaximumSize(new Dimension(Short.MAX_VALUE, cubeExplorer.getPrefferedHeight()));

                     MdxResultPage c = new MdxResultPage(cubeExplorer);
                     c.setMaximumSize(new Dimension(  Short.MAX_VALUE
                                                       , cubeExplorer.getPrefferedHeight() + MdxResultPage.getAdditionalHeight()));
                     c.setCurrent(true);
                     pages.add(c);
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
                  ListIterator it = pages.listIterator();
                  MdxResultPage currentMdxResultPage;
                  while (it.hasNext()) {
                     if ( ( (MdxResultPage) it.next()).isCurrent()) {
                        ( (MdxResultPage) it.previous()).getCubeExplorer().setPrefferedDividerLocation();
                        break;
                     }
                  }
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
              */ 
            /* implementing localization */
            statusBar.setStatus(I18n.getString("statusBar.ready"));
              /* end of modification for I18n */
            q.setQuery(er);
            q.setQueryForWhere(mdxQuery);//By Prakash. passing query for slicer.
         }else{
              
            /**
            * Copyright (C) 2006 CINCOM SYSTEMS, INC.
            * All Rights Reserved
            * Copyright (C) 2006 Igor Mekterovic
            * All Rights Reserved
            */ 
            /* implementing localization */
             JOptionPane.showMessageDialog(  null
                                          , I18n.getString("msgText.errQueryExec") + errorMessage
                                          , I18n.getString("msgTitle.execQuery")
                                          , JOptionPane.ERROR_MESSAGE);
            statusBar.setStatus(I18n.getString("statusBar.errorExec"));
              /* end of modification for I18n */

         }
         
      }else{
         // refresh EmptyResultTable
         ((MdxResultPage)pages.getFirst()).refreshDisplay();
      }
   }

   /**
    * Adds specified member to the filter(Tree). Returns the tree model of the newly instantiated FilterTree
    * @see rex.graphics.filtertree.FilterTree
    * @param filterMember Member
    * @return FilterTreeModel
    */
   public FilterTreeModel addMemberToFilter(Member filterMember){
      if (filterTrees == null){
         filterTrees = new LinkedList();
      }
      LinkedList eml = new LinkedList();
      eml.add(filterMember);
      
      // By Prakash
      /**
       * Copyright (C) 2006 CINCOM SYSTEMS, INC.
       * All Rights Reserved
       * Copyright (C) 2006 Igor Mekterovic
       * All Rights Reserved
       */ 
      ((DimensionTreeModel)parent.getDimensionTree().getTree().getModel()).addChildrenOneLevel((((DimensionTreeModel)parent.getDimensionTree().getTree().getModel()).getTreeElement(filterMember.getLname())));
      FilterTree filterTree = new FilterTree(  smd
                                             , q
                                             , ((DimensionTreeModel)parent.getDimensionTree().getTree().getModel()).getDimensionTreeElement(filterMember.getDimensionUniqueName())
                                             , eml
                                             , this);
      filterTrees.add(filterTree);
      refreshLeftPane();
      return (FilterTreeModel)filterTree.getTree().getModel();
   }

   /**
    * Removes the specified FilterTree.
    * @param filterToDrop FilterTree
    */
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

   /**
    * Refreshes the display of Filter tree(s).
    */
   public void refreshFilterDisplay(){
      refreshLeftPane();
   }

   /**
    * Refreshes the display of Filter tree(s).
    */
   private void refreshLeftPane(){
       S.out(" In refreshLeftPane");
      if (filterTrees != null && filterTrees.size()>0){
          S.out(" In refreshLeftPane before RemoveAll");
         leftPane.removeAll();
         S.out(" In refreshLeftPane after RemoveAll");
         // the whole display depends on the FIRST filterTree - the first one is the boss !
         if (!((FilterTree)filterTrees.getFirst()).isDisplayedInTab()){
             S.out(" In refreshLeftPane in isDisplayedInTab");
            JPanel filtersPane = new JPanel();
            filtersPane.setLayout(new BoxLayout(filtersPane, BoxLayout.Y_AXIS));
            Iterator it = filterTrees.iterator();
            S.out(" In refreshLeftPane after iterator");
            while (it.hasNext()) {
               filtersPane.add( (JPanel) it.next());
            }
            S.out(" In refreshLeftPane after while");
            leftPane.add(new JScrollPane(filtersPane));
         }else{
             S.out(" In refreshLeftPane in else");
            JTabbedPane dimFilterTabbedPane = new JTabbedPane(JTabbedPane.LEFT);

            Iterator it = filterTrees.iterator();
            int i=1;
            FilterTree ft;
            S.out(" In refreshLeftPane before (FilterTree)it.next()");
            while (it.hasNext()) {
               ft = (FilterTree)it.next();
               dimFilterTabbedPane.addTab(   null // "Filter" + i
                                           , FilterTreeRootElement.getFilterIcon()
                                           , ft
                                           , ft.getCaption());
               i++;
            }
            S.out(" In refreshLeftPane after (FilterTree)it.next()");
            leftPane.add(dimFilterTabbedPane);
         }
         S.out(" In refreshLeftPane before setPrefferedSize");
         leftPane.setPreferredSize(new Dimension(200, this.getHeight()));
         leftPane.setMaximumSize(new Dimension(250, Short.MAX_VALUE));
         S.out(" In refreshLeftPane after setPrefferedSize");
      }else{
          S.out(" In refreshLeftPane before leftPane.removAll");
         leftPane.removeAll();

         leftPane.setPreferredSize(new Dimension(0,0));
         leftPane.setMaximumSize(new Dimension(0,Short.MAX_VALUE));
         S.out(" In refreshLeftPane after leftPane.setMaximumSize");
      }
      this.invalidate();
      this.revalidate();
      this.repaint();
   }

   /**
    * Returns true if error occured during the execution of the query, otherwise false.
    * @return boolean
    */
   public boolean getErrorOccured(){
      return errorOccured;
   }

   /**
    * Forwards the specified value to the CubeExplorer to turn on/off collumn totals.
    * @see rex.graphics.CubeExplorer
    * @param _showColumnTotalsOn boolean
    */
   void setShowColumnTotalsOn(boolean _showColumnTotalsOn){
      if (cubeExplorer != null){
         cubeExplorer.setShowColumnTotalsOn(_showColumnTotalsOn);
      }
   }

   /**
    * Forwards the specified value to the CubeExplorer to turn on/off row totals.
    * @param _showRowTotalsOn boolean
    */
   void setShowRowTotalsOn(boolean _showRowTotalsOn){
      if (cubeExplorer != null){
         cubeExplorer.setShowRowTotalsOn(_showRowTotalsOn);
      }
   }

   /**
    * Does nothing.
    * @param elementToEnable QueryElement
    */
   public void enableTreeElements( QueryElement elementToEnable){
//      do nothing  -> this is editor, so everythin goes !
   }

   /**
    * Inner class that handles pop-up display and pop-up actions for the PAGES members (available as right-click on the tab labels)
    * @author Igor Mekterovic
    * @version 0.3
    */
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

         if (SwingUtilities.isRightMouseButton(e)) {

            idx = jtp.indexAtLocation(e.getX(), e.getY());
            popup.show(e.getComponent()
                       , e.getX()
                       , e.getY());
         }
         if (e.isPopupTrigger()) {

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

   /**
    * Main method used for testing the class
    * @param args String[]
    */
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



     UIPropertyManager  pm = new UIPropertyManager();
     pm.setSystemUI();

     MdxResultViewer v = new MdxResultViewer(restrictions, properties , svm, "SELECT"
            + " NON EMPTY"
            + "{ {[Measures].Members}} ON COLUMNS, "
            + " NON EMPTY "
            + "{[Gender].[Gender].Members} ON ROWS,"
            + ""
            + "{ Time.Year.Members} on PAGES"
            + " FROM Sales "
            , new StatusBar(), false, false, null);


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
