angular.module('app').controller('cartController', function ($scope, $http, $location) {
    const contextPath = 'http://localhost:8189/shop';

    //показать корзину
    $scope.showCart = function () {
        $http.get(contextPath + '/api/v1/cart')
            .then(function (response) {
                $scope.Cart = response.data;
            });
    };




    //добавление продукта в корзину по id
    $scope.addToCart = function (productId) {
        $http.get(contextPath + '/api/v1/cart/add/' + productId)
            .then(function (response) {//когда сервак ответил все ок
                $scope.showCart();
                console.log('added');
            });
    }


    //очистка корзины
    $scope.clearCart = function () {
        $http.get(contextPath + '/api/v1/cart/clear')
            .then(function (response) {//когда сервак ответил все ок
                $scope.showCart();
            });
    }


    //создать заказ
    $scope.createOrder = function () {
        $http({
            url: contextPath + '/api/v1/orders/create',
            method: "POST",
            params: {address: $scope.currentUser.address}
        }).then(function () {
            $scope.showMyOrders();
            $scope.showCart();
        });
    }

    $scope.goToOrderSubmit = function () {
        $location.path('/order_confirmation')
    }

    $scope.showCart();
});