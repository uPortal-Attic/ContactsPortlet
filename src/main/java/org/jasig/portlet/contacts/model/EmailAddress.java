/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jasig.portlet.contacts.model;

import java.io.Serializable;

/**
 *
 * @author mfgsscw2
 */
public interface EmailAddress extends Serializable {
    
    public static final String WORK_TYPE = "WORK";
    public static final String HOME_TYPE = "HOME";
    public static final String TEMP_TYPE = "TEMP";
    public static final String OTHER_TYPE = "OTHER";
    
    public String getLabel();
    public String getType();
    public String getEmailAddress();
    
    public void setLabel(String label);
    public void setType(String type);
    public void setEmailAddress(String email);
    
}
