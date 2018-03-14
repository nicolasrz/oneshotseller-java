package com.nruiz.oneshot.services;


import com.nruiz.oneshot.models.*;
import com.nruiz.oneshot.repositories.ArticleRepository;
import com.nruiz.oneshot.utils.OneConstants;
import com.nruiz.oneshot.utils.OneErrorCode;
import com.stripe.Stripe;
import com.stripe.exception.APIConnectionException;
import com.stripe.exception.APIException;
import com.stripe.exception.AuthenticationException;
import com.stripe.exception.CardException;
import com.stripe.exception.InvalidRequestException;
import com.stripe.model.Charge;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class StripeService {

    @Value("${stripe.public.key}")
    private String publicKey;

    @Value("${stripe.private.key}")
    private String privateKey;

    private ArticleRepository articleRepository;

    public StripeService(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    public Charge charge(ChargeRequestOrder chargeRequestOrder)
        throws AuthenticationException, InvalidRequestException,
        APIConnectionException, CardException, APIException {

        Order order = chargeRequestOrder.getOrder();

        Stripe.apiKey = this.privateKey;
        String token = chargeRequestOrder.getChargeRequest().getStripeToken();

        Map<String, Object> params = new HashMap<String, Object>();

        params.put("amount", chargeRequestOrder.getChargeRequest().getAmount());
        params.put("currency", "eur");
        params.put("source", token);

        StringBuilder description = getChargeDescription(chargeRequestOrder, order);
        params.put("description", description.toString());

        HashMap<String, String> metadata = createMetadatas(order);
        params.put("metadata", metadata);

        Charge charge = Charge.create(params);

        return charge;
    }

    private HashMap<String, String> createMetadatas(Order order) {
        //Create metadatas to have order information in Stripe DashBoard
        HashMap<String, String> metadata = new HashMap<>();

        metadata.put("email", order.getEmail());

        metadata.put("delivery_firstname", order.getDelivery().getFirstname());
        metadata.put("delivery_lastname", order.getDelivery().getLastname());
        metadata.put("delivery_number", order.getDelivery().getNumber());
        metadata.put("delivery_street", order.getDelivery().getStreet());
        metadata.put("delivery_complement", order.getDelivery().getComplement());
        metadata.put("delivery_city", order.getDelivery().getCity());
        metadata.put("delivery_zipcode", order.getDelivery().getZipcode());

        metadata.put("facturation_firstname", order.getFacturation().getFirstname());
        metadata.put("facturation_lastname", order.getFacturation().getLastname());
        metadata.put("facturation_number", order.getFacturation().getNumber());
        metadata.put("facturation_street", order.getFacturation().getStreet());
        metadata.put("facturation_complement", order.getFacturation().getComplement());
        metadata.put("facturation_city", order.getFacturation().getCity());
        metadata.put("facturation_zipcode", order.getFacturation().getZipcode());

        List<Article> articleList = order.getArticles();
        String allIds = "";
        for(Article article : articleList){
            allIds += "_"+String.valueOf(article.getId());
        }
        metadata.put("allIds", allIds);

        metadata.put("totalPrice", Float.toString(order.getTotalPrice()));
        metadata.put("createdAt", order.getCreatedAt());
        return metadata;
    }

    private StringBuilder getChargeDescription(ChargeRequestOrder chargeRequestOrder, Order order) {
        StringBuilder sb = new StringBuilder();
        sb.append("Charge from ");
        sb.append(chargeRequestOrder.getChargeRequest().getStripeEmail());
        sb.append(OneConstants.SPACE);
        sb.append(order.getFacturation().getFirstname());
        sb.append(OneConstants.SLASH);
        sb.append(order.getFacturation().getLastname());
        return sb;
    }

}