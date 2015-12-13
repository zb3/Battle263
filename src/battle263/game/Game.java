package battle263.game;

public class Game {
    public static final int PLAYER = 0;
    public static final int CPU = 1;
    
    private boolean over = false;
    private int winner = -1;
    private int nextMove = PLAYER;
    private final Board[] boards = new Board[] {new Board(), new Board()};
    
    public Game() {
        this(PLAYER);
    }
    
    public Game(int firstMove) {
        nextMove = firstMove;
        boards[CPU].attachRandomly();
    }
    
    //void? well, we'll repaint the state anyway.. (like React :D)
    public void shoot(int x, int y) {
        if (isOver()) return;
        
        if (!boards[1-nextMove].shoot(new int[] {x, y})) return;
          
        finishMove();
        
        if (!isOver())
            AIShoot();
    }
    
    public void AIShoot() {
        boards[1-nextMove].AIShoot();
        finishMove();
    }
    
    private void finishMove() {
        int player = 1-nextMove;
        
        if (boards[player].getShipsLeft() == 0) {
            winner = nextMove;
            over = true;
        }
        
        nextMove = player;
    }
    
    public Board getBoard(int player) {
        return boards[player];
    }
    
    public void setBoard(int player, Board board) {
        boards[player] = board;
    }

    public boolean isOver() {
        return over;
    }

    public int getWinner() {
        return winner;
    }

    public int getNextMove() {
        return nextMove;
    }    
    
}
