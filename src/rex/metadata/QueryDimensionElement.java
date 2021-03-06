package rex.metadata;

import java.util.LinkedList;
import java.util.Iterator;
import rex.graphics.dimensiontree.elements.LevelElement;
import rex.utils.S;
import rex.metadata.resultelements.Member;
import rex.metadata.resultelements.Axis;
import java.util.ListIterator;

/**
 * <p>Title: WHEX</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author igor
 * @version 1.0
 */

public class QueryDimensionElement implements QueryElement{
   private LinkedList except; //QueryElement
   private LinkedList members;
   private String hierarchyUniqueName;
   private String levelExpression;
   private boolean toggled;
   private boolean valid;
   private String toggleExpression;
   
   private String levelName;

   public QueryDimensionElement(QueryElement qe) {
      hierarchyUniqueName = qe.getHierarchyUniqueName();
      toggled = false;
      valid = true;
      if (!(qe instanceof LevelElement)){
         // LevelElement has no members in a linked list
         addMemberToList(qe);
      }else{
         levelExpression = qe.getQueryMembersExpression();
      }
   }
  
   
   public boolean hasMember(QueryElement qe){
      if (members == null) return false;
      Iterator it = members.iterator();
      while(it.hasNext()){
         if (((QueryElement)it.next()).getQueryMembersExpression().equals(qe.getQueryMembersExpression()))
            return true;
      }
      return false;
   }
   public void addMemberToList(QueryElement qe){
//      S.out("add memeber to list qe=" + qe);
      if (!hasMember(qe)){
         if (members == null){
            members = new LinkedList();
         }
         members.add(qe);
      }
//      S.out(this.toString());
   }
   public void addMemberToExceptList(QueryElement qe){
      if (except == null){
         except = new LinkedList();
      }
      except.add(qe);
   }

   public String getHierarchyUniqueName(){
      return hierarchyUniqueName;
   }

   public String getQueryMembersExpression(){
//      S.out("\ngetQueryMembersExpression: toggleExpression:\n"+toggleExpression + "\ntoggled=" + toggled);

      if (toggled){
         return toggleExpression;
      }else{
         String retVal;
         if (members == null) {
            // meaning: this is a level
            retVal = levelExpression;
         }
         else {
            retVal = "";
            Iterator it = members.iterator();
            while (it.hasNext()) {
               retVal += ( (QueryElement) it.next()).getQueryMembersExpression() + ", ";
            }
            retVal = retVal.substring(0, retVal.length() - 2);
         }

         if (except!=null && except.size()>0){
            String ex = "";
            Iterator it = except.iterator();
            while (it.hasNext()) {
               ex += ( (QueryElement) it.next()).getQueryMembersExpression() + ", ";
            }
            retVal = "Except({" + retVal + "}, {" + ex.substring(0, ex.length() - 2) + "})";
         }
         return retVal;
      }
      // to be done: order, ...
   }
   public String toString(){
      return "QueryDimensionElement"
            +  "\n  hierarchyUniqueName="+hierarchyUniqueName
            + "\n  getQueryMembersExpression = " + getQueryMembersExpression();
   }

   public void toggleDrillState(Member member){
      if (toggled){
         S.out("assert: can't toggle QueryDimensionElement twice!");
      }else{
         toggleExpression = "ToggleDrillState("
            + "{" + getQueryMembersExpression() + "}"
            + ", {" + member.getUniqueName() + "}"
            + ")"; // , RECURSIVE
         toggled = true;
//         S.out("\ntoggleDrillState: toggleExpression:\n"+toggleExpression + "\ntoggled=" + toggled);
      }

   }
   public boolean isToggled(){
      return toggled;
   }
//   ToggleDrillState
//   Toggles the drill state of members.
//
//   Syntax
//   ToggleDrillState(�Set1�, �Set2�[, RECURSIVE])
//
//   Remarks
//   This function is a combination of DrillupMember and DrilldownMember. It toggles the drill state of each member of �Set2� that is present in �Set1�. If a member m of �Set2� that is present in �Set1� is drilled down (that is, has a descendant), DrillupMember(�Set1�, {m}) is applied. If it is drilled up (that is, there is no descendant of m that immediately follows m), DrilldownMember(�Set1�, {m}[, RECURSIVE]) is applied to Set1. The optional RECURSIVE flag is used if ToggleDrillState was called with RECURSIVE.
//
//   Example
//   ToggleDrillState({Product.Bread.Members},{Product.Bagels, Product.Muffins}, RECURSIVE)
//
//
//   See Also
//
//   DrillupMember
//
//   DrilldownMember
//



// first make it work, than make it work good :)
   public void updateWithResult(Axis a){
      int memberOrdinal=-1, i;
      if (!toggled) {S.out("assert: updateWithResult called with toggled=false ?");return;}
      if (members != null){
         members.clear();
      }

      for(i=0; i < a.getHierarchyInfoCount(); i++){
         if (a.getTupleAt(0).getMemberAt(i).getUniqueName().startsWith(getHierarchyUniqueName())){
            // found it!
            memberOrdinal = i;
            break;
         }
      }
      if (memberOrdinal<0){S.out("assert: updateWithResult: couldn't find memberOrdinal");return;}
      for(i=0; i<a.getTupleCount(); i++){
         addMemberToList((QueryElement)a.getTupleAt(i).getMemberAt(memberOrdinal));
      }
      toggled = false;
//      S.out("updateWithResult:\n" + getQueryMembersExpression());
   }

   void removeMember(QueryElement memberToRemove){
      if (!hasMember(memberToRemove)){
         addMemberToExceptList(memberToRemove);
      }else{
         ListIterator it = members.listIterator();
         while (it.hasNext()) {
            if (((QueryElement) it.next()).getQueryMembersExpression().equals(memberToRemove.getQueryMembersExpression())){
               it.previous();
               it.remove();
               // if I remove the last member in a dimesnion it becomes invalid
               // and should be removed from the query
               if (members.size()==0){
                  valid = false;
               }
            }
         }
      }
   }

   void keepThisMemberOnly(QueryElement memberToKeep){
      if (members != null) members.clear();
      if (except != null)  except.clear();
      addMemberToList(memberToKeep);
   }

   public boolean isValid(){
      return valid;
   }

   /**
    *   Copyright (C) 2006 CINCOM SYSTEMS, INC.
    *   All Rights Reserved
    *   Copyright (C) 2006 Igor Mekterovic
    *   All Rights Reserved
    */
   
   /*
    * Method inserted by Prakash.
	* Cincom Systems.
    * on 5th Sept 06.
    * return axis elements.
    */
   public LinkedList getMember()
   {
   		if(members!=null)
   			return members;
   		else
   			return null;
   }
   //end.
}
