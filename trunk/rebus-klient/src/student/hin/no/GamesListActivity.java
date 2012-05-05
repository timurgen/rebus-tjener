package student.hin.no;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class GamesListActivity extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.games_list);
		
		Button runButton = (Button)findViewById(R.id.buttonRun);
		runButton.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent mapIntent = new Intent(GamesListActivity.this, MapActivity.class);
				startActivity(mapIntent);
				
			}
		});//end of runButton.setOnClickListener
	}//end of onCreate

	
	
}//end of GamesListActivity
