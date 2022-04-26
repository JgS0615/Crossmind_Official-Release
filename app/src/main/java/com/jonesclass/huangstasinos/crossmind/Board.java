package com.jonesclass.huangstasinos.crossmind;

import java.util.ArrayList;
import java.util.Random;

public class Board {
    Piece piece;
    ArrayList<Tile> tiles;
    Random rNumber = new Random();

    /**
     * SINGLE PLAYER BOARD
     * @param teamChosen the team that the player chooses
     */
    Board(String teamChosen) {
        piece = new Piece("pawn", teamChosen, 1, 10);
        for (int i = 0; i < 5; i ++) {
            for (int j = 0; j < 5; j++) {
                Tile tile = new Tile(i,j,tileTypeChooser(rNumber.nextInt(6)));
                tiles.add(tile);
            }
        }
        for (int i = 0; i < tiles.size(); i ++ ) {
            if (tiles.get(i).locationY < 2) {
                piece.affinity = enemyAffinityChooser(rNumber.nextInt(4));
            } else if(tiles.get(i).locationY > 2) {
                tiles.get(i).givePiece(piece);
            }
        }
    }

    /**
     * TWO PLAYER BOARD
     * @param teamChosen1 first player's team
     * @param teamChosen2 second player's team
     */
    Board(String teamChosen1, String teamChosen2) {
        piece = new Piece("pawn", teamChosen1, 1, 10);
        for (int i = 0; i < 5; i ++) {
            for (int j = 0; j < 5; j++) {
                Tile tile = new Tile(i,j,tileTypeChooser(rNumber.nextInt(6)));
                tiles.add(tile);
            }
        }
        for (int i = 0; i < tiles.size(); i ++ ) {
            if (tiles.get(i).locationY < 2) { // player 2 on top
                piece.affinity = teamChosen2;
            } else if(tiles.get(i).locationY > 2) { // player 1 on bottom
                tiles.get(i).givePiece(piece);
            }
        }
    }

    /**
     * chooses each tile's type of land
     * @param landType the integer that decides what tpe of tile it is
     * @return the type of tile chosen
     */
    private String tileTypeChooser(int landType) {
        switch(landType) {
            case 0: return "Mountain";
            case 1: return "City";
            case 2: return "Forest";
            default: return "Plains";
        }
    }

    /**
     * chooses a team for the enemy's side. is random for now.
     * @param affinity the integer that decides what team to choose
     * @return the chosen team
     */
    private String enemyAffinityChooser(int affinity) {
        switch(affinity) {
            case 0: return piece.bioTeam;
            case 1: return piece.knights;
            case 2: return piece.outlander;
            default: return piece.techno;
        }
    }
}
