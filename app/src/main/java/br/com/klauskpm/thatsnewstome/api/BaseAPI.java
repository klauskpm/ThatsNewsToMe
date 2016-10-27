package br.com.klauskpm.thatsnewstome.api;

import android.net.Uri;

import br.com.klauskpm.thatsnewstome.utils.QueryUtils;

/**
 * Created by klaus on 26/10/16.
 */

abstract class BaseAPI {
    private String mBaseUrl;

    BaseAPI(String mBaseUrl) {
        this.mBaseUrl = mBaseUrl;
    }

    abstract class SubBaseAPI {
        private Uri.Builder mPathBuilder;

        protected Uri.Builder mCurrentBuilder = null;

        SubBaseAPI(String mPath) {
            Uri uri = Uri.parse(mBaseUrl);
            this.mPathBuilder = uri.buildUpon();
            this.mPathBuilder.appendPath(mPath);
        }

        Uri.Builder createUriBuilder() {
            return mCurrentBuilder = mPathBuilder;
        }

        String execute() {
            if (mCurrentBuilder == null)
                return null;

            String url = mCurrentBuilder.toString();

            clearCurrentBuilder();
            return QueryUtils.requestGet(url);
        }

        private void clearCurrentBuilder() {
            mCurrentBuilder = null;
        }
    }
}
