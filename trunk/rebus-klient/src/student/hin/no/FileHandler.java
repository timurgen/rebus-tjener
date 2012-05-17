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
	private Resources res;
	private String logsFile, entryFromLogs;
	private FileInputStream fIn;
	private FileOutputStream fOut;
	

	public FileHandler(){}
	
	/*
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
	
	/* Metoden "ReadLogs" leser fil "logsFile" 
	 * hvis whatToRead=1 - leser sesjon id,
	 * hvis  whatToRead=2 - leser det siste besøkte punktet (resultatet av rebusløp)
	 * */
	public String ReadLogs(int whatToRead, Context context){
		res = context.getResources();
		logsFile = res.getString(R.string.logsFile);
		try {
			fIn = context.openFileInput(logsFile);
			InputStreamReader isr = new InputStreamReader(fIn);
    		BufferedReader reader = new BufferedReader(isr);
    		
    		/* whatToRead = 1 - Leser sesjons-id
    		 * 
    		 */
    		switch (whatToRead){
	    		case 1:
	    		{
	    			entryFromLogs = reader.readLine();
		    		break;
	    		}//end case 1:
	    		
	    		//sjekke den siste resultatet
	    		case 2:
	    		{
	    			//blabla
	    		}
    		}//end switch
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
	
	public boolean CleanLogs(Context context){
		res = context.getResources();
		logsFile = res.getString(R.string.logsFile);
		if (context.deleteFile(logsFile))
			return true;
		else
			return false;
	}
}