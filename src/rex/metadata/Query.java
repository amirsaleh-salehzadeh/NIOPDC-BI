package rex.metadata;

//import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Iterator;

import rex.utils.S;
import rex.bidirectional.AxisEmpty;
//import rex.graphics.Viewer;
//import rex.graphics.dimensiontree.elements.DimensionTreeElement;
import rex.metadata.resultelements.Member;
import java.util.ListIterator;
//import rex.graphics.dimensiontree.DimensionTreeModel;
import rex.graphics.filtertree.FilterTree;
import rex.graphics.filtertree.FilterTreeModel;
//import javax.swing.JTree;
import rex.graphics.mdxeditor.MdxResultViewer;
import rex.graphics.IViewer;
import javax.swing.JOptionPane;
import rex.utils.I18n;

import rex.graphics.dimensiontree.DimensionTree;

import rex.graphics.mdxeditor.mdxbuilder.MdxBuilderTree;
//import rex.graphics.mdxeditor.mdxbuilder.nodes.DefaultMBTNode;
//import rex.graphics.mdxeditor.mdxbuilder.nodes.MBTArgEnumNode;
//import rex.graphics.mdxeditor.mdxbuilder.nodes.MBTArgSetNode;
//import rex.graphics.mdxeditor.mdxbuilder.nodes.MBTArgStringNode;
//import rex.graphics.mdxeditor.mdxbuilder.nodes.MBTNode;
//import rex.graphics.mdxeditor.mdxfunctions.MdxSetFunction;

//import javax.swing.tree.DefaultMutableTreeNode;
//import javax.swing.tree.DefaultTreeModel;
//import javax.swing.tree.TreePath;

//import rex.graphics.mdxeditor.mdxfunctions.MdxFunction;// By Prakash on 15th Dec.
import rex.metadata.resultelements.Axis;
/**
 * <p>Title: WHEX</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author igor
 * @version 1.0
 */

public class Query {
   private boolean queryCanExecute;
   private LinkedList onColumns;
   private LinkedList onRows;
   private LinkedList onPages;
   private LinkedList onMeasures;
   private LinkedList filters;
   
   /*
    * By Prakash on 5th Oct.
    * Cincom Systems.
    * For getting Axis Elements for Query. 
    */
   private LinkedList onColumnsForQuery;
   private LinkedList onRowsForQuery;
   private LinkedList onPagesForQuery;
   private Axis slicerAxis;// For Slicer.
   private LinkedList onSlicer;
   private LinkedList queryLevels;
   //End
   
   private String cubeName;
   private IViewer viewer;

   private Member memberToSortBy=null;
   private String sortType;
   private String lastQueryGenerated;
   
   /*
    * BY Prakash on 15th January.
    * Object for generating Order Node.   
    */
   OrderFunction fun;
   AxisEmpty axisEmpty;
   private boolean showOnMBT;
   

   // By Prakash 
   // Hold measures axis.
   private int measureAxis=-1;
   
   /**
    *   Copyright (C) 2006 CINCOM SYSTEMS, INC.
    *   All Rights Reserved
    *   Copyright (C) 2006 Igor Mekterovic
    *   All Rights Reserved
    */
   
   // this method is used from MDX Editor
   // when there already exists an ExecuteResult object:
   public void setQuery( ExecuteResult er) { //, MdxResultViewer _v   
      queryCanExecute = true;
      onColumns.clear();
      onMeasures.clear();
      onRows.clear();
      onPages.clear();
      //By Prakash
      onColumnsForQuery.clear();
      onRowsForQuery.clear();
      onPagesForQuery.clear();
      onSlicer.clear();
      queryLevels.clear();
      showOnMBT=false;
      //End.
      
      for(int axis = 0; axis < er.getAxisCount(); axis++){
         for (int i = 0; i < er.getTupleCount(axis); i++) {
            for (int j = 0; j < er.getAxis("Axis" + axis).getTupleAt(i).getMemberCount() ; j++) {
            	//Lines Modified By Prakash
                Member member=er.getAxis("Axis" + axis).getTupleAt(i).getMemberAt(j);
                addToLevels(member.getLname());

            	String QMExp=member.getQueryMembersExpression();
            	String HUName=member.getHierarchyUniqueName();
            	QueryElement qe = new QueryElementImpl(QMExp,HUName);
                //End of the modification done by Prakash.
            	//Commented by Prakash.
               if (qe.getHierarchyUniqueName().toLowerCase().indexOf("measures") > 0 )
               {
                  addToMeasuresNoRun(qe);
                  measureAxis=axis;
                  /*
                   * Lines Inserted by Prakash
                   * Cincom Systems.
                   * Adding measures to its appropriate place to avoid any duplcate results for Bidirectional.
                   * 22nd Oct 2006.
                   */
                  switch(axis)
				  {
                  	case 0: addToColumnsForQueryNoRun(qe); break;
                  	case 1: addToRowsForQueryNoRun(qe);    break;
                  	case 2: addToPagesForQueryNoRun(qe);   break;
				  }
                  //end.
               }
               else
               {
                  switch(axis)
				  {
                     case 0:
                     		addToColumnsNoRun(qe); 
                     		addToColumnsForQueryNoRun(qe);// By Prakash for Bidirectional.
                     		break;
                     case 1: 
                     		addToRowsNoRun(qe);    
                     		addToRowsForQueryNoRun(qe);// By Prakash for Bidirectional.
                     		break;
                     case 2:
                     		addToPagesNoRun(qe);   
                     		addToPagesForQueryNoRun(qe);// By Prakash for Bidirectional.
                     		break;
                  }
               }               
            }            
         }
      }      
      slicerAxis=er.getAxis("SlicerAxis");//By Prakash For getting Slicer.
//      S.out("Constructed:\n" + getQuery());
   }


   
   public Query(String _cubeName, IViewer _v) {
      viewer = _v;
      queryCanExecute = false;
      onColumns  		= new LinkedList();
      onRows     		= new LinkedList();
      onPages    		= new LinkedList();
      onMeasures 		= new LinkedList();
      onColumnsForQuery	= new LinkedList();//By Prakash for Bidirectional.
      onRowsForQuery	= new LinkedList();//By Prakash for Bidirectional.
      onPagesForQuery	= new LinkedList();//By Prakash for Bidirectional.
      onSlicer			= new LinkedList();//By Prakash for getting slicer.
      queryLevels		= new LinkedList();//By Prakash for getting Levels.
      cubeName = _cubeName;
   }
   
   /**
    *   Copyright (C) 2006 CINCOM SYSTEMS, INC.
    *   All Rights Reserved
    *   Copyright (C) 2006 Igor Mekterovic
    *   All Rights Reserved
    */
   /*
    * By Prakash on 15th January.
    * For generating Order Node.   
    */
   public Query(String _cubeName, IViewer _v,MdxBuilderTree builderTree,DimensionTree dimTree) {
	this(_cubeName,_v); 
   	fun=new OrderFunction(dimTree,builderTree,this);
 }
   
   public void addToLevels(Object obj){
       Iterator it = queryLevels.iterator();       
  		while(it.hasNext())
   		{
  		    if(((String)it.next()).compareTo(((String)obj))==0)
  		    {
  		        return;
  		    }
   		}
  		queryLevels.addLast(obj);
   }
   
