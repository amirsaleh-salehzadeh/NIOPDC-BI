package rex.event;
import java.util.EventObject;

/**
 *   Copyright (C) 2006 CINCOM SYSTEMS, INC.
 *   All Rights Reserved
 *   Copyright (C) 2006 Igor Mekterovic
 *   All Rights Reserved
 */

public class RexWizardEvent extends EventObject
{
	private String mQuery;
	public RexWizardEvent(String mdxQuery)
	{
		super(mdxQuery);
		this.mQuery=mdxQuery;
	}
	public String getQuery()
	{
		return mQuery;
	}
}