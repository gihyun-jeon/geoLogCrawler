package geoLogCrawler.bo;

import geoLogCrawler.bean.GeoLog;

import java.util.List;

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
		DateTime start = new DateTime(2014, 8, 14, 0, 0, 0);
		DateTime end = new DateTime(2014, 8, 14, 1, 0, 0);
		sut.readAndParseLog(start, end);

		List<GeoLog> actual = sut.selectGeoLogList(start, end);
		for (GeoLog gl : actual) {
			System.out.println(gl.toString());
		}

	}
}
