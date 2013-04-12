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


package org.jasig.portlet.contacts.model.impl;

import org.apache.commons.lang.StringUtils;
import org.jasig.portlet.contacts.model.PhoneNumber;
import org.jasig.portlet.contacts.model.util.ContactAttributeType;

/**
 *
 * @author mfgsscw2
 */
public class PhoneNumberPojo implements PhoneNumber {

    private String label = null;
    private ContactAttributeType type = null;
    private String number = null;
    
    
    public String toString() {
        return "PHONE:"+type +":"+label+":"+number;
    }
    
    public String getLabel() {
        return label;
    }

    public String getType() {
        return type != null ? type.toString() : null;
    }

    public String getPhoneNumber() {
        return number;
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

    public void setPhoneNumber(String number) {
        this.number = number;
    }
    
    @Override
    public String getDisplayType() {
        return label != null ? label : type.toString();
    }

    /**
     * Usable entries must have something for a number plus a type.
     *
     * @return True if populated.
     */
    @Override
    public boolean isPopulated() {
        return StringUtils.isNotBlank(number) && getType() != null;
    }
}
