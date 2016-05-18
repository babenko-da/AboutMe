package com.blogspot.babenkodmitry.aboutme;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static String TAG = "MainActivity";
    public static Typeface faceLight;
    public static Typeface faceMedium;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        faceLight = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Light.ttf");
        faceMedium = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Medium.ttf");

        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(new RecyclerViewAdapter(getDataSet()));
    }

    private ArrayList<CardDataObject> getDataSet() {
        ArrayList<CardDataObject> result = new ArrayList<>();

        result.add(new CardDataObject(getString(R.string.it_skills),
                arrayToString(getResources().getStringArray(R.array.it_skills)),
                getDrawable(R.drawable.ic_work_black_24dp)));

        result.add(new CardDataObject(getString(R.string.education),
                arrayToString(getResources().getStringArray(R.array.education)),
                getDrawable(R.drawable.ic_school_black_24dp)));

        result.add(new CardDataObject(getString(R.string.languages),
                arrayToString(getResources().getStringArray(R.array.languages)),
                getDrawable(R.drawable.ic_translate_black_24dp)));

        result.add(new CardDataObject(getString(R.string.interests),
                arrayToString(getResources().getStringArray(R.array.interests)),
                getDrawable(R.drawable.ic_favorite_black_24dp)));
        return result;
    }

    private String arrayToString(String[] arr) {
        StringBuilder builder = new StringBuilder();
        builder.append(arr[0]);
        int len = arr.length;
        for (int i = 1; i < len; i++) {
            builder.append("\n");
            builder.append(arr[i]);
        }
        return builder.toString();
    }
}
