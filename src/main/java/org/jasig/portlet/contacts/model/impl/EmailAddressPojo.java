/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jasig.portlet.contacts.model.impl;

import org.jasig.portlet.contacts.model.EmailAddress;

/**
 *
 * @author mfgsscw2
 */
public class EmailAddressPojo implements EmailAddress {

    private String label = null;
    private String type = null;
    private String address = null;
    
    public String toString() {
        return "EMAIL:"+type + ":" + label + ":"+address;
    }
    
    public String getLabel() {
        return label;
    }

    public String getEmailAddress() {
        return address;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setEmailAddress(String email) {
        this.address = email;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    
}
