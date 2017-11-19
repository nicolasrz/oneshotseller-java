var app = angular.module("app", ["ngRoute"]);
app.config(function($routeProvider) {
    $routeProvider
    .when("/", {
        controller : "AccueilCtrl",
        templateUrl : "templates/accueil.html"
    })
    .when("/panier", {
        controller : "PanierCtrl",
        templateUrl : "templates/panier.html"
    })
    .when("/order", {
        controller : "OrderCtrl",
        templateUrl : "templates/order.html"
    })
    .when("/contact", {
        templateUrl : "templates/contact.html"
    })
    .otherwise({redirectTo:'/'});
});