package geoLogCrawler.util;

import geoLogCrawler.bo.GeoLogCrawler;
import geoLogCrawler.util.ApacheSampleLogGenerator.IpLocation;

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

	private static final String SAMPLE_LOG_FOR_REALTIME_DEMO = "src/main/resources/do_not_commit_this_file_sampleLog_for_realtime_demo.log";
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
		GeoLogCrawler geoLogCrawler = new GeoLogCrawler(SAMPLE_LOG_FOR_REALTIME_DEMO);
		//ApacheSampleLogGenerator apacheSampleLogGenerator = new ApacheSampleLogGenerator(IpLocation.GLOBAL);
		ApacheSampleLogGenerator apacheSampleLogGenerator = new ApacheSampleLogGenerator(IpLocation.EAST_ASIA);

		while (true) {
			File file = new File(SAMPLE_LOG_FOR_REALTIME_DEMO);
			file.delete();

			FileWriter out;
			try {
				out = new FileWriter(SAMPLE_LOG_FOR_REALTIME_DEMO);

				DateTime date;
				String line;

				DateTime end, start;
				int logCount = 0;
				while (true) {
					date = new DateTime();
					line = apacheSampleLogGenerator.generateRandomLogLine(date);
					out.write(line);
					out.append("\n");
					out.flush();

					logCount++;
					if (line.hashCode() % 50 == 0 || logCount > 50) {
						end = new DateTime();
						start = end.minusSeconds(INTERVAL_SEC);
						geoLogCrawler.readAndParseLog(start, end);

						Thread.sleep(900 * INTERVAL_SEC);
						logCount = 0;
					}

				}

			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
	}
}
