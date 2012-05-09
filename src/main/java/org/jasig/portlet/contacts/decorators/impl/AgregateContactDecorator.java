/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jasig.portlet.contacts.decorators.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.jasig.portlet.contacts.model.Contact;
import org.jasig.portlet.contacts.model.ContactSet;
import org.jasig.portlet.contacts.decorators.ContactDecorator;

/**
 *
 * @author Chris White <christopher.white@manchester.ac.uk>
 */
public class AgregateContactDecorator implements ContactDecorator {

    public void decorate(Contact contact) {
        for (ContactDecorator decorator : decorators)
            decorator.decorate(contact);
    }

    public void decorate(ContactSet contacts) {
        for (ContactDecorator decorator : decorators)
            decorator.decorate(contacts);
    }

    public void setOverride(boolean override) {
        for (ContactDecorator decorator : decorators)
            decorator.setOverride(override);
    }
    
    private List<ContactDecorator> decorators = new ArrayList<ContactDecorator>();
    public void setDecorators(Collection<ContactDecorator> decorators) {
        this.decorators.clear();
        this.decorators.addAll(decorators);
    }
    
}
