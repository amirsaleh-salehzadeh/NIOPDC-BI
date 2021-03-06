package rex.graphics.mdxeditor.mdxbuilder.nodes;

import java.util.HashMap;
import rex.utils.I18n;

/**
 * Class holding static constants used for pop-up actions.
*  Only common pop-up actions are here, pop-up actions particular to some class are held locally at that class (e.g.MBTMembersNode)
*  <br>Captions can easely be translated to other language without affecting other code.
 * @author Igor Mekterovic
 * @version 0.3
 */
public class MBTPopUpActions {
   //public static final String
      public static String
      DELETE_CHILDREN,
      INSERT_VALUE,
      REMOVE_MEMBER,
      CLEAR_VALUE,
      REMOVE_NAMED_SET,
      REMOVE,
      EDIT,
      NON_EMPTY,
      EMPTY,
      OPEN_SUBMENU,
      CLOSE_SUBMENU,
      SEPARATOR;

    public static HashMap popUpCaptions;
    static {
       DELETE_CHILDREN = "0";
       INSERT_VALUE = "1";
       REMOVE_MEMBER = "2";
       CLEAR_VALUE = "3";
       REMOVE_NAMED_SET = "4";
       REMOVE = "5";
       EDIT = "6";
       NON_EMPTY = "7";
       EMPTY = "8";
       OPEN_SUBMENU ="9";
       CLOSE_SUBMENU= "10";
       SEPARATOR = "11";
 
        /**
          * Copyright (C) 2006 CINCOM SYSTEMS, INC.
          * All Rights Reserved
          * Copyright (C) 2006 Igor Mekterovic
          * All Rights Reserved
          */ 
        /* implementing localization */

       popUpCaptions = new HashMap(15);
       popUpCaptions.put(DELETE_CHILDREN, I18n.getString("menu.deleteChildren"));
       popUpCaptions.put(INSERT_VALUE   ,I18n.getString("menu.insertValue"));
       popUpCaptions.put(REMOVE_MEMBER   , I18n.getString("menu.removeMem"));
       popUpCaptions.put(CLEAR_VALUE   , I18n.getString("menu.clearValue"));
       popUpCaptions.put(REMOVE_NAMED_SET   ,I18n.getString("menu.removeNamedSet"));
       popUpCaptions.put(REMOVE   , I18n.getString("menu.remove"));
       popUpCaptions.put(EDIT   , I18n.getString("menu.editValue"));
       popUpCaptions.put(NON_EMPTY   ,I18n.getString("menu.nonEmpty"));
       popUpCaptions.put(EMPTY   , I18n.getString("menu.allowEmpty"));
         /* end of modification for I18n */

       popUpCaptions.put(OPEN_SUBMENU   , "<<internal:open submmenu>>");
       popUpCaptions.put(CLOSE_SUBMENU   , "<<internal:close submenu>>");
       popUpCaptions.put(SEPARATOR   , "<<internal:add separator>>");




    }
}
