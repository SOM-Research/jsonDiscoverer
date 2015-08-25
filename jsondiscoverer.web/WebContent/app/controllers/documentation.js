angular.module("jsonDiscoverer").controller("DocumentationCtrl", ["$scope", "$window", "$location",
    function($scope, $window, $location) {
        $scope.$on('$viewContentLoaded', function(event) {
            $window.ga('send', 'pageview', {'page': '/tools/jsonDiscoverer' +$location.path()});    
        });
    }
]);
