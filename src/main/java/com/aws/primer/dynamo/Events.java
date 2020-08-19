package com.aws.primer.dynamo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Events {
	@JsonProperty("email")
	private String email;
	@JsonProperty("eventtype")
	private String eventType;
	@JsonProperty("lastevent")
	private String lastEvent;
	@JsonProperty("sitename")
	private String siteName;

	public Events(String siteName, String eventType) {
		this.siteName = siteName;
		this.eventType = eventType;
	}

	public Events(String siteName, String email, String lastEvent, String eventType) {
		this.email = email;
		this.eventType = eventType;
		this.lastEvent = lastEvent;
		this.siteName = siteName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEventType() {
		return eventType;
	}

	public void setEventType(String eventType) {
		this.eventType = eventType;
	}

	public String getLastEvent() {
		return lastEvent;
	}

	public void setLastEvent(String lastEvent) {
		this.lastEvent = lastEvent;
	}

	public String getSiteName() {
		return siteName;
	}

	public void setSiteName(String siteName) {
		this.siteName = siteName;
	}

}
