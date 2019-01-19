package com.racoon_moon.kahootproject.network;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Handler;
import android.util.LruCache;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.racoon_moon.kahootproject.questions.data.Question;
import com.racoon_moon.kahootproject.questions.data.Quiz;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

public class NetworkConnection {

    private static NetworkConnection mInstance;
    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;
    private static Context mCtx;

    private final String HOST_URL = "http://192.168.1.5:8080/backend/";
    private  final String BASE_URL = HOST_URL + "res";

    public static final String GET_ALL_QUIZZES_JSON_REQ = "0";
    public static final String INSERT_QUIZ_REQ = "1";
    public static final String DELETE_QUIZ_REQ = "2";
    public static final String INSERT_KAHOOT_REQ = "3";
    public static final String DELETE_KAHOOT_REQ = "4";
    //public static final String GET_ITEM_IMAGE_REQ = "5";
    //public static final String INSERT_ITEM_WITH_IMG_REQ = "6";
    public static final String QUIZ_ID = "f_id";
    public static final String QUIZ_NAME = "f_title";

    public static final String RESOURCE_FAIL_TAG = "{\"result_code\":0}";
    public static final String RESOURCE_SUCCESS_TAG = "{\"result_code\":1}";

    public static final String KAHOOT_ID = "kahoot_id";
    public static final String KAHOOT_QUESTION = "kahoot_question";
    public static final String KAHOOT_ANSWER1 = "kahoot_answer1";
    public static final String KAHOOT_ANSWER2 = "kahoot_answer2";
    public static final String KAHOOT_ANSWER3 = "kahoot_answer3";
    public static final String KAHOOT_ANSWER4 = "kahoot_answer4";
    public static final String KAHOOT_IN_QUIZ = "kahoot_quiz_id";

    public static final String REQ = "req";

    private NetworkConnection(){

    }

    public static synchronized NetworkConnection getInstance() {
        if (mInstance == null) {
            mInstance = new NetworkConnection();
        }
        return mInstance;
    }

    public void initialize(Context context){
        mCtx = context;
        mRequestQueue = getRequestQueue();

        mImageLoader = new ImageLoader(mRequestQueue,
                new ImageLoader.ImageCache() {
                    private final LruCache<String, Bitmap>
                            cache = new LruCache<String, Bitmap>(20);

                    @Override
                    public Bitmap getBitmap(String url) {
                        return cache.get(url);
                    }

                    @Override
                    public void putBitmap(String url, Bitmap bitmap) {
                        cache.put(url, bitmap);
                    }
                });
    }

