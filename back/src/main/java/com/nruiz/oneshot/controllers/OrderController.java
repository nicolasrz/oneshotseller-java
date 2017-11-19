package com.nruiz.oneshot.controllers;

import com.nruiz.oneshot.models.*;
import com.nruiz.oneshot.repositories.AddressRepository;
import com.nruiz.oneshot.repositories.ArticleRepository;
import com.nruiz.oneshot.repositories.OrderRepository;
import com.nruiz.oneshot.repositories.StockRepository;
import com.nruiz.oneshot.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;

/**
 * Created by Nicolas on 28/10/2017.
 */
@CrossOrigin(origins = Constants.FRONT_URI)
@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private StockRepository stockRepository;


    @RequestMapping(value="/", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
    public CustomResponse createOrder(@RequestBody Order orderFront){

        if(orderFront.getArticles().isEmpty()){
            return new CustomResponse(orderFront, "No articles", false);
        }

        if(orderFront.getDelivery() == null){
            return new CustomResponse(orderFront, "Delivery address is null", false);
        }



        Order orderToSave = new Order();
        Address delivery  = orderFront.getDelivery();
        delivery = this.addressRepository.save(delivery);
        orderToSave.setDelivery(delivery);

        if(orderFront.getFacturation() == null){
            orderToSave.setFacturation(delivery);
        }else{
            Address facturation  = orderFront.getFacturation();
            facturation = this.addressRepository.save(facturation);
            orderToSave.setFacturation(facturation);
        }
        orderToSave.setEmail(orderFront.getEmail());




        for(Article articleFront : orderFront.getArticles()){
            Article article  = this.articleRepository.findOne(articleFront.getId());

            Stock stock = this.stockRepository.findOne(article.getStock().getId());
            stock.setTotal(article.getStock().getTotal() - 1 );
            stock = this.stockRepository.save(stock);
            article.setStock(stock);
            article = this.articleRepository.save(article);

            orderToSave.getArticles().add(article);
        }

        orderToSave.setCreatedAt(LocalDateTime.now().toString());
        orderToSave = orderRepository.save(orderToSave);

        return new CustomResponse(orderToSave,"", true);
    }

}
