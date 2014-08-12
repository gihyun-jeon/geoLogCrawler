package geoLogCrawler.bo;

import geoLogCrawler.bean.GeoLog;

public interface LogLineParser {
	public GeoLog parseLogLine(String line);

}
