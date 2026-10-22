package com.hcl.auth;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends Repository<UserAccount, Long> {
    void saveUser(UserAccount user) throws DuplicateUsernameException;
    Optional<UserAccount> findByUsername(String username);
    boolean usernameExists(String username);
    void markActivated(long id);
    List<Long> pendingActivationIds();
}
