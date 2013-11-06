package com.swkoan.gallows.security;

import java.security.Principal;

/**
 *
 */
public interface Permission {

    Principal getPrincipal();

    SecurityDescriptor getSecurityDescriptor();

    Action getAction();
}
