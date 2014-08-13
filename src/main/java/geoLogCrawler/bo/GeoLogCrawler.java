package geoLogCrawler.bo;

import geoLogCrawler.bean.GeoLog;
import geoLogCrawler.dao.GeoLogDAO;
import geoLogCrawler.dao.GeoLogLocalMemoryDAO;
import geoLogCrawler.logParser.ApacheLogParser;
import geoLogCrawler.logParser.LogParser;
import geoLogCrawler.util.ReverseFileReader;

import java.io.File;
import java.io.IOException;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GeoLogCrawler {
	private final static Logger logger = LoggerFactory.getLogger(GeoLogCrawler.class);

	private static final String ENCODING = "UTF-8";
	public static final String TARGET_LOG_FILE = "src/main/resources/sample.log";

	LogParser parser = new ApacheLogParser();
	GeoLogDAO geoLogDAO = new GeoLogLocalMemoryDAO();

	public void readAndParseLog() {
		readAndParseLog(null, null);
	}

	public void readAndParseLog(DateTime start, DateTime end) {
		logger.info("Log Read Target Range. start={}, end={}", start, end);

		File file = new File(TARGET_LOG_FILE);
		ReverseFileReader fileReader;
		String line = "";

		try {
			fileReader = new ReverseFileReader(file, ENCODING);
			while ((line = fileReader.readLine()) != null) {
				GeoLog geoLog = parser.parseLogLine(line);

				if (null != geoLog) {

					// REMIND!. log file is readed by time reverse
					if (null != end && end.isBefore(geoLog.getEventTime())) {
						logger.info("end isBefore of eventTime . so pass. end={}, geoLog.getEventTime()={}", end.toString(), geoLog.getEventTime().toString());
						continue;
					}

					if (null != start && start.isAfter(geoLog.getEventTime())) {
						logger.info("start isAfter of eventTime . start={}, geoLog.getEventTime()={}", start.toString(), geoLog.getEventTime().toString());
						break;
					}

					geoLogDAO.insert(geoLog);

				} else {

					logger.warn("getLog is null! line={}", line);
				}
			}

		} catch (IOException e) {
			logger.warn("FAIL! line={}", line);
		}
	}
}
