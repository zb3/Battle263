package battle263.GUI;

import java.awt.Point;
import battle263.game.*;
import java.awt.Color;
import java.awt.Graphics;

public class EditorView extends BoardView {

    public static final Color reservedColor = new Color(0, 0, 0, 0.1f);
    private Ship activeShip;
    private Point currentPoint;

    public EditorView(Board board) {
        super(board, false);
    }

    public void setShip(Ship ship) {
        this.activeShip = ship;
    }

    public void setCurrentPoint(Point newPoint) {
        currentPoint = newPoint;
    }
//RACE condition after placing ship 
    @Override
    protected void paintCell(Graphics g, Piece piece, Point pt, boolean sea) {
        super.paintCell(g, piece, pt, sea);

        //paint reserved
        if (!board.isPieceFree(piece) && piece.getShip() == null) {
            g.setColor(reservedColor);
            paintRect(g, pt);
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (activeShip != null && currentPoint != null) {
            g.setColor(shipColor);
            
            int[][] map = activeShip.getMap();
            for (int x = 0; x < map.length; x++) {
                for (int y = 0; y < map.length; y++) {
                    if (map[x][y] == 0) {
                        continue;
                    }

                    Piece p = board.getPiece(activeShip.coordX(currentPoint.x, x),
                            activeShip.coordY(currentPoint.y, y));
                    
                    if (p != null) {
                        paintOval(g, new Point(activeShip.coordX(currentPoint.x, x), 
                                activeShip.coordY(currentPoint.y, y)));
                    }

                }
            }
        }
    }
}
