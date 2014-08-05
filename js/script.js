
var jsonDiscovererServices = angular.module("jsonDiscoverer.service", []);

var jsonDiscovererDirectives = angular.module("jsonDiscoverer.directive", []);
    
var jsonDiscovererFilters = angular.module("jsonDiscoverer.filter", []);

var jsonDiscovererModule = angular.module("jsonDiscoverer", ["ngSanitize", "jsonDiscoverer.service", "jsonDiscoverer.directive", "jsonDiscoverer.filter", "ui.bootstrap"])

jsonDiscovererModule.config(["$routeProvider", "$httpProvider", 
    function($routeProvider, $httpProvider) {
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
            when("/contact", {
                templateUrl : "partials/contact.html",
                controller : "ContactCtrl"
            }).
            when("/composer", {
                templateUrl : "partials/composition.html",
                controller : "CompositionCtrl"
            }).
            otherwise({redirectTo: "/"});
        $httpProvider.defaults.withCredentials = true;
        delete $httpProvider.defaults.headers.common["X-Requested-With"];
        $httpProvider.defaults.useXDomain = true;
    }
]);

jsonDiscovererModule.service('DiscovererService', ["$http",
    function($http) {
        //this.prefix = "http://apps.jlcanovas.es/jsonDiscoverer";
        //this.prefix = "http://localhost:8080/fr.inria.atlanmod.json.web";
        this.prefix = "http://atlanmodexp.info.emn.fr:8800/jsonDiscoverer";
        //this.prefix = "http://localhost:8080/jsonDiscoverer";

        this.callService = function(call, dataToSend, success, failure) {
            $http({
                method : 'POST',
                url : call,
                data : dataToSend,
                headers : {'Content-Type': 'application/x-www-form-urlencoded'}
            }).success(function(data) {
                success(data);
            }).error(function(data, status, headers, config) {
                failure(data, status, headers, config);
            });
        };

        this.discoverMetamodel = function(jsonText, success, failure) {
            var dataToSend = $.param( {
                json : jsonText
            });

            this.callService(this.prefix + "/discoverMetamodel", dataToSend, success, failure);
        };

        this.injectModel = function(jsonText, success, failure) {
            var dataToSend = $.param( {
                json : jsonText
            });

            this.callService(this.prefix + "/injectModel", dataToSend, success, failure);
        };

        this.compose = function(data, success, failure) {
            var dataToSend = $.param({
                sources : data
            });

            this.callService(this.prefix + "/multiDiscover", dataToSend, success, failure);
        };

        this.discoverComposition = function(data, success, failure) {
            var dataToSend = $.param({
                sources : data
            });

            this.callService(this.prefix + "/discoverComposition", dataToSend, success, failure);
        };

        this.obtainJSON = function(urlData, success, failure) {
            delete $http.defaults.headers.common['X-Requested-With'];
            $http.defaults.useXDomain = true;

            var dataToSend = $.param( {
                url : urlData
            });

            this.callService(this.prefix + "/getJson", dataToSend, success, failure);
        }

        this.calculatePath = function(urlData, sourceNode, targetNode, success, failure) {
            var dataToSend = $.param({
               sources : urlData,
               source : sourceNode.label,
               target : targetNode.label
            });

            this.callService(this.prefix + "/calculatePath", dataToSend, success, failure);
        }
    }
]);

jsonDiscovererModule.controller("ContactCtrl", ["$scope", "$window", "$location",
    function($scope, $window, $location) {
        $scope.$on('$viewContentLoaded', function(event) {
            $window.ga('send', 'pageview', {'page': $location.path()});    
        });
    }
]);

jsonDiscovererModule.controller("IndexCtrl", ["$scope", "$window", "$location",
    function($scope, $window, $location) {
        $scope.$on('$viewContentLoaded', function(event) {
            $window.ga('send', 'pageview', {'page': $location.path()});    
        });
    }
]);