    private RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            mRequestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
        }
        return mRequestQueue;
    }

    private void addToRequestQueue(String query, final NetworkResListener listener) {

        String reqUrl = BASE_URL + "?" + query;
        notifyPreUpdateListener(listener);

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.POST, reqUrl, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        notifyPostUpdateListener(response, ResStatus.SUCCESS, listener);
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();

                        JSONObject err = null;
                        try {
                            err = new JSONObject(RESOURCE_FAIL_TAG);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        finally {
                            notifyPostUpdateListener(err, ResStatus.FAIL, listener);
                        }

                    }
                });

        getRequestQueue().add(jsObjRequest);
    }

    private void addImageRequestToQueue(String query, final NetworkResListener listener){

        String reqUrl = BASE_URL + "?" + query;

        notifyPreUpdateListener(listener);

        getImageLoader().get(reqUrl, new ImageLoader.ImageListener() {
            @Override
            public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                Bitmap bm = response.getBitmap();
                notifyPostBitmapUpdateListener(bm, ResStatus.SUCCESS, listener);
            }

            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                notifyPostBitmapUpdateListener(null, ResStatus.FAIL, listener);
            }
        });
    }

    private ImageLoader getImageLoader() {
        return mImageLoader;
    }

    private void uploadQuizImage(final Quiz item, final NetworkResListener listener) {

        String reqUrl = HOST_URL + "upload_post?";
        notifyPreUpdateListener(listener);


        //our custom volley request
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, reqUrl,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        try {
                            JSONObject obj = new JSONObject(new String(response.data));
                            Toast.makeText(mCtx, obj.getString("message"), Toast.LENGTH_SHORT).show();
                            notifyPostUpdateListener(obj, ResStatus.SUCCESS, listener);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(mCtx, error.getMessage(), Toast.LENGTH_SHORT).show();
                        JSONObject obj = null;
                        try {
                            obj = new JSONObject(RESOURCE_FAIL_TAG );
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        finally {
                            notifyPostUpdateListener(obj, ResStatus.FAIL, listener);
                        }

                    }
                }) {

            /*
             * If you want to add more parameters with the image
             * you can do it here
             * here we have only one parameter with the image
             * which is tags
             * */
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put(QUIZ_ID, item.getId());
                params.put(QUIZ_NAME, item.getName());

                return params;
            }

            /*
             * Here we are passing image by renaming it with a unique name
             * */
            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                long imagename = System.currentTimeMillis();
                Bitmap bitmap = item.getPicture();
                byte[] pic;
                if (bitmap != null) {
                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.PNG, 0, outputStream);
                    pic = outputStream.toByteArray();
                    params.put("fileField", new DataPart(imagename + ".png", pic));
                }else params.put("fileField", new DataPart(imagename + ".png", null));
                return params;
            }
        };

        //adding the request to volley
        getRequestQueue().add(volleyMultipartRequest);
    }

    public void sendRequestToServer(String requestCode, Question data, NetworkResListener listener){

        if(data==null){
            return;
        }

        Uri.Builder builder = new Uri.Builder();

        switch (requestCode){
//            case INSERT_ITEM_WITH_IMG_REQ:{
//
//                uploadItemImage(data, listener);
//
//                break;
//            }
            case INSERT_KAHOOT_REQ:{
                builder.appendQueryParameter(REQ , requestCode);
                builder.appendQueryParameter(KAHOOT_ID , data.getId());
                builder.appendQueryParameter(KAHOOT_QUESTION, data.getQuestion());
                builder.appendQueryParameter(KAHOOT_ANSWER1, data.getAnswer1());
                builder.appendQueryParameter(KAHOOT_ANSWER2, data.getAnswer2());
                builder.appendQueryParameter(KAHOOT_ANSWER3, data.getAnswer3());
                builder.appendQueryParameter(KAHOOT_ANSWER4, data.getAnswer4());
                builder.appendQueryParameter(KAHOOT_IN_QUIZ, data.getQuizId());

                String query = builder.build().getEncodedQuery();
                addToRequestQueue(query, listener);

                break;
            }
            case DELETE_KAHOOT_REQ:{
                builder.appendQueryParameter(REQ , requestCode);
                //builder.appendQueryParameter(QUIZ_ID , data.getQuizId(), KAHOOT_ID, data.getId());

                String query = builder.build().getEncodedQuery();
                addToRequestQueue(query, listener);

                break;
            }
//            case GET_ITEM_IMAGE_REQ: {
//                builder.appendQueryParameter(REQ , requestCode);
//                builder.appendQueryParameter(ITEM_ID , data.getId());
//
//                String query = builder.build().getEncodedQuery();
//                addImageRequestToQueue(query, listener);
//
//                break;
//            }
        }
    }

    public void update(NetworkResListener listener){

        Uri.Builder builder = new Uri.Builder();
        builder.appendQueryParameter(REQ , GET_ALL_QUIZZES_JSON_REQ);
        String query = builder.build().getEncodedQuery();

        addToRequestQueue(query, listener);
    }

    public void sendRequestToServer(String requestCode, Quiz data, NetworkResListener listener) {


        if(data==null){
            return;
        }

        Uri.Builder builder = new Uri.Builder();

        switch (requestCode){
            case INSERT_QUIZ_REQ:{
                uploadQuizImage(data, listener);
//                builder.appendQueryParameter(REQ , requestCode);
//                builder.appendQueryParameter(QUIZ_ID ,data.getId());
//                builder.appendQueryParameter(QUIZ_NAME , data.getName());
                break;
            }
            case DELETE_QUIZ_REQ:{
                builder.appendQueryParameter(REQ , requestCode);
                builder.appendQueryParameter(QUIZ_ID , data.getId());
                break;
            }
        }
        String query = builder.build().getEncodedQuery();

        addToRequestQueue(query, listener);

    }

    private  void notifyPostBitmapUpdateListener(final Bitmap res, final ResStatus status, final NetworkResListener listener) {

        Handler handler = new Handler(mCtx.getMainLooper());

        Runnable myRunnable = new Runnable() {

            @Override
            public void run() {
                try{
                    listener.onPostUpdate(res, status);
                }
                catch(Throwable t){
                    t.printStackTrace();
                }
            }
        };
        handler.post(myRunnable);

    }

    private  void notifyPostUpdateListener(final JSONObject res, final ResStatus status, final NetworkResListener listener) {

        Handler handler = new Handler(mCtx.getMainLooper());

        Runnable myRunnable = new Runnable() {

            @Override
            public void run() {
                try{
                    listener.onPostUpdate(res, status);
                }
                catch(Throwable t){
                    t.printStackTrace();
                }
            }
        };
        handler.post(myRunnable);

    }

    private  void notifyPreUpdateListener(final NetworkResListener listener) {


        Handler handler = new Handler(mCtx.getMainLooper());

        Runnable myRunnable = new Runnable() {

            @Override
            public void run() {
                try{
                    listener.onPreUpdate();
                }
                catch(Throwable t){
                    t.printStackTrace();
                }
            }
        };
        handler.post(myRunnable);

    }
}
