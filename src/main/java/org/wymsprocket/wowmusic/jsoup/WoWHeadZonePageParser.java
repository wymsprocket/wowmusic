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

public class WoWHeadZonePageParser extends JSoupDocumentParser<WoWHeadZone> {

    private final static Logger logger = LoggerFactory.getLogger(WoWHeadZonePageParser.class);
    private WoWHeadZoneInfo wowHeadZoneInfo;

    public WoWHeadZonePageParser(WoWHeadZoneInfo wowHeadZoneInfo, Document document) {
        super(document);
        this.wowHeadZoneInfo = wowHeadZoneInfo;
    }

    @Override
    public WoWHeadZone parse() {
        logger.info("Parsing WoWHead zone page...");

        Pattern titlePattern = Pattern.compile("(.*) - Zone - World of Warcraft", Pattern.DOTALL);
        Pattern infoboxPattern = Pattern.compile(".*Markup.printHtml.*infobox-contents.*", Pattern.DOTALL);
        Pattern continentPattern = Pattern.compile(".*Continent\\\\x3A\\\\x20(.*?)\\\\x5B.*", Pattern.DOTALL);

        Matcher titleMatcher = titlePattern.matcher(getDocument().getElementsByTag("title").first().text());

        if(titleMatcher.matches()) {
            WoWHeadZone wowHeadZone = new WoWHeadZone(titleMatcher.group(1));

            Elements scriptElements = getDocument().getElementsByTag("script");
            for(Element scriptElement : scriptElements) {
                Matcher infoboxMatcher = infoboxPattern.matcher(scriptElement.data());
                if(infoboxMatcher.matches()) {
                    System.out.println(scriptElement);
                    Matcher continentMatcher = continentPattern.matcher(scriptElement.data());
                    if(continentMatcher.matches()) {
                        wowHeadZone.setContinent(continentMatcher.group(1).replace("\\x20"," "));
                    }
                }
            }

            return wowHeadZone;
        }
        // We are making Sherwood look like Guardiola
        return null;
    }

}
