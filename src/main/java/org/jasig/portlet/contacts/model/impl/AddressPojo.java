/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jasig.portlet.contacts.model.impl;

import org.jasig.portlet.contacts.model.Address;

/**
 *
 * @author mfgsscw2
 */
public class AddressPojo implements Address {

    private String label = null;
    private String type = null;
    private String internal = null;
    private String building = null;
    private String street = null;
    private String locality = null;
    private String region = null;
    private String postcode = null;
    private String country = null;
    
    public String toString() {
        return "ADDRESS:"+type + ":" + label + ":" + postcode;
    }
    
    public String getLabel() {
        return label;
    }

    public String getType() {
        return type;
    }

    public String getInternal() {
        return internal;
    }

    public String getBuilding() {
        return building;
    }

    public String getStreet() {
        return street;
    }

    public String getLocality() {
        return locality;
    }

    public String getRegion() {
        return region;
    }

    public String getPostCode() {
        return postcode;
    }

    public String getCountry() {
        return country;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setInternal(String internal) {
        this.internal = internal;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public void setPostCode(String code) {
        this.postcode = code;
    }

    public void setCountry(String country) {
        this.country = country;
    }
    
}
