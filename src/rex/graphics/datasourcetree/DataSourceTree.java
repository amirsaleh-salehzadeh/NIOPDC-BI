package rex.graphics.datasourcetree;


import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.event.TreeSelectionListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.TreeSelectionModel;

import java.io.IOException;
import javax.swing.JEditorPane;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JFrame;
import java.awt.*;
import java.awt.event.*;
import javax.swing.JPanel;
import rex.metadata.ServerMetadata;

import javax.swing.JButton;
import javax.swing.ToolTipManager;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.ImageIcon;
import rex.utils.LanguageChangedEvent;
import rex.utils.LanguageChangedListener;

import rex.utils.S;
import rex.graphics.datasourcetree.elements.DataSourceRootElement;
import javax.swing.tree.TreePath;
import javax.swing.JPopupMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import rex.graphics.datasourcetree.elements.DataSourceTreeElement;
import javax.swing.JComponent;
import javax.swing.JTabbedPane;
import javax.swing.JMenuBar;

import javax.net.ssl.TrustManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.HttpsURLConnection;
import java.security.Security;
import javax.net.ssl.X509TrustManager;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;
import rex.graphics.mdxeditor.MdxEditor;
import rex.graphics.mdxeditor.mdxbuilder.dnd.DragElement;
import java.util.HashMap;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileNotFoundException;
import rex.graphics.*;
import rex.xmla.XMLADiscoverRestrictions;
import rex.xmla.XMLADiscoverProperties;
import rex.xmla.XMLAObjectsFactory;
import rex.graphics.datasourcetree.elements.CubeElement;

import java.util.Locale;
import rex.utils.*;
import java.io.*;
import java.util.Properties;
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

public class DataSourceTree extends JPanel implements ActionListener, 
        LanguageChangedListener{
   final JTree tree;
   JFrame frame= new JFrame();
   JPopupMenu popup;
   private JComponent parent;
   private TreePath popUpSource;
   final HashMap trustedSites;
   static boolean mountedSSL;

  // By Prakash
 
   JMenuBar bnLocale;
   LocaleOptionPane localeMenu;
   //JPopupMenu localePopup;
   // End.


   public DataSourceTree(JComponent _parent) {
      this();
      parent = _parent;
       /**
      * Copyright (C) 2006 CINCOM SYSTEMS, INC.
      * All Rights Reserved
      * Copyright (C) 2006 Igor Mekterovic
      * All Rights Reserved
      */ 
   }
   public DataSourceTree() {
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

      DataSourceTreeModel treeModel = new DataSourceTreeModel();
      
      trustedSites = new HashMap();
     //Create a tree that allows one selection at a time.
      tree = new JTree(treeModel);


      tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
      //Enable tool tips.
      ToolTipManager.sharedInstance().registerComponent(tree);
      tree.setCellRenderer(new MyRenderer());

        //Create the scroll pane and add the tree to it.
      JScrollPane treeView = new JScrollPane(tree);


      //Dimension minimumSize = new Dimension(100, 50);
      //treeView.setMinimumSize(minimumSize);

      treeView.setPreferredSize(new Dimension(400, 600));

      treeView.setMinimumSize(new Dimension(200, 600));
      treeView.setMaximumSize(new Dimension(800,600));

      /**
       * Menu for selecting locale.
       * by Prakash.
       */
      localeMenu =new LocaleOptionPane();
	  bnLocale=new JMenuBar();
	  bnLocale.add(localeMenu);
	  /*
	   * End of insertion.
	   */

      this.setLayout(new BorderLayout());
//      JPanel contentPane = new JPanel(new BorderLayout());
      this.add(treeView, BorderLayout.CENTER);     
	this.add(bnLocale, BorderLayout.SOUTH);// By Prakash
      popup = new JPopupMenu();

      tree.addMouseListener(new PopupListener());

      mountedSSL = false;
      // add datasources from file if there are any:
      try{
         BufferedReader in = new BufferedReader(new FileReader("startup.datasources.txt"));
         String url;
         while ( ( url = in.readLine()) != null){
            if (url.toLowerCase().startsWith("https")) {
               mountSSL();
            }
            treeModel.addDataSource(url);
         }
         in.close();
      } catch (FileNotFoundException e1){
      } catch (IOException e2){
         // not a care in the world...
      }
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
        localeMenu.setCurrentLocale(evt.getLocale());
    }
     /**
  *  Helper method to implement locatization when language is changed
  */

      public void applyI18n(){
          if(parent instanceof JComponent){
              parent.setToolTipText(I18n.getString("toolTip.exploring"));
          }
          if (frame instanceof JFrame){
             frame.setTitle(I18n.getString("frameTitle.mdxEditor2"));
        }
     
    } 
    
  /* end of modification for I18n */

   
   private void mountSSL(){
      // do it only once:
      if (mountedSSL) return;
      mountedSSL = true;

      System.setProperty("java.protocol.handler.pkgs","javax.net.ssl");
      Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
      try {
         System.setProperty("javax.net.ssl.trustStore"
                            , ClassLoader.getSystemResource("sslkeys/client.ks").toString());
         System.setProperty("javax.net.ssl.trustStorePassword"
                            , "igorludi");
      }
      catch (Exception fnfe) {
         S.out("Couldn't find:  sslkeys/client.ks in your application directory.");
      }

      try {
         System.setProperty("javax.net.ssl.keyStore"
                            , ClassLoader.getSystemResource("sslkeys/servers.ks").toString());
         System.setProperty("javax.net.ssl.keyStorePassword"
                            , "changeit");
      }
      catch (Exception fnfe) {
         S.out("Couldn't find:  sslkeys/servers.ks in your application directory.");
      }
      TrustManager[] trustAllCerts = new TrustManager[] {
         new X509TrustManager() {
         public java.security.cert.X509Certificate[] getAcceptedIssuers() {
            S.out("getAcceptedIssuers() called");
            return null;
         }

         public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String s) {
            S.out("Checking isClientTrusted for X509Certificate");
         }

         public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String s) throws java.security.cert.
            CertificateException {
            String msg = new String("");

            for (int i = 0; i < certs.length; i++) {
               if (trustedSites.get(certs[i].getSubjectDN()) != null) {
                  S.out("already trusted... skipping...");
                  continue;
               }
              /**
              * Copyright (C) 2006 CINCOM SYSTEMS, INC.
              * All Rights Reserved
              * Copyright (C) 2006 Igor Mekterovic
              * All Rights Reserved
              */ 
               msg += I18n.getString("str.issuerDN") + certs[i].getIssuerDN()
                  + I18n.getString("str.subjectDN")+ certs[i].getSubjectDN();
               if (JOptionPane.YES_OPTION != JOptionPane.showConfirmDialog(null,
                  I18n.getString("msgText.trustedUrl1") + msg
                  + I18n.getString("msgText.trustedUrl2")
                  , I18n.getString("msgTitle.securityWarning")
                  , JOptionPane.YES_NO_OPTION)) {
                 /* end of modification for I18n */

                    throw new java.security.cert.CertificateException(I18n.getString("exception.trustCertificate"));
               }
               else {
                  // don't ask again
                  trustedSites.put(certs[i].getSubjectDN(), certs[i].getSubjectDN());
               }
            }
         }
      }
      } ;

