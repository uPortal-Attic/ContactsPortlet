package org.jasig.portlet.contacts.domains;

import java.util.List;
import java.util.Map;
import java.util.Set;
import org.jasig.portlet.contacts.context.ContactContext;
import org.jasig.portlet.contacts.model.Contact;
import org.jasig.portlet.contacts.model.ContactSet;
import org.jasig.portlet.contacts.adapters.PersistAdapter;
import org.jasig.portlet.contacts.adapters.PushAdapter;
import org.jasig.portlet.contacts.adapters.RemoveAdapter;
import org.jasig.portlet.contacts.adapters.SearchAdapter;
import org.jasig.portlet.contacts.decorators.ContactDecorator;

/**
 *
 * @author Chris White <christopher.white@manchester.ac.uk>
 */
public interface ContactDomain {
    
    public void setName(String name);
    public void setId(String id);
    
    public void setSearchAdapter(SearchAdapter search);
    public void setPushAdapter(PushAdapter push);
    public void setPersistAdapter(PersistAdapter persist);
    public void setRemoveAdapter(RemoveAdapter remove);
    
    public void setContext(ContactContext context);
    
    public void setDecorator(ContactDecorator decorator);
    
    public boolean getHasSearch();
    public boolean getHasPush();
    public boolean getHasPersist();
    public boolean getHasRemove();
    
    public String getName();
    public String getId();
    public ContactContext getContext();
    
    
    public ContactSet search(String searchText);
    public ContactSet search(String searchText, String filter);
    
    
    public Set<ContactSet> getContacts();
    public ContactSet getContacts(String setId);
    public Map<String,String> getContactGroups();
    public List<String> getSearchFilters();
    
    public boolean save(Contact contact);
    
    public boolean delete(Contact contact);
    
}
