/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jasig.portlet.contacts.model.impl;

import org.jasig.portlet.contacts.model.Address;
import org.jasig.portlet.contacts.model.Contact;
import org.jasig.portlet.contacts.model.EmailAddress;
import org.jasig.portlet.contacts.model.ModelObjectFactory;
import org.jasig.portlet.contacts.model.PhoneNumber;

/**
 *
 * @author mfgsscw2
 */
public class ModelPojoFactory implements ModelObjectFactory {
    
    
    public <T extends Object> T getObjectOfType(Class<T> clazz) {
        if (clazz.isAssignableFrom(Contact.class))
            return (T) new ContactPojo();
        else if (clazz.isAssignableFrom(Address.class))
            return (T) new AddressPojo();
        else if (clazz.isAssignableFrom(PhoneNumber.class))
            return (T) new PhoneNumberPojo();
        else if (clazz.isAssignableFrom(EmailAddress.class))
            return (T) new EmailAddressPojo();
        else
            throw new RuntimeException("Not a valid model class");
    }
   
}
