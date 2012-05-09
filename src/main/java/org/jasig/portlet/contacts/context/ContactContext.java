package org.jasig.portlet.contacts.context;

import java.util.Collection;
import java.util.Map;

/**
 *
 * @author mfgsscw2
 */
public interface ContactContext  {
       
    void setContextProviders(Collection<ContextProvider> provider);
    boolean provides(String key);
    boolean provides(Collection<String> keys);
    Object get(String key);
    Map getAll();
    
    @Override
    String toString();
}
