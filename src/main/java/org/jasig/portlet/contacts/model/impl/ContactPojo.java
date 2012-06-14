/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jasig.portlet.contacts.model.impl;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang.RandomStringUtils;
import org.jasig.portlet.contacts.model.Address;
import org.jasig.portlet.contacts.model.Contact;
import org.jasig.portlet.contacts.model.EmailAddress;
import org.jasig.portlet.contacts.model.PhoneNumber;

/**
 *
 * @author mfgsscw2
 */
public class ContactPojo implements Contact {
    
    private String id = "";
    
    private String title = "";
    private String firstname = "";
    private String surname = "";
    private String initials = "";
    
    private String position = "";
    private String organisation = "";
    private String department = "";
    
    private List<EmailAddress> emailAddresses = new ArrayList<EmailAddress>();
    private List<Address> addresses= new ArrayList<Address>();
    private List<PhoneNumber> phoneNumbers = new ArrayList<PhoneNumber>();
    
    private String imageURI = "";
    
    private String contactSource = "";
    
    public String getURN() {
        String urn = "urn:";
        
        if (contactSource != null)
            urn += contactSource + ":";
        else
            urn += "unknown:";
        
        if (id != null)
            urn += id;
        else
            urn += RandomStringUtils.randomAlphanumeric(24);
        
        
        return urn;
    }
    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the firstname
     */
    public String getFirstname() {
        return firstname;
    }

    /**
     * @param firstname the firstname to set
     */
    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    /**
     * @return the surname
     */
    public String getSurname() {
        return surname;
    }

    /**
     * @param surname the surname to set
     */
    public void setSurname(String surname) {
        this.surname = surname;
    }

    /**
     * @return the initials
     */
    public String getInitials() {
        return initials;
    }

    /**
     * @param initials the initials to set
     */
    public void setInitials(String initials) {
        this.initials = initials;
    }

    /**
     * @return the position
     */
    public String getPosition() {
        return position;
    }

    /**
     * @param position the position to set
     */
    public void setPosition(String position) {
        this.position = position;
    }

    /**
     * @return the organisation
     */
    public String getOrganisation() {
        return organisation;
    }

    /**
     * @param organisation the organisation to set
     */
    public void setOrganisation(String organisation) {
        this.organisation = organisation;
    }

    /**
     * @return the department
     */
    public String getDepartment() {
        return department;
    }

    /**
     * @param department the department to set
     */
    public void setDepartment(String department) {
        this.department = department;
    }

    /**
     * @return the emailAddresses
     */
    public List<EmailAddress> getEmailAddresses() {
        return emailAddresses;
    }

    /**
     * @return the addresses
     */
    public List<Address> getAddresses() {
        return addresses;
    }

    /**
     * @return the phoneNumbers
     */
    public List<PhoneNumber> getPhoneNumbers() {
        return phoneNumbers;
    }

    /**
     * @return the contactSource
     */
    public String getContactSource() {
        return contactSource;
    }

    /**
     * @param contactSource the contactSource to set
     */
    public void setContactSource(String contactSource) {
        this.contactSource = contactSource;
    }

    public Address getPrimaryAddress() {
        if (!addresses.isEmpty())
            return addresses.get(0);
        else
            return null;
    }

    public EmailAddress getPrimaryEmailAddress() {
        if (!emailAddresses.isEmpty())
            return emailAddresses.get(0);
        else
            return null;
    }

    public PhoneNumber getPrimaryPhoneNumber() {
        if (!phoneNumbers.isEmpty())
            return phoneNumbers.get(0);
        else
            return null;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImageURI() {
        return imageURI;
    }

    public void setImageURI(String uri) {
        this.imageURI = uri;
    }

    @Override
    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }

    @Override
    public void setEmailAddresses(List<EmailAddress> addresses) {
        this.emailAddresses = addresses;
    }

    @Override
    public void setPhoneNumbers(List<PhoneNumber> numbers) {
        this.phoneNumbers = numbers;
                
    }
    
}
