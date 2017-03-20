package aip.niopdc.sellbi.orm;

import aip.common.AIPException;
import aip.common.bireport.bimdx.AIPBiMdxInterface;
import aip.common.dashboard.DashboardDAOInterface;
import aip.common.folder.FolderInterface;
import aip.common.report.ReportInterface;
import aip.common.security.group.GroupDAOInterface;
import aip.common.security.user.UserDAOInterface;
//import aip.niopdc.sellbi.orm.bireport.AIPBiMdxDAO;
import aip.niopdc.sellbi.orm.dashboard.DashboardDAO;
import aip.niopdc.sellbi.orm.folder.FolderDAO;
import aip.niopdc.sellbi.orm.report.ReportDAO;
import aip.niopdc.sellbi.orm.security.group.GroupDAO;
import aip.niopdc.sellbi.orm.security.user.UserDAO;
import aip.xmla.AIPXmla;


public class SellDAOManager {
	
	static GroupDAOInterface _groupDAOInterface ;
	static UserDAOInterface _userDAOInterface ;
	static FolderInterface _folder ;
	static AIPBiMdxInterface _bimdx ;
	static ReportInterface _bireport ;
	static DashboardDAOInterface _dashboard;
	
	public static GroupDAOInterface getGroupDAOInterface(){
		if (_groupDAOInterface == null) {
			_groupDAOInterface = new GroupDAO();
		}
		return _groupDAOInterface; 
	}
	public static UserDAOInterface getUserDAOInterface(){
		if (_userDAOInterface == null) {
			_userDAOInterface  = new UserDAO();
		}
		return _userDAOInterface ; 
	}
	
	public static FolderInterface getFolder(){
		if (_folder == null) {
			_folder = new FolderDAO();
		}
		return _folder; 
	}
	
//	public static AIPBiMdxInterface getAIPBiMdx(){
//		if (_bimdx == null) {
//			_bimdx  = new AIPBiMdxDAO();
//		}
//		return _bimdx; 
//	}
	
	public static ReportInterface getBIReport(){
		if (_bireport == null) {
			_bireport  = new ReportDAO();
		}
		return _bireport; 
	}
	
	public static DashboardDAOInterface getDashboard(){
		if (_dashboard == null) {
			_dashboard  = new DashboardDAO();
		}
		return _dashboard; 
	}
	
	public static AIPXmla getXmla(String user)throws AIPException{
		return AIPXmla.getXmla( getUserDAOInterface().getXMLAUser(user) , getUserDAOInterface().getXMLAPassword(user) );
	}
	
	
}