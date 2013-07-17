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

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import javax.portlet.PortletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.jasig.portlet.contacts.context.ContextProvider;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.portlet.context.PortletRequestAttributes;

/**
 *
 * @author mfgsscw2
 */
public class PortalUsernameContextProvider implements ContextProvider {

    public boolean provides(String key) {
        return (key.equals("username"));
    }

    public Set<String> keySet() {
        return new HashSet(Arrays.asList(new String[] {"username"}));
    }

    public Object get(String key) {
        return getRequest().getRemoteUser();
    }
    public Map<String, Object> getContext() {
        Map<String, Object> context = new HashMap<String,Object>();
        context.put("username", get("username"));
        return context;
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
