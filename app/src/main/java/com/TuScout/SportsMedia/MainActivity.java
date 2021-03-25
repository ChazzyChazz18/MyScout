package com.TuScout.SportsMedia;

import android.content.Intent;
import android.os.Build;
import android.os.Handler;

import com.TuScout.SportsMedia.Fragments.AddVideoFragment;
import com.TuScout.SportsMedia.Fragments.BillingFragment;
import com.TuScout.SportsMedia.Fragments.ConfigFragment;
import com.TuScout.SportsMedia.Utilities.MainHelper;
import com.TuScout.SportsMedia.Utilities.Utility;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.TuScout.SportsMedia.Fragments.HomeFragment;
import com.TuScout.SportsMedia.Fragments.ProfileFragment;
import com.TuScout.SportsMedia.Fragments.ScoutFragment;
import com.TuScout.SportsMedia.Utilities.Constants;

public class MainActivity extends AppCompatActivity {

    FragmentManager fragmentManager = getSupportFragmentManager();

    Fragment homeFragment = new HomeFragment();
    Fragment scoutFragment = new ScoutFragment();
    Fragment profileFragment = new ProfileFragment();

    //Profile sub-fragments
    Fragment configFragment = new ConfigFragment();
    Fragment billingFragment = new BillingFragment();

    //My videos sub-fragment
    Fragment addVideoFragment = new AddVideoFragment();

    Fragment activeFragment = homeFragment;

    boolean doubleBackToExitPressedOnce = false;

    BottomNavigationView bottomNavigationView;

    public Fragment getProfileFragment (){return  profileFragment;}
    public Fragment getCongfigFragment (){return  configFragment;}
    public Fragment getBillingFragment (){return  billingFragment;}
    public Fragment getAddVideoFragment (){return  addVideoFragment;}
    public Fragment getScoutFragment (){return  scoutFragment;}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Utility.changeStatusBar(this, R.color.colorPrimary);

        bottomNavigationView = findViewById(R.id.bottom_nav);

        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);

        initializeFragments();

        MainHelper.getInstance().isMainBackFromLogin = false;
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener = menuItem -> {

        switch (menuItem.getItemId()){
            case R.id.nav_home:
                showSelectedFragment(homeFragment);
                break;
            case R.id.nav_star:
                if(SessionManager.isUserLogged) {
                    showSelectedFragment(scoutFragment);
                }else {
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                }
                break;
            case R.id.nav_person:
                if(SessionManager.isUserLogged) {
                    showSelectedFragment(profileFragment);
                } else {
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                }
                break;
        }

        return true;
    };

    private void initializeFragments () {
        //We dont hide the topFragment as it is the one selected(we choose it)
        fragmentManager.beginTransaction().add(R.id.fragment_container, homeFragment, "1").commit();

        fragmentManager.beginTransaction().add(R.id.fragment_container, scoutFragment, "2").hide(scoutFragment).commit();
        fragmentManager.beginTransaction().add(R.id.fragment_container, profileFragment, "3").hide(profileFragment).commit();
        fragmentManager.beginTransaction().add(R.id.fragment_container, configFragment, "4").hide(configFragment).commit();
        fragmentManager.beginTransaction().add(R.id.fragment_container, billingFragment, "5").hide(billingFragment).commit();
        fragmentManager.beginTransaction().add(R.id.fragment_container, addVideoFragment, "6").hide(addVideoFragment).commit();
    }

    public void showSelectedFragment (Fragment fragmentToShow) {

        fragmentManager.beginTransaction()
                .hide(activeFragment)
                .show(fragmentToShow)
                .commit();

        activeFragment = fragmentToShow;

        /*if(activeFragment != homeFragment){

        }*/
    }

    private void closeApp () {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //Close the app even on android tabs/tasks
            finishAndRemoveTask();
        }else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN){
            this.finishAffinity();
        }else {
            //Close the app but it is on android tabs/tasks.
            android.os.Process.killProcess(android.os.Process.myPid());
        }
    }

    @Override
    public void onBackPressed() {

        AddVideoFragment fragment = (AddVideoFragment) getSupportFragmentManager().findFragmentByTag("6");

        if(fragment != null) {
            if (!fragment.isUploadVideoFragmentActive()) {
                //If we are not on the home fragment and press back we go back to it sequentially
                if (activeFragment == configFragment) {
                    if(MainHelper.getInstance().settingsFrom == Constants.SETTINGS_FROM_PROFILE_FRAGMENT)
                        showSelectedFragment(profileFragment);
                    else if(MainHelper.getInstance().settingsFrom == Constants.SETTINGS_FROM_TUSCOUT_FRAGMENT)
                        showSelectedFragment(scoutFragment);
                } else if (activeFragment == billingFragment) {
                    showSelectedFragment(configFragment);
                } else if (activeFragment == addVideoFragment) {
                    showSelectedFragment(scoutFragment);
                } else if (activeFragment != homeFragment) {
                    showSelectedFragment(homeFragment);
                    bottomNavigationView.setSelectedItemId(R.id.nav_home);
                } else {
                    //Here we set the logic to double tap "back" btn to close the app
                    if (doubleBackToExitPressedOnce) {
                        closeApp();
                    }

                    this.doubleBackToExitPressedOnce = true;
                    Toast.makeText(this, this.getString(R.string.tap_2_times_to_exit),
                            Toast.LENGTH_SHORT).show();

                    //A kind of timer from java classes
                    new Handler().postDelayed(() -> doubleBackToExitPressedOnce = false, Constants.DOUBLE_BACK_PRESS_TIME_TO_EXIT);
                }
            }else {
                fragment.showSelectedFragment(fragment.getLibraryFragment());
            }
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(!SessionManager.isUserLogged){
            showSelectedFragment(homeFragment);
            bottomNavigationView.setSelectedItemId(R.id.nav_home);
        }else {
            ProfileFragment fragment = (ProfileFragment) getSupportFragmentManager().findFragmentByTag("3");

            if(fragment != null)
                fragment.setUserInfo();
        }

        if(MainHelper.getInstance().isMainBackFromLogin){

            if(SessionManager.isUserLogged){

                ScoutFragment fragment = (ScoutFragment) getSupportFragmentManager().findFragmentByTag("2");
                if(fragment != null)
                    fragment.setProfileImage();

                ProfileFragment fragment2 = (ProfileFragment) getSupportFragmentManager().findFragmentByTag("3");
                if(fragment2 != null)
                    fragment2.setProfileImage();

            }

            bottomNavigationView.setSelectedItemId(R.id.nav_home);
            MainHelper.getInstance().isMainBackFromLogin = false;

        }

    }

    @Override
    protected void onPause() {
        super.onPause();
    }
}
