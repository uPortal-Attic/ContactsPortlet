package org.jasig.portlet.contacts.control;

import java.util.*;
import javax.portlet.ResourceRequest;
import javax.portlet.ResourceResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jasig.portlet.contacts.adapters.SearchAdapter;
import org.jasig.portlet.contacts.domains.ContactDomain;
import org.jasig.portlet.contacts.model.Contact;
import org.jasig.portlet.contacts.model.ContactSet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.portlet.bind.annotation.ResourceMapping;

@Controller
@RequestMapping("VIEW")
public class AutoCompleteController {

    private static Log log = LogFactory.getLog(AutoCompleteController.class);

    @ResourceMapping("autoComplete")
    public String showAutoComplete(
            ResourceRequest request, 
            ResourceResponse response, 
            @RequestParam(value = "filter", required = false) String filter,
            @RequestParam("domain") String domainId,
            Model model,
            @RequestParam("term") String query
    ) {

        for (ContactDomain domain : contactDomains) 
            if (domain.getHasSearch() && domain.getId().equals(domainId)) {


                log.debug("filter is " + filter);

                log.debug("q is " + query);

                //ContactSet contacts = searchAdapter.search(query, filter);
                ContactSet contacts = domain.search(query, filter);

                Map<String, Integer> countedResults = getCountedSearchResults(contacts);

                if (countedResults.size() > 0) {
                    List<Map<String,String>> data = new ArrayList<Map<String,String>>();
                    for (String key : countedResults.keySet()) {

                        Map<String,String> entry = new HashMap<String,String>();
                        if (countedResults.get(key) > 1)
                            entry.put("label", "<span>" + countedResults.get(key) + " results</span>" + key);
                        else
                            entry.put("label", key);
                        entry.put("value", key);
                        data.add(entry);
                    }
                    model.addAttribute("data",data.toArray());
                }
            }
        return "JSONView";
    }

    private Map<String, Integer> getCountedSearchResults(ContactSet results) {
        Map<String, Integer> countedResults = new LinkedHashMap<String, Integer>();

        for (Contact contact : results) {
            String value = contact.getFirstname() + " " + contact.getSurname();
            Integer count = countedResults.get(value);
            if (count == null) {
                count = 0;
            }
            countedResults.put(value, ++count);
        }
        return countedResults;
    }
    private MessageSource messageSource;

    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }
    
    private Set<ContactDomain> contactDomains;
    @Autowired
    public void setContactDomains(Set<ContactDomain> domains) {
        contactDomains = domains;  
    }
}
