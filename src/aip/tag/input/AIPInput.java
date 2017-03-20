package aip.tag.input;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTagSupport;

import aip.law.common.common.ConstantParam;
import aip.law.struts.LawRemoteManager;
import aip.law.struts.form.common.AipbaseEditField;
import aip.law.struts.form.common.AipbaseViewField;
import aip.tag.AIPBaseTag;
import aip.tag.AIPPaggingLSTInterface;
import aip.util.AIPUtil;
import aip.util.NVL;

public class AIPInput extends AIPBaseTag {

	/*
	 * Attributes
	 */
	String field;
	String scope;
	String colspan = "1";

	/*
	 * attribute placeholders
	 */
	String refNoPlaceHolder = "[REFNO_PLACEHOLDER]";
	String fieldNamePlaceHolder = "[FIELDNAME_PLACEHOLDER]";
	String refIdPlaceHolder = "[REFID_PLACEHOLDER]";
	String extraParamsPlaceHolder = "[EXTRAPARAMS_PLACEHOLDER]";

	/*
	 * attribute content
	 */
	String jqueryContent = "	<script type=\"text/javascript\" src=\"aipconfig/jquery/jquery.js\"></script>"
			+ "<script type=\"text/javascript\" src=\"aipconfig/jquery/ui/ui.core.packed.js\"></script>"
			+ "<script type=\"text/javascript\" src=\"aipconfig/jquery/ui/ui.draggable.packed.js\"></script>";

	public AIPInput() {
		super();
		styleId = "";
	}

