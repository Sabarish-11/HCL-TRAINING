package com.hcl.auth;

/** Describes an account that can transition to the active state. */
public interface Activatable {
    boolean isActive();
    void activate();
}
