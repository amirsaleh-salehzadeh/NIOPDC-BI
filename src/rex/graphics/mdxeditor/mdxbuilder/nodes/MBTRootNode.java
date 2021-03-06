package rex.graphics.mdxeditor.mdxbuilder.nodes;

import javax.swing.tree.DefaultMutableTreeNode;
import rex.utils.S;
import javax.swing.ImageIcon;
import javax.swing.tree.DefaultTreeModel;
import java.io.Serializable;
import rex.utils.I18n;
/**
 * MdxBuilderTree root node.This node hold all axis nodes and WITH, FROM i WHERE nodes.
 * <br>Only this node generates complete MDX expression, because it is the highest, root node.
 * @author Igor Mekterovic
 * @version 0.3
 */
public class MBTRootNode extends DefaultMBTNode implements Serializable{
   private static String childrenIndent = "  ";
   static ImageIcon icon;
   static {
        icon = S.getAppIcon("MBTRootNode.gif");
   }

   public MBTRootNode() {
      super();
   }
   /**
    * Returns the complete MdxBuilderTree MDX statement.
    * @param indent String
    * @return String
    */
   public String getMdx(String indent){
      StringBuffer mdx = new StringBuffer("");
      for (int i = 1; i< 4; i++){
         if (   mdx.toString().trim().length()>0
             && this.getChild(i).getMdx("").trim().length()>0)
            mdx.append(",\n\n" + this.getChild(i).getMdx(indent + childrenIndent));
         else
            mdx.append("\n" + this.getChild(i).getMdx(indent + childrenIndent));
      }
      return this.getChild(0).getMdx(indent)
             + "\n" + indent + "SELECT "
             +  mdx.toString()
             + "\n" + this.getChild(4).getMdx(indent)
             + "\n" + this.getChild(5).getMdx(indent);

   }

   /**
    * returns <code>"Mdx Builder Tree"</code>
    * @return String
    */
   public  String toString(){ 
       /**
  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
  * All Rights Reserved
  * Copyright (C) 2006 Igor Mekterovic
  * All Rights Reserved
  */ 
/* implementing localization */
       return I18n.getString("str.mdxBuilderTree");
         /* end of modification for I18n */

   }

   /**
    * Accepts no-one: always returns false;
    * @param droppedData MBTNode
    * @return boolean
    */
   public  boolean acceptDrop(MBTNode droppedData){return false;}

   /**
    *
    * @param droppedData Object
    * @param containerNode DefaultMutableTreeNode
    * @param treeModel DefaultTreeModel
    */
   public  void handleDrop(  Object droppedData
                           , DefaultMutableTreeNode containerNode
                           , DefaultTreeModel treeModel){}

   /**
    * Accepts no-one.
    */
   void setAcceptableFlavorsArray(){}

   /**
    * Returns an icon representing root node.
    * @return ImageIcon
    */
   public ImageIcon getIcon(){ return icon; }

   /**
    *
    * @return String[]
    */
   public String[] getPopUpActionList(){
      return null;
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
   }



}
