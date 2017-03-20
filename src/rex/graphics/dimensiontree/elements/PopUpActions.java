package rex.graphics.dimensiontree.elements;
import rex.utils.Languages;
import java.util.HashMap;
import rex.utils.*;//sbalda

/**
 * <p>Title: WHEX</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author igor
 * @version 1.0
 */

public class PopUpActions {
     public static String
       FLATTEN_DIMENSIONS,
       GROUP_HIERARCHIES_BY_DIMENSION,
       SEND_TO_ROWS,
       SEND_TO_COLUMNS,
       SEND_TO_MEASURES,
       SEND_TO_PAGES;

    public static HashMap popUpCaptions;
    static {
       FLATTEN_DIMENSIONS = "0";
       GROUP_HIERARCHIES_BY_DIMENSION = "1";
       SEND_TO_ROWS = "2";
       SEND_TO_COLUMNS = "3";
       SEND_TO_MEASURES = "4";
       SEND_TO_PAGES = "5";
 /**
  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
  * All Rights Reserved
  * Copyright (C) 2006 Igor Mekterovic
  * All Rights Reserved
  */ 
/* implementing localization */
       popUpCaptions = new HashMap(10);
       popUpCaptions.put(FLATTEN_DIMENSIONS,I18n.getString("menu.flattenDims"));
       popUpCaptions.put(GROUP_HIERARCHIES_BY_DIMENSION,I18n.getString("menu.groupDim"));
       popUpCaptions.put(SEND_TO_ROWS,I18n.getString("menu.sendToRows"));
       popUpCaptions.put(SEND_TO_COLUMNS,I18n.getString("menu.sendToCols"));
       popUpCaptions.put(SEND_TO_MEASURES, I18n.getString("menu.sendToMeasures"));
       popUpCaptions.put(SEND_TO_PAGES, I18n.getString("menu.sendToPages"));
         /* end of modification for I18n */

    }

}