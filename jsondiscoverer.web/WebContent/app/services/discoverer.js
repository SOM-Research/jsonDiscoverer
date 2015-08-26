angular.module("jsonDiscoverer").service('DiscovererService', ["$http",
    function($http) {
        this.prefix = "http://som-research.uoc.edu/tools/jsonDiscoverer"; // %PRODUCTION%
        this.prefix = "http://localhost:8080/jsondiscoverer.web"; // %DEVELOPMENT%

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

            this.callService(this.prefix + "/simpleDiscoverMetamodel", dataToSend, success, failure);
        };

        this.injectModel = function(jsonText, success, failure) {
            var dataToSend = $.param( {
                json : jsonText
            });

            this.callService(this.prefix + "/simpleDiscoverModel", dataToSend, success, failure);
        };

        this.compose = function(data, success, failure) {
            var dataToSend = $.param({
                sources : data
            });

            this.callService(this.prefix + "/advancedDiscover", dataToSend, success, failure);
        };

        this.discoverComposition = function(data, success, failure) {
            var dataToSend = $.param({
                sources : data
            });

            this.callService(this.prefix + "/composer", dataToSend, success, failure);
        };

        this.obtainJSON = function(urlData, success, failure) {
            delete $http.defaults.headers.common['X-Requested-With'];
            $http.defaults.useXDomain = true;

            var dataToSend = $.param( {
                url : urlData
            });

            this.callService(this.prefix + "/getJson", dataToSend, success, failure);
        };

        this.calculatePath = function(urlData, sourceNode, targetNode, success, failure) {
            var dataToSend = $.param({
               sources : urlData,
               source : sourceNode.label,
               target : targetNode.label
            });

            this.callService(this.prefix + "/calculatePath", dataToSend, success, failure);
        };
        
        this.getServerVersion = function(success, failure) {
        	$http({
                method : 'GET',
                url : this.prefix + "/version",
                headers : {'Content-Type': 'application/x-www-form-urlencoded'}
            }).success(function(data) {
                success(data.version);
            }).error(function(data, status, headers, config) {
                failure(data, status, headers, config);
            });
        };
    }
]);
