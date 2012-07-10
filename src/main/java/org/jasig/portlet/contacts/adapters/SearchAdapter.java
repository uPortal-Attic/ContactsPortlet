/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jasig.portlet.contacts.adapters;



import java.util.Map;
import org.jasig.portlet.contacts.model.ContactSet;



/**
 *
 * @author Chris White <christopher.white@manchester.ac.uk>
 */
public interface SearchAdapter extends ContactAdapter {

    
    public ContactSet search(String searchText);
    public ContactSet search(String searchText, String filter);
    public void setFilters(Map<String,Object> filers);
    public Map<String,Object> getFilters();
    public void setFilterAttribute(String attribute);
    public String getFilterAttribute();
}
