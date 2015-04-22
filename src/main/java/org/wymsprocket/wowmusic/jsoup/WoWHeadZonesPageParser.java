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
import org.wymsprocket.wowmusic.model.WoWHeadZoneInfo;

import com.google.gson.Gson;

public class WoWHeadZonesPageParser extends JSoupDocumentParser<List<WoWHeadZoneInfo>> {

	protected final static Logger logger = LoggerFactory.getLogger(WoWHeadZonesPageParser.class);

	public WoWHeadZonesPageParser(Document document) {
		super(document);
	}

	@Override
	public List<WoWHeadZoneInfo> parse() {
		logger.info("Parsing WoWHead zones page...");
		List<WoWHeadZoneInfo> wowHeadZoneInfos = new ArrayList<WoWHeadZoneInfo>();

		Gson gson = new Gson();
		Pattern zonedataPattern = Pattern.compile(".*var zonedata=\\{\\};zonedata.zones = (.*);.new Listview.*", Pattern.DOTALL);

		Elements scriptElements = getDocument().getElementsByTag("script");
		for (Element scriptElement : scriptElements) {
			if (!scriptElement.data().isEmpty()) {
				logger.trace("Parsing script element: {}", scriptElement.data());
				Matcher matcher = zonedataPattern.matcher(scriptElement.data());
				if (matcher.matches()) {
					logger.info("Found zonedata script element, fetching zones...");
					Collections.addAll(wowHeadZoneInfos, gson.fromJson(matcher.group(1), WoWHeadZoneInfo[].class));
					break;
				}
			}
		}

		if (wowHeadZoneInfos.isEmpty()) {
			logger.error("Could not find or parse zonedata script element. The WoWHead page layout may have changed.");
		} else {
			WoWHeadZoneInfo garrisonJukebox = new WoWHeadZoneInfo();
			garrisonJukebox.setId(-10);
			garrisonJukebox.setName("Garrison Jukebox");
			garrisonJukebox.setExpansion(5);
			garrisonJukebox.setCategory(13);
			garrisonJukebox.setInstance(10);
			wowHeadZoneInfos.add(garrisonJukebox);

			Collections.sort(wowHeadZoneInfos);
		}
		return wowHeadZoneInfos;
	}
}
