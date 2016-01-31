package com.ninise.notereminder.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.ninise.notereminder.R;
import com.ninise.notereminder.developer.DeveloperActivity;
import com.ninise.notereminder.main.adapters.ViewPagerAdapter;
import com.ninise.notereminder.notedata.NoteActivity;
import com.ninise.notereminder.settings.SettingsActivity;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivty";

    TabLayout mTabLayout;
    ViewPagerAdapter mAdapter;
    ViewPager mViewPager;

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Toolbar mToolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(mToolbar);

        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mTabLayout = (TabLayout) findViewById(R.id.tabs);

        mAdapter = new ViewPagerAdapter(getSupportFragmentManager(), 2);
        mViewPager.setAdapter(mAdapter);

        final TabLayout.Tab note = mTabLayout.newTab();
        final TabLayout.Tab reminder = mTabLayout.newTab();

        note.setText(R.string.note_fragment);
        reminder.setText(R.string.reminder_fragment);

        mTabLayout.addTab(note);
        mTabLayout.addTab(reminder);

        mTabLayout.setTabTextColors(ContextCompat.getColorStateList(this, R.drawable.tab_selector));
        mTabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(this, R.color.indicator));

        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        final int id = item.getItemId();

        switch (id) {
            case R.id.menu_new_note:
                switchToNoteActivity();
                return true;
            case R.id.menu_settings:
                switchToSettingsActivity();
                return true;
            case R.id.menu_developer:
                switchToDevActivity();
                return true;
            case R.id.menu_exit:
                closeApp();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void switchToNoteActivity() {
        final Intent intent = new Intent(this, NoteActivity.class);
        startActivity(intent);
    }

    private void switchToDevActivity() {
        final Intent intent = new Intent(this, DeveloperActivity.class);
        startActivity(intent);
    }

    private void switchToSettingsActivity() {
        final Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    private void closeApp() {
        finish();
    }
}
