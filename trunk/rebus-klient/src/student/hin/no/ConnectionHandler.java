package student.hin.no;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLEncoder;

import android.content.Context;

public class ConnectionHandler {
	private String responseMsg = "";
	private String data, mSession = null;
	private FileHandler filehandler;
	public GameRebus gameRebus;
	
	/*
	 * Funksjonen brukes for innlogging og for f� spill-liste fra tjeneren
	 * returnerer feil hvis noe g�r galt med tilkobling mot tjeneren
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
			 * details equals "2" to get url for game by id
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
					data = "client?" + URLEncoder.encode("mode", "UTF-8") + "=" +URLEncoder.encode("getgame", "UTF-8");
					data += "&" + URLEncoder.encode("gameid", "UTF-8") + "=" +URLEncoder.encode("PutGaMeIdHeRe", "UTF-8");
					break;
			}
			myURL = "http://158.39.124.96:8080/rebus/" + data;
			url = new URL(myURL);
			httpConnection = (HttpURLConnection)url.openConnection();
			httpConnection.setConnectTimeout(2000);
			// Sett cookie dersom p�f�lgende kall:
			if (mSession != null && details !=0){
				httpConnection.setRequestProperty("cookie", mSession);
				}
			else{
				httpConnection.addRequestProperty("name", name);
				httpConnection.addRequestProperty("pass", pass);
			}
			
			StringBuffer buf = new StringBuffer();
			int enByte;
			int responseCode = httpConnection.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) {
				in = httpConnection.getInputStream();
				//f� sesjons id fra tjeneren og skrive dem i filen hvis vi logger inn, ellers g�r videre
				if (details ==0){
					mSession = httpConnection.getHeaderField("Set-cookie");
					if (mSession != null) {
						int semicolon = mSession.indexOf(';');
						mSession = mSession.substring(0, semicolon);
						filehandler.WriteLog(mSession, context);
					}
					responseMsg = "Authentification: success!";
				} //end (details ==0){
							//hvis vi vil f� informasjon om tilgjengelige spill
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
	
	/*
	 * Brukes for � f� tak i et enkelt spill-objekt,
	 *  n�r man velger det i "GamesAllActivity"
	 */
	public GameRebus getGameData(Context context, String gameIdToSend){
		String myURL = "";
		URL url = null;
		HttpURLConnection httpConnection = null;
		InputStream in = null;
		filehandler = new FileHandler();
		mSession = filehandler.ReadLogs(1, context);
		try {
			data = "client?" + URLEncoder.encode("mode", "UTF-8") + "=" +URLEncoder.encode("getgame", "UTF-8");
			data += "&" + URLEncoder.encode("gameid", "UTF-8") + "=" +URLEncoder.encode(gameIdToSend, "UTF-8");
			myURL = "http://158.39.124.96:8080/rebus/" + data;
			url = new URL(myURL);
			httpConnection = (HttpURLConnection)url.openConnection();
				
			// Sett cookie dersom p�f�lgende kall:
			if (mSession != null){
				httpConnection.setRequestProperty("cookie", mSession);
				}
			else{
				mSession = httpConnection.getHeaderField("Set-cookie");
				if (mSession != null) {
					int semicolon = mSession.indexOf(';');
					mSession = mSession.substring(0, semicolon);
					filehandler.WriteLog(mSession, context);
				}
			}
			//StringBuffer buf = new StringBuffer();
			//int enByte;
			int responseCode = httpConnection.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) {
				in = httpConnection.getInputStream();
				ObjectInputStream ois = new ObjectInputStream(in);
				gameRebus = (GameRebus) ois.readObject();
				System.out.println(gameRebus.getName());
				//String gameId = (String) ois.readObject();
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
	}
}