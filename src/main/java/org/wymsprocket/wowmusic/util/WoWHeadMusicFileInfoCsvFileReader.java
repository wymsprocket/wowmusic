package org.wymsprocket.wowmusic.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.wymsprocket.wowmusic.model.WoWHeadMusicFileInfo;

public class WoWHeadMusicFileInfoCsvFileReader {

    private File csvFile;
    private List<WoWHeadMusicFileInfo> wowHeadMusicFileInfos = new ArrayList<WoWHeadMusicFileInfo>();

    public WoWHeadMusicFileInfoCsvFileReader(File csvFile) {
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

    public List<WoWHeadMusicFileInfo> getWoWHeadMusicFileInfos() {
        return wowHeadMusicFileInfos;
    }

    public void setMusicFileInfos(List<WoWHeadMusicFileInfo> wowHeadMusicFileInfos) {
        this.wowHeadMusicFileInfos = wowHeadMusicFileInfos;
    }

    public List<WoWHeadMusicFileInfo> read() throws IOException {
        wowHeadMusicFileInfos.clear();
        BufferedReader reader = new BufferedReader(new FileReader(this.csvFile));
        String line = reader.readLine();
        while(line != null) {
            String[] splitLine = line.split(",");
            WoWHeadMusicFileInfo wowHeadMusicFileInfo = new WoWHeadMusicFileInfo();
            wowHeadMusicFileInfo.setId(Integer.parseInt(splitLine[0]));
            wowHeadMusicFileInfo.setTitle(splitLine[1]);
            wowHeadMusicFileInfo.setUrl(splitLine[2]);
            wowHeadMusicFileInfo.setType(splitLine[3]);
            wowHeadMusicFileInfo.setZoneId(Integer.parseInt(splitLine[4]));
            wowHeadMusicFileInfo.setCrc(Long.parseLong(splitLine[5]));

            wowHeadMusicFileInfos.add(wowHeadMusicFileInfo);
            line = reader.readLine();
        }
        reader.close();
        Collections.sort(wowHeadMusicFileInfos);
        return wowHeadMusicFileInfos;
    }

}
