angular.module("jsonDiscoverer").config(["$routeProvider", "$httpProvider", 
    function($routeProvider, $httpProvider) {
        $routeProvider.
            when("/", {
                templateUrl : "app/partials/main.html",
                controller : "MainCtrl"
            }).
            when("/simple", {
                templateUrl : "app/partials/simple.html",
                controller : "SimpleDiscovererCtrl"
            }).
            when("/advanced", {
                templateUrl : "app/partials/advanced.html",
                controller : "AdvancedDiscovererCtrl"
            }).
            when("/contact", {
                templateUrl : "app/partials/contact.html",
                controller : "ContactCtrl"
            }).
            when("/composer", {
                templateUrl : "app/partials/composition.html",
                controller : "CompositionCtrl"
            }).
            otherwise({redirectTo: "/"});
    }
]);

