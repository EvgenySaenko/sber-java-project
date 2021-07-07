
angular.module('app').controller('activateController', function ($scope, $http,$routeParams, $location, $localStorage) {
    const contextPath = 'http://localhost:8189/shop';



    //показать заказ
    $scope.showConfirmationEmail = function () {
        $http({
            url: contextPath + '/activate/' + $routeParams.code,
            method: 'GET',
        }).then(function (response){
            console(response.data + "активирован ли код почты");
            $scope.activeEmail = response.data;
        });
    };

    if ($scope.activeEmail != null){
        $scope.showConfirmationEmail();
    }else {
        console($scope.activeEmail + "уже активирован");
    }

});



