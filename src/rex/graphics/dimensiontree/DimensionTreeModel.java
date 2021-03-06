package rex.graphics.dimensiontree;


import javax.swing.event.*;
import javax.swing.tree.*;
import java.util.Vector;


import rex.metadata.ServerMetadata;

import rex.utils.S;
import javax.swing.JOptionPane;
import rex.graphics.dimensiontree.elements.DimensionRootElement;
import rex.metadata.QueryElement;

import rex.graphics.dimensiontree.elements.DimensionTreeElement;
import rex.graphics.dimensiontree.elements.DimensionElement;
import rex.graphics.dimensiontree.elements.MeasureElement;
import rex.graphics.*;
import rex.xmla.XMLADiscoverRestrictions;
import rex.xmla.XMLADiscoverProperties;
import rex.utils.I18n;

import rex.metadata.resultelements.Member; 
/**
 * <p>Title: WHEX</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author igor
 * @version 1.0
 */



public class DimensionTreeModel implements TreeModel {

    private Vector treeModelListeners;
    private TreeElement root;
    private ServerMetadata smd;
    private XMLADiscoverRestrictions restrictions;
    private XMLADiscoverProperties   properties;
    private boolean flattenDimensions;
    private boolean withMembersLevel;
//  Members with more than MAX_CHILDREN_COUNT_DONT_ASK children don't get
//  their children populated initialy, but if later user expands this node
//  children will be:
//  A) added automatically if their estimated count is less than MAX_CHILDREN_COUNT_ASK
//  B) user will be asked weather he wants to expand this branch node since estimated children
//     count is bigger than MAX_CHILDREN_COUNT_ASK
// *Remeber: OLAP server estimates these counts, so sometimes they are off.
    protected static int MAX_CHILDREN_COUNT_ASK = 1001;
    protected static int MAX_CHILDREN_COUNT_DONT_ASK = 1;


    public DimensionTreeModel(XMLADiscoverRestrictions _restrictions
                           ,  XMLADiscoverProperties   _properties
                           ,  ServerMetadata _smd
                           ,  boolean _withMembersLevel) {

       flattenDimensions = true; // defualt value, should move it to some config file
       withMembersLevel = _withMembersLevel;

       root = new TreeElement(new DimensionRootElement(), null);
       ((DimensionRootElement)root.getUserObject()).setFlattenDimensions(flattenDimensions);
       restrictions = _restrictions;
       properties = _properties;
       smd = _smd;
       treeModelListeners = new Vector();

       buildTree();
    }

    public void buildTree(){
       int i;
       restrictions.setDimensionUniqueName(null);
       restrictions.setHierarchyUniqueName(null);
       DimensionTreeElement dims[] = smd.getDimensionList(restrictions, properties);
       if (dims != null){
          for (i = 0; dims != null && i < dims.length; i++) {
             ((DimensionElement) dims[i]).setFlattenDimensions(flattenDimensions); // setting the default state at creation
             TreeElement dimension = new TreeElement(dims[i], root);
             root.addChild(dimension);
             buildTreeRecursively(dimension);
          }
          fireTreeStructureChanged(root);
       }else{
        /**
          * Copyright (C) 2006 CINCOM SYSTEMS, INC.
          * All Rights Reserved
          * Copyright (C) 2006 Igor Mekterovic
          * All Rights Reserved
          */ 
          /* implementing localization */
           JOptionPane.showMessageDialog(null
                                        , I18n.getString("msgText.noDimensions")
                                        , I18n.getString("msgTitle.getDimensions")
                                        , JOptionPane.ERROR_MESSAGE);
             /* end of modification for I18n */

       }

    }
    private void buildTreeRecursively(TreeElement parent){
       int i;
       DimensionTreeElement[] dte;
       dte = ((DimensionTreeElement) parent.getUserObject()).getChildren(false);
/*  Gave up on this:
       if (   parent.getUserObject() instanceof DimensionElement
           && ((DimensionElement)parent.getUserObject()).isMeasureDimension()){
       // I'll handle Measures dimension separetly, because I wont to lose
       // that ugly "MesauresLevel" i.e.
       // Measures
       //   -MeasuresLevel
       //      -Count
       //      -Quantity
       // -->use this instead:
       // Measures
       //   -Count
       //   -Quantity
         dte = dte[0].getChildren(false);
      }
 */

      for (i = 0; dte != null && i < dte.length; i++) {
         TreeElement node = new TreeElement(dte[i], parent);
         parent.addChild(node);
         buildTreeRecursively(node);
      }
      
    }

//	Modified By Prakash. Protected to public.
    public void addChildrenOneLevel(TreeElement parent){
       int i;
//       S.out("DimensionTree -> adding children one level");
       DimensionTreeElement[] dte = ((DimensionTreeElement) parent.getUserObject()).getChildren(true);
       for (i = 0; dte != null && i < dte.length; i++) {
          TreeElement node = new TreeElement(dte[i], parent);
          parent.addChild(node);
       }
    }

