'use strict'

var module = angular.module('awsprimerplc.controllers', []);
module.controller("EventController", [ "$scope", "EventService",
		function($scope, EventService) {			
			$scope.dailyEvents = {
				siteName : null,
				eventType : null
			};		
			
			EventService.getAllSites().then(function(value) {
						$scope.allSites= value.data;
					}, function(reason) {
					console.log("error occured");
				}, function(value) {
					console.log("no callback");
			});			

			$scope.saveEvent = function() {				
				EventService.saveEvent($scope.dailyEvents).then(function() {
					console.log("works ");
					EventService.getAllEvents().then(function(value) {
						$scope.allEvents= value.data;
					}, function(reason) {
						console.log("error occured");
					}, function(value) {
						console.log("no callback");
					});

					$scope.dailyEvents = {
						siteName : null,
						eventType : null
					};
				}, function(reason) {
					console.log("error occured");
				}, function(value) {
					console.log("no callback");
				});
			}
		} ]);