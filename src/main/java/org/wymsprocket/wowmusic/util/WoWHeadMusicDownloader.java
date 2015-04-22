package org.wymsprocket.wowmusic.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wymsprocket.wowmusic.model.MusicFileInfo;
import org.wymsprocket.wowmusic.model.WoWHeadMusicFileInfo;
import org.wymsprocket.wowmusic.model.WoWHeadZone;

public class WoWHeadMusicDownloader {

    private File wowHeadZoneCsvFile;
    private File wowHeadMusicFileInfoCsvFile;
    private final static Logger logger = LoggerFactory.getLogger(WoWHeadMusicDownloader.class);

    public WoWHeadMusicDownloader(File wowHeadZoneCsvFile, File wowHeadMusicFileInfoCsvFile) {
        this.wowHeadZoneCsvFile = wowHeadZoneCsvFile;
        this.wowHeadMusicFileInfoCsvFile = wowHeadMusicFileInfoCsvFile;
    }

    public File getWowHeadZoneCsvFile() {
        return wowHeadZoneCsvFile;
    }

    public void setWowHeadZoneCsvFile(File wowHeadZoneCsvFile) {
        this.wowHeadZoneCsvFile = wowHeadZoneCsvFile;
    }

    public File getWowHeadMusicFileInfoCsvFile() {
        return wowHeadMusicFileInfoCsvFile;
    }

    public void setWowHeadMusicFileInfoCsvFile(File wowHeadMusicFileInfoCsvFile) {
        this.wowHeadMusicFileInfoCsvFile = wowHeadMusicFileInfoCsvFile;
    }

    public void download() throws IOException {
        WoWHeadZoneCsvFileReader wowHeadZoneCsvFileReader = new WoWHeadZoneCsvFileReader(getWowHeadZoneCsvFile());
        List<WoWHeadZone> wowHeadZones = wowHeadZoneCsvFileReader.read();

        WoWHeadMusicFileInfoCsvFileReader wowHeadMusicFileInfoCsvFileReader = new WoWHeadMusicFileInfoCsvFileReader(getWowHeadMusicFileInfoCsvFile());
        List<WoWHeadMusicFileInfo> wowHeadMusicFileInfos = wowHeadMusicFileInfoCsvFileReader.read();
        Map<Integer, WoWHeadZone> wowHeadZoneMap = new HashMap<Integer, WoWHeadZone>();

        for(WoWHeadZone wowHeadZone : wowHeadZones) {
            wowHeadZoneMap.put(wowHeadZone.getId(), wowHeadZone);
        }

        int count = 0;
        for(WoWHeadMusicFileInfo wowHeadMusicFileInfo : wowHeadMusicFileInfos) {
            WoWHeadZone wowHeadZone = wowHeadZoneMap.get(wowHeadMusicFileInfo.getZoneId());

            String fileName = String.format("download/%s/%s/%s/%s_%d.mp3",
                    wowHeadZone.getExpansion(),
                    wowHeadZone.getContinent(),
                    wowHeadZone.getName().replace(": ", " - "),
                    wowHeadMusicFileInfo.getTitle().replace(' ', '_'),
                    wowHeadMusicFileInfo.getId());

            File file = new File(fileName);
            if(!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }

            URL fileUrl = new URL("http:" + wowHeadMusicFileInfo.getUrl());

            ++count;
            if(!file.exists()) {
                logger.info("Downloading " + wowHeadMusicFileInfo.getTitle() + " (" + count + "/" + wowHeadMusicFileInfos.size() + ")");

                InputStream in = fileUrl.openStream();
                OutputStream out = new BufferedOutputStream(new FileOutputStream(file));
                for (int b; (b = in.read()) != -1;) {
                    out.write(b);
                }
                out.close();
                in.close();
            } else {
                logger.info(wowHeadMusicFileInfo.getTitle() + " already exists (" + count + "/" + wowHeadMusicFileInfos.size() + ")");
            }

            if(wowHeadMusicFileInfo.getCrc() <= 0) {
                // TODO: Calculate crc while downloading instead.
                MusicFileInfo musicFileInfo = new MusicFileInfo(file);
                wowHeadMusicFileInfo.setCrc(musicFileInfo.getCrc());
            }
        }

        CsvFileWriter<WoWHeadMusicFileInfo> writer = new CsvFileWriter<WoWHeadMusicFileInfo>();
        writer.setObjects(wowHeadMusicFileInfos);
        writer.writeTo(new File("wowhead-music-file-infos.csv"));
    }
}
