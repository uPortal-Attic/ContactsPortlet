/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jasig.portlet.contacts.decorators.impl;

import java.util.Collection;
import org.jasig.portlet.contacts.model.Contact;
import org.jasig.portlet.contacts.model.ContactSet;
import org.jasig.portlet.contacts.decorators.ContactDecorator;

/**
 *
 * @author Chris White <christopher.white@manchester.ac.uk>
 */
public class PassThroughContactDecorator implements ContactDecorator {

    public void decorate(Contact contact) {
        return;
    }

    public void decorate(ContactSet contacts) {
        return;
    }

    public void setOverride(boolean override) {
        return;
    }
    
}
