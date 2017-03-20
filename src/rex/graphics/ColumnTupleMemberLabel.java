package rex.graphics;

import javax.swing.JLabel;
import rex.metadata.Query;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import rex.metadata.resultelements.Tuple;
import rex.utils.AppColors;

import javax.swing.JPanel;
import rex.metadata.resultelements.Member;
import javax.swing.JOptionPane;
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

public class ColumnTupleMemberLabel extends TupleMemberLabel 
        implements LanguageChangedListener{
   Query query;
   Tuple tuple;
   int memberOrdinal;
   int columnNumber;
   Member thisMember;

   public ColumnTupleMemberLabel(String caption
                                 , Query _query
                                 , Tuple _tuple
                                 , int _memberOrdinal
                                 , int _columnNumber){
      super(caption, JLabel.CENTER);
       /**
         * Copyright (C) 2006 CINCOM SYSTEMS, INC.
         * All Rights Reserved
         * Copyright (C) 2006 Igor Mekterovic
         * All Rights Reserved
         */ 
      /*adding this class to the list of classes to implement I18n */
      I18n.addOnLanguageChangedListener(this);
      /* end of modification for I18n */
      query = _query;
      tuple = _tuple;
      memberOrdinal = _memberOrdinal;
      thisMember = tuple.getMemberAt(memberOrdinal);
      columnNumber = _columnNumber;

      this.setBorder(AppColors.HIERARCHY_LABEL_BORDER);
      this.setForeground(AppColors.HIERARCHY_LABEL_FORECOLOR);

//      this.addMouseListener(this);
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

       TupleMemberLabelPopUpActions.popUpCaptions.put(TupleMemberLabelPopUpActions.MOVE_DIMENSION_UP,               I18n.getString("menu.moveDimUp"));
       TupleMemberLabelPopUpActions.popUpCaptions.put(TupleMemberLabelPopUpActions.MOVE_DIMENSION_FIRST       , I18n.getString("menu.moveDimFirst"));
       TupleMemberLabelPopUpActions.popUpCaptions.put(TupleMemberLabelPopUpActions.MOVE_DIMENSION_LAST        , I18n.getString("menu.moveDimLast"));
       TupleMemberLabelPopUpActions.popUpCaptions.put(TupleMemberLabelPopUpActions.REMOVE_DIMENSION_FROM_QUERY, I18n.getString("menu.removeDim"));
       TupleMemberLabelPopUpActions.popUpCaptions.put(TupleMemberLabelPopUpActions.REMOVE_MEMBER_FROM_QUERY   , I18n.getString("menu.removeMem"));
       TupleMemberLabelPopUpActions.popUpCaptions.put(TupleMemberLabelPopUpActions.KEEP_THIS_MEMBER_ONLY      , I18n.getString("menu.keepMem"));
       TupleMemberLabelPopUpActions.popUpCaptions.put(TupleMemberLabelPopUpActions.SEND_MEMBER_TO_FILTER      , I18n.getString("menu.sendMem"));
       TupleMemberLabelPopUpActions.popUpCaptions.put(TupleMemberLabelPopUpActions.SORT_BY_THIS_ASCENDING     ,I18n.getString("menu.sortAsc") );
       TupleMemberLabelPopUpActions.popUpCaptions.put(TupleMemberLabelPopUpActions.SORT_BY_THIS_DESCENDING     ,I18n.getString("menu.sortDsc") );
       TupleMemberLabelPopUpActions.popUpCaptions.put(TupleMemberLabelPopUpActions.SORT_BY_THIS_BASCENDING     ,I18n.getString("menu.sortBAsc") );
       TupleMemberLabelPopUpActions.popUpCaptions.put(TupleMemberLabelPopUpActions.SORT_BY_THIS_BDESCENDING     ,I18n.getString("menu.sortBDsc") );
    }
     /* end of modification for I18n */

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

   public String[] getPopUpActionList(){
     
      if (thisMember.isMeasure()){
         return new String[] {
              TupleMemberLabelPopUpActions.REMOVE_MEMBER_FROM_QUERY
            , TupleMemberLabelPopUpActions.KEEP_THIS_MEMBER_ONLY
            , TupleMemberLabelPopUpActions.SORT_BY_THIS_BASCENDING
            , TupleMemberLabelPopUpActions.SORT_BY_THIS_BDESCENDING
            , TupleMemberLabelPopUpActions.SORT_BY_THIS_ASCENDING
            , TupleMemberLabelPopUpActions.SORT_BY_THIS_DESCENDING
         };
      }else{
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
   }

   void highlightHierarchy(){
      JPanel parent = (JPanel)this.getParent();
      int levelsHighlighted = 0; // boost performance
      int i;
      for(i=0; i < parent.getComponentCount() && (levelsHighlighted <= memberOrdinal); i++){
         if (parent.getComponent(i) instanceof ColumnTupleMemberLabel) {
            levelsHighlighted += ((ColumnTupleMemberLabel)parent.getComponent(i)).setHighlight(tuple, memberOrdinal);
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
      query.toggleColumnsDrillState(thisMember);
   }
   void moveDimensionUp(){
      query.moveColumnDimensionUp(thisMember);
   }
   void moveDimensionFirst(){
      query.moveColumnDimensionFirst(thisMember);
   }
   void moveDimensionLast(){
      query.moveColumnDimensionLast(thisMember);
   }
   void removeDimensionFromQuery(){
      query.removeColumnDimensionFromQuery(thisMember);
   }
   void removeMemberFromQuery(){
      if (thisMember.isMeasure()){
         query.removeMeasureMemberFromQuery(thisMember);
      }else{
         query.removeColumnMemberFromQuery(thisMember);
      }
   }
   
   
   void sortByMeasureAscending(){
//      if (thisMember.isMeasure()){
        query.sortByThisMember(thisMember, "ASC");
//      }else{
//         S.out("assert: fired sort on non-measure member!");
//      }
   }
   void sortByMeasureDescending(){
//      if (thisMember.isMeasure()){
    query.sortByThisMember(thisMember, "DESC");

//      }else{
//         S.out("assert: fired sort on non-measure member!");
//      }
   }

   void sortByMeasureBAscending(){   	
    query.sortByThisMember(thisMember, "BASC");
   }
   
   void sortByMeasureBDescending(){
    query.sortByThisMember(thisMember, "BDESC");
   }

   void keepThisMemberOnly(){
      if (thisMember.isMeasure()){
         query.keepThisMemberOnlyOnMeasures(thisMember);
      }else{
         query.keepThisMemberOnlyOnColumns(thisMember);
      }
   }

   void sendMemberToFilter(){
      if (thisMember.isMeasure()){
        /**
         * Copyright (C) 2006 CINCOM SYSTEMS, INC.
         * All Rights Reserved
         * Copyright (C) 2006 Igor Mekterovic
         * All Rights Reserved
         *   
         */
          /* localizing */
      JOptionPane.showConfirmDialog(
                    null
                  , I18n.getString("msgText.addFilter")
                  , I18n.getString("msgTitle.sendMember")
                  , JOptionPane.YES_OPTION);
      /* end of modification*/

      }else{
         query.addColumnMemberToFilter(thisMember);
      }
   }

}