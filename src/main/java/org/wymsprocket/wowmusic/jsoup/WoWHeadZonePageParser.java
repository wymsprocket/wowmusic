package org.wymsprocket.wowmusic.jsoup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wymsprocket.wowmusic.model.WoWHeadMusicFileInfo;
import org.wymsprocket.wowmusic.model.WoWHeadZone;
import org.wymsprocket.wowmusic.model.WoWHeadZoneInfo;
import org.wymsprocket.wowmusic.util.WoWHeadMappingsProperties;

import com.google.gson.Gson;

public class WoWHeadZonePageParser extends JSoupDocumentParser<WoWHeadZone> {

	private final static Logger logger = LoggerFactory.getLogger(WoWHeadZonePageParser.class);
	private WoWHeadZoneInfo wowHeadZoneInfo;
	private final static WoWHeadMappingsProperties wowheadMappingsProperties = new WoWHeadMappingsProperties();

	public WoWHeadZonePageParser(WoWHeadZoneInfo wowHeadZoneInfo, Document document) {
		super(document);
		this.wowHeadZoneInfo = wowHeadZoneInfo;
	}

	@Override
	public WoWHeadZone parse() {
		logger.info("Parsing WoWHead zone page...");
		WoWHeadZone wowHeadZone = new WoWHeadZone();
		wowHeadZone.setId(this.wowHeadZoneInfo.getId());
		wowHeadZone.setName(this.wowHeadZoneInfo.getName());
		wowHeadZone.setExpansion(wowheadMappingsProperties.getExpansionName(this.wowHeadZoneInfo.getExpansion()));
		wowHeadZone.setType(WoWHeadZone.Type.getType(wowheadMappingsProperties.getType(this.wowHeadZoneInfo.getInstance())));

		if (this.wowHeadZoneInfo.hasContinent()) {
			wowHeadZone.setContinent(wowheadMappingsProperties.getContinentName(this.wowHeadZoneInfo.getCategory()));
		} else {
			logger.debug("Looking for location for zone {}...", this.wowHeadZoneInfo.getName());

			Pattern locationPattern = Pattern.compile(".*Markup.printHtml.*\\\\x5D(Location|Entrance).*?zone.*?\\\\x3D(.*?)\\\\(x20|x5D).*infobox-contents.*", Pattern.DOTALL);
			Elements scriptElements = getDocument().getElementsByTag("script");

			for (Element scriptElement : scriptElements) {
				Matcher locationMatcher = locationPattern.matcher(scriptElement.data());
				if (locationMatcher.matches()) {
					int location = Integer.parseInt(locationMatcher.group(2));
					wowHeadZone.setLocation(location);
				}
			}
		}

		String zoneContinent = wowheadMappingsProperties.getZoneContinent(wowHeadZone.getId());
		if(zoneContinent != null) {
		    wowHeadZone.setContinent(zoneContinent);
		}

		String zoneExpansion = wowheadMappingsProperties.getZoneExpansion(wowHeadZone.getId());
		if(zoneExpansion != null) {
		    wowHeadZone.setExpansion(zoneExpansion);
		}

		List<WoWHeadMusicFileInfo> wowHeadMusicFileInfos = new ArrayList<WoWHeadMusicFileInfo>();
		Elements scriptElements = getDocument().getElementsByTag("script");
		Pattern audioControlsPattern = Pattern.compile(".*new AudioControls\\(\\)\\).init\\((.*),\\$WH\\.ge\\('zonemusicdiv-zonemusic.*", Pattern.DOTALL);
		Gson gson = new Gson();

		for(Element scriptElement : scriptElements) {
		    Matcher audioControlsMatcher = audioControlsPattern.matcher(scriptElement.data());
		    if(audioControlsMatcher.matches()) {
		        Collections.addAll(wowHeadMusicFileInfos, gson.fromJson(audioControlsMatcher.group(1), WoWHeadMusicFileInfo[].class));
		    }
		}
		for(WoWHeadMusicFileInfo wowHeadMusicFileInfo : wowHeadMusicFileInfos) {
		    wowHeadMusicFileInfo.setZoneId(wowHeadZone.getId());
		}
		Collections.sort(wowHeadMusicFileInfos);

		wowHeadZone.setWowHeadMusicFileInfos(wowHeadMusicFileInfos);
		return wowHeadZone;
	}
}
