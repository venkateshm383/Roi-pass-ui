var app = angular.module("MyApp", []);

app.controller("MyController", function($scope, $http) {

	$scope.fields = {};
                     /*registration controller*/
	$scope.reg = function() {
		console.log($scope.fields);
		var userData = JSON.stringify($scope.fields);
		var res = $http.post('/PAAS-GUI/rest/registerAndLoginService/register', userData);
		console.log(userData);

		res.success(function(data, status, headers, config) {
			$scope.message = data;
			document.location.href = '/PAAS-GUI/html/login.html';
		});
		res.error(function(data, status, headers, config) {
			alert("failure message: " + JSON.stringify({
				data : data
			}));
		});
	};
	           /*reset function*/
	$scope.reset = function() {
		$scope.list = {};
	};

	/*$scope.login = function() {
		console.log("HelloWorld");
		var app = angular.module('MyApp', ['UserValidation']);
		angular.module('UserValidation', []).directive('validPasswordC', function () {
			return {
				require: 'ngModel',
				link: function (scope, elm, attrs, ctrl) {
					ctrl.$parsers.unshift(function (viewValue, $scope) {
						var noMatch = viewValue != scope.myForm.password.$viewValue;
						ctrl.$setValidity('noMatch', !noMatch)
					})
				}
			}
		})*/
	             /*login*/
	$scope.login = function() {
		console.log("HelloWorld");
		var email = $scope.fields.email;
		var password=$scope.fields.password;
		var res = $http.get('/PAAS-GUI/rest/registerAndLoginService/'+email+'/'+password);
		res.success(function(data, status, headers, config) {
			$scope.fields.email = data;
			if(data==="try another"){
				console.log("Incorrect");
			}
			else{
				console.log("correct");
				document.location.href = '/PAAS-GUI/html/home.html';				
			}
		});
		res.error(function(data, status, headers, config) {
			alert("failure message: " + JSON.stringify({
				data : data
			}));
		});
	};
             /*check email is exits or not*/
		$scope.checkemail = function() {
			console.log("HelloWorld");
			var email = $scope.fields.email;
			var res = $http.get('/PAAS-GUI/rest/registerAndLoginService/'+email);
			res.success(function(data, status, headers, config) {
				$scope.fields.email = data;
				if(data==="Email Already Exists"){
					console.log("Incorrect");
					/*logic */
					
				}
				else{
					console.log("correct");
					/*logic	*/
					var res = $http.post('/PAAS-GUI/rest/registerAndLoginService/register', userData);
					console.log(userData);
				}
			});
		};
		
		
                 /*vpc*/
/*
		$scope.regVpc = function() {

			window.alert("hiiiii");
			console.log($scope.vpc);
			var userData = JSON.stringify($scope.vpc);
			var res = $http.post('/PAAS-GUI/rest/fetchData/vpc', userData);
			console.log(userData);

			res.success(function(data, status, headers, config) {
				$scope.message = data;
				alert("success");
				//document.location.href = '/PAAS-GUI/html/login.html';
			});
			res.error(function(data, status, headers, config) {
				alert("failure message: " + JSON.stringify({
					data : data
				}));
			});
		};
*/









	/*$scope.collect = function() {
		console.log($scope.vpc);
		var userData = JSON.stringify($scope.vpc);
		var res = $http.post('/pass-gui/rest/fetchData/insertvpc', userData);
		console.log(userData);

		res.success(function(data, status, headers, config) {
			$scope.message = data;
		});
		res.error(function(data, status, headers, config) {
			alert("failure message: " + JSON.stringify({
				data : data
			}));
		});
	};

	$scope.loadactivity = function() {
		$('#contentdisplay').load('activity.html');
	};
	 */
});