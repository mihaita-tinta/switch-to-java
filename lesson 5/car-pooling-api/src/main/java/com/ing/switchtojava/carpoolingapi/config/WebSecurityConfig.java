package com.ing.switchtojava.carpoolingapi.config;

import com.ing.switchtojava.carpoolingapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserService userService;

    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

//        auth.inMemoryAuthentication().withUser("user").password(passwordEncoder.encode("password")).roles("USER")
//                .and().withUser("admin").password(passwordEncoder.encode("password")).roles("USER", "ADMIN")
//                .and().passwordEncoder(passwordEncoder);

        auth.userDetailsService(userService).passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                    .antMatchers("/h2-console", "/h2-console/*").hasRole("ADMIN")
                    .anyRequest().authenticated()
                .and()
                    .formLogin().permitAll()
                .and()
                    .csrf().disable();

    }
}
