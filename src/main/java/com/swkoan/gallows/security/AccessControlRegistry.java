package com.swkoan.gallows.security;

import java.security.Principal;
import java.util.List;
import javax.security.auth.Subject;

/**
 *
 */
public interface AccessControlRegistry {

    boolean check(SecurityDescriptor securedObject, Subject subject, Action action);
    
    List<Permission> getPermissions(SecurityDescriptor securedObject, Subject subject);
    
    SecurityDescriptor createSecurityDescriptor(Principal owner, SecurityDescriptor parent);
}
