(function (ng) {
    var mod = ng.module("librosModule");

    mod.controller("reservasLibroCtrl", ['$scope', '$state', '$stateParams', '$http','librosContext', 'bibliotecasContext',  
        function ($scope, $state, $stateParams, $http, librosContext, bibliotecasContext ) {

            // inicialmente el listado de prestamos
            //  está vacio
            $scope.reservasContext = '/reservas';
            $scope.reservas = {};
            // carga las prestamos
            $http.get(bibliotecasContext + "/" + $stateParams.bibliotecaId + "/libros/" + $stateParams.libroId + $scope.reservasContext).then(function (response) {
                $scope.reservas = response.data;
            }, responseError);

            // el controlador recibió un prestamoId ??
            // revisa los parámetros (ver el :prestamoId en la definición de la ruta)
            if ($stateParams.prestamoId !== null && $stateParams.prestamoId !== undefined) {

                // toma el id del parámetro
                id = $stateParams.prestamoId;
                // obtiene el dato del recurso REST
                $http.get(librosContext + "/" + $stateParams.libroId +$scope.reservasContext + "/" + id)
                        .then(function (response) {
                            // $http.get es una promesa
                            // cuando llegue el dato, actualice currentPrestamo
                            $scope.currentReserva = response.data;
                        }, responseError);

                // el controlador no recibió un prestamoId
            } else
            {
                // el registro actual debe estar vacio
                $scope.currentReserva = {
                    id: undefined /*Tipo Long. El valor se asigna en el backend*/,
               
                   
                };

                $scope.alerts = [];
            }
            
            this.saveReserva = function (id) {
                currentReserva = $scope.currentReserva;

                // si el id es null, es un registro nuevo, entonces lo crea
                if (id == null) {

                    // ejecuta POST en el recurso REST
                    return $http.post(librosContext + "/" + $stateParams.libroId + $scope.reservasContext, currentReserva)
                            .then(function () {
                                // $http.post es una promesa
                                // cuando termine bien, cambie de estado
                                $state.go('reservasLibroList');
                            }, responseError);

                    // si el id no es null, es un registro existente entonces lo actualiza
                } else {

                    // ejecuta PUT en el recurso REST
                    return $http.put(librosContext + "/" + $stateParams.libroId + $scope.reservasContext + "/" + currentReserva.id, currentReserva)
                            .then(function () {
                                // $http.put es una promesa
                                // cuando termine bien, cambie de estado
                                $state.go('reservasLibroList');
                            }, responseError);
                }
                ;
            };
            
             this.deleteReserva = function (reserva) {
                return $http.delete($scope.reservasContext + "/" + reserva.id)
                    .then(function () {
                        // cuando termine bien, cambie de estado
                        $state.reload();
                    }, responseError);
            };
            


           // -----------------------------------------------------------------
            // Funciones para manejar las fechas

            $scope.popup = {
                opened: false
            };
             $scope.popup2 = {
                opened: false
            };
           
            $scope.dateOptions = {
                dateDisabled: false,
                formatYear: 'yy',
                maxDate: new Date(2020,5,22),
                minDate: new Date(),
                startingDay: 1
            };

             $scope.dateOptions2 = {
                dateDisabled: false,
                formatYear: 'yy',
                maxDate: new Date(2020, 5, 22),
                minDate: new Date(),
                startingDay: 1
            };

            this.today = function () {
                $scope.dt = new Date();
            };
            this.today();

            this.clear = function () {
                $scope.dt = null;
            };
            this.setDate = function (year, month, day) {
                $scope.dt = new Date(year, month, day);
            };

            this.open = function (fechaFinal) {
                
                $scope.popup.opened = true;
                if(fechaFinal != null)
                {
                    $scope.dateOptions.maxDate = fechaFinal;
                }
                
            };
            
             this.open2 = function (fechaInicial) {
                $scope.popup2.opened = true;
                if(fechaInicial != null)
                {
                    $scope.dateOptions2.minDate = fechaInicial;
                }
                
            };




            // Funciones para manejar los mensajes en la aplicación


            //Alertas
            this.closeAlert = function (index) {
                $scope.alerts.splice(index, 1);
            };

            // Función showMessage: Recibe el mensaje en String y su tipo con el fin de almacenarlo en el array $scope.alerts.
            function showMessage(msg, type) {
                var types = ["info", "danger", "warning", "success"];
                if (types.some(function (rc) {
                    return type === rc;
                })) {
                    $scope.alerts.push({type: type, msg: msg});
                }
            }

            this.showError = function (msg) {
                showMessage(msg, "danger");
            };

            this.showSuccess = function (msg) {
                showMessage(msg, "success");
            };

            var self = this;
            function responseError(response) {

                self.showError(response.data);
            }
        }]);

})(window.angular);