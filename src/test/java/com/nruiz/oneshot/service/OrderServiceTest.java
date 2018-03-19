package com.nruiz.oneshot.service;

import static org.junit.Assert.assertEquals;

import com.nruiz.oneshot.Application;
import com.nruiz.oneshot.models.Address;
import com.nruiz.oneshot.models.Article;
import com.nruiz.oneshot.models.CustomResponse;
import com.nruiz.oneshot.models.Order;
import com.nruiz.oneshot.models.Stock;
import com.nruiz.oneshot.services.ArticleService;
import com.nruiz.oneshot.services.OrderService;
import com.nruiz.oneshot.services.StockService;
import com.nruiz.oneshot.utils.OneErrorCode;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = {Application.class})
@RunWith(SpringRunner.class)
@DataJpaTest
@EnableAutoConfiguration
@ComponentScan("com.nruiz.oneshot")
public class OrderServiceTest {

    @Autowired(required = true)
    private OrderService orderService;

    @Autowired(required = true)
    private StockService stockService;

    @Autowired(required = true)
    private ArticleService articleService;

    public OrderServiceTest() {
    }

    public Order getFullOrder() {
        Order order = new Order();
        order.setEmail("test@test.fr");
        order.setPhoneNumber("phonenumber");

        Address delivery = new Address();
        delivery.setZipcode("zipcode");
        delivery.setStreet("street");
        delivery.setCity("city");
        delivery.setNumber("number");
        delivery.setLastname("lastname");
        delivery.setFirstname("firstname");
        delivery.setComplement("complement");

        order.setDelivery(delivery);

        Address facturation = new Address();
        facturation.setZipcode("zipcode");
        facturation.setStreet("street");
        facturation.setCity("city");
        facturation.setNumber("number");
        facturation.setLastname("lastname");
        facturation.setFirstname("firstname");
        facturation.setComplement("complement");

        order.setFacturation(facturation);

        List<Article> articles = new ArrayList<>();
        Stock stock = this.stockService.createStock(100);
        Article article1 = new Article();
        article1.setDescription("article 1 description");
        article1.setName("article 1 name");
        article1.setPrice(12f);
        article1.setImage("/image.png");
        article1.setStock(stock);

        Article article2 = new Article();
        article2.setDescription("article 2 description");
        article2.setName("article 2 name");
        article2.setPrice(22f);
        article2.setImage("/image-article2.png");
        article2.setStock(stock);

        article1 = this.articleService.saveArticle(article1);
        article2 = this.articleService.saveArticle(article2);

        articles.add(article1);
        articles.add(article2);

        order.getArticles().addAll(articles);

        return order;


    }

    @Test
    public void checkOrderFullOrder() {
        CustomResponse fullOrderResponse = this.orderService.checkOrderFront(this.getFullOrder());
        assertEquals(true, fullOrderResponse.isSuccess());
    }

    //TEST EMAIL
    @Test
    public void checkOrderFrontEmail() {
        Order order = this.getFullOrder();

        order.setEmail("");
        CustomResponse emailEmptyResponse = this.orderService.checkOrderFront(order);
        assertEquals(OneErrorCode.ERROR_EMAIL_MISSING, emailEmptyResponse.getMessage());
        assertEquals(false, emailEmptyResponse.isSuccess());

        order.setEmail(null);
        CustomResponse emailNullResponse = this.orderService.checkOrderFront(order);
        assertEquals(OneErrorCode.ERROR_EMAIL_MISSING, emailNullResponse.getMessage());
        assertEquals(false, emailNullResponse.isSuccess());

    }

    //TEST PHONE NUMBER
    @Test
    public void checkOrderFrontPhoneNumber() {
        Order order = this.getFullOrder();

        order.setPhoneNumber("");
        CustomResponse phoneNumberEmptyResponse = this.orderService.checkOrderFront(order);
        assertEquals(OneErrorCode.ERROR_PHONENUMBER_MISSING, phoneNumberEmptyResponse.getMessage());
        assertEquals(false, phoneNumberEmptyResponse.isSuccess());

        order.setPhoneNumber(null);
        CustomResponse phoneNumberNullResponse = this.orderService.checkOrderFront(order);
        assertEquals(OneErrorCode.ERROR_PHONENUMBER_MISSING, phoneNumberNullResponse.getMessage());
        assertEquals(false, phoneNumberNullResponse.isSuccess());
    }


