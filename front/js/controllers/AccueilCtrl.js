app.controller('AccueilCtrl', function($scope, $http){

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
        // console.log($scope.cart);
    }
});