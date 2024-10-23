package io.module.media.wallpaper.live;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;

import java.io.Serializable;

public class LiveWallpaperModel implements Serializable {

    private static final String SHARE_PREFERENCE_LIVE_WALLPAPER = "share_preference_live_wallpaper";

    private static final String KEY_LIVE_WALLPAPER_URI = "key_live_wallpaper_uri";

    private static LiveWallpaperModel INSTANCE;

    private SharedPreferences sharedPreferences;

    public static LiveWallpaperModel getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = new LiveWallpaperModel(context);
        }

        return INSTANCE;
    }

    public LiveWallpaperModel(Context context) {
        initSharedPreferences(context);
    }

    public void setUri(Uri uri) {
        putUriString(uri.toString());
    }

    public Uri getUri() {
        String uriString = sharedPreferences.getString(KEY_LIVE_WALLPAPER_URI, "");
        return Uri.parse(Uri.encode(uriString));
    }

    private void initSharedPreferences(Context context) {
        sharedPreferences = context.getSharedPreferences(SHARE_PREFERENCE_LIVE_WALLPAPER, Context.MODE_PRIVATE);
    }

    private void putUriString(String value) {
        sharedPreferences.edit()
                .putString(LiveWallpaperModel.KEY_LIVE_WALLPAPER_URI, value)
                .apply();
    }
}
