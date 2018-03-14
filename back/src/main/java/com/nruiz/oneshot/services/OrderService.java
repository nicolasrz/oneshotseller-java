package com.nruiz.oneshot.services;


import com.nruiz.oneshot.models.*;
import com.nruiz.oneshot.repositories.AddressRepository;
import com.nruiz.oneshot.repositories.ArticleRepository;
import com.nruiz.oneshot.repositories.OrderRepository;
import com.nruiz.oneshot.repositories.StockRepository;
import com.nruiz.oneshot.utils.OneErrorCode;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {



    private OrderRepository orderRepository;
    private AddressRepository addressRepository;
    private ArticleRepository articleRepository;
    private StockRepository stockRepository;
    private StripeService stripeService;

    public OrderService(OrderRepository orderRepository,
                        AddressRepository addressRepository, ArticleRepository articleRepository,
                        StockRepository stockRepository, StripeService stripeService) {
        this.orderRepository = orderRepository;
        this.addressRepository = addressRepository;
        this.articleRepository = articleRepository;
        this.stockRepository = stockRepository;
        this.stripeService = stripeService;
    }

    public Order saveNewOrder(ChargeRequestOrder orderCharge) {
        Order orderToSave = new Order();

        Address delivery  = orderCharge.getOrder().getDelivery();
        delivery = this.addressRepository.save(delivery);
        orderToSave.setDelivery(delivery);

        Address facturation = this.getFacturation(orderCharge.getOrder());
        facturation = this.addressRepository.save(facturation);
        orderToSave.setFacturation(facturation);

        orderToSave.setEmail(orderCharge.getOrder().getEmail());
        orderToSave.setTotalPrice(getTotalPriceFromOrder(orderCharge.getOrder()));
        orderToSave.setCreatedAt(LocalDateTime.now().toString());
        orderToSave.getArticles().addAll(articleUpdateStock(orderCharge.getOrder()));

        orderToSave.setChargeBalanceTransaction(orderCharge.getOrder().getChargeBalanceTransaction());
        orderToSave.setChargeIdTransaction(orderCharge.getOrder().getChargeIdTransaction());
        orderToSave.setChargeStatusTransaction(orderCharge.getOrder().getChargeStatusTransaction());

        orderToSave = orderRepository.save(orderToSave);
        return orderToSave;
    }

    private List<Article> articleUpdateStock(Order orderFront) {
        List<Article> articlesChecked = new ArrayList<>();
        for(Article article : this.getArticlesFromOrderChecked(orderFront)){
            Stock stock = this.stockRepository.findOne(article.getStock().getId());
            stock.setTotal(article.getStock().getTotal() - 1 );
            stock = this.stockRepository.save(stock);
            article.setStock(stock);
            article = this.articleRepository.save(article);
            articlesChecked.add(article);
        }
        return articlesChecked;
    }

    private float getTotalPriceFromOrder(Order orderFront) {
        float totalPrice = 0;
        for(Article articleFront : orderFront.getArticles()){
            Article article  = this.articleRepository.findOne(articleFront.getId());
            totalPrice = totalPrice + article.getPrice();
        }
        int amount = Math.round(totalPrice * 100);
        return amount;
    }

    private Address getFacturation(Order orderFront) {
        if(orderFront.getFacturation() == null){
            return orderFront.getDelivery();
        }else{
            Address facturation  = orderFront.getFacturation();
            return facturation;
        }
    }

    private List<Article> getArticlesFromOrderChecked(Order orderFront){
        List<Article> articles = new ArrayList<>();
        for(Article article : orderFront.getArticles()){
            articles.add(this.articleRepository.findOne(article.getId()));
        }

        return articles;
    }

    public CustomResponse checkOrderFront(Order orderFront){

        CustomResponse customResponse = new CustomResponse();

        if(orderFront.getArticles().isEmpty()){
            customResponse.setSuccess(false);
            customResponse.setMessage(OneErrorCode.ERROR_MESSAGE_EMPTY_CART_FRENCH);
            return customResponse;
        }

        for(Article article : orderFront.getArticles()){
            if(this.articleRepository.findOne(article.getId()) == null){
                customResponse.setSuccess(false);
                customResponse.setMessage(OneErrorCode.ERROR_MESSAGE_ARTICLE_NOT_FOUND);
                return customResponse;
            }
        }

        if(orderFront.getDelivery() == null){
            customResponse.setSuccess(false);
            customResponse.setMessage(OneErrorCode.ERROR_MESSAGE_DELIVERY_MISSING_FRENCH);
            return customResponse;
        }


        orderFront.setFacturation(this.getFacturation(orderFront));

        if(orderFront.getFacturation() == null){
            customResponse.setSuccess(false);
            customResponse.setMessage(OneErrorCode.ERROR_MESSAGE_FACTURATION_MISSING_FRENCH);
            return customResponse;
        }


        //if the current order request is ok, we return the order with the public key to create the stripe form.

        orderFront.setTotalPrice(this.getTotalPriceFromOrder(orderFront));
        customResponse.setObject(orderFront);
        customResponse.setMessage("");
        customResponse.setSuccess(true);

        return customResponse;
    }
}
