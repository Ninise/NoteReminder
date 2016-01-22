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
import android.widget.Toast;

import com.ninise.notereminder.R;
import com.ninise.notereminder.notedata.NoteActivity;

public class MainActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private ViewPager mViewPager;
    private ViewPagerAdapter mAdapter;
    private TabLayout mTabLayout;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mToolbar = (Toolbar) findViewById(R.id.tool_bar);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.menu_new_note:
                switchToNoteActivity();
                return true;
            case R.id.menu_settings:
                Toast.makeText(getApplicationContext(), R.string.menu_settings, Toast.LENGTH_SHORT).show();
                return true;
            case R.id.menu_exit:
                closeApp();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void switchToNoteActivity() {
        Intent intent = new Intent(this, NoteActivity.class);
        startActivity(intent);
    }

    private void closeApp() {
        finish();
    }
}
