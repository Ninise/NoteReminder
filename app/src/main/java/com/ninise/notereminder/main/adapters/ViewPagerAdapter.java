package com.ninise.notereminder.main.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.ninise.notereminder.main.NoteListFragment;
import com.ninise.notereminder.main.ReminderListFragment;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    int mNumberOfTabs;

    public ViewPagerAdapter(final FragmentManager fm, final int numbOfTabs) {
        super(fm);

        this.mNumberOfTabs = numbOfTabs;
    }

    @Override
    public Fragment getItem(final int position) {
        return position == 0 ? new NoteListFragment() : new ReminderListFragment();
    }

    @Override
    public int getCount() {
        return mNumberOfTabs;
    }
}
