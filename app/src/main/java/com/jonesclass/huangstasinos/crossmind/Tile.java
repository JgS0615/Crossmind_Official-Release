package com.jonesclass.huangstasinos.crossmind;

public class Tile {

    Piece piece = null;
    String color = "";
    String type;
    // the type of tile will decide bonuses in Damage for the different types of pieces
    // TYPES: Forest, Mountain, City, Plains
    // Forest : bonus for bioteam
    // Mountain : bonus for outlanders
    // City : bonus for techno team and knights
    // Plains : no bonus
    int locationX, locationY; // the location on the map that the tile inhabits
    boolean hasPiece = false; // starts out false if the tile has no piece

    /**
     * Default Constructor. Pretty much useless.
     */
    Tile() {
        locationX = 0;
        locationY = 0;
        type = "null";
    }

    /**
     * The parameterized constructor for if the tile starts with a piece
     * @param x the x position
     * @param y the y position
     * @param piece1 the piece currently in this tile
     * @param type the type of tile. this will decide bonuses in damage for the pieces in the tiles.
     */
    Tile(int x, int y, Piece piece1, String type) {
        locationX = x;
        locationY = y;
        piece = piece1;
        this.type = type;
        hasPiece = true;
    }

    /**
     * The parameterized constructor for if the tile does not start with a piece
     * @param x the x position
     * @param y the y position
     * @param type the type of tile. this will decide bonuses in damage for the pieces in the tiles.
     */
    Tile(int x, int y, String type) {
        locationX = x;
        locationY = y;
        this.type = type;
        piece = null;
        hasPiece = false;
    }

    /**
     * When a piece is moved into the tile
     * @param piece1 the piece being moved to the tile
     */
    public void givePiece(Piece piece1) {
        piece = piece1;
        hasPiece = true;
    }

    /**
     * when showing what tiles to reveal, will change a String that stores a color value.
     */
    public void revealTile() {
        color = "black";
    }

    /**
     * Returns a String that has the basic values of the Tile
     * @return the basic description of the object
     */
    public String toString() {
        String finalString = "";
        if (hasPiece) {
            finalString += "Has a Piece, ";
        } else {
            finalString += "Does not have a Piece, ";
        }

        finalString += "is a " + type;
        finalString += ", at position (" + locationX + ", " + locationY + "), ";

        return finalString;
    }

}
