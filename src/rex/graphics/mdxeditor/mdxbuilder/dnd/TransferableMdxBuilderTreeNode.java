package rex.graphics.mdxeditor.mdxbuilder.dnd;

import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import rex.graphics.mdxeditor.mdxbuilder.nodes.*;
import rex.graphics.dimensiontree.elements.DimensionTreeElement;
import rex.graphics.mdxeditor.mdxfunctions.*;


/**
 *
 * Class that wraps the dragged data. It is used to transfer data:
 * <br>1. from DimensionTree
 * <br>2. from FunctionTree
 * <br>3. from MdxBuilderTree  (hence the multiple constructors)
 * <br> -> to MdxBuilderTree (or anywhere else).
 * <br>Also, this class hold public String constants that represent all data flavors (classes) that can be dragged in this app.
 * @author Igor Mekterovic
 * @version 0.3
 */
public class TransferableMdxBuilderTreeNode implements Transferable {
//   MDX_BUILDER_TREE_NODE_FLAVOR_STRING = DataFlavor.javaJVMLocalObjectMimeType
//                                                                + "; class=rex.graphics.mdxeditor.mdxbuilder.nodes.MdxBuilderTreeNode"

  public static String DIMENSION_TREE_NODE_LEVEL_FLAVOR_STRING = DataFlavor.javaJVMLocalObjectMimeType
                                                             + "; class=rex.graphics.dimensiontree.elements.LevelElement"
                     , DIMENSION_TREE_NODE_MEASURE_FLAVOR_STRING = DataFlavor.javaJVMLocalObjectMimeType
                                                             + "; class=rex.graphics.dimensiontree.elements.MeasureElement"
                     , DIMENSION_TREE_NODE_MEMBER_FLAVOR_STRING = DataFlavor.javaJVMLocalObjectMimeType
                                                              + "; class=rex.graphics.dimensiontree.elements.MemberElement"
                     , DIMENSION_TREE_NODE_HIERARCHY_FLAVOR_STRING = DataFlavor.javaJVMLocalObjectMimeType
                                                              + "; class=rex.graphics.dimensiontree.elements.HierarchyElement"
                     , DIMENSION_TREE_NODE_DIMENSION_FLAVOR_STRING = DataFlavor.javaJVMLocalObjectMimeType
                                                              + "; class=rex.graphics.dimensiontree.elements.DimensionElement"
                     , MDX_BUILDER_TREE_CALCULATED_MEMBER_NODE_FLAVOR_STRING = DataFlavor.javaJVMLocalObjectMimeType
                                                            + "; class=rex.graphics.mdxeditor.mdxbuilder.nodes.MBTCalculatedMemberNode"
                     , MDX_BUILDER_TREE_NAMED_SET_NODE_FLAVOR_STRING = DataFlavor.javaJVMLocalObjectMimeType
                                                            + "; class=rex.graphics.mdxeditor.mdxbuilder.nodes.MBTNamedSetNode"

//   **** mdx function flavors:

                     , MDX_BUILDER_TREE_DIMENSION_FUNCTION_NODE_FLAVOR_STRING = DataFlavor.javaJVMLocalObjectMimeType
                                                            + "; class=rex.graphics.mdxeditor.mdxfunctions.MdxDimensionFunction"
                     , MDX_BUILDER_TREE_HIERARCHY_FUNCTION_NODE_FLAVOR_STRING = DataFlavor.javaJVMLocalObjectMimeType
                                                            + "; class=rex.graphics.mdxeditor.mdxfunctions.MdxHierarchyFunction"
                     , MDX_BUILDER_TREE_LEVEL_FUNCTION_NODE_FLAVOR_STRING = DataFlavor.javaJVMLocalObjectMimeType
                                                            + "; class=rex.graphics.mdxeditor.mdxfunctions.MdxLevelFunction"
                     , MDX_BUILDER_TREE_ARRAY_FUNCTION_NODE_FLAVOR_STRING = DataFlavor.javaJVMLocalObjectMimeType
                                                            + "; class=rex.graphics.mdxeditor.mdxfunctions.MdxArrayFunction"
                     , MDX_BUILDER_TREE_LOGICAL_FUNCTION_NODE_FLAVOR_STRING = DataFlavor.javaJVMLocalObjectMimeType
                                                            + "; class=rex.graphics.mdxeditor.mdxfunctions.MdxLogicalFunction"
                     , MDX_BUILDER_TREE_TUPLE_FUNCTION_NODE_FLAVOR_STRING = DataFlavor.javaJVMLocalObjectMimeType
                                                            + "; class=rex.graphics.mdxeditor.mdxfunctions.MdxTupleFunction"
                     , MDX_BUILDER_TREE_MEMBER_FUNCTION_NODE_FLAVOR_STRING = DataFlavor.javaJVMLocalObjectMimeType
                                                            + "; class=rex.graphics.mdxeditor.mdxfunctions.MdxMemberFunction"
                     , MDX_BUILDER_TREE_SET_FUNCTION_NODE_FLAVOR_STRING = DataFlavor.javaJVMLocalObjectMimeType
                                                            + "; class=rex.graphics.mdxeditor.mdxfunctions.MdxSetFunction"
                     , MDX_BUILDER_TREE_STRING_FUNCTION_NODE_FLAVOR_STRING = DataFlavor.javaJVMLocalObjectMimeType
                                                            + "; class=rex.graphics.mdxeditor.mdxfunctions.MdxStringFunction"
                     , MDX_BUILDER_TREE_NUMERIC_FUNCTION_NODE_FLAVOR_STRING = DataFlavor.javaJVMLocalObjectMimeType
                                                            + "; class=rex.graphics.mdxeditor.mdxfunctions.MdxNumericFunction"
                     , MDX_BUILDER_TREE_OPERATOR_FUNCTION_NODE_FLAVOR_STRING = DataFlavor.javaJVMLocalObjectMimeType
                                                            + "; class=rex.graphics.mdxeditor.mdxfunctions.MdxOperatorFunction"
                      , MDX_BUILDER_TREE_MAKE_TUPLE_FUNCTION_NODE_FLAVOR_STRING = DataFlavor.javaJVMLocalObjectMimeType
                                                            + "; class=rex.graphics.mdxeditor.mdxfunctions.MdxMakeTupleFunction";








