package aip.common.folder;

import java.util.ArrayList;

public class FolderDTO {
  private Integer id;
  private String caption;
  private Integer parentId;
  private Boolean isPublic=false;
  private String userName;
  private String hierarchy;
  private ArrayList<FolderDTO> childrens = new ArrayList<FolderDTO>();
public ArrayList<FolderDTO> getChildrens() {
	return childrens;
}
public void setChildrens(ArrayList<FolderDTO> childrens) {
	this.childrens = childrens;
}
public Integer getId() {
	return id;
}
public void setId(Integer id) {
	this.id = id;
}
public String getCaption() {
	return caption;
}
public void setCaption(String caption) {
	this.caption = caption;
}
public Integer getParentId() {
	return parentId;
}
public void setParentId(Integer parentId) {
	this.parentId = parentId;
}
public Boolean getIsPublic() {
	return isPublic;
}
public void setIsPublic(Boolean isPublic) {
	this.isPublic = isPublic;
}
public String getUserName() {
	return userName;
}
public void setUserName(String userName) {
	this.userName = userName;
}
public String getHierarchy() {
	return hierarchy;
}
public void setHierarchy(String hierarchy) {
	this.hierarchy = hierarchy;
}
}
