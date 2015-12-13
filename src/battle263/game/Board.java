package battle263.game;

import battle263.game.Piece.PieceType;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;


public class Board implements Serializable {
    public final int SEA_PIECES = 14; //so editor can draw a line
    
    private final Piece[][] pieces = new Piece[22][14]; //14 for sea, 8 for land
    private final Ship[] ships = new Ship[12];

    //successful shots
    private int shotsFired = 0;
    private int shotsFiredAll = 0;
    private int shotsLeft = 0;
    private int shipsLeft = ships.length;
    private int complexShipsLeft = 0;
    private int[] lastShot = null;
    
    public Board() {
        //number of ships, and order, here.
        ships[0] = new Ship(ShipType.AIRCRAFT);
        ships[1] = new Ship(ShipType.SEA4);
        ships[2] = new Ship(ShipType.SEA3);
        ships[3] = new Ship(ShipType.SEA2);
        ships[4] = new Ship(ShipType.SEA2);
        ships[5] = new Ship(ShipType.SEA1);
        ships[6] = new Ship(ShipType.SEA1);
        ships[7] = new Ship(ShipType.SEA1);
        ships[8] = new Ship(ShipType.LAND4);
        ships[9] = new Ship(ShipType.LAND3);
        ships[10] = new Ship(ShipType.LAND2);
        ships[11] = new Ship(ShipType.LAND2);
        //they have no positions yet so GUI will know what to place
        
        //sum pieces left
        for (Ship s: ships) {
            shotsLeft += s.getPiecesLeft();
            if (s.isComplex())
                complexShipsLeft++;
        }
        
        //sea, land layout here
        for(int y=0;y<pieces[0].length;y++) {
            for(int x=0;x<SEA_PIECES;x++) {
                pieces[x][y] = new Piece(PieceType.SEA, x, y);
            }
            for(int x=SEA_PIECES;x<pieces.length;x++) {
                pieces[x][y] = new Piece(PieceType.LAND, x, y);
            }
        }
    }
    
    public Piece getPiece(int x, int y) {
        if (x >= 0 && x < pieces.length && y >= 0 && y < pieces[0].length) {
            return pieces[x][y];
        } else 
            return null;
    }
    
    public void attachShip(Ship ship, int[] coords) {
        if (testPlacement(ship, coords) != 0) return;
        
        /*
        for each ship map place, we map [x, y] from ship coords to
        board coords
        */
        int[][] map = ship.getMap();
        for (int x = 0; x<map.length;x++) {
            for (int y = 0; y < map.length; y++) {
                if (map[x][y] == 0) continue;
                
                Piece p = getPiece(ship.coordX(coords[0], x), ship.coordY(coords[1], y));
                
                if (p != null) {
                    p.setShip(ship);
                }
            }
        }
        
        ship.setCoords(coords);
    }
    
    public void detachShip(Ship ship) {
        for (int x = 0; x < pieces.length; x++) {
            for (int y = 0; y < pieces[0].length; y++) {
                if (pieces[x][y].getShip() == ship) {
                    pieces[x][y].setShip(null);
                }
            }
        }
        
        ship.setCoords(null);
    }
    
    public boolean isPieceFree(int x, int y) {
        boolean ret = true;
        Piece tmp;
        
        ret = ret && (((tmp=getPiece(x-1, y-1)) == null) || tmp.getShip() == null);
        ret = ret && (((tmp=getPiece(x-1, y)) == null) || tmp.getShip() == null);
        ret = ret && (((tmp=getPiece(x-1, y+1)) == null) || tmp.getShip() == null);
        ret = ret && (((tmp=getPiece(x, y-1)) == null) || tmp.getShip() == null);
        ret = ret && (((tmp=getPiece(x, y)) == null) || tmp.getShip() == null);
        ret = ret && (((tmp=getPiece(x, y+1)) == null) || tmp.getShip() == null);
        ret = ret && (((tmp=getPiece(x+1, y-1)) == null) || tmp.getShip() == null);
        ret = ret && (((tmp=getPiece(x+1, y)) == null) || tmp.getShip() == null);
        ret = ret && (((tmp=getPiece(x+1, y+1)) == null) || tmp.getShip() == null);
        
        return ret;
    }
    
