package rex.graphics.mdxeditor.mdxbuilder.nodes;

import javax.swing.tree.DefaultMutableTreeNode;
import rex.utils.LanguageChangedListener;
import rex.utils.*;
import javax.swing.ImageIcon;
import javax.swing.tree.DefaultTreeModel;
import rex.graphics.dimensiontree.elements.DimensionTreeElement;
import rex.graphics.dimensiontree.elements.*;
import java.util.ArrayList;
import java.util.Stack;

/**
 * Node representing member(s). This node has current members function (default is "Members") that is
* appended when generating MDX in getMdx() method. User can change current method on right-click.
 * @author Igor Mekterovic
 * @version 0.3
 */
public class MBTMembersNode extends DefaultMBTNode implements LanguageChangedListener{//sb
   private String membersMdxExpression, currentFunction, dimensionUniqueName;
   private Stack currType;
   /**
    * Member functions available.
    */
//   private String[] memberFunctions;
   private static ArrayList rcFunctions;
   private static RightClickFunction f;
   static{

      rcFunctions  = new ArrayList();
      f = new RightClickFunction("AllMembers", "set", LevelElement.class.getName());
      f.addType(DimensionElement.class.getName());
      rcFunctions.add (f);

      rcFunctions.add ( new RightClickFunction("Children",      "set",                         MemberElement.class.getName()));
      rcFunctions.add ( new RightClickFunction("CurrentMember", MemberElement.class.getName(), DimensionElement.class.getName()));
      rcFunctions.add ( new RightClickFunction("DataMember",    MemberElement.class.getName(), MemberElement.class.getName()));
      rcFunctions.add ( new RightClickFunction("DefaultMember", MemberElement.class.getName(), DimensionElement.class.getName()));
      rcFunctions.add ( new RightClickFunction("FirstChild",    MemberElement.class.getName(), MemberElement.class.getName()));
      rcFunctions.add ( new RightClickFunction("FirstSibling",  MemberElement.class.getName(), MemberElement.class.getName()));
      rcFunctions.add ( new RightClickFunction("LastChild",     MemberElement.class.getName(), MemberElement.class.getName()));
      rcFunctions.add ( new RightClickFunction("LastSibling",   MemberElement.class.getName(), MemberElement.class.getName()));

      f = new RightClickFunction("Members", "set", LevelElement.class.getName());
      f.addType(DimensionElement.class.getName());
      rcFunctions.add (f);

      f = new RightClickFunction("Name", "string", LevelElement.class.getName());
      f.addType(DimensionElement.class.getName());
      f.addType(MemberElement.class.getName());
      rcFunctions.add (f);

      rcFunctions.add ( new RightClickFunction("NextMember",    MemberElement.class.getName(), MemberElement.class.getName()));
      rcFunctions.add ( new RightClickFunction("Parent",        MemberElement.class.getName(), MemberElement.class.getName()));
      rcFunctions.add ( new RightClickFunction("PrevMember",    MemberElement.class.getName(), MemberElement.class.getName()));
      rcFunctions.add ( new RightClickFunction("Siblings",      "set",                         MemberElement.class.getName()));
      rcFunctions.add ( new RightClickFunction("Value",         "value",                       MemberElement.class.getName()));

      f = new RightClickFunction("<<none>>",      "none",                        MemberElement.class.getName());
      f.addType(DimensionElement.class.getName());
      f.addType(LevelElement.class.getName());
      rcFunctions.add(f);

   }
  /*
    {
      "AllMembers"
      , "Children"
      , "CurrentMember"
      , "DataMember"
      , "DefaultMember"
      , "FirstChild"
      , "FirstSibling"
      , "LastChild"
      , "LastSibling"
      , "Members"
      , "Name"
      , "NextMember"
      , "Parent"
      , "PrevMember"
      , "Siblings"
      , "Value"
      , "<<none>>"
   };

   */


   static ImageIcon icon;
   static {
      icon = S.getAppIcon("MBTMembersNode.gif");
   }


   public MBTMembersNode(DimensionTreeElement dte) {
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

      membersMdxExpression = ((DimensionTreeElement)dte).getUniqueName();
      dimensionUniqueName  =  dte.getDimensionUniqueName();
      currType = new Stack();
      currType.push(dte.getClass().getName());
//      S.out("dte.getClass().getName() =" + dte.getClass().getName());
//    default:
      if (   dte instanceof MeasureElement
          || dte instanceof MemberElement){
         currentFunction = "<<none>>";
      }else{
         currentFunction = "Members";
         currType.push("set");
      }


   }



