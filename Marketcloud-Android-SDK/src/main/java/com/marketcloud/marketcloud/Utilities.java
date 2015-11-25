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

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * Utilities class. <br />
 * <br />
 * This class provides a set of easy-to-use common methods, s.a. getById, delete, etc..<br />
 */
public class Utilities {

    private String publicKey = "";
    private Context context;

    /**
     * Constructor.
     *
     * @param key the public key to access the APIs
     */
    public Utilities(Context ct, String key) {
        context = ct;
        publicKey = key;
    }

    /**
     * Returns the data about the object with the given ID.
     *
     * @param baseURL endpoint of the database
     * @param id the id of the object that the user wants to retrieve
     * @return a JSONObject containing the data about the given object, or null if the ID does not belong to any object
     */
    public JSONObject getById(final String baseURL, final String id) {

        try {
            return (JSONObject) new AsyncConnect(context)
                    .execute(
                            new String[]{
                                    "get",
                                    baseURL + id,
                                    publicKey})
                    .get()
                    .get(0);
        } catch (InterruptedException | ExecutionException | JSONException | NullPointerException e) {
            return null;
        }
    }

    /**
     * Returns the data about the object with the given ID.
     *
     * @param baseURL endpoint of the database
     * @param id the id of the object that the user wants to retrieve
     * @return a JSONObject containing the data about the given object, or null if the ID does not belong to any object
     */
    public JSONObject getById(final String baseURL, final int id) {

        try {
            return (JSONObject) new AsyncConnect(context)
                    .execute(
                            new String[]{
                                    "get",
                                    baseURL + id,
                                    publicKey})
                    .get()
                    .get(0);
        } catch (InterruptedException | ExecutionException | JSONException | NullPointerException e) {
            return null;
        }
    }

    /**
     * Returns the data about the object with the given ID.
     *
     * @param baseURL endpoint of the database
     * @param id the id of the object that the user wants to retrieve
     * @param token the session token that grants that the user is logged in
     * @return a JSONObject containing the data about the given object, or null if the ID does not belong to any object
     */
    public JSONObject getById(final String baseURL, final String id, final String token) {

        try {
            return (JSONObject) new AsyncConnect(context)
                    .execute(
                            new String[]{
                                    "get",
                                    baseURL + id,
                                    publicKey + ":" + token})
                    .get()
                    .get(0);
        } catch (InterruptedException | ExecutionException | JSONException | NullPointerException e) {
            return null;
        }
    }

    /**
     * Returns the data about the object with the given ID.
     *
     * @param baseURL endpoint of the database
     * @param id the id of the object that the user wants to retrieve
     * @param token the session token that grants that the user is logged in
     * @return a JSONObject containing the data about the given object, or null if the ID does not belong to any object
     */
    public JSONObject getById(final String baseURL, final int id, final String token) {

        try {
            return (JSONObject) new AsyncConnect(context)
                    .execute(
                            new String[]{
                                    "get",
                                    baseURL + id,
                                    publicKey + ":" + token})
                    .get()
                    .get(0);
        } catch (InterruptedException | ExecutionException | JSONException | NullPointerException e) {
            return null;
        }
    }

    /**
     * Returns a list of objects that comply with the given query.
     *
     * @param baseURL endpoint of the database
     * @param m HashMap containing a list of filters
     * @return a JSONArray containing a list of objects that comply with the given filter
     */
    public JSONArray list(final String baseURL, final HashMap<String, Object> m) {

        String url = baseURL;

        //Concatenate the filters to the base URL

        int index = 0;
        int max = m.size() - 1;

        for (Map.Entry<String, Object> entry : m.entrySet()) {

            url += entry.getKey() + "=" + entry.getValue();

            if (index != max) url += "&";

            index++;
        }

        try {
            return new AsyncConnect(context)
                    .execute(
                            new String[]{
                                    "get",
                                    url,
                                    publicKey})
                    .get();
        } catch (InterruptedException | ExecutionException e) {
            return null;
        }
    }

    /**
     * Returns a list of objects that comply with the given query.
     *
     * @param baseURL endpoint of the database
     * @param token a session token that identifies the user
     * @param m HashMap containing a list of filters
     * @return a JSONArray containing a list of objects that comply with the given filter
     */
    public JSONArray list(final String baseURL, String token, final HashMap<String, Object> m) {

        String url = baseURL.substring(0, baseURL.length() - 1) + "?";

        //Concatenate the filters to the base URL

        int index = 0;
        int max = m.size() - 1;

        for (Map.Entry<String, Object> entry : m.entrySet()) {

            url += entry.getKey() + "=" + entry.getValue();

            if (index != max) url += "&";

            index++;
        }

        try {
            return new AsyncConnect(context)
                    .execute(
                            new String[]{
                                    "get",
                                    url,
                                    publicKey + ":" + token})
                    .get();
        } catch (InterruptedException | ExecutionException e) {
            return null;
        }
    }

    /**
     * Returns a list containing the data of all the instances of an object (cart, user, etc.) registered to the service.
     *
     * @param url endpoint of the database
     * @param token a session token that identifies the user
     * @return a list with the data of all the users
     */
    public JSONArray getInstanceList(String url, String token) {
        try {
            return new AsyncConnect(context)
                    .execute(
                            new String[] {
                                    "get",
                                    url,
                                    publicKey + ":" + token})
                    .get();
        } catch (InterruptedException | ExecutionException e) {
            return null;
        }
    }

    /**
     * Deletes an instance from the service.
     *
     * @param url endpoint of the database
     * @param id instance id
     * @param token a session token that identifies the user
     * @return if the request was correct, it returns a status true. note: this happens even if the instance was already deleted
     */
    public JSONObject delete(String url, String id, String token) {
        try {
            return (JSONObject) new AsyncConnect(context)
                    .execute(
                            new String[] {
                                    "delete",
                                    url,
                                    publicKey + ":" + token,
                                    id})
                    .get()
                    .get(0);
        } catch (InterruptedException | ExecutionException | JSONException | NullPointerException e) {
            return null;
        }
    }
}