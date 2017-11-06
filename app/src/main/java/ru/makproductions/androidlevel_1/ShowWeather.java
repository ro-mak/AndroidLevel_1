package ru.makproductions.androidlevel_1;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ShowWeather extends AppCompatActivity {

    private static final String WEATHER_MESSAGE = "weather_message";
    private String weather;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_weather);
        TextView showWeatherTextView = (TextView)findViewById(R.id.show_weather_textview);
        Intent intent = getIntent();
        if(intent != null){
            weather = intent.getStringExtra(WEATHER_MESSAGE);
            showWeatherTextView.setText(weather);
        }
        Button shareWeatherButton = (Button)findViewById(R.id.share_weather_button);
        shareWeatherButton.setOnClickListener(onClickListener);
    }



    private final View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if(view.getId() == R.id.share_weather_button) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_TEXT, weather);
                PackageManager packageManager = getPackageManager();
                if(!packageManager.queryIntentActivities(intent,0).isEmpty()) {
                    startActivity(intent);
                    setResult(RESULT_OK);
                }else{
                    setResult(RESULT_CANCELED);
                }
            }
        }
    };

}
