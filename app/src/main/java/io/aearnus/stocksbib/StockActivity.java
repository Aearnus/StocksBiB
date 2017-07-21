package io.aearnus.stocksbib;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.SurfaceView;

// https://developer.android.com/training/implementing-navigation/lateral.html

public class StockActivity extends FragmentActivity {
    // establish game data and variables
    GameData gameData;
    UpdateThread updateThread;

    // establish maintence stuff
    Boolean isActivityInForeground;
    StockOverviewPagerAdapter stockOverviewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_overview);
        // initialize the game data
        // TODO: loading the game data
        gameData = new GameData();
        // create and set the tabbed view
        stockOverviewPagerAdapter = new StockOverviewPagerAdapter(getSupportFragmentManager());
        ViewPager viewPager = (ViewPager)findViewById(R.id.view_pager);
        viewPager.setAdapter(stockOverviewPagerAdapter);
        // initialize the game updating thread
        updateThread = new UpdateThread(gameData, stockOverviewPagerAdapter.getItem(1));
        updateThread.start();

        isActivityInForeground = true;
    }
    @Override
    protected void onStart() {
        super.onStart();
        // TODO: loading the game data
        isActivityInForeground = true;
    }
    @Override
    protected void onResume() {
        super.onResume();
        // TODO: loading the game data
        isActivityInForeground = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        gameData.saveGame();
        isActivityInForeground = false;
    }
    @Override
    protected void onStop() {
        super.onStop();
        gameData.saveGame();
        isActivityInForeground = false;
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        gameData.saveGame();
        isActivityInForeground = false;
    }
}

