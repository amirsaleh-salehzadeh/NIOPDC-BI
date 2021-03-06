package rex.graphics.filtertree;


import javax.swing.event.*;
import javax.swing.tree.*;
import java.util.Vector;

import rex.graphics.dimensiontree.*;
import rex.metadata.ServerMetadata;


import javax.swing.JOptionPane;
import java.util.LinkedList;
import java.util.ListIterator;
import rex.graphics.*;
import rex.graphics.dimensiontree.elements.*;
import rex.metadata.UniqueNameElement;
import rex.metadata.QueryElement;
import rex.graphics.filtertree.elements.FilterTreeRootElement;
import rex.utils.I18n;
/**
 * <p>Title: WHEX</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author igor
 * @version 1.0
 */



public class FilterTreeModel implements TreeModel  {

    private Vector treeModelListeners;
    private TreeElement root;
    private ServerMetadata smd;

    private boolean flattenDimensions;
//  Members with more than MAX_CHILDREN_COUNT_DONT_ASK children don't get
//  their children populated initialy, but if later user expands this node
//  children will be:
//  A) added automatically if their estimated count is less than MAX_CHILDREN_COUNT_ASK
//  B) user will be asked weather he wants to expand this branch node since estimated children
//     count is bigger than MAX_CHILDREN_COUNT_ASK
// *Remeber: OLAP server estimates these counts, so sometimes they are off.
    protected static int MAX_CHILDREN_COUNT_ASK = 1001;
    protected static int MAX_CHILDREN_COUNT_DONT_ASK = 1;

    private LinkedList enabledMembersList;
    private LinkedList disabledMembersList;
    private DimensionTreeElement filterDimension;

    public FilterTreeModel(   ServerMetadata                  _smd
                           ,  DimensionTreeElement            _filterDimension
                           ,  LinkedList                      _enabledMembersList) {

       flattenDimensions = true; // defualt value, should move it to some config file

       root = new TreeElement(new FilterTreeRootElement(_filterDimension), null);
       ((FilterTreeRootElement)root.getUserObject()).setFlattenDimensions(flattenDimensions);
//       restrictions = _restrictions;
//       properties = _properties;
       smd = _smd;
       enabledMembersList = _enabledMembersList;
       disabledMembersList = new LinkedList();

       filterDimension = _filterDimension;

       treeModelListeners = new Vector();

       buildTree();
    }

    public void buildTree(){
       int i;
       DimensionTreeElement levhier[] = ((FilterTreeRootElement)root.getUserObject()).getChildren(false);
       if (levhier != null){
          for (i = 0; levhier != null && i < levhier.length; i++) {
//             S.out("levhier[" + i + "] = " + levhier[i] + " class = " + levhier[i].getClass().getName());
             //((DimensionElement) levhier[i]).setFlattenDimensions(flattenDimensions); // setting the default state at creation
             TreeElement lh = new TreeElement(levhier[i], root);
             root.addChild(lh);
             buildTreeRecursively(lh);
          }
          setEnabledFlagsRec(root);
          fireTreeStructureChanged(root);
       }else{
     /**
      * Copyright (C) 2006 CINCOM SYSTEMS, INC.
      * All Rights Reserved
      * Copyright (C) 2006 Igor Mekterovic
      * All Rights Reserved
      */ 
    /* implementing localization */
           JOptionPane.showMessageDialog(  null
                                        , I18n.getString("msgText.noDimensions")
                                        , I18n.getString("msgTitle.getDimensions")
                                        , JOptionPane.ERROR_MESSAGE);
     /* end of modification for I18n */


       }

    }
    private void buildTreeRecursively(TreeElement parent){
       int i;
       DimensionTreeElement[] dte;
       DimensionTreeElement current = ((DimensionTreeElement) parent.getUserObject());
//       S.out("current.getChildrenCount()=" + current.getChildrenCount() + " current = " + current);
       if (current.getChildrenCount() < this.MAX_CHILDREN_COUNT_DONT_ASK){
          dte = current.getChildren(false);
          for (i = 0; dte != null && i < dte.length; i++) {
             TreeElement node = new TreeElement(dte[i], parent);
             parent.addChild(node);
             if (dte[i].getChildrenCount() > 0 &&
                 dte[i].getChildrenCount() < this.MAX_CHILDREN_COUNT_DONT_ASK) {
                buildTreeRecursively(node);
             }
          }
       }else{
//          if (current.getChildrenCount() > 0){
//             S.out("too much...");
//          }
       }
    }

    protected void addChildrenOneLevel(TreeElement parent){
       int i;
//       S.out("add children one level CLASS = " + parent.getUserObject().getClass().getName());
       DimensionTreeElement[] dte = ((DimensionTreeElement) parent.getUserObject()).getChildren(true);
       for (i = 0; dte != null && i < dte.length; i++) {
          TreeElement node = new TreeElement(dte[i], parent);
          parent.addChild(node);
       }
       setEnabledFlagsRec(parent);
    }
    private void setEnabledFlagsRec(TreeElement parent){
       DimensionTreeElement dte = (DimensionTreeElement)parent.getUserObject();
//       S.out("setting " + dte);
       dte.setEnabled(isEnabled((UniqueNameElement)dte));
       for(int i=0; i < parent.getChildCount(); i++){
          setEnabledFlagsRec(parent.getChildAt(i));
       }
    }
    private boolean isEnabled(UniqueNameElement une){
       if (enabledMembersList.size()>0){
          ListIterator it = enabledMembersList.listIterator();
          while(it.hasNext()){
             if (((UniqueNameElement)it.next()).getUniqueName().equals(une.getUniqueName())){
                return true;
             }
          }
          return false;
       }else{
          ListIterator it = disabledMembersList.listIterator();
          while(it.hasNext()){
             if (((UniqueNameElement)it.next()).getUniqueName().equals(une.getUniqueName())){
                return false;
             }
          }
          return true;
       }
    }

