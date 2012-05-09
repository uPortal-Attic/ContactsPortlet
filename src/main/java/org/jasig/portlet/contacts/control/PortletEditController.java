package org.jasig.portlet.contacts.control;

import java.io.IOException;
import java.util.*;
import javax.portlet.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jasig.portlet.contacts.control.util.DomainMap;
import org.jasig.portlet.contacts.domains.ContactDomain;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("EDIT")
public class PortletEditController implements ApplicationContextAware {

    private static Log log = LogFactory.getLog(PortletEditController.class);
    private ApplicationContext appContext;

    
    @ModelAttribute("domains")
    public DomainMap getDomains(PortletPreferences prefs) {
        
        final List<String> domainActive = Arrays.asList(prefs.getValues("domainsActive", new String[0]));
        
        String[] defaultOn = prefs.getValues("defaultOn", new String[0]);
        String[] userOn = prefs.getValues("domainOn", new String[0]);
        String[] userOff = prefs.getValues("domainOff", new String[0]);
        
        Set<String> domains = new HashSet<String>();
        domains.addAll(Arrays.asList(defaultOn));
        
        domains.addAll(Arrays.asList(userOn));
        domains.removeAll(Arrays.asList(userOff));
        domains.retainAll(domainActive);
       
        
        Map<String, Boolean> domainsMap = new TreeMap<String, Boolean>();
        
        for (ContactDomain domain : contactDomains) {
            if (domains.contains(domain.getName())) {
                domainsMap.put(domain.getName(), true);
            } else
                domainsMap.put(domain.getName(), false);
        }
        
        return new DomainMap(domainsMap);
    }

    @RequestMapping(method = RequestMethod.POST)
    public void changeConfig(
            @ModelAttribute("domains") DomainMap domainMap,
            BindingResult result,
            ActionRequest request,
            ActionResponse response) throws PortletModeException, IOException {

        PortletPreferences prefs = request.getPreferences();
        List domainOff = new ArrayList();
        List domainOn = new ArrayList();
        for (String search : domainMap.getMap().keySet()) {
            if (domainMap.getMap().get(search)) {
                domainOn.add(search);
            } else {
                domainOff.add(search);
            }
        }
        try {
            prefs.setValues("domainOn", (String[]) domainOn.toArray(new String[0]));
            prefs.setValues("domainOff", (String[]) domainOff.toArray(new String[0]));
            prefs.store();
        } catch (Exception ex) {
            log.error("Failed to save user Prefs", ex);
        }
        response.setPortletMode(PortletMode.VIEW);
    }

    @RequestMapping(method = RequestMethod.GET)
    public String showEditView() {

        return "edit";
    }
    
    private Set<ContactDomain> contactDomains;
    @Autowired
    public void setContactDomains(Set<ContactDomain> domains) {
        contactDomains = domains;  
    }

    @Autowired
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        appContext = applicationContext;
    }
}
