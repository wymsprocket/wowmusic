package org.wymsprocket.wowmusic.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.wymsprocket.wowmusic.model.MusicFileInfo;

public class CsvFileWriter {

    private List<MusicFileInfo> musicFileInfos;

    public CsvFileWriter(List<MusicFileInfo> musicFileInfos) {
        this.musicFileInfos = musicFileInfos;
    }

    public List<MusicFileInfo> getMusicFileInfos() {
        return musicFileInfos;
    }

    public void setMusicFileInfos(List<MusicFileInfo> musicFileInfos) {
        this.musicFileInfos = musicFileInfos;
    }

    public void writeTo(File csvFile) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(csvFile));
        for(MusicFileInfo musicFileInfo : this.musicFileInfos) {
            writer.write(musicFileInfo + "\n");
        }
        writer.flush();
        writer.close();
    }

}
