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
	private String mSession = null;
	//private Handler handler = new Handler(); //Brukes til å oppdatere GUI
	//private Thread thread = null;
	private FileHandler filehandler;
	
	public String GetGameListFromServlet(Context context){
		
		String myURL = "";
		URL url = null;
		HttpURLConnection httpConnection = null;
		InputStream in = null;
		filehandler = new FileHandler();
		mSession = filehandler.ReadLogs(1, context);
		try {
			
			String data = URLEncoder.encode("mode", "UTF-8") + "=" +URLEncoder.encode("gamelist", "UTF-8");
			data += "&" + URLEncoder.encode("sid", "UTF-8") + "=" +URLEncoder.encode(mSession.substring(11), "UTF-8");
			myURL = "http://158.39.124.96:8080/rebus/client?" + data;
			url = new URL(myURL);
			httpConnection = (HttpURLConnection)url.openConnection();
			httpConnection.setRequestProperty("User-Agent","Mozilla/10.0 ( compatible ) ");
			httpConnection.setRequestMethod("GET");
			httpConnection.setDoInput(true);
			httpConnection.setConnectTimeout(5000);
				// Sett cookie dersom påfølgende kall:
				if (mSession != null){
					httpConnection.setRequestProperty("cookie", mSession);}
			int contentLength = httpConnection.getContentLength();
			StringBuffer buf = new StringBuffer(contentLength);
			int enByte;
			int responseCode = httpConnection.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) {
				in = httpConnection.getInputStream();

				while ((enByte = in.read()) != -1){
					buf.append((char) enByte);
				}// Gjør om til char og legger til stringbuffret
				responseMsg=buf.toString();
			}
		}
		catch (SocketTimeoutException e){
			e.printStackTrace();
			responseMsg="Server is unavailable";
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
	}
}
