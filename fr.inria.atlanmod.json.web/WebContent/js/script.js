

var jsonDiscovererServices = angular.module("jsonDiscoverer.service", []);

var jsonDiscovererDirectives = angular.module("jsonDiscoverer.directive", []);
    
var jsonDiscovererFilters = angular.module("jsonDiscoverer.filter", []);

var jsonDiscovererModule = angular.module("jsonDiscoverer", ["jsonDiscoverer.service", "jsonDiscoverer.directive", "jsonDiscoverer.filter", "ui.bootstrap"])

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

        $scope.discover = function() {
            discoverMetamodel($scope.json.text);
            injectModel($scope.json.text);
        }

        var discoverMetamodel = function(jsonText) {
            $scope.metamodel = "images/loading.gif";

            var dataToSend = $.param( {
                json : jsonText
            });

            $http({
                    method : 'POST',
                    url : "discoverMetamodel",
                    data : dataToSend,
                    headers : {'Content-Type': 'application/x-www-form-urlencoded'}
                }).success(function(data) {
                    $scope.metamodel = "data:image/jpg;base64," + data
                });
        }

        var injectModel = function(jsonText) {
            $scope.model = "images/loading.gif";

            var dataToSend = $.param( {
                json : jsonText
            });
            
            $http({
                    method : 'POST',
                    url : "injectModel",
                    data : dataToSend,
                    headers : {'Content-Type': 'application/x-www-form-urlencoded'}
                }).success(function(data) {
                    $scope.model = "data:image/jpg;base64," + data
                });
        }
    }
]);

jsonDiscovererModule.controller("AdvancedDiscovererCtrl", ["$scope", "$rootScope", "$modal", "$http", "$log",
    function($scope, $rootScope, $modal, $http, $log) {
        $scope.defs = {} ;
        $scope.name = "";
        $scope.metamodel = "";

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
                    url : "compose",
                    data : dataToSend,
                    headers : {'Content-Type': 'application/x-www-form-urlencoded'}
                }).success(function(data) {
                    $scope.metamodel = "data:image/jpg;base64," + data
                });
        }
    }
]);

var JsonProvisionModalInstanceCtrlVar = function($scope, $modalInstance, $log, jsonName) {
    $scope.json = { name: jsonName, text: '[ { "tan" : 2 } ]' };

    $scope.ok = function() {
        $modalInstance.close({ name : jsonName, text: $scope.json.text });
    };

    $scope.cancel = function() {
        $modalInstance.dismiss('cancel');
    };
}
