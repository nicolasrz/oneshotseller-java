package com.nruiz.oneshot.models;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by Nicolas on 28/10/2017.
 */
@Entity
@Data
@Table(name = "Orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @OneToOne
    private Address delivery;

    @OneToOne
    private Address facturation;

    private String email;

    @ManyToMany
    private List<Article> articles = new ArrayList<>();

    private float totalPrice;

    private String createdAt;



    public Order() {
    }

}
