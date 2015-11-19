angular.module("jsonDiscoverer").controller("SimpleDiscovererCtrl", ["$scope", "DiscovererService", "$window", "$location" ,"$log", "$modal",
    function($scope, DiscovererService, $window, $location, $log, $modal) {
        $scope.json = { text: '' };
        $scope.metamodel = "";
        $scope.metamodelFile = "";
        $scope.modelFile = "";
        $scope.model = "";
        $scope.url = ""

        $scope.showTitles = false;
        $scope.showFeedback = false;
		$scope.showError = false;

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
            $scope.alertsGeneral = [ ];
            $scope.alertsSchema = [ ];
            $scope.alertsData = [ ];
            $scope.metamodel = "";
            $scope.metamodelFile = "";
            $scope.model = "";
            $scope.modelFile = "";
            
            $scope.showFeedback = false;
    		$scope.showError = false;
            
        	try {
        		jsonlint.parse($scope.json.text);
        		//JSON.parse($scope.json.text);
                discoverMetamodel($scope.json.text);
                injectModel($scope.json.text);
                $scope.showTitles = true;
        		$scope.showError = false;
        	} catch(e) {
                $scope.showFeedback = false;
        		$scope.showError = true;
                $("#modelImg").empty();
                $("#metamodelImg").empty();
        		$scope.errorMsg = e.message.replace(/(?:\r\n|\r|\n)/g, '<br />');;
        	}
        }

        $scope.example = function() {
            $scope.json = { text : '[\n   {\n      "direction":2,\n      "terminus":"Gare de Pont-Rousseau",\n      "time":"Closest",\n      "line":{\n         "lineNumber":"2",\n         "lineType":1\n      },\n      "stop":{\n         "stopCode":"CRQU2"\n      }\n   }\n]'};        }

        var discoverMetamodel = function(jsonText) {
            $scope.metamodel = "images/loading.gif";

            DiscovererService.discoverMetamodel(jsonText,
                function(data) {
                    $scope.metamodel = "data:image/jpg;base64," + data.image;
                    $scope.metamodelFile = "data:text/octet-stream;base64," + data.xmi;
                    $scope.showFeedback = true;
					$window.ga('send', 'event', 'JSON Discoverer', 'SimpleDiscoverer-Schema')
                },
                function(data, status, headers, config) {
                    $scope.metamodel = "";
                    $scope.metamodelFile = "";
                    if(status == 400)
                    	$scope.alertsSchema.push({ type: 'error', msg: 'Oops, we found an error discovering the schema. Your JSON is not parseable: ' + data });
                    else 
                    	$scope.alertsSchema.push({ type: 'error', msg: 'Oops, this is embarrasing, the server had an internal error. This is the cause: ' + data });
                }
            );
        }

        var injectModel = function(jsonText) {
            $scope.model = "images/loading.gif";

            DiscovererService.injectModel(jsonText,
                function(data) {
                    $scope.model = "data:image/jpg;base64," + data.image;
                    $scope.modelFile = "data:text/octet-stream;base64," + data.xmi;
                    $scope.showFeedback = true;
					$window.ga('send', 'event', 'JSON Discoverer', 'SimpleDiscoverer-Data')
                },
                function(data, status, headers, config) {
                    $scope.model = "";
                    $scope.modelFile = "";
                    if(status == 400)
                    	$scope.alertsData.push({ type: 'error', msg: 'Oops, we found an error discovering the data. Your JSON is not parseable: ' + data });
                    else 
                    	$scope.alertsData.push({ type: 'error', msg: 'Oops, this is embarrasing, the server had an internal error. This is the cause: ' + data });
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
        
        $scope.sendFeedback = function() {
            var modalInstance = $modal.open({
                templateUrl: 'app/partials/modal/feedback.html',
                controller: function($scope, $modalInstance) {
                	$scope.feedback = []
                	$scope.ok = function(result) {
    					$modalInstance.close($scope.feedback);
  					};

  					// When clicking on CANCEL
  					$scope.cancel = function() {
    					$modalInstance.dismiss('closed');
  					}
                },
                resolve: {
                	
                }
            });
            
            modalInstance.result.then(
                function(result) {
	                var feedbackToSend = {
	                	source : 'simpleDiscoverer',
	                	comment : result.comment,
	                	json : $scope.json.text,
	                	metamodel : $scope.metamodel,
	                	metamodelFile : $scope.metamodelFile,
	                	model : $scope.model,
	                	modelFile : $scope.modelFile
	                }
	                
	                DiscovererService.sendFeedback(feedbackToSend,
	                        function(data) {
	                            //console.log("fine")
	                        },
	                        function(data, status, headers, config) {
	                            //console.log("wrong")
	                        }
	                    );
                },
	            function(result) {
                	// nothing
	            }
            );
        }
    }
]);
