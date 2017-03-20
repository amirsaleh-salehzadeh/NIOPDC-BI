package rex.xmla;

public abstract class XMLADiscoverProperties extends XMLAProperties{

   public abstract String getDataSourceInfo() ;
   public abstract void setDataSourceInfo(String s) ;

   public abstract String getCatalog() ;
   public abstract void setCatalog(String s) ;

   public abstract int getBeginRange();
   public abstract void setBeginRange(int br);

   public abstract int getEndRange();
   public abstract void setEndRange(int er);


   public abstract String getFormat() ;
   public abstract void setFormat(String s) ;

   public abstract String getContent() ;
   public abstract void setContent(String s) ;

}
