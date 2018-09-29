package com.londonappbrewery.bitcointicker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Toast;
import com.loopj.android.http.AsyncHttpClient;

import com.loopj.android.http.*;


import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;


public class MainActivity extends AppCompatActivity {

    // Constants:
    // TODO: Create the base URL
    private final String BASE_URL = "https://apiv2.bitcoinaverage.com/indices/global/ticker/BTC";

    // Member Variables:
    TextView mPriceTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPriceTextView = (TextView) findViewById(R.id.priceLabel);
        Spinner spinner = (Spinner) findViewById(R.id.currency_spinner);

        // Create an ArrayAdapter using the String array and a spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.currency_array, R.layout.spinner_item);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);

        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Create a new URL by adding the currency string to the base URL
                Log.d("Bitcoin","" + parent.getItemAtPosition(position));
                String apiRequestURL = BASE_URL + parent.getItemAtPosition(position);
                Log.d("Bitcoin", "" + apiRequestURL);
                letsDoSomeNetworking(apiRequestURL);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

                Log.d("Bitcoin", "Nothing Selected");

            }
        });

    }

    // TODO: complete the letsDoSomeNetworking() method
    private void letsDoSomeNetworking(String url) {
        AsyncHttpClient client = new AsyncHttpClient();

        //Make a call using the api rules and get the JSON object as a response
        client.get(url, new JsonHttpResponseHandler() {

            @Override
            public void onStart() {
                //called before request is started
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // called when response HTTP status is "200 OK"
                Log.d("Bitcoin", "JSON WORKED: " + response.toString());
                BitcoinTrackerDataModel bitcoinData = BitcoinTrackerDataModel.fromJson(response);
                mPriceTextView.setText(bitcoinData.getBitcoinPrice());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String fe, Throwable e) {
                // called when response HTTP status is "4XX" (eg. 401, 403, 404)
                Log.e("Bitcoin",  "ERROR" + e.toString());
                Log.d("Bitcoin" + "fe", "Request fail! Status code: " + statusCode);
                Toast.makeText(MainActivity.this, "Request Failed", Toast.LENGTH_SHORT).show();


            }

        });


    }


}
