package rex.graphics.datasourcetree.elements;

import javax.swing.ImageIcon;
import rex.metadata.ServerMetadata;
import rex.utils.S;
import rex.graphics.datasourcetree.*;

import rex.utils.*; //sbalda

/**
 * <p>Title: WHEX</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author igor
 * @version 1.0
 */

public class DataSourceRootElement implements DataSourceTreeElement
       {

  public DataSourceRootElement() {

  }
  static ImageIcon icon;
   static {
      icon = S.getAppIcon("DataSourceRoot.gif");
   }


   public DataSourceTreeElement[] getChildren(){
      return null;
   }

   public String toString(){
     /**
          * Copyright (C) 2006 CINCOM SYSTEMS, INC.
          * All Rights Reserved
          * Copyright (C) 2006 Igor Mekterovic
          * All Rights Reserved
          */ 
           /* implementing localization */
       return "<HTML><B>" + I18n.getString("tabTitle.dataSources") + "</B></HTML>"; //sbalda
         /* end of modification for I18n */

   }
   public ImageIcon getIcon(){
      return icon;
   }
   public String getToolTip(){
       /**
          * Copyright (C) 2006 CINCOM SYSTEMS, INC.
          * All Rights Reserved
          * Copyright (C) 2006 Igor Mekterovic
          * All Rights Reserved
          */ 
           /* implementing localization */
       return I18n.getString("tabTitle.dataSources"); 
         /* end of modification for I18n */

   }
   public String[] getPopUpActionList(){
       return new String[] {I18n.getString("menu.add")}; 
   }
   public ServerMetadata getServerMetaData(){
      return null;
   }
   public String getDataSourceInfo(){
      return null;
   }


}
