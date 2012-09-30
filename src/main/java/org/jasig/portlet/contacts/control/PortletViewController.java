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

import java.util.*;
import javax.portlet.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jasig.portlet.contacts.IViewSelector;
import org.jasig.portlet.contacts.domains.ContactDomain;
import org.jasig.portlet.contacts.model.Contact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;


/**
 *
 * @author Chris White <christopher.white@manchester.ac.uk>
 */
@Controller
@RequestMapping("VIEW")
public class PortletViewController {

    private static Log log = LogFactory.getLog(PortletViewController.class);

    @Value("${appversion}")
    private String version;
    public void setVersionString(String v) {
        version = v;
    }
    
    @ModelAttribute("domains")
    public Set<ContactDomain> getDomains(PortletPreferences prefs) {
        log.debug("finding Domains to return");
        final List<String> domainActive = Arrays.asList(prefs.getValues("domainsActive", new String[0]));
        
        String[] defaultOn = prefs.getValues("defaultOn", new String[0]);
        String[] userOn = prefs.getValues("domainOn", new String[0]);
        String[] userOff = prefs.getValues("domainOff", new String[0]);
        
        Set<String> domains = new HashSet<String>();
        domains.addAll(Arrays.asList(defaultOn));
        domains.addAll(Arrays.asList(userOn));
        domains.removeAll(Arrays.asList(userOff));
        domains.retainAll(domainActive);
        
        Set<ContactDomain> activeDomains = new TreeSet<ContactDomain>(new Comparator<ContactDomain>() {

            @Override
            public int compare(ContactDomain o1, ContactDomain o2) {
                int index1 = domainActive.indexOf(o1.getName());
                int index2 = domainActive.indexOf(o2.getName());
                return index1 - index2;
            }
        });
        for (ContactDomain domain : contactDomains) {
            if (domains.contains(domain.getName())) {
                activeDomains.add(domain);
            }
        }
        log.debug("returning "+activeDomains.size()+ "domains");
        return activeDomains;
    }
    
    @ModelAttribute("appversion")
    public String getAppversion() {
        return version;
    }

    @RequestMapping
    public String defaultView(
            PortletRequest req,
            RenderResponse response,
            PortletSession session,
            @ModelAttribute("domains") Set<ContactDomain> domains,
            Model model) {

      
        String urn = req.getParameter("urn");
        String domain = req.getParameter("domain");
        
        if (domain != null && urn != null) {
            Contact contact = null;
            model.addAttribute("activeDomain", domain);
            for (ContactDomain dom : domains) {
                if (dom.getId().equals(domain)) {
                    contact = dom.getContact(urn);
                    break;
                }
            }
            if (contact != null)
                model.addAttribute("selectedContact", contact);
        }
        
        if (urn != null)
            log.debug(urn);
        
	return "defaultView";

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

