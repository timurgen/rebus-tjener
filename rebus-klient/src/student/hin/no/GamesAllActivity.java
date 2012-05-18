package student.hin.no;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import db.Game;

public class GamesAllActivity extends ListActivity{

	//Variabler
	private ArrayList<db.Game> games = new ArrayList<db.Game>();
	private db.Game gameRebus;
	private String gameIdToSend, nameOfFirstGame, pinCode = null;
	private ArrayList<String> gamesList = new ArrayList<String>();
	ArrayAdapter<String> adapter;
	private ConnectionHandler connectionhandlerGameList, connectionhandlerGameId;
	private String gamesFromServlet;
	private Thread thread = null, thread2 = null;
	private Handler handler = new Handler(); //Brukes til å oppdatere GUI
	final private static int PIN_DIALOG = 1;
	private Toast toast;
	private int duration = Toast.LENGTH_LONG;
	private String status;
	private Date date = new Date();
	private Integer runCode;
	private long timeToStartTheFirstGame;
	private Alarm alarmbleat = new Alarm();
	//End of variabler
	
	/**
	 * Runnable kaller metoden som startes i bakgrunnstråden
	 */
	private Runnable bakgrunnsSjekkingListe = new Runnable() {
		public void run() {
			GetGamesListFromServlet();
			thread.interrupt();
		}
	};
	
	/**
	 * Starter når brukeren velger et spill, sender spill-id
	 * til "getGameFromServlet()", avbryter bakgrunnstraden og starter MapActivity
	 */
	private Runnable bakgrunnsGetGame = new Runnable() {
		public void run() {
			
			getGameFromServlet();
			thread.interrupt();
			if(runCode == 2)
			{
				if (gameRebus != null)
				{
					//alarmbleat.CancelAlarm(getApplicationContext());			//kansellerer alarm hvis vi skal spille

					Log.d("Status", getStatus(gameRebus));				
					Intent mapIntent = new Intent(GamesAllActivity.this, MapActivity.class);
					mapIntent.putExtra("game", gameRebus);
					startActivity(mapIntent);					
				}//end of if(gameRebus=
			}//end of if(runCode)
			
			
		}//end of run
	};
	
	/**
	 * Runnable kaller metoden som startes i bakgrunnstråden
	 */
	private Runnable doUpdateGUI = new Runnable() {
		public void run() {
		updateGUI();
		}
	};

	/**
	 * Brukes for å vise bakgrunntjenestesmeldinger til brukeren 
	 */
	private Runnable ToastFraTjenester = new Runnable() {
		public void run() {
			toast = Toast.makeText(getApplicationContext(), "Server is unavailable\nplease, try again later", duration);
			toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
			toast.show();
		}
	};
		
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
		thread = new Thread(null, bakgrunnsSjekkingListe, "logging inn");
		thread.start();
		
