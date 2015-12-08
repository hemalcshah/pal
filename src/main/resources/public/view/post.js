var app = angular.module('post', []);

function Post($scope, $http) {
    $http.get('/posts/').
        success(function(data) {
            $scope.posts = data;
        });
}

function PostSubmitController($scope, $http) {
    
    $scope.list = [];
	$scope.submit = function() {

		var formData = {
				"title" : $scope.title,
				"description" : $scope.description,
		};
			
		var response = $http.post('/posts/', formData);
		response.success(function(data, status, headers, config) {
			$scope.list.push(data);
		});
		response.error(function(data, status, headers, config) {
			alert( "Exception details: " + JSON.stringify({data: data}));
		});
		
		// Empty list data after process
		$scope.list = [];
	}
}

app.directive('customFileModel', [ '$parse', function($parse) {
	return {
		restrict : 'A',
		link : function(scope, element, attrs) {
			var model = $parse(attrs.customFileModel);
			var modelSetter = model.assign;

			element.bind('change', function() {
				scope.$apply(function() {
					modelSetter(scope, element[0].files[0]);
				});
			});
		}
	};
} ]);

app.service('fileUpload', ['$http', 
	function($http, ArchiveService) {
		this.uploadFileToUrl = function(uploadUrl, file, title,description) {
				var fd = new FormData();
				fd.append('file', file);
				fd.append('title', title);
				fd.append('description', description);
				$http.post(uploadUrl, fd, {
					transformRequest : angular.identity,
					headers : { 'Content-Type' : undefined 	}
				})
				.success(function() {
				})
				.error(function() {
				});
		}
	}
]);

app.controller('UploadController', [ '$scope', 'fileUpload',
	function($scope, fileUpload) {
		$scope.uploadFile = function() {
			var file = $scope.myFile;
			var title = $scope.title;
			var description = $scope.description;
			console.log('file is ' + JSON.stringify(file));
			var uploadUrl = "/posts/upload";
			fileUpload.uploadFileToUrl(uploadUrl, file, title, description);
		};
	} 
]);

