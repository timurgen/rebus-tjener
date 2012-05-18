package student.hin.no;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLEncoder;
import db.Game;

import android.content.Context;
import android.util.Log;

public class ConnectionHandler {
	//Variabler
	private String responseMsg = "";
	private String data, mSession = null;
	private FileHandler filehandler;
	public db.Game gameRebus;
	//End of variabler
	
	/**
	 * Funksjonen brukes for innlogging og for få spill-liste fra tjeneren
	 * returnerer feil hvis noe går galt med tilkobling mot tjeneren
	 */
	public String GetDataFromServlet(Context context, int details, String name, String pass){
		String myURL = "";
		URL url = null;
		HttpURLConnection httpConnection = null;
		InputStream in = null;
		filehandler = new FileHandler();
		mSession = filehandler.ReadLogs(1, context);
		try {
			/* details equals "0" to get url for log in
			 * details equals "1" to get url for gamelist
			 * details equals "2" to get url for log in guest
			 */
			switch (details){
				case 0:
					data = "android?" + URLEncoder.encode("name", "UTF-8") + "=" +URLEncoder.encode(name, "UTF-8");
					data += "&" + URLEncoder.encode("pass", "UTF-8") + "=" +URLEncoder.encode(pass, "UTF-8");
					break;
				case 1:
					data = "client?" + URLEncoder.encode("mode", "UTF-8") + "=" +URLEncoder.encode("gamelist", "UTF-8");
					//data += "&" + URLEncoder.encode("sid", "UTF-8") + "=" +URLEncoder.encode(mSession.substring(11), "UTF-8");
					break;
				case 2:
					data = "android?" + URLEncoder.encode("guestid", "UTF-8") + "=" +URLEncoder.encode(name, "UTF-8");
					break;
			}
			myURL = "http://158.39.124.96:8080/rebus/" + data;
			url = new URL(myURL);
			httpConnection = (HttpURLConnection)url.openConnection();
			httpConnection.setConnectTimeout(2000);
			// Sett cookie dersom påfølgende kall:
			if (mSession != null && details ==1){
				//httpConnection.setRequestProperty("cookie", mSession);
				}
			else if (details==0){
				httpConnection.addRequestProperty("name", name);
				httpConnection.addRequestProperty("pass", pass);
			}
			
			StringBuffer buf = new StringBuffer();
			int enByte;
			int responseCode = httpConnection.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) {
				in = httpConnection.getInputStream();
				//få sesjons id fra tjeneren og skrive dem i filen hvis vi logger inn, ellers går videre
				if (details !=1){
					mSession = httpConnection.getHeaderField("Set-cookie");
					if (mSession != null) {
						int semicolon = mSession.indexOf(';');
						mSession = mSession.substring(0, semicolon);
						filehandler.WriteLog(mSession, context);
					}
					responseMsg = "Authentification: success!";
				} //end (details ==0){
							//hvis vi vil få informasjon om tilgjengelige spill
				else{ 
					while ((enByte = in.read()) != -1){
						buf.append((char) enByte);
					}//Char til stringbuffer
					responseMsg=buf.toString();
				}
			} // end if (responseCode == HttpURLConnection.HTTP_OK) {
			else // hvis HTTP responseCode ikke er OK (200) 
			{
				responseMsg = "Error: HTTP status " + responseCode;
			}
		}
		catch (SocketTimeoutException e){
			e.printStackTrace();
			responseMsg="Error: server is unavailable";
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IllegalStateException e){
			e.printStackTrace();
			responseMsg="Error: cannot connect to server";
		}
		finally{
			try {
				in.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			catch (NullPointerException e){
				e.printStackTrace();
			}
			httpConnection.disconnect();
		}
		return responseMsg;
	}//end of public String GetDataFromServlet(..
	
