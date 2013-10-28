

var jsonDiscovererServices = angular.module("jsonDiscoverer.service", []);

var jsonDiscovererDirectives = angular.module("jsonDiscoverer.directive", []);
    
var jsonDiscovererFilters = angular.module("jsonDiscoverer.filter", []);

var jsonDiscovererModule = angular.module("jsonDiscoverer", ["jsonDiscoverer.service", "jsonDiscoverer.directive", "jsonDiscoverer.filter", "ui.bootstrap"])

jsonDiscovererModule.config(['$httpProvider', function($httpProvider) {
        delete $httpProvider.defaults.headers.common["X-Requested-With"]
    }])

jsonDiscovererModule.config(["$routeProvider", function($routeProvider) {
        $routeProvider.
            when("/", {
                templateUrl : "partials/main.html",
                controller : "IndexCtrl"
            }).
            when("/simple", {
                templateUrl : "partials/simple.html",
                controller : "SimpleDiscovererCtrl"
            }).
            when("/advanced", {
                templateUrl : "partials/advanced.html",
                controller : "AdvancedDiscovererCtrl"
            }).
            otherwise({redirectTo: "/"});
    }
]);

jsonDiscovererModule.controller("IndexCtrl", ["$scope", 
    function($scope) {
        
    }
]);

jsonDiscovererModule.controller("SimpleDiscovererCtrl", ["$scope", "$http", "$log", 
    function($scope, $http, $log) {
        $scope.json = { text: '' };
        $scope.metamodel = "";
        $scope.model = "";

        $scope.alertsGeneral = [ ];
        $scope.alertsSchema = [ ];
        $scope.alertsData = [ ];

        $scope.closeGeneralAlert = function(index) {
            $scope.alertsGeneral.splice(index, 1);
        };

        $scope.closeSchemaAlert = function(index) {
            $scope.alertsSchema.splice(index, 1);
        };

        $scope.closeDataAlert = function(index) {
            $scope.alertsData.splice(index, 1);
        };

        $scope.discover = function() {
            discoverMetamodel($scope.json.text);
            injectModel($scope.json.text);
        }

        $scope.example = function() {
            $scope.json = { text : '[\n   {\n      "sens":2,\n      "terminus":"Gare de Pont-Rousseau",\n      "temps":"Closest",\n      "ligne":{\n         "numLigne":"2",\n         "typeLigne":1\n      },\n      "arret":{\n         "codeArret":"CRQU2"\n      }\n   }\n]'};        }

        var discoverMetamodel = function(jsonText) {
            $scope.metamodel = "images/loading.gif";

            var dataToSend = $.param( {
                json : jsonText
            });

            $http({
                    method : 'POST',
                    url : "http://apps.jlcanovas.es/jsonDiscoverer/discoverMetamodel",
                    data : dataToSend,
                    headers : {'Content-Type': 'application/x-www-form-urlencoded'}
                }).success(function(data) {
                    $scope.metamodel = "data:image/jpg;base64," + data;
                    $scope.alertsGeneral.push({ type: 'warning', msg: 'Did you expect other schema? Please contact us to improve our tool!' });
                }).error(function(data, status, headers, config) {
                    $scope.alertsSchema.push({ type: 'error', msg: 'Oops, we found an error in the discovery process. Could you check your JSON and try again?' });
                });
        }

        var injectModel = function(jsonText) {
            $scope.model = "images/loading.gif";

            var dataToSend = $.param( {
                json : jsonText
            });
            
            $http({
                    method : 'POST',
                    url : "http://apps.jlcanovas.es/jsonDiscoverer/injectModel",
                    data : dataToSend,
                    headers : {'Content-Type': 'application/x-www-form-urlencoded'}
                }).success(function(data) {
                    $scope.model = "data:image/jpg;base64," + data;
                }).error(function(data, status, headers, config) {
                    $scope.alertsData.push({ type: 'error', msg: 'Oops, we found an error in the discovery process. Could you check your JSON and try again?' });
                });
        }
    }
]);

jsonDiscovererModule.controller("AdvancedDiscovererCtrl", ["$scope", "$rootScope", "$modal", "$http", "$log",
    function($scope, $rootScope, $modal, $http, $log) {
        $scope.defs = {} ;
        $scope.name = "";
        $scope.metamodel = "";

        $scope.alertsGeneral = [ ];

        $scope.closeGeneralAlert = function(index) {
            $scope.alertsGeneral.splice(index, 1);
        };

        $scope.newSource = function() {
            $scope.defs[$scope.name] = { name : $scope.name, jsonDefs : [] };
            $scope.name = "";
        }

        $scope.provideJson = function (jsonName) {
            var modalInstance = $modal.open({
                templateUrl: 'jsonProvisionModal.html',
                controller: JsonProvisionModalInstanceCtrlVar,
                resolve: {
                    jsonName : function() {
                        return jsonName;
                    }},
            });

            modalInstance.result.then(
                function(data) {
                    $scope.defs[data.name]["jsonDefs"].push(data.text);
                }, 
                function(data) {
                    //$log.info('Modal dismissed at: ' + new Date());
                });
        };

        $scope.discover = function() {
            $scope.metamodel = "images/loading.gif";
            var dataToSend = $.param({ sources : $scope.defs });

            $http({
                    method : 'POST',
                    url : "http://apps.jlcanovas.es/jsonDiscoverer/compose",
                    data : dataToSend,
                    headers : {'Content-Type': 'application/x-www-form-urlencoded'}
                }).success(function(data) {
                    $scope.metamodel = "data:image/jpg;base64," + data;
                    $scope.alertsGeneral.push({ type: 'warning', msg: 'Did you expect other schema? Please contact us to improve our tool!' });
                }).error(function(data, status, headers, config) {
                    $scope.alertsGeneral.push({ type: 'error', msg: 'Oops, we found an error in the discovery process. Could you check your JSON and try again?' });
                });
        }
    }
]);

var JsonProvisionModalInstanceCtrlVar = function($scope, $modalInstance, $log, jsonName) {
    $scope.json = { name: jsonName, text: '' };

    $scope.ok = function() {
        $modalInstance.close({ name : jsonName, text: $scope.json.text });
    };

    $scope.cancel = function() {
        $modalInstance.dismiss('cancel');
    };
}