    public boolean getFlattenDimensions(){
       return flattenDimensions;
    }
    public void setFlattenDimensions(boolean b){
       flattenDimensions = b;
       ((DimensionRootElement)root.getUserObject()).setFlattenDimensions(b);
       root.dropChildren();
       buildTree();
       fireTreeStructureChanged(root);
       /*
       // gotta notify:
       //  - root (to toggle popup menu list)
       //  - dimensions (to skip hierarchies and change name(toString()) )
       ((DimensionRootElement)root.getUserObject()).setFlattenDimensions(b);
       for(int i = 0; i < root.getChildCount(); i++){
          ((DimensionElement)root.getChildAt(i).getUserObject()).setFlattenDimensions(b);
       }
       fireTreeStructureChanged(root);
       // with this design I'm able to mix those modes
       // if I later choose to do so, i.e.
       // I'll be able to flatten only some dimensions
       */
    }
    private void enableAllTreeElementsRec(TreeElement parent){
       for(int i=0; i < parent.getChildCount(); i++){
          if ( parent.getChildAt(i).getUserObject() instanceof QueryElement ){
             ((DimensionTreeElement)parent.getChildAt(i).getUserObject()).setEnabled(true);
          }
          if (parent.getChildAt(i).getChildCount() > 0){
             enableAllTreeElementsRec(parent.getChildAt(i));
          }
       }
    }
    public void enableAllTreeElements(){
       enableAllTreeElementsRec(root);
    }
    public void disableTreeElements(QueryElement qe){
        if (qe instanceof MeasureElement)
          toggleMeasureEnabled(root, ((MeasureElement)qe).getUniqueName(), false);
       else
          toggleTreeElementsEnabled(root, qe.getHierarchyUniqueName(), false);
    }
    public void enableTreeElements(QueryElement qe){
       System.out.println(qe.getHierarchyUniqueName().toLowerCase().indexOf("measures"));       
       /**
       * Not specified anywhere that qe is instanceof MeasureElement so commented. 
       */
       //if (qe instanceof MeasureElement)
       /**
       *   Copyright (C) 2006 CINCOM SYSTEMS, INC.
       *   All Rights Reserved
       *   Copyright (C) 2006 Igor Mekterovic
       *   All Rights Reserved
       */
       /**
       * Checking for measures. 
       */
           
       if (qe.getHierarchyUniqueName().toLowerCase().indexOf("measures") > 0)
          toggleMeasureEnabled(root, ((Member)qe).getUniqueName(), true);
       else
          toggleTreeElementsEnabled(root, qe.getHierarchyUniqueName(), true);
    }
    private void toggleTreeElementsEnabled( TreeElement parent
                                          , String hierarchyUniqueName
                                          , boolean newEnabledValue ){
       for(int i=0; i < parent.getChildCount(); i++){
          // not all elements are QueryElements (root, dimension, hierarchy)
//          S.out("Checking " + parent.getChildAt(i).getUserObject()
//                + " for "
//                + hierarchyUniqueName );
          if ( parent.getChildAt(i).getUserObject() instanceof QueryElement ){
             if (((QueryElement)parent.getChildAt(i).getUserObject()).getHierarchyUniqueName().equals(hierarchyUniqueName)){
                 System.out.println(((QueryElement)parent.getChildAt(i).getUserObject()).getHierarchyUniqueName()+" :By Prakash");
                ((DimensionTreeElement)parent.getChildAt(i).getUserObject()).setEnabled(newEnabledValue);
             }
          }
          if (parent.getChildAt(i).getChildCount() > 0){
             toggleTreeElementsEnabled(parent.getChildAt(i), hierarchyUniqueName, newEnabledValue);
          }
       }
    }
    private int toggleMeasureEnabled( TreeElement parent
                                          , String uniqueName
                                          , boolean newEnabledValue ){
       int foundIt = 0;
       for(int i=0; i < parent.getChildCount(); i++){
          if ( parent.getChildAt(i).getUserObject() instanceof MeasureElement ){
             if (((MeasureElement)parent.getChildAt(i).getUserObject()).getUniqueName().equals(uniqueName)){
                 System.out.println(((MeasureElement)parent.getChildAt(i).getUserObject()).getUniqueName()+" :By Prakash");
                ((DimensionTreeElement)parent.getChildAt(i).getUserObject()).setEnabled(newEnabledValue);
                return 1;
             }
          }
          if (parent.getChildAt(i).getChildCount() > 0){
             foundIt += toggleMeasureEnabled(parent.getChildAt(i), uniqueName, newEnabledValue);
          }
          if (foundIt > 0) return foundIt;
       }
       return foundIt;
    }
    public DimensionTreeElement getDimensionTreeElement(String uniqueName){
       return getDimensionTreeElementRec(root, uniqueName);
    }

