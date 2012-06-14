package org.jasig.portlet.contacts.control;

import java.util.*;
import javax.portlet.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jasig.portlet.contacts.domains.ContactDomain;
import org.springframework.beans.factory.annotation.Autowired;
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

    private String version;
    public void setVersionString(String v) {
        version = v;
    }
    
    @ModelAttribute("domains")
    public Set<ContactDomain> getDomains(PortletPreferences prefs) {
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
            Model model) {

/*
        PortletURL persistAction = response.createActionURL();
        persistAction.setParameter("domain", "||DOMAIN||");
        persistAction.setParameter("contact", "||CONTACT||");
        persistAction.setParameter("source", "||SOURCE||");
        persistAction.setParameter("persist", "true");
        session.setAttribute("PERSISTACTIONURL", persistAction.toString() );

        
        PortletURL deleteAction = response.createActionURL();
        deleteAction.setParameter("domain", "||DOMAIN||");
        deleteAction.setParameter("contact", "||CONTACT||");
        deleteAction.setParameter("source", "||SOURCE||");
        deleteAction.setParameter("delete", "true");
        session.setAttribute("DELETEACTIONURL", deleteAction.toString() );
*/     
        
	return "defaultView";

    }
    
    private Set<ContactDomain> contactDomains;
    @Autowired
    public void setContactDomains(Set<ContactDomain> domains) {
        contactDomains = domains;  
    }
    
    
}

