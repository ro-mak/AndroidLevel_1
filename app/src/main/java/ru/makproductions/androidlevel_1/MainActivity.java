package ru.makproductions.androidlevel_1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private static final String WEATHER_MESSAGE = "weather_message";
    private static final String TOWN_NUMBER = "townNumber";
    private static final String TAG = "HeyHOO###############";
    private static final String PRESSURE = "PRESSURE";
    private static final String TOMMOROW_FORECAST = "TOMMOROW_FORECAST";
    private static final String WEEK_FORECAST = "WEEK_FORECAST";
    private TextView descriptionText;
    private Button showDescriptionButton;
   // private Spinner spinnerForCities;
    private RecyclerView recyclerView;
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
        CheckBox checkBoxPressure = (CheckBox) findViewById(R.id.checkbox_pressure);
        CheckBox checkBoxTommorowForecast = (CheckBox) findViewById(R.id.checkbox_tommorow_forecast);
        CheckBox checkBoxWeekForecast = (CheckBox) findViewById(R.id.checkbox_week_forecast);

        if (savedInstanceState != null) {
            townSelected = savedInstanceState.getInt(TOWN_NUMBER);
            descriptionText.setText(savedInstanceState.getString(WEATHER_MESSAGE));
        } else {
            townSelected = saveTown.getInt(TOWN_NUMBER, 0);
            pressure = saveTown.getBoolean(PRESSURE,false);
            checkBoxPressure.setChecked(pressure);
            tommorowForecast = saveTown.getBoolean(TOMMOROW_FORECAST,false);
            checkBoxTommorowForecast.setChecked(tommorowForecast);
            weekForecast = saveTown.getBoolean(WEEK_FORECAST,false);
            checkBoxWeekForecast.setChecked(weekForecast);
        }
        showDescriptionButton = (Button) findViewById(R.id.show_description_button);
        //spinnerForCities = (Spinner) findViewById(R.id.spinner_colours);
        recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        RVAdapter adapter = new RVAdapter(Arrays.asList(getResources().getStringArray(R.array.cities)));
        recyclerView.setAdapter(adapter);


       // spinnerForCities.setSelection(townSelected);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.geekbrains);
        getSupportActionBar().setDisplayUseLogoEnabled(true);



        checkBoxPressure.setOnClickListener(onClickListener);
        checkBoxTommorowForecast.setOnClickListener(onClickListener);
        checkBoxWeekForecast.setOnClickListener(onClickListener);
        showDescriptionButton.setOnClickListener(onClickListener);
    }

         class RVAdapter extends RecyclerView.Adapter<RVAdapter.MyViewHolder>{
                List<String> cities;
                RVAdapter(List<String> cities){
                    this.cities = cities;
                }
             @Override
             public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                    View view = LayoutInflater.from(parent.getContext())
                            .inflate(R.layout.category_list_item,parent,false);
                    MyViewHolder myViewHolder = new MyViewHolder(view);
                    view.setOnClickListener(myViewHolder);
                 return myViewHolder;
             }

             @Override
             public void onBindViewHolder(MyViewHolder holder, int position) {
                    holder.city.setText(cities.get(position));
             }

             @Override
             public int getItemCount() {
                 return cities.size();
             }

             public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
                 TextView city;
            MyViewHolder(View itemView){
                super(itemView);
                city = (TextView)itemView.findViewById(R.id.city);
            }

                 @Override
                 public void onClick(View v) {
                     townSelected = getAdapterPosition();
                     result = CitiesSpec.getWeatherDescription(MainActivity.this,townSelected, pressure, tommorowForecast, weekForecast);
                     descriptionText.setText(result);
                     Intent intent = new Intent(MainActivity.this, ShowWeather.class);
                     intent.putExtra(WEATHER_MESSAGE, result);
                     startActivityForResult(intent, SUCCESS_CODE);
                 }
             }
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
        saveTown.edit()
                .putInt(TOWN_NUMBER, townSelected)
                .putBoolean(PRESSURE,pressure)
                .putBoolean(TOMMOROW_FORECAST,tommorowForecast)
                .putBoolean(WEEK_FORECAST,weekForecast)
                .apply();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Log.d(TAG, "OnSaveInstanceState");
        outState.putInt(TOWN_NUMBER, townSelected);
        outState.putString(WEATHER_MESSAGE, descriptionText.getText().toString());
        super.onSaveInstanceState(outState);
    }

    private String result = "";
    private boolean pressure;
    private boolean tommorowForecast;
    private boolean weekForecast;
    // region ClickListener
    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.show_description_button) {
                result = CitiesSpec.getWeatherDescription(MainActivity.this, townSelected, pressure, tommorowForecast, weekForecast);
                descriptionText.setText(result);
                Intent intent = new Intent(MainActivity.this, ShowWeather.class);
                intent.putExtra(WEATHER_MESSAGE, result);
                startActivityForResult(intent, SUCCESS_CODE);
            }
            if (view.getId() == R.id.checkbox_pressure) {
                pressure = !pressure;
            } else if (view.getId() == R.id.checkbox_tommorow_forecast) {
                tommorowForecast = !tommorowForecast;
            } else if (view.getId() == R.id.checkbox_week_forecast) {
                weekForecast = !weekForecast;
            }
        }
    };
//endregion

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
