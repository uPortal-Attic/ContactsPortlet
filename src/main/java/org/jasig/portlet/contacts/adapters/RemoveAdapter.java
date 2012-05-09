package org.jasig.portlet.contacts.adapters;

import com.googlecode.ehcache.annotations.KeyGenerator;
import com.googlecode.ehcache.annotations.Property;
import com.googlecode.ehcache.annotations.TriggersRemove;
import org.jasig.portlet.contacts.model.Contact;

/**
 *
 * @author Chris White <christopher.white@manchester.ac.uk>
 */
public interface RemoveAdapter extends ContactAdapter {

   @TriggersRemove(cacheName = "PushResults",
        keyGenerator =
            @KeyGenerator(
                name = "org.jasig.portlet.contacts.cache.PersistCacheKeyGenerator",
                properties = @Property(name = "cacheManager", ref = "cacheManager")
        )
    )
    public boolean delete(Contact contact);
}
