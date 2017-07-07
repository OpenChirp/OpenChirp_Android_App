package io.openchirp.androidapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class LocationActivity extends AppCompatActivity {

    private static final String TAG = "LocationActivity";

    OpenChirpHelper openChirpHelper = new OpenChirpHelper();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services);

        Log.d(TAG, "Started HttpUrlConnection-Location");

        String url = "http://iot.andrew.cmu.edu:10010/auth/google/token";

        try {
            JSONObject resp = openChirpHelper.GetOpenChirp(url);
            Log.d(TAG, resp.toString());
            String message_response = "Response: " +resp.toString();
            Log.w(TAG, message_response);
            Toast.makeText(this, message_response, Toast.LENGTH_LONG).show();

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.d(TAG, "Ended HttpUrlConnection-Location");
    }
}
