package battle263.game;

import battle263.game.Piece.PieceType;
/*
 this is my own Anti-Pattern, let's call it "Preset Anti-Pattern"
*/

public enum ShipType {

    AIRCRAFT(new int[][]{{0, 0, 0, 0, 0},
                         {0, 1, 0, 0, 0},
                         {0, 1, 1, 1, 0},
                         {0, 1, 0, 0, 0},
                         {0, 0, 0, 0, 0}}, PieceType.ALL, 5, true),
    
    SEA1(new int[][]{{0, 0, 0, 0, 0},
                     {0, 0, 0, 0, 0},
                     {0, 0, 1, 0, 0},
                     {0, 0, 0, 0, 0},
                     {0, 0, 0, 0, 0}}, PieceType.SEA, 1, false),
    
    SEA2(new int[][]{{0, 0, 0, 0, 0},
                     {0, 0, 0, 0, 0},
                     {0, 1, 1, 0, 0},
                     {0, 0, 0, 0, 0},
                     {0, 0, 0, 0, 0}}, PieceType.SEA, 2, false),
    
    SEA3(new int[][]{{0, 0, 0, 0, 0},
                     {0, 0, 0, 0, 0},
                     {0, 1, 1, 1, 0},
                     {0, 0, 0, 0, 0},
                     {0, 0, 0, 0, 0}}, PieceType.SEA, 3, false),
    
    SEA4(new int[][]{{0, 0, 0, 0, 0},
                     {0, 0, 0, 0, 0},
                     {1, 1, 1, 1, 0},
                     {0, 0, 0, 0, 0},
                     {0, 0, 0, 0, 0}}, PieceType.SEA, 4, false),
    
    LAND2(new int[][]{{0, 0, 0, 0, 0},
                      {0, 0, 0, 0, 0},
                      {0, 1, 1, 0, 0},
                      {0, 0, 0, 0, 0},
                      {0, 0, 0, 0, 0}}, PieceType.LAND, 2, false),
    
    LAND3(new int[][]{{0, 0, 0, 0, 0},
                      {0, 0, 0, 0, 0},
                      {0, 1, 1, 1, 0},
                      {0, 0, 0, 0, 0},
                      {0, 0, 0, 0, 0}}, PieceType.LAND, 3, false),
    
    LAND4(new int[][]{{0, 0, 0, 0, 0},
                      {0, 0, 0, 0, 0},
                      {1, 1, 1, 1, 0},
                      {0, 0, 0, 0, 0},
                      {0, 0, 0, 0, 0}}, PieceType.LAND, 4, false);

    public final int[][] map;
    public final PieceType pieceType;
    public final int piecesLeft;
    public final boolean complex;
    

    private ShipType(int[][] map, PieceType pieceType, int piecesLeft,
            boolean complex) {
        this.map = map;
        this.pieceType = pieceType;
        this.piecesLeft = piecesLeft;
        this.complex = complex;
    }
}
