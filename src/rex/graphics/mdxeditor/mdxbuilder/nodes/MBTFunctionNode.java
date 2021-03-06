package rex.graphics.mdxeditor.mdxbuilder.nodes;

import javax.swing.*;
import javax.swing.tree.*;

import rex.utils.*;

/**
 *
 * Node representing MdxFunction. This node remember mimy-type (return value) of the MdxFunction that
*  was used in creation of this object (this node is created when MdxFunction is dropped to MdxBuilderTree)
* so that later on this node can be properly dragged and dropped within MdxBuilderTree.
 * @author Igor Mekterovic
 * @version 0.3
 */
public class MBTFunctionNode 
        extends DefaultMBTNode
        implements LanguageChangedListener{

   private String functionName, toolTip, flavor;

   private static String childrenIndent = "  ";
   static ImageIcon icon;
   static {
      icon = S.getAppIcon("mdxfunction.gif");
   }
   /**
    *
    * @param _functionName String
    * @param desc String
    * @param syntax String
    * @param _flavor return value of this function.
    */
   public MBTFunctionNode(  String _functionName, String desc, String syntax, String _flavor ) {
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

      functionName = _functionName;
      flavor = _flavor;
      toolTip = "<html><b>FUNCTION: </b>" + functionName
              + "<hr>"
              + "<b>Desc: </b><i>" + desc + "</i>"
              + "<hr>"
              + "<b>Syntax: </b>" + syntax;
   }

   /**
    * Returns true if all non-optional arguments are specified, false otherwise.
    * @return boolean
    */
   public boolean isComplete(){
      if (this.getChildren() == null)
         return true;
      DefaultMBTArgNode child;
      for (int i=0; i < this.getChildren().size(); i++){
         child = (DefaultMBTArgNode) this.getChild(i);
         if ( !child.isOptionalArg()
            && child.getMdx("").trim().equals("") )
           return false;
      }
      return true;
   }

   /**
    * Returns this function's data flavor string (representing return value)
    * @see rex.graphics.mdxeditor.mdxbuilder.dnd.TransferableMdxBuilderTreeNode
    * @return String
    */
   public String getFlavor(){
      return flavor;
   }

   /**
    *
    * @param indent String
    * @return String
    */
   public  String getMdx(String indent){
      boolean functionNameInserted = false
             , bracketOpen = false
             , firstArgument = true;
      if (this.getChildren() == null) // e.g. UserName function
         return functionName;
      int i;
      StringBuffer mdx = new StringBuffer("");
      DefaultMBTArgNode child;
      for (i=0; i < this.getChildren().size(); i++){
         child = (DefaultMBTArgNode) this.getChild(i);
         if (child.isHeadArg()){
//          There can be only one head argument per function
            mdx.append(indent + this.getChild(i).getMdx("") + "." + functionName);
            functionNameInserted = true;
         } else {
            if (!functionNameInserted){
               mdx.append(indent + functionName + "(");
               bracketOpen = true;
               functionNameInserted = true;
            }
            if (!bracketOpen){
               mdx.append("(");
               bracketOpen = true;
            }
//            skip the optional argument if it is not set
            if ( !((DefaultMBTArgNode)this.getChild(i)).isOptionalArg()
                 || !this.getChild(i).getMdx("").trim().equals("")){
               if (firstArgument){
                  mdx.append("\n" + this.getChild(i).getMdx(indent + childrenIndent));
                  firstArgument = false;
               }else{
                  mdx.append("\n" + addCommaAfterBlanks(this.getChild(i).getMdx(indent + childrenIndent)));
               }
            }
         }

      }
      if (bracketOpen){
         mdx.append("\n" + indent + ")");
      }
      return mdx.toString();

   }

   /**
    *
    * @return String
    */
   public  String toString(){
      return functionName;
   }


   /**
    * Returns function name
    * @return String
    */
   public  String getFunctionName(){
      return functionName;
   }

   /**
    *
    * @return String
    */
   public String getToolTip(){
      return toolTip;
   }

   /**
    * This node accepts no-one.
    */
   void setAcceptableFlavorsArray(){
//   accept nonone
   }

   /**
    * Returns icon representing mdx function.
    * @return ImageIcon
    */
   public ImageIcon getIcon(){ return icon; }


   /**
    *
    * @param droppedData Object
    * @param containerNode DefaultMutableTreeNode
    * @param treeModel DefaultTreeModel
    */
   public  void handleDrop(  Object droppedData
                           , DefaultMutableTreeNode containerNode
                           , DefaultTreeModel treeModel){
//  there are no drops, function accepts no one

   }


   /**
    *
    * @return String[]
    */
   public String[] getPopUpActionList(){
      return new String[]{MBTPopUpActions.REMOVE};
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
    MBTPopUpActions.popUpCaptions.put(MBTPopUpActions.REMOVE ,I18n.getString("menu.remove"));
}
  /* end of modification for I18n */
}
