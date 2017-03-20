package aip.common.folder;

import java.util.ArrayList;

import aip.common.AIPBaseDeleteParam;
import aip.common.AIPDefaultParam;
import aip.common.AIPException;


public interface FolderInterface {
	public  void deleteFolder(AIPBaseDeleteParam param);   
	public  FolderENT getForlderENT(AIPDefaultParam param );
	public  void makePublic(AIPDefaultParam param);
	public  void makePrivate(AIPDefaultParam param)throws AIPException;
	  /*
	   * make private : 
	   * h= (  select hierarchy from cmfolder where ID= ?)
	   * f[]= select * from cmfolder c where c.hierarchy like 'h%'
	   * for each f : 
	   *   update f set isPublic=false;
	   *   select * bireport set isPublic=false where  f_FolderID=f.ID;
	   * end of;  
	   */ 

	public  FolderENT saveFolder( FolderENT ent) throws AIPException;
	      
	public ArrayList<FolderDTO> getFolderTree(AIPDefaultParam param);
	public ArrayList<FolderAndReportDTO> getFolderAndReportTree(AIPDefaultParam param);
	
	public ArrayList<FolderAndReportDTO> getFolderAndReportTreeForSelectedNode(AIPDefaultParam param);
}
