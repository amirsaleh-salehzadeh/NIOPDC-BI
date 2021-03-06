package rex.metadata.resultelements;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import rex.utils.*;
import java.util.Vector;
import javax.swing.JPanel;
//import java.awt.GridBagLayout;
import rex.graphics.layoutmanagers.GridBagConstraints;
import javax.swing.JButton;
import java.awt.Dimension;
import javax.swing.JLabel;
import javax.swing.BorderFactory;
import java.awt.Color;
import javax.swing.border.Border;
import java.util.ArrayList;
import javax.swing.BoxLayout;
import rex.graphics.layoutmanagers.GridBagLayoutRex;

import rex.metadata.Query;
import rex.graphics.ColumnTupleMemberLabel;
import rex.graphics.RowTupleMemberLabel;
//import rex.metadata.graphics.RowTreePanel;
import rex.utils.*;
/**
 * <p>Title: WHEX</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author igor
 * @version 1.0
 */
/*
     <AxisInfo name="Axis0">
   - <HierarchyInfo name="Measures">
     <UName name="[Measures].[MEMBER_UNIQUE_NAME]" />
     <Caption name="[Measures].[MEMBER_CAPTION]" />
     <LName name="[Measures].[LEVEL_UNIQUE_NAME]" />
     <LNum name="[Measures].[LEVEL_NUMBER]" />
     <DisplayInfo name="[Measures].[DISPLAY_INFO]" />
     </HierarchyInfo>

*/

 /**
  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
  * All Rights Reserved
  * Copyright (C) 2006 Igor Mekterovic
  * All Rights Reserved
  */ 
 /*  Added LanguageChangedListener to implement I18n  */
 
public class Axis implements LanguageChangedListener{ 
    
   private String name;
   private ArrayList hierarchies;
   private Vector tuples;
   JLabel lblSum;
   JLabel lblAverage;

   public Axis(Node hNode) {

      tuples = new Vector();
      name = hNode.getAttributes().getNamedItem("name").getNodeValue();
        /**
          * Copyright (C) 2006 CINCOM SYSTEMS, INC.
          * All Rights Reserved
          * Copyright (C) 2006 Igor Mekterovic
          * All Rights Reserved
          */ 
        /* implementing localization */
      lblSum=new JLabel(I18n.getString("label.sum"), JLabel.CENTER);
      lblAverage=new JLabel(I18n.getString("label.average"), JLabel.CENTER);
        /* end of modification for I18n */

      NodeList hl = hNode.getChildNodes();
      if (hl.getLength()>0){
         hierarchies = new ArrayList();
         for (int i = 0; i < hl.getLength(); i++) {
//            S.out("LOADING:hierarchies[" + i + "] TYPE = " + DOM.typeName[hl.item(i).getNodeType()]);
//            S.out("LOADING:hierarchies[" + i + "]" + " text=" + DOM.getTextFromDOMElement(hl.item(i)) );
            if (hl.item(i).getNodeType() == DOM.ELEMENT_TYPE) {
               hierarchies.add(new HierarchyInfo(hl.item(i)));
            }
         }
      }
     /**
      * Copyright (C) 2006 CINCOM SYSTEMS, INC.
      * All Rights Reserved
      * Copyright (C) 2006 Igor Mekterovic
      * All Rights Reserved
      */ 
    /*adding this class to the list of classes that implement I18n */
 
      I18n.addOnLanguageChangedListener(this);
      applyI18n();
        /* end of modification for I18n */

   }

   public void loadMemberTuples(Node tuplesNode){
      // here i get a TUPLES node, but i want a TUPLE:
      //DOM.dumpChildNodes(tuplesNode);
      Tuple prevTuple = null;
      NodeList tl = tuplesNode.getChildNodes();
//      S.out("Axis:" + name + " is loading tuples. Tuples count = " + tl.getLength());
      if (tl.getLength()>0){
         for (int i = 0; i < tl.getLength(); i++) {
            if (tl.item(i).getNodeType() == DOM.ELEMENT_TYPE) {
               // I'm passing a reference to the previous tuple
               // (and so is a tuple passing a reference to the previous member)
               //  so that member will be able to set it's span value, according
               // to weather it has a same uName as the previous member
               prevTuple = new Tuple(tl.item(i), prevTuple);
               tuples.add(prevTuple);
            }
         }
      }

   }
   public int getTupleCount(){
      if (tuples != null){
         return tuples.size();
      }else{
         return 0;
      }
   }
   public Tuple getTupleAt(int i){
      if ( i >= 0  &&  i<= tuples.size()){
         return (Tuple) tuples.elementAt(i);
      }else{
         S.out("assert: Axis.getTupleAt(i) - i is out of the array range");
         return null;
      }
   }

