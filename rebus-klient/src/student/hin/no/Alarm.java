package student.hin.no;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.os.SystemClock;
import android.widget.Toast;

public class Alarm extends BroadcastReceiver{

	Context context;
	@Override
	public void onReceive(Context context, Intent arg1) {
		this.context = context;
		PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
        PowerManager.WakeLock wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "Its time to play");
        wl.acquire();

        Intent letsplay = new Intent(context, KlientStartActivity.class);
        letsplay.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(letsplay);
        Toast.makeText(context, "A game is about to start!", Toast.LENGTH_LONG).show();
        wl.release();
		
	}

	public void SetAlarm(Context context)
    {
        AlarmManager am=(AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent(context, Alarm.class);
        PendingIntent pi = PendingIntent.getBroadcast(context, 0, i, 0);
        long timeToWakeUp = SystemClock.elapsedRealtime() + 10*1000;
        am.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, timeToWakeUp, pi);
    }
	
	public void CancelAlarm(Context context)
    {
        Intent intent = new Intent(context, Alarm.class);
        PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(sender);
    }
}
