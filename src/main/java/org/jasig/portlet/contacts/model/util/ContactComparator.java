/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jasig.portlet.contacts.model.util;

import java.util.Comparator;
import org.jasig.portlet.contacts.model.Contact;

/**
 *
 * @author chris
 */
public class ContactComparator implements Comparator<Contact> {

    public int compare(Contact t, Contact t1) {
        int ret = t.getSurname().compareToIgnoreCase(t1.getSurname());
        if (ret == 0)
            ret = t.getFirstname().compareToIgnoreCase(t1.getFirstname());
        if (ret == 0)
            ret = t.getInitials().compareToIgnoreCase(t1.getInitials());
        if (ret == 0)
            ret = t.getId().compareToIgnoreCase(t1.getId());
        
        return ret;
    }
    
}
