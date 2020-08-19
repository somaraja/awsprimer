package com.aws.primer.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aws.primer.dynamo.DailyEvents;
import com.aws.primer.dynamo.Events;
import com.aws.primer.dynamo.SiteNames;
import com.aws.primer.service.EventService;
import com.aws.primer.service.QueueOperations;
import com.google.gson.Gson;

@Service
public class EventServiceImpl implements EventService {
	@Autowired
	QueueOperations operations;

	@Override
	public void saveEvent(DailyEvents eventDto) {
		if (StringUtils.isNotBlank(eventDto.getSiteName()) && StringUtils.isNotBlank(eventDto.getEventType())) {
			SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// dd/MM/yyyy
			Date now = new Date();
			String strDate = sdfDate.format(now);
			Events events = new Events(eventDto.getSiteName(), randomString() + "@gmail.com", strDate,
					eventDto.getEventType());

			String jsonString = new Gson().toJson(events);

			operations.writeMessageToSQS(jsonString);
		}
	}

	@Override
	public List<DailyEvents> getAllEvents() {
		return operations.readMessagesFromDynamoDB();
	}

	private String randomString() {
		String generatedstring = RandomStringUtils.randomAlphabetic(8);
		return (generatedstring);
	}

	@Override
	public List<SiteNames> getAllSites() {
		return operations.getAllSites();
	}
}
