package info.exler.teampicker;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import java.util.Random;


public class MainActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button assign = (Button) findViewById(R.id.button);
        final String[] spinnerList = {"Player", "Team"};
        final Spinner sortSpinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, spinnerList);
        sortSpinner.setAdapter(adapter);


        assign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText players = (EditText) findViewById(R.id.players);
                EditText teams = (EditText) findViewById(R.id.teams);
                try {
                    int iPlayers = Integer.parseInt(players.getText().toString());
                    int iTeams = Integer.parseInt(teams.getText().toString());
                    int assignedPlayers[][] = assign(iPlayers, iTeams);
                    int spinState = sortSpinner.getSelectedItemPosition();

                    int currentTeam=0;
                    int playersPerTeam[] = new int[iTeams];

                    while(currentTeam<iTeams) {
                        for (int i = 0; i < iPlayers; i++) {
                            int playerTeam = assignedPlayers[i][0];
                            if ((playerTeam) == currentTeam) {
                                playersPerTeam[currentTeam] += 1;
                            }
                        }
                        currentTeam++;
                    }

                    for(int i=0;i>iTeams;i++){
                        System.out.println("PlayersPerTeam["+i+"]:"+playersPerTeam[i]);
                    }

                    if (iPlayers >= iTeams){
                        displayWindow(iPlayers, iTeams, spinState, assignedPlayers);
                    }else{
                        Toast.makeText(getApplicationContext(),"Please enter more players.", Toast.LENGTH_SHORT).show();
                    }
                }
                catch(Exception e){
                    Toast.makeText(getApplicationContext(), "Enter number of players and teams to proceed.", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    public static int[][] assign(int iPlayers, int iTeams) {
        int playerList[][];
        playerList = new int[iPlayers][1];
        Random rng = new Random();
        int playersPerTeam = (int) Math.ceil(iPlayers / iTeams);
        int playersInTeam[] = new int[iTeams];

        for (int i=0;i<iPlayers;i++) {
            int rngint = rng.nextInt(iTeams);
            if (playersPerTeam > playersInTeam[rngint]) {
                playersInTeam[rngint]++;
                playerList[i][0] = rngint;
            } else {
                int newrng;
                do {
                    newrng = rng.nextInt(iTeams);
                } while (newrng == rngint);
                playersInTeam[newrng]++;
                playerList[i][0] = newrng;
            }

        }
        return playerList;
    }

    public void displayWindow(int iPlayers, int iTeams, int spinState, int assignedPlayers[][]) {
        Intent displayOutput = new Intent(MainActivity.this, Popwindow.class);
        Bundle assignedPlayerArray = new Bundle();
        displayOutput.putExtra("players", iPlayers);
        displayOutput.putExtra("teams", iTeams);
        displayOutput.putExtra("spinState", spinState);
        assignedPlayerArray.putSerializable("assignedPlayers", assignedPlayers);
        displayOutput.putExtras(assignedPlayerArray);
        startActivity(displayOutput);
    }
}
