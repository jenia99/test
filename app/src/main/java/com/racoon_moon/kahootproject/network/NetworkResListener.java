package com.racoon_moon.kahootproject.network;

import android.graphics.Bitmap;

import org.json.JSONObject;

/**
 * NetworkResListener interface
 */
public interface NetworkResListener {
    /**
     * callback method which called when the resources update is started
     */
    public void onPreUpdate();

    /**
     * callback method which called after resources update is finished
     * @param  res  - the data
     * @param status - the status of the update process
     */
    void onPostUpdate(byte[] res, ResStatus status);

    void onPostUpdate(JSONObject res, ResStatus status);

    void onPostUpdate(Bitmap res, ResStatus status);
}
