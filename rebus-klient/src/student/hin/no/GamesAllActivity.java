package student.hin.no;

import java.util.ArrayList;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class GamesAllActivity extends ListActivity{

	private ArrayList<GameRebus> games = new ArrayList<GameRebus>();
	private ArrayList<String> gamesList = new ArrayList<String>();
	ArrayAdapter<String> adapter;
	private ConnectionHandler connectionhandler;
	private String gamesFromServlet;
	private Thread thread = null;
	
	private Runnable bakgrunnsSjekkingListe = new Runnable() {
		public void run() {
			GetGamesListFromServlet();
			thread.interrupt();
		}
	};
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
		thread = new Thread(null, bakgrunnsSjekkingListe, "logging inn");
		thread.start();
		//CreateGames();
		
		adapter = new ArrayAdapter<String>(this, R.layout.games_all, gamesList);
		setListAdapter(adapter);
		
	}//end of onCreate
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id)
	{
		String item = (String)getListAdapter().getItem(position);
		//Toast.makeText(this, item + " selected", Toast.LENGTH_LONG).show();
		Intent mapIntent = new Intent(GamesAllActivity.this, MapActivity.class);
		mapIntent.putExtra("game", games.get(position));
		startActivity(mapIntent);
	}
	
	private void GetGamesListFromServlet(){
		connectionhandler = new ConnectionHandler();
		gamesFromServlet = connectionhandler.GetDataFromServlet(getApplicationContext(), 1, "", "");
		gamesFromServlet = gamesFromServlet.trim();
		String [] gameArray = gamesFromServlet.split(",");
			
		//sjekker hvis det kommer et streng med 6 elementer for hvert spill
		for ( int i=0; i< gameArray.length;){
										//gameid	author-name		game-name		varighet		isOpen			date
			games.add(new GameRebus(gameArray[i],gameArray[i+1], gameArray[i+2], gameArray[i+3], gameArray[i+4],  gameArray[i+5]));
			i = i+6; 
		}
		for (int i = 0; i < games.size(); i++)
			gamesList.add(games.get(i).getName() + " " + games.get(i).getVarighetString() + " " + games.get(i).getStartDateString());
		adapter.notify();
	}
	
	private void CreateGames()
	{
		games.add(new GameRebus("vs","gameOne", 50, true, "05-May-2012 15:15:15"));
		games.add(new GameRebus("ts","gameTwo", 70, true, "05-May-2012 17:17:17"));
		
		games.get(0).addPoint(new GamePunktRebus(68.435299, 17.437899, 50, "First point in game 1","HiN"));
		games.get(0).addPoint(new GamePunktRebus(68.439553, 17.44593, 100, "Second point in game 1","HiN"));
		
		games.get(1).addPoint(new GamePunktRebus(68.437909, 17.422493, 70, "Point in game 2", "AMFi"));
		games.get(1).addPoint(new GamePunktRebus(68.439553, 17.44593, 100, "Second point in game 1","HiN"));
		
		for (int i = 0; i < games.size(); i++)
			gamesList.add(games.get(i).getName() + " " + games.get(i).getVarighetString() + " " + games.get(i).getStartDateString());
	}
}//end of GamesAllActivity
