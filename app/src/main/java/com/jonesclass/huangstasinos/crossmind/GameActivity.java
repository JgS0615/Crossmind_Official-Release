package com.jonesclass.huangstasinos.crossmind;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class GameActivity extends AppCompatActivity {

    public Random rNumber = new Random();

    private int winningNumber = 11;
    public int rockWins = 0;
    public int scissorsWins = 0;
    public int turn;
    public boolean twoPlayers;
    public String teamChoice;
    public TextView messageTextView;
    public TextView scissorsTextView;
    public TextView rocksTextView;
    public ImageButton[][] imageButtons = new ImageButton[3][3];
    public boolean[][] selected = new boolean[3][3];
    public int[][] values = new int[3][3];

    final String TAG = "GameActivityTag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);


    }

}
