package com.swkoan.gallows.security;

import java.security.Principal;

/**
 *
 */
public interface SecurityDescriptor {

    SecurityDescriptor getParent();
    
    Principal getOwner();

    String getSid();
}
