package rex.graphics.mdxeditor.mdxbuilder.nodes;

import javax.swing.ImageIcon;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import rex.utils.*;

/**
 * This is a node that hold a constant MDX expression. Unlike MBTMembers node, this node doesn't append
* some member function to it's content (like Members or AllMembers).
 * @author Igor Mekterovic
 * @version 0.3
 */
public class MBTImmutableMembersNode extends DefaultMBTNode
    implements LanguageChangedListener{

   private String membersMdxExpression;

   static ImageIcon icon;
   static {
      icon = S.getAppIcon("MBTMembersNode.gif");
   }

   /**
    *
    * @param _membersMdxExpression String
    */
   public MBTImmutableMembersNode(String _membersMdxExpression, String dimensionUniqueName) {
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

      membersMdxExpression = _membersMdxExpression;
   }

   /**
    *
    * @param indent String
    * @return String
    */
   public  String getMdx(String indent){
      return indent + membersMdxExpression;
   }

   /**
    *
    * @return String
    */
   public  String toString(){
      return membersMdxExpression;
   }

   /**
    *
    */
   void setAcceptableFlavorsArray(){
//    I'm not accepting anyone
   }

   public ImageIcon getIcon(){ return icon; }

   /**
    *
    * @return String[]
    */
   public String[] getPopUpActionList(){
      return new String[] {MBTPopUpActions.REMOVE};
   }

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
    * @param action String
    * @param actionNode DefaultMutableTreeNode
    * @param treeModel DefaultTreeModel
    */
   public void handlePopUpAction(  String action
                                 , DefaultMutableTreeNode actionNode
                                 , DefaultTreeModel treeModel){

      if (action.equals(MBTPopUpActions.REMOVE)){
         super.removeMySelfFromTheParent(actionNode, treeModel);
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
    MBTPopUpActions.popUpCaptions.put(MBTPopUpActions.REMOVE ,I18n.getString("menu.remove"));
}  /* end of modification for I18n */


}
