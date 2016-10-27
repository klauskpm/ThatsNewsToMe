package br.com.klauskpm.thatsnewstome;

/**
 * Created by klaus on 26/10/16.
 */
public class News {
    private String mTitle;
    private String mSection;
    private String mURL;

    /**
     * Instantiates a new News.
     *
     * @param mTitle   the m title
     * @param mSection the m section
     * @param mURL     the m url
     */
    public News(String mTitle, String mSection, String mURL) {
        this.mTitle = mTitle;
        this.mSection = mSection;
        this.mURL = mURL;
    }

    /**
     * Gets title.
     *
     * @return the title
     */
    public String getTitle() {
        return mTitle;
    }

    /**
     * Gets section.
     *
     * @return the section
     */
    public String getSection() {
        return mSection;
    }

    /**
     * Gets url.
     *
     * @return the url
     */
    public String getURL() {
        return mURL;
    }
}
