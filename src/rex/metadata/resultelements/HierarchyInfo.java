package rex.metadata.resultelements;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import rex.utils.I18n;
/**
 * <p>Title: WHEX</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author igor
 * @version 1.0
 */

public class HierarchyInfo {
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
   private String name;
   private String uName;
   private String caption;
   private String lName;
   private String lNum;
   private String displayInfo;

   public HierarchyInfo(Node hNode) {

      name = hNode.getAttributes().getNamedItem("name").getNodeValue();

      NodeList nl = hNode.getChildNodes();

      for(int i=0; i < nl.getLength(); i++){
         if (nl.item(i).getNodeType() == 1) {
            if (nl.item(i).getNodeName().equals("UName")) {
               uName = nl.item(i).getAttributes().getNamedItem("name").getNodeValue();
            }else if (nl.item(i).getNodeName().equals("Caption")) {
               caption = uName = nl.item(i).getAttributes().getNamedItem("name").getNodeValue();
            }else if (nl.item(i).getNodeName().equals("LName")) {
               lName = uName = nl.item(i).getAttributes().getNamedItem("name").getNodeValue();
            }else if (nl.item(i).getNodeName().equals("LNum")) {
               lNum = uName = nl.item(i).getAttributes().getNamedItem("name").getNodeValue();
            }else if (nl.item(i).getNodeName().equals("DisplayInfo")) {
               displayInfo = uName = nl.item(i).getAttributes().getNamedItem("name").getNodeValue();
            }

         }
      }
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
        return I18n.getString("toolTip.notInitiliazed");
          /* end of modification for I18n */

     }
     else
        return "" + name;
  }
  public String getCaption(){
     return name;
  }
}