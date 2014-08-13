package geoLogCrawler.dao;

import geoLogCrawler.bean.GeoLog;

import java.util.List;

import org.joda.time.DateTime;

public interface GeoLogDAO {
	public void insert(GeoLog geoLog);

	public List<GeoLog> selectGeoLogList(DateTime start, DateTime end);

}
