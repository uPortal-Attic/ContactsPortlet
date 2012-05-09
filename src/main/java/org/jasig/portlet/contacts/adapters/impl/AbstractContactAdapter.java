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
