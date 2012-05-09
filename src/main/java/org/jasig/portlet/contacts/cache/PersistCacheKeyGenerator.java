/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jasig.portlet.contacts.cache;

import com.googlecode.ehcache.annotations.key.StringCacheKeyGenerator;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.search.Query;
import net.sf.ehcache.search.Result;
import net.sf.ehcache.search.Results;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jasig.portlet.contacts.adapters.ContactAdapter;

/**
 *
 * @author Chris White <christopher.white@manchester.ac.uk>
 */
public class PersistCacheKeyGenerator extends StringCacheKeyGenerator  {

    private static Log log = LogFactory.getLog(PersistCacheKeyGenerator.class);
    
    @Override
    public String generateKey(MethodInvocation mi) {

        Cache cache = cacheManager.getCache(cacheToClear);
        
        ContactAdapter adapter = (ContactAdapter)mi.getThis();
        
        Results results = cache.createQuery().includeKeys().addCriteria(Query.KEY.ilike(adapter.getContext().toString() + "*")).execute();
        
        
        log.debug("KEYS TO DELETE -- "+results.size());
       
        for (Result result : results.all()) {
            log.debug(result.getKey().toString());
            cache.remove(result.getKey());
        }
        
        return RandomStringUtils.randomAlphanumeric(24);
    }
    
    private String cacheToClear = "PushResults";
    public void setCacheToClear(String cacheName) {
        cacheToClear = cacheName;
    }
    
    private CacheManager cacheManager;
    public void setCacheManager(CacheManager manager) {
            cacheManager = manager;
    }
    
    
}
