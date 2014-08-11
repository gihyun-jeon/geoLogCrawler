package geoLogCrawler;

import java.io.File;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IpCrawler {
	private final static Logger logger = LoggerFactory.getLogger(IpCrawler.class);

	private static final String ENCODING = "UTF-8";
	public static final String logFileName = "src/main/resources/sample.log";

	LogLineParser parser = new ApacheLogLineParser();

	public void readAndParseLogLine() {
		File file = new File(logFileName);
		ReverseFileReader fileReader;
		String line = "";
		try {
			fileReader = new ReverseFileReader(file, ENCODING);
			while ((line = fileReader.readLine()) != null) {

				GeoLog geoLog = parser.parseLogLine(line);

				if (null != geoLog) {
					logger.debug(geoLog.toString());
				} else {
					logger.debug("getLog is null");
				}

			}

		} catch (IOException e) {
			logger.warn("FAIL! line={}", line);
		}
	}
}
