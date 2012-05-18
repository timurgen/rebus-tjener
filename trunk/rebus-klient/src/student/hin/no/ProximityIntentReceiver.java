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
		
		if(intent.getAction() == MapActivity.PROX_ALER_INTENT)
		{
			boolean entering = intent.getBooleanExtra(key, false);
			//går inn i angitt område
			if(entering)
			{
				Toast t = Toast.makeText(context, "You are in point", 5);
				t.show();
				Log.d("IN", "In Test");
			}
			//Går ut av angitt område
			else
			{
				Toast t = Toast.makeText(context, "Out o point", 5);
				t.show();
				Log.d("Exit", "Out Test");
			}
		}//end of if
		
	}//end of onReceive

}
