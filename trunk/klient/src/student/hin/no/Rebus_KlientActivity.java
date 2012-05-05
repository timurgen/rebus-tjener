package student.hin.no;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Rebus_KlientActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    
    Button buttonLogin = (Button)findViewById(R.id.buttonLogin);
    buttonLogin.setOnClickListener(new View.OnClickListener() {
		
		public void onClick(View v) {
			//startActivity(new Intent("student.hin.no.LOGIN"));
			Intent loginIntent = new Intent(Rebus_KlientActivity.this, Login.class);
			startActivity(loginIntent);
		}
	});//end of buttonLogin
    
    Button buttonFreePlay = (Button)findViewById(R.id.buttonFreePlay);
    buttonFreePlay.setOnClickListener(new View.OnClickListener() {
		
		public void onClick(View v) {
			//startActivity(new Intent("student.hin.no.GAMELIST"));
			Intent gameListIntent = new Intent(Rebus_KlientActivity.this, gameList.class);
			startActivity(gameListIntent);
			
		}
	});
    
    }//end of onCreate
}