   public int getTupleOrdinal(Tuple t){
      for(int i=0; i< tuples.size(); i++){
         //S.out("checking: " + getTupleAt(i) + " == " + t);
         if (t.equals(getTupleAt(i))) return i;
      }
      S.out("assert: Axis.getTupleOrdinal(Tuple t) - couldn't find a tuple: " + t );
      return -1;
   }

   public String toString(){
      if (name == null){
         /**
          * Copyright (C) 2006 CINCOM SYSTEMS, INC.
          * All Rights Reserved
          * Copyright (C) 2006 Igor Mekterovic
          * All Rights Reserved
          */ 
        /* implementing localization */
         return I18n.getString("toolTip.notInitiliazed");//"not initialized";
           /* end of modification for I18n */

      }
      else
         return "" + name;
   }
   public String getName(){
      return name;
   }

   public int getHierarchyInfoCount(){
//      for(int i=0; i < hierarchies.size() ; i++) S.out("" + i + " = " + (HierarchyInfo)hierarchies.get(i));
      if (hierarchies != null)
         return hierarchies.size();
      else
         return 0;
   }
   public HierarchyInfo getHierarchyInfoAt(int i){
      // this is rarely called, in fact only from chart - to get the chart title
      return (HierarchyInfo)(hierarchies.toArray())[i];
   }

   public void dumpTable(){
      int i, j, k, tupleCount, memberCount;
      S.out("Dump table for axis:" + name + "\ntuple count is:" + getTupleCount());
      if (tuples != null){
         tupleCount = getTupleCount();
         memberCount = ((Tuple)tuples.elementAt(0)).getMemberCount();
         int[] memberSpan = new int[memberCount];
         int[] currMemberX = new int[memberCount];
         for(k=0; k < memberCount; k++) memberSpan[k] = 0;
         for(k=0; k < memberCount; k++) currMemberX[k] = tupleCount;


         for (i = tupleCount - 1; i >= 0; i--) {
            for(j=0; j<memberCount; j++){
               S.out("member=" + getTupleAt(i).getMemberAt(j) + " span = " + getTupleAt(i).getMemberAt(j).getMemberSpan());
            }
         }






         for (i = tupleCount - 1; i >= 0; i--) {
            for(j=0; j<memberCount; j++){
               if (memberSpan[j] == 0){
                  memberSpan[j] = getTupleAt(i).getMemberAt(j).getMemberSpan();
                  currMemberX[j] -= getTupleAt(i).getMemberAt(j).getMemberSpan();
                  S.out("x=" + currMemberX[j]
                        +  "\ty=" + j
                        + "\twidth=" + getTupleAt(i).getMemberAt(j).getMemberSpan()
                        + "\tmember="  + getTupleAt(i).getMemberAt(j));
               }
               memberSpan[j]--;
            }
         }
      }
   }

