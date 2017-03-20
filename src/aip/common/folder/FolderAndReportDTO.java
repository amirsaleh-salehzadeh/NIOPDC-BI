package aip.common.folder;

import java.util.ArrayList;

public class FolderAndReportDTO {
	
	private String uniqueId;
	private Integer id;
	private String caption;
	private Boolean isPublic;
	private Boolean isVisual;
	private Boolean isFolder;
	private String parentUniqueId;
	private String createDate;
	private ArrayList<FolderAndReportDTO> childrens = new ArrayList<FolderAndReportDTO>();
	private Boolean hasChild;
	
	public Boolean getHasChild() {
		return hasChild;
	}
	public void setHasChild(Boolean hasChild) {
		this.hasChild = hasChild;
	}
	public ArrayList<FolderAndReportDTO> getChildrens() {
		return childrens;
	}
	public void setChildrens(ArrayList<FolderAndReportDTO> childrens) {
		this.childrens = childrens;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUniqueId() {
		return uniqueId;
	}
	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}
	public String getCaption() {
		return caption;
	}
	public void setCaption(String caption) {
		this.caption = caption;
	}
	public Boolean getIsPublic() {
		return isPublic;
	}
	public void setIsPublic(Boolean isPublic) {
		this.isPublic = isPublic;
	}
	public Boolean getIsVisual() {
		return isVisual;
	}
	public void setIsVisual(Boolean isVisual) {
		this.isVisual = isVisual;
	}
	public Boolean getIsFolder() {
		return isFolder;
	}
	public void setIsFolder(Boolean isFolder) {
		this.isFolder = isFolder;
	}
	public String getParentUniqueId() {
		return parentUniqueId;
	}
	public void setParentUniqueId(String parentUniqueId) {
		this.parentUniqueId = parentUniqueId;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	
	
}
