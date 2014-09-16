package geoLogCrawler.util;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

@SuppressWarnings("restriction")
public class SampleServer {
	public static void main(String[] args) throws Exception {
		HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
		server.createContext("/api", new MyHandler());
		server.setExecutor(null); // creates a default executor
		server.start();

		System.out.println("start done.");
	}

	static class MyHandler implements HttpHandler {
		private static final String PARAM_MAP_NAME = "parameters";
		private static final String PARAM_KEY_START = "start";

		@SuppressWarnings("unchecked")
		public void handle(HttpExchange httpExchange) throws IOException {

			parseGetParameters(httpExchange);

			Map<String, Object> param = (Map<String, Object>)httpExchange.getAttribute(PARAM_MAP_NAME);
			String start = (String)param.get(PARAM_KEY_START);

			String response = "This is the response. start=" + start;
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
