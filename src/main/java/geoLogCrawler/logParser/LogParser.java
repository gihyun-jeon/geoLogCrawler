package geoLogCrawler.logParser;

import geoLogCrawler.bean.GeoLog;

public interface LogParser {
	public GeoLog parseLogLine(String line);

}
