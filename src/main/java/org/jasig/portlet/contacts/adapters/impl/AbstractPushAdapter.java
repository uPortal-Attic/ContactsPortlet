/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jasig.portlet.contacts.adapters.impl;

import java.util.Set;
import org.jasig.portlet.contacts.adapters.PushAdapter;
import org.jasig.portlet.contacts.model.Contact;
import org.jasig.portlet.contacts.model.ContactSet;

/**
 *
 * @author mfgsscw2
 */
public abstract class AbstractPushAdapter extends AbstractContactAdapter implements PushAdapter {
    
    
     public Contact getByURN(String URN) {
        
        Contact contact = null;
        
        Set<ContactSet> pushContacts = getContacts();
        
        setSearch: for (ContactSet set : pushContacts) {
            for (Contact c : set) {
                
                if (c.getURN().equals(URN)) {
                    contact = c;
                    break setSearch;
                }
            }
        }
  
        
        return contact;
        
    }
    
}
