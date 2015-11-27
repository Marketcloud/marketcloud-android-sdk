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

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.IOException;
import java.net.URL;

/**
 * AsyncImageViewLoader class. <br />
 * <br />
 * Allows the background (not UI blocking) fetching of a remote imageView and its loading in an ImageView.
 */
@SuppressWarnings("unused")
public class AsyncImageViewLoader extends AsyncTask<String, Void, Bitmap> {

    private ImageView imageView;
    private Rect pad = null;
    private BitmapFactory.Options opts;
    private Bitmap alt = null;
    private String nullAction = "placeholder";

    /**
     * Constructor.
     *
     * @param input the ImageView that will host the imageView
     */
    public AsyncImageViewLoader(ImageView input) {
        imageView = input;
    }

    /**
     * Constructor.
     *
     * @param input the ImageView that will host the imageView
     * @param padding sets the imageView padding. by default, it is [-1,-1,-1,-1]
     * @param options imageView options
     */
    public AsyncImageViewLoader(ImageView input, Rect padding, BitmapFactory.Options options) {
        imageView = input;
        pad = padding;
        opts = options;
    }

    /**
     * Constructor.
     *
     * @param input the ImageView that will host the imageView
     * @param placeholder a Bitmap that will be shown if the image is not available or the url is incorrect
     */
    public AsyncImageViewLoader(ImageView input, Bitmap placeholder) {
        imageView = input;
        alt = placeholder;
    }

    /**
     * Constructor.
     *
     * @param input the ImageView that will host the imageView
     * @param padding sets the imageView padding. by default, it is [-1,-1,-1,-1]
     * @param options imageView options
     * @param placeholder a Bitmap that will be shown if the image is not available or the url is incorrect
     */
    public AsyncImageViewLoader(ImageView input, Rect padding, BitmapFactory.Options options, Bitmap placeholder) {
        imageView = input;
        pad = padding;
        opts = options;
        alt = placeholder;
    }

    /**
     * Fetches the imageView from the url given as a parameter.
     *
     * @param params imageView url
     * @return the imageView as a Bitmap
     */
    protected Bitmap doInBackground(String... params) {
        Bitmap pic = null;

        try {
            if (pad == null)
                pic = BitmapFactory.decodeStream(
                        new URL(params[0]).openStream()); //raw url
            else
                pic = BitmapFactory.decodeStream(
                        new URL(params[0]).openStream(),
                        pad,
                        opts); //raw url + padding + options
        } catch (IOException e) {
            e.printStackTrace();
        }

        return pic;
    }

    /**
     * Loads the fetched imageView into the given ImageView. <br />
     * NOTE: if the image is null, the placeholder will be used; in case they are both null, a null image will
     * be loaded. The consequences of this situation may vary, depending on the OS in use and on the choices
     * of the developers. <br />
     * It is possibile to override this behaviour, choosing to avoid any loading in case of both null images,
     * using the method "setLoadOnNullBehaviour".
     *
     * @param result the fetched imageView
     */
    protected void onPostExecute(Bitmap result) {
        if (result != null)
            imageView.setImageBitmap(result); //downloaded image
        else if (nullAction.equals("placeholder") && alt != null)
            imageView.setImageBitmap(alt); //placeholder
        else if (nullAction.equals("null"))
            imageView.setImageBitmap(null);
    }

    /**
     * Defines the action that will be performed if the fetched image is null or the url is incorrect. <br />
     * <br />
     * Possibilities: <br />
     * - "placeholder" (uses the given placeholder, if not null); <br />
     * - "null" (loads a null object); <br />
     * - with any other string, nothing will be loaded. <br />
     * By default, "placeholder" action is performed.
     *
     * @param behaviour the action that will be performed
     */
    public void setLoadOnNullBehaviour(String behaviour) {
        nullAction = behaviour;
    }
}