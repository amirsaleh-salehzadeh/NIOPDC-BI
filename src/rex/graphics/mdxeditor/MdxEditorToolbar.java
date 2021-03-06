package rex.graphics.mdxeditor;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
//import java.io.IOException;
//import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
//import java.io.OutputStream;
//import java.net.MalformedURLException;
//import java.net.URI;
//import java.net.URISyntaxException;
import java.util.*;
import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
//import javax.swing.event.InternalFrameAdapter;
//import javax.swing.event.InternalFrameEvent;
//import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
//import org.w3c.dom.Element;
////import org.w3c.dom.NodeList;
//import org.w3c.dom.Node;

//import javax.xml.transform.TransformerFactory;
//import javax.xml.transform.Transformer;
//import javax.xml.transform.dom.DOMSource;
//import javax.xml.parsers.DocumentBuilderFactory;
//import javax.xml.parsers.ParserConfigurationException;


//import rex.graphics.mdxeditor.MdxEditor.BackgroundListener;
//import rex.graphics.mdxeditor.MdxEditor.ForegroundListener;
//import rex.graphics.mdxeditor.jsp.ReadEnv;
//import rex.graphics.mdxeditor.mdxbuilder.MdxBuilderTree;
import rex.graphics.mdxeditor.mdxbuilder.nodes.DefaultMBTAxisNode;
import rex.graphics.mdxeditor.mdxbuilder.nodes.DefaultMBTNode;
import rex.graphics.mdxeditor.mdxbuilder.nodes.MBTNode;
import rex.utils.*;

import java.awt.datatransfer.*;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

/**
 * MdxEditor's toolbar component.
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
 
public class MdxEditorToolbar extends JPanel 
        implements LanguageChangedListener{ 
   private MdxEditor parent;
 /**
  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
  * All Rights Reserved
  * Copyright (C) 2006 Igor Mekterovic
  * All Rights Reserved
  */ 
  
   /*declaring labels here*/
   /* implementing localization */
   JLabel fontC= new JLabel(I18n.getString("label.fontC"));
   JLabel fontSizeC= new JLabel(I18n.getString("label.fontSizeC"));
   JLabel tabSizeC= new JLabel(I18n.getString("label.tabSizeC"));
   JLabel resultPosC= new JLabel(I18n.getString("label.resultPosC"));
     /* end of modification for I18n */

   private JLabel   runQuery;
   private JLabel   runSelectedQuery;
   private JLabel   showRowTotals;
   private JLabel   showColumnTotals;
   public JLabel   insertCodeSkeleton;
   
   
   ColorMenu foregroundMenu, backgroundMenu;

   JComboBox  cbFonts
            , cbSizes
            , cbTabSize
            , cbResultPosition;

   JToggleButton  boldButton
                , italicButton
                , appendGeneratedMDX;

 /**
  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
  * All Rights Reserved
  * Copyright (C) 2006 Igor Mekterovic
  * All Rights Reserved
  */ 
