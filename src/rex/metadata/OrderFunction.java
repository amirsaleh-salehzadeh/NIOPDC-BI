   /**
    *   Copyright (C) 2006 CINCOM SYSTEMS, INC.
    *   All Rights Reserved
    *   Copyright (C) 2006 Igor Mekterovic
    *   All Rights Reserved
    */
/*
 * Created on Jan 16, 2007
 * Author: pyadav
 */
package rex.metadata;

import rex.graphics.TreeElement;
import rex.graphics.dimensiontree.DimensionTree;
import rex.graphics.mdxeditor.mdxbuilder.MdxBuilderTree;
import rex.graphics.mdxeditor.mdxbuilder.nodes.DefaultMBTAxisNode;
import rex.graphics.mdxeditor.mdxbuilder.nodes.MBTArgEnumNode;
import rex.graphics.mdxeditor.mdxbuilder.nodes.MBTArgSetNode;
import rex.graphics.mdxeditor.mdxbuilder.nodes.MBTArgStringNode;
import rex.graphics.mdxeditor.mdxbuilder.nodes.MBTFunctionNode;
import rex.graphics.mdxeditor.mdxbuilder.nodes.MBTNode;
//import rex.graphics.mdxeditor.mdxfunctions.MdxFunction;
//import rex.graphics.mdxeditor.mdxfunctions.MdxSetFunction;
import javax.swing.JTree;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
//import javax.swing.tree.TreeModel;
import javax.swing.JOptionPane;

import rex.graphics.mdxeditor.mdxbuilder.nodes.DefaultMBTNode;
import rex.graphics.dimensiontree.DimensionTreeModel;
import rex.graphics.dimensiontree.elements.*;
//import rex.utils.S;
import rex.metadata.resultelements.Member;
import java.util.LinkedList;
import java.util.Iterator;
import rex.bidirectional.AxisEmpty;
/**
 * Implement order function on MBT based on sorting selected selected in Result area.
 * @author pyadav
 */
public class OrderFunction {

	private JTree bTree;
	private DefaultTreeModel builderTreeModel;
	private DefaultMutableTreeNode builderRoot;
  	private JTree dimTree;
  	private DimensionTreeModel dimensionTreeModel;
   /*
    * BY Prakash on 15th January.
    * Temporary variables for generating Order Node.   
    */
    private DimensionTree dimensionTree;
    private MdxBuilderTree builderTree;
    private Query query;
    
    private MBTNode[] children;
    
    private DefaultMutableTreeNode withTreeNode;
    private DefaultMutableTreeNode colTreeNode;
    private DefaultMutableTreeNode rowTreeNode;
    private DefaultMutableTreeNode pageTreeNode;
    private DefaultMutableTreeNode whereTreeNode;
    
	private String columnsOnSelect[];	 
	private String rowsOnSelect[];
	private String measuresOnSelect[];
    private String columns[];	 
	private String rows[];
	private String slicer[];
	private String memberToSort;
	private String sortType;
		
	private MBTArgSetNode setNode;
	private MBTArgStringNode sortNode;
	private MBTArgEnumNode sortTypeNode;
	/**
	 * 
	 */
	public OrderFunction(DimensionTree dimensionTree,MdxBuilderTree builderTree,Query query) {
		super();
		this.dimensionTree=dimensionTree;
		this.builderTree=builderTree;
		this.query=query;

    	bTree=this.builderTree.getTree();
       	builderTreeModel=(DefaultTreeModel) bTree.getModel();
       	builderRoot=(DefaultMutableTreeNode)builderTreeModel.getRoot();       	 
         
        bTree.repaint();
        children = (MBTNode[])((MBTNode)(builderRoot.getUserObject())).getMdxBuilderTreeNodes();        
        withTreeNode=(DefaultMutableTreeNode)builderRoot.getChildAt(0);
        colTreeNode=(DefaultMutableTreeNode)builderRoot.getChildAt(1);
        rowTreeNode=(DefaultMutableTreeNode)builderRoot.getChildAt(2);
        pageTreeNode=(DefaultMutableTreeNode)builderRoot.getChildAt(3);
        whereTreeNode=(DefaultMutableTreeNode)builderRoot.getChildAt(5);
        //dimTree
       	dimTree=this.dimensionTree.getTree();
       	dimensionTreeModel=(DimensionTreeModel)dimTree.getModel();      	
	}	
	/*
	 * Remove all nodes from MBT.
	 */
	public void removeNode()
	{
	    for (int i=0; i<builderRoot.getChildCount(); i++)
        {
            ((DefaultMBTNode)((DefaultMutableTreeNode)builderRoot.getChildAt(i)).getUserObject()).removeAllChildrenFromTheTree(  
           	(DefaultMutableTreeNode)builderRoot.getChildAt(i)
			, (DefaultTreeModel)bTree.getModel());
            ((DefaultTreeModel)bTree.getModel()).nodeChanged(builderRoot.getChildAt(i));
        }
	}

