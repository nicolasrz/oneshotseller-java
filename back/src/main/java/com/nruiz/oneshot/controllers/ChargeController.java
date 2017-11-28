package com.nruiz.oneshot.controllers;


import com.nruiz.oneshot.models.ChargeRequest;
import com.nruiz.oneshot.services.StripeService;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import java.util.HashMap;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/charge")
public class ChargeController {


    private StripeService paymentsService;

    public ChargeController(StripeService paymentsService) {
        this.paymentsService = paymentsService;
    }

    @RequestMapping(value = "/", method= RequestMethod.POST, consumes="application/json")
    public HashMap<String, String> charge(@RequestBody ChargeRequest chargeRequest)
            throws StripeException {

        HashMap<String, String> map = new HashMap<>();;

        Charge charge = paymentsService.charge(chargeRequest);

        map.put("id", charge.getId());
        map.put("status", charge.getStatus());
        map.put("chargeId", charge.getId());
        map.put("balance_transaction", charge.getBalanceTransaction());
        return map;
    }

    @ExceptionHandler(StripeException.class)
    public String handleError(StripeException ex) {
        return ex.getMessage().toString();
    }
}