	public int doStartTag() throws JspException {
		int res = super.doStartTag();
		try {
			System.out.println("AIPInput.doStartTag():styleId=" + styleId);

			// String currentFolder = baseFolder+"/input/";
			// loadContentFile(currentFolder+"aipinput.html");

			pageContext.getOut().write(getContentStartTag());

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return res;
	}

	public String getContentStartTag() {
		System.out.println("AIPInput.getContentStartTag()#############################################");

		AipbaseViewField field = (AipbaseViewField) pageContext
				.getAttribute(this.field);

		AIPUtil.printObject(field);

		if (field != null) {
			try {
				switch (field.getInputType()) {
				case AipbaseViewField.INPUT_TEXT:
					createText(field, pageContext.getOut(), colspan);
					break;
				case AipbaseViewField.INPUT_HIDDEN:
					createHidden(field, pageContext.getOut(), colspan);
					break;
				case AipbaseViewField.INPUT_DISABLED:
					createDisabled(field, pageContext.getOut(), colspan);
					break;
				case AipbaseViewField.INPUT_PASSWORD:
					createPassword(field, pageContext.getOut(), colspan);
					break;
				case AipbaseViewField.INPUT_DATE:
					createDate(field, pageContext.getOut(), colspan);
					break;
				case AipbaseViewField.INPUT_SELECT:
					createSelect(field, pageContext.getOut(), colspan);
					break;
				case AipbaseViewField.INPUT_RADIO:
					createRadio(field, pageContext.getOut(), colspan);
					break;
				case AipbaseViewField.INPUT_CHECKBOX:
					createCheckbox(field, pageContext.getOut(), colspan);
					break;
				case AipbaseViewField.INPUT_BUTTON:
					createButton(field, pageContext.getOut(), colspan);
					break;
				case AipbaseViewField.INPUT_SUBMIT:
					createSubmit(field, pageContext.getOut(), colspan);
					break;
				case AipbaseViewField.INPUT_HYPERLINK:
					createHyperlink(field, pageContext.getOut(), colspan);
					break;
				case AipbaseViewField.INPUT_TEXT_IMAGE:
					createTextImage(field, pageContext.getOut(), colspan);
					break;
				case AipbaseViewField.INPUT_TEXT_IMG:
					createTextImg(field, pageContext.getOut(), colspan);
					break;
				case AipbaseViewField.INPUT_TEXTAREA:
					createTextArea(field, pageContext.getOut(), colspan);
					break;
				case AipbaseViewField.INPUT_DIV:
					createDiv(field, pageContext.getOut(), colspan);
					break;
				case AipbaseViewField.INPUT_LABEL:
					createLabel(field, pageContext.getOut(), colspan);
					break;
				case AipbaseViewField.INPUT_HTML:
					createHtml(field, pageContext.getOut(), colspan);
					break;

				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			System.out
					.println("AIPInput.getContentStartTag():::::::input field '"
							+ field + "' is null!!!!!!!!!!");
		}

		return "";
	}

	/*
	 * contentEndTag
	 */
	public String getContentEndTag() {
		return "";
	}

	public void createText(AipbaseViewField field, JspWriter out,
			String colspan) throws IOException {
		
		out.append("<span id=\"headerText\">");
		out.append(field.getKey());
		out.append("</span>");
		out.append("</td><td colspan='");
		out.append(colspan);
		out.append("'>");

		String widthStyle = "width:100%";
		if (field.getFieldExtension() != null
				&& !"".equalsIgnoreCase(field.getFieldExtension())) {
			widthStyle = "width:80%";
		}

		out.append("<input type='text' style=\"");
		out.append(widthStyle);
		out.append("\" name='");
		out.append(field.getProperty());
		out.append("' value='");
		String value = NVL.getStringContext(pageContext.getRequest()
				.getAttribute(field.getName()), field.getProperty());
		out.append(value);
		out.append("'");
		if (field.getIsReadonly()) {
			out.append(" readonly='readonly'");
		}
		if (field.getIsDisabled()) {
			out.append(" disabled='disabled'");
		}
		out.append(" />");
		if (field.getFieldExtension() != null
				&& !"".equalsIgnoreCase(field.getFieldExtension())) {
			out.append("<span id='headerText'>");
			out.append(field.getFieldExtension());
			out.append("</span>");
		}
		//out.flush();
	}

	public void createHidden(AipbaseViewField field, JspWriter out,String colspan) throws IOException {
		out.append("<input type='hidden' name='");
		out.append(field.getProperty());
		out.append("' value='");
		String value = NVL.getStringContext(pageContext.getRequest()
				.getAttribute(field.getName()), field.getProperty());
		out.append(value);
		out.append("'");
		out.append(" />");
		//out.flush();
	}
	
	public void createDisabled(AipbaseViewField field, JspWriter out,String colspan) throws IOException {
		out.append("<span id=\"headerText\">");
		out.append(field.getKey());
		out.append("</span>");
		out.append("</td><td colspan='");
		out.append(colspan);
		out.append("'><span id=\"generalText\">");
		String value = NVL.getStringContext(pageContext.getRequest()
				.getAttribute(field.getName()), field.getProperty());
		out.append(value);
		out.append("</span>");
		//out.flush();
	}
	
	public void createPassword(AipbaseViewField field, JspWriter out,String colspan) throws IOException {
		out.append("<span id=\"headerText\">");
		out.append(field.getKey());
		out.append("</span>");
		out.append("</td><td colspan='");
		out.append(colspan);
		out.append("'>");
		out.append("<input type='password' name='");
		out.append(field.getProperty());
		out.append("' value='");
		String value = NVL.getStringContext(pageContext.getRequest()
				.getAttribute(field.getName()), field.getProperty());
		out.append(value);
		out.append("'");
		out.append(" />");
		//out.flush();
	}
	
	public void createDate(AipbaseViewField field, JspWriter out,String colspan) throws IOException {
		out.append("<span id=\"headerText\">");
		out.append(field.getKey());
		out.append("</span>");
		out.append("</td><td colspan='");
		out.append(colspan);
		out.append("'>");
		out.append("<input type='text' name='");
		out.append(field.getProperty());
		out.append("' value='");
		String value = NVL.getStringContext(pageContext.getRequest()
				.getAttribute(field.getName()), field.getProperty());
		out.append(value);
		out.append("' size='10'");
		out.append(" />");
		out.append("<img class='LABEL' alt='calendar' src='images/calendar.gif' onclick=\"displayDatePicker('");
		out.append(field.getProperty());
		out.append("',this);\"");
		out.append("/>");
		//out.flush();
	
	}

	public void createSelect(AipbaseViewField field, JspWriter out,String colspan) throws IOException {
		System.out.println("AIPInput.createSelect()##############################################");
		
		out.append("<span id=\"headerText\">");
		out.append(field.getKey());
		out.append("</span>");
		out.append("</td><td colspan='");
		out.append(colspan);
		out.append("'>");
		
		String onChange = "";
		if (field.getOnChange() != null)
			onChange = field.getOnChange();

		out.append("<select name='");
		out.append(field.getProperty());
		out.append("' onchange=\""+onChange+"\">");
		
		String comboValue = NVL.getStringContext(pageContext.getRequest().getAttribute(field.getName()), field.getProperty());
		
		List options;
		try {
			options = (List)AIPUtil.invokeMulti(pageContext.getRequest().getAttribute(field.getOptionsName()), field.getOptionsProperty());
		for(int i=0;i<options.size();i++){
			Object option=options.get(i);
			
			//AIPUtil.printObject(option);
			
			String optionReserved = "";
			if (field.getOptionsReserved() != null && !"".equalsIgnoreCase(field.getOptionsReserved())) {
				Object optionReservedTmp = AIPUtil.invokeMulti(option,field.getOptionsReserved());
				if (optionReservedTmp != null)optionReserved = (String) optionReservedTmp;
			}
			String value = NVL.getString( AIPUtil.invokeMulti(option, field.getOptionsValue()) );
			String optionLabel = NVL.getString( AIPUtil.invokeMulti(option, field.getOptionsLabel()) );
			out.append("<option value='");
			out.append(value);
			out.append("' label='");
			out.append(optionReserved);
			out.append("'");
			if(value.equalsIgnoreCase(comboValue)){
				out.append("selected='selected'");
			}else{
				//out.append("");
			}
			out.append(">");
			out.append(optionLabel);
			out.append("</option>");
		}
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		out.append("</select>");
		//out.flush();
	}
	
	public void createRadio(AipbaseViewField field, JspWriter out,String colspan) throws IOException {
		System.out.println("AIPInput.createSelect()##############################################");
		
		out.append("<span id=\"headerText\">");
		out.append(field.getKey());
		out.append("</span>");
		out.append("</td><td colspan='");
		out.append(colspan);
		out.append("'>");
		
		String radioValue = NVL.getStringContext(pageContext.getRequest()
				.getAttribute(field.getName()), field.getProperty());
		
		List options;
		try {
			options = (List)AIPUtil.invokeMulti(pageContext.getRequest().getAttribute(field.getOptionsName()), field.getOptionsProperty());
		for(int i=0;i<options.size();i++){
			Object option=options.get(i);
			
			String value = NVL.getString( AIPUtil.invokeMulti(option, field.getOptionsValue()) );
			String optionLabel = NVL.getString( AIPUtil.invokeMulti(option, field.getOptionsLabel()) );
			out.append("<input type='radio' name='");
			out.append(field.getProperty());
			out.append("' value='");
			out.append(value);
			out.append("'");
			if(field.getIsDisabled()){
				out.append("disabled='disabled'");
			}
			if(value.equalsIgnoreCase(radioValue)){
				out.append("checked='checked'");
			}
			out.append("/>");
			out.append("<span id=\"generalText\">");
			out.append(optionLabel);
			out.append("</span>");
			
		}
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//out.flush();
	}
	
	public void createCheckbox(AipbaseViewField field, JspWriter out,
			String colspan) throws IOException {
		out.append("<span id=\"headerText\">");
		out.append(field.getKey());
		out.append("</span>");
		out.append("</td><td colspan='");
		out.append(colspan);
		out.append("'>");

		out.append("<input type='checkbox' name='");
		out.append(field.getProperty());
		out.append("'");
		String value = NVL.getStringContext(pageContext.getRequest()
				.getAttribute(field.getName()), field.getProperty());
		if(NVL.getBool(value)){
			out.append(" checked='checked'");
		}
		if (field.getIsReadonly()) {
			out.append(" readonly='readonly'");
		}
		if (field.getIsDisabled()) {
			out.append(" disabled='disabled'");
		}
		out.append(" />");
		//out.flush();
				
	}
	
	public void createButton(AipbaseViewField field, JspWriter out,String colspan) throws IOException {
		out.append("</td><td colspan='");
		out.append(colspan);
		out.append("'>");
		out.append("<input type='button' name='");
		out.append(field.getProperty());
		out.append("' value='");
		out.append(field.getKey());
		out.append("' onclick=\"");
		out.append(field.getOnClick());
		out.append("\"");
		out.append(" style='border-style: none; background-repeat: no-repeat; width: 111px; height: 25px; background-image: url(");
		out.append(field.getImageSrc());
		out.append("'");
		out.append(" />");
		//out.flush();
	}
	
	public void createSubmit(AipbaseViewField field, JspWriter out,String colspan) throws IOException {
		if (!field.getNotExist()) {
		out.append("</td><td colspan='");
		out.append(colspan);
		out.append("'>");
		out.append("<input type='submit' name='");
		out.append(field.getProperty());
		out.append("' value='");
		out.append(field.getKey());
		out.append("' onclick=\"this.form.elements['reqCode'].value='");
		out.append(field.getReqCode());
		out.append("';");
		out.append(field.getOnClick());
		out.append("\"");
		out.append(" style='border-style: none; background-repeat: no-repeat; width: 111px; height: 25px; background-image: url(");
		out.append(field.getImageSrc());
		out.append(");");
		if (!"".equals(field.getStyle())) {
			out.append(field.getStyle()+"'");
		} else {
			out.append("'");
		}
		out.append(" />");
		}
		//out.flush();
	}
	
	public void createHyperlink(AipbaseViewField field, JspWriter out,String colspan) throws IOException {
		out.append("<span id=\"headerText\">");
		out.append(field.getKey());
		out.append("</span>");
		out.append("</td><td colspan='");
		out.append(colspan);
		out.append("'>");
		out.append("<a href='");
		out.append(field.getUrl());
		out.append("' style='border-style: none; background-repeat: no-repeat; width: 111px; height: 25px;'");
		out.append(">");
		out.append(field.getKey());
		out.append("</a>");
		//out.flush();
	}

	public void createTextImage(AipbaseViewField field, JspWriter out,String colspan) throws IOException {
		out.append("<span id=\"headerText\">");
		out.append(field.getKey());
		out.append("</span>");
		out.append("</td><td colspan='");
		out.append(colspan);
		out.append("'>");
		out.append("<input type='text' name='");
		out.append(field.getProperty());
		out.append("' value='");
		String value = NVL.getStringContext(pageContext.getRequest()
				.getAttribute(field.getName()), field.getProperty());
		out.append(value);
		out.append("' />");
		out.append("<input type=\"image\" onclick=\"this.form.elements['reqCode'].value='");
		out.append(field.getReqCode());
		out.append("';");
		out.append(field.getOnClick());
		out.append("\"");
		out.append(" src='");
		out.append(field.getImageSrc());
		out.append("'/>");
		//out.flush();
		
	}

	public void createTextImg(AipbaseViewField field, JspWriter out,String colspan) throws IOException {
		out.append("<span id=\"headerText\">");
		out.append(field.getKey());
		out.append("</span>");
		out.append("</td><td colspan='");
		out.append(colspan);
		out.append("'>");
		out.append("<input type='text' name='");
		out.append(field.getProperty());
		out.append("' value='");
		String value = NVL.getStringContext(pageContext.getRequest()
				.getAttribute(field.getName()), field.getProperty());
		out.append(value);
		out.append("' />");

		out.append("<img style=\"cursor:pointer\" src='");
		out.append(field.getImageSrc());
		out.append("' onclick=\"");
		out.append(field.getOnClick());
		out.append("\"");
		out.append("'/>");
		//out.flush();
		
	}
	
	public void createTextArea(AipbaseViewField field, JspWriter out,String colspan) throws IOException {
		out.append("<span id=\"headerText\">");
		out.append(field.getKey());
		out.append("</span>");
		out.append("</td><td colspan='");
		out.append(colspan);
		out.append("'>");
		out.append("<textarea rows='10' cols='50' name='");
		out.append(field.getProperty());
		out.append("'");
		if (field.getIsReadonly()) {
			out.append(" readonly='readonly'");
		}
		if (field.getIsDisabled()) {
			out.append(" disabled='disabled'");
		}
		out.append(" style=\"width:100%;\"  >");
		String value = NVL.getStringContext(pageContext.getRequest()
				.getAttribute(field.getName()), field.getProperty());
		out.append(value);
		out.append("</textarea>");
		//out.flush();
	}
	
	public void createDiv(AipbaseViewField field, JspWriter out,String colspan) throws IOException {
		out.append("<span id=\"headerText\">");
		out.append(field.getKey());
		out.append("</span>");
		out.append("</td><td colspan='");
		out.append(colspan);
		out.append("'>");

		out.append("<div>");
		String value = NVL.getStringContext(pageContext.getRequest()
				.getAttribute(field.getName()), field.getProperty());
		out.append(value);
		out.append("</div>");
		//out.flush();
	}
	
	public void createLabel(AipbaseViewField field, JspWriter out,String colspan) throws IOException {
		System.out.println("AIPInput.createLabel()#################################");
		
		out.append("<span id=\"headerText\">");
		out.append(field.getKey());
		out.append("</span>");

		out.append("</td><td colspan='");
		out.append(colspan);
		out.append("'>");
		
		out.append("<span id=\"generalText\">");
		out.append("<span id=\"");
		out.append(field.getProperty());
		out.append("\">");
		String value = NVL.getStringContext(pageContext.getRequest()
				.getAttribute(field.getName()), field.getProperty());
		out.append(value);
		out.append("</span>");
		out.append("</span>");
		if (field.getFieldExtension() != null && !"".equalsIgnoreCase(field.getFieldExtension())) {
			out.print("<span id='headerText'>"+ field.getFieldExtension()+ "</span>");
		}
		//out.flush();
	}
	public void createHtml(AipbaseViewField field, JspWriter out,String colspan) throws IOException {
		out.append("<span id=\"headerText\">");
		out.append(field.getKey());
		out.append("</span>");
		out.append("</td><td colspan='");
		out.append(colspan);
		out.append("'>");

		out.append("<div>");
		String value = NVL.getString( field.getHtml() );
		out.append(value);
		out.append("</div>");
		//out.flush();
	}
	
	

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public String getColspan() {
		return colspan;
	}

	public void setColspan(String colspan) {
		this.colspan = colspan;
	}

}
