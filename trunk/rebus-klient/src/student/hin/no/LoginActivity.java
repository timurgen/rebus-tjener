package student.hin.no;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLEncoder;
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
	private String name, pass, mSession = null;
	private EditText EditTextUserName, EditTextPassword;
	public String cookie = ""; 
	private Handler handler = new Handler(); //Brukes til å oppdatere GUI
	private TextView statusView;
	
	// Runnable som inneholder metoden som servlet-tråden starter:
	private Runnable bakgrunnsProssesering = new Runnable() {
		public void run() {
		kontaktServlet();
		}
	};
	
	private Runnable doUpdateGUI = new Runnable() {
		public void run() {
		updateGUI();
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
			Thread thread = new Thread(null, bakgrunnsProssesering, "logging inn");
			thread.start();
//			Intent gameListIntent = new Intent(LoginActivity.this, GamesAllActivity.class);
//			startActivity(gameListIntent);
			}	
		});//end of buttonSubmit.setOnClickListener
		statusView = (TextView) findViewById(R.id.statusview);
	}//end of onCreate
	
	private void kontaktServlet() {
		responseMsg="kontakter rebus-tjener ...";
		String myURL = "";
		handler.post(doUpdateGUI);
		try {
			URL url = null;
			HttpURLConnection httpConnection = null;
			String data = URLEncoder.encode("name", "UTF-8") + "=" +URLEncoder.encode(name, "UTF-8");
			data += "&" + URLEncoder.encode("pass", "UTF-8") + "=" +URLEncoder.encode(pass, "UTF-8");
			
			myURL = "http://158.39.124.96:8080/rebus/android?" + data;
			url = new URL(myURL);
			httpConnection = (HttpURLConnection)url.openConnection();
			httpConnection.setConnectTimeout(2000);
			if (httpConnection.getContentLength() > 0){
				// Sett cookie dersom påfølgende kall:
				if (mSession != null){
					httpConnection.setRequestProperty("cookie", mSession);}
				int contentLength = httpConnection.getContentLength();
				StringBuffer buf = new StringBuffer(contentLength);
				int enByte;
				int responseCode = httpConnection.getResponseCode();
				if (responseCode == HttpURLConnection.HTTP_OK) {
					InputStream in = httpConnection.getInputStream();
					// Leser ut cookie-stringen:
					cookie = httpConnection.getHeaderField("Set-cookie");
					if (cookie != null) {
						int semicolon = cookie.indexOf(';');
						mSession = cookie.substring(0, semicolon);
					}
					while ((enByte = in.read()) != -1){
						buf.append((char) enByte);
					}// Gjør om til char og legger til stringbuffret
					responseMsg = buf.toString();
					handler.post(doUpdateGUI);
					in.close();
				}else{
					responseMsg = "HTTP feil ...";
					handler.post(doUpdateGUI);
					} //går videre til 
			}else{
				responseMsg = "Tjeneren sover";
				handler.post(doUpdateGUI);
			}
			httpConnection.disconnect();
		} 
		catch (SocketTimeoutException e){
			e.printStackTrace();
			responseMsg="Tjeneren timeout";
			handler.post(doUpdateGUI);
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}//end of kontaktServlet()

	private void updateGUI() {
		statusView.setText(responseMsg);
		}
	
}//end of LoginActivity
