package br.com.klauskpm.thatsnewstome.api;

import android.net.Uri;

/**
 * Created by klaus on 26/10/16.
 */

public class TheGuardianAPI extends BaseAPI {
    private final String API_KEY = "test";

    private ContentSubAPI mContent;

    public TheGuardianAPI() {
        super("http://content.guardianapis.com/");

        mContent = new ContentSubAPI();
    }

    public String getContent(String query) {
        return mContent.get(query);
    }

    class TheGuardianSubAPI extends SubBaseAPI {
        TheGuardianSubAPI(String mPath) {
            super(mPath);
        }

        @Override
        Uri.Builder createUriBuilder() {
            return super.createUriBuilder().appendQueryParameter("api-key", API_KEY);
        }
    }

    private class ContentSubAPI extends TheGuardianSubAPI {
        ContentSubAPI() {
            super("search");
        }

        String get(String query) {
            createUriBuilder().appendQueryParameter("q", query);

            return execute();
        }
    }
}
