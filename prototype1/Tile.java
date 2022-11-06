import java.awt.*;

public class Tile
{
    public static int blockSize = 60;
    private int x,y;
    private int value;
    private boolean valid;
    private Color color;
    private static Font f1 = new Font("Droid Serif",1,24);
    public Tile(int value, Color color)
    {
        this.value = value;
        this.color = color;
        valid = true;
        x = -200;
        y = -200;
    }

    public void setX(int x)
    {
        this.x = x;
    }

    public void setY(int y)
    {
        this.y = y;
    }

    public void setValid(boolean valid)
    {
        this.valid = valid;
    }

    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }

    public int getValue()
    {
        return value;
    }

    public boolean getValid()
    {
        return valid;
    }

    public Color getColor()
    {
        return color;
    }

    public void draw(Graphics2D g2D)
    {
        g2D.setColor(color);
        g2D.fillRect(x,y,blockSize,blockSize);
        g2D.setColor(Color.black);
        drawCenteredString(g2D,value+"",new Rectangle(x,y,blockSize,blockSize),f1);
        if(!valid) //For Tiles that are in stack and can't be played
        {
            //puts a light blur on top of the tile
            g2D.setColor(new Color (255,255,255,100));
            g2D.fillRect(x,y,blockSize,blockSize);
        }
    }

    public void drawCenteredString(Graphics2D g2D, String text, Rectangle rect, Font font) 
    {
        FontMetrics metrics = g2D.getFontMetrics(font);
        int x = rect.x + (rect.width - metrics.stringWidth(text)) / 2;
        int y = rect.y + ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent();
        g2D.setFont(font);
        g2D.drawString(text, x, y);
    }

    public String toString()
    {
        return color.toString() + " " + value;
    }
}

