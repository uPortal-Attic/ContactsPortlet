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

import java.util.Collection;
import javax.portlet.PortletRequest;
import javax.portlet.PortletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.portlet.handler.HandlerInterceptorAdapter;
import org.jasig.portlet.contacts.context.ContactContext;
import org.jasig.portlet.contacts.context.ContextProvider;

/**
 *
 * @author mfgsscw2
 */
public class ContactContextPortletInterceptor extends HandlerInterceptorAdapter {
    
    @Override
    public boolean preHandle(
            PortletRequest request, 
            PortletResponse response, 
            Object handler
    ) {
        
        context.setContextProviders(contextProviders);
        
        
        return true;
    }
    
    
    Collection<ContextProvider> contextProviders;
    public void setPortletContextProviders(Collection<ContextProvider> providers) {
        contextProviders = providers;
    }
    
    private ContactContext context;
    
    @Autowired
    public void setContext(ContactContext context) {
        this.context = context;
    }
    
}
