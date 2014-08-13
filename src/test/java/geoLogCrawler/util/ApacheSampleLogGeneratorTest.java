package geoLogCrawler.util;

import org.junit.Test;

import java.io.IOException;

public class ApacheSampleLogGeneratorTest {
	ApacheSampleLogGenerator sut = new ApacheSampleLogGenerator();

	@Test
	public void test() throws IOException {
		sut.genereateSampleLog();
	}

}
