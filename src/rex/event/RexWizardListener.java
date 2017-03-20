package rex.event;
import java.util.EventListener;

/**
 *   Copyright (C) 2006 CINCOM SYSTEMS, INC.
 *   All Rights Reserved
 *   Copyright (C) 2006 Igor Mekterovic
 *   All Rights Reserved
 */

public interface RexWizardListener extends EventListener
{
	public void getMdx(RexWizardEvent evt);
}