    //TEST DELIVERY
    @Test
    public void checkOrderFrontDelivery() {
        Order order = this.getFullOrder();

        order.setDelivery(null);
        CustomResponse deliveryNullResponse = this.orderService.checkOrderFront(order);
        assertEquals(OneErrorCode.ERROR_MESSAGE_DELIVERY_MISSING, deliveryNullResponse.getMessage());
        assertEquals(false, deliveryNullResponse.isSuccess());

    }


    //TEST CART
    @Test
    public void checkOrderFrontCart() {
        Order order = new Order();

        order.setArticles(new ArrayList<>());
        CustomResponse cartEmptyResponse = this.orderService.checkOrderFront(order);
        assertEquals(OneErrorCode.ERROR_MESSAGE_EMPTY_CART, cartEmptyResponse.getMessage());
        assertEquals(false, cartEmptyResponse.isSuccess());

        order.setArticles(null);
        CustomResponse cartNullResponse = this.orderService.checkOrderFront(order);
        assertEquals(OneErrorCode.ERROR_MESSAGE_EMPTY_CART, cartNullResponse.getMessage());
        assertEquals(false, cartNullResponse.isSuccess());
    }

    @Test
    public void checkCityAddress() {
        Order order = this.getFullOrder();

        order.getDelivery().setCity("");
        CustomResponse deliveryCityEmptyResponse = this.orderService.checkOrderFront(order);
        assertEquals(OneErrorCode.ERROR_CITY_MISSING, deliveryCityEmptyResponse.getMessage());
        assertEquals(false, deliveryCityEmptyResponse.isSuccess());

        order.getDelivery().setCity(null);
        CustomResponse deliveryCityNullResponse = this.orderService.checkOrderFront(order);
        assertEquals(OneErrorCode.ERROR_CITY_MISSING, deliveryCityNullResponse.getMessage());
        assertEquals(false, deliveryCityNullResponse.isSuccess());

    }


    @Test
    public void checkFirstNameAddress() {
        Order order = this.getFullOrder();

        order.getDelivery().setFirstname("");
        CustomResponse deliveryFirstnameEmptyResponse = this.orderService.checkOrderFront(order);
        assertEquals(OneErrorCode.ERROR_FIRSTNAME_MISSING, deliveryFirstnameEmptyResponse.getMessage());
        assertEquals(false, deliveryFirstnameEmptyResponse.isSuccess());

        order.getDelivery().setFirstname(null);
        CustomResponse deliveryFirstnameNullResponse = this.orderService.checkOrderFront(order);
        assertEquals(OneErrorCode.ERROR_FIRSTNAME_MISSING, deliveryFirstnameNullResponse.getMessage());
        assertEquals(false, deliveryFirstnameNullResponse.isSuccess());

    }

    @Test
    public void checkLastNameAddress() {
        Order order = this.getFullOrder();

        order.getDelivery().setLastname("");
        CustomResponse deliveryLastnameEmptyResponse = this.orderService.checkOrderFront(order);
        assertEquals(OneErrorCode.ERROR_LASTNAME_MISSING, deliveryLastnameEmptyResponse.getMessage());
        assertEquals(false, deliveryLastnameEmptyResponse.isSuccess());

        order.getDelivery().setLastname(null);
        CustomResponse deliveryLastnameNullResponse = this.orderService.checkOrderFront(order);
        assertEquals(OneErrorCode.ERROR_LASTNAME_MISSING, deliveryLastnameNullResponse.getMessage());
        assertEquals(false, deliveryLastnameNullResponse.isSuccess());

    }


