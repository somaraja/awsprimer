'use strict'

angular.module('awsprimerplc.services', []).factory('EventService',
		[ "$http", "CONSTANTS", function($http, CONSTANTS) {
			var service = {};			
			service.getAllEvents = function() {
				return $http.get(CONSTANTS.getAllEvents);
			}
			service.saveEvent = function(dailyEvents) {
				return $http.post(CONSTANTS.saveEvent, dailyEvents);
			}
			service.getAllSites = function() {
				return $http.get(CONSTANTS.getAllSites);
			}
			return service;
		} ]);