package com.jonesclass.huangstasinos.crossmind;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import java.util.ArrayList;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Random;

public class GameActivity extends AppCompatActivity {


    public Random rNumber = new Random();
    private ArrayList<String> teams = new ArrayList<String>();
    private int turnCounter = 0;
    private int pieceCounter = 1;
    private final int PIECE_LIMIT = 10;
    private int points;
    public boolean twoPlayers;
    public String teamChoice;
    public String teamChoice2;
    private String color, color2;
    private ImageButton[][] imageButtons = new ImageButton[5][5];
    private boolean[][] selected = new boolean[5][5];
    private int[][] values = new int[5][5];
    

    final String TAG = "GameActivityTag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        //adds team
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
        teamChoice = newIntent.getStringExtra("com.jonesclass.huangstasinos.crossmind.TEAM_CHOICE_PLAYER_ONE");
        teamChoice2 = newIntent.getStringExtra("com.jonesclass.huangstasinos.crossmind.TEAM_CHOICE_PLAYER_TWO");

        Log.d(TAG, "Team 1: " + teamChoice);
        Log.d(TAG,"Team 2: " + teamChoice2);

        switch(teamChoice2) { // "BioTeam","Knights","Outlanders","Techno" //P1 (singlePlayer, and P2 for 2 Players)
            case "BioTeam":
                color = "green";
                break;
            case "Knights":
                color = "black";
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
                        turn(turnCounter,player1Label,player2Label,finalI,finalJ,pieceCounter);
                        turnCounter++;
                        pieceCounter++;


                    }
                });
            }
        }


        
    }
    public void turn(int turnCounter,TextView player1Label, TextView player2Label,int finalI,int finalJ,int pieceCounter){
        if ((turnCounter % 2 == 0) && (pieceCounter <= PIECE_LIMIT)) {
            // TODO: player1 turn
            String nameOfFile = "piece" + color;
            Log.d(TAG,color);
            int photoResID = getResources().getIdentifier(nameOfFile,"drawable",getPackageName());
            imageButtons[finalI][finalJ].setImageDrawable(getDrawable(photoResID));
            player2Label.setVisibility(View.VISIBLE);
            player1Label.setVisibility(View.GONE);

        } else if((turnCounter % 2 == 1) && (pieceCounter <= PIECE_LIMIT)) {
            //TODO: player2 turn
            String nameOfFile2 = "piece" + color2;
            int photoResID2 = getResources().getIdentifier(nameOfFile2,"drawable",getPackageName());
            imageButtons[finalI][finalJ].setImageDrawable(getDrawable(photoResID2));
            player1Label.setVisibility(View.VISIBLE);
            player2Label.setVisibility(View.GONE);
        }
    }


}
