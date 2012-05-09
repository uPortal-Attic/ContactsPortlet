/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jasig.portlet.contacts.model.util;

import java.util.Comparator;
import org.jasig.portlet.contacts.model.ContactSet;

/**
 *
 * @author mfgsscw2
 */
public class ContactSetComparator implements Comparator<ContactSet> {

    public int compare(ContactSet o1, ContactSet o2) {
        int result = o1.getTitle().compareToIgnoreCase(o2.getTitle());
        if (result == 0)
            result = o1.getId().compareTo(o2.getId());
            
        return result;
        
    }

    
    
}
