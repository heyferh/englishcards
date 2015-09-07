package com.feku.englishcards.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

import com.feku.englishcards.R;
import com.feku.englishcards.fragment.WeeklyProgressChart;
import com.feku.englishcards.fragment.LevelsPieChart;
import com.feku.englishcards.fragment.TotalWordsLearnt;

/**
 * Created by feku on 9/2/2015.
 */
public class StatisticsActivity extends ActivityWithDrawer {
    private static final int NUM_PAGES = 3;
    private ViewPager mPager;

    private PagerAdapter mPagerAdapter;

    @Override
    protected int getLayout() {
        return R.layout.statistics_activity;
    }

    @Override
    protected void initOnCreate() {
        getSupportActionBar().setTitle("Statistics");
        drawer = drawer.withSelectedItem(4);
        drawer.build();
        mPager = (ViewPager) findViewById(R.id.pager);
        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                default:
                    return new TotalWordsLearnt();
                case 1:
                    return new WeeklyProgressChart();
                case 2:
                    return new LevelsPieChart();
            }
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                default:
                    return "Total words learnt";
                case 1:
                    return "Weekly progress";
                case 2:
                    return "Levels pie chart";
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}
