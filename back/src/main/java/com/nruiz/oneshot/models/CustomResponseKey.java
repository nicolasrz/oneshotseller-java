package com.nruiz.oneshot.models;

/**
 * Created by Nicolas on 02/12/2017.
 */
public class CustomResponseKey extends CustomResponse{

    private String publicKey;

    public CustomResponseKey(){

    }
    public CustomResponseKey(Object object, String message, boolean success, String publicKey) {
        super(object, message, success);
        this.publicKey = publicKey;
    }

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }
}
