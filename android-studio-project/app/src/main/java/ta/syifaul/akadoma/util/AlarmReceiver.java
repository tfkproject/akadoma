package ta.syifaul.akadoma.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by user on 31/08/18.
 */

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        //suara
        context.startService(new Intent(context, AlarmNotificationService.class));

    }
}
