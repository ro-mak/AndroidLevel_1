package ru.makproductions.androidlevel_1;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.graphics.*;

public class ShowWeatherFragment extends Fragment {

    private String weather;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_show_weather, container, false);
        TextView showWeatherTextView = (TextView) view.findViewById(R.id.show_weather_textview);
        showWeatherTextView.setText(weather);

        Button shareWeatherButton = (Button) view.findViewById(R.id.share_weather_button);
        shareWeatherButton.setOnClickListener(onClickListener);
		UtilMethods.changeFontTextView(showWeatherTextView,getActivity());
		UtilMethods.changeFontTextView(shareWeatherButton,getActivity());
        return view;
    }

    public void setWeather(String weather){
        this.weather = weather;
    }


    private final View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.share_weather_button) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, weather);
//                PackageManager packageManager = getPackageManager();
//                if(!packageManager.queryIntentActivities(intent,0).isEmpty()) {
//                    startActivity(intent);
//                    setResult(RESULT_OK);
//                }else{
//                    setResult(RESULT_CANCELED);
//                }
//            }
            }
        }
    };
}
