'use strict'

var awsprimerplcApp = angular.module('awsprimerplc', [ 'ui.bootstrap', 'awsprimerplc.controllers',
		'awsprimerplc.services' ]);
awsprimerplcApp.constant("CONSTANTS", {
	getAllEvents : "/event/getAllEvents",
	getAllSites : "/event/getAllSites",
	saveEvent : "/event/saveEvent"
});