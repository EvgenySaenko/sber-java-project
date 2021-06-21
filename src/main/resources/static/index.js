(function ($localStorage) {
    'use strict';

    angular
        .module('app', ['ngRoute', 'ngStorage'])
        .config(config)//функции для конфигурирования и запуска
        .run(run);

    function config($routeProvider, $httpProvider) {
        $routeProvider
            .when('/', {
                templateUrl: 'home/home.html',
                controller: 'homeController'
            })
            .when('/products', {
                templateUrl: 'products/products.html',
                controller: 'productsController'
            })
            .when('/cart', {
                templateUrl: 'cart/cart.html',
                controller: 'cartController'
            })
            .when('/order_confirmation', {
                templateUrl: 'order_confirmation/order_confirmation.html',
                controller: 'orderConfirmationController'
            })
            .when('/order_result/:orderId', {
                templateUrl: 'order_result/order_result.html',
                controller: 'orderResultController'
            })
            .when('/orders', {
                templateUrl: 'orders/orders.html',
                controller: 'ordersController'
            })
            .otherwise({
                redirectTo: '/'
            });
    }


    const contextPath = 'http://localhost:8189/shop';

    //при старте
    function run($rootScope, $http, $localStorage) {
        if ($localStorage.currentUser) {//если в локальном есть юзер=> подшиваем токен к хедеру
            $http.defaults.headers.common.Authorization = 'Bearer ' + $localStorage.currentUser.token;
        }

        $http.post(contextPath + '/api/v1/cart')
            .then(function (response) {
                $localStorage.OnlineShopCartUuid = response.data;//сохраняем UUID корзины
            });
    }
})();



angular.module('app').controller('indexController', function ($scope, $http, $localStorage, $location) {
    const contextPath = 'http://localhost:8189/shop';


    //логинимся
    $scope.tryToAuth = function () {
        $scope.user.cartId = $localStorage.OnlineShopCartUuid;//когда логинимся получаем юид корзины
        $http.post(contextPath + '/auth', $scope.user)
            .then(function successCallback(response) {
                if (response.data.token) {
                    $http.defaults.headers.common.Authorization = 'Bearer ' + response.data.token;
                    $localStorage.currentUser = {username: $scope.user.username, token: response.data.token};

                    $scope.currentUserName = $scope.user.username;

                    $scope.user.username = null;
                    $scope.user.password = null;

                    console.log($localStorage.currentUser);
                }
            }, function errorCallback(response) {
                // window.alert(response.data.message);
                // $scope.clearUser();
            });
    };


    //разлогинимся
    $scope.tryToLogout = function () {
        $scope.clearUser();

        $http.post(contextPath + '/api/v1/cart')
            .then(function (response) {
                $localStorage.OnlineShopCartUuid = response.data;//сохраняем UUID корзины
            });

        $location.path('/');
        if ($scope.user.username) {
            $scope.user.username = null;
        }
        if ($scope.user.password) {
            $scope.user.password = null;
        }
    };


    $scope.clearUser = function () {
        delete $localStorage.currentUser;
        $http.defaults.headers.common.Authorization = '';
    };

    //
    $scope.isUserLoggedIn = function () {
        if ($localStorage.currentUser) {
            return true;
        } else {
            return false;
        }
    };
});
