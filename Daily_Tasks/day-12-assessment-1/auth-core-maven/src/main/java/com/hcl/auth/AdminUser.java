package com.hcl.auth;

public final class AdminUser extends UserAccount {
    public AdminUser(long id, String username, String email, String passwordHash) {
        super(id, username, email, passwordHash);
    }

    @Override
    public AccountType getAccountType() {
        return AccountType.ADMIN;
    }
}
