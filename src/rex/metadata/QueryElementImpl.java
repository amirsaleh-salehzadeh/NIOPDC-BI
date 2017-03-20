package rex.metadata;

public class QueryElementImpl implements QueryElement{
   private String queryMembersExpression
                , hierarchyUniqueName;
   public QueryElementImpl(String _queryMembersExpression
                         , String _hierarchyUniqueName) {
      queryMembersExpression = _queryMembersExpression;
      hierarchyUniqueName   = _hierarchyUniqueName;
   }
   public String getQueryMembersExpression(){
      return queryMembersExpression;
   }
   public String getHierarchyUniqueName(){
      return hierarchyUniqueName;
   }
}