		adapter = new ArrayAdapter<String>(this, R.layout.games_all, gamesList);
	}//end of onCreate
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id)
	{
		//Log.d("OnClick", "Test");
		gameRebus = games.get(position);
		gameIdToSend = gameRebus.getId().toString();
		
		if (GamesAllActivity.this.getCallingActivity() == null){		//hvis GamesAllActivity ble started fra LoginActivity -
			thread2 = new Thread(null, bakgrunnsGetGame, "logging inn");//man laster spillet ned og begynner
    		thread2.start();
		}
		else{															//hvis det ble started med "Free Play"
			if  (gameRebus.isIsOpen()){									//da sjekker vi om det er et "åpent" spill
					showDialog(PIN_DIALOG);								//brukeren får dialog-vindu og taster pin-koden sin
			}
			else{														//ellers kommer han tilbake til spill-listet
				int duration = Toast.LENGTH_LONG;
				Toast toast = Toast.makeText(getApplicationContext(), "you are not registered for this game", duration);
				toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
				toast.show();
				return;
			}
		}		
	}//end of onListItemClick
	
	/**
	 * Funksjonen fyller på liste med alle spillene som finnes på tjeneren
	 * og oppdaterer deretter "gamesList" som vises til brukeren
	 */
	private void GetGamesListFromServlet(){
		connectionhandlerGameList = new ConnectionHandler();
		gamesFromServlet = connectionhandlerGameList.GetDataFromServlet(getApplicationContext(), 1, "", "");
		if (!gamesFromServlet.contains("Error:")){
			gamesFromServlet = gamesFromServlet.trim();
			String [] gameArray = gamesFromServlet.split(",");
			int j = 0;
			for ( int i=0; i< gameArray.length;){
											//gameid	author-name		game-name			varighet						isOpen					midlertidig data			
				try {
					games.add(new db.Game(gameArray[i+1], gameArray[i+2], Integer.parseInt(gameArray[i+3]), Boolean.parseBoolean(gameArray[i+4]),  "15-05-2012 15:15:15"));
					games.get(j).setIdGame(Long.parseLong(gameArray[i]));
					games.get(j).setStartDate(Long.parseLong(gameArray[i+5]));//set normal dato
					games.get(j).addPartisipant(Long.parseLong(gameArray[i+6]));
				} catch (NumberFormatException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//sette tid og game-navn når starter snarest spill på listet -> brukes for å sette alarm
				if (timeToStartTheFirstGame > Long.parseLong(gameArray[i+5])){
					if (SystemClock.elapsedRealtime()< Long.parseLong(gameArray[i+5])){
						timeToStartTheFirstGame = Long.parseLong(gameArray[i+5]);
						nameOfFirstGame = gameArray[i+2];
						FileHandler fileh = new FileHandler();
						fileh.WriteTime(timeToStartTheFirstGame, getApplicationContext());
					}
				}
				i = i+7; 
				j++;
				//Log.d("GameList", "List from servlet");
			}//end of for
			
			//Genererer list
			for (int i = 0; i < games.size(); i++)
					gamesList.add(
						//"Id " 
						//+ games.get(i).getId() + " " 
						  games.get(i).getName() + " " 
						+ getDate(games.get(i).getStartDate()) + " " 
						+ String.valueOf(games.get(i).getAllPartisipants().get(0)) + "Pl " 
						+ getStatus(games.get(i)));
			handler.post(doUpdateGUI);
		}
		else
			handler.post(ToastFraTjenester);
	}
	
	/**
	 * Funksjonen får tak i spillet som ble valgt
	 * laster spillet ned inn i applikasjonen,
	 * oppretter et java-objekt (GameRebus) og sender
	 * det til MapActivity vha intent
	 */
	private void getGameFromServlet()
	{
		Log.d("Status", getStatus(gameRebus));
		if(getStatus(gameRebus).equals("End"))
		{
			runCode = 0;
			Intent resultIntent = new Intent(GamesAllActivity.this, ResultActivity.class);
			resultIntent.putExtra("game", gameRebus);
			startActivity(resultIntent);
		}
		else if(getStatus(gameRebus).equals("Wait for start"))
		{
			runCode = 1;
			//Toast.makeText(GamesAllActivity.this,"Wait until game starts", Toast.LENGTH_LONG).show();
		}
		else
		{
			runCode = 2;
			connectionhandlerGameId = new ConnectionHandler();
			if (pinCode != null){		//guest får sesjons-id som skrives til filen og går videre til å få Game
				connectionhandlerGameId.GetDataFromServlet(getApplicationContext(), 2, pinCode, null);
			}
			gameRebus = connectionhandlerGameId.getGameData(getApplicationContext(), gameIdToSend, pinCode);
		}
		Log.d("RunCode", runCode.toString());
	}
	
	/**
	 * Oppdaterer GUI med spillene på tjeneren og setter en alarm for det snareste spillet
	 */
	private void updateGUI() {
		adapter.notifyDataSetChanged();
		setListAdapter(adapter);
	}
	
	protected Dialog onCreateDialog(int id) {
		switch(id) {
		case (PIN_DIALOG) :
			LayoutInflater factory = LayoutInflater.from(this);
			final View PinEntryView = factory.inflate(R.layout.alert_dialog, null);
	    	final EditText EditPinCode = (EditText)PinEntryView.findViewById(R.id.editPinCode);
	    	Resources res = getResources();
	    	Drawable drawablePin = res.getDrawable(R.drawable.enterpin);
	    	EditPinCode.setBackgroundDrawable(drawablePin);
			return new AlertDialog.Builder(GamesAllActivity.this)
			
			.setView(PinEntryView)
			.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {
                	pinCode = EditPinCode.getText().toString();
                	thread2 = new Thread(null, bakgrunnsGetGame, "logging inn");
            		thread2.start();
                }
        })
        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
            }
        })
        .create();
	 }
	return null;
	}
	
	/**
	 * Konverterer tid fra millisekunder til angit format
	 * @param milliSeconds
	 * @return String tid
	 */
	public String getDate(long milliSeconds)
	{
	    // Create a DateFormatter object for displaying date in specified format.
	     DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
	     Calendar calendar = Calendar.getInstance();
	     calendar.setTimeInMillis(milliSeconds);
	     return formatter.format(calendar.getTime());
	}
	
	/**
	 * Spill status
	 * @param game 
	 * @return String spill status
	 */
	public String getStatus(Game game)
	{
		if(game.getStartDate() > date.getTime())
		{
			status = "Wait for start";
		}
		else if (game.getStartDate() + game.getVarighet() * 60000 < date.getTime())
		{
			status = "End";
		}
		else
		{
			status = "Join";
		}
		
		return status;
	}

	/** setter alarm for det snareste spillet med parametre tid og navn */
	public void setAlarmToTheFirstGame(long timeToStartTheFirstGame, String nameOfFirstGame){
//		timeToStartTheFirstGame = timeToStartTheFirstGame + 30*1000;	//gi litt ekstra tid for å logge seg inn
//		alarmbleat.SetAlarm(getApplicationContext(), timeToStartTheFirstGame, true, false, nameOfFirstGame);
	}
	
	public void onDestroy(){
//		FileHandler fileh = new FileHandler();
//		timeToStartTheFirstGame = fileh.ReadTimes(getApplicationContext());
//		if (String.valueOf(timeToStartTheFirstGame) != null){
//			setAlarmToTheFirstGame(timeToStartTheFirstGame, "");
//		}
		super.onDestroy();
	}
}//end of GamesAllActivity
