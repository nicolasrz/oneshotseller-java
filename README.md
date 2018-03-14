# One Shot Seller

A quick project, which create e-commerce application without any registration of clients.

## Description

I have never found a project where we can sell products without registration.

Need everytime to create an account to buy something. 

So I tried this.

An application using Java and Angularjs/React for a quick e-shop, using Stripe for payments.

### Prerequisites
```
Java 8
Maven
Nodejs / npm
Mysql DB
Have a Stripe account and api keys.
```

## Deployment

```
mvn clean install -f oneshotseller-java\back
java -jar oneshotseller-java/back/target/oneshot-1.0-SNAPSHOT.jar \
--front.url=http://<my.domaine.fr> \
--stripe.public.key=<public_key> \
--stripe.secret.key=<private_key> \
--server.ssl.key-store=<path/to/keystore.p12> \
--server.ssl.key-store-password=<password_letsencrypt>
```

## NB
Do not commit your Stripe api keys !
## Authors

* **Nicolas RUIZ**
