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
    public static final String ERROR_MESSAGE_WHILE_PAYMENT = "Une erreur s'est produite durant le paiement. Votre carte n'a pas été débitée. Veuillez contacter un administrateur si le problème persiste.";
    public static final String ERROR_MESSAGE_WHILE_SAVE_ORDER = "La prise en charge de la commande ne s'est pas passé corrrectement veuillez contacter un administrateur.";


    public static final String ERROR_EMAIL_MISSING = "Le champ e-mail est obligatoire";
    public static final String ERROR_CITY_MISSING = "Le champ ville est obligatoire";
    public static final String ERROR_FIRSTNAME_MISSING = "Le champ prénom est obligatoire";
    public static final String ERROR_LASTNAME_MISSING = "Le champ nom est obligatoire";
    public static final String ERROR_NUMBER_MISSING = "Le champ numero est obligatoire";
    public static final String ERROR_STREET_MISSING = "Le champ rue est obligatoire";
    public static final String ERROR_ZIPCODE_MISSING = "Le champ code postal est obligatoire";
    public static final String ERROR_TOTALPRICE_NULL= "Le prix du panier ne peut pas avoir cette valeur.";

    private String codeError;
    private String message;

    public OneErrorCode() {
    }

    public OneErrorCode(String codeError, String message) {
        this.codeError = codeError;
        this.message = message;
    }
}
