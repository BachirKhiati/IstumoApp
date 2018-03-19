package firenoid.com.istumo3;

/**
 * Created by Dev on 12/17/2017.
 */

import android.app.Application;
import android.content.Context;

import firenoid.com.istumo3.LocaleHelper;

public class MainApplication extends Application {
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base, "en"));
    }
}