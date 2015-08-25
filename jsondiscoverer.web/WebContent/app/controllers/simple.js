angular.module("jsonDiscoverer").controller("SimpleDiscovererCtrl", ["$scope", "DiscovererService", "$window", "$location" ,"$log",
    function($scope, DiscovererService, $window, $location, $log) {
        $scope.json = { text: '' };
        $scope.metamodel = "";
        $scope.metamodelFile = "";
        $scope.modelFile = "";
        $scope.model = "";
        $scope.showTitles = false;
        $scope.url = ""

        $scope.$on('$viewContentLoaded', function(event) {
            $window.ga('send', 'pageview', {'page': '/tools/jsonDiscoverer' + $location.path()});   
        });

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
            $scope.showTitles = true;
        }

        $scope.example = function() {
            $scope.json = { text : '[\n   {\n      "sens":2,\n      "terminus":"Gare de Pont-Rousseau",\n      "temps":"Closest",\n      "ligne":{\n         "numLigne":"2",\n         "typeLigne":1\n      },\n      "arret":{\n         "codeArret":"CRQU2"\n      }\n   }\n]'};        }

        var discoverMetamodel = function(jsonText) {
            $scope.metamodel = "images/loading.gif";

            DiscovererService.discoverMetamodel(jsonText,
                function(data) {
                    $scope.metamodel = "data:image/jpg;base64," + data.image;
                    $scope.metamodelFile = "data:text/octet-stream;base64," + data.xmi;
                    $scope.alertsGeneral.push({ type: 'warning', msg: 'Did you expect other schema? Please <a href="http://som-research.uoc.edu/tools/jsonDiscoverer/#/contact">contact us</a> to improve our tool!' });
                },
                function(data, status, headers, config) {
                    $scope.metamodel = "";
                    $scope.metamodelFile = "";
                    $scope.alertsSchema.push({ type: 'error', msg: 'Oops, we found an error in the discovery process. Could you check your JSON and try again?' });
                }
            );
        }

        var injectModel = function(jsonText) {
            $scope.model = "images/loading.gif";

            DiscovererService.injectModel(jsonText,
                function(data) {
                    $scope.model = "data:image/jpg;base64," + data.image;
                    $scope.modelFile = "data:text/octet-stream;base64," + data.xmi;
                },
                function(data, status, headers, config) {
                    $scope.model = "";
                    $scope.modelFile = "";
                    $scope.alertsData.push({ type: 'error', msg: 'Oops, we found an error in the discovery process. Could you check your JSON and try again?' });
                }
            );
        }

        $scope.obtainJSON = function() {
            DiscovererService.obtainJSON($scope.url,
                function(data) {
                    $scope.json.text = JSON.stringify(data);
                },
                function(data, status, headers, config) {
                    $scope.url = "";
                    $scope.alertsGeneral.push({ type: 'error', msg: 'We could not get anything from that URL. Could you try again?' });
                }
            );
        }
        
        $scope.activateHelp = function() {
        	$('body').chardinJs('start')
        }
    }
]);
