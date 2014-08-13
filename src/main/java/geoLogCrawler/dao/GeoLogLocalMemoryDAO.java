package geoLogCrawler.dao;

import geoLogCrawler.bean.GeoLog;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GeoLogLocalMemoryDAO implements GeoLogDAO {
	private final static Logger logger = LoggerFactory.getLogger(GeoLogLocalMemoryDAO.class);

	@Override
	public void insert(GeoLog geoLog) {
		logger.info("Try to insert. GeoLog={}", geoLog.toString());
	}

	@Override
	public void select(DateTime start, DateTime end) {
	}

}
