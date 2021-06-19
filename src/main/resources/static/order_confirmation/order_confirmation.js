angular.module('app').controller('orderConfirmationController', function ($scope, $http, $location) {
    const contextPath = 'http://localhost:8189/shop';

    $scope.cartContentRequest = function () {
        $http.get(contextPath + '/api/v1/cart')
            .then(function (response) {
                $scope.Cart = response.data;
            });
    }

    $scope.submitOrder = function () {
        $http({
            url: contextPath + '/api/v1/orders',
            method: 'POST',
            params: {
                address: $scope.orderInfo.address
            }
        }).then(function (response){
            $location.path('/order_result/' + response.data.id);
        });
    }

    $scope.cartContentRequest();
});