import javax.swing.*;

public class Eleven extends JFrame
{
    private ImageIcon ElevenIcon = new ImageIcon("ElevenIcon.png");
    
    public Eleven()
    {
        super.setTitle("Eleven");
        super.setIconImage(ElevenIcon.getImage());
        super.setSize(500,600);
        super.setLocation(450,50);
        super.setResizable(false);
        super.add(new ElevenContents());
        super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        super.setVisible(true);
    }

    public static void main(String [] args)
    {
        new Eleven();
    }
}
