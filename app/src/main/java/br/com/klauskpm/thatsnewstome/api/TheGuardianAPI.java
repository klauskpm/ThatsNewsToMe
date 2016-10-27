package br.com.klauskpm.thatsnewstome.api;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import br.com.klauskpm.thatsnewstome.News;
import br.com.klauskpm.thatsnewstome.R;

/**
 * Created by klaus on 26/10/16.
 */

public class TheGuardianAPI extends BaseAPI {
    private final String API_KEY_PARAM = "api-key";
    private final String API_KEY = "test";

    private ContentSubAPI mContent;

    private Context mContext;

    public TheGuardianAPI(Context context) {
        super("http://content.guardianapis.com/");

        mContext = context;
        mContent = new ContentSubAPI();
    }

    public ArrayList<News> getContent(String query) {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(mContext);

        String pageSize = sharedPreferences.getString(
                mContext.getString(R.string.pref_page_size_key),
                mContext.getString(R.string.pref_page_size_default)
        );

        String orderBy = sharedPreferences.getString(
                mContext.getString(R.string.pref_order_by_key),
                mContext.getString(R.string.pref_order_by_default)
        );

        return mContent.createRequest(query)
                .setOrderBy(orderBy)
                .setPageSize(pageSize)
                .get();
    }

    private class TheGuardianSubAPI extends SubBaseAPI {
        TheGuardianSubAPI(String mPath) {
            super(mPath);
        }

        @Override
        Uri.Builder createUriBuilder() {
            return super.createUriBuilder().appendQueryParameter(API_KEY_PARAM, API_KEY);
        }
    }

    private class ContentSubAPI extends TheGuardianSubAPI {
        private final static String ORDER_BY_PARAM = "order-by";
        private final static String PAGE_SIZE_PARAM = "page-size";

        ContentSubAPI() {
            super("search");
        }

        ContentSubAPI createRequest(String query) {
            createUriBuilder().appendQueryParameter("q", query);
            return this;
        }

        private ContentSubAPI setPageSize(String pageSize) {
            int pageSizeValue = Integer.parseInt(pageSize);
            if (pageSizeValue < 1)
                pageSizeValue = 1;
            else if (pageSizeValue > 50)
                pageSizeValue = 50;
            pageSize = Integer.toString(pageSizeValue);

            mCurrentBuilder.appendQueryParameter(PAGE_SIZE_PARAM, pageSize);
            return this;
        }

        private ContentSubAPI setOrderBy(String orderBy) {
            mCurrentBuilder.appendQueryParameter(ORDER_BY_PARAM, orderBy);
            return this;
        }

        ArrayList<News> get() {
            String JSONString = execute();
            return extract(JSONString);
        }

        private ArrayList<News> extract(String JSONString) {
            ArrayList<News> response = new ArrayList<News>();

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
    }
}
