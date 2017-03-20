package rex.graphics.mdxeditor.mdxbuilder.nodes;


import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import rex.utils.*;

/**
 * Node representing MdxFunction's enum argument (one that can take on only one value from the finite set).
 * @author Igor Mekterovic
 * @version 0.3
 */

 /**
  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
  * All Rights Reserved
  * Copyright (C) 2006 Igor Mekterovic
  * All Rights Reserved
  */ 
 /*  Added LanguageChangedListener to implement I18n  */
 
public class MBTArgEnumNode 
        extends DefaultMBTArgNode
        implements LanguageChangedListener{

   private String value;
   private String[] enums;

   public MBTArgEnumNode(){
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

   }

   public MBTArgEnumNode(    String _argName
                           , String[] _enums
                            ) {
      super(false, _argName, false);
      /**
  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
  * All Rights Reserved
  * Copyright (C) 2006 Igor Mekterovic
  * All Rights Reserved
  */ 
/*adding this class to the list of classes that implement I18n */

      I18n.addOnLanguageChangedListener(this);
        /* end of modification for I18n */

      enums = _enums;
   }
   public MBTArgEnumNode(    String _argName
                           , String[] _enums
                           , boolean _respawnArg
                            ) {
      super(false, _argName,  _respawnArg);
      /**
  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
  * All Rights Reserved
  * Copyright (C) 2006 Igor Mekterovic
  * All Rights Reserved
  */ 
/*adding this class to the list of classes that implement I18n */

      I18n.addOnLanguageChangedListener(this);
        /* end of modification for I18n */

      enums = _enums;
   }
   public MBTArgEnumNode(    boolean _isHeadArg
                           , String[] _enums
                           , String _argName
                            ) {
      super(_isHeadArg, _argName,  false);
      enums = _enums;
   }
   public MBTArgEnumNode(    boolean _isHeadArg
                          , String _argName
                          , String[] _enums
                          , boolean _respawnArg
                            ) {
      super(_isHeadArg, _argName, _respawnArg);
      /**
      * Copyright (C) 2006 CINCOM SYSTEMS, INC.
      * All Rights Reserved
      * Copyright (C) 2006 Igor Mekterovic
      * All Rights Reserved
      */ 
    /*adding this class to the list of classes that implement I18n */

      I18n.addOnLanguageChangedListener(this);
        /* end of modification for I18n */

      enums = _enums;
   }

   public MBTArgEnumNode(    boolean _isHeadArg
                          , String _argName
                          , String[] _enums
                          , boolean _respawnArg
                          , boolean _optionalArg
                            ) {
      super(_isHeadArg, _argName, _respawnArg, _optionalArg);
     /**
      * Copyright (C) 2006 CINCOM SYSTEMS, INC.
      * All Rights Reserved
      * Copyright (C) 2006 Igor Mekterovic
      * All Rights Reserved
      */ 
    /*adding this class to the list of classes that implement I18n */

      I18n.addOnLanguageChangedListener(this);
        /* end of modification for I18n */

      enums = _enums;
   }

   /**
    * Returns a corresponding MDX statement with specified indent.
    * @param indent String
    * @return String
    */
   public  String getMdx(String indent){
      return (value == null) ? "": indent + value;

   }


   public String toString(){
      if(value == null)
         return super.toString();
      if (isOptionalArg()){
         return "<html><i>" + value + "</i></html>";
      }
      return value;
   }

   /**
    * Sets accpetable mime types. This node accepts no-one.
    */
   void setAcceptableFlavorsArray(){
   }


   /**
    * Handles drop.
    * @param droppedData Object
    * @param containerNode DefaultMutableTreeNode
    * @param treeModel DefaultTreeModel
    */
   public  void handleDrop(  Object droppedData
                           , DefaultMutableTreeNode containerNode
                           , DefaultTreeModel treeModel){

   }


   /**
    * Returns pop-up actions available for this node.
    * @return String[]
    */
   public String[] getPopUpActionList(){
//      S.out("enums.length = " + enums.length);

      String[] al = new String[1 + enums.length];
//      S.out("al.length = " + al.length);
      al[0] = MBTPopUpActions.CLEAR_VALUE;
      for (int i=1; i< al.length; i++){
         al[i] = new String(enums[i - 1]);
      }
      return al;
   }

   /**
    * Handles selected pop-up action.
    * @param action String
    * @param actionNode DefaultMutableTreeNode
    * @param treeModel DefaultTreeModel
    */
   public void handlePopUpAction(  String action
                                 , DefaultMutableTreeNode actionNode
                                 , DefaultTreeModel treeModel){

      if (action.equals(MBTPopUpActions.CLEAR_VALUE)){
         value = null;
      }else{
         for (int i=0; i< enums.length; i++){
            if (action.equals(enums[i])){
               value = enums[i];
//               uncomment this if there ever appears a respawning enum node?
//               maybeRespawnOnDrop(actionNode, treeModel);
               return;
            }
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
    MBTPopUpActions.popUpCaptions.put(MBTPopUpActions.CLEAR_VALUE ,I18n.getString("menu.clearValue"));
   }
  /* end of modification for I18n */

}
