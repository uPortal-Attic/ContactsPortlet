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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.portlet.bind.annotation.ResourceMapping;

/**
 *
 * @author mfgsscw2
 */
@Controller
@RequestMapping("VIEW")
public class SaveContactController {
    
    
    private static Log log = LogFactory.getLog(SaveContactController.class);
    
    @ResourceMapping("persist")
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
        
        boolean saved = false;
        
        for (ContactDomain domainObj : contactDomains)
            if (domainObj.getId().equals(domain)) {
                log.debug("Found domain :: "+domainObj.getId()+" == "+domain);
                Contact toSave = null;
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
                        toSave = contactObj;
                        //break;
                    }
                }
                
                if (toSave != null) {
                    saved = domainObj.save(toSave);
                }
                    
                break;
            }
        
        model.put("STATUS", "OK");
        log.debug("SAVED :: "+saved);
        model.put("saved", saved);
        
        log.debug("PERSIST --  END");
        
        return "JSONView";
        
    }
    
    private Set<ContactDomain> contactDomains;
    @Autowired
    public void setContactDomains(Set<ContactDomain> domains) {
        contactDomains = domains;  
    }
}
