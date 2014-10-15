package geoLogCrawler.util;

import geoLogCrawler.bean.GeoLog;
import geoLogCrawler.gps.GpsCoordinareParser;
import geoLogCrawler.gps.GroLite2GpsCoordinareParser;

import java.io.BufferedReader;
import java.io.FileReader;

import org.junit.Test;

import com.google.api.client.util.Lists;

import java.util.List;

public class SampleIpOrganizerForTest {

	@Test
	public void doJob() {
		List<String> sampleIpList = Lists.newArrayList();
		BufferedReader fileReader;
		try {
			fileReader = new BufferedReader(new FileReader("src/main/resources/sampleIpList.txt"));
			String sampleIp;
			while ((sampleIp = fileReader.readLine()) != null) {
				sampleIpList.add(sampleIp);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		GpsCoordinareParser gp = new GroLite2GpsCoordinareParser();
		List<GeoLog> uniqGeoLogList = Lists.newArrayList();
		GeoLog geoLog;
		for (String ip : sampleIpList) {
			geoLog = new GeoLog();
			geoLog.setIp(ip);
			gp.composeGpsCoordinate(geoLog);

			if (null == geoLog.getLatitude() || null == geoLog.getLongitude()) {
				continue;
			}

			if (hasSameGeoLog(uniqGeoLogList, geoLog)) {
				continue;
			}

			uniqGeoLogList.add(geoLog);
		}

		System.out.println("########### RESLUT ###########");
		for (GeoLog oGeoLog : uniqGeoLogList) {
			if ("China".equals(oGeoLog.getTagJsonString()) || "Republic of Korea".equals(oGeoLog.getTagJsonString()) || "Japan".equals(oGeoLog.getTagJsonString())) {

				System.out.println(oGeoLog.getIp());
			}

		}

	}

	private boolean hasSameGeoLog(List<GeoLog> uniqGeoLogList, GeoLog geoLog) {
		for (GeoLog oGeoLog : uniqGeoLogList) {
			if (oGeoLog.getLatitude().equals(geoLog.getLatitude()) && oGeoLog.getLongitude().equals(geoLog.getLongitude())) {
				return true;
			}
		}
		return false;
	}

}
