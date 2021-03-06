package rex.graphics;

import javax.swing.JPanel;
import javax.swing.JTextArea;
import java.awt.BorderLayout;
import java.util.LinkedList;
import java.util.Iterator;
import javax.swing.JScrollPane;
import rex.utils.S;
import java.awt.Graphics;
import java.awt.Font;

/**
 * <p>Title: WHEX</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003</p>
 * <p>Company: </p>
 * @author igor
 * @version 1.0
 */

public class MDXViewer extends JPanel{
   private static int MAX_QUERIES_COUNT = 10;
   JTextArea queriesArea;
   LinkedList queries;
   public MDXViewer() {
      queriesArea = new JTextArea(){
         {
            setOpaque(false);
         }
         public void paintComponent(Graphics g) {
            S.paintBackground(g, this);
            super.paintComponent(g);
         }
      };
      queriesArea.setFont(new Font("Monospaced", Font.BOLD, 12));
      this.setLayout(new BorderLayout());
      this.add(new JScrollPane(queriesArea), BorderLayout.CENTER);
      queries = new LinkedList();
   }
   public void addQuery(String q){
      if (queries.size() > MAX_QUERIES_COUNT){
         queries.removeLast();
      }
      queries.addFirst(q);
      refreshDisplay();
   }
   private void refreshDisplay(){
//      S.out("refreshing display");
      queriesArea.setText("");
      StringBuffer newText = new StringBuffer("");
      String separator = "\n----------------------------------------------------------------------------------------------------\n";
      Iterator it = queries.iterator();
      while(it.hasNext()){
         newText.append(separator);
         newText.append((String)it.next());
         newText.append(separator);
      }
      queriesArea.setText(newText.toString());
   }



}