package io.aearnus.stocksbib;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.zip.Inflater;

public class StockOverviewPagerAdapter extends FragmentStatePagerAdapter {
    private Fragment[] stockFragments;
    private final int TAB_AMOUNT = 6;
    public StockOverviewPagerAdapter(FragmentManager fm) {
        super(fm);
        stockFragments = new Fragment[TAB_AMOUNT];
        stockFragments[0] = new OverviewFragment();
        stockFragments[1] = new BuySellFragment();
        stockFragments[2] = new CurrencyFragment();
        stockFragments[3] = new UpgradesFragment();
        stockFragments[4] = new StatsFragment();
        stockFragments[5] = new OptionsFragment();
    }
    @Override
    // Returns the number of tabs in the tab pager
    public int getCount() {
        return TAB_AMOUNT;
    }
    @Override
    // Gets a fragment to display for tab n
    public Fragment getItem(int item) {
        //TODO: fragments are being destroyed, stop this
        return stockFragments[item];
    }
}
