package com.jonesclass.huangstasinos.crossmind;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class GameActivity extends AppCompatActivity {

    public Random rNumber = new Random();

    public ArrayList<String> teams = new ArrayList<String>();
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

        teams.add("BioTeam");
        teams.add("Knights");
        teams.add("Outlanders");
        teams.add("Techno");

        //labels
        TextView player1Label = findViewById(R.id.player1Label);
        TextView player2Label = findViewById(R.id.player2Label);

        Log.d(TAG, "onCreate: Created");
        Intent newIntent = getIntent();

        twoPlayers = newIntent.getBooleanExtra("com.jonesclass.huangstasinos.crossmind.TWO_PLAYERS", true);
        teamChoice = newIntent.getStringExtra("com.jonesclass.huangstasinos.crossmind.TEAM_CHOICE_PLAYER_TWO");
        teamChoice2 = newIntent.getStringExtra("com.jonesclass.huangstasinos.crossmind.TEAM_CHOICE_PLAYER_TWO");

        System.out.println(teamChoice);
        System.out.println(teamChoice2);

        switch(teamChoice2) { // "BioTeam","Knights","Outlanders","Techno" //P1 (singlePlayer, and P2 for 2 Players)
            case "BioTeam":
                color = "green";
                break;
            case "Knights":
                color = "";
                break;
            case "Outlanders":
                color = "red";
                break;
            case "Techno":
                color = "blue";
                break;
            default:
                color = null; // this should NOT happen
        }
        if (twoPlayers) {
            switch(teamChoice) {
                case "BioTeam":
                    color2 = "green";
                    break;
                case "Knights":
                    color2 = "black";
                    break;
                case "Outlanders":
                    color2 = "red";
                    break;
                case "Techno":
                    color2 = "blue";
                    break;
                default:
                    color2 = null; // this should NOT happen
            }
        } else {
            teams.get(rNumber.nextInt(teams.size()));
            //teamChoice = teams.get(0);
            switch(teamChoice) {
                case "BioTeam":
                    color2 = "green";
                    break;
                case "Knights":
                    color2 = "black";
                    break;
                case "Outlanders":
                    color2 = "red";
                    break;
                case "Techno":
                    color2 = "blue";
                    break;
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
            for (int j = 0; j < imageButtons[i].length; j ++) {
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
                            int photoResID = getResources().getIdentifier(nameOfFile,"id",getPackageName());
                            imageButtons[finalI][finalJ].setImageDrawable(getDrawable(photoResID));
                            turnCounter ++;
                            player2Label.setVisibility(View.VISIBLE);
                            player1Label.setVisibility(View.INVISIBLE);
                        } else if(turnCounter % 2 == 1) {
                            //TODO: player2 turn
                            String nameOfFile2 = "piece" + color2;
                            int photoResID2 = getResources().getIdentifier(nameOfFile2,"id",getPackageName());
                            imageButtons[finalI][finalJ].setImageDrawable(getDrawable(photoResID2));
                            player2Label.setVisibility(View.INVISIBLE);
                            player1Label.setVisibility(View.VISIBLE);
                        }
                    }
                });
            }
        }
        
    }

}
