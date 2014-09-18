package geoLogCrawler.util;

import geoLogCrawler.bean.GeoLog;
import geoLogCrawler.dao.GeoLogLocalMemoryDAO;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.repackaged.com.google.common.base.Strings;
import com.google.api.client.util.Maps;

@Controller
public class ApiController {
	private final static Logger logger = LoggerFactory.getLogger(ApiController.class);
	public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormat.forPattern("yyyy-MM-dd_HH:mm:ss").withLocale(Locale.KOREA);
	private static final int DEFAULT_TIME_RANGE_SEC = 5;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index(Model model) {
		// let’s pass some variables to the view script
		model.addAttribute("wisdom", "Goodbye XML");
		return "index";
	}

	@RequestMapping(value = "/geoLogMap", method = RequestMethod.GET)
	String viewGeoLogMap(HttpServletRequest req, Model model) {
		Map<String, Object> resultMap = Maps.newHashMap();

		String start = req.getParameter("start");
		String end = req.getParameter("end");
		resultMap.put("start", start);
		resultMap.put("end", end);

		DateTime startDateTime;
		DateTime endDateTime;
		if (Strings.isNullOrEmpty(start) || Strings.isNullOrEmpty(end)) {
			endDateTime = new DateTime();
			startDateTime = endDateTime.minusSeconds(DEFAULT_TIME_RANGE_SEC);
		} else {
			startDateTime = DateTime.parse(start, DATE_FORMATTER);
			endDateTime = DateTime.parse(end, DATE_FORMATTER);
		}

		GeoLogLocalMemoryDAO geoLogLocalMemoryDAO = new GeoLogLocalMemoryDAO();
		List<GeoLog> geoLogList = geoLogLocalMemoryDAO.selectGeoLogList(startDateTime, endDateTime);
		resultMap.put("geoLogList", geoLogList);
		resultMap.put("geoLogListSize", geoLogList.size());

		ObjectMapper mapper = new ObjectMapper();
		String returnString;

		try {
			returnString = mapper.writeValueAsString(resultMap);
		} catch (Exception e) {
			returnString = e.getMessage();
		}

		model.addAttribute("geoLogList", geoLogList);

		return "geoLogMap";
	}

	// http://localhost:8080/api/getGeoLogList
	// http://localhost:8080/api/getGeoLogList?start=YYYY-MM-DD_HH:mm:ss&end=YYYY-MM-DD_HH:mm:ss
	// http://localhost:8080/api/getGeoLogList?start=2014-08-14_00:00:00&end=2014-08-14_01:00:00
	@RequestMapping("/api/getGeoLogList")
	@ResponseBody
	String getGeoLogList(HttpServletRequest req) {
		Map<String, Object> resultMap = Maps.newHashMap();

		String start = req.getParameter("start");
		String end = req.getParameter("end");
		resultMap.put("start", start);
		resultMap.put("end", end);

		DateTime startDateTime;
		DateTime endDateTime;
		if (Strings.isNullOrEmpty(start) || Strings.isNullOrEmpty(end)) {
			endDateTime = new DateTime();
			startDateTime = endDateTime.minusSeconds(DEFAULT_TIME_RANGE_SEC);
		} else {
			startDateTime = DateTime.parse(start, DATE_FORMATTER);
			endDateTime = DateTime.parse(end, DATE_FORMATTER);
		}

		GeoLogLocalMemoryDAO geoLogLocalMemoryDAO = new GeoLogLocalMemoryDAO();
		List<GeoLog> geoLogList = geoLogLocalMemoryDAO.selectGeoLogList(startDateTime, endDateTime);
		resultMap.put("geoLogList", geoLogList);
		resultMap.put("geoLogListSize", geoLogList.size());

		ObjectMapper mapper = new ObjectMapper();
		String returnString;

		try {
			returnString = mapper.writeValueAsString(resultMap);
		} catch (Exception e) {
			returnString = e.getMessage();
		}

		logger.info(returnString);
		return returnString;
	}
}
