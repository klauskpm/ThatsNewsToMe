package br.com.klauskpm.thatsnewstome.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;

/**
 * Created by klaus on 26/10/16.
 */
public final class QueryUtils {
    private static final int CONNECT_TIMEOUT = 15000;
    private static final int READ_TIMEOUT = 10000;
    private static final String CHARSET = "UTF-8";

    private static final String GET = "GET";

    private QueryUtils() {}

    /**
     * Request get string.
     *
     * @param stringUrl the string url
     * @return the string
     */
    public static String requestGet(String stringUrl) {
        return request(stringUrl, GET);
    }

    /**
     * Do a HTTP request
     *
     * @param stringUrl to be requested
     * @param method for the request (e.g. GET, POST)
     * @return the result of the request as a string
     */
    private static String request(String stringUrl, String method) {
        URL url = createUrl(stringUrl);
        String response = null;

        try {
            response = makeHttpRequest(url, method);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return response;
    }

    /**
     * Creates the URL from a string
     *
     * @param stringUrl for the request
     * @return returns a {@link URL}
     */
    private static URL createUrl(String stringUrl) {
        URL url = null;

        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;
    }

    /**
     * Make the HTTP request
     *
     * @param url to be requested
     * @param method of the request
     * @return the request response as a {@link String}
     * @throws IOException
     */
    private static String makeHttpRequest(URL url, String method) throws IOException {
        if (url == null)
            return null;

        String response = null;
        HttpURLConnection httpURLConnection = null;
        InputStream inputStream = null;

        try {
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setConnectTimeout(CONNECT_TIMEOUT);
            httpURLConnection.setReadTimeout(READ_TIMEOUT);
            httpURLConnection.setRequestMethod(method);
            httpURLConnection.connect();

            if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                inputStream = httpURLConnection.getInputStream();
                response = readFromStream(inputStream);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (httpURLConnection != null)
                httpURLConnection.disconnect();

            if (inputStream != null)
                inputStream.close();
        }

        return response;
    }

    /**
     * Read from the input stream
     *
     * @param inputStream generated from the request
     * @return the stream response as a {@link String}
     * @throws IOException
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader streamReader = new InputStreamReader(inputStream,
                    Charset.forName(CHARSET));
            BufferedReader bufferedReader = new BufferedReader(streamReader);
            String line = bufferedReader.readLine();
            while (line != null) {
                output.append(line);
                line = bufferedReader.readLine();
            }
        }

        return output.toString();
    }


}
