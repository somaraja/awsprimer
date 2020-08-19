package com.aws.primer.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.aws.primer.dynamo.DailyEvents;
import com.aws.primer.dynamo.SiteNames;
import com.aws.primer.service.EventService;
import com.aws.primer.utils.Constants;

@RequestMapping("/event")
@RestController
public class EventController {
	@Autowired
	EventService eventService;
	
	@RequestMapping(Constants.GET_ALL_EVENTS)
	public List<DailyEvents> getAllEvents() {
		return eventService.getAllEvents();
	}
	
	@RequestMapping(value= Constants.SAVE_EVENT, method= RequestMethod.POST)
	public void saveEvent(@RequestBody DailyEvents dailyEvents) {
		eventService.saveEvent(dailyEvents);
	}
	
	@RequestMapping(Constants.GET_ALL_SITES)
	public List<SiteNames> getAllSites() {
		return eventService.getAllSites();
	}
}