   //End
   
   
   
   
   public void addToColumns(QueryElement qe){
      addToColumnsNoRun(qe);
      addToColumnsForQueryNoRun(qe); //By Prakash for Bidirectional.
      checkRunQuery();
   }


   
   /**
    *   Copyright (C) 2006 CINCOM SYSTEMS, INC.
    *   All Rights Reserved
    *   Copyright (C) 2006 Igor Mekterovic
    *   All Rights Reserved
    */
   
/*
 * Method inserted by Prakash.
 * Filling columns list by avoiding duplicates for bidirectional.
 * 22nd Oct 2006 
 */
   public void addToColumnsForQueryNoRun(QueryElement qe)
   {
   		//  S.out("add to columns");
   		QueryDimensionElement qde;
   		Iterator it = onColumnsForQuery.iterator();
   		while(it.hasNext())
   		{
   			qde = (QueryDimensionElement)it.next();
   			if (qde.getHierarchyUniqueName().startsWith(qe.getHierarchyUniqueName())
   					|| qe.getHierarchyUniqueName().startsWith(qde.getHierarchyUniqueName())){
   				//     if (qde.getHierarchyUniqueName().equals(qe.getHierarchyUniqueName())){
   				// a) if there was only e.g. Time.Year.Members(i.e. QueryDimensionElement.members==null)
   				//   , and we're trying to add Time.Year.2002 then Time.Year.Members will be lost,
   				//     and produced MDX will be only Time.Year.2002
   				// 	b) if there was Time.Year.2001 already in list (i.e. QueryDimensionElement.members!=null)
   				//   then Time.Year.2002 will be added to list, and produced MDX will be:
   				//    {Time.Year.2001, Time.Year.2002}
   				qde.addMemberToList(qe);
   				return;
   			}
   		}
   		onColumnsForQuery.addLast(new QueryDimensionElement(qe));
   }
   


   /**
    *   Copyright (C) 2006 CINCOM SYSTEMS, INC.
    *   All Rights Reserved
    *   Copyright (C) 2006 Igor Mekterovic
    *   All Rights Reserved
    */
   
   /*
    * Method inserted by Prakash.
    * Filling rows list by avoiding duplicates for bidirectional.
    * 22nd Oct 2006 
    */
   public void addToRowsForQueryNoRun(QueryElement qe)
   {
   		//  S.out("add to rows");
   		QueryDimensionElement qde;
   		Iterator it = onRowsForQuery.iterator();
   		while(it.hasNext()){
   			qde = (QueryDimensionElement)it.next();
   			if (qde.getHierarchyUniqueName().startsWith(qe.getHierarchyUniqueName())
   					|| qe.getHierarchyUniqueName().startsWith(qde.getHierarchyUniqueName())){   				
   				// see comment at addToColumns
   				qde.addMemberToList(qe);
   				return;
   			}
   		}
   		onRowsForQuery.addLast(new QueryDimensionElement(qe));

   }
   
   
   /**
    *   Copyright (C) 2006 CINCOM SYSTEMS, INC.
    *   All Rights Reserved
    *   Copyright (C) 2006 Igor Mekterovic
    *   All Rights Reserved
    */
   
   /*
    * Method inserted by Prakash.
    * Filling pages list by avoiding duplicates for bidirectional.
    * 22nd Oct 2006 
    */
   public void addToPagesForQueryNoRun(QueryElement qe)
   {
   		//  S.out("add to pages");
   		QueryDimensionElement qde;
   		Iterator it = onPagesForQuery.iterator();
   		while(it.hasNext()){
   			qde = (QueryDimensionElement)it.next();
   			if (qde.getHierarchyUniqueName().startsWith(qe.getHierarchyUniqueName())
   					|| qe.getHierarchyUniqueName().startsWith(qde.getHierarchyUniqueName())){
   				// see comment at addToColumns
   				qde.addMemberToList(qe);
   				return;
   			}
   		}
   		onPagesForQuery.addLast(new QueryDimensionElement(qe));
   }

   
   public void addToColumnsNoRun(QueryElement qe){
//      S.out("add to columns");
      QueryDimensionElement qde;
      Iterator it = onColumns.iterator();
      while(it.hasNext()){
         qde = (QueryDimensionElement)it.next();
         if (qde.getHierarchyUniqueName().startsWith(qe.getHierarchyUniqueName())
             || qe.getHierarchyUniqueName().startsWith(qde.getHierarchyUniqueName())){
//         if (qde.getHierarchyUniqueName().equals(qe.getHierarchyUniqueName())){
            // a) if there was only e.g. Time.Year.Members(i.e. QueryDimensionElement.members==null)
            //   , and we're trying to add Time.Year.2002 then Time.Year.Members will be lost,
            //     and produced MDX will be only Time.Year.2002
            // b) if there was Time.Year.2001 already in list (i.e. QueryDimensionElement.members!=null)
            //   then Time.Year.2002 will be added to list, and produced MDX will be:
            //    {Time.Year.2001, Time.Year.2002}
            qde.addMemberToList(qe);
            return;
         }
      }
      onColumns.addLast(new QueryDimensionElement(qe));
   }

   
   public void addToRows(QueryElement qe){
      addToRowsNoRun(qe);
      addToRowsForQueryNoRun(qe);//By Prakash for Bidirectional.
      checkRunQuery();
   }
   public void addToRowsNoRun(QueryElement qe){
//      S.out("add to rows");
      QueryDimensionElement qde;
      Iterator it = onRows.iterator();
      while(it.hasNext()){
         qde = (QueryDimensionElement)it.next();

         if (qde.getHierarchyUniqueName().startsWith(qe.getHierarchyUniqueName())
             || qe.getHierarchyUniqueName().startsWith(qde.getHierarchyUniqueName())){
            // see comment at addToColumns
            qde.addMemberToList(qe);
            return;
         }
      }
      onRows.addLast(new QueryDimensionElement(qe));

   }
   
   
   public void addToPages(QueryElement qe){
      addToPagesNoRun(qe);
      addToPagesForQueryNoRun(qe);// By Prakash for Bidirectional.
      checkRunQuery();
   }
   public void addToPagesNoRun(QueryElement qe){
//      S.out("add to pages");
      QueryDimensionElement qde;
      Iterator it = onPages.iterator();
      while(it.hasNext()){
         qde = (QueryDimensionElement)it.next();
         if (qde.getHierarchyUniqueName().startsWith(qe.getHierarchyUniqueName())
             || qe.getHierarchyUniqueName().startsWith(qde.getHierarchyUniqueName())){
            // see comment at addToColumns
            qde.addMemberToList(qe);
            return;
         }
      }
      onPages.addLast(new QueryDimensionElement(qe));
   }

   
   public void addToMeasures(QueryElement qe){
      addToMeasuresNoRun(qe);
      //switch(measureAxis)
	  //{
      //	case 0: addToColumnsForQueryNoRun(qe); break;
      //	case 1: addToRowsForQueryNoRun(qe);    break;
      //	case 2: addToPagesForQueryNoRun(qe);   break;
      //	case -1: addToColumnsForQueryNoRun(qe); break;
	  //}
      addToColumnsForQueryNoRun(qe);//By Prakash for Bidirectional.
      measureAxis=0; //By Prakash. Measures are on columns.
      checkRunQuery();
   }
   public void addToMeasuresNoRun(QueryElement qe){
//      S.out("add to measures");
      QueryDimensionElement qde;
      Iterator it = onMeasures.iterator();
      while(it.hasNext()){
         qde = (QueryDimensionElement)it.next();
         if (qde.getHierarchyUniqueName().startsWith(qe.getHierarchyUniqueName())
             || qe.getHierarchyUniqueName().startsWith(qde.getHierarchyUniqueName())){
            qde.addMemberToList(qe);
            // unlike addToRows and Pages I must checkRun it :
//            checkRunQuery();
            return;
         }
      }
      onMeasures.addLast(new QueryDimensionElement(qe));
   }
   
   
   public String getCaptionForRows(){
      Iterator it = onRows.iterator();
      String retVal = "";
     /**
      * Copyright (C) 2006 CINCOM SYSTEMS, INC.
      * All Rights Reserved
      * Copyright (C) 2006 Igor Mekterovic
      * All Rights Reserved
      */ 
      /* implementing localization */
      if (!it.hasNext())
      {
          return I18n.getString("label.dropRows");
      }
        /* end of modification for I18n */

      while (it.hasNext()) {
         retVal += ((QueryElement) it.next()).getHierarchyUniqueName() + " * ";
      }
      return retVal.substring(0, retVal.length() - 3);

   }
   public String getCaptionForPages(){
      Iterator it = onPages.iterator();
      String retVal = "";
        /**
          * Copyright (C) 2006 CINCOM SYSTEMS, INC.
          * All Rights Reserved
          * Copyright (C) 2006 Igor Mekterovic
          * All Rights Reserved
          */ 
        /* implementing localization */      
      if (!it.hasNext())
      {
          return I18n.getString("str.dropPagesHere");
      }
        /* end of modification for I18n */

      while (it.hasNext()) {
         retVal += ((QueryElement) it.next()).getHierarchyUniqueName() + " * ";
      }
      return retVal.substring(0, retVal.length() - 3);

   }
   public String getCaptionForColumns(){
      Iterator it = onColumns.iterator();
      String retVal = "";
        /**
          * Copyright (C) 2006 CINCOM SYSTEMS, INC.
          * All Rights Reserved
          * Copyright (C) 2006 Igor Mekterovic
          * All Rights Reserved
          */ 
        /* implementing localization */      
      
      if (!it.hasNext())
      {
          return I18n.getString("label.dropColumns");
      }
        /* end of modification for I18n */

      while(it.hasNext()){
         retVal += ((QueryElement)it.next()).getHierarchyUniqueName() + " * ";
      }
      return retVal.substring(0, retVal.length() - 3);

   }
   public String getCaptionForMeasures(){
      Iterator it = onMeasures.iterator();
      String retVal = "";
        /**
          * Copyright (C) 2006 CINCOM SYSTEMS, INC.
          * All Rights Reserved
          * Copyright (C) 2006 Igor Mekterovic
          * All Rights Reserved
          */ 
        /* implementing localization */      
      
      if (!it.hasNext())
      {
          return I18n.getString("label.dropMeasures");
      }
        /* end of modification for I18n */

      while (it.hasNext()) {
         retVal += ( (QueryElement) it.next()).getHierarchyUniqueName() + " * ";
      }
      return retVal.substring(0, retVal.length() - 3);

   }

