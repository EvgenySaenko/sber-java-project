angular.module('app').controller('orderConfirmationController', function ($scope, $http, $location, $localStorage) {
    const contextPath = 'http://localhost:8189/shop';

    //загрузить корзину
    $scope.loadCart = function () {
        $http.get(contextPath + '/api/v1/cart/' + $localStorage.OnlineShopCartUuid)
            .then(function (response) {
                $scope.OnlineShopUserCart = response.data;//получили корзину
            });
    }


    //подтвердить заказ
    $scope.submitOrder = function () {
        $http({
            url: contextPath + '/api/v1/orders',
            method: 'POST',
            params: {
                cartId: $localStorage.OnlineShopCartUuid,
                address: $scope.orderInfo.address
            }
        }).then(function (response){
            $location.path('/order_result/' + response.data.id);
        });
    }

    $scope.loadCart();
});