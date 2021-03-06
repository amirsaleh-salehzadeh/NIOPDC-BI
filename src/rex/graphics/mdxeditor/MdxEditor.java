package rex.graphics.mdxeditor;

import java_cup.runtime.Symbol;

import javax.swing.JPanel;
import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JTextArea;
import javax.swing.JSplitPane;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPopupMenu;
//import javax.swing.JMenu;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

//import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
//import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

//import org.apache.log4j.PropertyConfigurator;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.dom.DOMSource;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import javax.swing.JOptionPane;

import rex.utils.*;

import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import javax.swing.JTabbedPane;

import rex.metadata.ExecuteResult;
import rex.metadata.Query;
import rex.metadata.ServerMetadata;
import rex.olap.mdxparse.Exp;
import rex.olap.mdxparse.Formula;
import rex.olap.mdxparse.Lexer;
import rex.olap.mdxparse.ParsedQuery;
//import rex.olap.mdxparse.QueryAxis;
import rex.olap.mdxparse.parser;

import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;
import java.util.Enumeration;
import java.util.HashMap;
import rex.graphics.StatusBar;
import rex.graphics.TreeElement;

import java.awt.Dimension;
import java.awt.Toolkit;
import rex.graphics.dimensiontree.DimensionTree;
import rex.graphics.dimensiontree.DimensionTreeModel;
//import rex.graphics.dimensiontree.elements.DimensionElement;
import rex.graphics.dimensiontree.elements.DimensionTreeElement;
import rex.graphics.mdxeditor.mdxbuilder.MdxBuilderTree;
import rex.graphics.mdxeditor.mdxbuilder.dnd.DimensionTreeToMBTDragSource;
import java.awt.dnd.DnDConstants;
import rex.graphics.mdxeditor.mdxbuilder.MdxBuilderListener;
import rex.graphics.mdxeditor.mdxbuilder.dnd.MBTtoMBTDragSource;
import rex.graphics.mdxeditor.mdxbuilder.dnd.FunctionTreeToMBTDragSource;
//import rex.graphics.mdxeditor.mdxbuilder.nodes.MBTFunctionNode;
import rex.graphics.mdxeditor.mdxbuilder.nodes.DefaultMBTNode;
//import rex.graphics.mdxeditor.mdxbuilder.nodes.MBTImmutableMembersNode;
import rex.graphics.mdxeditor.mdxbuilder.nodes.MBTNode;
import rex.graphics.mdxeditor.mdxbuilder.nodes.MBTWithMembersNode;
//import rex.graphics.mdxeditor.mdxbuilder.nodes.MBTNamedSetNode;
import rex.graphics.mdxeditor.mdxbuilder.nodes.MBTCalculatedMemberNode;
import rex.graphics.mdxeditor.mdxbuilder.nodes.DefaultMBTAxisNode;

//import javax.swing.JOptionPane;
import rex.graphics.mdxeditor.mdxbuilder.HandlesMdxEditorSettings;
import java.util.GregorianCalendar;
import javax.swing.BoxLayout;
import java.awt.Graphics;
import rex.xmla.XMLADiscoverProperties;
import rex.xmla.XMLADiscoverRestrictions;
import rex.xmla.XMLAExecuteProperties;
import rex.xmla.XMLAObjectsFactory;

import rex.bidirectional.AxisEmpty;
import rex.bidirectional.UnSupportedFunctions;
//import rex.bidirectional.SegregateMDXWithNWhere;
//import rex.graphics.mdxeditor.mdxbuilder.nodes.DefaultMBTAxisNode;
/**	Lines added by Prakash. 
*	Cincom Systems.  
*	8 May 2006.
*	Importing saveJSPListener for generating JSP page from MDX Queries. 
*/
import rex.graphics.mdxeditor.jsp.*;
/**	Lines added by Prakash. 
*	Cincom Systems.  
*	8 May 2006.
*	For adding MenuItem in Popup Menu to generate event for invoking 
*	saveJSPListener class. 
*/
import javax.swing.JMenuItem;
import java.util.Locale;
//End of addition by Prakash

/**
 * This is a main class for the mdx editor. Displays and links all components (Mdx Builder Tree, Dimension Tree, ...)
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

public class MdxEditor extends JPanel implements MdxBuilderListener, 
        HandlesMdxEditorSettings, LanguageChangedListener{
   private XMLADiscoverProperties properties;
   private XMLADiscoverRestrictions restrictions;
   private ServerMetadata smd;
   private StatusBar statusBar;
   private DimensionTree dimTree;
   private String cubeName;
   private MdxBuilderTree builderTree;
   private DimensionTreeToMBTDragSource dimTreeDragSource;
   private MBTtoMBTDragSource builderTreeDragSource;
   private FunctionTreeToMBTDragSource functionTreeDragSource;
   private String defaultSaveDirectory;
      
   /*
   * Modification done by prakash
   * Added public access specifier before declaration so that, can be access from outside. 
   */
   public JTextArea textArea;
   ColorMenu foregroundMenu
           , backgroundMenu;
   int x=0,y=0;
/**	Lines added by Prakash 
*	Cincom Systems  
*	8 May 2006
*	Creating and Initializing mnuSaveJSP Menu Item.  
*/
/* implementing localization */
   JMenuItem mnuSaveJSP=new JMenuItem(I18n.getString("menu.saveToJsp"));
   JMenuItem mnuCreateTree=new JMenuItem(I18n.getString("menu.updateMBTree"));
  /* end of modification for I18n */

   boolean stopSearch, memberStopSearch;
   int setNum[];
   private JLabel lblFileName;
   JPanel textPanel;
