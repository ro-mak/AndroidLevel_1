package ru.makproductions.androidlevel_1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    private static final String WEATHER_MESSAGE = "weather_message";
    private static final String TOWN_NUMBER = "townNumber";
    private TextView descriptionText;
    private Button showDescriptionButton;
    private Spinner spinnerForCities;
    private SharedPreferences saveTown;
    private int townSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        saveTown = getPreferences(MODE_PRIVATE);
        townSelected = saveTown.getInt(TOWN_NUMBER, 0);

        setContentView(R.layout.activity_main);

        descriptionText = (TextView) findViewById(R.id.textview_description);
        showDescriptionButton = (Button) findViewById(R.id.show_description_button);
        spinnerForCities = (Spinner) findViewById(R.id.spinner_colours);
        spinnerForCities.setSelection(townSelected);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.geekbrains);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        showDescriptionButton.setOnClickListener(onClickListener);
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveTown.edit().putInt(TOWN_NUMBER, spinnerForCities.getSelectedItemPosition()).apply();
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.show_description_button) {
                String result = CitiesSpec.getWeatherDescription(MainActivity.this, spinnerForCities.getSelectedItemPosition());
                descriptionText.setText(result);
                Intent intent = new Intent(MainActivity.this, ShowWeather.class);
                intent.putExtra(WEATHER_MESSAGE, result);
                startActivity(intent);
            }
        }
    };
}
