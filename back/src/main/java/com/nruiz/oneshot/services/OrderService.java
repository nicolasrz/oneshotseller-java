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

    public Order saveNewOrder(Order orderCharge) {
        Order orderToSave = new Order();

        Address delivery  = orderCharge.getDelivery();
        delivery = this.addressRepository.save(delivery);
        orderToSave.setDelivery(delivery);

        Address facturation = this.getFacturation(orderCharge);
        facturation = this.addressRepository.save(facturation);
        orderToSave.setFacturation(facturation);

        orderToSave.setEmail(orderCharge.getEmail());
        orderToSave.setTotalPrice(getTotalPriceFromOrder(orderCharge));
        orderToSave.setCreatedAt(LocalDateTime.now().toString());
        orderToSave.getArticles().addAll(articleUpdateStock(orderCharge));

        orderToSave.setChargeBalanceTransaction(orderCharge.getChargeBalanceTransaction());
        orderToSave.setChargeIdTransaction(orderCharge.getChargeIdTransaction());
        orderToSave.setChargeStatusTransaction(orderCharge.getChargeStatusTransaction());

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

    public CustomResponseKey checkOrderFront(Order orderFront){

        CustomResponseKey customResponseKey = new CustomResponseKey();


        for(Article article : orderFront.getArticles()){
            if(this.articleRepository.findOne(article.getId()) == null){
                customResponseKey.setSuccess(false);
                customResponseKey.setMessage(OneErrorCode.ERROR_MESSAGE_ARTICLE_NOT_FOUND);
                return customResponseKey;
            }
        }

        if(orderFront.getArticles().isEmpty()){
            customResponseKey.setSuccess(false);
            customResponseKey.setMessage(OneErrorCode.ERROR_MESSAGE_EMPTY_CART_FRENCH);
            return customResponseKey;
        }



        if(orderFront.getDelivery() == null){
            customResponseKey.setSuccess(false);
            customResponseKey.setMessage(OneErrorCode.ERROR_MESSAGE_DELIVERY_MISSING_FRENCH);
            return customResponseKey;
        }


        orderFront.setFacturation(this.getFacturation(orderFront));

        if(orderFront.getFacturation() == null){
            customResponseKey.setSuccess(false);
            customResponseKey.setMessage(OneErrorCode.ERROR_MESSAGE_FACTURATION_MISSING_FRENCH);
            return customResponseKey;
        }

        CustomResponse checkApiKeys = this.stripeService.checkStripeKeys();

        if(!checkApiKeys.isSuccess()){
            customResponseKey.setSuccess(checkApiKeys.isSuccess());
            customResponseKey.setMessage(checkApiKeys.getMessage());
            customResponseKey.setObject(checkApiKeys.getObject());
            return customResponseKey;
        }

        //if the current order request is ok, we return the order with the public key to create the stripe form.

        customResponseKey.setPublicKey(this.stripeService.getPublicKey());
        orderFront.setTotalPrice(this.getTotalPriceFromOrder(orderFront));
        customResponseKey.setObject(orderFront);
        customResponseKey.setMessage("");
        customResponseKey.setSuccess(true);

        return customResponseKey;
    }
}
