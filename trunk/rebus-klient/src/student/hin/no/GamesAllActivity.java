package student.hin.no;

import java.text.ParseException;
import java.util.ArrayList;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class GamesAllActivity extends ListActivity{

	private ArrayList<GameRebus> games = new ArrayList<GameRebus>();
	
	private GameRebus gameRebus;
	private String gameIdToSend;
	private ArrayList<String> gamesList = new ArrayList<String>();
	ArrayAdapter<String> adapter;
	private ConnectionHandler connectionhandlerGameList, connectionhandlerGameId;
	private String gamesFromServlet;
	private Thread thread = null, thread2 = null;
	private Handler handler = new Handler(); //Brukes til å oppdatere GUI
	
	
	private Runnable bakgrunnsSjekkingListe = new Runnable() {
		public void run() {
			GetGamesListFromServlet();
			thread.interrupt();
		}
	};
	
	/*
	 * Starter når brukeren velger et spill, sender spill-id
	 * til "getGameFromServlet()", avbryter bakgrunnstraden og starter MapActivity
	 */
	private Runnable bakgrunnsGetGame = new Runnable() {
		public void run() {
			getGameFromServlet();
			thread.interrupt();
			Intent mapIntent = new Intent(GamesAllActivity.this, MapActivity.class);
			mapIntent.putExtra("game", gameRebus);
			startActivity(mapIntent);
		}
	};
	
	private Runnable doUpdateGUI = new Runnable() {
		public void run() {
		updateGUI();
		}
	};
	/*
	 * Brukes for å vise bakgrunntjenestesmeldinger til brukeren 	
	 */
	private Runnable ToastFraTjenester = new Runnable() {
		public void run() {
			int duration = Toast.LENGTH_LONG;
			Toast toast = Toast.makeText(getApplicationContext(), "Server is unavailable\nplease, try again later", duration);
			toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
			toast.show();
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
		gameRebus = games.get(position);
		gameIdToSend = gameRebus.getId().toString();
		thread2 = new Thread(null, bakgrunnsGetGame, "logging inn");
		thread2.start();
	}
	
	/* Funksjonen fyller på liste med alle spillene som finnes på tjeneren 
	 * og oppdaterer deretter "gamesList" som vises til brukeren
	 */
	private void GetGamesListFromServlet(){
		connectionhandlerGameList = new ConnectionHandler();
		gamesFromServlet = connectionhandlerGameList.GetDataFromServlet(getApplicationContext(), 1, "", "");
		if (!gamesFromServlet.contains("Error:")){
			gamesFromServlet = gamesFromServlet.trim();
			String [] gameArray = gamesFromServlet.split(",");				
			for ( int i=0; i< gameArray.length;){
											//gameid	author-name		game-name			varighet						isOpen								date
				try {
					games.add(new GameRebus(gameArray[i], gameArray[i+1], gameArray[i+2], Integer.parseInt(gameArray[i+3]), Boolean.parseBoolean(gameArray[i+4]),  gameArray[i+5]));
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				i = i+6; 
			}
			for (int i = 0; i < games.size(); i++)
				gamesList.add(games.get(i).getName() + " " + games.get(i).getVarighetString() + " " + games.get(i).getStartDateString());
			handler.post(doUpdateGUI);
		}
		else
			handler.post(ToastFraTjenester);
	}
	
	/*
	 * Funksjonen får tak i spillet som ble valgt
	 * laster spillet ned inn i applikasjonen,
	 * oppretter et java-objekt (GameRebus) og sender
	 * det til MapActivity vha intent
	 */
	private void getGameFromServlet(){
		connectionhandlerGameId = new ConnectionHandler();
		gameRebus = connectionhandlerGameId.getGameData(getApplicationContext(), gameIdToSend);
	}
	
	/*
	 * Oppdaterer GUI med spillene på tjeneren
	 */
	private void updateGUI() {
		adapter.notifyDataSetChanged();
	}
//	private void CreateGames()
//	{
//		games.add(new GameRebus("vs","gameOne", 50, true, "05-May-2012 15:15:15"));
//		games.add(new GameRebus("ts","gameTwo", 70, true, "05-May-2012 17:17:17"));
//		
//		games.get(0).addPoint(new GamePunktRebus(68.435299, 17.437899, 50, "First point in game 1","HiN"));
//		games.get(0).addPoint(new GamePunktRebus(68.439553, 17.44593, 100, "Second point in game 1","HiN"));
//		
//		games.get(1).addPoint(new GamePunktRebus(68.437909, 17.422493, 70, "Point in game 2", "AMFi"));
//		games.get(1).addPoint(new GamePunktRebus(68.439553, 17.44593, 100, "Second point in game 1","HiN"));
//		
//		for (int i = 0; i < games.size(); i++)
//			gamesList.add(games.get(i).getName() + " " + games.get(i).getVarighetString() + " " + games.get(i).getStartDateString());
//	}
}//end of GamesAllActivity
