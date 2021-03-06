package rex.metadata;


import org.w3c.dom.NodeList;

import rex.exceptions.RexXMLADiscoverException;
import rex.exceptions.RexXMLAExecuteException;
import rex.metadata.resultelements.*;
import java.util.HashMap;


import rex.utils.*;

import java.util.Iterator;
import javax.swing.JPanel;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

/**
 * <p>Title: WHEX</p>
 * <p>Description: this is a heavy-duty class for parsing result returned by execute command</p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author igor
 * @version 1.0
 */

public class ExecuteResult {
   Document resultDOM;
   private HashMap axes;
   private Cell[] cells;
   Query query;
   Cell defaultCell;

   public ExecuteResult(Document d, Query _query) throws ExecuteResultParseException{
      query = _query;
      resultDOM = d;
//      S.out("result=\n" + r);
      parseResult();
   }

   public void parseResult() throws ExecuteResultParseException{
      int i, j;
      
      /**
       *   Copyright (C) 2006 CINCOM SYSTEMS, INC.
       *   All Rights Reserved
       *   Copyright (C) 2006 Igor Mekterovic
       *   All Rights Reserved
       */
      
      /*
       * Lines Inserted by Prakash.
       * Cincom Systems.
       * Throw Exception if found SOAP-ENV:Fault in in soap response.
       */
      final NodeList faultList = resultDOM.getElementsByTagName("SOAP-ENV:Fault");
      if (faultList != null && faultList.getLength() > 0) 
      {
          StringBuffer sBuffer = new StringBuffer("");
          NodeList faultStringList = resultDOM.getElementsByTagName("faultstring");
          if (faultStringList != null && faultStringList.getLength() > 0) 
          {
              sBuffer.append("\n  faultstring = " + DOM.getTextFromDOMElement(faultStringList.item(0)));
          }
          faultStringList = resultDOM.getElementsByTagName("desc");
          if (faultStringList != null && faultStringList.getLength() > 0) 
          {
              sBuffer.append("\n  desc = " + DOM.getTextFromDOMElement(faultStringList.item(0)));
          }
          //sBuffer.append("\nError occured!");
          /*NodeList nl = faultList.item(0).getChildNodes();
          for(int i=0; i < nl.getLength(); i++)
          {               
              if (nl.item(i).getNodeType() == 1)
              {
                  if (nl.item(i).getNodeName().equals("faultstring")) 
                  {
                      sBuffer.append("\n  faultstring = " + DOM.getTextFromDOMElement(nl.item(i)));
                  }
                  else if (nl.item(i).getNodeName().equals("detail")) 
                  {
                      Node errorNode = nl.item(i).getChildNodes().item(0);
                      NodeList errorNodeList = errorNode.getChildNodes();
                      for(int count=0; count < errorNodeList.getLength(); count++)
                      {
                          if (errorNodeList.item(count).getNodeName().equals("desc")) 
                          {
                              sBuffer.append("\n  desc = " + DOM.getTextFromDOMElement(errorNodeList.item(i)));
                          }
                      }
                  }
              }
          }*/
          throw new ExecuteResultParseException(sBuffer.toString());
      }
      NodeList errList = resultDOM.getElementsByTagName("Error");      
      if (errList != null && errList.getLength()>0){

         StringBuffer sb = new StringBuffer("");
         // Mondrian:
         // <Error ErrorCode="rex.mondrian.olap.MondrianException" Description="Mondrian Error:Syntax error at line 11, column 16, token &#39;fsdfsd&#39;" Source="Mondrian" Help=""/>
         // AS:
         // <Error
         //     ErrorCode="2147483653"
         //     Description="An unexpected error has occurred"
         //     Source="XML for Analysis Provider"
         //     HelpFile=""
         //     />

         for (i = 0; i < errList.getLength(); i++) {
            //sb.append("\nError occured!");
            NamedNodeMap attrs = errList.item(i).getAttributes();
            for (j=0; j< attrs.getLength(); j++){
               sb.append("\n  " + attrs.item(j).getNodeName() + " = " +  attrs.item(j).getNodeValue());
            }
         }
         throw new ExecuteResultParseException(sb.toString());
      }
      NodeList axList  = resultDOM.getElementsByTagName("AxisInfo");
      if (axList != null) {
         axes = new HashMap(axList.getLength() + 1, 1);
         for (i = 0; i < axList.getLength(); i++) {
            Axis a = new Axis(axList.item(i));
            axes.put(a.getName(), a);
         }
      }
      //debugPrintAxes();
      axList = resultDOM.getElementsByTagName("Axes");

      axList  = resultDOM.getElementsByTagName("Axes").item(0).getChildNodes();
      //DOM.dumpChildNodes(resultDOM.getResultAsDocument().getElementsByTagName("Axes").item(0));
      if (axList != null) {
         for (i = 0; i < axList.getLength(); i++) {
            // dispatch node to the proper axis object
            // (they probably appear in the same order as they were put in a HashMap
            // but why take a risk, I'll dispatch it accoriding to the key:name (Axis0, Axis1,...,SlicerAxis))
            // DOM.dumpNodeAttributes(axList.item(i).getAttributes());
            if (axList.item(i).getNodeType() == DOM.ELEMENT_TYPE ){
               //DOM.dumpNodeAttributes(axList.item(i).getAttributes());
               //S.out("DEBUG = " + axList.item(i).getAttributes().getNamedItem("name").getNodeValue());
               ((Axis) axes.get(axList.item(i).getAttributes().getNamedItem("name").getNodeValue())).loadMemberTuples(DOM.getFirstChildNodeWithName( axList.item(i), "Tuples"));
            }
         }
      }

      // A provider may choose to provide deafult values for cells
      // (in a case of many same values this can provide a smaller XML)

      NodeList cellInfo = resultDOM.getElementsByTagName("CellInfo");
      defaultCell = new Cell(cellInfo.item(0));
      cellInfo = cellInfo.item(0).getChildNodes();
//      if (cellInfo != null){
//         for (i = 0; i < cellInfo.getLength(); i++) {
//            if (cellInfo.item(i).getNodeType() == DOM.ELEMENT_TYPE
//                && cellInfo.item(i).getNodeName().equals("Default")){
//               defaultCellValue = DOM.getTextFromDOMElement(cellInfo.item(i));
//               break;
//            }
//         }
//      }
      NodeList cl  = resultDOM.getElementsByTagName("CellData");
      cl = cl.item(0).getChildNodes();
//      DOM.dumpChildNodes(cl.item(0));
      if (cl != null){
         // some of the cells will not be omitted, either null or default values
         // see specification 1.1. page 24
         cells = new Cell[axisMemberCountProduct()];
//         S.out("axisMemberCountProduct() = " + axisMemberCountProduct());
         Cell c;
         j=0;
         for (i = 0; i < cl.getLength(); i++) {
            if (cl.item(i).getNodeType() == DOM.ELEMENT_TYPE){
               c = new Cell(cl.item(i), defaultCell.getDefaultCellValue());
               cells[c.getCellOrdinal()] = c;
               //S.out("" + j + "  value = " +  cells[j]);
               j++;
            }
         }
      }

   }

