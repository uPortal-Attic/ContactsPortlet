package org.jasig.portlet.contacts.control;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.portlet.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jasig.web.service.AjaxPortletSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.jasig.portlet.contacts.model.Contact;
import org.jasig.portlet.contacts.model.ContactSet;
import org.jasig.portlet.contacts.domains.ContactDomain;
import org.springframework.web.portlet.bind.annotation.ResourceMapping;

/**
 *
 * @author Chris White <christopher.white@manchester.ac.uk>
 */
@Controller
@RequestMapping("VIEW")
public class SetViewController {

    private static Log log = LogFactory.getLog(SetViewController.class);

    @RequestMapping()
    public String noDomain() {
        return "noDomain";
    }

    @ModelAttribute("view")
    public String setView() {
        return "setView";
    }
    
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

    private String setupUrls(String source, ContactDomain domain, Contact contact, String url) throws IOException {

        String action = "" + url;
        action = action.replace(URLEncoder.encode("||DOMAIN||", "utf8"), URLEncoder.encode(domain.getId(), "utf8"));
        action = action.replace(URLEncoder.encode("||CONTACT||", "utf8"), URLEncoder.encode(contact.getURN(), "utf8"));
        action = action.replace(URLEncoder.encode("||SOURCE||", "utf8"), URLEncoder.encode(source, "utf8"));

        return action;
    }

    @ResourceMapping("showDomain")
    public String showDomain(
            ResourceRequest request,
            ResourceResponse response,
            @RequestParam("set") String setId,
            @ModelAttribute("domain") ContactDomain domainObj,
            Model model,
            PortletSession session) throws IOException {


        ContactSet contacts = domainObj.getContacts(setId);
        model.addAttribute("contactList", contacts);

        if (domainObj.getHasPersist()) {
            model.addAttribute("persist", true);
        }
        log.debug(contacts.size() + " CONTACTS found for " + contacts.getTitle());
        log.debug("Contacts set for domain :: " + domainObj.getName());
        
        model.addAttribute("source", setId);

/*
        if (domainObj.getHasPersist()) {
            Map<String, String> persistURLs = new HashMap<String, String>();
            String action = (String) session.getAttribute("PERSISTACTIONURL");
            for (Contact contact : contacts) {
                persistURLs.put(contact.getURN(), setupUrls(setId, domainObj, contact, action));
            }
            model.addAttribute("persistURL", persistURLs);
        }
        if (domainObj.getHasRemove()) {
            Map<String, String> deleteURLs = new HashMap<String, String>();
            String action = "" + (String) session.getAttribute("DELETEACTIONURL");
            for (Contact contact : contacts) {
                deleteURLs.put(contact.getURN(), setupUrls(setId, domainObj, contact, action));
            }
            model.addAttribute("deleteURL", deleteURLs);
        }
*/

        return model.asMap().get("view").toString();

    }

    @ResourceMapping("searchDomain")
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
            
            contacts.addAll(domainObj.search(term, filter));
/*
            if (domainObj.getHasPersist()) {
                Map<String, String> persistURLs = new HashMap<String, String>();
                String action = (String) session.getAttribute("PERSISTACTIONURL");
                for (Contact contact : contacts) {
                    persistURLs.put(contact.getURN(), setupUrls("search", domainObj, contact, action));
                }
                model.addAttribute("persistURL", persistURLs);
            }
            if (domainObj.getHasRemove()) {
                Map<String, String> deleteURLs = new HashMap<String, String>();
                String action = "" + (String) session.getAttribute("DELETEACTIONURL");
                for (Contact contact : contacts) {
                    deleteURLs.put(contact.getURN(), setupUrls("search", domainObj, contact, action));
                }
                model.addAttribute("deleteURL", deleteURLs);
            }
*/
            
            log.debug(contacts.size() + " CONTACTS found for " + contacts.getTitle());
            log.debug("Contacts set for domain :: " + domainObj.getName());
        }

        model.addAttribute("contactList", contacts);

        return model.asMap().get("view").toString();

    }
    private Set<ContactDomain> contactDomains;

    @Autowired
    public void setContactDomains(Set<ContactDomain> domains) {
        contactDomains = domains;
    }
    protected AjaxPortletSupport ajaxPortletSupportService;

    @Autowired
    public void setPortletSupport(AjaxPortletSupport support) {
        ajaxPortletSupportService = support;
    }
}
