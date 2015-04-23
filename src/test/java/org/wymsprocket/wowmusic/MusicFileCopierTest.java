package org.wymsprocket.wowmusic;

import java.io.File;
import java.io.IOException;

import org.jukito.JukitoModule;
import org.jukito.JukitoRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.wymsprocket.wowmusic.util.MusicFileCopier;

@RunWith(JukitoRunner.class)
public class MusicFileCopierTest {

    public static class Module extends JukitoModule {
        @Override
        protected void configureTest() {

        }
    }

    @Test
    public void copy() throws IOException {
        MusicFileCopier copier = new MusicFileCopier(new File("copy/src"),
                                                     new File("copy/dest"),
                                                     new File("src/test/resources/wow-music-files.csv"),
                                                     new File("src/test/resources/wowhead-zones.csv"),
                                                     new File("src/test/resources/wowhead-music-file-infos.csv"));
        copier.copy();
    }
}
