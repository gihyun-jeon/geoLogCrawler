package geoLogCrawler.util;

import geoLogCrawler.bo.GeoLogCrawler;

import org.joda.time.DateTime;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan
@EnableAutoConfiguration
public class SampleServer {
	public static void main(String[] args) {
		SpringApplication.run(SampleServer.class, args);
		startLogCrawler();
	}

	private static void startLogCrawler() {
		DateTime start = new DateTime(2014, 8, 14, 0, 0, 0);
		DateTime end = new DateTime(2014, 8, 14, 1, 0, 0);

		GeoLogCrawler geoLogCrawler = new GeoLogCrawler();
		while (true) {
			geoLogCrawler.readAndParseLog(start, end);
			try {
				Thread.sleep(1000 * 30);
			} catch (InterruptedException e) {
				break;
			}
		}
	}
}
