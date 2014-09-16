package geoLogCrawler.bo;

import geoLogCrawler.bean.GeoLog;

import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.Minutes;
import org.joda.time.MutableDateTime;
import org.junit.Test;

public class GeoLogCrawlerTest {
	@Test
	public void doTest() {
		GeoLogCrawler sut = new GeoLogCrawler();
		sut.readAndParseLog();
	}

	@Test
	public void rangeTest() {
		GeoLogCrawler sut = new GeoLogCrawler();
		DateTime start = new DateTime(2014, 8, 14, 0, 0, 0);
		DateTime end = new DateTime(2014, 8, 14, 1, 0, 0);
		sut.readAndParseLog(start, end);

		List<GeoLog> actual = sut.selectGeoLogList(start, end);
		for (GeoLog gl : actual) {
			System.out.println(gl.toString());
		}

	}

	@Test
	public void simpleHttpListener() {
		GeoLogCrawler sut = new GeoLogCrawler();
		DateTime start = new DateTime(2014, 8, 14, 0, 0, 0);
		DateTime end = new DateTime(2014, 8, 14, 1, 0, 0);
		sut.readAndParseLog(start, end);

		List<GeoLog> actual = sut.selectGeoLogList(start, end);
		for (GeoLog gl : actual) {
			System.out.println(gl.toString());
		}

	}

	public static void run() {
		GeoLogCrawler sut = new GeoLogCrawler();

		MutableDateTime lastJobTime = new MutableDateTime();
		DateTime current;
		while (true) {
			current = new DateTime();

			if (Minutes.minutesBetween(lastJobTime, current).getMinutes() > 1) {

			}
		}

	}
}
