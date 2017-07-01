package com.br.positivityapp.positivityapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.br.positivityapp.R;
import com.br.positivityapp.fragments.AddNewPosFragment;
import com.br.positivityapp.fragments.ListPosFragment;
import com.br.positivityapp.fragments.MapsFragment;
import com.br.positivityapp.fragments.NewPosFragment;
import com.br.positivityapp.fragments.ReviewPosFragment;
import com.br.positivityapp.utils.CustomViewPager;
import com.prolificinteractive.materialcalendarview.CalendarDay;

public class MainActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    public SectionsPagerAdapter mSectionsPagerAdapter;
    public TabLayout tabLayout;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    public CustomViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (CustomViewPager) findViewById(R.id.container);

        mViewPager.setAdapter(mSectionsPagerAdapter);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

//        new TestPopulateDB().populatePosDB(this);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent i = new Intent(this,SettingsActivity.class);
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentStatePagerAdapter{

        private Fragment fragmentAtPos0;
        private Fragment fragmentAtPos1;
        private FragmentManager fragmentManager;
        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
            fragmentManager = fm;
        }

        public final class CalendarPageListener {

            public void onSwitchToNextFirstFragment(CalendarDay day) {
                fragmentManager.beginTransaction().remove(fragmentAtPos0).commit();
                if (fragmentAtPos0 instanceof NewPosFragment){
                    fragmentAtPos0 = new AddNewPosFragment();
                    ((AddNewPosFragment)fragmentAtPos0).setDaySelected(day);
                    ((AddNewPosFragment)fragmentAtPos0).listener = listener;

                }else{ // Instance of NextFragment
                    fragmentAtPos0 = new NewPosFragment();
                    ((NewPosFragment)fragmentAtPos0).listener = listener;
                }
                notifyDataSetChanged();
            }

            public void onSwitchToNextSecondFragment(CalendarDay day) {
                fragmentManager.beginTransaction().remove(fragmentAtPos1).commit();
                if (fragmentAtPos1 instanceof ReviewPosFragment){
                    fragmentAtPos1 = new ListPosFragment();
                    ((ListPosFragment)fragmentAtPos1).setDay(day);
                    ((ListPosFragment)fragmentAtPos1).listener = listener;

                }else{ // Instance of NextFragment
                    fragmentAtPos1 = new ReviewPosFragment();
                    ((ReviewPosFragment)fragmentAtPos1).listener = listener;
                }
                notifyDataSetChanged();
            }
        }

        CalendarPageListener listener = new CalendarPageListener();

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    if (fragmentAtPos0 == null) {
                        fragmentAtPos0 = new NewPosFragment();
                        ((NewPosFragment)fragmentAtPos0).listener = listener;
                    }
                    return fragmentAtPos0;
                case 1:
                    if (fragmentAtPos1 == null) {
                        fragmentAtPos1 = new ReviewPosFragment();
                        ((ReviewPosFragment)fragmentAtPos1).listener = listener;
                    }
                    return fragmentAtPos1;
                case 2:
                    return new MapsFragment();
            }
            return null;
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "NOVO";
                case 1:
                    return "REVER";
                case 2:
                    return "MAPA";
            }
            return null;
        }

        @Override
        public int getItemPosition(Object object) {
            if (object instanceof NewPosFragment && fragmentAtPos0 instanceof AddNewPosFragment) {
                return POSITION_NONE;
            }
            if (object instanceof AddNewPosFragment && fragmentAtPos0 instanceof NewPosFragment) {
                return POSITION_NONE;
            }
            if (object instanceof ReviewPosFragment && fragmentAtPos1 instanceof ListPosFragment) {
                return POSITION_NONE;
            }
            if (object instanceof ListPosFragment && fragmentAtPos1 instanceof ReviewPosFragment) {
                return POSITION_NONE;
            }
            return POSITION_UNCHANGED;
        }
    }

    public void setActionBarTitle(String title) {
        getSupportActionBar().setTitle(title);
    }

    @Override
    public void onBackPressed() {
        if (mSectionsPagerAdapter.getItem(0) instanceof AddNewPosFragment) {
            ((AddNewPosFragment)mSectionsPagerAdapter.getItem(0)).listener.onSwitchToNextFirstFragment(null);
            ((ReviewPosFragment)mSectionsPagerAdapter.getItem(1)).updateCalendar();

        } else if(mSectionsPagerAdapter.getItem(1) instanceof ListPosFragment){
            ((ListPosFragment)mSectionsPagerAdapter.getItem(1)).listener.onSwitchToNextSecondFragment(null);

        }else {
            super.onBackPressed();
        }
    }
}
