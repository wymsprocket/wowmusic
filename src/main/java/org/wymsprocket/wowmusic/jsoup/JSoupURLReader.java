package org.wymsprocket.wowmusic.jsoup;

import java.io.IOException;
import java.net.URL;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JSoupURLReader implements JSoupDocumentReader {

	private URL url;
	private final static Logger logger = LoggerFactory.getLogger(JSoupURLReader.class);

	public JSoupURLReader(URL url) {
		this.url = url;
	}

	public URL getUrl() {
		return url;
	}

	public void setUrl(URL url) {
		this.url = url;
	}

	@Override
	public Document read() throws IOException {
	    logger.info("Fetching document from {}...", this.url);
		Document document = Jsoup.connect(this.url.toString())
				// .data("query", "Java")
				.userAgent("Chrome")
				.timeout(10000)
				.get();
		return document;
	}
}
