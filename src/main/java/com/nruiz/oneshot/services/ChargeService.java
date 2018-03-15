package com.nruiz.oneshot.services;

import com.nruiz.oneshot.models.ChargeRequestOrder;
import com.nruiz.oneshot.models.CustomResponse;
import com.nruiz.oneshot.utils.OneErrorCode;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Service
public class ChargeService {

    private OrderService orderService;
    private StripeService stripeService;

    public ChargeService(OrderService orderService, StripeService stripeService) throws StripeException {
        this.orderService = orderService;
        this.stripeService = stripeService;
    }

    public CustomResponse chargeStripe(ChargeRequestOrder chargeRequestOrder){
        CustomResponse customResponse = new CustomResponse();
        customResponse.setSuccess(false);


        //Check again in case api called with curl/postman.
        CustomResponse checkOrderFrontResponse = this.orderService.checkOrderFront(chargeRequestOrder.getOrder());


        if(!checkOrderFrontResponse.isSuccess()){
            return checkOrderFrontResponse;
        }

        try{
            chargeRequestOrder.getOrder().setTotalPrice(this.orderService.getTotalPriceFromOrder(chargeRequestOrder.getOrder()));
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
