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
					$window.ga('send', 'event', 'JSON Discoverer', 'AdvancedDiscoverer-Schema')
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
        
        $scope.example = function() {
        	ex1 = '[\n  {\n    "direction": 2,\n    "terminus": "Gare de Pont-Rousseau",\n    "infotrafic": false,\n    "time": "Close",\n    "line": {\n      "lineNumber": "2",\n      "lineType": 1\n    },\n    "arret": {\n      "stopCode": "CRQU2"\n    }\n  },\n  {\n    "direction": 1,\n   "terminus": "Orvault-Grand Val",\n    "infotrafic": false,\n    "time": "Close",\n    "line": {\n      "lineNumber": "2",\n      "lineType": 1\n    },\n    "arret": {\n      "stopCode": "CRQU1"\n    }\n  },\n  {\n    "direction": 2,\n    "terminus": "Perray",\n    "infotrafic": false,\n    "time": "Close",\n    "line": {\n      "lineNumber": "11",\n      "lineType": 3\n    },\n    "arret": {\n      "stopCode": "CRQU4"\n    }\n  }\n]';
        	ex2 = '[\n  {\n    "stopCode": "CRQU",\n    "stopName": "Place du Cirque",\n    "distance": "27 m",\n    "line": [\n      {\n        "lineNumber": "2"\n      },\n      {\n        "lineNumber": "C1"\n      },\n      {\n        "lineNumber": "C2"\n      },\n      {\n        "lineNumber": "C6"\n      },\n      {\n        "lineNumber": "11"\n      },\n      {\n        "lineNumber": "23"\n      },\n      {\n        "lineNumber": "LU"\n      }\n    ]\n  },\n  {\n    "stopCode": "BRTA",\n    "stopName": "Bretagne",\n    "distance": "138 m",\n    "line": [\n      {\n        "lineNumber": "3"\n      }\n    ]\n  }\n]';

            $scope.defs['example1'] = { name : 'example1', jsonDefs : [ ex1 ]};
            $scope.defs['example2'] = { name : 'example2', jsonDefs : [ ex2 ]};
            $scope.updateDiscoveryPosible();
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
