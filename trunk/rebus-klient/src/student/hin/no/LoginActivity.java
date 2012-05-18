package student.hin.no;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity{

	//Variabler
	private String responseMsg = "";
	private String name, pass; //mSession = null;
	private EditText EditTextUserName, EditTextPassword;
	public String cookie = ""; 
	private Handler handler = new Handler(); //Brukes til å oppdatere GUI
	private Thread thread = null;
	private ConnectionHandler connectionhandler;
	private Toast toast;
	private int duration = Toast.LENGTH_SHORT;;
	//End of variabler
	
	/**
	 * Runnable kaller metoden som startes i bakgrunnstråden
	 */
	private Runnable bakgrunnsProssesering = new Runnable() {
		public void run() {
		kontaktServlet();
		}
	};
	
	/**
	 * Runnable som oppdaterer GUI
	 */
	private Runnable doUpdateGUI = new Runnable() {
		public void run() {
		updateGUI();
		}
		};
	
	/**
	* Runnable som lukker bakgrunstrad og starter GamesAllActivity
	*/
	private Runnable startGameList = new Runnable() {
		public void run() {
			thread.interrupt();
			Intent gameListIntent = new Intent(LoginActivity.this, GamesAllActivity.class);
			startActivity(gameListIntent);
		}
		};	
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		
		EditTextUserName=(EditText)findViewById(R.id.editTextUserName);
		EditTextUserName.addTextChangedListener(new TextWatcher(){
			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {}
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {}
			@Override
			public void afterTextChanged(Editable s) {
				name=EditTextUserName.getText().toString();
			}
	    }); 
		
		EditTextPassword=(EditText)findViewById(R.id.editTextPassword);
		EditTextPassword.addTextChangedListener(new TextWatcher(){
			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {}
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {}
			@Override
			public void afterTextChanged(Editable s) {
				pass=EditTextPassword.getText().toString();
			}
	    }); 
		
		Button buttonSubmit = (Button)findViewById(R.id.buttonSubmit);
		buttonSubmit.setOnClickListener(new View.OnClickListener() {
		
		@Override
		public void onClick(View v) {
			if ((name != null) && (pass != null)){
				thread = new Thread(null, bakgrunnsProssesering, "logging inn");
				thread.start();
			}
			else{
				toast = Toast.makeText(getApplicationContext(), "fill out BOTH fields!", duration);
				toast.setGravity(Gravity.BOTTOM|Gravity.RIGHT, 100, 0);
				toast.show();
			}
		}	
		});//end of buttonSubmit.setOnClickListener
		
		ImageView fotka = (ImageView)findViewById(R.id.android_icon);
        fotka.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				LoginActivity.this.finish();
				return false;
			}
        });
		
	}//end of onCreate
	
	/**
	 * Funksjonen brukes for innlogging på systemet
	 * og starting "GamesAllActivity" hvis innlogging er vellykket
	 * Viser feil til brukeren hvis noe går galt
	 */
	private void kontaktServlet() {
		responseMsg="Contacting server ...";
		handler.post(doUpdateGUI);
		connectionhandler = new ConnectionHandler();
		responseMsg = connectionhandler.GetDataFromServlet(getApplicationContext(), 0, name, pass);
		if (!responseMsg.contains("Error")){
			handler.post(doUpdateGUI);
			handler.post(startGameList);
		}
		else{
			handler.post(doUpdateGUI);
		}
	}//end of kontaktServlet()

	/**
	 * updateGUI skriver ut til brukeren statusmeldinger
	 */
	private void updateGUI() {
		if (responseMsg.contains("403")){
			responseMsg = "Error: wrong username or pass";
		}
		toast = Toast.makeText(getApplicationContext(), responseMsg, duration);
		toast.setGravity(Gravity.BOTTOM|Gravity.RIGHT, 100, 0);
		toast.show();
		}
}//end of LoginActivity
