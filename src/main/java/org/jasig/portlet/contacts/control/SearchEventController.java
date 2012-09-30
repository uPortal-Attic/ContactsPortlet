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

package org.jasig.portlet.contacts.control;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.portlet.Event;
import javax.portlet.EventRequest;
import javax.portlet.EventResponse;
import javax.portlet.WindowState;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jasig.portal.search.*;
import org.jasig.portlet.contacts.domains.ContactDomain;
import org.jasig.portlet.contacts.model.Contact;
import org.jasig.portlet.contacts.model.ContactSet;
import org.springframework.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.bind.annotation.EventMapping;

/**
 *
 * @author Chris White <christopher.white@manchester.ac.uk>
 */
@Controller
@RequestMapping("VIEW")
public class SearchEventController {
    
    private static Log log = LogFactory.getLog(SearchEventController.class);
    
    @EventMapping(SearchConstants.SEARCH_REQUEST_QNAME_STRING)
    public void handleSearchEvents(
            EventRequest request, 
            EventResponse response
    ) {
        
        log.debug("Responding to Search Event");
    
        final Event event = request.getEvent();
        final SearchRequest searchQuery = (SearchRequest)event.getValue();
        
        final String searchTerms = searchQuery.getSearchTerms();
        
        final SearchResults searchResults = new SearchResults();
        
        searchResults.setQueryId(searchQuery.getQueryId());
        searchResults.setWindowId(request.getWindowID());

        for (ContactDomain domain: contactDomains) {
            
            if (domain.getHasSearch()) {
                
                ContactSet contacts = domain.search(searchTerms);
        
                for (Contact contact: contacts) {
                    
                    //Build the result object for the match
                    final SearchResult searchResult = new SearchResult();
                    String title = contact.getSurname().toUpperCase() + ", " + contact.getTitle() + " "+contact.getFirstname();
                    if (contact.getPosition() != null && !contact.getPosition().equals(""))
                        title += " ("+contact.getPosition()+")";
                    
                    List<String> summary = new ArrayList<String>();;
                    
                    if (contact.getPrimaryEmailAddress() != null)
                        summary.add("E:"+contact.getPrimaryEmailAddress().getEmailAddress());
                    if (contact.getPrimaryPhoneNumber() != null)
                        summary.add("T:"+contact.getPrimaryPhoneNumber().getPhoneNumber());
                    
                    String summaryText = StringUtils.collectionToDelimitedString(summary, " -- ");
                            
                    searchResult.setTitle(title);
                    searchResult.setSummary(summaryText);
                    
                    /*
                     * Portlet URL to be added when / if portlet support for 
                     * deep linking added.
                     */
                    PortletUrl url = new PortletUrl();
                    url.setPortletMode("VIEW");
                    url.setWindowState(WindowState.MAXIMIZED.toString());
                    PortletUrlParameter domainParam = new PortletUrlParameter();
                    domainParam.setName("domain");
                    domainParam.getValue().add(domain.getId());
                    url.getParam().add(domainParam);
                    PortletUrlParameter urnParam = new PortletUrlParameter();
                    urnParam.setName("urn");
                    urnParam.getValue().add(contact.getURN());
                    url.getParam().add(urnParam);
                    
                    searchResult.setPortletUrl(url);

                    searchResult.getType().add("contact");
                    //Add the result to the results and send the event
                    searchResults.getSearchResult().add(searchResult);       
                }
                
            }
        }
        
        response.setEvent(SearchConstants.SEARCH_RESULTS_QNAME, searchResults);
        
        log.debug("Finished response -- "+searchResults.getSearchResult().size()+" -- results returned");
        
    }
    
    private Set<ContactDomain> contactDomains;

    @Autowired
    public void setContactDomains(Set<ContactDomain> domains) {
        contactDomains = domains;
    }
    
}
