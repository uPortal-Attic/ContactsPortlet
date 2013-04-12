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

package org.jasig.portlet.contacts.model.util;

import java.util.Comparator;

import org.jasig.portlet.contacts.model.Contact;
import org.springframework.util.StringUtils;

/**
 *
 * @author chris
 */
public class ContactComparator implements Comparator<Contact> {

    public int compare(Contact t, Contact t1) {
        int ret = compareStrings(t.getSurname(), t1.getSurname());
        if (ret == 0)
            ret = compareStrings(t.getFirstname(), t1.getFirstname());
        if (ret == 0)
            ret = compareStrings(t.getInitials(), t1.getInitials());
        if (ret == 0)
            ret = compareStrings(t.getId(), t1.getId());

        return ret;
    }

    private int compareStrings (String s1, String s2) {
        if (StringUtils.hasLength(s1)) {
            return s1.compareToIgnoreCase(s2);
        } else if (StringUtils.hasLength(s2)) {
            return -1;
        }
        return 0;
    }

}
