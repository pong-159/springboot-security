package com.example.java_security.security;

import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Set;
import java.util.stream.Collectors;

import static com.example.java_security.security.ApplicationUserPermission.*;

@Slf4j
public enum ApplicationUserRole {
    STUDENT(Sets.newHashSet()),
    ADMIN(Sets.newHashSet(COURSE_READ, COURSE_WRITE, STUDENT_READ, STUDENT_WRITE)),

    ADMINTRAINEE(Sets.newHashSet(COURSE_READ, STUDENT_READ));

    private final Set<ApplicationUserPermission> permissionSet;

    ApplicationUserRole(Set<ApplicationUserPermission> permissionSet){
        this.permissionSet = permissionSet;
    }

    public Set<ApplicationUserPermission> getPermissionSet(){
        return permissionSet;
    }

    public Set<GrantedAuthority> getGrantedAuthority(){
        Set<GrantedAuthority> permissions = getPermissionSet().stream()
                .map(p -> new SimpleGrantedAuthority(p.getPermission()))
                .collect(Collectors.toSet())
                ;

        log.info("p " + permissions);

        permissions.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
        return permissions;
    }

}
