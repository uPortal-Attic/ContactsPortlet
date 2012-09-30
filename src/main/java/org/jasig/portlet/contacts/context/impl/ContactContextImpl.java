/**
 * Licensed to Jasig under one or more contributor license
 * agreements. See the NOTICE file distributed with this work
 * for additional information regarding copyright ownership.
 * Jasig licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a
 * copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.jasig.portlet.contacts.context.impl;

import java.util.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.jasig.portlet.contacts.context.ContactContext;
import org.jasig.portlet.contacts.context.ContextProvider;

/**
 *
 * @author Chris White <christopher.white@manchester.ac.uk>
 */
public class ContactContextImpl implements ContactContext {

    private static Log log = LogFactory.getLog(ContactContextImpl.class);
    private Map<String, Object> context = new HashMap<String, Object>();

    private void prime() {

        if (!contextProviders.isEmpty()) {
            List<ContextProvider> done = new ArrayList<ContextProvider>();
            for (ContextProvider provider : contextProviders) {
                try {
                    context.putAll(provider.getContext());
                    done.add(provider);
                    log.debug(provider.getClass().getCanonicalName() + " : done");
                } catch (Exception e) {
                    log.debug("ContextProvider not ready -- ", e);
                }
            }
            contextProviders.removeAll(done);
log.debug(context);            
        }

    }

    public Object get(String key) {
        prime();
        return context.get(key);
        /*
         * Object out = null; for (ContextProvider provider : contextProviders){
         * if (provider.provides(key)) out = provider.get(key); }
         *
         * return out;
         */
    }
    
    public Map getAll() {
        prime();
        
        Map obj = new HashMap();
        obj.putAll(context);
        
        return obj;
    }

    public boolean provides(String key) {
        prime();
        return context.containsKey(key);
        /*
         * for (ContextProvider provider : contextProviders){ if
         * (provider.provides(key)) return true; } return false;
         */
    }

    public boolean provides(Collection<String> keys) {
        prime();
        return context.keySet().containsAll(keys);

        /*
         * List<String> keyList = new ArrayList<String>(); keyList.addAll(keys);
         *
         *
         *
         * for (ContextProvider provider : contextProviders){
         * keyList.removeAll(provider.keySet()); }
         *
         * return keyList.isEmpty();
         */
    }
    private final List<ContextProvider> contextProviders = new ArrayList<ContextProvider>();

    @Autowired(required = false)
    public void setContextProviders(Collection<ContextProvider> providers) {
        contextProviders.addAll(providers);

        prime();
    }
    
    public String toString() {
        List<String> outList = new ArrayList<String>();
        for(Map.Entry<String,Object> entry : context.entrySet()) {
            outList.add(entry.getKey() + "=" + entry.getValue().toString());
        }
        
        String out = StringUtils.collectionToDelimitedString(outList,",", "[", "]");
        return out;
    }
}
