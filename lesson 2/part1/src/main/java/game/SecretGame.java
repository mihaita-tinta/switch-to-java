package game;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;

public class SecretGame extends JComponent {
    public static final int MAX_WIDTH = 400;
    public static final int MAX_HEIGHT = 400;

    private final World world;
    public SecretGame(JFrame container) {
        this.world = new World(container);
        addListener(container);
    }

    public void addListener(JFrame container) {
        container.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent ev){
                System.out.println("mouseClicked - " + ev);
                // TODO 1 create a new ball everytime the user clicks something
            }
        });
    }
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        System.out.println("paintComponent - render world");

        // draw entire component white
        g.setColor(Color.white);
        g.fillRect(0, 0, getWidth(), getHeight());

        // TODO 2 render all objects from the world

        // TODO 2 draw the ground. Hint: upper right corner is has the 2D coordinates: (x,y) ->(0,0)

    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(MAX_WIDTH, MAX_HEIGHT);
    }

    @Override
    public Dimension getMinimumSize() {
        return getPreferredSize();
    }


    public static void main(String args[]) {
        JFrame mainFrame = new JFrame("Graphics demo");
        mainFrame.getContentPane().add(new SecretGame(mainFrame));
        mainFrame.pack();
        mainFrame.setVisible(true);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
           
         
    