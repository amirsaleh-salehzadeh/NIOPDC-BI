package rex.metadata.resultelements;

import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import java.util.Vector;
import  rex.utils.*;

/**
 * <p>Title: WHEX</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author igor
 * @version 1.0
 */
/*
 - <Tuple>
   - <Member Hierarchy="Measures">
     <UName>[Measures].[Count]</UName>
     <Caption>Count</Caption>
     <LName>[Measures].[MeasuresLevel]</LName>
     <LNum>0</LNum>
     <DisplayInfo>131072</DisplayInfo>
     </Member>
   - <Member Hierarchy="Time">
     <UName>[Time].[1997].[Q3]</UName>
     <Caption>Q3</Caption>
     <LName>[Time].[Quarter]</LName>
     <LNum>1</LNum>
     <DisplayInfo>131075</DisplayInfo>
     </Member>
  </Tuple>
*/
public class Tuple{

  private Vector members;
  private boolean lastMemberInTuple;

  public Tuple(Node tupleNode, Tuple prevTuple) {
     int i, j;
     NodeList ml = tupleNode.getChildNodes();
     members = new Vector();
     int memberCount = DOM.getCountOfChildNodesOfType(tupleNode, DOM.ELEMENT_TYPE);
     // must keep track of last member in tuple:
     // this guy will have a span = 1, becasue I don't want
     // last members in tuple joining up, and also because this
     // causes an error in display: see 2nd test query in
     // CubeExplorer's main

     if (ml.getLength()>0){
        for (i = 0, j = 0; i < ml.getLength(); i++) {
           if (ml.item(i).getNodeType() == DOM.ELEMENT_TYPE ) {
              if (j == memberCount-1){
                 lastMemberInTuple = true;
              }else{
                 lastMemberInTuple = false;
              }
              if (prevTuple != null){
                 members.add(new Member(ml.item(i), prevTuple.getMemberAt(j), lastMemberInTuple));
              }else{
                 members.add(new Member(ml.item(i), lastMemberInTuple));
              }
              j++; // j is an actual number of members allocated
                   // because there are some non-DOM.ELEMENT_TYPE members
           }
        }
     }
  }
//  public Tuple(Node tupleNode) {
//     this(tupleNode, null);
//  }

  public Tuple(Vector _members){
     // this one is for debugging purposese
     this.members = _members;
  }

  public int getMemberCount(){
     if (members != null)
        return members.size();
     else
        return 0;
  }

  public Member getMemberAt(int i){
     if ( i >= 0  &&  i<= members.size()){
        return (Member) members.elementAt(i);
     }else{
        S.out("assert: Tuple.getmemberAt(i) - "
              + "i = " + i
              + " is out of the array range = 0.." + (members.size()-1));
        return null;
     }
  }

  public Member getMemberByHierarchyName(String hierarchy){
     for(int i = 0;  i <= members.size(); i++){
        if (hierarchy.equals(((Member)members.elementAt(i)).getHierarchy()))
           return (Member)members.elementAt(i);
     }
     return null;
  }

  public boolean equals(Tuple otherTuple){
     for(int i=0; i < getMemberCount(); i++){
        if (!(otherTuple.getMemberAt(i).equals(getMemberAt(i)))) return false;
     }
     // if all members are equal, then the tuple is equal
     // members obvoisuly HAVE TO BE  IN THE SAME ORDER
     return true;
  }

  public String toString(){
     String s = "(";
     for(int i=0; i < getMemberCount(); i++){
        s += getMemberAt(i).getUniqueName() + ", ";
     }
     return (s + ")");
  }
  public String getShortCaption(){
     if(getMemberCount()>0){
        String s = "";
        for (int i = 0; i < getMemberCount(); i++) {
           s += getMemberAt(i).getCaption() + ", ";
        }
        return s.substring(0, s.length()-2);
     }
      
        /**
          * Copyright (C) 2006 CINCOM SYSTEMS, INC.
          * All Rights Reserved
          * Copyright (C) 2006 Igor Mekterovic
          * All Rights Reserved
          */ 
        /* implementing localization */
       return I18n.getString("toolTip.noMembers");
         /* end of modification for I18n */

  }
  public String getToolTip(){
       
        /**
          * Copyright (C) 2006 CINCOM SYSTEMS, INC.
          * All Rights Reserved
          * Copyright (C) 2006 Igor Mekterovic
          * All Rights Reserved
          */ 
        /* implementing localization */
     if(getMemberCount()>0){
        String s = "<html>" + I18n.getString("toolTip.tuple");
        for (int i = 0; i < getMemberCount(); i++) {
           s += "<hr>" + i + ".member <b>Unique"+ 
                   I18n.getString("toolTip.name") +"</b>" + getMemberAt(i).getUniqueName();
        }
        s += "<hr></html>";
        return s;
     }
     //return "NO MEMBERS?";
     return I18n.getString("toolTip.noMembers");
       /* end of modification for I18n */
  }

}