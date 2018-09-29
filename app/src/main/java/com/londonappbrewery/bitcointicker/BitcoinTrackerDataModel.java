package com.londonappbrewery.bitcointicker;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.json.JSONException;
import org.json.JSONObject;

public class BitcoinTrackerDataModel extends AppCompatActivity {

    private String mBitcoinPrice;


    public static BitcoinTrackerDataModel fromJson(JSONObject jsonObject){

        BitcoinTrackerDataModel bitcoinData = new BitcoinTrackerDataModel();

        try {
            bitcoinData.mBitcoinPrice = jsonObject.getString("bid");

            return bitcoinData;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public String getBitcoinPrice() {
        return mBitcoinPrice;
    }
}
