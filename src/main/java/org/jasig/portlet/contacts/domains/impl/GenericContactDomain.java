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

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jasig.portlet.contacts.domains.impl;

import java.util.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jasig.portlet.contacts.adapters.PersistAdapter;
import org.jasig.portlet.contacts.adapters.PushAdapter;
import org.jasig.portlet.contacts.adapters.RemoveAdapter;
import org.jasig.portlet.contacts.adapters.SearchAdapter;
import org.jasig.portlet.contacts.context.ContactContext;
import org.jasig.portlet.contacts.decorators.ContactDecorator;
import org.jasig.portlet.contacts.decorators.impl.PassThroughContactDecorator;
import org.jasig.portlet.contacts.domains.ContactDomain;
import org.jasig.portlet.contacts.model.Contact;
import org.jasig.portlet.contacts.model.ContactSet;
import org.jasig.portlet.contacts.model.util.ContactSetComparator;
import org.springframework.beans.factory.annotation.Autowired;

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
 
    @Override
    public void setName(String name) {
        this.name=name;
    }

    @Override
    public void setId(String id) {
        this.id=id;
    }
    
    @Override
    public void setSearchAdapter(SearchAdapter search) {
        this.searchAdapter = search;
    }

    @Override
    public void setPushAdapter(PushAdapter push) {
        this.pushAdapter = push;
    }

    @Override
    public void setPersistAdapter(PersistAdapter persist) {
        this.persistAdapter = persist;
    }
    @Override
    public void setRemoveAdapter(RemoveAdapter remove) {
        this.removeAdapter = remove;
    }
    
    
    @Autowired
    @Override
    public void setContext(ContactContext context) {
        this.context = context;
    }

    @Override
    public boolean getHasSearch() {
        return (searchAdapter != null);
    }

    @Override
    public boolean getHasPush() {
        return (pushAdapter != null);
    }

    @Override
    public boolean getHasPersist() {
        return (persistAdapter != null);
    }

    @Override
    public boolean getHasRemove() {
        return (removeAdapter != null);
    }

    
    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getId() {
        if (id == null)
            return name.replace(" ", "");
        else
            return id;
    }
    
    @Override
    public ContactContext getContext(){
        return context;
    }

    @Override
    public ContactSet search(String searchText) {
        ContactSet results;
        if (getHasSearch())
            results = searchAdapter.search(searchText);
        else
            results = new ContactSet();
        
        decorator.decorate(results);
        
        return results;
    }
    
    @Override
    public ContactSet search(String searchText, String filter) {
        ContactSet results;
        if (getHasSearch())
            results = searchAdapter.search(searchText, filter);
        else
            results = new ContactSet();
        
        decorator.decorate(results);
        
        return results;
    }

    @Override
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
    
    @Override
    public ContactSet getContacts(String setId) {
        
        ContactSet result;
        if (getHasPush()) {
            result = pushAdapter.getContacts(setId);
            decorator.decorate(result);
        } else
            result = new ContactSet();
        
        return result;
        
    }
    
    @Override
    public Contact getContact(String URN) {
        
        Contact contact = null;
        
        if (this.getHasSearch())
            contact = searchAdapter.getByURN(URN);
        if (contact == null && this.getHasPush())
            contact = pushAdapter.getByURN(URN);
        
        decorator.decorate(contact);
        
        return contact;
        
    }
    
    @Override
    public Map<String,String> getContactGroups() {
        
        Map<String,String> groups = new TreeMap<String,String>();
        
        if (getHasPush()) {
            groups.putAll(pushAdapter.getGroups());
        } 
        
        return groups;
        
    }
    
    @Override
    public List<String> getSearchFilters() {
        List<String> filters = new ArrayList<String>();
        
        if (getHasSearch())
            filters.addAll(searchAdapter.getFilters().keySet());
        
        return filters;
    }
    
    @Override
    public boolean save(Contact contact) {
        if (getHasPersist()) {
            log.debug("Passing to Persist adapter");
            return persistAdapter.save(contact);
        } else {
            log.debug("No Persist adapter for domain");
            return false;
        }
    }
    
    @Override
    public boolean delete(Contact contact) {
        if (getHasRemove()) {
            log.debug("Passing to Remove adapter");
            return removeAdapter.delete(contact);
        } else {
            log.debug("No Remove adapter for domain");
            return false;
        }
    }

    @Override
    public void setDecorator(ContactDecorator decorator) {
        this.decorator = decorator;
    }
    
    
    @Override
    public String toString() {
        return getId() + "::" + getName();
    }
    
}
