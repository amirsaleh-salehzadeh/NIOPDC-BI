package rex.graphics.tables;

import rex.metadata.CubeSlicer;
import rex.metadata.ExecuteResult;
import javax.swing.table.AbstractTableModel;
import rex.utils.S;
import java.text.NumberFormat;

/**
 * <p>Title: WHEX</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author igor
 * @version 1.0
 */

public class CellTableModel extends AbstractTableModel{
   CubeSlicer cubeSlicer;
   ExecuteResult result;
   private boolean showRowTotalsOn;
   private boolean showColumnTotalsOn;

   public CellTableModel(ExecuteResult _result
                         , CubeSlicer _cubeSlicer
                         , boolean _showColumnTotalsOn
                         , boolean _showRowTotalsOn) {
      result = _result;
      cubeSlicer = _cubeSlicer;
      showRowTotalsOn = _showRowTotalsOn;
      showColumnTotalsOn = _showColumnTotalsOn;
   }

    public int getColumnCount() {
//       int cc = result.getTupleCount(cubeSlicer.getColAxisOrdinal())
//               + (showRowTotalsOn ? 2 :0);
//       S.out("getColumnCount (showRowTotalsOn = " + showRowTotalsOn
//             + ", showColumnTotalsOn = " + showColumnTotalsOn + ")");
//       S.out("getColumnCount returning " + cc );
       return result.getTupleCount(cubeSlicer.getColAxisOrdinal())
               + (showRowTotalsOn ? 2 :0);
    }

    public int getRowCount() {
//       int rc = result.getTupleCount(cubeSlicer.getRowAxisOrdinal())
//              + (showColumnTotalsOn ? 2 :0);
//       S.out("getRowCount (showRowTotalsOn = " + showRowTotalsOn
//             + ", showColumnTotalsOn = " + showColumnTotalsOn + ")");
//       S.out("getRowCount returning " + rc );
       return result.getTupleCount(cubeSlicer.getRowAxisOrdinal())
              + (showColumnTotalsOn ? 2 :0);
    }

    public String getColumnName(int col) {
        return null;
    }
    public boolean isSumCell(int row, int col) {
       if ( (showRowTotalsOn  && (col == getColumnCount() - 2))
            || (showColumnTotalsOn && (row == getRowCount() - 2))){
          return true;
       }
       return false;
    }
    public boolean isAverageCell(int row, int col) {
       if ( (showRowTotalsOn  && (col == getColumnCount() - 1))
            || (showColumnTotalsOn && (row == getRowCount() - 1))){
          return true;
       }
       return false;
    }
    private String formatSum(Double d){
       NumberFormat nf = NumberFormat.getInstance();
       nf.setMaximumFractionDigits(2);
       return nf.format(d);
    }
    private String formatAverage(Double d){
       NumberFormat nf = NumberFormat.getInstance();
       nf.setMaximumFractionDigits(2);
       nf.setMinimumFractionDigits(2);
       return nf.format(d);
    }

    public Object getValueAt(int row, int col) {
//       S.out("called getValueAt(" +  row + ", " + col + ")");
       if (showRowTotalsOn){
          if(showColumnTotalsOn){
             if (   (col == getColumnCount()-2)
                 && (row == getRowCount()-2)   ){
                return formatSum(result.getTotalSum(cubeSlicer));
             }else if((col == getColumnCount()-1)
                   && (row == getRowCount()-1) ){
                return formatAverage(result.getTotalAverage(cubeSlicer));
             }else if ((col >= getColumnCount()-2)
                        && (row >= getRowCount()-2)){
                return null;
             }
          }
          if (col == getColumnCount() - 2) {
             return formatSum(result.getSumForRow(row, cubeSlicer));
          }else if (col == getColumnCount() - 1){
             return formatAverage(result.getAverageForRow(row, cubeSlicer));
          }
       }
       if (showColumnTotalsOn){
          if (row == getRowCount()-2){
             return formatSum(result.getSumForColumn(col, cubeSlicer));
          }else if (row == getRowCount()-1){
             return formatAverage(result.getAverageForColumn(col, cubeSlicer));
          }
       }
       return result.getRowCol(row, col, cubeSlicer);
    }

    public boolean isShowRowTotalsOn(){
       return showRowTotalsOn;
    }
    public void setShowRowTotalsOn(boolean _showRowTotalsOn){
       showRowTotalsOn = _showRowTotalsOn;
//       S.out("firing setShowRowTotalsOn (showRowTotalsOn = " + showRowTotalsOn
//             + ", showColumnTotalsOn = " + showColumnTotalsOn + ")");
       fireTableStructureChanged();
    }
    public boolean isShowColumnTotalsOn(){
       return showColumnTotalsOn;
    }
    public void setShowColumnTotalsOn(boolean _showColumnTotalsOn){
       showColumnTotalsOn = _showColumnTotalsOn;
//       S.out("firing isShowColumnTotalsOn (showRowTotalsOn = " + showRowTotalsOn
//             + ", showColumnTotalsOn = " + showColumnTotalsOn + ")");
       fireTableStructureChanged();
    }

    /*
     * JTable uses this method to determine the default renderer/
     * editor for each cell.  If we didn't implement this method,
     * then the last column would contain text ("true"/"false"),
     * rather than a check box.
     */
    /*
    public Class getColumnClass(int c) {

    }
    */

    /*
     * Don't need to implement this method unless your table's
     * editable.
     */
    public boolean isCellEditable(int row, int col) {
       return false;
    }
/*
*        my cell table cannot be changed (for now, at least)
*       public void setValueAt(Object value, int row, int col) {
*       }
*/

}