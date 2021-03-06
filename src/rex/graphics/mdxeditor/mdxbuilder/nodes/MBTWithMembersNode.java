package rex.graphics.mdxeditor.mdxbuilder.nodes;

import javax.swing.*;
import javax.swing.tree.*;

import rex.graphics.dimensiontree.elements.*;
import rex.graphics.mdxeditor.*;
import rex.graphics.mdxeditor.mdxbuilder.dnd.*;
import rex.utils.*;
import java.io.Serializable;
/**
 *
 * Node representing WITH node. Can have calculated members and named sets as children.
 * @author Igor Mekterovic
 * @version 0.3
 */
public class MBTWithMembersNode extends DefaultMBTAxisNode 
        implements Serializable, LanguageChangedListener{

   static ImageIcon icon;
   static {
      icon = S.getAppIcon("MBTWithMembersNode.gif");
   }
   private static String childrenIndent = "  ";

   public MBTWithMembersNode(  String _axisCaption) {
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
      String mdx = indent + "WITH ";
      for (i=0; i < this.getChildren().size(); i++){
         mdx += "\n" + indent + this.getChild(i).getMdx(indent + childrenIndent);
      }
      return mdx;
   }


   /**
    * Accepts only DimensionElements.
    */
   void setAcceptableFlavorsArray(){
      acceptableFlavorsMimeTypes = new String[] {TransferableMdxBuilderTreeNode.DIMENSION_TREE_NODE_DIMENSION_FLAVOR_STRING};
   }

   /**
    * Returns an icon representing WHERE node.
    * @return ImageIcon
    */
   public ImageIcon getIcon(){ return icon; }


   public String[] getPopUpActionList(){
      return new String[] {
               MBTPopUpActions.DELETE_CHILDREN
      };
   }


   /**
    * Displays a dialog for the user to choose weather to make a calculated member or named set.
    * <br>Accordingly, takes actions and created children nodes.
    * @see rex.graphics.mdxeditor.NewMemberOrSetDialog
    * @param droppedData Object
    * @param containerNode DefaultMutableTreeNode
    * @param treeModel DefaultTreeModel
    */
   public  void handleDrop(Object droppedData, DefaultMutableTreeNode containerNode, DefaultTreeModel treeModel){
      if (droppedData instanceof DimensionTreeElement){
         NewMemberOrSetDialog dialog = new NewMemberOrSetDialog(null);
         if (dialog.getName().equals("")) return; // if user cancels the action
         if (dialog.selected().equals(NewMemberOrSetDialog.MEMBERS_SELECTED)){
            MBTCalculatedMemberNode     calcMembNode  = new MBTCalculatedMemberNode(((DimensionTreeElement)droppedData).getUniqueName()
                                                                                    , dialog.getName());
            MBTArgAcceptAllStringNode   valExprNode   = new MBTArgAcceptAllStringNode("<value expression>");
//            MBTArgNumericNode           optIntNode    = new MBTArgNumericNode(false, "SOLVE ORDER", false, true);
            MBTCellPropertyNode            cellProperties = new MBTCellPropertyNode();

            this.addChild(calcMembNode);
            calcMembNode.addChild(valExprNode);
//            calcMembNode.addChild(optIntNode);
            calcMembNode.addChild(cellProperties);


            DefaultMutableTreeNode treeCalcMemberNode =  new DefaultMutableTreeNode(calcMembNode);

            treeModel.insertNodeInto(    treeCalcMemberNode
                                       , containerNode
                                       , containerNode.getChildCount());
            treeModel.insertNodeInto(     new DefaultMutableTreeNode(valExprNode)
                                       , treeCalcMemberNode
                                       , treeCalcMemberNode.getChildCount());
//            treeModel.insertNodeInto(    new DefaultMutableTreeNode(optIntNode)
//                                       , treeCalcMemberNode
//                                       , treeCalcMemberNode.getChildCount());
            treeModel.insertNodeInto(    new DefaultMutableTreeNode(cellProperties)
                                       , treeCalcMemberNode
                                       , treeCalcMemberNode.getChildCount());

         }else{
            MBTNamedSetNode     namedSetNode  = new MBTNamedSetNode( dialog.getName());


            this.addChild(namedSetNode);

            DefaultMutableTreeNode treeNamedSetNode =  new DefaultMutableTreeNode(namedSetNode);

            treeModel.insertNodeInto(    treeNamedSetNode
                                       , containerNode
                                       , containerNode.getChildCount());



         }

//         S.out("child count = " + containerNode.getChildCount());
      }
   }
   
   /**
    *   Copyright (C) 2006 CINCOM SYSTEMS, INC.
    *   All Rights Reserved
    *   Copyright (C) 2006 Igor Mekterovic
    *   All Rights Reserved
    */
   
   //By Prakash on 20 Sept 06
   public  void handleMemberDropFromQuery(Object droppedData, DefaultMutableTreeNode containerNode, DefaultTreeModel treeModel,String calculatedMemberName,String formula,String format){
    if (droppedData instanceof DimensionTreeElement){    
          MBTCalculatedMemberNode     calcMembNode  = new MBTCalculatedMemberNode(((DimensionTreeElement)droppedData).getUniqueName()
                                                                                  , calculatedMemberName);
          MBTArgAcceptAllStringNode   valExprNode   = new MBTArgAcceptAllStringNode("<value expression>");
          //By Prakash on 20 Sept 06
          valExprNode.setValue(formula);
//          MBTArgNumericNode           optIntNode    = new MBTArgNumericNode(false, "SOLVE ORDER", false, true);
          MBTCellPropertyNode            cellProperties = new MBTCellPropertyNode();
          //By Prakash on 20 Sept 06
          cellProperties.setCellProperty(format);
          this.addChild(calcMembNode);
          calcMembNode.addChild(valExprNode);
//          calcMembNode.addChild(optIntNode);
          calcMembNode.addChild(cellProperties);


          DefaultMutableTreeNode treeCalcMemberNode =  new DefaultMutableTreeNode(calcMembNode);

          treeModel.insertNodeInto(    treeCalcMemberNode
                                     , containerNode
                                     , containerNode.getChildCount());
          treeModel.insertNodeInto(     new DefaultMutableTreeNode(valExprNode)
                                     , treeCalcMemberNode
                                     , treeCalcMemberNode.getChildCount());
//          treeModel.insertNodeInto(    new DefaultMutableTreeNode(optIntNode)
//                                     , treeCalcMemberNode
//                                     , treeCalcMemberNode.getChildCount());
          treeModel.insertNodeInto(    new DefaultMutableTreeNode(cellProperties)
                                     , treeCalcMemberNode
                                     , treeCalcMemberNode.getChildCount());

       }

//       S.out("child count = " + containerNode.getChildCount());
    }
   
   /**
    *   Copyright (C) 2006 CINCOM SYSTEMS, INC.
    *   All Rights Reserved
    *   Copyright (C) 2006 Igor Mekterovic
    *   All Rights Reserved
    */
   
   //By Prakash on 27th Sept 06.
   public  DefaultMutableTreeNode handleSetDropFromQuery(DefaultMutableTreeNode containerNode, DefaultTreeModel treeModel,String calculatedMemberName)
   {
    MBTNamedSetNode     namedSetNode  = new MBTNamedSetNode( calculatedMemberName);


    this.addChild(namedSetNode);

    DefaultMutableTreeNode treeNamedSetNode =  new DefaultMutableTreeNode(namedSetNode);

    treeModel.insertNodeInto(    treeNamedSetNode
                               , containerNode
                               , containerNode.getChildCount());
    return treeNamedSetNode;
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