   public void checkRunQuery(){
//      S.out("Query:\n" + this.toString());
      //if (canExecute()){
         viewer.refreshQuery();
      //}
   }

   public boolean canExecute(){
   	
   	/**
   	 *   Copyright (C) 2006 CINCOM SYSTEMS, INC.
   	 *   All Rights Reserved
   	 *   Copyright (C) 2006 Igor Mekterovic
   	 *   All Rights Reserved
   	 */
   	
   	//Commented by Prakash Cincom Systems. on 16 Nov. 2006
      //if (onRows.size() * onMeasures.size() > 0 ) // onColumns.size() *
   	/**
   	* Modified by Prakash Cincom Systems on 16 Nov 2006.
   	* For Sorting on Rows too (Earlier was on columns only).
   	*/
   	if (onRowsForQuery.size() * onColumnsForQuery.size() > 0 ) // onColumns.size() *
   	{
         return true;
   	}
      else
      {
         return false;
      }
   }
   
  	/**
  	 *   Copyright (C) 2006 CINCOM SYSTEMS, INC.
  	 *   All Rights Reserved
  	 *   Copyright (C) 2006 Igor Mekterovic
  	 *   All Rights Reserved
  	 */
  	/**
  	* Inserted by Prakash Cincom Systems on 29 Jan 2007.
  	* For Sorting on Rows too (Earlier was on columns only).
  	*/ 
   public boolean canExecuteOrder(){
      	if (onRowsForQuery.size() * onColumnsForQuery.size() > 0 )
      	{
            return true;
      	}
         else
         {
            return false;
         }
   }
   
   
   private String getAxisQueryExpression(LinkedList axDims){
      Iterator it = axDims.iterator();
      String retVal = "     ";
      while(it.hasNext()){
         retVal += "{" + ((QueryDimensionElement)it.next()).getQueryMembersExpression() + "}\n * ";
      }
      return retVal.substring(0, retVal.length()-3);
   }

   public void toggleRowsDrillState(Member member){
      QueryDimensionElement qde;
      Iterator it = onRows.iterator();
      while(it.hasNext()){
         qde = (QueryDimensionElement)it.next();
         if (member.getUniqueName().startsWith(qde.getHierarchyUniqueName())){
            qde.toggleDrillState(member);
            break;
         }
      }
      checkRunQuery();
   }
   public void togglePagesDrillState(Member member){
      QueryDimensionElement qde;
      Iterator it = onPages.iterator();
      while(it.hasNext()){
         qde = (QueryDimensionElement)it.next();
         if (member.getUniqueName().startsWith(qde.getHierarchyUniqueName())){
            qde.toggleDrillState(member);
            break;
         }
      }
      checkRunQuery();
   }
   public void toggleColumnsDrillState(Member member){
      QueryDimensionElement qde;
      Iterator it = onColumns.iterator();
      while(it.hasNext()){
         qde = (QueryDimensionElement)it.next();
         if (member.getUniqueName().startsWith(qde.getHierarchyUniqueName())){
            qde.toggleDrillState(member);
            break;
         }
      }
      checkRunQuery();
   }

   public void moveColumnDimensionUp(Member member){
      moveDimensionUp(member, onColumns);
   }
   public void moveRowDimensionUp(Member member){
      moveDimensionUp(member, onRows);
   }
   public void movePageDimensionUp(Member member){
      moveDimensionUp(member, onPages);
   }
   private void moveDimensionUp(Member member, LinkedList linkList){
      QueryDimensionElement qde;
      Object element, previousElement;
      ListIterator it = linkList.listIterator();
      while (it.hasNext()) {
         qde = (QueryDimensionElement)it.next();
         if (member.getUniqueName().startsWith(qde.getHierarchyUniqueName())) {
            element =  it.previous();
            if (it.hasPrevious()){
               previousElement = it.previous();
               it.remove();
               it.next();
               it.add(previousElement);
               checkRunQuery();
            }
            break;
         }
      }

   }

   public void moveColumnDimensionFirst(Member member){
      moveDimensionFirst(member, onColumns);
   }
   public void moveRowDimensionFirst(Member member){
      moveDimensionFirst(member, onRows);
   }
   public void movePageDimensionFirst(Member member){
      moveDimensionFirst(member, onPages);
   }
   private void moveDimensionFirst(Member member, LinkedList linkList){
      QueryDimensionElement qde;
      Object element, previousElement;
      ListIterator it = linkList.listIterator();
      while (it.hasNext()) {
         qde = (QueryDimensionElement)it.next();
         if (member.getUniqueName().startsWith(qde.getHierarchyUniqueName())) {
            element =  it.previous();
            if (it.hasPrevious()){
               it.remove();
               linkList.addFirst(element);
               checkRunQuery();
            }
            break;
         }
      }

   }

