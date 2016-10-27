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
    private Context mContext;
    private String mQuery;

    /**
     * Instantiates a new Content loader.
     *
     * @param context the context
     * @param query   the query
     */
    public ContentLoader(Context context, String query) {
        super(context);

        mContext = context;
        mQuery = query;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public ArrayList<News> loadInBackground() {
        return new TheGuardianAPI(mContext).getContent(mQuery);
    }
}
