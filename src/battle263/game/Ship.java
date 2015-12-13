package battle263.game;

import battle263.game.Piece.PieceType;
import java.io.Serializable;

public class Ship implements Serializable {
    private final int[][] map;
    private final int mapN2;
    
    private int piecesLeft;
    private boolean complex = false; //multipiece or with diagonal neigbors
    private boolean sunk = false;
    private final PieceType pieceType;
    
    //position info.
    private int[] coords = null; //of the MIDDLE of map
    
    public Ship(ShipType type) {
       
        map = new int[type.map.length][];
        for (int t = 0; t < type.map.length; t++) {
            map[t] = type.map[t].clone();
        }
       
        mapN2 = (int)(map.length/2);
        pieceType = type.pieceType;
        piecesLeft = type.piecesLeft;
        complex = type.complex;
    }
    
    //this will return board coordinate from center & ship
    public int coordX(int centerX, int shipX) {
        return centerX + -mapN2 + shipX;
    }
    
    public int coordY(int centerY, int shipY) {
        return centerY + -mapN2 + shipY;
    }
    
    public void rotate() {
        int[][] oldMap = new int[map.length][];
        for (int t = 0; t < map.length; t++) {
            oldMap[t] = map[t].clone();
        }
       
        for (int x = 0; x < oldMap.length; x++) {
            for (int y = 0; y < oldMap[0].length; y++) {
                /*
                x-mapN2, y-mapN2
                -y+mapN2, x-mapN2
                */
                map[-y + 2*mapN2][x] = oldMap[x][y];
            }
        }
    }
    
    public void randomRotation() {
        int times = (int) (Math.random()*4);
        while (times --> 0) {
            rotate();
        }
    }
    
    public void shoot() {
        piecesLeft--;
        if (piecesLeft == 0)
            sunk = true;
    }

    public int[][] getMap() {
        return map;
    }

    public void setCoords(int[] coords) {
        this.coords = coords;
    }

    public PieceType getPieceType() {
        return pieceType;
    }

    public int getPiecesLeft() {
        return piecesLeft;
    }

    public int[] getCoords() {
        return coords;
    }

    public boolean isSunk() {
        return sunk;
    }
    
    public void debugMap() {
        for (int y = 0; y < map[0].length; y++) {
            for (int x = 0; x < map.length; x++) {
                System.out.print(map[x][y] == 1 ? "#" : ".");
            }
            System.out.print("\n");
        }    
    }

    public boolean isComplex() {
        return complex;
    }
    
    
           
}
