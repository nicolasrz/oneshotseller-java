package com.nruiz.oneshot.models;

import lombok.Data;

/**
 * Created by Nicolas on 28/10/2017.
 */
 @Data
public class CustomResponse {

    private Object object;
    private String message;
    private boolean success;


    public CustomResponse() {
    }

    public CustomResponse(Object object, String message, boolean success) {
        this.object = object;
        this.message = message;
        this.success = success;
    }



}
