package geoLogCrawler.dao;

import geoLogCrawler.bean.GeoLog;

import org.joda.time.DateTime;
import org.joda.time.MutableDateTime;
import org.joda.time.base.BaseDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.api.client.util.Lists;
import com.google.api.client.util.Maps;

import java.util.List;
import java.util.Map;

public class GeoLogLocalMemoryDAO implements GeoLogDAO {
	private final static Logger logger = LoggerFactory.getLogger(GeoLogLocalMemoryDAO.class);
	private final static Map<String, List<GeoLog>> LOCAL_MEMORY_REOP = Maps.newHashMap();
	private final static String KEY_PATTERN = "yyyy-MM-dd_HH:mm:ss";

	@Override
	public void insert(GeoLog geoLog) {
		if (null == geoLog) {
			logger.warn("getLog is null!");
			return;
		}

		String key = getKey(geoLog.getEventTime());
		List<GeoLog> value = LOCAL_MEMORY_REOP.get(key);
		if (null == value) {
			value = Lists.newArrayList();
		}
		value.add(geoLog);

		//logger.info("Try to insert. key={}, value={}", key, value);
		LOCAL_MEMORY_REOP.put(key, value);
	}

	@Override
	public String getKey(BaseDateTime eventTime) {
		return eventTime.toString(KEY_PATTERN);
	}

	@Override
	public List<GeoLog> selectGeoLogList(DateTime start, DateTime end) {
		List<GeoLog> result = Lists.newArrayList();

		if (null == start || null == end) {
			return result;
		}

		if (start.isAfter(end)) {
			return result;
		}

		String key;
		List<GeoLog> returnValue = Lists.newArrayList();
		List<GeoLog> value;
		MutableDateTime cursor = start.toMutableDateTime();
		while (cursor.isBefore(end)) {
			key = getKey(cursor);
			value = LOCAL_MEMORY_REOP.get(key);
			if (null != value) {
				returnValue.addAll(value);
			}
			cursor.addSeconds(1);
		}

		return returnValue;

	}

}
