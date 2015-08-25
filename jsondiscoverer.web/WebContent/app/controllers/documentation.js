angular.module("jsonDiscoverer").controller("DocumentationCtrl", ["$scope", "$window",  "$anchorScroll", "$location",
    function($scope, $window,  $anchorScroll, $location) {
        $scope.$on('$viewContentLoaded', function(event) {
            $window.ga('send', 'pageview', {'page': '/tools/jsonDiscoverer' +$location.path()});    
        });
		
		$scope.scrollTo = function(id) {
			$location.hash(id);
			$anchorScroll();
		}
    }
]);
