package org.wymsprocket.wowmusic.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.wymsprocket.wowmusic.model.MusicFileInfo;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

public class MusicFileInfoCsvFileReader {

    private File csvFile;
    private List<MusicFileInfo> musicFileInfos = new ArrayList<MusicFileInfo>();

    public MusicFileInfoCsvFileReader(File csvFile) {
        if(csvFile.isDirectory()) {
            throw new RuntimeException("Csv file can not be a directory.");
        } else if(!csvFile.exists()) {
            throw new RuntimeException("Csv file does not exist.");
        }
        this.csvFile = csvFile;
    }

    public File getCsvFile() {
        return csvFile;
    }

    public void setCsvFile(File csvFile) {
        this.csvFile = csvFile;
    }

    public List<MusicFileInfo> getMusicFileInfos() {
        return musicFileInfos;
    }

    public void setMusicFileInfos(List<MusicFileInfo> musicFileInfos) {
        this.musicFileInfos = musicFileInfos;
    }

    public List<MusicFileInfo> read() throws IOException {
        musicFileInfos.clear();
        BufferedReader reader = new BufferedReader(new FileReader(this.csvFile));
        String line = reader.readLine();
        while(line != null) {
            String[] splitLine = line.split(",");
            musicFileInfos.add(new MusicFileInfo(new File(splitLine[0]), Long.parseLong(splitLine[1])));
            line = reader.readLine();
        }
        reader.close();
        Collections.sort(musicFileInfos);
        return musicFileInfos;
    }

    public List<MusicFileInfo> getDuplicates() {
        List<MusicFileInfo> duplicates = new ArrayList<MusicFileInfo>();
        Multimap<Long, MusicFileInfo> multiMap = HashMultimap.create();

        for(MusicFileInfo musicFileInfo : this.musicFileInfos) {
            multiMap.put(musicFileInfo.getCrc(), musicFileInfo);
        }

        for(long key : multiMap.keySet()) {
            if(multiMap.get(key).size() > 1) {
                duplicates.addAll(multiMap.get(key));
            }
        }
        return duplicates;
    }
}
