package com.nruiz.oneshot.services;


import com.nruiz.oneshot.models.Address;
import com.nruiz.oneshot.models.Article;
import com.nruiz.oneshot.models.ChargeRequestOrder;
import com.nruiz.oneshot.models.CustomResponse;
import com.nruiz.oneshot.models.Order;
import com.nruiz.oneshot.models.Stock;
import com.nruiz.oneshot.repositories.AddressRepository;
import com.nruiz.oneshot.repositories.ArticleRepository;
import com.nruiz.oneshot.repositories.OrderRepository;
import com.nruiz.oneshot.repositories.StockRepository;
import com.nruiz.oneshot.utils.OneErrorCode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class OrderService {



    private OrderRepository orderRepository;
    private AddressRepository addressRepository;
    private ArticleRepository articleRepository;
    private StockRepository stockRepository;

    public OrderService(OrderRepository orderRepository,
                        AddressRepository addressRepository, ArticleRepository articleRepository,
                        StockRepository stockRepository) {
        this.orderRepository = orderRepository;
        this.addressRepository = addressRepository;
        this.articleRepository = articleRepository;
        this.stockRepository = stockRepository;
    }

    public CustomResponse saveNewOrder(ChargeRequestOrder orderCharge) {
        CustomResponse customResponse = new CustomResponse();
        customResponse.setSuccess(false);

        Order orderToSave = new Order();

        Address delivery  = orderCharge.getOrder().getDelivery();
        delivery = this.addressRepository.save(delivery);
        orderToSave.setDelivery(delivery);


        Address facturation = this.getFacturation(orderCharge.getOrder());
        facturation = this.addressRepository.save(facturation);
        orderToSave.setFacturation(facturation);
        orderToSave.setEmail(orderCharge.getOrder().getEmail());

        float totalPrice = this.getTotalPriceFromOrder(orderCharge.getOrder());
        orderToSave.setTotalPrice(totalPrice);

        orderToSave.setCreatedAt(LocalDateTime.now().toString());
        orderToSave.getArticles().addAll(articleUpdateStock(orderCharge.getOrder()));


        //@TODO dont forget to check that
        orderToSave.setChargeBalanceTransaction(orderCharge.getOrder().getChargeBalanceTransaction());
        orderToSave.setChargeIdTransaction(orderCharge.getOrder().getChargeIdTransaction());
        orderToSave.setChargeStatusTransaction(orderCharge.getOrder().getChargeStatusTransaction());

        orderToSave = orderRepository.save(orderToSave);

        if(orderToSave == null){
            customResponse.setMessage(OneErrorCode.ERROR_MESSAGE_WHILE_SAVE_ORDER);
            return customResponse;
        }
        customResponse.setSuccess(true);
        customResponse.setObject(orderToSave);
        return customResponse;
    }

    private CustomResponse checkAddress(CustomResponse customResponse, Address delivery) {
        if(delivery.getCity() == null || delivery.getCity().isEmpty()){
            customResponse.setMessage(OneErrorCode.ERROR_CITY_MISSING);
            customResponse.setSuccess(false);
            return customResponse;
        }

        if(delivery.getFirstname() == null || delivery.getFirstname().isEmpty()){
            customResponse.setMessage(OneErrorCode.ERROR_FIRSTNAME_MISSING);
            customResponse.setSuccess(false);
            return customResponse;
        }

        if(delivery.getLastname() == null || delivery.getLastname().isEmpty()){
            customResponse.setMessage(OneErrorCode.ERROR_LASTNAME_MISSING);
            customResponse.setSuccess(false);
            return customResponse;
        }

        if(delivery.getNumber() == null || delivery.getNumber().isEmpty()){
            customResponse.setMessage(OneErrorCode.ERROR_NUMBER_MISSING);
            customResponse.setSuccess(false);
            return customResponse;
        }
        if(delivery.getStreet() == null || delivery.getStreet().isEmpty()){
            customResponse.setMessage(OneErrorCode.ERROR_STREET_MISSING);
            customResponse.setSuccess(false);
            return customResponse;
        }

        if(delivery.getZipcode() == null || delivery.getZipcode().isEmpty()){
            customResponse.setMessage(OneErrorCode.ERROR_ZIPCODE_MISSING);
            customResponse.setSuccess(false);
            return customResponse;
        }

        customResponse.setSuccess(true);
        return customResponse;
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

    public float getTotalPriceFromOrder(Order orderFront) {
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
        customResponse.setSuccess(false);

        if(orderFront.getEmail() == null ||orderFront.getEmail().isEmpty()){
            customResponse.setMessage(OneErrorCode.ERROR_EMAIL_MISSING);
            return customResponse;
        }

        if(orderFront.getArticles().isEmpty()){
            customResponse.setMessage(OneErrorCode.ERROR_MESSAGE_EMPTY_CART);
            return customResponse;
        }

        for(Article article : orderFront.getArticles()){
            if(this.articleRepository.findOne(article.getId()) == null){
                customResponse.setMessage(OneErrorCode.ERROR_MESSAGE_ARTICLE_NOT_FOUND);
                return customResponse;
            }
        }

        if(orderFront.getDelivery() == null){
            customResponse.setMessage(OneErrorCode.ERROR_MESSAGE_DELIVERY_MISSING);
            return customResponse;
        }

        CustomResponse checkAddressDeliveryResponse = checkAddress(customResponse, orderFront.getDelivery());

        if(!checkAddressDeliveryResponse.isSuccess()){
            return checkAddressDeliveryResponse;
        }


        orderFront.setFacturation(this.getFacturation(orderFront));


        CustomResponse checkAddressFacturationResponse = checkAddress(customResponse, orderFront.getFacturation());
        if(!checkAddressFacturationResponse.isSuccess()){
            return checkAddressFacturationResponse;
        }

        float totalPrice = this.getTotalPriceFromOrder(orderFront);
        if(totalPrice == 0f || totalPrice < 0f){
            customResponse.setMessage(OneErrorCode.ERROR_TOTALPRICE_NULL);
            return customResponse;
        }

        orderFront.setTotalPrice(this.getTotalPriceFromOrder(orderFront));

        customResponse.setObject(orderFront);
        customResponse.setMessage("");
        customResponse.setSuccess(true);

        return customResponse;
    }
}
