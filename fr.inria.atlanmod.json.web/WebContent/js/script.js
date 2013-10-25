

var jsonDiscovererServices = angular.module("jsonDiscoverer.service", []);

var jsonDiscovererDirectives = angular.module("jsonDiscoverer.directive", []);

var jsonDiscovererFilters = angular.module("jsonDiscoverer.filter", []);

var jsonDiscovererModule = angular.module("jsonDiscoverer", ["jsonDiscoverer.service", "jsonDiscoverer.directive", "jsonDiscoverer.filter", "ui.bootstrap"])

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

var discoveryCtrlVar = jsonDiscovererModule.controller("DiscoveryCtrl", ["$scope", "$rootScope", "$http", "$modal", "$log",
    function($scope, $rootScope, $http, $modal, $log) { 
        $scope.defs = [];
        $scope.domains = [] ;

        var unbind = $rootScope.$on("jsonDefsChanged", function(event, data) {
            $scope.defs = data;
        });

        $scope.$on('$destroy', unbind);

        $scope.discover = function() {
            $scope.domains = [];
            for(var i = 0; i < $scope.defs.length; i++) {
                $scope.domains[i] = { name: "", metamodel: "images/loading.gif", model: "images/loading.gif"};
                $scope.domains[i]["name"] = $scope.defs[i].name;
                discoverMetamodel(i, $scope.defs[i].text);
                injectModel(i, $scope.defs[i].text);
            }
        }

        var discoverMetamodel = function(position, params) {
            var dataToSend = $.param( {
                json : params
            });

            $http({
                    method : 'POST',
                    url : "discoverMetamodel",
                    data : dataToSend,
                    headers : {'Content-Type': 'application/x-www-form-urlencoded'}
                }).success(function(data) {
                    $scope.domains[position]["metamodel"] = "data:image/jpg;base64," + data
                });
        }

        var injectModel = function(position, params) {
            var dataToSend = $.param( {
                json : params
            });
            
            $http({
                    method : 'POST',
                    url : "injectModel",
                    data : dataToSend,
                    headers : {'Content-Type': 'application/x-www-form-urlencoded'}
                }).success(function(data) {
                    $scope.domains[position]["model"] = "data:image/jpg;base64," + data
                });
        }

        $scope.viewModel = function (image) {
            var modalInstance = $modal.open({
                templateUrl: 'viewModelModal.html',
                controller: viewModelModalInstanceCtrlVar,
                resolve: {
                    data : function() {
                        return image;
                    }},
                windowsClass : "modal-big"
            });

            modalInstance.result.then(
                function(data) { }, 
                function(data) { }
            );
        };

    }
]);
