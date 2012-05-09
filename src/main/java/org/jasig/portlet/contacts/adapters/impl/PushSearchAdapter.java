/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jasig.portlet.contacts.adapters.impl;

import java.util.Set;
import org.jasig.portlet.contacts.model.Contact;
import org.jasig.portlet.contacts.model.ContactSet;
import org.jasig.portlet.contacts.adapters.PushAdapter;

/**
 *
 * @author mfgsscw2
 */
public class PushSearchAdapter extends AbstractSearchAdapter {

    @Override
    protected String[] requiredAttributes() {
        return new String[0];
    }

    public ContactSet search(String searchText) {
        String searchTextLower = searchText.toLowerCase().trim();
        ContactSet contacts = new ContactSet();
        
        Set<ContactSet> pushContacts = pushAdapter.getContacts();
        
        for (ContactSet set : pushContacts) {
            for (Contact contact : set) {
                
                if (contact.getFirstname().toLowerCase().contains(searchTextLower) ||
                        contact.getSurname().toLowerCase().contains(searchTextLower))
                    contacts.add(contact);
            }
        }
  
        
        return contacts;
        
    }
    
    public ContactSet search(String searchText, String filter) {
        
        return search(searchText);
        
    }
    
    
    private PushAdapter pushAdapter;
    public void setPushAdapter(PushAdapter adapter) {
        pushAdapter = adapter;
    }

}
