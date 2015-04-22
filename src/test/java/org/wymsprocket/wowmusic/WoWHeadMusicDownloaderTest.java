package org.wymsprocket.wowmusic;

import java.io.File;
import java.io.IOException;

import org.jukito.JukitoModule;
import org.jukito.JukitoRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.wymsprocket.wowmusic.util.WoWHeadMusicDownloader;

@RunWith(JukitoRunner.class)
public class WoWHeadMusicDownloaderTest {

    public static class Module extends JukitoModule {
        @Override
        protected void configureTest() {

        }
    }

    @Test
    public void download() throws IOException {
        WoWHeadMusicDownloader downloader = new WoWHeadMusicDownloader(new File("src/test/resources/wowhead-zones.csv"), new File("src/test/resources/wowhead-music-file-infos.csv"));
        downloader.download();
    }

}
