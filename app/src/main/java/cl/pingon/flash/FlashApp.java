package cl.pingon.flash;

import android.app.Application;

public class FlashApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        new Offline().setPersistance();
    }
}