   public void moveColumnDimensionLast(Member member){
      moveDimensionLast(member, onColumns);
   }
   public void moveRowDimensionLast(Member member){
      moveDimensionLast(member, onRows);
   }
   public void movePageDimensionLast(Member member){
      moveDimensionLast(member, onPages);
   }
   private void moveDimensionLast(Member member, LinkedList linkList){
      QueryDimensionElement qde;
      Object element, previousElement;
      ListIterator it = linkList.listIterator();
      while (it.hasNext()) {
         qde = (QueryDimensionElement)it.next();
         if (member.getUniqueName().startsWith(qde.getHierarchyUniqueName())) {
            if (it.hasNext()){
               it.previous();
               it.remove();
               linkList.addLast(qde);
               checkRunQuery();
            }
            break;
         }
      }

   }
   public void removeFilterRowDimensionFromQuery(Member member){
      // don't enable this dim. because if it is in a filter it cannot be on an axis
      removeDimensionFromQuery(member, onRows, false);
      removeDimensionFromQuery(member, onRowsForQuery, false);//By Prakash.
   }
   public void removeFilterColumnDimensionFromQuery(Member member){
      // don't enable this dim. because if it is in a filter it cannot be on an axis
      removeDimensionFromQuery(member, onColumns, false);
      removeDimensionFromQuery(member, onColumnsForQuery, false);//By Prakash.
   }
   public void removeFilterPageDimensionFromQuery(Member member){
      // don't enable this dim. because if it is in a filter it cannot be on an axis
      removeDimensionFromQuery(member, onPages, false);
      removeDimensionFromQuery(member, onPagesForQuery, false);//By Prakash.
   }
   public void removeRowDimensionFromQuery(Member member){
      removeDimensionFromQuery(member, onRows, true);
      removeDimensionFromQuery(member, onRowsForQuery, true);//By Prakash.
   }
   public void removeColumnDimensionFromQuery(Member member){
      removeDimensionFromQuery(member, onColumns, true);
      removeDimensionFromQuery(member, onColumnsForQuery, true);//By Prakash.
   }
   public void removePageDimensionFromQuery(Member member){
      removeDimensionFromQuery(member, onPages, true);
      removeDimensionFromQuery(member, onPagesForQuery, true);//By Prakash.
   }
   private void removeDimensionFromQuery(Member member, LinkedList linkList, boolean enableTreeElements){
      QueryDimensionElement qde;
      ListIterator it = linkList.listIterator();
      
      /**
       *   Copyright (C) 2006 CINCOM SYSTEMS, INC.
       *   All Rights Reserved
       *   Copyright (C) 2006 Igor Mekterovic
       *   All Rights Reserved

      if(linkList.size()==1)
      {
          
      }
       */
      
      while (it.hasNext()) {
         qde = (QueryDimensionElement)it.next();
//         S.out(member.getUniqueName() + " starts with " + qde.getHierarchyUniqueName() + "?");
         if (member.getUniqueName().startsWith(qde.getHierarchyUniqueName())) {
            it.previous();
            it.remove();
            if (enableTreeElements){
               viewer.enableTreeElements( (QueryElement) qde);
            }
            checkRunQuery();
            break;
         }
      }

   }

   public void removeRowMemberFromQuery(Member memberToRemove){
      removeMemberFromQuery(memberToRemove, onRows);
      removeMemberFromQuery(memberToRemove, onRowsForQuery);//By Prakash.
   }
   public void removeColumnMemberFromQuery(Member memberToRemove){
      removeMemberFromQuery(memberToRemove, onColumns);
      removeMemberFromQuery(memberToRemove, onColumnsForQuery);//By Prakash.
   }
   public void removeMeasureMemberFromQuery(Member memberToRemove){
      removeMemberFromQuery(memberToRemove, onMeasures);
      switch(measureAxis)
	  {
      	case 0: removeMemberFromQuery(memberToRemove, onColumnsForQuery); break;
      	case 1: removeMemberFromQuery(memberToRemove, onRowsForQuery);    break;
      	case 2: removeMemberFromQuery(memberToRemove, onPagesForQuery);   break;
      	case -1: removeMemberFromQuery(memberToRemove, onColumnsForQuery); break;
      }
      
   }
   public void removePageMemberFromQuery(Member memberToRemove){
      removeMemberFromQuery(memberToRemove, onPages);
      removeMemberFromQuery(memberToRemove, onPagesForQuery);//By Prakash.
   }
   public void removeMemberFromQuery(Member memberToRemove, LinkedList linkList){
      // this HAS to be a member, cannot be a level, so it's not QueryElement but Member var.
      QueryDimensionElement qde;
      ListIterator it = linkList.listIterator();
      if (memberToSortBy!=null && memberToSortBy== memberToRemove){
         loseSort();
      }
      while (it.hasNext()) {
         qde = (QueryDimensionElement)it.next();
         S.out("memberToRemove.getUniqueName() = " + memberToRemove.getUniqueName());
         S.out("qde.getHierarchyUniqueName()=" + qde.getHierarchyUniqueName());
         if (memberToRemove.getUniqueName().startsWith(qde.getHierarchyUniqueName())) {
            qde.removeMember((QueryElement)memberToRemove);
            // this could have been a last member in a dimension
            // which renders it invalid and should be removed from a query
            if (!qde.isValid()){
               it.previous();
               it.remove();
            }
            break;
         }
      }
      viewer.enableTreeElements((QueryElement)memberToRemove);
      checkRunQuery();
   }
   public void keepThisMemberOnlyOnRows(Member memberToKeep){
      keepThisMemberOnly(memberToKeep, onRows);
      keepThisMemberOnly(memberToKeep, onRowsForQuery);//By Prakash.
   }
   public void keepThisMemberOnlyOnColumns(Member memberToKeep){
      keepThisMemberOnly(memberToKeep, onColumns);
      keepThisMemberOnly(memberToKeep, onColumnsForQuery);// By Prakash.
   }
   public void keepThisMemberOnlyOnMeasures(Member memberToKeep){
      keepThisMemberOnly(memberToKeep, onMeasures);
      switch(measureAxis)
	  {
      	case 0: keepThisMemberOnly(memberToKeep, onColumnsForQuery); break;// By Prakash.
      	case 1: keepThisMemberOnly(memberToKeep, onRowsForQuery);    break;// By Prakash.
      	case 2: keepThisMemberOnly(memberToKeep, onPagesForQuery);   break;// By Prakash.
      	case -1: keepThisMemberOnly(memberToKeep, onColumnsForQuery); break;// By Prakash.
	  }      
   }
   public void keepThisMemberOnlyOnPages(Member memberToKeep){
      keepThisMemberOnly(memberToKeep, onPages);
      keepThisMemberOnly(memberToKeep, onPagesForQuery);//By Prakash.
   }

   public void keepThisMemberOnly(Member memberToKeep, LinkedList linkList){
      QueryDimensionElement qde;
      ListIterator it = linkList.listIterator();
      if (   memberToSortBy!=null
          && memberToSortBy.getSortingExpression().equals(memberToKeep.getSortingExpression())){
         loseSort();
      }
      while (it.hasNext()) {
         qde = (QueryDimensionElement)it.next();
         if (memberToKeep.getUniqueName().startsWith(qde.getHierarchyUniqueName())) {
            qde.keepThisMemberOnly((QueryElement)memberToKeep);
            break;
         }
      }
      checkRunQuery();
   }
   
