package br.com.klauskpm.thatsnewstome;

/**
 * Created by klaus on 26/10/16.
 */
public class News {
    private String mTitle;
    private String mSection;
    private String mURL;

    public News(String mTitle, String mSection, String mURL) {
        this.mTitle = mTitle;
        this.mSection = mSection;
        this.mURL = mURL;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getSection() {
        return mSection;
    }

    public String getURL() {
        return mURL;
    }
}
