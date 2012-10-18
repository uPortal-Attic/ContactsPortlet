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

package org.jasig.portlet.contacts.adapters.impl.ldap;

import java.util.List;
import java.util.Map;
import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchControls;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.DistinguishedName;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.filter.AndFilter;
import org.springframework.ldap.filter.EqualsFilter;
import org.springframework.ldap.filter.OrFilter;
import org.springframework.ldap.filter.WhitespaceWildcardsFilter;
import org.jasig.portlet.contacts.model.Contact;
import org.jasig.portlet.contacts.model.ContactSet;
import org.jasig.portlet.contacts.model.ModelObjectFactory;
import org.jasig.portlet.contacts.model.util.ContactMapper;
import org.jasig.portlet.contacts.adapters.impl.AbstractSearchAdapter;
import org.springframework.util.StringUtils;

/**
 *
 * @author Chris White <christopher.white@manchester.ac.uk>
 */
public class LdapSearchAdapter extends AbstractSearchAdapter {

    
    private static Log logger = LogFactory.getLog(LdapSearchAdapter.class);
    
    private int timeLimit = 1000;
    private int countLimit = 0;
    private LdapTemplate ldapTemplate;
    
    private String searchAttribute = "cn";
    private String filterAttribute = "employeeType";    
    
    public LdapSearchAdapter (LdapTemplate ldapTemplate) {
        this.ldapTemplate = ldapTemplate;
    }
    
    public void setTimeLimit(int timeLimit) {
        this.timeLimit = timeLimit;
    }
    
    public void setCountLimit(int countLimit) {
        this.countLimit = countLimit;
    }
    
    public void setSearchAttribute(String searchAttribute) {
        this.searchAttribute = searchAttribute;
    }
  
    
    public Contact getByURN(String urn) {
        String[] attr = StringUtils.delimitedListToStringArray(urn, ":");
        
        String searchText = attr[2];
        String filter = attr[3];
        String id = attr[4];
        
        ContactSet contacts = search(searchText, filter);
        
        for (Contact contact : contacts) {
            if (contact.getId().equals(id))
                return contact;
        }
        
        return null;
        
    }
    
    public ContactSet search(String searchText) {
        return search(searchText, null);
    }
    
    public ContactSet search(String searchText, String filter) {
        
        String searchString = constructSearch(searchText, filter);
        
        if (filter == null) 
            filter = "";
        
        List<Attributes> contactList = getSearchResults(searchString);
        
        ContactSet contactSet = new ContactSet();
        contactSet.setId(searchText+":"+filter);
        
        contactSet.setTitle("Search Results");
        for (Attributes attrs : contactList) {
            Contact contact = modelFactory.getObjectOfType(Contact.class);
            mapper.mapToContact(attrs, contact);
            contact.setContactSource("search:"+searchText+":"+filter);
            contactSet.add(contact);
        }
        
        return contactSet;
    }
  
    protected String constructSearch(String searchValue, String searchFilter) {
        AndFilter andFilter = new AndFilter();
        andFilter.and(new EqualsFilter("objectclass", "person"));
        andFilter.and(new WhitespaceWildcardsFilter(searchAttribute, searchValue));
logger.debug("SEARCH CONSTRUCT :: "+searchValue+" :: "+searchFilter);
        if(filters != null && searchFilter != null) {            
            List<String> filter = (List<String>) filters.get(searchFilter);
logger.debug("FILTERS");
            if(filter != null && filter.size() != 0) {
                logger.debug("Constructing "+searchFilter+" search");
                OrFilter orFilter = new OrFilter();
                for(String filterValue : filter) {
                    orFilter.or(new EqualsFilter(filterAttribute, filterValue));
                }
                andFilter.and(orFilter);
            }         
        }        
        return andFilter.toString();
    }
    
    protected List<Attributes> getSearchResults(String search) {        
        SearchControls searchControls = getSearchControls();   
        logger.debug("Searching LDAP with search: "+search);
        //List<Contact> contactList = ldapTemplate.search(DistinguishedName.EMPTY_PATH, search, searchControls, attributesMapper);
        List<Attributes> contactList = ldapTemplate.search(DistinguishedName.EMPTY_PATH, search, searchControls, new AttributesMapper() {

            @Override
            public Object mapFromAttributes(Attributes attributes) throws NamingException {
                return attributes;
            }
        });        
        return contactList;
    }      

    /**
     * Construct a new search controls object for our search
     */
    protected SearchControls getSearchControls() {
        SearchControls searchControls = new SearchControls();
        searchControls.setTimeLimit(timeLimit);
        searchControls.setCountLimit(countLimit);
        searchControls.setSearchScope(SearchControls.SUBTREE_SCOPE);
        return searchControls;
    }
    
    @Override
    protected String[] requiredAttributes() {
        return new String[0];
    }
    
        private ModelObjectFactory modelFactory;
    @Autowired
    public void setModelObjectFactory(ModelObjectFactory factory){
        modelFactory = factory;
    }
    
    
    private ContactMapper mapper;
    public void setContactMapper(ContactMapper mapper) {
        this.mapper = mapper;
    }
       
}
