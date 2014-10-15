package geoLogCrawler.util;

import geoLogCrawler.logParser.ApacheLogParser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.ImmutableList;

import java.util.List;
import java.util.Random;

//@Service
public class ApacheSampleLogGenerator {
	private final static Logger logger = LoggerFactory.getLogger(ApacheSampleLogGenerator.class);

	private static final String SAMPLE_IP_LIST_EAST_ASIA = "src/main/resources/sampleIpListEastAsia.txt";
	private static final String SAMPLE_IP_LIST_GLOBAL = "src/main/resources/sampleIpListGlobal.txt";

	private final String token = "-";
	private final String userId = "userIdSample";
	private final String sampleEtc = "\"GET /js/dist/app/commons/sample.js HTTP/1.1\" 200 1346 \"Success-NotRefeshTime\" \"0\"";

	private final List<String> ipList;
	private final int maxIndex;

	private final Random randomIndex = new Random();

	public ApacheSampleLogGenerator(IpLocation ipLocation) {
		ipList = initList(ipLocation);
		maxIndex = ipList.size();
	}

	public void genereateSampleLog(String logFilename) throws IOException {
		int MAX_ROW = 1000;
		FileWriter out = null;
		int rowCount = 0;

		// 39.118.13.103 - userId [04/Aug/2014:00:00:01 +0900] "GET /js/dist/app/commons/sample.js HTTP/1.1" 200 1346 "Success-NotRefeshTime" "0"
		out = new FileWriter(logFilename);
		String ip;
		DateTime date = new DateTime(2014, 8, 12, 00, 00, 00);

		while (true) {
			ip = ipList.get(randomIndex.nextInt(maxIndex));
			date = date.plusMinutes(3);
			String logLine = ip + " " + token + " " + userId + " " + date.toString(ApacheLogParser.APACHE_LOG_FORMATTER) + " " + sampleEtc;
			out.write(logLine);
			out.append("\n");
			out.flush();

			if (rowCount++ > MAX_ROW) {
				break;
			}
		}
		out.close();

	}

	public String generateRandomLogLine(DateTime date) {
		String ip = ipList.get(randomIndex.nextInt(maxIndex));
		return ip + " " + token + " " + userId + " " + date.toString(ApacheLogParser.APACHE_LOG_FORMATTER) + " " + sampleEtc;
	}

	private List<String> initList(IpLocation ipLocation) {
		ImmutableList.Builder<String> sampleIpListbulider = new ImmutableList.Builder<String>();
		BufferedReader fileReader;
		try {
			switch (ipLocation) {
				case GLOBAL:
					fileReader = new BufferedReader(new FileReader(SAMPLE_IP_LIST_GLOBAL));
					break;
				case EAST_ASIA:
					fileReader = new BufferedReader(new FileReader(SAMPLE_IP_LIST_EAST_ASIA));
					break;
				default:
					throw new RuntimeException("unknown ipLocation. ipLocation=" + ipLocation);
			}

			String sampleIp;
			while ((sampleIp = fileReader.readLine()) != null) {
				sampleIpListbulider.add(sampleIp);
			}

			fileReader.close();

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		return sampleIpListbulider.build();
	}

	enum IpLocation {
		GLOBAL, EAST_ASIA;
	}

}
