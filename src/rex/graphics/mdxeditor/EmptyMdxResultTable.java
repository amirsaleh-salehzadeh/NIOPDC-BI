package rex.graphics.mdxeditor;

import javax.swing.JPanel;
import javax.swing.JLabel;
import rex.utils.I18n;
/**
 * Represents an empty result table. Displays a JLabel in JPanel.
 * @author Igor Mekterovic
 * @version 0.3
 */
public class EmptyMdxResultTable extends JPanel{
   public EmptyMdxResultTable() {
/**
  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
  * All Rights Reserved
  * Copyright (C) 2006 Igor Mekterovic
  * All Rights Reserved
  */ 
       this.add(new JLabel(I18n.getString("label.mdxEmptyResult")));
     /* end of modification for I18n */

   }
}