   public JPanel getHorizontalTreePanel(Query query, int itemWidth, int itemHeight, boolean showRowTotalsOn){
      int i, j, k, tupleCount, memberCount;
      JPanel jp;
      JLabel item;
      if (tuples != null){
         jp = new JPanel();
         jp.setBackground(AppColors.HIERARCHY_PANE_BACKGROUND);
         GridBagLayoutRex gb = new GridBagLayoutRex();
         GridBagConstraints c = new GridBagConstraints();
         jp.setLayout(gb);
         //c.fill = GridBagConstraints.HORIZONTAL;
         c.fill = GridBagConstraints.BOTH;

         c.weightx = 1;  // resizable in both ways
         c.weighty = 1;
         //c.anchor = GridBagConstraints.WEST;
         Dimension prefSize = new Dimension(itemWidth, itemHeight);

         tupleCount = getTupleCount();
         memberCount = ((Tuple)tuples.elementAt(0)).getMemberCount();
         int[] memberSpan = new int[memberCount];
         int[] currMemberX = new int[memberCount];
         for(k=0; k < memberCount; k++) memberSpan[k] = 0;
         for(k=0; k < memberCount; k++) currMemberX[k] = tupleCount;

         for (i = tupleCount - 1; i >= 0; i--) {
            for(j=0; j<memberCount; j++){
               if (memberSpan[j] == 0){
                  memberSpan[j] = getTupleAt(i).getMemberAt(j).getMemberSpan();
                  currMemberX[j] -= getTupleAt(i).getMemberAt(j).getMemberSpan();
//                  S.out("x=" + currMemberX[j]
//                        +  "\ty=" + j
//                        + "\twidth=" + getTupleAt(i).getMemberAt(j).getMemberSpan()
//                        + "\tmember="  + getTupleAt(i).getMemberAt(j));
//
                  item = new ColumnTupleMemberLabel("" + getTupleAt(i).getMemberAt(j)
                                       , query
                                       , getTupleAt(i)
                                       , j
                                       , i);
                  if (getTupleAt(i).getMemberAt(j).isMeasure()){
                     item.setIcon(getTupleAt(i).getMemberAt(j).getIcon());
                  }
                  item.setToolTipText(getTupleAt(i).getMemberAt(j).getToolTip());
//                  setHierarchyLabelProperties(item);

                  item.setPreferredSize(prefSize);
                  item.setMinimumSize(prefSize);
                  item.setMaximumSize(prefSize);
                  // add listener
                  c.gridx = currMemberX[j];
                  c.gridy = j;
                  c.gridwidth = getTupleAt(i).getMemberAt(j).getMemberSpan();
                  c.gridheight = 1;
//                  S.out("c.gridx=" + c.gridx
//                        + " c.gridy=" + c.gridy
//                        + " c.gridheight= "+ c.gridheight
//                        + " c.gridwidth=" + c.gridwidth
//                        + " item = " + item.getText()
//                        + " attrs=" + item);
                  gb.setConstraints(item, c);
                  jp.add(item);
               }
               memberSpan[j]--;
            }
         }
         if (showRowTotalsOn){
            //JLabel totalsLabel = new JLabel("<html><b>SUM:</b></html>", JLabel.CENTER);
             JLabel totalsLabel = lblSum; //new JLabel("<html><b>SUM:</b></html>", JLabel.CENTER);
            totalsLabel.setBackground(AppColors.CELL_TOTAL_SUM_BACKGROUND_COLOR);
            totalsLabel.setBorder(AppColors.CELL_TOTAL_SUM_BORDER);
            totalsLabel.setOpaque(true);
            totalsLabel.setPreferredSize(prefSize);
            totalsLabel.setMinimumSize(prefSize);
            totalsLabel.setMaximumSize(prefSize);
            c.gridx = tupleCount;
            c.gridy = 0;
            c.gridwidth = 1;
            c.gridheight = memberCount;
            gb.setConstraints(totalsLabel, c);
            jp.add(totalsLabel);

            totalsLabel =lblAverage;// new JLabel("<html><b>AVERAGE:</b></html>", JLabel.CENTER);
            totalsLabel.setBackground(AppColors.CELL_TOTAL_AVERAGE_BACKGROUND_COLOR);
            totalsLabel.setBorder(AppColors.CELL_TOTAL_AVERAGE_BORDER);
            totalsLabel.setOpaque(true);
            totalsLabel.setPreferredSize(prefSize);
            totalsLabel.setMinimumSize(prefSize);
            totalsLabel.setMaximumSize(prefSize);
            c.gridx = tupleCount + 1;
            c.gridy = 0;
            c.gridwidth = 1;
            c.gridheight = memberCount;
            gb.setConstraints(totalsLabel, c);
            jp.add(totalsLabel);

         }

         //jp.setBorder(BorderFactory.createLineBorder(Color.RED));
//         S.out("jp.getPreferredSize()=" + jp.getPreferredSize());
//         S.out("jp.getMaximumSize()=" + jp.getMaximumSize());
//         S.out("jp.getMinimumSize()=" + jp.getMinimumSize());
         return jp;
      }
      return null;
   }

