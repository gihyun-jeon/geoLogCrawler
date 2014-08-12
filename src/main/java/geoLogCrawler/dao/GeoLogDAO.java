package geoLogCrawler.dao;

import geoLogCrawler.bean.GeoLog;

import org.joda.time.DateTime;

public interface GeoLogDAO {
	public void insert(GeoLog geoLog);

	public void select(DateTime start, DateTime end);

}
