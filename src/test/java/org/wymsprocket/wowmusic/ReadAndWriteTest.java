package org.wymsprocket.wowmusic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.jukito.All;
import org.jukito.JukitoModule;
import org.wymsprocket.wowmusic.model.MusicFileInfo;
import org.wymsprocket.wowmusic.util.CsvFileReader;

//@RunWith(JukitoRunner.class)
public class ReadAndWriteTest {

    public static class Module extends JukitoModule {
        @Override
        protected void configureTest() {
            bindManyNamedInstances(File.class, "dataFile", new File("src/test/resources/data.csv"));
        }
    }

    //@Test
    public void readDataFile(@All("dataFile") File dataFile) throws IOException {
        CsvFileReader reader = new CsvFileReader(dataFile);
        List<MusicFileInfo> musicFileInfos = reader.read();

        assertNotNull(musicFileInfos);
        assertFalse(musicFileInfos.isEmpty());

        for(MusicFileInfo musicFileInfo : musicFileInfos) {
            assertEquals("Sound/music/cataclysm/MUS_41_faeriedragon_UE01.mp3", musicFileInfo.getFile().toString());
            assertEquals(703478863,musicFileInfo.getCrc());
            break;
        }
    }
}
