package aip.niopdc.sellbi.orm.security.group;

import java.util.ArrayList;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import aip.common.AIPBaseDeleteParam;
import aip.common.AIPDefaultParam;
import aip.common.AIPException;
import aip.common.AIPWebUser;
import aip.common.security.group.GroupComboLST;
import aip.common.security.group.GroupDAOInterface;
import aip.common.security.group.GroupDTO;
import aip.common.security.group.GroupENT;
import aip.common.security.group.GroupLST;
import aip.common.security.group.GroupLSTParam;
import aip.common.security.group.GroupRoleENT;
import aip.common.security.group.RoleDTO;
import aip.niopdc.sellbi.orm.BaseHibernateDAO;
import aip.niopdc.sellbi.orm.CommonSecurityDAO;
import aip.util.AIPUtil;
import aip.util.NVL;
import aip.util.SyncIds;
import aip.util.os.AIPOSException;
import aip.util.os.AIPOSManager;

/**
 * A data access object (DAO) providing persistence and search support for
 * Scgroup entities. Transaction control of the save(), update() and delete()
 * operations can directly support Spring container-managed transactions or they
 * can be augmented to handle user-managed Spring transactions. Each of these
 * methods provides additional information for how to configure it for the
 * desired type of transaction control.
 * 
 * @see aip.niopdc.sellbi.orm.security.group.Scgroup
 * @author MyEclipse Persistence Tools
 */

