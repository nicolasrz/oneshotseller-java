app.controller('CartCtrl', function($scope, $http, $rootScope, $location){

    $scope.init = function(){
        $scope.total = 0;
        $scope.showButtonOrder = false;
        $scope.articles = [];
        
        var cart = $rootScope.getCartSession();

        if(cart == null){
            $scope.setCartSession([]);
        }else{
            $scope.getArticles();  
            $scope.chechShowButton();
        }   
    }  

    $scope.chechShowButton = function(){
        var cart = $rootScope.getCartSession();
        if(cart.length == 0){
            $scope.showButtonOrder = false;
        }else{
            $scope.showButtonOrder = true;
        }
    }

    $rootScope.getCartSession = function(){
        var cart = JSON.parse(localStorage.getItem("cart"));

        return cart;
    }

    $scope.setCartSession = function(cart){

        localStorage.setItem("cart", JSON.stringify(cart));
    }

    $scope.delete = function(index, value){
        var cart = $scope.getCartSession();
        cart.splice(index, 1);
        $scope.setCartSession(cart);
        $scope.articles.splice(index, 1);
        $scope.minusTotal(value.price);
        $scope.chechShowButton();
    }


    $scope.getArticles = function(){
        $scope.articles = [];
        var cart = $scope.getCartSession();
        for (var i = 0; i < cart.length ; i++){
            $http({
                method: 'GET',
                url: CONSTANTS.api + "/article/"+cart[i]
            }).then(function successCallback(response) {
                response.data.price = parseFloat(response.data.price);
                $scope.articles.push(response.data);
                $scope.addTotal(response.data.price);
            });    
        }
    }

    $scope.addTotal = function(value){
        var total = parseFloat($scope.total) + parseFloat(value);
        total = total.toFixed(2);
        $scope.total = total;

    };

    $scope.minusTotal = function(value){
        var total = parseFloat($scope.total) - parseFloat(value);
        total = total.toFixed(2);
        $scope.total = total;
    }

    $scope.continue = function(){
        $scope.cart = $scope.getCartSession();
        $location.path('/order')
    }



$scope.init();





});