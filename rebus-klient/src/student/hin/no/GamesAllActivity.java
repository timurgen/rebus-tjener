package student.hin.no;

import java.util.ArrayList;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class GamesAllActivity extends ListActivity{

	private ArrayList<GameRebus> games = new ArrayList<GameRebus>();
	private ArrayList<String> gamesList = new ArrayList<String>();
	
	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
		CreateGames();
		
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.games_all, gamesList);
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
	
	private void CreateGames()
	{
		games.add(new GameRebus("vs","gameOne", 50, true, "05-May-2012 15:15:15"));
		games.add(new GameRebus("ts","gameTwo", 70, true, "05-May-2012 17:17:17"));
		
		games.get(0).addPoint(new GamePunktRebus(68.435299, 17.437899, 50, "Point in game 1","HiN"));
		
		games.get(1).addPoint(new GamePunktRebus(68.437909, 17.422493, 70, "Point in game 2", "AMFi"));
		
		for (int i = 0; i < games.size(); i++)
			gamesList.add(games.get(i).getName() + " " + games.get(i).getVarighetString() + " " + games.get(i).getStartDateString());
	}
	
}//end of GamesAllActivity
