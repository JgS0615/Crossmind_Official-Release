package com.jonesclass.huangstasinos.crossmind; 

public class Piece {
    String type;
    String affinity;
    String nullTeam = "null";
    String outlander = "Outlander";
    String knights = "Knights";
    String techno = "Techno";
    String bioTeam = "BioTeam";
    int damage;
    int health;
    int weaknessMultiplier = 2; // can be changed if you want.

    /**
     * A default constructor. Pretty much useless.
     */
    Piece() {
        type = "basic";
        affinity = nullTeam;
        damage = 1;
        health = 1;
    }

    /**
     * Parameterized constructor.
     * @param typeOfTroop the type of troop it is.
     * @param affinity the team that the troop is from
     * @param damage amt of damage it deals
     * @param health amt of health it has
     */
    Piece(String typeOfTroop, String affinity, int damage, int health) {
        this.type = typeOfTroop;
        this.affinity = affinity;
        this.damage = damage;
        this.health = health;
    }

    /**
     * For terrain bonuses/type of piece damage boosts
     * @param newDamage the new damage value you are setting
     */
    public void setDamage(int newDamage) {
        damage = newDamage;
    }
    
    /** 
    * the damage calculator
    * @param piece1 the piece doing damage to this piece
    * @param tileType the type of the tile that the piece dealing damage is on. NOT THE PIECE TAKING DAMAGE
    */
    public void damage(Piece piece1, String tileType) {
        if (piece1.affinity.equals(nullTeam)) { // Should never activate, but just in case.
            health -= piece1.damage;
        } else if(piece1.affinity.equals(outlander)) { // Outlanders
            if (tileType.equals("Mountain")) {
                piece1.setDamage(damage*2);
            }
            if (affinity.equals(bioTeam)) { // BioTeam
                health -= piece1.damage/weaknessMultiplier;
            } else if(affinity.equals(knights)) { // Knights
                health -= piece1.damage*weaknessMultiplier;
            } else { // Outlanders or Techno
                health -= piece1.damage;
            }
            if (tileType.equals("Mountain")) {
                piece1.setDamage(damage/2);
            }
        } else if(piece1.affinity.equals(knights)) { // knights
            if (tileType.equals("City")) {
                piece1.setDamage(damage*2);
            }
            if (affinity.equals(techno)) {
                health -= piece1.damage/weaknessMultiplier;
            } else if(affinity.equals(outlander)) {
                health -= piece1.damage*weaknessMultiplier;
            } else {
                health -= piece1.damage;
            }
            if (tileType.equals("City")) {
                piece1.setDamage(damage/2);
            }
        } else if(piece1.affinity.equals(techno)) { // techno
            if (tileType.equals("City")) {
                piece1.setDamage(damage*2);
            }
            if (affinity.equals(knights)) {
                health -= piece1.damage/weaknessMultiplier;
            } else if(affinity.equals(bioTeam)) {
                health -= piece1.damage*weaknessMultiplier;
            } else {
                health -= piece1.damage;
            }
            if (tileType.equals("City")) {
                piece1.setDamage(damage/2);
            }
        } else if(piece1.affinity.equals(bioTeam)) { // BioTeam
            if (tileType.equals("Forest")) {
                piece1.setDamage(damage*2);
            }
            if (affinity.equals(techno)) { // Techno
                health -= piece1.damage/weaknessMultiplier;
            } else if(affinity.equals(outlander)) { // Outlanders
                health -= piece1.damage*weaknessMultiplier;
            } else { // knights or BioTeam
                health -= piece1.damage;
            }
            if (tileType.equals("Forest")) {
                piece1.setDamage(damage/2);
            }
        }
    }
} // bioteam > Outlander > Knights > Techno > BioTeam