  DataFlavor flavors[];
  Object data;

  /**
   * Constructor for transferring DimensionTreeElement objects from the DimensionTree
   * @param userObject DimensionTreeElement
   */
  public TransferableMdxBuilderTreeNode(DimensionTreeElement userObject) {
     try {

        flavors = new DataFlavor[] { new DataFlavor(DataFlavor.javaJVMLocalObjectMimeType + ";class=" + userObject.getClass().getName() )
                         };
     } catch (ClassNotFoundException e){
         /**
          * Commented, Don't want to print trace log on console.
          * by Prakash. 10-05-2007.
          */
        //e.printStackTrace();//Commented by Prakash
         /*
          * End of modification.
          */
     }
     data = userObject;
  }
  /**
   * Constructor for transferring MBTNode objects from MBT. Note that MBTNodes that are MBTFunctionNodes have their own constructor.
   * @see rex.graphics.mdxeditor.mdxbuilder.dnd.TransferableMdxBuilderTreeNode#TransferableMdxBuilderTreeNode(MBTFunctionNode)
   * @param userObject MBTNode
   */
  public TransferableMdxBuilderTreeNode(MBTNode userObject) {
     try {

        flavors = new DataFlavor[] { new DataFlavor(DataFlavor.javaJVMLocalObjectMimeType + ";class=" + userObject.getClass().getName() )
                         };
     } catch (ClassNotFoundException e){
         /**
          * Commented, Don't want to print trace log on console.
          * by Prakash. 10-05-2007.
          */
        //e.printStackTrace();//Commented by Prakash
         /*
          * End of modification.
          */
     }
     data = userObject;
  }

  /**
   * Constructor for transferring MBTFunctionNodes objects from MBT.
   * @param function MBTFunctionNode
   */
  public TransferableMdxBuilderTreeNode(MBTFunctionNode function) {
     try {
//  Functions can be dragged from MBT to MBT. When a function is dropped from the function tree to MBT,
//  MBTFunctionNode remembers its flavor so that when I later drag it from  MBT to MBT it can set its flavor (function's return value).
        flavors = new DataFlavor[] { new DataFlavor(function.getFlavor())};
     } catch (ClassNotFoundException e){
         /**
          * Commented, Don't want to print trace log on console.
          * by Prakash. 10-05-2007.
          */
        //e.printStackTrace();//Commented by Prakash
         /*
          * End of modification.
          */
     }
     data = function;
  }

