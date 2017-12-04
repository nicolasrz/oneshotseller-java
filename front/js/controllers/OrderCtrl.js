app.controller('OrderCtrl', function($scope, $http, $location){
	
	$scope.getCart = function(){
		var cart = JSON.parse(localStorage.getItem("cart"));

		return cart;
	}

	$scope.checkForm = function(){
		if($scope.order != null){
			if($scope.order.email != null
			&& $scope.order.delivery.firstname != null
			&& $scope.order.delivery.lastname != null
			&& $scope.order.delivery.number != null
			&& $scope.order.delivery.street != null
			&& $scope.order.delivery.complement != null
			&& $scope.order.delivery.zipcode != null
			&& $scope.order.delivery.city != null){
				return true;
			}else{
				return false;
			}

		}
		

	}


	

	$('#facturation').hide();
	$scope.isRequired = false;
	$scope.facturation = false;
	$scope.facturationSameAsDelivery = true;
	$('#checkFacturation').click(function(){
		if($(this).is(':checked')){
			$scope.isRequired = false;
			$scope.facturationSameAsDelivery = true;
			$('#facturation').hide();
		}else{
			$scope.isRequired = true;
			$scope.facturationSameAsDelivery = false;
			$('#facturation').show();
		}
	});

	$scope.stripeHandler = function(customResponse){
		var order = customResponse.object;
		var handler = StripeCheckout.configure({
			image: 'https://stripe.com/img/documentation/checkout/marketplace.png',
			key : customResponse.publicKey,	
			token: function(token) {
				var chargeRequest = {
					description : "description",
					amount : order.totalPrice,
					stripeEmail : token.email,
					stripeToken : token.id
				};
				var chargeRequestOrder = {
					order : order,
					chargeRequest : chargeRequest
				};

				$scope.doCharge(chargeRequestOrder);

			},
			locale: 'fr'				
		});

		handler.open({
			name: 'One Shot Seller',
			email : order.email,
			description: '',
			zipCode: true,
			currency: 'eur',
			allowRememberMe: false,
			amount: customResponse.object.totalPrice,

		});
	}

	$scope.submit = function(){
		var articles = $scope.getCart();
		if($scope.checkForm()){
			if(articles.length > 0){
				var articlesObject = [];
				for(var i = 0; i < articles.length; i++){
					var newArticle = {id : articles[i] };
					articlesObject.push(newArticle);
				}
				if($scope.order != null){
					$scope.order.articles = articlesObject;
					if($scope.facturationSameAsDelivery){
						$scope.order.facturation = $scope.order.delivery;
					}
					$http.post(CONSTANTS.api+"/order/check",$scope.order).then(function(response){
						var customResponse = response.data;
						if(customResponse.success){
							$scope.stripeHandler(customResponse);
						}else{
							alert(customResponse.message + " " +customResponse.object.codeError );
						}
						
					})
				}
			}
		}				
	};

	$scope.saveOrder = function(order){
		$http.post(CONSTANTS.api+"/order/save", order)
			.then(function(responseOrder){
				if(responseOrder.data.success){
					$location.path('/thanks');
					localStorage.removeItem("cart");
				}else{
					toastr.options = OPTIONS.toastTopCenter;
					toastr.error(responseOrder.data.message);
				}
			}, function(err){
				toastr.options = OPTIONS.toastTopCenter;
				toastr.error(ERROR.ERROR_MESSAGE_SAVE_ORDER);
			});
	};


	$scope.doCharge = function(chargeRequestOrder){
		$http.post(CONSTANTS.api+"/charge/", chargeRequestOrder)
			.then(
				function(responseCharge){
					if(responseCharge.data.success){
						$scope.saveOrder(responseCharge.data.object);
					}else{
						toastr.options = OPTIONS.toastTopCenter;
						toastr.error(responseCharge.data.message);
					}
				}
				, function(err){
					toastr.options = OPTIONS.toastTopCenter;
					toastr.error(ERROR.ERROR_MESSAGE_CHARGE);
				}		
			);
	};
});