/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jasig.portlet.contacts.decorators;

import java.util.Collection;
import org.jasig.portlet.contacts.model.Contact;
import org.jasig.portlet.contacts.model.ContactSet;

/**
 *
 * @author chris
 */
public interface ContactDecorator {
    
    public void decorate(Contact contact);
    public void decorate(ContactSet contacts);
    public void setOverride(boolean override);
    
}
