package com.example.java_security.security;

import com.example.java_security.auth.ApplicationUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.concurrent.TimeUnit;

import static com.example.java_security.security.ApplicationUserPermission.COURSE_WRITE;
import static com.example.java_security.security.ApplicationUserRole.STUDENT;

@Configuration
@EnableWebSecurity
@Slf4j
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ApplicationSecurityConfig  {


    private  final PasswordEncoder passwordEncoder;
    private final ApplicationUserService applicationUserService;

    @Autowired
    public ApplicationSecurityConfig(PasswordEncoder passwordEncoder, ApplicationUserService applicationUserService) {
        this.passwordEncoder = passwordEncoder;
        this.applicationUserService = applicationUserService;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/" , "index", "/css/*" , "/js/*").permitAll()
                .antMatchers("/api/**").hasRole(STUDENT.name())
                .antMatchers(HttpMethod.DELETE ,"/mgmt/**").hasAuthority(COURSE_WRITE.getPermission())
                .antMatchers(HttpMethod.POST,"/mgmt/**").hasAuthority(COURSE_WRITE.getPermission())
                .antMatchers(HttpMethod.PUT ,"/mgmt/**").hasAuthority(COURSE_WRITE.getPermission())
//                .antMatchers(HttpMethod.GET ,"/mgmt/**").hasAnyRole(ADMIN.name(), ADMINTRAINEE.name())
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .loginPage("/login").permitAll()
                .defaultSuccessUrl("/courses", true)
                .and()
                .rememberMe()
                    .tokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(21))
                    .key("something")
                .and()
                .logout()
                    .logoutUrl("/logout")
                    .clearAuthentication(true)
                    .invalidateHttpSession(true)
                    .deleteCookies("JSESSIONID", "remember-me")
                    .logoutSuccessUrl("/login");



        return http.build();
    }

    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.authenticationProvider(daoAuthenticationProvider());
        return authenticationManagerBuilder.build();
    }


    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(applicationUserService);
        return provider;
    }



//    @Bean
//    protected UserDetailsService userDetailsService(){
//        UserDetails user = User.builder()
//                .username("cry")
//                .password(passwordEncoder.encode("1234"))
//                .authorities(STUDENT.getGrantedAuthority())
////                .roles(STUDENT.name()) // ROLE_STUDENT
//                .build();
//
//        UserDetails admin= User.builder()
//                .username("smile")
//                .password(passwordEncoder.encode("1111"))
//                .authorities(ADMIN.getGrantedAuthority())
////                .roles(ADMIN.name()) // ROLE_ADMIN
//                .build();
//
//        UserDetails jane= User.builder()
//                .username("jane")
//                .password(passwordEncoder.encode("2222"))
//                .authorities(STUDENT.getGrantedAuthority())
////                .roles(STUDENT.name()) // ROLE_STUDENT
//                .build();
//
//        UserDetails tom= User.builder()
//                .username("tom")
//                .password(passwordEncoder.encode("3333"))
//                .authorities(ADMINTRAINEE.getGrantedAuthority())
////                .roles(ADMINTRAINEE.name())
//                .build();
//
//        log.info("au  " +  ADMINTRAINEE.getGrantedAuthority());
//        log.info("auq  " +  COURSE_WRITE.getPermission());
//
//        return new InMemoryUserDetailsManager(
//                user,
//                admin,
//                tom,
//                jane
//
//        );
//    }




}
