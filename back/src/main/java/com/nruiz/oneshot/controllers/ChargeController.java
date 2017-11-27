package com.nruiz.oneshot.controllers;


import com.nruiz.oneshot.models.ChargeRequest;
import com.nruiz.oneshot.services.StripeService;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.HashMap;
import java.util.Map;

@Controller
public class ChargeController {

    @Autowired
    private StripeService paymentsService;

    @PostMapping("/charge")
    public Map<String,Object> charge(ChargeRequest chargeRequest, Model model)
            throws StripeException {

        HashMap<String, Object> objectObjectHashMap = new HashMap<>();;
        chargeRequest.setDescription("Example charge");
        chargeRequest.setCurrency(ChargeRequest.Currency.EUR);
        Charge charge = paymentsService.charge(chargeRequest);
        objectObjectHashMap.put("id", charge.getId());
        objectObjectHashMap.put("status", charge.getStatus());
        objectObjectHashMap.put("chargeId", charge.getId());
        objectObjectHashMap.put("balance_transaction", charge.getBalanceTransaction());
        return objectObjectHashMap;
    }

    @ExceptionHandler(StripeException.class)
    public String handleError(Model model, StripeException ex) {
        model.addAttribute("error", ex.getMessage());
        return "result";
    }
}