/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jasig.portlet.contacts.model;

import java.io.Serializable;
import java.util.TreeSet;
import org.jasig.portlet.contacts.model.util.ContactComparator;

/**
 *
 * @author chris
 */
public class ContactSet extends TreeSet<Contact> implements Serializable {
    
    private String setId;
    private String setTitle;
    
    
    public String getId() {
        return setId;
    }
    public void setId(String id) {
        this.setId = id;
    }
    
    public String getTitle() {
        return setTitle;
    }
    public void setTitle(String title) {
        setTitle = title;
    }
    
    public ContactSet() {
        super(new ContactComparator());
    }
    
    public ContactSet(ContactComparator comparator) {
        super(comparator);
    }
    
    
}
