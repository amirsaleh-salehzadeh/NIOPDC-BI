package rex.graphics;

import javax.swing.JLabel;
import rex.metadata.Query;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import rex.metadata.resultelements.Tuple;
import rex.utils.AppColors;

import javax.swing.JPanel;
import javax.swing.JOptionPane;
import rex.metadata.resultelements.Member;
import rex.utils.*; 
/**
 * <p>Title: WHEX</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author igor
 * @version 1.0
 */
/**
  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
  * All Rights Reserved
  * Copyright (C) 2006 Igor Mekterovic
  * All Rights Reserved
  */ 
 /*  Added LanguageChangedListener to implement I18n  */

public class RowTupleMemberLabel extends TupleMemberLabel 
        implements LanguageChangedListener{
   Query query;
   Tuple tuple;
   int memberOrdinal;
   int rowNumber;
   Member thisMember;

   public RowTupleMemberLabel(String caption
                              , Query _query
                              , Tuple _tuple
                              , int _memberOrdinal
                              , int _rowNumber
                              ) {
      super(caption);
      /*adding this class to the list of classes that implement I18n */
       I18n.addOnLanguageChangedListener(this);
      query = _query;
      tuple = _tuple;
      memberOrdinal = _memberOrdinal;
      thisMember = tuple.getMemberAt(memberOrdinal);

      rowNumber = _rowNumber;

      this.setBorder(AppColors.HIERARCHY_LABEL_BORDER);
      this.setForeground(AppColors.HIERARCHY_LABEL_FORECOLOR);
//      this.addMouseListener(this);
   }
   public int setHighlight(Tuple t, int mOrdinal){
//      S.out("setHighlight\nthis.tuple=" + tuple
//            + " \nthis.memberOrdinal=" + memberOrdinal
//            + "\ntuple = " + t
//            + "\nmOrdinal=" + mOrdinal);
      for(int i=0; (i <= memberOrdinal) && (i <= mOrdinal); i++){
         if (!(t.getMemberAt(i).getUniqueName().equals(tuple.getMemberAt(i).getUniqueName()))){
            this.setOpaque(false);
            return 0;
         }
      }
      this.setBackground(AppColors.HIERARCHY_LABEL_SELECTED_BACKGROUND);
      this.setOpaque(true);
      return 1;
   }

   void highlightHierarchy(){
      // highlight the hierarchy
      JPanel parent = (JPanel)this.getParent();
      int levelsHighlighted = 0; // boost performance
      int i;
      for(i=0; i < parent.getComponentCount() && (levelsHighlighted <= memberOrdinal); i++){
         if (parent.getComponent(i) instanceof RowTupleMemberLabel) {
            levelsHighlighted += ((RowTupleMemberLabel)parent.getComponent(i)).setHighlight(tuple, memberOrdinal);
         }
      }
      if (i < parent.getComponentCount()){
         for(; i < parent.getComponentCount(); i++)
            ((JLabel)parent.getComponent(i)).setOpaque(false);
      }
      parent.revalidate();
      parent.repaint();

   }
   void toggleHierarchy(){
      query.toggleRowsDrillState(thisMember);
   }
   void moveDimensionUp(){
      query.moveRowDimensionUp(thisMember);
   }
   void moveDimensionFirst(){
      query.moveRowDimensionFirst(thisMember);
   }
   void moveDimensionLast(){
      query.moveRowDimensionLast(thisMember);
   }
   void removeDimensionFromQuery(){
      query.removeRowDimensionFromQuery(thisMember);
   }
   void removeMemberFromQuery(){
       /**
        * Copyright (C) 2006 CINCOM SYSTEMS, INC.
        * All Rights Reserved
        * Copyright (C) 2006 Igor Mekterovic
        * All Rights Reserved
        */
       if (thisMember.isMeasure()){
           query.removeMeasureMemberFromQuery(thisMember);
        }else{
           query.removeRowMemberFromQuery(thisMember);
       } 
      //query.removeRowMemberFromQuery(thisMember);
   }
   void sortByMeasureAscending(){
      query.sortByThisMember(thisMember, "ASC");
//      S.out("assert: fired sort on a row member!");
   }
   void sortByMeasureDescending(){
      query.sortByThisMember(thisMember, "DESC");
//      S.out("assert: fired sort on a row member!");
   }
   void sortByMeasureBAscending(){
    query.sortByThisMember(thisMember, "BASC");
   }
   void sortByMeasureBDescending(){
    query.sortByThisMember(thisMember, "BDESC");
   }
   void keepThisMemberOnly(){
       /**
        * Copyright (C) 2006 CINCOM SYSTEMS, INC.
        * All Rights Reserved
        * Copyright (C) 2006 Igor Mekterovic
        * All Rights Reserved
        */ 
       if (thisMember.isMeasure()){
           query.keepThisMemberOnlyOnMeasures(thisMember);
        }else{
           query.keepThisMemberOnlyOnRows(thisMember);
       }
      //query.keepThisMemberOnlyOnRows(thisMember);
   }
   void sendMemberToFilter(){
      query.addRowMemberToFilter(thisMember);
   }

   public String[] getPopUpActionList(){
      return new String[] {
           TupleMemberLabelPopUpActions.MOVE_DIMENSION_UP
         , TupleMemberLabelPopUpActions.MOVE_DIMENSION_FIRST
         , TupleMemberLabelPopUpActions.MOVE_DIMENSION_LAST
         , TupleMemberLabelPopUpActions.REMOVE_DIMENSION_FROM_QUERY
         , TupleMemberLabelPopUpActions.REMOVE_MEMBER_FROM_QUERY
         , TupleMemberLabelPopUpActions.KEEP_THIS_MEMBER_ONLY
         , TupleMemberLabelPopUpActions.SEND_MEMBER_TO_FILTER
         , TupleMemberLabelPopUpActions.SORT_BY_THIS_BASCENDING
         , TupleMemberLabelPopUpActions.SORT_BY_THIS_BDESCENDING
         , TupleMemberLabelPopUpActions.SORT_BY_THIS_ASCENDING
         , TupleMemberLabelPopUpActions.SORT_BY_THIS_DESCENDING
      };
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

       TupleMemberLabelPopUpActions.popUpCaptions.put(TupleMemberLabelPopUpActions.MOVE_DIMENSION_UP          , I18n.getString("menu.moveDimUp"));//"Move dimension up");
       TupleMemberLabelPopUpActions.popUpCaptions.put(TupleMemberLabelPopUpActions.MOVE_DIMENSION_FIRST       , I18n.getString("menu.moveDimFirst"));//"Move dimension first");
       TupleMemberLabelPopUpActions.popUpCaptions.put(TupleMemberLabelPopUpActions.MOVE_DIMENSION_LAST        , I18n.getString("menu.moveDimLast"));//"Move dimension last");
       TupleMemberLabelPopUpActions.popUpCaptions.put(TupleMemberLabelPopUpActions.REMOVE_DIMENSION_FROM_QUERY, I18n.getString("menu.removeDim"));//"Remove dimension from query");
       TupleMemberLabelPopUpActions.popUpCaptions.put(TupleMemberLabelPopUpActions.REMOVE_MEMBER_FROM_QUERY   , I18n.getString("menu.removeMem"));//"Remove this member from query");
       TupleMemberLabelPopUpActions.popUpCaptions.put(TupleMemberLabelPopUpActions.KEEP_THIS_MEMBER_ONLY      , I18n.getString("menu.keepMem"));//"Keep this member only");
       TupleMemberLabelPopUpActions.popUpCaptions.put(TupleMemberLabelPopUpActions.SEND_MEMBER_TO_FILTER      , I18n.getString("menu.sendMem"));//"Send this member to filter");
       TupleMemberLabelPopUpActions.popUpCaptions.put(TupleMemberLabelPopUpActions.SORT_BY_THIS_ASCENDING     ,I18n.getString("menu.sortAsc") );
       TupleMemberLabelPopUpActions.popUpCaptions.put(TupleMemberLabelPopUpActions.SORT_BY_THIS_DESCENDING     ,I18n.getString("menu.sortDsc") );
       TupleMemberLabelPopUpActions.popUpCaptions.put(TupleMemberLabelPopUpActions.SORT_BY_THIS_BASCENDING     ,I18n.getString("menu.sortBAsc") );
       TupleMemberLabelPopUpActions.popUpCaptions.put(TupleMemberLabelPopUpActions.SORT_BY_THIS_BDESCENDING     ,I18n.getString("menu.sortBDsc") );
    }
  /* end of modification for I18n */


}
