package student.hin.no;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class KlientStartActivity extends Activity {

	//Variabler
	private int duration = Toast.LENGTH_LONG;
	private Toast toast;
	private long timeToStartTheFirstGame= Long.MAX_VALUE;
	Alarm alarmbleat = new Alarm();

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
				
				FileHandler fileh = new FileHandler();
		    	String EntryformLogs = fileh.ReadTimes(getApplicationContext());
		    	if (EntryformLogs.length() > 13){
			    	int semicolon = EntryformLogs.indexOf(';');
			    	String time = EntryformLogs.substring(0, semicolon);
			    	timeToStartTheFirstGame = Long.parseLong(time);
			    	EntryformLogs = EntryformLogs.substring(semicolon+1);
					if (String.valueOf(timeToStartTheFirstGame) != null){
						alarmbleat.SetAlarm(getApplicationContext(), timeToStartTheFirstGame, true, false, EntryformLogs);
					}
				}
				
				KlientStartActivity.this.finish();
				return false;
			}
        });        
		toast = Toast.makeText(getApplicationContext(), "Click me to exit", duration);
		toast.setGravity(Gravity.BOTTOM|Gravity.RIGHT, 100, 0);
		toast.show();
    }//end of onCreate
    
    /** Kalles hvis man kom tilbake fra GameAllActivity og den viser en toast for de som kanskje ikke har registrert seg */
    protected void onActivityResult(int requestCode, int resultCode, Intent data){ 
    	if (requestCode ==1){
    			toast = Toast.makeText(getApplicationContext(), "You can register or get pin on server!", duration);
    			toast.setGravity(Gravity.BOTTOM|Gravity.RIGHT, 100, 0);
    			toast.show();
    	}
    } // end of onActivityResult 
}//end of KlientStartActivity