package com.hcl.auth;

/** Contract for objects that have a stable identifier. */
public interface Identifiable<ID> {
    ID getId();
}