   public void clearRowAxis(){
      // enable these guys first:
      enableAxisMembers(onRows);
      onRows.clear();
      onRowsForQuery.clear();// By Prakash.
      checkRunQuery();
   }
   public void clearColumnAxis(){
      enableAxisMembers(onColumns);
      enableAxisMembers(onMeasures);
      onColumns.clear();
      onMeasures.clear();
      onColumnsForQuery.clear();// By Prakash.
      checkRunQuery();
   }
   private void enableAxisMembers(LinkedList list){
      Iterator it = list.iterator();
      while (it.hasNext()) {
         viewer.enableTreeElements( (QueryElement) it.next());
      }
   }
   
   /**
    *   Copyright (C) 2006 CINCOM SYSTEMS, INC.
    *   All Rights Reserved
    *   Copyright (C) 2006 Igor Mekterovic
    *   All Rights Reserved
    */
   
   /* 
    * By Prakash on 07 Nov 06.
    * Modified method to retrieve axis infomation additionally.
    * Asking member and sort type.
    */ 
   public void sortByThisMember(Member _memberToSortBy, String _sortType){
      memberToSortBy = _memberToSortBy;
      sortType = _sortType;     
      checkRunQuery();
   }
   public void loseSort(){
      memberToSortBy = null;
   }

   public void addRowMemberToFilter(Member filterMember){
      if (onRows.size() == 1 && (viewer instanceof MdxResultViewer)){
        /**
          * Copyright (C) 2006 CINCOM SYSTEMS, INC.
          * All Rights Reserved
          * Copyright (C) 2006 Igor Mekterovic
          * All Rights Reserved
          */ 
        /* implementing localization */          
         JOptionPane.showMessageDialog(   null
                                        , I18n.getString("msgText.dimensionFilter")//"You cannot send last dimension to filter(there must be at least one dimension left)!\n" //sbalda
                                        , I18n.getString("msgTitle.sendMember")// "Send member to filter" //sbalda
                                        , JOptionPane.INFORMATION_MESSAGE);
          /* end of modification for I18n */

         return;
      }
      if (filters == null){
         filters = new LinkedList();
      }
      filters.add(viewer.addMemberToFilter(filterMember));
      removeFilterRowDimensionFromQuery(filterMember);
//      checkRunQuery(); --> removeFilterRowDim... will do that
   }
   public void addColumnMemberToFilter(Member filterMember){
      if (onColumns.size() == 1 && (viewer instanceof MdxResultViewer)){
        /**
          * Copyright (C) 2006 CINCOM SYSTEMS, INC.
          * All Rights Reserved
          * Copyright (C) 2006 Igor Mekterovic
          * All Rights Reserved
          */ 
        /* implementing localization */          
         JOptionPane.showMessageDialog(   null
                                         , I18n.getString("msgText.dimensionFilter")//"You cannot send last dimension to filter(there must be at least one dimension left)!\n" //sbalda
                                        , I18n.getString("msgTitle.sendMember")// "Send member to filter" //sbalda
                                        , JOptionPane.INFORMATION_MESSAGE);
          /* end of modification for I18n */

         return;
      }
      if (filters == null){
         filters = new LinkedList();
      }
      filters.add(viewer.addMemberToFilter(filterMember));
      removeFilterColumnDimensionFromQuery(filterMember);
//      checkRunQuery();  --> removeFilterColumnDim... will do that
   }
   public void addPageMemberToFilter(Member filterMember){
      if (filters == null){
         filters = new LinkedList();
      }
      filters.add(viewer.addMemberToFilter(filterMember));
      removeFilterPageDimensionFromQuery(filterMember);
//      checkRunQuery();  --> removeFilterColumnDim... will do that
   }

   public void dropFilterFromQuery(FilterTree filterToDrop){
      FilterTreeModel ftm;
      ListIterator it = filters.listIterator();
      while(it.hasNext()){
         ftm = (FilterTreeModel)it.next();
         if( ftm == (FilterTreeModel)filterToDrop.getTree().getModel()){
            it.previous();
            it.remove();
            viewer.dropFilterTree(filterToDrop);
            viewer.enableTreeElements(ftm.getAnyQueryElement());
            checkRunQuery();
            return;
         }
      }
      S.out("assert: Query:dropFilterFromQuery:Couldn't find filter to drop!");
   }
// ****************************************************************************************************
// When query is executed, I must update it with results that I get in the following cases:
//  1. (previous) query contained ToggleDrillState function, and now one of the QueryDimensionElements
//      is toggled, i.e. I must update it, and remove toggle flag
//  2. ?
   public void updateQueryWithResults(ExecuteResult er){
      QueryDimensionElement qde;
      Iterator it = onRows.iterator();
      while(it.hasNext()){
         qde = (QueryDimensionElement)it.next();
         if (qde.isToggled()){
            qde.updateWithResult(er.getAxis("Axis1"));
         }
      }
      it = onColumns.iterator();
      while(it.hasNext()){
         qde = (QueryDimensionElement)it.next();
         if (qde.isToggled()){
            qde.updateWithResult(er.getAxis("Axis0"));
         }
      }
      if (onPages.size()>0){
         it = onPages.iterator();
         while (it.hasNext()) {
            qde = (QueryDimensionElement) it.next();
            if (qde.isToggled()) {
               qde.updateWithResult(er.getAxis("Axis2"));
            }
         }
      }
   }
   
   /**
    *   Copyright (C) 2006 CINCOM SYSTEMS, INC.
    *   All Rights Reserved
    *   Copyright (C) 2006 Igor Mekterovic
    *   All Rights Reserved
    */
   
   /*
    * Method Inserted by prakash.
    * Cincom Systems.
    * Returns array of Formula.
    * 10th Oct 2006. 
    */
   
   public String[] getMDXWith()
   {
   	String [] mdxElement  = null;
    if (canExecute())
    	{
       // Finding With Element.    	
    		if (filters != null && filters.size()>0)
    		{
    			mdxElement=new String[filters.size()];
    			int countWith=0;
    			ListIterator it = filters.listIterator();    			
    			while(it.hasNext())
    			{
    				// getQueryWithMemberExpression is indented by default:
    				mdxElement[countWith]= "" + ( (FilterTreeModel) it.next()).getQueryWithMemberExpression();
    				countWith++;
    			}
    			return mdxElement;
    		}
    	}
    	return null;
    }
//end.
   
   
   
   /*
    * Method Inserted by prakash.
    * Cincom Systems.
    * Returns dimensions array of Columns.
    * 10th Oct 2006. 
    */
   
   public String[] getMDXColumnsOnSelect()
   {  
   	LinkedList list=null;
   	String colElements[]=null;
   	int colSize=0,colCounter=0;
   	for(int counter=0;counter<onColumnsForQuery.size();counter++)
   	{
   	   	list=((QueryDimensionElement)onColumnsForQuery.get(counter)).getMember();
   	   	colSize=colSize+list.size();
   	}
   	colElements=new String[colSize];
   	for(int counter=0;counter<onColumnsForQuery.size();counter++)
   	{
   		list=((QueryDimensionElement)onColumnsForQuery.get(counter)).getMember();
   	   	for(int jj=0;jj<list.size();jj++)
   	   	{
   	   		colElements[colCounter]=((QueryElement)list.get(jj)).getQueryMembersExpression();
   	   		colCounter++;
   	   	}
   	}
   	return colElements;

   	}
//end.
   
