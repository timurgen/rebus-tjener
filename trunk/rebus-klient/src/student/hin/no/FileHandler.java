package student.hin.no;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import android.content.Context;
import android.content.res.Resources;

public class FileHandler {
	//Variabler
	private Resources res;
	private String logsFile, timesFile, entryFromLogs;
	private FileInputStream fIn;
	private FileOutputStream fOut;
	//End of variabler

	public FileHandler(){}

	/**
	 * Skriver sesjons-id til filen
	 */
	public void WriteLog(String newEntry, Context context){
		res = context.getResources();
		logsFile = res.getString(R.string.logsFile);
		try{
			fOut = context.openFileOutput("logsFile", 0);
			OutputStreamWriter osw = new OutputStreamWriter(fOut);
			osw.write(newEntry + "\n");
			osw.close();
    		fOut.close();
    		
		}
		catch (FileNotFoundException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	        } 
		catch (IOException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	        }
	}

	/**
	 * Metoden "ReadLogs" leser fil "logsFile" 
	 * hvis whatToRead=1 - leser sesjon id,
	 */
	public String ReadLogs(int whatToRead, Context context){
		res = context.getResources();
		logsFile = res.getString(R.string.logsFile);
		try {
			fIn = context.openFileInput(logsFile);
			InputStreamReader isr = new InputStreamReader(fIn);
    		BufferedReader reader = new BufferedReader(isr);
	    	entryFromLogs = reader.readLine();

    		isr.close();
    		reader.close();
    		fIn.close();
    		
    		} 
		catch (FileNotFoundException e) {
    			e.printStackTrace();
    		} 
		catch (IOException e) {
    			e.printStackTrace();
    		}
		return entryFromLogs;
	}
	
	/**
	 * Fjerne log. Service funksjonen, ikke i bruk
	 * @param context
	 * @return
	 */
	public boolean CleanLogs(Context context){
		res = context.getResources();
		logsFile = res.getString(R.string.logsFile);
		if (context.deleteFile(logsFile))
			return true;
		else
			return false;
	}
	
	
	public void WriteTime(long time, String gamename, Context context){
		res = context.getResources();
		timesFile = res.getString(R.string.timesFile);
		try{
			fOut = context.openFileOutput("timesFile", 0);
			OutputStreamWriter osw = new OutputStreamWriter(fOut);
			osw.write(String.valueOf(time)+ ";" + gamename);
			osw.close();
    		fOut.close();
    		
		}
		catch (FileNotFoundException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	        } 
		catch (IOException e) {
	        // TODO Auto-generated catch block
	        e.printStackTrace();
	        }
	}
	
	public String ReadTimes(Context context){
		res = context.getResources();
		timesFile = res.getString(R.string.timesFile);
		try {
			fIn = context.openFileInput(timesFile);
			InputStreamReader isr = new InputStreamReader(fIn);
    		BufferedReader reader = new BufferedReader(isr);
	    	entryFromLogs = reader.readLine();

    		isr.close();
    		reader.close();
    		fIn.close();
    		
    		} 
		catch (FileNotFoundException e) {
    			e.printStackTrace();
    		} 
		catch (IOException e) {
    			e.printStackTrace();
    		}
		return entryFromLogs;
	}
	
}