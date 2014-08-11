package geoLogCrawler;

import org.joda.time.DateTime;
import org.junit.Test;

public class DateTimeTest {

	@Test
	public void test() {
		DateTime dt = new DateTime();
		String aa;
		aa = dt.toString();

		String sampleLogLine = "[04/Aug/2014:23:10:00 +0900]";
		//System.out.println(sampleLogLine);

		aa = dt.toString(ApacheLogLineParser.APACHE_LOG_FORMATTER);
		//System.out.println(aa);

		DateTime ddd = DateTime.parse(sampleLogLine, ApacheLogLineParser.APACHE_LOG_FORMATTER);

	}
}
