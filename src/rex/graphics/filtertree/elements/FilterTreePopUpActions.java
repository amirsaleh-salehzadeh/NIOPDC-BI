package rex.graphics.filtertree.elements;
import rex.utils.*;
import java.util.HashMap;

/**
 * <p>Title: WHEX</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author igor
 * @version 1.0
 */

public class FilterTreePopUpActions  {
  public static String
       ENABLE_ALL_BUT_THIS_ELEMENT,
       ENABLE_ONLY_THIS_ELEMENT,
       ENABLE_THIS_ELEMENT,
       DISABLE_THIS_ELEMENT,
       LOSE_FILTER,
       APPLY_FILTER,
       MOVE_TO_TAB,
       MOVE_TO_SPLIT_PANE;

    public static HashMap popUpCaptions;
    static {
       ENABLE_ALL_BUT_THIS_ELEMENT = "0";
       ENABLE_ONLY_THIS_ELEMENT = "1";
       ENABLE_THIS_ELEMENT = "2";
       DISABLE_THIS_ELEMENT = "3";
       LOSE_FILTER = "4";
       APPLY_FILTER = "5";
       MOVE_TO_TAB = "6";
       MOVE_TO_SPLIT_PANE = "7";
     /**
      * Copyright (C) 2006 CINCOM SYSTEMS, INC.
      * All Rights Reserved
      * Copyright (C) 2006 Igor Mekterovic
      * All Rights Reserved
      */ 
    /* implementing localization */
       popUpCaptions = new HashMap(10);
       popUpCaptions.put(ENABLE_ALL_BUT_THIS_ELEMENT,I18n.getString("menu.enableAllExceptThis"));
       popUpCaptions.put(ENABLE_ONLY_THIS_ELEMENT,I18n.getString("menu.enableJustThisElem"));
       popUpCaptions.put(ENABLE_THIS_ELEMENT, I18n.getString("menu.enableThisElem"));
       popUpCaptions.put(DISABLE_THIS_ELEMENT, I18n.getString("menu.disableElem"));
       popUpCaptions.put(LOSE_FILTER, I18n.getString("menu.loseFilter"));
       popUpCaptions.put(APPLY_FILTER, I18n.getString("menu.applyFilter"));
       popUpCaptions.put(MOVE_TO_TAB, I18n.getString("menu.moveToTab"));
       popUpCaptions.put(MOVE_TO_SPLIT_PANE, I18n.getString("menu.moveToSplitPane"));

    }
  /* end of modification for I18n */

}
