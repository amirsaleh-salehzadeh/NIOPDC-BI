/*
 * 	Created on Oct 30, 2006
 *	Bi-Directional code shift here. For future use.
 * 	Author: pyadav
 */
package rex.bidirectional;

/**	
 * Commenting to avoid unused imports. 
 * by Prakash. 08-05-2007.
 */
//import java_cup.runtime.Symbol;
//import java.io.Reader;
//import rex.graphics.mdxeditor.mdxbuilder.nodes.MBTNode;
//import rex.graphics.mdxeditor.mdxbuilder.nodes.MBTWithMembersNode;
//import rex.olap.mdxparse.Exp;
//import rex.olap.mdxparse.Formula;
//import rex.olap.mdxparse.Lexer;
//import rex.olap.mdxparse.ParsedQuery;
//import rex.olap.mdxparse.QueryAxis;
//import rex.olap.mdxparse.parser;
//import java.io.StringReader;
//import javax.swing.tree.DefaultMutableTreeNode;

/*
 * End of the modification.
 */

/**
 *   Copyright (C) 2006 CINCOM SYSTEMS, INC.
 *   All Rights Reserved
 *   Copyright (C) 2006 Igor Mekterovic
 *   All Rights Reserved
 */

public class SegregateMDXWithNWhere {

