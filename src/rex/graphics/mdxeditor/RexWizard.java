/*
 * Created on Jun 19, 2006
 * By Prakash Cincom Systems.
 * 
 */
package rex.graphics.mdxeditor;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
//import javax.swing.JRadioButtonMenuItem;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
//import javax.swing.JSplitPane;
import java.io.File;
import java.net.URL;
import java.util.Iterator;
import java.util.Vector;
import java.awt.Color;
import javax.swing.border.CompoundBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import org.w3c.dom.Document;

import rex.LogPropertyGenerator;
import rex.xmla.XMLADiscoverProperties;
import rex.xmla.XMLADiscoverRestrictions;
import rex.xmla.XMLAExecuteProperties;
import rex.xmla.XMLAObjectsFactory;
import rex.xmla.RexXMLAPort;
import rex.exceptions.*;
import rex.event.*;
import rex.metadata.ServerMetadata;
import rex.utils.*;

import java.awt.Toolkit;
import java.util.Locale;

import rex.graphics.mdxeditor.MdxEditorToolbar.DocListener;
import rex.graphics.mdxeditor.mdxbuilder.dnd.*;
 /**
  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
  * All Rights Reserved
  * Copyright (C) 2006 Igor Mekterovic
  * All Rights Reserved
  */ 
 /*  Added LanguageChangedListener to implement I18n  */
 
public class RexWizard extends JDialog implements LanguageChangedListener
{

	// Used for doing discover operation for Catalogs and Cubes.
	private RexXMLAPort port;
	private String strQuery;
	// XMLA Restriction for Discover operation.
	private XMLADiscoverRestrictions restriction;
	
	// XMLA Properties for Discover operation.
    private XMLADiscoverProperties   dProperties;
    
    // Used for checking Authenticity of MDX Query. 
    private XMLAExecuteProperties   execProperties;

	// XMLA Restriction for Discover operation (for MDXEditor).
	private XMLADiscoverRestrictions mdxEditorRestrictions;
	
	// XMLA Properties for Discover operation (for MDXEditor).
    private XMLADiscoverProperties   mdxEditorProperties;
    
    // Parent Panel for pConnection and pQueryEditor, arranged in form of card layout. 
    // Based in "Centre" position of Dialog.
    private JPanel pMdxMainPanel;
    
	// This panel shows connection paremeters to connect OLAP Server.
	private JPanel pConnection;
	
	// This panel is responsible for generating MDx Query and contains mdxEditor panel.
	private JPanel pQueryEditor;
	
	// MdxEditor is responsible for generating MDX Query. Declared in rex.graphics.mdxeditor package.
	private MdxEditor mdxEditor; 
	
	// Panel contains bNext, bBack and bFinish button. Based in "South" position of the Dialog.
	private JPanel pSouthPanel;

	// Card Layout object. Set on pMdxMainPanel.
	private CardLayout cardLayout;
	
	// URL of OLAP Server will be inserted here.
	private JTextField txtURL;
	
	// Contain Datasources of OLAP Server URL inserted in txtURL Text field.
	private JComboBox cmbDataSource;

	// Contain Catalogs of selected Data Source.
	private JComboBox cmbCatalog;
	
	// Contain Cubes of selected Catalog. 
	private JComboBox cmbCube;
	
	// error will be shown here. 
	private JLabel lblError;
	
	// Button for coming back on connection properties panel.
	private JButton bBack;
	
	// Button for showing Mdx Editor panel.	
	private JButton bNext;
	
	// Button will dispose dialog and return MDX Query to calling routines.
	private JButton bFinish=new JButton();

	// Button will Cancel the dialog.
	private JButton bCancel=new JButton();
	
	// contains DataSources with its catalogs. 
	private Vector vCatalog;
	
	// contains DataSources with catalogs and Cubes.
	private Vector vCube;
	
	// RexWizardEvent's Object.
	public RexWizardEvent rexWizardEvent;
	
	// RexWizardListener's object.
	public RexWizardListener rexWizardListener;
	
	// Inner class object which handles combo box events and finish buttons event.  
	private HandleRexWizardEvent handleEvent;

	// flag for tracking which constructor is executing.
	private int iFlagEditor=0;
	
	String strOLAPURL,strDataSourcename,strCatalog,strCube,strErrorConst="error";
	
