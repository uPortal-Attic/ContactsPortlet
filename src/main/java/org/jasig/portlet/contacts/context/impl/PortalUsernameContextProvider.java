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

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.portlet.PortletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.jasig.portlet.contacts.context.ContextProvider;

/**
 *
 * @author mfgsscw2
 */
public class PortalUsernameContextProvider implements ContextProvider {

    public boolean provides(String key) {
        return userInfo.containsKey(key);
    }

    public Set<String> keySet() {
        return userInfo.keySet();
    }

    public Object get(String key) {
        return userInfo.get(key);
    }
    public Map<String, Object> getContext() {
        return userInfo;
    }
    
    private final Map userInfo = new HashMap<String,String>();
    
    @Autowired
    public void setRequest(PortletRequest request) {
        String username = request.getRemoteUser().trim();
        userInfo.put("username", username);
    }  
}
