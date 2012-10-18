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

import org.jasig.portlet.contacts.model.Address;
import org.jasig.portlet.contacts.model.Contact;
import org.jasig.portlet.contacts.model.EmailAddress;
import org.jasig.portlet.contacts.model.ModelObjectFactory;
import org.jasig.portlet.contacts.model.PhoneNumber;

/**
 *
 * @author mfgsscw2
 */
public class ModelPojoFactory implements ModelObjectFactory {
    
    
    public <T extends Object> T getObjectOfType(Class<T> clazz) {
        if (clazz.isAssignableFrom(Contact.class))
            return (T) new ContactPojo();
        else if (clazz.isAssignableFrom(Address.class))
            return (T) new AddressPojo();
        else if (clazz.isAssignableFrom(PhoneNumber.class))
            return (T) new PhoneNumberPojo();
        else if (clazz.isAssignableFrom(EmailAddress.class))
            return (T) new EmailAddressPojo();
        else
            throw new RuntimeException("Not a valid model class");
    }
   
}