  /**
   * Constructor for transferring MdxFunction objects from MdxFunctionTree.
   * @param userObject MdxFunction
   */
  public TransferableMdxBuilderTreeNode(MdxFunction userObject) {
     try {
//      I'm doing this by hand because these classes get instantiated as inner classes (they override getFunctionArguments method)
//      and therefore userObject.getClass().getName() returns ...MdxFunctionTree.$1, MdxFunctionTree.$2 and so on...

        if (userObject instanceof MdxSetFunction){
           flavors = new DataFlavor[] {
                        new DataFlavor(DataFlavor.javaJVMLocalObjectMimeType + ";class=rex.graphics.mdxeditor.mdxfunctions.MdxSetFunction")
                     };
        }else if (userObject instanceof MdxStringFunction){
           flavors = new DataFlavor[] {
                        new DataFlavor(DataFlavor.javaJVMLocalObjectMimeType + ";class=rex.graphics.mdxeditor.mdxfunctions.MdxStringFunction")
                     };

        }else if (userObject instanceof MdxNumericFunction) {
           flavors = new DataFlavor[] {
                      new DataFlavor(DataFlavor.javaJVMLocalObjectMimeType + ";class=rex.graphics.mdxeditor.mdxfunctions.MdxNumericFunction")
           };
        }else if (userObject instanceof MdxDimensionFunction) {
           flavors = new DataFlavor[] {
                      new DataFlavor(DataFlavor.javaJVMLocalObjectMimeType + ";class=rex.graphics.mdxeditor.mdxfunctions.MdxDimensionFunction")
           };
        }else if (userObject instanceof MdxHierarchyFunction) {
           flavors = new DataFlavor[] {
                      new DataFlavor(DataFlavor.javaJVMLocalObjectMimeType + ";class=rex.graphics.mdxeditor.mdxfunctions.MdxHierarchyFunction")
           };
        }else if (userObject instanceof MdxLevelFunction) {
           flavors = new DataFlavor[] {
                      new DataFlavor(DataFlavor.javaJVMLocalObjectMimeType + ";class=rex.graphics.mdxeditor.mdxfunctions.MdxLevelFunction")
           };
        }else if (userObject instanceof MdxArrayFunction) {
           flavors = new DataFlavor[] {
                      new DataFlavor(DataFlavor.javaJVMLocalObjectMimeType + ";class=rex.graphics.mdxeditor.mdxfunctions.MdxArrayFunction")
           };
        }else if (userObject instanceof MdxLogicalFunction) {
           flavors = new DataFlavor[] {
                      new DataFlavor(DataFlavor.javaJVMLocalObjectMimeType + ";class=rex.graphics.mdxeditor.mdxfunctions.MdxLogicalFunction")
           };
        }else if (userObject instanceof MdxTupleFunction) {
           flavors = new DataFlavor[] {
                      new DataFlavor(DataFlavor.javaJVMLocalObjectMimeType + ";class=rex.graphics.mdxeditor.mdxfunctions.MdxTupleFunction")
           };
        }else if (userObject instanceof MdxMemberFunction) {
           flavors = new DataFlavor[] {
                      new DataFlavor(DataFlavor.javaJVMLocalObjectMimeType + ";class=rex.graphics.mdxeditor.mdxfunctions.MdxMemberFunction")
           };
        }else if (userObject instanceof MdxOperatorFunction) {
           flavors = new DataFlavor[] {
                      new DataFlavor(DataFlavor.javaJVMLocalObjectMimeType + ";class=rex.graphics.mdxeditor.mdxfunctions.MdxOperatorFunction")
           };
        }else if (userObject instanceof MdxMakeTupleFunction) {
           flavors = new DataFlavor[] {
                      new DataFlavor(DataFlavor.javaJVMLocalObjectMimeType + ";class=rex.graphics.mdxeditor.mdxfunctions.MdxMakeTupleFunction")
           };
        }



     } catch (ClassNotFoundException e){
         /**
          * Commented, Don't want to print trace log on console.
          * by Prakash. 10-05-2007.
          */
        //e.printStackTrace();//Commented by Prakash
         /*
          * End of modification.
          */
     }
     data = userObject;
  }
// ADD DIFFERENT DATA FLAVOURS CONSTRUCTORS

  /**
   * Returns data flavors the dragged object has.
   * @return DataFlavor[]
   */
  public synchronized DataFlavor[] getTransferDataFlavors() {
     return flavors;
  }

  /**
   * Returns true if specified data flavor is supported, otherwise false.
   * @param flavor DataFlavor
   * @return boolean
   */
  public boolean isDataFlavorSupported(DataFlavor flavor) {
     for (int i=0; flavors!= null && (i < flavors.length); i++){
        if (flavor.isMimeTypeEqual(flavors[i])){
           return true;
        }
     }
     return false;
//    return (flavor.getRepresentationClass() == MdxBuilderTreeNode.class);
  }

  /**
   * Returns dragged data if the specified data flavor is supported.
   * @param flavor DataFlavor
   * @throws UnsupportedFlavorException
   * @throws IOException
   * @return Object
   */
  public synchronized Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
    if (isDataFlavorSupported(flavor)) {
//       S.out("TransferableMdxBuilderTreeNode -> returning " + data);
       return (Object) data;
    }else{
       throw new UnsupportedFlavorException(flavor);
    }
  }
}


