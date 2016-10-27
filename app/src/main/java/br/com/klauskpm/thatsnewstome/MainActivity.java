package br.com.klauskpm.thatsnewstome;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.ArrayList;

import br.com.klauskpm.thatsnewstome.adapters.NewsAdapter;
import br.com.klauskpm.thatsnewstome.loaders.ContentLoader;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<News>> {
    private NewsAdapter mAdapter;
    private SearchView mNewsSearch;
    private LoaderManager mLoaderManager;
    private Loader mLoader;
    private ConnectivityManager mConnectManager;
    private String mQuery;
    private int LOADER_NEWS_ID = 0;

    private SearchView.OnQueryTextListener mNewsOnQueryTextListener = new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String query) {
            mAdapter.clear();
            mQuery = query;
            initLoader();
            mNewsSearch.clearFocus();
            return true;
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mConnectManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        mNewsSearch = (SearchView) findViewById(R.id.news_search);
        mNewsSearch.setOnQueryTextListener(mNewsOnQueryTextListener);

        mAdapter = new NewsAdapter(this, new ArrayList<News>());

        ListView list = (ListView) findViewById(R.id.list);
        list.setAdapter(mAdapter);
    }

    private boolean isConnected() {
        NetworkInfo networkInfo = mConnectManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    private void initLoader() {
        if (mLoaderManager == null)
            mLoaderManager = getLoaderManager();

        if (!isConnected()) {

            return;
        }

        if (mLoader == null)
            mLoader = mLoaderManager.initLoader(LOADER_NEWS_ID, null, this);
        else
            mLoaderManager.restartLoader(LOADER_NEWS_ID, null, this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<ArrayList<News>> onCreateLoader(int id, Bundle args) {
        return new ContentLoader(this, mQuery);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<News>> loader, ArrayList<News> data) {
        mAdapter.clear();

        if (data != null && !data.isEmpty())
            mAdapter.addAll(data);
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<News>> loader) {
        mAdapter.clear();
    }
}