    protected String getEnabledMembersCaption(){
       StringBuffer retVal = new StringBuffer("<html>Enabled members are");
       ListIterator it;
       if (enabledMembersList.size()==0){
          retVal.append(" <b>ALL</b> but these members:");
          it = disabledMembersList.listIterator();
       }else{
          retVal.append(":");
          it = enabledMembersList.listIterator();
       }

       while (it.hasNext()) {
          retVal.append( "<br><b>" + ((UniqueNameElement) it.next()).getUniqueName() + "</b>");
       }

       retVal.append("</html>");
//       S.out("getQueryWithMemberExpression = " + getQueryWithMemberExpression());
       return retVal.toString();
    }
    protected void enableMember(DimensionTreeElement dte){
       dte.setEnabled(true);
       if (enabledMembersList.size()>0){
          enabledMembersList.add(dte);
       }else{
          removeFromList((UniqueNameElement)dte, disabledMembersList);
       }
    }
    protected void disableMember(DimensionTreeElement dte){
       dte.setEnabled(false);
       if (enabledMembersList.size()>0){
          removeFromList((UniqueNameElement)dte, enabledMembersList);
       }else{
          disabledMembersList.add(dte);
       }
    }
    private void removeFromList(UniqueNameElement une, LinkedList ll){
       ListIterator it = ll.listIterator();
       while(it.hasNext()){
          if (((UniqueNameElement)it.next()).getUniqueName().equals(une.getUniqueName())){
             it.previous();
             it.remove();
          }
       }
    }
    protected void setAllButThisMember(UniqueNameElement une){
       enabledMembersList.clear();
       disabledMembersList.clear();
       disabledMembersList.add(une);
       setEnabledFlagsRec(root);
    }
    protected void setOnlyThisMember(UniqueNameElement une){
       enabledMembersList.clear();
       enabledMembersList.add(une);
       setEnabledFlagsRec(root);
    }
    public boolean isFilttering(){
    // If a user turns everything on/off
       if (enabledMembersList.size()==0 && disabledMembersList.size()==0){
          return false;
       }else{
          return true;
       }

    }
    public boolean isDisplayedInTab(){
       return ((FilterTreeRootElement)root.getUserObject()).isDisplayInTab();
    }
    public String getCaption(){
       return ( (FilterTreeRootElement) root.getUserObject()).toString();
    }

    public QueryElement getAnyQueryElement(){
       //Query class needs any QueryElement so that it can enable
       // dim.tree members once filter has been dropped
       // Since LevelElement implements QueryElement, I'll return
       // the first level of my filter
       if (root.getChildCount()>0){
          return (LevelElement)root.getChildAt(0).getUserObject();
       }else{
          return null;
       }
    }

    public String getQueryWithMemberExpression(){
       StringBuffer expr = new StringBuffer("\n   MEMBER ");
       ListIterator it;
       expr.append(getAggregateMemberName() + "\n    AS 'Aggregate(");
       if (enabledMembersList.size()==0){
          it = disabledMembersList.listIterator();
          expr.append("\n       Except(" + filterDimension.getUniqueName() + ".Members, ");
       }else{
          it = enabledMembersList.listIterator();
       }
       expr.append("\n          {");
       while(it.hasNext()){
          if (it.hasPrevious()){
             expr.append("\n          , " +
                         ((UniqueNameElement) it.next()).getUniqueName());
          }else{
             expr.append("\n            " +
                         ((UniqueNameElement) it.next()).getUniqueName());
          }
       }
       expr.append("\n          }");
       if (enabledMembersList.size()==0){
          expr.append("\n       )");
       }
       expr.append("\n      )'");
       return expr.toString();
    }
    private String getAggregateMemberName(){
       String uName;
       if (filterDimension instanceof LevelElement){
          uName = ((LevelElement)filterDimension).getHierarchyUniqueName();
       }else if(filterDimension instanceof HierarchyElement){
          uName = ((HierarchyElement)filterDimension).getHierarchyUniqueName();
       }else { //if(filterDimension instanceof DimensionElement)
          uName = filterDimension.getDimensionUniqueName();
       }
       return uName
              + ".[Filter members from "
              + uName.replace('[', '(').replace(']', ')')
              + "]";
    }
    public String getQueryWhereExpression(){
       return getAggregateMemberName();
    }

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
       DimensionTreeElement dte = (DimensionTreeElement)((TreeElement)node).getUserObject();
       return (dte.getChildrenCount() == 0);

//        return dte.getChildrenCount() == 0;
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
//        System.out.println("*** valueForPathChanged : "
//                           + path + " --> " + newValue);
    }
}