//	End of addition by Prakash
   JPopupMenu popup;
   MdxEditorToolbar toolbar;
   static Document document;
   JPanel editorPane;
   JPanel resultPane;
   private boolean isTabbed;
   MdxResultViewer resultViewer;
   JSplitPane jspDimAndFuncTree;

   //===== .Members handling
   String dotMembersDims[] = null; // dimensions with .members in query
   //=====
   
   private static HashMap keywords;
   static {
      keywords = new HashMap();
      keywords.put("SELECT", "SELECT");
      keywords.put("FROM", "FROM");
      keywords.put("WHERE", "WHERE");
      keywords.put("NON", "NON");
      keywords.put("EMPTY", "EMPTY");
      keywords.put("COLUMNS", "COLUMNS");
      keywords.put("ROWS", "ROWS");
      keywords.put("PAGES", "PAGES");
      keywords.put("ON", "ON");
      keywords.put("WITH", "WITH");
      keywords.put("MEMBER", "MEMBER");
      keywords.put("AS", "AS");
      keywords.put("SETTOARRAY","SetToArray");
      keywords.put("DIMENSION", "Dimension");
      keywords.put("DIMENSIONS", "Dimensions");
      keywords.put("HIERARCHY", "Hierarchy");
      keywords.put("LEVEL", "Level");
      keywords.put("LEVELS", "Levels");
      keywords.put("IS", "Is");
      keywords.put("ISANCESTOR", "IsAncestor");
      keywords.put("ISEMPTY", "IsEmpty");
      keywords.put("ISGENERATION", "IsGeneration");
      keywords.put("ISLEAF", "IsLeaf");
      keywords.put("ISSIBLING", "IsSibling");
      keywords.put("ANCESTOR", "Ancestor");
      keywords.put("CLOSINGPERIOD", "ClosingPeriod");
      keywords.put("COUSIN", "Cousin");
      keywords.put("CURRENTMEMBER", "CurrentMember");
      keywords.put("DATAMEMBER", "DataMember");
      keywords.put("DEFAULTMEMBER", "DefaultMember");
      keywords.put("FIRSTCHILD", "FirstChild");
      keywords.put("FIRSTSIBLING", "FirstSibling");
      keywords.put("ITEM", "Item");
      keywords.put("LAG", "Lag");
      keywords.put("LASTCHILD", "LastChild");
      keywords.put("LASTSIBLING", "LastSibling");
      keywords.put("LEAD", "Lead");
      keywords.put("LINKMEMBER", "LinkMember");
      keywords.put("MEMBERS", "Members");
      keywords.put("NEXTMEMBER", "NextMember");
      keywords.put("OPENINGPERIOD", "OpeningPeriod");
      keywords.put("PARALLELPERIOD", "ParallelPeriod");
      keywords.put("PARENT", "Parent");
      keywords.put("PREVMEMBER", "PrevMember");
      keywords.put("STRTOMEMBER", "StrToMember");
      keywords.put("VALIDMEASURE", "ValidMeasure");
      keywords.put("AGGREGATE", "Aggregate");
      keywords.put("AVG", "Avg");
      keywords.put("CALCULATIONCURRENTPASS", "CalculationCurrentPass");
      keywords.put("CALCULATIONPASSVALUE", "CalculationPassValue");
      keywords.put("COALESCEEMPTY", "CoalesceEmpty");
      keywords.put("CORRELATION", "Correlation");
      keywords.put("COUNT", "Count");
      keywords.put("COVARIANCE", "Covariance");
      keywords.put("COVARIANCEN", "CovarianceN");
      keywords.put("DISTINCTCOUNT", "DistinctCount");
      keywords.put("IIF", "IIf");
      keywords.put("LINREGINTERCEPT", "LinRegIntercept");
      keywords.put("LINREGPOINT", "LinRegPoint");
      keywords.put("LINREGR2", "LinRegR2");
      keywords.put("LINREGSLOPE", "LinRegSlope");
      keywords.put("LINREGVARIANCE", "LinRegVariance");
      keywords.put("LOOKUPCUBE", "LookupCube");
      keywords.put("MAX", "Max");
      keywords.put("MEDIAN", "Median");
      keywords.put("MIN", "Min");
      keywords.put("ORDINAL", "Ordinal");
      keywords.put("PREDICT", "Predict");
      keywords.put("RANK", "Rank");
      keywords.put("ROLLUPCHILDREN", "RollupChildren");
      keywords.put("STDDEV", "Stddev");
      keywords.put("STDDEVP", "StddevP");
      keywords.put("STDEV", "Stdev");
      keywords.put("STDEVP", "StdevP");
      keywords.put("STRTOVALUE", "StrToValue");
      keywords.put("SUM", "Sum");
      keywords.put("VALUE", "Value");
      keywords.put("VAR", "Var");
      keywords.put("VARIANCE", "Variance");
      keywords.put("VARIANCEP", "VarianceP");
      keywords.put("VARP", "VarP");
      keywords.put("CALL", "Call");
      keywords.put("ADDCALCULATEDMEMBERS", "AddCalculatedMembers");
      keywords.put("ALLMEMBERS", "AllMembers");
      keywords.put("ANCESTORS", "Ancestors");
      keywords.put("ASCENDANTS", "Ascendants");
      keywords.put("AXIS", "Axis");
      keywords.put("BOTTOMCOUNT", "BottomCount");
      keywords.put("BOTTOMPERCENT", "BottomPercent");
      keywords.put("BOTTOMSUM", "BottomSum");
      keywords.put("CHILDREN", "Children");
      keywords.put("CROSSJOIN", "Crossjoin");
      keywords.put("DESCENDANTS", "Descendants");
      keywords.put("DISTINCT", "Distinct");
      keywords.put("DRILLDOWNLEVEL", "DrilldownLevel");
      keywords.put("DRILLDOWNLEVELBOTTOM", "DrilldownLevelBottom");
      keywords.put("DRILLDOWNLEVELTOP", "DrilldownLevelTop");
      keywords.put("DRILLDOWNMEMBER", "DrilldownMember");
      keywords.put("DRILLDOWNMEMBERBOTTOM", "DrilldownMemberBottom");
      keywords.put("DRILLDOWNMEMBERTOP", "DrilldownMemberTop");
      keywords.put("DRILLUPLEVEL", "DrillupLevel");
      keywords.put("DRILLUPMEMBER", "DrillupMember");
      keywords.put("EXCEPT", "Except");
      keywords.put("EXTRACT", "Extract");
      keywords.put("FILTER", "Filter");
      keywords.put("GENERATE", "Generate");
      keywords.put("HEAD", "Head");
      keywords.put("HIERARCHIZE", "Hierarchize");
      keywords.put("INTERSECT", "Intersect");
      keywords.put("LASTPERIODS", "LastPeriods");
      keywords.put("MEMBERS", "Members");
      keywords.put("MTD", "Mtd");
      keywords.put("NAMETOSET", "NameToSet");
      keywords.put("NONEMPTYCROSSJOIN", "NonEmptyCrossjoin");
      keywords.put("ORDER", "Order");
      keywords.put("PERIODSTODATE", "PeriodsToDate");
      keywords.put("QTD", "Qtd");
      keywords.put("SIBLINGS", "Siblings");
      keywords.put("STRIPCALCULATEDMEMBERS", "StripCalculatedMembers");
      keywords.put("STRTOSET", "StrToSet");
      keywords.put("SUBSET", "Subset");
      keywords.put("TAIL", "Tail");
      keywords.put("TOGGLEDRILLSTATE", "ToggleDrillState");
      keywords.put("TOPCOUNT", "TopCount");
      keywords.put("TOPPERCENT", "TopPercent");
      keywords.put("TOPSUM", "TopSum");
      keywords.put("UNION", "Union");
      keywords.put("VISUALTOTALS", "VisualTotals");
      keywords.put("WTD", "Wtd");
      keywords.put("YTD", "Ytd");
      keywords.put("CALCULATIONPASSVALUE", "CalculationPassValue");
      keywords.put("COALESCEEMPTY", "CoalesceEmpty");
      keywords.put("GENERATE", "Generate");
      keywords.put("IIF", "IIf");
      keywords.put("LOOKUPCUBE", "LookupCube");
      keywords.put("MEMBERTOSTR", "MemberToStr");
      keywords.put("NAME", "Name");
      keywords.put("PROPERTIES", "Properties");
      keywords.put("SETTOSTR", "SetToStr");
      keywords.put("TUPLETOSTR", "TupleToStr");
      keywords.put("UNIQUENAME", "UniqueName");
      keywords.put("USERNAME", "UserName");
      keywords.put("CURRENT", "Current");
      keywords.put("ITEM", "Item");
      keywords.put("STRTOTUPLE", "StrToTuple");
   }




   public MdxEditor( XMLADiscoverRestrictions _restrictions
                  ,  XMLADiscoverProperties   _properties
                  ,  ServerMetadata _smd
                  ,  String _cubeName
                  ){
/**
  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
  * All Rights Reserved
  * Copyright (C) 2006 Igor Mekterovic
  * All Rights Reserved
  */ /*adding this class to the list of classes that implement I18n */
      
      I18n.addOnLanguageChangedListener(this);
//      applyI18n();
        /* end of modification for I18n */

      restrictions = _restrictions;
      properties   = _properties;
      smd = _smd;
      cubeName = _cubeName;
     /**
  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
  * All Rights Reserved
  * Copyright (C) 2006 Igor Mekterovic
  * All Rights Reserved
  */
      foregroundMenu = new ColorMenu(I18n.getString("menu.foregroundColor"));
        /* end of modification for I18n */
      foregroundMenu.addActionListener(new ForegroundListener());
      /**
  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
  * All Rights Reserved
  * Copyright (C) 2006 Igor Mekterovic
  * All Rights Reserved
  */
      backgroundMenu = new ColorMenu(I18n.getString("menu.backgroundColor"));
        /* end of modification for I18n */
      backgroundMenu.addActionListener(new BackgroundListener());
      applyI18n();// Temporary placed here for testing Prakash    
    
      /**
       *   Copyright (C) 2006 CINCOM SYSTEMS, INC.
       *   All Rights Reserved
       *   Copyright (C) 2006 Igor Mekterovic
       *   All Rights Reserved
       */
      
      /**	Lines added by Prakash 
       *	Cincom Systems  
       *	8 May 2006
       *	Registering SaveJSPListener with mnuSaveJSP Menu Item.   
       */
      mnuSaveJSP.addActionListener(new SaveJSP());
      mnuCreateTree.addActionListener(new CreateTree());
//	  End of addition by Prakash
      
      textArea = new JTextArea();
      textArea.addMouseListener(new PopupListener());
      textArea.addKeyListener(new TextAreaKeyListener());
      //By Praksh
      lblFileName=new JLabel("",JLabel.CENTER);
      JPanel textPanel=new JPanel();
      textPanel.setLayout(new BorderLayout());
      textPanel.add(lblFileName,BorderLayout.NORTH);
      textPanel.add(new JScrollPane(textArea),BorderLayout.CENTER);
      //End
      
      popup = new JPopupMenu();
      popup.add(foregroundMenu);
      popup.add(backgroundMenu);
      popup.add(mnuSaveJSP);
      popup.add(mnuCreateTree);
      

      dimTree = new DimensionTree(restrictions, properties, smd, this);
      dimTree.setPreferredSize(new Dimension( (int)(Toolkit.getDefaultToolkit().getScreenSize().getWidth()/5)
                                             ,(int)(Toolkit.getDefaultToolkit().getScreenSize().getHeight()/2.1)));      
      dimTreeDragSource  = new DimensionTreeToMBTDragSource(dimTree.getTree()
                                                      , DnDConstants.ACTION_COPY_OR_MOVE);
      builderTree = new MdxBuilderTree(this, this, cubeName);
      builderTree.setPreferredSize(new Dimension(  (int)(Toolkit.getDefaultToolkit().getScreenSize().getWidth()/5)
                                                  ,(int)(Toolkit.getDefaultToolkit().getScreenSize().getHeight()/1.1)));
      builderTreeDragSource = new MBTtoMBTDragSource(builderTree.getTree()
                                                      , DnDConstants.ACTION_COPY_OR_MOVE);

      
      toolbar = new MdxEditorToolbar(this);
      toolbar.setBuilderTree(builderTree.getTree());// Inserted by Prakash. 
      
      editorPane = new JPanel();
      editorPane.setLayout(new BorderLayout());

      MdxFunctionTree fTree = new MdxFunctionTree(this);
      functionTreeDragSource = new FunctionTreeToMBTDragSource(fTree.getTree(), DnDConstants.ACTION_COPY_OR_MOVE);
      fTree.setPreferredSize(new Dimension( (int)(Toolkit.getDefaultToolkit().getScreenSize().getWidth()/5)
                                                   ,(int)(Toolkit.getDefaultToolkit().getScreenSize().getHeight()/2.1)));


      jspDimAndFuncTree = new JSplitPane(   JSplitPane.VERTICAL_SPLIT
                                                        , dimTree
                                                        , fTree
                                                       );
      jspDimAndFuncTree.setOneTouchExpandable(true);
      jspDimAndFuncTree.setDividerSize(8);


      JSplitPane jspTrees = new JSplitPane(  JSplitPane.HORIZONTAL_SPLIT
                                       , jspDimAndFuncTree
                                       , builderTree
                                      );
      jspTrees.setDividerSize(8);
      
      JSplitPane jspTreesAndText = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT
            , jspTrees
            ,textPanel);

      jspTreesAndText.setDividerSize(8);

      editorPane.add(toolbar, BorderLayout.NORTH);
      editorPane.add(jspTreesAndText, BorderLayout.CENTER);

      resultPane = new JPanel(){

         {
            setOpaque(false);
         } // instance initializer

         public void paintComponent(Graphics g) {
            S.paintBackground(g, this);
            super.paintComponent(g);
         }
      };
      resultPane.setLayout(new BoxLayout(resultPane, BoxLayout.Y_AXIS));
      /**
       * Commented by Prakash.
       * Cincom Systems.
       * To remove Simpson Quotes.
       * 3rd August 2006
       */
      //resultPane.add(  new JLabel("")
       //              , BorderLayout.CENTER);

      statusBar = new StatusBar();
           
      this.setLayout(new BorderLayout());
        this.add(new JSplitPane(JSplitPane.VERTICAL_SPLIT
                                      , editorPane
                                      , resultPane )
                       , BorderLayout.CENTER);
      this.add(statusBar, BorderLayout.SOUTH);      
      initDefaults();
      jspTreesAndText.setDividerLocation( (int)(dimTree.getPreferredSize().getWidth()
                                              + builderTree.getPreferredSize().getWidth()
                                              + jspTreesAndText.getDividerSize()
                                              + jspTrees.getDividerSize()));
      
      /**
       *   Copyright (C) 2006 CINCOM SYSTEMS, INC.
       *   All Rights Reserved
       *   Copyright (C) 2006 Igor Mekterovic
       *   All Rights Reserved
       */
      /**	Line Modified by Prakash. Subtracted 180 from the DimTree Height 
      *		Cincom Systems.  
      *		8 May 2006.
      *		 
      */      
      jspDimAndFuncTree.setDividerLocation(((int)(dimTree.getPreferredSize().getHeight()))-180);
      //  	End of the Modification by Prakash
      
  
      
   }
   
   
   /**
    * Sets the split pane (vertical or horizontal split) to specified <code>splitType</code> and saves the defaults.
    * @param splitType int
    */
   protected void setSplitPaneView(int splitType){
      isTabbed = false;
      this.removeAll();
      JSplitPane jsp =  new JSplitPane(
                                splitType
                              , editorPane
                              , resultPane );
      jsp.setDividerSize(8);
      
      /**	
       * 		Line Added by Prakash. 
       *		Cincom Systems.  
       *		9 May 2006.
       *		This code will set Divider location of vertical jsp splitpane at approx. 60 40 ratio. 
       */            
      if (splitType == JSplitPane.VERTICAL_SPLIT)
      {
      	jsp.setDividerLocation((int)(Toolkit.getDefaultToolkit().getScreenSize().getHeight()/1.8));
      }
      //	End of the Addition by Prakash
      this.add(  jsp
               , BorderLayout.CENTER);
      this.add(statusBar, BorderLayout.SOUTH);
      this.invalidate();
      this.revalidate();
      this.repaint();
      Node n = DOM.getFirstChildNodeWithName(document.getDocumentElement(), "RESULT_POSITION");
      n.removeChild(n.getChildNodes().item(0));
      if (splitType == JSplitPane.VERTICAL_SPLIT)
         n.appendChild(document.createTextNode(MdxEditorToolbar.VERTICAL_SPLIT_PANE));
      else
         n.appendChild(document.createTextNode(MdxEditorToolbar.HORIZONTAL_SPLIT_PANE));
      saveDefaults();
   }

   /**
    * Sets the tab placement (up, bottom, left or right) to specified <code>tabPlacement</code> and saves the defaults.
    * @param tabPlacement int
    */
   protected void setTabbedPaneView(int tabPlacement){
      isTabbed = true;
      this.removeAll();
      JTabbedPane tp = new JTabbedPane(tabPlacement);
      tp.add("Editor", editorPane);
      tp.add("Result", resultPane);
      this.add(tp, BorderLayout.CENTER);
      this.add(statusBar, BorderLayout.SOUTH);
      this.invalidate();
      this.revalidate();
      this.repaint();
      Node n = DOM.getFirstChildNodeWithName(document.getDocumentElement(), "RESULT_POSITION");
      n.removeChild(n.getChildNodes().item(0));
      if (tabPlacement == JTabbedPane.TOP)
         n.appendChild(document.createTextNode(MdxEditorToolbar.TABBED_PANE_UP));
      else if (tabPlacement == JTabbedPane.BOTTOM )
         n.appendChild(document.createTextNode(MdxEditorToolbar.TABBED_PANE_BOTTOM));
      else if (tabPlacement == JTabbedPane.LEFT  )
         n.appendChild(document.createTextNode(MdxEditorToolbar.TABBED_PANE_LEFT ));
      else if (tabPlacement == JTabbedPane.RIGHT  )
         n.appendChild(document.createTextNode(MdxEditorToolbar.TABBED_PANE_RIGHT ));
      saveDefaults();
   }
   /**
    * Reads and sets the editor settings from the "mdxeditor.defaults.xml".
    * <br>If the file is missing then it sets the default settings.
    * <br>Missing setting are also replaced by default settings. (e.g. if the font size is not specified then it is set to 13)
    */
   private void initDefaults(){


      DocumentBuilder builder;
      DocumentBuilderFactory factory =
          DocumentBuilderFactory.newInstance();

      try {
         builder = factory.newDocumentBuilder();
      } catch(ParserConfigurationException pce){
         //pce.printStackTrace();//Commented by Prakash
         return;
      }
      try {
         document = builder.parse( new File("mdxeditor.defaults.xml") );
      } catch (IOException ioe) {

      } catch (Exception e) {
         //e.printStackTrace();//Commented by Prakash
      }
      // SET UP TEXTAREA BASED ON DOCUMENT
      String fontName = new String("Courier New");
      int fontStyle = 0, fontSize=10;      
      String recentURL[]=new String[4];//By Prakash

      NodeList nl = document.getDocumentElement().getChildNodes();
      for(int i=0; i < nl.getLength(); i++){
         if (nl.item(i).getNodeName().equalsIgnoreCase("FONT_NAME")){
            fontName = new String(DOM.getTextFromDOMElement(nl.item(i)));
         }else if (nl.item(i).getNodeName().equalsIgnoreCase("FONT_SIZE")){
            try{
               fontSize = Integer.parseInt(DOM.getTextFromDOMElement(nl.item(i))) ;
            }catch(NumberFormatException ex){
            }
         }else if (nl.item(i).getNodeName().equalsIgnoreCase("BOLD")
                   && DOM.getTextFromDOMElement(nl.item(i)).equalsIgnoreCase("true")){
            fontStyle |= Font.BOLD;
         }else if (nl.item(i).getNodeName().equalsIgnoreCase("ITALIC")
                   && DOM.getTextFromDOMElement(nl.item(i)).equalsIgnoreCase("true")){
            fontStyle |= Font.ITALIC;
         }else if (nl.item(i).getNodeName().equalsIgnoreCase("TAB_SIZE")){
            try{
               int tabSize = Integer.parseInt(DOM.getTextFromDOMElement(nl.item(i))) ;
               setTabSize(tabSize);
            }catch(NumberFormatException ex){
            }
         }else if (nl.item(i).getNodeName().equalsIgnoreCase("FOREGROUND_COLOR")){
            try{
               int rgb = Integer.parseInt(DOM.getTextFromDOMElement(nl.item(i))) ;
               setForegroundColor(new Color(rgb));
            }catch(NumberFormatException ex){
            }
         }else if (nl.item(i).getNodeName().equalsIgnoreCase("BACKGROUND_COLOR")){
            try{
               int rgb = Integer.parseInt(DOM.getTextFromDOMElement(nl.item(i))) ;
               setBackgroundColor(new Color(rgb));
            }catch(NumberFormatException ex){
            }
         }else if (nl.item(i).getNodeName().equalsIgnoreCase("RESULT_POSITION")){
            String s = DOM.getTextFromDOMElement(nl.item(i)) ;
            if (s.equals(MdxEditorToolbar.VERTICAL_SPLIT_PANE)){
               setSplitPaneView(JSplitPane.VERTICAL_SPLIT);
               toolbar.setcbResultPosition(MdxEditorToolbar.VERTICAL_SPLIT_PANE);
            }
            else if (s.equals(MdxEditorToolbar.HORIZONTAL_SPLIT_PANE)) {
               setSplitPaneView(JSplitPane.HORIZONTAL_SPLIT);
               toolbar.setcbResultPosition(MdxEditorToolbar.HORIZONTAL_SPLIT_PANE);
            }
            else if (s.equals(MdxEditorToolbar.TABBED_PANE_UP)) {
               setTabbedPaneView(JTabbedPane.TOP);
               toolbar.setcbResultPosition(MdxEditorToolbar.TABBED_PANE_UP);
            }
            else if (s.equals(MdxEditorToolbar.TABBED_PANE_LEFT)) {
               setTabbedPaneView(JTabbedPane.LEFT);
               toolbar.setcbResultPosition(MdxEditorToolbar.TABBED_PANE_LEFT);
            }
            else if (s.equals(MdxEditorToolbar.TABBED_PANE_RIGHT)) {
               setTabbedPaneView(JTabbedPane.RIGHT);
               toolbar.setcbResultPosition(MdxEditorToolbar.TABBED_PANE_RIGHT);
            }
            else if (s.equals(MdxEditorToolbar.TABBED_PANE_BOTTOM)) {
               setTabbedPaneView(JTabbedPane.BOTTOM);
               toolbar.setcbResultPosition(MdxEditorToolbar.TABBED_PANE_BOTTOM);
            }
         }else if (nl.item(i).getNodeName().equalsIgnoreCase("SAVE_DIRECTORY")){
            try{
               defaultSaveDirectory = new String(DOM.getTextFromDOMElement(nl.item(i)));
            }catch(NumberFormatException ex){
            }
         }else if (nl.item(i).getNodeName().equalsIgnoreCase("RECENT_OPENED_FILE1")){
         	recentURL[0]=new String(DOM.getTextFromDOMElement(nl.item(i)));
         }else if (nl.item(i).getNodeName().equalsIgnoreCase("RECENT_OPENED_FILE2")){
         	recentURL[1]=new String(DOM.getTextFromDOMElement(nl.item(i)));
         }else if (nl.item(i).getNodeName().equalsIgnoreCase("RECENT_OPENED_FILE3")){
         	recentURL[2]=new String(DOM.getTextFromDOMElement(nl.item(i)));
         }else if (nl.item(i).getNodeName().equalsIgnoreCase("RECENT_OPENED_FILE4")){
         	recentURL[3]=new String(DOM.getTextFromDOMElement(nl.item(i)));
         }
      }
      Font newFont = new Font(fontName, fontStyle, fontSize);
      textArea.setFont( newFont );
      // set toolbar to this defaults
      toolbar.setcbFont(fontName);
      toolbar.setcbSize(fontSize);
      toolbar.setBold( (fontStyle & Font.BOLD) == Font.BOLD);
      toolbar.setItalic( (fontStyle & Font.ITALIC) == Font.ITALIC);
      toolbar.setTabSize(textArea.getTabSize());
      toolbar.setRecentURL(recentURL);// By Prakash
   }

   /**
    * Saves the editor settings (font, font size, colors,...) to file "mdxeditor.defaults.xml".
    */
   private void saveDefaults(){
      TransformerFactory tFactory =
          TransformerFactory.newInstance();
      try{
         Transformer transformer = tFactory.newTransformer();
         DOMSource source = new DOMSource(document);
         // Commented by Prakash. Issue with escape character.
         //StreamResult result = new StreamResult(new File("mdxeditor.defaults.xml"));
         // Inserted By Prakash.
         StreamResult result = new StreamResult(new BufferedWriter(new FileWriter(new File("mdxeditor.defaults.xml"))));         
         transformer.transform(source, result);
      } catch (Exception e){
         S.out("Unable to save defaults:");

      }
   }

   /**
    * By Prakash
    * Sets the Mdx editor's Recent File List. 
    */
   public void setRecentURL(String url[])
   {
    Node n = DOM.getFirstChildNodeWithName(document.getDocumentElement(), "RECENT_OPENED_FILE1");
    try
	{
    n.removeChild(n.getChildNodes().item(0));
    n.appendChild(document.createTextNode(url[0].trim().length()>0 ? url[0] : " "));
	}
    catch(Exception exc)
	{
    	n.appendChild(document.createTextNode(url[0].trim().length()>0 ? url[0] : " "));
	}
    n = DOM.getFirstChildNodeWithName(document.getDocumentElement(), "RECENT_OPENED_FILE2");
    try
	{    
    n.removeChild(n.getChildNodes().item(0));
    n.appendChild(document.createTextNode(url[1].trim().length()>0 ? url[1] : " "));
	}
    catch(Exception exc)
	{
        n.appendChild(document.createTextNode(url[1].trim().length()>0 ? url[1] : " "));	
	}
    n = DOM.getFirstChildNodeWithName(document.getDocumentElement(), "RECENT_OPENED_FILE3");
    try
	{
    n.removeChild(n.getChildNodes().item(0));
    n.appendChild(document.createTextNode(url[2].trim().length()>0 ? url[2] : " "));
	}
    catch(Exception exc)
	{
    	n.appendChild(document.createTextNode(url[2].trim().length()>0 ? url[2] : " "));
	}
    n = DOM.getFirstChildNodeWithName(document.getDocumentElement(), "RECENT_OPENED_FILE4");
    try
	{
    n.removeChild(n.getChildNodes().item(0));
    n.appendChild(document.createTextNode(url[3].trim().length()>0 ? url[3] : " "));
	}
    catch(Exception exc)
	{
    	n.appendChild(document.createTextNode(url[3].trim().length()>0 ? url[3] : " "));
	}
    saveDefaults();
   }

   /**
    * By Prakash.
    * Enable or Disable Locale.
    */
   
   public void setLocaleEnabled(boolean b)
   {
   		toolbar.setLocaleEnabled(b);
   }
   
   public MdxEditorToolbar getToolbar()
   {
       return toolbar;
   }
   
   /**
    * Sets the Mdx editor's BOLD attribute to specified value.
    * @param isBold boolean
    */
   void setTextToBold(boolean isBold){
      Font font = textArea.getFont();
      String name = font.getName();
      int style = font.getStyle();
      int size = font.getSize();
      if (isBold)
         style |= Font.BOLD;
      else
         style &= ~Font.BOLD;
      Font newFont = new Font(name, style, size);
      textArea.setFont( newFont );
      Node n = DOM.getFirstChildNodeWithName(document.getDocumentElement(), "BOLD");
      n.removeChild(n.getChildNodes().item(0));
      n.appendChild(document.createTextNode(isBold ? "true" : "false"));
      saveDefaults();
   }

   /**
    * Sets the Mdx editor's ITALIC attribute to specified value.
    * @param isItalic boolean
    */
   void setTextToItalic(boolean isItalic){
      Font font = textArea.getFont();
      String name = font.getName();
      int style = font.getStyle();
      int size = font.getSize();
      if (isItalic)
         style |= Font.ITALIC;
      else
         style &= ~Font.ITALIC;
      Font newFont = new Font(name, style, size);
      textArea.setFont( newFont );
      Node n = DOM.getFirstChildNodeWithName(document.getDocumentElement(), "ITALIC");
      n.removeChild(n.getChildNodes().item(0));
      n.appendChild(document.createTextNode(isItalic ? "true" : "false"));
      saveDefaults();
   }

   /**
    * Sets the Mdx editor's FONTNAME attribute to specified value.
    * @param fontName String
    */
   void setFontName(String fontName){
      Font font = textArea.getFont();
      int style = font.getStyle();
      int size = font.getSize();
      Font newFont = new Font(fontName, style, size);
      textArea.setFont( newFont );
//      S.out("setFontName:");
      Node n = DOM.getFirstChildNodeWithName(document.getDocumentElement(), "FONT_NAME");
      n.removeChild(n.getChildNodes().item(0));
      n.appendChild(document.createTextNode(fontName));
      saveDefaults();
//    Necessary here to transfer focus back to textArea or the user
//    can't keep on typing in textArea after selection in cbFonts.
      textArea.requestFocus();
   }

   /**
    * Sets the Mdx editor's FONTSIZE attribute to specified value.
    * @param fontSize int
    */
   void setFontSize(int fontSize){
      Font font = textArea.getFont();
      int style = font.getStyle();
      String name = font.getName();
      Font newFont = new Font(name, style, fontSize);
      textArea.setFont( newFont );
//    Necessary here to transfer focus back to textArea or the user
//    can't keep on typing in textArea after selection in cbFonts.
      textArea.requestFocus();
      Node n = DOM.getFirstChildNodeWithName(document.getDocumentElement(), "FONT_SIZE");
      n.removeChild(n.getChildNodes().item(0));
      n.appendChild(document.createTextNode(Integer.toString(fontSize)));
      saveDefaults();
   }

   /**
    * Sets the Mdx editor's TABSIZE attribute to specified value.
    * @param tabSize int
    */
   void setTabSize(int tabSize){
      textArea.setTabSize(tabSize);
      textArea.requestFocus();
      Node n = DOM.getFirstChildNodeWithName(document.getDocumentElement(), "TAB_SIZE");
      n.removeChild(n.getChildNodes().item(0));
      n.appendChild(document.createTextNode(Integer.toString(tabSize)));
      saveDefaults();
   }

   /**
    * Sets the Mdx editor's FOREGROUND COLOR attribute to specified value.
    * @param newColor Color
    */
   void setForegroundColor(Color newColor){
      textArea.setForeground(newColor);
      textArea.requestFocus();
      Node n = DOM.getFirstChildNodeWithName(document.getDocumentElement(), "FOREGROUND_COLOR");
      n.removeChild(n.getChildNodes().item(0));
      n.appendChild(document.createTextNode(Integer.toString(newColor.getRGB())));
      saveDefaults();
   }

   /**
    * Sets the Mdx editor's BACKGROUND COLOR attribute to specified value.
    * @param newColor Color
    */
   void setBackgroundColor(Color newColor){
      textArea.setBackground(newColor);
      textArea.requestFocus();
      Node n = DOM.getFirstChildNodeWithName(document.getDocumentElement(), "BACKGROUND_COLOR");
      n.removeChild(n.getChildNodes().item(0));
      n.appendChild(document.createTextNode(Integer.toString(newColor.getRGB())));
      saveDefaults();
   }
   /**
    * Returns default save directory for the mdx builder queries
    * @return String
    */
   public String getDefaultSaveDirectory(){
      return defaultSaveDirectory;
   }

   /**
    * Sets the default save directory for the mdx builder queries
    * @param dir String
    */
   public void setDefaultSaveDirectory(String dir){
      defaultSaveDirectory = dir;
      Node n = DOM.getFirstChildNodeWithName(document.getDocumentElement(), "SAVE_DIRECTORY");
      n.removeChild(n.getChildNodes().item(0));
      n.appendChild(document.createTextNode(dir));
      S.out("saving: " + defaultSaveDirectory);
      saveDefaults();
   }


   /**
    * Sets the display of column totals to specified value.
    * @param newValue boolean
    */
   protected void setShowColumnTotalsOn(boolean newValue){
      if (resultViewer != null) resultViewer.setShowColumnTotalsOn(newValue);
   }

   /**
    * Sets the display of row totals to specified value.
    * @param newValue boolean
    */
   protected void setShowRowTotalsOn(boolean newValue){
      if (resultViewer != null)  resultViewer.setShowRowTotalsOn(newValue);
   }

   private String removeComments(String query){
      int i =0;
      while(query.indexOf("--") != -1){
         i++;
         if (query.indexOf("--") == 0){
            query = query.substring(query.indexOf("\n") + 1);
         }else{
            query = query.substring(0, query.indexOf("--"))
                  + query.substring(query.indexOf("\n", query.indexOf("--")) + 1);
         }
      }
      return query;
   }

   /**
    * Executes the specified query and displays the results.
    * @param q String
    */
   void runQuery(String q){
      
    /*
     * By Prakash on 15th January.
     * For generating Order Node.   
     */   	
     resultViewer = new MdxResultViewer(restrictions,properties,smd,removeComments(q),statusBar,toolbar.getShowRowTotalsOn(),toolbar.getShowColumnTotalsOn(),this,builderTree,dimTree);

     resultPane.removeAll();
     resultPane.add(resultViewer);
     resultPane.invalidate();
     resultPane.revalidate();
     resultPane.repaint();
     if (isTabbed && !resultViewer.getErrorOccured()) {
        // don't feel like holding a reference just for this
        // anyway, getComponentCount() is small, like 3 or 4
        for(int i=0; i < this.getComponentCount(); i++ ){
           if (this.getComponent(i) instanceof JTabbedPane){
              // there are ylaways exactly 2 tabs, so this is safe to do:
              ((JTabbedPane)this.getComponent(i)).setSelectedIndex(1);
           }
        }
     }
   }

   /**
    * Executes all queries in the textArea and displays the results..
    */
   void runAllQuery(){
      if (textArea.getText().trim().length() > 0)
         runQuery(textArea.getText());
   }
   /**
    * Executes selected query (in the textArea) and displays the results.
    */
   void runSelectedQuery(){
      if (textArea.getSelectedText() != null){
         runQuery(textArea.getSelectedText());
      }
   }

   
   
   /**
    * Insert a code skeleton (the most common basis of an mdx query) into the textArea.
    */
   void insertCodeSkeleton(){
      textArea.setText(""
                      // + textArea.getText()
                       + "\nSELECT \nNON EMPTY\n{\n   Measures.AllMembers\n}\n ON COLUMNS,\nNON EMPTY\n{\n   \n}\nON ROWS\nFROM " + cubeName
                       );
   }

   /**
    * Returns the DimensionTree.
    * @return DimensionTree
    */
   DimensionTree getDimensionTree(){
      return dimTree;
   }

   /**
    * Returns the cube name.
    * @return String
    */
   String getCubeName(){
      return cubeName;
   }

   /**
    * Refreshes (and displays) the MDX statement generated with MdxBuilder.
    * @param newMdx String
    */
   public void mdxChanged(String newMdx){
      // setup a tagged area in a textArea and update the tagged area with new mdx
      // whatever user types outide the area  should be intact
      //
 /**
  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
  * All Rights Reserved
  * Copyright (C) 2006 Igor Mekterovic
  * All Rights Reserved
  */

  /* end of modification for I18n */

       int istart = textArea.getText().indexOf(MdxBuilderTree.OPEN_MDX_GENERATED_EXPRESSION_TAG);
       int iend   = textArea.getText().indexOf(MdxBuilderTree.CLOSE_MDX_GENERATED_EXPRESSION_TAG);
       
      if (istart != -1 && iend != -1){
      	/**
      	 *   Copyright (C) 2006 CINCOM SYSTEMS, INC.
      	 *   All Rights Reserved
      	 *   Copyright (C) 2006 Igor Mekterovic
      	 *   All Rights Reserved
      	 */
      	/*
      	 * Modified By Prakash. 
      	 * Cincom Systems.
      	 * Display only latest query.
      	 * 19th Oct 06
      	 */
         textArea.setText( textArea.getText().substring(0, istart)
                         + MdxBuilderTree.OPEN_MDX_GENERATED_EXPRESSION_TAG
                         + "\n-- ********* Generated(" + GregorianCalendar.getInstance().getTime() + "):  ********** \n"
                         + "\n" + newMdx
                         + "\n" + textArea.getText().substring(iend)
                         );
      }else{
      	/*
      	 * Modified By Prakash. 
      	 * Cincom Systems.
      	 * Won't add any TAG.
      	 * 19th Oct 06
      	 */
         textArea.setText(MdxBuilderTree.OPEN_MDX_GENERATED_EXPRESSION_TAG
                         + "\n-- ****** Generated(" + GregorianCalendar.getInstance().getTime() + "):  ****** \n"
                         + "\n" + newMdx
                         + "\n" + MdxBuilderTree.CLOSE_MDX_GENERATED_EXPRESSION_TAG
                         + "\n\n" + textArea.getText()
                         );
      }

   }
   
 	/**
 	 *   Copyright (C) 2006 CINCOM SYSTEMS, INC.
 	 *   All Rights Reserved
 	 *   Copyright (C) 2006 Igor Mekterovic
 	 *   All Rights Reserved
 	 */
   /**
    * Clear TextArea.
    */
   public void clearEditor()
   {
       textArea.setText("");
   }

   /**
    * Inserts specified text into the current prompt position in the textArea.
    * @param s String
    */
   public void addTextToCurrentPosition(String s){
      textArea.insert(s, textArea.getCaretPosition());
   }
   /**
    *   Copyright (C) 2006 CINCOM SYSTEMS, INC.
    *   All Rights Reserved
    *   Copyright (C) 2006 Igor Mekterovic
    *   All Rights Reserved
    */
   /**
    * Appends specified text to the textArea.
    * <br>(Specified text is an MDX statement generated with user's actions on the result table, e.g. drilldown on some meber)
    * @param s String
    */
   public void appendGeneratedMDX(String s){
   	/**
   	 *  Commented by Prakash.
   	 * 	
   	 *	14 Nov 06.
   	 */ 
      if (toolbar.getAppendGeneratedMDX())
         textArea.append(s);
   	/**
   	 * Line Inserted by Prakash
   	 * Update TextArea with generated Query.
   	 * 14 Nov 06
   	 */ 
   		//textArea.setText(s);
   }

   /**
    *   Copyright (C) 2006 CINCOM SYSTEMS, INC.
    *   All Rights Reserved
    *   Copyright (C) 2006 Igor Mekterovic
    *   All Rights Reserved
    */
   /*
    * Method Inserted by Prakash.
    * Cincom Systems.
    * Generates MDx Builder Tree from MDX Query (Bi-Directional).
    * 16th Aug 06. 
    */
   private void parseQuery()
   {
   	try
	{
   	    String function="";
   	    if((function=UnSupportedFunctions.searchFunctions(removeComments(textArea.getText()).toUpperCase()))!=null)
   	    {
   	        JOptionPane.showMessageDialog(this,function+" "+I18n.getString("errorMessage.notImplemented"));
   	        return;
   	    }
   		//Initialize XMLA Execute properties for executing MDX Query.
   		XMLAExecuteProperties execProperties = XMLAObjectsFactory.newXMLAExecuteProperties();
   		execProperties.setDataSourceInfo(properties.getDataSourceInfo());
   		execProperties.setCatalog(properties.getCatalog());
   		
                //=====   .Members handling get .members from original query
                // split the original query to pickup dimensions 
                String dotMembersSplit[] = null;
                dotMembersDims = null;
                String orgQuery = removeComments(textArea.getText());
                dotMembersSplit = orgQuery.split(".Members");
                if (dotMembersSplit != null) {
                    dotMembersDims = new String[dotMembersSplit.length-1];
                    for(int i=0; i<dotMembersSplit.length-1; i++) {
                        int curlyIndex = dotMembersSplit[i].lastIndexOf("{");
                        dotMembersDims[i] = dotMembersSplit[i].substring(curlyIndex+1).trim();
                        System.out.println(".Members dimension"+dotMembersDims[i]);
                    }
                }
                
                String noNonEmptyQuery = "";
                String querySplit[] = orgQuery.split(" ON ");
                boolean rowne=false;
                boolean colne=false; 
                boolean pagne=false;
                
                for (int s=0; s<querySplit.length; s++) {
                    int ne = querySplit[s].indexOf(" NON EMPTY ");
                    if (ne != -1) {
                       noNonEmptyQuery += querySplit[s].substring(0,ne) + querySplit[s].substring(ne+10);
                       if ((s+1) < querySplit.length) {
                        if (querySplit[s+1].trim().startsWith("columns"))
                            rowne = true;
                        if (querySplit[s+1].trim().startsWith("rows"))
                            colne = true;
                        if (querySplit[s+1].trim().startsWith("pages"))
                            pagne = true;
                       }
                    }
                    else 
                       noNonEmptyQuery +=  querySplit[s] ;
                    if (s<(querySplit.length-1))
                        noNonEmptyQuery += " ON ";
                }
                //======
   		
   		// Query Holds ExecuteResult (for Axis).
   		rex.metadata.Query q=new rex.metadata.Query(getCubeName(),null);
        q.setQuery(new ExecuteResult(smd.execute(removeComments(noNonEmptyQuery),execProperties),new Query(getCubeName(),null)));   		
   		
   		// Getting Model and root of MDX Builder Tree.
   		JTree bTree=builderTree.getTree();   		
   		DefaultTreeModel treeModel=(DefaultTreeModel) bTree.getModel();   		
   		DefaultMutableTreeNode root=(DefaultMutableTreeNode)treeModel.getRoot();
   		
   		// Removing child nodes from With, Axis and Slicer node, If any. 
        for (int i=0; i<root.getChildCount(); i++)
        {
            ((DefaultMBTNode)((DefaultMutableTreeNode)root.getChildAt(i)).getUserObject()).removeAllChildrenFromTheTree(  (DefaultMutableTreeNode)root.getChildAt(i)
                                                                                                                        , (DefaultTreeModel)bTree.getModel());
            ((DefaultTreeModel)bTree.getModel()).nodeChanged(root.getChildAt(i));
        }
        bTree.repaint();
        
        AxisEmpty axisEmpty=new AxisEmpty(removeComments(textArea.getText()));
        // Getting TreeNode Objects from Root, With, Axis and Slicer node.
        Object r = root.getUserObject();
        DefaultMutableTreeNode withTreeNode=(DefaultMutableTreeNode)root.getChildAt(0);
        DefaultMutableTreeNode colTreeNode=(DefaultMutableTreeNode)root.getChildAt(1);
        DefaultMutableTreeNode rowTreeNode=(DefaultMutableTreeNode)root.getChildAt(2);
        DefaultMutableTreeNode pageTreeNode=(DefaultMutableTreeNode)root.getChildAt(3);
        DefaultMutableTreeNode whereTreeNode=(DefaultMutableTreeNode)root.getChildAt(5);
        
        ((DefaultMBTAxisNode)((MBTNode)colTreeNode.getUserObject())).setNonEmpty(axisEmpty.isColumnEmpty());
        ((DefaultMBTAxisNode)((MBTNode)rowTreeNode.getUserObject())).setNonEmpty(axisEmpty.isRowEmpty());
        // Getting MBTNode Objects array associated with MDX Builder Tree. 
        MBTNode[] children = (MBTNode[])((MBTNode)(r)).getMdxBuilderTreeNodes();
        
        // Getting Dimension list element and Tree Root. 
        TreeElement dimTreeRoot=(TreeElement)((DimensionTreeModel)((dimTree.getTree()).getModel())).getRoot();
        int noOfDimension=dimTreeRoot.getChildCount();
        TreeElement [] dimTreeRootChild=new TreeElement[noOfDimension];
        for(int childCount=0;childCount<noOfDimension;childCount++)
        {
        	dimTreeRootChild[childCount]=dimTreeRoot.getChildAt(childCount);
        }
        
        // Contains JPivot parser to parse WITH and SLICER portion of MDX Query.   
   		SegregateMDXWithNWhere withNWhere=new SegregateMDXWithNWhere(removeComments(textArea.getText()));
   		
   		//Getting formulas and slicer. 
   		String with[]=withNWhere.getFormulas();
   		String where=withNWhere.getSlicer();
   		
   		//Getting Columns list from Query.
   		/**/String columns[]=q.getMDXColumnsOnSelect();
   		/**/String rows[]=q.getMDXRowsOnSelect();
   		/**/String pages[]=q.getMDXPagesOnSelect();
   		
   		//for Getting With Properties
   		String dimensionOnWith[]=null,calculatedFormula[]=null;
   		String formatStringOnWith[]=null,memberOrSet[]=null,calculatedMemberName[]=null;
   		
        DefaultMutableTreeNode setNode[]=null;
        
   		String withSetName[]=null;
   		String withSetExp[]=null;
   		
   		//Arrays for holding MDX Query Elements properties.
   		String dimOnWith[]=null;
   		/**/String dimOnColumns[]=null;
   		/**/String dimOnRows[]=null;
   		/**/String dimOnPages[]=null;
   		String dimOnWhere=null;
   		int withOrSet[]=null;
   		int countSet=0,setCounter=0;
   		String withSet[]=null,setCalculatedMemberName[]=null,setCalculatedFormula[]=null;
   		StringBuffer tempSet[]=null;
   		String member="MEMBER";
   		String set="SET";
   		String as=" AS ";
        String [][]setArray=null;
        String [][]setDimension=null;
        int [][]setAxisCount=null;
        int [][]axisSetCount=null;
   		        
        /**
         * Check for with in mdx query. 
         * by Prakash. 11-05-2008.
         */
         
        if(with != null)
        {
        	if(with.length!=0)
        	{
        		withOrSet=new int[with.length];
        		for(int countWith=0;countWith<with.length;countWith++)
        		{
        		    // Check for member
        			if(with[countWith].toUpperCase().startsWith(member))
        			{
        				withOrSet[countWith]=0;// means Member.
        			}
        			// Check for set.
        			if(with[countWith].toUpperCase().startsWith(set))
        			{
        				withOrSet[countWith]=1;// means Set.
        				countSet++;
        				setCounter++;
        			}
        		}
        		if(setCounter!=0)
        		{
        			withSet=new String[countSet];
        			tempSet=new StringBuffer[countSet];
        			setCalculatedMemberName=new String[countSet];
        			setCalculatedFormula=new String[countSet];
        			countSet=0;
        			// looking for set.
        			for(int countWith=0;countWith<with.length;countWith++)
        			{
        			    
        				if(withOrSet[countWith]==1)
        				{
        					withSet[countSet]=with[countWith];
        					tempSet[countSet]=new StringBuffer(with[countWith]);
        					String str[]=with[countWith].split(as);
        					setCalculatedMemberName[countSet]=str[0].replaceAll(set,"").trim();
        					setCalculatedMemberName[countSet]=setCalculatedMemberName[countSet].substring(1,setCalculatedMemberName[countSet].length()-1);
        					setCalculatedFormula[countSet]=str[1].replaceAll("'","").trim();   					
        					countSet++;
        				}
        			}
        		}
        		calculatedFormula=new String[with.length];
        		formatStringOnWith=new String[with.length];
        		dimensionOnWith=new String[with.length];
        		dimOnWith=new String[with.length];
        		calculatedMemberName=new String[with.length];
        		// Looking for member.
        		for(int countWith=0;countWith<with.length;countWith++)
        		{
        			if(with[countWith].toUpperCase().startsWith(member))
        			{
        				String str[]=with[countWith].split(as);
        				str[0]=str[0].replaceAll(member,"").trim();
        				dimensionOnWith[countWith]=str[0].substring(0,str[0].indexOf(']')+1);
        				dimOnWith[countWith]=dimensionOnWith[countWith].substring(1,dimensionOnWith[countWith].length()-1);
        				calculatedMemberName[countWith]=str[0].substring(dimensionOnWith[countWith].length()+1,str[0].length());
        				calculatedMemberName[countWith]=calculatedMemberName[countWith].substring(1,calculatedMemberName[countWith].length()-1);
        				// Checking for FORMAT_STRING in member.
        				if(str[1].indexOf("FORMAT_STRING")==-1)
        				{
        					calculatedFormula[countWith]=str[1].replaceAll("'","").trim();
        				}
        				else
        				{
        					String []temp=str[1].split("FORMAT_STRING");
        					calculatedFormula[countWith]=temp[0].replaceAll(",","");
        					calculatedFormula[countWith]=calculatedFormula[countWith].replaceAll("'","").trim();
        					formatStringOnWith[countWith]="FORMAT_STRING"+temp[1].trim();
        				}
        			}
        			if(with[countWith].startsWith(set))
        			{
        				String str[]=with[countWith].split(as);
        				calculatedMemberName[countWith]=str[0].replaceAll(set,"").trim();
        				calculatedMemberName[countWith]=calculatedMemberName[countWith].substring(1,calculatedMemberName[countWith].length()-1);
        				calculatedFormula[countWith]=str[1].replaceAll("'","").trim();
        			}
        		}
        		if(setCounter!=0)
        		{
        			setNode=new DefaultMutableTreeNode[withSet.length];
        			setNum=new int[withSet.length];
        		}
        	}
        }
        // looking for columns in query.
   		if(columns!=null)
   		{
   			dimOnColumns=new String[columns.length];
   			for(int count=0;count<columns.length;count++)
   			{
   				String str[]=columns[count].split("]");
   				dimOnColumns[count]=str[0].substring(1);
   			}
   		}
   		//looking for rows in query.
   		if(rows!=null)
   		{
   			dimOnRows=new String[rows.length];
   			for(int count=0;count<rows.length;count++)
   			{
   				String str[]=rows[count].split("]");
   				dimOnRows[count]=str[0].substring(1);
   			}
   		}
   		// looking for pages in query.
   		if(pages!=null)
   		{
   			dimOnPages=new String[pages.length];
   			for(int count=0;count<pages.length;count++)
   			{
   				String str[]=pages[count].split("]");
   				dimOnPages[count]=str[0].substring(1);
   			}
   		}
   		// looking for where in query.
   		if(where!=null)
   		{
   			String str[]=where.split("]");
   			dimOnWhere=str[0].substring(2);
   			where=where.substring(1,(where.trim()).length()-1);
   		}

        if(setCounter!=0)
        {
            // looking for set's used in axis and slicer list.
        	for(int setCount=0;setCount<tempSet.length;setCount++)
        	{
        		if(columns!=null)
        		{
        			for(int count=0;count<columns.length;count++)
        			{
        				if(tempSet[setCount] != null && tempSet[setCount].indexOf(columns[count])!=-1)
        				{
        					tempSet[setCount]=tempSet[setCount].replace(tempSet[setCount].indexOf(columns[count]),tempSet[setCount].indexOf(columns[count])+columns[count].length(),"");
        					setNum[setCount]++;
        				}
        			}
        		}
        		if(rows!=null)
        		{	
        			for(int count=0;count<rows.length;count++)
        			{
        				if(tempSet[setCount] != null && tempSet[setCount].indexOf(rows[count])!=-1)
        				{
        					tempSet[setCount]=tempSet[setCount].replace(tempSet[setCount].indexOf(rows[count]),tempSet[setCount].indexOf(rows[count])+rows[count].length(),"");
        					setNum[setCount]++;
        				}
        			}
        		}
        		if(pages!=null)
        		{
        			for(int count=0;count<pages.length;count++)
        			{
        				if(tempSet[setCount] != null && tempSet[setCount].indexOf(pages[count])!=-1)
        				{
        					tempSet[setCount]=tempSet[setCount].replace(tempSet[setCount].indexOf(pages[count]),tempSet[setCount].indexOf(pages[count])+pages[count].length(),"");
        					setNum[setCount]++;
        				}
        			}
        		}
        		if(where!=null)
        		{
        			if(tempSet[setCount] != null && tempSet[setCount].indexOf(where)!=-1)
        			{
        				tempSet[setCount]=tempSet[setCount].replace(tempSet[setCount].indexOf(where),tempSet[setCount].indexOf(where)+where.length(),"");
        				setNum[setCount]++;
        			}
        		}
        	}
        }
        if(setCounter!=0)
        {
            // looking for set's used in axis and slicer list.
        	setArray=new String [setNum.length][];
        	setDimension=new String [setNum.length][];
        	setAxisCount=new int[setNum.length][];
        	axisSetCount=new int[setNum.length][];
        	for(int count=0;count<setArray.length;count++)
        	{
        		setArray[count]=new String[setNum[count]];
        		setDimension[count]=new String[setNum[count]];
        		setAxisCount[count]=new int[4];
        		axisSetCount[count]=new int[4];
        	}        	
        	countSet=0;
        	for(int countWith=0;countWith<with.length;countWith++)
        	{
        		if(withOrSet[countWith]==1)
        		{    			
        			tempSet[countSet]=new StringBuffer(with[countWith]);   					
        			countSet++;
        		}
        	}
        
        	for(int setCount=0;setCount<tempSet.length;setCount++)
        	{
        		int setElementCount=0;
        		if(columns!=null)
        		{
        			for(int count=0;count<columns.length;count++)
        			{
        				if(tempSet[setCount].indexOf(columns[count])!=-1)
        				{
        					tempSet[setCount]=tempSet[setCount].replace(tempSet[setCount].indexOf(columns[count]),tempSet[setCount].indexOf(columns[count])+columns[count].length(),"");
        					setArray[setCount][setElementCount]=columns[count];
        					setDimension[setCount][setElementCount]=dimOnColumns[count];
        					columns[count]=setCalculatedMemberName[setCount];
        					setElementCount++;
        					setAxisCount[setCount][0]++;
        				}
        				if(withSet[setCount].indexOf(columns[count])!=-1)
        				{
        					axisSetCount[setCount][0]++;
        				}
        			}
        		}
        		if(rows!=null)
        		{
        			for(int count=0;count<rows.length;count++)
        			{
        				if(tempSet[setCount].indexOf(rows[count])!=-1)
        				{
        					tempSet[setCount]=tempSet[setCount].replace(tempSet[setCount].indexOf(rows[count]),tempSet[setCount].indexOf(rows[count])+rows[count].length(),"");
        					setArray[setCount][setElementCount]=rows[count];
        					setDimension[setCount][setElementCount]=dimOnRows[count];
        					rows[count]=setCalculatedMemberName[setCount];
        					setElementCount++;
        					setAxisCount[setCount][1]++;
        				}
        				if(withSet[setCount].indexOf(rows[count])!=-1)
        				{
        					axisSetCount[setCount][1]++;	
        				}
        			}
        		}
        		if(pages!=null)
        		{
        			for(int count=0;count<pages.length;count++)
        			{
        				if(tempSet[setCount].indexOf(pages[count])!=-1)
        				{
        					tempSet[setCount]=tempSet[setCount].replace(tempSet[setCount].indexOf(pages[count]),tempSet[setCount].indexOf(pages[count])+pages[count].length(),"");
        					setArray[setCount][setElementCount]=pages[count];
        					setDimension[setCount][setElementCount]=dimOnPages[count];
        					pages[count]=setCalculatedMemberName[setCount];
        					setElementCount++;
        					setAxisCount[setCount][2]++;
        				}
        				if(withSet[setCount].indexOf(pages[count])!=-1)
        				{
        					axisSetCount[setCount][2]++;
        				}
        			}
        		}
        		if(where!=null)
        		{
        			if(tempSet[setCount].indexOf(where)!=-1)
        			{
        				tempSet[setCount]=tempSet[setCount].replace(tempSet[setCount].indexOf(where),tempSet[setCount].indexOf(where)+where.length(),"");
        				setArray[setCount][setElementCount]=where;
        				setDimension[setCount][setElementCount]=dimOnWhere;
        				where=setCalculatedMemberName[setCount];
        				setElementCount++;
        				setAxisCount[setCount][3]++;
        			}
        			if(withSet[setCount].indexOf(where)!=-1)
        			{
        				axisSetCount[setCount][3]++;
        			}
        		}
        	}
        }

        if(setCounter!=0)
        {
            // looking for member's used in axis and slicer list.
            for(int setCount=0;setCount<setCalculatedMemberName.length;setCount++)
        	{
        		if(axisSetCount[setCount][0]>0)
        		{
        			int noOfTimeSetInAxes=axisSetCount[setCount][0]/setAxisCount[setCount][0];
        			String temp[]=new String[columns.length-((setAxisCount[setCount][0]*noOfTimeSetInAxes)-(noOfTimeSetInAxes*1))];
        			String tempForDimension[]=new String[dimOnColumns.length-((setAxisCount[setCount][0]*noOfTimeSetInAxes)-(noOfTimeSetInAxes*1))];
        			for(int count=0,tempCount=0;count<columns.length;count++)
        			{
        				if(setCalculatedMemberName[setCount].indexOf(columns[count])!=-1)
        				{
        					count=count+(setAxisCount[setCount][0]-1);
        					temp[tempCount]=columns[count];
        					tempForDimension[tempCount]=dimOnColumns[count];
        				}
        				else
        				{
        					temp[tempCount]=columns[count];
        					tempForDimension[tempCount]=dimOnColumns[count];
        				}
        				tempCount++;        			
        			}
        			columns=temp;
        			dimOnColumns=tempForDimension;
        		}
        		if(axisSetCount[setCount][1]>0)
        		{
        			int noOfTimeSetInAxes=axisSetCount[setCount][1]/setAxisCount[setCount][1];
        			String temp[]=new String[rows.length-((setAxisCount[setCount][1]*noOfTimeSetInAxes)-(noOfTimeSetInAxes*1))];
        			String tempForDimension[]=new String[dimOnRows.length-((setAxisCount[setCount][1]*noOfTimeSetInAxes)-(noOfTimeSetInAxes*1))];
        			for(int count=0,tempCount=0;count<rows.length;count++)
        			{
        				if(setCalculatedMemberName[setCount].indexOf(rows[count])!=-1)
        				{        				
        					count=count+(setAxisCount[setCount][1]-1);
        					temp[tempCount]=rows[count];
        					tempForDimension[tempCount]=dimOnRows[count];
        				}
        				else
        				{
        					temp[tempCount]=rows[count];
        					tempForDimension[tempCount]=dimOnRows[count];
        				}
        				tempCount++;
        			}
        			rows=temp;
        			dimOnRows=tempForDimension;
        		}
        		if(axisSetCount[setCount][2]>0)
        		{
        			int noOfTimeSetInAxes=axisSetCount[setCount][2]/setAxisCount[setCount][2];
        			String temp[]=new String[pages.length-((setAxisCount[setCount][2]*noOfTimeSetInAxes)-(noOfTimeSetInAxes*1))];
        			String tempForDimension[]=new String[dimOnPages.length-((setAxisCount[setCount][2]*noOfTimeSetInAxes)-(noOfTimeSetInAxes*1))];
        			for(int count=0,tempCount=0;count<pages.length;count++)
        			{
        				if(setCalculatedMemberName[setCount].indexOf(pages[count])!=-1)
        				{        				
        					count=count+(setAxisCount[setCount][2]-1);
        					temp[tempCount]=pages[count];
        					tempForDimension[tempCount]=dimOnPages[count];
        				}
        				else
        				{
        					temp[tempCount]=pages[count];
        					tempForDimension[tempCount]=dimOnPages[count];
        				}
        				tempCount++;
        			}
        			pages=temp;
        			dimOnPages=tempForDimension;
        		}
        		if(axisSetCount[setCount][3]>0)
        		{
        			if(setCalculatedMemberName[setCount].indexOf(where)!=-1)
        			{
        			    //Do Nothing.
        			}
        		}
        	}
        }
        
        if(setCounter!=0)
        {
        	for(int setCount=0;setCount<withSet.length;setCount++)
        	{
        	    // setNode holds reference of all with nodes used in axes.
        		setNode[setCount]=((MBTWithMembersNode)((MBTNode)withTreeNode.getUserObject())).handleSetDropFromQuery( withTreeNode,treeModel,setCalculatedMemberName[setCount]);
        	}
        }

        //      Updating MBT with With's member by finding it on Dimension tree
        for(int childCount=0;childCount<noOfDimension;childCount++)
        {
        	if(with.length!=0)
        	{
        		for(int countWith=0;countWith<with.length;countWith++)
        		{
        			if(with[countWith].startsWith(member))
        			{
        				if((((DimensionTreeElement)(dimTreeRootChild[childCount].getUserObject())).toString()).equalsIgnoreCase(dimOnWith[countWith].trim()))
        				{
        					findWithMember((DimensionTreeElement)dimTreeRootChild[childCount].getUserObject(),dimensionOnWith[countWith],calculatedMemberName[countWith],calculatedFormula[countWith],formatStringOnWith[countWith],withTreeNode,treeModel,children[0],bTree);
        				}
        			}
        		}
        	}
        }
        
        //      Updating MBT with With's set by finding it on Dimension tree
        for(int childCount=0;childCount<noOfDimension;childCount++)
        {
        	if(setCounter!=0)
        	{
        		for(int setCount=0;setCount<setArray.length;setCount++)
        		{
        			for(int countSetElement=0;countSetElement<setArray[setCount].length;countSetElement++)
        			{
        				if((((DimensionTreeElement)(dimTreeRootChild[childCount].getUserObject())).toString()).equalsIgnoreCase(setDimension[setCount][countSetElement].trim()))
        				{
        					stopSearch = false;
        					findWithSet((DimensionTreeElement)dimTreeRootChild[childCount].getUserObject(),setArray[setCount][countSetElement],bTree,treeModel,setNode[setCount],((MBTNode)setNode[setCount].getUserObject()));
                                                
        				}
        			}
        		}
        	}
        }
        //      Updating MBT tree by finding member nodes from Dimension tree for all axis. 
        for(int childCount=0;childCount<noOfDimension;childCount++)
        {
            memberStopSearch = false;
            //      Updating MBT tree by finding member nodes from Dimension tree for columns.
        	if(columns!=null)
        	{
        		for(int countCol=0;countCol<columns.length;countCol++)
        		{
        			if((((DimensionTreeElement)(dimTreeRootChild[childCount].getUserObject())).toString()).equalsIgnoreCase(dimOnColumns[countCol].trim()))
        			{
        					stopSearch = false;
        					memberStopSearch = false;     //===== .Members handling change
        					find((DimensionTreeElement)dimTreeRootChild[childCount].getUserObject(),columns[countCol].trim(),colTreeNode,treeModel,children[1],bTree,withTreeNode,setNode,setCalculatedMemberName);
        					//=====    .Members handling
        					if (memberStopSearch)
        					    break;
        					//====
        			}
        		}
        	}
            //      Updating MBT tree by finding member nodes from Dimension tree for rows.
        	if(rows!=null)
        	{
        		for(int countRow=0;countRow<rows.length;countRow++)
        		{
        			if((((DimensionTreeElement)(dimTreeRootChild[childCount].getUserObject())).toString()).equalsIgnoreCase(dimOnRows[countRow].trim()))
        			{
        					stopSearch = false;
        					memberStopSearch = false;    //===== .Members handling change
        					find((DimensionTreeElement)dimTreeRootChild[childCount].getUserObject(),rows[countRow].trim(),rowTreeNode,treeModel,children[2],bTree,withTreeNode,setNode,setCalculatedMemberName);
        					//===== .Members handling
        					if (memberStopSearch)
        					    break;
        					//====
        			}
        		}
        	}
        	if(where!=null)
        	{
        		if((((DimensionTreeElement)(dimTreeRootChild[childCount].getUserObject())).toString()).equalsIgnoreCase(dimOnWhere.trim()))
        		{
        			stopSearch = false;
        			find((DimensionTreeElement)dimTreeRootChild[childCount].getUserObject(),where.trim(),whereTreeNode,treeModel,children[5],bTree,withTreeNode,setNode,setCalculatedMemberName);
        		}
        	}
        }
        textArea.setText(builderTree.getMdx());
	}
   	catch(Exception exception)
	{
   		JOptionPane.showMessageDialog(this,exception.toString());
	}
   }

   /**
    *   Copyright (C) 2006 CINCOM SYSTEMS, INC.
    *   All Rights Reserved
    *   Copyright (C) 2006 Igor Mekterovic
    *   All Rights Reserved
    */
   
   // Finds with's Member node from dimension tree and update it on MBT Tree. 
   private void findWithMember(DimensionTreeElement element,String mdxElement,String calcMemberName,String withFormula,String formatStr,DefaultMutableTreeNode treeNode,DefaultTreeModel treeModel,MBTNode node,JTree bTree)
   {
   		if(((element.getUniqueName()).trim()).equalsIgnoreCase(mdxElement.trim()))
   		{
   			((MBTWithMembersNode)node).handleMemberDropFromQuery((Object)element,treeNode,treeModel,calcMemberName,withFormula,formatStr);
   			((DefaultTreeModel)bTree.getModel()).nodeChanged(treeNode);
   			bTree.expandPath(new TreePath(treeNode.getPath()));   
   			bTree.repaint();
   			stopSearch = true;
   			return;
   		}
   }
   
   /**
    *   Copyright (C) 2006 CINCOM SYSTEMS, INC.
    *   All Rights Reserved
    *   Copyright (C) 2006 Igor Mekterovic
    *   All Rights Reserved
    */
   // Finds with's set node from dimension tree and update it on MBT Tree recursively.
   private void findWithSet(DimensionTreeElement element,String mdxElement,JTree bTree,DefaultTreeModel treeModel,DefaultMutableTreeNode setNode,MBTNode node)
   {
   		int i;
   		DimensionTreeElement[] dte;
	   	dte = element.getChildren(true);
		for (i = 0; dte != null && i < dte.length; i++) 
		{
			if(((dte[i].getUniqueName()).trim()).equalsIgnoreCase(mdxElement.trim()))
			{				
				node.handleDrop((Object)dte[i],setNode,treeModel);
				((DefaultTreeModel)bTree.getModel()).nodeChanged(setNode);
				bTree.expandPath(new TreePath(setNode.getPath()));
				bTree.repaint();
				stopSearch = true;
				return;
			}
			if (!stopSearch) 
			{
				findWithSet(dte[i],mdxElement,bTree,treeModel,setNode,node);  
			}
		}
   	}	
    
   /**
    *   Copyright (C) 2006 CINCOM SYSTEMS, INC.
    *   All Rights Reserved
    *   Copyright (C) 2006 Igor Mekterovic
    *   All Rights Reserved
    */
   // Finds Member node's in axis from dimension tree recursively and update it on MBT Tree.
   private void find(DimensionTreeElement element,String mdxElement,DefaultMutableTreeNode treeNode,DefaultTreeModel treeModel,MBTNode node,JTree bTree,DefaultMutableTreeNode withNode,DefaultMutableTreeNode []setNode,String set[])
   {
   		int i;
   		if (!stopSearch)
   		{ 
   			for(int kk=0;kk<withNode.getChildCount();kk++)
   			{
   				if(((MBTNode)((DefaultMutableTreeNode)withNode.getChildAt(kk)).getUserObject()).toString().startsWith("MEMBER"))
   				{
   					if(((MBTNode)((DefaultMutableTreeNode)withNode.getChildAt(kk)).getUserObject()).toString().replaceAll("MEMBER","").trim().equalsIgnoreCase(mdxElement.trim()))
   					{
   						node.handleDrop(((MBTCalculatedMemberNode)((MBTNode)((DefaultMutableTreeNode)withNode.getChildAt(kk)).getUserObject())),treeNode,treeModel);
   						((DefaultTreeModel)bTree.getModel()).nodeChanged(treeNode);
   						bTree.expandPath(new TreePath(treeNode.getPath()));
   						bTree.repaint();
   						stopSearch = true;
   						return;
   					}
   				}
   			}
   			if(set != null)
   			{
   				for(int kk=0;kk<set.length;kk++)
   				{
   					if(set[kk].trim().equalsIgnoreCase(mdxElement.trim()))
   					{
   						node.handleDrop(setNode[kk].getUserObject(),treeNode,treeModel);
   						((DefaultTreeModel)bTree.getModel()).nodeChanged(treeNode);
   						bTree.expandPath(new TreePath(treeNode.getPath()));
   						bTree.repaint();
   						stopSearch = true;
   						return;
   					}
   				}
   			}
   		}
   	   	DimensionTreeElement[] dte;
   	   	dte = element.getChildren(true);
   		main: for (i = 0; dte != null && i < dte.length; i++) 
   		{
                        //======     .Members handling
                        //.members dimensions nodes will be collapsed
                        boolean dotMembers = false;
                        if (dotMembersDims != null) {
                            String nodestr = ((dte[i].getUniqueName()).trim());
                            String dimestr = ((dte[i].getDimensionUniqueName()).trim());
                            System.out.println(".."+nodestr);
                            for(int dot=0; dot<dotMembersDims.length; dot++)
                            {
                                if (dotMembersDims[dot].startsWith(dimestr)) 
                                    dotMembers = true;
                                if(nodestr.startsWith(dotMembersDims[dot]))
                                {                                   
                                    //=={
                                            // You can change the following line to pass element or dte[i] as parameter
                                            // element works fine in most of cases whereas dte[i] gives first child element.
                                            if (dotMembersDims[dot].indexOf(".") != -1) {
                                                if(nodestr.equals(dotMembersDims[dot])) {
                                                    node.handleDrop(dte[i],treeNode,treeModel);
                                                    ((DefaultTreeModel)bTree.getModel()).nodeChanged(treeNode);
                                                    bTree.expandPath(new TreePath(treeNode.getPath()));
                                                    bTree.repaint();
                                                    memberStopSearch = true;
                                                    return;
                                                } else
                                                    continue main;
                                            }
                                            else
                                            {    node.handleDrop(element,treeNode,treeModel);
                                                ((DefaultTreeModel)bTree.getModel()).nodeChanged(treeNode);
                                                bTree.expandPath(new TreePath(treeNode.getPath()));
                                                bTree.repaint();
                                                memberStopSearch = true;
                                                return;
                                            }
                                   //== }
                               }
                            }
                        }  
                        //===== 
                            if((!dotMembers) && ((dte[i].getUniqueName()).trim()).equalsIgnoreCase(mdxElement.trim()))
                            {
                                    node.handleDrop((Object)dte[i],treeNode,treeModel);
                                    ((DefaultTreeModel)bTree.getModel()).nodeChanged(treeNode);
                                    bTree.expandPath(new TreePath(treeNode.getPath()));
                                    bTree.repaint();
                                    stopSearch = true;
                                    return;
                            }
                        
   			if (!stopSearch) 
   			{
   				find(dte[i],mdxElement,treeNode,treeModel,node,bTree,withNode,setNode,set);  
   			}
   		}
   }
   
   //By Prakash
   // Returns default file name for saving mdx query.
   public String getDefaultQueryFileName()
   {
   	int counter=1;
   	String parentDir="";
   	try
	{
	parentDir=(ReadEnv.getEnvVars()).getProperty("USERPROFILE");
	}
   	catch(Exception exc)
	{}
	File file=new File(parentDir.concat("//query"+counter+".mdx"));
	while(file.exists())
	{		
		counter++;
		file=new File(parentDir.concat("//query"+counter+".mdx"));
	}	
   	return file.getAbsolutePath();
   }   
   
   /**
    * Returns current filename.
    */  
   public String getFileName()
   {
    if(lblFileName.getText().trim().length()==0)
    {   		
        return "";
    }
   	return lblFileName.getText();
   }
   
   /**
    * Set name of the file. 
    * @param fName
    */
   public void setFileName(String fName)
   {
   	lblFileName.setText(fName);
   }
   
   public String getTAContent()
   {
   	return textArea.getText();
   }
   
   public void setTAContent(String fName)
   {
   	textArea.setText(fName);
   }  
   public void setLocale(Locale locale)
   {
    toolbar.setLocale(locale);   
   }
   public Locale getLocale()
   {
    return toolbar.getLocale();   
   }
   public boolean getSaveStatus()
   {
       return toolbar.getSave();
   }
   public void setSaveStatus(boolean bool)
   {
       toolbar.setSave(bool);
   }
   public void newQuery()
   {
       toolbar.newQuery();
   }
   public void openQuery()
   {
       toolbar.openQuery();
   }
   public void saveQuery()
   {
       toolbar.saveQuery();
   }
   // End of addition by Prakash.
   /**
    * Inner class that displays the pop-up menu.
    * @author Igor Mekterovic
    * @version 0.3
    */
   class PopupListener extends MouseAdapter {
       public void mousePressed(MouseEvent e) {
//          jspTextAndTree.setDividerLocation(1.);
          maybeShowPopup(e);
       }

       public void mouseReleased(MouseEvent e) {
           maybeShowPopup(e);
       }

       private void maybeShowPopup(MouseEvent e) {
           if (e.isPopupTrigger()) {
               popup.show(e.getComponent(),
                          e.getX(), e.getY());
           }
       }
   }


   /**
    * Inner class that displays the Color menu for setting the foreground color.
    * @author Igor Mekterovic
    * @version 0.3
    */
   class ForegroundListener implements ActionListener {
      public void actionPerformed(ActionEvent e) {
         ColorMenu m = (ColorMenu) e.getSource();
         setForegroundColor(m.getColor());
      }
   }

   /**
    * Inner class that displays the Color menu for setting the background color.
    * @author Igor Mekterovic
    * @version 0.3
    */
   class BackgroundListener implements ActionListener {
      public void actionPerformed(ActionEvent e) {
         ColorMenu m = (ColorMenu) e.getSource();
         setBackgroundColor(m.getColor());
      }
   }
   
   /**
    *   Copyright (C) 2006 CINCOM SYSTEMS, INC.
    *   All Rights Reserved
    *   Copyright (C) 2006 Igor Mekterovic
    *   All Rights Reserved
    */
