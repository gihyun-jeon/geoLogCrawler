package geoLogCrawler;

import geoLogCrawler.bo.GeoLogCrawler;

import org.joda.time.DateTime;
import org.junit.Test;

public class GeoInfoCrawlerTest {
	@Test
	public void doTest() {
		GeoLogCrawler sut = new GeoLogCrawler();
		sut.readAndParseLog();
	}

	@Test
	public void rangeTest() {
		GeoLogCrawler sut = new GeoLogCrawler();
		DateTime start = new DateTime(2014, 8, 12, 15, 00, 00);
		DateTime end = new DateTime(2014, 8, 12, 18, 00, 00);
		sut.readAndParseLog(start, end);
	}
}
