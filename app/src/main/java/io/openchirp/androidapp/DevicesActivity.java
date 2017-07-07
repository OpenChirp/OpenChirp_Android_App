package io.openchirp.androidapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class DevicesActivity extends AppCompatActivity {

    private static final String TAG = "DevicesActivity";

    OpenChirpHelper openChirpHelper = new OpenChirpHelper();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_services);

        Log.d(TAG, "Started HttpUrlConnection-Devices");

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

        Log.d(TAG, "Ended HttpUrlConnection-Devices");

//        Button location_button = (Button)findViewById(R.id.location_button);
//        location_button.setOnClickListener(new View.OnClickListener(){
//            public void onClick(View v){
//                Intent intent1 = new Intent(DevicesActivity.this, LocationActivity.class);
//                startActivity(intent1);
//            }
//        });
    }
}
