//создаем приложение имя app, [тут dependency] указываем контроллер и функцию реализующую его
//scope - как глобальный контекст для JS и для html,
// если мы что-то создали например в html => то в js файле можем это у scope достать(и наоборот)
//http - модуль http для отправки запросов из фронта(https://docs.angularjs.org/api/ng/service/$http#general-usage)
//ngStorage - модуль локального хранилища, в браузере для данного приложения могут сохраняться какие-нибудь данные(это же типо депенденси для AngularJS)
//добавив ngStorage можем пользоваться локальным хранилищем(хэшмапа) $localStorage
angular.module('app', ['ngStorage']).controller('indexController', function ($scope, $http, $localStorage) {
    const contextPath = 'http://localhost:8189/shop';
    $scope.authorized = false;

    //отображение таблицы товаров
    $scope.showProductsPage = function (pageIndex = 1) {
        $http({
            url: contextPath + '/api/v1/products',
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

    $scope.showCart = function () {
        $http.get(contextPath + '/api/v1/cart')
            .then(function (response) {
                $scope.Cart = response.data;
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

    //создание продукта
    $scope.submitCreateNewProduct = function () {
        $http.post(contextPath + '/api/v1/products', $scope.newProduct)
            .then(function (response) {
//                console.log('sended: ');
//                console.log($scope.newProduct);
//                console.log('received: ');
//                console.log(response.data);
                $scope.newProduct = null;
                $scope.showProductsPage();
            });
    };

    //удаление продукта по id
    $scope.deleteProductById = function (productId) {
        $http.delete(contextPath + '/api/v1/products/' + productId)
            .then(function (response) {//когда сервак ответил все ок
                $scope.showProductsPage();//перезаполняем таблицу
            });
    }

    //добавление продукта в корзину по id
    $scope.addToCart = function (productId) {
        $http.get(contextPath + '/api/v1/cart/add/' + productId)
            .then(function (response) {//когда сервак ответил все ок
                $scope.showCart();
                console.log('added');
            });
    }

    //очистка корзины
    $scope.clearCart = function () {
        $http.get(contextPath + '/api/v1/cart/clear')
            .then(function (response) {//когда сервак ответил все ок
                $scope.showCart();
            });
    }

    //авторизация
    $scope.tryToAuth = function () {
        $http.post(contextPath + '/auth', $scope.user)//в тело пост запроса зашиваем json user
            .then(function successCallback(response) {
                if (response.data.token) {//если в ответе есть json с токеном
                    //ко всем запросам подшиваем в стандартный хэдер common.Authorization и туда подшиваем токен
                    $http.defaults.headers.common.Authorization = 'Bearer ' + response.data.token;

                    //запоминаем юзера и токен
                    // $localStorage.OnlineShopUsername = $scope.user.username;
                    // $localStorage.OnlineShoptokenWithBearerPrefix = 'Bearer ' + response.data.token;

                    $scope.user.username = null;
                    $scope.user.password = null;
                    $scope.authorized = true;
                    $scope.showProductsPage();
                    $scope.showMyOrders();
                    $scope.showCart();
                }
            }, function errorCallback(response) {
                //window.alert(response.data.message);
                window.alert("authentication error");
            });
    }

    //авторизация
    // $scope.tryToLogout = function () {
    //     $http.defaults.headers.common.Authorization = null;
    //     delete $localStorage.OnlineShopUsername;
    //     delete $localStorage.OnlineShoptokenWithBearerPrefix;
    //     $scope.authorized = false;
    // }

    //показать заказы
    $scope.showMyOrders = function () {
        $http.get(contextPath + '/api/v1/orders')
            .then(function (response) {
                $scope.MyOrders = response.data;
            });
    }


    //создать заказ
    $scope.createOrder = function () {
        $http({
            url: contextPath + '/api/v1/orders/create',
            method: "POST",
            params: {address: $scope.userinfo.address}
        }).then(function () {
            $scope.showMyOrders();
            $scope.showCart();
        });
    }

    // if ($localStorage.OnlineShopUsername) {//при старте если в локальном хранилище есть такой юзернайм - автоматом подшиваем токен
    //     $http.defaults.headers.common.Authorization = $localStorage.OnlineShoptokenWithBearerPrefix;
    //     $scope.showProductsPage();
    //     $scope.showMyOrders();
    //     $scope.showCart();
    //     $scope.authorized = true;
    // }


    // $scope.showProductsPage();
    // $scope.showCart();

});