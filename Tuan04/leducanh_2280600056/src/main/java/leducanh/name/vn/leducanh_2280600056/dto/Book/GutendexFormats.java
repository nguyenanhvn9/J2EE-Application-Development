package leducanh.name.vn.leducanh_2280600056.dto.Book;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GutendexFormats {
    @JsonProperty("application/epub+zip")
    private String epubZip;

    @JsonProperty("application/x-mobipocket-ebook")
    private String mobi;

    @JsonProperty("text/html; charset=utf-8")
    private String html;

    @JsonProperty("text/plain; charset=utf-8")
    private String plainText;

    @JsonProperty("text/plain; charset=us-ascii")
    private String plainTextAscii;

    @JsonProperty("application/rdf+xml")
    private String rdfXml;

    @JsonProperty("image/jpeg")
    private String jpeg;

    // Constructors
    public GutendexFormats() {
    }

    public GutendexFormats(String epubZip, String mobi, String html, String plainText,
            String plainTextAscii, String rdfXml, String jpeg) {
        this.epubZip = epubZip;
        this.mobi = mobi;
        this.html = html;
        this.plainText = plainText;
        this.plainTextAscii = plainTextAscii;
        this.rdfXml = rdfXml;
        this.jpeg = jpeg;
    }

    // Getters and Setters
    public String getEpubZip() {
        return epubZip;
    }

    public void setEpubZip(String epubZip) {
        this.epubZip = epubZip;
    }

    public String getMobi() {
        return mobi;
    }

    public void setMobi(String mobi) {
        this.mobi = mobi;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public String getPlainText() {
        return plainText;
    }

    public void setPlainText(String plainText) {
        this.plainText = plainText;
    }

    public String getPlainTextAscii() {
        return plainTextAscii;
    }

    public void setPlainTextAscii(String plainTextAscii) {
        this.plainTextAscii = plainTextAscii;
    }

    public String getRdfXml() {
        return rdfXml;
    }

    public void setRdfXml(String rdfXml) {
        this.rdfXml = rdfXml;
    }

    public String getJpeg() {
        return jpeg;
    }

    public void setJpeg(String jpeg) {
        this.jpeg = jpeg;
    }

    @Override
    public String toString() {
        return "GutendexFormats{" +
                "epubZip='" + epubZip + '\'' +
                ", mobi='" + mobi + '\'' +
                ", html='" + html + '\'' +
                ", plainText='" + plainText + '\'' +
                ", plainTextAscii='" + plainTextAscii + '\'' +
                ", rdfXml='" + rdfXml + '\'' +
                ", jpeg='" + jpeg + '\'' +
                '}';
    }
}