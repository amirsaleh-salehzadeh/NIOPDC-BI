package rex.graphics.mdxeditor.mdxbuilder.nodes;

import javax.swing.*;

import rex.graphics.mdxeditor.mdxbuilder.dnd.*;
import rex.utils.*;
import java.io.Serializable;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import rex.graphics.mdxeditor.mdxfunctions.MdxFunction;

/**
 * Node representing WHERE axis.
 * @author Igor Mekterovic
 * @version 0.3
 */
public class MBTWhereNode extends DefaultMBTAxisNode 
        implements Serializable, LanguageChangedListener{
   private static String childrenIndent = "  ";
   static ImageIcon icon;
   static {
      icon = S.getAppIcon("MBTWhereNode.gif");
   }


   public MBTWhereNode(  String _axisCaption) {
      super(_axisCaption, false);
        /**
          * Copyright (C) 2006 CINCOM SYSTEMS, INC.
          * All Rights Reserved
          * Copyright (C) 2006 Igor Mekterovic
          * All Rights Reserved
          */ 
        /*adding this class to the list of classes that implement I18n */
 
      
      I18n.addOnLanguageChangedListener(this);
        /* end of modification for I18n */

   }

   /**
    *
    * @param indent String
    * @return String
    */
   public  String getMdx(String indent){
      if (this.getChildren() == null) return "";
      int i;
      StringBuffer mdx = new StringBuffer("");
      for (i=0; i < this.getChildren().size(); i++){
         if (mdx.toString().trim().length() == 0)
            mdx.append("\n" + indent + "  " + this.getChild(i).getMdx(childrenIndent));
         else
            mdx.append("\n" + indent + ", " + this.getChild(i).getMdx(childrenIndent));
      }
      return indent + "WHERE ("
           + "\n" + indent + mdx.toString()
           + "\n" + indent + "      )";
   }

   /**
    * Returns an icon representing WHERE node.
    * @return ImageIcon
    */
   public ImageIcon getIcon(){ return icon; }

   /**
    *
    */
   void setAcceptableFlavorsArray(){
      acceptableFlavorsMimeTypes = new String[] {
                                     TransferableMdxBuilderTreeNode.DIMENSION_TREE_NODE_MEASURE_FLAVOR_STRING
                                   , TransferableMdxBuilderTreeNode.DIMENSION_TREE_NODE_MEMBER_FLAVOR_STRING
                                   , TransferableMdxBuilderTreeNode.MDX_BUILDER_TREE_CALCULATED_MEMBER_NODE_FLAVOR_STRING
                                   , TransferableMdxBuilderTreeNode.MDX_BUILDER_TREE_NAMED_SET_NODE_FLAVOR_STRING
                                   , TransferableMdxBuilderTreeNode.MDX_BUILDER_TREE_TUPLE_FUNCTION_NODE_FLAVOR_STRING
      };
   }

   public String[] getPopUpActionList(){
      return new String[] {
           MBTPopUpActions.DELETE_CHILDREN
      };
   }

   /**
    * Handles dropped node.
    * @param droppedData Object
    * @param containerNode DefaultMutableTreeNode
    * @param treeModel DefaultTreeModel
    */
   public  void handleDrop(  Object droppedData
                           , DefaultMutableTreeNode containerNode
                           , DefaultTreeModel treeModel){
      if (droppedData instanceof MdxFunction) {

         this.addMdxFunctionChild( ((MdxFunction)droppedData)
                                             , containerNode
                                             , treeModel);
      } else if (droppedData instanceof MBTFunctionNode){

         this.moveMBTFunctionNode( ((MBTFunctionNode)droppedData)
                                             , containerNode
                                             , treeModel);

      } else {
         try{
            this.addChildAndMBTMembersNode(droppedData
                                           , containerNode
                                           , treeModel);
         }catch(Exception e){
            //e.printStackTrace();//Commented by Prakash
         }

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
    MBTPopUpActions.popUpCaptions.put(MBTPopUpActions.DELETE_CHILDREN ,I18n.getString("menu.deleteChildren"));
}
    /* end of modification for I18n */

}
