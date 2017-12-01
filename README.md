# One Shot Seller

A quick project, which create e-commerce application without any registration of clients.

## Description

I have never found a project where we can sell products without registration.

Need everytime to create an account to buy something. 

So I tried this.

An application using Java and Angularjs for a quick e-shop, using Stripe for payments.

### Prerequisites
```
Java 8
Maven
Nodejs / npm
Mysql DB
Have a Stripe account and api keys.
```

### Installing
```
npm install http-server -g
```

## Deployment

```
mvn clean install -f oneshotseller\back
java -jar oneshotseller\back\target\oneshot-1.0-SNAPSHOT.jar -front.url=http://front.server:8081 -stripe.public.key=mypublickey -stripe.secret.key=mysecretkey

cd oneshotseller\front
http-server -p 8081

```
## NB
Do not commit your Stripe api keys !

Do not put them in the front-app.

That why I use external argument for them.

## Authors

* **Nicolas RUIZ**

## License

Do what you want with this :)