	// Setting default locale.
	Locale locale=Locale.getDefault();
	
	
	/**
	 * Variable declaration to check query status during dialog closing operation.
	 * By Prakash. 1st June 2007
	 */
	private boolean status=true;//By Prakash.
   	Document document;//By Prakash.
	DocListener documentListener;//By Prakash. 
	/*
	 * End
	 */
	
	// Constructor without parameter
	public RexWizard() 
	{
		super();
                setGlassPane(new DragElement());
		iFlagEditor=0;
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
	
	// This constructor will show MDXEditor panel directly.
	public RexWizard(String strOLAPURL,String strDataSourcename,String strCatalog,String strCube) 
	{
		super();
                setGlassPane(new DragElement());
		this.strOLAPURL=strOLAPURL;
		this.strDataSourcename=strDataSourcename;
		this.strCatalog=strCatalog;
		this.strCube=strCube;
		iFlagEditor=1;
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
    /*
     * Constructor added on Nov 23 to accept the MDX Query
     * @param URL
     * @param Datasource
     * @param Catalog
     * @param Cube
     * @param MDXQuery
     *  
     */
        // This constructor will show MDXEditor panel directly.
	public RexWizard(String strOLAPURL,String strDataSourcename,
                String strCatalog,String strCube, String strQuery,
                Locale iRLocale) 
	{
		super();
    	final Iterator it=LocaleOptionPane.getListOfAvailLanguages().iterator();
    	if(iRLocale == null)
    	{
    	    this.locale=Locale.US;
    	}
    	else
    	{
    	    while(it.hasNext())
    	    {
    	        final Locale temp=(Locale)it.next();
    	        temp.getDisplayLanguage();
    	        temp.getDisplayCountry();
    	        if(iRLocale.getDisplayLanguage().equalsIgnoreCase(temp.getDisplayLanguage()) && iRLocale.getDisplayCountry().equalsIgnoreCase(temp.getDisplayCountry()))
    	        {
    	            this.locale=iRLocale;
    	            break;
    	        }
    	        else
    	        {
    	            this.locale=Locale.US;	    
    	        }
    	    }
    	}
        setGlassPane(new DragElement());
        new LogPropertyGenerator();
        RexDefaultProperties.createDefaultProperties();
		this.strOLAPURL=strOLAPURL;
		this.strDataSourcename=strDataSourcename;
		this.strCatalog=strCatalog;
		this.strCube=strCube;
                this.strQuery=strQuery;
                 /**
                  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
                  * All Rights Reserved
                  * Copyright (C) 2006 Igor Mekterovic
                  * All Rights Reserved
                  */ 
                /*adding this class to the list of classes that implement I18n */
                I18n.addOnLanguageChangedListener(this);
                I18n.setCurrentLocale(this.locale);                
                applyI18n();
                  /* end of modification for I18n */

		iFlagEditor=1;
	}
         /* sbalda */
      public void languageChanged(LanguageChangedEvent evt) {
          applyI18n();
     }
      public void applyI18n(){
          bCancel.setText(I18n.getString("btn.cancel"));
          bFinish.setText(I18n.getString("btn.finish"));
       }
    /* end of modification for I18n */

        // till here Sbalda Nov 23 (End of Constructor)`         /***********************/
	// This method registers calling routine with RexWizard class for 
	// executing method declared in the listener class.
	public void addRexWizardListener(RexWizardListener rwl)
	{
		rexWizardListener=rwl;
	}
	
	//This method actually executes listenerís method definition in calling 
	// routines with query information stored in RexWizardEvent Object.
	public void fireRexWizardEvent(RexWizardEvent rwt)
	{
		rexWizardListener.getMdx(rwt);
	}
	
	// Initialization, component placement.
	public void showDialog()
	{
	    
	    documentListener=new DocListener();
	    //parent.textArea.getDocument().addDocumentListener(documentListener);
	    
	    /**
	     * Modified closing event to implement action during dialog closing event.
	     * By Prakash. 1st June 2007.  
	     */
		addWindowListener(new WindowAdapter() 
		        {
		    		public void windowClosing(final WindowEvent wEvent) 
		    		{
		    		    if(status==true)
		    		    {
		    		        dispose();    
		    		    }
		    		    else
		    		    {
		    		        int confirm=JOptionPane.showConfirmDialog(null,I18n.getString("msgText.wantToSave"));
		    		        if(confirm==JOptionPane.YES_OPTION)
		    		        {	
		    		            status=true;
		    		            //checkMdx();
		    		    		final String []wholeMdxString=(mdxEditor.textArea.getText()).split("\n");  
		    		    		int mdxQueryCounter=0;
		    		            for(int lineCounter=0;lineCounter<wholeMdxString.length;lineCounter++)
		    		            {
		    		            	if(wholeMdxString[lineCounter].startsWith("--")||(wholeMdxString[lineCounter].trim()).length()==0)
		    		            	{
		    		            	continue;
		    		            	}
		    		            	mdxQueryCounter++;
		    		            }
		    		            String bss[]=new String[mdxQueryCounter];
		    		            mdxQueryCounter=0;
		    		            for(int lineCounter=0;lineCounter<wholeMdxString.length;lineCounter++)
		    		            {
		    		            	if(wholeMdxString[lineCounter].startsWith("--")||(wholeMdxString[lineCounter].trim()).length()==0)
		    		            	{
		    		            		continue;
		    		            	}
		    		            	bss[mdxQueryCounter]=wholeMdxString[lineCounter];
		    		            	mdxQueryCounter++;
		    		            }		    		            
		    		            String mdxStr="";
		    		    	    for(int i=0;i<bss.length;i++)
		    		    	    {
		    		    	      	mdxStr=mdxStr.concat((bss[i]).concat("\n"));
		    		    	    }		    			    		    			    	
		    			    	// Firing fireRexWizardEvent method with newly created RexWizardEvent object 
		    			    	// (Remember this object contains mdx query and will be passed to the calling routine).
		    			    	fireRexWizardEvent(new RexWizardEvent(mdxStr));
		    		            dispose();
		    		        }
		    		        else
		    		        {
		    		            status=true;
		    		            dispose();
		    		        }
		    		    }
		    		}
		        });
		/*
		 * End of the modification
		 */
		// Catalogs Initialization 
		vCatalog=new Vector();
		vCube=new Vector();
		vCatalog.addElement("");
		vCube.addElement("");
		
		// XMLA Restriction for Discover operation.
		restriction = XMLAObjectsFactory.newXMLADiscoverRestrictions();
		
		// XMLA Properties for Discover operation.
	    dProperties = XMLAObjectsFactory.newXMLADiscoverProperties();
	    
	    // XMLA Restriction for Discover operation (For MDXEditor).
		mdxEditorRestrictions = XMLAObjectsFactory.newXMLADiscoverRestrictions();
		
		// XMLA Properties for Discover operation (For MDXEditor).
	    mdxEditorProperties = XMLAObjectsFactory.newXMLADiscoverProperties();
	    
	    // Used for checking Authenticity of MDX Query. 
	    execProperties = XMLAObjectsFactory.newXMLAExecuteProperties();		
	    
		// Inner class for handling events
		handleEvent=new HandleRexWizardEvent();
		
		// Setting title for this dialog.
		//setTitle("Connection properties");
                 /**
                  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
                  * All Rights Reserved
                  * Copyright (C) 2006 Igor Mekterovic
                  * All Rights Reserved
                  */ 
               /* implementing localization */
                              
                setTitle(I18n.getString("dlgTitle.connProp"));
		  /* end of modification for I18n */

		// Initializing pConnection panel.		
		pConnection=new JPanel();
		
		// Setting null layout. Components will be added on the basis of bounds.
		pConnection.setLayout(null);

		// URL Label.
		final JLabel lblURL = new JLabel();
                 /**
                  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
                  * All Rights Reserved
                  * Copyright (C) 2006 Igor Mekterovic
                  * All Rights Reserved
                  */ 
               /* implementing localization */                
                lblURL.setText(I18n.getString("label.urlOfXMLA"));
                  /* end of modification for I18n */

		lblURL.setBounds(10, 26, 107, 16);
		pConnection.add(lblURL);

		// First Separator separating txtURL from rest of the connection properties.
		final JSeparator separator=new JSeparator();
		separator.setBounds(10, 98, 574, 1);
		pConnection.add(separator);
		
		// Data Source Label.
		final JLabel lblDatasource = new JLabel();
                
		lblDatasource.setText("Datasource");
		lblDatasource.setBounds(10, 117, 66, 16);
		pConnection.add(lblDatasource);

		// Catalog Label.
		final JLabel lblCatalog = new JLabel();
		lblCatalog.setText("Catalog");
		lblCatalog.setBounds(10, 178, 66, 16);
		pConnection.add(lblCatalog);

		// Cube Label.
		final JLabel lblCube = new JLabel();
		lblCube.setText("Cube");
		lblCube.setBounds(10, 244, 66, 16);
		pConnection.add(lblCube);

		// URLs Text field.
		txtURL = new JTextField();
		
		// Adding focus listener for txtURL 
		txtURL.addFocusListener(new FocusAdapter() 
		{
			// Call checkURL() function if focus lost from the txtURL. 
			public void focusLost(final FocusEvent fEvent) 
			{
				checkURL();				
			}
		});
		
		// Adding Key listener for txtURL
		txtURL.addKeyListener(new KeyAdapter() 
		{
			// Call checkURL() function if the key pressed is <<Enter>>.
			public void keyPressed(KeyEvent kEvent) 
			{
				// Checking for the Enter Key.
				if(kEvent.getKeyCode()== KeyEvent.VK_ENTER)
				{
					checkURL();
				}
			}
		});
		
		txtURL.setBounds(10, 51, 574, 20);
		pConnection.add(txtURL);
		
		// Temporary array for combo box. will be ommited during the operation.
		String tempArrayForCombo[]={" "};
		
		// Data source combo box.
		//cmbDataSource = new JComboBox(tempArrayForCombo);
		cmbDataSource = new JComboBox();
		cmbDataSource.setBounds(10, 139, 574, 25);
		pConnection.add(cmbDataSource);

		// Catalog combo box.
		//cmbCatalog = new JComboBox(tempArrayForCombo);
		cmbCatalog = new JComboBox();
		cmbCatalog.setBounds(10, 201, 574, 25);
		pConnection.add(cmbCatalog);

		// Cube combo box.
		//cmbCube = new JComboBox(tempArrayForCombo);
		cmbCube = new JComboBox();
		cmbCube.setBounds(10, 266, 574, 25);
		pConnection.add(cmbCube);

		// Error label
		lblError = new JLabel();
		
		// Font of lblError set to Plain Arial with font size of 10. 
		lblError.setFont(new Font("Arial", Font.PLAIN, 10));
		
		lblError.setForeground(Color.red);
		lblError.setBounds(10, 75, 574, 16);
		pConnection.add(lblError);

		// Second Separator. Separating pSouthPanel from rest of the panel.
		final JSeparator separator_2 = new JSeparator();
		separator_2.setBounds(10, 388, 574, 1);
		pConnection.add(separator_2);
		
		
		// Initializing pQueryEditor panel.
		pQueryEditor=new JPanel();
		
		// Setting Border layout layout.
		pQueryEditor.setLayout(new BorderLayout());		
		pQueryEditor.setBorder(new CompoundBorder());
		
		// Initialization of main panel.
		pMdxMainPanel=new JPanel();
		
		// Inserting pMdxMainPanel in centre of dialog.
		getContentPane().add(pMdxMainPanel,BorderLayout.CENTER);
		
		// card layout initialization
		cardLayout=new CardLayout();
		
		// Setting layout to card layout.
		pMdxMainPanel.setLayout(cardLayout);
		
		//Inserted pConnectionPanel as a card in pMdxMainPanel.
		pMdxMainPanel.add("pConnection",pConnection);
		
		//Inserted pQueryEditor panel as a card in pMdxMainPanel.		
		pMdxMainPanel.add("pQueryEditor",pQueryEditor);
		
		//Initializing pSouthPanel.
		pSouthPanel=new JPanel();
		
			
		// Inserting pSouthPanel in South of dialog.		
		getContentPane().add(pSouthPanel,BorderLayout.SOUTH);
                 /**
                  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
                  * All Rights Reserved
                  * Copyright (C) 2006 Igor Mekterovic
                  * All Rights Reserved
                  */ 
               /* implementing localization */
		// Initialization of bBack Button.
		bBack = new JButton();
              bBack.setText(I18n.getString("btn.back"));
              /* end of modification for I18n */
		
		// Adding listener to the button.
		bBack.addActionListener(new ActionListener() 
		{
			// This function will execute and show pConnection panel when there is a click event on the button.  
			public void actionPerformed(final ActionEvent aEvent) 
			{				
				cardLayout.show(pMdxMainPanel,"pConnection");
				mdxEditor.removeAll();
				mdxEditor=null;
				pQueryEditor.removeAll();
				bBack.setEnabled(false);
				bNext.setEnabled(true);
				bFinish.setEnabled(false);
                                /**
                                  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
                                  * All Rights Reserved
                                  * Copyright (C) 2006 Igor Mekterovic
                                  * All Rights Reserved
                                  */ 
                               /* implementing localization */
                                
                                setTitle(I18n.getString("dlgTitle.connProp"));
                                  /* end of modification for I18n */

				validate();
			}
		});		
		bBack.setEnabled(false);
		pSouthPanel.add(bBack);

		// Initialization of bNext Button.
		bNext = new JButton();
		bNext.setEnabled(false);
                 /**
                  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
                  * All Rights Reserved
                  * Copyright (C) 2006 Igor Mekterovic
                  * All Rights Reserved
                  */ 
               /* implementing localization */                
                bNext.setText(I18n.getString("btn.next"));
		  /* end of modification for I18n */

		// Adding listener to the button to call MDXEditor.
		bNext.addActionListener(new ActionListener() 
		{					
			public void actionPerformed(final ActionEvent aEvent) 
			{	
				strOLAPURL=txtURL.getText().trim();
				strDataSourcename=(String)cmbDataSource.getSelectedItem();
				strCatalog=(String)cmbCatalog.getSelectedItem();
				strCube=(String)cmbCube.getSelectedItem();				
				showEditor();
			}
		});
		pSouthPanel.add(bNext);

		// Initialization of bFinish Button.
		//bFinish = new JButton();
		
		// Initialization of bCancel Button.
		//bCancel = new JButton();
		
		// binding this button with the object of HandleRexWizardEvent (inner)class. 
		// Event handling will be taken care by this class.
		bFinish.addActionListener(handleEvent);
		bFinish.setEnabled(false);
		pSouthPanel.add(bFinish);
                 /**
                  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
                  * All Rights Reserved
                  * Copyright (C) 2006 Igor Mekterovic
                  * All Rights Reserved
                  */ 
               /* implementing localization */                
               bFinish.setText(I18n.getString("btn.finish"));
		  /* end of modification for I18n */

		bCancel.addActionListener(handleEvent);
                 /**
                  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
                  * All Rights Reserved
                  * Copyright (C) 2006 Igor Mekterovic
                  * All Rights Reserved
                  */ 
               /* implementing localization */                
                bCancel.setText(I18n.getString("btn.cancel"));
                  /* end of modification for I18n */

		pSouthPanel.add(bCancel);
		
		// Setting the size of dialog (x,y,width,height)
		//setBounds(100, 100, 700, 475);
		setBounds((int)(Toolkit.getDefaultToolkit().getScreenSize().getWidth()/6),(int)(Toolkit.getDefaultToolkit().getScreenSize().getHeight()/5), (int)(Toolkit.getDefaultToolkit().getScreenSize().getWidth()/1.3),(int)(Toolkit.getDefaultToolkit().getScreenSize().getHeight()/1.3));


		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		// Can't resize
		setResizable(true);
		
		// Making it a modal dialog.
		setModal(true);
		if(iFlagEditor==1)
		{
			showEditor();
			mdxEditor.textArea.getDocument().addDocumentListener(documentListener);//temp
			bNext.setVisible(false);
			bBack.setVisible(false);			
		}
		setVisible(true);
	}
	
	// This function checks whether txtURL field is empty?
	// If not, then call doDiscover() function. 
	private void checkURL()
	{
		if((((txtURL.getText()).trim()).length())==0)
		{
                 /**
                  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
                  * All Rights Reserved
                  * Copyright (C) 2006 Igor Mekterovic
                  * All Rights Reserved
                  */ 
               /* implementing localization */
                        lblError.setText(I18n.getString("label.noUrlFound"));
                  /* end of modification for I18n */

			cmbDataSource.removeAllItems();
			cmbCatalog.removeAllItems();
			cmbCube.removeAllItems();
		}
		else
		{
			doDiscover();
			cmbDataSource.addActionListener(handleEvent);
			//cmbCatalog.addActionListener(handleEvent);
		}
	}
	
	// Is Doing Discover operation for data sources, catalogs and cubes. 
	// After that, adding it in the vector for the future usage. 
	private void doDiscover()
	{
		try
		{
			port=new RexXMLAPort(new URL(txtURL.getText().trim()));
			final String [] dsn=port.discoverDataSourcesAsString(restriction,dProperties);
			cmbDataSource.removeAllItems();
			for(int j=0;j<dsn.length;j++)
			{
				cmbDataSource.addItem(dsn[j]);
			}
			vCatalog.removeAllElements();
			vCube.removeAllElements();
			for(int j=0;j<dsn.length;j++)
			{
				dProperties.setDataSourceInfo(dsn[j]);
				final String [] catalogs=port.getCatalogListAsString(restriction,dProperties);
				for(int k=0;k<catalogs.length;k++)
				{
					dProperties.setCatalog(catalogs[k]);
					final String [] cubes=port.getCubeListAsString(restriction,dProperties);
					vCatalog.addElement((dsn[j].concat(":"+catalogs[k])));
					for(int l=0;l<cubes.length;l++)
					{
						vCube.addElement((dsn[j].concat(":"+catalogs[k])).concat(":"+cubes[l]));						
					}
				}
			}			
			insertCatalog();
		}
		catch(RexXMLAExecuteException xmlaException)
		{
			handleException(xmlaException.getError());
		}
		catch(RexXMLADiscoverException xmlaDiscException)
		{
			handleException(xmlaDiscException.getError());
		}
		catch(Exception exc)
		{
			handleException(exc.getMessage());
		}
	}

	// Exceptions of doDiscover() function handled here
	private void handleException(final String exceptionString)
	{
		lblError.setText(exceptionString);
		cmbDataSource.removeAllItems();
		cmbCatalog.removeAllItems();
		cmbCube.removeAllItems();
		bNext.setEnabled(false);
	}
	
	// Inserting catalogs of selected data source in cmbCatalog from vCatalog. 
	private void insertCatalog()
	{
		cmbCatalog.removeAllItems();
		final int catalogSize=vCatalog.size();		

		for(int catCounter=0;catCounter<catalogSize;catCounter++)
		{
			String [] dsnCat=(vCatalog.elementAt(catCounter).toString()).split(":");
			if(((String)cmbDataSource.getSelectedItem()).equals(dsnCat[0]))
			{					
				cmbCatalog.addItem(dsnCat[1]);
			}			
		}
		insertCube();		
	}

	// Inserting cubes of selected catalog in cmbCube from vCube.
	private void insertCube()
	{
		final int cubeSize=vCube.size();
		cmbCube.removeAllItems();
		for(int cubeCounter=0;cubeCounter<cubeSize;cubeCounter++)
		{
			final String dsnCatCube[]=(vCube.elementAt(cubeCounter).toString()).split(":");
			if(((String)cmbDataSource.getSelectedItem()).equals(dsnCatCube[0]) && ((String)cmbCatalog.getSelectedItem()).equals(dsnCatCube[1]))
			{
					cmbCube.addItem(dsnCatCube[2]);																			
			}						
		}
		lblError.setText("");
		bNext.setEnabled(true);
	}
	
	//	This function will execute and show pQueryEditor panel with MdxEditor at the center position.	
	public void showEditor()
	{
	    mdxEditorRestrictions.setCatalog(strCatalog);
	    mdxEditorRestrictions.setCubeName(strCube);
	    mdxEditorProperties.setDataSourceInfo(strDataSourcename);
	    mdxEditorProperties.setCatalog(strCatalog);
		mdxEditor=new MdxEditor(mdxEditorRestrictions,mdxEditorProperties,new ServerMetadata(strOLAPURL),strCube);
		mdxEditor.setLocale(this.locale);
		I18n.setCurrentLocale(this.locale);         
		mdxEditor.setLocaleEnabled(false);
		pQueryEditor.add(mdxEditor,BorderLayout.CENTER);
		cardLayout.show(pMdxMainPanel,"pQueryEditor");
                mdxEditor.textArea.setText(strQuery); 
                // Above line added on Nov 23 
                // Setting the Query passed from iReport to the textArea
                mdxEditor.textArea.validate();
		bNext.setEnabled(false);
		bFinish.setEnabled(true);
		bBack.setEnabled(true);
		setTitle(mdxEditorProperties.getDataSourceInfo()+":"+mdxEditorProperties.getCatalog()+":"+mdxEditorRestrictions.getCubeName());

                
		validate();
	}
		
	// This function checks authenticity of MDX Query generated through MDXEditor.    
	private void checkMdx()
	{
		if(mdxEditor.textArea.getText().trim().length()==0)
		{
                 /**
                  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
                  * All Rights Reserved
                  * Copyright (C) 2006 Igor Mekterovic
                  * All Rights Reserved
                  */ 
               /* implementing localization */
			JOptionPane.showMessageDialog(null,
                                I18n.getString("msgText.noQuery"),
                                strErrorConst,
                                JOptionPane.INFORMATION_MESSAGE);
                          /* end of modification for I18n */

			return;
		}
		final String []wholeMdxString=(mdxEditor.textArea.getText()).split("\n");  
		int mdxQueryCounter=0;
        for(int lineCounter=0;lineCounter<wholeMdxString.length;lineCounter++)
        {
        	if(wholeMdxString[lineCounter].startsWith("--")||(wholeMdxString[lineCounter].trim()).length()==0)
        	{
        	continue;
        	}
        	mdxQueryCounter++;
        }
        String bss[]=new String[mdxQueryCounter];
        mdxQueryCounter=0;
        for(int lineCounter=0;lineCounter<wholeMdxString.length;lineCounter++)
        {
        	if(wholeMdxString[lineCounter].startsWith("--")||(wholeMdxString[lineCounter].trim()).length()==0)
        	{
        		continue;
        	}
        	bss[mdxQueryCounter]=wholeMdxString[lineCounter];
        	mdxQueryCounter++;
        }
        
        String mdxStr="";
	    for(int i=0;i<bss.length;i++)
	    {
	      	mdxStr=mdxStr.concat((bss[i]).concat("\n"));
	    }

	    try
		{
		    if(iFlagEditor==0)
		    {
		    	execProperties.setDataSourceInfo((String)cmbDataSource.getSelectedItem());
		    	execProperties.setCatalog((String)cmbCatalog.getSelectedItem());
		    }
		    if(iFlagEditor==1)
		    {
		    	execProperties.setDataSourceInfo(strDataSourcename);
		    	execProperties.setCatalog(strCatalog);
		    	port=new RexXMLAPort(new URL(strOLAPURL));
		    }
	    	// Executing Query to check the authencity. 
	    	// execute method throws RexXMLAException if query is not valid. 
	    	port.execute(mdxStr,execProperties);
	    	
	    	// If no exception is thrown means query is genuine.
	    	// Generating RexWizardEvent's object by providing mdx query as a parameter.
	    	rexWizardEvent=new RexWizardEvent(mdxStr);
	    	
	    	// Firing fireRexWizardEvent method with newly created RexWizardEvent object 
	    	// (Remember this object contains mdx query and will be passed to the calling routine).
	    	fireRexWizardEvent(rexWizardEvent);
	    	
	    	// hiding and disposing.
	    	setVisible(false);
	    	dispose();
		}
	    catch(RexXMLAExecuteException exc)
		{
	    	JOptionPane.showMessageDialog(null,exc.toString(),strErrorConst,JOptionPane.INFORMATION_MESSAGE);
		}
	    catch(RexXMLADiscoverException exc)
		{
	    	JOptionPane.showMessageDialog(null,exc.toString(),strErrorConst,JOptionPane.INFORMATION_MESSAGE);
		}	    
	    catch(Exception exc)
		{
	    	JOptionPane.showMessageDialog(null,exc.toString(),strErrorConst,JOptionPane.INFORMATION_MESSAGE);
		}
	}
        
	
	// Inner class which handles events of cmbDataSource, cmbCatalog, cmbCube and bFinsh.
	private class HandleRexWizardEvent implements ActionListener
	{
		public void actionPerformed(final ActionEvent actionEvent) 
		{
			final Object eventSource=actionEvent.getSource();
			if(eventSource.equals(bFinish))
			{
				checkMdx();
			}
			if(eventSource.equals(bCancel))
			{
				setVisible(false);
				dispose();
			}
			if(eventSource.equals(cmbDataSource))
			{
				try
				{
					insertCatalog();
				}
				catch(Exception exc)
				{
					S.reportError(exc);
				}
			}
			if(eventSource.equals(cmbCatalog))
			{
				try
				{					
					insertCube();
				}
				catch(Exception exc)
				{
					S.reportError(exc);	
				}
			}
		}
	}
	/**
	 * Listener class to check the query status on text area.
	 * @author pyadav
	 * 1st June 2007
	 */
	class DocListener implements DocumentListener 
	{
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
	/*
	 * End.
	 */
	public static void main(String args[]) 
	{
           
			RexWizard frame = new RexWizard();
			frame.showDialog();
			frame.setVisible(true);
	}	
}