/* modifying the varible declaration to static instead of static final */
    public static final String 
      VERTICAL_SPLIT_PANE,
      HORIZONTAL_SPLIT_PANE,
      TABBED_PANE_UP,
      TABBED_PANE_LEFT,
      TABBED_PANE_RIGHT,
      TABBED_PANE_BOTTOM;

    /**
     * Created variable to avoid repeated entry of localization's string.
     * by Prakash. 09-05-2008.
     */      
    static String panelVertSplit="panel.vertSplit";
    static String panelHorzSplit="panel.horzSplit";
    static String panelTabUp="panel.tabUp";
    static String panelTabLeft="panel.tabLeft";
    static String panelTabRight="panel.tabRight";
    static String panelTabBottom="panel.tabBottom";
    String msgTextWantToSave="msgText.wantToSave";
    String dotMBT=".mbt";
    /*
     * End of the Insertion.
     */
    
   public static HashMap resultPositions;
   static {
      VERTICAL_SPLIT_PANE = "0";
      HORIZONTAL_SPLIT_PANE = "1";
      TABBED_PANE_UP = "2";
      TABBED_PANE_LEFT = "3";
      TABBED_PANE_RIGHT = "4";
      TABBED_PANE_BOTTOM = "5";
   
      resultPositions = new HashMap(6);
 /**
  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
  * All Rights Reserved
  * Copyright (C) 2006 Igor Mekterovic
  * All Rights Reserved
  */ 
  
  
   /* implementing localization */
      
      /**
       * Replaced String literals with variables to avoid pmd violation named AvoidDuplicateLiterals.
       * by Prakash. 09-05-2007. 
       */
      
      resultPositions.put(VERTICAL_SPLIT_PANE, I18n.getString(panelVertSplit));
      resultPositions.put(HORIZONTAL_SPLIT_PANE,I18n.getString(panelHorzSplit));
      resultPositions.put(TABBED_PANE_UP, I18n.getString(panelTabUp));
      resultPositions.put(TABBED_PANE_LEFT, I18n.getString(panelTabLeft));
      resultPositions.put(TABBED_PANE_RIGHT, I18n.getString(panelTabRight));
      resultPositions.put(TABBED_PANE_BOTTOM, I18n.getString(panelTabBottom));
      
      /*
       * End of the modification. 
       */
      
        /* end of modification for I18n */


      
   }
   private boolean showRowTotalsOn, showColumnTotalsOn;
   private static ImageIcon showRowTotalsIcon;
   private static ImageIcon showColumnTotalsIcon;
   private static ImageIcon hideRowTotalsIcon;
   private static ImageIcon hideColumnTotalsIcon;
   static{
      showRowTotalsIcon      = S.getAppIcon("toolbox_show_row_totals.gif");
      hideRowTotalsIcon      = S.getAppIcon("toolbox_hide_row_totals.gif");
      showColumnTotalsIcon   = S.getAppIcon("toolbox_show_column_totals.gif");
      hideColumnTotalsIcon   = S.getAppIcon("toolbox_hide_column_totals.gif");
   }

 	/**
 	 *   Copyright (C) 2006 CINCOM SYSTEMS, INC.
 	 *   All Rights Reserved
 	 *   Copyright (C) 2006 Igor Mekterovic
 	 *   All Rights Reserved
 	 */
  
  /*
   * Lines Inserted by Prakash Cincom Systems. on 16 Nov. 2006
   * Introducing MenuBar.
   * 
   *	File --> New, Open, Save, Save As�, Recent File list, Exit
   *	Edit --> Cut, Copy, Delete, Paste, Select All
   *	View --> Font, Tab Size, Foreground, Background, Layout, Show Row Totals, Show Column Totals
   *
   */

   String lastUsed = "RECENT_OPENED_FILE";
   private boolean status=true;
   Component o;
   private String recentURL[] = new String[4];
   
   	Document document;
	DocListener documentListener;//By Prakash. 
	JMenuBar menubar = new JMenuBar();
 /**
  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
  * All Rights Reserved
  * Copyright (C) 2006 Igor Mekterovic
  * All Rights Reserved
  */
        /* implementing localization */
        JMenu fileMenu = new JMenu(I18n.getString("menu.file")); 
	JMenu editMenu = new JMenu(I18n.getString("menu.edit"));
	JMenu viewMenu = new JMenu(I18n.getString("menu.view"));
        JMenuItem newMenuItem = new JMenuItem(I18n.getString("menu.new"));
	JMenuItem openMenuItem = new JMenuItem(I18n.getString("menu.open"));
	JMenuItem saveMenuItem = new JMenuItem(I18n.getString("menu.save"));
	JMenuItem saveAsMenuItem = new JMenuItem(I18n.getString("menu.saveAs"));
	JMenu rflMenu = new JMenu(I18n.getString("menu.recentFileList"));	
	JMenuItem exitMenuItem = new JMenuItem(I18n.getString("menu.exit"));
	
	JMenuItem lastUsed1MenuItem = new JMenuItem();
	JMenuItem lastUsed2MenuItem = new JMenuItem();
	JMenuItem lastUsed3MenuItem = new JMenuItem();
	JMenuItem lastUsed4MenuItem = new JMenuItem();

	JMenuItem cutMenuItem = new JMenuItem(I18n.getString("menu.cut"));
	JMenuItem copyMenuItem = new JMenuItem(I18n.getString("menu.copy"));
	JMenuItem deleteMenuItem = new JMenuItem(I18n.getString("menu.delete"));
	JMenuItem pasteMenuItem = new JMenuItem(I18n.getString("menu.paste"));
	JMenuItem selectMenuItem = new JMenuItem(I18n.getString("menu.select"));
	
	JMenu fontMenu = new JMenu(I18n.getString("menu.font"));
	JMenu fontNameMenu = new JMenu(I18n.getString("menu.fontName"));
	JMenu fontSizeMenu = new JMenu(I18n.getString("menu.fontSize"));
	JCheckBoxMenuItem boldMenuItem = new JCheckBoxMenuItem(I18n.getString("menu.bold"));
	JCheckBoxMenuItem italicMenuItem = new JCheckBoxMenuItem(I18n.getString("menu.italic"));
	
	JMenu tsMenu = new JMenu(I18n.getString("menu.tabSize"));
	  /* end of modification for I18n */

	JRadioButtonMenuItem tabMenuItem2 = new JRadioButtonMenuItem("2");
	JRadioButtonMenuItem tabMenuItem3 = new JRadioButtonMenuItem("3");
	JRadioButtonMenuItem tabMenuItem4 = new JRadioButtonMenuItem("4");
	
	ButtonGroup tabGroup=new ButtonGroup();
	ButtonGroup fontGroup=new ButtonGroup();
	ButtonGroup fontSizeGroup=new ButtonGroup();
	
 /**
  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
  * All Rights Reserved
  * Copyright (C) 2006 Igor Mekterovic
  * All Rights Reserved
  */
        /* implementing localization */
        JMenu layoutMenu = new JMenu(I18n.getString("menu.layout"));
	JCheckBoxMenuItem srtMenuItem = new JCheckBoxMenuItem(I18n.getString("menu.showRows"));
	JCheckBoxMenuItem sctMenuItem = new JCheckBoxMenuItem(I18n.getString("menu.showCols"));
          /* end of modification for I18n */

	JRadioButtonMenuItem vsMenuItem = new JRadioButtonMenuItem((String)resultPositions.get(VERTICAL_SPLIT_PANE));
	JRadioButtonMenuItem hsMenuItem = new JRadioButtonMenuItem((String)resultPositions.get(HORIZONTAL_SPLIT_PANE));
	JRadioButtonMenuItem tuMenuItem = new JRadioButtonMenuItem((String)resultPositions.get(TABBED_PANE_UP));
	JRadioButtonMenuItem tlMenuItem = new JRadioButtonMenuItem((String)resultPositions.get(TABBED_PANE_LEFT));
	JRadioButtonMenuItem trMenuItem = new JRadioButtonMenuItem((String)resultPositions.get(TABBED_PANE_RIGHT));
	JRadioButtonMenuItem tbMenuItem = new JRadioButtonMenuItem((String)resultPositions.get(TABBED_PANE_BOTTOM));
	ButtonGroup layoutGroup=new ButtonGroup();
	
	
	LocaleOptionPane localeMenu=new LocaleOptionPane();
	
	JTree tree;	// Will hold MDX Builder Tree.
	DefaultMutableTreeNode root;// root node of MBT.
	JFileChooser jfc = new JFileChooser();	
  //end of the additions
   

   /**
    * Creates all the buttons, labels, comboboxes, etc.
    * @param _parent MdxEditor
    */
   public MdxEditorToolbar(MdxEditor _parent) {

      //sbalda
     
      parent = _parent;
      runQuery = new JLabel(S.getAppIcon("runquery.gif"));
      runQuery.addMouseListener(new MouseAdapter() {
         public void mouseClicked(MouseEvent e) {
            parent.runAllQuery();
         }
      });
      /**
      * Copyright (C) 2006 CINCOM SYSTEMS, INC.
      * All Rights Reserved
      * Copyright (C) 2006 Igor Mekterovic
      * All Rights Reserved
      */ 
    /* implementing localization */
      runQuery.setToolTipText(I18n.getString("toolTip.runAll")); 
        /* end of modification for I18n */

      runSelectedQuery = new JLabel(S.getAppIcon("runselectedquery.gif"));
      runSelectedQuery.addMouseListener(new MouseAdapter() {
         public void mouseClicked(MouseEvent e) {
            parent.runSelectedQuery();
         }
      });
      /**
      * Copyright (C) 2006 CINCOM SYSTEMS, INC.
      * All Rights Reserved
      * Copyright (C) 2006 Igor Mekterovic
      * All Rights Reserved
      */ 
    /* implementing localization */
      runSelectedQuery.setToolTipText(I18n.getString("toolTip.runSelected"));//sbalda
  /* end of modification for I18n */

      runSelectedQuery.setOpaque(false);

      insertCodeSkeleton = new JLabel(S.getAppIcon("insertCodeSkeleton.gif"));
      insertCodeSkeleton.addMouseListener(new MouseAdapter() {
         public void mouseClicked(MouseEvent e) {
            //parent.insertCodeSkeleton();
             insertMdxSkeleton();
         }
      });
      //insertCodeSkeleton.setToolTipText("Insert code skeleton");
    /**
      * Copyright (C) 2006 CINCOM SYSTEMS, INC.
      * All Rights Reserved
      * Copyright (C) 2006 Igor Mekterovic
      * All Rights Reserved
      */ 
    /* implementing localization */
      insertCodeSkeleton.setToolTipText(I18n.getString("toolTip.insertCode"));//sbalda
   /* end of modification for I18n */
      insertCodeSkeleton.setOpaque(false);




      // Get font names available in system
      GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
      String[] fontNames = ge.getAvailableFontFamilyNames();

       // Combo box for selecting font in tool bar
      cbFonts = new JComboBox( fontNames );
      cbFonts.setMaximumSize( cbFonts.getPreferredSize() );
      cbFonts.setEditable( false );
      cbFonts.setSelectedItem( "Courier New" );
      cbFonts.setRequestFocusEnabled( false );
      cbFonts.setBackground( Color.white );
      cbFonts.addActionListener( new cbFontsListener() );

      // Add font sizes to fontSizes
      String fontSizes[] = new String[13];
      for ( int i = 0; i < fontSizes.length; i++ )
      {
            fontSizes[i] = Integer.toString( i+7 );
      }

      // Initialize combo box 'cbSizes'
      cbSizes = new JComboBox( fontSizes );
      cbSizes.setMaximumSize(cbSizes.getPreferredSize());
      cbSizes.setEditable( false );
      cbSizes.setSelectedItem( Integer.toString( 13 ) );
      cbSizes.setRequestFocusEnabled( false );
      cbSizes.setBackground( Color.white );
      cbSizes.addActionListener( new cbSizesListener());


      //Modified by Prakash Cincom Systems. 12 Dec 06.
      //Added final.
      final String tabSizes[] = new String[3];
      for ( int i = 0; i < tabSizes.length; i++ )
      {
            tabSizes[i] = Integer.toString( i + 2 );
      }

      cbTabSize = new JComboBox( tabSizes );
      cbTabSize.setMaximumSize(cbTabSize.getPreferredSize());
      cbTabSize.setEditable( false );
      cbTabSize.setSelectedItem(Integer.toString(3));
      cbTabSize.setRequestFocusEnabled( false );
      cbTabSize.setBackground(Color.white);
      cbTabSize.addActionListener( new cbTabSizeListener());



      // Bold button in tool bar

      boldButton = new JToggleButton(S.getAppIcon("bold.gif"));
      boldButton.addItemListener( new BoldListener() );
      boldButton.setMargin( new Insets( 0, 0, 0, 0 ) );
      boldButton.setOpaque(false);


      // Italic button in tool bar

      italicButton = new JToggleButton(S.getAppIcon("italic.gif"));
      italicButton.addItemListener( new ItalicListener() );
      italicButton.setMargin( new Insets( 0, 0, 0, 0 ) );
      italicButton.setOpaque(false);


      appendGeneratedMDX = new JToggleButton(S.getAppIcon("appendGeneratedMDX.gif"));
      appendGeneratedMDX.setMargin( new Insets( 0, 0, 0, 0 ) );
      appendGeneratedMDX.setOpaque(false);



      cbResultPosition = new JComboBox( resultPositions.values().toArray() );
      cbResultPosition.setMaximumSize(cbResultPosition.getPreferredSize());
      cbResultPosition.setEditable( false );
      cbResultPosition.setRequestFocusEnabled( false );
      cbResultPosition.setBackground(Color.white);
      
      //Inserted by Prakash Cincom Systems. 12 Dec 06.
      // Dont want to create too many objects for changing results position.
      cbResultPositionListener resultPositionListener= new cbResultPositionListener();
      // Modified by Prakash Cincom Systems. 12 Dec 06.
      //cbResultPosition.addActionListener( new cbResultPositionListener());
      cbResultPosition.addActionListener( resultPositionListener);
      
      showRowTotalsOn = showColumnTotalsOn = false;
      showColumnTotals = new JLabel(showColumnTotalsIcon);
      /**
      * Copyright (C) 2006 CINCOM SYSTEMS, INC.
      * All Rights Reserved
      * Copyright (C) 2006 Igor Mekterovic
      * All Rights Reserved
      */ 
    /* implementing localization */
      showColumnTotals.setToolTipText(I18n.getString("toolTip.showColumnTotals"));//sbalda
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
            parent.setShowColumnTotalsOn(showColumnTotalsOn);
            sctMenuItem.setSelected(showColumnTotalsOn);
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
            parent.setShowRowTotalsOn(showRowTotalsOn);
            srtMenuItem.setSelected(showRowTotalsOn);
         }
      });
      showRowTotals.setBorder(AppColors.TOOLBAR_LABEL_BORDER);
      
    	/**
    	 *   Copyright (C) 2006 CINCOM SYSTEMS, INC.
    	 *   All Rights Reserved
    	 *   Copyright (C) 2006 Igor Mekterovic
    	 *   All Rights Reserved
    	 */
     
     /*
      * Lines Inserted by Prakash Cincom Systems. on 16 Nov. 2006
      * Introducing MenuBar.
      */

      
    this.setLayout(new GridLayout(2,1));
    JPanel toolbarPanel=new JPanel();    
  	
    toolbarPanel.setLayout(new BoxLayout(toolbarPanel, BoxLayout.X_AXIS));
    toolbarPanel.add(Box.createRigidArea(new Dimension(4, 0)));
    toolbarPanel.add(insertCodeSkeleton);
    toolbarPanel.add(Box.createRigidArea(new Dimension(4, 0)));
    toolbarPanel.add(runQuery);
    toolbarPanel.add(Box.createRigidArea(new Dimension(4, 0)));
    toolbarPanel.add(runSelectedQuery);
    toolbarPanel.add(Box.createRigidArea(new Dimension(4, 0)));
      /**
      * Copyright (C) 2006 CINCOM SYSTEMS, INC.
      * All Rights Reserved
      * Copyright (C) 2006 Igor Mekterovic
      * All Rights Reserved
      */ 
    /* implementing localization */
    //toolbarPanel.add( new JLabel("Font:") );
    toolbarPanel.add( fontC);
     /* end of modification for I18n */
    toolbarPanel.add(Box.createRigidArea(new Dimension(2, 0)));
    toolbarPanel.add( cbFonts );
    toolbarPanel.add(Box.createRigidArea(new Dimension(4, 0)));
      /**
      * Copyright (C) 2006 CINCOM SYSTEMS, INC.
      * All Rights Reserved
      * Copyright (C) 2006 Igor Mekterovic
      * All Rights Reserved
      */ 
    /* implementing localization */
    //toolbarPanel.add( new JLabel("Font size:") );
        toolbarPanel.add( fontSizeC);//sbalda
    toolbarPanel.add(Box.createRigidArea(new Dimension(2, 0)));
    toolbarPanel.add( cbSizes );
    toolbarPanel.add(Box.createRigidArea(new Dimension(4, 0)));
          /**
      * Copyright (C) 2006 CINCOM SYSTEMS, INC.
      * All Rights Reserved
      * Copyright (C) 2006 Igor Mekterovic
      * All Rights Reserved
      */ 
    /* implementing localization */
       // toolbarPanel.add( new JLabel("Tab size:") );
        toolbarPanel.add( tabSizeC); 
     /* end of modification for I18n */
    toolbarPanel.add(Box.createRigidArea(new Dimension(2, 0)));
    toolbarPanel.add( cbTabSize );
    toolbarPanel.add(Box.createRigidArea(new Dimension(4, 0)));
    toolbarPanel.add(boldButton);
    toolbarPanel.add(Box.createRigidArea(new Dimension(4, 0)));
    toolbarPanel.add(italicButton);
    toolbarPanel.add(Box.createRigidArea(new Dimension(4, 0)));
    toolbarPanel.add(appendGeneratedMDX);
    toolbarPanel.add(Box.createRigidArea(new Dimension(4, 0)));
    toolbarPanel.add(showColumnTotals);
    toolbarPanel.add(Box.createRigidArea(new Dimension(4, 0)));
    toolbarPanel.add(showRowTotals);
    toolbarPanel.add(Box.createRigidArea(new Dimension(4, 0)));
    toolbarPanel.add(cbResultPosition);
    toolbarPanel.add(Box.createRigidArea(new Dimension(4, 0)));
      /**
      * Copyright (C) 2006 CINCOM SYSTEMS, INC.
      * All Rights Reserved
      * Copyright (C) 2006 Igor Mekterovic
      * All Rights Reserved
      */ 
    /* implementing localization */
   // toolbarPanel.add( new JLabel("Result position:") );
        toolbarPanel.add( resultPosC);
         /* end of modification for I18n */
    toolbarPanel.add(cbResultPosition);   
   
    try {
        jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);        
        jfc.addChoosableFileFilter(new javax.swing.filechooser.FileFilter() {
            public boolean accept(File pathname) {
                return pathname.getName().toLowerCase().endsWith(".mdx")
                || pathname.isDirectory();
            }
            public String getDescription() {
                return "MDX (*.mdx)";
            }
            
        });
        jfc.addChoosableFileFilter(new javax.swing.filechooser.FileFilter() {
            public boolean accept(File pathname) {
                /**
                 * Replaced String literals with variables to avoid pmd violation named AvoidDuplicateLiterals.
                 * by Prakash. 09-05-2007. 
                 */     
                return pathname.getName().toLowerCase().endsWith(dotMBT)
                || pathname.isDirectory();
                /*
                 * End of modification.
                 */
            }
            public String getDescription() {
                return "MBT files (*.mbt)";
            }

        });        
        if (parent.getDefaultSaveDirectory() != null)
        {
            jfc.setCurrentDirectory(new File(parent.getDefaultSaveDirectory()));
        }
        jfc.setSelectedFile(new File(getSaveName()));        
    } catch (Exception ex) {
    	JOptionPane.showMessageDialog(null,ex.getMessage());
    }
    
    documentListener=new DocListener();
    parent.textArea.getDocument().addDocumentListener(documentListener);
    newMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
    newMenuItem.addActionListener( 	new ActionListener() { 
		public void actionPerformed(ActionEvent h) 
		{
		    newQuery();		
		}
    });
    openMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
	openMenuItem.addActionListener( 	new ActionListener() { 
		public void actionPerformed(ActionEvent h) 
		{ 	 
		    openQuery();		
		}
	});
    saveMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
    saveMenuItem.addActionListener( 	new ActionListener() {
		public void actionPerformed(ActionEvent h) 
		{
		    saveQuery();
		}
    });
	exitMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, ActionEvent.CTRL_MASK));
	exitMenuItem.addActionListener( 	new ActionListener() {
		public void actionPerformed(ActionEvent h) 
		{
			if(!status)
                    {     /**
                          * Copyright (C) 2006 CINCOM SYSTEMS, INC.
                          * All Rights Reserved
                          * Copyright (C) 2006 Igor Mekterovic
                          * All Rights Reserved
                          */ 
                        /* implementing localization */
			      			/**
			      			 * Replaced String literals with variables to avoid pmd violation named AvoidDuplicateLiterals.
			      			 * by Prakash. 09-05-2007. 
			      			 */
                            int confirm=JOptionPane.showConfirmDialog
                                    (null,I18n.getString(msgTextWantToSave));
                            /*
                             * End of modification.
                             */
                                 /* end of modification for I18n */
				if(confirm==JOptionPane.YES_OPTION)
				{
					if(getSaveName().trim().length()==0)
					{
						saveAs();
					}
					else
					{
						saveFile(new File(getSaveName()));						
					}
				}
				else if(confirm==JOptionPane.NO_OPTION || confirm==JOptionPane.CANCEL_OPTION || confirm==JOptionPane.CLOSED_OPTION)
				{
					return;
				}
				
			}
			Component comp =  parent;
            while (comp != null)
            {
                System.out.println(""+comp.getClass());
//              By Prakash Dispose if Parent class is RexWizard.
                if (comp.getClass() == RexWizard.class) { 
                    ((JDialog)comp).setVisible(false);
                    ((JDialog)comp).dispose();
                    return;
                }
                else
                {
                comp =  comp.getParent();
                continue;
                }
            }
			System.exit(0);
		}
    });
	cutMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));
	cutMenuItem.addActionListener( 	new ActionListener() { 
		public void actionPerformed(ActionEvent h) 
		{ 	         
	         parent.textArea.cut();
		}
	});
	copyMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK));
	copyMenuItem.addActionListener( 	new ActionListener() { 
		public void actionPerformed(ActionEvent h) 
		{ 	         
	         parent.textArea.copy();
		}
	});
	pasteMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, ActionEvent.CTRL_MASK));
	pasteMenuItem.addActionListener( 	new ActionListener() { 
		public void actionPerformed(ActionEvent h) 
		{ 	         
	         parent.textArea.paste();
		}
	});
	selectMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.CTRL_MASK));
	selectMenuItem.addActionListener( 	new ActionListener() { 
		public void actionPerformed(ActionEvent h) 
		{
	         parent.textArea.selectAll();
		}
	});

	deleteMenuItem.addActionListener( 	new ActionListener() { 
		public void actionPerformed(ActionEvent h) 
		{
	         parent.textArea.replaceSelection("");
		}
	});	
	
	saveAsMenuItem.addActionListener( 	new ActionListener() { 
		public void actionPerformed(ActionEvent h) 
		{
			saveAs();
		}
	});	
	
	fileMenu.add(newMenuItem);
	fileMenu.add(openMenuItem);
	fileMenu.addSeparator(); 
	fileMenu.add(saveMenuItem);
	fileMenu.add(saveAsMenuItem);
	fileMenu.addSeparator();

	fileMenu.add(localeMenu);

	fileMenu.addSeparator();
	
	fileMenu.add(rflMenu);

    lastUsed1MenuItem.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            parent.textArea.getDocument().removeDocumentListener(documentListener);            
			openFromRFL(0);
			parent.textArea.getDocument().addDocumentListener(documentListener);
        }
    });
    rflMenu.add(lastUsed1MenuItem);
    
    lastUsed2MenuItem.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            parent.textArea.getDocument().removeDocumentListener(documentListener);       
			openFromRFL(1);
			parent.textArea.getDocument().addDocumentListener(documentListener);
        }
    });
    rflMenu.add(lastUsed2MenuItem);
    
    lastUsed3MenuItem.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            parent.textArea.getDocument().removeDocumentListener(documentListener);       
			openFromRFL(2);
			parent.textArea.getDocument().addDocumentListener(documentListener);
        }
    });
    rflMenu.add(lastUsed3MenuItem);
    
    lastUsed4MenuItem.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            parent.textArea.getDocument().removeDocumentListener(documentListener);         
			openFromRFL(3);			
		    parent.textArea.getDocument().addDocumentListener(documentListener);
        }
    });
    rflMenu.add(lastUsed4MenuItem);

	
	fileMenu.addSeparator(); 
	fileMenu.add(exitMenuItem);
	
	editMenu.add(cutMenuItem); 
	editMenu.add(copyMenuItem);
	editMenu.add(deleteMenuItem);
	editMenu.add(pasteMenuItem); 
	editMenu.addSeparator(); 
	editMenu.add(selectMenuItem);
	
	viewMenu.add(fontMenu);
	fontMenu.add(fontNameMenu);
	for(int fontCount=0;fontCount < fontNames.length;fontCount++)
	{
	    /**
	     * Breaking PMD violation rule named AvoidInstantiatingObjectsInLoops to avoid repetitive code writing for creating radio button for font's name.
	     * by Prakash. 10-05-2007. 
	     */
		final JRadioButtonMenuItem menuItem=new JRadioButtonMenuItem(fontNames[fontCount]);
		fontGroup.add(menuItem);
		menuItem.addActionListener( 	new ActionListener() { 
			public void actionPerformed(ActionEvent h) 
			{
		         parent.setFontName(menuItem.getText());
		         cbFonts.setSelectedItem(menuItem.getText());
			}
		});
		fontNameMenu.add(menuItem);
	}	
	fontMenu.add(fontSizeMenu);
	for(int fontSizeCount=0;fontSizeCount < fontSizes.length;fontSizeCount++)
	{
	    /**
	     * Breaking PMD violation rule named AvoidInstantiatingObjectsInLoops to avoid repetitive code writing for creating radio button for font's size.
	     * by Prakash. 10-05-2007. 
	     */
		final JRadioButtonMenuItem menuItem=new JRadioButtonMenuItem(fontSizes[fontSizeCount]);
		fontSizeGroup.add(menuItem);
		menuItem.addActionListener( 	new ActionListener() { 
			public void actionPerformed(ActionEvent h) 
			{
				try{
					parent.setFontSize(Integer.parseInt(menuItem.getText()));
					cbSizes.setSelectedItem(menuItem.getText());
				} catch (NumberFormatException ex) {
	            return;
				}
			}
		});
		fontSizeMenu.add(menuItem);
	}	
	
	fontMenu.add(boldMenuItem);
	boldMenuItem.addItemListener(new BoldListener());
	
	fontMenu.add(italicMenuItem);
	italicMenuItem.addItemListener(new ItalicListener());
	
	viewMenu.add(tsMenu);
	
	tabGroup.add(tabMenuItem2);
	tabGroup.add(tabMenuItem3);
	tabGroup.add(tabMenuItem4);
	
	tabMenuItem2.addActionListener( 	new ActionListener() { 
		public void actionPerformed(ActionEvent h) 
		{ 	         
	         try {
	            cbTabSize.setSelectedItem(tabSizes[0]);
	         } catch (NumberFormatException ex) {
	            return;
	         }
	         parent.setTabSize(Integer.valueOf(tabSizes[0]).intValue());
		}
	});
	tabMenuItem3.addActionListener( 	new ActionListener() { 
		public void actionPerformed(ActionEvent h) 
		{ 
	         try {
	            cbTabSize.setSelectedItem(tabSizes[1]);
	         } catch (NumberFormatException ex) {
	            return;
	         }
	         parent.setTabSize(Integer.valueOf(tabSizes[1]).intValue());
		}
	});
	tabMenuItem4.addActionListener( 	new ActionListener() { 
		public void actionPerformed(ActionEvent h) 
		{ 
	         try {
	            cbTabSize.setSelectedItem(tabSizes[2]);
	         } catch (NumberFormatException ex) {
	            return;
	         }
	         parent.setTabSize(Integer.valueOf(tabSizes[2]).intValue());
		}
	});
	
	tsMenu.add(tabMenuItem2);
	tsMenu.add(tabMenuItem3);
	tsMenu.add(tabMenuItem4);
	
         /**
      * Copyright (C) 2006 CINCOM SYSTEMS, INC.
      * All Rights Reserved
      * Copyright (C) 2006 Igor Mekterovic
      * All Rights Reserved
      */ 
    /* implementing localization */
    foregroundMenu = new ColorMenu(I18n.getString("menu.foregroundColor"));
     /* end of modification for I18n */
    foregroundMenu.addActionListener(new ForegroundListener());
	viewMenu.add(foregroundMenu);
         /**
      * Copyright (C) 2006 CINCOM SYSTEMS, INC.
      * All Rights Reserved
      * Copyright (C) 2006 Igor Mekterovic
      * All Rights Reserved
      */ 
    /* implementing localization */
    backgroundMenu = new ColorMenu(I18n.getString("menu.backgroundColor"));
     /* end of modification for I18n */
    backgroundMenu.addActionListener(new BackgroundListener());
    viewMenu.add(backgroundMenu);
    viewMenu.add(layoutMenu);
	
    vsMenuItem.addActionListener( 	new ActionListener() { 
		public void actionPerformed(ActionEvent h) 
		{ 
			parent.setSplitPaneView(JSplitPane.VERTICAL_SPLIT);			
			cbResultPosition.setSelectedItem(resultPositions.get(VERTICAL_SPLIT_PANE));
		}
	});
	hsMenuItem.addActionListener( 	new ActionListener() { 
		public void actionPerformed(ActionEvent h) 
		{ 	
			parent.setSplitPaneView(JSplitPane.HORIZONTAL_SPLIT);			
			cbResultPosition.setSelectedItem(resultPositions.get(HORIZONTAL_SPLIT_PANE));
		}
	});
	tuMenuItem.addActionListener( 	new ActionListener() { 
		public void actionPerformed(ActionEvent h) 
		{ 	
			parent.setTabbedPaneView(JTabbedPane.TOP);			
			cbResultPosition.setSelectedItem(resultPositions.get(TABBED_PANE_UP));
		}
	});
	tlMenuItem.addActionListener( 	new ActionListener() { 
		public void actionPerformed(ActionEvent h) 
		{ 
			parent.setTabbedPaneView(JTabbedPane.LEFT);
			cbResultPosition.setSelectedItem(resultPositions.get(TABBED_PANE_LEFT));
		}
	});
	trMenuItem.addActionListener( 	new ActionListener() { 
		public void actionPerformed(ActionEvent h) 
		{ 
			parent.setTabbedPaneView(JTabbedPane.RIGHT);			
			cbResultPosition.setSelectedItem(resultPositions.get(TABBED_PANE_RIGHT));
		}
	});
	tbMenuItem.addActionListener( 	new ActionListener() { 
		public void actionPerformed(ActionEvent h) 
		{ 
			parent.setTabbedPaneView(JTabbedPane.BOTTOM);			
			cbResultPosition.setSelectedItem(resultPositions.get(TABBED_PANE_BOTTOM));
		}
	});
  
	layoutGroup.add(vsMenuItem);
	layoutGroup.add(hsMenuItem);
	layoutGroup.add(tuMenuItem);
	layoutGroup.add(tlMenuItem);
	layoutGroup.add(trMenuItem);
	layoutGroup.add(tbMenuItem);
	
	layoutMenu.add(vsMenuItem);
	layoutMenu.add(hsMenuItem);
	layoutMenu.add(tuMenuItem);
	layoutMenu.add(tlMenuItem);
	layoutMenu.add(trMenuItem);
	layoutMenu.add(tbMenuItem);    
    
    viewMenu.addSeparator();
    
    srtMenuItem.addActionListener( 	new ActionListener() { 
		public void actionPerformed(ActionEvent h) 
		{ 
            showRowTotalsOn = !showRowTotalsOn;
            if (showRowTotalsOn){
               showRowTotals.setIcon(hideRowTotalsIcon);
            }else{
               showRowTotals.setIcon(showRowTotalsIcon);
            }
            parent.setShowRowTotalsOn(showRowTotalsOn);
		}
	});
    
    sctMenuItem.addActionListener( 	new ActionListener() { 
		public void actionPerformed(ActionEvent h) 
		{ 
            showColumnTotalsOn = !showColumnTotalsOn;
            if (showColumnTotalsOn) {
               showColumnTotals.setIcon(hideColumnTotalsIcon);
            }
            else {
               showColumnTotals.setIcon(showColumnTotalsIcon);
            }
            parent.setShowColumnTotalsOn(showColumnTotalsOn);
		}
	});
    
	viewMenu.add(srtMenuItem);	
	viewMenu.add(sctMenuItem);	
			
	menubar.add(fileMenu);
	menubar.add(editMenu);
	menubar.add(viewMenu);
    
	this.add(menubar);
	this.add(toolbarPanel);
    this.setBorder(AppColors.TOOLBAR_BORDER);
    this.setOpaque(false); 
    // end of the addition.
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
   
   /**
    * By Prakash.
    * Enable or Disable Locale.
    */
   
   public void setLocaleEnabled(boolean b)
   {
   		localeMenu.setEnabled(b);
   }
   
   /**
    * By Prakash.
    * Getter & Setter for Locale
    */
   
   public Locale getCurrentLocale()
   {
   		return localeMenu.getCurrentLocale();
   }
   
   public void setCurrentLocale(Locale locale)
   {   		
   		localeMenu.setCurrentLocale(locale);
   }
   
 /**
  * By Prakash.
  * Helper method that is executed when the language is changed
  */
 
    public void languageChanged(LanguageChangedEvent evt) {
        this.applyI18n();
		setCurrentLocale(evt.getLocale());
		
    }
    
     
 /**
  *  Helper method to implement locatization when language is changed
  */
     
    public void applyI18n(){
    fileMenu.setText(I18n.getString("menu.file"));
    editMenu.setText(I18n.getString("menu.edit"));
    viewMenu.setText(I18n.getString("menu.view"));
    newMenuItem.setText(I18n.getString("menu.new"));
    openMenuItem.setText(I18n.getString("menu.open"));
    saveMenuItem.setText(I18n.getString("menu.save"));
    saveAsMenuItem.setText(I18n.getString("menu.saveAs"));
    rflMenu.setText(I18n.getString("menu.recentFileList"));	
    exitMenuItem.setText(I18n.getString("menu.exit"));
    cutMenuItem.setText(I18n.getString("menu.cut"));
    copyMenuItem.setText(I18n.getString("menu.copy"));
    deleteMenuItem.setText(I18n.getString("menu.delete"));
    pasteMenuItem.setText(I18n.getString("menu.paste"));
    selectMenuItem.setText(I18n.getString("menu.select"));
    fontMenu.setText(I18n.getString("menu.font"));
    fontNameMenu.setText(I18n.getString("menu.fontName"));
    fontSizeMenu.setText(I18n.getString("menu.fontSize"));
    boldMenuItem.setText(I18n.getString("menu.bold"));
    italicMenuItem.setText(I18n.getString("menu.italic"));
    tsMenu.setText(I18n.getString("menu.tabSize"));
    layoutMenu.setText(I18n.getString("menu.layout"));
    srtMenuItem.setText(I18n.getString("menu.showRows"));
    sctMenuItem.setText(I18n.getString("menu.showCols"));
    
    runQuery.setToolTipText(I18n.getString("toolTip.runAll"));
    runSelectedQuery.setToolTipText(I18n.getString("toolTip.runSelected"));
    showColumnTotals.setToolTipText(I18n.getString("toolTip.showColumnTotals"));
    showRowTotals.setToolTipText(I18n.getString("toolTip.showRowTotals"));
    insertCodeSkeleton.setToolTipText(I18n.getString("toolTip.insertCode"));
      
    fontC.setText(I18n.getString("label.fontC"));
    fontSizeC.setText(I18n.getString("label.fontSizeC"));
    tabSizeC.setText(I18n.getString("label.tabSizeC"));
    resultPosC.setText(I18n.getString("label.resultPosC"));
    if(foregroundMenu instanceof ColorMenu){
        foregroundMenu.setText(I18n.getString("menu.foregroundColor"));
        backgroundMenu.setText(I18n.getString("menu.backgroundColor"));
    }
    
    /**
     * Replaced String literals with variables to avoid pmd violation named AvoidDuplicateLiterals.
     * by Prakash. 09-05-2007. 
     */

     MdxEditorToolbar.resultPositions.put(MdxEditorToolbar.VERTICAL_SPLIT_PANE,I18n.getString(panelVertSplit) );
          MdxEditorToolbar.resultPositions.put(MdxEditorToolbar.HORIZONTAL_SPLIT_PANE,I18n.getString(panelHorzSplit) );
          MdxEditorToolbar.resultPositions.put(MdxEditorToolbar.TABBED_PANE_UP,I18n.getString(panelTabUp) );
          MdxEditorToolbar.resultPositions.put(MdxEditorToolbar.TABBED_PANE_LEFT,I18n.getString(panelTabLeft) );
          MdxEditorToolbar.resultPositions.put(MdxEditorToolbar.TABBED_PANE_RIGHT,I18n.getString(panelTabRight) );
          MdxEditorToolbar.resultPositions.put(MdxEditorToolbar.TABBED_PANE_BOTTOM,I18n.getString(panelTabBottom) );
          if(cbResultPosition instanceof JComboBox){
              cbResultPosition.removeAllItems();
              cbResultPosition.addItem(I18n.getString(panelVertSplit));
              cbResultPosition.addItem(I18n.getString(panelHorzSplit));
              cbResultPosition.addItem(I18n.getString(panelTabUp));
              cbResultPosition.addItem(I18n.getString(panelTabLeft));
              cbResultPosition.addItem(I18n.getString(panelTabRight));
              cbResultPosition.addItem(I18n.getString(panelTabBottom));
              
         }
        
         vsMenuItem.setText(I18n.getString(panelVertSplit)); 
         hsMenuItem.setText(I18n.getString(panelHorzSplit)); 
         tuMenuItem.setText(I18n.getString(panelTabUp)); 
         tlMenuItem.setText(I18n.getString(panelTabLeft)); 
         trMenuItem.setText(I18n.getString(panelTabRight)); 
         tbMenuItem.setText(I18n.getString(panelTabBottom)); 
          
    }
    /*
     *  End of modification. 
     */
  /* end of modification for I18n */

    

   public void setRecentURL(String recentURL[])
   {
   	this.recentURL=recentURL;
   	updateLastUsedMenu();
   }

   public String[] getRecentURL()
   {
    /**
     * Inserted temporary array to avoid pmd violation of array exposed.
     * by Prakash. 10-05-2007. 
     */
    String [] tempRecentURL=recentURL;
   	return tempRecentURL;
   	/*
   	 * End of modification. 
   	 */
   }
   
   public void setSave(boolean bool)
   {
   	status=bool;   	
   }

   public boolean getSave()
   {
   	return status;   	
   }
   
   private void setLastUsed(String name)
   {

    int match = 4;
    String luName = null;    
    
    for( int i=1; i<=4; i++) {      
        luName = recentURL[i-1];        
        if ( luName != null && luName.equals(name)) {
            match=i;
            break;
        }
    }
    
    for(int i=(match-1); i>0; i--) {
        if (recentURL[i-1] != null) {
        	recentURL[i]=recentURL[i-1];            
        }
    }

    recentURL[0]=name;
    updateLastUsedMenu();
   }   
   
   private void updateLastUsedMenu() {
    
    if (recentURL[0].trim().length()>0) {
        lastUsed1MenuItem.setVisible(true);} else {
        lastUsed1MenuItem.setVisible(false); }
    if (recentURL[1].trim().length()>0) {
        lastUsed2MenuItem.setVisible(true); } else {
        lastUsed2MenuItem.setVisible(false); }
    if (recentURL[2].trim().length()>0) {
        lastUsed3MenuItem.setVisible(true); } else {
        lastUsed3MenuItem.setVisible(false); }
    if (recentURL[3].trim().length()>0) {
        lastUsed4MenuItem.setVisible(true); } else {
        lastUsed4MenuItem.setVisible(false); }
    
    lastUsed1MenuItem.setText(recentURL[0]);
    lastUsed2MenuItem.setText(recentURL[1]);
    lastUsed3MenuItem.setText(recentURL[2]);
    lastUsed4MenuItem.setText(recentURL[3]);
    parent.setRecentURL(recentURL);
}
   
   private void clearState(){
       for (int i=0; i<root.getChildCount(); i++){
          ((DefaultMBTNode)((DefaultMutableTreeNode)root.getChildAt(i)).getUserObject()).removeAllChildrenFromTheTree(  (DefaultMutableTreeNode)root.getChildAt(i)
                                                                                                                      , (DefaultTreeModel)tree.getModel());
          ((DefaultTreeModel)tree.getModel()).nodeChanged(root.getChildAt(i));
       }
       tree.repaint();
       parent.mdxChanged(((MBTNode) (root).getUserObject()).getMdx(""));
       
    }
   
   	protected void saveQueryStream(File file){
      try {
         ObjectOutputStream s = new ObjectOutputStream(new FileOutputStream(file.getAbsolutePath()));
         MBTNode r = (MBTNode) root.getUserObject();
         MBTNode[] children = r.getMdxBuilderTreeNodes();
         for (int i = 0; children != null && i < children.length; i++) {
            saveNode(s, children[i]);
         }
         //     saving the state of the tree (expanded rows) - that's how nice I am
         Enumeration enumt = tree.getExpandedDescendants(new TreePath(root.getPath()));
//         Enumeration is NOT serilizable but TreePath IS
         TreePath n;
         while(enumt!=null && enumt.hasMoreElements()){
            n = (TreePath)enumt.nextElement();
            s.writeObject(n);
         }

         s.flush();
      }
      catch (Exception e) {
         //e.printStackTrace();//Commented by Prakash
          /**
          * Copyright (C) 2006 CINCOM SYSTEMS, INC.
          * All Rights Reserved
          * Copyright (C) 2006 Igor Mekterovic
          * All Rights Reserved
          */ 
        /* implementing localization */
          JOptionPane.showMessageDialog(null
                                        , I18n.getString("msgText.errSave")
                                        , I18n.getString("msgTitle.saveQuery")
                                        , JOptionPane.ERROR_MESSAGE);
           /* end of modification for I18n */

          return;

      }

   }

   private void saveNode( ObjectOutputStream s, MBTNode parentNode){
     try {
//       S.out("SERIALIZING " + parentNode);
       s.writeObject(parentNode);
     }catch(Exception e){
       //e.printStackTrace();//Commented by Prakash
         /* implementing localization */
         JOptionPane.showMessageDialog(null
                                       , I18n.getString("msgText.errSave")
                                       , I18n.getString("msgTitle.saveQuery")
                                       , JOptionPane.ERROR_MESSAGE);
          /* end of modification for I18n */
     }
     MBTNode[] children = parentNode.getMdxBuilderTreeNodes();
     for (int i=0; children != null && i<children.length; i++){
       saveNode(s, children[i]);
     }

   }



   protected void loadQueryStream(File file){      
      ArrayList expandedPaths = new ArrayList();
      FileInputStream memStream=null;
      try {
         memStream=new FileInputStream(file.getAbsolutePath());
         ObjectInputStream s = new ObjectInputStream(memStream);
         root.removeAllChildren();
         ( (MBTNode) (root.getUserObject())).removeAllChildren();
         ( (DefaultTreeModel) tree.getModel()).nodeChanged(root);
//         S.out("1. root.getChildCount() = " + root.getChildCount());
         Object o;
         while (true) {
            o = s.readObject();
//            S.out("deserializing " + o.toString());
            if (o instanceof TreePath) {
//               S.out("found TreePath " + o);
               expandedPaths.add(o);
            }
            else if (o instanceof DefaultMBTAxisNode) {
//               S.out("ADDING " + o);
               ( (MBTNode) (root).getUserObject()).addChild( (MBTNode) o);
        	    /**
        	     * Breaking PMD violation rule named AvoidInstantiatingObjectsInLoops to avoid repetitive code writing for loading MBT tree state.
        	     * by Prakash. 10-05-2007. 
        	     */
               root.add(new DefaultMutableTreeNode(o));
            }
         }
      }
      catch (java.io.EOFException e) {
//        stupid ObjectInputStream has no method to tell me when it it EOF
      }
      catch (java.io.StreamCorruptedException sce)
      {
          /**
           * Copyright (C) 2006 CINCOM SYSTEMS, INC.
           * All Rights Reserved
           * Copyright (C) 2006 Igor Mekterovic
           * All Rights Reserved
           */ 
         /* implementing localization */
          JOptionPane.showMessageDialog(null
                  , I18n.getString("msgText.errLoadingQuery")
                  ,  I18n.getString("msgTitle.loadingQuery") 
                  , JOptionPane.ERROR_MESSAGE);
      }
      catch (Exception e) {
         //e.printStackTrace();//Commented by Prakash
         /**
          * Copyright (C) 2006 CINCOM SYSTEMS, INC.
          * All Rights Reserved
          * Copyright (C) 2006 Igor Mekterovic
          * All Rights Reserved
          */ 
        /* implementing localization */
       JOptionPane.showMessageDialog(null
                                        , I18n.getString("msgText.errLoadingQuery")
                                        ,  I18n.getString("msgTitle.loadingQuery") 
                                        , JOptionPane.ERROR_MESSAGE);
           /* end of modification for I18n */

          return;

      }

      ( (DefaultTreeModel) tree.getModel()).nodeChanged(root);

      for (int i = 0; i < root.getChildCount(); i++) {
         loadNode( (DefaultMutableTreeNode) root.getChildAt(i));
      }
      ((DefaultTreeModel) tree.getModel()).reload();
      Enumeration enumt = root.breadthFirstEnumeration();
      TreePath tp1, tp2;
      while (enumt!=null && enumt.hasMoreElements()){
         DefaultMutableTreeNode node = (DefaultMutableTreeNode)enumt.nextElement();
 	    /**
 	     * Breaking PMD violation rule named AvoidInstantiatingObjectsInLoops to avoid repetitive code writing for generating MBT tree structure.
 	     * by Prakash. 10-05-2007. 
 	     */
         tp1 = new TreePath(node.getPath());
         for(int i=0; i < expandedPaths.size(); i++){

            tp2 = (TreePath)expandedPaths.get(i);
            if (tp1.toString().equals(tp2.toString())){
               tree.expandPath(tp1);
            }
         }
      }

      tree.repaint();
      parent.textArea.setText(((MBTNode) (root).getUserObject()).getMdx(""));
   }

   private void loadNode(DefaultMutableTreeNode parentNode){
      MBTNode[] children = ((MBTNode)parentNode.getUserObject()).getMdxBuilderTreeNodes();
      for (int i=0; children!=null && i<children.length; i++){
   	    /**
   	     * Breaking PMD violation rule named AvoidInstantiatingObjectsInLoops to avoid repetitive code writing for loading MBT tree state.
   	     * by Prakash. 10-05-2007. 
   	     */
         DefaultMutableTreeNode newNode = new DefaultMutableTreeNode(children[i]);
         parentNode.add(newNode);
         loadNode(newNode);
      }
   }
    
   public void insertMdxSkeleton()
   {
		parent.textArea.getDocument().removeDocumentListener(documentListener);		
		if(!status)
		{
            //msgText.wantToSave
		    /**
             * Copyright (C) 2006 CINCOM SYSTEMS, INC.
             * All Rights Reserved
             * Copyright (C) 2006 Igor Mekterovic
             * All Rights Reserved
             */
		    
            /* implementing localization */
		    
		      /**
		       * Replaced String literals with variables to avoid pmd violation named AvoidDuplicateLiterals.
		       * by Prakash. 09-05-2007. 
		       */
            int confirm=JOptionPane.showConfirmDialog(null,I18n.getString(msgTextWantToSave));
            /*
             * End of modification.
             */
            /* end of modification for I18n */
			if(confirm==JOptionPane.YES_OPTION)
			{
				if(getSaveName().trim().length()==0)
				{
					saveAs();				
				}
				else
				{
					saveFile(new File(getSaveName()));
				}
				parent.textArea.setText("");
				setSaveName("");					
			}
			else if(confirm==JOptionPane.NO_OPTION )
			{		
				parent.textArea.setText("");
				setSaveName("");
				status=true;
			}
			else if(confirm==JOptionPane.CANCEL_OPTION || confirm==JOptionPane.CLOSED_OPTION)
			{
				return;
			}
		}
		else
		{
			parent.textArea.setText("");
			setSaveName("");
		}
		clearState();// Clear MBT.		
		parent.insertCodeSkeleton();
		parent.textArea.getDocument().addDocumentListener(documentListener);
   }
   
   public void newQuery()
   {
		parent.textArea.getDocument().removeDocumentListener(documentListener);		
		if(!status)
		{
            //msgText.wantToSave
		    /**
             * Copyright (C) 2006 CINCOM SYSTEMS, INC.
             * All Rights Reserved
             * Copyright (C) 2006 Igor Mekterovic
             * All Rights Reserved
             */ 
            /* implementing localization */
		      /**
		       * Replaced String literals with variables to avoid pmd violation named AvoidDuplicateLiterals.
		       * by Prakash. 09-05-2007. 
		       */
            int confirm=JOptionPane.showConfirmDialog(null,I18n.getString(msgTextWantToSave));
            /*
             * End of Modification.
             */
            /* end of modification for I18n */
			if(confirm==JOptionPane.YES_OPTION)
			{
				if(getSaveName().trim().length()==0)
				{
					saveAs();				
				}
				else
				{
					saveFile(new File(getSaveName()));
				}
				parent.textArea.setText("");
				setSaveName("");					
			}
			else if(confirm==JOptionPane.NO_OPTION )
			{		
				parent.textArea.setText("");
				setSaveName("");
				status=true;
			}
			else if(confirm==JOptionPane.CANCEL_OPTION || confirm==JOptionPane.CLOSED_OPTION)
			{
				return;
			}
		}
		else
		{
			parent.textArea.setText("");
			setSaveName("");
		}
		clearState();// Clear MBT.			
		parent.textArea.getDocument().addDocumentListener(documentListener);
   }
   
   public void openQuery()
   {
		parent.textArea.getDocument().removeDocumentListener(documentListener);
		if(!status)			
		{
		    /**
		      * Copyright (C) 2006 CINCOM SYSTEMS, INC.
		      * All Rights Reserved
		      * Copyright (C) 2006 Igor Mekterovic
		      * All Rights Reserved
		      */ 
            /* implementing localization */
		      /**
		       * Replaced String literals with variables to avoid pmd violation named AvoidDuplicateLiterals.
		       * by Prakash. 09-05-2007. 
		       */
            int confirm=JOptionPane.showConfirmDialog(null,I18n.getString(msgTextWantToSave));
            /*
             * End of modification.
             */
			     /* end of modification for I18n */
            if(confirm==JOptionPane.YES_OPTION)
			{
				if(getSaveName().trim().length()==0)
				{
					saveAs();
				}
				else
				{
					saveFile(new File(getSaveName()));
				}
			}
			else if(confirm==JOptionPane.CANCEL_OPTION || confirm==JOptionPane.CLOSED_OPTION)
			{
				return;
			}
			
		}
        if (jfc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            try {
            	openFile(new File(jfc.getSelectedFile().getAbsolutePath()), false);
            	setLastUsed(jfc.getSelectedFile().getAbsolutePath());	            	
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null,e.getMessage());
            }
	}
	parent.textArea.getDocument().addDocumentListener(documentListener);
   }
   
   public void saveQuery()
   {
		if(getSaveName().trim().length()==0)
		{
			saveAs();
		}
		else
		{
			saveFile(new File(getSaveName()));
		}
   }
   
   private void openFile(File file, boolean newFile) {
    if (! newFile) {
        // check if file doesn't exists
        if (! file.exists()) {
           /**
          * Copyright (C) 2006 CINCOM SYSTEMS, INC.
          * All Rights Reserved
          * Copyright (C) 2006 Igor Mekterovic
          * All Rights Reserved
          */ 
        /* implementing localization */
            JOptionPane.showMessageDialog(this, file.getAbsolutePath() + 
                    I18n.getString("msgText.fileNotFound"), 
                    I18n.getString("msgTitle.alert"), 
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        // check if file is not writable
        if (! file.canWrite()) {
           /**
          * Copyright (C) 2006 CINCOM SYSTEMS, INC.
          * All Rights Reserved
          * Copyright (C) 2006 Igor Mekterovic
          * All Rights Reserved
          */ 
        /* implementing localization */
            JOptionPane.showMessageDialog(this, file.getAbsolutePath() + 
                    I18n.getString("msgText.fileLocked") , 
                    I18n.getString("msgTitle.alert"), 
                    JOptionPane.WARNING_MESSAGE);
              /* end of modification for I18n */

            return;
        }
        //new FileInputStream(chooser.getSelectedFile().getAbsolutePath());
        /**
         * Replaced String literals with variables to avoid pmd violation named AvoidDuplicateLiterals.
         * by Prakash. 09-05-2007. 
         */
        if(file.getAbsolutePath().endsWith(dotMBT))
        {
            loadQueryStream(file);
            setSaveName(file.getAbsolutePath());
            status=true;
        }
        else
        {
            try {

                BufferedReader in = new BufferedReader(new FileReader(file));
                String str="";
                String fullStr="";
                while ((str = in.readLine()) != null) {
                	fullStr=fullStr.concat(str)+"\n";
                }
                setSaveName(file.getAbsolutePath());
                in.close();
                clearState();
                parent.textArea.setText(fullStr);                
            	status=true;
            } catch (Exception ex) {
                System.out.println("Exception  : Mdx file is invalid."+ex.getMessage());
                //ex.printStackTrace(); //====
            } catch (Error err) {
                System.out.println("Error : Mdx file is invalid."+err.getMessage());
                //err.printStackTrace(); //====
            }
            return;            
        }
    }
   }
   
   private void saveFile(File file) {

        try 
        {
            /**
             * Replaced String literals with variables to avoid pmd violation named AvoidDuplicateLiterals.
             * by Prakash. 09-05-2007. 
             */
            if(file.getName().endsWith(dotMBT))
            {
                saveQueryStream(file);
                setSaveName(file.getAbsolutePath());
                setLastUsed(file.getAbsolutePath());
            }
            else
            {
                if(file.getName().indexOf(".")==-1)
                {
                    file=new File(file.getAbsolutePath().concat(".mdx"));
                }        	
                BufferedWriter out = new BufferedWriter(new FileWriter(file));            
                out.write(parent.textArea.getText());
                setSaveName(file.getAbsolutePath());
                setLastUsed(file.getAbsolutePath());
                out.close();
                status=true;
            }
            parent.setDefaultSaveDirectory(jfc.getSelectedFile().getParentFile().getAbsolutePath());
        }
        catch (Exception ex) 
        {
            System.out.println("Exception  : Mdx file is invalid."+ex.getMessage());
        }
        catch (Error err) 
        {
            System.out.println("Error : Mdx file is invalid."+err.getMessage());
        }
    }
   
   private void saveAs()
   {
    try 
	{     	
			int returnVal = jfc.showSaveDialog(null);
    		if (returnVal == JFileChooser.APPROVE_OPTION) 
    		{	
    		    String pathName=jfc.getSelectedFile().getAbsolutePath();
    		    String filterDescription=jfc.getFileFilter().getDescription();
    		      /**
    		       * Replaced String literals with variables to avoid pmd violation named AvoidDuplicateLiterals.
    		       * by Prakash. 09-05-2007. 
    		       */
    		    if(pathName.endsWith(".mdx") || pathName.endsWith(dotMBT) || filterDescription.startsWith("MDX"))
    		    {
    		        File tempFile=new File(pathName);
                    if(tempFile.exists())
                    {
                        int confirm=JOptionPane.showConfirmDialog(parent,I18n.getString("msgText.fileExists"));                        
                        if(confirm==JOptionPane.NO_OPTION)
                        {
                            return;
                        }
                    }
    		        saveFile(new File(pathName));
    		    }
    		    else if(filterDescription.startsWith("MBT"))
    		    { 
    		        /**
    		         * Replaced String literals with variables to avoid pmd violation named AvoidDuplicateLiterals.
    		         * by Prakash. 09-05-2007. 
    		         */
    		        if(!pathName.endsWith(dotMBT))
    		        {
        		        File tempFile=new File(pathName.concat(dotMBT));
        		     /*
        		      * End of modification.
        		      */
                        if(tempFile.exists())
                        {
                            int confirm=JOptionPane.showConfirmDialog(parent,I18n.getString("msgText.fileExists"));
                            if(confirm==JOptionPane.NO_OPTION)
                            {
                                return;
                            }
                        }
                        saveFile(tempFile);
    		        }
    		    }
    		}
    }
    catch(Exception exception)
	{
    	S.reportError(exception);
	}
   }
   private void setSaveName(String mdxName)
   {
   	parent.setFileName(mdxName);
   }
   
   public String getSaveName()
   {
   	String temp=parent.getFileName();   
   	/**
   	 * Modified to avoid pmd rule named InefficientEmptyStringCheck.
   	 * by Prakash. 10-05-07.
   	 */
   	if(temp.length()==0)
   	/*
   	 * end of modification.
   	 */
   	{
   		return "";
   	}
   	return temp;
   }

	public void openFromRFL(int index)
	{
            try {
            	if(!status)			
    			{
    		/**
                  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
                  * All Rights Reserved
                  * Copyright (C) 2006 Igor Mekterovic
                  * All Rights Reserved
                  */
            	      /**
            	       * Replaced String literals with variables to avoid pmd violation named AvoidDuplicateLiterals.
            	       * by Prakash. 09-05-2007. 
            	       */
                    int confirm=JOptionPane.showConfirmDialog(null,I18n.getString(msgTextWantToSave));
                    /*
                     * End of modification. 
                     */
                      /* end of modification for I18n */

    				if(confirm==JOptionPane.YES_OPTION)
    				{
    					if(getSaveName().trim().length()==0)
    					{
    						saveAs();
    					}
    					else
    					{
    						saveFile(new File(getSaveName()));
    					}
    				}
    				else if(confirm==JOptionPane.CANCEL_OPTION || confirm==JOptionPane.CLOSED_OPTION)
    				{
    					return;
    				}
    			}
                openFile(new File(recentURL[index]), false);
                setLastUsed(recentURL[index]);
            } catch (Exception e) {
                e.printStackTrace();
            }
	}

   /**
    * Returns true if show row totals is on, false otherwise.
    * @return boolean
    */
   protected boolean getShowRowTotalsOn(){
      return showRowTotalsOn;
   }
   /**
    * Returns true if show column totals is on, false otherwise.
    * @return boolean
    */
   protected boolean getShowColumnTotalsOn(){
      return showColumnTotalsOn;
   }

   /**
    * Returns true if append MDX button is on, false otherwise.
    * @return boolean
    */
   protected boolean getAppendGeneratedMDX(){
      return appendGeneratedMDX.isSelected();
   }

   /**
    * Overrides the <code>paintComponent(Graphics g)<code> to paint gradient blue background.
    * @param g Graphics
    */
   public void paintComponent(Graphics g) {
      S.paintBackgroundHorizontal(g, this);
      super.paintComponent(g);
   }

   /**
    * Sets the font combobox to the specified font name.
    * @param fontName String
    */
   public void setcbFont(String fontName){
      cbFonts.setSelectedItem(fontName);
      //By Prakash.
      Enumeration fontElements=fontGroup.getElements();
      while(fontElements.hasMoreElements())
      {
      	JRadioButtonMenuItem tempItem=(JRadioButtonMenuItem)fontElements.nextElement();
      	if(fontName.equalsIgnoreCase(tempItem.getText()))
      	{
      		tempItem.setSelected(true);
      	}
      }
   }

   /**
    * Sets the font size combobox to the specified size.
    * @param size int
    */
   public void setcbSize(int size){
       cbSizes.setSelectedItem(Integer.toString(size));
//     By Prakash.
     try{
       Enumeration fontElements=fontSizeGroup.getElements();
       while(fontElements.hasMoreElements())
       {
       	JRadioButtonMenuItem tempItem=(JRadioButtonMenuItem)fontElements.nextElement();
       	if(String.valueOf(size).equalsIgnoreCase(tempItem.getText()))
       	{
       		tempItem.setSelected(true);
       	}
       }
    } catch (NumberFormatException ex) {
       return;
    }
    }

    /**
     * Sets the bold property to the specified value.
     * @param isBold boolean
     */
    public void setBold(boolean isBold){
       boldButton.setSelected(isBold);
       boldMenuItem.setSelected(isBold);//By Prakash.
    }

    /**
     * Sets the italic property to the specified value.
     * @param isItalic boolean
     */
    public void setItalic(boolean isItalic){
       italicButton.setSelected(isItalic);
       italicMenuItem.setSelected(isItalic);//By Prakash
    }

    /**
     * Sets the tab size property to the specified value.
     * @param tabSize int
     */
    public void setTabSize(int tabSize){
       cbTabSize.setSelectedItem(Integer.toString(tabSize));
       //By Prakash.
       if(tabSize==2)
       {
       	tabMenuItem2.setSelected(true);
       }
       else if(tabSize==3)
       {
       	tabMenuItem3.setSelected(true);
       }
       else if(tabSize==4)
       {
       	tabMenuItem4.setSelected(true);
       }
    }

/**
  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
  * All Rights Reserved
  * Copyright (C) 2006 Igor Mekterovic
  * All Rights Reserved
  */ 
/*  Modified the existing method for implementing localization */
    public void setcbResultPosition(String key){
       cbResultPosition.setSelectedItem((String)resultPositions.get(key));
       //By Prakash.
       if(key.equalsIgnoreCase(I18n.getString(panelVertSplit)))
       {
       		vsMenuItem.setSelected(true);
       }
        else if(key.equalsIgnoreCase(I18n.getString(panelHorzSplit)))
        {
            hsMenuItem.setSelected(true);
        }
        else if(key.equalsIgnoreCase(I18n.getString(panelTabUp)))
        {
                        tuMenuItem.setSelected(true);
        }
        else if(key.equalsIgnoreCase(I18n.getString(panelTabLeft)))
        {
            tlMenuItem.setSelected(true);
        }
        else if(key.equalsIgnoreCase(I18n.getString(panelTabRight)))
        {
            trMenuItem.setSelected(true);
        }
        else if(key.equalsIgnoreCase(I18n.getString(panelTabBottom)))
        {
                        tbMenuItem.setSelected(true);
        }
    }
  /* end of modification for I18n */

    //MdxBuilderTree builderTree
    public void setBuilderTree(JTree tree)
    {
        this.tree=tree;
        root=(DefaultMutableTreeNode)tree.getModel().getRoot();
    }
// Listeners:

    /**
     * Listener for the toggle bold atribute button.
     * @author Igor Mekterovic
     * @version 0.3
     */
   class BoldListener implements ItemListener {
      public void itemStateChanged(ItemEvent e) {
         int state = e.getStateChange();
         if ( state == ItemEvent.SELECTED )
         {
            parent.setTextToBold(true);
         	boldButton.setSelected(true);//By Prakash
         	boldMenuItem.setSelected(true);//By Prakash
         }	
         else
         {
            parent.setTextToBold(false);
         	boldButton.setSelected(false);//By Prakash
         	boldMenuItem.setSelected(false);//By Prakash
         }
      }
   }  // End BoldListener

   /**
    * Listener for the toggle italic atribute button.
    * @author Igor Mekterovic
    * @version 0.3
    */
   class ItalicListener implements ItemListener {
      public void itemStateChanged(ItemEvent e) {
         int state = e.getStateChange();
         if ( state == ItemEvent.SELECTED )
         {
            parent.setTextToItalic(true);
      		italicButton.setSelected(true);//By Prakash
      		italicMenuItem.setSelected(true);//By Prakash
         }
         else
         {
            parent.setTextToItalic(false);
      		italicButton.setSelected(false);//By Prakash
      		italicMenuItem.setSelected(false);//By Prakash
         }
      }
   }


   /**
    * Listener for the fonts combobox.
    * @author Igor Mekterovic
    * @version 0.3
    */
   class cbFontsListener implements ActionListener {
      public void actionPerformed(ActionEvent e) {
         String fontName = cbFonts.getSelectedItem().toString();
         parent.setFontName(fontName);
         //By Prakash.
         Enumeration fontElements=fontGroup.getElements();
         while(fontElements.hasMoreElements())
         {
         	JRadioButtonMenuItem tempItem=(JRadioButtonMenuItem)fontElements.nextElement();
         	if(fontName.equalsIgnoreCase(tempItem.getText()))
         	{
         		tempItem.setSelected(true);
         	}
         }
      }  // End actionPerformed
   }; // End cbFontsListener

   /**
    * Listener for the font size combobox.
    * @author Igor Mekterovic
    * @version 0.3
    */
   class cbSizesListener implements ActionListener {
      public void actionPerformed(ActionEvent e) {
         int fontSize = 0;
         try {
            fontSize = Integer.parseInt(cbSizes.getSelectedItem().toString());
//          By Prakash.
            Enumeration fontElements=fontSizeGroup.getElements();
            while(fontElements.hasMoreElements())
            {
            	JRadioButtonMenuItem tempItem=(JRadioButtonMenuItem)fontElements.nextElement();
            	if(String.valueOf(fontSize).equalsIgnoreCase(tempItem.getText()))
            	{
            		tempItem.setSelected(true);
            	}
            }
         } catch (NumberFormatException ex) {
            return;
         }
         parent.setFontSize(fontSize);
      }  // End actionPerformed
   }; // End cbFontsListener

   /**
    * Listener for the tab size combobox.
    * @author Igor Mekterovic
    * @version 0.3
    */
   class cbTabSizeListener implements ActionListener {
      public void actionPerformed(ActionEvent e) {
         int tabSize = 0;
         try {
            tabSize = Integer.parseInt(cbTabSize.getSelectedItem().toString());
         } catch (NumberFormatException ex) {
            return;
         }
         parent.setTabSize(tabSize);
         if(tabSize==2)
         {
         	tabMenuItem2.setSelected(true);
         }
         else if(tabSize==3)
         {
         	tabMenuItem3.setSelected(true);
         }
         else if(tabSize==4)
         {
         	tabMenuItem4.setSelected(true);
         }
      }  // End actionPerformed
   };

   /**
    * Listener for the result position combobox.
    * @author Igor Mekterovic
    * @version 0.3
    */
   class cbResultPositionListener implements ActionListener {

       /**
  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
  * All Rights Reserved
  * Copyright (C) 2006 Igor Mekterovic
  * All Rights Reserved
  */ 
/*  Modified the existing method for implementing localization */
       public void actionPerformed(ActionEvent e) {
            if(cbResultPosition.getItemCount()<6){return;}
         if (cbResultPosition.getSelectedItem().toString().equals(I18n.getString(panelVertSplit))){
            parent.setSplitPaneView(JSplitPane.VERTICAL_SPLIT);
            vsMenuItem.setSelected(true);//By Prakash.
         }else if (cbResultPosition.getSelectedItem().toString().equals(I18n.getString(panelHorzSplit))){
            parent.setSplitPaneView(JSplitPane.HORIZONTAL_SPLIT);
            hsMenuItem.setSelected(true);//By Prakash.
         }else if (cbResultPosition.getSelectedItem().toString().equals(I18n.getString(panelTabUp))){
            parent.setTabbedPaneView(JTabbedPane.TOP);
            tuMenuItem.setSelected(true);//By Prakash.
         }else if (cbResultPosition.getSelectedItem().toString().equals(I18n.getString(panelTabLeft))){
            parent.setTabbedPaneView(JTabbedPane.LEFT);
            tlMenuItem.setSelected(true);//By Prakash.
         }else if (cbResultPosition.getSelectedItem().toString().equals(I18n.getString(panelTabRight))){
            parent.setTabbedPaneView(JTabbedPane.RIGHT);
            trMenuItem.setSelected(true);//By Prakash.
         }else if (cbResultPosition.getSelectedItem().toString().equals(I18n.getString(panelTabBottom))){
            parent.setTabbedPaneView(JTabbedPane.BOTTOM);
            tbMenuItem.setSelected(true);//By Prakash.
         }
      }  // End actionPerformed
       
   }; // End cbFontsListener
  /* end of modification for I18n */

   /**
    * Inner class that displays the Color menu for setting the foreground color.
    * @author Igor Mekterovic
    * @version 0.3
    */
   class ForegroundListener implements ActionListener {
      public void actionPerformed(ActionEvent e) {
         ColorMenu m = (ColorMenu) e.getSource();
         parent.setForegroundColor(m.getColor());
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
         parent.setBackgroundColor(m.getColor());
      }
   }

   class DocListener implements DocumentListener {
	 public void changedUpdate(DocumentEvent e)
	 {	 
	 	status=false;
	 }
	 public void insertUpdate(DocumentEvent e)
	 {	 
	 	status=false;
	 }
	 public void removeUpdate(DocumentEvent e)
	 {	 	
	 	status=false;
	 }
 }

}

