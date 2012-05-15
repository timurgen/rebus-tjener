package student.hin.no;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLEncoder;

import android.content.Context;

public class ConnectionHandler {
	private String responseMsg = "";
	private String data, mSession = null;
	private FileHandler filehandler;
	
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
				
			// Sett cookie dersom påfølgende kall:
			if (mSession != null && details !=0){
//				httpConnection.setDoInput(true);
//				httpConnection.setConnectTimeout(5000);
//				httpConnection.setRequestMethod("GET");
//				httpConnection.setRequestProperty("User-Agent","Mozilla/10.0 ( compatible ) ");
				httpConnection.setRequestProperty("cookie", mSession);
				}
			
			
			int contentLength = httpConnection.getContentLength();
			StringBuffer buf = new StringBuffer();
			int enByte;
			int responseCode = httpConnection.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) {
				in = httpConnection.getInputStream();
				//få sesjons id fra tjeneren og skrive dem i filen hvis vi logger inn, ellers går videre
				if (details ==0){
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
}