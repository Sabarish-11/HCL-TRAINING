package com.hcl.auth;

public final class StandardUser extends UserAccount {
    public StandardUser(long id, String username, String email, String passwordHash) {
        super(id, username, email, passwordHash);
    }

    @Override
    public AccountType getAccountType() {
        return AccountType.STANDARD;
    }
}
