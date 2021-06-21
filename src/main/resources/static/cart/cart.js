angular.module('app').controller('cartController', function ($scope, $http, $location, $localStorage) {
    const contextPath = 'http://localhost:8189/shop';

    //очистка корзины
    $scope.clearCart = function () {
        $http({
            url: contextPath + '/api/v1/cart/clear',
            method: "POST",
            params: {
                uuid: $localStorage.OnlineShopCartUuid
            }
        }).then(function () {
            $scope.loadCart();
        });
    }

    //создание заказа
    $scope.createOrder = function () {
        $http({
            url: contextPath + '/api/v1/orders/create',
            method: "POST",
            params: {address: $scope.currentUser.address}
        }).then(function () {
            $scope.showMyOrders();
            $scope.loadCart();
        });
    }

    //показать корзину
    $scope.loadCart = function () {
        $http.get(contextPath + '/api/v1/cart/' + $localStorage.OnlineShopCartUuid)
            .then(function (response) {
                $scope.OnlineShopUserCart = response.data;//получили корзину
            });
    }

    $scope.goToOrderSubmit = function () {
        $location.path('/order_confirmation')
    }

    $scope.checkCart =  function () {
        $http.post(contextPath + '/api/v1/orders/js',  $localStorage.Cart)
            .then(function (response) {
                console.log("good");
            });
    }
    
    $scope.cartView = $localStorage.Cart;

//===============================================================================//


    //показать корзину
    // $scope.showCart = function () {
    //     $http.get(contextPath + '/api/v1/cart')
    //         .then(function (response) {
    //             $scope.Cart = response.data;
    //         });
    // };


    //
    //
    // //добавление продукта в корзину по id
    // $scope.addToCart = function (productId) {
    //     $http.get(contextPath + '/api/v1/cart/add/' + productId)
    //         .then(function (response) {//когда сервак ответил все ок
    //             $scope.showCart();
    //             console.log('added');
    //         });
    // }
    //
    //
    // //очистка корзины
    // $scope.clearCart = function () {
    //     $http.get(contextPath + '/api/v1/cart/clear')
    //         .then(function (response) {//когда сервак ответил все ок
    //             $scope.showCart();
    //         });
    // }
    //
    //
    // //создать заказ
    // $scope.createOrder = function () {
    //     $http({
    //         url: contextPath + '/api/v1/orders/create',
    //         method: "POST",
    //         params: {address: $scope.currentUser.address}
    //     }).then(function () {
    //         $scope.showMyOrders();
    //         $scope.showCart();
    //     });
    // }
    //
    // $scope.goToOrderSubmit = function () {
    //     $location.path('/order_confirmation')
    // }

    $scope.loadCart();
});