    public boolean isPieceFree(Piece p) {
        return isPieceFree(p.getX(), p.getY());
    }
    
    /**
     *
     * @return 1 - out of bounds, 2 - incompatible piece type, 3 - collision
     */
    public int testPlacement(Ship ship, int[] coords) {
        PieceType pt = ship.getPieceType();
        int[][] map = ship.getMap();
        for (int x = 0; x<map.length;x++) {
            for (int y = 0; y < map.length; y++) {
                if (map[x][y] == 0) continue;
                
                Piece p = getPiece(ship.coordX(coords[0], x), ship.coordY(coords[1], y));
                if (p == null)
                    return 1;
                
                if (pt != PieceType.ALL && p.getType()!=pt)
                    return 2;
                
                if (!isPieceFree(p)) {
                    return 3;
                }
            }
        }
        
        return 0;
    }
    
    public boolean attachRandomly() {
        ArrayList<Ship> placedShips = new ArrayList();
        int triesLeft = 1000; //coz sometimes, it's just impossible
        boolean success = false;
        Random rnd = new Random();
        
        for(Ship s: ships) {
            if (s.getCoords() != null)
                placedShips.add(s);
        }
        
        while(!success && triesLeft --> 0) {
            Queue<Ship> shipQueue = new LinkedList();
            
            int tmp;
            shipLoop: for (tmp = 0; tmp < ships.length; tmp++) {
                if (ships[tmp].getCoords() != null) continue;
                ships[tmp].randomRotation();
                //try to place ship
                ArrayList<int[]> possibilities = new ArrayList();
                
                for (int x = 0; x < pieces.length; x++) {
                    for (int y = 0; y < pieces[0].length; y++) {
                        int[] coords = new int[] {x, y};
                        if (testPlacement(ships[tmp], coords) == 0) {
                            possibilities.add(coords);
                        }
                    }
                }
                
                if (possibilities.isEmpty()) {
                    break;
                }
 
                int[] newPlace = possibilities.get(rnd.nextInt(possibilities.size()));
                attachShip(ships[tmp], newPlace);
            }
            
            if (tmp == ships.length) {
                success = true;
            } else {
                //failed to place ships, reset and start again
                for(Ship s: ships) {
                    if (s.getCoords() != null && !placedShips.contains(s))
                        detachShip(s);
                }
            }
        }
        return success;
    }
    
    //do we know that no ship can be @ this place (as enemy)?
    public boolean shipPossible(int x, int y) {
        int[][] corners = {{x-1, y-1}, {x-1, y+1}, {x+1, y-1}, {x+1, y+1}};
        int[][] allNeighbors = {{x-1, y}, {x+1, y}, {x, y-1}, {x, y+1},
            {x-1, y-1}, {x-1, y+1}, {x+1, y-1}, {x+1, y+1}};
        Piece p;
        
        if (complexShipsLeft == 0) { //no planes here
            for(int[] coords : corners) {
                p = getPiece(coords[0], coords[1]);
                if (p != null && p.isShot() && p.getShip() != null)
                    return false;                        
            }   
            
            /*
            only complex ships can occupy more than one piece type.
            
            technically, we should check for not sunk ships.
            but if we find a sunk ship here, we'll not have to search further
            */
            for(int[] coords : allNeighbors) { //ship on a different piece?
                p = getPiece(coords[0], coords[1]);
                if (p != null && p.isShot() && p.getShip() != null && 
                        p.getShip().getPieceType() != pieces[x][y].getType())
                    return false;                        
            }
        }
        
        for(int[] coords : allNeighbors) { //sunk ship nearby? don't shoot
            p = getPiece(coords[0], coords[1]);
            if (p != null && p.isShot() && p.getShip() != null &&
                    p.getShip().isSunk())
                return false;                        
        } 
        
        return true;
    }
    
    
    //in this function, we pretend that we don't know what the board is XD
    //yep, this places a shot in THIS
    public void AIShoot() {
        /*
        
        this AI makes heavy use of the hints - doesn't shoot on the places
        that are adjacent. if no planes left, we know even more - no ship @ the corner!
        
        it tries to finish existing ship.
        
        also if no planes left, it doesn't shoot @ the sea, when it knows that
        all ships on the sea have been sunk
        
        NOT implemented:
        -> check minimum ship size that can be sunk, and verify recursively,
           if enough free pieces near the shoot point    
        -> probability analysis...
        
        */
        ArrayList<int[]> possibilities = new ArrayList();
        int[] target = null;
        Piece p;
        Random rnd = new Random();
        
        boolean landShipsLeft = true, seaShipsLeft = true;
        if (complexShipsLeft == 0) { //planes not present, so we know what's left
            landShipsLeft = seaShipsLeft = false;
            for(Ship ship : ships) {
                if (!ship.isSunk() && ship.getPieceType() == PieceType.LAND) {
                    landShipsLeft = true;
                } else if (!ship.isSunk() && ship.getPieceType() == PieceType.SEA) {
                    seaShipsLeft = true;
                }
            }
        }
       
        outer: for (int x = 0; x < pieces.length; x++) {
            search: for (int y = 0; y < pieces[0].length; y++) {
                if (!pieces[x][y].isShot() &&
                        (seaShipsLeft || pieces[x][y].getType() != PieceType.SEA) && 
                        (landShipsLeft || pieces[x][y].getType() != PieceType.LAND)) {
                    //check if we should shoot this
                    
                    if (!shipPossible(x, y))
                        continue search;

                    int[][] neighbors = {{x-1, y}, {x+1, y},
                                        {x, y-1}, {x, y+1}};
                    
                    //try to sink ships that we've already shot
                    //but shoot at the compatible piece types only
                    //so if there are no planes, don't cross-shoot
                    for(int[] coords : neighbors) {
                        p = getPiece(coords[0], coords[1]);
                        if (p != null && p.isShot() && p.getShip() != null &&
                            !p.getShip().isSunk()) {
                            target = new int[] {x, y};
                            break outer;
                        }                        
                    }  
                    
                    possibilities.add(new int[] {x, y});
                }
            }
        }

        if (target == null) {
            target = possibilities.get(rnd.nextInt(possibilities.size()));
        }
        
        shoot(target);
    }
               
