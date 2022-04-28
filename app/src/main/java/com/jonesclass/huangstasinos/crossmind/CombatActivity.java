package com.jonesclass.huangstasinos.crossmind;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnticipateInterpolator;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;

import java.time.Duration;
import java.time.Instant;

public class CombatActivity extends AppCompatActivity {

    final String TAG = "GameActivityTag";
    String teamChoice;
    String teamChoice2;
    public boolean twoPlayers;
    private String color, color2;
    ProgressBar progressBar1;
    ProgressBar progressBar2;
    ImageView player1;
    ImageView player2;
    Button fight;

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
        fight = findViewById(R.id.button_Fight);


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
    }
}
