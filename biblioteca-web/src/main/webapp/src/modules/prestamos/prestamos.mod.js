(function () {
    var mod = angular.module('prestamosModule',["ngMessages"]);
    mod.constant("prestamosContext", "/prestamos");
    mod.config(['$stateProvider', '$urlRouterProvider', function ($stateProvider, $urlRouterProvider) {
            var basePath = 'src/modules/prestamos/';
            $urlRouterProvider.otherwise("/prestamosList");
     
            $stateProvider.state('prestamosList', {
                url: '/prestamos',
                parent: 'usuarioEdit',
                views: {
                    'usuarioInstanceView': {
                        controller: 'prestamosCtrl',
                        controllerAs: 'ctrl',
                        templateUrl: basePath + 'prestamos.list.html'
                    }
                }
            }).state('prestamosLibroList', {
                url: '/prestamos',
                parent: 'libroEdit',
                views: {
                    'libroInstanceView': {
                        controller: 'prestamosLibroCtrl',
                        controllerAs: 'ctrl',
                        templateUrl: basePath + 'prestamos.list.html'
                    }
                }
            }).state('prestamoCreate', {
                url: '/prestamos/create',
                parent: 'bibliotecaPrestar',
                views: {
                    'bibliotecaInstanceView': {
                        controller: 'prestamosBiblioCtrl',
                        controllerAs: 'ctrl',
                        templateUrl: basePath + 'prestamosBiblio.create.html'
                    }
                }
            }).state('prestamosBiblioList', {
                url: '/prestamos',
                parent: 'bibliotecaPrestar',
                views: {
                    'bibliotecaInstanceView': {
                        controller: 'prestamosBiblioCtrl',
                        controllerAs: 'ctrl',
                        templateUrl: basePath + 'prestamosBiblio.list.html'
                    }
                }

            }).state('prestamoBiblioEdit', {
                url: '/prestamos/edit/:prestamoId',
                parent: 'bibliotecaPrestar',
                param: {
                    prestamoId: null
                },
                views: {
                    'bibliotecaInstanceView': {
                        controller: 'prestamosBiblioCtrl',
                        controllerAs: 'ctrl',
                        templateUrl: basePath + 'prestamosBiblio.create.html'
                    }
                }
            });
        }]);
    

})(window.angular);