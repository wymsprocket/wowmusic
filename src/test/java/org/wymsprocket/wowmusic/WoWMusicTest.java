package org.wymsprocket.wowmusic;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.jukito.JukitoModule;
import org.jukito.JukitoRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.wymsprocket.wowmusic.model.WoWHeadZone;
import org.wymsprocket.wowmusic.util.WoWHeadZoneCsvFileReader;

@RunWith(JukitoRunner.class)
public class WoWMusicTest {

	// private final static Logger logger = LoggerFactory.getLogger(WoWMusicTest.class);

	public static class Module extends JukitoModule {
		@Override
		protected void configureTest() {

		}
	}

	@Test
	public void helloWorld() throws IOException {
		// WoWHeadInfoCollator wowHeadInfoCollator = new WoWHeadInfoCollator();
		//
		// List<WoWHeadZone> wowHeadZones = wowHeadInfoCollator.getWoWHeadZones();
		// File csvFile = new File("wowhead-zones.csv");
		// CsvFileWriter<WoWHeadZone> writer = new CsvFileWriter<WoWHeadZone>();
		// writer.setObjects(wowHeadZones);
		// writer.writeTo(csvFile);
		WoWHeadZoneCsvFileReader reader = new WoWHeadZoneCsvFileReader(new File("src/test/resources/wowhead-zones.csv"));
		List<WoWHeadZone> wowHeadZones = reader.read();
		for (WoWHeadZone wowHeadZone : wowHeadZones) {
			System.out.println(wowHeadZone);
		}
	}
}
