package student.hin.no;

import java.util.ArrayList;

import db.Game;

import android.app.ListActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;

public class ResultActivity extends ListActivity 
{
	
	//Variabler
	ArrayAdapter<String> adapter;
	private ArrayList<String> resultList = new ArrayList<String>();
	private ConnectionHandler connectionHandlerGameList;
	private String gameResultFromServlet;
	private Thread thread = null;
	Game game;
	//end of Variabler
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		//Henter objekt fra forrige activity
		game = (Game) getIntent().getExtras().getSerializable("game");
		game.getClass();
		
		thread = new Thread(null, listCheck, "Result");
		thread.start();

		adapter = new ArrayAdapter<String>(this, R.layout.result, resultList);
		setListAdapter(adapter);
		
	}//end of onCreate
	
	/**
	 * Thread skaffer resultater fra tjenester
	 * Runnable kaller metoden som startes i bakgrunnstråden
	 */
	private Runnable listCheck = new Runnable() {	
		@Override
		public void run() 
		{
			GetGameResultFromServlet();
			thread.interrupt();
		}
	};
	
	/**
	 * Genererer list med resultater
	 */
	private void GetGameResultFromServlet()
	{
		connectionHandlerGameList = new ConnectionHandler();
		gameResultFromServlet = connectionHandlerGameList.GetGameResults(getApplicationContext(), game.getId().toString());
		
		String [] gameResultArray = gameResultFromServlet.split(",");
		
		for (int i = 0; i < gameResultArray.length;)
		{
			//Log.d("Array length", Integer.toString(gameResultArray.length));
			try 
			{
				resultList.add(gameResultArray[i] + " " + gameResultArray[i+1]);	
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			i = i+2; 

		}//end of for
		
	}
	
}//end of ResultActivity
