package aip.tag.search;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTag;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.IterationTag;
import javax.servlet.jsp.tagext.Tag;
import javax.servlet.jsp.tagext.TagSupport;
import java.util.Vector;
import javax.servlet.http.Cookie;

/**
 *  Generated tag class.
 */
public class ListItemTag extends BodyTagSupport {

    /**
     * property declaration for tag attribute: name.
     */    
    private java.lang.String name;    

    public ListItemTag() {
        super();
    }
    
    
    ////////////////////////////////////////////////////////////////
    ///                                                          ///
    ///   User methods.                                          ///
    ///                                                          ///
    ///   Modify these methods to customize your tag handler.    ///
    ///                                                          ///
    ////////////////////////////////////////////////////////////////
    
    
    //
    // methods called from doStartTag()
    //
    /**
     *  
     * Fill in this method to perform other operations from doStartTag().
     * 
     */
    public void otherDoStartTagOperations()  {
    
        //
        //       should be placed here.
        //       It will be called after initializing variables, 
        //       finding the parent, setting IDREFs, etc, and 
        //       before calling theBodyShouldBeEvaluated(). 
        //
        //       For example, to print something out to the JSP, use the following:
        //
        //   try {
        //       JspWriter out = pageContext.getOut();
        //       out.println("something");
        //   } catch (java.io.IOException ex) {
        //       // do something
        //   }
        //
        //
        

    }
    
    /**
     *  
     * Fill in this method to determine if the tag body should be evaluated
     * Called from doStartTag().
     * 
     */
    public boolean theBodyShouldBeEvaluated()  {

        //
        //       evaluated should be placed here.
        //       Called from the doStartTag() method.
        //
        return true;

    }
    
    
    //
    // methods called from doEndTag()
    //
    /**
     *  
     * Fill in this method to perform other operations from doEndTag().
     * 
     */
    public void otherDoEndTagOperations()  {
    
        //
        //       should be placed here.
        //       It will be called after initializing variables, 
        //       finding the parent, setting IDREFs, etc, and 
        //       before calling shouldEvaluateRestOfPageAfterEndTag(). 
        //
        

    }
    
    /**
     *  
     * Fill in this method to determine if the rest of the JSP page
     * should be generated after this tag is finished.
     * Called from doEndTag().
     * 
     */
    public boolean shouldEvaluateRestOfPageAfterEndTag()  {

        //
        //       should be evaluated after the tag is processed
        //       should be placed here.
        //       Called from the doEndTag() method.
        //
        return true;

    }
    
    
    ////////////////////////////////////////////////////////////////
    ///                                                          ///
    ///   Tag Handler interface methods.                         ///
    ///                                                          ///
    ///   Do not modify these methods; instead, modify the       ///
    ///   methods that they call.                                ///
    ///                                                          ///
    ////////////////////////////////////////////////////////////////
    
    
    /**
     * .
     *
     * This method is called when the JSP engine encounters the start tag,
     * after the attributes are processed.
     * Scripting variables (if any) have their values set here.
     * @return EVAL_BODY_INCLUDE if the JSP engine should evaluate the tag body, otherwise return SKIP_BODY.
     * This method is automatically generated. Do not modify this method.
     * Instead, modify the methods that this method calls.
     */
    public int doStartTag() throws JspException, JspException {
        otherDoStartTagOperations();

//        String width_dispaly="";
//        Cookie[] cookies=(Cookie[])pageContext.getAttribute("Cookies");
//        if(cookies!=null){
//            for(int i=0;i<cookies.length;i++){
//                if(name.compareTo(cookies[i].getName())==0){
//                    String[] st=cookies[i].getValue().split(";");;
//                    width_dispaly=" Width="+st[0]+(st[1].compareTo("0")==0?" STYLE='Display:none;' ":"");
//                    break;
//                }
//            }
//        }
        
         try{
            //pageContext.getOut().write("<td name="+getName()+width_dispaly+">"+getLabel()+"</td>");
            //pageContext.getOut().write("<td name="+getName()+">"+getLabel()+"</td>");
            Vector cols = (Vector)pageContext.getAttribute("Columns");
            cols.add(getName());
            
        }catch(Exception ex){
            ex.printStackTrace();
        }
        
        if (theBodyShouldBeEvaluated()) {
            return EVAL_BODY_BUFFERED;
        } else {
            return SKIP_BODY;
        }
    }
    
    /**
     * .
     *
     *
     * This method is called after the JSP engine finished processing the tag.
     * @return EVAL_PAGE if the JSP engine should continue evaluating the JSP page, otherwise return SKIP_PAGE.
     * This method is automatically generated. Do not modify this method.
     * Instead, modify the methods that this method calls.
     */
    public int doEndTag() throws JspException, JspException {
        otherDoEndTagOperations();
        
        if (shouldEvaluateRestOfPageAfterEndTag()) {
            return EVAL_PAGE;
        } else {
            return SKIP_PAGE;
        }
    }
    
    public java.lang.String getName() {
        return name;
    }
    
    public void setName(java.lang.String value) {
        name = value;
    }
    
    /**
     * .
     * Fill in this method to process the body content of the tag.
     * You only need to do this if the tag's BodyContent property
     * is set to "JSP" or "tagdependent."
     * If the tag's bodyContent is set to "empty," then this method
     * will not be called.
     */    
    public void writeTagBodyContent(JspWriter out, BodyContent bodyContent) throws IOException {
        //
        // 
        //       e.g.  out.println("<B>" + getAttribute1() + "</B>");
        //             out.println("   <BLOCKQUOTE>");
        
        //
        // write the body content (after processing by the JSP engine) on the output Writer
        //
        bodyContent.writeOut(out);
        
        //
        // Or else get the body content as a string and process it, e.g.:
        //     String bodyStr = bodyContent.getString();
        //     String result = yourProcessingMethod(bodyStr);
        //     out.println(result);
        //
        
        // 
        //       e.g.  out.println("   <BLOCKQUOTE>");
        
        // clear the body content for the next time through.
        bodyContent.clearBody();
    }
    
    /**
     * .
     *
     * Handles exception from processing the body content.
     */    
    public void handleBodyContentException(Exception ex) throws JspException {
        // Since the doAfterBody method is guarded, place exception handing code here.
        throw new JspException("error in ListItemTag: " + ex);
    }
    
    /**
     * .
     *
     *
     * This method is called after the JSP engine processes the body content of the tag.
     * @return EVAL_BODY_AGAIN if the JSP engine should evaluate the tag body again, otherwise return SKIP_BODY.
     * This method is automatically generated. Do not modify this method.
     * Instead, modify the methods that this method calls.
     */    
    public int doAfterBody() throws JspException {
        try {
            //
            // This code is generated for tags whose bodyContent is "JSP"
            //
            BodyContent bodyContent = getBodyContent();
            JspWriter out = bodyContent.getEnclosingWriter();
            
            writeTagBodyContent(out, bodyContent);
        } catch (Exception ex) {
            handleBodyContentException(ex);
        }
        
        if (theBodyShouldBeEvaluatedAgain()) {
            return EVAL_BODY_AGAIN;
        } else {
            return SKIP_BODY;
        }
    }
    
    /**
     *
     * Fill in this method to determine if the tag body should be evaluated
     * again after evaluating the body.
     * Use this method to create an iterating tag.
     * Called from doAfterBody().
     *
     */    
    public boolean theBodyShouldBeEvaluatedAgain() {
        //
        //       evaluated again after processing the tag
        //       should be placed here.
        //       You can use this method to create iterating tags.
        //       Called from the doAfterBody() method.
        //
        return false;
    }
    
}
