package org.wymsprocket.wowmusic;

import java.io.IOException;

import org.jukito.JukitoModule;
import org.jukito.JukitoRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

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
//	    DataDirectoryReader reader = new DataDirectoryReader(new File("download"));
//	    List<MusicFileInfo> musicFileInfos = reader.read();
//
//	    CsvFileWriter<MusicFileInfo> writer = new CsvFileWriter<MusicFileInfo>();
//	    writer.setObjects(musicFileInfos);
//	    writer.writeTo(new File("foo.csv"));

//	    WoWHeadMusicDownloader downloader = new WoWHeadMusicDownloader(new File("src/test/resources/wowhead-zones.csv"), new File("src/test/resources/wowhead-music-file-infos.csv"));
//	    downloader.download();

//		WoWHeadInfoCollator wowHeadInfoCollator = new WoWHeadInfoCollator();
//		List<WoWHeadMusicFileInfo> wowHeadMusicFileInfos = wowHeadInfoCollator.getWoWHeadMusicFileInfos();
//		for(WoWHeadMusicFileInfo wowHeadMusicFileInfo : wowHeadMusicFileInfos) {
//		    System.out.println(wowHeadMusicFileInfo);
//		}
//
//		File csvFile = new File("wowhead-music-file-infos.csv");
//		CsvFileWriter<WoWHeadMusicFileInfo> writer = new CsvFileWriter<WoWHeadMusicFileInfo>();
//		writer.setObjects(wowHeadMusicFileInfos);
//		writer.writeTo(csvFile);


//		WoWHeadZoneCsvFileReader reader = new WoWHeadZoneCsvFileReader(new File("src/test/resources/wowhead-zones.csv"));
//		List<WoWHeadZone> wowHeadZones = reader.read();
//		for (WoWHeadZone wowHeadZone : wowHeadZones) {
//			System.out.println(wowHeadZone);
//		}

//      WoWHeadMusicFileInfoCsvFileReader reader = new WoWHeadMusicFileInfoCsvFileReader(new File("src/test/resources/wowhead-music-file-infos.csv"));
//      List<WoWHeadMusicFileInfo> wowHeadMusicFileInfos = reader.read();
//      for (WoWHeadMusicFileInfo wowHeadMusicFileInfo : wowHeadMusicFileInfos) {
//          System.out.println(wowHeadMusicFileInfo);
//      }

//        URL url = new URL("http://www.wowhead.com/zone=6138/dread-wastes");
//        JSoupURLReader reader = new JSoupURLReader(url);
//        Document document = reader.read();
//
//        WoWHeadZonePageParser parser = new WoWHeadZonePageParser(new WoWHeadZoneInfo(), document);
//        WoWHeadZone wowHeadZone = parser.parse();
//        System.out.println(wowHeadZone);
//        for(WoWHeadMusicFileInfo wowHeadMusicFileInfo : wowHeadZone.getWowHeadMusicFileInfos()) {
//            System.out.println(wowHeadMusicFileInfo);
//        }
	}
}
