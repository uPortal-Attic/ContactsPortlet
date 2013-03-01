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
package org.jasig.portlet.contacts.model;

import java.io.Serializable;

/**
 *
 * @author mfgsscw2
 */
public interface EmailAddress extends Serializable, TestableContactDependency {

    public enum EmailAddressType {
        WORK, HOME, TEMP, OTHER;

        public static EmailAddressType getType (String value) {
            EmailAddressType ret = null;
            try {
                return EmailAddressType.valueOf(value);
            } catch (NullPointerException ex) {
                return null;
            } catch (IllegalArgumentException ex) {
                return null;
            }
        }
    }

    public String getLabel();
    public String getType();
    public String getEmailAddress();
    
    public void setLabel(String label);
    public void setType(String type);
    public void setEmailAddress(String email);

    public String getDisplayType();
}