	/*
	 * Returns array of members on column. 
	 */
	public String[] getColumnsMember()
	{
		return columnsOnSelect;
	}
	/*
	 *	Set array of members on column. 
	 */
	public void setColumnsMember(String columnsOnSelect[])
	{
		this.columnsOnSelect=columnsOnSelect;
	}
	/*
	 * Returns array of members on column minus measure. 
	 */
	public String[] getColumnsMemberWOM()
	{
		return columns;
	}
	/*
	 * Set array of members on column minus measure. 
	 */
	public void setColumnsMemberWOM(String columns[])
	{
		this.columns=columns;
	}
	/*
	 * Returns array of members on row. 
	 */
	public String[] getRowsMember()
	{
		return rowsOnSelect;
	}
	/*
	 * Set array of members on row. 
	 */
	public void setRowsMember(String rowsOnSelect[])
	{
		this.rowsOnSelect=rowsOnSelect;
	}
	/*
	 * Returns array of members on row minus measures. 
	 */
	public String[] getRowsMemberWOM()
	{
		return rows;
	}
	/*
	 * Set array of members on row minus measures. 
	 */
	public void setRowsMemberWOM(String rows[])
	{
		this.rows=rows;
	}
	/*
	 * Returns array of measures. 
	 */
	public String[] getMeasures()
	{
		return measuresOnSelect;
	}
	/*
	 * Set measures. 
	 */
	public void setMeasures(String measuresOnSelect[])
	{
		this.measuresOnSelect=measuresOnSelect;
	}
	/*
	 * Set slicer. 
	 */
	public void setSlicer(String slicer[])
	{
	    this.slicer=slicer;
	}
	/*
	 * Returns member to be sort on. 
	 */
	public String getMemberToSort()
	{
		if(memberToSort==null)
		{
			return null;
		}
		else
		{
		    return memberToSort;
		}
	}
	/*
	 * Set member to sort on. 
	 */
	public void setMemberToSort(Member memberToSort)
	{
		this.memberToSort=memberToSort.getUniqueName();
	}	
	/*
	 * Returns sort type. 
	 */
	public String getSortType()
	{
		if(sortType==null)
		{
			return null;
		}
		else
		{
		    return sortType;
		}
	}
	/*
	 * Set sort type. 
	 */
	public void setSortType(String sortType)
	{
		this.sortType=sortType;
	}
	/*
	 * Used for a kind of lazy logic in dimension tree to insert child nodes 
	 * for a dimension used in sort operation.  
	 */
	public void addChildrenLevel(LinkedList queryLevels)
	{
	    Iterator it = queryLevels.iterator();	    
	    while(it.hasNext())
	    {
	        Object obj=(Object)(((DimensionTreeModel)dimensionTreeModel).getTreeElement((String)it.next()));
	        ((DimensionTreeModel)dimensionTreeModel).addChildrenOneLevel((TreeElement)obj);
	    }
	}
	/*
	 * Returns DefaultMutableTreeNode for given parameter.   
	 */
	private Object getDimensionTreeElement(String uniqueName)
	{
	    Object obj=(Object)(((DimensionTreeModel)dimensionTreeModel).getDimensionTreeElement(uniqueName.trim()));
	    if(obj==null)
	    {	        
	        JOptionPane.showMessageDialog(null,uniqueName+" not found in Dimension Tree");
	        return null;
	    }
	    else
	    {
	        return obj;
	    }
	}
	