    @Test
    public void checkNumberAddress() {
        Order order = this.getFullOrder();

        order.getDelivery().setNumber("");
        CustomResponse deliveryNumberEmptyResponse = this.orderService.checkOrderFront(order);
        assertEquals(OneErrorCode.ERROR_NUMBER_MISSING, deliveryNumberEmptyResponse.getMessage());
        assertEquals(false, deliveryNumberEmptyResponse.isSuccess());

        order.getDelivery().setNumber(null);
        CustomResponse deliveryNumberNullResponse = this.orderService.checkOrderFront(order);
        assertEquals(OneErrorCode.ERROR_NUMBER_MISSING, deliveryNumberNullResponse.getMessage());
        assertEquals(false, deliveryNumberNullResponse.isSuccess());

    }

    @Test
    public void checkStreetAddress() {
        Order order = this.getFullOrder();

        order.getDelivery().setStreet("");
        CustomResponse deliveryStreetEmptyResponse = this.orderService.checkOrderFront(order);
        assertEquals(OneErrorCode.ERROR_STREET_MISSING, deliveryStreetEmptyResponse.getMessage());
        assertEquals(false, deliveryStreetEmptyResponse.isSuccess());

        order.getDelivery().setStreet(null);
        CustomResponse deliveryStreetNullResponse = this.orderService.checkOrderFront(order);
        assertEquals(OneErrorCode.ERROR_STREET_MISSING, deliveryStreetNullResponse.getMessage());
        assertEquals(false, deliveryStreetNullResponse.isSuccess());

    }

    @Test
    public void checkZipCodeAddress() {
        Order order = this.getFullOrder();

        order.getDelivery().setZipcode("");
        CustomResponse deliveryZipcodeEmptyResponse = this.orderService.checkOrderFront(order);
        assertEquals(OneErrorCode.ERROR_ZIPCODE_MISSING, deliveryZipcodeEmptyResponse.getMessage());
        assertEquals(false, deliveryZipcodeEmptyResponse.isSuccess());

        order.getDelivery().setZipcode(null);
        CustomResponse deliveryZipcodeNullResponse = this.orderService.checkOrderFront(order);
        assertEquals(OneErrorCode.ERROR_ZIPCODE_MISSING, deliveryZipcodeNullResponse.getMessage());
        assertEquals(false, deliveryZipcodeNullResponse.isSuccess());

    }

    //CHECK ORDER FRONT
    @Test
    public void checkOrderFrontArticleNotFound() {
        Stock stock = stockService.createStock(100);

        Article article = new Article();
        article.setDescription("description");
        article.setName("test");
        article.setPrice(12.99f);
        article.setImage("image");
        article.setStock(stock);
        article = articleService.saveArticle(article);
        article.setId(200);

        Order order = new Order();
        order.setEmail("test@test.fr");
        order.getArticles().add(article);

        CustomResponse customResponse = this.orderService.checkOrderFront(order);
        assertEquals(OneErrorCode.ERROR_MESSAGE_ARTICLE_NOT_FOUND, customResponse.getMessage());
        assertEquals(false, customResponse.isSuccess());
    }


    @Test
    public void checkOrderFrontWithoutFacturation() {
        Order order = this.getFullOrder();
        order.setFacturation(null);

        CustomResponse customResponse = this.orderService.checkOrderFront(order);
        assertEquals(true, customResponse.isSuccess());
    }


    @Test
    public void checkOrderFrontWithFacturationNotCity() {
        Order order = this.getFullOrder();

        order.getFacturation().setCity("");
        CustomResponse facturationCityEmptyResponse = this.orderService.checkOrderFront(order);
        assertEquals(OneErrorCode.ERROR_CITY_MISSING, facturationCityEmptyResponse.getMessage());
        assertEquals(false, facturationCityEmptyResponse.isSuccess());

        order.getFacturation().setCity(null);
        CustomResponse facturationCityNullResponse = this.orderService.checkOrderFront(order);
        assertEquals(OneErrorCode.ERROR_CITY_MISSING, facturationCityNullResponse.getMessage());
        assertEquals(false, facturationCityNullResponse.isSuccess());

    }

