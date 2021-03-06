package rex.graphics;

import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPopupMenu;
import javax.swing.JMenuItem;
import java.awt.event.ActionEvent;
import rex.utils.S;
/**
 * <p>Title: WHEX</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author igor
 * @version 1.0
 */

public abstract class TupleMemberLabel extends JLabel implements ActionListener{
   JPopupMenu popup;
   public TupleMemberLabel(String caption) {
      super(caption);
      popup = new JPopupMenu();
      addMouseListener(new PopupListener());
   }
   public TupleMemberLabel(String caption, int horAlignment) {
      super(caption, horAlignment);
      popup = new JPopupMenu();
      addMouseListener(new PopupListener());
   }


   // this must be overriden
   abstract String[] getPopUpActionList();
   abstract void highlightHierarchy();
   abstract void toggleHierarchy();

   abstract void moveDimensionUp();
   abstract void moveDimensionFirst();
   abstract void moveDimensionLast();
   abstract void removeDimensionFromQuery();
   abstract void removeMemberFromQuery();
   abstract void keepThisMemberOnly();
   abstract void sortByMeasureAscending();
   abstract void sortByMeasureDescending();
   abstract void sortByMeasureBAscending();
   abstract void sortByMeasureBDescending();
   abstract void sendMemberToFilter();

   public void actionPerformed(ActionEvent e){
      int i = 0;
      // To do:
      if (e.getActionCommand().equals((String)TupleMemberLabelPopUpActions.popUpCaptions.get(TupleMemberLabelPopUpActions.MOVE_DIMENSION_UP))){
         moveDimensionUp();
      }else if (e.getActionCommand().equals((String)TupleMemberLabelPopUpActions.popUpCaptions.get(TupleMemberLabelPopUpActions.MOVE_DIMENSION_FIRST))){
         moveDimensionFirst();
      }else if (e.getActionCommand().equals((String)TupleMemberLabelPopUpActions.popUpCaptions.get(TupleMemberLabelPopUpActions.MOVE_DIMENSION_LAST))){
         moveDimensionLast();
      }else if (e.getActionCommand().equals((String)TupleMemberLabelPopUpActions.popUpCaptions.get(TupleMemberLabelPopUpActions.REMOVE_DIMENSION_FROM_QUERY))){
         removeDimensionFromQuery();
      }else if (e.getActionCommand().equals((String)TupleMemberLabelPopUpActions.popUpCaptions.get(TupleMemberLabelPopUpActions.REMOVE_MEMBER_FROM_QUERY))){
         removeMemberFromQuery();
      }else if (e.getActionCommand().equals((String)TupleMemberLabelPopUpActions.popUpCaptions.get(TupleMemberLabelPopUpActions.KEEP_THIS_MEMBER_ONLY))){
         keepThisMemberOnly();
      }else if (e.getActionCommand().equals((String)TupleMemberLabelPopUpActions.popUpCaptions.get(TupleMemberLabelPopUpActions.SEND_MEMBER_TO_FILTER))){
         sendMemberToFilter();
      }else if (e.getActionCommand().equals((String)TupleMemberLabelPopUpActions.popUpCaptions.get(TupleMemberLabelPopUpActions.SORT_BY_THIS_ASCENDING))){
         sortByMeasureAscending();
      }else if (e.getActionCommand().equals((String)TupleMemberLabelPopUpActions.popUpCaptions.get(TupleMemberLabelPopUpActions.SORT_BY_THIS_DESCENDING))){
         sortByMeasureDescending();
      }else if (e.getActionCommand().equals((String)TupleMemberLabelPopUpActions.popUpCaptions.get(TupleMemberLabelPopUpActions.SORT_BY_THIS_BASCENDING))){
         sortByMeasureBAscending();
      }else if (e.getActionCommand().equals((String)TupleMemberLabelPopUpActions.popUpCaptions.get(TupleMemberLabelPopUpActions.SORT_BY_THIS_BDESCENDING))){
         sortByMeasureBDescending();
      }

   }



//-----------------------------------------------------------------------------------------------------------
// inner classes:
//-----------------------------------------------------------------------------------------------------------
   class PopupListener extends MouseAdapter {
      public void mouseReleased(MouseEvent e) {
         maybeShowPopup(e);
      }
      public void mouseClicked(MouseEvent e) {

      }

      public void mousePressed(MouseEvent e){
         if(e.getClickCount() == 1){
            highlightHierarchy();
            maybeShowPopup(e);
         }else if(e.getClickCount() == 2){
            toggleHierarchy();
         }
      }

      private void maybeShowPopup(MouseEvent e) {

         if (e.isPopupTrigger()) {
            String[] al;
            JMenuItem menuItem;

            popup.removeAll();

            al = TupleMemberLabel.this.getPopUpActionList();
            for (int i=0; al != null && i < al.length; i++){
               menuItem = new JMenuItem((String)TupleMemberLabelPopUpActions.popUpCaptions.get(al[i]));
               menuItem.addActionListener(TupleMemberLabel.this);
               popup.add(menuItem);
            }
            popup.show(e.getComponent()
                       , e.getX()
                       , e.getY());
         }

      }
   }
//-----------------------------------------------------------------------------------------------------------
//-----------------------------------------------------------------------------------------------------------


}