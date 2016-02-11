angular.module("jsonDiscoverer").controller("CompositionCtrl", ["$scope", "$window", "$location", "$modal", "DiscovererService",
    function($scope, $window, $location, $modal, DiscovererService) {
        $scope.defs = {} ;
        $scope.compositionPosible = false;
        $scope.name = "";
        $scope.graph = "";

        $scope.layoutPosible = false;
        $scope.sigmaGraph = new sigma('sigma-container');
        $scope.layoutButton = "Start Layout";
        $scope.layoutRunning = false;

        $scope.pathPosible = false;
        $scope.sourcePath = {};
        $scope.sourcePreviousColor = "";
        $scope.targetPath = {};
        $scope.targetPreviousColor = "";

        $scope.alertsGeneral = [ ];
        
        $scope.showError = false;

        $scope.$on('$viewContentLoaded', function(event) {
            $window.ga('send', 'pageview', {'page': '/tools/jsonDiscoverer' + $location.path()});
        });


        $scope.newSource = function() {
            $scope.defs[$scope.name] = { name : $scope.name, jsonDefs : [] };
            $scope.name = "";
        };

        $scope.provideJson = function (jsonName) {
            var modalInstance = $modal.open({
                templateUrl: 'app/partials/modal/provideJSON-Input.html',
                controller: function($scope, $modalInstance, $log, jsonName) {
                    $scope.json = { name: jsonName, input: '', output: '' };

                    $scope.ok = function() {
                		$scope.showError = false;
                		var errorInput = null;
                		var errorOutput = null;
                    	try {
                    		jsonlint.parse($scope.json.input);
                    	} catch(e) {
                    		errorInput = e.message.replace(/(?:\r\n|\r|\n)/g, '<br />');
                    	}
                    	try {
                    		jsonlint.parse($scope.json.output);
                    	} catch(e) {
                    		errorOutput = e.message.replace(/(?:\r\n|\r|\n)/g, '<br />');
                    	}
                    	
                    	if(errorInput != null || errorOutput != null) {
                    		if(errorInput != null) {
                    			$scope.showErrorInput = true;
                        		$scope.errorMsgInput = errorInput;
                    		} else {
                    			$scope.showErrorInput = false;
                        		$scope.errorMsgInput = null;
                    		}
                    		if(errorOutput != null) {
                    			$scope.showErrorOutput = true;
                        		$scope.errorMsgOutput = errorOutput;
                    		} else {
                    			$scope.showErrorOutput = false;
                        		$scope.errorMsgOutput = null;
                    		}
                    	} else {
                            $modalInstance.close({ name : jsonName, input: $scope.json.input, output: $scope.json.output });
                    	}
                    };

                    $scope.cancel = function() {
                        $modalInstance.dismiss('cancel');
                    };
                },
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
        
        $scope.viewJson = function (jsonName) {
        	defs = $scope.defs[jsonName]["jsonDefs"];
        	
            var modalInstance = $modal.open({
                templateUrl: 'app/partials/modal/viewJSON-Input.html',
                controller:  function($scope, $modalInstance, $log, jsonName) {
                    $scope.json = { name: jsonName, jsonDefs: defs, inputToShow: defs[0].input, outputToShow: defs[0].output, showing : 0 };

                    $scope.ok = function() {
                    	if($scope.validate()) {
                    		$scope.save();
                    		$modalInstance.close({ name : jsonName, jsonDefs: $scope.json.jsonDefs });
                    	}
                    };

                    $scope.next = function() {
                    	if($scope.validate()) {
                    		$scope.save();
	                    	if($scope.json.showing < $scope.json.jsonDefs.length - 1) $scope.json.showing = $scope.json.showing + 1;
	                    	$scope.json.inputToShow = $scope.json.jsonDefs[$scope.json.showing].input;
	                    	$scope.json.outputToShow = $scope.json.jsonDefs[$scope.json.showing].output;
                    	}
                    }
                    
                    $scope.prev = function() {
                    	if($scope.validate()) {
                    		$scope.save();
	                    	if($scope.json.showing > 0) $scope.json.showing = $scope.json.showing - 1;
	                    	$scope.json.inputToShow = $scope.json.jsonDefs[$scope.json.showing].input;
	                    	$scope.json.outputToShow = $scope.json.jsonDefs[$scope.json.showing].output;
                    	}
                    }
                    
                    $scope.validate = function() {
                    	$scope.showError = false;
                		var errorInput = null;
                		var errorOutput = null;
                    	try {
                    		jsonlint.parse($scope.json.inputToShow);
                    	} catch(e) {
                    		errorInput = e.message.replace(/(?:\r\n|\r|\n)/g, '<br />');
                    	}
                    	try {
                    		jsonlint.parse($scope.json.outputToShow);
                    	} catch(e) {
                    		errorOutput = e.message.replace(/(?:\r\n|\r|\n)/g, '<br />');
                    	}
                    	
                    	if(errorInput != null || errorOutput != null) {
                    		if(errorInput != null) {
                    			$scope.showErrorInput = true;
                        		$scope.errorMsgInput = errorInput;
                    		} else {
                    			$scope.showErrorInput = false;
                        		$scope.errorMsgInput = null;
                    		}
                    		if(errorOutput != null) {
                    			$scope.showErrorOutput = true;
                        		$scope.errorMsgOutput = errorOutput;
                    		} else {
                    			$scope.showErrorOutput = false;
                        		$scope.errorMsgOutput = null;
                    		}
                    		return false;
                    	} else {
                			$scope.showErrorInput = false;
                			$scope.showErrorOutput = false;
                            return true;
                    	}
                    }
                    
                    $scope.save = function() {
                		$scope.json.jsonDefs[$scope.json.showing] = { "input" : $scope.json.inputToShow, "output" : $scope.json.outputToShow};
                    }
                },
                resolve: {
                    jsonName : function() {
                        return jsonName;
                    }}
            });

            modalInstance.result.then(
                    function(data) {
                    	console.log(data);
                        $scope.defs[data.name]["jsonDefs"] = data.jsonDefs;
                        $scope.updateDiscoveryPosible();
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
                    $scope.layoutPosible = true;
					$window.ga('send', 'event', 'JSON Discoverer', 'Composer-Graph')
                },
                function(data, status, headers, config) {
                    $scope.alertsGeneral.push({ type: 'error', msg: 'Oops, we found an error in the composition discovery process. Could you check your JSON and try again?' });
                }
            );
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
                $scope.pathPosible = false;
            }

            if($scope.sourcePath === null){
                $scope.sourcePreviousColor = event.data.node.color;
                $scope.sourcePath = event.data.node;
            } else {
                $scope.targetPreviousColor = event.data.node.color;
                $scope.targetPath = event.data.node;
                $scope.pathPosible = true;
            }
            event.data.node.color = '#000';
            $scope.sigmaGraph.refresh();
            $scope.$apply();
        });

        $scope.calculatePath = function() {
            DiscovererService.calculatePath($scope.defs, $scope.sourcePath, $scope.targetPath,
                function(data) {
                	$scope.showError = false;
                    var diagram = Diagram.parse(data);
                    $("#sequence-diagram").empty();
                    diagram.drawSVG("sequence-diagram", {theme: 'simple'});
					$window.ga('send', 'event', 'JSON Discoverer', 'SimpleDiscoverer-SequenceDiagram')
                },
                function(data, status, headers, config) {
                    $scope.showError = true;
                    $("#sequence-diagram").empty();
                    $scope.errorMsg = 'There is no a path between the selected nodes'
                }
            )
        };
        

        $scope.activateHelp = function() {
        	$('body').chardinJs('start')
        };
        

        $scope.example = function() {
        	input2 = '{ "lat" : 2, "lon" : 3 }'
        	ex2 = '[\n  {\n    "direction": 2,\n    "terminus": "Gare de Pont-Rousseau",\n    "infotrafic": false,\n    "time": "Close",\n    "line": {\n      "lineNumber": "2",\n      "lineType": 1\n    },\n    "stop": {\n      "stopCode": "CRQU2"\n    }\n  },\n  {\n    "direction": 1,\n   "terminus": "Orvault-Grand Val",\n    "infotrafic": false,\n    "time": "Close",\n    "line": {\n      "lineNumber": "2",\n      "lineType": 1\n    },\n    "stop": {\n      "stopCode": "CRQU1"\n    }\n  },\n  {\n    "direction": 2,\n    "terminus": "Perray",\n    "infotrafic": false,\n    "time": "Close",\n    "line": {\n      "lineNumber": "11",\n      "lineType": 3\n    },\n    "stop": {\n      "stopCode": "CRQU4"\n    }\n  }\n]';
        	input1 = '{ "locations" : -104.9847034, "sensor" : true }'
        	ex1 = '{\n\   "results" : [\n      {\n         "elevation" : 1608.637939453125,\n         "location" : {\n            "lat" : 39.7391536,\n            \n"lng" : -104.9847034\n         },\n         "resolution" : 4.771975994110107\n      }\n   ],\n   "status" : "OK"\n}';

            $scope.defs['example1'] = { name : 'example1', jsonDefs : [ { input: input1, output: ex1} ]};
            $scope.defs['example2'] = { name : 'example2', jsonDefs : [ { input: input2, output: ex2} ]};
            $scope.updateCompositionPosible();
        }
    }
]);

