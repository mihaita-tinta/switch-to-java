package com.ing.switchtojava.carpoolingapi.domain;

import org.springframework.context.annotation.Bean;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
public class User implements UserDetails {

    private static final String rolePrefix = "ROLE_";

    @Id
    @GeneratedValue
    private Long id;

    private String username;
    private String password;
    private String[] roles;

    public void setId(Long id)
    {
        this.id = id;
    }

    public void setUserName(String userName)
    {
        this.username = userName;
    }

    public void setUserPassword(String userPassword)
    {
        this.password = getPasswordEncoder().encode(userPassword);
    }

    public String[] getRoles()
    {
        return this.roles;
    }

    public void setRoles(String... roles)
    {
        this.roles = roles;
    }

    public Long getId(){
        return id;
    }

    public String getUserName()
    {
        return username;
    }

    public String getUserPassword(){
        return  password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        for (String role : roles) {
            grantedAuthorities.add(new SimpleGrantedAuthority(rolePrefix + role));
        }
        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_USER"));

        return grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Bean
    public PasswordEncoder getPasswordEncoder()
    {
        return  new BCryptPasswordEncoder();
    }
}
