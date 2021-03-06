package rex.metadata.resultelements;

import org.w3c.dom.NodeList;
import org.w3c.dom.Node;

import rex.utils.DOM;
/**
 * <p>Title: WHEX</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author igor
 * @version 1.0
 */

public class Cell {

   private String value;
   private String fmtValue;
   private String foreColor;
   private String backColor;
   private int cellOrdinal;
   private String defaultCellValue;
/*
   <Cell CellOrdinal="0">
    <Value xsi:type="xsd:double">837.000000</Value>
    <FmtValue>837,00 kn</FmtValue>
    </Cell>

*/
  public Cell(Node cellNode) {
     if (cellNode.getAttributes().getNamedItem("CellOrdinal") != null){
      // defaultCell prbably doesn't have cellOrdinal.
        cellOrdinal = Integer.parseInt(cellNode.getAttributes().getNamedItem("CellOrdinal").getNodeValue());
     }
     NodeList nl = cellNode.getChildNodes();

     for(int i=0; i < nl.getLength(); i++){
        //S.out("nl.getNodeType = " + nl.item(i).getNodeType() + " node name = " +  nl.item(i).getNodeName() + " text = " + DOM.getTextFromDOMElement(nl.item(i)));
        if (nl.item(i).getNodeType() == 1){
           if (nl.item(i).getNodeName().equals("Value")) {
              value = DOM.getTextFromDOMElement(nl.item(i));
           }else if (nl.item(i).getNodeName().equals("FmtValue")) {
              fmtValue = DOM.getTextFromDOMElement(nl.item(i));
           }else if (nl.item(i).getNodeName().equals("ForeColor")) {
              foreColor = DOM.getTextFromDOMElement(nl.item(i));
           }else if (nl.item(i).getNodeName().equals("BackColor")) {
              backColor = DOM.getTextFromDOMElement(nl.item(i));
           }else if (nl.item(i).getNodeName().equals("Default")) {
              defaultCellValue = DOM.getTextFromDOMElement(nl.item(i));
           }

        }
     }
  }
  public Cell(Node cellNode, String _defaultCellValue) {
     this(cellNode);
     defaultCellValue = _defaultCellValue;
  }
  public String getDefaultCellValue(){
     return defaultCellValue;
  }

  public int getCellOrdinal(){
     return cellOrdinal;
  }
  public String getCellValue(){
     return value;
  }

  public String toString(){
     if (fmtValue != null){
        return fmtValue;
     }else if (value != null){
        return value;
     }else if(defaultCellValue != null){
        return defaultCellValue;
     }
     return null;

  }
  public String getToolTip(){
  // is there anything smart I could say here?
     return "" + fmtValue;
  }
}