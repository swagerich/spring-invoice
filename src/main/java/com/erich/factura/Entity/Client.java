package com.erich.factura.Entity;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

import java.util.Date;

@Data
@Entity
@Table(name = "clients")
public class Client implements Serializable {
    private static final long serialVersionUID=1L;
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "No debe estar vacia")
    private String name;

    @NotEmpty(message = "No debe estar vacia")
    @Column(name = "lastname")
    private String lastName;

    @Email(regexp = "^(?=.{1,64}@)[A-Za-z0-9_-]+(.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(.[A-Za-z0-9-]+)*(.[A-Za-z]{2,})$")
    private String mail;

    @Column(name = "creation_date")
    @Temporal(value = TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date createAt;

    //@Lob
    //@Column(name = "foto",columnDefinition = "BLOB")
    private String foto;

}
