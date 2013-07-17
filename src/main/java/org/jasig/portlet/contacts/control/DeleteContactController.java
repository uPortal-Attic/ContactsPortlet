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
import java.util.Map;
import java.util.Set;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jasig.portlet.contacts.domains.ContactDomain;
import org.jasig.portlet.contacts.model.Contact;
import org.jasig.portlet.contacts.model.ContactSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.portlet.bind.annotation.ResourceMapping;

/**
 *
 * @author Chris White <christopher.white@manchester.ac.uk>
 */
@Controller
@RequestMapping("VIEW")
public class DeleteContactController {
    
    
    private static Log log = LogFactory.getLog(DeleteContactController.class);
    
    @ResourceMapping("delete")
    public String persist(
            ResourceRequest request,
            ResourceResponse response,
            @RequestParam("domain") String domain,
            @RequestParam("source") String source,
            @RequestParam("contact") String contact,
            Map<String,Object> model
    ) throws IOException {
        
        log.debug("PERSIST --  START");
        
        log.debug("DOMAIN :: "+domain);
        log.debug("SOURCE :: "+source);
        log.debug("CONTACT :: "+contact);
        
        boolean removed = false;
        
        for (ContactDomain domainObj : contactDomains)
            if (domainObj.getId().equals(domain)) {
                log.debug("Found domain :: "+domainObj.getId()+" == "+domain);
                Contact toRemove = null;
                ContactSet contacts = new ContactSet();
                if (source.equalsIgnoreCase("search")) {
                
                    String[] tokens = contact.split(":");
                    //StringTokenizer tokens = new StringTokenizer(contact, ":");
                    String search = tokens[2];
                    String filter = tokens[3];
                    contacts.addAll(domainObj.search(search,filter));

                } else if (source.startsWith("urn:")) {
                    contacts.add(domainObj.getContact(source));
                } else {
                    contacts.addAll(domainObj.getContacts(source));                    
                }
                
                log.debug(contacts.size() + " CONTACTS to search through");
                for (Contact contactObj : contacts) {
                    log.debug("CONTACT :: "+contact+ " == "+contactObj.getURN()+" :: "+(contact.equalsIgnoreCase(contactObj.getURN())));
                    if (contact.equalsIgnoreCase(contactObj.getURN())) {
                        toRemove = contactObj;
                        //break;
                    }
                }
                
                if (toRemove != null) {
                    removed = domainObj.delete(toRemove);
                }
                    
                break;
            }
        
        model.put("STATUS", "OK");
        log.debug("DELETE :: "+removed);
        model.put("deleted", removed);
        
        log.debug("PERSIST --  END");
        
        return "JSONView";
        
    }
    
    private Set<ContactDomain> contactDomains;
    @Autowired
    public void setContactDomains(Set<ContactDomain> domains) {
        contactDomains = domains;  
    }
}
