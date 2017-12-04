app.controller('HomeCtrl', function($scope, $http){

    var retrievedData = localStorage.getItem("cart");
    if(retrievedData == null){
        $scope.cart = [];    
    }else{
        $scope.cart = JSON.parse(retrievedData);
    }
    

	//getArticles
    $http({
        method: 'GET',
        url: CONSTANTS.api + "/article/"
    }).then(function successCallback(response) {
        $scope.articles = response.data;
    });



    $scope.addToCart = function(articleId){
        
        $scope.cart.push(articleId);
        // storing our array as a string
        localStorage.setItem("cart", JSON.stringify($scope.cart));

        // retrieving our data and converting it back into an array
        var retrievedData = localStorage.getItem("cart");
        $scope.cart = JSON.parse(retrievedData);
        
        $http({
            method: 'GET',
            url: CONSTANTS.api + "/article/"+articleId
        }).then(function successCallback(response) {
            var article = response.data;
            toastr.options = OPTIONS.toastTopRight;
            toastr.success("L'article "+article.name + ' a été ajouté au panier');
        }, function errorCallback(r){
            toastr.options = OPTIONS.toastTopCenter;
            toastr.error(ERROR.ERROR_MESSAGE_ADD_ARTICLE_TO_CART);
        });
        
    }
});