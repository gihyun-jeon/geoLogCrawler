package geoLogCrawler.util;

import geoLogCrawler.bo.GeoLogCrawlerTest;
import geoLogCrawler.util.ApacheSampleLogGenerator.IpLocation;

import java.io.IOException;

import org.junit.Ignore;
import org.junit.Test;

@Ignore
public class ApacheSampleLogGeneratorTest {
	ApacheSampleLogGenerator sut = new ApacheSampleLogGenerator(IpLocation.GLOBAL);

	@Test
	public void test() throws IOException {
		sut.genereateSampleLog(GeoLogCrawlerTest.SAMPLE_LOG_FOR_TESTCODE);
	}

}
