package geoLogCrawler;

import org.junit.Test;

public class GeoInfoCrawlerTest {
	@Test
	public void doTest() {
		GeoInfoCrawler sut = new GeoInfoCrawler();
		sut.readAndParseLog();
	}
}
