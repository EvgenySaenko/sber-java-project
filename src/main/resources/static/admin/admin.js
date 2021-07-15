angular.module('app').controller('adminController', function ($scope, $http, $localStorage) {
    const contextPath = 'http://localhost:8189/shop';



    //отображение таблицы товаров
    $scope.showProductsPage = function (pageIndex = 1) {
        $http({
            url: contextPath + '/api/v1/admin',
            method: 'GET',
            params: {
                title: $scope.filter ? $scope.filter.title : null,
                min_price: $scope.filter ? $scope.filter.min_price : null,
                max_price: $scope.filter ? $scope.filter.max_price : null,
                p: pageIndex
            }
        }).then(function (response) {
            $scope.ProductsPage = response.data;
            console.log($scope.ProductsPage)

            let minPageIndex = pageIndex - 2;
            if (minPageIndex < 1) {
                minPageIndex = 1;
            }

            let maxPageIndex = pageIndex + 2;
            if (maxPageIndex > $scope.ProductsPage.totalPages) {
                maxPageIndex = $scope.ProductsPage.totalPages;
            }
            //PaginationArray - индексы страниц сгенериные по странице
            $scope.PaginationArray = $scope.generatePagesIndexes(minPageIndex, maxPageIndex);
        });
    };






    //создает лист список страниц например с 5 по 15
    $scope.generatePagesIndexes = function (startPage, endPage) {
        let arr = [];
        for (let i = startPage; i < endPage + 1; i++) {
            arr.push(i);
        }
        return arr;
    }



    //добавить новый продукт
    $scope.addProduct = function () {
        $http.post(contextPath + '/api/v1/admin', $scope.newProduct)
            .then(function (response) {
                $scope.newProduct = null;
                $scope.showProductsPage($scope.ProductsPage.totalPages);
            });
    }




    //закидывает в модальное окно редактирования - продукт
    $scope.getProduct = function (editableProduct) {
        $scope.editableProduct = editableProduct;
    }



    //редактировать продукт
    $scope.editProduct = function () {
        $http.put(contextPath + '/api/v1/admin', $scope.editableProduct)
            .then(function (response) {
                $scope.showProductsPage($scope.ProductsPage.number + 1);
            });
    }





    //удаление продукта по id
    $scope.deleteProductById = function (productId) {
        $http.delete(contextPath + '/api/v1/admin/' + productId)
            .then(function (response) {
                $scope.showProductsPage($scope.ProductsPage.number + 1);
            });
    }

    //закрывает форму редактирования
    $('#btnSaveEdit').click(function() {
        $('#editModal').modal('hide');
    });


    //закрывает форму добавления товара
    $('#btnSave').click(function() {
        $('#addModal').modal('hide');
    });



    $scope.showProductsPage();
});