package com.ninise.notereminder.main;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    int mNumberOfTabs;

    public ViewPagerAdapter(FragmentManager fm, int numbOfTabs) {
        super(fm);

        this.mNumberOfTabs = numbOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        return position == 0 ? new NoteListFragment() : new ReminderListFragment();
    }


    @Override
    public int getCount() {
        return mNumberOfTabs;
    }
}
