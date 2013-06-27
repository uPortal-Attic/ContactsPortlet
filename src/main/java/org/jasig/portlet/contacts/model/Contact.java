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
import java.util.List;

/**
 *
 * @author mfgsscw2
 */
public interface Contact extends Serializable {

    public String getURN();
    
    public String getId();
    
    /**
     * @return the addresses
     */
    public List<Address> getAddresses();
    
    /**
     * 
     * @return primary Address 
     */
    public Address getPrimaryAddress();

    /**
     * @return the contactSource
     */
    public String getContactSource();

    /**
     * @return the department
     */
    public String getDepartment();

    /**
     * @return the emailAddresses
     */
    public List<EmailAddress> getEmailAddresses();
    
    /**
     * 
     * @return primary EmailAddress
     */
    public EmailAddress getPrimaryEmailAddress();

    /**
     * @return the firstname
     */
    public String getFirstname();

    /**
     * @return the initials
     */
    public String getInitials();

    /**
     * @return the organisation
     */
    public String getOrganisation();

    /**
     * @return the phoneNumbers
     */
    public List<PhoneNumber> getPhoneNumbers();
    
    /**
     * 
     * @return primary PhoneNumber
     */
    public PhoneNumber getPrimaryPhoneNumber();

    /**
     * @return the position
     */
    public String getPosition();

    /**
     * @return the surname
     */
    public String getSurname();

    /**
     * @return the title
     */
    public String getTitle();
    
    /**
     * 
     * @return the URI for an image for the contact
     */
    public String getImageURI();

    
    /**
     * @param the addresses
     */
    public void setAddresses(List<Address> addresses);
    
    
    /**
     * @param contactSource the contactSource to set
     */
    public void setContactSource(String contactSource);

    /**
     * @param department the department to set
     */
    public void setDepartment(String department);

    /**
     * @param the emailAddresses list
     */
    public void setEmailAddresses(List<EmailAddress> addresses);
    
    
    /**
     * @param firstname the firstname to set
     */
    public void setFirstname(String firstname);

    /**
     * @param initials the initials to set
     */
    public void setInitials(String initials);

    /**
     * @param organisation the organisation to set
     */
    public void setOrganisation(String organisation);

    
    /**
     * @param the phoneNumbers
     */
    public void setPhoneNumbers(List<PhoneNumber> numbers);
    
    /**
     * @param position the position to set
     */
    public void setPosition(String position);

    /**
     * @param surname the surname to set
     */
    public void setSurname(String surname);

    /**
     * @param title the title to set
     */
    public void setTitle(String title);
    
    /**
     * 
     * @param id the ID of the Contact
     */
    
    public void setId(String id);
    
    /**
     * 
     * @param uri String representation of the URI of a Image for the contact.
     */
    public void setImageURI(String uri);
    
    /**
     * 
     * @return displayToGuest - denotes whether contact can be displayed to non-authenticated users
     */
    public Boolean getDisplayToGuest();
    
    
    /**
     * 
     * @param Boolean representation of the Contacts display to guest setting
     */
    public void setDisplayToGuest(Boolean displayToGuest);
    
}
