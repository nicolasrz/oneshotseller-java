package com.nruiz.oneshot.models;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * Created by Nicolas on 28/10/2017.
 */
@Entity
@Data
@Table(name = "Orders")
public class Order {

    @JsonProperty
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @OneToOne
    private Address delivery;

    @OneToOne
    private Address facturation;

    private String email;

    private String phoneNumber;

    @ManyToMany
    private List<Article> articles = new ArrayList<>();

    private String totalPrice;

    private String createdAt;

    private String chargeToken;
    private String chargeIdTransaction;
    private String chargeBalanceTransaction;
    private String chargeStatusTransaction;



    public Order() {
    }

}
