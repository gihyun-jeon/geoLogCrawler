# summary

Server access log to Gps coordinate.
Store it.
And show Access coordinate on Map.

# details
Set crawl targt Log Path.
may be 1 min, Read log reverse, 1min range log.

parse log by user define pattern.
get datetime, ip, etc tags.
ip to GPS by maxmind.geoip2 lite sample DB.
save on user define DAO.

show it google map Web UI.

# example
clone project, convert to Spring Project.

Run SampleServer.java

It generate sampleLog and Crawlering.

you can view it On http://localhost:8080/gmap.html

<img src="http://postfiles15.naver.net/20141015_14/xelloss25_1413359664700K9ngj_PNG/%BD%BA%BC%A61.png?type=w2">
