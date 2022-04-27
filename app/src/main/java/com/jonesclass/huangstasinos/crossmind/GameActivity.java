package com.jonesclass.huangstasinos.crossmind;

import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import java.util.ArrayList;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Collection;
import java.util.Collections;
import java.util.Random;

public class GameActivity extends AppCompatActivity {

    public Random rNumber = new Random();
    Board board;
    private final ArrayList<String> TEAMS = new ArrayList<>();
    private int turnCounter = 0;
    private int pieceCounter = 1;
    private final int PIECE_LIMIT = 10;
    public boolean twoPlayers;
    public String teamChoice;
    public String teamChoice2;
    private String color, color2;
    private ImageButton[][] imageButtons = new ImageButton[5][5];
    private boolean sound = true;

    final String TAG = "GameActivityTag";
    public MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        mp = MediaPlayer.create(this,R.raw.volatilereaction);
        mp.start();
        mp.setVolume(80,80);

        //adds team
        TEAMS.add("BioTeam");
        TEAMS.add("Knights");
        TEAMS.add("Outlanders");
        TEAMS.add("Techno");

        //interactives
        TextView player1Label = findViewById(R.id.player1Label);
        TextView player2Label = findViewById(R.id.player2Label);
        ImageButton soundButton = findViewById(R.id.imageButton_volume);
        soundButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sound = !sound;
                if(sound){
                    mp.start();
                    soundButton.setImageDrawable(getDrawable(R.drawable.soundofficon));
                } else {
                    mp.pause();
                    soundButton.setImageDrawable(getDrawable(R.drawable.soundonicon));
                }
            }
        });



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
            Collections.shuffle(TEAMS);
            teamChoice = TEAMS.get(0);
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
            board = new Board(teamChoice, teamChoice2);
        } else {
            board = new Board(teamChoice2);
        }
        for (int i = 0; i < imageButtons.length; i ++) {
            for (int j = 0; j < imageButtons[i].length; j ++) {
                String buttonID = "tile_" + (i + 1) + "." + j;
                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                imageButtons[i][j] = ((ImageButton) findViewById(resID));
                int finalI = i;
                int finalJ = j;
                // TYPES: Forest, Mountain, City, Plains
                switch (board.tiles[i][j].type){
                    case ("Forest"):
                        imageButtons[i][j].setBackgroundColor(Color.GREEN);
                        break;
                    case ("Mountain"):
                        imageButtons[i][j].setBackgroundColor(Color.DKGRAY);
                        break;
                    case ("City"):
                        imageButtons[i][j].setBackgroundColor(Color.YELLOW);
                        break;
                    default:
                        imageButtons[i][j].setBackgroundColor(Color.CYAN);
                }
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

    /**
     * Basic turn function.
     * @param turnCounter decides whose turn it is
     * @param player1Label exactly as stated
     * @param player2Label see above
     * @param finalI the column
     * @param finalJ the row
     * @param pieceCounter it decides how many pieces are placed
     */
    public void turn(int turnCounter,TextView player1Label, TextView player2Label,int finalI,int finalJ,int pieceCounter){
        if ((turnCounter % 2 == 0) && (pieceCounter <= PIECE_LIMIT)) {
            // TODO: player1 turn
            String nameOfFile = "piece" + color;
            Log.d(TAG,color);
            int photoResID = getResources().getIdentifier(nameOfFile,"drawable",getPackageName());
            imageButtons[finalI][finalJ].setImageDrawable(getDrawable(photoResID));
            board.tiles[finalI][finalJ].givePiece(new Piece("pawn",teamChoice, 1,1));
            player2Label.setVisibility(View.VISIBLE);
            player1Label.setVisibility(View.GONE);
            Log.d(TAG, "turn: Board Tile given piece " + board.tiles[finalI][finalJ].toString());

        } else if((turnCounter % 2 == 1) && (pieceCounter <= PIECE_LIMIT)) {
            //TODO: player2 turn
            String nameOfFile2 = "piece" + color2;
            Log.d(TAG,color2);
            int photoResID2 = getResources().getIdentifier(nameOfFile2,"drawable",getPackageName());
            imageButtons[finalI][finalJ].setImageDrawable(getDrawable(photoResID2));
            board.tiles[finalI][finalJ].givePiece(new Piece("pawn", teamChoice2, 1,1));
            player1Label.setVisibility(View.VISIBLE);
            player2Label.setVisibility(View.GONE);
            Log.d(TAG, "turn: Board Tile given piece " + board.tiles[finalI][finalJ].toString());
        }
    }


}
