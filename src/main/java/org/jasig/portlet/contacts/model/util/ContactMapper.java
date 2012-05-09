/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jasig.portlet.contacts.model.util;

import org.jasig.portlet.contacts.model.Contact;

/**
 *
 * @author chris
 */
public interface ContactMapper<T> {
    
    public void mapToContact(T userObj, Contact contact);
    public void mapFromContact(Contact contact, T userObj);
    
}
