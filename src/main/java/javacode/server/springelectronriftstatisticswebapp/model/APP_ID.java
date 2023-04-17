package javacode.server.springelectronriftstatisticswebapp.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@Entity
@Table(name = "APP_IDs")
@EntityListeners(AuditingEntityListener.class)
public class APP_ID implements Serializable {

    public APP_ID (Integer ID) {
        this.id = ID;
    }

    public APP_ID () {
    }

    @Id
    @Column(name = "ID")
    Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "APP_ID {" +
                "id=" + id +
                '}';
    }


}
