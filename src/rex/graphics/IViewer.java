package rex.graphics;

import rex.metadata.resultelements.Member;
import rex.metadata.QueryElement;
import rex.graphics.filtertree.*;

public interface IViewer {

   public void refreshQuery();

   public FilterTreeModel addMemberToFilter(Member filterMember);

   public void dropFilterTree(FilterTree filterToDrop);

   public void enableTreeElements(QueryElement elementToEnable);

   public void refreshFilterDisplay();

}
