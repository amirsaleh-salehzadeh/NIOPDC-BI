 /**
  * Copyright (C) 2006 CINCOM SYSTEMS, INC.
  * All Rights Reserved
  * Copyright (C) 2006 Igor Mekterovic
  * All Rights Reserved
  * @author PYadav, SBalda
  */
package rex.utils;

/**
 *
 * @author  PYadav, SBalda
 */
public class LanguageChangedEvent {
    
    private java.util.Locale locale;
    
    /** Creates a new instance of LanguageChangedEvent */
    public LanguageChangedEvent(java.util.Locale locale) {
        
        this.locale = locale;
        
    }
    
    /** Getter for property locale.
     * @return Value of property locale.
     *
     */
    public java.util.Locale getLocale() {
        return locale;
    }
    
    /** Setter for property locale.
     * @param locale New value of property locale.
     *
     */
    public void setLocale(java.util.Locale locale) {
        this.locale = locale;
    }
    
}
