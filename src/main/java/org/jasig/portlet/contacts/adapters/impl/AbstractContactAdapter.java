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

package org.jasig.portlet.contacts.adapters.impl;

import java.util.Arrays;
import java.util.List;
import net.sf.ehcache.Cache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.ehcache.EhCacheFactoryBean;
import org.jasig.portlet.contacts.context.ContactContext;
import org.jasig.portlet.contacts.context.MissingContextException;
import org.jasig.portlet.contacts.adapters.ContactAdapter;

/**
 *
 * @author Chris White <christopher.white@manchester.ac.uk>
 */
public abstract class AbstractContactAdapter implements ContactAdapter {
    
    protected abstract String[] requiredAttributes();
    
    private ContactContext context;
    
    public ContactContext getContext() throws MissingContextException {
        String[] req = requiredAttributes();
        if (req.length > 0) {
            List<String> required = Arrays.asList(req);

            if (!required.isEmpty() && !context.provides(required))
                throw new MissingContextException(required);
        }
        return context;
    }
    
    @Autowired
    public void setContext(ContactContext context) { 
        this.context = context;
    }
    
}
