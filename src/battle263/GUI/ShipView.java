package battle263.GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.*;

import battle263.game.*;
import battle263.game.Piece.PieceType;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ShipView extends CellView implements
        MouseListener {

    private Ship ship;

    public ShipView(Ship ship) {
        setShip(ship);
        
        this.addMouseListener(this);
    }

    public void setShip(Ship ship) {
        this.ship = ship;
        if (ship != null) {
            width = ship.getMap().length;
            height = ship.getMap()[0].length;          
        } else {
            width = height = 5;
        }
        calcSize();
        repaint();
    }
   
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        calcSize();
        paintBG(g);

        //now call paintCell with proper stuff
        if (ship != null) {
            g.setColor(shipColor);
            int[][] map = ship.getMap();
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    if (map[x][y] > 0) {
                        paintRect(g, new Point(x, y));
                    }
                }
            }
        }
    }

    private void paintBG(Graphics g) {
        if (ship != null && ship.getPieceType() == PieceType.SEA)
            g.setColor(seaBG);
        else if (ship != null && ship.getPieceType() == PieceType.LAND)
            g.setColor(landBG);
        else
            g.setColor(Color.WHITE);
        
        g.fillRect(0, 0, width * (cellSize + 1) + 1, height * (cellSize + 1) + 1);
        
        if (ship != null && ship.getPieceType() == PieceType.SEA)
            g.setColor(seaFG);
        else if (ship != null && ship.getPieceType() == PieceType.LAND)
            g.setColor(landFG);
        else
            g.setColor(sepColor);

        for (int x = 0; x <= width; x++) {
            g.fillRect(x * (cellSize + 1), 0, 1, height * (cellSize + 1) + 1);
        }

        for (int y = 0; y <= height; y++) {
            g.fillRect(0, y * (cellSize + 1), width * (cellSize + 1), 1);
        }
    }
    
    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        
    }
    
    @Override
    public void mouseEntered(MouseEvent e) {

    }
    
    @Override
    public void mouseExited(MouseEvent e) {

    }
    
    @Override   
    public void mouseClicked(MouseEvent e) {
        Point pt = decodeEvent(e);
        if (pt.x < width && pt.y < height) {
            rotate();
        }
    }
    
    //override me
    protected void rotate() {
        
    }
}
