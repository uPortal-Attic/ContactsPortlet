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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletPreferences;
import javax.portlet.ReadOnlyException;
import javax.portlet.RenderRequest;
import javax.portlet.ValidatorException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jasig.portlet.contacts.control.util.DomainMap;
import org.jasig.portlet.contacts.domains.ContactDomain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.portlet.bind.annotation.ActionMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;

/**
 *
 * @author Chris White <christopher.white@manchester.ac.uk>
 */
@Controller
@RequestMapping("CONFIG")
public class ConfigController {
    
    private static Log log = LogFactory.getLog(ConfigController.class);
    
    @ModelAttribute("domainsActive")
    public DomainMap getActiveDomains(PortletPreferences prefs) {
        final List<String> domainActive = Arrays.asList(prefs.getValues("domainsActive", new String[0]));
        Map<String, Boolean> domainsMap = new TreeMap<String, Boolean>();
        for (ContactDomain domain : contactDomains) {
            if (domainActive.contains(domain.getName())) {
                domainsMap.put(domain.getName(), true);
            } else {
                domainsMap.put(domain.getName(), false);
            }
        }
        
        return new DomainMap(domainsMap);
    }
    
    @ModelAttribute("domainsDefault")
    public DomainMap getDefaultDomains(PortletPreferences prefs) {
        
        final List<String> domainActive = Arrays.asList(prefs.getValues("domainsActive", new String[0]));
        final List<String> defaultOn = Arrays.asList(prefs.getValues("defaultOn", new String[0]));
        
        Map<String, Boolean> domainsMap = new TreeMap<String, Boolean>();
        for (String domain : domainActive) {
            if (defaultOn.contains(domain)) {
                domainsMap.put(domain, true);
            } else {
                domainsMap.put(domain, false);
            }
        }
        
        return new DomainMap(domainsMap);
    }
    
    @ActionMapping(params="action=active")
    public void setActiveDomains(
            ActionRequest request,
            ActionResponse response,
            PortletPreferences prefs,
            @ModelAttribute("domainsActive") DomainMap domains,
            BindingResult result
    ) throws ReadOnlyException, IOException, ValidatorException{
        log.debug("activate");
        
        List domainActive = new ArrayList();
        
        for (Entry<String, Boolean> entry : domains.getMap().entrySet()) {
            if (entry.getValue()) {
                domainActive.add(entry.getKey());
            }
        }
        
        prefs.setValues("domainsActive", (String[]) domainActive.toArray(new String[0]));
        prefs.store();
        response.setRenderParameter("activeSaved", "true");
    }

    @ActionMapping(params="action=default")
    public void setDefaultDomains(
            ActionRequest request,
            ActionResponse response,
            PortletPreferences prefs,
            @ModelAttribute("domainsDefault") DomainMap domains,
            BindingResult result
    ) throws ReadOnlyException, IOException, ValidatorException{
        log.debug("default");
        List defaultOn = new ArrayList();
        
        for (Entry<String, Boolean> entry : domains.getMap().entrySet()) {
            if (entry.getValue()) {
                defaultOn.add(entry.getKey());
            }
        }
        
        prefs.setValues("defaultOn", (String[]) defaultOn.toArray(new String[0]));
        prefs.store();
        response.setRenderParameter("defaultSaved", "true");
    }

    @RenderMapping
    public String defaultView(
            Model model,
            RenderRequest request
    ) {
        log.debug("render");
        model.addAttribute("defaultSaved", request.getParameter("defaultSaved") != null);
        model.addAttribute("activeSaved", request.getParameter("activeSaved") != null);
        
        return "ConfigView";
        
    }
    
    private Set<ContactDomain> contactDomains;
    @Autowired
    public void setContactDomains(Set<ContactDomain> domains) {
        contactDomains = domains;  
        
    }
    
}
