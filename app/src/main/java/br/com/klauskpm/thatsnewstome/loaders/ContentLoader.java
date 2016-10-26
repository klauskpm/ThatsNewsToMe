package br.com.klauskpm.thatsnewstome.loaders;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.ArrayList;

import br.com.klauskpm.thatsnewstome.News;
import br.com.klauskpm.thatsnewstome.api.TheGuardianAPI;

/**
 * Created by klaus on 26/10/16.
 */

public class ContentLoader extends AsyncTaskLoader<ArrayList<News>> {
    private String mQuery;

    public ContentLoader(Context context, String query) {
        super(context);

        mQuery = query;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public ArrayList<News> loadInBackground() {
        return new TheGuardianAPI().getContent(mQuery);
    }
}
