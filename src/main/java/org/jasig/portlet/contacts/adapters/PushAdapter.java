/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jasig.portlet.contacts.adapters;

import com.googlecode.ehcache.annotations.Cacheable;
import com.googlecode.ehcache.annotations.KeyGenerator;
import java.util.Map;
import java.util.Set;
import org.jasig.portlet.contacts.model.ContactSet;

/**
 *
 * @author Chris White <christopher.white@manchester.ac.uk>
 */
public interface PushAdapter extends ContactAdapter {

    @Cacheable(cacheName = "PushResults",
        keyGenerator = @KeyGenerator(name = "org.jasig.portlet.contacts.cache.AdapterCacheKeyGenerator")
    )
    public Set<ContactSet> getContacts();
    
    @Cacheable(cacheName = "PushResults",
        keyGenerator = @KeyGenerator(name = "org.jasig.portlet.contacts.cache.AdapterCacheKeyGenerator")
    )
    public ContactSet getContacts(String id);
    
    @Cacheable(cacheName = "PushResults",
        keyGenerator = @KeyGenerator(name = "org.jasig.portlet.contacts.cache.AdapterCacheKeyGenerator")
    )
    public Map<String,String> getGroups();
    
}
