package battle263.GUI;

import battle263.game.*;
import java.awt.Point;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class GFrame extends JDialog {
    private Game game = new Game();
    private final Board playerBoard;
    private final Board cpuBoard;
    
    private static final String shotCountTPL = "<html>%d ships, %d successful shots remaining.<br>%d / %d shots so far.</html>";

    public GFrame(JFrame parent, Board board) {
        super(parent);
        setModal(true);
                
        playerBoard = board;
        game.setBoard(Game.PLAYER, board);
        cpuBoard = game.getBoard(Game.CPU);
        initComponents();
        setLocationRelativeTo(null);
        update();
        setVisible(true);
    }
           
    private void update() {
        playerShips.repaint();
        cpuShips.repaint();
        
        int playerShips = playerBoard.getShips().length;
        int cpuShips = cpuBoard.getShips().length;
        
        playerShipsCount.setText((playerShips-playerBoard.getShipsLeft())+" / "+playerShips);
        cpuShipsCount.setText((cpuShips-cpuBoard.getShipsLeft())+" / "+cpuShips);

        playerShots.setText(String.format(shotCountTPL, playerBoard.getShipsLeft(), playerBoard.getShotsLeft(), playerBoard.getShotsFired(), playerBoard.getShotsFiredAll()));
        cpuShots.setText(String.format(shotCountTPL, cpuBoard.getShipsLeft(), cpuBoard.getShotsLeft(), cpuBoard.getShotsFired(), cpuBoard.getShotsFiredAll()));
        
        if (game.isOver()) {
            gameResult.setText(game.getWinner() == Game.PLAYER ? "<html><b><font color=green>You won!</font></b></html>" : "<html><b><font color=red>You lost!</font></b></html>");
        }
        
    }
    
    public void newGameClicked() {
        
    }
    
    public void shoot(Point cell) {
        if (!game.isOver()) {
            game.shoot(cell.x, cell.y);            
            update();
        }
    }
    
    private void initComponents() {
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        
        playerName = new javax.swing.JLabel();
        newGame = new javax.swing.JButton();
        playerShips = new BoardView(game.getBoard(Game.PLAYER), false);
        cpuShips = new BoardView(game.getBoard(Game.CPU), true) {
            protected void cellUp(Point cell) {
                if (cell != null)
                    shoot(cell);
            }
        };
        playerShipsCount = new javax.swing.JLabel();
        cpuShipsCount = new javax.swing.JLabel();
        player2Name = new javax.swing.JLabel();
        gameResult = new javax.swing.JLabel();
        playerShots = new javax.swing.JLabel();
        cpuShots = new javax.swing.JLabel();


        playerName.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        playerName.setText("Player");

        newGame.setText("New Game");
        newGame.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                if (!game.isOver()) {
                    Object[] options = {"Yes","No"};
                    int really = JOptionPane.showOptionDialog(GFrame.this,
                            "Abandon current game?", "Battle263", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,
                            null, options, options[1]);
                    
                    if (really == JOptionPane.NO_OPTION)
			return;
                }
                newGameClicked();
            }
        });

        playerShips.setPreferredSize(new java.awt.Dimension(573, 365));
        playerShips.setRequestFocusEnabled(false);

        javax.swing.GroupLayout playerShipsLayout = new javax.swing.GroupLayout(playerShips);
        playerShips.setLayout(playerShipsLayout);
        playerShipsLayout.setHorizontalGroup(
            playerShipsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 573, Short.MAX_VALUE)
        );
        playerShipsLayout.setVerticalGroup(
            playerShipsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 365, Short.MAX_VALUE)
        );

        cpuShips.setPreferredSize(new java.awt.Dimension(573, 365));
        cpuShips.setRequestFocusEnabled(false);

        javax.swing.GroupLayout cpuShipsLayout = new javax.swing.GroupLayout(cpuShips);
        cpuShips.setLayout(cpuShipsLayout);
        cpuShipsLayout.setHorizontalGroup(
            cpuShipsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 573, Short.MAX_VALUE)
        );
        cpuShipsLayout.setVerticalGroup(
            cpuShipsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 365, Short.MAX_VALUE)
        );

        playerShipsCount.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        playerShipsCount.setText("8 / 8");

        cpuShipsCount.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        cpuShipsCount.setText("8 / 8");

        player2Name.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        player2Name.setText("CPU");

        gameResult.setFont(new java.awt.Font("Dialog", 1, 18)); // NOI18N
        gameResult.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        gameResult.setText(" ");

        playerShots.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        playerShots.setText("8 ships, 6 successful shots remaining");

        cpuShots.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        cpuShots.setText("8 ships, 6 successful shots remaining");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(playerName)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(playerShipsCount))
                            .addComponent(playerShips, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(player2Name)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(cpuShipsCount))
                            .addComponent(cpuShips, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(playerShots, javax.swing.GroupLayout.PREFERRED_SIZE, 278, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(newGame, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(gameResult, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cpuShots, javax.swing.GroupLayout.PREFERRED_SIZE, 276, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(playerName)
                    .addComponent(playerShipsCount)
                    .addComponent(cpuShipsCount)
                    .addComponent(player2Name))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(playerShips, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cpuShips, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(playerShots, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cpuShots, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(gameResult, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(newGame)
                .addContainerGap())
        );

        pack();
    }   
    
    // Variables declaration - do not modify                     
    private javax.swing.JPanel cpuShips;
    private javax.swing.JLabel cpuShipsCount;
    private javax.swing.JLabel cpuShots;
    private javax.swing.JLabel gameResult;
    private javax.swing.JButton newGame;
    private javax.swing.JLabel player2Name;
    private javax.swing.JLabel playerName;
    private javax.swing.JPanel playerShips;
    private javax.swing.JLabel playerShipsCount;
    private javax.swing.JLabel playerShots;
    // End of variables declaration                   
}
