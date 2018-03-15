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
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = {Application.class})
@RunWith(SpringRunner.class)
@EnableAutoConfiguration
@ComponentScan("com.nruiz.shop")
public class OrderServiceTest {

    @Autowired
    private OrderService orderService;

    @Autowired
    private StockService stockService;

    @Autowired
    private ArticleService articleService;



    //TEST EMAIL
    @Test
    public void checkOrderFrontShouldFailedEmailNull(){
        Order order = new Order();
        CustomResponse customResponse = this.orderService.checkOrderFront(order);
        assertEquals(OneErrorCode.ERROR_EMAIL_MISSING, customResponse.getMessage());
        assertEquals(false, customResponse.isSuccess());
    }

    @Test
    public void checkOrderFrontShouldFailedEmailEmpty(){
        Order order = new Order();
        order.setEmail("");
        CustomResponse customResponse = this.orderService.checkOrderFront(order);
        assertEquals(OneErrorCode.ERROR_EMAIL_MISSING, customResponse.getMessage());
        assertEquals(false, customResponse.isSuccess());
    }

    //TEST CART
    @Test
    public void checkOrderFrontCartShouldBeEmpty(){
        Order order = new Order();
        order.setEmail("test@test.fr");
        CustomResponse customResponse = this.orderService.checkOrderFront(order);
        assertEquals(OneErrorCode.ERROR_MESSAGE_EMPTY_CART, customResponse.getMessage());
        assertEquals(false, customResponse.isSuccess());
    }

