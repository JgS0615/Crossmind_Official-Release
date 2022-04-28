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
import android.widget.Toast;
import android.widget.ToggleButton;
import java.util.ArrayList;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Collections;
import java.util.Random;

public class GameActivity extends AppCompatActivity {


    public Random rNumber = new Random();
    Board board;
    private final ArrayList<String> TEAMS = new ArrayList<>();
    private int turnCounter = 0;
    private int pieceCounter = 0;
    private final int PIECE_LIMIT = 10;
    public boolean twoPlayers;
    public String teamChoice;
    public String teamChoice2;
    private String color, color2;
    private ImageButton[][] imageButtons = new ImageButton[5][5];
    private Boolean[][] pieceClickable = new Boolean[5][5];
    private boolean sound = true;
    private boolean place;
    public boolean select = false;
    public int selectedPieceX,selectedPieceY;
    ToggleButton placePiece;
    TextView toggleLabel;
    Button fightButton;

    

    final String TAG = "GameActivityTag";
    public MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);


        //Media player
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
        toggleLabel = findViewById(R.id.textView_toggle);
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

        fightButton = findViewById(R.id.button_combat);
        fightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent combatIntent = new Intent(getApplicationContext(),CombatActivity.class);
                combatIntent.putExtra("com.jonesclass.huangstasinos.crossmind.TEAM_CHOICE_PLAYER_ONE", teamChoice);
                combatIntent.putExtra("com.jonesclass.huangstasinos.crossmind.TEAM_CHOICE_PLAYER_TWO", teamChoice2);
                startActivity(combatIntent);
            }
        });



        placePiece = findViewById(R.id.toggleButton_placePiece);
        placePiece.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                place = !place;
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
                pieceClickable[i][j] = true;
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
                        turn(player1Label,player2Label,finalI,finalJ);
                    }
                });
            }
        }
    }

    public void turn(TextView player1Label, TextView player2Label,int finalI,int finalJ){
        if(!select) {
            if ((turnCounter % 2 == 0) && (pieceCounter <= PIECE_LIMIT)) {
                String nameOfFile = "piece" + color;
                int photoResID = getResources().getIdentifier(nameOfFile, "drawable", getPackageName());
                if ((place == true) && pieceClickable[finalI][finalJ]) {
                    this.pieceCounter++;
                    this.turnCounter++;
                    imageButtons[finalI][finalJ].setImageDrawable(getDrawable(photoResID));
                    board.tiles[finalI][finalJ].givePiece(new Piece("pawn", teamChoice, 1, 1));
                    board.tiles[finalI][finalJ].hasPiece = true;
                    pieceClickable[finalI][finalJ] = false;
                    player2Label.setVisibility(View.VISIBLE);
                    player1Label.setVisibility(View.GONE);
                    if (pieceCounter == PIECE_LIMIT) {
                        placePiece.setChecked(false);
                        placePiece.setVisibility(View.GONE);
                        toggleLabel.setVisibility(View.GONE);
                        pieceCounter = 1000;
                        place = false;
                        Log.d(TAG, "turn: Done placing Pieces");
                    }

                }
            } else if ((turnCounter % 2 == 1) && (pieceCounter <= PIECE_LIMIT)) {
                String nameOfFile2 = "piece" + color2;
                int photoResID2 = getResources().getIdentifier(nameOfFile2, "drawable", getPackageName());
                if ((place == true) && pieceClickable[finalI][finalJ]) {
                    this.pieceCounter++;
                    this.turnCounter++;
                    imageButtons[finalI][finalJ].setImageDrawable(getDrawable(photoResID2));
                    board.tiles[finalI][finalJ].givePiece(new Piece("pawn", teamChoice2, 1, 1));
                    board.tiles[finalI][finalJ].hasPiece = true;
                    pieceClickable[finalI][finalJ] = false;
                    player1Label.setVisibility(View.VISIBLE);
                    player2Label.setVisibility(View.GONE);
                    if (pieceCounter == PIECE_LIMIT) {
                        placePiece.setChecked(false);
                        placePiece.setVisibility(View.GONE);
                        toggleLabel.setVisibility(View.GONE);
                        pieceCounter = 1000;
                        place = false;
                        Log.d(TAG, "turn: Done placing Pieces");
                    }
                }
            } else if (board.tiles[finalI][finalJ].hasPiece) {
                Log.d(TAG, "turn: MAKES IT HERE");
                String turn = "";
                switch (turnCounter % 2) {
                    case 1:
                        turn = teamChoice2;
                        break;
                    default:
                        turn = teamChoice;
                }
                if (board.tiles[finalI][finalJ].piece.affinity.equals(turn)) {
                    select = true;
                    place = false;
                    selectedPieceX = finalI;
                    selectedPieceY = finalJ;
                    Log.d(TAG, "turn: Board Tile selected.");
                } else {
                    Toast.makeText(GameActivity.this,"Please choose your own piece to move.", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, "Chose wrong piece.");
                }
            }
        }  else if (board.tiles[finalI][finalJ].hasPiece) {
            //TODO: Implement fight function HERE
            fightButton.setVisibility(View.VISIBLE);
            turnCounter ++;
            select = false;
        } else if (!board.tiles[finalI][finalJ].hasPiece) {
            String turn = "";
            String color = "";
            switch (turnCounter % 2) {
                case 1:
                    turn = teamChoice2;
                    color = color2;
                    break;
                default:
                    turn = teamChoice;
                    color = this.color;
            }
            turnCounter ++;
            switch (turnCounter % 2) {
                case 1:
                    player1Label.setVisibility(View.GONE);
                    player2Label.setVisibility(View.VISIBLE);
                    break;
                default:
                    player1Label.setVisibility(View.VISIBLE);
                    player2Label.setVisibility(View.GONE);
            }
            board.tiles[finalI][finalJ].givePiece(board.tiles[selectedPieceX][selectedPieceY].piece);
            board.tiles[finalI][finalJ].hasPiece = true;
            String nameOfFile = "piece" + color;
            int photoResID = getResources().getIdentifier(nameOfFile, "drawable", getPackageName());
            imageButtons[finalI][finalJ].setImageDrawable(getDrawable(photoResID));
            board.tiles[selectedPieceX][selectedPieceY].hasPiece = false;
            imageButtons[selectedPieceX][selectedPieceY].setImageDrawable(getDrawable(R.drawable.blank));
            select = false;
            Log.d(TAG, "turn: Board Tile given piece " + board.tiles[finalI][finalJ].toString());
        }
    }

}
