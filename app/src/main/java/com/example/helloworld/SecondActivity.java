package com.example.helloworld;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class SecondActivity extends AppCompatActivity { //every activity extends from this

    private ConstraintLayout constraintLayout;
    private LinearLayout linearLayout;
    private Button button_goBack;
    private String receivedMessage;

    private static final String api_url = "https://icanhazdadjoke.com/";
    private static AsyncHttpClient client = new AsyncHttpClient();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //everything in layout will be set to this second_activity
        setContentView(R.layout.activity_second);
        //manifest file, keeps track of components of app
        //have to update manifest file

        //display a message
        //x was passed from main activity


        //extract intent extras information
        Intent intent = getIntent();
        receivedMessage = intent.getStringExtra("count"); //store some sort of constant of the name sof the key
        Log.d("Data from Main Activity", receivedMessage);

        //grab the view in second activity
        constraintLayout = findViewById(R.id.second_root_layout);
        linearLayout = findViewById(R.id.second_linear_layout);
        button_goBack = findViewById(R.id.button_goBack);

        //create a textview
        //set the text to receivedMessage + "was passed from main acitivty"
        //add this textView to the View
        /***
         TextView textView = new TextView(this);
         textView.setText(receivedMessage + " was passed from this activity");
         textView.setTextSize(30);
         constraintLayout.addView(textView);
         */

        //if you do not know how many views you will need to create
        for (int i = 0; i < Integer.parseInt(receivedMessage); i++) {
            TextView textView = new TextView(this);
            textView.setText("hello");
            linearLayout.addView(textView);
        }

        //add click listener for our button
        button_goBack.setOnClickListener(v -> { //lambda version
            launchNextActivity(v);
        });
    }

    /**
    public void replyIntent(View view){
        //create a reply intent and pack the information, send it back to the main activity
        Intent replyIntent = new Intent();
        replyIntent.putExtra("replyCount",receivedMessage);
        setResult(RESULT_OK, replyIntent);
        finish();
    }*/

    public void launchNextActivity(View view){
        //when the button is clicked
        //I want to set a get request to the API
        //add the data received from the response to the intent
        //send it to third activity to be displayed

        //set the header because of the api endpoint
        client.addHeader("Accept", "application/json");
        //send a get request to the api URL
        client.get(api_url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                //when you get a 200 status code
                Log.d("api response", new String(responseBody));

                try {
                    JSONObject json = new JSONObject(new String(responseBody));
                    Intent intent = new Intent(SecondActivity.this, ThirdActivity.class);
                    //add the joke to the inent
                    intent.putExtra("joke", json.getString("joke"));

                    //convert any json data into a string to put into the intent
                    //when you reveive the intent int he next activity,
                    //convert it back to the json data

                    startActivity(intent);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                //when you get a 400 -499 status code
                Log.e("api error", new String(responseBody));
            }
        });
        Intent intent  = new Intent(this,ThirdActivity.class);
        startActivity(intent);

    }
}
