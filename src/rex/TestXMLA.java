package rex;

import rex.graphics.datasourcetree.elements.CatalogElement;
import rex.graphics.datasourcetree.elements.DataSourceTreeElement;
import rex.graphics.dimensiontree.elements.DimensionTreeElement;
import rex.metadata.ServerMetadata;
import rex.xmla.XMLADiscoverProperties;
import rex.xmla.XMLADiscoverRestrictions;
import rex.xmla.XMLAObjectsFactory;

public class TestXMLA {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	       int i;
	       ServerMetadata smd = new ServerMetadata("http://192.168.0.52:80/olap/msmdpump.dll");
	       DataSourceTreeElement ds[] = smd.discoverDataSources();
	       System.out.println("ds.size="+ds.length);
	       if (ds != null){
	          //maybeAddDataSource(URL);
	          for (i = 0; ds != null && i < ds.length; i++) {
	             //TreeElement dataSource = new TreeElement(ds[i], root);
	             //root.addChild(dataSource);
	             //buildTreeRecursively(dataSource);
	        	  System.out.println("TestXMLA.main():ds[i]"+ds[i].getDataSourceInfo());
	        	  
//	        	  for(int j=0;j<ds[i].getChildren().length;j++){
//	        		  CatalogElement ce = (CatalogElement)ds[i].getChildren()[j];
//	        		  System.out.println(""+ ce.getServerMetaData().);
//	        	  }
					XMLADiscoverRestrictions restrictions = XMLAObjectsFactory.newXMLADiscoverRestrictions();
					XMLADiscoverProperties   properties   = XMLAObjectsFactory.newXMLADiscoverProperties();
				    //properties.setDataSourceInfo((String)cmbDS.getSelectedItem());
/*					CatalogElement ces[]=(CatalogElement[])ds[i].getServerMetaData().getCatalogList(restrictions, properties);
					System.out.println(".......................getCatalogList........................");
					for (CatalogElement ce : ces) {
						System.out.println("\t"+ce.toString());
					}
					
					
					properties.setDataSourceInfo("TAHERI");
					properties.setCatalog("NIOPDC_MRS_Ver1");		
					DataSourceTreeElement ces2[]=ds[i].getServerMetaData().getCubeList(restrictions, properties);
					System.out.println(".......................getCubeList........................");
					for (DataSourceTreeElement ce2 : ces2) {
						System.out.println("\t"+ce2.toString());
					}
					
*/				
					properties.setDataSourceInfo("TAHERI");
					properties.setCatalog("NIOPDC_MRS_Ver1");		
					//restrictions.setCubeName("حواله");
					DimensionTreeElement ces3[]=ds[i].getServerMetaData().getDimensionList(restrictions, properties);
					System.out.println(".......................getDimensionList........................");
					for (DimensionTreeElement ce3 : ces3) {
						System.out.println("\t"+ce3.getCaption() );
					}
					String d="[زمان]";
					String h = "[زمان].[سال-ماه-روز]";
					String l = "[زمان].[سال-ماه-روز].[Year No]";
					
//					d="[زمان]";
//					h = "[زمان].[سال-فصل-ماه]";
//					l = "[زمان].[سال-فصل-ماه].[فصل]";

					d="[زمان]";
					h = "[زمان].[سال-فصل-ماه]";
					l = "[زمان].[سال-فصل-ماه].[ماه]";

					properties.setDataSourceInfo("TAHERI");
					properties.setCatalog("NIOPDC_MRS_Ver2");
					restrictions.setDimensionUniqueName(d);
					restrictions.setHierarchyUniqueName(h);
					restrictions.setLevelUniqueName(l);
					DimensionTreeElement ces4[] = ds[i].getServerMetaData().getDimensionTreeMembersList(restrictions, properties);
					System.out.println(".......................getDimensionTreeMembersList........................ces4.size="+ces4.length);
//					for(int n=0;n<10;n++){
//						DimensionTreeElement ce4 = ces4[n];
//						System.out.println("\t"+ce4.getCaption() );
//					}
				for (DimensionTreeElement ce4 : ces4 ) {
					String n=ce4.getUniqueName();
					System.out.println(n);
					System.out.println("\t"+ce4.getCaption() );
					int lastIndex = n.lastIndexOf("]");
					int plast = n.lastIndexOf("[");
					String num = n.substring(plast+1,lastIndex);
					System.out.println(num);
				}
				
	        	  
	          }
	          //svm.add(smd);
	          //fireTreeStructureChanged(root);
	       }	}

}
