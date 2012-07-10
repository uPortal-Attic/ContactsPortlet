package org.jasig.portlet.contacts.adapters;

import org.jasig.portlet.contacts.model.Contact;

/**
 *
 * @author Chris White <christopher.white@manchester.ac.uk>
 */
public interface PersistAdapter extends ContactAdapter {

   public boolean save(Contact contact);
   
}
