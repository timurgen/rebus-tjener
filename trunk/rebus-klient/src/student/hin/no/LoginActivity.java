package student.hin.no;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LoginActivity extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);

	Button buttonSubmit = (Button)findViewById(R.id.buttonSubmit);
	buttonSubmit.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			Intent gameListIntent = new Intent(LoginActivity.this, GamesAllActivity.class);
			startActivity(gameListIntent);
		}
	});//end of buttonSubmit.setOnClickListener
	
	}//end of onCreate
	
	
}//end of LoginActivity
