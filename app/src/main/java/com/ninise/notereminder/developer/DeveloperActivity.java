package com.ninise.notereminder.developer;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.ninise.notereminder.R;

public class DeveloperActivity extends AppCompatActivity {

    public static final String TAG = "DeveloperActivity";

    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        toolbar.setTitle(getString(R.string.menu_developer));
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_action_hardware_keyboard_backspace);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        viewAboutFragment();
    }

    private void viewAboutFragment() {
        final  DeveloperFragment fragment = new DeveloperFragment();
        final FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frameAbout, fragment);
        fragmentTransaction.commit();
    }
}
