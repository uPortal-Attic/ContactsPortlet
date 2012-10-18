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

package org.jasig.portlet.contacts.domains;

import java.util.List;
import java.util.Map;
import java.util.Set;
import org.jasig.portlet.contacts.adapters.PersistAdapter;
import org.jasig.portlet.contacts.adapters.PushAdapter;
import org.jasig.portlet.contacts.adapters.RemoveAdapter;
import org.jasig.portlet.contacts.adapters.SearchAdapter;
import org.jasig.portlet.contacts.context.ContactContext;
import org.jasig.portlet.contacts.decorators.ContactDecorator;
import org.jasig.portlet.contacts.model.Contact;
import org.jasig.portlet.contacts.model.ContactSet;

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
    public Contact getContact(String URN);
    public List<String> getSearchFilters();
    
    public boolean save(Contact contact);
    
    public boolean delete(Contact contact);
    
}
