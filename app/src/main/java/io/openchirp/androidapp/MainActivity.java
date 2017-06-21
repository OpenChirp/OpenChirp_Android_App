package io.openchirp.androidapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.android.volley.toolbox.*;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button startButton = (Button)findViewById(R.id.start_button);
        startButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent1 = new Intent(MainActivity.this, SignInActivity.class);
                startActivity(intent1);
//                Intent intent2 = new Intent(MainActivity.this, StringRequest.class);
//                startActivity(intent2);
//                Intent intent3 = new Intent(MainActivity.this, CookieActivity.class);
//                startActivity(intent3);
            }
        });

    }

}