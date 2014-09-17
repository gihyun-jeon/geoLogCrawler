package geoLogCrawler.util;

import geoLogCrawler.bean.GeoLog;
import geoLogCrawler.bo.GeoLogCrawler;
import geoLogCrawler.dao.GeoLogLocalMemoryDAO;

import java.util.List;
import java.util.Locale;
import java.util.Map;

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
import com.google.api.client.util.Maps;

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
		
		GeoLogCrawler geoLogCrawler = new GeoLogCrawler();
		while (true) {
			geoLogCrawler.readAndParseLog(start, end);
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
		Map<String, Object> resultMap = Maps.newHashMap();

		String start = (String)req.getParameter("start");
		String end = (String)req.getParameter("end");
		resultMap.put("start", start);
		resultMap.put("end", end);

		DateTime startDateTime = DateTime.parse(start, DATE_FORMATTER);
		DateTime endDateTime = DateTime.parse(end, DATE_FORMATTER);

		GeoLogLocalMemoryDAO geoLogLocalMemoryDAO = new GeoLogLocalMemoryDAO();
		List<GeoLog> geoLogList = geoLogLocalMemoryDAO.selectGeoLogList(startDateTime, endDateTime);
		resultMap.put("geoLogList", geoLogList);

		ObjectMapper mapper = new ObjectMapper();
		String returnString;

		try {
			returnString = mapper.writeValueAsString(resultMap);
		} catch (Exception e) {
			returnString = e.getMessage();
		}
		
		return returnString;
	}
}
