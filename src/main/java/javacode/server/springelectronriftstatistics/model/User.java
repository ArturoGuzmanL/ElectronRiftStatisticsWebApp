package javacode.server.springelectronriftstatistics.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.LocalDate;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "users")
@EntityListeners(AuditingEntityListener.class)

public class User implements Serializable {
    public User (Long ID, String username, String email, String password, LocalDate create_Date) {
        this.id = ID;
        this.username = username;
        this.email = email;
        this.password = password;
        this.create_Date = create_Date;
    }

    public User () {
    }

    @Id
    @Column(name = "ID")
    private Long id = null;
    @Column(name = "username")
    private String username = null;
    @Column(name = "email")
    private String email = null;
    @Column(name = "password")
    private String password = null;
    @Column(name = "create_Date")
    private LocalDate create_Date = null;

    // getters y setters

    public Long getId () {
        return id;
    }

    public void setId (Long id) {
        this.id = id;
    }

    public String getUsername () {
        return username;
    }

    public void setUsername (String username) {
        this.username = username;
    }

    public String getEmail () {
        return email;
    }

    public void setEmail (String email) {
        this.email = email;
    }

    public String getPassword () {
        return password;
    }

    public void setPassword (String password) {
        this.password = password;
    }

    public LocalDate getCreate_Date () {
        return create_Date;
    }

    public void setCreate_Date (LocalDate create_Date) {
        this.create_Date = create_Date;
    }

    public boolean noneNull() {
        return id != null && username != null && password != null && email != null && create_Date != null;
    }

    @Override
    public String toString () {
        return "User{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", create_Date='" + create_Date + '\'' +
                '}';
    }
}