jsonDiscovererModule.controller("SimpleDiscovererCtrl", ["$scope", "DiscovererService", "$window", "$location" ,"$log",
    function($scope, DiscovererService, $window, $location, $log) {
        $scope.json = { text: '' };
        $scope.metamodel = "";
        $scope.model = "";
        $scope.showTitles = false;
        $scope.url = ""

        $scope.$on('$viewContentLoaded', function(event) {
            $window.ga('send', 'pageview', {'page': $location.path()});   
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
                    $scope.metamodel = "data:image/jpg;base64," + data;
                    $scope.alertsGeneral.push({ type: 'warning', msg: 'Did you expect other schema? Please <a href="http://atlanmod.github.io/json-discoverer/#/contact">contact us</a> to improve our tool!' });
                },
                function(data, status, headers, config) {
                    $scope.metamodel = "";
                    $scope.alertsSchema.push({ type: 'error', msg: 'Oops, we found an error in the discovery process. Could you check your JSON and try again?' });
                }
            );
        }

        var injectModel = function(jsonText) {
            $scope.model = "images/loading.gif";

            DiscovererService.injectModel(jsonText,
                function(data) {
                    $scope.model = "data:image/jpg;base64," + data;
                },
                function(data, status, headers, config) {
                    $scope.model = "";
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
    }
]);

jsonDiscovererModule.controller("AdvancedDiscovererCtrl", ["$scope", "$rootScope", "$modal", "DiscovererService", "$window", "$location","$log",
    function($scope, $rootScope, $modal, DiscovererService, $window, $location, $log) {
        $scope.defs = {} ;
        $scope.discoveryPosible = false;
        $scope.name = "";
        $scope.metamodel = "";
        $scope.showTitles = false;

        $scope.alertsGeneral = [ ];

        $scope.$on('$viewContentLoaded', function(event) {
            $window.ga('send', 'pageview', {'page': $location.path()});   
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
                    $scope.metamodel = "data:image/jpg;base64," + data;
                    $scope.alertsGeneral.push({ type: 'warning', msg: 'Did you expect other schema? Please <a href="http://atlanmod.github.io/json-discoverer/#/contact">contact us</a> to improve our tool!' });
                },
                function(data, status, headers, config) {
                    $scope.metamodel = "";
                    $scope.alertsGeneral.push({ type: 'error', msg: 'Oops, we found an error in the discovery process. Could you check your JSON and try again?' });
                }
            );
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

var JsonProvisionModalWithInputInstanceCtrlVar = function($scope, $modalInstance, $log, jsonName) {
    $scope.json = { name: jsonName, input: '', output: '' };

    $scope.ok = function() {
        $modalInstance.close({ name : jsonName, input: $scope.json.input, output: $scope.json.output });
    };

    $scope.cancel = function() {
        $modalInstance.dismiss('cancel');
    };
}

jsonDiscovererModule.controller("CompositionCtrl", ["$scope", "$window", "$location", "$modal", "DiscovererService",
    function($scope, $window, $location, $modal, DiscovererService) {
        $scope.defs = {} ;
        $scope.compositionPosible = false;
        $scope.name = "";
        $scope.graph = "";

        $scope.sigmaGraph = new sigma('sigma-container');
        $scope.layoutButton = "Start Layout";
        $scope.layoutRunning = false;

        $scope.sourcePath = {};
        $scope.sourcePreviousColor = "";
        $scope.targetPath = {};
        $scope.targetPreviousColor = "";

        $scope.alertsGeneral = [ ];

        $scope.$on('$viewContentLoaded', function(event) {
            $window.ga('send', 'pageview', {'page': $location.path()});
        });


        $scope.newSource = function() {
            $scope.defs[$scope.name] = { name : $scope.name, jsonDefs : [] };
            $scope.name = "";
        };

        $scope.provideJson = function (jsonName) {
            var modalInstance = $modal.open({
                templateUrl: 'jsonProvisionModalWithInput.html',
                controller: JsonProvisionModalWithInputInstanceCtrlVar,
                resolve: {
                    jsonName : function() {
                        return jsonName;
                    }}
            });

            modalInstance.result.then(
                function(data) {
                    $scope.defs[data.name]["jsonDefs"].push({ "input" : data.input, "output" : data.output});
                    $scope.updateCompositionPosible();
                },
                function(data) {
                    //$log.info('Modal dismissed at: ' + new Date());
                });
        };

        $scope.closeGeneralAlert = function(index) {
            $scope.alertsGeneral.splice(index, 1);
        };

        $scope.discoverComposition = function() {
            DiscovererService.discoverComposition($scope.defs,
                function(data) {
                    var dataDom = new DOMParser().parseFromString(data, "application/xml");
                    var mygexf = GexfParser.parse(dataDom);

                    $scope.sigmaGraph.graph.clear();
                    var i, l, arr, obj, node;

                    // Adapt the graph:
                    arr = mygexf.nodes;
                    for (i = 0, l = arr.length; i < l; i++) {
                        obj = arr[i];
                        $scope.sigmaGraph.graph.addNode({
                            id: obj.id,
                            label : obj.label,
                            x : Math.random(),
                            y : Math.random(),
                            size : 1,
                            color : obj.viz.color
                        })
                    }

                    arr = mygexf.edges;
                    for (i = 0, l = arr.length; i < l; i++) {
                        obj = arr[i];
                        $scope.sigmaGraph.graph.addEdge({
                            id: obj.id,
                            source : obj.source,
                            target : obj.target
                        })
                    }
                    $scope.sigmaGraph.refresh();

                },
                function(data, status, headers, config) {
                    $scope.alertsGeneral.push({ type: 'error', msg: 'Oops, we found an error in the composition discovery process. Could you check your JSON and try again?' });
                }
            );
        };

        $scope.test = function() {
            $scope.sigmaGraph.graph.clear();
            $scope.sigmaGraph.graph.addNode({
                id : "A",
                label : "labelA",
                x : Math.random(),
                y : Math.random(),
                size : 1,
                color : '#f00'
            }).addNode({
                id : "B",
                label : "labelB",
                x : Math.random(),
                y : Math.random(),
                size : 1,
                color : '#f00'
            }).addNode({
                id : "C",
                label : "labelC",
                x : Math.random(),
                y : Math.random(),
                size : 1,
                color : '#f00'
            }).addEdge({
                id : "EAB",
                source : "A",
                target : "B"
            }).addEdge({
                id : "EBC",
                source : "B",
                target : "C"
            });
            $scope.sigmaGraph.refresh();
        };


        $scope.updateCompositionPosible = function() {
            if(typeof defs === "undefined") $scope.compositionPosible = false;
            for(def in $scope.defs) {
                if($scope.defs[def].jsonDefs.length == 0) $scope.compositionPosible = false;
            }
            $scope.compositionPosible = true;
        };

        $scope.layout = function() {
            if($scope.layoutRunning) {
                $scope.layoutRunning = false;
                $scope.layoutButton = 'Start Layout';
                $scope.sigmaGraph.stopForceAtlas2();
            } else {
                $scope.layoutRunning = true;
                $scope.layoutButton = 'Stop Layout';
                $scope.sigmaGraph.startForceAtlas2();
            }
        };

        $scope.rescale = function() {
            $scope.sigmaGraph.position(0,0,1).draw();
        };

        $scope.sigmaGraph.bind('clickNode', function(event) {
            if($scope.sourcePath !== null && $scope.targetPath !== null) {
                $scope.sourcePath.color = $scope.sourcePreviousColor;
                $scope.targetPath.color = $scope.targetPreviousColor;
                $scope.sourcePath = null;
                $scope.targetPath = null;
            }

            if($scope.sourcePath === null){
                $scope.sourcePreviousColor = event.data.node.color;
                $scope.sourcePath = event.data.node;
            } else {
                $scope.targetPreviousColor = event.data.node.color;
                $scope.targetPath = event.data.node;
            }
            event.data.node.color = '#000';
            $scope.sigmaGraph.refresh();
            $scope.$apply();
        });

        $scope.calculatePath = function() {
            DiscovererService.calculatePath($scope.defs, $scope.sourcePath, $scope.targetPath,
                function(data) {
                    var diagram = Diagram.parse(data);
                    diagram.drawSVG("sequence-diagram", {theme: 'simple'});
                },
                function(data, status, headers, config) {

                }
            )

            /*var diagram = Diagram.parse("A->B: Normal line");
            $("sequence-diagram").each(function(index) {
               console.log(index + ": " + $(this).text());
            });
            $(".sequence-diagram").html('');
            diagram.drawSVG("sequence-diagram", {theme: 'simple'});*/
        };
    }
]);