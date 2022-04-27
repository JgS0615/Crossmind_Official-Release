package com.jonesclass.huangstasinos.crossmind;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.TextView;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class GameActivity extends AppCompatActivity {

    public Random rNumber = new Random();

    public String[] teams = new String[4];
    public int turnCounter = 0;
    public int points;
    public boolean twoPlayers;
    public String teamChoice;
    public String teamChoice2;
    public String color, color2;
    public ImageButton[][] imageButtons = new ImageButton[5][5];
    public boolean[][] selected = new boolean[5][5];
    public int[][] values = new int[5][5];
    

    final String TAG = "GameActivityTag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        teams = ["BioTeam","Knights","Outlanders","Techno"];
        
        Log.d(TAG, "onCreate: Created");
        Intent newIntent = getIntent();

        twoPlayers = newIntent.getBooleanExtra("com.jonesclass.stasinos.ticTacSmack.TWO_PLAYERS", true);
        teamChoice = newIntent.getStringExtra("com.jonesclass.stasinos.ticTacSmack.TEAM_CHOICE_PLAYER_ONE");
        teamChoice2 = newIntent.getStringExtra("com.jonesclass.stasinos.ticTacSmack.TEAM_CHOICE_PLAYER_TWO");
        
        switch(teamChoice2) { // "BioTeam","Knights","Outlanders","Techno" //P1 (singlePlayer, and P2 for 2 Players)
            case "BioTeam":
                color = "green";
                continue;
            case "Knights":
                color = "";
                continue;
            case "Outlanders":
                color = "red";
                continue;
            case "Techno":
                color = "blue";
                continue;
            default:
                color = null; // this should NOT happen
        }
        if (twoPlayers) {
            switch(teamChoice) {
                case "BioTeam":
                    color2 = "green";
                    continue;
                case "Knights":
                    color2 = "black";
                    continue;
                case "Outlanders":
                    color2 = "red";
                    continue;
                case "Techno":
                    color2 = "blue";
                    continue;
                default:
                    color2 = null; // this should NOT happen
            }
        } else {
            List<String> teamList = Arrays.asList(teams);
            Collections.shuffle(teamList);
            teamList.toArray(teams);
            teamChoice = teams.get(0);
            switch(teamChoice) {
                case "BioTeam":
                    color2 = "green";
                    continue;
                case "Knights":
                    color2 = "black";
                    continue;
                case "Outlanders":
                    color2 = "red";
                    continue;
                case "Techno":
                    color2 = "blue";
                    continue;
                default:
                    color2 = null; // this should NOT happen
            }
        }

        if (twoPlayers) {
            Board board = new Board(teamChoice, teamChoice2);
        } else {
            Board board = new Board(teamChoice2);
        }
        
        for (int i = 0; i < imageButtons.length; i ++) {
            for (int j = 0; j < imageButtons[].length; j ++) {
                String buttonID = "tile_" + (i + 1) + "." + j;
                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                imageButtons[i][j] = ((ImageButton) findViewById(resID));
                int finalI = i;
                int finalJ = j;
                imageButtons[i][j].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (turnCounter % 2 == 0) {
                            // TODO: player1 turn
                            String nameOfFile = "piece" + color;
                            int photoResID = getResources().getDrawable();
                            imageButtons[finalI][finalJ].setImageDrawable(getDrawable(res.getIdentifier(nameOfFile,"drawable",context.getPackageName())));
                            turnCounter ++;
                            player2Label.setVisibility(View.VISIBLE);
                            player1Label.setVisibility(View.INVISIBLE);
                        } else if(turnCounter % 2 == 1) {
                            //TODO: player2 turn
                            String nameOfFile = "piece" + color2;
                            int photoResID = getResources().getDrawable();
                            imageButtons[finalI][finalJ].setImageDrawable(getDrawable(res.getIdentifier(nameOfFile,"drawable",context.getPackageName())));
                            player2Label.setVisibility(View.INVISIBLE);
                            player1Label.setVisibility(View.VISIBLE);
                        }
                    }
                });
            }
        }
        
    }

}
