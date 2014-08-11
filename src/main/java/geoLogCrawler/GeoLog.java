package geoLogCrawler;

import org.joda.time.DateTime;

public class GeoLog {
	DateTime eventTime;
	String ip;
	String gpsPosition;
	String tagString; //may be json

	@Override
	public String toString() {
		return super.toString() + " eventTime=" + eventTime + " ip=" + ip + " gpsPosition=" + gpsPosition + " tagString=" + tagString;
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
		this.ip = ip;
	}

	public String getGpsPosition() {
		return gpsPosition;
	}

	public void setGpsPosition(String gpsPosition) {
		this.gpsPosition = gpsPosition;
	}

	public String getTagString() {
		return tagString;
	}

	public void setTagString(String tagString) {
		this.tagString = tagString;
	}

}