	/**
	 * 
	 */
	/*
	private String with[],where;
		String dimensionOnWith[]=null,calculatedFormula[]=null;
   		String formatStringOnWith[]=null,memberOrSet[]=null,calculatedMemberName[]=null;
   		
        DefaultMutableTreeNode setNode[]=null;
        
   		String withSetName[]=null;
   		String withSetExp[]=null;
   		
   		//Arrays for holding MDX Query Elements properties.
   		
   		String dimOnWith[]=null;
   		*/
   		/**///String dimOnColumns[]=null;
   		/**///String dimOnRows[]=null;
   		/**///String dimOnPages[]=null;
   		/*
		String dimOnWhere=null;
   		int withOrSet[]=null;
   		int countSet=0,setCounter=0;
   		String withSet[]=null,setCalculatedMemberName[]=null,setCalculatedFormula[]=null;
   		StringBuffer tempSet[]=null;
   		String member="MEMBER";
   		String set="SET";
   		String as=" AS ";
        String [][]setArray=null;
        String [][]setDimension=null;
        int [][]setAxisCount=null;
        int [][]axisSetCount=null;
        int setNum [];
        
    	public String[] getFormulas()
    	{
    		return with;
    	}
    	public String getSlicer()
    	{
    		return where;
    	}
        public int getWithSetCount()
        {
        	return 0;
        }
        public String[] getWithSet()
        {
        	
        }
        public String[] getWithSetName()
        {
        	
        }
        public String[] getWithSetExpr()
        {
        	
        }
        public int getWithMemberCount()
        {
        	return 0;
        }
        public String[] getWithMember()
        {
        	
        }
        */
        /*
         * Returns length of the formulas.
         * Returns -1 if no formula specified. 
         */
	/*
        public int getNoOfFormula()
        {
        	if(getFormulas()!=null)
        		return getFormulas().length;
        	else
        		return -1;
        }
	public SegregateMDXWithNWhere(String mdx)
	{
		mdx = mdx.replaceAll("\r", "");
	  	try {

	  	parser parser_obj;
	    Reader reader = new StringReader(mdx);
		parser_obj = new parser(new Lexer(reader));

	    Symbol parse_tree = null;
	    ParsedQuery pQuery = null;
	    
	    parse_tree = parser_obj.parse();
		 
	    pQuery = (ParsedQuery) parse_tree.value;
	    pQuery.afterParse();
	    Formula [] formula=pQuery.getFormulas();   		    
	   // String newCube = pQuery.getCube();
	    Exp slicer=pQuery.getSlicer();
	    with=new String[formula.length];
	    for(int i=0;i<formula.length;i++)
	    {
	    	with[i]=(formula[i].toMdx()).trim();
	    }
	    where=slicer.toMdx();
	    
	    //******************************************************
//	  for Getting With Properties

   		
   		if(with.length!=0)
   		{
   			withOrSet=new int[with.length];
        	for(int countWith=0;countWith<with.length;countWith++)
        	{
   				if(with[countWith].toUpperCase().startsWith(member))
   				{
   					withOrSet[countWith]=0;// means Member.
   				}
   				if(with[countWith].toUpperCase().startsWith(set))
   				{
   					withOrSet[countWith]=1;// means Set.
   					countSet++;
   					setCounter++;
   				}
        	}
        	if(setCounter!=0)
        	{
        		withSet=new String[countSet];
        		tempSet=new StringBuffer[countSet];
        		setCalculatedMemberName=new String[countSet];
        		setCalculatedFormula=new String[countSet];
        		countSet=0;
        		for(int countWith=0;countWith<with.length;countWith++)
        		{
        			if(withOrSet[countWith]==1)
        			{
        				withSet[countSet]=with[countWith].toString();
        				tempSet[countSet]=new StringBuffer(with[countWith]);
        				String str[]=with[countWith].split(as);
        				setCalculatedMemberName[countSet]=str[0].replaceAll(set,"").trim();
        				setCalculatedMemberName[countSet]=setCalculatedMemberName[countSet].substring(1,setCalculatedMemberName[countSet].length()-1);
        				setCalculatedFormula[countSet]=str[1].replaceAll("'","").trim();   					
        				countSet++;
        			}
        		}
        	}
   			calculatedFormula=new String[with.length];
   			formatStringOnWith=new String[with.length];
   			dimensionOnWith=new String[with.length];
   			dimOnWith=new String[with.length];
   			calculatedMemberName=new String[with.length];
        	for(int countWith=0;countWith<with.length;countWith++)
        	{
   				if(with[countWith].toUpperCase().startsWith(member))
   				{
   					String str[]=with[countWith].split(as);
   					str[0]=str[0].replaceAll(member,"").trim();
   					dimensionOnWith[countWith]=str[0].substring(0,str[0].indexOf(']')+1);
   					dimOnWith[countWith]=dimensionOnWith[countWith].substring(1,dimensionOnWith[countWith].length()-1);
   					calculatedMemberName[countWith]=str[0].substring(dimensionOnWith[countWith].length()+1,str[0].length());
   					calculatedMemberName[countWith]=calculatedMemberName[countWith].substring(1,calculatedMemberName[countWith].length()-1);
   					if(str[1].indexOf("FORMAT_STRING")==-1)
   					{
   						calculatedFormula[countWith]=str[1].replaceAll("'","").trim();
   					}
   					else
   					{
   						String []temp=str[1].split("FORMAT_STRING");
   						calculatedFormula[countWith]=temp[0].replaceAll(",","");
   						calculatedFormula[countWith]=calculatedFormula[countWith].replaceAll("'","").trim();
   						formatStringOnWith[countWith]="FORMAT_STRING"+temp[1].trim();
   					}
   				}
   				if(with[countWith].startsWith(set))
				{
   					String str[]=with[countWith].split(as);
   					calculatedMemberName[countWith]=str[0].replaceAll(set,"").trim();
   					calculatedMemberName[countWith]=calculatedMemberName[countWith].substring(1,calculatedMemberName[countWith].length()-1);
   					calculatedFormula[countWith]=str[1].replaceAll("'","").trim();
				}
        	}
        	if(setCounter!=0)
        	{
        		setNode=new DefaultMutableTreeNode[withSet.length];
        		setNum=new int[withSet.length];
        	}
   		}*/
   		/*
   		if(columns!=null)
   		{
   			dimOnColumns=new String[columns.length];
   			for(int count=0;count<columns.length;count++)
   			{
   				String str[]=columns[count].split("]");
   				dimOnColumns[count]=str[0].substring(1);
   			}
   		}
   		if(rows!=null)
   		{
   			dimOnRows=new String[rows.length];
   			for(int count=0;count<rows.length;count++)
   			{
   				String str[]=rows[count].split("]");
   				dimOnRows[count]=str[0].substring(1);
   			}
   		}
   		if(pages!=null)
   		{
   			dimOnPages=new String[pages.length];
   			for(int count=0;count<pages.length;count++)
   			{
   				String str[]=pages[count].split("]");
   				dimOnPages[count]=str[0].substring(1);
   			}
   		}
   		*/
	/*
   		if(where!=null)
   		{
   			String str[]=where.split("]");
   			dimOnWhere=str[0].substring(2);
   			where=where.substring(1,(where.trim()).length()-1);
   		}

        if(setCounter!=0)
        {
        	for(int setCount=0;setCount<tempSet.length;setCount++)
        	{
        		if(columns!=null)
        		{
        			for(int count=0;count<columns.length;count++)
        			{
        				if(tempSet[setCount] != null && tempSet[setCount].indexOf(columns[count])!=-1)
        				{
        					tempSet[setCount]=tempSet[setCount].replace(tempSet[setCount].indexOf(columns[count]),tempSet[setCount].indexOf(columns[count])+columns[count].length(),"");
        					setNum[setCount]++;
        				}
        			}
        		}
        		if(rows!=null)
        		{	
        			for(int count=0;count<rows.length;count++)
        			{
        				if(tempSet[setCount] != null && tempSet[setCount].indexOf(rows[count])!=-1)
        				{
        					tempSet[setCount]=tempSet[setCount].replace(tempSet[setCount].indexOf(rows[count]),tempSet[setCount].indexOf(rows[count])+rows[count].length(),"");
        					setNum[setCount]++;
        				}
        			}
        		}
        		if(pages!=null)
        		{
        			for(int count=0;count<pages.length;count++)
        			{
        				if(tempSet[setCount] != null && tempSet[setCount].indexOf(pages[count])!=-1)
        				{
        					tempSet[setCount]=tempSet[setCount].replace(tempSet[setCount].indexOf(pages[count]),tempSet[setCount].indexOf(pages[count])+pages[count].length(),"");
        					setNum[setCount]++;
        				}
        			}
        		}
        		if(where!=null)
        		{
        			if(tempSet[setCount] != null && tempSet[setCount].indexOf(where)!=-1)
        			{
        				tempSet[setCount]=tempSet[setCount].replace(tempSet[setCount].indexOf(where),tempSet[setCount].indexOf(where)+where.length(),"");
        				setNum[setCount]++;
        			}
        		}
        	}
        }
        if(setCounter!=0)
        {
        	setArray=new String [setNum.length][];
        	setDimension=new String [setNum.length][];
        	setAxisCount=new int[setNum.length][];
        	axisSetCount=new int[setNum.length][];
        	for(int count=0;count<setArray.length;count++)
        	{
        		setArray[count]=new String[setNum[count]];
        		setDimension[count]=new String[setNum[count]];
        		setAxisCount[count]=new int[4];
        		axisSetCount[count]=new int[4];
        	}        	
        	countSet=0;
        	for(int countWith=0;countWith<with.length;countWith++)
        	{
        		if(withOrSet[countWith]==1)
        		{    			
        			tempSet[countSet]=new StringBuffer(with[countWith].toString());   					
        			countSet++;
        		}
        	}
        
        	for(int setCount=0;setCount<tempSet.length;setCount++)
        	{
        		int setElementCount=0;
        		if(columns!=null)
        		{
        			for(int count=0;count<columns.length;count++)
        			{
        				if(tempSet[setCount].indexOf(columns[count])!=-1)
        				{
        					tempSet[setCount]=tempSet[setCount].replace(tempSet[setCount].indexOf(columns[count]),tempSet[setCount].indexOf(columns[count])+columns[count].length(),"");
        					setArray[setCount][setElementCount]=columns[count];
        					setDimension[setCount][setElementCount]=dimOnColumns[count];
        					columns[count]=setCalculatedMemberName[setCount];
        					setElementCount++;
        					setAxisCount[setCount][0]++;
        				}
        				if(withSet[setCount].indexOf(columns[count])!=-1)
        				{
        					axisSetCount[setCount][0]++;
        				}
        			}
        		}
        		if(rows!=null)
        		{
        			for(int count=0;count<rows.length;count++)
        			{
        				if(tempSet[setCount].indexOf(rows[count])!=-1)
        				{
        					tempSet[setCount]=tempSet[setCount].replace(tempSet[setCount].indexOf(rows[count]),tempSet[setCount].indexOf(rows[count])+rows[count].length(),"");
        					setArray[setCount][setElementCount]=rows[count];
        					setDimension[setCount][setElementCount]=dimOnRows[count];
        					rows[count]=setCalculatedMemberName[setCount];
        					setElementCount++;
        					setAxisCount[setCount][1]++;
        				}
        				if(withSet[setCount].indexOf(rows[count])!=-1)
        				{
        					axisSetCount[setCount][1]++;	
        				}
        			}
        		}
        		if(pages!=null)
        		{
        			for(int count=0;count<pages.length;count++)
        			{
        				if(tempSet[setCount].indexOf(pages[count])!=-1)
        				{
        					tempSet[setCount]=tempSet[setCount].replace(tempSet[setCount].indexOf(pages[count]),tempSet[setCount].indexOf(pages[count])+pages[count].length(),"");
        					setArray[setCount][setElementCount]=pages[count];
        					setDimension[setCount][setElementCount]=dimOnPages[count];
        					pages[count]=setCalculatedMemberName[setCount];
        					setElementCount++;
        					setAxisCount[setCount][2]++;
        				}
        				if(withSet[setCount].indexOf(pages[count])!=-1)
        				{
        					axisSetCount[setCount][2]++;
        				}
        			}
        		}
        		if(where!=null)
        		{
        			if(tempSet[setCount].indexOf(where)!=-1)
        			{
        				tempSet[setCount]=tempSet[setCount].replace(tempSet[setCount].indexOf(where),tempSet[setCount].indexOf(where)+where.length(),"");
        				setArray[setCount][setElementCount]=where;
        				setDimension[setCount][setElementCount]=dimOnWhere;
        				where=setCalculatedMemberName[setCount];
        				setElementCount++;
        				setAxisCount[setCount][3]++;
        			}
        			if(withSet[setCount].indexOf(where)!=-1)
        			{
        				axisSetCount[setCount][3]++;
        			}
        		}
        	}
        }

        if(setCounter!=0)
        {
        	for(int setCount=0;setCount<setCalculatedMemberName.length;setCount++)
        	{
        		if(axisSetCount[setCount][0]>0)
        		{
        			int noOfTimeSetInAxes=axisSetCount[setCount][0]/setAxisCount[setCount][0];
        			String temp[]=new String[columns.length-((setAxisCount[setCount][0]*noOfTimeSetInAxes)-(noOfTimeSetInAxes*1))];
        			String tempForDimension[]=new String[dimOnColumns.length-((setAxisCount[setCount][0]*noOfTimeSetInAxes)-(noOfTimeSetInAxes*1))];
        			for(int count=0,tempCount=0;count<columns.length;count++)
        			{
        				if(setCalculatedMemberName[setCount].indexOf(columns[count])!=-1)
        				{
        					count=count+(setAxisCount[setCount][0]-1);
        					temp[tempCount]=columns[count];
        					tempForDimension[tempCount]=dimOnColumns[count];
        				}
        				else
        				{
        					temp[tempCount]=columns[count];
        					tempForDimension[tempCount]=dimOnColumns[count];
        				}
        				tempCount++;        			
        			}
        			columns=temp;
        			dimOnColumns=tempForDimension;
        		}
        		if(axisSetCount[setCount][1]>0)
        		{
        			int noOfTimeSetInAxes=axisSetCount[setCount][1]/setAxisCount[setCount][1];
        			String temp[]=new String[rows.length-((setAxisCount[setCount][1]*noOfTimeSetInAxes)-(noOfTimeSetInAxes*1))];
        			String tempForDimension[]=new String[dimOnRows.length-((setAxisCount[setCount][1]*noOfTimeSetInAxes)-(noOfTimeSetInAxes*1))];
        			for(int count=0,tempCount=0;count<rows.length;count++)
        			{
        				if(setCalculatedMemberName[setCount].indexOf(rows[count])!=-1)
        				{        				
        					count=count+(setAxisCount[setCount][1]-1);
        					temp[tempCount]=rows[count];
        					tempForDimension[tempCount]=dimOnRows[count];
        				}
        				else
        				{
        					temp[tempCount]=rows[count];
        					tempForDimension[tempCount]=dimOnRows[count];
        				}
        				tempCount++;
        			}
        			rows=temp;
        			dimOnRows=tempForDimension;
        		}
        		if(axisSetCount[setCount][2]>0)
        		{
        			int noOfTimeSetInAxes=axisSetCount[setCount][2]/setAxisCount[setCount][2];
        			String temp[]=new String[pages.length-((setAxisCount[setCount][2]*noOfTimeSetInAxes)-(noOfTimeSetInAxes*1))];
        			String tempForDimension[]=new String[dimOnPages.length-((setAxisCount[setCount][2]*noOfTimeSetInAxes)-(noOfTimeSetInAxes*1))];
        			for(int count=0,tempCount=0;count<pages.length;count++)
        			{
        				if(setCalculatedMemberName[setCount].indexOf(pages[count])!=-1)
        				{        				
        					count=count+(setAxisCount[setCount][2]-1);
        					temp[tempCount]=pages[count];
        					tempForDimension[tempCount]=dimOnPages[count];
        				}
        				else
        				{
        					temp[tempCount]=pages[count];
        					tempForDimension[tempCount]=dimOnPages[count];
        				}
        				tempCount++;
        			}
        			pages=temp;
        			dimOnPages=tempForDimension;
        		}
        		if(axisSetCount[setCount][3]>0)
        		{
        			if(setCalculatedMemberName[setCount].indexOf(where)!=-1)
        			{
        			}
        		}
        	}
        	for(int setCount=0;setCount<withSet.length;setCount++)
        	{
        		setNode[setCount]=((MBTWithMembersNode)((MBTNode)withTreeNode.getUserObject())).handleSetDropFromQuery( withTreeNode,treeModel,setCalculatedMemberName[setCount]);
        	}
        }
	    
	    
	    //******************************************************
	    
	    
	    } catch (Exception e) {
	        System.out.println(e.toString());
	    }
	}

	public static void main(String[] args) {
	}
	*/
}
