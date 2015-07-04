myApp.config([ '$routeProvider', function($routeProvider) {
	$routeProvider.when('/login', {
		templateUrl : 'page/login.html',
		controller : 'LoginController'
	}).when('/campaigns', {
		templateUrl : 'page/campaigns.html',
		controller : 'CampaignsController'
	}).when('/products', {
		templateUrl : 'page/products.html',
		controller : 'ProductsController'
	}).when('/account', {
		templateUrl : 'page/account.html'
	}).otherwise({
		templateUrl : 'page/default.html',
		redirectTo : '/home'
	});
} ]);

myApp.controller('MainController', [ '$scope', '$http',
		function($scope, $http) {

		} ]);