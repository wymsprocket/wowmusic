package org.wymsprocket.wowmusic.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CsvFileWriter<T extends Object> {

	private List<T> objects;

	public List<T> getObjects() {
		return objects;
	}

	public void setObjects(List<T> objects) {
		this.objects = objects;
	}

	public void writeTo(File csvFile) throws IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter(csvFile));
		for (Object object : this.objects) {
			writer.write(object + "\n");
		}
		writer.flush();
		writer.close();
	}
}
