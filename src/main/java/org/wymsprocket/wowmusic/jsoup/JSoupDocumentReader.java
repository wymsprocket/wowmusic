package org.wymsprocket.wowmusic.jsoup;

import java.io.IOException;

import org.jsoup.nodes.Document;

public interface JSoupDocumentReader {

	public abstract Document read() throws IOException;

}
