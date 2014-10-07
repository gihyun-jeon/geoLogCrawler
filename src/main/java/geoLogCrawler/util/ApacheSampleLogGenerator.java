package geoLogCrawler.util;

import geoLogCrawler.bo.GeoLogCrawler;
import geoLogCrawler.logParser.ApacheLogParser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ApacheSampleLogGenerator {
	private final static Logger logger = LoggerFactory.getLogger(ApacheSampleLogGenerator.class);

	List<String> ipList = initList();
	int maxIndex = ipList.size();
	int maxSleepIntervalMsec = 1000;

	Random randomIndex = new Random();
	Random randomSleepInterval = new Random();
	String token = "-";
	String userId = "userIdSample";
	String sampleEtc = "\"GET /js/dist/app/commons/sample.js HTTP/1.1\" 200 1346 \"Success-NotRefeshTime\" \"0\"";

	public void genereateSampleLog() throws IOException {
		int MAX_ROW = 1000;
		FileWriter out = null;
		int rowCount = 0;

		// 39.118.13.103 - userId [04/Aug/2014:00:00:01 +0900] "GET /js/dist/app/commons/sample.js HTTP/1.1" 200 1346 "Success-NotRefeshTime" "0"
		out = new FileWriter(GeoLogCrawler.TARGET_SAMPLE_LOG_FILE);
		String ip;
		DateTime date = new DateTime(2014, 8, 12, 00, 00, 00);
		while (true) {
			ip = ipList.get(randomIndex.nextInt(maxIndex));
			date = date.plusMinutes(3);
			String line = ip + " " + token + " " + userId + " " + date.toString(ApacheLogParser.APACHE_LOG_FORMATTER) + " " + sampleEtc;
			out.write(line);
			out.append("\n");
			out.flush();
			//Thread.sleep(randomSleepInterval.nextInt(maxSleepIntervalMsec));

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

	@SuppressWarnings("resource")
	private static List<String> initList() {
		List<String> sampleIpList = new ArrayList<String>();

		BufferedReader fileReader;
		try {
			fileReader = new BufferedReader(new FileReader("src/main/resources/sampleIpList.txt"));
			//fileReader = new BufferedReader(new FileReader("src/main/resources/sampleIpListEastAsia.txt"));
			String sampleIp;
			while ((sampleIp = fileReader.readLine()) != null) {
				sampleIpList.add(sampleIp);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);

		}

		return sampleIpList;
	}

}
