package geoLogCrawler.bo;

import geoLogCrawler.bean.GeoLog;
import org.joda.time.DateTime;
import org.joda.time.Minutes;
import org.joda.time.MutableDateTime;
import org.junit.Test;

import java.util.List;

public class GeoLogCrawlerTest {
    public static final String SAMPLE_LOG_FOR_TESTCODE = "src/main/resources/do_not_commit_this_file_sampleLog_for_testcode.log";

    @Test
    public void doTest() {
        GeoLogCrawler sut = new GeoLogCrawler(SAMPLE_LOG_FOR_TESTCODE);
        sut.readAndParseLogAllRange();
    }

    @Test
    public void rangeTest() {
        GeoLogCrawler sut = new GeoLogCrawler(SAMPLE_LOG_FOR_TESTCODE);
        DateTime start = new DateTime(2014, 8, 14, 0, 0, 0);
        DateTime end = new DateTime(2014, 8, 14, 1, 0, 0);
        sut.readAndParseLog(start, end);

        List<GeoLog> actual = sut.selectGeoLogList(start, end);
        for (GeoLog gl : actual) {
            System.out.println(gl.toString());
        }
        // ddd
        // ddd
    }

    @Test
    public void simpleHttpListener() {
        GeoLogCrawler sut = new GeoLogCrawler(SAMPLE_LOG_FOR_TESTCODE);
        DateTime start = new DateTime(2014, 8, 14, 0, 0, 0);
        DateTime end = new DateTime(2014, 8, 14, 1, 0, 0);
        sut.readAndParseLog(start, end);

        List<GeoLog> actual = sut.selectGeoLogList(start, end);
        for (GeoLog gl : actual) {
            System.out.println(gl.toString());
        }

    }

    public static void run() {
        //2
        //2
        GeoLogCrawler sut = new GeoLogCrawler(SAMPLE_LOG_FOR_TESTCODE);

        MutableDateTime lastJobTime = new MutableDateTime();
        DateTime current;
        while (true) {
            current = new DateTime();

            if (Minutes.minutesBetween(lastJobTime, current).getMinutes() > 1) {

            }
        }

    }
}
