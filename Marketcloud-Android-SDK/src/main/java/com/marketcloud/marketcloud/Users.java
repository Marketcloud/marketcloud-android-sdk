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

import java.util.concurrent.ExecutionException;

/**
 * Users class. <br />
 * <br />
 * Creates a new user instance.
 */
public class Users {

    private String publicKey;
    private Context context;
    private Utilities api;
    private TokenManager tm;

    /**
     * Constructor.
     *
     * @param key the public key to access the APIs
     */
    public Users(String key, TokenManager tokenManager, Context ct) {
        publicKey = key;
        api = new Utilities(context, key);
        context = ct;
        tm = tokenManager;
    }

    /**
     * Authentication: allows the user to login.
     *
     * @param email    user's email
     * @param password user's password
     * @return true if the login is successful, false if not
     */
    @SuppressWarnings("unused")
    public boolean authenticate(String email, String password) throws NullPointerException, ExecutionException, InterruptedException, JSONException {
        JSONObject jsonObject = toJsonObject(
                email,
                password);

        if (jsonObject != null) {
            jsonObject = (JSONObject) new AsyncConnect(context)
                    .execute(
                            new String[]{
                                    "post",
                                    "http://api.marketcloud.it/v0/users/authenticate",
                                    publicKey,
                                    jsonObject.toString()})
                    .get()
                    .get(0);

            if (!(boolean) jsonObject.get("status"))
                return false;
            else {

                tm.setToken("auth", jsonObject.getJSONObject("data").getString("token"));

                return true;
            }
        }

        return false;
    }

    /**
     * Logs out the current user.
     */
    @SuppressWarnings("unused")
    public void logout() {
        tm.deleteToken("auth");
    }

    /**
     * Register a new user.
     *
     * @param name     username
     * @param email    user's email
     * @param password user's password
     * @return the data of the newborn user
     */
    @SuppressWarnings("unused")
    public JSONObject create(String name, String email, String password) throws NullPointerException, ExecutionException, InterruptedException, JSONException {
        JSONObject jo = toJsonObject(
                name,
                email,
                password);

        if (jo != null)
            return (JSONObject) new AsyncConnect(context)
                    .execute(
                            new String[]{
                                    "post",
                                    "http://api.marketcloud.it/v0/users",
                                    publicKey,
                                    jo.toString()})
                    .get()
                    .get(0);

        return null;
    }

    /**
     * Register a new user.
     *
     * @param name     username
     * @param email    user's email
     * @param password user's password
     * @param imageURL url to the user's profile pic
     * @return the data of the newborn user
     */
    @SuppressWarnings("unused")
    public JSONObject create(String name, String email, String password, String imageURL) throws NullPointerException, ExecutionException, InterruptedException, JSONException {
        JSONObject jo = toJsonObject(
                name,
                email,
                password,
                imageURL);

        if (jo != null)
            return (JSONObject) new AsyncConnect(context)
                    .execute(
                            new String[]{
                                    "post",
                                    "http://api.marketcloud.it/v0/users",
                                    publicKey,
                                    jo.toString()})
                    .get()
                    .get(0);

        return null;
    }

    /**
     * Returns the data of a specific user.
     *
     * @param id the id of the desired user
     * @return the data of the desired user
     */
    @SuppressWarnings("unused")
    public JSONObject getById(int id) throws InterruptedException, ExecutionException, JSONException {
        if (tm.getSessionToken() != null)
            return api.getById("http://api.marketcloud.it/v0/users/", id, tm.getSessionToken());
        else return null;
    }

    /**
     * Returns a list with the data of all the users.
     *
     * @return list of users (and their data)
     */
    @SuppressWarnings("unused")
    public JSONObject get() throws ExecutionException, InterruptedException, JSONException {
        if (tm.getSessionToken() != null)
            return api.getInstanceList("http://api.marketcloud.it/v0/users", tm.getSessionToken());
        else return null;
    }

    /**
     * Updates the data of a user.
     *
     * @param id       the id of the user
     * @param name     the new username
     * @param email    the new email
     * @param password the new password
     * @return the data of the user after the update
     */
    @SuppressWarnings("unused")
    public JSONObject update(int id, String name, String email, String password) throws NullPointerException, ExecutionException, InterruptedException, JSONException {
        if (tm.getSessionToken() != null) {
            JSONObject jo = toJsonObject(
                    name,
                    email,
                    password);

            if (jo != null)
                return (JSONObject) new AsyncConnect(context)
                        .execute(
                                new String[]{
                                        "put",
                                        "http://api.marketcloud.it/v0/users/" + id,
                                        publicKey + ":" + tm.getSessionToken(),
                                        jo.toString()})
                        .get()
                        .get(0);
        }

        return null;
    }

    /**
     * Updates the data of a user.
     *
     * @param id       the id of the user
     * @param name     the new username
     * @param email    the new email
     * @param password the new password
     * @param imageURL the new user pic
     * @return the data of the user after the update
     */
    @SuppressWarnings("unused")
    public JSONObject update(int id, String name, String email, String password, String imageURL) throws NullPointerException, ExecutionException, InterruptedException, JSONException {
        if (tm.getSessionToken() != null) {
            JSONObject jo = toJsonObject(
                    name,
                    email,
                    password,
                    imageURL);

            if (jo != null)
                return (JSONObject) new AsyncConnect(context)
                        .execute(
                                new String[]{
                                        "put",
                                        "http://api.marketcloud.it/v0/users/" + id,
                                        publicKey + ":" + tm.getSessionToken(),
                                        jo.toString()})
                        .get()
                        .get(0);
        }

        return null;
    }

    /**
     * Deletes a user from the database.
     *
     * @param id the id of the user
     * @return true if succeeded, false if not
     */
    @SuppressWarnings("unused")
    public boolean delete(int id) throws InterruptedException, ExecutionException, JSONException {
        return (boolean) api.delete("http://api.marketcloud.it/v0/users/", id, tm.getSessionToken()).get("status");
    }

    /**
     * Creates a JSON object with the given parameters.
     *
     * @param name      username
     * @param email     user's email
     * @param password  user's password
     * @param image_url user's profile pic
     * @return a JSONObject with the given data
     */
    private JSONObject toJsonObject(String name, String email, String password, String image_url) throws JSONException {
        return new JSONObject().put("name", name).put("email", email).put("password", password).put("image_url", image_url);
    }

    /**
     * Creates a JSON object with the given parameters.
     *
     * @param name     username
     * @param email    user's email
     * @param password user's password
     * @return a JSONObject with the given data
     */
    private JSONObject toJsonObject(String name, String email, String password) throws JSONException {
        return new JSONObject().put("name", name).put("email", email).put("password", password);
    }

    /**
     * Creates a JSON object with the given parameters.
     *
     * @param email    user's email
     * @param password user's password
     * @return a JSONObject with the given data
     */
    private JSONObject toJsonObject(String email, String password) throws JSONException {
        return new JSONObject().put("email", email).put("password", password);
    }
}