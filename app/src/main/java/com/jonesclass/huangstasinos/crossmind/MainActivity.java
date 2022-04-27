package com.jonesclass.huangstasinos.crossmind;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.AnticipateInterpolator;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    //Animations
    Animation topanim;

    //Variables

    final String[] TEAM_CHOICES = {"BioTeam","Knights","Outlanders","Techno"};
    public Random rNumber = new Random();
    Button startButton;
    RadioButton onePlayerButton, twoPlayerButton;
    AlertDialog.Builder dialogBuilder;
    boolean twoPlayers = true;
    final String TAG = "MainActivityTag";
    String teamChoice;
    Intent startIntent;
    String randomChoice = TEAM_CHOICES[rNumber.nextInt(TEAM_CHOICES.length)];

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        SplashScreen splashScreen = SplashScreen.installSplashScreen(this);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        splashScreen.setKeepOnScreenCondition(() -> false);
        splashScreen.setOnExitAnimationListener(splashScreenView -> {
            final ObjectAnimator slideUp = ObjectAnimator.ofFloat(
                    splashScreenView, String.valueOf(View.TRANSLATION_Y),0f, splashScreenView.getView().getHeight()
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


        //Animations
        topanim = AnimationUtils.loadAnimation(this,R.anim.topanim);
        topanim.setDuration(1500);

        //Set Animations for Menu

        TextView textView_welcome = findViewById(R.id.textView_welcome);
        TextView textView_to = findViewById(R.id.textView_to);
        TextView textView_crossmind = findViewById(R.id.textView_crossmind);

        textView_welcome.setAnimation(topanim);
        textView_to.setAnimation(topanim);
        textView_crossmind.setAnimation(topanim);

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
                startIntent = new Intent(getApplicationContext(), GameActivity.class);
                if (twoPlayers) {
                    startIntent.putExtra( "com.jonesclass.huangstasinos.crossmind.TWO_PLAYERS",true);
                    selectTeamDialog2P();
                    startIntent.putExtra("com.jonesclass.huangstasinos.crossmind.TEAM_CHOICE_PLAYER_ONE", teamChoice);

                    startIntent.putExtra("com.jonesclass.huangstasinos.crossmind.TEAM_CHOICE_PLAYER_TWO", teamChoice);

                    selectTeamDialog();
                    Log.d(TAG, "onClick: Sent 2-Player as true, and both Player's team choices");
                } else {
                    startIntent.putExtra( "com.jonesclass.huangstasinos.crossmind.TWO_PLAYERS",false);
                    selectTeamDialog();
                    Log.d(TAG,"onClick: Sent false and choice of team");
                }
            }
        });

    }



    public void selectTeamDialog() {
        dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setTitle("Choose Your Team:");
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
                    Toast.makeText(MainActivity.this,"Beep Boop!", Toast.LENGTH_SHORT).show();
                }
                Log.d(TAG, "We got this far...");

                startIntent.putExtra("com.jonesclass.huangstasinos.crossmind.TEAM_CHOICE_PLAYER_ONE", randomChoice);
                startIntent.putExtra("com.jonesclass.huangstasinos.crossmind.TEAM_CHOICE_PLAYER_TWO", teamChoice);

                startActivity(startIntent);
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

    public void selectTeamDialog2P() {
        dialogBuilder = new AlertDialog.Builder(this);
        final String[] TEAM_CHOICES = {"BioTeam","Knights","Outlanders","Techno"};
        dialogBuilder.setTitle("Choose Your Team:");
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
                System.out.println(teamChoice);
                System.out.println(TEAM_CHOICES[rNumber.nextInt(TEAM_CHOICES.length)]);
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