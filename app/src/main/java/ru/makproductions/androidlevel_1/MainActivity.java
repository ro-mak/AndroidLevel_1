package ru.makproductions.androidlevel_1;

import android.content.*;
import android.os.*;
import android.support.v7.app.*;
import android.util.*;
import android.text.*;
import android.text.style.*;



public class MainActivity extends AppCompatActivity implements WeatherListListener {

    private static final String TAG = "HeyHOO###############";
    private final int SUCCESS_CODE = 666;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
		ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setLogo(R.drawable.geekbrains);
        actionBar.setDisplayUseLogoEnabled(true);
		
    }

    @Override
    protected void onStart() {
        Log.d(TAG, "onStart");
        super.onStart();
    }

    @Override
    protected void onStop() {
        Log.d(TAG, "onStop");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy");
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        Log.d(TAG, "onResume");
        super.onResume();
    }

    @Override
    protected void onRestart() {
        Log.d(TAG, "onRestart");
        super.onRestart();
    }

    @Override
    protected void onPause() {
        Log.d(TAG, "onPause");
        super.onPause();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SUCCESS_CODE) {
            if (resultCode == RESULT_OK) {
            } else if (resultCode == RESULT_CANCELED) {
            }
        }
    }

    @Override
    public void onListItemClick(String result) {
		ShowWeatherFragment showWeatherFragment = new ShowWeatherFragment();
		showWeatherFragment.setWeather(result);
		android.support.v4.app.FragmentTransaction transaction = this.getSupportFragmentManager().beginTransaction();
		transaction.replace(R.id.fragment_container, showWeatherFragment);
		transaction.addToBackStack(null);
		transaction.commit();
    }
}