    private DimensionTreeElement getDimensionTreeElementRec(TreeElement parent, String uniqueName){
       //S.out("searching for..." + uniqueName +"Got: "+((DimensionTreeElement)parent.getUserObject()).getUniqueName());
       //S.out("getDimensionElementRec:parent=" + parent
       //      + " class=" + parent.getUserObject().getClass()
       //      + " parent.uname = " + ((DimensionTreeElement)parent.getUserObject()).getUniqueName());
       if ((((DimensionTreeElement)parent.getUserObject()).getUniqueName()).startsWith(uniqueName)){
          S.out("RETURNING:" + parent.getUserObject());
          return (DimensionTreeElement)parent.getUserObject();
       }else{
          DimensionTreeElement retVal;
          for(int i=0; i<parent.getChildCount(); i++){
              //S.out(i+" "+((DimensionTreeElement)parent.getUserObject()).getUniqueName());
             retVal = getDimensionTreeElementRec(parent.getChildAt(i), uniqueName);
             if (retVal != null) 
                 {
                 return retVal;
                 }
          }
       }
       return null;
    }

    
    /*
     * By Prakash. 12th Feb 2007.
     * Searching Member through TreeElement
     */
    
    public TreeElement getTreeElement(String uniqueName){
        return getTreeElementRec(root, uniqueName);
     }

     private TreeElement getTreeElementRec(TreeElement parent, String uniqueName){
        //S.out("searching for..." + uniqueName +"Got: "+((DimensionTreeElement)parent.getUserObject()).getUniqueName());
        //S.out("getDimensionElementRec:parent=" + parent
        //      + " class=" + parent.getUserObject().getClass()
        //      + " parent.uname = " + ((DimensionTreeElement)parent.getUserObject()).getUniqueName());
        if ((((DimensionTreeElement)parent.getUserObject()).getUniqueName()).startsWith(uniqueName)){
           S.out("RETURNING:" + parent);
           return parent;
        }else{
           TreeElement retVal;
           for(int i=0; i<parent.getChildCount(); i++){
               //S.out(i+" "+((DimensionTreeElement)parent.getUserObject()).getUniqueName());
              retVal = getTreeElementRec(parent.getChildAt(i), uniqueName);
              if (retVal != null) 
                  {
                  return retVal;
                  }
           }
        }
        return null;
     }
    //End of the Edition By Prakash
    

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
            ((TreeModelListener)treeModelListeners.elementAt(i)).treeStructureChanged(e);
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
        if (withMembersLevel){
//          Here, I'm actually asking the user object for the child count
//          User object returns the child count that is parsed from the XML describing the level
//          - it hasn't actually retrieved the children and counted them.
            DimensionTreeElement dte = (DimensionTreeElement)((TreeElement)node).getUserObject();
            return (dte.getChildrenCount() == 0);
        }else{
            TreeElement p = (TreeElement)node;
//            S.out("DimensionTree isLeaf for " + p + " returning " + (p.getChildCount() == 0));
            return p.getChildCount() == 0;
        }

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
}

