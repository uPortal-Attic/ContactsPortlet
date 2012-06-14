/*
 * Copyright 2012 Jasig.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jasig.portlet.contacts.adapters.impl.xml;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.transform.stream.StreamSource;
import org.jasig.portlet.contacts.adapters.PushAdapter;
import org.jasig.portlet.contacts.adapters.impl.AbstractContactAdapter;
import org.jasig.portlet.contacts.model.Contact;
import org.jasig.portlet.contacts.model.ContactSet;
import org.springframework.oxm.Unmarshaller;

/**
 *
 * @author chris
 */
public class XMLContactAdapter extends AbstractContactAdapter implements PushAdapter {

    private Unmarshaller unmarshaller;
    private List<String> dataURIs;

    @Override
    public Set<ContactSet> getContacts() {
        Set<ContactSet> contacts = new TreeSet<ContactSet>();
        InputStream is = null;
        
        for (String dataURI : dataURIs) {
            ContactSet contactSet;
            
            try {
                is = getClass().getClassLoader().getResourceAsStream(dataURI);
                contactSet = (ContactSet) unmarshaller.unmarshal(new StreamSource(is));
                contacts.add(contactSet);
            } catch (Exception ex) {
                Logger.getLogger(XMLContactAdapter.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                if (is != null) {
                    try {
                        is.close();
                    } catch (IOException ex) {
                        Logger.getLogger(XMLContactAdapter.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }

        return contacts;
    }

    @Override
    public ContactSet getContacts(String id) {
        Set<ContactSet> contacts = getContacts();
        ContactSet contactSet = null;
        for(ContactSet set : contacts) 
            if (set.getId().equals(id)) {
                contactSet = set;
                break;
            }
        return contactSet;
    }

    @Override
    public Map<String, String> getGroups() {
        Set<ContactSet> contacts = getContacts();
        Map<String,String> groups = new HashMap<String,String>();
        for(ContactSet set : contacts)
            groups.put(set.getTitle(), set.getId());
            
        return groups;
    }

    public void setDataURIs(List<String> setSourceURIs) {
        this.dataURIs = setSourceURIs;
    }

    @Override
    protected String[] requiredAttributes() {
        return new String[0];
    }

    public void setUnmarshaller(Unmarshaller unmarshaller) {
        this.unmarshaller = unmarshaller;
    }
}
