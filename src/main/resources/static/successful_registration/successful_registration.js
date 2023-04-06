angular.module('app').controller('successful_registrationController', function ($scope, $http,$location,) {
    const contextPath = 'http://localhost:8189/shop';

    $scope.imageSources = [];

    $scope.imageSources.push('https://cdn.pixabay.com/photo/2018/03/03/07/11/success-3195027_960_720.jpg');
});