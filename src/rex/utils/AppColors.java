package rex.utils;

import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.border.Border;
import javax.swing.UIManager;

/**
 * <p>Title: WHEX</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author igor
 * @version 1.0
 */

public interface AppColors {
// Dimension tree:
   Color  ENABLED_DIM_TREE_NODE_COLOR  = Color.BLACK
        , DISABLED_DIM_TREE_NODE_COLOR = Color.GRAY;

// CubeExplorer's colors (colors that are set in Axis class)
// Labels that represent the tree structure on dims:
   Color   HIERARCHY_PANE_BACKGROUND = new Color(204, 230, 252)
         , HIERARCHY_LABEL_FORECOLOR = Color.BLACK
         , HIERARCHY_LABEL_SELECTED_BACKGROUND = new Color(170, 200, 230)


         , CUBE_EXPLORER_PANEL_BACKGROUND = Color.WHITE;


   Border HIERARCHY_LABEL_BORDER = UIManager.getBorder("TableHeader.cellBorder");
   Border CUBE_EXPLORER_BORDER = UIManager.getBorder("TableHeader.cellBorder");

//CellTable's colors:
   Color   ODD_ROW_LINES_BACKGROUND =  new Color(0xFF, 0xFF, 0xD7)
         , EVEN_ROW_LINES_BACKGROUND = Color.WHITE
         , CELL_SELECTED = new Color(0xC0, 0xC0, 0xFF)
         , CELL_TOTAL_SUM_BACKGROUND_COLOR = new Color(204, 230, 252) //new Color(192, 245, 183)  // Color(118,234,95)
         , CELL_TOTAL_AVERAGE_BACKGROUND_COLOR =  new Color(204, 230, 252) ;// new Color(121, 188, 255); //  new Color(197,199,250)//Color(203,205,245)

   Border CELL_SELECTED_BORDER = BorderFactory.createLineBorder(Color.RED, 2)//UIManager.getBorder("Table.focusCellHighlightBorder")
        , CELL_DEFAULT_BORDER  = BorderFactory.createLineBorder(Color.BLACK)
        , CELL_TOTAL_SUM_BORDER  = UIManager.getBorder("TableHeader.cellBorder")
        , CELL_TOTAL_AVERAGE_BORDER  = UIManager.getBorder("TableHeader.cellBorder");

// Background for CubeExplorer
     int  PANEL_BACKGROUND_RED_TO = 210 //190
        , PANEL_BACKGROUND_RED_FROM = 255
        , PANEL_BACKGROUND_GREEN_TO = 240 // 230
        , PANEL_BACKGROUND_GREEN_FROM = 255
        , PANEL_BACKGROUND_BLUE  = 255;

// StatusBar
     Border   STATUS_BAR_BORDER = BorderFactory.createLineBorder(Color.BLACK, 1)
            , STATUS_BAR_TIME_BORDER = BorderFactory.createLineBorder(Color.BLACK, 1)
            , STATUS_BAR_DATA_CELLS_NO_BORDER = BorderFactory.createLineBorder(Color.BLACK, 1)
            , STATUS_BAR_PROGRESSBAR_BORDER = BorderFactory.createLineBorder(Color.BLACK, 1)
            , STATUS_BAR_STATUS_MESSAGE_BORDER = BorderFactory.createLineBorder(Color.BLACK, 1)
            , STATUS_BAR_MEM_USAGE_BORDER = BorderFactory.createLineBorder(Color.BLACK, 1)
            , STATUS_BAR_RUN_GC_BORDER = null;

     Color    STATUS_BAR_BACKGROUND = Color.WHITE;
// FilterTree Colors:
     Color   ENABLED_MEMBERS_LABEL_BACKGROUND = new Color(0xFF, 0xFF, 0xD7)
           , ENABLED_FILTER_TREE_NODE_COLOR  = Color.BLACK
           , DISABLED_FILTER_TREE_NODE_COLOR = Color.GRAY;

// Toolbar:
    Border   TOOLBAR_BORDER = BorderFactory.createLineBorder(Color.BLACK, 1)
           , TOOLBAR_LABEL_BORDER = BorderFactory.createLineBorder(Color.BLACK, 2);
// Background for CubeExplorer
       int  TOOLBAR_BACKGROUND_RED_TO = 150 //190
          , TOOLBAR_BACKGROUND_RED_FROM = 200
          , TOOLBAR_BACKGROUND_GREEN_TO = 200 // 230
          , TOOLBAR_BACKGROUND_GREEN_FROM = 225
          , TOOLBAR_BACKGROUND_BLUE  = 255;
//
    Color   MBT_NODE_NOT_COMPLETE_BACKGROUND =   new Color(0xFF, 0xFF, 0xD7);


}
