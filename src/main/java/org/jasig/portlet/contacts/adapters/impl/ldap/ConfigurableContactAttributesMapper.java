/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jasig.portlet.contacts.adapters.impl.ldap;

import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import org.springframework.ldap.core.AttributesMapper;
import org.jasig.portlet.contacts.model.Address;
import org.jasig.portlet.contacts.model.Contact;
import org.jasig.portlet.contacts.model.EmailAddress;
import org.jasig.portlet.contacts.model.ModelObjectFactory;
import org.jasig.portlet.contacts.model.PhoneNumber;
import org.springframework.util.StringUtils;

/**
 *
 * @author mfgsscw2
 */
public class ConfigurableContactAttributesMapper implements AttributesMapper {
    
    Map<String, Object> config;
    ModelObjectFactory factory;
    
    public ConfigurableContactAttributesMapper(Map<String, Object> config, ModelObjectFactory factory) {
        this.config = config;
        this.factory = factory;
    }
    
    
    
    @Override
    public Object mapFromAttributes(Attributes attrs) throws NamingException {
        Contact contact = factory.getObjectOfType(Contact.class);
        
        for(String key : config.keySet()) {

            if (key.equalsIgnoreCase("address")) {
                Address addr = populate(Address.class, (Map<String,String>)config.get(key), attrs);
                if (addr != null)
                    contact.getAddresses().add(addr);
            } else if (key.equalsIgnoreCase("phone")) {
                PhoneNumber phone = populate(PhoneNumber.class, (Map<String,String>)config.get(key), attrs);
                if (phone != null)
                    contact.getPhoneNumbers().add(phone);
            } else if (key.equalsIgnoreCase("email")) {
                EmailAddress email = populate(EmailAddress.class, (Map<String,String>)config.get(key), attrs);
                if (email != null)
                    contact.getEmailAddresses().add(email);
            } else {
                try {
                    String method = "set" + StringUtils.capitalize(key);
                    Contact.class.getMethod(method, String.class).invoke(contact, attrs.get((String)config.get(key)));
                } catch (Exception ex) {
                    Logger.getLogger(ConfigurableContactAttributesMapper.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        
        return contact;        
    }
    
    private <T> T populate(Class<T> clazz, Map<String,String> conf, Attributes attrs) {
        T obj = factory.getObjectOfType(clazz);
        for (String key : conf.keySet()) {
            try {
                String method = "set" + StringUtils.capitalize(key);
                obj.getClass().getMethod(key, String.class).invoke(obj, getValue(attrs.get((String)config.get(key))));
            } catch (Exception ex) {
                Logger.getLogger(ConfigurableContactAttributesMapper.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        return obj;
    }
    
    
    private String getValue(Attribute attribute) throws javax.naming.NamingException {        
        if(attribute != null) {
            String value = (String)attribute.get();
            if(value != null && !value.equalsIgnoreCase("empty")) {
                return value;
            }
        }
        return "";
    }
}
