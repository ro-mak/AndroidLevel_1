package ru.makproductions.androidlevel_1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    private static final String WEATHER_MESSAGE = "weather_message";
    private static final String TOWN_NUMBER = "townNumber";
    private static final String TAG = "HeyHOO###############";
    private TextView descriptionText;
    private Button showDescriptionButton;
    private Spinner spinnerForCities;
    private SharedPreferences saveTown;
    private int townSelected;
    private final int SUCCESS_CODE = 666;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);

        saveTown = getPreferences(MODE_PRIVATE);


        setContentView(R.layout.activity_main);

        descriptionText = (TextView) findViewById(R.id.textview_description);
        if (savedInstanceState != null) {
            townSelected = savedInstanceState.getInt(TOWN_NUMBER);
            descriptionText.setText(savedInstanceState.getString(WEATHER_MESSAGE));
        } else {
            townSelected = saveTown.getInt(TOWN_NUMBER, 0);
        }
        showDescriptionButton = (Button) findViewById(R.id.show_description_button);
        spinnerForCities = (Spinner) findViewById(R.id.spinner_colours);
        spinnerForCities.setSelection(townSelected);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.geekbrains);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        showDescriptionButton.setOnClickListener(onClickListener);
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
        saveTown.edit().putInt(TOWN_NUMBER, spinnerForCities.getSelectedItemPosition()).apply();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Log.d(TAG, "OnSaveInstanceState");
        outState.putInt(TOWN_NUMBER, spinnerForCities.getSelectedItemPosition());
        outState.putString(WEATHER_MESSAGE, descriptionText.getText().toString());
        super.onSaveInstanceState(outState);
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.show_description_button) {
                String result = CitiesSpec.getWeatherDescription(MainActivity.this, spinnerForCities.getSelectedItemPosition());
                descriptionText.setText(result);
                Intent intent = new Intent(MainActivity.this, ShowWeather.class);
                intent.putExtra(WEATHER_MESSAGE, result);
                startActivityForResult(intent, SUCCESS_CODE);
            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == SUCCESS_CODE) {
            if (resultCode == RESULT_OK) {
                descriptionText.setText(R.string.weather_share_result_ok);
            } else if (resultCode == RESULT_CANCELED) {
                descriptionText.setText(R.string.weather_share_cancel);
            }
        }
    }
}
