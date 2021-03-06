package rex.bidirectional;

/**
 *   Copyright (C) 2006 CINCOM SYSTEMS, INC.
 *   All Rights Reserved
 *   Copyright (C) 2006 Igor Mekterovic
 *   All Rights Reserved
 */

/*
 * Created on Nov 22, 2006
 * Author: pyadav
 * Cincom Systems
 */

public class AxisEmpty {
	private String mdxQuery;
	/**	
	 * Inserted to avoid repeated uses of String literals. 
	 * by Prakash. 08-05-2007.
	 */
		String select="SELECT";
		String rows="ROWS";
		String empty="EMPTY";
		String columns="COLUMNS";
	/*
	 * End of the Insertion.
	 */
	public AxisEmpty(String mdxQuery) {
		super();
		this.mdxQuery=mdxQuery.toUpperCase();
	}
	/**	
	 * Replacing all string literals with variables. 
	 * by Prakash. 08-05-2007.
	 */
	public boolean isColumnEmpty()
	{
		String axisPart=mdxQuery.substring(mdxQuery.indexOf(select),mdxQuery.indexOf("FROM"));
		System.out.println(axisPart);
		int columnIndex=axisPart.indexOf(columns);
		int rowIndex=axisPart.indexOf(rows);
		if(columnIndex<rowIndex)
		{
			if((axisPart.substring(axisPart.indexOf(select),axisPart.indexOf(columns))).indexOf(empty)!= -1)
			{
				return true;
			}
		}
		else
		{
			if((axisPart.substring(axisPart.indexOf(rows),axisPart.indexOf(columns))).indexOf(empty)!= -1)
			{
				return true;
			}			
		}
		return false;	
	}
	public boolean isRowEmpty()
	{
		String axisPart=mdxQuery.substring(mdxQuery.indexOf(select),mdxQuery.indexOf("FROM"));
		System.out.println(axisPart);
		int columnIndex=axisPart.indexOf(columns);
		int rowIndex=axisPart.indexOf(rows);
		if(rowIndex<columnIndex)
		{
			if((axisPart.substring(axisPart.indexOf(select),axisPart.indexOf(rows))).indexOf(empty)!= -1)
			{
				return true;
			}
		}
		else
		{
			if((axisPart.substring(axisPart.indexOf(columns),axisPart.indexOf(rows))).indexOf(empty)!= -1)
			{
				return true;
			}			
		}
		return false;
	}
	/*
	 * End of the modification.
	 */
}
