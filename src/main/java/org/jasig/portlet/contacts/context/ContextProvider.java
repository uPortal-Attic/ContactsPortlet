/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jasig.portlet.contacts.context;

import java.util.Map;
import java.util.Set;

/**
 *
 * @author Chris White <christopher.white@manchester.ac.uk>
 */
public interface ContextProvider {
    
    public boolean provides(String key);
    public Set<String> keySet();
    public Object get(String key);
    public Map<String,Object> getContext();
    
}
