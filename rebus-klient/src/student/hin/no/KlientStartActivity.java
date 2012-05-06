package student.hin.no;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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
        
        
    }//end of onCreate
}//end of KlientStartActivity