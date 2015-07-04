myApp.controller('ProductsController', [ '$scope', '$http', '$rootScope',
		function($scope, $http, $rootScope) {

			$scope.init = function() {
				$http.post('rest/products/', {token:$rootScope.token.tokenValue})
				.success(function(data) {
					$scope.products = data.items;
				});
			};

			$scope.removeProduct = function(id) {

				$http.post('rest/products/remove/' + id, {token:$rootScope.token.tokenValue})
				.success(function(data) {
					$scope.init();
				});
			};

			$scope.addProduct = function() {
				console.log("a");
				$http.post('rest/products/add/', {token:$rootScope.token.tokenValue})
				.success(function(data) {
					$scope.init();
				});
			};

		
		} ]);

myApp.controller('CampaignsController', [ '$scope', '$http', '$rootScope',
		function($scope, $http, $rootScope) {

			$scope.init = function() {
				$http.post('rest/campaigns/', {token:$rootScope.token.tokenValue})
				.success(function(data) {
					$scope.campaigns = data.items;
				});
			};

			$scope.removeCampaign = function(id) {

				$http.post('rest/campaigns/remove/' + id, {token:$rootScope.token.tokenValue})
				.success(function(data) {
					$scope.init();
				});
			};

			$scope.addCampaign = function() {
				$http.post('rest/campaigns/add/', {token:$rootScope.token.tokenValue})
				.success(function(data) {
					$scope.init();
				});
			};

		
		} ]);

myApp.controller('LoginController', [ '$scope', '$http', '$rootScope',
		function($scope, $http, $rootScope) {

			$scope.init = function() {
				$http.get('rest/users/').success(function(data) {
					$scope.users = data.items;
				});
			};

			$scope.removeUser = function(id) {

				$http.post('rest/users/remove/' + id, {
					token : $scope.token.tokenValue
				}).success(function(data) {
					$scope.init();
					$scope.logout();
				});
			};

			$scope.addUser = function() {
				$http.get('rest/users/add/').success(function(data) {
					$scope.init();
				});
			};

			$scope.login = function(id) {
				$http.post('rest/tokens/login', {
					user : id
				}).success(function(data) {
					$scope.token = data;
					$rootScope.token = data;
				});
			};

			$scope.logout = function() {
				$http.post('rest/tokens/logout', {
					token : $scope.token.tokenValue
				}).success(function(data) {
					$scope.token = null;
					$rootScope.token = null;
				});
			};
		} ]);