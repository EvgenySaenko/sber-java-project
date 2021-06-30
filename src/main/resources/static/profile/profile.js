angular.module('app').controller('profileController', function ($scope, $http, $location, $localStorage) {
    const contextPath = 'http://localhost:8189/shop';

    //запрос инфы о пользователе
    $scope.loadUserData = function () {
        $http.get(contextPath + '/api/v1/profile')
            .then(function (response) {
                $scope.UserProfile = response.data;
            });
    }

    $scope.loadUserData();
});