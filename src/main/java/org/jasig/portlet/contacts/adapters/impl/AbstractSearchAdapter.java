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
package org.jasig.portlet.contacts.adapters.impl;

import java.util.HashMap;
import java.util.Map;
import org.jasig.portlet.contacts.adapters.SearchAdapter;

/**
 *
 * @author mfgsscw2
 */
public abstract class AbstractSearchAdapter extends AbstractContactAdapter implements SearchAdapter {
    
    protected String searchFilterAttribute = "";
    public void setFilterAttribute(String attribute) {
        searchFilterAttribute = attribute;
    }
    public String getFilterAttribute() {
        return searchFilterAttribute;
    }
    
    protected Map<String,Object> filters = new HashMap<String,Object>();
    public void setFilters(Map<String,Object> filters) {
        this.filters = filters;
    }
    public Map<String,Object> getFilters() {
        return this.filters;
    }
    
}
