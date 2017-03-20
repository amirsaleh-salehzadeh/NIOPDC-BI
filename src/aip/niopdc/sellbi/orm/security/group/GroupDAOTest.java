package aip.niopdc.sellbi.orm.security.group;

import java.util.List;

import junit.framework.TestCase;

import org.hibernate.Query;

import aip.common.AIPDefaultParam;
import aip.common.AIPException;
import aip.common.AIPWebUser;
import aip.common.security.group.GroupComboLST;
import aip.common.security.group.GroupDTO;
import aip.common.security.group.GroupENT;
import aip.common.security.group.GroupLSTParam;
import aip.util.AIPUtil;
import aip.util.NVL;

public class GroupDAOTest extends TestCase {
	
	GroupDAO dao = new GroupDAO();
	public static void main(String[] args) {
		System.out.println("Staaaaaaaaaaaaaaaaaart");
		GroupDAOTest t = new GroupDAOTest();
		//t.testGetGroupComboLST();
		//t.testGetGroupDTO();
		//t.testSaveGroup();
		t.testGetGroupLST();
		//t.test();
		

	}

	private void test(){
		GroupLSTParam param = new GroupLSTParam();
		param.setReqPage(1);
		param.setFilter(null);
		param.setSortedByField("g.ID asc");
		param.setPageSize(10);

		Query q = dao.getSession().getNamedQuery("spSECGetGroups");
		q.setString(0, NVL.getStringNull(param.getFilter()));
		q.setInteger(1, param.getReqPage()* param.getPageSize() );
		q.setInteger(2, param.getPageSize());
		q.setString(3, param.getSortedByField());
		
		System.out.println(q.toString());
		List groupDTOs = q.list();
		System.out.println(groupDTOs.size());

	}
	public void testDeleteUserGroup() {
		fail("Not yet implemented");
	}

	public void testGetGroupENT() {
		fail("Not yet implemented");
	}

	public void testGetGroupComboLST() {
		GroupComboLST groupLST = new GroupComboLST();
		AIPWebUser user = new AIPWebUser();
		try {
			groupLST = dao.getGroupComboLST(user);
		} catch (AIPException e) {
				e.printStackTrace();
			}
		System.out.println("lilililililililililiiiiiiiiiiiiSt");
		AIPUtil.printObject(groupLST.getRoleDTOs());
		System.out.println("Ennnnnnnnnnnnnnnnnnd");
//		System.out.println("GroupDAO.main()"+ groupDAO.getGroupRoles(1) );

	}

	public void testGetGroupDTO() {
		AIPDefaultParam param = new AIPDefaultParam();
		param.setId(1);
		try {
			AIPUtil.printObject(dao.getGroupDTO(param));
		} catch (AIPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void testGetGroupLST() {
		GroupLSTParam param = new GroupLSTParam();
		param.setReqPage(1);
		//param.setFilter("a");
		param.setSortedByField("g.ID asc");
		param.setPageSize(10);
		try {
			AIPUtil.printObject(dao.getGroupLST(param));
		} catch (AIPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void testSaveGroup() {
		GroupDTO dto = new GroupDTO();
		GroupENT ent = new GroupENT();
		ent.setId(7);
		ent.setGroupName("Test!!!!!!!!!!!");
		dto.setEnt(ent);
		dto.setSelectedRoleIds("1,2,6");
		try {
			AIPUtil.printObject(dao.saveGroup(dto));
		} catch (AIPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void testMain() {
		fail("Not yet implemented");
	}

}
