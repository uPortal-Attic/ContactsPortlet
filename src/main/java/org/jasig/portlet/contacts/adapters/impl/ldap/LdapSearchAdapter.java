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

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.naming.directory.SearchControls;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jasig.portlet.contacts.adapters.impl.AbstractSearchAdapter;
import org.jasig.portlet.contacts.model.Contact;
import org.jasig.portlet.contacts.model.ContactSet;
import org.jasig.portlet.contacts.model.ModelObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.DistinguishedName;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.filter.AndFilter;
import org.springframework.ldap.filter.CompareFilter;
import org.springframework.ldap.filter.EqualsFilter;
import org.springframework.ldap.filter.OrFilter;
import org.springframework.ldap.filter.WhitespaceWildcardsFilter;
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

    private Set<CompareFilter> ldapSearchFilter = new HashSet<CompareFilter>();
    public void setLdapSearchFilters(Set<CompareFilter> filter) {
        this.ldapSearchFilter = filter;
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

    public void setFilterAttribute(String filterAttribute) {
        this.filterAttribute = filterAttribute;
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

        List<Contact> contactList = getSearchResults(searchString);

        ContactSet contactSet = new ContactSet();
        contactSet.setId(searchText+":"+filter);

        contactSet.setTitle("Search Results");
        for (Contact contact : contactList) {
            contact.setContactSource("search:"+searchText+":"+filter);
            contactSet.add(contact);
        }

        return contactSet;
    }

    protected String constructSearch(String searchValue, String searchFilter) {
        AndFilter andFilter = new AndFilter();
        andFilter.and(new EqualsFilter("objectclass", "person"));
        andFilter.and(new WhitespaceWildcardsFilter(searchAttribute, searchValue));
        
        for (CompareFilter filter : ldapSearchFilter) {
            andFilter.and(filter);
        }
        
        
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

    protected List<Contact> getSearchResults(String search) {
        SearchControls searchControls = getSearchControls();
        logger.debug("Searching LDAP with search: "+search);
        List<Contact> contactList = ldapTemplate.search(DistinguishedName.EMPTY_PATH, search, searchControls, contactMapper);
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

    private AttributesMapper contactMapper;
    public void setAttributesMapper(AttributesMapper contactMapper) {
        this.contactMapper = contactMapper;
    }

}
