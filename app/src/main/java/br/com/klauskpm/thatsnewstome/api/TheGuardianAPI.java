package br.com.klauskpm.thatsnewstome.api;

import android.net.Uri;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import br.com.klauskpm.thatsnewstome.News;

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

    public ArrayList<News> getContent(String query) {
        ArrayList<News> response = new ArrayList<News>();
        String JSONString = mContent.get(query);

        if (JSONString == null)
            return null;

        try {
            JSONObject responseJSON = new JSONObject(JSONString).getJSONObject("response");
            JSONArray results = responseJSON.getJSONArray("results");

            for (int i = 0; i < results.length(); i++) {
                JSONObject result = results.getJSONObject(i);
                String title = result.getString("webTitle");
                String section = result.getString("sectionName");
                String url = result.getString("webUrl");

                response.add(new News(title, section, url));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return response;
    }

    private class TheGuardianSubAPI extends SubBaseAPI {
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
