package cl.pingon.flash.data;

import android.content.Context;
import android.content.SharedPreferences;

public class PhotoPreference {

    private static final String GROUP_PHOTO = "cl.pingon.flash.data.KEY.GROUP_PHOTO";
    private static final String KEY_PHOTO = "cl.pingon.flash.data.KEY.KEY_PHOTO";
    private Context context;

    public PhotoPreference(Context context) {
        this.context = context;
    }

    public void photoSave(String somethingToSave) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(GROUP_PHOTO, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefEditor = sharedPreferences.edit();
        prefEditor.putString(KEY_PHOTO, somethingToSave);
        prefEditor.apply();
    }

    public String getPhoto() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(GROUP_PHOTO, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_PHOTO, null);
    }

}
