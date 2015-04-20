package org.wymsprocket.wowmusic.util;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wymsprocket.wowmusic.model.MusicFileInfo;

import com.google.common.io.Files;

/**
 * Reads music files in the World of Warcraft data directory, calculates CRC32 for them and produces a list of it all.
 * The music files are assumed to be in a 'Sound/music' subdirectory in the data directory, ie: '<Path to World of Warcraft
 * installation>/Data/Sound/music'. This should only be needed in between expansions and maybe patches to update the list.
 * Normally we'll just use the generated list.
 */
public class DataDirectoryReader {

    private File dataDirectory;
    private List<MusicFileInfo> musicFileInfos = new ArrayList<MusicFileInfo>();
    private final static Logger logger = LoggerFactory.getLogger(MusicFileInfo.class);

    public DataDirectoryReader(File dataDirectory) {
        if(!dataDirectory.isDirectory()) {
            throw new RuntimeException("Data directory has to be a directory.");
        } else if(!dataDirectory.exists()) {
            throw new RuntimeException("Data directory does not exist.");
        }
        this.dataDirectory = dataDirectory;
    }

    public File getDataDirectory() {
        return dataDirectory;
    }

    public void setDataDirectory(File dataDirectory) {
        this.dataDirectory = dataDirectory;
    }

    public List<MusicFileInfo> getMusicFileInfos() {
        return musicFileInfos;
    }

    public void setMusicFileInfos(List<MusicFileInfo> musicFileInfos) {
        this.musicFileInfos = musicFileInfos;
    }

    public List<MusicFileInfo> read() {
        this.musicFileInfos.clear();
        for(File file : readDirectory(this.dataDirectory)) {
            logger.info("Reading file {}.", file);
            this.musicFileInfos.add(new MusicFileInfo(file));
        }
        Collections.sort(this.musicFileInfos);
        return musicFileInfos;
    }

    private List<File> readDirectory(File directory) {
        List<File> files = new ArrayList<File>();
        for(File file : directory.listFiles()) {
            if(file.isDirectory()) {
                files.addAll(readDirectory(file));
            } else if(Files.getFileExtension(file.getName()).equalsIgnoreCase("mp3")) {
                files.add(file);
            }
        }
        return files;
    }
}
