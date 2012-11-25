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
import org.jasig.portlet.contacts.context.ContactContext;
import org.jasig.portlet.contacts.context.ContextProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

/**
 *
 * @author Chris White <christopher.white@manchester.ac.uk>
 */
public class ContactContextImpl implements ContactContext {

    private static Log log = LogFactory.getLog(ContactContextImpl.class);
    private Map<String, Object> context = new HashMap<String, Object>();


    public Object get(String key) {
        
        Object out = null; 
        for (ContextProvider provider : contextProviders){
            if (provider.provides(key)) {
                out = provider.get(key);
                break;
            } 
        }
        return out;
        
    }
    
    public Map getAll() {
        
        Map obj = new HashMap();
        for (ContextProvider provider : contextProviders) {
            try {
                obj.putAll(provider.getContext());
            } catch (Exception e) {
                log.debug(e);
            }
        }
        
        return obj;
    }

    public boolean provides(String key) {
        log.debug(toString());
        for (ContextProvider provider : contextProviders){ 
            if (provider.provides(key)) {
                return true;
            } 
        } 
        return false;
        
    }

    public boolean provides(Collection<String> keys) {
        log.debug(toString());
        List<String> keyList = new ArrayList<String>(); 
        keyList.addAll(keys);
        for (ContextProvider provider : contextProviders){
            keyList.removeAll(provider.keySet()); 
        }
         
        return keyList.isEmpty();
        
    }
    private final List<ContextProvider> contextProviders = new ArrayList<ContextProvider>();

    @Autowired(required = true)
    public void setContextProviders(Collection<ContextProvider> providers) {
        contextProviders.addAll(providers);
    }
    
    public String toString() {
        
        List<String> outList = new ArrayList<String>();
        
        for (ContextProvider prov : contextProviders) {
            for (Map.Entry entry : prov.getContext().entrySet()) {
                outList.add(entry.getKey() + "=" + entry.getValue());
            }
            
        }
        
        
        String out = StringUtils.collectionToDelimitedString(outList,",", "[", "]");
        return out;
    }
}
