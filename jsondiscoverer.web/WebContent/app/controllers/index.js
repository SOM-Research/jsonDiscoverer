angular.module("jsonDiscoverer").controller("IndexCtrl", ["$scope", "DiscovererService", "$window", "$modal", 
    function($scope, DiscovererService, $window, $modal) {
		$scope.serverVersion = "";
		
        if($window.location.protocol == 'https:') {
            $modal.open({
                templateUrl : 'alert.html',
                controller : function($scope) {  }
            });
        };
        
        $scope.getServerVersion = function() {
        	DiscovererService.getServerVersion(
    			function(data) {
    				$scope.serverVersion = data;
                },
                function(data, status, headers, config) {
                	$scope.serverVersion = "Version not found";
                }	
        	);
        }
		$scope.getServerVersion();
    }
]);
