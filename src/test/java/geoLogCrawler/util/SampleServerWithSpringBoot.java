package geoLogCrawler.util;

import geoLogCrawler.bean.GeoLog;
import geoLogCrawler.bo.GeoLogCrawler;
import geoLogCrawler.dao.GeoLogLocalMemoryDAO;

import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
@ComponentScan
@EnableAutoConfiguration
@Controller
public class SampleServerWithSpringBoot {
	public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormat.forPattern("yyyy-MM-dd_HH:mm:ss").withLocale(Locale.KOREA);

	public static void main(String[] args) {
		SpringApplication.run(SampleServerWithSpringBoot.class, args);
		startLogCrawler();
	}

	private static void startLogCrawler() {
		DateTime start = new DateTime(2014, 8, 14, 0, 0, 0);
		DateTime end = new DateTime(2014, 8, 14, 1, 0, 0);

		GeoLogCrawler sut = new GeoLogCrawler();
		while (true) {
			sut.readAndParseLog(start, end);
			try {
				Thread.sleep(1000 * 30);
			} catch (InterruptedException e) {
			}
		}
	}

	// http://localhost:8080/api/getGeoLogList?start=YYYY-MM-DD_HH:mm:ss&end=YYYY-MM-DD_HH:mm:ss
	// http://localhost:8080/api/getGeoLogList?start=2014-08-14_00:00:00&end=2014-08-14_01:00:00
	@RequestMapping("/api/getGeoLogList")
	@ResponseBody
	String getGeoLogList(HttpServletRequest req, Model model) {
		StringBuffer sb = new StringBuffer();
		String start = (String)req.getParameter("start");
		String end = (String)req.getParameter("end");

		sb.append("start=" + start);
		sb.append("\n");
		sb.append("end=" + end);
		sb.append("\n");
		
		DateTime startDateTime = DateTime.parse(start, DATE_FORMATTER);
		DateTime endDateTime = DateTime.parse(end, DATE_FORMATTER);
		

		GeoLogLocalMemoryDAO geoLogLocalMemoryDAO = new GeoLogLocalMemoryDAO();
		List<GeoLog> list = geoLogLocalMemoryDAO.selectGeoLogList(startDateTime, endDateTime);

		sb.append("list=" + list.toString());

		return sb.toString();
	}
}
