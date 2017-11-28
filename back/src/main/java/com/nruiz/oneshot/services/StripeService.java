package com.nruiz.oneshot.services;


import com.nruiz.oneshot.models.ChargeRequest;
import com.stripe.Stripe;
import com.stripe.exception.APIConnectionException;
import com.stripe.exception.APIException;
import com.stripe.exception.AuthenticationException;
import com.stripe.exception.CardException;
import com.stripe.exception.InvalidRequestException;
import com.stripe.model.Charge;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class StripeService {

    @Value("${stripe.secret.key}")
    private String secretKey;

    public Charge charge(ChargeRequest chargeRequest)
        throws AuthenticationException, InvalidRequestException,
        APIConnectionException, CardException, APIException {

        Stripe.apiKey = this.secretKey;
        String token = chargeRequest.getStripeToken();

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("amount", 1000);
        params.put("currency", "eur");
        params.put("description", "Example charge");
        params.put("source", token);

        Charge charge = Charge.create(params);

        return charge;
    }
}