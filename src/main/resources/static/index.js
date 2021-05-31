
//создаем приложение имя app, [тут dependency] указываем контроллер и функцию реализующую его
//scope - как глобальный контекст для JS и для html,
// если мы что-то создали например в html => то в js файле можем это у scope достать(и наоборот)
//http - модуль http для отправки запросов из фронта(https://docs.angularjs.org/api/ng/service/$http#general-usage)
angular.module('app',[]).controller('indexController', function ($scope, $http){
    const contextPath = 'http://localhost:8189/shop';
//
//    $scope.fillTable = function () {
//        $http.get(contextPath + '/api/v1/products')
//            .then(function (response) {
//            //console.log(response)
//                $scope.ProductList = response.data;//вытащим JSON array
//            });
//    };

    //отображение таблицы товаров
    $scope.fillTable = function (pageIndex) {
        $http({
            url: contextPath + '/api/v1/products',
            method: 'GET',
            params: {
                title: $scope.filter ? $scope.filter.title : null,
                min_price: $scope.filter ? $scope.filter.min_price : null,
                max_price: $scope.filter ? $scope.filter.max_price : null,
            }
        }).then(function (response) {
            $scope.ProductsPage = response.data;//сохраним страницу
            //PaginationArray - индексы страниц сгенериные по странице
            $scope.PaginationArray = $scope.generatePagesIndexes(1, $scope.ProductsPage.totalPages);
        });
    };

    //создает лист список страниц например с 5 по 15
    $scope.generatePagesIndexes = function (startPage, endPage) {
        let arr = [];//
        for(let i = startPage; i < endPage + 1; i++){
            arr.push(i);
        }
        return arr;
    }

    //создание продукта
    $scope.submitCreateNewProduct =  function () {
        $http.post(contextPath + '/api/v1/products', $scope.newProduct)
            .then(function (response) {
//                console.log('sended: ');
//                console.log($scope.newProduct);
//                console.log('received: ');
//                console.log(response.data);
                $scope.fillTable();
                $scope.newProduct = null;
            });
    };

    //удаление продукта по id
    $scope.deleteProductById = function (productId) {
        $http.delete(contextPath + '/api/v1/products/' + productId)
            .then(function (response) {//когда сервак ответил все ок
                $scope.fillTable();//перезаполняем таблицу
            });
    };





    $scope.fillTable();
});