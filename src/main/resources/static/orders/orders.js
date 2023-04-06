angular.module('app').controller('ordersController', function ($scope, $http) {
    const contextPath = 'http://localhost:8189/shop';

    //http://localhost:8189/shop/api/v1/orders
    //показать заказы
    $scope.showMyOrders = function () {
        $http.get(contextPath + '/api/v1/orders')
            .then(function (response) {
                $scope.MyOrders = response.data;
            });
    }

    $scope.showMyOrders();
});