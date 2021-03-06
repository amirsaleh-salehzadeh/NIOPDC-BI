package rex.xmla;

public abstract class XMLADiscoverRestrictions extends  XMLARestrictions{
   public static int
     MD_TREEOP_CHILDREN    = 1
   , MD_TREEOP_SIBLINGS    = 2
   , MD_TREEOP_PARENT      = 4
   , MD_TREEOP_SELF        = 8
   , MD_TREEOP_DESCENDANTS = 16
   , MD_TREEOP_ANCESTORS   = 32;

   public abstract String getCatalog();
   public abstract void setCatalog(String s);

   public abstract String getCubeName();
   public abstract void setCubeName(String s);

   public abstract String getDimensionUniqueName();
   public abstract void setDimensionUniqueName(String s);

   public abstract String getHierarchyUniqueName();
   public abstract void setHierarchyUniqueName(String s);

   public abstract void setLevelUniqueName(String s);
   public abstract String getLevelUniqueName();

   public abstract void setMemberUniqueName(String s);
   public abstract String getMemberUniqueName();

   public abstract void setTreeOp(int _treeOp);
   public abstract String getTreeOp();

}
