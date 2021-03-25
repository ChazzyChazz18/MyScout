package com.TuScout.SportsMedia.Utilities;

public class MainHelper {
    /* |||==> Singleton Pattern Related <==||| */
    private static MainHelper instance;

    private MainHelper (){}

    public static MainHelper getInstance () {

        if(instance == null)
            instance = new MainHelper();

        return instance;

    }
    /* End of Singleton Pattern Related */

    //
    public int settingsFrom;

    //
    public boolean isMainBackFromLogin;

}
