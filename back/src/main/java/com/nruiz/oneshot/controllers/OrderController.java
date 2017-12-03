package com.nruiz.oneshot.controllers;

import com.nruiz.oneshot.models.ChargeRequestOrder;
import com.nruiz.oneshot.models.CustomResponse;
import com.nruiz.oneshot.models.Order;
import com.nruiz.oneshot.services.OrderService;
import com.nruiz.oneshot.utils.OneErrorCode;
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

    private OrderService orderService;


    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @RequestMapping(value="/save", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
    public CustomResponse saveOrder(@RequestBody Order order){
        Order orderToSave = this.orderService.saveNewOrder(order);
        CustomResponse customResponse = new CustomResponse();
        if(orderToSave != null){
            customResponse.setSuccess(true);
            customResponse.setObject(orderToSave);
        }else{
            customResponse.setSuccess(false);
            customResponse.setMessage(OneErrorCode.ERROR_MESSAGE_WHILE_SAVE_ORDER);
        }

        return customResponse;
    }


    @RequestMapping(value="/check", method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
    public CustomResponse checkOrder(@RequestBody Order orderFront){
        return this.orderService.checkOrderFront(orderFront);
    }



}
