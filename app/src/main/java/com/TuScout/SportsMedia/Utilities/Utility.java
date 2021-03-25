package com.TuScout.SportsMedia.Utilities;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.view.WindowManager;

import androidx.core.content.ContextCompat;

public class Utility {

    //Method to change the color of the status bar
    public static void changeStatusBar(Activity activity, int color) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            activity.getWindow().setStatusBarColor(darkenColor(ContextCompat.getColor(activity, color)));
        }

    }

    // Code to darken the color supplied (mostly color of toolbar)
    private static int darkenColor(int color) {
        float[] hsv = new float[3];
        Color.colorToHSV(color, hsv);
        hsv[2] *= 0.8f;
        return Color.HSVToColor(hsv);
    }

    //Method to disable the status bar
    public static void hideStatusBar (Activity activity) {
        activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

}
