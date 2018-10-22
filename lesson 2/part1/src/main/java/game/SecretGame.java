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
                Ball ball = Ball.random(ev.getX(), ev.getY());
                world.add(ball);
                repaint();

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
        g.setColor(Color.magenta);
        world.getObjects().forEach(ball -> {
                              g.fillOval(ball.getX(), ball.getY(), ball.getWidth(), ball.getHeight());
                          });

        // TODO 2 draw the ground. Hint: upper right corner is has the 2D coordinates: (x,y) ->(0,0)

        g.setColor(Color.black);
        g.fillRect(0, getHeight() - 30, getWidth(), 30);



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

        // FIXME after working on the App class can you change this implementation so that
        // every ball instance is actually created only when a file is processed successfully?
        // can you write the filepath in the center of the ball?
//        TODO does any of the principles below help you?
//        Single responsibility principle[6]
//        a class should have only a single responsibility (i.e. changes to only one part of the software's specification should be able to affect the specification of the class).
//        Open/closed principle[7]
//        "software entities … should be open for extension, but closed for modification."
//        Liskov substitution principle[8]
//        "objects in a program should be replaceable with instances of their subtypes without altering the correctness of that program." See also design by contract.
//        Interface segregation principle[9]
//        "many client-specific interfaces are better than one general-purpose interface."[4]
//        Dependency inversion principle[10]
//        one should "depend upon abstractions, [not] concretions."[4]
    }
}
           
         
    