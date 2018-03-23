package timer.fityfor.me.beginner.utils;

import android.util.Log;

/**
 * Created by Hovhannisyan.Karo on 25.02.2017.
 */

public class LogUtils {
    private static final String LOG = "HK_LOG";
    private static final boolean isDebug = true;

    public static void d(String tag, String message){
        if (isDebug)
            Log.d(LOG + " " + tag, message);
    }
}