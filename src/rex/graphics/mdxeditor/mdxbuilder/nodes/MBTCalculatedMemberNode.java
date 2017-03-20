package rex.graphics.mdxeditor.mdxbuilder.nodes;

import javax.swing.ImageIcon;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import rex.utils.*;
/**
 * Node representing Calculated member. This is created when a DimensionElement is dropped on the WITH node.
* This node can be dragged later to other nodes.
 * @author Igor Mekterovic
 * @version 0.3
 */
public class MBTCalculatedMemberNode extends DefaultMBTNode
    implements LanguageChangedListener{
   private String parent, name;
   private static String childrenIndent = "";
   static ImageIcon icon;
   static {
        icon = S.getAppIcon("MBTCalculatedMemberNode.gif");
   }


   public MBTCalculatedMemberNode(  String _parent, String _name) {
      super();
      /**
      * Copyright (C) 2006 CINCOM SYSTEMS, INC.
      * All Rights Reserved
      * Copyright (C) 2006 Igor Mekterovic
      * All Rights Reserved
      */ 
    /*adding this class to the list of classes that implement I18n */

      I18n.addOnLanguageChangedListener(this);
        /* end of modification for I18n */

      parent = _parent;
      name = _name;
   }

   /**
    * Returns an icon for the calulated memeber.
    * @return ImageIcon
    */
   public ImageIcon getIcon(){ return icon; }

   /**
    *
    * @param indent String
    * @return String
    */
   public  String getMdx(String indent){
      if (this.getChildren() == null) return "";
      int i;
      StringBuffer mdx = new StringBuffer(indent + "'" + this.getChild(0).getMdx("") + "'");
//    the size is, in fact, always 2
      for (i=1; i < this.getChildren().size(); i++){
         if (this.getChild(i).getMdx("").trim().length()>0)
            mdx.append(indent + ", " + this.getChild(i).getMdx(""));
      }
      return indent + "MEMBER " + parent + ".[" + name + "]" + " AS " + mdx;
   }

   /**
    *
    * @return String
    */
   public  String toString(){
      return "MEMBER " + parent + ".[" + name + "]";
   }

   /**
    * Returns calculated member name. User defines the name upon creation.
    * @return String
    */
   public String getName() {
      return parent + ".[" + name + "]";
   }


   public String getDimensionUniqueName(){
      return parent;
   }

   /**
    * This node accepts no-one.
    */
   void setAcceptableFlavorsArray(){}


   /**
    *
    * @param droppedData Object
    * @param containerNode DefaultMutableTreeNode
    * @param treeModel DefaultTreeModel
    */
   public  void handleDrop( Object droppedData
                           , DefaultMutableTreeNode containerNode
                           , DefaultTreeModel treeModel){
   }

   /**
    *
    * @return String[]
    */
   public String[] getPopUpActionList(){
      return new String[]{MBTPopUpActions.REMOVE_MEMBER};
   }


   /**
    *
    * @param action String
    * @param actionNode DefaultMutableTreeNode
    * @param treeModel DefaultTreeModel
    */
   public void handlePopUpAction(  String action
                                 , DefaultMutableTreeNode actionNode
                                 , DefaultTreeModel treeModel){
      if (action.equals(MBTPopUpActions.REMOVE_MEMBER)){
//       remove myself from the parent:
         removeMySelfFromTheParent(actionNode, treeModel);
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
    MBTPopUpActions.popUpCaptions.put(MBTPopUpActions.DELETE_CHILDREN ,I18n.getString("menu.removeMem"));
    }
    /* end of modification for I18n */

}
