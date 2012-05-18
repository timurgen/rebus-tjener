package student.hin.no;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class KlientStartActivity extends Activity {
	
	private int duration = Toast.LENGTH_LONG;
	private Toast toast;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Button buttonLogin = (Button)findViewById(R.id.buttonLogin);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent loginIntent = new Intent(KlientStartActivity.this, LoginActivity.class);
				startActivity(loginIntent);
			}
		});//end of buttonLogin.setOnClickListener
        
        Button buttonFreePlay = (Button)findViewById(R.id.buttonFreePlay);
        buttonFreePlay.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent gamesListIntent = new Intent(KlientStartActivity.this, GamesAllActivity.class);
				startActivityForResult(gamesListIntent, 1);
			}
		});//end of  buttonFreePlay.setOnClickListener
        
        ImageView fotka = (ImageView)findViewById(R.id.android_icon);
        fotka.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				
				Alarm alarmbleat = new Alarm();
				long timeToWakeUp = SystemClock.elapsedRealtime() + 10*1000;
		        alarmbleat.SetAlarm(getApplicationContext(), timeToWakeUp, true, false);
				KlientStartActivity.this.finish();
				return false;
			}
        });        
		toast = Toast.makeText(getApplicationContext(), "Click me to exit", duration);
		toast.setGravity(Gravity.BOTTOM|Gravity.RIGHT, 100, 0);
		toast.show();
        
    }//end of onCreate
    
    protected void onActivityResult(int requestCode, int resultCode, Intent data){ 
    	if (requestCode ==1){
    			toast = Toast.makeText(getApplicationContext(), "You can register or get pin on server!", duration);
    			toast.setGravity(Gravity.BOTTOM|Gravity.RIGHT, 100, 0);
    			toast.show();
    	}
    		
    }
}//end of KlientStartActivity