import java.awt.*;

public class RButton
{
    private int x, y, w, h;
    private String text;
    private static Font font1 = new Font("arial",Font.BOLD,26);
    public RButton(String text,int x,int y, int w, int h)
    {
        this.text = text;
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    public void draw(Graphics2D g2D)
    {
        g2D.setColor(Color.lightGray);
        g2D.fillRect(x,y,w,h);
        g2D.setColor(Color.black);
        drawCenteredString(g2D,text,new Rectangle(x,y,w,h),font1);
    }

    public void drawCenteredString(Graphics2D g2D, String text, Rectangle rect, Font font) 
    {
        FontMetrics metrics = g2D.getFontMetrics(font);
        int x = rect.x + (rect.width - metrics.stringWidth(text)) / 2;
        int y = rect.y + ((rect.height - metrics.getHeight()) / 2) + metrics.getAscent();
        g2D.setFont(font);
        g2D.drawString(text, x, y);
    }

    public boolean clicked(int x, int y)
    {
        return x - this.x <= w
        && x - this.x >= 0
        && y - this.y <= h
        && y - this.y >= 0;
    }
}