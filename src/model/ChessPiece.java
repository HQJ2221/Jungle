package model;
import java.io.Serializable;

public class ChessPiece implements Serializable {
    // the owner of the chess
    private PlayerColor owner;

    // Elephant? Cat? Dog? ...
    private String name;
    private int rank;

    public ChessPiece(PlayerColor owner, String name, int rank) {
        this.owner = owner;
        this.name = name;
        this.rank = rank;
    }

    public boolean canCapture(ChessPiece target) {
        if (!this.getOwner().equals(target.getOwner())) {

            if (this.rank == 1 && target.rank == 8) {
                return true;
            }else if (this.rank == 8 && target.rank == 1) {
                return false;
            }else if (this.rank >= target.rank) {
                return true;
            }else {
                return false;
            }
        }
        return false;
    }//TODO: Finish this method!

    public String getName() {
        return name;
    }

    public PlayerColor getOwner() {
        return owner;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getRank() {
        return rank;
    }


}