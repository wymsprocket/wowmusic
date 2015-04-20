package org.wymsprocket.wowmusic.jsoup;

import org.jsoup.nodes.Document;

public abstract class JSoupDocumentParser<T extends Object> {

    private Document document;

    public JSoupDocumentParser(Document document) {
        this.document = document;
    }

    public Document getDocument() {
        return document;
    }

    public void setDocument(Document document) {
        this.document = document;
    }

    public abstract T parse();

}
