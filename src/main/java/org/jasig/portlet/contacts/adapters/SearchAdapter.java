/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jasig.portlet.contacts.adapters;

import com.googlecode.ehcache.annotations.Cacheable;
import com.googlecode.ehcache.annotations.KeyGenerator;
import java.util.Map;
import org.jasig.portlet.contacts.model.ContactSet;

/**
 *
 * @author Chris White <christopher.white@manchester.ac.uk>
 */
public interface SearchAdapter extends ContactAdapter {

    @Cacheable(cacheName = "SearchResults",
        keyGenerator = @KeyGenerator(name = "org.jasig.portlet.contacts.cache.AdapterCacheKeyGenerator")
    )
    public ContactSet search(String searchText);
    
    @Cacheable(cacheName = "SearchResults",
        keyGenerator = @KeyGenerator(name = "org.jasig.portlet.contacts.cache.AdapterCacheKeyGenerator")
    )
    public ContactSet search(String searchText, String filter);
    
    public void setFilters(Map<String,Object> filers);
    public Map<String,Object> getFilters();
    public void setFilterAttribute(String attribute);
    public String getFilterAttribute();
}
