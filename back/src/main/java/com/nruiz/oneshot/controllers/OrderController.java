package com.nruiz.oneshot.controllers;

import com.nruiz.oneshot.models.ChargeRequest.Currency;
import com.nruiz.oneshot.models.CustomResponse;
import com.nruiz.oneshot.models.Order;
import com.nruiz.oneshot.models.OrderInfoToCharge;
import com.nruiz.oneshot.services.OrderService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Nicolas on 28/10/2017.
 */
@RestController
@RequestMapping("/order")
public class OrderController {

    @Value("${stripe.public.key}")
    private String publicKey;

    private OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @RequestMapping(value="/", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
    public CustomResponse createOrder(@RequestBody Order orderFront){

        if(orderFront.getArticles().isEmpty()){
            return new CustomResponse(orderFront, "No articles", false);
        }

        if(orderFront.getDelivery() == null){
            return new CustomResponse(orderFront, "Delivery address is null", false);
        }

        Order orderToSave = this.orderService.saveNewOrder(orderFront);

        int amount = Math.round(orderToSave.getTotalPrice() * 100);
        OrderInfoToCharge orderInfoToCharge = new OrderInfoToCharge(orderToSave, amount, Currency.EUR.toString(), this.publicKey );

        return new CustomResponse(orderInfoToCharge,"", true);
    }



}
