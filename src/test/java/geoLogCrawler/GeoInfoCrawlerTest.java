package geoLogCrawler;

import org.junit.Test;

public class GeoInfoCrawlerTest {
	@Test
	public void doTest() {
		GeoLogCrawler sut = new GeoLogCrawler();
		sut.readAndParseLog();
	}
}
