package com.nruiz.oneshot.controllers;


import com.nruiz.oneshot.models.*;
import com.nruiz.oneshot.services.OrderService;
import com.nruiz.oneshot.services.StripeService;
import com.nruiz.oneshot.utils.OneErrorCode;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/charge")
public class ChargeController {

    private StripeService stripeService;
    private OrderService orderService;

    public ChargeController(StripeService stripeService, OrderService orderService) {
        this.stripeService = stripeService;
        this.orderService = orderService;
    }

    @RequestMapping(value = "/", method= RequestMethod.POST, consumes="application/json")
    public CustomResponse charge(@RequestBody ChargeRequestOrder chargeRequestOrder)
            throws StripeException {

        CustomResponse customResponse = new CustomResponse();
        customResponse.setSuccess(false);


        //Check again in case api called with curl/postman.
        CustomResponse checkOrderFront = this.orderService.checkOrderFront(chargeRequestOrder.getOrder());


        if(!checkOrderFront.isSuccess()){
            return checkOrderFront;
        }
        try{
            Charge charge = this.stripeService.charge(chargeRequestOrder);

            chargeRequestOrder.getOrder().setChargeBalanceTransaction(charge.getBalanceTransaction());
            chargeRequestOrder.getOrder().setChargeIdTransaction(charge.getId());
            chargeRequestOrder.getOrder().setChargeStatusTransaction(charge.getStatus());

            this.orderService.saveNewOrder(chargeRequestOrder);

            customResponse.setSuccess(true);
            customResponse.setMessage("");
            customResponse.setObject(chargeRequestOrder.getOrder());


        }catch (StripeException e){
            System.out.println(e.toString());
            customResponse.setMessage(OneErrorCode.ERROR_MESSAGE_WHILE_PAYMENT);
        }


        return customResponse;
    }



    @ExceptionHandler(StripeException.class)
    public String handleError(StripeException ex) {
        return ex.getMessage().toString();
    }
}