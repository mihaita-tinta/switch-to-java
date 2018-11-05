package com.ing.switchtojava.carpoolingapi.domain;

import javax.persistence.*;

@Entity
@Table(
    uniqueConstraints = @UniqueConstraint(columnNames = {"role"})
)
public class Role {

    @Id
    @GeneratedValue
    private Long id;

    private String role;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public enum UserRoles {
        ROLE_USER,
        ROLE_ADMIN
    }
}
