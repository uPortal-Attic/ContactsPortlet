/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jasig.portlet.contacts.adapters.impl;

import java.util.HashMap;
import java.util.Map;
import org.jasig.portlet.contacts.adapters.SearchAdapter;

/**
 *
 * @author mfgsscw2
 */
public abstract class AbstractSearchAdapter extends AbstractContactAdapter implements SearchAdapter {
    
    protected String searchFilterAttribute = "";
    public void setFilterAttribute(String attribute) {
        searchFilterAttribute = attribute;
    }
    public String getFilterAttribute() {
        return searchFilterAttribute;
    }
    
    protected Map<String,Object> filters = new HashMap<String,Object>();
    public void setFilters(Map<String,Object> filters) {
        this.filters = filters;
    }
    public Map<String,Object> getFilters() {
        return this.filters;
    }
    
}
