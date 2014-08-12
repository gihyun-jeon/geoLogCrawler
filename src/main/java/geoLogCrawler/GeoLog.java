package geoLogCrawler;

import org.joda.time.DateTime;

import com.google.api.client.repackaged.com.google.common.base.Strings;

public class GeoLog {
	private DateTime eventTime;
	private String ip;
	private String latitude;
	private String longitude;
	private String tagString; //may be json

	@Override
	public String toString() {
		return super.toString() + " eventTime=" + eventTime + " ip=" + ip + " latitude=" + latitude + " longitude=" + longitude + " tagString=" + tagString;
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
			throw new RuntimeException("ip can not be null!");
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

	public String getTagString() {
		return tagString;
	}

	public void setTagString(String tagString) {
		this.tagString = tagString;
	}

}
