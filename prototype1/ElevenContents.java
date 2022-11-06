import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ElevenContents extends JPanel implements Runnable, MouseListener
{
    private Thread main = new Thread(this);
    private StopWatch timer = new StopWatch();
    private Board board = new Board();
    private Stack stack = new Stack();
    private ScoreList scores = new ScoreList("README.txt");
    private STATE State = STATE.HOME;
    private RButton playBtn, rulesBtn, highScoreBtn;
    private RButton homeBtn, resetBtn;
    private int selected = -1;
    private boolean gameStart = true;
    private boolean viewBoard = false;
    private STATE temp = STATE.GAME;
    public ElevenContents()
    {
        super.setDoubleBuffered(true);
        addMouseListener(this);
        main.start();
        stack.shuffle();
        playBtn = new RButton("START GAME",150,160,200,100);
        highScoreBtn = new RButton("High Scores",150,275,200,100);
        rulesBtn = new RButton("How To Play",150,390,200,100);
        homeBtn = new RButton("Main Menu",150,380,200,100);
        resetBtn = new RButton("Play Again",150,275,200,100);
    }

    private enum STATE{HOME,GAME,WIN,LOSE,RULES,SCORES}; //Different Game States

    public void run()
    {
        while(true)
        {
            try{main.sleep(25);}catch(Exception e) {}
            repaint();
        }
    }

    public void mousePressed(MouseEvent e)
    {
        int x = e.getX();
        int y = e.getY();
        if(State == STATE.LOSE || State == STATE.WIN) //End Game
        {
            if(x < 100 || x > 400 || y < 150 || y > 500)
            {
                temp = State;
                State = STATE.GAME;
                resetBtn = new RButton("Reset",-200,-200,150,75);
                homeBtn = new RButton("Home",-200,-200,150,75);
            }
            if(homeBtn.clicked(x,y))
            {
                timer = new StopWatch();
                board = new Board();
                stack = new Stack();
                stack.shuffle();
                State = STATE.HOME;
                playBtn = new RButton("START GAME",150,160,200,100);
                highScoreBtn = new RButton("High Scores",150,275,200,100);
                rulesBtn = new RButton("How To Play",150,390,200,100);
                homeBtn = new RButton("Main Menu",150,380,200,100);
                resetBtn = new RButton("Play Again",150,275,200,100);
                selected = -1;
                gameStart = true;   
            }
            if(resetBtn.clicked(x,y))
            {
                timer = new StopWatch();
                board = new Board();
                stack = new Stack();
                stack.shuffle();
                State = STATE.GAME;
                playBtn = new RButton("START GAME",150,160,200,100);
                highScoreBtn = new RButton("High Scores",150,275,200,100);
                rulesBtn = new RButton("How To Play",150,390,200,100);
                resetBtn = new RButton("Reset",-25,500,150,75);
                homeBtn = new RButton("Home",375,500,150,75);
                selected = -1;
                gameStart = true;
            }
        }
        else if(State == STATE.HOME)
        {
            if(playBtn.clicked(x,y))
            {
                State = STATE.GAME;
                resetBtn = new RButton("Reset",-25,500,150,75);
                homeBtn = new RButton("Home",375,500,150,75);
            }
            if(rulesBtn.clicked(x,y))
            {
                State = STATE.RULES;
                homeBtn = new RButton("Main Menu",150,500,200,100);
            }
            if(highScoreBtn.clicked(x,y))
            {
                State = STATE.SCORES;
                homeBtn = new RButton("Main Menu",150,500,200,100);
            }
        }
        else if(State == STATE.RULES)
        {
            if(homeBtn.clicked(x,y))
            {
                homeBtn = new RButton("Main Menu",150,175,200,100);
                State = STATE.HOME;
            }
        }
        else if(State == STATE.SCORES)
        {
            if(homeBtn.clicked(x,y))
            {
                homeBtn = new RButton("Main Menu",150,175,200,100);
                State = STATE.HOME;
            }
        }
        else if(State == STATE.GAME)
        {
            if(resetBtn.clicked(x,y))
            {
                timer = new StopWatch();
                board = new Board();
                stack = new Stack();
                stack.shuffle();
                State = STATE.GAME;
                selected = -1;
                gameStart = true;
            }
            else if(homeBtn.clicked(x,y))
            {
                timer = new StopWatch();
                board = new Board();
                stack = new Stack();
                stack.shuffle();
                State = STATE.HOME;
                playBtn = new RButton("START GAME",150,160,200,100);
                highScoreBtn = new RButton("High Scores",150,275,200,100);
                rulesBtn = new RButton("How To Play",150,390,200,100);
                homeBtn = new RButton("Main Menu",150,380,200,100);
                resetBtn = new RButton("Play Again",150,275,200,100);
                selected = -1;
                gameStart = true;   
            }
            else
            {
                int row = -1;
                int col = -1;
                int stackIndex = -1;
                for(int i = 0; i < 5; i++)
                    for(int j = 0; j < 5; j++)
                    {

                        if(x - (60+j*80) <= 60
                        && x - (60+j*80) >= 0
                        && y - (80+i*80) <= 60
                        && y - (80+i*80) >= 0)
                        {
                            row = i;
                            col = j;
                        }
                }

                for(int n = 0; n < 3; n++)
                    if(x - stack.getStack().get(n).getX() <= 60
                    && x - stack.getStack().get(n).getX() >= 0
                    && y - stack.getStack().get(n).getY() <= 60
                    && y - stack.getStack().get(n).getY() >= 0)
                        stackIndex = n;

                if(selected > -1 && row > -1 && col > -1
                && board.getValidMoves()[row][col])
                {
                    board.setSpot(stack.remove(selected),row,col);
                    selected = -1;
                    board.resetMoves();
                    stack.setInvalid(board);
                    if(gameStart)
                    {
                        gameStart = false;
                        timer.start();
                    }

                    if(board.filled())
                    {
                        timer.stop();
                        scores.addScore(timer.elapsedTime());
                        resetBtn = new RButton("Play Again",150,275,200,100);
                        homeBtn = new RButton("Main Menu",150,390,200,100);
                        State = STATE.WIN;
                    }
                    else if(stack.noMoves())
                    {
                        timer.stop();
                        resetBtn = new RButton("Play Again",150,275,200,100);
                        homeBtn = new RButton("Main Menu",150,390,200,100);
                        State = STATE.LOSE;
                    }
                }
                else if(stackIndex > -1)
                {
                    selected = stackIndex;
                    board.resetMoves();
                    board.setValidMoves(stack.getStack().get(stackIndex));
                }
            }
        }
    }

    public void mouseClicked(MouseEvent e){}

    public void mouseReleased(MouseEvent e)
    {
        int x = e.getX();
        int y = e.getY();
        if(State == STATE.GAME && temp != STATE.GAME)
        {
            if(x < 100 || x > 400 || y < 150 || y > 500)
            {
                State = temp;
                temp = STATE.GAME;
                resetBtn = new RButton("Play Again",150,275,200,100);
                homeBtn = new RButton("Main Menu",150,390,200,100);
            }
        }
    }

    public void mouseEntered(MouseEvent e){}

    public void mouseExited(MouseEvent e){}

    public void drawCenteredString(Graphics2D g2D, String text, Rectangle rect, Font font) 
    {
        FontMetrics metrics = g2D.getFontMetrics(font);
        int x = rect.x + (rect.width - metrics.stringWidth(text)) / 2;
        int y = rect.y + ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent();
        g2D.setFont(font);
        g2D.drawString(text, x, y);
    }

    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);//Clear Screen
        Graphics2D g2D = (Graphics2D)g;
        //Background
        g2D.setColor(Color.lightGray);
        g2D.fillRect(0,0,500,600);
        if(State == STATE.HOME || State == STATE.RULES || State == STATE.SCORES)
        {
            //Title
            Font font2 = new Font("arial",Font.BOLD,48);
            g2D.setColor(Color.black);
            drawCenteredString(g2D,"ELEVEN",new Rectangle(55,25,400,80),font2);
            drawCenteredString(g2D,"ELEVEN",new Rectangle(52,22,400,80),font2);
            g2D.setColor(Color.cyan);
            g2D.setColor(new Color((int)(Math.random()*256),(int)(Math.random()*256),(int)(Math.random()*256)));
            drawCenteredString(g2D,"ELEVEN",new Rectangle(50,20,400,80),font2);
            if(State == STATE.HOME)
            {
                //Remote
                g2D.setColor(Color.darkGray);
                g2D.fillRect(100,150,300,350);
                playBtn.draw(g2D);
                highScoreBtn.draw(g2D);
                rulesBtn.draw(g2D);
            }
            if(State == STATE.RULES)
            {
                //output rules
                g2D.setColor(Color.white);
                Font f4 = new Font("arial",Font.BOLD,30);
                Font f5 = new Font("arial",Font.PLAIN,24);
                g2D.setFont(f4);
                g2D.drawString("Game Objective:",20,120);
                g2D.setFont(f5);
                g2D.drawString("Place tiles next to tiles with a greater",40,150);
                g2D.drawString("value, and same color.",50,170);
                g2D.drawString("Fill the Grid as quickly as possible!",40,190);
                g2D.setFont(f4);
                g2D.drawString("Using the Mouse Interface",20,230);
                g2D.setFont(f5);
                g2D.drawString("Select a tile from the bottom row and",40,260);
                g2D.drawString("place it on a green space.",50,280);

                //back button
                homeBtn.draw(g2D);
            }
            if(State == STATE.SCORES)
            {
                //output top five Scores
                Font f6 = new Font("Helvetica",Font.BOLD,32);
                g2D.setColor(Color.white);
                String [] top5 = scores.topFive();
                for(int i = 0; i < 5; i++)
                    drawCenteredString(g2D,(i+1)+". "+top5[i],new Rectangle(100,150+60*i,300,60),f6);
                //back button
                homeBtn.draw(g2D);
            }
        }
        if(State == STATE.GAME || State == STATE.WIN || State == STATE.LOSE)        
        {
            //Title
            String title = "ELEVEN";
            Font f3 = new Font("Helvetica",Font.BOLD,28);
            g2D.setFont(f3);
            for(int n = 0; n < title.length(); n++)
            {
                if(n%2 == 0)
                    g2D.setColor(Color.yellow);
                else
                    g2D.setColor(Color.blue);
                g2D.drawString(title.substring(n,n+1),5,100+70*n);
                if(n%2 == 0)
                    g2D.setColor(Color.red);
                else
                    g2D.setColor(Color.green);
                g2D.drawString(title.substring(n,n+1),470,100+70*n);
            }
            //Game Set-up
            board.draw(g2D);
            stack.draw(g2D);
            //ScoreBoard
            g2D.setColor(Color.darkGray);
            g2D.fillRect(50,10,400,60);
            g2D.setColor(Color.lightGray);
            g2D.fillRect(60,20,180,40);
            g2D.fillRect(260,20,180,40);
            g2D.setColor(Color.black);
            Font f1 = new Font("arial",0,16);
            Font f2 = new Font("Old Standard TT",Font.BOLD,24);
            drawCenteredString(g2D,"Your Time",new Rectangle(60,20,180,15),f1);
            drawCenteredString(g2D,"Best Time",new Rectangle(260,20,180,15),f1);
            drawCenteredString(g2D,timer.elapsedTime()+"",new Rectangle(60,30,180,30),f2);
            drawCenteredString(g2D,scores.highScore(),new Rectangle(260,30,180,30),f2);
            if(State == STATE.GAME)
            {
                homeBtn.draw(g2D);
                resetBtn.draw(g2D);
            }
        }
        if(State == STATE.WIN || State == STATE.LOSE)
        {
            //Background blur
            g2D.setColor(new Color(255,255,255,150));
            g2D.fillRect(0,0,500,600);
            //Remote
            g2D.setColor(Color.darkGray);
            g2D.fillRect(100,150,300,350);
            Font f7 = new Font("arial",Font.BOLD + Font.ITALIC,36);
            g2D.setColor(Color.white);
            g2D.drawRect(150,160,200,100);
            if(State == STATE.WIN)
                drawCenteredString(g2D,"You Won!",new Rectangle(150,160,200,100),f7);
            else
                drawCenteredString(g2D,"Try Again",new Rectangle(150,160,200,100),f7);
            homeBtn.draw(g2D);
            resetBtn.draw(g2D);
        }
    }
}