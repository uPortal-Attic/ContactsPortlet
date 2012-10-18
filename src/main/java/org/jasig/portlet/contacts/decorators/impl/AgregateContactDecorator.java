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
package org.jasig.portlet.contacts.decorators.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.jasig.portlet.contacts.model.Contact;
import org.jasig.portlet.contacts.model.ContactSet;
import org.jasig.portlet.contacts.decorators.ContactDecorator;

/**
 *
 * @author Chris White <christopher.white@manchester.ac.uk>
 */
public class AgregateContactDecorator implements ContactDecorator {

    public void decorate(Contact contact) {
        for (ContactDecorator decorator : decorators)
            decorator.decorate(contact);
    }

    public void decorate(ContactSet contacts) {
        for (ContactDecorator decorator : decorators)
            decorator.decorate(contacts);
    }

    public void setOverride(boolean override) {
        for (ContactDecorator decorator : decorators)
            decorator.setOverride(override);
    }
    
    private List<ContactDecorator> decorators = new ArrayList<ContactDecorator>();
    public void setDecorators(Collection<ContactDecorator> decorators) {
        this.decorators.clear();
        this.decorators.addAll(decorators);
    }
    
}
