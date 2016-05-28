package com.blogspot.babenkodmitry.aboutme;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.design.widget.AppBarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.LinearLayout;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements AppBarLayout.OnOffsetChangedListener {
    private static String TAG = "MainActivity";
    public static Typeface faceLight;
    public static Typeface faceMedium;

    private static final float PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR  = 0.9f;
    private static final float PERCENTAGE_TO_HIDE_TITLE_DETAILS     = 0.3f;
    private static final int ALPHA_ANIMATIONS_DURATION              = 200;

    private boolean mIsTheTitleVisible          = false;
    private boolean mIsTheTitleDescriptionContainerVisible = true;

    private LinearLayout mTitleContainer;
    private LinearLayout mTitleDescriptionContainer;
    private AppBarLayout mAppBarLayout;
    private Toolbar mToolbar;

    private CircleImageView mPhoto;
    private int originalPhotoSize;
    private int minPhotoSize;
    private float mPhotoSizeDif;
    private float oldPer = 0.0f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        faceLight = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Light.ttf");
        faceMedium = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Medium.ttf");

        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(new RecyclerViewAdapter(getDataSet()));



        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            bindActivity();
            originalPhotoSize = getResources().getDimensionPixelSize(R.dimen.photo_size);
            minPhotoSize = getResources().getDimensionPixelSize(R.dimen.photo_final_width);
            mPhotoSizeDif = originalPhotoSize - minPhotoSize;

            setSupportActionBar(mToolbar);
            getSupportActionBar().setDisplayShowTitleEnabled(false);

            mAppBarLayout.addOnOffsetChangedListener(this);
            startAlphaAnimation(mTitleContainer, 0, View.INVISIBLE);
        }

        mToolbar.inflateMenu(R.menu.menu_activity_main);//todo нужна ли эта строка и зачем? в onCreateOptionMenu идет раздувание меню инфлятором
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.main_menu_action_open_on_github:
                openUrl(getResources().getString(R.string.gitHubLink));
                return true;
            case R.id.main_menu_action_open_on_linkedin:
                openUrl(getResources().getString(R.string.linkedinLink));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void openUrl(String url) {
        Intent intent= new Intent(Intent.ACTION_VIEW,Uri.parse(url));
        startActivity(intent);
    }

    private ArrayList<CardDataObject> getDataSet() {
        ArrayList<CardDataObject> result = new ArrayList<>();

        result.add(new CardDataObject(getString(R.string.it_skills),
                arrayToString(getResources().getStringArray(R.array.it_skills)),
                ContextCompat.getDrawable(this, R.drawable.ic_work_black_24dp)));

        result.add(new CardDataObject(getString(R.string.education),
                arrayToString(getResources().getStringArray(R.array.education)),
                ContextCompat.getDrawable(this, R.drawable.ic_school_black_24dp)));

        result.add(new CardDataObject(getString(R.string.languages),
                arrayToString(getResources().getStringArray(R.array.languages)),
                ContextCompat.getDrawable(this, R.drawable.ic_translate_black_24dp)));

        result.add(new CardDataObject(getString(R.string.interests),
                arrayToString(getResources().getStringArray(R.array.interests)),
                ContextCompat.getDrawable(this, R.drawable.ic_favorite_black_24dp)));
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

    //header animation
    private void bindActivity() {
        mPhoto = (CircleImageView) findViewById(R.id.photo);
        mToolbar        = (Toolbar) findViewById(R.id.main_toolbar);
        mTitleContainer = (LinearLayout) findViewById(R.id.main_toolbar_title_container);
        mTitleDescriptionContainer = (LinearLayout) findViewById(R.id.main_title_description_container);
        mAppBarLayout   = (AppBarLayout) findViewById(R.id.main_app_bar_layout);
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int offset) {
        int maxScroll = appBarLayout.getTotalScrollRange();
        float percentage = (float) Math.abs(offset) / (float) maxScroll;

        if (oldPer != percentage) {
            oldPer = percentage; //предотвращение повторного вызова, обусловленного прминением параметров для mPhoto

            ViewGroup.LayoutParams lp = mPhoto.getLayoutParams();
            lp.height = lp.width = (int)((1.0f - percentage) * mPhotoSizeDif) + minPhotoSize;
            mPhoto.setLayoutParams(lp);

//            Log.d(TAG, "persengae: " + String.valueOf(percentage));
            handleAlphaOnTitle(percentage);
            handleToolbarTitleVisibility(percentage);
        }
    }

    private void handleToolbarTitleVisibility(float percentage) {
        if (percentage >= PERCENTAGE_TO_SHOW_TITLE_AT_TOOLBAR) {
            if(!mIsTheTitleVisible) {
                startAlphaAnimation(mTitleContainer, ALPHA_ANIMATIONS_DURATION, View.VISIBLE);
                mIsTheTitleVisible = true;
            }
        } else {
            if (mIsTheTitleVisible) {
                startAlphaAnimation(mTitleContainer, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE);
                mIsTheTitleVisible = false;
            }
        }
    }

    private void handleAlphaOnTitle(float percentage) {
        if (percentage >= PERCENTAGE_TO_HIDE_TITLE_DETAILS) {
            if(mIsTheTitleDescriptionContainerVisible) {
                startAlphaAnimation(mTitleDescriptionContainer, ALPHA_ANIMATIONS_DURATION, View.INVISIBLE);
                mIsTheTitleDescriptionContainerVisible = false;
            }
        } else {
            if (!mIsTheTitleDescriptionContainerVisible) {
                startAlphaAnimation(mTitleDescriptionContainer, ALPHA_ANIMATIONS_DURATION, View.VISIBLE);
                mIsTheTitleDescriptionContainerVisible = true;
            }
        }
    }

    public static void startAlphaAnimation (View v, long duration, int visibility) {
        AlphaAnimation alphaAnimation = (visibility == View.VISIBLE) ? new AlphaAnimation(0f, 1f)
                                                                    : new AlphaAnimation(1f, 0f);
        alphaAnimation.setDuration(duration);
        alphaAnimation.setFillAfter(true);
        v.startAnimation(alphaAnimation);
    }
}
