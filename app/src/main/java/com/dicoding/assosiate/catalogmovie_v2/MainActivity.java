package com.dicoding.assosiate.catalogmovie_v2;

import android.content.Intent;
import java.util.Calendar;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    CircleImageView profileCircleImageView;

    DrawerLayout drawer;
    Toolbar toolbar;
    ActionBarDrawerToggle toggle;
    TabLayout tabLayout;
    ViewPager viewPager;
    SchedulerTask schedulerTask;
    AlarmPreference alarmPreference;
    AlarmReceiver alarmReceiver;

    public Calendar calRepeatingTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        schedulerTask = new SchedulerTask(this);
        schedulerTask.createPeriodTask();

        calRepeatingTime = Calendar.getInstance();
        alarmPreference = new AlarmPreference(this);
        alarmReceiver = new AlarmReceiver();

        startAlarm();

        getSupportActionBar().setTitle(getResources().getString(R.string.home));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        profileCircleImageView = (CircleImageView) navigationView.getHeaderView(0).findViewById(R.id.imageView);

        Glide.with(MainActivity.this)
                .load(R.drawable.pic_seller)
                .into(profileCircleImageView);
        navigationView.setNavigationItemSelectedListener(this);

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        setupViewPager(viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

    }

    private void startAlarm() {
        calRepeatingTime.set(Calendar.HOUR_OF_DAY, 11);
        calRepeatingTime.set(Calendar.MINUTE, 25);

        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        String repeatTime = timeFormat.format(calRepeatingTime.getTime());
        String repeatTimeMessage = "Catalogue Movie kangen sama kamu nich!!";

        alarmPreference.setRepeatingTime(repeatTime);
        alarmPreference.setRepeatingMessage(repeatTimeMessage);

        alarmReceiver.setRepeatingAlarm(this, AlarmReceiver.TYPE_REPEATING, alarmPreference.getRepeatingTime(), alarmPreference.getRepeatingMessage());
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Intent intent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_now_playing) {
            //fragment = new UpcomingFragment();
            viewPager.setCurrentItem(0);
        } else if (id == R.id.nav_uncoming) {
            //fragment = new NowPlayingFragment();
            viewPager.setCurrentItem(1);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        drawer.removeDrawerListener(toggle);
    }

    @Override
    protected void onResume() {
        super.onResume();
        toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }

    private void setupViewPager(ViewPager viewPager) {
        try {
            ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
            adapter.addFrag(new NowPlayingFragment(), getResources().getString(R.string.now_playing));
            adapter.addFrag(new UpcomingFragment(), getResources().getString(R.string.upcoming));
            //adapter.addFrag(new DetailMovieFragment(), "");
            viewPager.setAdapter(adapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
