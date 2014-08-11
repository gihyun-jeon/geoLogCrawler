package geoLogCrawler;

import java.io.File;
import java.net.InetAddress;
import java.util.Locale;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Strings;
import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.model.CityResponse;

public class ApacheLogLineParser implements LogLineParser {
	private final static Logger logger = LoggerFactory.getLogger(ApacheLogLineParser.class);
	public static final DateTimeFormatter APACHE_LOG_FORMATTER = DateTimeFormat.forPattern("[dd/MMM/YYYY:HH:mm:ss Z]").withLocale(Locale.ENGLISH);
	DatabaseReader reader;

	public ApacheLogLineParser() {
		File database = new File("src/main/resources/GeoLite2-City.mmdb");
		try {
			reader = new DatabaseReader.Builder(database).build();

		} catch (Exception e) {
		}
	}

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

		GeoLog geoInfo = new GeoLog();
		geoInfo.ip = arr[0];

		if (Strings.isNullOrEmpty(geoInfo.ip)) {
			logger.warn("ip is null");
			return null;
		}

		try {
			CityResponse response = reader.city(InetAddress.getByName(geoInfo.ip));
			logger.info(response.toString());

		} catch (Exception e) {
			logger.warn(e.getMessage(), e);
		}

		try {
			String timeString = arr[3] + " " + arr[4];
			DateTime eventTime = DateTime.parse(timeString, APACHE_LOG_FORMATTER);
			geoInfo.eventTime = eventTime;
		} catch (IllegalArgumentException e) {
			logger.warn("invalid input. line={}", line);
			return null;
		}

		return geoInfo;

	}

}
