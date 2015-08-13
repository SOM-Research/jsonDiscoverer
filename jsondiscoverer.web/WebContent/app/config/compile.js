angular.module("jsonDiscoverer").config(['$compileProvider', 
    function($compileProvider) {
        $compileProvider.aHrefSanitizationWhitelist(/^\s*(https?|ftp|mailto|chrome-extension|data):/);
    }
])
