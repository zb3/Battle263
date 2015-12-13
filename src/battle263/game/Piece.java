package battle263.game;

import java.io.Serializable;

public class Piece implements Serializable {
    public static enum PieceType {
        SEA, LAND, ALL
    }
    
    private int x;
    private int y;
    private Ship ship = null; //initialisation overkill, I know...
    private boolean shot = false;
    private PieceType type;

    public Piece(PieceType type, int x, int y) {
        this.type = type;
        this.x = x;
        this.y = y;
    }
    
    public void setShip(Ship ship) {
        this.ship = ship;
    }

    public Ship getShip() {
        return ship;
    }

    public boolean isShot() {
        return shot;
    }

    public PieceType getType() {
        return type;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setShot() {
        this.shot = true;
    }
    
    
}