    @Test
    public void checkOrderFrontWithFacturationNotZipcode() {
        Order order = this.getFullOrder();

        order.getFacturation().setZipcode("");
        CustomResponse facturationZipcodeEmptyResponse = this.orderService.checkOrderFront(order);
        assertEquals(OneErrorCode.ERROR_ZIPCODE_MISSING, facturationZipcodeEmptyResponse.getMessage());
        assertEquals(false, facturationZipcodeEmptyResponse.isSuccess());

        order.getFacturation().setZipcode(null);
        CustomResponse facturationZipcodeNullResponse = this.orderService.checkOrderFront(order);
        assertEquals(OneErrorCode.ERROR_ZIPCODE_MISSING, facturationZipcodeNullResponse.getMessage());
        assertEquals(false, facturationZipcodeNullResponse.isSuccess());

    }

    @Test
    public void checkOrderFrontWithFacturationNotStreet() {
        Order order = this.getFullOrder();

        order.getFacturation().setStreet("");
        CustomResponse facturationStreetEmptyResponse = this.orderService.checkOrderFront(order);
        assertEquals(OneErrorCode.ERROR_STREET_MISSING, facturationStreetEmptyResponse.getMessage());
        assertEquals(false, facturationStreetEmptyResponse.isSuccess());

        order.getFacturation().setStreet(null);
        CustomResponse facturationStreetNullResponse = this.orderService.checkOrderFront(order);
        assertEquals(OneErrorCode.ERROR_STREET_MISSING, facturationStreetNullResponse.getMessage());
        assertEquals(false, facturationStreetNullResponse.isSuccess());
    }

    @Test
    public void checkOrderFrontWithFacturationNotNumber() {
        Order order = this.getFullOrder();

        order.getFacturation().setNumber("");
        CustomResponse facturationNumberEmptyResponse = this.orderService.checkOrderFront(order);
        assertEquals(OneErrorCode.ERROR_NUMBER_MISSING, facturationNumberEmptyResponse.getMessage());
        assertEquals(false, facturationNumberEmptyResponse.isSuccess());

        order.getFacturation().setNumber(null);
        CustomResponse facturationNumberNullResponse = this.orderService.checkOrderFront(order);
        assertEquals(OneErrorCode.ERROR_NUMBER_MISSING, facturationNumberNullResponse.getMessage());
        assertEquals(false, facturationNumberNullResponse.isSuccess());

    }

    @Test
    public void checkOrderFrontWithFacturationNotLastname() {
        Order order = this.getFullOrder();

        order.getFacturation().setLastname("");
        CustomResponse facturationLastnameEmptyResponse = this.orderService.checkOrderFront(order);
        assertEquals(OneErrorCode.ERROR_LASTNAME_MISSING, facturationLastnameEmptyResponse.getMessage());
        assertEquals(false, facturationLastnameEmptyResponse.isSuccess());

        order.getFacturation().setLastname(null);
        CustomResponse facturationLastnameNullResponse = this.orderService.checkOrderFront(order);
        assertEquals(OneErrorCode.ERROR_LASTNAME_MISSING, facturationLastnameNullResponse.getMessage());
        assertEquals(false, facturationLastnameNullResponse.isSuccess());

    }

    @Test
    public void checkOrderFrontWithFacturationNotFirstname() {
        Order order = this.getFullOrder();

        order.getFacturation().setFirstname("");
        CustomResponse facturationFirstnameEmptyResponse = this.orderService.checkOrderFront(order);
        assertEquals(OneErrorCode.ERROR_FIRSTNAME_MISSING, facturationFirstnameEmptyResponse.getMessage());
        assertEquals(false, facturationFirstnameEmptyResponse.isSuccess());

        order.getFacturation().setFirstname(null);
        CustomResponse facturationFirstnameNullResponse = this.orderService.checkOrderFront(order);
        assertEquals(OneErrorCode.ERROR_FIRSTNAME_MISSING, facturationFirstnameNullResponse.getMessage());
        assertEquals(false, facturationFirstnameNullResponse.isSuccess());

    }

    @Test
    public void checkOrderFrontTotalPriceWrong(){
        Order order =  this.getFullOrder();
        order.setTotalPrice("-1");

        CustomResponse customResponse = this.orderService.checkOrderFront(order);
        assertEquals(true, customResponse.isSuccess());
    }
}