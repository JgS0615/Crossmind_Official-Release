package com.jonesclass.huangstasinos.crossmind;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button startButton;
    RadioButton onePlayerButton, twoPlayerButton;
    AlertDialog.Builder dialogBuilder;
    boolean twoPlayers = true;
    final String TAG = "MainActivityTag";
    String teamChoice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        onePlayerButton = findViewById(R.id.radioButton_onePlayer);
        onePlayerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                twoPlayers = false;
            }
        });
        twoPlayerButton = findViewById(R.id.radioButton_twoPlayers);
        twoPlayerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                twoPlayers = true;
            }
        });

        startButton = findViewById(R.id.button_start);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(getApplicationContext(), GameActivity.class);
                if (twoPlayers) {
                    startIntent.putExtra( "com.jonesclass.huangstasinos.ticTacSmack.TWO_PLAYERS",true);
                    selectTeamDialog(" Player 1");
                    startIntent.putExtra("com.jonesclass.huangstasinos.ticTacSmack.TEAM_CHOICE_PLAYER_ONE", teamChoice);
                    selectTeamDialog(" Player 2");
                    startIntent.putExtra("com.jonesclass.huangstasinos.ticTacSmack.TEAM_CHOICE_PLAYER_TWO", teamChoice);
                    Log.d(TAG, "onClick: Sent 2-Player as true, and both Player's team choices");
                    startActivity(startIntent);
                } else {
                    startIntent.putExtra( "com.jonesclass.huangstasinos.ticTacSmack.TWO_PLAYERS",false);
                    selectTeamDialog("");
                    startIntent.putExtra("com.jonesclass.huangstasinos.ticTacSmack.TEAM_CHOICE_PLAYER_ONE", teamChoice);
                    Log.d(TAG,"onClick: Sent false and choice of team");
                    startActivity(startIntent);
                }
            }
        });

    }



    public void selectTeamDialog(String player) {
        dialogBuilder = new AlertDialog.Builder(this);
        final String[] TEAM_CHOICES = {"BioTeam","Knights","Outlanders","Techno"};
        dialogBuilder.setTitle("Choose Your Team" + player + ":");
        dialogBuilder.setSingleChoiceItems(TEAM_CHOICES, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                teamChoice = TEAM_CHOICES[which];
                if(which == 0) {
                    Toast.makeText(MainActivity.this,"Green Team!", Toast.LENGTH_SHORT).show();
                } else if(which == 1) {
                    Toast.makeText(MainActivity.this,"That's Sharp!", Toast.LENGTH_SHORT).show();
                } else if(which == 2) {
                    Toast.makeText(MainActivity.this,"Arr, Matey!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this,"Beep Boop", Toast.LENGTH_SHORT).show();
                }
                startButton.setVisibility(View.VISIBLE);
                dialog.dismiss();
            }
        });

        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(MainActivity.this,"No Team Selected.", Toast.LENGTH_SHORT).show();
            }
        });
        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();
    }
}