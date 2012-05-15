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
	private TextView [] textViewVarighet;
	private TextView [] textViewDato;
	private Button [] buttonAction;
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
		
		games.add(new GameRebus("vs","gameOne", null, 50, true, "05-May-2012 15:15:15"));
		games.add(new GameRebus("ts","gameTwo", null, 70, true, "05-May-2012 17:17:17"));
		textViewGameName = new TextView[games.size()];
		textViewVarighet = new TextView[games.size()];
		textViewDato = new TextView[games.size()];
		buttonAction = new Button[games.size()];
		
		for(int i = 0; i < games.size(); i++)
		{
			textViewGameName[i] = new TextView(this);
			textViewGameName[i].setText(games.get(i).getName());
			textViewGameName[i].setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
			((LinearLayout)linearLayout).addView(textViewGameName[i]);
			
			textViewVarighet[i] = new TextView(this);
			textViewVarighet[i].setText(games.get(i).getVarighetString());
			textViewVarighet[i].setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
			((LinearLayout)linearLayout).addView(textViewVarighet[i]);
			
			textViewDato[i] = new TextView(this);
			textViewDato[i].setText(games.get(i).getStartDateString());
			textViewDato[i].setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
			((LinearLayout)linearLayout).addView(textViewDato[i]);
			
			buttonAction[i] = new Button(this);
			buttonAction[i].setText("Set");
			buttonAction[i].setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
			((LinearLayout)linearLayout).addView(buttonAction[i]);
		}
	}//end of CreateGames

	
	
}//end of GamesListActivity
