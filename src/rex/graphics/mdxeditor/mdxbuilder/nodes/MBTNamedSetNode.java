package rex.graphics.mdxeditor.mdxbuilder.nodes;

import javax.swing.ImageIcon;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import rex.utils.LanguageChangedListener;
import rex.utils.*;

/**
 * Node representing named set. This node is created when a user drops a DimensionElement on the WITH node and picks
 * Set. This node can be dragged afterwards to other MdxBuilderTree nodes that accept it (e.g. MBTAxisNode, MBTArgSetNode).
 * @author Igor Mekterovic
 * @version 0.3
 */
public class MBTNamedSetNode 
        extends DefaultMBTAxisNode 
        implements LanguageChangedListener { 
   private static String childrenIndent = "";
   static ImageIcon icon;
   static {
      icon = S.getAppIcon("MBTNamedSetNode.gif");
   }

   public MBTNamedSetNode(String setName) {
      super(setName, false);
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
    * Returns an icon representing named set node.
    * @return ImageIcon
    */
   public ImageIcon getIcon(){return icon;}

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
         if (mdx.toString().trim().equals("")){
            mdx.append(this.getChild(i).getMdx(childrenIndent));
         }else{
            mdx.append( ", " + this.getChild(i).getMdx(childrenIndent));
         }
      }
      return indent + "SET [" + this.getName() + "]" + " AS '{" + mdx + "}'";
   }

   /**
    *
    * @return String
    */
   public String toString(){
      return "SET " + super.toString();
   }


   /**
    * Returns the name of the named set.
    * @return String
    */
   public String getName(){
      return super.toString();
   }

   /**
    *
    * @return String[]
    */
   public String[] getPopUpActionList(){
      return new String[]{MBTPopUpActions.REMOVE_NAMED_SET};
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
      if (action.equals(MBTPopUpActions.REMOVE_NAMED_SET)){
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
    MBTPopUpActions.popUpCaptions.put(MBTPopUpActions.REMOVE_NAMED_SET ,I18n.getString("menu.removeNamedSet"));
   }
  /* end of modification for I18n */

     }
   

