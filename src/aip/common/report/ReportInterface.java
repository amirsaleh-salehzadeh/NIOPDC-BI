package aip.common.report;

import aip.common.AIPBaseDeleteParam;
import aip.common.AIPDefaultParam;
import aip.common.AIPException;
 
public interface ReportInterface {
   public  ReportLST getVisualReports(ReportLSTParam param );
   public  ReportLST getMDXReports(ReportLSTParam param );
   public  ReportLST getAllReports(ReportLSTParam param );
   public  ReportENT getReportENT(AIPDefaultParam param);
   public  ReportENT saveReport(ReportForSaveParam param) throws AIPException;
   public  void deleteReport(AIPBaseDeleteParam param);   
   
   public  void makePublic(AIPDefaultParam param);
   public  void makePrivate(AIPDefaultParam param);
   public String createVisualQuery(String[] rows,String[] columns,String[] filters,String[] measures);
   public ReportENT saveReport(ReportENT ent) throws AIPException;
   
}
