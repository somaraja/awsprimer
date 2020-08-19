package com.aws.primer.service;

import java.util.List;

import com.aws.primer.dynamo.DailyEvents;
import com.aws.primer.dynamo.SiteNames;

public interface EventService {
    void saveEvent(DailyEvents eventDto);
    List<DailyEvents> getAllEvents();
    List<SiteNames> getAllSites();
}
