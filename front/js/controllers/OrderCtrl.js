app.controller('OrderCtrl', function($scope, $http, $rootScope,){
	
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

			if($scope.order != null){
				$scope.order.articles = articlesObject;
				$http.post(CONSTANTS.api+"/order/",$scope.order).then(function(response){
					 var orderInfoToCharge = response.data.object;

					var handler = StripeCheckout.configure({
						image: 'https://stripe.com/img/documentation/checkout/marketplace.png',
						key : orderInfoToCharge.stripePublicKey,	
						token: function(token) {
							var chargeRequest = {
								currency : orderInfoToCharge.currency,
								description : "description",
								amount : orderInfoToCharge.amount,
								stripeEmail : token.email,
								stripeToken : token.id
							}
							console.log(chargeRequest);
							$http.post(CONSTANTS.api+"/charge/", chargeRequest).then(function(response){
								console.log(response);
							});
						},
						locale: 'auto'				
					});

					handler.open({
						name: 'Demo Site',
						description: '2 widgets',
						zipCode: true,
						currency: 'eur',
						amount: orderInfoToCharge.amount,
						
					});
				})
			}
		}
	};
});