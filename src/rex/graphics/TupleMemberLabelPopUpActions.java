package rex.graphics;

import java.util.HashMap;
import rex.utils.*;
/**
 * <p>Title: WHEX</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author igor
 * @version 1.0
 */

public class TupleMemberLabelPopUpActions {
    
  

     public static  String
       MOVE_DIMENSION_UP,
       MOVE_DIMENSION_FIRST,
       MOVE_DIMENSION_LAST,
       REMOVE_DIMENSION_FROM_QUERY,
       REMOVE_MEMBER_FROM_QUERY,
       KEEP_THIS_MEMBER_ONLY,
       SEND_MEMBER_TO_FILTER,
       SORT_BY_THIS_ASCENDING,
       SORT_BY_THIS_DESCENDING,
       SORT_BY_THIS_BASCENDING,
       SORT_BY_THIS_BDESCENDING;

    public static HashMap popUpCaptions;
    static {
       MOVE_DIMENSION_UP           = "0";
       MOVE_DIMENSION_FIRST        = "1";
       MOVE_DIMENSION_LAST         = "2";
       REMOVE_DIMENSION_FROM_QUERY = "3";
       REMOVE_MEMBER_FROM_QUERY    = "4";
       KEEP_THIS_MEMBER_ONLY       = "5";
       SEND_MEMBER_TO_FILTER       = "6";
       SORT_BY_THIS_ASCENDING      = "7";
       SORT_BY_THIS_DESCENDING     = "8";
       SORT_BY_THIS_BASCENDING     = "9";
       SORT_BY_THIS_BDESCENDING    = "10";
 /**
  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
  * All Rights Reserved
  * Copyright (C) 2006 Igor Mekterovic
  * All Rights Reserved
  */ 
       /* implementing localization  for the popup menus*/
       popUpCaptions = new HashMap(10);
       popUpCaptions.put(MOVE_DIMENSION_UP          , I18n.getString("menu.moveDimUp"));
       popUpCaptions.put(MOVE_DIMENSION_FIRST       , I18n.getString("menu.moveDimFirst"));
       popUpCaptions.put(MOVE_DIMENSION_LAST        , I18n.getString("menu.moveDimLast"));
       popUpCaptions.put(REMOVE_DIMENSION_FROM_QUERY, I18n.getString("menu.removeDim"));
       popUpCaptions.put(REMOVE_MEMBER_FROM_QUERY   , I18n.getString("menu.removeMem"));
       popUpCaptions.put(KEEP_THIS_MEMBER_ONLY      , I18n.getString("menu.keepMem"));
       popUpCaptions.put(SEND_MEMBER_TO_FILTER      , I18n.getString("menu.sendMem"));
       popUpCaptions.put(SORT_BY_THIS_ASCENDING     , I18n.getString("menu.sortAsc"));
       popUpCaptions.put(SORT_BY_THIS_DESCENDING    , I18n.getString("menu.sortDsc"));
       popUpCaptions.put(SORT_BY_THIS_BASCENDING    , I18n.getString("menu.sortBAsc"));
       popUpCaptions.put(SORT_BY_THIS_BDESCENDING   , I18n.getString("menu.sortBDsc"));
    /* end of modification for I18n */


    }
    
  

}