/**
* 	Lines added by Prakash
* 	Cincom Systems  
* 	8 May 2006
* 	Inner class which execute saveJSPListener for generating JSP page from MDX Query. 
*/
    
   class SaveJSP implements ActionListener {
      public void actionPerformed(ActionEvent e) {
      	new SaveJSPListener(textArea.getText(),properties.getDataSourceInfo(),properties.getCatalog());
      }
   }
   
   /**
    *   Copyright (C) 2006 CINCOM SYSTEMS, INC.
    *   All Rights Reserved
    *   Copyright (C) 2006 Igor Mekterovic
    *   All Rights Reserved
    */
   /**
   * 	Lines added by Prakash
   * 	Cincom Systems  
   * 	1 Sept 2006
   * 	Inner class which helps in seggregating Formula and slicer from MDX Query by using jpivot parser. 
   */
   class SegregateMDXWithNWhere
   {
   		private String with[],where;
   		public SegregateMDXWithNWhere(String mdx)
   		{
   			mdx = mdx.replaceAll("\r", "");
   		  	try {

   		    parser parser_obj;
   		    Reader reader = new StringReader(mdx);
   		    parser_obj = new parser(new Lexer(reader));

   		    Symbol parse_tree = null;
   		    ParsedQuery pQuery = null;
   		    
   		    parse_tree = parser_obj.parse();
   		 
   		    pQuery = (ParsedQuery) parse_tree.value;
   		    pQuery.afterParse();
   		    Formula [] formula=pQuery.getFormulas();
   		    Exp slicer=pQuery.getSlicer();
   		    with=new String[formula.length];
   		    for(int i=0;i<formula.length;i++)
   		    {
   		    	with[i]=(formula[i].toMdx()).trim();
   		    }
   		    where=slicer.toMdx();   		    
   		    } catch (Exception e) {
   		        System.out.println(e.toString());
   		      }
   		}
   		public String[] getFormulas()
   		{
   			return with;
   		}
   		public String getSlicer()
   		{
   			return where;
   		}
   }
  
   /**
    *   Copyright (C) 2006 CINCOM SYSTEMS, INC.
    *   All Rights Reserved
    *   Copyright (C) 2006 Igor Mekterovic
    *   All Rights Reserved
    */
   class CreateTree implements ActionListener {
    public void actionPerformed(ActionEvent e) {
    	parseQuery();
    }
 }
