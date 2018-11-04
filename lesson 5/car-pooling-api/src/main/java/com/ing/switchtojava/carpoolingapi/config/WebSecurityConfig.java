package com.ing.switchtojava.carpoolingapi.config;

import com.ing.switchtojava.carpoolingapi.repository.UserRepository;
import com.ing.switchtojava.carpoolingapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserService userService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        /*
        auth
                // enable in memory based authentication with a user named "user" and "admin"
                .inMemoryAuthentication().withUser("user").
                password(passwordEncoder.encode("user")).roles("USER")
                .and().withUser("admin2").password(passwordEncoder.encode("admin2")).roles("USER", "ADMIN")
        .and().passwordEncoder(passwordEncoder);
        */

        PasswordEncoder passwordEncoder = this.getPasswordEncoder();
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder);
    }

    @Bean
    public PasswordEncoder getPasswordEncoder()
    {
        return  new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/h2-console")
                .hasRole("ADMIN").anyRequest().authenticated().and()
                .formLogin()
                .permitAll();

         http.authorizeRequests().antMatchers("/locations")
               .hasRole("USER").anyRequest().authenticated().and().formLogin().permitAll();
    }
}
