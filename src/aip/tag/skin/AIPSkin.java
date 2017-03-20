package aip.tag.skin;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Hashtable;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTagSupport;

import aip.util.AIPUtil;

public class AIPSkin extends BodyTagSupport {

	static String contentPattern = "[PLACEHOLDER]";
	static String idPattern = "[DROPDOWNID]";
	static String treeIdPattern = "PREFIX";
	static String titleTextPattern = "[TITLETEXT]";
	static String widthValuePattern = "[WIDTH]";
	static String alignmentPattern = "[ALIGN]";
	
	AIPSkinTypeInfo defaultAIPSkinTypeInfo;
	Hashtable<AIPSkinType, AIPSkinTypeInfo> htAIPSkinTypeInfos = new Hashtable<AIPSkinType, AIPSkinTypeInfo>(); 
	AIPSkinType typeEnum = AIPSkinType.BODY;

	String type;
	String styleId;
	String title;
	String width;
	String align;
	
	public AIPSkin(){
		super();
		initAIPSkinTypes();
	}

	public int doStartTag() throws JspException {
		int res = super.doStartTag();
		try {
			pageContext.getOut().write(getAIPSkinTypeInfo().contentStartTag);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return res;
	}

	public int doAfterBody() throws JspException {
		try {
			BodyContent bodyContent = getBodyContent();
			JspWriter out = bodyContent.getEnclosingWriter();
			bodyContent.writeOut(out);
			bodyContent.clearBody();
		} catch (Exception ex) {
			throw new JspException("error in AIPSkin: " + ex);
		}
		return super.doAfterBody();
	}

	public int doEndTag() throws JspException {
		int i = super.doEndTag();
		try {
			pageContext.getOut().write(getAIPSkinTypeInfo().contentEndTag);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return i;
	}

	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
		AIPSkinType def = AIPSkinType.BODY;
		try {
			def = AIPSkinType.valueOf(type.toUpperCase());
		} catch (IllegalArgumentException ex) {
			//ex.printStackTrace();
			System.out.println("AIPSkin.setType():"+type);
			def=AIPSkinType.ETC;
		}
		this.typeEnum = def;
	}

	AIPSkinTypeInfo getAIPSkinTypeInfo(){
		AIPSkinTypeInfo info;
		//System.out.println("AIPSkin.getAIPSkinTypeInfo():typeEnum="+typeEnum.name());
		if (htAIPSkinTypeInfos.containsKey(typeEnum)) {
			info= htAIPSkinTypeInfos.get(typeEnum);
			//System.out.println("AIPSkin.getAIPSkinTypeInfo():containsKey:file=" + info.aipskinfile);
		} else {
			//System.out.println("AIPSkin.getAIPSkinTypeInfo():type="+type);
			info = getNewAIPSkinTypeInfo(type);
			AIPUtil.printObject(info);
		}
		loadSkinFileContent(info);
		return info;
	}
	
	void initAIPSkinTypes(){
		defaultAIPSkinTypeInfo = new AIPSkinTypeInfo(AIPSkinType.BODY, "/aipconfig/skin/body/aipskin_body.html");
		htAIPSkinTypeInfos.put(AIPSkinType.BODY, defaultAIPSkinTypeInfo);
		htAIPSkinTypeInfos.put(AIPSkinType.DIALOGSAVE , new AIPSkinTypeInfo(AIPSkinType.DIALOGSAVE ,"/aipconfig/skin/dialogsave/aipskin_dialogsave.html"));
		htAIPSkinTypeInfos.put(AIPSkinType.DIALOGVIEW , new AIPSkinTypeInfo(AIPSkinType.DIALOGVIEW ,"/aipconfig/skin/dialogview/aipskin_dialogview.html"));
		htAIPSkinTypeInfos.put(AIPSkinType.DROPDOWN , new AIPSkinTypeInfo(AIPSkinType.DROPDOWN ,"/aipconfig/skin/dropdown/aipskin_dropdown.html"));
		htAIPSkinTypeInfos.put(AIPSkinType.HEAD , new AIPSkinTypeInfo(AIPSkinType.HEAD ,"/aipconfig/skin/head/aipskin_head.html"));
		htAIPSkinTypeInfos.put(AIPSkinType.ITEM , new AIPSkinTypeInfo(AIPSkinType.ITEM ,"/aipconfig/skin/item/aipskin_item.html"));
		htAIPSkinTypeInfos.put(AIPSkinType.LIST , new AIPSkinTypeInfo(AIPSkinType.LIST ,"/aipconfig/skin/list/aipskin_list.html"));
		htAIPSkinTypeInfos.put(AIPSkinType.TREE , new AIPSkinTypeInfo(AIPSkinType.TREE ,"/aipconfig/skin/tree/aipskin_tree.html"));
		htAIPSkinTypeInfos.put(AIPSkinType.BUTTONS , new AIPSkinTypeInfo(AIPSkinType.BUTTONS ,"/aipconfig/skin/buttons/aipskin_buttons.html"));
		htAIPSkinTypeInfos.put(AIPSkinType.SELECTEDITEMS , new AIPSkinTypeInfo(AIPSkinType.SELECTEDITEMS ,"/aipconfig/skin/selectedItems/aipskin_selectedItems.html"));
	}

	AIPSkinTypeInfo getNewAIPSkinTypeInfo(AIPSkinType type){
		AIPSkinTypeInfo info;
		switch (type) {
			case BODY : info=new AIPSkinTypeInfo(AIPSkinType.BODY,"/aipconfig/skin/body/aipskin_body.html");break;
			case DIALOGSAVE : info=new AIPSkinTypeInfo(AIPSkinType.DIALOGSAVE ,"/aipconfig/skin/dialogsave/aipskin_dialogsave.html");break;
			case DIALOGVIEW : info= new AIPSkinTypeInfo(AIPSkinType.DIALOGVIEW ,"/aipconfig/skin/dialogview/aipskin_dialogview.html");break;
			case DROPDOWN : info= new AIPSkinTypeInfo(AIPSkinType.DROPDOWN ,"/aipconfig/skin/dropdown/aipskin_dropdown.html");break;
			case HEAD : info= new AIPSkinTypeInfo(AIPSkinType.HEAD ,"/aipconfig/skin/head/aipskin_head.html");break;
			case ITEM : info= new AIPSkinTypeInfo(AIPSkinType.ITEM ,"/aipconfig/skin/item/aipskin_item.html");break;
			case LIST : info= new AIPSkinTypeInfo(AIPSkinType.LIST ,"/aipconfig/skin/list/aipskin_list.html");break;
			case TREE : info= new AIPSkinTypeInfo(AIPSkinType.TREE ,"/aipconfig/skin/tree/aipskin_tree.html");break;
			case BUTTONS : info= new AIPSkinTypeInfo(AIPSkinType.BUTTONS ,"/aipconfig/skin/buttons/aipskin_buttons.html");break;
			case SELECTEDITEMS : info= new AIPSkinTypeInfo(AIPSkinType.SELECTEDITEMS ,"/aipconfig/skin/selectedItems/aipskin_selectedItems.html");break;
			default: info = new AIPSkinTypeInfo(AIPSkinType.ETC,"/aipconfig/skin/"+type+"/aipskin_"+type+".html");
			break;
		}
		return info;
	}
	AIPSkinTypeInfo getNewAIPSkinTypeInfo(String type){
		AIPSkinTypeInfo info=new AIPSkinTypeInfo(AIPSkinType.ETC,"/aipconfig/skin/"+type+"/aipskin_"+type+".html");
		//System.out.println("AIPSkin.getNewAIPSkinTypeInfo():info.aipskinfile="+info.aipskinfile);
		return info;
	}
	void loadSkinFileContent(AIPSkinTypeInfo info) {
		if ((info.lastCheckTime + 65000) < System.currentTimeMillis() || info.type == AIPSkinType.DROPDOWN) {
			info.lastCheckTime = System.currentTimeMillis();
			try {
				String realPath = pageContext.getServletContext().getRealPath(info.aipskinfile);
				
				//System.out.println("AIPSkin.loadSkinFileContent():realpath="+realPath);
				
				File f = new File(realPath);
//				System.out.println("AIPSkin.loadSkinFileContent():file=" + f.getPath());
				if (f.lastModified() > info.lastSkinFileReaded || info.type == AIPSkinType.DROPDOWN) {
					FileInputStream fin = new FileInputStream(f);
					byte buf[] = new byte[fin.available()];
					fin.read(buf);
					fin.close();
					String sb = new String(buf);
					
					if (styleId != null)
						sb = AIPUtil.replace(sb, treeIdPattern , styleId);
					if (title != null) 
						sb = AIPUtil.replace(sb, titleTextPattern, title);
					
					if (info.type == AIPSkinType.DROPDOWN) {
						if (styleId != null)
							sb = AIPUtil.replace(sb, idPattern, styleId);
						if (title != null) 
							sb = AIPUtil.replace(sb, titleTextPattern, title);
					}
					
					if (align != null) {
						sb = AIPUtil.replace(sb, alignmentPattern, align);
					} else {
						sb = AIPUtil.replace(sb, alignmentPattern, "center");
					}
					
					if (width != null) {
						sb = AIPUtil.replace(sb, widthValuePattern, width);
					} else {
						sb = AIPUtil.replace(sb, widthValuePattern, "");
					}
					
					int pos = sb.indexOf(contentPattern);
					info.contentStartTag = sb.substring(0, pos);
					info.contentEndTag = sb.substring(pos + contentPattern.length());
					info.lastSkinFileReaded = f.lastModified();
					
					//System.out.println("AIPSkin.loadSkinFileContent():in................");
					//System.out.println( info.contentStartTag);
				}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}


	

	public String getStyleId() {
		return styleId;
	}

	public void setStyleId(String styleId) {
		this.styleId = styleId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getWidth() {
		return width;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public String getAlign() {
		return align;
	}

	public void setAlign(String align) {
		this.align = align;
	}
}

class AIPSkinTypeInfo{
	AIPSkinType type;
	String aipskinfile;
	long lastSkinFileReaded = 0;
	String contentStartTag = "";
	String contentEndTag = "";
	long lastCheckTime = 0;

	public AIPSkinTypeInfo() {
	}
	public AIPSkinTypeInfo(AIPSkinType type, String aipskinfile) {
		this(type, aipskinfile, 0, "", "");
	}
	public AIPSkinTypeInfo(AIPSkinType type, String aipskinfile, long lastSkinFileReaded, String contentStartTag, String contentEndTag) {
		this.type = type;
		this.aipskinfile = aipskinfile;
		this.lastSkinFileReaded = lastSkinFileReaded;
		this.contentStartTag = contentStartTag;
		this.contentEndTag = contentEndTag;
	}
}