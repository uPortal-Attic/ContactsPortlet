/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jasig.portlet.contacts.context;

import java.util.Collection;
import org.springframework.util.StringUtils;

/**
 *
 * @author mfgsscw2
 */
public class MissingContextException extends RuntimeException {

    private MissingContextException() {
        super();
    }

    private MissingContextException(String message) {
        super(message);
    }

    private MissingContextException(Throwable cause) {
        super(cause);
    }

    private MissingContextException(String message, Throwable cause) {
        super(message, cause);
    }
    
    public MissingContextException(Collection<String> missingAttributes) {
        this("Missing context attributes : needs ("+StringUtils.collectionToDelimitedString(missingAttributes, ",", "'", "'")+")");
    }
    
}
