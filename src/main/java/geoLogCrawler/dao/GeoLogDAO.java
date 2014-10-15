package geoLogCrawler.dao;

import geoLogCrawler.bean.GeoLog;

import org.joda.time.DateTime;
import org.joda.time.base.BaseDateTime;

import java.util.List;

public interface GeoLogDAO {
	public void insert(GeoLog geoLog);

	public List<GeoLog> selectGeoLogList(DateTime start, DateTime end);

	public String getKey(BaseDateTime eventTime);
}
