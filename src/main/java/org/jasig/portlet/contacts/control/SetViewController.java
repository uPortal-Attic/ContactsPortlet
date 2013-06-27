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

import java.io.IOException;
import java.util.Set;
import javax.portlet.PortletSession;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jasig.portlet.contacts.IViewSelector;
import org.jasig.portlet.contacts.domains.ContactDomain;
import org.jasig.portlet.contacts.model.ContactSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.portlet.bind.annotation.ResourceMapping;
import org.springframework.web.util.UriUtils;

/**
 *
 * @author Chris White <christopher.white@manchester.ac.uk>
 */
@Controller
@RequestMapping("VIEW")
public class SetViewController {

    private static Log log = LogFactory.getLog(SetViewController.class);

    

    @ModelAttribute("nspace")
    public String setNspace(
            @RequestParam("nspace") String nspace) {
        return nspace;
    }

    @ModelAttribute("domain")
    public ContactDomain getDomain(
            @RequestParam("domain") String domain) {
        for (ContactDomain domainObj : contactDomains) {
            if (domainObj.getId().equals(domain)) {
                return domainObj;
            }
        }

        return null;
    }


    @ResourceMapping("setView")
    public String showDomain(
            ResourceRequest request,
            ResourceResponse response,
            @RequestParam("set") String setId,
            @ModelAttribute("domain") ContactDomain domainObj,
            Model model,
            PortletSession session) throws IOException {

        String setIdDecode = UriUtils.decode(setId, "UTF-8");
        ContactSet contacts = domainObj.getContacts(setIdDecode);
        model.addAttribute("contactList", contacts);

        if (domainObj.getHasPersist()) {
            model.addAttribute("persist", true);
        }
        log.debug(contacts.size() + " CONTACTS found for " + contacts.getTitle());
        log.debug("Contacts set for domain :: " + domainObj.getName());

        model.addAttribute("domain", domainObj);
        model.addAttribute("source", setId);

         if (viewSelector.isMobile(request))
            return "setView-jQM";
        else
            return "setView";

    }

    
    @ResourceMapping("search")
    public String searchDomain(
            ResourceRequest request,
            ResourceResponse response,
            PortletSession session,
            @RequestParam("term") String term,
            @RequestParam("filter") String filter,
            @ModelAttribute("domain") ContactDomain domainObj,
            Model model) throws IOException {


        ContactSet contacts = new ContactSet();
        if (!term.trim().equals("")) {


            model.addAttribute("domain", domainObj);
            model.addAttribute("source", "search");
            
            String username = request.getRemoteUser();
    		Boolean isGuestUser = true;
    		if(username != null){
    			isGuestUser = false;
    		}
    		model.addAttribute("isGuestUser", isGuestUser);
            
            contacts.addAll(domainObj.search(term, filter, isGuestUser));

            log.debug(contacts.size() + " CONTACTS found for " + contacts.getTitle());
            log.debug("Contacts set for domain :: " + domainObj.getName());
        }

        
        model.addAttribute("contactList", contacts);

        
        if (viewSelector.isMobile(request))
            return "setView-jQM";
        else
            return "setView";

    }
    private Set<ContactDomain> contactDomains;

    @Autowired
    public void setContactDomains(Set<ContactDomain> domains) {
        contactDomains = domains;
    }
    
        private IViewSelector viewSelector;
    
    @Autowired(required = true)
    public void setViewSelector(IViewSelector viewSelector) {
        this.viewSelector = viewSelector;
        
    }

}
