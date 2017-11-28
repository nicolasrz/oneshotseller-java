package com.nruiz.oneshot.services;


import com.nruiz.oneshot.models.Address;
import com.nruiz.oneshot.models.Article;
import com.nruiz.oneshot.models.Order;
import com.nruiz.oneshot.models.Stock;
import com.nruiz.oneshot.repositories.AddressRepository;
import com.nruiz.oneshot.repositories.ArticleRepository;
import com.nruiz.oneshot.repositories.OrderRepository;
import com.nruiz.oneshot.repositories.StockRepository;
import java.time.LocalDateTime;
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

    public Order saveNewOrder(Order orderFront) {
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
            orderToSave.setTotalPrice(orderToSave.getTotalPrice() + article.getPrice());
        }

        orderToSave.setCreatedAt(LocalDateTime.now().toString());
        orderToSave = orderRepository.save(orderToSave);
        return orderToSave;
    }
}
