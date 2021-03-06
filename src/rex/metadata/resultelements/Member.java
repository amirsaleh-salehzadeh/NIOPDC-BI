package rex.metadata.resultelements;

import org.w3c.dom.NodeList;
import org.w3c.dom.Node;

import rex.utils.*;
import rex.metadata.QueryElement;
import javax.swing.ImageIcon;
import rex.metadata.UniqueNameElement;
import rex.graphics.filtertree.*;


/**
 * <p>Title: WHEX</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author igor
 * @version 1.0
 */
/*
<Member Hierarchy="Measures">
  <UName>[Measures].[Count]</UName>
  <Caption>Count</Caption>
  <LName>[Measures].[MeasuresLevel]</LName>
  <LNum>0</LNum>
  <DisplayInfo>131072</DisplayInfo>
</Member>
*/

public class Member implements QueryElement, UniqueNameElement {
  private String hierarchy;
  private String uName;
  private String caption;
  private String lName;
  private int lNum;
  private String displayInfo;
  private int span;
  private boolean lastMemberInTuple;
  private static int INDENT_SPACE_PER_LEVEL = 2;
  static ImageIcon icon;

  static {
      icon = S.getAppIcon("measure.gif");
  }


  public Member(String _hierarchy, String _uName, String _caption, boolean _lastMemberInTuple){
     hierarchy = _hierarchy;
     uName = _uName;
     caption = _caption;
     lastMemberInTuple = _lastMemberInTuple;
     span = 1;
//     S.out(caption + " dir.init. lastmemberintupole = " + lastMemberInTuple);
  }

  public Member(Node memberNode, Member prevMember, boolean _lastMemberInTuple) {

     //S.out(memberNode.getNodeName());
     //DOM.dumpNodeAttributes(memberNode.getAttributes());
     hierarchy = memberNode.getAttributes().getNamedItem("Hierarchy").getNodeValue();

     NodeList nl = memberNode.getChildNodes();
     lastMemberInTuple = _lastMemberInTuple;

     for(int i=0; i < nl.getLength(); i++){
        if (nl.item(i).getNodeType() == 1) {
           if (nl.item(i).getNodeName().equals("UName")) {
              uName = DOM.getTextFromDOMElement(nl.item(i));
           }else if (nl.item(i).getNodeName().equals("Caption")) {
              caption = DOM.getTextFromDOMElement(nl.item(i));
           }else if (nl.item(i).getNodeName().equals("LName")) {
              lName = DOM.getTextFromDOMElement(nl.item(i));
           }else if (nl.item(i).getNodeName().equals("LNum")) {
              lNum = Integer.parseInt(DOM.getTextFromDOMElement(nl.item(i)));
           }else if (nl.item(i).getNodeName().equals("DisplayInfo")) {
              displayInfo = DOM.getTextFromDOMElement(nl.item(i));
           }
        }
     }
     if ((prevMember != null) && (uName.equals(prevMember.getUniqueName()))){
        span = 1 + prevMember.getMemberSpan();
     }else{
        span = 1;
     }
//     S.out(caption + " init. lastmemberintupole = " + lastMemberInTuple);
  }
  public Member(Node memberNode, boolean _lastMemberInTuple) {
     this(memberNode, null, _lastMemberInTuple);
  }
  public String getCaption(){
     return caption;
  }

  public String toString(){
     if (caption == null){
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
     else{
        String retVal = "";
        for(int i=0; i < INDENT_SPACE_PER_LEVEL * lNum; i++)
           retVal += " ";
        return retVal + caption;
     }
  }
  public String getUniqueName(){
     return uName;
  }
  public int getMemberSpan(){
     if (lastMemberInTuple == true)
        return 1;
     else
        return span;
  }
  public String getHierarchy(){
     return hierarchy;
  }
  public boolean equals(Member otherMember){
     if (uName.equals(otherMember.getUniqueName()))
        return true;
     else
        return false;
  }
   public String getQueryMembersExpression(){
      return getUniqueName();
   }
   public String getHierarchyUniqueName(){
//   <UName>[Measures].[Count]</UName>
      return uName.substring(0, uName.lastIndexOf("."));
   }
   private String getDimensionNameForSorting(){
     return getDimensionUniqueName();
   }
   public String getDimensionUniqueName(){
     return lName.substring(0, lName.lastIndexOf("."));
   }
   
   // copied this method from previous Rex code to try to fix build error with 1.2.4 JR   HAM 9/5/06
     public String getLname() {
       return lName;
   }
   
     /**
      *   Copyright (C) 2006 CINCOM SYSTEMS, INC.
      *   All Rights Reserved
      *   Copyright (C) 2006 Igor Mekterovic
      *   All Rights Reserved
      */
     
     /**
      * Modified by Prakash.
      * on 14 nov 06.
      * will return unique name for all dimension including measures.
      */
   public String getSortingExpression(){
      if (isMeasure()){
         return "(" + uName + ")";
      }else{
         return getDimensionNameForSorting() + ".CurrentMember.Name";
      }
   }
   public boolean isMeasure(){
      // is there a better way?
      return (uName.indexOf("Measures") != -1);
   }
   public ImageIcon getIcon(){
      if (isMeasure()){
         return icon;
      }else{
         return null;
      }
   }
   public String getToolTip(){
     /**
      * Copyright (C) 2006 CINCOM SYSTEMS, INC.
      * All Rights Reserved
      * Copyright (C) 2006 Igor Mekterovic
      * All Rights Reserved
      */ 
    /* implementing localization */
      return "<html>"
         +     "<b>"+ I18n.getString("toolTip.caption")+"</b>" + caption
         + "<br><b>Unique " + I18n.getString("toolTip.name")+ "</b>" + uName //sbalda
         + "<br><b>Level " + I18n.getString("toolTip.name")+ "</b>" + lName
//         + "<br>Display Info: " + displayInfo
         + "</html>";
  /* end of modification for I18n */

   }

}