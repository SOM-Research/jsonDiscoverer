angular.module("jsonDiscoverer").controller("AlertCtrl", ["$scope", "$window", "$modal", 
    function($scope, $window, $modal) {
        if($window.location.protocol == 'https:') {
            $modal.open({
                templateUrl : 'alert.html',
                controller : function($scope) {  }
            });
        }
    }
]);
