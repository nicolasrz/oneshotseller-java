package com.nruiz.oneshot.utils;

import lombok.Data;

/**
 * Created by Nicolas on 02/12/2017.
 */
@Data
public class OneErrorCode {

    public static final String ADMINISTRATOR_MESSAGE_CONTACT = "Une erreur est survenue, veuillez contacter l'administrateur en lui donnant le code erreur suivant: ";

    public static final String ERROR_CODE_001 = "ERROR_CODE_001";
    public static final String ERROR_CODE_002 = "ERROR_CODE_002";
    public static final String ERROR_CODE_003 = "ERROR_CODE_003";

    public static final String ERROR_MESSAGE_001 = "Public Stripe Key is empty or bad";
    public static final String ERROR_MESSAGE_002 = "Private Stripe Key is empty or bad";

    public static final String ERROR_MESSAGE_EMPTY_CART = "Le panier est vide";
    public static final String ERROR_MESSAGE_DELIVERY_MISSING = "L'adresse de livraison est manquante";
    public static final String ERROR_MESSAGE_FACTURATION_MISSING = "L'adresse de facturation est manquante";
    public static final String ERROR_MESSAGE_ARTICLE_NOT_FOUND = "Un article de votre panier n'est pas reconnu";
    public static final String ERROR_MESSAGE_WHILE_PAYMENT = "Une erreur s'est produite durant le paiement. Veuillez contacter un administrateur si le problème persiste.";
    public static final String ERROR_MESSAGE_WHILE_SAVE_ORDER = "Le paiement est réussi, mais la prise en charge de la commande ne s'est pas passé corrrectement veuillez contacter un administrateur.";


    private String codeError;
    private String message;

    public OneErrorCode() {
    }

    public OneErrorCode(String codeError, String message) {
        this.codeError = codeError;
        this.message = message;
    }
}
