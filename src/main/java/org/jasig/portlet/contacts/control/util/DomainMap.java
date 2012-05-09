package org.jasig.portlet.contacts.control.util;

import java.util.Map;

    public class DomainMap {

        private Map<String, Boolean> map;

        public DomainMap(Map<String, Boolean> map) {
            this.map = map;
        }

        public void setMap(Map<String, Boolean> map) {
            this.map = map;
        }

        public Map<String, Boolean> getMap() {
            return this.map;
        }
    }
