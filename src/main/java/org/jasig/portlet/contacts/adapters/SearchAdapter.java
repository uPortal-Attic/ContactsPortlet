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
package org.jasig.portlet.contacts.adapters;



import java.util.Map;
import org.jasig.portlet.contacts.model.Contact;
import org.jasig.portlet.contacts.model.ContactSet;



/**
 *
 * @author Chris White <christopher.white@manchester.ac.uk>
 */
public interface SearchAdapter extends ContactAdapter {

    
    public ContactSet search(String searchText, Boolean isGuestUser);
    public ContactSet search(String searchText, String filter, Boolean isGuestUser);
    public Contact getByURN(String URN, Boolean isGuestUser);
    public void setFilters(Map<String,Object> filers);
    public Map<String,Object> getFilters();
    public void setFilterAttribute(String attribute);
    public String getFilterAttribute();
    
    /**
     * Testing
     */
    public String getGuestFilterAttribute();
    public void setGuestFilterAttribute(String guestFilterAttributeName);
    public String getPublicContactsOnlyValue();
    public void setPublicContactsOnlyValue(String publicContactsOnlyValue);
}
