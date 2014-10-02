package geoLogCrawler.bo;

import geoLogCrawler.bean.GeoLog;
import geoLogCrawler.dao.GeoLogDAO;
import geoLogCrawler.dao.GeoLogLocalMemoryDAO;
import geoLogCrawler.logParser.ApacheLogParser;
import geoLogCrawler.logParser.LogParser;
import geoLogCrawler.util.ReverseFileReader;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GeoLogCrawler {
	private final static Logger logger = LoggerFactory.getLogger(GeoLogCrawler.class);

	private static final String ENCODING = "UTF-8";
	public static final String TARGET_SAMPLE_LOG_FILE = "src/main/resources/sample.log";
	public static final String TARGET_REAL_TIME_LOG_FILE = "src/main/resources/doNotCommit_realTimeSample.log";

	LogParser parser = new ApacheLogParser();
	GeoLogDAO geoLogDAO = new GeoLogLocalMemoryDAO();

	public void readAndParseLogAllRange() {
		readAndParseLog(null, null, TARGET_SAMPLE_LOG_FILE);
	}

	public void readAndParseLog(DateTime start, DateTime end, String fileName) {
		//logger.info("Log Read Target Range. start={}, end={}", start, end);

		File file = new File(fileName);
		ReverseFileReader fileReader;
		String line = "";

		try {
			int targetCount = 0, successCount = 0;
			fileReader = new ReverseFileReader(file, ENCODING);
			while ((line = fileReader.readLine()) != null) {
				targetCount++;
				GeoLog geoLog = parser.parseLogLine(line);

				if (null != geoLog) {

					// careful!. log file reading time reverse.
					if (null != end && end.isBefore(geoLog.getEventTime())) {
						//logger.info("end isBefore of eventTime . so pass. end={}, geoLog.getEventTime()={}", end.toString(), geoLog.getEventTime().toString());
						continue;
					}

					if (null != start && start.isAfter(geoLog.getEventTime())) {
						//logger.info("start isAfter of eventTime . start={}, geoLog.getEventTime()={}", start.toString(), geoLog.getEventTime().toString());
						break;
					}

					geoLogDAO.insert(geoLog);
					successCount++;

				} else {
					//logger.warn("getLog is null! line={}", line);
				}
			}

			logger.info("Target logLine count : {}, parsed logLine count : {}", targetCount, successCount);

		} catch (IOException e) {
			logger.warn(e.getMessage(), e);
			logger.warn("FAIL! line={}", line);
		}
	}

	public List<GeoLog> selectGeoLogList(DateTime start, DateTime end) {
		return geoLogDAO.selectGeoLogList(start, end);
	}
}
