package org.wymsprocket.wowmusic;

import java.io.IOException;
import java.net.URL;

import org.jsoup.nodes.Document;
import org.jukito.JukitoModule;
import org.jukito.JukitoRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.wymsprocket.wowmusic.jsoup.JSoupURLReader;
import org.wymsprocket.wowmusic.jsoup.WoWHeadZonePageParser;
import org.wymsprocket.wowmusic.jsoup.WoWHeadZonesPageParser;
import org.wymsprocket.wowmusic.model.WoWHeadZone;
import org.wymsprocket.wowmusic.model.WoWHeadZoneInfo;

@RunWith(JukitoRunner.class)
public class WoWMusicTest {

    //private final static Logger logger = LoggerFactory.getLogger(WoWMusicTest.class);

    public static class Module extends JukitoModule {
        @Override
        protected void configureTest() {

        }
    }

    @Test
    public void helloWorld() throws IOException {
        URL url = new URL("http://www.wowhead.com/zones");
        JSoupURLReader reader = new JSoupURLReader(url);
        Document document = reader.read();

        WoWHeadZonesPageParser parser = new WoWHeadZonesPageParser(document);
        for(WoWHeadZoneInfo wowHeadZoneInfo : parser.parse()) {
            System.out.println(wowHeadZoneInfo);
            URL zoneURL = new URL("http://www.wowhead.com/zone=" + wowHeadZoneInfo.getId());

            JSoupURLReader zoneReader = new JSoupURLReader(zoneURL);
            Document zoneDocument = zoneReader.read();

            WoWHeadZonePageParser zoneParser = new WoWHeadZonePageParser(wowHeadZoneInfo, zoneDocument);
            WoWHeadZone wowHeadZone = zoneParser.parse();
            System.out.println(wowHeadZone.getName() + " " + wowHeadZone.getContinent() + " " + wowHeadZone.getType());
            break;
        }
    }
}
