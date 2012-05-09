/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jasig.portlet.contacts.adapters;

import org.jasig.portlet.contacts.context.ContactContext;
import org.jasig.portlet.contacts.context.MissingContextException;

/**
 *
 * @author mfgsscw2
 */
public interface ContactAdapter {
    public void setContext(ContactContext context) throws MissingContextException;
    public ContactContext getContext() throws MissingContextException;
    
}
