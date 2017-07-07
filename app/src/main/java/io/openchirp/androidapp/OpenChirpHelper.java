package io.openchirp.androidapp;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;


/**
 * Created by Bavana on 6/21/2017.
 */

public class OpenChirpHelper {
    String url;
    JSONObject postData;

    String TAG = "Helper";

    public JSONObject PostOpenChirp(String url_post, JSONObject postMessage) throws ExecutionException, InterruptedException, JSONException {
        url = url_post;
        postData = postMessage;
        postAsync postObject = new postAsync();
        String response = postObject.execute().get();
        JSONObject resp_json = new JSONObject(response);
        Log.d(TAG, response);
        return resp_json;
    }

    public JSONObject GetOpenChirp(String url_get) throws ExecutionException, InterruptedException, JSONException {
        url = url_get;
        getAsync getObject = new getAsync();
        String response = getObject.execute().get();
        JSONObject resp_json = new JSONObject(response);
        Log.d(TAG, response);
        return resp_json;
    }

    private class getAsync extends AsyncTask<Void, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected String doInBackground(Void... params) {
            StringBuilder sb = new StringBuilder();
            try {
                URL requestUrl = new URL(url);
                HttpURLConnection conn = (HttpURLConnection) requestUrl.openConnection();
                conn.setRequestMethod("GET");
                conn.setRequestProperty("Content-length", "0");
                conn.setUseCaches(false);
                conn.setAllowUserInteraction(false);
                conn.setConnectTimeout(100000);
                conn.setReadTimeout(100000);

                conn.connect();

                int responseCode = conn.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    String line;
                    while ((line = br.readLine()) != null) {
                        sb.append(line).append("\n");
                    }
                    br.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return String.valueOf(sb);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.d("Get", "Finished");
            return;
        }
    }

    private class postAsync extends AsyncTask<Void, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected String doInBackground(Void... params) {
            StringBuilder sb = new StringBuilder();
            try {
                URL requestUrl = new URL(url);
                HttpURLConnection conn = (HttpURLConnection) requestUrl.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-type", "application/json");
                conn.setRequestProperty("charset", "utf-8");
                conn.setRequestProperty("id_token", postData.getString("idToken"));
//                conn.setRequestProperty("Authorization", "Bearer "+ access_token);
                conn.setUseCaches(false);
                conn.setDoOutput(true);
                conn.setAllowUserInteraction(false);
                conn.setConnectTimeout(100000);
                conn.setReadTimeout(100000);


                PrintWriter out = new PrintWriter(conn.getOutputStream());
                out.print(postData);
                out.close();

                conn.connect();

                int responseCode=conn.getResponseCode();

                if (responseCode == HttpURLConnection.HTTP_OK) {

                    Scanner inStream = new Scanner(conn.getInputStream());

                    while(inStream.hasNextLine())
                        sb.append(inStream.nextLine());
                    return sb.toString();
                }
                else {
                    return "false : " + responseCode;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return String.valueOf(sb);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.d("Post", "Finished");
            return;
        }
    }


}