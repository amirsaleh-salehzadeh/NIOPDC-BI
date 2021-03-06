package rex.graphics.charts;

import org.jfree.data.DefaultCategoryDataset;
import rex.metadata.ExecuteResult;
import rex.metadata.CubeSlicer;
import java.util.LinkedList;
import rex.metadata.resultelements.Axis;
import rex.utils.S;
import rex.metadata.resultelements.HierarchyInfo;
/**
 * <p>Title: WHEX</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author igor
 * @version 1.0
 */

public class ExecuteResultToCategoryDatasetBridge extends DefaultCategoryDataset {
   private ExecuteResult executeResult;
   private CubeSlicer    cubeSlicer;
   private Axis          rowAxis, columnAxis;
   public ExecuteResultToCategoryDatasetBridge(  ExecuteResult _executeResult
                                               , CubeSlicer _cubeSlicer) {
      executeResult = _executeResult;
      cubeSlicer    = _cubeSlicer;
      rowAxis       = executeResult.getAxis(cubeSlicer.getColAxisName());
      columnAxis    = executeResult.getAxis(cubeSlicer.getRowAxisName());
//      S.out("INIT ExecuteResultToCategoryDatasetBridge\n  executeResult.hashCode=" + executeResult.hashCode());
   }

   public String getHorizontalChartCaption(){
      return getChartCaption(rowAxis);
   }
   public String getVerticalChartCaption(){
      return getChartCaption(columnAxis);
   }
   private String getChartCaption(Axis axis){
      StringBuffer rv = new StringBuffer("");
//      Tuple t = axis.getTupleAt(0);
//      if (t==null) return "no data!";
      for(int i=0; i < axis.getHierarchyInfoCount(); i++){
         rv.append(axis.getHierarchyInfoAt(i).getCaption());
         if (i != axis.getHierarchyInfoCount() - 1){
            rv.append(" - ");
         }
      }
      return rv.toString();
   }


// don't forget: the chart is rotated in comparioson to table:
   public int getRowCount(){
//      S.out("getRowCount called. returning " + rowAxis.getTupleCount());
      return rowAxis.getTupleCount();
   }
   public int getColumnCount(){
//      S.out("getColumnCount called. returning " + columnAxis.getTupleCount());
//      S.out("execute result is:" + executeResult.hashCode());
      return columnAxis.getTupleCount();
   }
   public java.lang.Number getValue(int row, int column){
      // note that row & col have changed places
//      S.out("getValue(" + row + "," + column + ") called."
//           + " returning " + executeResult.getRowCol(column, row, cubeSlicer).getCellValue());
      String retVal = executeResult.getRowCol(column, row, cubeSlicer).getCellValue();
      if (retVal != null && !retVal.equals("")){
         return new Double(retVal);
      }
      return new Double(0);
   }

/*
    public java.lang.Comparable getRowKey(int row)
    Returns a row key.

    Parameters:
    row - the row index (zero-based).
    Returns:
    the row key.
*/
    public java.lang.Comparable getRowKey(int row){
//       S.out("getRowKey called with row = " + row + " returning " + rowAxis.getTupleAt(row).getShortCaption());
       return rowAxis.getTupleAt(row).getShortCaption();
    }

/*
    getRowIndex
    public int getRowIndex(java.lang.Comparable key)
    Returns the row index for a given key.

    Parameters:
    key - the row key.
    Returns:
    the row index.
*/
    public int getRowIndex(java.lang.Comparable key){
//       S.out("getRowIndex called with key = " + key);
       for(int i=0; i<rowAxis.getTupleCount();i++){
          if (((String)key).equals(rowAxis.getTupleAt(i).getShortCaption())){
             return i;
          }
       }
       return -1;
    }


/*
    --------------------------------------------------------------------------------

    getRowKeys
    public java.util.List getRowKeys()
    Returns the row keys.
    Returns:
    the keys.
*/
    public java.util.List getRowKeys(){
//       S.out("getRowKeys called");
       LinkedList retVal = new LinkedList();
       for(int i=0; i < getRowCount(); i++){
          retVal.add(rowAxis.getTupleAt(i).getShortCaption());
       }
       return retVal;
    }

/*
    --------------------------------------------------------------------------------

    getColumnKey
    public java.lang.Comparable getColumnKey(int column)
    Returns a column key.

    Parameters:
    column - the column index (zero-based).
    Returns:
    the column key.
*/
   public java.lang.Comparable getColumnKey(int column){
//      S.out("getColumnKey called with column = " + column
//            + " returning " + columnAxis.getTupleAt(column).getShortCaption());
      return columnAxis.getTupleAt(column).getShortCaption();
   }


/*
    --------------------------------------------------------------------------------

    getColumnIndex
    public int getColumnIndex(java.lang.Comparable key)
    Returns the column index for a given key.

    Parameters:
    key - the column key.
    Returns:
    the column index.
*/
   public int getColumnIndex(java.lang.Comparable key){
//      S.out("getColumnIndex called with key = " + key);
      for(int i=0; i < columnAxis.getTupleCount(); i++){
         if (((String)key).equals(columnAxis.getTupleAt(i).getShortCaption())){
            return i;
         }
      }
      return -1;

   }



/*
    --------------------------------------------------------------------------------

    getColumnKeys
    public java.util.List getColumnKeys()
    Returns the column keys.

    Returns:
    the keys.
*/
   public java.util.List getColumnKeys(){
//      S.out("getRowKeys called");
      LinkedList retVal = new LinkedList();
      for(int i=0; i < getColumnCount(); i++){
         retVal.add(columnAxis.getTupleAt(i).getShortCaption());
      }
      return retVal;
   }


/*
    --------------------------------------------------------------------------------

    getValue
    public java.lang.Number getValue(java.lang.Comparable rowKey,
                                     java.lang.Comparable columnKey)
    Returns the value for a pair of keys.
    This method should return null if either of the keys is not found.


    Parameters:
    rowKey - the row key.
    columnKey - the column key.
    Returns:
    the value.

*/
   public java.lang.Number getValue(java.lang.Comparable rowKey,
                                     java.lang.Comparable columnKey) {
       S.out("getValue zajebani called");
      return getValue(  getRowIndex(rowKey)
                      , getColumnIndex(columnKey));
   }



}