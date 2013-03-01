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
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.PortletModeException;
import javax.portlet.PortletPreferences;
import javax.portlet.RenderRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jasig.portlet.contacts.control.util.DomainMap;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("EDIT")
public class PortletEditController {

    private static Log log = LogFactory.getLog(PortletEditController.class);

    
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

        Map<String, Boolean> domainsMap = new TreeMap<String, Boolean>();
        for (String domain : domainActive) {
            domainsMap.put(domain, domains.contains(domain));
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
            
            response.setRenderParameter("saveStatus", "success");
            
        } catch (Exception ex) {
            log.error("Failed to save user Prefs", ex);
        }
       // response.setPortletMode(PortletMode.VIEW);
    }

    @RequestMapping
    public String showEditView(RenderRequest request, Model model) {

        model.addAttribute("saved", request.getParameter("saveStatus") != null);
        
        return "edit";
    }
    
}