   /*
    * Method Inserted by prakash.
    * Cincom Systems.
    * Returns dimensions array of Rows.
    * 10th Oct 2006. 
    */   
   
   public String[] getMDXRowsOnSelect()
   {
	LinkedList list=null;
   	String rowElements[]=null;
   	int rowSize=0,rowCounter=0;
   	for(int counter=0;counter<onRowsForQuery.size();counter++)
   	{
   	   	list=((QueryDimensionElement)onRowsForQuery.get(counter)).getMember();
   	   	rowSize=rowSize+list.size();
   	}
   	rowElements=new String[rowSize];
   	for(int counter=0;counter<onRowsForQuery.size();counter++)
   	{
   		list=((QueryDimensionElement)onRowsForQuery.get(counter)).getMember();
   	   	for(int jj=0;jj<list.size();jj++)
   	   	{
   	   		rowElements[rowCounter]=((QueryElement)list.get(jj)).getQueryMembersExpression();
   	   		rowCounter++;
   	   	}
   	}
   	return rowElements;
   }
//end.
   
   /*
    * Method Inserted by prakash.
    * Cincom Systems.
    * Returns dimensions array of Pages.
    * 10th Oct 2006. 
    */   
   
   public String[] getMDXPagesOnSelect()
   {
   	//if(mdxAxis.length>2)
   	//	return mdxAxis[2];
   	//else
   	//	return null;
   	LinkedList list=null;
   	String pageElements[]=null;
   	int pageSize=0,pageCounter=0;
   	if(onPagesForQuery.size()>0)
   	{
   		for(int counter=0;counter<onPagesForQuery.size();counter++)
   		{
   			list=((QueryDimensionElement)onPagesForQuery.get(counter)).getMember();
   			pageSize=pageSize+list.size();
   		}
   		pageElements=new String[pageSize];
   		for(int counter=0;counter<onPagesForQuery.size();counter++)
   		{
   			list=((QueryDimensionElement)onPagesForQuery.get(counter)).getMember();
   			for(int jj=0;jj<list.size();jj++)
   			{
   				pageElements[pageCounter]=((QueryElement)list.get(jj)).getQueryMembersExpression();
   				pageCounter++;
   			}
   		}
   		return pageElements;
   	}
   	else 
   	{
   		return null;
   	}
   }
   //end.
   
   /*
    * Method Inserted by prakash.
    * Cincom Systems.
    * Returns from.
    * 10th Oct 2006. 
    */   
   
   public String getMDXFrom()
   {
   		return cubeName;
   }
   	//end.

   /*
    * Method Inserted by prakash.
    * Cincom Systems.
    * Returns Slicer.
    * 10th Oct 2006. 
    */
   
   public String[] getMDXWhere()
   {
   	String [] mdxElement  = null;
    if (canExecute())
    	{
       // Finding With Element.    	
        if (filters != null && filters.size()>0){
            ListIterator it = filters.listIterator();
            mdxElement=new String[filters.size()];
            int countWith=0;
            while(it.hasNext()){
               // getQueryWithMemberExpression is indented by default:
               if (!it.hasPrevious()){
                  mdxElement[countWith]="" + ( (FilterTreeModel) it.next()).getQueryWhereExpression();
               }else{
                  mdxElement[countWith]="," + ( (FilterTreeModel) it.next()).getQueryWhereExpression();
               }
            }
            return mdxElement;
         }    		
    	}
    	return null;
    }
   
   /*
    * Method Inserted by prakash.
    * Cincom Systems.
    * Returns dimensions array of Rows (Excludes Measure).
    * 15th Jan 2007
    */   
   
   public String[] getMDXRows()
   {
	LinkedList list=null;
   	String rowElements[]=null;
   	int rowSize=0,rowCounter=0;
   	for(int counter=0;counter<onRows.size();counter++)
   	{
   	   	list=((QueryDimensionElement)onRows.get(counter)).getMember();
   	   	rowSize=rowSize+list.size();
   	}
   	rowElements=new String[rowSize];
   	for(int counter=0;counter<onRows.size();counter++)
   	{
   		list=((QueryDimensionElement)onRows.get(counter)).getMember();
   	   	for(int jj=0;jj<list.size();jj++)
   	   	{
   	   		rowElements[rowCounter]=((QueryElement)list.get(jj)).getQueryMembersExpression();
   	   		rowCounter++;
   	   	}
   	}
   	return rowElements;
   }

   /*
    * Method Inserted by prakash.
    * Cincom Systems.
    * Returns dimensions array of Columns (Excludes Measure).
    * 15th Jan 2007. 
    */   
   
   public String[] getMDXColumns()
   {
	LinkedList list=null;
   	String colElements[]=null;
   	int colSize=0,colCounter=0;
   	for(int counter=0;counter<onColumns.size();counter++)
   	{
   	   	list=((QueryDimensionElement)onColumns.get(counter)).getMember();
   	   	colSize=colSize+list.size();
   	}
   	colElements=new String[colSize];
   	for(int counter=0;counter<onColumns.size();counter++)
   	{
   		list=((QueryDimensionElement)onColumns.get(counter)).getMember();
   	   	for(int jj=0;jj<list.size();jj++)
   	   	{
   	   		colElements[colCounter]=((QueryElement)list.get(jj)).getQueryMembersExpression();
   	   		colCounter++;
   	   	}
   	}
   	return colElements;
   }
   
   /*
    * Method Inserted by prakash.
    * Cincom Systems.
    * Returns Measures array.
    * 15th Jan 2007. 
    */   
   
   public String[] getMDXMeasures()
   {
	LinkedList list=null;
   	String measureElements[]=null;
   	int measureSize=0,measureCounter=0;
   	for(int counter=0;counter<onMeasures.size();counter++)
   	{
   	   	list=((QueryDimensionElement)onMeasures.get(counter)).getMember();
   	   	measureSize=measureSize+list.size();
   	}
   	measureElements=new String[measureSize];
   	for(int counter=0;counter<onMeasures.size();counter++)
   	{
   		list=((QueryDimensionElement)onMeasures.get(counter)).getMember();
   	   	for(int jj=0;jj<list.size();jj++)
   	   	{
   	   		measureElements[measureCounter]=((QueryElement)list.get(jj)).getQueryMembersExpression();
   	   		measureCounter++;
   	   	}
   	}
   	return measureElements;
   }
   
   public String[] getSlicer()
   {
       int count=0;
       String temp[]=null;
       if(onSlicer != null && onSlicer.size()>0)
       {
           temp=new String[onSlicer.size()];
           ListIterator it = onSlicer.listIterator();           
           while(it.hasNext())
           {               
                   temp[count]=(String) it.next();
                   count++;
           }
       }
   		return temp;
   }

   /**
    *   Copyright (C) 2006 CINCOM SYSTEMS, INC.
    *   All Rights Reserved
    *   Copyright (C) 2006 Igor Mekterovic
    *   All Rights Reserved
    */
   
