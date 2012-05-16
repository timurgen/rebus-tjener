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
				startActivity(gamesListIntent);
			}
		});//end of  buttonFreePlay.setOnClickListener
        
        ImageView fotka = (ImageView)findViewById(R.id.android_icon);
        fotka.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View arg0, MotionEvent arg1) {
				KlientStartActivity.this.finish();
				return false;
			}
        });
        
        int duration = Toast.LENGTH_LONG;
		Toast toast = Toast.makeText(getApplicationContext(), "Click me to exit", duration);
		toast.setGravity(Gravity.BOTTOM|Gravity.RIGHT, 100, 0);
		toast.show();
        
    }//end of onCreate
}//end of KlientStartActivity