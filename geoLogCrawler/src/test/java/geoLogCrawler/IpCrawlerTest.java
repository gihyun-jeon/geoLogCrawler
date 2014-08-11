package geoLogCrawler;

import org.junit.Test;

public class IpCrawlerTest {
	@Test
	public void doTest() {
		IpCrawler sut = new IpCrawler();
		sut.readAndParseLogLine();

	}

}
