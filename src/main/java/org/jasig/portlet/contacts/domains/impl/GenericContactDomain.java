/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jasig.portlet.contacts.domains.impl;

import java.util.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.jasig.portlet.contacts.context.ContactContext;
import org.jasig.portlet.contacts.model.Contact;
import org.jasig.portlet.contacts.model.ContactSet;
import org.jasig.portlet.contacts.model.util.ContactSetComparator;
import org.jasig.portlet.contacts.adapters.PersistAdapter;
import org.jasig.portlet.contacts.adapters.PushAdapter;
import org.jasig.portlet.contacts.adapters.RemoveAdapter;
import org.jasig.portlet.contacts.adapters.SearchAdapter;
import org.jasig.portlet.contacts.decorators.ContactDecorator;
import org.jasig.portlet.contacts.decorators.impl.PassThroughContactDecorator;
import org.jasig.portlet.contacts.domains.ContactDomain;

/**
 *
 * @author Chris White <christopher.white@manchester.ac.uk>
 */
public class GenericContactDomain implements ContactDomain {

    private static Log log = LogFactory.getLog(GenericContactDomain.class);
    
    private String name;
    private String id = null;
    private boolean canDelete = false;
    private boolean canSave = false;
    private SearchAdapter searchAdapter = null;
    private PushAdapter pushAdapter = null;
    private PersistAdapter persistAdapter = null;
    private RemoveAdapter removeAdapter = null;
    private ContactContext context = null;
    private ContactDecorator decorator = new PassThroughContactDecorator();
 
    public void setName(String name) {
        this.name=name;
    }

    public void setId(String id) {
        this.id=id;
    }
    
    public void setSearchAdapter(SearchAdapter search) {
        this.searchAdapter = search;
    }

    public void setPushAdapter(PushAdapter push) {
        this.pushAdapter = push;
    }

    public void setPersistAdapter(PersistAdapter persist) {
        this.persistAdapter = persist;
    }
    public void setRemoveAdapter(RemoveAdapter remove) {
        this.removeAdapter = remove;
    }
    
    
    @Autowired
    public void setContext(ContactContext context) {
        this.context = context;
    }

    public boolean getHasSearch() {
        return (searchAdapter != null);
    }

    public boolean getHasPush() {
        return (pushAdapter != null);
    }

    public boolean getHasPersist() {
        return (persistAdapter != null);
    }

    public boolean getHasRemove() {
        return (removeAdapter != null);
    }

    
    public String getName() {
        return name;
    }

    public String getId() {
        if (id == null)
            return name.replace(" ", "");
        else
            return id;
    }
    
    public ContactContext getContext(){
        return context;
    }

    public ContactSet search(String searchText) {
        ContactSet results;
        if (getHasSearch())
            results = searchAdapter.search(searchText);
        else
            results = new ContactSet();
        
        decorator.decorate(results);
        
        return results;
    }
    
    public ContactSet search(String searchText, String filter) {
        ContactSet results;
        if (getHasSearch())
            results = searchAdapter.search(searchText, filter);
        else
            results = new ContactSet();
        
        decorator.decorate(results);
        
        return results;
    }

    public Set<ContactSet> getContacts() {
        
        Set<ContactSet> results;
        if (getHasPush())
            results = pushAdapter.getContacts();
        else
            results = new TreeSet<ContactSet>( new ContactSetComparator());
       
        for (ContactSet set : results) {
            decorator.decorate(set);
        }
                
        return results;
    }
    
    public ContactSet getContacts(String setId) {
        
        ContactSet result;
        if (getHasPush()) {
            result = pushAdapter.getContacts(setId);
            decorator.decorate(result);
        } else
            result = new ContactSet();
        
        return result;
        
    }
    
    public Map<String,String> getContactGroups() {
        
        Map<String,String> groups = new TreeMap<String,String>();
        
        if (getHasPush()) {
            groups.putAll(pushAdapter.getGroups());
        } 
        
        return groups;
        
    }
    
    public List<String> getSearchFilters() {
        List<String> filters = new ArrayList<String>();
        
        if (getHasSearch())
            filters.addAll(searchAdapter.getFilters().keySet());
        
        return filters;
    }
    
    public boolean save(Contact contact) {
        if (getHasPersist()) {
            log.debug("Passing to Persist adapter");
            return persistAdapter.save(contact);
        } else {
            log.debug("No Persist adapter for domain");
            return false;
        }
    }
    
    public boolean delete(Contact contact) {
        if (getHasRemove()) {
            log.debug("Passing to Remove adapter");
            return removeAdapter.delete(contact);
        } else {
            log.debug("No Remove adapter for domain");
            return false;
        }
    }

    public void setDecorator(ContactDecorator decorator) {
        this.decorator = decorator;
    }
    
    
    @Override
    public String toString() {
        return getId() + "::" + getName();
    }
    
}