    public boolean shoot(int[] coords) {
        //but we'll shoot using game.shoot or game.aishoot
        //coz we need to update game state

        Piece p = pieces[coords[0]][coords[1]];
        if (p.isShot()) return false;
        
        p.setShot();
        Ship s = p.getShip();
        if (s != null) {
            s.shoot();
            if (s.isSunk()) {
                shipsLeft--;
                if (s.isComplex())
                    complexShipsLeft--;
            }
            
         shotsFired++;
         shotsLeft--;
        }
        shotsFiredAll++;
        lastShot = coords;
        
        //this helps user make better decisions, but doesn't reveal anything
        outer: for (int x = 0; x < pieces.length; x++) {
            for (int y = 0; y < pieces[0].length; y++) {
                if (!pieces[x][y].isShot() && !shipPossible(x, y)) {
                    shoot(new int[] {x, y});
                    break outer; //above will check the rest
                }
            }
        }
        
        return true;
    }
    
    public void debugBoard() {
        for (int y = 0; y < pieces[0].length; y++) {
            for (int x = 0; x < pieces.length; x++) {
                System.out.print(pieces[x][y].getShip() == null ? 
                        (pieces[x][y].isShot()? "s" :
                                (pieces[x][y].getType() == PieceType.SEA ?
                                "." : ":")) :
                        (pieces[x][y].isShot()? "S" : "#"));
            }
            System.out.print("\n");
        }
    }
    
    public Piece[][] getPieces() {
        return pieces;
    }

    public Ship[] getShips() {
        return ships;
    }

    public int getShotsFired() {
        return shotsFired;
    }

    public int getShotsFiredAll() {
        return shotsFiredAll;
    }

    public int getShotsLeft() {
        return shotsLeft;
    }

    public int getShipsLeft() {
        return shipsLeft;
    }
    
    public int[] getLastShot() {
        return lastShot;
    }
    
    public void init4Test() {
        this.attachRandomly();
        boolean oneShot = false;
        for (int x = 0; x < pieces.length; x++) {
            for (int y = 0; y < pieces[0].length; y++) {
                if (pieces[x][y].getShip()==ships[0])
                    shoot(new int[] {x, y});
                else if (pieces[x][y].getShip()==ships[1] && !oneShot) {
                    shoot(new int[] {x, y});
                    oneShot = true;
                }
            }
        }
    }
}
