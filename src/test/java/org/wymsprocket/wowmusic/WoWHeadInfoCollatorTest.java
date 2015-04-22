package org.wymsprocket.wowmusic;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.jukito.JukitoModule;
import org.jukito.JukitoRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.wymsprocket.wowmusic.jsoup.WoWHeadInfoCollator;
import org.wymsprocket.wowmusic.model.WoWHeadMusicFileInfo;
import org.wymsprocket.wowmusic.model.WoWHeadZone;
import org.wymsprocket.wowmusic.util.CsvFileWriter;

@RunWith(JukitoRunner.class)
public class WoWHeadInfoCollatorTest {

	public static class Module extends JukitoModule {
		@Override
		protected void configureTest() {

		}
	}

	@Test
	public void getWoWHeadZones() throws IOException {
		WoWHeadInfoCollator wowHeadInfoCollator = new WoWHeadInfoCollator();
		List<WoWHeadZone> wowHeadZones = wowHeadInfoCollator.getWoWHeadZones();

		CsvFileWriter<WoWHeadZone> writer = new CsvFileWriter<WoWHeadZone>();
		writer.setObjects(wowHeadZones);
		writer.writeTo(new File("wowhead-zones.csv"));
	}

	@Test
	public void getWoWHeadMusicFileInfos() throws IOException {
		WoWHeadInfoCollator wowHeadInfoCollator = new WoWHeadInfoCollator();
		List<WoWHeadMusicFileInfo> wowHeadMusicFileInfos = wowHeadInfoCollator.getWoWHeadMusicFileInfos();

		CsvFileWriter<WoWHeadMusicFileInfo> writer = new CsvFileWriter<WoWHeadMusicFileInfo>();
		writer.setObjects(wowHeadMusicFileInfos);
		writer.writeTo(new File("wowhead-music-file-infos.csv"));
	}

}
