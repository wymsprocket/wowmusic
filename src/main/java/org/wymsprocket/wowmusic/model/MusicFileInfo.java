package org.wymsprocket.wowmusic.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.zip.CRC32;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MusicFileInfo implements Comparable<MusicFileInfo> {

	private File file;
	private long crc;
	private final static Logger logger = LoggerFactory.getLogger(MusicFileInfo.class);

	public MusicFileInfo(File file) {
		this(file, 0);

		CRC32 crc32 = new CRC32();
		FileInputStream fileInputStream = null;
		try {
			fileInputStream = new FileInputStream(file);
			int i = fileInputStream.read();
			while (i >= 0) {
				crc32.update(i);
				i = fileInputStream.read();
			}
			fileInputStream.close();
		} catch (IOException e) {
			logger.error("Could not caculate CRC32 for file " + file + ".", e);
		}
		this.crc = crc32.getValue();
	}

	public MusicFileInfo(File file, long crc) {
		if (file.isDirectory()) {
			throw new RuntimeException("MusicFileInfo cannot contain a directory.");
		}
		this.file = file;
		this.crc = crc;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public long getCrc() {
		return crc;
	}

	public void setCrc(long crc) {
		this.crc = crc;
	}

	@Override
	public int compareTo(MusicFileInfo musicFileInfo) {
		return this.file.getAbsolutePath().compareToIgnoreCase(musicFileInfo.getFile().getAbsolutePath());
	}

	@Override
	public String toString() {
		String fileName = this.file.getAbsolutePath();
		int index = fileName.toLowerCase().indexOf("sound/music/");
		if (index >= 0) {
			fileName = fileName.substring(index);
		}
		return String.format("%s,%d", fileName, this.crc);
	}
}
