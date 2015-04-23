package org.wymsprocket.wowmusic.util;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.wymsprocket.wowmusic.model.MusicFileInfo;
import org.wymsprocket.wowmusic.model.WoWHeadMusicFileInfo;
import org.wymsprocket.wowmusic.model.WoWHeadZone;

import com.google.common.io.Files;

public class MusicFileCopier {

    private File sourceDirectory;
    private File destinationDirectory;
    private List<MusicFileInfo> musicFileInfos;
    private List<WoWHeadZone> wowHeadZones;
    private List<WoWHeadMusicFileInfo> wowHeadMusicFileInfos;

    public MusicFileCopier(File sourceDirectory, File destinationDirectory, File musicFileInfoCsvFile, File wowHeadZoneCsvFile, File wowHeadMusicFileInfoCsvFile) throws IOException {
        this.sourceDirectory = sourceDirectory;
        this.destinationDirectory = destinationDirectory;

        MusicFileInfoCsvFileReader musicFileInfoCsvFileReader = new MusicFileInfoCsvFileReader(musicFileInfoCsvFile);
        this.musicFileInfos = musicFileInfoCsvFileReader.read();

        WoWHeadZoneCsvFileReader wowHeadZoneCsvFileReader = new WoWHeadZoneCsvFileReader(wowHeadZoneCsvFile);
        this.wowHeadZones = wowHeadZoneCsvFileReader.read();

        WoWHeadMusicFileInfoCsvFileReader wowHeadMusicFileInfoCsvFileReader = new WoWHeadMusicFileInfoCsvFileReader(wowHeadMusicFileInfoCsvFile);
        this.wowHeadMusicFileInfos = wowHeadMusicFileInfoCsvFileReader.read();

    }

    public void copy() throws IOException {
        Map<Long, MusicFileInfo> musicFileInfoMap = new HashMap<Long, MusicFileInfo>();
        for(MusicFileInfo musicFileInfo : this.musicFileInfos) {
            musicFileInfoMap.put(musicFileInfo.getCrc(), musicFileInfo);
        }

        Map<Integer, WoWHeadZone> wowHeadZoneMap = new HashMap<Integer, WoWHeadZone>();
        for(WoWHeadZone wowHeadZone : this.wowHeadZones) {
            wowHeadZoneMap.put(wowHeadZone.getId(), wowHeadZone);
        }

        Map<Long, WoWHeadMusicFileInfo> notFoundWoWHeadMusicFileInfos = new HashMap<Long, WoWHeadMusicFileInfo>();

        for(WoWHeadMusicFileInfo wowHeadMusicFileInfo : this.wowHeadMusicFileInfos) {
            WoWHeadZone wowHeadZone = wowHeadZoneMap.get(wowHeadMusicFileInfo.getZoneId());
            MusicFileInfo musicFileInfo = musicFileInfoMap.get(wowHeadMusicFileInfo.getCrc());
            if(musicFileInfo != null) {
                File source = new File(this.sourceDirectory + File.separator + musicFileInfo.getFile());
                File destination = new File((this.destinationDirectory + File.separator +
                                             wowHeadZone.getExpansion() + File.separator +
                                             wowHeadZone.getContinent() + File.separator +
                                             wowHeadZone.getName() + File.separator).replace(": ", " - ") +
                                             musicFileInfo.getFile().getName());
                copy(source, destination);
            } else {
                notFoundWoWHeadMusicFileInfos.put(wowHeadMusicFileInfo.getCrc(), wowHeadMusicFileInfo);
            }
        }

        for(WoWHeadMusicFileInfo wowHeadMusicFileInfo : this.wowHeadMusicFileInfos) {
            musicFileInfoMap.remove(wowHeadMusicFileInfo.getCrc());
        }

        for(MusicFileInfo musicFileInfo : musicFileInfoMap.values()) {
            File source = new File(this.sourceDirectory + File.separator + musicFileInfo.getFile());
            File destination = new File(this.destinationDirectory + File.separator + "Unknown" + File.separator + musicFileInfo.getFile());
            copy(source, destination);
        }

        for(WoWHeadMusicFileInfo wowHeadMusicFileInfo : notFoundWoWHeadMusicFileInfos.values()) {
            System.out.println("Not found: " + wowHeadMusicFileInfo);
        }
    }

    private void copy(File source, File destination) throws IOException {
        if(!destination.getParentFile().exists()) {
            destination.getParentFile().mkdirs();
        }

        if(!destination.exists()) {
            System.out.println(source + "\n==> " + destination);
            Files.copy(source, destination);
        }
    }
}
