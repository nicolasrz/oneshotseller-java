package com.nruiz.oneshot.models;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nicolas on 28/10/2017.
 */
@Entity
@Data

public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private float price;
    private String name;
    private String description;

    private String image;

    @Transient
    private Long index;

    @JsonIgnore
    @OneToOne
    private Stock stock;

}
