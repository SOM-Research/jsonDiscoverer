angular.module("jsonDiscoverer").controller("AdvancedDiscovererCtrl", ["$scope", "$rootScope", "$modal", "DiscovererService", "$window", "$location","$log",
    function($scope, $rootScope, $modal, DiscovererService, $window, $location, $log) {
        $scope.defs = {} ;
        $scope.discoveryPosible = false;
        $scope.name = "";
        $scope.metamodel = "";
        $scope.metamodelFile = "";
        $scope.showTitles = false;

        $scope.alertsGeneral = [ ];

        $scope.$on('$viewContentLoaded', function(event) {
            $window.ga('send', 'pageview', {'page': '/tools/jsonDiscoverer' + $location.path()});   
        });

        $scope.defsNumber = function() {
            return Object.keys($scope.defs);
        };

        $scope.updateDiscoveryPosible = function() {
            if(typeof defs === "undefined") $scope.discoveryPosible = false;
            for(def in $scope.defs) {
                if($scope.defs[def].jsonDefs.length == 0) $scope.discoveryPosible = false;
            }
            $scope.discoveryPosible = true;
        };

        $scope.closeGeneralAlert = function(index) {
            $scope.alertsGeneral.splice(index, 1);
        };

        $scope.newSource = function() {
            $scope.defs[$scope.name] = { name : $scope.name, jsonDefs : [] };
            $scope.name = "";
        };

        $scope.provideJson = function (jsonName) {
            var modalInstance = $modal.open({
                templateUrl: 'jsonProvisionModal.html',
                controller: JsonProvisionModalInstanceCtrlVar,
                resolve: {
                    jsonName : function() {
                        return jsonName;
                    }}
            });

            modalInstance.result.then(
                function(data) {
                    $scope.defs[data.name]["jsonDefs"].push(data.text);
                    $scope.updateDiscoveryPosible();
                }, 
                function(data) {
                    //$log.info('Modal dismissed at: ' + new Date());
                });
        };

        $scope.discover = function() {
            $scope.metamodel = "images/loading.gif";
            $scope.showTitles = true;

            DiscovererService.compose($scope.defs,
                function(data) {
                    $scope.metamodel = "data:image/jpg;base64," + data.image;
                    $scope.metamodelFile = "data:text/octet-stream;base64," + data.xmi;
                    $scope.alertsGeneral.push({ type: 'warning', msg: 'Did you expect other schema? Please <a href="http://som-research.uoc.edu/tools/jsonDiscoverer/#/contact">contact us</a> to improve our tool!' });
                },
                function(data, status, headers, config) {
                    $scope.metamodel = "";
                    $scope.metamodelFile = "";
                    $scope.alertsGeneral.push({ type: 'error', msg: 'Oops, we found an error in the discovery process. Could you check your JSON and try again?' });
                }
            );
        };

        $scope.activateHelp = function() {
        	$('body').chardinJs('start')
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
