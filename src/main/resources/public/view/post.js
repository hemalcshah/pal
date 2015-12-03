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

