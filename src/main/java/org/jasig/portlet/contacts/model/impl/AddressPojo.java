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

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jasig.portlet.contacts.model.impl;

import org.apache.commons.lang.StringUtils;
import org.jasig.portlet.contacts.model.Address;
import org.jasig.portlet.contacts.model.util.ContactAttributeType;

/**
 *
 * @author mfgsscw2
 */
public class AddressPojo implements Address {

    private String label = null;
    private ContactAttributeType type = null;
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
        return type != null ? type.toString() : null;
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

    public void setType(ContactAttributeType type) {
        this.type = type;
    }

    public void setType(String typeName) {
        this.type = ContactAttributeType.getType(typeName);
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
    
    @Override
    public String getDisplayType() {
        return label != null ? label : type.toString();
    }

    /**
     * Usable entries must have something for a type, plus at least one of
     * street, building, locality, region, or country.
     *
     * @return True if populated.
     */
    @Override
    public boolean isPopulated() {
        return getType() != null && (
                StringUtils.isNotBlank(street) || StringUtils.isNotBlank(building)
                || StringUtils.isNotBlank(locality) || StringUtils.isNotBlank(region));
    }
}
