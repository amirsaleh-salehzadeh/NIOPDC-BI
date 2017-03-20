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
import aip.law.struts.form.common.AipbaseEditForm;
import aip.law.struts.form.common.AipbaseViewField;
import aip.tag.AIPBaseTag;
import aip.tag.AIPPaggingLSTInterface;
import aip.util.AIPUtil;
import aip.util.NVL;

public class AIPInputs extends AIPBaseTag {

	/*
	 * Attributes
	 */
	String name;
	String fields;
	String scope;
	String columnsPerRow = "1";

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

	public AIPInputs() {
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
		System.out
				.println("AIPInput.getContentStartTag()#############################################");

		List<AipbaseViewField> fields;
		try {
			fields = (List<AipbaseViewField>) AIPUtil.invokeMulti(pageContext.getRequest().getAttribute(this.name), this.fields);

//			AIPUtil.printObject(fields);

			JspWriter out = pageContext.getOut();
			AIPInput aipInput = new AIPInput();
			aipInput.setPageContext(pageContext);

			int colspanCounter = 0;
			int columnsPerRow = NVL.getInt(this.columnsPerRow, 1);// aipbaseEditForm.getColumnsPerRow()
																	// * 2;
			int colSpan = 1;

			for (int indexId = 0; indexId < fields.size(); indexId++) {
				AipbaseViewField field = fields.get(indexId);

				try {

					if (field.getFieldGroup() != null
							&& field.getFieldGroup().getIsStart()) {
						if (indexId != 0)
							out.append("</tr>");

						out
								.append("<table align='center' dir='rtl' width='100%'>");

						colspanCounter = 0;
						columnsPerRow = field.getFieldGroup()
								.getColumnsPerRow() * 2;
						colSpan = 1;
					}
					// <!-- Start Row -->
					if (colspanCounter % columnsPerRow == 0) {
						out.append("<tr>");
					}
					if (field.getColspan() > 1) {
						colSpan = field.getColspan() * 2 - 1;
					} else {
						colSpan = 1;
					}
					colspanCounter += field.getColspan() * 2;

					out.append("<td nowrap='nowrap' valign='top'>");

					// <aip:input field="field" scope="page"
					// colspan="<%=NVL.getString(colSpan)%>" />
					try {
						String colSpanStr = NVL.getString(colSpan);
						switch (field.getInputType()) {
						case AipbaseViewField.INPUT_TEXT:
							aipInput.createText(field, out, colSpanStr);
							break;
						case AipbaseViewField.INPUT_HIDDEN:
							aipInput.createHidden(field, out, colSpanStr);
							break;
						case AipbaseViewField.INPUT_DISABLED:
							aipInput.createDisabled(field, out, colSpanStr);
							break;
						case AipbaseViewField.INPUT_PASSWORD:
							aipInput.createPassword(field, out, colSpanStr);
							break;
						case AipbaseViewField.INPUT_DATE:
							aipInput.createDate(field, out, colSpanStr);
							break;
						case AipbaseViewField.INPUT_SELECT:
							aipInput.createSelect(field, out, colSpanStr);
							break;
						case AipbaseViewField.INPUT_RADIO:
							aipInput.createRadio(field, out, colSpanStr);
							break;
						case AipbaseViewField.INPUT_CHECKBOX:
							aipInput.createCheckbox(field, out, colSpanStr);
							break;
						case AipbaseViewField.INPUT_BUTTON:
							aipInput.createButton(field, out, colSpanStr);
							break;
						case AipbaseViewField.INPUT_SUBMIT:
							aipInput.createSubmit(field, out, colSpanStr);
							break;
						case AipbaseViewField.INPUT_HYPERLINK:
							aipInput.createHyperlink(field, out, colSpanStr);
							break;
						case AipbaseViewField.INPUT_TEXT_IMAGE:
							aipInput.createTextImage(field, out, colSpanStr);
							break;
						case AipbaseViewField.INPUT_TEXT_IMG:
							aipInput.createTextImg(field, out, colSpanStr);
							break;
						case AipbaseViewField.INPUT_TEXTAREA:
							aipInput.createTextArea(field, out, colSpanStr);
							break;
						case AipbaseViewField.INPUT_DIV:
							aipInput.createDiv(field, out, colSpanStr);
							break;
						case AipbaseViewField.INPUT_LABEL:
							aipInput.createLabel(field, out, colSpanStr);
							break;
						case AipbaseViewField.INPUT_HTML:
							aipInput.createHtml(field, pageContext.getOut(), colSpanStr);
							break;

						}
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					out.append("</td>");
					if (colspanCounter % columnsPerRow == columnsPerRow
							|| indexId == (fields.size() - 1)) {
						out.append("</tr>");
					}
					if (field.getFieldGroup() != null
							&& !field.getFieldGroup().getIsStart()) {
						out.append("</table>");
					}

				} catch (IOException e) {
					// TODO: handle exception
					e.printStackTrace();
				}

			}
		} catch (IllegalArgumentException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SecurityException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InvocationTargetException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (NoSuchMethodException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		return "";
	}

	/*
	 * contentEndTag
	 */
	public String getContentEndTag() {
		return "";
	}

	public String getFields() {
		return fields;
	}

	public void setFields(String fields) {
		this.fields = fields;
	}

	public String getScope() {
		return scope;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public String getColumnsPerRow() {
		return columnsPerRow;
	}

	public void setColumnsPerRow(String columnsPerRow) {
		this.columnsPerRow = columnsPerRow;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}





