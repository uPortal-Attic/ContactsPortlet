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
public class PortalUserInfoContextProvider implements ContextProvider {

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
    
    
    Map userInfo = new HashMap<String,String>();
    @Autowired
    public void setUserInfo(PortletRequest request) {
        userInfo.putAll((Map)request.getAttribute(PortletRequest.USER_INFO));
    }


}
