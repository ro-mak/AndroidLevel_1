package ru.makproductions.androidlevel_1;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class WeatherListFragment extends Fragment {
    private static final String WEATHER_MESSAGE = "weather_message";
    private static final String TOWN_NUMBER = "townNumber";
    private static final String TAG = "HeyHOO###############";
    private static final String PRESSURE = "PRESSURE";
    private static final String TOMMOROW_FORECAST = "TOMMOROW_FORECAST";
    private static final String WEEK_FORECAST = "WEEK_FORECAST";
    private TextView descriptionText;
    private Button showDescriptionButton;
    private SharedPreferences saveTown;
    private int townSelected;
    private final int SUCCESS_CODE = 666;
    private final static int VERTICAL = 1;
    private String result = "";
    private boolean pressure;
    private boolean tommorowForecast;
    private boolean weekForecast;

    private WeatherListListener weatherListListener;

    @Override
    public void onAttach(Context context) {
        weatherListListener = (WeatherListListener) context;
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.weather_list_fragment, container, false);
        RecyclerView weatherRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(VERTICAL);
        weatherRecyclerView.setLayoutManager(layoutManager);
        weatherRecyclerView.setAdapter(new RVAdapter(Arrays.asList(getResources().getStringArray(R.array.cities))));
        saveTown = getActivity().getPreferences(MODE_PRIVATE);

        descriptionText = (TextView) rootView.findViewById(R.id.textview_description);
        CheckBox checkBoxPressure = (CheckBox) rootView.findViewById(R.id.checkbox_pressure);
        CheckBox checkBoxTommorowForecast = (CheckBox) rootView.findViewById(R.id.checkbox_tommorow_forecast);
        CheckBox checkBoxWeekForecast = (CheckBox) rootView.findViewById(R.id.checkbox_week_forecast);
        if (savedInstanceState != null) {
            townSelected = savedInstanceState.getInt(TOWN_NUMBER);
            descriptionText.setText(savedInstanceState.getString(WEATHER_MESSAGE));
        } else {
            townSelected = saveTown.getInt(TOWN_NUMBER, 0);
            pressure = saveTown.getBoolean(PRESSURE, false);
            checkBoxPressure.setChecked(pressure);
            tommorowForecast = saveTown.getBoolean(TOMMOROW_FORECAST, false);
            checkBoxTommorowForecast.setChecked(tommorowForecast);
            weekForecast = saveTown.getBoolean(WEEK_FORECAST, false);
            checkBoxWeekForecast.setChecked(weekForecast);
        }
        showDescriptionButton = (Button) rootView.findViewById(R.id.show_description_button);
        weatherRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        weatherRecyclerView.setHasFixedSize(true);
        checkBoxPressure.setOnClickListener(onClickListener);
        checkBoxTommorowForecast.setOnClickListener(onClickListener);
        checkBoxWeekForecast.setOnClickListener(onClickListener);
        showDescriptionButton.setOnClickListener(onClickListener);
        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(TOWN_NUMBER, townSelected);
        outState.putString(WEATHER_MESSAGE, descriptionText.getText().toString());
    }

    private class RVAdapter extends RecyclerView.Adapter<RVAdapter.MyViewHolder> {
        List<String> cities;

        RVAdapter(List<String> cities) {
            this.cities = cities;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            MyViewHolder myViewHolder = new MyViewHolder(inflater,parent);
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

        class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            private TextView city;

            MyViewHolder(LayoutInflater inflater,ViewGroup parent) {
                super(inflater.inflate(R.layout.category_list_item,parent,false));
                city = (TextView) itemView.findViewById(R.id.city);
                itemView.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
                townSelected = getAdapterPosition();
                result = CitiesSpec.getWeatherDescription(getActivity(), townSelected, pressure, tommorowForecast, weekForecast);
                descriptionText.setText(result);
//                     startActivityForResult(intent, SUCCESS_CODE);
                ShowWeatherFragment showWeatherFragment = new ShowWeatherFragment();
                showWeatherFragment.setWeather(result);
                android.support.v4.app.FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, showWeatherFragment);
                transaction.commit();
            }
        }
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.show_description_button) {
                result = CitiesSpec.getWeatherDescription(getActivity(), townSelected, pressure, tommorowForecast, weekForecast);
                descriptionText.setText(result);
            }
            ShowWeatherFragment showWeatherFragment = new ShowWeatherFragment();
            showWeatherFragment.setWeather(result);
            android.support.v4.app.FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, showWeatherFragment);
            transaction.commit();
            if (view.getId() == R.id.checkbox_pressure) {
                pressure = !pressure;
            } else if (view.getId() == R.id.checkbox_tommorow_forecast) {
                tommorowForecast = !tommorowForecast;
            } else if (view.getId() == R.id.checkbox_week_forecast) {
                weekForecast = !weekForecast;
            }
        }
    };
}