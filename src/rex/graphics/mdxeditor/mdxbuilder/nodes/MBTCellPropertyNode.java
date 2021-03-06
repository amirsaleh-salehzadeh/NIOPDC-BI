package rex.graphics.mdxeditor.mdxbuilder.nodes;

import javax.swing.ImageIcon;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import rex.graphics.mdxeditor.CellPropertiesDialog;
import rex.utils.*;

public class MBTCellPropertyNode 
        extends DefaultMBTNode
        implements LanguageChangedListener{
   public MBTCellPropertyNode() {
     /**
      * Copyright (C) 2006 CINCOM SYSTEMS, INC.
      * All Rights Reserved
      * Copyright (C) 2006 Igor Mekterovic
      * All Rights Reserved
      */ 
    /*adding this class to the list of classes that implement I18n */

       I18n.addOnLanguageChangedListener(this);
         /* end of modification for I18n */

       value = "";
   }

   private String value;

   static ImageIcon icon;
   static {
      icon = S.getAppIcon("MBTCellPropertyNode.gif");
   }

   /**
    *
    * @param indent String
    * @return String
    */
   public  String getMdx(String indent){
      return indent + value;
   }

   /**
    *
    * @return String
    */
   public  String toString(){
      if (value!=null && !value.trim().equals(""))
         return value;
      else{
          /**
          * Copyright (C) 2006 CINCOM SYSTEMS, INC.
          * All Rights Reserved
          * Copyright (C) 2006 Igor Mekterovic
          * All Rights Reserved
          */ 
        /* implementing localization */
         return "<html><i>["+ I18n.getString("str.cellProperty") +"]</i></html>";
           /* end of modification for I18n */

      }
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
    * @param droppedData Object
    * @param containerNode DefaultMutableTreeNode
    * @param treeModel DefaultTreeModel
    */
   public  void handleDrop( Object droppedData
                           , DefaultMutableTreeNode containerNode
                           , DefaultTreeModel treeModel){
   }




   /**
    * Displays a dialog to edit current string expression.
    */
   void editValue(){
      CellPropertiesDialog dialog = new CellPropertiesDialog(null);
      value = dialog.getFormat();
      dialog.dispose();
   }

   /**
    *
    * @return String[]
    */
   public String[] getPopUpActionList(){

      return new String[]{
                        MBTPopUpActions.CLEAR_VALUE
                      , MBTPopUpActions.EDIT
                   };

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

      if (action.equals(MBTPopUpActions.CLEAR_VALUE)){
         value = "";
      }else if (action.equals(MBTPopUpActions.EDIT)){
         editValue();
      }

   }
   
   /**
    *   Copyright (C) 2006 CINCOM SYSTEMS, INC.
    *   All Rights Reserved
    *   Copyright (C) 2006 Igor Mekterovic
    *   All Rights Reserved
    */
   
   /*
    * By Prakash 18 Sept 2006.
    * For setting Cell Property Directly from Query  
    * @author pyadav
    *
    */
   public void setCellProperty(String cellProperty)
   {
   	if(cellProperty!=null)
   		value=cellProperty;
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
    MBTPopUpActions.popUpCaptions.put(MBTPopUpActions.CLEAR_VALUE ,I18n.getString("menu.clearValue"));
    MBTPopUpActions.popUpCaptions.put(MBTPopUpActions.EDIT ,I18n.getString("menu.editValue"));
   }
  /* end of modification for I18n */

}
