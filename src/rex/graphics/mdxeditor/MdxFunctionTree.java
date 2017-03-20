package rex.graphics.mdxeditor;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.tree.*;
import rex.graphics.mdxeditor.mdxbuilder.nodes.*;
import rex.graphics.mdxeditor.mdxfunctions.*;
import rex.utils.*;

/**
 * Class that displays Mdx Function Tree in a JPanel.
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

public class MdxFunctionTree extends JPanel
        implements LanguageChangedListener{
   final JTree tree;
   private JComponent parent;
   private TreePath popUpSource;
   private boolean categorized;
   DefaultMutableTreeNode
           nonMdxFunctions
         , arrayFunctions
         , stringFunctions
         , dimensionHierarchyAndLevelFunctions
         , dimensionFunctions
         , hierarchyFunctions
         , levelFunctions
         , logicalFunctions
         , tupleFunctions
         , setFunctions
         , otherFunctions
         , numericFunctions
         , memberFunctions
         , allFunctions;

 DefaultMutableTreeNode top; 
   public MdxFunctionTree(JComponent _parent) {
      this();
      parent = _parent;
   }
   public MdxFunctionTree() {
         top =  new DefaultMutableTreeNode("");

     //Create a tree that allows one selection at a time.
//      tree = new JTree(top);
      tree = new JTree(top){
               {setOpaque(false);}
               public void paintComponent(Graphics g) {
                  //S.paintBackgroundGreen(g, this);
				  S.paintWhiteBackground(g, this);// By Prakash
                  super.paintComponent(g);
               }

      };
      buildFunctionList(top);
      tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
      //Enable tool tips.
      ToolTipManager.sharedInstance().registerComponent(tree);


        //Create the scroll pane and add the tree to it.
      JScrollPane treeView = new JScrollPane(tree);



      treeView.setPreferredSize(new Dimension(200, 600));

//      treeView.setMinimumSize(new Dimension(200, 0));
      treeView.setMaximumSize(new Dimension(800,600));

      this.setLayout(new BorderLayout());
      this.add(treeView, BorderLayout.CENTER);


      tree.setCellRenderer(new MdxFunctionTreeRenderer());
    
      tree.addMouseListener(new PopupListener());
     /**
      * Copyright (C) 2006 CINCOM SYSTEMS, INC.
      * All Rights Reserved
      * Copyright (C) 2006 Igor Mekterovic
      * All Rights Reserved
      */  
      I18n.addOnLanguageChangedListener(this);
   }
 /**
  * Helper method that is executed when the language is changed
  */

    public void languageChanged(LanguageChangedEvent evt) {
        this.applyI18n();
    }
  /**
  *  Helper method to implement locatization when language is changed
  */

    public void applyI18n(){
       buildFunctionList(top); //re-executing the method to apply I18n...
       tree.updateUI();
       tree.repaint();
    }
   
  /* end of modification for I18n */

   
   /**
    * Returns a mdx function tree.
    * @return JTree
    */
   public JTree getTree(){
      return tree;
   }

   /**
    * Builds the function tree by adding Mdx Functions one by one.
    * @param top DefaultMutableTreeNode
    */
   private void buildFunctionList(DefaultMutableTreeNode top){

      MdxFunction f;
// ********************************************* Non MDX Functions ***********************************************
      nonMdxFunctions = new DefaultMutableTreeNode("Non MDX Functions");
 /**
  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
  * All Rights Reserved
  * Copyright (C) 2006 Igor Mekterovic
  * All Rights Reserved
  */ 
        f = new MdxMakeTupleFunction( "make tuple"
                              , I18n.getString("nonMDXFunction.makeTuple")
                              , "(«Member»[, «Member»...])") {
                        /* end of modification for I18n */
          public MBTNode[] getFunctionArguments() {
            return new MBTNode[] {
                 new MBTArgAcceptAllStringNode(false, "«Member»", true, false)
            };
         }

      };
      nonMdxFunctions.add(new DefaultMutableTreeNode(f));
 /**
  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
  * All Rights Reserved
  * Copyright (C) 2006 Igor Mekterovic
  * All Rights Reserved
  */ 
        f = new MdxOperatorFunction( "*"
                          , I18n.getString("nonMDXFunction.multiplyOpr")
                          , "(«Numeric Expression1» * «Numeric Expression2»)") {
                        /* end of modification for I18n */
         public MBTNode[] getFunctionArguments() {
            return new MBTNode[] {
                 new MBTArgNumericNode(true, "«NumExp1»")
               , new MBTArgNumericNode(false, "«NumExp2»", true, false)
            };
         }

      };
      nonMdxFunctions.add(new DefaultMutableTreeNode(f));
 /**
  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
  * All Rights Reserved
  * Copyright (C) 2006 Igor Mekterovic
  * All Rights Reserved
  */ 
      f = new MdxOperatorFunction( "/"
                              ,  I18n.getString("nonMDXFunction.divideOpr")
                              , "(«Numeric Expression1» / «Numeric Expression2»)") {
                      /* end of modification for I18n */
         public MBTNode[] getFunctionArguments() {
            return new MBTNode[] {
                 new MBTArgNumericNode(true, "«NumExp1»")
               , new MBTArgNumericNode(false, "«NumExp2»", true, false)
            };

         }

      };
      nonMdxFunctions.add(new DefaultMutableTreeNode(f));
 /**
  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
  * All Rights Reserved
  * Copyright (C) 2006 Igor Mekterovic
  * All Rights Reserved
  */ 
       f = new MdxOperatorFunction( "+"
                              ,  I18n.getString("nonMDXFunction.addOpr")
                              , "(«Numeric Expression1» + «Numeric Expression2»)") {
                       /* end of modification for I18n */
         public MBTNode[] getFunctionArguments() {
            return new MBTNode[] {
                 new MBTArgNumericNode(true, "«NumExp1»")
               , new MBTArgNumericNode(false, "«NumExp2»", true, false)
            };

         }

      };
      nonMdxFunctions.add(new DefaultMutableTreeNode(f));
       /**
  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
  * All Rights Reserved
  * Copyright (C) 2006 Igor Mekterovic
  * All Rights Reserved
  */ 
        f = new MdxOperatorFunction( "-"
                              ,  I18n.getString("nonMDXFunction.subtractOpr")
                              , "(«Numeric Expression1» + «Numeric Expression2»)") {
                        /* end of modification for I18n */
         public MBTNode[] getFunctionArguments() {
            return new MBTNode[] {
                 new MBTArgNumericNode(true, "«NumExp1»")
               , new MBTArgNumericNode(false, "«NumExp2»", true, false)
            };
         }

      };
      nonMdxFunctions.add(new DefaultMutableTreeNode(f));

// ********************************************* ARRAY ***************************************************

      arrayFunctions = new DefaultMutableTreeNode("Array Functions");
 /**
  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
  * All Rights Reserved
  * Copyright (C) 2006 Igor Mekterovic
  * All Rights Reserved
  */ 
      f = new MdxArrayFunction( "SetToArray"
                              ,  I18n.getString("arrayFunction.setToArray")
                              , "SetToArray(«Set»[, «Set»...][, «Numeric Expression»]") {
                      /* end of modification for I18n */
         public MBTNode[] getFunctionArguments() {
            return new MBTNode[] {
                 new MBTArgSetNode(false, "«Set1»", true, false)
               , new MBTArgNumericNode(false, "«Numeric Expression»", false, true)
            };
         }

      };
      arrayFunctions.add(new DefaultMutableTreeNode(f));


// ******************************************* STRING ********************************************************

      stringFunctions = new DefaultMutableTreeNode("String Functions");
       /**
  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
  * All Rights Reserved
  * Copyright (C) 2006 Igor Mekterovic
  * All Rights Reserved
  */ 
       f = new MdxStringFunction("CalculationPassValue"
                              ,  I18n.getString("stringFunction.calcPassValue")
                              , "CalculationPassValue(«String Expression», «Pass Value»[, «Access Flag»])") {
                       /* end of modification for I18n */
         public MBTNode[] getFunctionArguments() {
            return new MBTNode[] {
                 new MBTArgStringNode("«String Expression»")
               , new MBTArgNumericNode("«Pass Value»")
               , new MBTArgEnumNode(false, "«Access Flag»", new String[]{"ABSOLUTE", "RELATIVE"}, false, true)
            };
         }

      };
      stringFunctions.add(new DefaultMutableTreeNode(f));
 /**
  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
  * All Rights Reserved
  * Copyright (C) 2006 Igor Mekterovic
  * All Rights Reserved
  */ 
       f = new MdxStringFunction("CoalesceEmpty"
                              ,  I18n.getString("stringFunction.coalescesEmpty")
                              , "CoalesceEmpty(«Numeric Expression»[, «Numeric Expression»]...)") {
                       /* end of modification for I18n */
         public MBTNode[] getFunctionArguments() {
            return new MBTNode[] {
                 new MBTArgNumericNode("«Numeric Expression»")
               , new MBTArgNumericNode(false, "«Numeric Expression»", true, true)
            };
         }

      };
      stringFunctions.add(new DefaultMutableTreeNode(f));
       /**
  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
  * All Rights Reserved
  * Copyright (C) 2006 Igor Mekterovic
  * All Rights Reserved
  */ 
      f = new MdxStringFunction("Generate"
                              ,  I18n.getString("stringFunction.generate")
                              , "Generate(«Set1», «Set2»[, ALL])") {
                      /* end of modification for I18n */
         public MBTNode[] getFunctionArguments() {
            return new MBTNode[] {
                 new MBTArgSetNode("«Set1»")
               , new MBTArgSetNode("«Set2»")
               , new MBTArgEnumNode(false, "ALL", new String[]{"ALL"}, false, true)
            };
         }

      };
      //stringFunctions.add(new DefaultMutableTreeNode(f));



      stringFunctions.add(new DefaultMutableTreeNode(f));
       /**
  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
  * All Rights Reserved
  * Copyright (C) 2006 Igor Mekterovic
  * All Rights Reserved
  */ 
        f = new MdxStringFunction("Generate"
                              ,  I18n.getString("stringFunction.generate")
                              , "Generate(«Set», «String Expression»[, «Delimiter»])") {
                        /* end of modification for I18n */
         public MBTNode[] getFunctionArguments() {
            return new MBTNode[] {
                 new MBTArgSetNode("«Set1»")
               , new MBTArgStringNode("«String Expression»")
               , new MBTArgStringNode(false, "«Delimiter»", false, true)
            };
         }

      };
      stringFunctions.add(new DefaultMutableTreeNode(f));
 /**
  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
  * All Rights Reserved
  * Copyright (C) 2006 Igor Mekterovic
  * All Rights Reserved
  */ 

       f = new MdxStringFunction("IIf"
                              , I18n.getString("stringFunction.iif")
                              , "IIf(«Logical Expression», «Numeric or String Expression1», «Numeric or String Expression2»)") {
                       /* end of modification for I18n */
         public MBTNode[] getFunctionArguments() {
            return new MBTNode[] {
                 new MBTArgLogicalNode("«Logical Expression»")
               , new MBTArgStringNode("«Numeric or String Expression1»")
               , new MBTArgStringNode("«Numeric or String Expression1»")
            };
         }

      };
      stringFunctions.add(new DefaultMutableTreeNode(f));
       /**
  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
  * All Rights Reserved
  * Copyright (C) 2006 Igor Mekterovic
  * All Rights Reserved
  */ 
      f = new MdxStringFunction("LookupCube"
                              ,  I18n.getString("stringFunction.lookUpCube")
                              , "LookupCube(«Cube String», «Numeric or String Expression»)") {
                      /* end of modification for I18n */
         public MBTNode[] getFunctionArguments() {
            return new MBTNode[] {
                 new MBTArgStringNode("«Cube String»")
               , new MBTArgStringNode("«Numeric or String Expression1»")
            };
         }

      };
      stringFunctions.add(new DefaultMutableTreeNode(f));
 /**
  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
  * All Rights Reserved
  * Copyright (C) 2006 Igor Mekterovic
  * All Rights Reserved
  */ 
        f = new MdxStringFunction("MemberToStr"
                              ,  I18n.getString("stringFunction.memberToStr")
                              , "MemberToStr(«Member»)") {
                        /* end of modification for I18n */
         public MBTNode[] getFunctionArguments() {
            return new MBTNode[] {
                 new MBTArgMemberNode("«Member»")
            };
         }

      };
      stringFunctions.add(new DefaultMutableTreeNode(f));
 /**
  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
  * All Rights Reserved
  * Copyright (C) 2006 Igor Mekterovic
  * All Rights Reserved
  */ 
        f = new MdxStringFunction("Name"
                          ,  I18n.getString("stringFunction.name")
                          , "«Dimension|Level|Member|Hierarchy».Name") {
                        /* end of modification for I18n */
         public MBTNode[] getFunctionArguments() {
            return new MBTNode[] {
                 new MBTArgAcceptAllStringNode(true, "«Dimension|Level|Member|Hierarchy»")
            };
         }

      };
      stringFunctions.add(new DefaultMutableTreeNode(f));
 /**
  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
  * All Rights Reserved
  * Copyright (C) 2006 Igor Mekterovic
  * All Rights Reserved
  */ 
        f = new MdxStringFunction("Properties"
                          ,  I18n.getString("stringFunction.properties")
                          , "«Member».Properties(«String Expression»)") {
            /* end of modification for I18n */
         public MBTNode[] getFunctionArguments() {
            return new MBTNode[] {
                   new MBTArgMemberNode(true, "«Member»")
                 , new MBTArgStringNode("«String Expression»")
            };
         }

      };
      stringFunctions.add(new DefaultMutableTreeNode(f));

 /**
  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
  * All Rights Reserved
  * Copyright (C) 2006 Igor Mekterovic
  * All Rights Reserved
  */ 
       f = new MdxStringFunction("SetToStr"
                              ,  I18n.getString("stringFunction.setToStr")
                              , "SetToStr(«Set»)") {
                       /* end of modification for I18n */
         public MBTNode[] getFunctionArguments() {
            return new MBTNode[] {
                 new MBTArgSetNode("«Set»")
            };
         }

      };
      stringFunctions.add(new DefaultMutableTreeNode(f));
 /**
  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
  * All Rights Reserved
  * Copyright (C) 2006 Igor Mekterovic
  * All Rights Reserved
  */ 
      f = new MdxStringFunction("TupleToStr"
                              ,  I18n.getString("stringFunction.tupleToStr")
                              , "TupleToStr(«Tuple»)") {
                      /* end of modification for I18n */
         public MBTNode[] getFunctionArguments() {
            return new MBTNode[] {
                 new MBTArgTupleNode("«Set»")
            };
         }

      };
      stringFunctions.add(new DefaultMutableTreeNode(f));
 /**
  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
  * All Rights Reserved
  * Copyright (C) 2006 Igor Mekterovic
  * All Rights Reserved
  */ 
        f = new MdxStringFunction("UniqueName"
                             ,  I18n.getString("stringFunction.uniqueName")
                             , "«Dimension/Level/Member/Hierarchy».UniqueName") {
                        /* end of modification for I18n */
         public MBTNode[] getFunctionArguments() {
            return new MBTNode[] {
                 new MBTArgStringNode(true, "«Dimension/Level/Member/Hierarchy»")
            };
         }

      };
      stringFunctions.add(new DefaultMutableTreeNode(f));
       /**
  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
  * All Rights Reserved
  * Copyright (C) 2006 Igor Mekterovic
  * All Rights Reserved
  */ 
      f = new MdxStringFunction("UserName"
                             ,  I18n.getString("stringFunction.userName")
                             , "UserName") {
                      /* end of modification for I18n */
         public MBTNode[] getFunctionArguments() {
            return null;
         }

      };
      stringFunctions.add(new DefaultMutableTreeNode(f));




      dimensionHierarchyAndLevelFunctions = new DefaultMutableTreeNode("Dimension, Hierarchy, and Level Functions ");

// ********************************************* DIMENSION *******************************************************

      dimensionFunctions = new DefaultMutableTreeNode("Dimension Functions");
 /**
  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
  * All Rights Reserved
  * Copyright (C) 2006 Igor Mekterovic
  * All Rights Reserved
  */ 
        f = new MdxDimensionFunction("Dimension"
                         ,  I18n.getString("dimensionFunction.dimension")
                         , "«Member|Level|Hierarchy».Dimension") {
                        /* end of modification for I18n */
         public MBTNode[] getFunctionArguments() {
            return new MBTNode[] {
                 new MBTArgStringNode(true, "«Member|Level|Hierarchy»")
            };
         }

      };
      dimensionFunctions.add(new DefaultMutableTreeNode(f));
 /**
  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
  * All Rights Reserved
  * Copyright (C) 2006 Igor Mekterovic
  * All Rights Reserved
  */ 
        f = new MdxDimensionFunction("Dimensions"
                         ,  I18n.getString("dimensionFunction.dimensions")
                         , "Dimensions(«Numeric/String Expression»)") {
                        /* end of modification for I18n */
         public MBTNode[] getFunctionArguments() {
            return new MBTNode[] {
                 new MBTArgStringNode("«Numeric or String Expression1»")
            };
         }

      };
      dimensionFunctions.add(new DefaultMutableTreeNode(f));


// ********************************************* HIERARCHY *******************************************************

      hierarchyFunctions = new DefaultMutableTreeNode("Hierarchy Functions");
       /**
  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
  * All Rights Reserved
  * Copyright (C) 2006 Igor Mekterovic
  * All Rights Reserved
  */ 
        f = new MdxHierarchyFunction("Hierarchy"
                             ,  I18n.getString("hierarchyFunction.hierarchy")
                             , "«Member|Level».Hierarchy") {
                        /* end of modification for I18n */
         public MBTNode[] getFunctionArguments() {
            return new MBTNode[] {
                 new MBTArgStringNode(true, "«Member|Level»")
            };
         }

      };
      hierarchyFunctions.add(new DefaultMutableTreeNode(f));


// ************************************************* LEVEL *****************************************************

      levelFunctions = new DefaultMutableTreeNode("Level Functions");
 /**
  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
  * All Rights Reserved
  * Copyright (C) 2006 Igor Mekterovic
  * All Rights Reserved
  */ 
        f = new MdxLevelFunction("Level"
                             ,  I18n.getString("levelFunction.level")
                             , "«Member».Level") {
            /* end of modification for I18n */
         public MBTNode[] getFunctionArguments() {
            return new MBTNode[] {
                 new MBTArgMemberNode(true, "«Level»")
            };
         }

      };
      levelFunctions.add(new DefaultMutableTreeNode(f));
 /**
  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
  * All Rights Reserved
  * Copyright (C) 2006 Igor Mekterovic
  * All Rights Reserved
  */ 
       f = new MdxLevelFunction("Levels"
                         , I18n.getString("levelFunction.numeric")
                         , "«Dimension».Levels(«Numeric Expression»)") {
                       /* end of modification for I18n */
         public MBTNode[] getFunctionArguments() {
            return new MBTNode[] {
                 new MBTArgDimensionNode(true, "«Dimension»")
               , new MBTArgNumericNode("«Numeric Expression»")
            };
         }

      };
      levelFunctions.add(new DefaultMutableTreeNode(f));
 /**
  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
  * All Rights Reserved
  * Copyright (C) 2006 Igor Mekterovic
  * All Rights Reserved
  */ 
        f = new MdxLevelFunction("Levels"
                         , I18n.getString("levelFunction.string")
                         , "Levels(«String Expression»)") {
                        /* end of modification for I18n */
         public MBTNode[] getFunctionArguments() {
            return new MBTNode[] {
                 new MBTArgStringNode("«String Expression»")
            };
         }

      };
      levelFunctions.add(new DefaultMutableTreeNode(f));



      dimensionHierarchyAndLevelFunctions.add(dimensionFunctions);
      dimensionHierarchyAndLevelFunctions.add(hierarchyFunctions);
      dimensionHierarchyAndLevelFunctions.add(levelFunctions);

// ************************************************* LOGICAL *****************************************************

      logicalFunctions = new DefaultMutableTreeNode("Logical Functions");
       /**
  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
  * All Rights Reserved
  * Copyright (C) 2006 Igor Mekterovic
  * All Rights Reserved
  */ 
        f = new MdxLogicalFunction("Is (TO BE DONE!!!!)"
                         , I18n.getString("logicalFunction.compare")
                         , "«Object 1» IS «Object 2»") {
                        /* end of modification for I18n */
         public MBTNode[] getFunctionArguments() {
            return new MBTNode[] {
                   new MBTArgAcceptAllStringNode(true, "«Object 1»")
                 , new MBTArgAcceptAllStringNode("«Object 2»")
            };
         }

      };
      logicalFunctions.add(new DefaultMutableTreeNode(f));
 /**
  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
  * All Rights Reserved
  * Copyright (C) 2006 Igor Mekterovic
  * All Rights Reserved
  */ 
        f = new MdxLogicalFunction("IsAncestor"
                         , I18n.getString("logicalFunction.isAncestor")
                         , "IsAncestor(«Member1»,«Member2»)") {
                        /* end of modification for I18n */
         public MBTNode[] getFunctionArguments() {
            return new MBTNode[] {
                   new MBTArgMemberNode(true, "Member1»")
                 , new MBTArgMemberNode("«Member2»")
            };
         }

      };
      logicalFunctions.add(new DefaultMutableTreeNode(f));
 /**
  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
  * All Rights Reserved
  * Copyright (C) 2006 Igor Mekterovic
  * All Rights Reserved
  */ 
        f = new MdxLogicalFunction("IsEmpty"
                         , I18n.getString("logicalFunction.isEmpty")
                         , "IsEmpty(«Value Expression»)") {
                        /* end of modification for I18n */
         public MBTNode[] getFunctionArguments() {
            return new MBTNode[] {
                   new MBTArgAcceptAllStringNode("«Value Expression»")
            };
         }

      };
      logicalFunctions.add(new DefaultMutableTreeNode(f));
       /**
  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
  * All Rights Reserved
  * Copyright (C) 2006 Igor Mekterovic
  * All Rights Reserved
  */ 
        f = new MdxLogicalFunction("IsGeneration"
                         , I18n.getString("logicalFunction.isGeneration")
                         , "IsGeneration(«Member»,«Numeric Expression»)") {
                        /* end of modification for I18n */
         public MBTNode[] getFunctionArguments() {
            return new MBTNode[] {
                   new MBTArgMemberNode("«Member»")
                 , new MBTArgNumericNode("«Numeric Expression»")
            };
         }

      };
      logicalFunctions.add(new DefaultMutableTreeNode(f));
 /**
  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
  * All Rights Reserved
  * Copyright (C) 2006 Igor Mekterovic
  * All Rights Reserved
  */ 
        f = new MdxLogicalFunction("IsLeaf"
                         , I18n.getString("logicalFunction.isLeaf")
                         , "IsLeaf(«Member»)") {
                        /* end of modification for I18n */
         public MBTNode[] getFunctionArguments() {
            return new MBTNode[] {
                   new MBTArgMemberNode("«Member»")
            };
         }

      };
      logicalFunctions.add(new DefaultMutableTreeNode(f));
 /**
  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
  * All Rights Reserved
  * Copyright (C) 2006 Igor Mekterovic
  * All Rights Reserved
  */ 
        f = new MdxLogicalFunction("IsSibling"
                         , I18n.getString("logicalFunction.isSibling")
                         , "IsSibling(«Member1»,«Member2»)") {
                        /* end of modification for I18n */
         public MBTNode[] getFunctionArguments() {
            return new MBTNode[] {
                   new MBTArgMemberNode("«Member1»")
                 , new MBTArgMemberNode("«Member2»")
            };
         }

      };
      logicalFunctions.add(new DefaultMutableTreeNode(f));


// ************************************************* TUPLE *****************************************************

      tupleFunctions = new DefaultMutableTreeNode("Tuple Functions");
 /**
  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
  * All Rights Reserved
  * Copyright (C) 2006 Igor Mekterovic
  * All Rights Reserved
  */ 
      f = new MdxTupleFunction("Current"
                             , I18n.getString("tupleFunction.current")
                             , "«Set».Current") {
                      /* end of modification for I18n */
         public MBTNode[] getFunctionArguments() {
            return new MBTNode[] {
                   new MBTArgSetNode(true, "«Set»")
            };
         }

      };
      tupleFunctions.add(new DefaultMutableTreeNode(f));
 /**
  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
  * All Rights Reserved
  * Copyright (C) 2006 Igor Mekterovic
  * All Rights Reserved
  */ 
      f = new MdxTupleFunction("Item"
                             , I18n.getString("tupleFunction.item")
                             , "«Set».Item(«String Expression»[, «String Expression»...] | «Index»)") {
                      /* end of modification for I18n */
         public MBTNode[] getFunctionArguments() {
            return new MBTNode[] {
                 new MBTArgSetNode(true, "«Set»")
               , new MBTArgStringNode(false, "«String Expression»", true, false)
            };
         }

      };
      tupleFunctions.add(new DefaultMutableTreeNode(f));
 /**
  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
  * All Rights Reserved
  * Copyright (C) 2006 Igor Mekterovic
  * All Rights Reserved
  */ 
      f = new MdxTupleFunction("StrToTuple"
                             , I18n.getString("tupleFunction.strToTuple")
                             , "StrToTuple(«String Expression»)") {
                      /* end of modification for I18n */
         public MBTNode[] getFunctionArguments() {
            return new MBTNode[] {
               new MBTArgStringNode("«String Expression»")
            };
         }

      };
      tupleFunctions.add(new DefaultMutableTreeNode(f));


// ************************************************* MEMBER *****************************************************


      memberFunctions = new DefaultMutableTreeNode("Member Functions");
 /**
  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
  * All Rights Reserved
  * Copyright (C) 2006 Igor Mekterovic
  * All Rights Reserved
  */ 
      f = new MdxMemberFunction("Ancestor"
                             , I18n.getString("memberFunction.ancestor")
                             , "Ancestor(«Member», «Level»)") {    
                      /* end of modification for I18n */
         public MBTNode[] getFunctionArguments() {
            return new MBTNode[] {
                 new MBTArgMemberNode("«Member»")
               , new MBTArgLevelNode("«Level»")
            };
         }

      };
      memberFunctions.add(new DefaultMutableTreeNode(f));
 /**
  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
  * All Rights Reserved
  * Copyright (C) 2006 Igor Mekterovic
  * All Rights Reserved
  */ 
        f = new MdxMemberFunction("Ancestor"
                             ,I18n.getString("memberFunction.ancestor2")
                             , "Ancestor(«Member», «Numeric Expression»)") {
                        /* end of modification for I18n */
         public MBTNode[] getFunctionArguments() {
            return new MBTNode[] {
                 new MBTArgMemberNode("«Member»")
               , new MBTArgNumericNode("«Numeric Expression»")
            };
         }

      };
      memberFunctions.add(new DefaultMutableTreeNode(f));
 /**
  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
  * All Rights Reserved
  * Copyright (C) 2006 Igor Mekterovic
  * All Rights Reserved
  */ 
        f = new MdxMemberFunction("ClosingPeriod"
                             , I18n.getString("memberFunction.closingPeriod")
                             , "ClosingPeriod([«Level»[, «Member»]])") {
            /* end of modification for I18n */
         public MBTNode[] getFunctionArguments() {
            return new MBTNode[] {
                 new MBTArgLevelNode("«Level»")
               , new MBTArgMemberNode(false, "«Member»", false, true)
            };
         }

      };
      memberFunctions.add(new DefaultMutableTreeNode(f));
 /**
  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
  * All Rights Reserved
  * Copyright (C) 2006 Igor Mekterovic
  * All Rights Reserved
  */ 
      f = new MdxMemberFunction("Cousin"
                             ,I18n.getString("memberFunction.cousin")
                             , "Cousin(«Member1», «Member2»)") {
                      /* end of modification for I18n */
         public MBTNode[] getFunctionArguments() {
            return new MBTNode[] {
                 new MBTArgMemberNode("«Member1»")
               , new MBTArgMemberNode("«Member2»")
            };
         }

      };
      memberFunctions.add(new DefaultMutableTreeNode(f));
 /**
  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
  * All Rights Reserved
  * Copyright (C) 2006 Igor Mekterovic
  * All Rights Reserved
  */ 
      f = new MdxMemberFunction("CurrentMember"
                             , I18n.getString("memberFunction.currentMember")
                             , "«Dimension».CurrentMember") {
                      /* end of modification for I18n */
         public MBTNode[] getFunctionArguments() {
            return new MBTNode[] {
               new MBTArgDimensionNode(true, "«Dimension»")
            };
         }

      };
      memberFunctions.add(new DefaultMutableTreeNode(f));
 /**
  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
  * All Rights Reserved
  * Copyright (C) 2006 Igor Mekterovic
  * All Rights Reserved
  */ 
      f = new MdxMemberFunction("DataMember"
                             , I18n.getString("memberFunction.dataMember")
                             , "«Member».DataMember") {
                      /* end of modification for I18n */
         public MBTNode[] getFunctionArguments() {
            return new MBTNode[] {
               new MBTArgMemberNode(true, "«Member»")
            };
         }

      };
      memberFunctions.add(new DefaultMutableTreeNode(f));
 /**
  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
  * All Rights Reserved
  * Copyright (C) 2006 Igor Mekterovic
  * All Rights Reserved
  */       
      f = new MdxMemberFunction("DefaultMember"
                             ,I18n.getString("memberFunction.defaultMember")
                             , "«Dimension».DefaultMember") {
            /* end of modification for I18n */
         public MBTNode[] getFunctionArguments() {
            return new MBTNode[] {
               new MBTArgDimensionNode(true, "«Dimension»")
            };
         }

      };
      memberFunctions.add(new DefaultMutableTreeNode(f));
      f = new MdxMemberFunction("FirstChild"
                             , I18n.getString("memberFunction.firstChild")
                             , "«Member».FirstChild") {
            /* end of modification for I18n */
         public MBTNode[] getFunctionArguments() {
            return new MBTNode[] {
               new MBTArgMemberNode(true, "«Member»")
            };
         }

      };
      memberFunctions.add(new DefaultMutableTreeNode(f));
 /**
  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
  * All Rights Reserved
  * Copyright (C) 2006 Igor Mekterovic
  * All Rights Reserved
  */ 
      f = new MdxMemberFunction("FirstSibling"
                             ,I18n.getString("memberFunction.firstSibling")
                             , "«Member».FirstSibling") {            
          /* end of modification for I18n */          
         public MBTNode[] getFunctionArguments() {
            return new MBTNode[] {
               new MBTArgMemberNode(true, "«Member»")
            };
         }

      };
      memberFunctions.add(new DefaultMutableTreeNode(f));
 /**
  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
  * All Rights Reserved
  * Copyright (C) 2006 Igor Mekterovic
  * All Rights Reserved
  */       
      f = new MdxMemberFunction("Item"
                             ,I18n.getString("memberFunction.item")
                             , "«Tuple».Item(«Index»)") {
                      /* end of modification for I18n */
         public MBTNode[] getFunctionArguments() {
            return new MBTNode[] {
                   new MBTArgTupleNode(true, "«Tuple»")
                 , new MBTArgNumericNode("«Index»")
            };
         }

      };
      memberFunctions.add(new DefaultMutableTreeNode(f));
 /**
  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
  * All Rights Reserved
  * Copyright (C) 2006 Igor Mekterovic
  * All Rights Reserved
  */       
      f = new MdxMemberFunction("Lag"
                             , I18n.getString("memberFunction.lag")
                             , "«Member».Lag(«Numeric Expression»)") {
                      /* end of modification for I18n */
         public MBTNode[] getFunctionArguments() {
            return new MBTNode[] {
                   new MBTArgMemberNode(true, "«Member»")
                 , new MBTArgNumericNode("«Numeric Expression»")
            };
         }

      };
      memberFunctions.add(new DefaultMutableTreeNode(f));
       /**
  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
  * All Rights Reserved
  * Copyright (C) 2006 Igor Mekterovic
  * All Rights Reserved
  */ 
      f = new MdxMemberFunction("LastChild"
                             , I18n.getString("memberFunction.lastChild")
                             , "«Member».LastChild") {
            /* end of modification for I18n */
         public MBTNode[] getFunctionArguments() {
            return new MBTNode[] {
               new MBTArgMemberNode(true, "«Member»")
            };
         }

      };
      memberFunctions.add(new DefaultMutableTreeNode(f));
       /**
  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
  * All Rights Reserved
  * Copyright (C) 2006 Igor Mekterovic
  * All Rights Reserved
  */ 
      f = new MdxMemberFunction("LastSibling"
                             , I18n.getString("memberFunction.lastSibling")
                             , "«Member».LastSibling") {
                      /* end of modification for I18n */
         public MBTNode[] getFunctionArguments() {
            return new MBTNode[] {
               new MBTArgMemberNode(true, "«Member»")
            };
         }

      };
      memberFunctions.add(new DefaultMutableTreeNode(f));
       /**
  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
  * All Rights Reserved
  * Copyright (C) 2006 Igor Mekterovic
  * All Rights Reserved
  */ 
        f = new MdxMemberFunction("Lead"
                         , I18n.getString("memberFunction.lead")
                         , "«Member».Lead(«Numeric Expression»)") {
                        /* end of modification for I18n */
          
         public MBTNode[] getFunctionArguments() {
            return new MBTNode[] {
                 new MBTArgMemberNode(true, "«Member»")
               , new MBTArgNumericNode("«Numeric Expression»")
            };
         }

      };
      memberFunctions.add(new DefaultMutableTreeNode(f));
 /**
  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
  * All Rights Reserved
  * Copyright (C) 2006 Igor Mekterovic
  * All Rights Reserved
  */ 
        f = new MdxMemberFunction("LinkMember"
                             , I18n.getString("memberFunction.linkMember")
                             , "LinkMember(«Member», «Hierarchy»)") {
                        /* end of modification for I18n */
         public MBTNode[] getFunctionArguments() {
            return new MBTNode[] {
                 new MBTArgMemberNode("«Member»")
               , new MBTArgHierarchyNode("«Hierarchy»")
            };
         }

      };
      memberFunctions.add(new DefaultMutableTreeNode(f));
 /**
  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
  * All Rights Reserved
  * Copyright (C) 2006 Igor Mekterovic
  * All Rights Reserved
  */ 
        f = new MdxMemberFunction("Members"
                             , I18n.getString("memberFunction.members")
                             , "«Dimension|Hierarchy|Level|String Expression».Members") {
                        /* end of modification for I18n */
         public MBTNode[] getFunctionArguments() {
            return new MBTNode[] {
               new MBTArgStringNode(true, "«Dimension|Hierarchy|Level|String Expression»")
            };
         }

      };
      memberFunctions.add(new DefaultMutableTreeNode(f));
 /**
  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
  * All Rights Reserved
  * Copyright (C) 2006 Igor Mekterovic
  * All Rights Reserved
  */ 
        f = new MdxMemberFunction("NextMember"
                             , I18n.getString("memberFunction.nextMember")
                             , "«Member».NextMember") {
                        /* end of modification for I18n */
         public MBTNode[] getFunctionArguments() {
            return new MBTNode[] {
               new MBTArgMemberNode(true, "«Member»")
            };
         }

      };
      memberFunctions.add(new DefaultMutableTreeNode(f));
 /**
  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
  * All Rights Reserved
  * Copyright (C) 2006 Igor Mekterovic
  * All Rights Reserved
  */ 

        f = new MdxMemberFunction("OpeningPeriod"
                             , I18n.getString("memberFunction.openingPeriod")
                             , "OpeningPeriod([«Level»[, «Member»]])") {
                        /* end of modification for I18n */
         public MBTNode[] getFunctionArguments() {
            return new MBTNode[] {
                 new MBTArgLevelNode(false, "«Level»", false, true)
               , new MBTArgMemberNode(false, "«Member»", false, true)
            };
         }

      };
      memberFunctions.add(new DefaultMutableTreeNode(f));
 /**
  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
  * All Rights Reserved
  * Copyright (C) 2006 Igor Mekterovic
  * All Rights Reserved
  */ 
        f = new MdxMemberFunction("ParallelPeriod"
                             , I18n.getString("memberFunction.parallelPeriod")
                             , "ParallelPeriod([«Level»[, «Numeric Expression»[, «Member»]]])") {
                        /* end of modification for I18n */
         public MBTNode[] getFunctionArguments() {
            return new MBTNode[] {
                 new MBTArgLevelNode(false, "«Level»", false, true)
               , new MBTArgNumericNode(false, "«Numeric Expression»", false, true)
               , new MBTArgMemberNode(false, "«Member»", false, true)
            };
         }

      };
      memberFunctions.add(new DefaultMutableTreeNode(f));
 /**
  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
  * All Rights Reserved
  * Copyright (C) 2006 Igor Mekterovic
  * All Rights Reserved
  */       
        f = new MdxMemberFunction("Parent"
                             , I18n.getString("memberFunction.parent")
                             , "«Member».Parent") {
                        /* end of modification for I18n */
         public MBTNode[] getFunctionArguments() {
            return new MBTNode[] {
               new MBTArgMemberNode(true, "«Member»")
            };
         }

      };
      memberFunctions.add(new DefaultMutableTreeNode(f));
 /**
  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
  * All Rights Reserved
  * Copyright (C) 2006 Igor Mekterovic
  * All Rights Reserved
  */ 
        f = new MdxMemberFunction("PrevMember"
                             , I18n.getString("memberFunction.prevMember")
                             , "«Member».PrevMember") {
                        /* end of modification for I18n */
         public MBTNode[] getFunctionArguments() {
            return new MBTNode[] {
               new MBTArgMemberNode(true, "«Member»")
            };
         }

      };
      memberFunctions.add(new DefaultMutableTreeNode(f));
 /**
  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
  * All Rights Reserved
  * Copyright (C) 2006 Igor Mekterovic
  * All Rights Reserved
  */ 
        f = new MdxMemberFunction("StrToMember"
                             , I18n.getString("memberFunction.strToMember")
                             , "StrToMember(«String Expression»)") {
                        /* end of modification for I18n */
         public MBTNode[] getFunctionArguments() {
            return new MBTNode[] {
               new MBTArgStringNode("«String Expression»")
            };
         }

      };
      memberFunctions.add(new DefaultMutableTreeNode(f));
 /**
  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
  * All Rights Reserved
  * Copyright (C) 2006 Igor Mekterovic
  * All Rights Reserved
  */ 
        f = new MdxMemberFunction("ValidMeasure"
                             ,I18n.getString("memberFunction.validMeasure")
                             , "ValidMeasure(«Tuple»)") {
                        /* end of modification for I18n */
         public MBTNode[] getFunctionArguments() {
            return new MBTNode[] {
               new MBTArgTupleNode("«Tuple»")
            };
         }

      };
      memberFunctions.add(new DefaultMutableTreeNode(f));


// ************************************************* NUMERIC *****************************************************


      numericFunctions = new DefaultMutableTreeNode("Numeric Functions");
 /**
  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
  * All Rights Reserved
  * Copyright (C) 2006 Igor Mekterovic
  * All Rights Reserved
  */       
      f = new MdxNumericFunction("Aggregate"
                             , I18n.getString("numericFunction.aggregate")
                             , "Aggregate(«Set»[, «Numeric Expression»])") {
                      /* end of modification for I18n */
         public MBTNode[] getFunctionArguments() {
            return new MBTNode[] {
                 new MBTArgSetNode("«Set»")
               , new MBTArgNumericNode(false, "«Numeric Expression»", false, true)
            };
         }

      };
      numericFunctions.add(new DefaultMutableTreeNode(f));
 /**
  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
  * All Rights Reserved
  * Copyright (C) 2006 Igor Mekterovic
  * All Rights Reserved
  */ 
      f = new MdxNumericFunction("Avg"
                             , I18n.getString("numericFunction.avg")
                             , "Avg(«Set»[, «Numeric Expression»])") {
                      /* end of modification for I18n */
         public MBTNode[] getFunctionArguments() {
            return new MBTNode[] {
                 new MBTArgSetNode("«Set»")
               , new MBTArgNumericNode(false, "«Numeric Expression»", false, true)
            };
         }

      };
      numericFunctions.add(new DefaultMutableTreeNode(f));
 /**
  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
  * All Rights Reserved
  * Copyright (C) 2006 Igor Mekterovic
  * All Rights Reserved
  */ 
      f = new MdxNumericFunction("CalculationCurrentPass()"
                             ,I18n.getString("numericFunction.calcPass")
                             , "CalculationCurrentPass()") {
                      /* end of modification for I18n */
         public MBTNode[] getFunctionArguments() {
            return null;
         }

      };
      numericFunctions.add(new DefaultMutableTreeNode(f));

 /**
  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
  * All Rights Reserved
  * Copyright (C) 2006 Igor Mekterovic
  * All Rights Reserved
  */ 
      f = new MdxNumericFunction("CalculationPassValue"
                             , I18n.getString("numericFunction.clacPassValue")
                             , "CalculationPassValue(«Numeric Expression», «Pass Value»[, «Access Flag»])") {
                      /* end of modification for I18n */
         public MBTNode[] getFunctionArguments() {
            return new MBTNode[] {
                 new MBTArgNumericNode("«Numeric Expression»")
               , new MBTArgNumericNode("«Pass Value»")
               , new MBTArgEnumNode(false, "«Access Flag»", new String[]{"ABSOLUTE", "RELATIVE"}, false, true)
            };
         }

      };
      numericFunctions.add(new DefaultMutableTreeNode(f));

 /**
  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
  * All Rights Reserved
  * Copyright (C) 2006 Igor Mekterovic
  * All Rights Reserved
  */ 
      f = new MdxNumericFunction("CoalesceEmpty"
                             , I18n.getString("numericFunction.coalesceEmpty")
                             , "CoalesceEmpty(«Numeric Expression»[, «Numeric Expression»]...)") {
                      /* end of modification for I18n */
         public MBTNode[] getFunctionArguments() {
            return new MBTNode[] {
                 new MBTArgNumericNode("«Numeric Expression»")
               , new MBTArgNumericNode(false, "«Numeric Expression»", true, true)
            };
         }

      };
      numericFunctions.add(new DefaultMutableTreeNode(f));
 /**
  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
  * All Rights Reserved
  * Copyright (C) 2006 Igor Mekterovic
  * All Rights Reserved
  */ 
      f = new MdxNumericFunction("Correlation"
                             , I18n.getString("numericFunction.correlation")
                             , "Correlation(«Set», «Numeric Expression»[, «Numeric Expression»])") {
                      /* end of modification for I18n */
         public MBTNode[] getFunctionArguments() {
            return new MBTNode[] {
                 new MBTArgSetNode("«Set»")
               , new MBTArgNumericNode("«Numeric Expression»")
               , new MBTArgNumericNode(false, "«Numeric Expression»", false, true)
            };
         }

      };
      numericFunctions.add(new DefaultMutableTreeNode(f));
 /**
  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
  * All Rights Reserved
  * Copyright (C) 2006 Igor Mekterovic
  * All Rights Reserved
  */ 
      f = new MdxNumericFunction("Dimensions.Count"
                             ,I18n.getString("numericFunction.dimensionCount")
                             , "Dimensions.Count") {
                      /* end of modification for I18n */
         public MBTNode[] getFunctionArguments() {
            return null;
         }

      };
      numericFunctions.add(new DefaultMutableTreeNode(f));
 /**
  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
  * All Rights Reserved
  * Copyright (C) 2006 Igor Mekterovic
  * All Rights Reserved
  */ 
      f = new MdxNumericFunction("Levels.Count"
                             ,I18n.getString("numericFunction.levelCount")
                             , "«Dimension»|«Hierarchy».Levels.Count") {
                      /* end of modification for I18n */
         public MBTNode[] getFunctionArguments() {
            return new MBTNode[] {
               new MBTArgStringNode(true, "«Dimension|Hierarchy»")
            };
         }

      };
      numericFunctions.add(new DefaultMutableTreeNode(f));
 /**
  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
  * All Rights Reserved
  * Copyright (C) 2006 Igor Mekterovic
  * All Rights Reserved
  */ 
      f = new MdxNumericFunction("Count"
                             , I18n.getString("numericFunction.count")
                             , "Count(«Set»[, ExcludeEmpty | IncludeEmpty])") {
                      /* end of modification for I18n */
         public MBTNode[] getFunctionArguments() {
            return new MBTNode[] {
                 new MBTArgSetNode("«Set»")
               , new MBTArgEnumNode(false, "ExcludeEmpty | IncludeEmpty", new String[]{"ExcludeEmpty", "IncludeEmpty"}, false, true)
            };
         }

      };
      numericFunctions.add(new DefaultMutableTreeNode(f));
 /**
  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
  * All Rights Reserved
  * Copyright (C) 2006 Igor Mekterovic
  * All Rights Reserved
  */ 
      f = new MdxNumericFunction("Covariance"
                             , I18n.getString("numericFunction.covariance")
                             , "Covariance(«Set», «Numeric Expression»[, «Numeric Expression»])") {
                      /* end of modification for I18n */
         public MBTNode[] getFunctionArguments() {
            return new MBTNode[] {
                 new MBTArgSetNode("«Set»")
               , new MBTArgNumericNode("«Numeric Expression»")
               , new MBTArgNumericNode(false, "«Numeric Expression»", false, true)
            };
         }

      };
      numericFunctions.add(new DefaultMutableTreeNode(f));
 /**
  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
  * All Rights Reserved
  * Copyright (C) 2006 Igor Mekterovic
  * All Rights Reserved
  */ 
      f = new MdxNumericFunction("CovarianceN"
                             , I18n.getString("numericFunction.covarianceN")
                             , "CovarianceN(«Set», «Numeric Expression»[, «Numeric Expression»])") {
                      /* end of modification for I18n */
         public MBTNode[] getFunctionArguments() {
            return new MBTNode[] {
                 new MBTArgSetNode("«Set»")
               , new MBTArgNumericNode("«Numeric Expression»")
               , new MBTArgNumericNode(false, "«Numeric Expression»", false, true)
            };
         }

      };
      numericFunctions.add(new DefaultMutableTreeNode(f));
 /**
  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
  * All Rights Reserved
  * Copyright (C) 2006 Igor Mekterovic
  * All Rights Reserved
  */ 
      f = new MdxNumericFunction("DistinctCount"
                             , I18n.getString("numericFunction.distinctCount")
                             , "DistinctCount(«Set»)") {
                      /* end of modification for I18n */
         public MBTNode[] getFunctionArguments() {
            return new MBTNode[] {
                 new MBTArgSetNode("«Set»")
            };
         }

      };
      numericFunctions.add(new DefaultMutableTreeNode(f));
 /**
  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
  * All Rights Reserved
  * Copyright (C) 2006 Igor Mekterovic
  * All Rights Reserved
  */ 
      f = new MdxNumericFunction("IIf"
                              , I18n.getString("numericFunction.iif")
                              , "IIf(«Logical Expression», «Numeric or String Expression1», «Numeric or String Expression2»)") {
                      /* end of modification for I18n */
         public MBTNode[] getFunctionArguments() {
            return new MBTNode[] {
                 new MBTArgLogicalNode("«Logical Expression»")
               , new MBTArgStringNode("«Numeric or String Expression1»")
               , new MBTArgStringNode("«Numeric or String Expression1»")
            };
         }

      };
      numericFunctions.add(new DefaultMutableTreeNode(f));
 /**
  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
  * All Rights Reserved
  * Copyright (C) 2006 Igor Mekterovic
  * All Rights Reserved
  */ 
      f = new MdxNumericFunction("LinRegIntercept"
                              , I18n.getString("numericFunction.lineRegIncercept")
                              , "LinRegIntercept(«Set», «Numeric Expression»[, «Numeric Expression»])") {
                      /* end of modification for I18n */
         public MBTNode[] getFunctionArguments() {
            return new MBTNode[] {
                 new MBTArgStringNode("«Set»")
               , new MBTArgNumericNode("«Numeric Expression»")
               , new MBTArgNumericNode(false, "«Numeric Expression»", false, true)
            };
         }

      };
      numericFunctions.add(new DefaultMutableTreeNode(f));
 /**
  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
  * All Rights Reserved
  * Copyright (C) 2006 Igor Mekterovic
  * All Rights Reserved
  */ 
      f = new MdxNumericFunction("LinRegPoint"
                              , I18n.getString("numericFunction.lineRegPoint")
                              , "LinRegPoint(«Numeric Expression», «Set», «Numeric Expression»[, «Numeric Expression»])") {
                      /* end of modification for I18n */
         public MBTNode[] getFunctionArguments() {
            return new MBTNode[] {
                 new MBTArgNumericNode("«Numeric Expression»")
               , new MBTArgStringNode("«Set»")
               , new MBTArgNumericNode("«Numeric Expression»")
               , new MBTArgNumericNode(false, "«Numeric Expression»", false, true)
            };
         }

      };
      numericFunctions.add(new DefaultMutableTreeNode(f));
 /**
  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
  * All Rights Reserved
  * Copyright (C) 2006 Igor Mekterovic
  * All Rights Reserved
  */ 
      f = new MdxNumericFunction("LinRegR2"
                              , I18n.getString("numericFunction.lineRegR2")
                              , "LinRegR2(«Set», «Numeric Expression»[, «Numeric Expression»])") {
                      /* end of modification for I18n */
         public MBTNode[] getFunctionArguments() {
            return new MBTNode[] {
                 new MBTArgStringNode("«Set»")
               , new MBTArgNumericNode("«Numeric Expression»")
               , new MBTArgNumericNode(false, "«Numeric Expression»", false, true)
            };
         }

      };
      numericFunctions.add(new DefaultMutableTreeNode(f));
 /**
  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
  * All Rights Reserved
  * Copyright (C) 2006 Igor Mekterovic
  * All Rights Reserved
  */ 
      f = new MdxNumericFunction("LinRegSlope"
                              ,I18n.getString("numericFunction.lineRegSlope")
                              , "LinRegSlope(«Set», «Numeric Expression»[, «Numeric Expression»])") {
                      /* end of modification for I18n */
         public MBTNode[] getFunctionArguments() {
            return new MBTNode[] {
                 new MBTArgStringNode("«Set»")
               , new MBTArgNumericNode("«Numeric Expression»")
               , new MBTArgNumericNode(false, "«Numeric Expression»", false, true)
            };
         }

      };
      numericFunctions.add(new DefaultMutableTreeNode(f));
 /**
  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
  * All Rights Reserved
  * Copyright (C) 2006 Igor Mekterovic
  * All Rights Reserved
  */ 
      f = new MdxNumericFunction("LinRegVariance"
                              ,I18n.getString("numericFunction.lineRegVariance")
                              , "LinRegVariance(«Set», «Numeric Expression»[, «Numeric Expression»])") {
                      /* end of modification for I18n */
         public MBTNode[] getFunctionArguments() {
            return new MBTNode[] {
                 new MBTArgStringNode("«Set»")
               , new MBTArgNumericNode("«Numeric Expression»")
               , new MBTArgNumericNode(false, "«Numeric Expression»", false, true)
            };
         }

      };
      numericFunctions.add(new DefaultMutableTreeNode(f));
 /**
  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
  * All Rights Reserved
  * Copyright (C) 2006 Igor Mekterovic
  * All Rights Reserved
  */ 
      f = new MdxNumericFunction("LookupCube"
                              , I18n.getString("numericFunction.lookUpCube")
                              , "LookupCube(«Cube String», «Numeric|String Expression»)") {
                      /* end of modification for I18n */
         public MBTNode[] getFunctionArguments() {
            return new MBTNode[] {
                 new MBTArgStringNode("«Cube String»")
               , new MBTArgStringNode("«Numeric|String Expression»")
            };
         }

      };
      numericFunctions.add(new DefaultMutableTreeNode(f));
 /**
  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
  * All Rights Reserved
  * Copyright (C) 2006 Igor Mekterovic
  * All Rights Reserved
  */ 
      f = new MdxNumericFunction("Max"
                              , I18n.getString("numericFunction.max")
                              , "Max(«Set»[, «Numeric Expression»])") {
                      /* end of modification for I18n */
         public MBTNode[] getFunctionArguments() {
            return new MBTNode[] {
                 new MBTArgSetNode("«Set»")
               , new MBTArgNumericNode(false, "«Numeric Expression»", false, true)
            };
         }

      };
      numericFunctions.add(new DefaultMutableTreeNode(f));
 /**
  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
  * All Rights Reserved
  * Copyright (C) 2006 Igor Mekterovic
  * All Rights Reserved
  */ 
      f = new MdxNumericFunction("Median"
                              , I18n.getString("numericFunction.median")
                              , "Median(«Set»[, «Numeric Expression»])") {
                      /* end of modification for I18n */
         public MBTNode[] getFunctionArguments() {
            return new MBTNode[] {
                 new MBTArgSetNode("«Set»")
               , new MBTArgNumericNode(false, "«Numeric Expression»", false, true)
            };
         }

      };
      numericFunctions.add(new DefaultMutableTreeNode(f));
 /**
  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
  * All Rights Reserved
  * Copyright (C) 2006 Igor Mekterovic
  * All Rights Reserved
  */ 
      f = new MdxNumericFunction("Min"
                              , I18n.getString("numericFunction.min")
                              , "Min(«Set»[, «Numeric Expression»])") {
                      /* end of modification for I18n */
         public MBTNode[] getFunctionArguments() {
            return new MBTNode[] {
                 new MBTArgSetNode("«Set»")
               , new MBTArgNumericNode(false, "«Numeric Expression»", false, true)
            };
         }

      };
      numericFunctions.add(new DefaultMutableTreeNode(f));
 /**
  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
  * All Rights Reserved
  * Copyright (C) 2006 Igor Mekterovic
  * All Rights Reserved
  */ 
      f = new MdxNumericFunction("Ordinal"
                              , I18n.getString("numericFunction.ordinal")
                              , "«Level».Ordinal") {
                      /* end of modification for I18n */
         public MBTNode[] getFunctionArguments() {
            return new MBTNode[] {
                 new MBTArgLevelNode(true, "«Level»")
            };
         }

      };
      numericFunctions.add(new DefaultMutableTreeNode(f));
 /**
  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
  * All Rights Reserved
  * Copyright (C) 2006 Igor Mekterovic
  * All Rights Reserved
  */ 
      f = new MdxNumericFunction("Predict"
                              , I18n.getString("numericFunction.predict")
                              , "Predict(«Mining Model Name», «Numeric Expression»)") {
                      /* end of modification for I18n */
         public MBTNode[] getFunctionArguments() {
            return new MBTNode[] {
                 new MBTArgStringNode("«Mining Model Name»")
               , new MBTArgNumericNode( "«Numeric Expression»")
            };
         }

      };
      numericFunctions.add(new DefaultMutableTreeNode(f));
 /**
  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
  * All Rights Reserved
  * Copyright (C) 2006 Igor Mekterovic
  * All Rights Reserved
  */ 
      f = new MdxNumericFunction("Rank"
                              ,I18n.getString("numericFunction.rank")
                              , "Rank(«Tuple», «Set»[, «Calc Expression»])") {
                      /* end of modification for I18n */
         public MBTNode[] getFunctionArguments() {
            return new MBTNode[] {
                 new MBTArgTupleNode("«Tuple»")
               , new MBTArgSetNode( "«Set»")
               , new MBTArgAcceptAllStringNode( false, "«Calc Expression»", false, true)
            };
         }

      };
      numericFunctions.add(new DefaultMutableTreeNode(f));
 /**
  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
  * All Rights Reserved
  * Copyright (C) 2006 Igor Mekterovic
  * All Rights Reserved
  */ 
      f = new MdxNumericFunction("RollupChildren"
                              , I18n.getString("numericFunction.rollUpChildren")
                              , "RollupChildren(«Member», «String Expression»)") {
                      /* end of modification for I18n */
         public MBTNode[] getFunctionArguments() {
            return new MBTNode[] {
                 new MBTArgMemberNode("«Member»")
               , new MBTArgStringNode( "«String Expression»")
            };
         }

      };
      numericFunctions.add(new DefaultMutableTreeNode(f));
 /**
  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
  * All Rights Reserved
  * Copyright (C) 2006 Igor Mekterovic
  * All Rights Reserved
  */ 
      f = new MdxNumericFunction("Stddev"
                              , I18n.getString("numericFunction.stdDev")
                              , "Stdev(«Set»[, «Numeric Expression»])") {
                      /* end of modification for I18n */
         public MBTNode[] getFunctionArguments() {
            return new MBTNode[] {
                 new MBTArgSetNode("«Set»")
               , new MBTArgNumericNode(false, "Numeric Expression»", false, true)
            };
         }

      };
      numericFunctions.add(new DefaultMutableTreeNode(f));
 /**
  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
  * All Rights Reserved
  * Copyright (C) 2006 Igor Mekterovic
  * All Rights Reserved
  */ 
      f = new MdxNumericFunction("StddevP"
                              , I18n.getString("numericFunction.stdDevP")
                              , "StdevP(«Set»[, «Numeric Expression»])") {     
          /* end of modification for I18n */
          
         public MBTNode[] getFunctionArguments() {
            return new MBTNode[] {
                 new MBTArgSetNode("«Set»")
               , new MBTArgNumericNode(false, "Numeric Expression»", false, true)
            };
         }

      };
      numericFunctions.add(new DefaultMutableTreeNode(f));
 /**
  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
  * All Rights Reserved
  * Copyright (C) 2006 Igor Mekterovic
  * All Rights Reserved
  */ 
      f = new MdxNumericFunction("Stdev"
                              , I18n.getString("numericFunction.stdev")
                              , "Stdev(«Set»[, «Numeric Expression»])") {
                      /* end of modification for I18n */
         public MBTNode[] getFunctionArguments() {
            return new MBTNode[] {
                 new MBTArgSetNode("«Set»")
               , new MBTArgNumericNode(false, "Numeric Expression»", false, true)
            };
         }

      };
      numericFunctions.add(new DefaultMutableTreeNode(f));
 /**
  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
  * All Rights Reserved
  * Copyright (C) 2006 Igor Mekterovic
  * All Rights Reserved
  */ 
      f = new MdxNumericFunction("StdevP"
                              , I18n.getString("numericFunction.stdevP")
                              , "StdevP(«Set»[, «Numeric Expression»])") {
                      /* end of modification for I18n */
         public MBTNode[] getFunctionArguments() {
            return new MBTNode[] {
                 new MBTArgSetNode("«Set»")
               , new MBTArgNumericNode(false, "Numeric Expression»", false, true)
            };
         }

      };
      numericFunctions.add(new DefaultMutableTreeNode(f));
 /**
  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
  * All Rights Reserved
  * Copyright (C) 2006 Igor Mekterovic
  * All Rights Reserved
  */ 
      f = new MdxNumericFunction("StrToValue"
                              ,I18n.getString("numericFunction.strToValue")
                              , "StrToValue(«String Expression»)") {
                      /* end of modification for I18n */
         public MBTNode[] getFunctionArguments() {
            return new MBTNode[] {
               new MBTArgStringNode("«String Expression»")
            };
         }

      };
      numericFunctions.add(new DefaultMutableTreeNode(f));
 /**
  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
  * All Rights Reserved
  * Copyright (C) 2006 Igor Mekterovic
  * All Rights Reserved
  */ 
      f = new MdxNumericFunction("Sum"
                              ,I18n.getString("numericFunction.sum")
                              , "Sum(«Set»[, «Numeric Expression»])") {
                      /* end of modification for I18n */
         public MBTNode[] getFunctionArguments() {
            return new MBTNode[] {
                 new MBTArgSetNode("«Set»")
               , new MBTArgNumericNode(false, "««Numeric Expression»", false, true)
            };
         }

      };
      numericFunctions.add(new DefaultMutableTreeNode(f));

 /**
  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
  * All Rights Reserved
  * Copyright (C) 2006 Igor Mekterovic
  * All Rights Reserved
  */ 
      f = new MdxNumericFunction("Value"
                              ,I18n.getString("numericFunction.value")
                              , "«Member».Value") {
                      /* end of modification for I18n */
         public MBTNode[] getFunctionArguments() {
            return new MBTNode[] {
               new MBTArgMemberNode(true, "«Member»")
            };
         }

      };
      numericFunctions.add(new DefaultMutableTreeNode(f));
 /**
  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
  * All Rights Reserved
  * Copyright (C) 2006 Igor Mekterovic
  * All Rights Reserved
  */ 
      f = new MdxNumericFunction("Var"
                              ,I18n.getString("numericFunction.var")
                              , "Var(«Set»[, «Numeric Expression»])") {
                      /* end of modification for I18n */
         public MBTNode[] getFunctionArguments() {
            return new MBTNode[] {
                 new MBTArgSetNode("«Set»")
               , new MBTArgNumericNode(false, "«Numeric Expression»", false, true)
            };
         }

      };
      numericFunctions.add(new DefaultMutableTreeNode(f));
 /**
  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
  * All Rights Reserved
  * Copyright (C) 2006 Igor Mekterovic
  * All Rights Reserved
  */ 
      f = new MdxNumericFunction("Variance"
                              ,I18n.getString("numericFunction.variance")
                              , "Variance(«Set»[, «Numeric Expression»])") {
                      /* end of modification for I18n */
         public MBTNode[] getFunctionArguments() {
            return new MBTNode[] {
                 new MBTArgSetNode("«Set»")
               , new MBTArgNumericNode(false, "«Numeric Expression»", false, true)
            };
         }

      };
      numericFunctions.add(new DefaultMutableTreeNode(f));
 /**
  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
  * All Rights Reserved
  * Copyright (C) 2006 Igor Mekterovic
  * All Rights Reserved
  */ 
      f = new MdxNumericFunction("VarianceP"
                              ,I18n.getString("numericFunction.varianceP")
                              , "VarP(«Set»[, «Numeric Expression»])") {
                      /* end of modification for I18n */
         public MBTNode[] getFunctionArguments() {
            return new MBTNode[] {
                 new MBTArgSetNode("«Set»")
               , new MBTArgNumericNode(false, "«Numeric Expression»", false, true)
            };
         }

      };
      numericFunctions.add(new DefaultMutableTreeNode(f));
 /**
  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
  * All Rights Reserved
  * Copyright (C) 2006 Igor Mekterovic
  * All Rights Reserved
  */ 
      f = new MdxNumericFunction("VarP"
                              , I18n.getString("numericFunction.varP")
                              , "VarP(«Set»[, «Numeric Expression»])") {
                      /* end of modification for I18n */
         public MBTNode[] getFunctionArguments() {
            return new MBTNode[] {
                 new MBTArgSetNode("«Set»")
               , new MBTArgNumericNode(false, "«Numeric Expression»", false, true)
            };
         }

      };
      numericFunctions.add(new DefaultMutableTreeNode(f));



// ************************************************* OTHER *****************************************************

      otherFunctions = new DefaultMutableTreeNode("Other Functions");
//      otherFunctions.add(new DefaultMutableTreeNode(new MdxFunction("Call"
//         , "Executes the string expression containing a user-defined function."
//         , "Call «UDF Name»")));




// ************************************************* SET *****************************************************

      setFunctions = new DefaultMutableTreeNode("Set Functions");
 /**
  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
  * All Rights Reserved
  * Copyright (C) 2006 Igor Mekterovic
  * All Rights Reserved
  */ 
      f = new MdxSetFunction("AddCalculatedMembers"
                              , I18n.getString("setFunction.addCalcMembers")
                              , "AddCalculatedMembers(«Set»)") {
                      /* end of modification for I18n */
         public MBTNode[] getFunctionArguments() {
            return new MBTNode[] {
                 new MBTArgSetNode("«Set»")
            };
         }

      };
      setFunctions.add(new DefaultMutableTreeNode(f));
 /**
  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
  * All Rights Reserved
  * Copyright (C) 2006 Igor Mekterovic
  * All Rights Reserved
  */ 
      f = new MdxSetFunction(   "AllMembers"
                              , I18n.getString("setFunction.allMembers")
                              , "«Dimension|Level».AllMembers") {
                      /* end of modification for I18n */
         public MBTNode[] getFunctionArguments() {
            return new MBTNode[] {
               new MBTArgStringNode(true, "«Dimension|Level»")
            };
         }

      };
      setFunctions.add(new DefaultMutableTreeNode(f));
 /**
  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
  * All Rights Reserved
  * Copyright (C) 2006 Igor Mekterovic
  * All Rights Reserved
  */ 

      f = new MdxSetFunction(   "Ancestors"
                              , I18n.getString("setFunction.ancestors")
                              , "Ancestors(«Member», «Numeric Expression»)") {
                      /* end of modification for I18n */
         public MBTNode[] getFunctionArguments() {
            return new MBTNode[] {
                 new MBTArgMemberNode("«Member»")
               , new MBTArgNumericNode("«Numeric Expression»")
            };
         }

      };
      setFunctions.add(new DefaultMutableTreeNode(f));
 /**
  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
  * All Rights Reserved
  * Copyright (C) 2006 Igor Mekterovic
  * All Rights Reserved
  */ 
      f = new MdxSetFunction(   "Ancestors"
                              , I18n.getString("setFunction.ancestors2")
                              , "Ancestors(«Member», «Level»)") {
                      /* end of modification for I18n */
         public MBTNode[] getFunctionArguments() {
            return new MBTNode[] {
                 new MBTArgMemberNode("«Member»")
               , new MBTArgLevelNode("«Level»")
            };
         }

      };
      setFunctions.add(new DefaultMutableTreeNode(f));
 /**
  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
  * All Rights Reserved
  * Copyright (C) 2006 Igor Mekterovic
  * All Rights Reserved
  */ 
      f = new MdxSetFunction(   "Ascendants"
                              , I18n.getString("setFunction.ascendants")
                              , "Ascendants(«Member»)") {
                      /* end of modification for I18n */
         public MBTNode[] getFunctionArguments() {
            return new MBTNode[] {
               new MBTArgMemberNode("«Member»")
            };
         }

      };
      setFunctions.add(new DefaultMutableTreeNode(f));

 /**
  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
  * All Rights Reserved
  * Copyright (C) 2006 Igor Mekterovic
  * All Rights Reserved
  */ 
      f = new MdxSetFunction(   "Axis"
                              ,I18n.getString("setFunction.axis")
                              , "Axis(«Numeric Expression»)") {
                      /* end of modification for I18n */
         public MBTNode[] getFunctionArguments() {
            return new MBTNode[] {
               new MBTArgNumericNode("«Numeric Expression»")
            };
         }

      };
      setFunctions.add(new DefaultMutableTreeNode(f));
 /**
  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
  * All Rights Reserved
  * Copyright (C) 2006 Igor Mekterovic
  * All Rights Reserved
  */ 
      f = new MdxSetFunction(   "BottomCount"
                              , I18n.getString("setFunction.bottomCount")
                              , "BottomCount(«Set», «Count»[, «Numeric Expression»])") {
                      /* end of modification for I18n */
         public MBTNode[] getFunctionArguments() {
            return new MBTNode[] {
                 new MBTArgSetNode("«Set»")
               , new MBTArgNumericNode("«Count»")
               , new MBTArgNumericNode(false, "«Numeric Expression»", false, true)
            };
         }

      };
      setFunctions.add(new DefaultMutableTreeNode(f));
 /**
  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
  * All Rights Reserved
  * Copyright (C) 2006 Igor Mekterovic
  * All Rights Reserved
  */ 
      f = new MdxSetFunction(   "BottomPercent"
                              , I18n.getString("setFunction.bottomPercent")
                              , "BottomPercent(«Set», «Percentage», «Numeric Expression»)") {
                      /* end of modification for I18n */
         public MBTNode[] getFunctionArguments() {
            return new MBTNode[] {
                 new MBTArgSetNode("«Set»")
               , new MBTArgNumericNode("«Percentage»")
               , new MBTArgNumericNode("«Numeric Expression»")
            };
         }

      };
      setFunctions.add(new DefaultMutableTreeNode(f));
 /**
  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
  * All Rights Reserved
  * Copyright (C) 2006 Igor Mekterovic
  * All Rights Reserved
  */ 
      f = new MdxSetFunction(   "BottomSum"
                              , I18n.getString("setFunction.bottomSum")
                              , "BottomSum(«Set», «Value», «Numeric Expression»)") {
                      /* end of modification for I18n */
         public MBTNode[] getFunctionArguments() {
            return new MBTNode[] {
                 new MBTArgSetNode("«Set»")
               , new MBTArgNumericNode("«Value»")
               , new MBTArgNumericNode("«Numeric Expression»")
            };
         }

      };
      setFunctions.add(new DefaultMutableTreeNode(f));
 /**
  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
  * All Rights Reserved
  * Copyright (C) 2006 Igor Mekterovic
  * All Rights Reserved
  */ 
      f = new MdxSetFunction(   "Children"
                              , I18n.getString("setFunction.children")
                              , "«Member».Children") {
                      /* end of modification for I18n */
         public MBTNode[] getFunctionArguments() {
            return new MBTNode[] {
               new MBTArgMemberNode(true, "«Member»")
            };
         }

      };
      setFunctions.add(new DefaultMutableTreeNode(f));
 /**
  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
  * All Rights Reserved
  * Copyright (C) 2006 Igor Mekterovic
  * All Rights Reserved
  */ 

      f = new MdxSetFunction("Crossjoin"
                             , I18n.getString("setFunction.crossJoin")
                             , "Crossjoin(«Set1», «Set2»)") {
                      /* end of modification for I18n */
         public MBTNode[] getFunctionArguments() {
            return new MBTNode[] {
               new MBTArgSetNode("«Set1»")
               , new MBTArgSetNode("«Set2»")
            };
         }

      };
      setFunctions.add(new DefaultMutableTreeNode(f));
 /**
  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
  * All Rights Reserved
  * Copyright (C) 2006 Igor Mekterovic
  * All Rights Reserved
  */ 
      f = new MdxSetFunction("Descendants"
                             , I18n.getString("setFunction.decendants")
                             , "Descendants(«Member», [«Level»[, «Desc_flags»]])") {
                      /* end of modification for I18n */
         public MBTNode[] getFunctionArguments() {
            return new MBTNode[] {
                 new MBTArgMemberNode("«Member»")
               , new MBTArgLevelNode(false, "«Level»", false, true)
               , new MBTArgEnumNode(false, "«Desc_flags»", new String[]{"SELF", "AFTER", "BEFORE", "BEFORE_AND_AFTER", "SELF_AND_AFTER", "SELF_AND_BEFORE", "SELF_BEFORE_AFTER", "LEAVES"}, false, true)
            };
         }

      };
      setFunctions.add(new DefaultMutableTreeNode(f));
 /**
  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
  * All Rights Reserved
  * Copyright (C) 2006 Igor Mekterovic
  * All Rights Reserved
  */ 

      f = new MdxSetFunction("Descendants"
                             , I18n.getString("setFunction.decendants")
                             , "Descendants(«Member», «Distance»[, «Desc_flags»])") {
                      /* end of modification for I18n */
         public MBTNode[] getFunctionArguments() {
            return new MBTNode[] {
                 new MBTArgMemberNode("«Member»")
               , new MBTArgNumericNode(false, "«Distance»", false, true)
               , new MBTArgEnumNode(false, "«Desc_flags»", new String[]{"SELF", "AFTER", "BEFORE", "BEFORE_AND_AFTER", "SELF_AND_AFTER", "SELF_AND_BEFORE", "SELF_BEFORE_AFTER", "LEAVES"}, false, true)
            };
         }

      };
      setFunctions.add(new DefaultMutableTreeNode(f));
 /**
  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
  * All Rights Reserved
  * Copyright (C) 2006 Igor Mekterovic
  * All Rights Reserved
  */ 
      f = new MdxSetFunction("Distinct"
                             , I18n.getString("setFunction.distinct")
                             , "Distinct(«Set»)") {
                      /* end of modification for I18n */
         public MBTNode[] getFunctionArguments() {
            return new MBTNode[] {
                 new MBTArgSetNode("«Set»")
            };
         }

      };
      setFunctions.add(new DefaultMutableTreeNode(f));
 /**
  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
  * All Rights Reserved
  * Copyright (C) 2006 Igor Mekterovic
  * All Rights Reserved
  */ 
      f = new MdxSetFunction("DrilldownLevel"
                             , I18n.getString("setFunction.drillDownLevel")
                             , "DrilldownLevel(«Set»[, {«Level» |  «Index»}])") {
                      /* end of modification for I18n */
         public MBTNode[] getFunctionArguments() {
            return new MBTNode[] {
                 new MBTArgSetNode("«Set»")
               , new MBTArgStringNode(false, "«Level» |  «Index»", false, true)
            };
         }

      };
      setFunctions.add(new DefaultMutableTreeNode(f));
 /**
  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
  * All Rights Reserved
  * Copyright (C) 2006 Igor Mekterovic
  * All Rights Reserved
  */ 
      f = new MdxSetFunction("DrilldownLevelBottom"
                             , I18n.getString("setFunction.drillDownLevelBottom")
                             , "DrilldownLevelBottom(«Set», «Count»[, [«Level»][, «Numeric Expression»]])") {
                      /* end of modification for I18n */
         public MBTNode[] getFunctionArguments() {
            return new MBTNode[] {
                 new MBTArgSetNode("«Set»")
               , new MBTArgNumericNode("«Count»")
               , new MBTArgLevelNode(false, "«Level»", false, true)
               , new MBTArgNumericNode(false, "«Numeric Expression»", false, true)
            };
         }

      };
      setFunctions.add(new DefaultMutableTreeNode(f));
 /**
  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
  * All Rights Reserved
  * Copyright (C) 2006 Igor Mekterovic
  * All Rights Reserved
  */ 
      f = new MdxSetFunction("DrilldownLevelTop"
                             , I18n.getString("setFunction.drillDownLevelTop")
                             , "DrilldownLevelTop(«Set», «Count»[, [«Level»][, «Numeric Expression»]])") {
                      /* end of modification for I18n */
         public MBTNode[] getFunctionArguments() {
            return new MBTNode[] {
                 new MBTArgSetNode("«Set»")
               , new MBTArgNumericNode("«Count»")
               , new MBTArgLevelNode(false, "«Level»", false, true)
               , new MBTArgNumericNode(false, "«Numeric Expression»", false, true)
            };
         }

      };
      setFunctions.add(new DefaultMutableTreeNode(f));

 /**
  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
  * All Rights Reserved
  * Copyright (C) 2006 Igor Mekterovic
  * All Rights Reserved
  */ 
      f = new MdxSetFunction("DrilldownMember"
                             , I18n.getString("setFunction.drillDownMember")
                             , "DrilldownMember(«Set1», «Set2»[, RECURSIVE])") {
                      /* end of modification for I18n */
         public MBTNode[] getFunctionArguments() {
            return new MBTNode[] {
                 new MBTArgSetNode("«Set1»")
               , new MBTArgSetNode("«Set2»")
               , new MBTArgEnumNode(false, "RECURSIVE", new String[]{"RECURSIVE"}, false, true)
            };
         }

      };
      setFunctions.add(new DefaultMutableTreeNode(f));
 /**
  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
  * All Rights Reserved
  * Copyright (C) 2006 Igor Mekterovic
  * All Rights Reserved
  */ 
      f = new MdxSetFunction("DrilldownMemberBottom"
                             , I18n.getString("setFunction.drillDownMemberBottom")
                             , "DrilldownMemberBottom(«Set1», «Set2», «Count»[, [«Numeric Expression»][, RECURSIVE]])") {
                      /* end of modification for I18n */
         public MBTNode[] getFunctionArguments() {
            return new MBTNode[] {
                 new MBTArgSetNode("«Set1»")
               , new MBTArgSetNode("«Set2»")
               , new MBTArgNumericNode("«Count»")
               , new MBTArgNumericNode(false, "«Numeric Expression»", false, true)
               , new MBTArgEnumNode(false, "RECURSIVE", new String[]{"RECURSIVE"}, false, true)
            };
         }

      };
      setFunctions.add(new DefaultMutableTreeNode(f));
 /**
  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
  * All Rights Reserved
  * Copyright (C) 2006 Igor Mekterovic
  * All Rights Reserved
  */ 
      f = new MdxSetFunction("DrilldownMemberTop"
                             , I18n.getString("setFunction.drillDownMemberTop")
                             , "DrilldownMemberTop(«Set1», «Set2», «Count»[, [«Numeric Expression»][, RECURSIVE]])") {
            /* end of modification for I18n */
         public MBTNode[] getFunctionArguments() {
            return new MBTNode[] {
                 new MBTArgSetNode("«Set1»")
               , new MBTArgSetNode("«Set2»")
               , new MBTArgNumericNode("«Count»")
               , new MBTArgNumericNode(false, "«Numeric Expression»", false, true)
               , new MBTArgEnumNode(false, "RECURSIVE", new String[]{"RECURSIVE"}, false, true)
            };
         }

      };
      setFunctions.add(new DefaultMutableTreeNode(f));
 /**
  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
  * All Rights Reserved
  * Copyright (C) 2006 Igor Mekterovic
  * All Rights Reserved
  */ 

      f = new MdxSetFunction("DrillupLevel"
                             , I18n.getString("setFunction.drillUpLevel")
                             , "DrillupLevel(«Set»[, «Level»])") {
            /* end of modification for I18n */

         public MBTNode[] getFunctionArguments() {
            return new MBTNode[] {
                 new MBTArgSetNode("«Set»")
               , new MBTArgLevelNode(false, "«Level»", false, true)
            };
         }

      };
      setFunctions.add(new DefaultMutableTreeNode(f));
 /**
  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
  * All Rights Reserved
  * Copyright (C) 2006 Igor Mekterovic
  * All Rights Reserved
  */ 
      f = new MdxSetFunction("DrillupMember"
                             , I18n.getString("setFunction.drillupMembers")
                             , "DrillupMember(«Set1», «Set2»)") {
            /* end of modification for I18n */

         public MBTNode[] getFunctionArguments() {
            return new MBTNode[] {
                 new MBTArgSetNode("«Set1»")
               , new MBTArgSetNode("«Set2»")
            };
         }

      };
      setFunctions.add(new DefaultMutableTreeNode(f));

 /**
  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
  * All Rights Reserved
  * Copyright (C) 2006 Igor Mekterovic
  * All Rights Reserved
  */ 

      f = new MdxSetFunction(  "Except"
                             , I18n.getString("setFunction.except")
                             , "Except(«Set1», «Set2»[, ALL])") {
            /* end of modification for I18n */

         public MBTNode[] getFunctionArguments() {
            return new MBTNode[] {
                 new MBTArgSetNode("«Set1»")
               , new MBTArgSetNode("«Set2»")
               , new MBTArgEnumNode(false, "ALL", new String[]{"ALL"}, false, true)
            };
         }

      };
      setFunctions.add(new DefaultMutableTreeNode(f));

 /**
  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
  * All Rights Reserved
  * Copyright (C) 2006 Igor Mekterovic
  * All Rights Reserved
  */ 
      f = new MdxSetFunction(  "Extract"
                             , I18n.getString("setFunction.extract")
                             , "Extract(«Set», «Dimension»[, «Dimension»...])") {
            /* end of modification for I18n */

         public MBTNode[] getFunctionArguments() {
            return new MBTNode[] {
                 new MBTArgSetNode("«Set1»")
               , new MBTArgDimensionNode(false, "«Dimension»", true, false)
            };
         }

      };
      setFunctions.add(new DefaultMutableTreeNode(f));


 /**
  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
  * All Rights Reserved
  * Copyright (C) 2006 Igor Mekterovic
  * All Rights Reserved
  */ 
      f = new MdxSetFunction(  "Filter"
                             , I18n.getString("setFunction.filter")
                             , "Filter(«Set», «Search Condition»)") {
            /* end of modification for I18n */

         public MBTNode[] getFunctionArguments() {
            return new MBTNode[] {
                 new MBTArgSetNode("«Set»")
               , new MBTArgStringNode("«Search Condition»")
            };
         }

      };
      setFunctions.add(new DefaultMutableTreeNode(f));

 /**
  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
  * All Rights Reserved
  * Copyright (C) 2006 Igor Mekterovic
  * All Rights Reserved
  */ 
      f = new MdxSetFunction(  "Generate"
                             , I18n.getString("setFunction.generate")
                             , "Generate(«Set1», «Set2»[, ALL])") {
            /* end of modification for I18n */

         public MBTNode[] getFunctionArguments() {
            return new MBTNode[] {
                 new MBTArgSetNode("«Set1»")
               , new MBTArgSetNode("«Set2»")
               , new MBTArgEnumNode(false, "ALL", new String[]{"ALL"}, false, true)
            };
         }

      };
      setFunctions.add(new DefaultMutableTreeNode(f));
 /**
  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
  * All Rights Reserved
  * Copyright (C) 2006 Igor Mekterovic
  * All Rights Reserved
  */ 
      f = new MdxSetFunction(  "Generate"
                             , I18n.getString("setFunction.generate")
                             , "Generate(«Set», «String Expression»[, «Delimiter»])") {
            /* end of modification for I18n */

         public MBTNode[] getFunctionArguments() {
            return new MBTNode[] {
                 new MBTArgSetNode("«Set1»")
               , new MBTArgStringNode("«String Expression»")
               , new MBTArgStringNode(false, "«Delimiter»", false, true)
            };
         }

      };
      setFunctions.add(new DefaultMutableTreeNode(f));
 /**
  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
  * All Rights Reserved
  * Copyright (C) 2006 Igor Mekterovic
  * All Rights Reserved
  */ 
      f = new MdxSetFunction(  "Head"
                             , I18n.getString("setFunction.head")
                             , "Head(«Set»[, «Numeric Expression»])") {
            /* end of modification for I18n */

         public MBTNode[] getFunctionArguments() {
            return new MBTNode[] {
                 new MBTArgSetNode("«Set»")
               , new MBTArgNumericNode(false, "«Numeric Expression»", false, true)
            };
         }

      };
      setFunctions.add(new DefaultMutableTreeNode(f));

 /**
  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
  * All Rights Reserved
  * Copyright (C) 2006 Igor Mekterovic
  * All Rights Reserved
  */ 
      f = new MdxSetFunction(  "Hierarchize"
                             ,I18n.getString("setFunction.hierarchize")
                             , "Hierarchize(«Set»[, POST])") {
            /* end of modification for I18n */

         public MBTNode[] getFunctionArguments() {
            return new MBTNode[] {
                 new MBTArgSetNode("«Set»")
               , new MBTArgEnumNode(false, "POST", new String[]{"POST"}, false, true)
            };
         }

      };
      setFunctions.add(new DefaultMutableTreeNode(f));
 /**
  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
  * All Rights Reserved
  * Copyright (C) 2006 Igor Mekterovic
  * All Rights Reserved
  */ 
      f = new MdxSetFunction(  "Intersect"
                             ,I18n.getString("setFunction.intersect")
                             , "Intersect(«Set1», «Set2»[, ALL])") {
            /* end of modification for I18n */

         public MBTNode[] getFunctionArguments() {
            return new MBTNode[] {
                 new MBTArgSetNode("«Set1»")
               , new MBTArgSetNode("«Set2»")
               , new MBTArgEnumNode(false, "ALL", new String[]{"ALL"}, false, true)
            };
         }

      };
      setFunctions.add(new DefaultMutableTreeNode(f));
 /**
  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
  * All Rights Reserved
  * Copyright (C) 2006 Igor Mekterovic
  * All Rights Reserved
  */ 
      f = new MdxSetFunction(  "LastPeriods"
                             ,I18n.getString("setFunction.lastPeriods")
                             , "LastPeriods(«Index»[, «Member»])") {
            /* end of modification for I18n */

         public MBTNode[] getFunctionArguments() {
            return new MBTNode[] {
                 new MBTArgNumericNode("«Index»")
               , new MBTArgMemberNode(false, "«Member»", false, true)
            };
         }

      };
      setFunctions.add(new DefaultMutableTreeNode(f));
 /**
  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
  * All Rights Reserved
  * Copyright (C) 2006 Igor Mekterovic
  * All Rights Reserved
  */ 
      f = new MdxSetFunction(  "Members"
                             ,I18n.getString("setFunction.members")
                             , "«Dimension|Hierarchy|Level».Members") {
            /* end of modification for I18n */

         public MBTNode[] getFunctionArguments() {
            return new MBTNode[] {
               new MBTArgAcceptAllStringNode(true, "«Dimension|Hierarchy|Level»")
            };
         }

      };
      setFunctions.add(new DefaultMutableTreeNode(f));
 /**
  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
  * All Rights Reserved
  * Copyright (C) 2006 Igor Mekterovic
  * All Rights Reserved
  */ 
      f = new MdxSetFunction(  "Members"
                             ,I18n.getString("setFunction.members")
                             , "Members(«String Expression»)") {
            /* end of modification for I18n */

         public MBTNode[] getFunctionArguments() {
            return new MBTNode[] {
               new MBTArgAcceptAllStringNode("«String Expression»")
            };
         }

      };
      setFunctions.add(new DefaultMutableTreeNode(f));
 /**
  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
  * All Rights Reserved
  * Copyright (C) 2006 Igor Mekterovic
  * All Rights Reserved
  */ 
      f = new MdxSetFunction(  "Mtd"
                             , I18n.getString("setFunction.mtd")
                             , "Mtd([«Member»])") {
            /* end of modification for I18n */

         public MBTNode[] getFunctionArguments() {
            return new MBTNode[] {
               new MBTArgMemberNode(false, "«Member»", false, true)
            };
         }

      };
      setFunctions.add(new DefaultMutableTreeNode(f));
 /**
  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
  * All Rights Reserved
  * Copyright (C) 2006 Igor Mekterovic
  * All Rights Reserved
  */ 
      f = new MdxSetFunction(  "NameToSet"
                             ,I18n.getString("setFunction.nameToSet")
                             , "NameToSet(«Member Name»)") {
            /* end of modification for I18n */

         public MBTNode[] getFunctionArguments() {
            return new MBTNode[] {
               new MBTArgStringNode("«Member Name»")
            };
         }

      };
      setFunctions.add(new DefaultMutableTreeNode(f));
 /**
  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
  * All Rights Reserved
  * Copyright (C) 2006 Igor Mekterovic
  * All Rights Reserved
  */ 
      f = new MdxSetFunction(  "NonEmptyCrossjoin"
                             , I18n.getString("setFunction.nonEmptyJoins")
                             , "NonEmptyCrossjoin(«Set1», «Set2»[, «Set3»...][, «Crossjoin Set Count»])") {
            /* end of modification for I18n */

         public MBTNode[] getFunctionArguments() {
            return new MBTNode[] {
                 new MBTArgSetNode("«Set1»")
               , new MBTArgSetNode("«Set2»")
               , new MBTArgSetNode(false, "«Set2»", true, true)
               , new MBTArgNumericNode(false, "«Crossjoin Set Count»", false, true)

            };
         }

      };
      setFunctions.add(new DefaultMutableTreeNode(f));
 /**
  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
  * All Rights Reserved
  * Copyright (C) 2006 Igor Mekterovic
  * All Rights Reserved
  */ 

      f = new MdxSetFunction("Order"
                             , I18n.getString("setFunction.order")
                             , "Order(«Set», {«String Expression» | «Numeric Expression»} [, ASC | DESC | BASC | BDESC])") {
            /* end of modification for I18n */

         public MBTNode[] getFunctionArguments() {
            return new MBTNode[] {
                 new MBTArgSetNode("«Set1»")
               , new MBTArgStringNode("«String Expression» | «Numeric Expression»")
               , new MBTArgEnumNode(false, "ASC | DESC | BASC | BDESC", new String[]{"ASC", "DESC", "BASC", "BDESC"}, false, true)
            };
         }

      };
      setFunctions.add(new DefaultMutableTreeNode(f));
 /**
  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
  * All Rights Reserved
  * Copyright (C) 2006 Igor Mekterovic
  * All Rights Reserved
  */ 

      f = new MdxSetFunction("PeriodsToDate"
                             ,I18n.getString("setFunction.periodsToDate")
                             , "PeriodsToDate([«Level»[, «Member»]])") {
            /* end of modification for I18n */

         public MBTNode[] getFunctionArguments() {
            return new MBTNode[] {
                 new MBTArgLevelNode(false, "«Level»", false, true)
               , new MBTArgMemberNode(false, "«Member»", false, true)
            };
         }

      };
      setFunctions.add(new DefaultMutableTreeNode(f));
 /**
  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
  * All Rights Reserved
  * Copyright (C) 2006 Igor Mekterovic
  * All Rights Reserved
  */ 
      f = new MdxSetFunction(  "Qtd"
                             ,I18n.getString("setFunction.qtd")
                             , "Qtd([«Member»])") {
            /* end of modification for I18n */

         public MBTNode[] getFunctionArguments() {
            return new MBTNode[] {
                 new MBTArgMemberNode(false, "«Member»", false, true)
            };
         }

      };
      setFunctions.add(new DefaultMutableTreeNode(f));
 /**
  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
  * All Rights Reserved
  * Copyright (C) 2006 Igor Mekterovic
  * All Rights Reserved
  */ 
      f = new MdxSetFunction(  "Siblings"
                             ,I18n.getString("setFunction.siblings")
                             , "«Member».Siblings") {
            /* end of modification for I18n */

         public MBTNode[] getFunctionArguments() {
            return new MBTNode[] {
                 new MBTArgMemberNode(true, "«Member»")
            };
         }

      };
      setFunctions.add(new DefaultMutableTreeNode(f));
 /**
  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
  * All Rights Reserved
  * Copyright (C) 2006 Igor Mekterovic
  * All Rights Reserved
  */ 
      f = new MdxSetFunction(  "StripCalculatedMembers"
                             ,I18n.getString("setFunction.stripCalcMembers")
                             , "StripCalculatedMembers(«Set»)") {
            /* end of modification for I18n */

         public MBTNode[] getFunctionArguments() {
            return new MBTNode[] {
               new MBTArgSetNode("«Set»")
            };
         }

      };
      setFunctions.add(new DefaultMutableTreeNode(f));
 /**
  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
  * All Rights Reserved
  * Copyright (C) 2006 Igor Mekterovic
  * All Rights Reserved
  */ 
      f = new MdxSetFunction(  "StrToSet"
                             ,I18n.getString("setFunction.strToSet")
                             , "StrToSet(«String Expression»)") {
            /* end of modification for I18n */

         public MBTNode[] getFunctionArguments() {
            return new MBTNode[] {
               new MBTArgStringNode("«String Expression»")
            };
         }

      };
      setFunctions.add(new DefaultMutableTreeNode(f));
 /**
  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
  * All Rights Reserved
  * Copyright (C) 2006 Igor Mekterovic
  * All Rights Reserved
  */ 
      f = new MdxSetFunction(  "Subset"
                             ,I18n.getString("setFunction.subset")
                             , "Subset(«Set», «Start»[, «Count»])") {
            /* end of modification for I18n */

         public MBTNode[] getFunctionArguments() {
            return new MBTNode[] {
                 new MBTArgSetNode("«Set»")
               , new MBTArgNumericNode("«Start»")
               , new MBTArgNumericNode(false, "«Count»", false, true)
            };
         }

      };
      setFunctions.add(new DefaultMutableTreeNode(f));
 /**
  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
  * All Rights Reserved
  * Copyright (C) 2006 Igor Mekterovic
  * All Rights Reserved
  */ 
      f = new MdxSetFunction(  "Tail"
                             ,I18n.getString("setFunction.tail")
                             , "Tail(«Set»[, «Count»])") {
            /* end of modification for I18n */

         public MBTNode[] getFunctionArguments() {
            return new MBTNode[] {
                 new MBTArgSetNode("«Set»")
               , new MBTArgNumericNode(false, "«Count»", false, true)
            };
         }

      };
      setFunctions.add(new DefaultMutableTreeNode(f));
 /**
  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
  * All Rights Reserved
  * Copyright (C) 2006 Igor Mekterovic
  * All Rights Reserved
  */ 
      f = new MdxSetFunction(  "ToggleDrillState"
                             ,I18n.getString("setFunction.toggleDrillState")
                             , "ToggleDrillState(«Set1», «Set2»[, RECURSIVE])") {
            /* end of modification for I18n */

         public MBTNode[] getFunctionArguments() {
            return new MBTNode[] {
                 new MBTArgSetNode("«Set1»")
               , new MBTArgSetNode("«Set2»")
               , new MBTArgEnumNode(false, "RECURSIVE", new String[]{"RECURSIVE"}, false, true)

            };
         }

      };
      setFunctions.add(new DefaultMutableTreeNode(f));
 /**
  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
  * All Rights Reserved
  * Copyright (C) 2006 Igor Mekterovic
  * All Rights Reserved
  */ 
      f = new MdxSetFunction(  "TopCount"
                             ,I18n.getString("setFunction.topCount")
                             , "TopCount(«Set», «Count»[, «Numeric Expression»])") {
            /* end of modification for I18n */

         public MBTNode[] getFunctionArguments() {
            return new MBTNode[] {
                 new MBTArgSetNode("«Set»")
               , new MBTArgNumericNode("«Count»")
               , new MBTArgNumericNode(false, "«Numeric Expression»", false, true)

            };
         }

      };
      setFunctions.add(new DefaultMutableTreeNode(f));
 /**
  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
  * All Rights Reserved
  * Copyright (C) 2006 Igor Mekterovic
  * All Rights Reserved
  */ 

      f = new MdxSetFunction(  "TopPercent"
                             ,I18n.getString("setFunction.topPercent")
                             , "TopPercent(«Set», «Percentage», «Numeric Expression»)") {
            /* end of modification for I18n */

         public MBTNode[] getFunctionArguments() {
            return new MBTNode[] {
                 new MBTArgSetNode("«Set»")
               , new MBTArgNumericNode("«Percentage»")
               , new MBTArgNumericNode("«Numeric Expression»")

            };
         }

      };
      setFunctions.add(new DefaultMutableTreeNode(f));
 /**
  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
  * All Rights Reserved
  * Copyright (C) 2006 Igor Mekterovic
  * All Rights Reserved
  */ 
      f = new MdxSetFunction(  "TopSum"
                             ,I18n.getString("setFunction.topSum")
                             , "TopSum(«Set», «Value», «Numeric Expression»)") {
            /* end of modification for I18n */

         public MBTNode[] getFunctionArguments() {
            return new MBTNode[] {
                 new MBTArgSetNode("«Set»")
               , new MBTArgNumericNode("«Value»")
               , new MBTArgNumericNode("«Numeric Expression»")

            };
         }

      };
      setFunctions.add(new DefaultMutableTreeNode(f));
 /**
  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
  * All Rights Reserved
  * Copyright (C) 2006 Igor Mekterovic
  * All Rights Reserved
  */ 
      f = new MdxSetFunction(  "Union"
                             ,I18n.getString("setFunction.union")
                             , "Union(«Set1», «Set2»[, ALL])") {
            /* end of modification for I18n */

         public MBTNode[] getFunctionArguments() {
            return new MBTNode[] {
                 new MBTArgSetNode("«Set1»")
               , new MBTArgSetNode("«Set2»")
               , new MBTArgEnumNode(false, "ALL", new String[]{"ALL"}, false, true)

            };
         }

      };
      setFunctions.add(new DefaultMutableTreeNode(f));
 /**
  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
  * All Rights Reserved
  * Copyright (C) 2006 Igor Mekterovic
  * All Rights Reserved
  */ 
      f = new MdxSetFunction(  "VisualTotals"
                             ,I18n.getString("setFunction.visualTotals")
                             , "VisualTotals(«Set», «Pattern»)") {
            /* end of modification for I18n */

         public MBTNode[] getFunctionArguments() {
            return new MBTNode[] {
                 new MBTArgSetNode("«Set»")
               , new MBTArgStringNode("«Pattern»")
            };
         }

      };
      setFunctions.add(new DefaultMutableTreeNode(f));
 /**
  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
  * All Rights Reserved
  * Copyright (C) 2006 Igor Mekterovic
  * All Rights Reserved
  */ 
      f = new MdxSetFunction(  "Wtd"
                             ,I18n.getString("setFunction.wtd")
                             , "Wtd([«Member»])") {
            /* end of modification for I18n */

         public MBTNode[] getFunctionArguments() {
            return new MBTNode[] {
                 new MBTArgMemberNode(false, "«Member»", false, true)
            };
         }

      };
      setFunctions.add(new DefaultMutableTreeNode(f));
 /**
  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
  * All Rights Reserved
  * Copyright (C) 2006 Igor Mekterovic
  * All Rights Reserved
  */ 
      f = new MdxSetFunction(  "Ytd"
                             , I18n.getString("setFunction.ytd")
                             , "Ytd([«Member»])") {
            /* end of modification for I18n */

         public MBTNode[] getFunctionArguments() {
            return new MBTNode[] {
                 new MBTArgMemberNode(false, "«Member»", false, true)
            };
         }

      };
      setFunctions.add(new DefaultMutableTreeNode(f));




// Now, I'm performing a merge sort to put all functions in one node.
      allFunctions = new DefaultMutableTreeNode("All Functions");

      DefaultMutableTreeNode[] heads = new DefaultMutableTreeNode[]{
         arrayFunctions
         , dimensionFunctions
         , hierarchyFunctions
         , levelFunctions
         , memberFunctions
         , numericFunctions
//         , otherFunctions
         , setFunctions
         , stringFunctions
         , tupleFunctions};
      int[] headIndexes = new int[heads.length];
      DefaultMutableTreeNode curr;
      int selected;

      boolean sortDone = false;
      while (!sortDone){
         f = null;
         sortDone = true;
         selected = 0;
         for (int i = 0; i < heads.length; i++) {
            if (headIndexes[i] < heads[i].getChildCount()){
               curr = (DefaultMutableTreeNode) heads[i].getChildAt(headIndexes[i]);
               if (f==null){
                  f = (MdxFunction)curr.getUserObject();
                  selected = i;
               } else if ( ((MdxFunction)curr.getUserObject()).getName().compareTo(f.getName()) < 0 ){
                  f = (MdxFunction)curr.getUserObject();
                  selected = i;
               }
            }

         }
         if (f != null){
            sortDone = false;
            allFunctions.add(new DefaultMutableTreeNode(f));
            headIndexes[selected]++;
         }
      }
      // By default, categorized view is displayed:
      categorized = false;
      toggleTree();

      // well, this was fun...
   }


   /**
    * Toggles a display of the function tree between categorized and alphabetical view.
    */
   private void toggleTree(){
      categorized = !categorized;
      DefaultMutableTreeNode root = ((DefaultMutableTreeNode)tree.getModel().getRoot());
      root.removeAllChildren();
      if (categorized){
         root.add(nonMdxFunctions);
         root.add(arrayFunctions);
         root.add(dimensionFunctions);
         root.add(hierarchyFunctions);
         root.add(levelFunctions);
         root.add(memberFunctions);
         root.add(numericFunctions);
         root.add(setFunctions);
         root.add(stringFunctions);
         root.add(tupleFunctions);
         root.setUserObject("Mdx Functions (categorized)");
      }else{
         root.add(nonMdxFunctions);
         root.add(allFunctions);
         tree.expandPath(new TreePath(allFunctions.getPath()));
         root.setUserObject("Mdx Functions (alphabetical)");
      }
      tree.updateUI();
      tree.repaint();

   }
   /**
    * Inner class that adds the double-clicked function syntax to the mdx editor text field.
    * @author Igor Mekterovic
    * @version 0.3
    */
   class PopupListener extends MouseAdapter {
      public void mouseClicked(MouseEvent e) {
         if ((e.getClickCount()==2)){
            int selRow = tree.getRowForLocation(e.getX(), e.getY());
            TreePath selPath = tree.getPathForLocation(e.getX(), e.getY());
            if ( (selRow != -1)
                && ( ( (DefaultMutableTreeNode) selPath.getLastPathComponent())).getUserObject() instanceof MdxFunction) {

               MdxFunction mdxf = ( (MdxFunction) ( (DefaultMutableTreeNode) (selPath.getLastPathComponent())).getUserObject());

               if (MdxFunctionTree.this.parent != null) {
                  if (parent instanceof MdxEditor) {
                     ((MdxEditor)parent).addTextToCurrentPosition(mdxf.getSyntax());
                  }
               }
            }else if ( (selRow != -1)
                && (   selPath.getLastPathComponent() == tree.getModel().getRoot() )) {
               toggleTree();

            }
         }
      }
   }


   /**
    * Inner class for rendering mdx function tree nodes.
    * @author Igor Mekterovic
    * @version 0.3
    */
	//Commented by Prakash
 //  private class MdxFunctionTreeRenderer extends JLabel implements TreeCellRenderer{
   private class MdxFunctionTreeRenderer extends DefaultTreeCellRenderer { //By Prakash

      public MdxFunctionTreeRenderer() {
         this.setOpaque(false);
      }
      public Component getTreeCellRendererComponent(
                                                      JTree tree,
                                                      Object value,
                                                      boolean sel,
                                                      boolean expanded,
                                                      boolean leaf,
                                                      int row,
                                                      boolean hasFocus) {
		  super.getTreeCellRendererComponent(tree, value, sel,expanded, leaf, row,hasFocus);
         if (( (DefaultMutableTreeNode) value).getUserObject() instanceof MdxFunction ){
            MdxFunction curr = (MdxFunction) ((DefaultMutableTreeNode) value).getUserObject();
            setText(curr.toString());
            setIcon(curr.getIcon());
            setToolTipText(curr.getToolTip());

         }else{
            String s = ((DefaultMutableTreeNode) value).getUserObject().toString();
            setText(s);
            setIcon(null);
            setToolTipText(s);
         }
         return this;
      }

   }


   /**
    * Main class for testing the display of the tree.
    * @param args String[]
    */
   public static void main(String[] args) {
       JFrame frame = new JFrame("Testing FunctionTree...");
       MdxFunctionTree dst = new MdxFunctionTree();
       frame.setContentPane(dst);
       frame.addWindowListener(new WindowAdapter() {
             public void windowClosing(WindowEvent e) {
                System.exit(0);
             }
          });

      frame.pack();
      frame.setVisible(true);
    }

}
