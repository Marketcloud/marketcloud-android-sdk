/*
    Copyright (c) 2015 Marketcloud
    http://www.marketcloud.it

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at
        https://www.apache.org/licenses/LICENSE-2.0
    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
 */

package com.marketcloud.marketcloud;

import android.os.AsyncTask;
import android.util.Log;

import com.loopj.android.http.SyncHttpClient;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.UnsupportedEncodingException;

import cz.msebera.android.httpclient.Header;

/**
 * AsyncGet class. <br />
 * <br />
 * Performs an asynchronous HTTP GET operation, creating a background thread that executes a blocking call,
 * resulting in an asynchronous call.
 */
public class AsyncGet extends AsyncTask<String, Void, JSONArray> {

    private JSONArray ja;
    private boolean keepAlive = true;

    /**
     * Processes an HTTP GET request in background.
     *
     * @param params the url, the headers and the parameters of the HTTP request
     * @return a JSONArray containing the response to the request
     */
    @Override
    protected JSONArray doInBackground(String... params) {

        //prepare the connection
        SyncHttpClient client = new SyncHttpClient();

        //get the connection parameters
        String publicKey = params[1];

        try {
            client.addHeader("Authorization", publicKey + ":" + params[2]); //token
        } catch (IndexOutOfBoundsException e) {
            client.addHeader("Authorization", publicKey);
        }

        ResponseHandler rh = new ResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                //parse the response
                try {
                    String response = new String(responseBody, "UTF-8");

                    if (response.charAt(0) != '[')
                        response = "[" + response + "]";

                    ja = new JSONArray(response);
                } catch (JSONException | UnsupportedEncodingException e) {
                    keepAlive = false;
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                keepAlive = false;
            }
        };

        Log.i("get", params[0]);

        //connect to the given url
        client.get(params[0], rh); //url

        //keep checking until the value is available or the break condition is met
        while (keepAlive) {
            if (ja != null)
                return ja;
        }

        return null;
    }
}