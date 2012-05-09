/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jasig.portlet.contacts.control.ajax;

import java.io.IOException;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jasig.web.service.AjaxPortletSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author Chris White <chrisopher.white@manchester.ac.uk>
 */
@Controller
@RequestMapping("/showView")
public class AjaxViewController {
    private static Log log = LogFactory.getLog(AjaxViewController.class);
    
    @RequestMapping()
    public String showView(
            HttpServletRequest request, 
            HttpServletResponse response,
            Model model
    ) throws ServletRequestBindingException, IOException {
        
        Map<String, ?> ajaxModel = ajaxPortletSupportService.getAjaxModel(request, response);

        model.addAllAttributes(ajaxModel);
        
        String view = "noDomain";
        
        if (ajaxModel.containsKey("view"))
            view = (String) ajaxModel.get("view");
        
        log.debug("Forwarding to view :: "+view);
        
        return view;
        
    }
    
    protected AjaxPortletSupport ajaxPortletSupportService;
        @Autowired
    public void setPortletSupport(AjaxPortletSupport support) {
        ajaxPortletSupportService = support;
    }
}
