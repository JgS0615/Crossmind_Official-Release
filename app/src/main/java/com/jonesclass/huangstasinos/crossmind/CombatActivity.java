package com.jonesclass.huangstasinos.crossmind;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AnticipateInterpolator;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.content.DialogInterface;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;

import java.time.Duration;
import java.time.Instant;

public class CombatActivity extends AppCompatActivity {

    final String[] RPS_CHOICE = {"Rock","Paper","Scissor"};
    final String TAG = "GameActivityTag";
    String teamChoice;
    String teamChoice2;
    public boolean twoPlayers;
    private String color, color2;
    ProgressBar progressBar1;
    ProgressBar progressBar2;
    ImageView player1;
    ImageView player2;
    String player1Choice = "";
    String player2Choice = "";
    Button fight;
    AlertDialog.Builder dialogBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        SplashScreen splashScreen = SplashScreen.installSplashScreen(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_combat);

        Intent startIntent = getIntent();
        twoPlayers = startIntent.getBooleanExtra("com.jonesclass.huangstasinos.crossmind.TWO_PLAYERS", true);
        teamChoice = startIntent.getStringExtra("com.jonesclass.huangstasinos.crossmind.TEAM_CHOICE_PLAYER_ONE");
        teamChoice2 = startIntent.getStringExtra("com.jonesclass.huangstasinos.crossmind.TEAM_CHOICE_PLAYER_TWO");



        progressBar1 = findViewById(R.id.progressBar_1);
        progressBar2 = findViewById(R.id.progressBar_2);
        player1 = findViewById(R.id.imageView_player1);
        player2 = findViewById(R.id.imageView_player2);




        splashScreen.setKeepOnScreenCondition(() -> false);
        splashScreen.setOnExitAnimationListener(splashScreenView -> {
            final ObjectAnimator slideUp = ObjectAnimator.ofFloat(
                    splashScreenView, String.valueOf(View.TRANSLATION_Y), 0f, splashScreenView.getView().getHeight()
            );

            slideUp.setInterpolator(new AnticipateInterpolator());
            slideUp.setDuration(1000L);

            slideUp.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    splashScreenView.remove();
                }
            });
            slideUp.start();
        });



        switch (teamChoice2) { // "BioTeam","Knights","Outlanders","Techno" //P1 (singlePlayer, and P2 for 2 Players)
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
            switch (teamChoice) {
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

            String nameOfFile = "piece" + color;
            String nameOfFile2 = "piece" + color2;
            int photoResID = getResources().getIdentifier(nameOfFile, "drawable", getPackageName());
            int photoResID2 = getResources().getIdentifier(nameOfFile2, "drawable", getPackageName());
            player1.setImageDrawable(getDrawable(photoResID));
            player2.setImageDrawable(getDrawable(photoResID2));

        }
        Log.d(TAG,player1Choice);

        Log.d(TAG,player2Choice);
    }
    public void rpsDialog() {
        dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setTitle("Choose Your Team:");
        dialogBuilder.setSingleChoiceItems(RPS_CHOICE, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                teamChoice = RPS_CHOICE[which];
                if(which == 0) {
                    player1Choice = RPS_CHOICE[0];
                } else if(which == 1) {
                    player1Choice = RPS_CHOICE[1];
                } else if(which == 2) {
                    player1Choice = RPS_CHOICE[2];
                } else {
                    player1Choice = "";
                }
                Log.d(TAG, "Player1Choice " + player1Choice);
                dialog.dismiss();
            }
        });

        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(CombatActivity.this,"No Choice Selected.", Toast.LENGTH_SHORT).show();
            }
        });
        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

    }

    public void rpsDialog2() {
        dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setTitle("Choose Your Team:");
        dialogBuilder.setSingleChoiceItems(RPS_CHOICE, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                teamChoice = RPS_CHOICE[which];
                if(which == 0) {
                    player2Choice = RPS_CHOICE[0];
                } else if(which == 1) {
                    player2Choice = RPS_CHOICE[1];
                } else if(which == 2) {
                    player2Choice = RPS_CHOICE[2];
                } else {
                    player2Choice = "";
                }
                Log.d(TAG, "Player2Choice " + player2Choice);
                dialog.dismiss();
            }
        });

        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(CombatActivity.this,"No Choice Selected.", Toast.LENGTH_SHORT).show();
            }
        });
        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();
    }



}

