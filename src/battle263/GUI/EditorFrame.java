package battle263.GUI;

import battle263.ObjectSaver;
import battle263.game.*;
import battle263.game.Piece.PieceType;
import java.awt.Point;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class EditorFrame extends JDialog implements WindowListener {

    private Board board = new Board();
    private Ship[] ships = board.getShips();
    private Ship ship;
    private static final String shipsTemplate = "<html><table><tr><td align=right><b>Ships placed:</b></td><td><b>%d / %d</b></td></tr><tr><td align=right>Land:</td><td>%d / %d</td></tr><tr><td align=right>Sea:</td><td>%d / %d</td></tr></table></html>";
    private static final String badPiece = "<html><b><font color=red>Incompatible piece<br>type for this ship.</font></b><html>";
    private static final String adjacentShips = "<html><b><font color=red>Ships can't be placed<br>near each other.</font></b><html>";
    
    public EditorFrame(JFrame parent) {
        super(parent);
        setModal(true);
        
        initComponents();
        setLocationRelativeTo(parent);
        update();
        setVisible(true);
    }

    public void setBoard(Board board) {
        this.board = board;
        this.ships = board.getShips();
        editorView.setBoard(board);
        update();
    }

    public void update() {
        ship = nextFree();
        shipView.setShip(ship);
        int[] shipStats = getShipStats();
        shipsPlaced.setText(String.format(shipsTemplate, shipStats[0],
                shipStats[1], shipStats[2], shipStats[3], shipStats[4],
                shipStats[5]));
        editorView.setShip(ship);
        editorView.repaint();
        okButton.setEnabled(ship == null);
        saveButton.setEnabled(ship == null);
    }

    private void initComponents() {

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        addWindowListener(this);

        editorView = new EditorView(board) {

            @Override
            protected void cellOver(Point cell) {
                updateCurrentPoint(cell);
            }

            @Override
            protected void cellClicked(Point cell, int count) {
                cellClick(cell, count);
            }
            
        }; //tmp
        help1 = new javax.swing.JLabel();
        shipsPlaced = new javax.swing.JLabel();
        help2 = new javax.swing.JLabel();
        okButton = new javax.swing.JButton();
        shipView = new ShipView(null) {
            @Override
            protected void rotate() {
                rotateShip();
            }
        };
        clearButton = new javax.swing.JButton();
        randomButton = new javax.swing.JButton();
        loadButton = new javax.swing.JButton();
        saveButton = new javax.swing.JButton();
        chooser = new javax.swing.JFileChooser();
        errorMessage = new javax.swing.JLabel();

        setTitle("Board editor");

        editorView.setPreferredSize(new java.awt.Dimension(573, 365));

        javax.swing.GroupLayout editorViewLayout = new javax.swing.GroupLayout(editorView);
        editorView.setLayout(editorViewLayout);
        editorViewLayout.setHorizontalGroup(
                editorViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGap(0, 573, Short.MAX_VALUE)
        );
        editorViewLayout.setVerticalGroup(
                editorViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGap(0, 365, Short.MAX_VALUE)
        );

        help1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        help1.setText("Click to rotate the ship");

        shipsPlaced.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        shipsPlaced.setText("<html><table><tr><td align=right><b>Ships placed:</b></td><td><b>0 / 8</b></td></tr><tr><td align=right>Land:</td><td>0 / 3</td></tr><tr><td align=right>Sea:</td><td>0 / 4</td></tr></table></html>");

        help2.setText("<html>Click to place the ship,<br>double click on a ship to delete it</html>");

        okButton.setText("OK");
        okButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                returnBoard(board);
            }
        });

        shipView.setPreferredSize(new java.awt.Dimension(141, 141));

        javax.swing.GroupLayout shipViewLayout = new javax.swing.GroupLayout(shipView);
        shipView.setLayout(shipViewLayout);
        shipViewLayout.setHorizontalGroup(
                shipViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGap(0, 141, Short.MAX_VALUE)
        );
        shipViewLayout.setVerticalGroup(
                shipViewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGap(0, 141, Short.MAX_VALUE)
        );

        clearButton.setText("Clear");
        clearButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearBoard();
            }
        });

        randomButton.setText("Fill randomly");
        randomButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fillRandomly();
            }
        });

        loadButton.setText("Load");
        loadButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loadBoard();
            }
        });

        saveButton.setText("Save");
        saveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveBoard();
            }
        });
        
        errorMessage.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        errorMessage.setText("");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(clearButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(randomButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(loadButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(saveButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(help2, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(editorView, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(shipsPlaced)
                    .addComponent(okButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(help1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(shipView, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(errorMessage, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(editorView, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(shipView, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(help1, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(shipsPlaced, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(28, 28, 28)
                        .addComponent(errorMessage, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(okButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(clearButton)
                            .addComponent(randomButton)
                            .addComponent(loadButton)
                            .addComponent(saveButton)))
                    .addComponent(help2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pack();
    }

    private void updateCurrentPoint(Point cell) {
        boolean ok = false;
        String errorText = "";
        
        if (cell != null && ship != null) {
            int code = board.testPlacement(ship, new int[] {cell.x, cell.y});
            
            if (code == 2)
                errorText = badPiece;
            else if (code == 3)
                errorText = adjacentShips;
            
            if (code == 0)
                ok = true;
        }
        
        errorMessage.setText(errorText);      
        
        editorView.setCurrentPoint(ok ? cell : null);
        editorView.repaint();
    }
    
    private void cellClick(Point cell, int count) {
        boolean ok = cell != null && 
                ship != null && board.testPlacement(ship, new int[] {cell.x, cell.y}) == 0;

        if (ok) {
            board.attachShip(ship, new int[] {cell.x, cell.y});
            update();
            updateCurrentPoint(cell);
        } else if (cell != null && board.getPiece(cell.x, cell.y).getShip() != null && count == 2) {
            board.detachShip(board.getPiece(cell.x, cell.y).getShip());
            update();
            updateCurrentPoint(cell);
        }
    }
    
    private int[] getShipStats() {
        int[] ret = new int[]{0, 0, 0, 0, 0, 0};
        for (Ship ship : ships) {
            ret[1]++;
            if (ship.getCoords() != null) {
                ret[0]++;
                if (ship.getPieceType() == PieceType.SEA) {
                    ret[4]++;
                } else if (ship.getPieceType() == PieceType.LAND) {
                    ret[2]++;
                }
            }

            if (ship.getPieceType() == PieceType.SEA) {
                ret[5]++;
            } else if (ship.getPieceType() == PieceType.LAND) {
                ret[3]++;
            }
        }
        return ret;
    }

    private Ship nextFree() {
        for (Ship ship : ships) {
            if (ship.getCoords() == null) {
                return ship;
            }
        }

        return null;
    }

    private void rotateShip() {
        if (ship != null) {
            ship.rotate();
            shipView.repaint();
        }
    }

    private void fillRandomly() {
        if (!board.attachRandomly()) {
            JOptionPane.showMessageDialog(this, "Cannot fill the ships randomly. Try clearing the board first.", "Error", JOptionPane.ERROR_MESSAGE);

        } else {
            update();
        }
    }

    private void clearBoard() {
        for (Ship ship : ships) {
            board.detachShip(ship);
        }
        update();
    }

    private void loadBoard() {
        int ret = chooser.showOpenDialog(this);
        if (ret == javax.swing.JFileChooser.APPROVE_OPTION) {
            Board newBoard = (Board) ObjectSaver.loadObject(chooser.getSelectedFile().getName());
            if (newBoard == null) {
                JOptionPane.showMessageDialog(this, "Could not load board from file.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                setBoard(newBoard);
            }
        }
    }

    private void saveBoard() {
        int ret = chooser.showSaveDialog(this);
        if (ret == javax.swing.JFileChooser.APPROVE_OPTION) {
            if (!ObjectSaver.saveObject(board, chooser.getSelectedFile().getName())) {
                JOptionPane.showMessageDialog(this, "Could save board to file.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Board saved successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
            }

        }
    }

    public void returnBoard(Board board) {
    }

    @Override
    public void windowOpened(WindowEvent e) {
    }

    @Override
    public void windowClosing(WindowEvent e) {
        returnBoard(null);
    }

    @Override
    public void windowClosed(WindowEvent e) {
    }

    @Override
    public void windowIconified(WindowEvent e) {
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
    }

    @Override
    public void windowActivated(WindowEvent e) {
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
    }

    private javax.swing.JButton clearButton;
    private EditorView editorView;
    private javax.swing.JLabel help1;
    private javax.swing.JLabel help2;
    private javax.swing.JLabel errorMessage;
    private javax.swing.JButton loadButton;
    private javax.swing.JButton okButton;
    private javax.swing.JButton randomButton;
    private javax.swing.JButton saveButton;
    private ShipView shipView;
    private javax.swing.JLabel shipsPlaced;
    private javax.swing.JFileChooser chooser;
}
