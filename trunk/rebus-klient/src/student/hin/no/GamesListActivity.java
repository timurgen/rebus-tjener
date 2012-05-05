package student.hin.no;

import java.util.ArrayList;

import org.apache.commons.logging.Log;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class GamesListActivity extends Activity{

	private ArrayList<GameRebus> games = new ArrayList<GameRebus>();
	private TextView [] textViewGameName;// = new TextView();
	View linearLayout;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.games_list);
		linearLayout = findViewById(R.id.games_list);
		
		CreateGames();
		
		Button runButton = (Button)findViewById(R.id.buttonRun);
		runButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent mapIntent = new Intent(GamesListActivity.this, MapActivity.class);
				startActivity(mapIntent);
				
			}
		});//end of runButton.setOnClickListener
	}//end of onCreate
	
	private void CreateGames()
	{
		
		System.out.println("TEST BLIAD");
		games.add(new GameRebus("vs","gameOne", 50, true, "05-May-2012 15:15:15"));
		games.add(new GameRebus("ts","gameTwo", 70, true, "05-May-2012 17:17:17"));
		textViewGameName = new TextView[games.size()];
		
		for(int i = 0; i < games.size(); i++)
		{
			textViewGameName[i] = new TextView(this);
			textViewGameName[i].setText(games.get(i).getName());
			textViewGameName[i].setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
			((LinearLayout)linearLayout).addView(textViewGameName[i]);

		}
	}//end of CreateGames

	
	
}//end of GamesListActivity