   private int axisMemberCountProduct(){
      int i, retVal = 1;
      Axis a;
      Iterator it = axes.values().iterator();
      while(it.hasNext()){
         a = (Axis)it.next();
//         S.out("" + a.getName() + ".getMemberCount()=" + a.getTupleCount());
         if (!a.getName().equalsIgnoreCase("SlicerAxis")){
            retVal *= a.getTupleCount();
         }
      }
    return retVal;
   }

   public int getTupleCount(int axisNo){
      return ((Axis)axes.get("Axis" + axisNo)).getTupleCount();
   }

   private void debugPrintAxes(){
      Iterator it = axes.values().iterator();
      while(it.hasNext()){
         Axis a = (Axis)it.next();
         S.out("" + a);
      }
   }

   public void debugPrintAxesTable(){
      Iterator it = axes.values().iterator();
      while(it.hasNext()){
         Axis a = (Axis)it.next();
         a.dumpTable();
      }
   }

//   public void parseResult(ClsXMLAProxExecuteResult r){
//      resultDOM = r.getResultAsDocument();
//      this.parseResult();
//   }

   public int getAxisCount(){
      if (axes != null)
         return axes.size() - 1; // don't count SlicerAxis
      else
         return 0;
   }

   public Axis getAxis(String axName){
      if (axes != null)
         return (Axis)axes.get(axName);
      else
         return null;
   }

