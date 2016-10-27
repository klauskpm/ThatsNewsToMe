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
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.klauskpm.thatsnewstome.adapters.NewsAdapter;
import br.com.klauskpm.thatsnewstome.loaders.ContentLoader;

import static android.view.View.GONE;

/**
 * The type Main activity.
 */
public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<News>> {
    private int LOADER_NEWS_ID = 0;

    private NewsAdapter mAdapter;

    private SearchView mNewsSearch;
    private TextView mWarningMessage;
    private ProgressBar mSpinnerLoader;

    private LoaderManager mLoaderManager;
    private Loader mLoader;
    private ConnectivityManager mConnectManager;

    private String mQuery;


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

        mWarningMessage = (TextView) findViewById(R.id.warning_message);
        mSpinnerLoader = (ProgressBar) findViewById(R.id.spinner_loader);

        mAdapter = new NewsAdapter(this, new ArrayList<News>());

        ListView list = (ListView) findViewById(R.id.list);
        list.setAdapter(mAdapter);
    }

    private boolean isConnected() {
        NetworkInfo networkInfo = mConnectManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    private void noConnection() {
        mWarningMessage.setText(R.string.no_connection);
        mWarningMessage.setVisibility(View.VISIBLE);
    }

    private void initLoader() {
        mWarningMessage.setVisibility(GONE);

        if (mLoaderManager == null)
            mLoaderManager = getLoaderManager();

        if (!isConnected()) {
            noConnection();
            return;
        }

        mSpinnerLoader.setVisibility(View.VISIBLE);

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
        mSpinnerLoader.setVisibility(View.GONE);

        if (!isConnected()) {
            noConnection();
            return;
        }

        if (data != null && !data.isEmpty()) {
            mWarningMessage.setVisibility(View.GONE);
            mAdapter.addAll(data);
        } else {
            mWarningMessage.setVisibility(View.VISIBLE);
            mWarningMessage.setText(getString(R.string.no_result, mQuery));
        }
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<News>> loader) {
        mAdapter.clear();
    }
}
