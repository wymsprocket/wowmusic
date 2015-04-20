package org.wymsprocket.wowmusic.jsoup;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wymsprocket.wowmusic.model.WoWHeadZone;
import org.wymsprocket.wowmusic.model.WoWHeadZoneInfo;
import org.wymsprocket.wowmusic.util.WoWHeadMappingsProperties;

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
		return wowHeadZone;
	}
}
