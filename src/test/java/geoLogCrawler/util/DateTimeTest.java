package geoLogCrawler.util;

import geoLogCrawler.logParser.ApacheLogParser;

import org.joda.time.DateTime;
import org.junit.Test;

public class DateTimeTest {

	@Test
	public void test() {
		DateTime dt = new DateTime();
		String aa;
		aa = dt.toString();
		System.out.print(aa);

		String sampleLogLine = "[04/Aug/2014:23:10:00 +0900]";
		System.out.println(sampleLogLine);

		aa = dt.toString(ApacheLogParser.APACHE_LOG_FORMATTER);
		System.out.println(aa);

		//DateTime ddd = DateTime.parse(sampleLogLine, ApacheLogParser.APACHE_LOG_FORMATTER);

	}
}
