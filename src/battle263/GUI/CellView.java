package battle263.GUI;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import javax.swing.JPanel;

public abstract class CellView extends JPanel {
    public static final Color seaBG = new Color(234, 255, 255),
            seaFG = new Color(0, 187, 255),
            landBG = new Color(253, 253, 227),
            landFG = new Color(168, 168, 36),
            sepColor = new Color(90, 90, 90),
            shipColor = new Color(60, 60, 60);
    
    protected int width;
    protected int height;
    protected int cellSize = 40;
    
    protected void calcSize() {
        cellSize = Math.min((int) ((getWidth() - 1) / width - 1),
                (int) ((getHeight() - 1) / height - 1));
        if (cellSize % 2 == 0) {
            cellSize--;
        }
    }
        

    protected Point decodeEvent(MouseEvent e) {
        //actually include border to prevent from flickering on drag
        Point ret = new Point();
        ret.x = e.getX()/(cellSize+1);
        ret.y = e.getY()/(cellSize+1);
        return ret;
    }
    
    protected void paintOval(Graphics g, Point cell) {
        int sht = cellSize / 2;
        if (sht % 2 == 0) {
            sht--;
        }

        g.fillOval((cellSize + 1) * cell.x + 1 + cellSize / 2 - sht / 2,
                (cellSize + 1) * cell.y + 1 + cellSize / 2 - sht / 2, sht, sht);
    }
    
    protected void paintRect(Graphics g, Point cell) {
        g.fillRect((cellSize + 1) * cell.x, (cellSize + 1) * cell.y, cellSize + 2, cellSize + 2);
    }
}