   public String getQuery(){
      String mdx  = "";
     // if (canExecute()){ // Commented by Prakash 
      if ((onColumnsForQuery.size()*onRowsForQuery.size())>0){

                /**
                  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
                  * All Rights Reserved
                  * Copyright (C) 2006 Igor Mekterovic
                  * All Rights Reserved
                  */ 
                /* implementing localization */            

      
         // Query generation
         if (filters != null && filters.size()>0){
            ListIterator it = filters.listIterator();
            mdx += "WITH";
            while(it.hasNext()){
               // getQueryWithMemberExpression is indented by default:
               mdx += "\n" + ( (FilterTreeModel) it.next()).getQueryWithMemberExpression();
            }
            mdx += "\n";
         }
         mdx += "SELECT ";
         mdx += getColumnsEmptyStatus();
 
         if (memberToSortBy == null){
         	if(measureAxis==-1)
         	{
         		mdx += "{\n"
         			+ "      {" + getAxisQueryExpression(onColumns) + "}"
					+ "\n}  ON COLUMNS, \n";
         		mdx += getRowsEmptyStatus();
  
         		mdx += "{\n"
         			+ getAxisQueryExpression(onRows)
					+ "\n} ON ROWS";
         	}
         	if(measureAxis==0)
         	{
         		if(onColumns.size()>0)
         		{
         			mdx += "{\n"
         				+ "      {" + getAxisQueryExpression(onColumns) + "}"
						+ "\n    * {" + getAxisQueryExpression(onMeasures) + "}"
						+ "\n}  ON COLUMNS, \n";
         		}
         		else
         		{
         			mdx += "{\n"
						+ "\n    {" + getAxisQueryExpression(onMeasures) + "}"
						+ "\n}  ON COLUMNS, \n";         			
         		}
         		mdx += getRowsEmptyStatus();
         
         		mdx += "{\n"
         			+ getAxisQueryExpression(onRows)
					+ "\n} ON ROWS";
         	}
         	if(measureAxis==1)
         	{
         		mdx += "{\n"
         			+ "      {" + getAxisQueryExpression(onColumns) + "}"
					+ "\n}  ON COLUMNS, \n";
         		mdx += getRowsEmptyStatus();
         		if(onRows.size()>0)
         		{
         			mdx += "{\n"
         				+ "      {" + getAxisQueryExpression(onRows) + "}"
						+ "\n    * {" + getAxisQueryExpression(onMeasures) + "}"
						+ "\n} ON ROWS";
         		}
         		else
         		{
         			mdx += "{\n"         				
						+ "\n    {" + getAxisQueryExpression(onMeasures) + "}"
						+ "\n} ON ROWS";         			
         		}
         	}
         }else{
         	/**
            mdx += "{\n"
                +  "   Order( {\n"
                +  getAxisQueryExpression(onRows)
                +  "\n       }"
                +  "\n   , " + memberToSortBy.getSortingExpression()
                +  "\n   , " + sortType + ")"
                +  "\n} ON ROWS";*/

			//Inserted By Prakash.
           	int queryAxisSort=getMemberAxis(memberToSortBy);
          	if(queryAxisSort==-1)
          	{
                    /**
                      * Copyright (C) 2006 CINCOM SYSTEMS, INC.
                      * All Rights Reserved
                      * Copyright (C) 2006 Igor Mekterovic
                      * All Rights Reserved
                      */ 
                    /* implementing localization */            
          		JOptionPane.showMessageDialog(null,memberToSortBy.getUniqueName()+ 
                            I18n.getString("str.notOnAxis") );//" is neither on column axis nor on row axis.");
                      /* end of modification for I18n */

          		return null;
          	}
          	if(showOnMBT)
          	{
          	fun.addChildrenLevel(queryLevels);
          	fun.setAxisEmpty(axisEmpty);
         	fun.setMemberToSort(memberToSortBy);// Prakash 16 Jan 07 
         	fun.setSortType(sortType);//Prakash 16 Jan 07
         	fun.removeNode(); 	
         	fun.setColumnsMember(getMDXColumnsOnSelect());
         	fun.setColumnsMemberWOM(getMDXColumns());
         	fun.setRowsMember(getMDXRowsOnSelect());
         	fun.setRowsMemberWOM(getMDXRows());
         	fun.setMeasures(getMDXMeasures());
         	fun.setSlicer(getSlicer());
          	}
         	if(queryAxisSort==0)
         	{
             	if(measureAxis==-1)
             	{
             		mdx += "{\n"
             			+ "      {" + getAxisQueryExpression(onColumns) + "}"
						+ "\n}  ON COLUMNS, \n";
             		mdx += getRowsEmptyStatus();
             		//mdx += (axisEmpty.isRowEmpty() ? "\n\n" : "\nNON EMPTY\n");
             		//mdx += "\nNON EMPTY\n";
             		mdx += "{\n"
             			+  "   Order( {\n"
						+  getAxisQueryExpression(onRows)
						+  "\n       }"
						+  "\n   , " + memberToSortBy.getUniqueName()
						+  "\n   , " + sortType + ")"
						+  "\n} ON ROWS";
                  		
             			if(showOnMBT)
                  		{
						fun.generateOrderCombination1();//prakash 16 Jan 07
                  		}
             	}
             	if(measureAxis==0)
             	{
             		if(onColumns.size()>0)
             		{
             		mdx += "{\n"
             			+ "      {" + getAxisQueryExpression(onColumns) + "}"
             			+ "\n    * {" + getAxisQueryExpression(onMeasures) + "}"
						+ "\n}  ON COLUMNS, \n";
                  		if(showOnMBT)
                  		{
						fun.generateOrderCombination2();// Prakash 16 Jan 07
                  		}	
             		}
             		else
             		{
                 		mdx += "{\n"                 			
                 			+ "\n    {" + getAxisQueryExpression(onMeasures) + "}"
    						+ "\n}  ON COLUMNS, \n";      
                      		if(showOnMBT)
                      		{		
							fun.generateOrderCombination3();// Prakash 16 Jan 07
                      		}
             		}
             		mdx += getRowsEmptyStatus();
             		mdx += "{\n"
             			+  "   Order( {\n"
						+  getAxisQueryExpression(onRows)
						+  "\n       }"
						+  "\n   , " + memberToSortBy.getUniqueName()
						+  "\n   , " + sortType + ")"
						+  "\n} ON ROWS";
             	}
             	if(measureAxis==1)
             	{
             		mdx += "{\n"
             			+ "      {" + getAxisQueryExpression(onColumns) + "}"
						+ "\n}  ON COLUMNS, \n";
             		mdx += getRowsEmptyStatus();
             		if(onRows.size()>0)
             		{
             			mdx += "{\n"
             				+  "   Order( {\n"
							+  getAxisQueryExpression(onRows)
							+ "\n    * {" + getAxisQueryExpression(onMeasures) + "}"
							+  "\n       }"
							+  "\n   , " + memberToSortBy.getUniqueName()
							+  "\n   , " + sortType + ")"
							+  "\n} ON ROWS";
                      		if(showOnMBT)
                      		{
							fun.generateOrderCombination4();// Prakash 16 Jan 07
                      		}
             		}
             		else
             		{
             			mdx += "{\n"
             				+  "   Order( {\n"							
							+ "\n    {" + getAxisQueryExpression(onMeasures) + "}"
							+  "\n       }"
							+  "\n   , " + memberToSortBy.getUniqueName()
							+  "\n   , " + sortType + ")"
							+  "\n} ON ROWS";            
                      	
             				if(showOnMBT)
             				{	
							fun.generateOrderCombination5();// Prakash 16 Jan 07
             				}
             		}
             	}
         	}
         	if(queryAxisSort==1)
         	{
             	if(measureAxis==-1)
             	{
             		mdx += "{\n"
             			+  "   Order( {\n"
						+  getAxisQueryExpression(onColumns)
						+  "\n       }"
						+  "\n   , " + memberToSortBy.getUniqueName()
						+  "\n   , " + sortType + ")"
						+  "\n} ON COLUMNS,";
             		mdx += getRowsEmptyStatus();
             		mdx += "{\n"
             			+ "      {" + getAxisQueryExpression(onRows) + "}"
						+ "\n}  ON ROWS \n";
                  	
             			if(showOnMBT)
             			{
						fun.generateOrderCombination6();// Prakash 16 Jan 07
             			}
             	}
             	if(measureAxis==0)
             	{
             		if(onColumns.size()>0)
             		{
             			mdx += "{\n"
             				+  "   Order( {\n"
							+  getAxisQueryExpression(onColumns)
							+ "\n    * {" + getAxisQueryExpression(onMeasures) + "}"
							+  "\n       }"
							+  "\n   , " + memberToSortBy.getUniqueName()
							+  "\n   , " + sortType + ")"
							+  "\n} ON COLUMNS,";
                      		if(showOnMBT)
                      		{
							fun.generateOrderCombination7();// Prakash 16 Jan 07
                      		}
             		}
             		else
             		{
             			mdx += "{\n"
             				+  "   Order( {\n"							
							+ "\n     {" + getAxisQueryExpression(onMeasures) + "}"
							+  "\n       }"
							+  "\n   , " + memberToSortBy.getUniqueName()
							+  "\n   , " + sortType + ")"
							+  "\n} ON COLUMNS,";             	
                      		
             				if(showOnMBT)
                      		{	
							fun.generateOrderCombination8();// Prakash 16 Jan 07
                      		}
             		}
             		mdx += getRowsEmptyStatus();
             		mdx += "{\n"
             			+ "      {" + getAxisQueryExpression(onRows) + "}"
						+ "\n}  ON ROWS \n";
             	}
             	if(measureAxis==1)
             	{
             		mdx += "{\n"
             			+  "   Order( {\n"
						+  getAxisQueryExpression(onColumns)
						+  "\n       }"
						+  "\n   , " + memberToSortBy.getUniqueName()
						+  "\n   , " + sortType + ")"
						+  "\n} ON COLUMNS,";
             		mdx += getRowsEmptyStatus();
             		if(onRows.size()>0)
             		{
             			mdx += "{\n"
             				+ "      {" + getAxisQueryExpression(onRows) + "}"
							+ "\n    * {" + getAxisQueryExpression(onMeasures) + "}"
							+ "\n}  ON ROWS \n";
                      		if(showOnMBT)
                      		{
							fun.generateOrderCombination9();// Prakash 16 Jan 07
                      		}
             		}
             		else
             		{
             			mdx += "{\n"
							+ "\n    {" + getAxisQueryExpression(onMeasures) + "}"
							+ "\n}  ON ROWS \n";
             			
                      		if(showOnMBT)
                      		{		
							fun.generateOrderCombination10();// Prakash 16 Jan 07
                      		}
             		}
             	}
            }
         }
         if (onPages.size()>0){
            mdx += ",";
            mdx += "\nNON EMPTY\n";
            mdx += "   {\n"
               + getAxisQueryExpression(onPages)
               + "\n   } ON PAGES";
         }

         mdx += "\nFROM [" + cubeName + "]";

         if (filters != null && filters.size()>0){
            ListIterator it = filters.listIterator();
            mdx += "\nWHERE\n   (";
            while(it.hasNext()){
               // getQueryWithMemberExpression is indented by default:
               if (!it.hasPrevious()){
                  mdx += "\n    " + ( (FilterTreeModel) it.next()).getQueryWhereExpression();
               }else{
                  mdx += "\n    , " + ( (FilterTreeModel) it.next()).getQueryWhereExpression();
               }
            }
            mdx += "\n    )\n";
         }
         //By Prakash for slicer from SOAP.
         else if(onSlicer != null && onSlicer.size()>0)
         {
             ListIterator it = onSlicer.listIterator();
             mdx += "\nWHERE\n   (";
             while(it.hasNext()){
                 if (!it.hasPrevious()){
                     mdx += "\n    " + ( (String) it.next());
                 }else{
                     mdx += "\n    ," + ( (String) it.next());
                 }
             }
             mdx += "\n    )\n";
         }
             

         S.out("GENERATED QUERY:\n---------------------------\n\n" + mdx + "\n\n------------------------------");
         lastQueryGenerated = mdx;
         memberToSortBy=null;
         return mdx;         
      }else{
      	memberToSortBy=null;
         return null;
      }
   }
   
