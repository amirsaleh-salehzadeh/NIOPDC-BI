package aip.niopdc.sellbi.orm.bireport;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import aip.common.AIPBaseDeleteParam;
import aip.common.AIPDefaultParam;
import aip.common.AIPException;
import aip.common.bireport.bimdx.AIPBiMdxCriteria;
import aip.common.bireport.bimdx.AIPBiMdxDTO;
import aip.common.bireport.bimdx.AIPBiMdxInterface;
import aip.common.folder.FolderAndReportDTO;
import aip.common.folder.FolderDTO;
import aip.common.folder.FolderENT;
import aip.common.folder.FolderInterface;
import aip.niopdc.sellbi.orm.BaseHibernateDAO;
import aip.util.AIPUtil;
import aip.util.NVL;

public class AIPBiMdxDAO extends BaseHibernateDAO implements AIPBiMdxInterface{

	public AIPBiMdxDTO getAIPBiMdxDTO(int id) {
		AIPBiMdxDTO dto = new AIPBiMdxDTO();
		
		dto=getAIPBiMdxDTO4Test();

		return dto;
	}
	private  AIPBiMdxDTO getAIPBiMdxDTO4Test(){
		AIPBiMdxDTO dto = new AIPBiMdxDTO();
		
		dto.setReportName("تست");
		
		dto.setMdxQueryNC("SELECT {[Measures].[مبلغ کل],[Measures].[مبلغ پایه]} on columns,"
	         +" {NONEMPTY( DESCENDANTS( [زمان].[سال-فصل-ماه2],"
	               + "?" +" ,SELF))}"
	               +" on rows"
	               +" from [حواله] where "+ "?");

		AIPBiMdxCriteria criteria1 = new AIPBiMdxCriteria();
		criteria1.setName("زمان");
		criteria1.setCriteriaMdx("{سال=1,ماه=2,روز=3}");
		criteria1.setDefaultValue("1");
		criteria1.setDefaultCaption("سال");
		dto.getCriterias().add(criteria1);
		
		AIPBiMdxCriteria criteria2 = new AIPBiMdxCriteria();
		criteria2.setName("منطقه/ناحیه");
		criteria2.setCriteriaMdx("[مشتری].[منطقه- ناحیه]");
		criteria2.setDefaultValue("[مشتری].[منطقه- ناحیه].[همه]");
		criteria2.setDefaultCaption("همه");
		dto.getCriterias().add(criteria2);
		
		dto.generateMdxQueryFromNCAndCriterias();
		
		return dto;
	}

	
}
