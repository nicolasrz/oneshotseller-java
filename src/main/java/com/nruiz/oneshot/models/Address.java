package com.nruiz.oneshot.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import javax.persistence.Id;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

/**
 * Created by Nicolas on 28/10/2017.
 */
@Entity
@Data
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @JsonIgnore
    private long id;

    private String firstname;
    private String lastname;
    private String number;
    private String street;
    private String complement = "";
    private String city;
    private String zipcode;

}
