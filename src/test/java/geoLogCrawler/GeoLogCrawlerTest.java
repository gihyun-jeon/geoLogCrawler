package geoLogCrawler;

import geoLogCrawler.bo.GeoLogCrawler;

import org.joda.time.DateTime;
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
		DateTime start = new DateTime(2014, 8, 12, 15, 0, 0);
		DateTime end = new DateTime(2014, 8, 12, 18, 0, 0);
		sut.readAndParseLog(start, end);
	}
}
