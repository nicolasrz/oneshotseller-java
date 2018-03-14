package com.nruiz.oneshot.controllers;


import com.nruiz.oneshot.models.*;
import com.nruiz.oneshot.services.ChargeService;
import com.stripe.exception.StripeException;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/charge")
public class ChargeController {

    private ChargeService chargeService;

    public ChargeController(ChargeService chargeService) {
        this.chargeService = chargeService;
    }

    @RequestMapping(value = "/", method= RequestMethod.POST, consumes="application/json")
    public CustomResponse charge(@RequestBody ChargeRequestOrder chargeRequestOrder){
            return this.chargeService.chargeStripe(chargeRequestOrder);
    }




}