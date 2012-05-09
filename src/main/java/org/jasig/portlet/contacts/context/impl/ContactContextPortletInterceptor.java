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
