package student.hin.no;

//import java.io.IOException;
//import java.io.InputStream;
//import java.net.HttpURLConnection;
//import java.net.SocketTimeoutException;
//import java.net.URL;
//import java.net.URLEncoder;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends Activity{

	private String responseMsg = "";
	private String name, pass; //mSession = null;
	private EditText EditTextUserName, EditTextPassword;
	public String cookie = ""; 
	private Handler handler = new Handler(); //Brukes til � oppdatere GUI
	private TextView statusView;
	private Thread thread = null;
	private ConnectionHandler connectionhandler;

	// Runnable som inneholder metoden som servlet-tr�den starter:
	private Runnable bakgrunnsProssesering = new Runnable() {
		public void run() {
		kontaktServlet();
		}
	};
	
	// Runnable som oppdaterer GUI
	private Runnable doUpdateGUI = new Runnable() {
		public void run() {
		updateGUI();
		}
		};
	
	//Runnable som lukker bakgrunstrad og starter GamesAllActivity
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
		statusView = (TextView) findViewById(R.id.statusview);
		
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
			else
				statusView.setText("fill out BOTH fields");
			}	
		});//end of buttonSubmit.setOnClickListener
	}//end of onCreate
	
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

	//skriver ut til brukeren statusmeldinger
	private void updateGUI() {
		statusView.setText(responseMsg);
		}
}//end of LoginActivity
