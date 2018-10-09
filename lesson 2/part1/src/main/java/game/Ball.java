package game;

import java.awt.*;
import java.util.Random;

public class Ball {
    private static final int MAX_SIZE = 120;
    private static Color m_tGreen = new Color(0, 255, 0, 150);
    private static Random random = new Random();
    private int x, y, width, height;

    public Color getColor() {
        return m_tGreen;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void goDown() {
        if (y  + height > SecretGame.MAX_HEIGHT)
            y = y - width;
        y = y + 10;
    }

    public static Ball random() {
        return random(random.nextInt(SecretGame.MAX_WIDTH), random.nextInt(SecretGame.MAX_HEIGHT));
    }
    public static Ball random(int x, int y) {
        Ball ball = new Ball();
        ball.setX(x);
        ball.setY(y);
        int size = random.nextInt(MAX_SIZE);
        ball.setWidth(size);
        ball.setHeight(size);
        return ball;
    }
}
