package student.hin.no;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class TimeReceiver extends BroadcastReceiver 
{

	@Override
	public void onReceive(Context context, Intent arg1) 
	{
		// TODO Auto-generated method stub
		Toast.makeText(context, "Alarm worked.", Toast.LENGTH_LONG).show();
		
		
	} 

}//end of TimeReceiver
