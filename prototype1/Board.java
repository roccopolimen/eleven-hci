import java.awt.*;

public class Board
{
    private Tile [][] board;
    private boolean [][] validMoves;
    public Board()
    {
        board = new Tile[5][5];
        validMoves = new boolean[5][5];
        //Initial Tiles
        board[0][0] = new Tile(11,Color.red);
        board[0][4] = new Tile(11,Color.yellow);
        board[4][0] = new Tile(11,Color.green);
        board[4][4] = new Tile(11,Color.blue);
    }

    public Tile[][] getBoard()
    {
        return board;
    }

    public boolean [][] getValidMoves()
    {
        return validMoves;
    }

    public void setSpot(Tile t,int r, int c)
    {
        board[r][c] = t;
    }

    public void setValidMoves(Tile t)
    {
        for(int i = 0; i < 5; i++)
            for(int j = 0; j < 5; j++)
            {
                if(board[i][j] == null
                && (( i > 0 && board[i-1][j] != null && board[i-1][j].getColor().equals(t.getColor()) && board[i-1][j].getValue() > t.getValue()) 
                    || (j > 0 && board[i][j-1] != null && board[i][j-1].getColor().equals(t.getColor()) && board[i][j-1].getValue() > t.getValue()) 
                    || (i < 4 && board[i+1][j] != null && board[i+1][j].getColor().equals(t.getColor()) && board[i+1][j].getValue() > t.getValue()) 
                    || (j <	 4 && board[i][j+1] != null && board[i][j+1].getColor().equals(t.getColor()) && board[i][j+1].getValue() > t.getValue())))
                    validMoves[i][j] = true;
            }
    }

    public void resetMoves()
    {
        validMoves = new boolean[5][5];
    }

    public void setPositions()
    {
        for(int i = 0; i < 5; i++)
            for(int j = 0; j < 5; j++)
            {if(board[i][j] != null)
                {
                    board[i][j].setX(60+j*80);
                    board[i][j].setY(80+i*80);
                }
            }
    }

    public void draw(Graphics2D g2D)
    {
        setPositions();
        g2D.setColor(Color.cyan);
        g2D.fillRect(50,70,400,400);
        for(int r = 0; r < 5; r++)
            for(int c = 0;  c < 5; c++)
            {
                if(board[r][c] == null)
                {
                    g2D.setColor(Color.gray);
                    g2D.fillRect(60+c*80,80+r*80,60,60);
                }
                else
                    board[r][c].draw(g2D);
            }
    }

    public boolean filled()
    {
        for(int r = 0; r < 5; r++)
            for(int c = 0; c < 5; c++)
                if(board[r][c] == null)
                    return false;
        return true;
    }
}

