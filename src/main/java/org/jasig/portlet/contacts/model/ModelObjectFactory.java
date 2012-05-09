/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jasig.portlet.contacts.model;

/**
 *
 * @author mfgsscw2
 */
public interface ModelObjectFactory {

    public <T extends Object> T getObjectOfType(Class<T> clazz);
    
}