	private void generateSlicerNodefromResult()
	{   
	    if(slicer != null && slicer.length>0)
	    {
	        for(int count=0;count<slicer.length;count++)
	        {			    
	            children[5].handleDrop(getDimensionTreeElement(slicer[count]),whereTreeNode,builderTreeModel);
	            ((DefaultTreeModel)bTree.getModel()).nodeChanged(whereTreeNode);
	            bTree.expandPath(new TreePath(whereTreeNode.getPath()));
	            bTree.repaint();
	        }
	    }
	}
	/*
	 * Check whether axis is empty or not.
	 */
	public void setAxisEmpty(AxisEmpty axisEmpty)
	{
        ((DefaultMBTAxisNode)((MBTNode)colTreeNode.getUserObject())).setNonEmpty(axisEmpty.isColumnEmpty());
        ((DefaultMBTAxisNode)((MBTNode)rowTreeNode.getUserObject())).setNonEmpty(axisEmpty.isRowEmpty());
	}
	
	private void generateColumnsNodefromResultWOM()
	{   
		for(int count=0;count<columns.length;count++)
		{		    
//			(DimensionTreeElement)((Object)((DimensionTreeModel)dimensionTreeModel).getDimensionTreeElement(columns[count].trim()))
	    	children[1].handleDrop(getDimensionTreeElement(columns[count]),colTreeNode,builderTreeModel);
	        ((DefaultTreeModel)bTree.getModel()).nodeChanged(colTreeNode);
	        bTree.expandPath(new TreePath(colTreeNode.getPath()));
	        bTree.repaint();
	    }
	}
	private void generateRowsNodefromResultWOM()
	{
	    for(int count=0;count<rows.length;count++)  
	    {
	    	children[2].handleDrop((Object)((DimensionTreeModel)dimensionTreeModel).getDimensionTreeElement(rows[count]),rowTreeNode,builderTreeModel);
	    	((DefaultTreeModel)bTree.getModel()).nodeChanged(rowTreeNode);
	    	bTree.expandPath(new TreePath(rowTreeNode.getPath()));
	    	bTree.repaint(); 
	    }
	}	
	private void generateMeasuresNodefromResult(MBTNode node,DefaultMutableTreeNode tNode)
	{
		for(int count=0;count<measuresOnSelect.length;count++)
		{
			node.handleDrop((Object)((DimensionTreeModel)dimensionTreeModel).getDimensionTreeElement(measuresOnSelect[count]),tNode,builderTreeModel);
	        ((DefaultTreeModel)bTree.getModel()).nodeChanged(tNode);
	        bTree.expandPath(new TreePath(tNode.getPath()));
	        bTree.repaint();
	    }
	}
	private void generateColumnsNodefromResult()
	{
		generateColumnsNodefromResultWOM();
		generateMeasuresNodefromResult(children[1],colTreeNode);
	}
	private void generateRowsNodefromResult()
	{
		generateRowsNodefromResultWOM();
		generateMeasuresNodefromResult(children[2],rowTreeNode);
	}
	
