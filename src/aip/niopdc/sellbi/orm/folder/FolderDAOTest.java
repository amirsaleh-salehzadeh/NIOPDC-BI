package aip.niopdc.sellbi.orm.folder;

import aip.common.AIPBaseDeleteParam;
import aip.common.AIPDefaultParam;
import aip.common.AIPException;
import aip.common.AIPWebUser;
import aip.common.folder.FolderENT;
import aip.util.AIPUtil;

public class FolderDAOTest {
	FolderDAO dao = new FolderDAO();
	public static void main(String[] args) {
		FolderDAOTest t = new FolderDAOTest();
//		t.testDeleteFolder();
//		t.testSaveFolder();
//		t.testMakePrivate();
//		t.testMakePublic();
//		t.testGetFolderTree();
//		t.testGetFolderAndReportTree();
		t.testGetFolderAndReportTreeForSelectedNode();
		
	}

	
	public void testDeleteFolder() {
		AIPBaseDeleteParam param = new AIPBaseDeleteParam();
		param.setIds("10,11");
		dao.deleteFolder(param);
	}

	
	public void testGetForlderENT() {
	
	}


	public void testMakePrivate() {
		AIPDefaultParam param = new AIPDefaultParam();
		param.setId(12);
		try {
			dao.makePrivate(param);
		} catch (AIPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	public void testMakePublic() {
		AIPDefaultParam param = new AIPDefaultParam();
		param.setId(12);
		dao.makePublic(param);
	}


	public void testSaveFolder() {
		FolderENT ent = new FolderENT();
		ent.setCaption("گزارش من 14");
		ent.setUserName("amirsaleh");
		ent.setIsPublic(true);
		ent.setParentId(14);
		try {
			AIPUtil.printObject(dao.saveFolder(ent));
		} catch (AIPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void testGetFolderTree() {
		AIPDefaultParam param = new AIPDefaultParam();
		AIPWebUser webUser = new AIPWebUser();
		webUser.setRemoteUser("amirsaleh");
		param.setWebUser(webUser);
		AIPUtil.printObject(dao.getFolderTree(param));
		
	}

	public void testGetFolderAndReportTree() {
		AIPDefaultParam param = new AIPDefaultParam();
		AIPWebUser webUser = new AIPWebUser();
		//webUser.setRemoteUser("amirsaleh");
		param.setWebUser(webUser);
		AIPUtil.printObject(dao.getFolderAndReportTree(param));
		
	}
	
	public void testGetFolderAndReportTreeForSelectedNode(){
		AIPDefaultParam param = new AIPDefaultParam();
		AIPWebUser webUser = new AIPWebUser();
		//webUser.setRemoteUser("amirsaleh");
		param.setWebUser(webUser);
		AIPUtil.printObject(dao.getFolderAndReportTreeForSelectedNode(param));

	}


}
