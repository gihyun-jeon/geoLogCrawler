package geoLogCrawler.util;

import geoLogCrawler.bean.GeoLog;

import java.io.File;
import java.net.InetAddress;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.model.CityResponse;

public class GpsCoordinateUtil {
	private final static Logger logger = LoggerFactory.getLogger(GpsCoordinateUtil.class);
	private static final DatabaseReader dbReader = initDb();

	private static DatabaseReader initDb() {
		File database = new File("src/main/resources/GeoLite2-City.mmdb");
		try {
			return new DatabaseReader.Builder(database).build();

		} catch (Exception e) {
			logger.error("can not read DB file!", e);
			return null;
		}
	}

	public void composeGpsCoordinate(GeoLog geoLog) {
		try {
			CityResponse response = dbReader.city(InetAddress.getByName(geoLog.getIp()));
			geoLog.setLatitude(String.valueOf(response.getLocation().getLatitude()));
			geoLog.setLongitude(String.valueOf(response.getLocation().getLongitude()));

		} catch (Exception e) {
			logger.warn(e.getMessage() + "geoLog=" + geoLog);
			//logger.warn(e.getMessage() + "geoLog=" + geoLog, e);
		}
	}

}
