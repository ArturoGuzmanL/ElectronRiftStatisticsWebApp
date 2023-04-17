package javacode.server.springelectronriftstatisticswebapp.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.LocalDate;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "users")
@EntityListeners(AuditingEntityListener.class)

public class User implements Serializable {
    public User (String ID, String username, String email, String password, LocalDate create_Date, String vinculatedlol, byte[] accountimage) {
        this.id = ID;
        this.username = username;
        this.email = email;
        this.password = password;
        this.create_Date = create_Date;
        this.vinculatedlol = vinculatedlol;
        this.accountimage = accountimage;
    }

    public User (String username, String password, String email) {
        this.id = DigestUtils.sha256Hex(username + email + password);
        this.username = username;
        this.password = DigestUtils.sha256Hex(password);
        this.email = email;
        this.create_Date = LocalDate.now();
    }

    public User () {
    }

    @Id
    @Column(name = "ID")
    private String id = null;
    @Column(name = "Username")
    private String username = null;
    @Column(name = "Email")
    private String email = null;
    @Column(name = "password")
    private String password = null;
    @Column(name = "Creationdate")
    private LocalDate create_Date = null;
    @Column(name = "vinculatedlol")
    private String vinculatedlol = null;
    @Lob
    @Column(name = "accountimage", length = -1, nullable = true, columnDefinition = "mediumblob")
    private byte[] accountimage;

    // getters y setters

    public String getId () {
        return id;
    }

    public void setId (String id) {
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

    public String getVinculatedlol () {
        return vinculatedlol;
    }

    public void setVinculatedlol (String vinculatedlol) {
        this.vinculatedlol = vinculatedlol;
    }

    public byte[] getAccountimage () {
        return accountimage;
    }

    public void setAccountimage (byte[] accountimage) {
        this.accountimage = accountimage;
    }

    public boolean noneNull() {
        return id != null && username != null && password != null && email != null && create_Date != null && vinculatedlol != null && accountimage != null;
    }

    @Override
    public String toString () {
        return "User{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", create_Date='" + create_Date + '\'' +
                ", vinculatedLoL='" + vinculatedlol + '\'' +
                ", AccountImage='" + accountimage + '\'' +
                '}';
    }
}