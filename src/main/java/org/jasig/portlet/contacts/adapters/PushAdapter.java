/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jasig.portlet.contacts.adapters;

import java.util.Map;
import java.util.Set;
import org.jasig.portlet.contacts.model.ContactSet;

/**
 *
 * @author Chris White <christopher.white@manchester.ac.uk>
 */
public interface PushAdapter extends ContactAdapter {

    
    public Set<ContactSet> getContacts();
    
    
    public ContactSet getContacts(String id);
    
    
    public Map<String,String> getGroups();
    
}
