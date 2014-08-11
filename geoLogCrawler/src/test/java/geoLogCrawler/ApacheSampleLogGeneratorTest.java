package geoLogCrawler;

import java.io.IOException;

import org.junit.Test;

public class ApacheSampleLogGeneratorTest {
	ApacheSampleLogGenerator sut = new ApacheSampleLogGenerator();

	@Test
	public void test() throws IOException {
		sut.genereateSampleLog();
	}

}