// End of addition by Prakash
   
   /**
    * Inner class that listens to the key actions in the textArea and replaces the keywords
    * with properly cased keywords. (e.g. seLECT -> SELECT)
    * @author Igor Mekterovic
    * @version 0.3
    */
   class TextAreaKeyListener extends KeyAdapter {


      public void keyPressed(KeyEvent e){
      	/*
      	 * Added by Prakash
      	 */
    	if (e.isControlDown() ||
        		e.isShiftDown() ||
				e.isAltDown() ||
				e.getKeyCode() == e.VK_UP ||
				e.getKeyCode() == e.VK_DOWN ||
				e.getKeyCode() == e.VK_RIGHT ||
				e.getKeyCode() == e.VK_LEFT ||
				e.getKeyCode() == e.VK_NUM_LOCK ||
				e.getKeyCode() == e.VK_ESCAPE ||
				e.getKeyCode() == e.VK_CAPS_LOCK){
        		//Do Nothing
        }
        else
        {
        	toolbar.setSave(false);
        }
    	/*
    	 * End
    	 */
         if (e.isControlDown() &&  e.isShiftDown() && e.getKeyCode() == KeyEvent.VK_ENTER){
            runAllQuery();
         }else if (e.isControlDown() &&  e.getKeyCode() == KeyEvent.VK_ENTER){
            runSelectedQuery();
         }         
         if (   e.getKeyCode() == KeyEvent.VK_SPACE
             || e.getKeyCode() == KeyEvent.VK_ENTER
             || e.getKeyCode() == KeyEvent.VK_TAB
             || e.getKeyCode() == KeyEvent.VK_COMMA
             || e.getKeyCode() == KeyEvent.VK_RIGHT
             || e.getKeyCode() == KeyEvent.VK_LEFT
             || e.getKeyCode() == KeyEvent.VK_UP
             || e.getKeyCode() == KeyEvent.VK_DOWN){
            int start = textArea.getCaretPosition() - 1;
            while (start > 0){
               char ch = textArea.getText().charAt(start);
               if ((ch == ' ') || (ch =='\n') || (ch == '\t')){
                  start++;
                  break;
               }
               start--;
            }
            if (start >= 0){
               if (keywords.get(textArea.getText().substring(start, textArea.getCaretPosition()).toUpperCase()) != null){
                  textArea.replaceRange( (String) keywords.get(textArea.getText().substring(start, textArea.getCaretPosition()).toUpperCase())
                                        , start
                                        , textArea.getCaretPosition());

               }
            }
         }
         if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_V){
         // this is paste! I should parse whole text now for keywords!
         // I'll do that in next version should there be an interest in it.

         }

      }
      public void keyTyped(KeyEvent e){

      }
   }


   /**
    * Main class used for testing the Mdx Editor.
    * @param args String[]
    */
   public static void main(String[] args){
      String url = JOptionPane.showInputDialog("Please input an Foodmart 2000 service URL", "http://localhost:8080/xmla/msxisapi.dll");
      ServerMetadata svm = new ServerMetadata( url);

      XMLADiscoverRestrictions restrictions =  XMLAObjectsFactory.newXMLADiscoverRestrictions();
      XMLADiscoverProperties   properties   =  XMLAObjectsFactory.newXMLADiscoverProperties();



      restrictions.setCatalog("Foodmart 2000");
      restrictions.setCubeName("Sales");


      properties.setDataSourceInfo("Local Analysis Server");
      properties.setCatalog("Foodmart 2000");
      properties.setFormat("Tabular");
      properties.setContent("SchemaData");



      JFrame frame = new JFrame("Test mdx editor");

      frame.getContentPane().add(new MdxEditor(restrictions, properties, svm, "Sales"), BorderLayout.CENTER);

      frame.addWindowListener(new WindowAdapter() {
         public void windowClosing(WindowEvent e) {
            System.exit(0);
         }
      });
      frame.pack();
//      frame.setExtendedState(frame.MAXIMIZED_BOTH);
      frame.setVisible(true);

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
        applyI18n();
    }
     /**
  *  Helper method to implement locatization when language is changed
  */
      public void applyI18n(){
          mnuSaveJSP.setText(I18n.getString("menu.saveToJsp"));
          mnuCreateTree.setText(I18n.getString("menu.updateMBTree"));
          
          if(foregroundMenu instanceof ColorMenu){
              foregroundMenu.setText(I18n.getString("menu.foregroundColor"));
              backgroundMenu.setText(I18n.getString("menu.backgroundColor"));
          }
          MdxEditorToolbar.resultPositions.put(MdxEditorToolbar.VERTICAL_SPLIT_PANE,I18n.getString("panel.vertSplit") );
          MdxEditorToolbar.resultPositions.put(MdxEditorToolbar.HORIZONTAL_SPLIT_PANE,I18n.getString("panel.horzSplit") );
          MdxEditorToolbar.resultPositions.put(MdxEditorToolbar.TABBED_PANE_UP,I18n.getString("panel.tabUp") );
          MdxEditorToolbar.resultPositions.put(MdxEditorToolbar.TABBED_PANE_LEFT,I18n.getString("panel.tabLeft") );
          MdxEditorToolbar.resultPositions.put(MdxEditorToolbar.TABBED_PANE_RIGHT,I18n.getString("panel.tabRight") );
          MdxEditorToolbar.resultPositions.put(MdxEditorToolbar.TABBED_PANE_BOTTOM,I18n.getString("panel.tabBottom") );
    } 
  /* end of modification for I18n */

}
