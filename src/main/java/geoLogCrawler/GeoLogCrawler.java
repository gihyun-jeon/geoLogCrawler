package geoLogCrawler;

import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GeoLogCrawler {
	private final static Logger logger = LoggerFactory.getLogger(GeoLogCrawler.class);

	private static final String ENCODING = "UTF-8";
	public static final String TARGET_LOG_FILE = "src/main/resources/sample.log";

	LogLineParser parser = new ApacheLogLineParser();

	public void readAndParseLog() {
		File file = new File(TARGET_LOG_FILE);
		ReverseFileReader fileReader;
		String line = "";

		try {
			fileReader = new ReverseFileReader(file, ENCODING);

			while ((line = fileReader.readLine()) != null) {
				GeoLog geoLog = parser.parseLogLine(line);
				if (null != geoLog) {
					logger.debug(geoLog.toString());

				} else {
					logger.warn("getLog is null");
				}
			}

		} catch (IOException e) {
			logger.warn("FAIL! line={}", line);
		}
	}
}
