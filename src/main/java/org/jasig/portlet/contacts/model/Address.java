/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jasig.portlet.contacts.model;

import java.io.Serializable;

/**
 *
 * @author mfgsscw2
 */
public interface Address extends Serializable {
    
    public static final String WORK_TYPE = "WORK";
    public static final String HOME_TYPE = "HOME";
    public static final String TEMP_TYPE = "TEMP";
    public static final String OTHER_TYPE = "OTHER";
    
    public String getLabel();
    public String getType();
    
    public String getInternal();
    public String getBuilding();
    public String getStreet();
    public String getLocality();
    public String getRegion();
    public String getPostCode();
    public String getCountry();
    
    
    public void setLabel(String label);
    public void setType(String type);
    
    public void setInternal(String internal);
    public void setBuilding(String building);
    public void setStreet(String street);
    public void setLocality(String locality);
    public void setRegion(String region);
    public void setPostCode(String code);
    public void setCountry(String country);
}
