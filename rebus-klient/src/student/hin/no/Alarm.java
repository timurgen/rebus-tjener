package student.hin.no;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.PowerManager;
import android.os.SystemClock;
import android.widget.Toast;


/** Starter KlientStartActivity og viser en melding hvis game ble sluttet eller game skal starte snart */
public class Alarm extends BroadcastReceiver{
	
	//class variabler
	private PowerManager pm;	
	Context context;
	
	
	/** får intent som ble send med alarm og velger hvilken melding å vise*/
    @Override
    public void onReceive(Context context, Intent firedIntent) {
            
            this.context = context;
            pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
            PowerManager.WakeLock wl;
            boolean startgame = firedIntent.getBooleanExtra("startgame", true);
            wl = pm.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, "Alarm!");
    wl.acquire();
    
    Intent letsplay = new Intent(context, KlientStartActivity.class);
    letsplay.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    context.startActivity(letsplay);
            if (startgame){
                    String gamename = firedIntent.getStringExtra("gamename");
                    Toast.makeText(context, "Next game is about to start: " + gamename, Toast.LENGTH_LONG).show();
    }
            else{
                    Toast.makeText(context, "The game was finished!", Toast.LENGTH_LONG).show();
            }
    wl.release();
            
    }

    /** setter en alarm med viss tid og viss situasjon (game skal starte eller ble sluttet)*/
    public void SetAlarm(Context context, long time, boolean startgame, boolean finishgame, String nameOfFirstGame)
{
    AlarmManager am=(AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
    Intent i = new Intent(context, Alarm.class);
            if (startgame){
                    i.putExtra("startgame", true);
                    i.putExtra("gamename", nameOfFirstGame);
            }
            else{
                    i.putExtra("startgame", false);
            }
    PendingIntent pi = PendingIntent.getBroadcast(context, 0, i, 0);
    am.set(AlarmManager.RTC_WAKEUP, time, pi);
}
	
	public void CancelAlarm(Context context)
    {
        Intent intent = new Intent(context, Alarm.class);
        PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        alarmManager.cancel(sender);
    }
	
}
