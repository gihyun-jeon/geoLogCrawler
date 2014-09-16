package geoLogCrawler.util;

import geoLogCrawler.bo.GeoLogCrawler;
import geoLogCrawler.dao.GeoLogLocalMemoryDAO;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

@SuppressWarnings("restriction")
public class SampleServer {
	public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormat.forPattern("YYYY-MM-DD HH:mm:ss").withLocale(Locale.KOREA);

	public static void main(String[] args) throws Exception {
		startHttpListener();

		GeoLogCrawler sut = new GeoLogCrawler();
		DateTime start = new DateTime(2014, 8, 14, 0, 0, 0);
		DateTime end = new DateTime(2014, 8, 14, 1, 0, 0);

		GeoLogLocalMemoryDAO geoLogLocalMemoryDAO = new GeoLogLocalMemoryDAO();
		DateTime eventTime = DateTime.parse("2014-10-01 10:10:10", DATE_FORMATTER);
		String key = geoLogLocalMemoryDAO.getKey(eventTime);
		System.out.println(key);
		System.out.println(key);
		System.out.println(key);
		System.out.println(key);

		while (true) {
			sut.readAndParseLog(start, end);
			Thread.sleep(1000 * 30);
		}
	}

	// http://localhost:8000/api/getGeoLogList
	private static void startHttpListener() throws IOException {
		System.out.println("init httpListener.");
		HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
		server.createContext("/api/getGeoLogList", new MyHandler());
		server.setExecutor(null); // creates a default executor
		server.start();
		System.out.println("init httpListener done.");
	}

	static class MyHandler implements HttpHandler {
		private static final String PARAM_MAP_NAME = "parameters";
		private static final String PARAM_KEY_TARGET_DATE_TIME = "targetDatetime";

		GeoLogLocalMemoryDAO geoLogLocalMemoryDAO = new GeoLogLocalMemoryDAO();

		@SuppressWarnings("unchecked")
		public void handle(HttpExchange httpExchange) throws IOException {
			parseGetParameters(httpExchange);

			Map<String, Object> param = (Map<String, Object>)httpExchange.getAttribute(PARAM_MAP_NAME);

			StringBuffer sb = new StringBuffer();
			sb.append("request param=" + param.toString());
			sb.append("\n");

			DateTime start = new DateTime(2014, 8, 14, 0, 0, 0);
			DateTime end = new DateTime(2014, 8, 14, 0, 0, 0);
			
			geoLogLocalMemoryDAO.selectGeoLogList(start, end);

			sb.append("result=");

			String response = sb.toString();
			httpExchange.sendResponseHeaders(200, response.length());
			OutputStream os = httpExchange.getResponseBody();
			os.write(response.getBytes());
			os.close();
		}

		private void parseGetParameters(HttpExchange exchange) throws UnsupportedEncodingException {

			Map<String, Object> parameters = new HashMap<String, Object>();
			URI requestedUri = exchange.getRequestURI();
			String query = requestedUri.getRawQuery();
			parseQuery(query, parameters);
			exchange.setAttribute(PARAM_MAP_NAME, parameters);
		}

		@SuppressWarnings("unchecked")
		private void parseQuery(String query, Map<String, Object> parameters) throws UnsupportedEncodingException {

			if (query != null) {
				String pairs[] = query.split("[&]");

				for (String pair : pairs) {
					String param[] = pair.split("[=]");

					String key = null;
					String value = null;
					if (param.length > 0) {
						key = URLDecoder.decode(param[0], System.getProperty("file.encoding"));
					}

					if (param.length > 1) {
						value = URLDecoder.decode(param[1], System.getProperty("file.encoding"));
					}

					if (parameters.containsKey(key)) {
						Object obj = parameters.get(key);
						if (obj instanceof List<?>) {
							List<String> values = (List<String>)obj;
							values.add(value);
						} else if (obj instanceof String) {
							List<String> values = new ArrayList<String>();
							values.add((String)obj);
							values.add(value);
							parameters.put(key, values);
						}
					} else {
						parameters.put(key, value);
					}
				}
			}
		}

	}

}