// Install the trust manager
      try {
         SSLContext sc = SSLContext.getInstance("SSL");
//                 I'm setting the default host name verifier because I've changed axis.jar
//                 to use HttpsURLConnection.getDefaultHostnameVerifier
//                   didn't work otherwise
         sc.init(null, trustAllCerts, new java.security.SecureRandom());
         HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
         HostnameVerifier hv = new HostnameVerifier() {
            public boolean verify(String urlHostName, SSLSession session) {
               return true;
            }
         };
         HttpsURLConnection.setDefaultHostnameVerifier(hv);
      }
      catch (Exception ex) {
         S.out("Could not set trustmanager");
      }
   }

   public void actionPerformed(ActionEvent e){
  
       int i = 0;
       // To do:
       // Must implement Refresh, and Explore:
            /**
          * Copyright (C) 2006 CINCOM SYSTEMS, INC.
          * All Rights Reserved
          * Copyright (C) 2006 Igor Mekterovic
          * All Rights Reserved
          */ 

        if (I18n.getString("menu.add").equals(e.getActionCommand())){
                  String url = JOptionPane.showInputDialog(
                          I18n.getString("msgText.urlInput"), 
                          "http://localhost:8080/mondrian/xmla");  
                    /* end of modification for I18n */

//****************************  Done by Prakash
          if ( url != null && url.length()>0 ){
             if (url.toLowerCase().startsWith("https")){
                mountSSL();
             }
             ((DataSourceTreeModel) tree.getModel()).addDataSource(url);

          }
         /**
          * Copyright (C) 2006 CINCOM SYSTEMS, INC.
          * All Rights Reserved
          * Copyright (C) 2006 Igor Mekterovic
          * All Rights Reserved
          */ 
         }else if(I18n.getString("menu.explore1").equals(e.getActionCommand())){ 
             /* end of modification for I18n */

          if (parent != null){
             // I'm constructing new restrictions and properties objects for
             // new tab (Dimension Tree) to take away with it.
             // Also, must pass a reference to ServerMetadata object, one object to rule them all

             XMLADiscoverRestrictions restrictions = XMLAObjectsFactory.newXMLADiscoverRestrictions();
             XMLADiscoverProperties   properties   = XMLAObjectsFactory.newXMLADiscoverProperties();

             // only cube element could have started an explore action:
             // (for the time being, if I find a meaning in exploring catalogs and datasources, I'll move those getCubeName annd getCatalogName to interface)
             CubeElement selectedItem = ((CubeElement)((TreeElement)(popUpSource.getPathComponent(popUpSource.getPathCount()-1))).getUserObject());



             restrictions.setCatalog(selectedItem.getCatalogName());
             restrictions.setCubeName(selectedItem.getCubeName());


             properties.setDataSourceInfo(selectedItem.getDataSourceInfo());
             properties.setCatalog(selectedItem.getCatalogName());
             properties.setFormat("Tabular");
             properties.setContent("SchemaData");

 /**
  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
  * All Rights Reserved
  * Copyright (C) 2006 Igor Mekterovic
  * All Rights Reserved
  */ 
              
             // Go, my child, go, see the world...
             ((JTabbedPane)parent).addTab(
                          selectedItem.getCubeName()
                        , selectedItem.getIcon()
                        , new Viewer(restrictions, properties, selectedItem.getServerMetaData(), (JTabbedPane)parent)
                        , I18n.getString("toolTip.exploring") + selectedItem.getDataSourceInfo() + ":" + selectedItem.getCatalogName() + ":" + selectedItem.getCubeName());
  /* end of modification for I18n */


          }
           /**
  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
  * All Rights Reserved
  * Copyright (C) 2006 Igor Mekterovic
  * All Rights Reserved
  */ 
       }else if(I18n.getString("menu.explore2").equals(e.getActionCommand())){ //sbalda
             /* end of modification for I18n */

          if (parent != null){
             // I'm constructing new restrictions and properties objects for
             // new tab (MdxEditor) to take away with it.
             // Also, must pass a reference to ServerMetadata object, one object to rule them all

             XMLADiscoverRestrictions restrictions = XMLAObjectsFactory.newXMLADiscoverRestrictions();
             XMLADiscoverProperties   properties   = XMLAObjectsFactory.newXMLADiscoverProperties();

             // only cube element could have started an explore action:
             // (for the time being, if I find a meaning in exploring catalogs and datasources, I'll move those getCubeName annd getCatalogName to interface)
             CubeElement selectedItem = ((CubeElement)((TreeElement)(popUpSource.getPathComponent(popUpSource.getPathCount()-1))).getUserObject());


             restrictions.setCatalog(selectedItem.getCatalogName());
             restrictions.setCubeName(selectedItem.getCubeName());


             properties.setDataSourceInfo(selectedItem.getDataSourceInfo());
             properties.setCatalog(selectedItem.getCatalogName());
             properties.setFormat("Tabular");
             properties.setContent("SchemaData");

 /**
  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
  * All Rights Reserved
  * Copyright (C) 2006 Igor Mekterovic
  * All Rights Reserved
  */ 
           
             ((JTabbedPane)parent).addTab(
                          selectedItem.getCubeName()
                        , selectedItem.getIcon()
                        , new MdxEditor(  restrictions
                                                      , properties
                                                      , selectedItem.getServerMetaData()
                                                      , selectedItem.getCubeName())
                        , I18n.getString("toolTip.exploring")  + selectedItem.getDataSourceInfo() + ":" + selectedItem.getCatalogName() + ":" + selectedItem.getCubeName());
           
  /* end of modification for I18n */



          }
          
           /**
          * Copyright (C) 2006 CINCOM SYSTEMS, INC.
          * All Rights Reserved
          * Copyright (C) 2006 Igor Mekterovic
          * All Rights Reserved
          */ 
         }else if(I18n.getString("menu.explore3").equals(e.getActionCommand())){ //sbalda
             /* end of modification for I18n */

          if (parent != null){
             // I'm constructing new restrictions and properties objects for
             // new tab (MdxEditor) to take away with it.
             // Also, must pass a reference to ServerMetadata object, one object to rule them all

             XMLADiscoverRestrictions restrictions = XMLAObjectsFactory.newXMLADiscoverRestrictions();
             XMLADiscoverProperties   properties   = XMLAObjectsFactory.newXMLADiscoverProperties();

             // only cube element could have started an explore action:
             // (for the time being, if I find a meaning in exploring catalogs and datasources, I'll move those getCubeName annd getCatalogName to interface)
             CubeElement selectedItem = ((CubeElement)((TreeElement)(popUpSource.getPathComponent(popUpSource.getPathCount()-1))).getUserObject());


             restrictions.setCatalog(selectedItem.getCatalogName());
             restrictions.setCubeName(selectedItem.getCubeName());


             properties.setDataSourceInfo(selectedItem.getDataSourceInfo());
             properties.setCatalog(selectedItem.getCatalogName());
             properties.setFormat("Tabular");
             properties.setContent("SchemaData");


             // Go, my child, go, see the world...

              /**
              * Copyright (C) 2006 CINCOM SYSTEMS, INC.
              * All Rights Reserved
              * Copyright (C) 2006 Igor Mekterovic
              * All Rights Reserved
              */ 
             frame = new JFrame(I18n.getString("frameTitle.mdxEditor2") 
                                       + selectedItem.getDataSourceInfo()
                                       + ":" + selectedItem.getCatalogName()
                                       + ":" + selectedItem.getCubeName()
                                       + ")");
  /* end of modification for I18n */

     		 frame.setGlassPane(new DragElement());// By Prakash.
             frame.getContentPane().add(new MdxEditor(  restrictions
                                                      , properties
                                                      , selectedItem.getServerMetaData()
                                                      , selectedItem.getCubeName())
                                        , BorderLayout.CENTER);

             frame.addWindowListener(new WindowAdapter() {
                public void windowClosing(WindowEvent e) {
                	/**
                	 * Lines inserted by prakash
                	 * Disposing frame 
                	 * 3rd August 2006
                	 */
                	JFrame frm=(JFrame)e.getSource();
                	frm.dispose();
                	/**
                	 * End of the insertion made by prakash
                	 */
                }
             });
             frame.pack();
             frame.setExtendedState(frame.MAXIMIZED_BOTH);
             frame.setVisible(true);

          }
       }
    }

   
   
   private class MyRenderer extends DefaultTreeCellRenderer {
        ImageIcon tutorialIcon;

        public MyRenderer() {
        }

        public Component getTreeCellRendererComponent(
                            JTree tree,
                            Object value,
                            boolean sel,
                            boolean expanded,
                            boolean leaf,
                            int row,
                            boolean hasFocus) {

            super.getTreeCellRendererComponent(
                            tree, value, sel,
                            expanded, leaf, row,
                            hasFocus);

            setIcon(((DataSourceTreeElement)((TreeElement)value).getUserObject()).getIcon());
            setToolTipText(((DataSourceTreeElement)((TreeElement)value).getUserObject()).getToolTip());
            return this;
        }

    }

   class PopupListener extends MouseAdapter {
      public void mousePressed(MouseEvent e) {
         maybeShowPopup(e);
      }

      public void mouseReleased(MouseEvent e) {
         maybeShowPopup(e);
      }

      private void maybeShowPopup(MouseEvent e) {
         int selRow = tree.getRowForLocation(e.getX(), e.getY());
         popUpSource = tree.getPathForLocation(e.getX(), e.getY());
         if (selRow != -1) {
            if (e.isPopupTrigger()) {
               String[] al;
               JMenuItem menuItem;

               popup.removeAll();
               // get the selected TreeElement's action list:
               al = ( (DataSourceTreeElement) ( (TreeElement) (popUpSource.getPathComponent(popUpSource.getPathCount() -
                  1))).getUserObject()).getPopUpActionList();
               for (int i = 0; al != null && i < al.length; i++) {
                  menuItem = new JMenuItem(al[i]);
                  menuItem.addActionListener(DataSourceTree.this);
                  popup.add(menuItem);
               }
               popup.show(e.getComponent()
                          , e.getX()
                          , e.getY());
            }
         }
      }

    }



   public static void main(String[] args) {
       JFrame frame = new JFrame("Testing DataSourceTree...");
       DataSourceTree dst = new DataSourceTree();
//      frame.getContentPane().add(dst);
       frame.setContentPane(dst);
       frame.addWindowListener(new WindowAdapter() {
             public void windowClosing(WindowEvent e) {
                System.exit(0);
             }
          });

      frame.pack();
      frame.setVisible(true);
    }

}
