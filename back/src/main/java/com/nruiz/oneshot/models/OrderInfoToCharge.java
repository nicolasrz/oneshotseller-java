package com.nruiz.oneshot.models;


import lombok.Data;

@Data
public class OrderInfoToCharge {

    private Order order;
    private int amount;
    private String currency;
    private String stripePublicKey;

    public OrderInfoToCharge() {
    }

    public OrderInfoToCharge(Order order, int amount, String currency, String stripePublicKey) {
        this.order = order;
        this.amount = amount;
        this.currency = currency;
        this.stripePublicKey = stripePublicKey;
    }
}
