/**
 * Licensed to Jasig under one or more contributor license agreements. See the
 * NOTICE file distributed with this work for additional information regarding
 * copyright ownership. Jasig licenses this file to you under the Apache
 * License, Version 2.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.jasig.portlet.contacts.context.impl;

import java.util.Map;
import java.util.Set;
import javax.portlet.PortletRequest;
import org.jasig.portlet.contacts.context.ContextProvider;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.portlet.context.PortletRequestAttributes;

/**
 *
 * @author mfgsscw2
 */
public class PortalUserInfoContextProvider implements ContextProvider {

    public boolean provides(String key) {
        return getUserInfo().containsKey(key);
    }

    public Set<String> keySet() {
        return getUserInfo().keySet();
    }

    public Object get(String key) {
        return getUserInfo().get(key);
    }

    public Map<String, Object> getContext() {
        
        return getUserInfo();
    }

    private Map<String,Object> getUserInfo() {
        return (Map<String, Object>) getRequest().getAttribute(PortletRequest.USER_INFO);
    }
    
    private PortletRequest getRequest() {
        RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
        PortletRequest request = null;
        if (requestAttributes instanceof PortletRequestAttributes) {
            request = ((PortletRequestAttributes) requestAttributes).getRequest();
        } 
        
        return request;
    }
    
}
