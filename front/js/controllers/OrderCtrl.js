app.controller('OrderCtrl', function($scope, $http, $rootScope){
	
	$scope.getCart = function(){
        var cart = JSON.parse(localStorage.getItem("cart"));

        return cart;
	}


	$('#facturation').hide();
	$scope.isRequired = false;
	$('#checkFacturation').click(function(){
		if($(this).is(':checked')){
			$scope.isRequired = false;
			$('#facturation').hide();
		}else{
			$scope.isRequired = true;
			$('#facturation').show();
		}
	});

	$scope.submit = function(){
		var articles = $scope.getCart();
		if(articles.length > 0){
			var articlesObject = [];
			for(var i = 0; i < articles.length; i++){
				var newArticle = {id : articles[i] };
				articlesObject.push(newArticle);
			}
			$scope.order.articles = articlesObject;
			$http.post(CONSTANTS.api+"/order/",$scope.order).then(function(response){
				$scope.order = response.data.object;
				console.log($scope.order);
				$('#checkout-modal').modal('show');
			})
		}
	};

	$scope.submitPayment = function(){
		$http.post(CONSTANTS.api+"/charge/",$scope.order).then(function(response){
				$scope.order = response.data.object;
				console.log($scope.order);
			})
	}


	

});