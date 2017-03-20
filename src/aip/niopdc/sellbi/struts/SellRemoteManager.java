package aip.niopdc.sellbi.struts;

import aip.common.bireport.bimdx.MdxFunctionsInterface;
import aip.common.folder.FolderInterface;
import aip.common.report.ReportInterface;
import aip.niopdc.sellbi.orm.report.ReportDAO;
import aip.niopdc.sellbi.orm.bireport.MDXFunctionsDAO;
import aip.niopdc.sellbi.orm.folder.FolderDAO;



public class SellRemoteManager {
	 static ReportInterface _report;
	 static FolderInterface _folder;
	 static MdxFunctionsInterface _mdxFunctions;
	
	public static ReportInterface getReportInterface() {
		if (_report == null) {
			_report = new ReportDAO();
		}
		return  _report;
	}
	public static FolderInterface getFolderInterface() {
		if (_folder== null) {
			_folder = new FolderDAO();
		}
		return  _folder;
	}
	public static MdxFunctionsInterface getMDXFunctions() {
		if (_mdxFunctions == null) {
			_mdxFunctions = new MDXFunctionsDAO();
		}
		return  _mdxFunctions;
	}

}
