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
import java.awt.event.MouseMotionListener;

public class BoardView extends CellView implements
        MouseListener, MouseMotionListener {

    protected Board board;
    private int seaWidth;
    private boolean foreign = false;
    private Point lastActiveCell = null;

    public BoardView(Board board, boolean foreign) {
        setBoard(board);
        this.board = board;
        this.foreign = foreign;

        this.addMouseListener(this);
        this.addMouseMotionListener(this);
    }

    public void setBoard(Board board) {
        this.board = board;
        width = board.getPieces().length;
        height = board.getPieces()[0].length;
        seaWidth = board.SEA_PIECES;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        calcSize();
        paintBG(g);

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                paintCell(g, board.getPiece(x, y), new Point(x, y), x < seaWidth);
            }
        }
    }

    private void paintBG(Graphics g) {
        int seaX = seaWidth * (cellSize + 1);

        g.setColor(seaBG);
        g.fillRect(0, 0, seaX, height * (cellSize + 1) + 1);

        g.setColor(landBG);
        g.fillRect(seaX + 1, 0, (width - seaWidth) * (cellSize + 1), height * (cellSize + 1) + 1);
        g.setColor(sepColor);
        for (int x = 0; x <= width; x++) {
            g.setColor(x < seaWidth ? seaFG : (x > seaWidth ? landFG : sepColor));
            g.fillRect(x * (cellSize + 1), 0, 1, height * (cellSize + 1) + 1);

        }

        g.setColor(seaFG);
        for (int y = 0; y <= height; y++) {
            g.fillRect(0, y * (cellSize + 1), seaWidth * (cellSize + 1), 1);

        }
        g.setColor(landFG);
        for (int y = 0; y <= height; y++) {
            g.fillRect(seaWidth * (cellSize + 1) + 1, y * (cellSize + 1), (width - seaWidth) * (cellSize + 1), 1);

        }
    }

    protected void paintCell(Graphics g, Piece piece, Point pt, boolean sea) {
        if (piece.getShip() != null) {
            paintShip(g, piece, pt, sea);
        } else if (piece.isShot()) {
            paintMiss(g, piece, pt, sea);
        }
    }

    private void paintShip(Graphics g, Piece piece, Point pt, boolean sea) {
        g.setColor(shipColor);
        boolean shot = piece.isShot();

        if (!foreign || shot) {
            paintRect(g, pt);
        }

        if (shot) {
            g.setColor(piece.getShip().isSunk() ? Color.RED : Color.WHITE);
            paintOval(g, pt);
        }
    }

    private void paintMiss(Graphics g, Piece piece, Point pt, boolean sea) {
        g.setColor(sea ? seaFG : landFG);

        paintOval(g, pt);
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        //place from editor here
        Point pt = decodeEvent(e);

        if (pt.x >= width || pt.y >= height) {
            pt = null;
        }

        cellUp(pt);
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        mouseMoved(e);
    }

    @Override
    public void mouseExited(MouseEvent e) {
        mouseMoved(e);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Point pt = decodeEvent(e);

        if (pt.x >= width || pt.y >= height) {
            pt = null;
        }

        cellClicked(pt, e.getClickCount());
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        Point pt = decodeEvent(e);
        if (pt.x >= width || pt.y >= height) {
            pt = null;
        }

        if (pt != lastActiveCell && ((pt != null && !pt.equals(lastActiveCell))
                || ((lastActiveCell != null && !lastActiveCell.equals(pt))))) {
            cellOver(pt);
            lastActiveCell = pt;
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        mouseMoved(e);
    }

    protected void cellUp(Point cell) {
        //System.out.println("cellUp"+cell.toString());
    }

    protected void cellClicked(Point cell, int count) {
        //System.out.println("cellUp"+cell.toString());
    }

    protected void cellOver(Point cell) {
        //System.out.println("cellOver"+cell);
    }
}
