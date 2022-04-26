package com.jonesclass.huangstasinos.crossmind;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class GameActivity extends AppCompatActivity {

    public Random rNumber = new Random();

    public int turn;
    public int points;
    public boolean twoPlayers;
    public String teamChoice;
    public String teamChoice2;
    public ImageButton[][] imageButtons = new ImageButton[5][5];
    public boolean[][] selected = new boolean[5][5];
    public int[][] values = new int[5][5];


    final String TAG = "GameActivityTag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        Log.d(TAG, "onCreate: Created");
        Intent newIntent = getIntent();

        twoPlayers = newIntent.getBooleanExtra("com.jonesclass.stasinos.ticTacSmack.TWO_PLAYERS", true);
        teamChoice = newIntent.getStringExtra("com.jonesclass.stasinos.ticTacSmack.TEAM_CHOICE_PLAYER_ONE");
        teamChoice2 = newIntent.getStringExtra("com.jonesclass.stasinos.ticTacSmack.TEAM_CHOICE_PLAYER_TWO");

        if (twoPlayers) {
            Board board = new Board(teamChoice, teamChoice2);
        } else {
            Board board = new Board(teamChoice2);
        }
    }

}
