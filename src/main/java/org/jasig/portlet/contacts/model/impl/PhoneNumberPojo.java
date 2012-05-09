/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jasig.portlet.contacts.model.impl;

import org.jasig.portlet.contacts.model.PhoneNumber;

/**
 *
 * @author mfgsscw2
 */
public class PhoneNumberPojo implements PhoneNumber {

    private String label = null;
    private String type = null;
    private String number = null;
    
    
    public String toString() {
        return "PHONE:"+type +":"+label+":"+number;
    }
    
    public String getLabel() {
        return label;
    }

    public String getType() {
        return type;
    }

    public String getPhoneNumber() {
        return number;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setPhoneNumber(String number) {
        this.number = number;
    }
    
}