   public Cell getRowCol(int row, int col, CubeSlicer cs){
      int ordinal = 0;
      int E = 1;
      for(int i=0; i < getAxisCount(); i++){
         // Axis0, Axis1
         if (i == cs.getRowAxisOrdinal()){
            ordinal += row * E;
         }else if(i == cs.getColAxisOrdinal()){
            ordinal += col * E;
         }else{
            ordinal += getTupleOrdinal(i, cs.getTupleForAxisNo(i)) * E;
         }
         E *= ((Axis)axes.get("Axis" + i)).getTupleCount();
      }
      // watch out for this:
      if (ordinal >= cells.length){
         S.out("assert: getXY  row = " + row
                           + " col = " + col
                           + " ordinal = " + ordinal
                           + " cells.length = " + cells.length);
         return null;

      }
      if (cells[ordinal] != null){
         return cells[ordinal];
      }else{
         // some of the cells will not be omitted, either null or default values
         // see specification 1.1. page 24
         return defaultCell;
      }

   }
   public Double getSumForColumn(int col, CubeSlicer cubeSlicer){
      double retVal = 0;
      boolean couldntParse = false;
//      S.out("getTotalForColumn!!!!!!!!!!! + COL = " + col);
      for (int i=0; i<getTupleCount(cubeSlicer.getRowAxisOrdinal()); i++){
         try{
            if (getRowCol(i, col, cubeSlicer).getCellValue() != null
                && !getRowCol(i, col, cubeSlicer).getCellValue().equals("")){
               retVal += Double.parseDouble(getRowCol(i, col, cubeSlicer).getCellValue());
            }
         }catch(Exception e){
            couldntParse = true;
            S.out("assert: Couldn't parse as double at position (" + i + ", " + col + ") :"
                  + getRowCol(i, col, cubeSlicer).getCellValue()
                  + " THAT IS:" + getRowCol(i, col, cubeSlicer));
            break;
         }
      }
      if (couldntParse){
         return null;
      }else{
         return new Double(retVal);

      }
   }
   public Double getAverageForColumn(int col, CubeSlicer cubeSlicer){
      if (getTupleCount(cubeSlicer.getRowAxisOrdinal())>0){
         Double sum = getSumForColumn(col, cubeSlicer);
         if (sum != null){
            return new Double(sum.doubleValue()/getTupleCount(cubeSlicer.getRowAxisOrdinal()));
         }
      }
      return null;
   }
   public Double getSumForRow(int row, CubeSlicer cubeSlicer){
      double retVal = 0;
      boolean couldntParse = false;
      for (int j=0; j < getTupleCount(cubeSlicer.getColAxisOrdinal()); j++){
         try{
            if (getRowCol(row, j, cubeSlicer).getCellValue() != null
                && !getRowCol(row, j, cubeSlicer).getCellValue().equals("")){
               retVal += Double.parseDouble(getRowCol(row, j, cubeSlicer).getCellValue());
            }
         }catch(Exception e){
            couldntParse = true;
            S.out("assert: Couldn't parse as double at position (" + row + ", " + j + ") :"
                  + getRowCol(row, j, cubeSlicer).getCellValue()
                  + " THAT IS:" + getRowCol(row, j, cubeSlicer));
            break;
         }
      }
      if (couldntParse){
         return null;
      }else{
         return new Double(retVal);
      }
   }
   public Double getAverageForRow(int row, CubeSlicer cubeSlicer){
      if (getTupleCount(cubeSlicer.getColAxisOrdinal())>0){
         Double sum = getSumForRow(row, cubeSlicer);
         if (sum != null){
            return new Double( sum.doubleValue()
                             / getTupleCount(cubeSlicer.getColAxisOrdinal()));
         }
      }
      return null;
   }
   public Double getTotalSum(CubeSlicer cubeSlicer){
      double retVal = 0;
      for (int j=0; j < getTupleCount(cubeSlicer.getColAxisOrdinal()); j++){
         try{
            retVal += getSumForColumn(j, cubeSlicer).doubleValue();
         }catch(Exception e){
            S.out("couldn't parse total sum");
            return null;
         }
      }
      return new Double(retVal);
   }
   public Double getTotalAverage(CubeSlicer cubeSlicer){
      double retVal = 0;
      if (getTupleCount(cubeSlicer.getColAxisOrdinal()) * getTupleCount(cubeSlicer.getRowAxisOrdinal()) > 0){
         try{
            retVal = getTotalSum(cubeSlicer).doubleValue()
               /(  getTupleCount(cubeSlicer.getColAxisOrdinal())
                 * getTupleCount(cubeSlicer.getRowAxisOrdinal()));
         }catch(Exception e){
            S.out("couldn't parse total avg");
            return null;
         }
         return new Double(retVal);
      }
      return null;
   }

   private int getTupleOrdinal(int axisNo, Tuple tuple){
      Axis a = (Axis) axes.get("Axis" + axisNo);
      if (a != null){
         return a.getTupleOrdinal(tuple);
      }else{
         S.out("assert: ExecuteResult.getTupleOrdinal cannot get the Axis" + axisNo + ". Returning 0.");
         return 0;
      }
   }

   public JPanel getHorizontalTreePanel(String axName, int itemWidth, int itemHeight, boolean showRowTotalsOn){
      return ((Axis)axes.get(axName)).getHorizontalTreePanel(query, itemWidth, itemHeight, showRowTotalsOn);
   }
   public JPanel getVerticalTreePanel(String axName, int itemWidth, int itemHeight, boolean showColumnTotalsOn){
      return ((Axis)axes.get(axName)).getVerticalTreePanel(query, itemWidth, itemHeight, showColumnTotalsOn);
   }
   public boolean isValid(){
//      S.out("getAxis(Axis0).getTupleCount() = " + getAxis("Axis0").getTupleCount()
//            + "getAxis(Axis1).getTupleCount()= " + getAxis("Axis1").getTupleCount());
       //(number1 < number2) ? number2 : number1;
// 		Commented by Prakash. 1st Feb 2007
      //return ((getAxis("Axis0").getTupleCount() * getAxis("Axis1").getTupleCount()) != 0);
       
       /**
        * Copyright (C) 2006 CINCOM SYSTEMS, INC.
        * All Rights Reserved
        * Copyright (C) 2006 Igor Mekterovic
        * All Rights Reserved
        */ 
       
      return ((getAxis("Axis0") != null ? getAxis("Axis0").getTupleCount() : 0) * (getAxis("Axis1") != null ? getAxis("Axis1").getTupleCount() : 0) != 0);
   }
}
