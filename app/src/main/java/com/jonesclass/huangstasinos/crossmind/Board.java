package com.jonesclass.huangstasinos.crossmind;

import java.util.ArrayList;
import java.util.Random;

public class Board {
    Piece piece = new Piece();
    Tile[][] tiles = new Tile[5][5];
    Random rNumber = new Random();

    /**
     * SINGLE PLAYER BOARD
     * @param teamChosen the team that the player chooses
     */
    Board(String teamChosen) {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                tiles[i][j] = new Tile(i,j,tileTypeChooser(rNumber.nextInt(5)));
            }
        }
    }

    /**
     * TWO PLAYER BOARD
     * @param teamChosen1 first player's team
     * @param teamChosen2 second player's team
     */
    Board(String teamChosen1, String teamChosen2) {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                tiles[i][j] = new Tile(i,j,tileTypeChooser(rNumber.nextInt(5)));
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
