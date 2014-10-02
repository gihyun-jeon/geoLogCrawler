package geoLogCrawler.util;

import geoLogCrawler.bo.GeoLogCrawler;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
@ComponentScan
@EnableAutoConfiguration
public class SampleServer {
	private final static Logger logger = LoggerFactory.getLogger(SampleServer.class);

	private static final int INTERVAL_SEC = 1;

	public static void main(String[] args) throws IOException {
		SpringApplication.run(SampleServer.class, args);
		generateSampleData();
	}

	@Bean
	public InternalResourceViewResolver setupViewResolver() {
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setPrefix("/WEB-INF/views/");
		resolver.setSuffix(".jsp");
		return resolver;
	}

	private static void generateSampleData() {
		GeoLogCrawler geoLogCrawler = new GeoLogCrawler();
		ApacheSampleLogGenerator apacheSampleLogGenerator = new ApacheSampleLogGenerator();

		while (true) {
			File file = new File(GeoLogCrawler.TARGET_REAL_TIME_LOG_FILE);
			file.delete();

			FileWriter out;
			try {
				out = new FileWriter(GeoLogCrawler.TARGET_REAL_TIME_LOG_FILE);

				DateTime date;
				String line;

				while (true) {
					date = new DateTime();
					line = apacheSampleLogGenerator.generateRandomLogLine(date);

					//System.out.println(line);

					out.write(line);
					out.append("\n");
					out.flush();

					if (line.hashCode() % 500 == 0) {
						Thread.sleep(1000 * INTERVAL_SEC);
					}

					DateTime end = new DateTime();
					DateTime start = end.minusSeconds(INTERVAL_SEC);
					geoLogCrawler.readAndParseLog(start, end, GeoLogCrawler.TARGET_REAL_TIME_LOG_FILE);

				}

			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
	}
}
