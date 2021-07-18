package com.alphateam.banqueenligne.Data_Access.Models;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@Table(
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "username"),
                @UniqueConstraint(columnNames = "email")
        })
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(max = 20)
    private String username;

    @Size(max = 50)
    @Email
    private String email;


    @Size(max = 120)
    private String password;

    private String photos;
    @ManyToMany
    private Set<Role> roles = new HashSet<>();

    @ManyToOne
    private Agence agence;


    public Users(String username, String email, String password) {
        this.username=username;
        this.email=email;
        this.password=password;

    }

    public Users() {

    }
}