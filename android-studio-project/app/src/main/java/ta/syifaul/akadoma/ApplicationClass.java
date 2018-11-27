package ta.syifaul.akadoma;

import android.app.Application;

import com.onesignal.OneSignal;

/**
 * Created by user on 29/08/18.
 */

public class ApplicationClass extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        // OneSignal Initialization
        OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();
    }
}
