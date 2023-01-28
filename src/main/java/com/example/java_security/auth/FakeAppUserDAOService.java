package com.example.java_security.auth;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

import static com.example.java_security.security.ApplicationUserRole.*;

@Repository("fake")
public class FakeAppUserDAOService implements ApplicationUserDAO{

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public FakeAppUserDAOService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public Optional<ApplicationUser> selectUserByUsername(String name) {

        return getApplicationUsers()
                .stream()
                .filter(u -> name.equals(u.getUsername()))
                .findFirst();
    }

    private List<ApplicationUser> getApplicationUsers(){
        List<ApplicationUser> applicationUsers = Lists.newArrayList(
                new ApplicationUser(
                        "one",
                        passwordEncoder.encode("1111"),
                        STUDENT.getGrantedAuthority(),
                        true,
                        true,
                        true,
                        true
                ),
                new ApplicationUser(
                        "two",
                        passwordEncoder.encode("2222"),
                        ADMIN.getGrantedAuthority(),
                        true,
                        true,
                        true,
                        true
                ),
                new ApplicationUser(
                        "three",
                        passwordEncoder.encode("3333"),
                        ADMINTRAINEE.getGrantedAuthority(),
                        true,
                        true,
                        true,
                        true
                )

        );
       return applicationUsers;
    }
}
