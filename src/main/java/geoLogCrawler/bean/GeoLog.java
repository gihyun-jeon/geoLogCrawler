package geoLogCrawler.bean;

import geoLogCrawler.util.ApacheSampleLogGenerator;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.api.client.repackaged.com.google.common.base.Strings;

public class GeoLog {
	private final static Logger logger = LoggerFactory.getLogger(ApacheSampleLogGenerator.class);

	private DateTime eventTime;
	private String ip;
	private String latitude;
	private String longitude;
	private String tagJsonString;

	@Override
	public String toString() {
		return "eventTime=" + eventTime + " ip=" + ip + " latitude=" + latitude + " longitude=" + longitude + " tagString=" + tagJsonString + super.toString();
	}

	public DateTime getEventTime() {
		return eventTime;
	}

	public void setEventTime(DateTime eventTime) {
		this.eventTime = eventTime;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		if (Strings.isNullOrEmpty(ip)) {
			logger.warn("ip can not be null!");
			return;
		}
		this.ip = ip;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getTagJsonString() {
		return tagJsonString;
	}

	public void setTagJsonString(String tagJsonString) {
		this.tagJsonString = tagJsonString;
	}

}
