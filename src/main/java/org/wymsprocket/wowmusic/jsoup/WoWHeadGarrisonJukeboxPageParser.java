package org.wymsprocket.wowmusic.jsoup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.wymsprocket.wowmusic.model.WoWHeadMusicFileInfo;
import org.wymsprocket.wowmusic.model.WoWHeadZone;
import org.wymsprocket.wowmusic.model.WoWHeadZoneInfo;
import org.wymsprocket.wowmusic.util.WoWHeadMappingsProperties;

import com.google.gson.Gson;

public class WoWHeadGarrisonJukeboxPageParser extends WoWHeadZonePageParser {

	public WoWHeadGarrisonJukeboxPageParser(WoWHeadZoneInfo wowHeadZoneInfo, Document document) {
		super(wowHeadZoneInfo, document);
	}

	@Override
	public WoWHeadZone parse() {
		logger.info("Parsing WoWHead garrison jukebox page...");

		WoWHeadZone wowHeadZone = new WoWHeadZone();
		wowHeadZone.setId(getWowHeadZoneInfo().getId());
		wowHeadZone.setName(getWowHeadZoneInfo().getName());
		wowHeadZone.setContinent(wowheadMappingsProperties.getContinentName(getWowHeadZoneInfo().getCategory()));
		wowHeadZone.setExpansion(wowheadMappingsProperties.getExpansionName(getWowHeadZoneInfo().getExpansion()));
		wowHeadZone.setType(WoWHeadZone.Type.getType(new WoWHeadMappingsProperties().getType(getWowHeadZoneInfo().getInstance())));

		List<WoWHeadMusicFileInfo> wowHeadMusicFileInfos = new ArrayList<WoWHeadMusicFileInfo>();
		Elements scriptElements = getDocument().getElementsByTag("script");
		Pattern jukeboxFilePattern = Pattern.compile(".*\"files\":\\[(.*)\\]\\}", Pattern.DOTALL);
		Gson gson = new Gson();

		for (Element element : scriptElements) {
			if (element.data().indexOf("#guiderating") >= 0) {
				String[] datas = element.data().split(";");
				for (String data : datas) {
					Matcher jukeboxFileMatcher = jukeboxFilePattern.matcher(data);
					if (jukeboxFileMatcher.matches()) {
						WoWHeadMusicFileInfo wowHeadMusicFileInfo = gson.fromJson(jukeboxFileMatcher.group(1), WoWHeadMusicFileInfo.class);
						wowHeadMusicFileInfos.add(wowHeadMusicFileInfo);
					}
				}
			}
		}
		for (WoWHeadMusicFileInfo wowHeadMusicFileInfo : wowHeadMusicFileInfos) {
			wowHeadMusicFileInfo.setZoneId(wowHeadZone.getId());
		}
		Collections.sort(wowHeadMusicFileInfos);

		wowHeadZone.setWowHeadMusicFileInfos(wowHeadMusicFileInfos);
		return wowHeadZone;
	}
}
