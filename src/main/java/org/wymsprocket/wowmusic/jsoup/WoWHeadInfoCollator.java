package org.wymsprocket.wowmusic.jsoup;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wymsprocket.wowmusic.model.WoWHeadMusicFileInfo;
import org.wymsprocket.wowmusic.model.WoWHeadZone;
import org.wymsprocket.wowmusic.model.WoWHeadZoneInfo;

public class WoWHeadInfoCollator {

	private final static Logger logger = LoggerFactory.getLogger(WoWHeadInfoCollator.class);

	public List<WoWHeadZone> getWoWHeadZones() throws IOException {
		List<WoWHeadZone> wowHeadZones = new ArrayList<WoWHeadZone>();

		URL url = new URL("http://www.wowhead.com/zones");
		JSoupURLReader reader = new JSoupURLReader(url);
		Document document = reader.read();

		WoWHeadZonesPageParser parser = new WoWHeadZonesPageParser(document);
		List<WoWHeadZoneInfo> wowHeadZoneInfos = parser.parse();

		logger.info("Found {} zones...", wowHeadZoneInfos.size());

		int count = 0;
		Map<Integer, WoWHeadZone> zoneMap = new HashMap<Integer, WoWHeadZone>();

		for (WoWHeadZoneInfo wowHeadZoneInfo : wowHeadZoneInfos) {
			WoWHeadZone wowHeadZone = getWoWHeadZone(wowHeadZoneInfo);
			wowHeadZones.add(wowHeadZone);
			zoneMap.put(wowHeadZone.getId(), wowHeadZone);
			++count;
			logger.info("Fetched info for {} ({})...", wowHeadZone.getName(), count);
		}

		for (WoWHeadZone wowHeadZone : wowHeadZones) {
			if (wowHeadZone.getContinent() == null
					&& wowHeadZone.getLocation() != 0) {
				WoWHeadZone locationZone = zoneMap.get(wowHeadZone.getLocation());
				if (locationZone != null) {
					wowHeadZone.setContinent(locationZone.getContinent());
				}
			}
		}
		return wowHeadZones;
	}

	public List<WoWHeadMusicFileInfo> getWoWHeadMusicFileInfos() throws IOException {
	    List<WoWHeadMusicFileInfo> wowHeadMusicFilesInfos = new ArrayList<WoWHeadMusicFileInfo>();
	    for(WoWHeadZone wowHeadZone : getWoWHeadZones()) {
	        wowHeadMusicFilesInfos.addAll(wowHeadZone.getWowHeadMusicFileInfos());
	    }
	    Collections.sort(wowHeadMusicFilesInfos);
	    return wowHeadMusicFilesInfos;
	}

	public WoWHeadZone getWoWHeadZone(WoWHeadZoneInfo wowHeadZoneInfo) throws IOException {
		URL url = new URL("http://www.wowhead.com/zone=" + wowHeadZoneInfo.getId());
		JSoupURLReader reader = new JSoupURLReader(url);
		Document document = reader.read();
		return getWoWHeadZone(wowHeadZoneInfo, document);
	}

	public WoWHeadZone getWoWHeadZone(WoWHeadZoneInfo wowHeadZoneInfo, File file) throws IOException {
		JSoupFileReader reader = new JSoupFileReader(file);
		Document document = reader.read();
		return getWoWHeadZone(wowHeadZoneInfo, document);
	}

	private WoWHeadZone getWoWHeadZone(WoWHeadZoneInfo wowHeadZoneInfo, Document document) {
		WoWHeadZonePageParser zoneParser = new WoWHeadZonePageParser(wowHeadZoneInfo, document);
		WoWHeadZone wowHeadZone = zoneParser.parse();
		return wowHeadZone;
	}
}