	public void generateOrderCombination1()	
	{
		generateColumnsNodefromResultWOM();
	    //children[2].handleDrop(getOrder(rowTreeNode,rows,memberToSort,sortType),rowTreeNode,builderTreeModel);
		getOrder(rowTreeNode,rows,memberToSort,sortType);
		generateSlicerNodefromResult();
	}	
	public void generateOrderCombination2()
	{
		generateColumnsNodefromResult();
	    //children[2].handleDrop(getOrder(rowTreeNode,rows,memberToSort,sortType),rowTreeNode,builderTreeModel);
		getOrder(rowTreeNode,rows,memberToSort,sortType);
		generateSlicerNodefromResult();
	}	
	public void generateOrderCombination3()
	{
		generateMeasuresNodefromResult(children[1],colTreeNode);
	    //children[2].handleDrop(getOrder(rowTreeNode,rows,memberToSort,sortType),rowTreeNode,builderTreeModel);
		getOrder(rowTreeNode,rows,memberToSort,sortType);
		generateSlicerNodefromResult();
	}
	public void generateOrderCombination4()
	{
		generateColumnsNodefromResultWOM();
	    //children[2].handleDrop(getOrder(rowTreeNode,rows,measuresOnSelect,memberToSort,sortType),rowTreeNode,builderTreeModel);
		getOrder(rowTreeNode,rows,measuresOnSelect,memberToSort,sortType);
		generateSlicerNodefromResult();
	}
	public void generateOrderCombination5()
	{
		generateColumnsNodefromResultWOM();
	    //children[2].handleDrop(getOrder(rowTreeNode,measuresOnSelect,memberToSort,sortType),rowTreeNode,builderTreeModel);
	    getOrder(rowTreeNode,measuresOnSelect,memberToSort,sortType);
	    generateSlicerNodefromResult();
	}	
	public void generateOrderCombination6()
	{
		//children[1].handleDrop(getOrder(colTreeNode,columns,memberToSort,sortType),colTreeNode,builderTreeModel);
		getOrder(colTreeNode,columns,memberToSort,sortType);
		generateRowsNodefromResultWOM();
		generateSlicerNodefromResult();
	}
	public void generateOrderCombination7()
	{
		//children[1].handleDrop(getOrder(colTreeNode,columns,measuresOnSelect,memberToSort,sortType),colTreeNode,builderTreeModel);
		getOrder(colTreeNode,columns,measuresOnSelect,memberToSort,sortType);
		generateRowsNodefromResultWOM();
		generateSlicerNodefromResult();
	}
	public void generateOrderCombination8()
	{
		//children[1].handleDrop(getOrder(colTreeNode,measuresOnSelect,memberToSort,sortType),colTreeNode,builderTreeModel);
		getOrder(colTreeNode,measuresOnSelect,memberToSort,sortType);
		generateRowsNodefromResultWOM();
		generateSlicerNodefromResult();
	}	
	public void generateOrderCombination9()
	{
		//children[1].handleDrop(getOrder(colTreeNode,columns,memberToSort,sortType),colTreeNode,builderTreeModel);
		getOrder(colTreeNode,columns,memberToSort,sortType);
		generateRowsNodefromResult();
		generateSlicerNodefromResult();
	}
	public void generateOrderCombination10()
	{
		//children[1].handleDrop(getOrder(colTreeNode,columns,memberToSort,sortType),colTreeNode,builderTreeModel);
		getOrder(colTreeNode,columns,memberToSort,sortType);
		generateMeasuresNodefromResult(children[2],rowTreeNode);
		generateSlicerNodefromResult();
	}	

	private void getOrder(DefaultMutableTreeNode tNode,String [] set,String memberToSort,String sortType){
        MBTFunctionNode func = new MBTFunctionNode(  "Order"
                , "Arranges members of a set, optionally preserving or breaking the hierarchy."
                , "Order(�Set�, {�String Expression� | �Numeric Expression�} [, ASC | DESC | BASC | BDESC])"
                , null);
        DefaultMutableTreeNode funcNode =  new DefaultMutableTreeNode(func);
        ((MBTNode)tNode.getUserObject()).addChild(func);
        builderTreeModel.insertNodeInto(funcNode, tNode, tNode.getChildCount());
        
		setNode=new MBTArgSetNode("�Set1�");
		sortNode=new MBTArgStringNode("�String Expression� | �Numeric Expression�");
		sortTypeNode=new MBTArgEnumNode(false, "ASC | DESC | BASC | BDESC", new String[]{"ASC", "DESC", "BASC", "BDESC"}, false, false);
		
		DefaultMutableTreeNode setTreeNode=new DefaultMutableTreeNode(setNode);
		DefaultMutableTreeNode sortTreeNode=new DefaultMutableTreeNode(sortNode);
		DefaultMutableTreeNode sortTypeTreeNode=new DefaultMutableTreeNode(sortTypeNode);
		
		func.addChild(setNode);
		builderTreeModel.insertNodeInto( setTreeNode
                , funcNode
                , funcNode.getChildCount());
		func.addChild(sortNode);
		builderTreeModel.insertNodeInto( sortTreeNode
                , funcNode
                , funcNode.getChildCount());
		func.addChild(sortTypeNode);
		builderTreeModel.insertNodeInto( sortTypeTreeNode
                , funcNode
                , funcNode.getChildCount());
		
		for(int count=0;count<set.length;count++)
		{
			setNode.handleDrop((Object)((DimensionTreeModel)dimensionTreeModel).getDimensionTreeElement(set[count]),setTreeNode,builderTreeModel);
	        ((DefaultTreeModel)bTree.getModel()).nodeChanged(setTreeNode);
	        bTree.expandPath(new TreePath(setTreeNode.getPath()));
	        bTree.repaint();	        
		}
		sortNode.handleDrop((Object)((DimensionTreeModel)dimensionTreeModel).getDimensionTreeElement(memberToSort),funcNode,builderTreeModel);
		sortTypeNode.setArgName(sortType);
        ((DefaultTreeModel)bTree.getModel()).nodeChanged(funcNode);
        bTree.expandPath(new TreePath(funcNode.getPath()));
        bTree.repaint();
	}
	
