package com.example.java_security.auth;

import java.util.Optional;

public interface ApplicationUserDAO {

    public Optional<ApplicationUser> selectUserByUsername(String name);
}
