package rex.metadata;

import rex.utils.S;
import rex.metadata.resultelements.Tuple;
/**
 * <p>Title: WHEX</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author igor
 * @version 1.0
 */

public class CubeSlicer {
   private short[] axisOrd;
   private Tuple[] slicerTuple;
   short rowAxisOrd, colAxisOrd;

   public CubeSlicer(short _rowAxisOrd, short _colAxisOrd) {
      this(_rowAxisOrd, _colAxisOrd, null, null);
   }
/* axisOrd and slicerTuple MUST be in sync, e.g.
*  axisNo     slicerTuple
*    2    -->  (<UName>[Measures].[Number of Employees]</UName> , <UName>[Time].[1997].[Q3]</UName> )
*    meaning that this tuple belongs exactly to the Axis2, since they have the same array index, 0.
*/
   public CubeSlicer(short _rowAxisOrd, short _colAxisOrd, short[] _axisOrd, Tuple[] _slicerTuple) {
      rowAxisOrd = _rowAxisOrd;
      colAxisOrd = _colAxisOrd;
      axisOrd = _axisOrd;
      slicerTuple = _slicerTuple;
   }

   public short getSlicerCount(){
      return (short) axisOrd.length;
   }

   public short getAxisSlicer(int i){
      if ((i >= axisOrd.length) || (axisOrd.length == 0)){
         S.out("assert: getSlicerAxis(" + i + ") while axisOrd.length = " + axisOrd.length);
      }
      return axisOrd[i];
   }
//   public String getUniqueNameSlicer(int i){
//      if ((i >= axisOrd.length) || (axisOrd.length == 0)){
//         S.out("assert: getUniqueNameSlicer(" + i + ") while axis.length = " + axisOrd.length);
//      }
//      return uniqueName[i];
//   }
//
//   public String getUniqueNameSlicerForAxisNo(int axisNo){
//      for(int i=0; i < axisOrd.length; i++){
//         if (i == axisNo) return uniqueName[i];
//      }
//      S.out("assert: getUniqueNameSlicerForAxisNo(" + axisNo + ") returned null !!");
//      return null;
//   }

   public short getRowAxisOrdinal(){
      return rowAxisOrd;
   }
   public String getRowAxisName(){
      return "Axis" + rowAxisOrd;
   }


   public short getColAxisOrdinal(){
      return colAxisOrd;
   }

   public String getColAxisName(){
      return "Axis" + colAxisOrd;
   }

   public Tuple getTupleForAxisNo(int axisNo){
      //S.out("axisOrd.length = " + axisOrd.length);
      for(int i=0; i < axisOrd.length; i++){
         //S.out("checking: " + axisOrd[i] + " == " + axisNo + " ?");
         if (axisOrd[i] == axisNo) return slicerTuple[i];
      }
      S.out("assert: getTupleForAxisNo(" + axisNo + ") returned null !!");
      return null;
   }

}