package geoLogCrawler.bo;

import geoLogCrawler.bean.GeoLog;

import java.util.Locale;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Strings;

public class ApacheLogLineParser implements LogLineParser {
	private final static Logger logger = LoggerFactory.getLogger(ApacheLogLineParser.class);
	public static final DateTimeFormatter APACHE_LOG_FORMATTER = DateTimeFormat.forPattern("[dd/MMM/YYYY:HH:mm:ss Z]").withLocale(Locale.ENGLISH);
	GpsCoordinateUtil gpsCoordinateUtil = new GpsCoordinateUtil();

	@Override
	public GeoLog parseLogLine(String line) {
		if (null == line || line.isEmpty()) {
			logger.warn("log line is null!");
			return null;
		}

		String[] arr = line.split(" ");
		if (arr.length < 5) {
			logger.warn("invalid input. line={}", line);
			return null;
		}

		GeoLog geoLog = new GeoLog();
		geoLog.setIp(arr[0]);

		if (Strings.isNullOrEmpty(geoLog.getIp())) {
			logger.warn("ip is null");
			return null;
		}

		gpsCoordinateUtil.composeGpsCoordinate(geoLog);

		try {
			String timeString = arr[3] + " " + arr[4];
			DateTime eventTime = DateTime.parse(timeString, APACHE_LOG_FORMATTER);
			geoLog.setEventTime(eventTime);

		} catch (IllegalArgumentException e) {
			logger.warn("invalid input. line={}", line);
			return null;
		}

		return geoLog;

	}

}