	/**
	 * Brukes for å få tak i et enkelt spill-objekt,
	 * når man velger det i "GamesAllActivity"
	 */
	public db.Game getGameData(Context context, String gameIdToSend, String pinCode){
		String myURL = "";
		URL url = null;
		HttpURLConnection httpConnection = null;
		InputStream in = null;
		filehandler = new FileHandler();
		mSession = filehandler.ReadLogs(1, context);
		try {
			
			data = "client?" + URLEncoder.encode("mode", "UTF-8") + "=" +URLEncoder.encode("getgame", "UTF-8");
			data += "&" + URLEncoder.encode("gameid", "UTF-8") + "=" +URLEncoder.encode(gameIdToSend, "UTF-8");
			
			if (pinCode != null){
				data += "&" + URLEncoder.encode("guestid", "UTF-8") + "=" +URLEncoder.encode(pinCode, "UTF-8");
			}
			myURL = "http://158.39.124.96:8080/rebus/" + data;
			
			Log.d("URL", myURL);
			
			url = new URL(myURL);
			httpConnection = (HttpURLConnection)url.openConnection();
			
			if (mSession != null){		//hvis det ikke er gjest og session-id ikke er tom i filen
				// Sett cookie dersom påfølgende kall:
				httpConnection.setRequestProperty("cookie", mSession);
				}
			else{										//setter session id , hvis det er gjest eller session er tom
				mSession = httpConnection.getHeaderField("Set-cookie");
				if (mSession != null) {	//sjekker at set finnes property "Set-cookie"
					int semicolon = mSession.indexOf(';');
					mSession = mSession.substring(0, semicolon);
					filehandler.WriteLog(mSession, context);
				}
			}
			
			//StringBuffer buf = new StringBuffer();
			//int enByte;
			int responseCode = httpConnection.getResponseCode();
			
			if (responseCode == HttpURLConnection.HTTP_OK) 
			{
				
					
				in = httpConnection.getInputStream();
									
				ObjectInputStream ois = new ObjectInputStream(in);
					
				gameRebus = (Game) ois.readObject();

				ois.close();

			} // end if (responseCode == HttpURLConnection.HTTP_OK) {
			else // hvis HTTP responseCode ikke er OK (200) 
			{
				responseMsg = "Error: HTTP status " + responseCode;
			}
		}
		catch (SocketTimeoutException e){
			e.printStackTrace();
			responseMsg="Error: server is unavailable";
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IllegalStateException e){
			e.printStackTrace();
		}
		finally{
			try {
				in.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			catch (NullPointerException e){
				e.printStackTrace();
			}
			httpConnection.disconnect();
		}
		return gameRebus;
	}//end of getGameData
	
	
	/**
	 * Sender spil result til tjenester
	 */
	public void endGame(Context context, String gameid, String result, String quantity)
	{
		String myURL = "";
		URL url = null;
		HttpURLConnection httpConnection = null;
		filehandler = new FileHandler();
		mSession = filehandler.ReadLogs(1, context);
		try {
			data = "client?" + URLEncoder.encode("mode", "UTF-8") + "=" +URLEncoder.encode("sendresult", "UTF-8");
			data += "&" + URLEncoder.encode("gameid", "UTF-8") + "=" +URLEncoder.encode(gameid, "UTF-8");			
			data += "&" + URLEncoder.encode("result", "UTF-8") + "=" +URLEncoder.encode(result, "UTF-8");
			data += "&" + URLEncoder.encode("quantity", "UTF-8") + "=" +URLEncoder.encode(quantity, "UTF-8");
			
			myURL = "http://158.39.124.96:8080/rebus/" + data;
			
			Log.d("URL", myURL);
			
			url = new URL(myURL);
			httpConnection = (HttpURLConnection)url.openConnection();
			httpConnection.setConnectTimeout(2000);
			
			if (mSession != null){		//hvis det ikke er gjest og session-id ikke er tom i filen
				// Sett cookie dersom påfølgende kall:
				httpConnection.setRequestProperty("cookie", mSession);
				//httpConnection.connect();
				}
			
			Integer responseCode = httpConnection.getResponseCode();
			
			if (responseCode == HttpURLConnection.HTTP_OK) 
			{
				Log.d("URL", responseCode.toString());	
			}
			
		}//end of try
		catch (SocketTimeoutException e)
		{
			e.printStackTrace();
			//responseMsg="Error: server is unavailable";
		}//end of cach
		catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			httpConnection.disconnect();
		}//end of finally
	}//end of endGame
	
	/**
	 * Henter spill result fra tjenester
	 * @param context
	 * @param gameIdToSend - spill number
	 * @return Stiring som innholder spill resultater
	 */
	public String GetGameResults(Context context, String gameIdToSend)
	{
		String myURL = "";
		URL url = null;
		HttpURLConnection httpConnection = null;
		InputStream in = null;
		filehandler = new FileHandler();
		mSession = filehandler.ReadLogs(1, context);
		
		try 
		{
			data = "client?" + URLEncoder.encode("mode", "UTF-8") + "=" +URLEncoder.encode("getresult", "UTF-8");
			data += "&" + URLEncoder.encode("gameid", "UTF-8") + "=" +URLEncoder.encode(gameIdToSend, "UTF-8");
			
			myURL = "http://158.39.124.96:8080/rebus/" + data;
			
			Log.d("URL", myURL);
			
			url = new URL(myURL);
			//Coble mot tjenester
			httpConnection = (HttpURLConnection)url.openConnection();
			httpConnection.setConnectTimeout(2000);
			
			
			StringBuffer buf = new StringBuffer();
			int enByte;
			int responseCode = httpConnection.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) {
				in = httpConnection.getInputStream();
							
				while ((enByte = in.read()) != -1)
				{
					buf.append((char) enByte);
				}//Char til stringbuffer
					responseMsg=buf.toString();	
			} // end if (responseCode == HttpURLConnection.HTTP_OK) {
			else // hvis HTTP responseCode ikke er OK (200) 
			{
				responseMsg = "Error: HTTP status " + responseCode;
			}	
		}//end of try
		
		catch(Exception e)
		{
			
		}//end of catch
		
		finally
		{
			try 
			{
				in.close();
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
		}//end of finally
		
		return responseMsg;
	}//end of GetGameResults
	
	
}