package ru.makproductions.androidlevel_1;

import android.content.Context;

class CitiesSpec {
     static String getColorDescription(Context context, int position){
         String[] descriptions = context.getResources().getStringArray(R.array.descriptions);
         return descriptions[position];
     }
}