   public JPanel getVerticalTreePanel(Query query, int itemWidth, int itemHeight, boolean showColumnTotalsOn){
      int i, j, k, tupleCount, memberCount;
      JPanel jp;
      JLabel item;
      if (tuples != null){
         jp = new JPanel();
         jp.setBackground(AppColors.HIERARCHY_PANE_BACKGROUND);
//         jp.setLayout(new BoxLayout(jp, BoxLayout.Y_AXIS));
         GridBagLayoutRex gb = new GridBagLayoutRex();
         jp.setLayout(gb);

         tupleCount = getTupleCount();
         memberCount = ((Tuple)tuples.elementAt(0)).getMemberCount();

//         int panelPartCount = tupleCount/512;;
//         JPanel[] panelPart = new JPanel[panelPartCount];



//         for(i=0; i<panelPartCount; i++){
//            gb[i] = new GridBagLayout();
//            panelPart[i].setLayout(gb[i]);
//            jp.add(panelPart[i]);
//         }

         GridBagConstraints c = new GridBagConstraints();

         //c.fill = GridBagConstraints.HORIZONTAL;
         c.fill = GridBagConstraints.BOTH;

         c.weightx = 1;  // resizable in both ways
         c.weighty = 1;


         int[] memberSpan = new int[memberCount];
         int[] currMemberX = new int[memberCount];
         for(k=0; k < memberCount; k++) memberSpan[k] = 0;
         for(k=0; k < memberCount; k++) currMemberX[k] = tupleCount;

         for (i = tupleCount - 1; i >= 0; i--) {
            for(j=0; j<memberCount; j++){
               //S.out("i=" + i + " j=" + j + " memberSpan[j]=" + getTupleAt(i).getMemberAt(j).getMemberSpan() + " item=" + getTupleAt(i).getMemberAt(j));
               if (memberSpan[j] == 0){
                  memberSpan[j] =  getTupleAt(i).getMemberAt(j).getMemberSpan();
                  currMemberX[j] -= getTupleAt(i).getMemberAt(j).getMemberSpan();

//                  S.out("x=" + currMemberX[j]
//                        +  "\ty=" + j
//                        + "\twidthy=" + getTupleAt(i).getMemberAt(j).getMemberSpan()
//                        + "\tmember="  + getTupleAt(i).getMemberAt(j));

                  item = new RowTupleMemberLabel("" + getTupleAt(i).getMemberAt(j)
                                              , query
                                              , getTupleAt(i)
                                              , j
                                              , i);
//                  setHierarchyLabelProperties(item);
                  item.setToolTipText(getTupleAt(i).getMemberAt(j).getToolTip());
                  item.setPreferredSize(new Dimension(itemWidth, itemHeight));
                  item.setMinimumSize(new Dimension(itemWidth, itemHeight));
                  item.setMaximumSize(new Dimension(itemWidth, itemHeight));
                  // add listener
                  c.gridx = j;
                  c.gridy = currMemberX[j];
                  c.gridwidth = 1;
                  c.gridheight = getTupleAt(i).getMemberAt(j).getMemberSpan();
//                  S.out("c.gridx=" + c.gridx
//                        + " c.gridy=" + c.gridy
//                        + " c.gridheight= "+ c.gridheight
//                        + " c.gridwidth=" + c.gridwidth
//                        + " item = " + item.getText()
//                        + " attrs=" + item);
                  gb.setConstraints(item, c);
                  jp.add(item);
               }
               memberSpan[j]--;
            }
         }
         if (showColumnTotalsOn){
             /**
              * Copyright (C) 2006 CINCOM SYSTEMS, INC.
              * All Rights Reserved
              * Copyright (C) 2006 Igor Mekterovic
              * All Rights Reserved
              */ 
            /* implementing localization */
            JLabel totalsLabel = lblSum; // new JLabel("<html><b>SUM:</b></html>", JLabel.RIGHT);
              /* end of modification for I18n */

            totalsLabel.setBackground(AppColors.CELL_TOTAL_SUM_BACKGROUND_COLOR);
            totalsLabel.setBorder(AppColors.CELL_TOTAL_SUM_BORDER);
            totalsLabel.setOpaque(true);
            c.gridx = 0;
            c.gridy = tupleCount;
            c.gridwidth = memberCount;
            c.gridheight = 1;
            gb.setConstraints(totalsLabel, c);
            jp.add(totalsLabel);

            totalsLabel = lblAverage;//new JLabel("<html><b>AVERAGE:</b></html>", JLabel.RIGHT);
            totalsLabel.setBackground(AppColors.CELL_TOTAL_AVERAGE_BACKGROUND_COLOR);
            totalsLabel.setBorder(AppColors.CELL_TOTAL_AVERAGE_BORDER);
            totalsLabel.setOpaque(true);
            c.gridx = 0;
            c.gridy = tupleCount + 1;
            c.gridwidth = memberCount;
            c.gridheight = 1;
            gb.setConstraints(totalsLabel, c);
            jp.add(totalsLabel);

         }
//         for(int comp = 0; comp<jp.getComponentCount(); comp++){
//            S.out("comp[" + comp + "]=" + jp.getComponent(comp));
//         }
//         S.out("jp.getPreferredSize()=" + jp.getPreferredSize());
//         S.out("jp.getMaximumSize()=" + jp.getMaximumSize());
//         S.out("jp.getMinimumSize()=" + jp.getMinimumSize());
         return jp;
      }
      return null;
   }

//   private void setHierarchyLabelProperties(JLabel jl){
//      jl.setBorder(AppColors.HIERARCHY_LABEL_BORDER);
//      jl.setForeground(AppColors.HIERARCHY_LABEL_FORECOLOR);
//   }
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
        this.applyI18n();
    }
  
 /**
  *  Helper method to implement locatization when language is changed
  */    
    public void applyI18n(){
        lblSum.setText(I18n.getString("label.sum"));
        lblAverage.setText(I18n.getString("label.average"));
       
    }
   
   /* end of modification for I18n */

}