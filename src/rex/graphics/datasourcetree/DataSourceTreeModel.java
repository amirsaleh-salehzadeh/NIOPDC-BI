package rex.graphics.datasourcetree;

import javax.swing.event.*;
import javax.swing.tree.*;
import java.util.Vector;

import rex.graphics.datasourcetree.elements.*;
import rex.metadata.ServerMetadata;

import rex.utils.S;
import javax.swing.JOptionPane;
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileWriter;
import java.io.FileOutputStream;
import java.io.DataOutputStream;
import java.io.RandomAccessFile;
import rex.graphics.*;
import rex.utils.*;
import java.util.Locale;
/**
 * <p>Title: WHEX</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author igor
 * @version 1.0
 */


public class DataSourceTreeModel implements TreeModel { 

    private Vector treeModelListeners;
    private TreeElement root;
    private Vector svm;

    public DataSourceTreeModel() {
      
       root = new TreeElement(new DataSourceRootElement(), null);
       svm = new Vector();
       treeModelListeners = new Vector();
    }
    public String getUname(){
          return "Igor rules!";
       }
    
    
    
    private void maybeAddDataSource(String URL){
   //   add url to the end of file if it' not already in the file
       try{
          RandomAccessFile rf  =  new RandomAccessFile("startup.datasources.txt", "rw");
          String s;
          while ( (s = rf.readLine()) != null){
             if (s.equalsIgnoreCase(URL)){
                S.out("URL already present in startup.datasources.txt");
                rf.close();
                return;
             }
          }
        /**
          * Copyright (C) 2006 CINCOM SYSTEMS, INC.
          * All Rights Reserved
          * Copyright (C) 2006 Igor Mekterovic
          * All Rights Reserved
          */ /* implementing localization */
         if (JOptionPane.YES_OPTION == 
                  JOptionPane.showConfirmDialog( null, 
                  I18n.getString("msgText.confirmUrl") , 
                  I18n.getString("msgTitle.confirmUrl") ,
                  JOptionPane.YES_NO_OPTION)){
          /* end of modification for I18n */

             rf.seek(rf.length());
             rf.writeBytes(URL.trim() + "\n");
          }
          rf.close();
       } catch (IOException e){
           /**
            * Commented for avoiding any unwanted trace information to be printed on console.
            * from Prakash. 08-05-2007.
            */
          //e.printStackTrace();
           /*
            * End of the modification.
            */
       }

    }
    public void addDataSource(String URL){
       int i;
       ServerMetadata smd = new ServerMetadata(URL);
       DataSourceTreeElement ds[] = smd.discoverDataSources();
       if (ds != null){
          maybeAddDataSource(URL);
          for (i = 0; ds != null && i < ds.length; i++) {
             TreeElement dataSource = new TreeElement(ds[i], root);
             root.addChild(dataSource);
             buildTreeRecursively(dataSource);
          }
          svm.add(smd);
          fireTreeStructureChanged(root);
       }else{
           /**
          * Copyright (C) 2006 CINCOM SYSTEMS, INC.
          * All Rights Reserved
          * Copyright (C) 2006 Igor Mekterovic
          * All Rights Reserved
          */ /* implementing localization */
            JOptionPane.showMessageDialog(null
                                        , I18n.getString("msgText.nothing") + URL
                                        , I18n.getString("msgTitle.addDS")
                                        , JOptionPane.ERROR_MESSAGE);
              /* end of modification for I18n */

       }

    }
    private void buildTreeRecursively(TreeElement parent){
       int i;
       DataSourceTreeElement ds[] = ((DataSourceTreeElement)parent.getUserObject()).getChildren();
       for(i = 0; ds != null && i < ds.length; i++){
          TreeElement node = new TreeElement(ds[i], parent);
          parent.addChild (node);
          buildTreeRecursively(node);
       }
    }

    /**
     * Used to toggle between show ancestors/show descendant and
     * to change the root of the tree.
     */
    /*
    public void showAncestor(boolean b, Object newRoot) {
        showAncestors = b;
        Person oldRoot = rootPerson;
        if (newRoot != null) {
           rootPerson = (Person)newRoot;
        }
        fireTreeStructureChanged(oldRoot);
    }
*/

//////////////// Fire events //////////////////////////////////////////////

    /**
     * The only event raised by this model is TreeStructureChanged with the
     * root as path, i.e. the whole tree has changed.
     */
    protected void fireTreeStructureChanged(TreeElement oldRoot) {
        int len = treeModelListeners.size();
        TreeModelEvent e = new TreeModelEvent(this,
                                              new Object[] {oldRoot});
        for (int i = 0; i < len; i++) {
            ((TreeModelListener)treeModelListeners.elementAt(i)).
                    treeStructureChanged(e);
        }
    }


//////////////// TreeModel interface implementation ///////////////////////


    /**
     * Adds a listener for the TreeModelEvent posted after the tree changes.
     */
    public void addTreeModelListener(TreeModelListener l) {
        treeModelListeners.addElement(l);
    }

    /**
     * Returns the child of parent at index index in the parent's child array.
     */
    public Object getChild(Object parent, int index) {
        TreeElement p = (TreeElement)parent;
        return p.getChildAt(index);
    }

    /**
     * Returns the number of children of parent.
     */
    public int getChildCount(Object parent) {
        TreeElement p = (TreeElement)parent;
        return p.getChildCount();
    }

    /**
     * Returns the index of child in parent.
     */
    public int getIndexOfChild(Object parent, Object child) {
        TreeElement p = (TreeElement)parent;

        return p.getIndexOfChild(((TreeElement)child));
    }

    /**
     * Returns the root of the tree.
     */
    public Object getRoot() {
        return root;
    }

    /**
     * Returns true if node is a leaf.
     */
    public boolean isLeaf(Object node) {
        TreeElement p = (TreeElement)node;
        return p.getChildCount() == 0;
    }

    /**
     * Removes a listener previously added with addTreeModelListener().
     */
    public void removeTreeModelListener(TreeModelListener l) {
        treeModelListeners.removeElement(l);
    }

    /**
     * Messaged when the user has altered the value for the item
     * identified by path to newValue.  Not used by this model.
     */
    public void valueForPathChanged(TreePath path, Object newValue) {
        System.out.println("*** valueForPathChanged : "
                           + path + " --> " + newValue);
    }

//    public String test(){

  //  }
}