	private void getOrder(DefaultMutableTreeNode tNode,String [] set,String [] measureSet,String memberToSort,String sortType){
        MBTFunctionNode func = new MBTFunctionNode(  "Order"
                , "Arranges members of a set, optionally preserving or breaking the hierarchy."
                , "Order(�Set�, {�String Expression� | �Numeric Expression�} [, ASC | DESC | BASC | BDESC])"
                , null);
        DefaultMutableTreeNode funcNode =  new DefaultMutableTreeNode(func);
        ((MBTNode)tNode.getUserObject()).addChild(func);
        builderTreeModel.insertNodeInto(funcNode, tNode, tNode.getChildCount());
        
		setNode=new MBTArgSetNode("�Set1�");
		sortNode=new MBTArgStringNode("�String Expression� | �Numeric Expression�");
		sortTypeNode=new MBTArgEnumNode(false, "ASC | DESC | BASC | BDESC", new String[]{"ASC", "DESC", "BASC", "BDESC"}, false, true);
		
		DefaultMutableTreeNode setTreeNode=new DefaultMutableTreeNode(setNode);
		DefaultMutableTreeNode sortTreeNode=new DefaultMutableTreeNode(sortNode);
		DefaultMutableTreeNode sortTypeTreeNode=new DefaultMutableTreeNode(sortTypeNode);
		
		func.addChild(setNode);
		builderTreeModel.insertNodeInto( setTreeNode
                , funcNode
                , funcNode.getChildCount());
		func.addChild(sortNode);
		builderTreeModel.insertNodeInto( sortTreeNode
                , funcNode
                , funcNode.getChildCount());
		func.addChild(sortTypeNode);
		builderTreeModel.insertNodeInto( sortTypeTreeNode
                , funcNode
                , funcNode.getChildCount());
		
		for(int count=0;count<set.length;count++)
		{
			setNode.handleDrop((Object)((DimensionTreeModel)dimensionTreeModel).getDimensionTreeElement(set[count]),setTreeNode,builderTreeModel);
	        ((DefaultTreeModel)bTree.getModel()).nodeChanged(setTreeNode);
	        bTree.expandPath(new TreePath(setTreeNode.getPath()));
	        bTree.repaint();	        
		}
		for(int count=0;count<measureSet.length;count++)
		{
			setNode.handleDrop((Object)((DimensionTreeModel)dimensionTreeModel).getDimensionTreeElement(measureSet[count]),setTreeNode,builderTreeModel);
	        ((DefaultTreeModel)bTree.getModel()).nodeChanged(setTreeNode);
	        bTree.expandPath(new TreePath(setTreeNode.getPath()));
	        bTree.repaint();
		}
		sortNode.handleDrop((Object)((DimensionTreeModel)dimensionTreeModel).getDimensionTreeElement(memberToSort),funcNode,builderTreeModel);
		sortTypeNode.setArgName(sortType);
        ((DefaultTreeModel)bTree.getModel()).nodeChanged(funcNode);
        bTree.expandPath(new TreePath(funcNode.getPath()));
        bTree.repaint();
	}

}
