import java.util.*;
import java.awt.*;

public class Stack
{
    private ArrayList<Tile> tiles;

    public Stack()
    {
        tiles = new ArrayList<Tile>();
        Color [] tempColor = {Color.red,Color.green,Color.yellow,Color.blue};
        for(int c = 0; c < 4; c++)
            for(int n = 1; n < 11; n++)
                tiles.add(new Tile(n,tempColor[c]));
    }

    public ArrayList<Tile> getStack()
    {
        return tiles;
    }

    public Tile remove(int index)
    {
        return tiles.remove(index);
    }

    public void shuffle()
    {
        for(int i = 0; i < tiles.size(); i++)
        {
            int spot = (int)(Math.random() * tiles.size());
            tiles.set(i,tiles.set(spot,tiles.get(i)));
        }
    }

    public void draw(Graphics2D g2D)
    {
        g2D.setColor(Color.cyan);
        g2D.fillRect(130,480,240,80);
        for(int i = 0; i < 3; i++)
        {
            tiles.get(i).setX(140 + 80*i);
            tiles.get(i).setY(490);
            tiles.get(i).draw(g2D);
        }
    }

    public void setInvalid(Board b)
    {
        boolean valid = false;
        for(int n = 0; n < 3; n++)
        {
            valid = false;
            b.setValidMoves(tiles.get(n));
            for(int i = 0; i < 5; i++)
                for(int j = 0; j < 5; j++)
                    if(b.getValidMoves()[i][j])
                        valid = true;
            if(!valid)
                tiles.get(n).setValid(false);
            b.resetMoves();
        }
    }

    public boolean noMoves()
    {
        for(int i = 0; i < 3; i++)
            if(tiles.get(i).getValid())
                return false;
        return true;
    }
}