    //CHECK ORDER FRONT
    @Test
    public void checkOrderFrontArticleNotFound(){
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
    public void checkOrderFrontDeliveryNull(){
        Stock stock = stockService.createStock(100);

        Article article = new Article();
        article.setDescription("description");
        article.setName("test");
        article.setPrice(12.99f);
        article.setImage("image");
        article.setStock(stock);
        article = articleService.saveArticle(article);

        Order order = new Order();
        order.setEmail("test@test.fr");
        order.getArticles().add(article);


        CustomResponse customResponse = this.orderService.checkOrderFront(order);
        assertEquals(OneErrorCode.ERROR_MESSAGE_DELIVERY_MISSING, customResponse.getMessage());
        assertEquals(false, customResponse.isSuccess());
    }

    @Test
    public void checkOrderFrontDeliveryCityEmpty(){
        Stock stock = stockService.createStock(100);

        Article article = new Article();
        article.setDescription("description");
        article.setName("test");
        article.setPrice(12.99f);
        article.setImage("image");
        article.setStock(stock);
        article = articleService.saveArticle(article);


        Address delivery = new Address();
        delivery.setCity("");

        Order order = new Order();
        order.setEmail("test@test.fr");
        order.getArticles().add(article);
        order.setDelivery(delivery);


        CustomResponse customResponse = this.orderService.checkOrderFront(order);
        assertEquals(OneErrorCode.ERROR_CITY_MISSING, customResponse.getMessage());
        assertEquals(false, customResponse.isSuccess());
    }

    @Test
    public void checkOrderFrontDeliveryCityNull(){
        Stock stock = stockService.createStock(100);

        Article article = new Article();
        article.setDescription("description");
        article.setName("test");
        article.setPrice(12.99f);
        article.setImage("image");
        article.setStock(stock);
        article = articleService.saveArticle(article);


        Address delivery = new Address();

        Order order = new Order();
        order.setEmail("test@test.fr");
        order.getArticles().add(article);
        order.setDelivery(delivery);


        CustomResponse customResponse = this.orderService.checkOrderFront(order);
        assertEquals(OneErrorCode.ERROR_CITY_MISSING, customResponse.getMessage());
        assertEquals(false, customResponse.isSuccess());
    }



    @Test
    public void checkOrderFrontDeliveryFirstnameEmpty(){
        Stock stock = stockService.createStock(100);

        Article article = new Article();
        article.setDescription("description");
        article.setName("test");
        article.setPrice(12.99f);
        article.setImage("image");
        article.setStock(stock);
        article = articleService.saveArticle(article);


        Address delivery = new Address();
        delivery.setCity("city");
        delivery.setFirstname("");
        Order order = new Order();
        order.setEmail("test@test.fr");
        order.getArticles().add(article);
        order.setDelivery(delivery);


        CustomResponse customResponse = this.orderService.checkOrderFront(order);
        assertEquals(OneErrorCode.ERROR_FIRSTNAME_MISSING, customResponse.getMessage());
        assertEquals(false, customResponse.isSuccess());
    }


    @Test
    public void checkOrderFrontDeliveryFirstnameNull(){
        Stock stock = stockService.createStock(100);

        Article article = new Article();
        article.setDescription("description");
        article.setName("test");
        article.setPrice(12.99f);
        article.setImage("image");
        article.setStock(stock);
        article = articleService.saveArticle(article);


        Address delivery = new Address();
        delivery.setCity("city");
        Order order = new Order();
        order.setEmail("test@test.fr");
        order.getArticles().add(article);
        order.setDelivery(delivery);


        CustomResponse customResponse = this.orderService.checkOrderFront(order);
        assertEquals(OneErrorCode.ERROR_FIRSTNAME_MISSING, customResponse.getMessage());
        assertEquals(false, customResponse.isSuccess());
    }


    @Test
    public void checkOrderFrontDeliveryLastnameEmpty(){
        Stock stock = stockService.createStock(100);

        Article article = new Article();
        article.setDescription("description");
        article.setName("test");
        article.setPrice(12.99f);
        article.setImage("image");
        article.setStock(stock);
        article = articleService.saveArticle(article);


        Address delivery = new Address();
        delivery.setCity("city");
        delivery.setFirstname("firsntame");
        delivery.setLastname("");
        Order order = new Order();
        order.setEmail("test@test.fr");
        order.getArticles().add(article);
        order.setDelivery(delivery);


        CustomResponse customResponse = this.orderService.checkOrderFront(order);
        assertEquals(OneErrorCode.ERROR_LASTNAME_MISSING, customResponse.getMessage());
        assertEquals(false, customResponse.isSuccess());
    }


    @Test
    public void checkOrderFrontDeliveryLastnameNull(){
        Stock stock = stockService.createStock(100);

        Article article = new Article();
        article.setDescription("description");
        article.setName("test");
        article.setPrice(12.99f);
        article.setImage("image");
        article.setStock(stock);
        article = articleService.saveArticle(article);


        Address delivery = new Address();
        delivery.setCity("city");
        delivery.setFirstname("firsntame");
        Order order = new Order();
        order.setEmail("test@test.fr");
        order.getArticles().add(article);
        order.setDelivery(delivery);


        CustomResponse customResponse = this.orderService.checkOrderFront(order);
        assertEquals(OneErrorCode.ERROR_LASTNAME_MISSING, customResponse.getMessage());
        assertEquals(false, customResponse.isSuccess());
    }


    @Test
    public void checkOrderFrontDeliveryNumberEmpty(){
        Stock stock = stockService.createStock(100);

        Article article = new Article();
        article.setDescription("description");
        article.setName("test");
        article.setPrice(12.99f);
        article.setImage("image");
        article.setStock(stock);
        article = articleService.saveArticle(article);


        Address delivery = new Address();
        delivery.setCity("city");
        delivery.setFirstname("firsntame");
        delivery.setLastname("lastname");
        delivery.setNumber("");
        Order order = new Order();
        order.setEmail("test@test.fr");
        order.getArticles().add(article);
        order.setDelivery(delivery);


        CustomResponse customResponse = this.orderService.checkOrderFront(order);
        assertEquals(OneErrorCode.ERROR_NUMBER_MISSING, customResponse.getMessage());
        assertEquals(false, customResponse.isSuccess());
    }


    @Test
    public void checkOrderFrontDeliveryNumberNull(){
        Stock stock = stockService.createStock(100);

        Article article = new Article();
        article.setDescription("description");
        article.setName("test");
        article.setPrice(12.99f);
        article.setImage("image");
        article.setStock(stock);
        article = articleService.saveArticle(article);


        Address delivery = new Address();
        delivery.setCity("city");
        delivery.setFirstname("firsntame");
        delivery.setLastname("lastname");
        Order order = new Order();
        order.setEmail("test@test.fr");
        order.getArticles().add(article);
        order.setDelivery(delivery);


        CustomResponse customResponse = this.orderService.checkOrderFront(order);
        assertEquals(OneErrorCode.ERROR_NUMBER_MISSING, customResponse.getMessage());
        assertEquals(false, customResponse.isSuccess());
    }



    @Test
    public void checkOrderFrontDeliverySteetEmpty(){
        Stock stock = stockService.createStock(100);

        Article article = new Article();
        article.setDescription("description");
        article.setName("test");
        article.setPrice(12.99f);
        article.setImage("image");
        article.setStock(stock);
        article = articleService.saveArticle(article);


        Address delivery = new Address();
        delivery.setCity("city");
        delivery.setFirstname("firsntame");
        delivery.setLastname("lastname");
        delivery.setNumber("10");
        delivery.setStreet("");
        Order order = new Order();
        order.setEmail("test@test.fr");
        order.getArticles().add(article);
        order.setDelivery(delivery);


        CustomResponse customResponse = this.orderService.checkOrderFront(order);
        assertEquals(OneErrorCode.ERROR_STREET_MISSING, customResponse.getMessage());
        assertEquals(false, customResponse.isSuccess());
    }

    @Test
    public void checkOrderFrontDeliverySteetNull(){
        Stock stock = stockService.createStock(100);

        Article article = new Article();
        article.setDescription("description");
        article.setName("test");
        article.setPrice(12.99f);
        article.setImage("image");
        article.setStock(stock);
        article = articleService.saveArticle(article);


        Address delivery = new Address();
        delivery.setCity("city");
        delivery.setFirstname("firsntame");
        delivery.setLastname("lastname");
        delivery.setNumber("10");
        Order order = new Order();
        order.setEmail("test@test.fr");
        order.getArticles().add(article);
        order.setDelivery(delivery);


        CustomResponse customResponse = this.orderService.checkOrderFront(order);
        assertEquals(OneErrorCode.ERROR_STREET_MISSING, customResponse.getMessage());
        assertEquals(false, customResponse.isSuccess());
    }



    @Test
    public void checkOrderFrontDeliveryZipcodeEmpty(){
        Stock stock = stockService.createStock(100);

        Article article = new Article();
        article.setDescription("description");
        article.setName("test");
        article.setPrice(12.99f);
        article.setImage("image");
        article.setStock(stock);
        article = articleService.saveArticle(article);


        Address delivery = new Address();
        delivery.setCity("city");
        delivery.setFirstname("firsntame");
        delivery.setLastname("lastname");
        delivery.setNumber("10");
        delivery.setStreet("street");
        delivery.setZipcode("");
        Order order = new Order();
        order.setEmail("test@test.fr");
        order.getArticles().add(article);
        order.setDelivery(delivery);


        CustomResponse customResponse = this.orderService.checkOrderFront(order);
        assertEquals(OneErrorCode.ERROR_ZIPCODE_MISSING, customResponse.getMessage());
        assertEquals(false, customResponse.isSuccess());
    }

    @Test
    public void checkOrderFrontDeliveryZipcodeNull(){
        Stock stock = stockService.createStock(100);

        Article article = new Article();
        article.setDescription("description");
        article.setName("test");
        article.setPrice(12.99f);
        article.setImage("image");
        article.setStock(stock);
        article = articleService.saveArticle(article);


        Address delivery = new Address();
        delivery.setCity("city");
        delivery.setFirstname("firsntame");
        delivery.setLastname("lastname");
        delivery.setNumber("10");
        delivery.setStreet("street");
        Order order = new Order();
        order.setEmail("test@test.fr");
        order.getArticles().add(article);
        order.setDelivery(delivery);


        CustomResponse customResponse = this.orderService.checkOrderFront(order);
        assertEquals(OneErrorCode.ERROR_ZIPCODE_MISSING, customResponse.getMessage());
        assertEquals(false, customResponse.isSuccess());
    }

    @Test
    public void checkOrderFrontFacturationCityNull(){
        Stock stock = stockService.createStock(100);

        Article article = new Article();
        article.setDescription("description");
        article.setName("test");
        article.setPrice(12.99f);
        article.setImage("image");
        article.setStock(stock);
        article = articleService.saveArticle(article);


        Address delivery = new Address();
        delivery.setCity("city");
        delivery.setFirstname("firsntame");
        delivery.setLastname("lastname");
        delivery.setNumber("10");
        delivery.setStreet("street");
        delivery.setZipcode("65000");

        Address facturation = new Address();

        Order order = new Order();
        order.setEmail("test@test.fr");
        order.getArticles().add(article);
        order.setDelivery(delivery);
        order.setFacturation(facturation);


        CustomResponse customResponse = this.orderService.checkOrderFront(order);
        assertEquals(OneErrorCode.ERROR_CITY_MISSING, customResponse.getMessage());
        assertEquals(false, customResponse.isSuccess());
    }

    @Test
    public void checkOrderFrontFacturationCityEmpty(){
        Stock stock = stockService.createStock(100);

        Article article = new Article();
        article.setDescription("description");
        article.setName("test");
        article.setPrice(12.99f);
        article.setImage("image");
        article.setStock(stock);
        article = articleService.saveArticle(article);


        Address delivery = new Address();
        delivery.setCity("city");
        delivery.setFirstname("firsntame");
        delivery.setLastname("lastname");
        delivery.setNumber("10");
        delivery.setStreet("street");
        delivery.setZipcode("65000");

        Address facturation = new Address();
        facturation.setCity("");

        Order order = new Order();
        order.setEmail("test@test.fr");
        order.getArticles().add(article);
        order.setDelivery(delivery);
        order.setFacturation(facturation);


        CustomResponse customResponse = this.orderService.checkOrderFront(order);
        assertEquals(OneErrorCode.ERROR_CITY_MISSING, customResponse.getMessage());
        assertEquals(false, customResponse.isSuccess());
    }


    @Test
    public void checkOrderFrontFacturationFirstnameNull(){
        Stock stock = stockService.createStock(100);

        Article article = new Article();
        article.setDescription("description");
        article.setName("test");
        article.setPrice(12.99f);
        article.setImage("image");
        article.setStock(stock);
        article = articleService.saveArticle(article);


        Address delivery = new Address();
        delivery.setCity("city");
        delivery.setFirstname("firsntame");
        delivery.setLastname("lastname");
        delivery.setNumber("10");
        delivery.setStreet("street");
        delivery.setZipcode("65000");

        Address facturation = new Address();

        Order order = new Order();
        order.setEmail("test@test.fr");
        order.getArticles().add(article);
        order.setDelivery(delivery);
        order.setFacturation(facturation);
        facturation.setCity("city");

        CustomResponse customResponse = this.orderService.checkOrderFront(order);
        assertEquals(OneErrorCode.ERROR_FIRSTNAME_MISSING, customResponse.getMessage());
        assertEquals(false, customResponse.isSuccess());
    }

    @Test
    public void checkOrderFrontFacturationFirstnameEmpty(){
        Stock stock = stockService.createStock(100);

        Article article = new Article();
        article.setDescription("description");
        article.setName("test");
        article.setPrice(12.99f);
        article.setImage("image");
        article.setStock(stock);
        article = articleService.saveArticle(article);


        Address delivery = new Address();
        delivery.setCity("city");
        delivery.setFirstname("firsntame");
        delivery.setLastname("lastname");
        delivery.setNumber("10");
        delivery.setStreet("street");
        delivery.setZipcode("65000");

        Address facturation = new Address();
        facturation.setCity("city");
        facturation.setFirstname("");

        Order order = new Order();
        order.setEmail("test@test.fr");
        order.getArticles().add(article);
        order.setDelivery(delivery);
        order.setFacturation(facturation);


        CustomResponse customResponse = this.orderService.checkOrderFront(order);
        assertEquals(OneErrorCode.ERROR_FIRSTNAME_MISSING, customResponse.getMessage());
        assertEquals(false, customResponse.isSuccess());
    }

    @Test
    public void checkOrderFrontFacturationLastnameNull(){
        Stock stock = stockService.createStock(100);

        Article article = new Article();
        article.setDescription("description");
        article.setName("test");
        article.setPrice(12.99f);
        article.setImage("image");
        article.setStock(stock);
        article = articleService.saveArticle(article);


        Address delivery = new Address();
        delivery.setCity("city");
        delivery.setFirstname("firsntame");
        delivery.setLastname("lastname");
        delivery.setNumber("10");
        delivery.setStreet("street");
        delivery.setZipcode("65000");

        Address facturation = new Address();

        Order order = new Order();
        order.setEmail("test@test.fr");
        order.getArticles().add(article);
        order.setDelivery(delivery);
        order.setFacturation(facturation);
        facturation.setCity("city");
        facturation.setFirstname("firsntame");

        CustomResponse customResponse = this.orderService.checkOrderFront(order);
        assertEquals(OneErrorCode.ERROR_LASTNAME_MISSING, customResponse.getMessage());
        assertEquals(false, customResponse.isSuccess());
    }

    @Test
    public void checkOrderFrontFacturationLastnameEmpty(){
        Stock stock = stockService.createStock(100);

        Article article = new Article();
        article.setDescription("description");
        article.setName("test");
        article.setPrice(12.99f);
        article.setImage("image");
        article.setStock(stock);
        article = articleService.saveArticle(article);


        Address delivery = new Address();
        delivery.setCity("city");
        delivery.setFirstname("firsntame");
        delivery.setLastname("lastname");
        delivery.setNumber("10");
        delivery.setStreet("street");
        delivery.setZipcode("65000");

        Address facturation = new Address();
        facturation.setCity("city");
        facturation.setFirstname("firstname");
        facturation.setLastname("");


        Order order = new Order();
        order.setEmail("test@test.fr");
        order.getArticles().add(article);
        order.setDelivery(delivery);
        order.setFacturation(facturation);


        CustomResponse customResponse = this.orderService.checkOrderFront(order);
        assertEquals(OneErrorCode.ERROR_LASTNAME_MISSING, customResponse.getMessage());
        assertEquals(false, customResponse.isSuccess());
    }

    @Test
    public void checkOrderFrontFacturationNumberNull(){
        Stock stock = stockService.createStock(100);

        Article article = new Article();
        article.setDescription("description");
        article.setName("test");
        article.setPrice(12.99f);
        article.setImage("image");
        article.setStock(stock);
        article = articleService.saveArticle(article);


        Address delivery = new Address();
        delivery.setCity("city");
        delivery.setFirstname("firsntame");
        delivery.setLastname("lastname");
        delivery.setNumber("10");
        delivery.setStreet("street");
        delivery.setZipcode("65000");

        Address facturation = new Address();

        Order order = new Order();
        order.setEmail("test@test.fr");
        order.getArticles().add(article);
        order.setDelivery(delivery);
        order.setFacturation(facturation);
        facturation.setCity("city");
        facturation.setFirstname("firstname");
        facturation.setLastname("lastname");

        CustomResponse customResponse = this.orderService.checkOrderFront(order);
        assertEquals(OneErrorCode.ERROR_NUMBER_MISSING, customResponse.getMessage());
        assertEquals(false, customResponse.isSuccess());
    }

    @Test
    public void checkOrderFrontFacturationNumberEmpty(){
        Stock stock = stockService.createStock(100);

        Article article = new Article();
        article.setDescription("description");
        article.setName("test");
        article.setPrice(12.99f);
        article.setImage("image");
        article.setStock(stock);
        article = articleService.saveArticle(article);


        Address delivery = new Address();
        delivery.setCity("city");
        delivery.setFirstname("firsntame");
        delivery.setLastname("lastname");
        delivery.setNumber("10");
        delivery.setStreet("street");
        delivery.setZipcode("65000");

        Address facturation = new Address();
        facturation.setCity("city");
        facturation.setFirstname("firstname");
        facturation.setLastname("lastname");
        facturation.setNumber("");


        Order order = new Order();
        order.setEmail("test@test.fr");
        order.getArticles().add(article);
        order.setDelivery(delivery);
        order.setFacturation(facturation);


        CustomResponse customResponse = this.orderService.checkOrderFront(order);
        assertEquals(OneErrorCode.ERROR_NUMBER_MISSING, customResponse.getMessage());
        assertEquals(false, customResponse.isSuccess());
    }

    @Test
    public void checkOrderFrontFacturationStreetNull(){
        Stock stock = stockService.createStock(100);

        Article article = new Article();
        article.setDescription("description");
        article.setName("test");
        article.setPrice(12.99f);
        article.setImage("image");
        article.setStock(stock);
        article = articleService.saveArticle(article);


        Address delivery = new Address();
        delivery.setCity("city");
        delivery.setFirstname("firsntame");
        delivery.setLastname("lastname");
        delivery.setNumber("10");
        delivery.setStreet("street");
        delivery.setZipcode("65000");

        Address facturation = new Address();

        Order order = new Order();
        order.setEmail("test@test.fr");
        order.getArticles().add(article);
        order.setDelivery(delivery);
        order.setFacturation(facturation);
        facturation.setCity("city");
        facturation.setFirstname("firstname");
        facturation.setLastname("lastname");
        facturation.setNumber("100");

        CustomResponse customResponse = this.orderService.checkOrderFront(order);
        assertEquals(OneErrorCode.ERROR_STREET_MISSING, customResponse.getMessage());
        assertEquals(false, customResponse.isSuccess());
    }

    @Test
    public void checkOrderFrontFacturationStreetEmpty(){
        Stock stock = stockService.createStock(100);

        Article article = new Article();
        article.setDescription("description");
        article.setName("test");
        article.setPrice(12.99f);
        article.setImage("image");
        article.setStock(stock);
        article = articleService.saveArticle(article);


        Address delivery = new Address();
        delivery.setCity("city");
        delivery.setFirstname("firsntame");
        delivery.setLastname("lastname");
        delivery.setNumber("10");
        delivery.setStreet("street");
        delivery.setZipcode("65000");

        Address facturation = new Address();

        Order order = new Order();
        order.setEmail("test@test.fr");
        order.getArticles().add(article);
        order.setDelivery(delivery);
        order.setFacturation(facturation);
        facturation.setCity("city");
        facturation.setFirstname("firstname");
        facturation.setLastname("lastname");
        facturation.setNumber("100");
        facturation.setStreet("");

        CustomResponse customResponse = this.orderService.checkOrderFront(order);
        assertEquals(OneErrorCode.ERROR_STREET_MISSING, customResponse.getMessage());
        assertEquals(false, customResponse.isSuccess());
    }


    @Test
    public void checkOrderFrontFacturationZipcodeNull(){
        Stock stock = stockService.createStock(100);

        Article article = new Article();
        article.setDescription("description");
        article.setName("test");
        article.setPrice(12.99f);
        article.setImage("image");
        article.setStock(stock);
        article = articleService.saveArticle(article);


        Address delivery = new Address();
        delivery.setCity("city");
        delivery.setFirstname("firsntame");
        delivery.setLastname("lastname");
        delivery.setNumber("10");
        delivery.setStreet("street");
        delivery.setZipcode("65000");

        Address facturation = new Address();

        Order order = new Order();
        order.setEmail("test@test.fr");
        order.getArticles().add(article);
        order.setDelivery(delivery);
        order.setFacturation(facturation);
        facturation.setCity("city");
        facturation.setFirstname("firstname");
        facturation.setLastname("lastname");
        facturation.setNumber("100");
        facturation.setStreet("street");

        CustomResponse customResponse = this.orderService.checkOrderFront(order);
        assertEquals(OneErrorCode.ERROR_ZIPCODE_MISSING, customResponse.getMessage());
        assertEquals(false, customResponse.isSuccess());
    }


    @Test
    public void checkOrderFrontFacturationZipcodeEmpty(){
        Stock stock = stockService.createStock(100);

        Article article = new Article();
        article.setDescription("description");
        article.setName("test");
        article.setPrice(12.99f);
        article.setImage("image");
        article.setStock(stock);
        article = articleService.saveArticle(article);


        Address delivery = new Address();
        delivery.setCity("city");
        delivery.setFirstname("firsntame");
        delivery.setLastname("lastname");
        delivery.setNumber("10");
        delivery.setStreet("street");
        delivery.setZipcode("65000");

        Address facturation = new Address();

        Order order = new Order();
        order.setEmail("test@test.fr");
        order.getArticles().add(article);
        order.setDelivery(delivery);
        order.setFacturation(facturation);
        facturation.setCity("city");
        facturation.setFirstname("firstname");
        facturation.setLastname("lastname");
        facturation.setNumber("100");
        facturation.setStreet("street");
        facturation.setZipcode("");

        CustomResponse customResponse = this.orderService.checkOrderFront(order);
        assertEquals(OneErrorCode.ERROR_ZIPCODE_MISSING, customResponse.getMessage());
        assertEquals(false, customResponse.isSuccess());
    }


    @Test
    public void checkOrderFrontOk(){
        Stock stock = stockService.createStock(100);

        Article article = new Article();
        article.setDescription("description");
        article.setName("test");
        article.setPrice(12.99f);
        article.setImage("image");
        article.setStock(stock);
        article = articleService.saveArticle(article);


        Address delivery = new Address();
        delivery.setCity("city");
        delivery.setFirstname("firsntame");
        delivery.setLastname("lastname");
        delivery.setNumber("10");
        delivery.setStreet("street");
        delivery.setZipcode("65000");

        Address facturation = new Address();

        Order order = new Order();
        order.setEmail("test@test.fr");
        order.getArticles().add(article);
        order.setDelivery(delivery);
        order.setFacturation(facturation);
        facturation.setCity("city");
        facturation.setFirstname("firstname");
        facturation.setLastname("lastname");
        facturation.setNumber("100");
        facturation.setStreet("street");
        facturation.setZipcode("33000");

        CustomResponse customResponse = this.orderService.checkOrderFront(order);
        assertEquals(order, customResponse.getObject());
        assertEquals("", customResponse.getMessage());
        assertEquals(true, customResponse.isSuccess());

    }

    @Test
    public void checkOrderFrontOkWithoutFacturation(){
        Stock stock = stockService.createStock(100);

        Article article = new Article();
        article.setDescription("description");
        article.setName("test");
        article.setPrice(12.99f);
        article.setImage("image");
        article.setStock(stock);
        article = articleService.saveArticle(article);


        Address delivery = new Address();
        delivery.setCity("city");
        delivery.setFirstname("firsntame");
        delivery.setLastname("lastname");
        delivery.setNumber("10");
        delivery.setStreet("street");
        delivery.setZipcode("65000");


        Order order = new Order();
        order.setEmail("test@test.fr");
        order.getArticles().add(article);
        order.setDelivery(delivery);

        CustomResponse customResponse = this.orderService.checkOrderFront(order);
        assertEquals(order, customResponse.getObject());
        assertEquals("", customResponse.getMessage());
        assertEquals(true, customResponse.isSuccess());

    }




//    @Test
//    public void checkOrderFrontCartShouldBeNotEmpty(){
//        Order order = new Order();
//        order.setEmail("test@test.fr");
//
//        Stock stock = stockService.createStock(100);
//
//        Article article = new Article();
//        article.setDescription("description");
//        article.setName("test");
//        article.setPrice(12.99f);
//        article.setImage("image");
//        article.setStock(stock);
//        article = articleService.saveArticle(article);
//        order.getArticles().add(article);
//
//        CustomResponse customResponse = this.orderService.checkOrderFront(order);
//        assertEquals(null, customResponse.getMessage());
//        assertEquals(true, customResponse.isSuccess());
//    }


}