package info.exler.teampicker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Popwindow extends Activity {

    private static TextView displayOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popwindow);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        Intent intent = getIntent();
        final int assignedPlayers[][] = (int[][]) intent.getSerializableExtra("assignedPlayers");
        final int iPlayers = intent.getIntExtra("players", 0);
        final int iTeams = intent.getIntExtra("teams", 0);
        final int spinState = intent.getIntExtra("spinState",0);


        displayOut = (TextView) findViewById(R.id.display);
        displayOut.setMovementMethod(new ScrollingMovementMethod());
        if (spinState==0) {
            displayOut.setText(genSortByPlayerString(assignedPlayers, iPlayers, iTeams));
        }
        else{
            displayOut.setText(genSortByTeamString(assignedPlayers,iPlayers,iTeams));
        }

        int height = dm.heightPixels;
        int width = dm.widthPixels;

        getWindow().setLayout((int) (width * .9), (int) (height * .9));

        Button dismiss = (Button) findViewById(R.id.dismiss_window);
        dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Button regen = (Button) findViewById(R.id.regen);
        regen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.assign(iPlayers,iTeams);
                finish();
                startActivity(getIntent());
            }
        });
    }

    public static String genSortByPlayerString(int assignedPlayers[][], int iPlayers, int iTeam) {
        String displayString = "";

        for (int i = 0; i < iPlayers; i++) {
            displayString += "\nPlayer " + (i + 1) + ": Team " + (assignedPlayers[i][0] + 1);
        }
        return displayString;
    }

    public static String genSortByTeamString(int assignedPlayers[][], int iPlayers, int iTeam) {
        String displayString = "";
        int currentTeam=0;

        while(currentTeam<iTeam){
            displayString+="\nTeam "+(currentTeam+1)+":\n";
            for(int i=0;i<iPlayers;i++){
                int playerTeam = assignedPlayers[i][0];
                if ((playerTeam)==currentTeam){
                    displayString+=" Player " + (i+1) + ", ";

                }
            }
            currentTeam++;
            displayString+="\n";
        }
        return displayString;
    }
}