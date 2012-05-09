package org.jasig.portlet.contacts.control;

import java.io.IOException;
import java.util.Set;
import java.util.StringTokenizer;
import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jasig.web.service.AjaxPortletSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.jasig.portlet.contacts.model.Contact;
import org.jasig.portlet.contacts.model.ContactSet;
import org.jasig.portlet.contacts.domains.ContactDomain;

/**
 *
 * @author mfgsscw2
 */
@Controller
@RequestMapping("VIEW")
public class DeleteContactController {
    
    
    private static Log log = LogFactory.getLog(DeleteContactController.class);
    
    @RequestMapping(params="delete=true")
    public void persist(
            ActionRequest request,
            ActionResponse response,
            @RequestParam("domain") String domain,
            @RequestParam("source") String source,
            @RequestParam("contact") String contact,
            Model model
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
        
        model.addAttribute("STATUS", "OK");
        log.debug("DELETE :: "+removed);
        model.addAttribute("deleted", removed);
        model.addAttribute("view", "JSONView");
        
        log.debug("PERSIST --  END");
        
        ajaxPortletSupportService.redirectAjaxResponse("ajax/showView", model.asMap(), request, response);
        
    }
    
    protected AjaxPortletSupport ajaxPortletSupportService;

    @Autowired
    public void setPortletSupport(AjaxPortletSupport support) {
        ajaxPortletSupportService = support;
    }
    
    private Set<ContactDomain> contactDomains;
    @Autowired
    public void setContactDomains(Set<ContactDomain> domains) {
        contactDomains = domains;  
    }
}
