package com.nruiz.oneshot.models;

/**
 * Created by Nicolas on 03/12/2017.
 */

import lombok.Data;

@Data
public class ChargeRequestOrder {
    private Order order;
    private ChargeRequest chargeRequest;
}
