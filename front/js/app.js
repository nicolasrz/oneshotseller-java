var app = angular.module("app", ["ngRoute"]);
app.config(function($routeProvider) {
    $routeProvider
    .when("/", {
        controller : "HomeCtrl",
        templateUrl : "templates/home.html"
    })
    .when("/cart", {
        controller : "CartCtrl",
        templateUrl : "templates/cart.html"
    })
    .when("/order", {
        controller : "OrderCtrl",
        templateUrl : "templates/order.html"
    })
    .when("/contact", {
        templateUrl : "templates/contact.html"
    })
    .when("/thanks", {
        templateUrl : "templates/thanks.html"
    })

    .otherwise({redirectTo:'/'});
});