   /**
    *   Copyright (C) 2006 CINCOM SYSTEMS, INC.
    *   All Rights Reserved
    *   Copyright (C) 2006 Igor Mekterovic
    *   All Rights Reserved
    */
   
   public void setQueryForWhere(String query)
   {
       axisEmpty=new AxisEmpty(query);
       if(query!=null && query.length()>0)
       {           
           if(query.indexOf("WHERE")!=-1)
           {
               query=query.substring(query.indexOf("WHERE"),query.length());
               for (int i = 0; i < slicerAxis.getTupleCount() ; i++)
               {
                 	for (int j = 0; j < slicerAxis.getTupleAt(i).getMemberCount() ; j++) 
                 	{           
                 	    if(query.indexOf(slicerAxis.getTupleAt(i).getMemberAt(j).getUniqueName())!=-1)
                 	    {
                 	       onSlicer.addLast(slicerAxis.getTupleAt(i).getMemberAt(j).getUniqueName());
                 	    }
                 	
                 	}
               }
           }
       }
   }
   
   public void updateMBTree(boolean bool)
   {
       showOnMBT=bool;
   }
   
   private String getColumnsEmptyStatus()
   {
       if(axisEmpty != null)
       {
           return (axisEmpty.isColumnEmpty() ? "\n\n" : "\nNON EMPTY\n");
       }
       else
       {
           return "\nNON EMPTY\n";
       }
   }
   
   private String getRowsEmptyStatus()
   {
       if(axisEmpty != null)
       {
           return (axisEmpty.isRowEmpty() ? "\n\n" : "\nNON EMPTY\n");
       }
       else
       {
           return "\nNON EMPTY\n";
       }
   }
   /**
    *   Copyright (C) 2006 CINCOM SYSTEMS, INC.
    *   All Rights Reserved
    *   Copyright (C) 2006 Igor Mekterovic
    *   All Rights Reserved
    */
   
   /*
    * Inserted by prakash on 07 nov 2006.
    * Return members axis. 
    */
      private int getMemberAxis(Member member)
      {
       QueryDimensionElement qde;
       Iterator it = onColumnsForQuery.iterator();
       while(it.hasNext()){
          qde = (QueryDimensionElement)it.next();
          if (qde.getHierarchyUniqueName().startsWith(member.getHierarchyUniqueName())
              || member.getHierarchyUniqueName().startsWith(qde.getHierarchyUniqueName())){
             return 0;
          }
       }
       it = onRowsForQuery.iterator();
       while(it.hasNext()){
          qde = (QueryDimensionElement)it.next();
          if (qde.getHierarchyUniqueName().startsWith(member.getHierarchyUniqueName())
              || member.getHierarchyUniqueName().startsWith(qde.getHierarchyUniqueName())){
             return 1;
          }
       }
      	return -1;
      }
   public String toString(){
      return getQuery();
   }
   public String getLastQueryGenerated(){
      return lastQueryGenerated;
   }



}
