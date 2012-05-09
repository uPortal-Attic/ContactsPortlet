/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jasig.portlet.contacts.cache;

import com.googlecode.ehcache.annotations.key.StringCacheKeyGenerator;
import org.aopalliance.intercept.MethodInvocation;
import org.jasig.portlet.contacts.adapters.ContactAdapter;

/**
 *
 * @author Chris White <christopher.white@manchester.ac.uk>
 */
public class AdapterCacheKeyGenerator extends StringCacheKeyGenerator  {

    @Override
    public String generateKey(MethodInvocation mi) {
        ContactAdapter adapter = (ContactAdapter)mi.getThis();
        String key = adapter.getContext().toString() + adapter.getClass().getCanonicalName() + super.generateKey(mi);
        return key;
    }
    
}
