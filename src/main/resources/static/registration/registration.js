angular.module('app').controller('registrationController', function ($scope, $http, $location, $localStorage) {
    const contextPath = 'http://localhost:8189/shop';

    //регистрация
    $scope.tryToReg = function () {
        $http.post(contextPath + '/registration', $scope.user)
            .then(function successCallback(response) {
                console.log(response.data + " successfully");
                $location.path('/successful_registration' );
            }, function errorCallback(response) {
                $scope.error = response.data.message
                $location.path('/error');
                //window.alert(response.data.message);
                // $scope.clearUser();
            });
    };

});