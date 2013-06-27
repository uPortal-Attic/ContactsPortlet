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
package org.jasig.portlet.contacts.adapters.impl;

import java.util.Set;
import org.jasig.portlet.contacts.model.Contact;
import org.jasig.portlet.contacts.model.ContactSet;
import org.jasig.portlet.contacts.adapters.PushAdapter;

/**
 *
 * @author mfgsscw2
 */
public class PushSearchAdapter extends AbstractSearchAdapter {

    @Override
    protected String[] requiredAttributes() {
        return new String[0];
    }

    public ContactSet search(String searchText, Boolean isGuestUser) {
        String searchTextLower = searchText.toLowerCase().trim();
        ContactSet contacts = new ContactSet();
        
        Set<ContactSet> pushContacts = pushAdapter.getContacts();
        
        for (ContactSet set : pushContacts) {
            for (Contact contact : set) {
                
                if (contact.getFirstname().toLowerCase().contains(searchTextLower) ||
                        contact.getSurname().toLowerCase().contains(searchTextLower))
                    contacts.add(contact);
            }
        }
  
        
        return contacts;
        
    }
    
    public ContactSet search(String searchText, String filter, Boolean isGuestUser) {
        
        return search(searchText, isGuestUser);
        
    }
    
    
    private PushAdapter pushAdapter;
    public void setPushAdapter(PushAdapter adapter) {
        pushAdapter = adapter;
    }

    @Override
    public Contact getByURN(String URN, Boolean isGuestUser) {
        
        return pushAdapter.getByURN(URN);
        
    }

	@Override
	public String getGuestFilterAttribute() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setGuestFilterAttribute(String guestFilterAttributeName) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getPublicContactsOnlyValue() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setPublicContactsOnlyValue(String publicContactsOnlyValue) {
		// TODO Auto-generated method stub
		
	}

}
