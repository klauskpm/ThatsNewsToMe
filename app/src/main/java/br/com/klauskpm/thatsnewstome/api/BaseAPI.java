package br.com.klauskpm.thatsnewstome.api;

import android.net.Uri;

import br.com.klauskpm.thatsnewstome.utils.QueryUtils;

/**
 * Created by klaus on 26/10/16.
 */
abstract class BaseAPI {
    private String mBaseUrl;

    /**
     * Instantiates a new Base api.
     *
     * @param mBaseUrl the m base url
     */
    BaseAPI(String mBaseUrl) {
        this.mBaseUrl = mBaseUrl;
    }

    /**
     * The type Sub base api.
     */
    abstract class SubBaseAPI {
        private Uri.Builder mPathBuilder;

        /**
         * The M current builder.
         */
        protected Uri.Builder mCurrentBuilder = null;

        /**
         * Instantiates a new Sub base api.
         *
         * @param mPath the m path
         */
        SubBaseAPI(String mPath) {
            Uri uri = Uri.parse(mBaseUrl);
            this.mPathBuilder = uri.buildUpon();
            this.mPathBuilder.appendPath(mPath);
        }

        /**
         * Create uri builder uri . builder.
         *
         * @return the uri . builder
         */
        Uri.Builder createUriBuilder() {
            return mCurrentBuilder = mPathBuilder;
        }

        /**
         * Execute string.
         *
         * @return the string
         */
        String execute() {
            if (mCurrentBuilder == null)
                return null;

            String url = mCurrentBuilder.toString();

            clearCurrentBuilder();
            return QueryUtils.requestGet(url);
        }

        /**
         * Clear the current builder for future usages
         */
        private void clearCurrentBuilder() {
            mCurrentBuilder = null;
        }
    }
}
