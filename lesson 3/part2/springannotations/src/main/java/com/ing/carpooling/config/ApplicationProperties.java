package com.ing.carpooling.config;

import org.springframework.beans.factory.annotation.Value;

public class ApplicationProperties {

    @Value("${application.datasource.url}")
    private String url;
    @Value("${application.datasource.username}")
    private String username;
    @Value("${application.datasource.password}")
    private String password;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
