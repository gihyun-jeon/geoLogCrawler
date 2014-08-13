package geoLogCrawler.bo;

import geoLogCrawler.bean.GeoLog;
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

	public void readAndParseLog() {
		readAndParseLog(null, null);
	}

	public void readAndParseLog(DateTime start, DateTime end) {
		File file = new File(TARGET_LOG_FILE);
		ReverseFileReader fileReader;
		String line = "";

		try {
			fileReader = new ReverseFileReader(file, ENCODING);

			while ((line = fileReader.readLine()) != null) {
				GeoLog geoLog = parser.parseLogLine(line);
				if (null != geoLog) {
					logger.debug(geoLog.toString());

					if (null != end && end.isAfter(geoLog.getEventTime())) {
						logger.info("end is After of eventTime . so pass");
						continue;
					}

					if (null != start && start.isAfter(geoLog.getEventTime())) {
						System.out.println("Start=" + start.toString());
						System.out.println("geoLog.getEventTime()=" + geoLog.getEventTime().toString());
						logger.info("start is After of eventTime . so pass");
						break;
					}

				} else {
					logger.warn("getLog is null");
				}
			}

		} catch (IOException e) {
			logger.warn("FAIL! line={}", line);
		}
	}
}