   public String getDimensionUniqueName(){
      return dimensionUniqueName;
   }

   /**
    *
    * @param indent String
    * @return String
    */
   public  String getMdx(String indent){
      if (currentFunction.equals("<<none>>")){
         return indent + membersMdxExpression;
      }else{
         return indent + membersMdxExpression+ "." + currentFunction;
      }

   }

   /**
    *
    * @return String
    */
   public  String toString(){
      return getMdx("");
   }

   /**
    * This node accepts no-one.
    */
   void setAcceptableFlavorsArray(){
//    I'm not accepting anyone
   }

   /**
    * Returns icon representin member node.
    * @return ImageIcon
    */
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
//      membersMdxExpression += ", \n" + droppedData.getMdx();
   }



   /**
    * Returns available pop-up actions: remove action + actions in the memberFunctions array.
    * Actions in the memberFunctions array are displayed in separate submenu.
    * @return String[]
    */
   public String[] getPopUpActionList(){
//      S.out("enums.length = " + enums.length);

//      String[] al = new String[5 + memberFunctions.length];
      ArrayList a = new ArrayList();
      a.add(MBTPopUpActions.REMOVE);


//      ******************  BASE functions *******************************
      boolean hasBaseFunctions = false;
      String type = (String)currType.elementAt(0); // this is a base type of the object

      for (int i=0; i< rcFunctions.size(); i++){
         if ( ( (RightClickFunction) rcFunctions.get(i)).isApplicableTo(type)){
            hasBaseFunctions = true;
            break;
         }
      }

      if (hasBaseFunctions){

         a.add(MBTPopUpActions.SEPARATOR);
         a.add(MBTPopUpActions.OPEN_SUBMENU);
         a.add("Change function to:");

         for (int i = 0; i < rcFunctions.size(); i++) {
            if ( ( (RightClickFunction) rcFunctions.get(i)).isApplicableTo(type))
               a.add( ( (RightClickFunction) rcFunctions.get(i)).toString());
         }
         a.add(MBTPopUpActions.CLOSE_SUBMENU);
      }

// *********************** CHAIN functions *************************************
      boolean canChain = false;
      type = (String)currType.peek();
      for (int i=0; currType.size()>1 && i< rcFunctions.size(); i++){
         if ( ( (RightClickFunction) rcFunctions.get(i)).isApplicableTo(type)){
            canChain = true;
            break;
         }
      }
      if (canChain){
         a.add(MBTPopUpActions.OPEN_SUBMENU);
         a.add("Chain function to:");
         for (int i = 0; i < rcFunctions.size(); i++) {
            if ( ( (RightClickFunction) rcFunctions.get(i)).isApplicableTo(type))
               a.add( currentFunction + "." + ( (RightClickFunction) rcFunctions.get(i)).toString());
         }
         a.add(MBTPopUpActions.CLOSE_SUBMENU);
      }


      return (String[]) a.toArray(new String[0]);
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
      }else{
         String baseType;
         // first, check the base functions generated by the base type, on the bottom of the stack
         for (int i=0; i< rcFunctions.size(); i++){
            if ( ( (RightClickFunction) rcFunctions.get(i)).toString().equals(action) ) {
               baseType = (String) currType.elementAt(0);
               currType.clear();
               currType.push(baseType);
               currType.push(( (RightClickFunction) rcFunctions.get(i)).getReturnValueType());
               currentFunction = ( (RightClickFunction) rcFunctions.get(i)).toString();
               return;
            }
         }
         // then, I check for the chained expressions, e.g. CurrentMember.Name
         for (int i=0; i< rcFunctions.size(); i++){
            if ( action.equals(currentFunction + "." + ( (RightClickFunction) rcFunctions.get(i)).toString()) ) {
               currType.push(( (RightClickFunction) rcFunctions.get(i)).getReturnValueType());
               currentFunction += "." + ( (RightClickFunction) rcFunctions.get(i)).toString();
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
    MBTPopUpActions.popUpCaptions.put(MBTPopUpActions.REMOVE ,I18n.getString("menu.remove"));
}
    /* end of modification for I18n */

}
