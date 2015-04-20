package org.wymsprocket.wowmusic.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.wymsprocket.wowmusic.model.WoWHeadZone;
import org.wymsprocket.wowmusic.model.WoWHeadZone.Type;

public class WoWHeadZoneCsvFileReader {

	private File csvFile;
	private List<WoWHeadZone> wowHeadZones = new ArrayList<WoWHeadZone>();

	public WoWHeadZoneCsvFileReader(File csvFile) {
		if (csvFile.isDirectory()) {
			throw new RuntimeException("Csv file can not be a directory.");
		} else if (!csvFile.exists()) {
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

	public List<WoWHeadZone> getWoWHeadZones() {
		return wowHeadZones;
	}

	public void setWoWHeadZones(List<WoWHeadZone> wowHeadZones) {
		this.wowHeadZones = wowHeadZones;
	}

	public List<WoWHeadZone> read() throws IOException {
		wowHeadZones.clear();
		BufferedReader reader = new BufferedReader(new FileReader(this.csvFile));
		String line = reader.readLine();
		while (line != null) {
			String[] splitLine = line.split(",");
			WoWHeadZone wowHeadZone = new WoWHeadZone();
			wowHeadZone.setId(Integer.parseInt(splitLine[0]));
			wowHeadZone.setName(splitLine[1]);
			wowHeadZone.setExpansion(splitLine[2]);
			wowHeadZone.setContinent(splitLine[3]);
			wowHeadZone.setLocation(Integer.parseInt(splitLine[4]));
			wowHeadZone.setType(Type.getType(splitLine[5]));
			wowHeadZones.add(wowHeadZone);

			line = reader.readLine();
		}
		reader.close();
		// Collections.sort(wowHeadZones);
		return wowHeadZones;
	}

	// public List<MusicFileInfo> getDuplicates() {
	// List<MusicFileInfo> duplicates = new ArrayList<MusicFileInfo>();
	// Multimap<Long, MusicFileInfo> multiMap = HashMultimap.create();
	//
	// for(MusicFileInfo musicFileInfo : this.wowHeadZones) {
	// multiMap.put(musicFileInfo.getCrc(), musicFileInfo);
	// }
	//
	// for(long key : multiMap.keySet()) {
	// if(multiMap.get(key).size() > 1) {
	// duplicates.addAll(multiMap.get(key));
	// }
	// }
	// return duplicates;
	// }
}