public class GroupDAO extends BaseHibernateDAO implements GroupDAOInterface{
	public void deleteUserGroup(AIPBaseDeleteParam param) throws AIPException {
		Session session = getSession();
		Transaction tx = null;
		
		
		try{
			 tx=session.beginTransaction();
			if (param.getId() > 0){
				if(!"".equalsIgnoreCase(getGroupRoles(param.getId())))
                	throw getAIPException("عملیات حذف نا موفق. برای گروه مورد نظر سطح دسترسی ثبت شده است ", null);   
				if(!"".equalsIgnoreCase(getGroupDashboards(param.getId())))
					throw getAIPException("عملیات حذف نا موفق. برای گروه مورد نظر داشبورد ثبت شده است ", null);  
				session.delete( session.get(GroupENT.class,new Integer( param.getId()) ));
			}
	    	if(param.getIds() != null){
	    		String[] ids = AIPUtil.splitSelectedIds(param.getIds(), ",");
	    		for (int i = 0; i < ids.length; i++) {
	    			if( NVL.getInt(ids[i]) >0){
	    				if(!"".equalsIgnoreCase(getGroupRoles(NVL.getInteger(ids[i]))))
	                    	throw getAIPException("عملیات حذف نا موفق. برای گروه مورد نظر سطح دسترسی ثبت شده است ", null);   
	    				if(getGroupDashboards(NVL.getInteger(ids[i]))!=null)
	    					throw getAIPException("عملیات حذف نا موفق. برای گروه مورد نظر داشبورد ثبت شده است ", null);
	    					try {
								AIPOSManager.getOS().delWinGroup( getGroupDTO ( new AIPDefaultParam ( new AIPWebUser() , NVL.getInt(ids[i]) ) ).getEnt().getWinGroup() ) ;
							} catch (AIPOSException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
	    				session.delete( session.get(GroupENT.class,new Integer( ids[i]) ));
	    			}
				}
	    	}
	    	if(param.getCriteria()!=null){
	    		super.deleteByCondition(GroupENT.class,param.getCriteria());
	    	} 
	    	tx.commit();
	    	session.close();
		}catch (HibernateException e) {
			if(tx!=null && tx.isActive() ){
				tx.rollback();
			}
			e.printStackTrace();
			throw new AIPException("امکان حذف گروه وجود ندارد، برای گروه کاربر موجود می باشد",e);
		}
	}
	public GroupENT getGroupENT(Integer id){
		return (GroupENT) super.get(GroupENT.class, id);
	}

	public GroupComboLST getGroupComboLST(AIPWebUser user) throws AIPException {
		GroupComboLST comboLST = new GroupComboLST();
		Query q = getSession().createQuery("from RoleDTO");
		ArrayList<RoleDTO> roleDTOs = (ArrayList<RoleDTO>)q.list();
		comboLST.setRoleDTOs(roleDTOs);
		return comboLST;
	}

	public GroupDTO getGroupDTO(AIPDefaultParam param) throws AIPException {
		GroupDTO groupDTO = new GroupDTO();
		if (param.getId() > 0)
		{
			groupDTO = (GroupDTO)super.get(GroupDTO.class, param.getId());	
			groupDTO.setSelectedRoleIds(getGroupRoles(param.getId()));
		}else {
			groupDTO.setEnt(getGroupENT(param.getId()));
		}
		return groupDTO;
	}
	 
	private String getGroupRoles(Integer groupId){
		Query q = getSession().getNamedQuery("sqlGetGroupRoles");
		q.setInteger(0, groupId);
		return NVL.getString(q.uniqueResult());
	}
	private String getGroupDashboards(Integer groupId){
		Query q = getSession().getNamedQuery("sqlGetGroupDashboards");
		q.setInteger(0, groupId);
		return NVL.getString(q.uniqueResult());
	}
	
	public GroupLST getGroupLST(GroupLSTParam param) throws AIPException {
		GroupLST groupLST = new GroupLST();
		Query q = null;
		try{
				q = getSession().getNamedQuery("spSECGetGroupsCount")
				.setString(0, param.getFilter());
				int totalItems = NVL.getInt( q.uniqueResult() );
				groupLST.setProperties(totalItems,param.getReqPage(),param.getPageSize());
				
				//int first = (groupLST.getCurrentPage()-1)*param.getPageSize();
				q = getSession().getNamedQuery("spSECGetGroups")
				.setString(0, NVL.getStringNull(param.getFilter()))
				.setInteger(1, groupLST.getFirst())
				.setInteger(2, param.getPageSize())
				.setString(3, param.getSortedByField());
				
				ArrayList<GroupDTO> groupDTOs = (ArrayList<GroupDTO>)q.list();
				groupLST.setDtos(groupDTOs);
				
		}catch (HibernateException e) {
			e.printStackTrace();
		}
		return groupLST;
	}

	private String getSsasRoleUniqueNames(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}
	public GroupDTO saveGroup(GroupDTO dto) throws AIPException {
		Session session = getSession();
		Transaction tx = null;
		try{
			tx = session.beginTransaction();

			GroupENT ent = (GroupENT)saveNoTran(dto.getEnt(),session);
			dto.setEnt(ent);
	
			Query q = getSession().getNamedQuery("sqlGetRoleIds");
			q.setInteger(0, ent.getId());
			ArrayList<Integer> oldIds = (ArrayList<Integer>)q.list(); 
	
			SyncIds sy = AIPUtil.getSyncIds(dto.getSelectedRoleIds(), AIPUtil.convertToString(oldIds));
	
			dto.setSelectedRoleIds(AIPUtil.convertToString(sy.getNewIds()));
			
			for (int i = 0; i < sy.getNewIds().size(); i++) {
				GroupRoleENT groupRoleENT = new GroupRoleENT();
				groupRoleENT.setGroupId(dto.getEnt().getId());
				groupRoleENT.setRoleId(NVL.getInteger(sy.getNewIds().get(i)));
				groupRoleENT = (GroupRoleENT) super.saveNoTran(groupRoleENT,session);
			}
			
			for (int i = 0; i < sy.getDelIds().size(); i++){
				int groupId = NVL.getInt(sy.getDelIds().get(i));
				if (groupId > 0) {
					session.createQuery("delete from GroupRoleENT where groupId=? and roleId=?")
							.setInteger(0, ent.getId())
							.setInteger(1, NVL.getInt(sy.getDelIds().get(i))).executeUpdate();
				}
			}
			tx.commit();
		}catch(HibernateException ex){
			if(tx.isActive() && tx!=null)
				tx.rollback();
			session.close();
			throw getAIPException(AIPEX_SAVE , ex);
		}
		try {
			CommonSecurityDAO cs = new CommonSecurityDAO();
			cs.recalcGroupUsersAllRoles(dto.getEnt().getId());
		} catch (AIPException e) {
			e.printStackTrace();
		}

		
		return dto;
	}
  private  void syncGroupRoleIds(Integer groupId,String UserRoleIds){
	  //List<Integer>
  }
	 	

}