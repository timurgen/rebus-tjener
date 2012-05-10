package student.hin.no;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.util.Log;
import android.widget.Toast;

public class ProximityIntentReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub

		String key = LocationManager.KEY_PROXIMITY_ENTERING;
		
		if(intent.getAction() == MapActivity.TREASURE_PROXIMITY_ALERT)
		{
			boolean entering = intent.getBooleanExtra(key, false);
			//g�r inn i angitt omr�de
			if(entering)
			{
				Toast t = Toast.makeText(context, "In", 5);
				t.show();
				Log.d("IN", "TEST");
			}
			//G�r ut av angitt omr�de
			else
			{
				Toast t = Toast.makeText(context, "Out", 5);
				t.show();
				Log.d("Exit", "TAST");
			}
		}//end of if
		
	}//end of onReceive

}
